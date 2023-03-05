import java.util.ArrayList;

public class WhereParser {
    private String input;
    private ArrayList<String> attributeNames;
    private ArrayList<String> operators;
    private ArrayList<String> comparisons;
    private ArrayList<String> logicalOperators;

    public WhereParser(String input) {
        this.input = input;
        attributeNames = new ArrayList<>();
        operators = new ArrayList<>();
        comparisons = new ArrayList<>();
        logicalOperators = new ArrayList<>();
        parseInput();
    }

    /* Separates the expressions in the WHERE clause into the arribute names,
     * the boolean operators, and the value that the attribute is being compared to*/
    private void parseInput() {
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

    /* Returns true if the AttributeValues in the tuple meet the conditions in the
     * saved Where clause, returns false if they don't.*/
    public boolean meetsConditions(Tuple tuple) {
        return meetsConditions(tuple,logicalOperators.size());
    }


    private boolean meetsConditions(Tuple tuple, int ops) {
        if (input.equals("")) {
            return true;
        }
        boolean first = evaluate(tuple,0);
        boolean second = first;
        /* Evaluates the expression from left to right, grouping all the "and"s together */
        for (int i = 0; i<ops; i++) {
            if (logicalOperators.get(i).equals("or")) {
                second = evaluate(tuple,i+1); 
                while (i+1 < ops && logicalOperators.get(i+1).equals("and")) {
                        i++;
                        second = second && evaluate(tuple,i+1);
                }
                first = first || second;
            } else {
                first = first && evaluate(tuple,i+1);
                while (i+1 < ops && logicalOperators.get(i+1).equals("and")) {
                    i++;
                    first = first && evaluate(tuple,i+1);
                }
            }
        }
        return first;
    }

    /* Evaluates an boolean expression of the form <value> <operator> <value>. */
    private boolean evaluate(Tuple tuple, int conditionNum) {
        String tupleValue = tuple.getValue(attributeNames.get(conditionNum));
        String compareValue = comparisons.get(conditionNum);
        switch(operators.get(conditionNum)) {
            case "=":
              return tupleValue.equals(compareValue);
            case "!=":
              return !tupleValue.equals(compareValue);
            case "<":
              return compareAttributes(tupleValue, compareValue) < 0;
            case ">":
            return compareAttributes(tupleValue, compareValue) > 0;
            case "<=":
              return compareAttributes(tupleValue, compareValue) <= 0;
            case ">=":
            return compareAttributes(tupleValue, compareValue) >= 0;
        }
        return true;
    }

    /* Returns negative if attribute1 is less than attribute2, returns 0 if they are the same,
     * returns positive value otherwise */
    private int compareAttributes(String attribute1, String attribute2) {
        int result = 0;
        try {
            result = Integer.valueOf(attribute1).compareTo(Integer.valueOf(attribute2));
        } catch (NumberFormatException e) {
            result = attribute1.compareTo(attribute2);
        }
        return result;
    }

    private boolean isComparisonOperator(String input) {
        return (input.contains("=") || input.contains("<") || input.contains(">"));
    }
}


