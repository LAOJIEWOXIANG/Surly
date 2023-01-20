import java.util.Arrays;

public class PrintParser {
  /* Reference to the input string being parsed */
  private String input;
  private boolean isValidSyntax;

  /* Constructor to initialize the input field */
  public PrintParser(String input) {
    this.input = input;
    this.isValidSyntax = verifySyntax();
  }

  /* Parses and returns the names the relations to print */
  public String[] parseRelationNames() {
    String[] parts = this.input.split("[\\s,;]+");
    String[] attributes = new String[parts.length - 1];
    System.arraycopy(parts, 1, attributes, 0, parts.length - 1);
    attributes = Arrays.stream(attributes).map(String::trim).toArray(String[]::new);

    return attributes;
  }

  /* Parses and returns the number of attributes to print */
  public int parseAttributeCount() {
    return parseRelationNames().length;
  }

  public boolean getIsValidSyntax() {
    return this.isValidSyntax;
  }

  /*
   * check if a string is in the form "PRINT <list>;", where <list> is a list of
   * items separated by commas and any amount of whitespace
   */
  private boolean verifySyntax() {
    return this.input.matches("PRINT\\s+(\\w+\\s*,\\s*)*\\w+\\s*;");
  }
}
