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

  /* Parses and returns the name of the relation to create */
  public String parseRelationName() {
    String[] words = this.input.split("\\s+");
    return words[1];
  }

  /* Parses and returns the number of attributes to create */
  public int parseAttributeCount() {
    // pick out attributes from string with regex and process to create a list of
    // attributes
    String[] parts = this.input.split("[\\(\\),;]+");
    String[] attributes = new String[parts.length - 1];
    System.arraycopy(parts, 1, attributes, 0, parts.length - 1);
    attributes = Arrays.stream(attributes).map(String::trim).toArray(String[]::new);

    return attributes.length;
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
