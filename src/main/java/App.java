import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

import java.util.ArrayList;

public class App {
  public static void main (String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("categories", Category.all());
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/categories", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Category newCategory = new Category(name);
      newCategory.save();
      response.redirect("/categories");
      return null;
    });

    post("/recipes", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Recipe newRecipe = new Recipe(name);
      newRecipe.save(); // ***ADDED FOR DB VERSION***
      response.redirect("/recipes");
      return null;
    });

    // SHOW SEARCH FORM
    get("/search", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      if (request.queryParams().contains("recipe_id")){
        int recipeIdThatBeingSearched = Integer.parseInt(request.queryParams("recipe_id"));
        List<Category> categoriesSearched = Recipe.find(recipeIdThatBeingSearched).getCategories();
        model.put("categories",categoriesSearched );
      }
      if (request.queryParams().contains("ingredient_id")){
        int ingredientIdThatBeingSearched = Integer.parseInt(request.queryParams("ingredient_id"));
        List<Recipe> recipesSearched = Ingredient.find(ingredientIdThatBeingSearched).getRecipes();
        model.put("recipes",recipesSearched);
      }
      model.put("allRecipes", Recipe.all());
      model.put("allIngredients", Ingredient.all());
      model.put("template", "templates/category-search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sortingAsc", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.sortByAscending());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sortingDesc", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.sortByDescending());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/categories/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int category_id = Integer.parseInt(request.params(":id"));
      Category category = Category.find(category_id);
      model.put("category", category);
      model.put("allRecipes", Recipe.all());
      model.put("template", "templates/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("allCategories", Category.all());
      model.put("template", "templates/recipe.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/allIngredients", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("allIngredients", Ingredient.all());
      model.put("template", "templates/allIngredients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  post("/allIngredients", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    int ingredientId = Integer.parseInt(request.queryParams("ingredient_id"));;
    Ingredient ingredient = Ingredient.find(ingredientId);
    response.redirect("/ingredient-update/" + ingredientId +"/edit");
    return null;
  });

  get("/ingredient-update/:id/edit", (request,response) ->{
    HashMap<String, Object> model = new HashMap<String, Object>();
     int ingredientId = Integer.parseInt(request.params(":id"));;
    Ingredient ingredient = Ingredient.find(ingredientId);
    model.put("ingredient", ingredient);
    model.put("template", "templates/ingredient-update.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

    post("/ingredient-update/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int oldIngredientId = Integer.parseInt(request.params(":id"));
      Ingredient oldIngredient = Ingredient.find(oldIngredientId);
      String newIngredient = request.queryParams("ingredient-update");
      oldIngredient.update(newIngredient);
      System.out.println(oldIngredient);
      model.put("allIngredients", oldIngredient);
    response.redirect("/allIngredients");
    return null;
  });


//    DO THE DELETE ingredient ACTION
    get("/allIngredients/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int ingredientId = Integer.parseInt(request.params(":id"));;
      Ingredient ingredient = Ingredient.find(ingredientId);
      ingredient.delete();
      model.put("allIngredients", ingredient);
      response.redirect("/allIngredients");
      return null;
    });



    post("/add_categories", (request, response) -> {
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      int recipeId = Integer.parseInt(request.queryParams("recipe_id"));
      Recipe recipe = Recipe.find(recipeId);
      Category category = Category.find(categoryId);
      recipe.addCategory(category);
      response.redirect("/recipes/" + recipeId);
      return null;
    });



    post("/add_ingredients", (request, response) -> {
      String ingredient_description = request.queryParams("ingredient_id");

      int recipeId = Integer.parseInt(request.queryParams("recipe_id"));
      Recipe recipe = Recipe.find(recipeId);
      Ingredient ingredient = new Ingredient(ingredient_description);
      ingredient.save();
      recipe.addIngredient(ingredient);
      response.redirect("/recipes/" + recipeId);
      return null;
    });


    post("/add_recipes", (request, response) -> {
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      int recipeId = Integer.parseInt(request.queryParams("recipe_id"));
      Recipe recipe = Recipe.find(recipeId);
      Category category = Category.find(categoryId);
      category.addRecipe(recipe);
      response.redirect("/categories/" + categoryId);
      return null;
    });
    // SHOW UPDATE category FORM - CLICK ON "a ingredient(href)"
    get("/categories/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params(":id")));
      model.put("category", category);
      model.put("template", "templates/category-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // PROCESSES UPDATE category FORM
    post("/categories/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category oldCategory = Category.find(Integer.parseInt(request.params(":id")));
      String newCategory = request.queryParams("category-update");
      oldCategory.update(newCategory);
      response.redirect("/categories");
      return null;
    });

    post("/addImage", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int recipeId = Integer.parseInt(request.queryParams("recipe_id"));
      Recipe oldRecipe = Recipe.find(recipeId);
      String newImage = request.queryParams("imageUpload");
      oldRecipe.uploadImage(newImage);
      response.redirect("/recipes/" + recipeId);
      return null;
    });

    // DO THE DELETE category ACTION
    get("/categories/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params(":id")));
      category.delete();
      response.redirect("/categories");
      return null;
    });

    // SHOW UPDATE recipe FORM
    get("/recipes/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      model.put("recipe", recipe);
      model.put("template", "templates/recipe-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // PROCESSES UPDATE recipe FORM
    post("/recipes/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe oldRecipe = Recipe.find(Integer.parseInt(request.params(":id")));
      int newRating = Integer.parseInt(request.queryParams("rating-update"));
      oldRecipe.updateRating(newRating);
      String newRecipe = request.queryParams("recipe-update");
      oldRecipe.update(newRecipe);
      response.redirect("/recipes/" + request.params(":id"));
      return null;
    });

    // DO THE DELETE recipe ACTION
    get("/recipes/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
      recipe.delete();
      response.redirect("/recipes");
      return null;
    });

  }
}
