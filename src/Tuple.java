import java.util.LinkedList;

public class Tuple {
    private LinkedList<AttributeValue> values;  /* Values of each attribute in the tuple */
    
    public Tuple() {
       values = new LinkedList<>();
    }
    
    /* Returns the value of the specified attribute */
    public String getValue(String attributeName) {
        for (int i = 0; i < values.size(); i++) {
           if (values.get(i).getName().equalsIgnoreCase(attributeName)) {
              return values.get(i).getValue();
           }
        }
        return null;
    }
    
    /* Adds given AttributeValue to the list of values. */
    public void add(AttributeValue attribute) {
       values.add(attribute);
    }
}
