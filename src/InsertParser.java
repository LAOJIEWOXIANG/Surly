import java.util.Arrays;

public class InsertParser {
  /* Reference to the input string being parsed */
  private String input;
  private boolean isValidSyntax;

  /* Constructor to initialize the input field */
  public InsertParser(String input) {
    this.input = input;
    this.isValidSyntax = verifySyntax();
  }

  /* Parses and returns the name of the relation to insert into*/
  public String parseRelationName() {
    String[] words = this.input.split("\\s+");
    return words[1];
  }

  /* Parses and returns the number of attributes to insert 
  public int parseAttributeCount() {
    String command = removeSemicolon(this.input);
    /*
     * The string is first split using the regular expression \\s+ which matches one
     * or more whitespace characters. Then, the resulting substrings are further
     * split by the regular expression (?=([^']*'[^']*')*[^']*$), which ensures that
     * the split only happens if the whitespace character is not enclosed within
     * single quotes.
     
    String[] parts = command.split("\\s+(?=([^']*'[^']*')*[^']*$)");
    String[] attributes = new String[parts.length - 2];
    System.arraycopy(parts, 2, attributes, 0, parts.length - 2);
    attributes = Arrays.stream(attributes).map(String::trim).toArray(String[]::new);

    return attributes.length;
  } */

  public boolean getIsValidSyntax() {
    return this.isValidSyntax;
  }

  /*
   * Regular expression that would match the strings of the form
   * "INSERT ITEM <list>;" where ITEM can be any word and <list> is a whitespace
   * separated list in the form "Item1 Item2" of any length and single quoted
   * strings are accepted in <list> :
   */
  private boolean verifySyntax() {
    return this.input.matches("INSERT\\s+\\w+\\s+(\\S+)\\s*((\\s+(\\S+))*)\\s*;");
  }

  private String removeSemicolon(String input) {
    if (input.endsWith(";")) {
      input = input.substring(0, input.length() - 1);
    }
    return input;
  }
}
