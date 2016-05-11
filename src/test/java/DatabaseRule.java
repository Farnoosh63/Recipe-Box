import org.junit.rules.ExternalResource;
import org.sql2o.*;

  public class DatabaseRule extends ExternalResource {

    @Override
    protected void before() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
    }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      String deleteRecipesQuery = "DELETE FROM recipes *;";
      String deleteIngredientsQuery = "DELETE FROM ingredients*;";
      String deleteRecipesCategoriesQuery = "DELETE FROM recipes_categories *;";
      String deleteRecipesIngredientsQuery = "DELETE FROM recipes_ingredients*;";
      con.createQuery(deleteCategoriesQuery).executeUpdate();
      con.createQuery(deleteRecipesQuery).executeUpdate();
      con.createQuery(deleteIngredientsQuery).executeUpdate();
      con.createQuery(deleteRecipesCategoriesQuery).executeUpdate();
      con.createQuery(deleteRecipesIngredientsQuery).executeUpdate();
    }
  }
}
