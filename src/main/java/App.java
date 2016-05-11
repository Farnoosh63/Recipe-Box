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

    // SHOW SEARCH BOOKS FORM
     get("/categories/search", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();

     model.put("allRecipes", Recipe.all());
     model.put("template", "templates/category-search.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());
    // 
    // // PROCESSES SEARCH FORM
    //  post("/categories/search", (request, response) -> {
    //    HashMap<String, Object> model = new HashMap<String, Object>();
    //   // List<String> searchedRecipeId = new ArrayList<String>();
    //    String searchedByRecipeName = request.queryParams("category-search");
    //    int recipeIdThatBeingSearched = Integer.parseInt(request.queryParams("recipe_id"));
    //    List<Category> recipeSearched = Recipe.find(recipeIdThatBeingSearched).getCategories();
    //    System.out.println(recipeSearched);
    //    //recipeSearched.getCategories();
    //    model.put("categories", searchedRecipeId);
    //    response.redirect("/categories/search");
    //    return null;
    //  });

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

    post("/add_categories", (request, response) -> {
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      int recipeId = Integer.parseInt(request.queryParams("recipe_id"));
      Recipe recipe = Recipe.find(recipeId);
      Category category = Category.find(categoryId);
      recipe.addCategory(category);
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
 // SHOW UPDATE BOOKS FORM - CLICK ON "a tag(href)"
    get("/categories/:id/edit", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params(":id")));
      model.put("category", category);
      model.put("template", "templates/category-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    // PROCESSES UPDATE BOOKS FORM
    post("/categories/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category oldCategory = Category.find(Integer.parseInt(request.params(":id")));
      String newCategory = request.queryParams("category-update");
      oldCategory.update(newCategory);
      response.redirect("/categories");
      return null;
    });

// DO THE DELETE BOOK ACTION
    get("/categories/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Category category = Category.find(Integer.parseInt(request.params(":id")));
      category.delete();
      response.redirect("/categories");
      return null;
    });

    // SHOW UPDATE AUTHORS FORM - CLICK ON "a tag(href)"
       get("/recipes/:id/edit", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();
         Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
         model.put("recipe", recipe);
         model.put("template", "templates/recipe-update.vtl");
         return new ModelAndView(model, layout);
       }, new VelocityTemplateEngine());
       // PROCESSES UPDATE AUTHORS FORM
       post("/recipes/:id/edit", (request, response) -> {
         HashMap<String, Object> model = new HashMap<String, Object>();
         Recipe oldRecipe = Recipe.find(Integer.parseInt(request.params(":id")));
         String newRecipe = request.queryParams("recipe-update");
         oldRecipe.update(newRecipe);
         response.redirect("/recipes");
         return null;
       });

   // DO THE DELETE AUTHOR ACTION
       get("/recipes/:id/delete", (request, response) -> {
         HashMap<String, Object> model = new HashMap<String, Object>();
         Recipe recipe = Recipe.find(Integer.parseInt(request.params(":id")));
         recipe.delete();
         response.redirect("/recipes");
         return null;
       });




  }
}
