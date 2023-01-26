
public class DestroyParser {
    /* Reference to the input string being parsed */
    private String input;
    private boolean isValidSyntax; 
    /* Constructor to initialize the input field */
    public DestroyParser(String input) {
		this.input = input;
        this.isValidSyntax = verifySyntax(); 
	}

    public boolean getIsValidSyntax() {
        return this.isValidSyntax;
    }
    /* Parses and returns the name of the relation to destroy */
    public String parseRelationName() {
        String name = this.input.split(" ")[1];
        return name.substring(0, name.length() - 1);
    }

    public boolean verifySyntax() {
        return this.input.matches("DESTROY\\s+(\\w+\\s*,\\s*)*\\w+\\s*;");    
    }
}
