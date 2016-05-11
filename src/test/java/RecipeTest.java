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
    Recipe myRecipe = new Recipe("Household chores");
    assertEquals(true, myRecipe instanceof Recipe);
  }

 //  @Test
 //  public void getName_recipeInstantiatesWithName_String() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    assertEquals("Household chores", myRecipe.getName());
 //  }
 //
 //  @Test
 //  public void all_emptyAtFirst() {
 //    assertEquals(Recipe.all().size(), 0);
 //  }
 //
 //   @Test
 //  public void equals_returnsTrueIfNamesAretheSame() {
 //    Recipe firstRecipe = new Recipe("Household chores");
 //    Recipe secondRecipe = new Recipe("Household chores");
 //    assertTrue(firstRecipe.equals(secondRecipe));
 //  }
 //
 //  @Test
 //  public void save_savesIntoDatabase_true() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    assertTrue(Recipe.all().get(0).equals(myRecipe));
 //  }
 //
 //  @Test
 //   public void save_assignsIdToObject() {
 //     Recipe myRecipe = new Recipe("Household chores");
 //     myRecipe.save();
 //     Recipe savedRecipe = Recipe.all().get(0);
 //     assertEquals(myRecipe.getId(), savedRecipe.getId());
 //   }
 //
 // @Test
 //  public void find_findRecipeInDatabase_true() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Recipe savedRecipe = Recipe.find(myRecipe.getId());
 //    assertTrue(myRecipe.equals(savedRecipe));
 //  }
 //
 //  @Test
 //  public void addCategory_addsCategoryToRecipe_true() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Category myCategory = new Category("Mow the lawn");
 //    myCategory.save();
 //    myRecipe.addCategory(myCategory);
 //    Category savedCategory = myRecipe.getCategories().get(0);
 //    assertTrue(myCategory.equals(savedCategory));
 //  }
 //
 //  @Test
 //  public void getCategories_returnsAllCategories_List() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Category myCategory = new Category("Mow the lawn");
 //    myCategory.save();
 //    myRecipe.addCategory(myCategory);
 //    List savedCategories = myRecipe.getCategories();
 //    assertEquals(1, savedCategories.size());
 //  }
 //
 //  @Test
 //  public void delete_deletesAllCategoriesAndRecipesAssociations() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Category myCategory = new Category("Mow the lawn");
 //    myCategory.save();
 //    myRecipe.addCategory(myCategory);
 //    myRecipe.delete();
 //    assertEquals(0, myCategory.getRecipes().size());
 //  }
}
