
public class DeleteParser {
    /* Reference to the input string being parsed */
    private String input;
    private boolean isValidSyntax; 
    /* Constructor to initialize the input field */
    public DeleteParser(String input) {
		this.input = input;
        this.isValidSyntax = verifySyntax();
	}

    /* Parses and returns the name of the relation for delete */
    public String parseRelationName() {
        return input.split(" ")[1];
    }

    public boolean getIsValidSyntax() {
        return isValidSyntax;
    }

    public boolean verifySyntax() {
        return this.input.matches("DELETE\\s+(\\w+\\s*,\\s*)*\\w+\\s*;");    
    }
}
