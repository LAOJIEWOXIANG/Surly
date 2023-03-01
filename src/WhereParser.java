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

    public boolean meetsConditions(Tuple tuple) {
        return meetsConditions(tuple,logicalOperators.size());
    }

    private boolean meetsConditions(Tuple tuple, int ops) {
        if (input.equals("")) {
            return true;
        }
        boolean first = evaluate(tuple,0);
        boolean second = first;
        for (int i = 0; i<ops; i++) {
            if (logicalOperators.get(i).equals("or")) {
                //if next is or
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

    private boolean evaluate(Tuple tuple, int conditionNum) {
        String tupleValue = tuple.getValue(attributeNames.get(conditionNum));
        String compareValue = comparisons.get(conditionNum);
        switch(operators.get(conditionNum)) {
            case "=":
              return tupleValue.equals(compareValue);
            case "!=":
              return !tupleValue.equals(compareValue);
            case "<":
              return Float.valueOf(tupleValue)
              .compareTo(Float.valueOf(compareValue)) < 0;
            case ">":
            return Float.valueOf(tupleValue)
            .compareTo(Float.valueOf(compareValue)) > 0;
            case "<=":
              return Float.valueOf(tupleValue)
              .compareTo(Float.valueOf(compareValue)) <= 0;
            case ">=":
            return Float.valueOf(tupleValue)
            .compareTo(Float.valueOf(compareValue)) >= 0;
        }
        return true;
    }

    private boolean isComparisonOperator(String input) {
        return (input.contains("=") || input.contains("<") || input.contains(">"));
    }
}


