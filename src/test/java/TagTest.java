import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;
import java.util.List;

public class TagTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();



 @Test
 public void Tag_instantiatesCorrectly_true(){
   Tag myTag = new Tag("Mow the lawn");
   assertEquals(true, myTag instanceof Tag);
 }

 // @Test
 //  public void getName_tagWithName_String(){
 //    Tag myTag = new Tag("Mow the lawn");
 //    assertEquals("Mow the lawn", myTag.getName());
 //  }
 //
 //  @Test
 //  public void all_emptyAtFirst() {
 //    assertEquals(Tag.all().size(), 0);
 //  }
 //
 //  @Test
 //  public void equals_returnsTrueIfNamesAreTheSame() {
 //    Tag firstTag = new Tag("Mow the lawn");
 //    Tag secondTag = new Tag("Mow the lawn");
 //    assertTrue(firstTag.equals(secondTag));
 //  }
 //
 //  @Test
 //  public void save_returnsTrueIfNamesAretheSame() {
 //    Tag myTag = new Tag("Mow the lawn");
 //    myTag.save();
 //    assertTrue(Tag.all().get(0).equals(myTag));
 //  }
 //
 //  @Test
 //  public void save_assignsIdToObject() {
 //    Tag myTag = new Tag("Mow the lawn");
 //    myTag.save();
 //    Tag savedTag = Tag.all().get(0);
 //    assertEquals(myTag.getId(), savedTag.getId());
 //  }
 //
 //  @Test
 //  public void find_findsTagsInDatabase_True() {
 //    Tag myTag = new Tag("Mow the lawn");
 //    myTag.save();
 //    Tag savedTag = Tag.find(myTag.getId());
 //    assertTrue(myTag.equals(savedTag));
 //  }
 //
 //  @Test
 //  public void addRecipe_addsRecipeToTag() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Tag myTag = new Tag("Mow the lawn");
 //    myTag.save();
 //    myTag.addRecipe(myRecipe);
 //    Recipe savedRecipe = myTag.getRecipes().get(0);
 //    assertTrue(myRecipe.equals(savedRecipe));
 //  }
 //
 //  @Test
 //  public void getRecipes_returnsAllRecipes_List() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Tag myTag = new Tag("Mow the lawn");
 //    myTag.save();
 //    myTag.addRecipe(myRecipe);
 //    List savedRecipes = myTag.getRecipes();
 //    assertEquals(1, savedRecipes.size());
 //  }
 //
 //  @Test
 //  public void delete_deletesAllTagsAndRecipesAssociations() {
 //    Recipe myRecipe = new Recipe("Household chores");
 //    myRecipe.save();
 //    Tag myTag = new Tag("Mow the lawn");
 //    myTag.save();
 //    myTag.addRecipe(myRecipe);
 //    myTag.delete();
 //    assertEquals(0, myRecipe.getTags().size());
 //  }

}
