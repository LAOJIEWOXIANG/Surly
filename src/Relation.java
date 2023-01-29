import java.util.LinkedList;

public class Relation {
  private String name; /* name of the relation */
  private LinkedList<Attribute> schema; /* Schema of the relation */
  private LinkedList<Tuple> tuples; /* Tuples stored on the relation */
  
  public Relation(String name) {
    this.name = name;
    this.schema = new LinkedList<>();
    this.tuples = new LinkedList<>();
  }
  
  /* Formats and prints the relation's name, schema, and tuples */
  public void print() {
    
    /* Calculates how much space each element needs in the table. */
    int schemaSize = this.schema.size();
    int[] lengths = new int[schemaSize];
    int totalLength = 0;
    for (int i = 0; i < schemaSize; i++) {
      Attribute a = this.schema.get(i);
      /* Gets the size of the attribute's max length or the
      length of the attribute name, whichever is largest*/
      int length = Math.max(a.getLength(), a.getName().length());
      lengths[i] = length;
      totalLength += length + 3; /* Gives buffer space for " " and "|". */
    }
    totalLength++; /* One more space for the rightmost "|". */
    System.out.println("*".repeat(totalLength));
    printRelationName(totalLength);
    System.out.println("-".repeat(totalLength));
    printSchema(totalLength,lengths);
    System.out.println("-".repeat(totalLength));
    printTuples(totalLength,lengths);
    System.out.println("*".repeat(totalLength));
  }
  
  /* Formats and prints the name of the relation. */
  private void printRelationName(int length) {
    System.out.print("| ");
    System.out.printf("%-" + (length - 3) +"S", this.name);
    System.out.println('|');
  }
  
  /* Formats and prints the schema of the relation. */
  private void printSchema(int length, int[] lengths) {
    for (int i = 0; i < schema.size(); i++) {
      System.out.print("| ");
      System.out.printf("%-" + (lengths[i] + 1) +"S", schema.get(i).getName());
    }
    System.out.println('|');
  }
  
  /* Formats and prints the tuples of the relation. */
  private void printTuples(int length, int[] lengths) {
    for (Tuple t : this.tuples) {
      for (int i = 0; i < this.schema.size(); i++) {
        System.out.print("| ");
        System.out.printf("%-" + (lengths[i] + 1) + "S", t.getValue(i));
      }
      System.out.println('|');
    }
  }
  
  /* Accessor method for name. */
  public String getName() {
    return this.name;
  }
  
  /* Adds the specified tuple to the relation. */
  public void insert(Tuple tuple) {
    if (tuple.length() == schema.size()) {
      for (int i = 0; i < this.schema.size(); i++) {
        Attribute currentSchema = this.schema.get(i);
        tuple.setName(i,currentSchema.getName());
        // check if the datatype of currentschema matches with tuple's datatype
        String datatype = currentSchema.getDataType();
        if (datatype.equalsIgnoreCase("NUM") && !isNumeric(tuple.getValue(i))) {
           System.out.println("Invalid data type for attribute \"" 
                              + currentSchema.getName() + "\" (given: \""
                              + tuple.getValue(i) + "\" expected: NUM).");
           return;
        }
        int maxLength = currentSchema.getLength();
        if (tuple.getValue(i).length() > maxLength) {
          tuple.trimValue(i, maxLength);
        }
      }
      this.tuples.add(tuple);
    } else {
      System.out.println("Error inserting to relation \"" + this.name + "\"" +
                         " (tuple length does not match schema length).");
    }
  }

  /* check if str is char */
  public boolean isChar(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (Character.isDigit(str.charAt(i))) {
        continue;
      } else {
        return true;
      }
    }
    return false;
  }

  /* check if str is numeric */
  public boolean isNumeric(String str) {
    if (str == null) {
      return false;
    }
    try {
        Integer d = Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
        return false;
    } 
    return true;
  }

  /* Deletes a tuple from the linked list by the name of its first attribute*/
  public boolean deletetuple(String name) {
    Boolean deleted = false;
    for (int i = 0; i < this.tuples.size(); i++) {
      Tuple currentTuple = this.tuples.get(i);
      if (currentTuple.getValue(0).equalsIgnoreCase(name)) {
        this.tuples.remove(i);
        deleted = true;
      }
    }
    return deleted;
  }
  
  /* Adds specified attribute to the schema. */
  public void addToSchema(Attribute attribute) {
    this.schema.add(attribute);
  }
  
  /* Remove all tuples from the relation */
  public void delete() {
    if (this.tuples.isEmpty()) {
      System.out.println("Relation is already empty");
    } else {
      this.tuples.clear();
    }
  }

  public Integer schemaSize() {
    return this.schema.size();
  }
}
