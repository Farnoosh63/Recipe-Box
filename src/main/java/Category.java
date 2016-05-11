import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Category {
  private int id;
  private String category_name;


  public Category(String name){
    this.category_name = name;
  }

  public String getName() {
    return category_name;
  }

  public int getId() {
    return id;
  }


  public static List<Category> all() {
    String sql = "SELECT id, name FROM categories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  @Override
  public boolean equals(Object otherCategory) {
    if (!(otherCategory instanceof Category)) {
      return false;
    } else {
      Category newCategory =  (Category) otherCategory;
      return this.getName().equals(newCategory.getName()) &&
             this.getId() == newCategory.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO categories (category_name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.category_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM categories WHERE id=:id";
      Category category = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return category;
    }
  }
  public void update(String newCategory) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE categories SET category_name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newCategory)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM categories WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM recipes_categories WHERE category_id = :categoryId";
        con.createQuery(joinDeleteQuery)
          .addParameter("categoryId", this.getId())
          .executeUpdate();
    }
  }

  public void addRecipe(Recipe recipe) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO recipes_categories (recipe_id, category_id) VALUES (:recipe_id, :category_id)";
    con.createQuery(sql)
      .addParameter("recipe_id", recipe.getId())
      .addParameter("category_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT recipe_id FROM recipes_categories WHERE category_id = :category_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("category_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for (Integer recipeId : recipeIds) {
        String categoryQuery = "Select * From recipes WHERE id = :recipeId";
        Recipe recipe = con.createQuery(categoryQuery)
          .addParameter("recipeId", recipeId)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      return recipes;
    }
  }
}
