import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer {
  
  private SurlyDatabase database;
  /* Constructor to initialize the database. */
  public LexicalAnalyzer() {
    database = new SurlyDatabase();
  }
  
  /*
  * Parses the given file into individual commands
  * and passes each to the appropriate parser
  */
  public void run(String fileName) {
    try {
      FileReader fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);
      int c;
      StringBuilder sb = new StringBuilder();
      while ((c = br.read()) != -1) {
        char nextChar = (char) c;
        if (nextChar == ';') {
          // The character is a semicolon - end of command
          sb.append(nextChar);
          String finalString = sb.toString();
          sb.setLength(0);
          processCommand(finalString);
        } else {
          // The character is still part of the command
          sb.append(nextChar);
        }
      }
      br.close();
      fr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /*
  * Given a command string, first process the string and remove commented lines.
  * Then verify the command is syntactically correct and execute it.
  */
  private void processCommand(String command) {
    command = dropComment(command);
    command = command.trim();
    String[] words = command.split("\\s+");
    String commandType = words[0];
    switch (commandType) {
      case "RELATION": {
      RelationParser relation = new RelationParser(command);
      if (relation.getIsValidSyntax()) {
        Relation r = relation.parseRelation();
        database.createRelation(r);
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      
      break;
    }
      case "INSERT": {
      InsertParser insert = new InsertParser(command);
      if (insert.getIsValidSyntax()) {
        String relationName = insert.parseRelationName();
        Tuple tuple = insert.parseTuple();
        database.getRelation(relationName).insert(tuple);
        // The Relation.insert(Tuple tuple) function must set the names of the attribute values to
        //  names in the schema.
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      
      break;
    }
      case "PRINT":{
      PrintParser print = new PrintParser(command);
      if (print.getIsValidSyntax()) {
        String[] names = print.parseRelationNames();
        for (String relationName: names) {
          if (database.getRelation(relationName) != null) {
            database.getRelation(relationName).print();
          }
        }
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      
      break;
    }
      case "DESTROY": { 
      DestroyParser destroy = new DestroyParser(command);
      String name = destroy.parseRelationName();
      if (destroy.getIsValidSyntax() == true) {
        if (database.getRelation(name) != null) {
          database.destroyRelation(name);
        } else {
          System.out.println("RELATION NOT FOUND");
        }
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      break;
    }
      case "DELETE": {
      DeleteParser delete = new DeleteParser(command);
      String name = delete.parseRelationName();
      Relation relationName = new Relation(name);
      relationName.delete();
      break;
    }
      default:
      System.out.println("INVALID COMMAND: " + command);
    }
  }
  
  // Drops commented lines from a string
  private String dropComment(String command) {
    return command.replaceAll("(?m)^#.*$", "");
  }
  
}
