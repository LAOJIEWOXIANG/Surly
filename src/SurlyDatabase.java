import java.util.LinkedList;

public class SurlyDatabase {
  /* Collection of relations in the database */
  private LinkedList<Relation> relations;
  private Relation catalog;
  private final Integer CATALOG_ATTRIBUTE_LENGTH = 16;
  private final String RELATION_NAME_COLUMN = "RELATION";
  private final String SCHEMA_LENGTH_COLUMN = "ATTRIBUTES";
  
  /* Constructor to initialize LinkedList of relations. */
  public SurlyDatabase() {
    this.relations = new LinkedList<>();
    this.catalog = new Relation("CATALOG");
    this.catalog.addToSchema(
      new Attribute(RELATION_NAME_COLUMN, "CHAR", CATALOG_ATTRIBUTE_LENGTH)
    );
    this.catalog.addToSchema(
      new Attribute(SCHEMA_LENGTH_COLUMN, "NUM", CATALOG_ATTRIBUTE_LENGTH)
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
        if (currentRelation.getName().equalsIgnoreCase(name)) {
          if (currentRelation != this.catalog) {
            deleteRelationFromCatalog(currentRelation);
          } else {
            System.out.println("Cannot destroy the Catalog.");
          }
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

  /* Adds a new tuple to the catalog every time a new relation is added the the db. */
  private void addRelationToCatalog(Relation relation) {
    String relationName = relation.getName();
    String schemaLength = Integer.toString(relation.schemaSize());
    AttributeValue nameValue = new AttributeValue(RELATION_NAME_COLUMN,relationName);
    AttributeValue attributesValue = new AttributeValue(SCHEMA_LENGTH_COLUMN,schemaLength);
    Tuple tuple = new Tuple();
    tuple.add(nameValue);
    tuple.add(attributesValue);
    this.catalog.insert(tuple);
  }

  /* Deletes a tuple from the catalog when a Relation is deleted. */
  private void deleteRelationFromCatalog(Relation relation) {
    String relationName = relation.getName();
    this.catalog.deletetuple(relationName);
  }
}
