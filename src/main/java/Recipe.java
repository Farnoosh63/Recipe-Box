import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;


public class Recipe {
  private int id;
  private String recipe_name;

  public Recipe(String name) {
    this.recipe_name = name;
  }

  public String getName() {
    return recipe_name;
  }

  public int getId() {
    return id;
  }

  public static List<Recipe> all() {
    String sql = "SELECT id, recipe_name FROM recipes";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getName().equals(newRecipe.getName()) &&
             this.getId() == newRecipe.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes(recipe_name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.recipe_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes where id=:id";
      Recipe recipe = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
      return recipe;
    }
  }


  // public static List<String> findRecipeByName(String name) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT id FROM recipes WHERE name=:name ";
  //     List<Integer> recipeIds = con.createQuery(sql)
  //       .addParameter("name", name)
  //       .executeAndFetch(Integer.class);
  //
  //       List<Integer> categoryIds = new ArrayList<>();
  //       for (Integer recipeId : recipeIds) {
  //         String recipeQuery = "SELECT category_id FROM recipes_categories WHERE id = :recipeId";
  //         Integer idForRecipe = con.createQuery(recipeQuery)
  //           .addParameter("recipeId", recipeId)
  //           .executeAndFetchFirst(Integer.class);
  //         categoryIds.add(idForRecipe);
  //       }
  //       List<String> categoriesName = new ArrayList<String>();
  //       for (Integer categoryId : categoryIds) {
  //         String categoryQuery = "SELECT name FROM categories WHERE id = :categoryId";
  //         String categoryNameById = con.createQuery(categoryQuery)
  //           .addParameter("categoryId", categoryId)
  //           .executeAndFetchFirst(String.class);
  //         categoriesName.add(categoryNameById);
  //       }
  //       System.out.println(categoriesName);
  //     return categoriesName ;
  //   }
  // }

  public void addCategory(Category category) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_categories (recipe_id, category_id) VALUES (:recipe_id, :category_id)";
      con.createQuery(sql)
        .addParameter("recipe_id", this.getId())
        .addParameter("category_id", category.getId())
        .executeUpdate();
    }
  }

  public List<Category> getCategories() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT category_id FROM recipes_categories WHERE recipe_id = :recipe_id";
      List<Integer> categoryIds = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Category> categories = new ArrayList<Category>();

      for (Integer categoryId : categoryIds) {
        String categoryQuery = "SELECT * FROM categories WHERE id = :categoryId";
        Category category = con.createQuery(categoryQuery)
          .addParameter("categoryId", categoryId)
          .executeAndFetchFirst(Category.class);
        categories.add(category);
      }
      return categories;
    }
  }



  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM recipes WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM recipes_categories WHERE recipe_id = :recipeId";
        con.createQuery(joinDeleteQuery)
          .addParameter("recipeId", this.getId())
          .executeUpdate();
      }
    }
    public void update(String newRecipe) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "UPDATE recipes SET recipe_name = :name WHERE id = :id";
        con.createQuery(sql)
          .addParameter("name", newRecipe)
          .addParameter("id", this.id)
          .executeUpdate();
      }
    }

}
