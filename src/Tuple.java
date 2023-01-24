/* Tuple class for storing attribute values. */
import java.util.LinkedList;

public class Tuple {
  private LinkedList<AttributeValue> values;  /* Values of each attribute in the tuple */
  
  /* Constructor to initialize the linked list */
  public Tuple() {
    values = new LinkedList<>();
  }
  
  /* Returns the value of the specified attribute, or returns null
  if none of the attributes have the specified attribute name. */
  public String getValue(String attributeName) {
    
    /* If the name of the AttributeValue at i equals the given name,
    returns the value of the AttributeValue. */
    for (int i = 0; i < values.size(); i++) {
      if (values.get(i).getName().equalsIgnoreCase(attributeName)) {
        return values.get(i).getValue();
      }
    }
    return null;
  }
  
  /* Returns the value of the attribute at index i in the tuple. */
  public String getValue(int i) {
    return values.get(i).getValue();
  }
  
  /* Sets the name of the AttributeValue at index i to the given String. */
  public void setName(int i, String name) {
    values.get(i).setName(name);
  }
  
  /* Trims the value of AttributeValue at i down to the given max length. */
  public void trimValue(int i, int maxLength) {
    values.get(i).setValue(values.get(i).getValue().substring(0,maxLength));
  }
  
  /* Adds given AttributeValue to the list of values. */
  public void add(AttributeValue attribute) {
    values.add(attribute);
  }
}
