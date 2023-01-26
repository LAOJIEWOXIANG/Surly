import java.util.LinkedList;

public class SurlyDatabase {
  /* Collection of relations in the database */
  private LinkedList<Relation> relations;
  private Relation catalog;
  
  /* Constructor to initialize LinkedList of relations. */
  public SurlyDatabase() {
    this.relations = new LinkedList<>();
    this.catalog = new Relation("CATALOG");
    this.catalog.addToSchema(
      new Attribute("RELATION", "CHAR", 16)
    );
    this.catalog.addToSchema(
      new Attribute("ATTRIBUTES", "NUM", 16)
    );
    createRelation(this.catalog);
  }
  
  /* Returns the relation with the specified name. Returns null
  if no such relation exists. */
  public Relation getRelation(String name) {
    for (Relation r : this.relations) {
      if (r.getName().equalsIgnoreCase(name)) {
        return r;
      }
    }
    return null;
  }
  
  /* Removes the relation with the specified name from the database */
  public void destroyRelation(String name) {
      for (int i = 0; i < this.relations.size(); i++) {
        Relation currentRelation = this.relations.get(i);
        if (currentRelation.getName().equalsIgnoreCase(name) && currentRelation != this.catalog) {
          deleteRelationFromCatalog(currentRelation);
          this.relations.remove(currentRelation);
        }
      }
  }
  
  /* Adds the given relation to the database */
  public void createRelation(Relation relation) {
    this.relations.add(relation);
    if(relation != this.catalog) {
      addRelationToCatalog(relation);
    }
  }

  public Relation getCatalog(){
    return this.catalog;
  }

  private void addRelationToCatalog(Relation relation) {
    String relationName = relation.getName();
    String schemaLength = Integer.toString(relation.schemaSize());
    AttributeValue nameValue = new AttributeValue("RELATION",relationName);
    AttributeValue attributesValue = new AttributeValue("ATTRIBUTES",schemaLength);
    Tuple tuple = new Tuple();
    tuple.add(nameValue);
    tuple.add(attributesValue);
    this.catalog.insert(tuple);
  }
  private void deleteRelationFromCatalog(Relation relation) {
    String relationName = relation.getName();
    this.catalog.deletetuple(relationName);
  }
}
