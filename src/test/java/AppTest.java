import org.sql2o.*; //for DB support
import org.junit.*; // for @Before and @After
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Categories List!");
  }

  @Test
  public void recipeIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Recipes"));
    fill("#name").with("Pasta");
    submit("#newRecipe");
    assertThat(pageSource()).contains("Pasta");
  }

  @Test
  public void categoryIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Categories"));
    fill("#name").with("Italian");
    submit("#addNewCat");
    assertThat(pageSource()).contains("Italian");
  }

  @Test
  public void recipeShowPageDisplaysName() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    String url = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
    goTo(url);
    assertThat(pageSource()).contains("Pasta");
  }

  @Test
  public void categoryShowPageDisplaysName() {
    Category testCategory = new Category("Italian");
    testCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(url);
    assertThat(pageSource()).contains("Italian");
  }

  @Test
  public void categoryIsAddedToRecipe() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    Category testCategory = new Category("Italian");
    testCategory.save();
    String url = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
    goTo(url);
    fillSelect("#category_id").withText("Italian");
    submit("#addCat");
    assertThat(pageSource()).contains("<li>");
    assertThat(pageSource()).contains("Italian");
  }

  @Test
  public void recipeIsAddedToCategory() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    Category testCategory = new Category("Italian");
    testCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(url);
    fillSelect("#recipe_id").withText("Pasta");
    submit(".btn");
    assertThat(pageSource()).contains("<li>");
    assertThat(pageSource()).contains("Pasta");
  }
  @Test
  public void categoryUpdate() {
    Category myCategory = new Category("Veronica Decides To Die");
    myCategory.save();
    String categoryPath = String.format("http://localhost:4567/categories/%d", myCategory.getId());
    goTo(categoryPath);
    click("a", withText("Edit this category"));
    fill("#category-update").with("Veronica Decides To Live");
    submit("#update-category");
    assertThat(pageSource()).doesNotContain("Veronica Decides To Die");
  }

  @Test
  public void categoryDelete() {
    Category myCategory = new Category("Veronica Decides To Die");
    myCategory.save();
    String categoryPath = String.format("http://localhost:4567/categories/%d", myCategory.getId());
    goTo(categoryPath);
    click("a", withText("Delete this category"));
    String allCategoriesPath = String.format("http://localhost:4567/categories/");
    goTo(allCategoriesPath);
    assertThat(pageSource()).doesNotContain("Veronica Decides To Die");
  }
  @Test
  public void recipeUpdate() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    String recipePath = String.format("http://localhost:4567/recipes/%d", myRecipe.getId());
    goTo(recipePath);
    click("a", withText("Edit this recipe"));
    fill("#recipe-update").with("Paulo De Coelho");
    submit("#update-recipe");
    assertThat(pageSource()).doesNotContain("Pasta");
  }

  @Test
  public void recipeDelete() {
    Recipe myRecipe = new Recipe("Pasta");
    myRecipe.save();
    String recipePath = String.format("http://localhost:4567/recipes/%d", myRecipe.getId());
    goTo(recipePath);
    click("a", withText("Delete this recipe"));
    String allRecipesPath = String.format("http://localhost:4567/recipes/");
    goTo(allRecipesPath);
    assertThat(pageSource()).doesNotContain("Pasta");
  }

  @Test
  public void categorySearchByRecipeName() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    Category testCategory = new Category("Italian");
    testCategory.save();
    String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(url);
    fillSelect("#recipe_id").withText("Pasta");
    submit(".btn");
    goTo("http://localhost:4567/");
    click("a", withText("Search for a category"));
    fillSelect("#recipe_id").withText("Pasta");
    submit("#search-button");
    assertThat(pageSource()).contains("Italian");
  }

  @Test
  public void recipeSearchByIngredientName() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    Ingredient testIngredient = new Ingredient("Tomato");
    testIngredient.save();
    String url = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
    goTo(url);
    fill("#ingredient_id").with("Tomato");
    submit("#ingredient_id_button");
    goTo("http://localhost:4567/search");
    fillSelect("#ingredient_id").withText("Tomato");
    submit("#searchByIngredient");
    assertThat(pageSource()).contains("Pasta");
  }

  @Test
  public void getRatingForRecipe() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    String url = String.format("http://localhost:4567/recipes");
    goTo(url);
    click("a", withText("Pasta"));
    click("a", withText("Edit this recipe"));
    fill("#recipe-update").with("Chicken Salad");
    fill("#rating-update").with("4");
    submit("#update-recipe");
    assertThat(pageSource()).contains("4");
  }

  @Test
  public void updateIngredient() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    Ingredient testIngredient = new Ingredient("Tomato");
    testIngredient.save();
    String urlrecipe = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
    goTo(urlrecipe);
    click("a", withText("Show All Ingredients in this App"));
    fill("#ingredient_id").with("Tomato");
    submit("#update_ingredient");
    String urlIngredient = String.format("http://localhost:4567/ingredient-update/%d/edit", testIngredient.getId());
    goTo(urlIngredient);
    assertThat(pageSource()).contains("Tomato");
    fill("#ingredient-update").with("updated Tomato");
    submit("#update-ingredient");
    String urlAllIngredient = String.format("http://localhost:4567/allIngredients");
    goTo(urlAllIngredient);
    assertThat(pageSource()).contains("updated Tomato");
  }

  @Test
  public void deleteIngredient() {
    Recipe testRecipe = new Recipe("Pasta");
    testRecipe.save();
    Ingredient testIngredient = new Ingredient("Tomato");
    testIngredient.save();
    String urlrecipe = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
    goTo(urlrecipe);
    click("a", withText("Show All Ingredients in this App"));
    fill("#ingredient_id").with("Tomato");
    submit("#update_ingredient");
    String urlIngredient = String.format("http://localhost:4567/ingredient-update/%d/edit", testIngredient.getId());
    goTo(urlIngredient);
    click("a", withText("Delete Ingredient"));
    String urlAllIngredient = String.format("http://localhost:4567/allIngredients");
    goTo(urlAllIngredient);
    assertThat(pageSource()).doesNotContain("Tomato");
  }
}
