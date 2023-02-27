import java.util.ArrayList;

public class ProjectParser {
    private String input;
    private ArrayList<String> attributes;

    public ProjectParser(String input) {
        this.input = input;
        attributes = new ArrayList<>();
        parseAttributes();
    }

    public Relation project(Relation relation, String name) {
        Relation newRelation = new Relation(name);
        int schemaSize = relation.schemaSize();
        /* Setting schema */
        for (int i = 0; i<schemaSize; i++) {
            Attribute currAttribute = relation.getAttribute(i);
            if (attributes.contains(currAttribute.getName())) {
                newRelation.addToSchema(currAttribute);
            }
        }
        int numTuples = relation.size();
        //doesnt ignore duplicates for now
        for (int i = 0; i < numTuples; i++) {
            Tuple tuple = relation.getTuple(i);
            Tuple newTuple = new Tuple();
            for (String s : attributes) {
                String value = tuple.getValue(s);
                AttributeValue av = new AttributeValue(s,tuple.getValue(s));
                newTuple.add(av);
            }
            newRelation.insert(newTuple);
        }
        return newRelation;
    }

    public String parseRelationName() {
        String[] splitInput = input.split("\\s+|;");
        return splitInput[splitInput.length-1];
    }

    private void parseAttributes() {
        String[] isolated = isolateAttributes().split(",\\s+");
        for (String s : isolated) {
            attributes.add(s);
        }
    }

    private String isolateAttributes() {
        String isolated = "";
        String removedProject = input.split("\\s+",2)[1].trim();
        int fromIndex = removedProject.indexOf("FROM");
        isolated = removedProject.substring(0,fromIndex);
        //maybe need to account for if there is no "FROM", but there should always be a FROM
        return isolated.trim();
    }
}


