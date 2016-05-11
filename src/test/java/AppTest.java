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

  // @Test
  // public void recipeIsCreatedTest() {
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Recipes"));
  //   fill("#name").with("Paulo Coelho");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("Paulo Coelho");
  // }
  //
  //  @Test
  //   public void categoryIsCreatedTest() {
  //     goTo("http://localhost:4567/");
  //     click("a", withText("Categories"));
  //     fill("#name").with("The Alchemist");
  //     submit(".btn");
  //     assertThat(pageSource()).contains("The Alchemist");
  // }
  //
  // @Test
  // public void recipeShowPageDisplaysName() {
  //   Recipe testRecipe = new Recipe("Paulo Coelho");
  //   testRecipe.save();
  //   String url = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
  //   goTo(url);
  //   assertThat(pageSource()).contains("Paulo Coelho");
  // }
  //
  // @Test
  // public void categoryShowPageDisplaysName() {
  //   Category testCategory = new Category("The Alchemist");
  //   testCategory.save();
  //   String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
  //   goTo(url);
  //   assertThat(pageSource()).contains("The Alchemist");
  // }
  //
  // @Test
  // public void categoryIsAddedToRecipe() {
  //   Recipe testRecipe = new Recipe("Paulo Coelho");
  //   testRecipe.save();
  //   Category testCategory = new Category("The Alchemist");
  //   testCategory.save();
  //   String url = String.format("http://localhost:4567/recipes/%d", testRecipe.getId());
  //   goTo(url);
  //   fillSelect("#category_id").withText("The Alchemist");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("<li>");
  //   assertThat(pageSource()).contains("The Alchemist");
  // }
  //
  // @Test
  // public void recipeIsAddedToCategory() {
  //   Recipe testRecipe = new Recipe("Paulo Coelho");
  //   testRecipe.save();
  //   Category testCategory = new Category("The Alchemist");
  //   testCategory.save();
  //   String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
  //   goTo(url);
  //   fillSelect("#recipe_id").withText("Paulo Coelho");
  //   submit(".btn");
  //   assertThat(pageSource()).contains("<li>");
  //   assertThat(pageSource()).contains("Paulo Coelho");
  // }
  // @Test
  // public void categoryUpdate() {
  //   Category myCategory = new Category("Veronica Decides To Die");
  //   myCategory.save();
  //   String categoryPath = String.format("http://localhost:4567/categories/%d", myCategory.getId());
  //   goTo(categoryPath);
  //   click("a", withText("Edit this category"));
  //   fill("#category-update").with("Veronica Decides To Live");
  //   submit("#update-category");
  //   assertThat(pageSource()).doesNotContain("Veronica Decides To Die");
  // }
  //
  // @Test
  // public void categoryDelete() {
  //   Category myCategory = new Category("Veronica Decides To Die");
  //   myCategory.save();
  //   String categoryPath = String.format("http://localhost:4567/categories/%d", myCategory.getId());
  //   goTo(categoryPath);
  //   click("a", withText("Delete this category"));
  //   String allCategoriesPath = String.format("http://localhost:4567/categories/");
  //   goTo(allCategoriesPath);
  //   assertThat(pageSource()).doesNotContain("Veronica Decides To Die");
  // }
  // @Test
  // public void recipeUpdate() {
  //   Recipe myRecipe = new Recipe("Paulo Coelho");
  //   myRecipe.save();
  //   String recipePath = String.format("http://localhost:4567/recipes/%d", myRecipe.getId());
  //   goTo(recipePath);
  //   click("a", withText("Edit this recipe"));
  //   fill("#recipe-update").with("Paulo De Coelho");
  //   submit("#update-recipe");
  //   assertThat(pageSource()).doesNotContain("Paulo Coelho");
  // }
  //
  // @Test
  // public void recipeDelete() {
  //   Recipe myRecipe = new Recipe("Paulo Coelho");
  //   myRecipe.save();
  //   String recipePath = String.format("http://localhost:4567/recipes/%d", myRecipe.getId());
  //   goTo(recipePath);
  //   click("a", withText("Delete this recipe"));
  //   String allRecipesPath = String.format("http://localhost:4567/recipes/");
  //   goTo(allRecipesPath);
  //   assertThat(pageSource()).doesNotContain("Paulo Coelho");
  // }
  //
  // @Test
  // public void categorySearchByRecipeName() {
  //   Recipe testRecipe = new Recipe("Paulo Coelho");
  //   testRecipe.save();
  //   Category testCategory = new Category("The Alchemist");
  //   testCategory.save();
  //   String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
  //   goTo(url);
  //   fillSelect("#recipe_id").withText("Paulo Coelho");
  //   submit(".btn");
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Search for a category"));
  //   fill("#category-search").with("Paulo Coelho");
  //   submit("#search-button");
  //   assertThat(pageSource()).contains("The Alchemist");
  // }
}
