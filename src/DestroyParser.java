
public class DestroyParser {
    /* Reference to the input string being parsed */
    private String input;
    private boolean isValidSyntax; 
    /* Constructor to initialize the input field */
    public DestroyParser(String input) {
		this.input = input; 
	}

    public boolean getIsValidSyntax() {
        return isValidSyntax;
    }
    /* Parses and returns the name of the relation to destroy */
    public String parseRelationName() {
        return input.split(" ")[1];
    }

    public boolean verifySyntax() {
        return this.input.matches("DESTORY\\s+(\\w+\\s*,\\s*)*\\w+\\s*;");    
    }
}
