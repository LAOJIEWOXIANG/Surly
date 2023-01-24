import java.util.LinkedList;

public class SurlyDatabase {
  /* Collection of relations in the database */
  private LinkedList<Relation> relations;
  
  /* Constructor to initialize LinkedList of relations. */
  public SurlyDatabase() {
    relations = new LinkedList<>();
  }
  
  /* Returns the relation with the specified name. Returns null
  if no such relation exists. */
  public Relation getRelation(String name) {
    for (Relation r : relations) {
      if (r.getName().equalsIgnoreCase(name)) {
        return r;
      }
    }
    return null;
  }
  
  /* Removes the relation with the specified name from the database */
  public void destroyRelation(String name) {
    for (Relation r : relations) {
      if (r.getName().equalsIgnoreCase(name)) {
        relations.remove(r);
      }
    }
  }
  
  /* Adds the given relation to the database */
  public void createRelation(Relation relation) {
    relations.add(relation);
  }
}
