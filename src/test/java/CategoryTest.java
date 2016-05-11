import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;
import java.util.List;

public class CategoryTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();



 @Test
 public void Category_instantiatesCorrectly_true(){
   Category myCategory = new Category("Italian");
   assertEquals(true, myCategory instanceof Category);
 }

 @Test
  public void getName_categoryWithName_String(){
    Category myCategory = new Category("Italian");
    assertEquals("Italian", myCategory.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Category.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Category firstCategory = new Category("Italian");
    Category secondCategory = new Category("Italian");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Category myCategory = new Category("Italian");
    myCategory.save();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void save_assignsIdToObject() {
    Category myCategory = new Category("Italian");
    myCategory.save();
    Category savedCategory = Category.all().get(0);
    assertEquals(myCategory.getId(), savedCategory.getId());
  }

  @Test
  public void find_findsCategoriesInDatabase_True() {
    Category myCategory = new Category("Italian");
    myCategory.save();
    Category savedCategory = Category.find(myCategory.getId());
    assertTrue(myCategory.equals(savedCategory));
  }

  @Test
  public void addRecipe_addsRecipeToCategory() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Category myCategory = new Category("Italian");
    myCategory.save();
    myCategory.addRecipe(myRecipe);
    Recipe savedRecipe = myCategory.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));
  }

  @Test
  public void getRecipesFromCategory_returnsAllRecipes_List() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Category myCategory = new Category("Italian");
    myCategory.save();
    myCategory.addRecipe(myRecipe);
    List savedRecipes = myCategory.getRecipes();
    assertEquals(1, savedRecipes.size());
  }
 
  @Test
  public void delete_deletesAllCategoriesAndRecipesAssociations() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    Category myCategory = new Category("Italian");
    myCategory.save();
    myCategory.addRecipe(myRecipe);
    myCategory.delete();
    assertEquals(0, myRecipe.getCategories().size());
  }

}
