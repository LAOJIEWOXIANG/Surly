public class TestInsertParser {

   public static void main(String[] args) {
      InsertParser ip = new InsertParser("INSERT COURSE CSCI241 'DATA STRUCTURES' 4;");
      ip.parseTuple();
   }
}