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
     int totalLength = 0;
     int numAttributes = schema.size();
     for (Attribute a : schema) {
        totalLength += a.getLength() + 4;
     }
     totalLength += 1;
     printMany('*',totalLength);
     System.out.print("| ");
     System.out.printf("%-" + (totalLength - 3) +"S", name);
     System.out.println('|');
     
  }
  
  private void printMany(char c, int size) {
     for (int i = 0; i < size; i++) {
        System.out.print(c);
     }
     System.out.println();
  }
  
  public void addToSchema(Attribute a) {
     schema.add(a);
  }

  /* Adds the specified tuple to the relation */
  public void insert(Tuple tuple) {

  }

  /* Remove all tuples from the relation */
  public void delete() {

  }
}
