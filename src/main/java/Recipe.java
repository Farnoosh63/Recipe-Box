import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;


public class Recipe {
  private int id;
  private String recipe_name;
  private int rating;

  public Recipe(String name) {
    this.recipe_name = name;
    rating = 3;
  }

  public String getName() {
    return recipe_name;
  }

  public int getId() {
    return id;
  }

  public int getRating() {
    return rating;
  }

  public static List<Recipe> all() {
    String sql = "SELECT id, recipe_name, rating FROM recipes";
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
      String sql = "INSERT INTO recipes(recipe_name,rating ) VALUES (:name, :rating)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.recipe_name)
      .addParameter("rating", 3)
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


  public int findRating() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT rating FROM recipes where id=:id";
      int recipe = con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetchFirst(Integer.class);
      return recipe;
    }
  }

  public static List<Recipe> sortByAscending() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes ORDER BY rating ASC ";

      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  public static List<Recipe> sortByDescending() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes ORDER BY rating DESC ";

      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }


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


  public void addIngredient(Ingredient ingredient) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_ingredients(recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id)";
      con.createQuery(sql)
      .addParameter("recipe_id", this.getId())
      .addParameter("ingredient_id", ingredient.getId())
      .executeUpdate();
    }
  }

  public List<Ingredient> getIngredients() {
    try(Connection con = DB.sql2o.open()){

      String joinQuery = "SELECT ingredient_id FROM recipes_ingredients WHERE recipe_id = :recipe_id";
      List<Integer> ingredientIds = con.createQuery(joinQuery)
      .addParameter("recipe_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Ingredient> ingredients= new ArrayList<Ingredient>();

      for (Integer ingredientId : ingredientIds) {
        String ingredientQuery = "SELECT * FROM ingredients WHERE id = :ingredientId";
        Ingredient ingredient = con.createQuery(ingredientQuery)
        .addParameter("ingredientId", ingredientId)
        .executeAndFetchFirst(Ingredient.class);
        ingredients.add(ingredient);
      }
      return ingredients;
    }
  }



  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM recipes WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", this.getId())
      .executeUpdate();

      String joinDeleteRecipesCategoriesQuery = "DELETE FROM recipes_categories WHERE recipe_id = :recipeId";
      con.createQuery(joinDeleteRecipesCategoriesQuery)
      .addParameter("recipeId", this.getId())
      .executeUpdate();


      String joinDeleteRecipesIngredientsQuery = "DELETE FROM recipes_ingredients WHERE recipe_id = :recipeId";
      con.createQuery(joinDeleteRecipesIngredientsQuery)
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

  public void updateRating(int newRating) {
    // this.rating = newRating;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET rating = :rating WHERE id = :id";
      con.createQuery(sql)
      .addParameter("rating", newRating)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

}
