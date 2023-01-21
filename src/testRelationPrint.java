public class testRelationPrint {
   public static void main(String[] args) {
      Relation r = new Relation("Prereq");
      r.addToSchema(new Attribute("CHUM","CHAR",8));
      r.addToSchema(new Attribute("PNUM","CHAR",8));
      AttributeValue a1 = new AttributeValue("CNUM","CSCI141");
      AttributeValue a2 = new AttributeValue("PNUM","MATH112");
      AttributeValue a3 = new AttributeValue("CNUM","CSCI145");
      AttributeValue a4 = new AttributeValue("PNUM","MATH115");
      AttributeValue a5 = new AttributeValue("CNUM","CSCI145");
      AttributeValue a6 = new AttributeValue("PNUM","CSCI141");
      AttributeValue a7 = new AttributeValue("CNUM","CSCI241");
      AttributeValue a8 = new AttributeValue("PNUM","MATH124");
      Tuple t1 = new Tuple();
      Tuple t2 = new Tuple();
      Tuple t3 = new Tuple(); //23  ---16
      Tuple t4 = new Tuple(); //55 --> 42
      t1.add(a1);
      t1.add(a2);
      t2.add(a3);
      t2.add(a4);
      t3.add(a5);
      t3.add(a6);
      t4.add(a7);
      t4.add(a8);
      r.insert(t1);
      r.insert(t2);
      r.insert(t3);
      r.insert(t4);
      r.print();
      
   }
}