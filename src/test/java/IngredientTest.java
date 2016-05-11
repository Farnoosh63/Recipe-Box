import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;
import java.util.List;

public class IngredientTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();



 @Test
 public void Ingredient_instantiatesCorrectly_true(){
   Ingredient myIngredient = new Ingredient("savory");
   assertEquals(true, myIngredient instanceof Ingredient);
 }

 @Test
  public void getName_ingredientWithName_String(){
    Ingredient myIngredient = new Ingredient("savory");
    assertEquals("savory", myIngredient.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Ingredient firstIngredient = new Ingredient("savory");
    Ingredient secondIngredient = new Ingredient("savory");
    assertTrue(firstIngredient.equals(secondIngredient));
  }
 //
  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    assertTrue(Ingredient.all().get(0).equals(myIngredient));
  }

  @Test
  public void save_assignsIdToObject() {
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals(myIngredient.getId(), savedIngredient.getId());
  }

  @Test
  public void find_findsIngredientsInDatabase_True() {
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    Ingredient savedIngredient = Ingredient.find(myIngredient.getId());
    assertTrue(myIngredient.equals(savedIngredient));
  }

  @Test
  public void addRecipe_addsRecipeToIngredient() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    myIngredient.addRecipe(myRecipe);
    Recipe savedRecipe = myIngredient.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void getRecipesFromIngredient_returnsAllRecipes_List() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    myIngredient.addRecipe(myRecipe);
    List savedRecipes = myIngredient.getRecipes();
    assertEquals(1, savedRecipes.size());
  }

  @Test
  public void delete_deletesAllIngredientsAndRecipesAssociations() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("savory");
    myIngredient.save();
    myIngredient.addRecipe(myRecipe);
    myIngredient.delete();
    assertEquals(0, myRecipe.getIngredients().size());
  }

}
