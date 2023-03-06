public class SelectParser {
    private String input;

    SelectParser(String input) {
        this.input=input.split(";")[0];
    }
    public String parseRelationName() {
        return input.split("\\s+")[1];
    }

    //method that takes a relation and returns a temporary relation with the tuples
    //pruned. T1 = SELECT OFFERING WHERE CNUM = CSCI241 and SECTION > 27922;
    public Relation selectWhere(Relation relation, String name) {
        WhereParser whereParser = new WhereParser(getWhereClause());
        Relation tempRelation = copyRelation(relation, name);
        int relationSize = relation.size();
        for (int i = 0; i<relationSize; i++) {
            Tuple temp = relation.getTuple(i);
            if (whereParser.meetsConditions(temp)) {
                tempRelation.insert(temp);
            }
        }
        return tempRelation;
    }

    

    private Relation copyRelation(Relation relation, String name) {
        Relation newRelation = new Relation(name);
        int schemaSize = relation.schemaSize();
        for (int i = 0; i<schemaSize; i++) {
            newRelation.addToSchema(relation.getSchema(i));
        }
        return newRelation;
    }

    private String getWhereClause() {
        int whereIndex = input.toUpperCase().indexOf("WHERE");
        if (whereIndex != -1) {
            return input.substring(whereIndex, input.length());
        }
        return ""; 
    }
}
