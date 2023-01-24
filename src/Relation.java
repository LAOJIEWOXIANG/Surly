import java.util.LinkedList;

public class Relation {
  private String name; /* name of the relation */
  private LinkedList<Attribute> schema; /* Schema of the relation */
  private LinkedList<Tuple> tuples; /* Tuples stored on the relation */
  
  public Relation(String name) {
    this.name = name;
    schema = new LinkedList<>();
    tuples = new LinkedList<>();
  }
  
  /* Formats and prints the relation's name, schema, and tuples */
  public void print() {
    
    /* Calculates how much space each element needs in the table. */
    int[] lengths = new int[schema.size()];
    int totalLength = 0;
    for (int i = 0; i < schema.size(); i++) {
      Attribute a = schema.get(i);
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
    System.out.printf("%-" + (length - 3) +"S", name);
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
    for (Tuple t : tuples) {
      for (int i = 0; i < schema.size(); i++) {
        System.out.print("| ");
        System.out.printf("%-" + (lengths[i] + 1) + "S", t.getValue(i));
      }
      System.out.println('|');
    }
  }
  
  /* Accessor method for name. */
  public String getName() {
    return name;
  }
  
  /* Adds the specified tuple to the relation. */
  public void insert(Tuple tuple) {
    for (int i = 0; i < schema.size(); i++) {
      tuple.setName(i,schema.get(i).getName());
      int maxLength = schema.get(i).getLength();
      if (tuple.getValue(i).length() > maxLength) {
        tuple.trimValue(i, maxLength);
      }
    }
    tuples.add(tuple);
  }
  
  /* Adds specified attribute to the schema. */
  public void addToSchema(Attribute attribute) {
    schema.add(attribute);
  }
  
  /* Remove all tuples from the relation */
  public void delete() {
  }
}
