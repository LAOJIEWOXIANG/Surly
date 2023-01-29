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
  
  /* Parses and returns a tuple based on a command.*/
  
  public Tuple parseTuple() {
    String attributes = removeSemicolon(this.input);
    attributes = attributes.split("\\s+",3)[2]; //get only attribute values
    Tuple tuple = new Tuple();
    for (int i = 0; i < attributes.length(); i++) {
      /* If the current char is a single quote, add everything
      inside it to the tuple. */
      if (attributes.charAt(i) == 39) {
        i++;
        String betweenQuotes = "";
        while (attributes.charAt(i) != 39) {
          betweenQuotes += attributes.charAt(i);
          i++;
        }
        tuple.add(new AttributeValue("",betweenQuotes));
        
        /* If the current char isn't a space, read until a space
        and add that string to the tuple. */
      } else if (attributes.charAt(i) != ' ') {
        String element = "";
        while (i < attributes.length() && attributes.charAt(i) != ' ') {
          element += attributes.charAt(i);
          i++;
        }
        tuple.add(new AttributeValue("",element));
      }                                      
    }                                            
    return tuple;                                  
  }
  
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
