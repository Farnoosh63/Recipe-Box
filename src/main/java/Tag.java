import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Tag {
  private int id;
  private String tag_name;


  public Tag(String name){
    this.tag_name = name;
  }

  public String getName() {
    return tag_name;
  }

  public int getId() {
    return id;
  }


  public static List<Tag> all() {
    String sql = "SELECT id, tag_name FROM tags";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag =  (Tag) otherTag;
      return this.getName().equals(newTag.getName()) &&
             this.getId() == newTag.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (tag_name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.tag_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }
  public void update(String newTag) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET tag_name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", newTag)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM tags WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM recipes_tags WHERE tag_id = :tagId";
        con.createQuery(joinDeleteQuery)
          .addParameter("tagId", this.getId())
          .executeUpdate();
    }
  }

  public void addRecipe(Recipe recipe) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO recipes_tags (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
    con.createQuery(sql)
      .addParameter("recipe_id", recipe.getId())
      .addParameter("tag_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT recipe_id FROM recipes_tags WHERE tag_id = :tag_id";
      List<Integer> recipeIds = con.createQuery(joinQuery)
        .addParameter("tag_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for (Integer recipeId : recipeIds) {
        String tagQuery = "Select * From recipes WHERE id = :recipeId";
        Recipe recipe = con.createQuery(tagQuery)
          .addParameter("recipeId", recipeId)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      return recipes;
    }
  }
}
