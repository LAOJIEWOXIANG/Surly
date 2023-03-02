import java.util.ArrayList;
import java.util.LinkedList;

public class Join {
  private LinkedList<Relation> relations;
  private String[] joinConditions;
  private ArrayList<String> attributeNames;
  private ArrayList<String> operators;
  private ArrayList<String> comparisons;
  private ArrayList<String> logicalOperators;

  public Join(LinkedList<Relation> relations, String[] joinConditions) {
    this.relations = relations;
    if (joinConditions.length != 0) { // Maybe should be an overloaded constructor
      this.joinConditions = joinConditions;
      attributeNames = new ArrayList<>();
      operators = new ArrayList<>();
      comparisons = new ArrayList<>();
      logicalOperators = new ArrayList<>();
      parseConditions(joinConditions[0]); // needs to change with multiple conditions
      System.out.println("test moment");
    }
  }

  public Relation getRelation() {
    // Check the Join attributes for compatible formats

    /* Create a new (temporary) relation where attribute names are <attribute names from the first relation>
    then <attribute names from the second relation, minus the repeated attribute name> */


    return null;
  }

  private Boolean validateAttributes() {
    Boolean verfied = false;
    LinkedList<Attribute> attributes = new LinkedList<Attribute>();

    for (Relation rel : this.relations) {
      rel.getAttribute(null);
    }

    return verfied;
  }

  private void parseConditions(String input) {
    String[] splitInput = input.split("\\s+");
        for (int i = 0; i<splitInput.length; i++) {
            if (isComparisonOperator(splitInput[i])) {
                attributeNames.add(splitInput[i-1]);
                operators.add(splitInput[i]);
                comparisons.add(splitInput[i+1]);
            } else if (splitInput[i].equalsIgnoreCase("and")) {
                logicalOperators.add("and");
            } else if (splitInput[i].equalsIgnoreCase("or")) {
                logicalOperators.add("or");
            }
        }
  }
  private boolean isComparisonOperator(String input) {
    return (input.contains("=") || input.contains("<") || input.contains(">"));
  }
}
