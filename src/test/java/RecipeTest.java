import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectlt_true() {
    Recipe myRecipe = new Recipe("Pasta");
    assertEquals(true, myRecipe instanceof Recipe);
  }

  @Test
  public void getName_recipeInstantiatesWithName_String() {
    Recipe myRecipe = new Recipe("Pasta");
    assertEquals("Pasta", myRecipe.getName());
  }

  @Test
  public void defaultRating_initiateCorrectly_int() {
    Recipe myRecipe = new Recipe("Pasta");
    assertEquals(3, myRecipe.getRating());
  }

  @Test
  public void getRating_getTheRating_int() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    myRecipe.updateRating(5);
    assertEquals(5, myRecipe.findRating());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

   @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Recipe firstRecipe = new Recipe("Pasta");
    Recipe secondRecipe = new Recipe("Pasta");
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    assertTrue(Recipe.all().get(0).equals(myRecipe));
  }

  @Test
   public void save_assignsIdToObject() {
     Recipe myRecipe = new Recipe("Pasta");
     myRecipe.save();
     Recipe savedRecipe = Recipe.all().get(0);
     assertEquals(myRecipe.getId(), savedRecipe.getId());
   }

 @Test
  public void find_findRecipeInDatabase_true() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Recipe savedRecipe = Recipe.find(myRecipe.getId());
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void addCategory_addsCategoryToRecipe_true() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Category myCategory = new Category("Italian");
    myCategory.save();
    myRecipe.addCategory(myCategory);
    Category savedCategory = myRecipe.getCategories().get(0);
    assertTrue(myCategory.equals(savedCategory));
  }

  @Test
  public void getCategories_returnsAllCategories_List() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Category myCategory = new Category("Italian");
    myCategory.save();
    myRecipe.addCategory(myCategory);
    List savedCategories = myRecipe.getCategories();
    assertEquals(1, savedCategories.size());
  }

  @Test
  public void getIngredients_returnsAllIngredients_List() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    myRecipe.addIngredient(myIngredient);
    List savedIngredients= myRecipe.getIngredients();
    assertEquals(1, savedIngredients.size());
  }

  @Test
  public void delete_deletesAllCategoriesAndIngredientsAndRecipesAssociations() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Category myCategory = new Category("Italian");
    myCategory.save();
    myRecipe.addCategory(myCategory);
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    myRecipe.addIngredient(myIngredient);
    myRecipe.delete();
    assertEquals(0, myCategory.getRecipes().size());
    assertEquals(0, myIngredient.getRecipes().size());
  }
}
