import java.util.Arrays;

public class RelationParser {
  /* Reference to the input string being parsed */
  private String input;
  private boolean isValidSyntax;
  
  /* Constructor to initialize the input field */
  public RelationParser(String input) {
    this.input = input;
    this.isValidSyntax = verifySyntax();
    
  }
  
  /* Returns the name of the relation. */
  public String parseRelationName() {
     return this.input.split("\\s+")[1];
  }
  
  /* Parses and returns the number of attributes to create */
  public Relation parseRelation() {
    String name = this.parseRelationName(); /* Parse relation name */
    Relation relation = new Relation(name);
    
    /* Isolates the attribute definitions. */
    String attr = input.split("\\s+",3)[2].trim();
    /* Need to handle bad parentheses syntax. */
    
    /* Array of individual attribute definitions. */
    String[] attrDef = attr.substring(attr.indexOf("(")+1, attr.length() - 2).split(",");
    
    /* Creates attributes with given attribute definitions and adds to relation schema. */
    for (int i = 0; i < attrDef.length; i++) {
      String[] elements = attrDef[i].trim().split("\\s+"); //can add error checking here later
      /* Ensures attribute definition has exactly 3 elements. */
      if (elements.length != 3) {
        System.out.println("Invalid attribute length.");
        return null;
      }
      relation.addToSchema(new Attribute(elements[0],elements[1],Integer.valueOf(elements[2])));
    }
    return relation;
  }
  
  public boolean getIsValidSyntax() {
    return this.isValidSyntax;
  }
  
  /*
  * Regular expression that would match the strings of the form
  * "RELATION ITEM (<list>);" where ITEM can be any word, and <list> is a comma
  * separated list of any length in the form
  * "ITEM1a ITEM1B ITEM1C, ITEM2a ITEM2B ITEM2C" where each list item can only
  * contain 3 words separated by any whitespace
  */
  private boolean verifySyntax() {
    return this.input.matches(
    "RELATION\\s+\\w+\\s*\\(\\s*((\\w+\\s+\\w+\\s+\\w+\\s*,\\s*)*(\\w+\\s+\\w+\\s+\\w+\\s*)*)\\s*\\)\\s*;");
  }
}
