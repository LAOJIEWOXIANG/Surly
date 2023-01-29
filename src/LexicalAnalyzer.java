import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzer {
  
  private SurlyDatabase database;
  /* Constructor to initialize the database. */
  public LexicalAnalyzer() {
    this.database = new SurlyDatabase();
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
      RelationParser relationParser = new RelationParser(command);
      String relationName = relationParser.parseRelationName();
      if (relationParser.getIsValidSyntax()) {
        if (this.database.getRelation(relationName) == null) {
           Relation newRelation = relationParser.parseRelation();
           if (newRelation != null) {
              this.database.createRelation(newRelation);
           }
        } 
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }     
      break;
    }
      case "INSERT": {
      InsertParser insert = new InsertParser(command);
      if (insert.getIsValidSyntax()) {
        String relationName = insert.parseRelationName();
        if (relationName.equalsIgnoreCase("CATALOG")) {
           System.out.println("ERROR INSERTING TO RELATION: CANNOT INSERT TO "
                              + "CATALOG.");
           break;
        }
        Tuple tuple = insert.parseTuple();
        Relation currentRelation = this.database.getRelation(relationName);
        if (currentRelation != null) {
          currentRelation.insert(tuple);
        } else {
          System.out.print("ERROR INSERTING TO RELATION \"" + relationName + "\": ");
          System.out.println("RELATION NOT FOUND.");
        }
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
          Relation currentRelation = this.database.getRelation(relationName);
          if (currentRelation != null) {
            currentRelation.print();
            System.out.println();
          } else {
            System.out.print("ERROR PRINTING RELATION \"" + relationName + "\": ");
            System.out.println("RELATION NOT FOUND.");
          }
        }
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      
      break;
    }
      case "DESTROY": { 
      DestroyParser destroy = new DestroyParser(command);
      String relationName = destroy.parseRelationName();
      if (destroy.getIsValidSyntax() == true) {
        if (this.database.getRelation(relationName) != null) {
          this.database.destroyRelation(relationName);
        } else {
          System.out.print("ERROR DESTROYING RELATION \"" + relationName + "\": ");
          System.out.println("RELATION NOT FOUND.");
        }
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      break;
    }
      case "DELETE": {
      DeleteParser delete = new DeleteParser(command);
      if (delete.getIsValidSyntax()) {
        String relationName = delete.parseRelationName();
        Relation relationToDelete = database.getRelation(relationName);
        if (relationToDelete != null) {
          relationToDelete.delete();
        } else {
          System.out.print("ERROR DELETING FROM RELATION \"" + relationName + "\": ");
          System.out.println("RELATION NOT FOUND.");
        }
      } else {
        System.out.println("INVALID SYNTAX: " + command);
      }
      break;
    }
      default:
      if (!command.equals("")) {
         System.out.println("INVALID COMMAND: " + command);
      }
    }
  }
  
  // Drops commented lines from a string
  private String dropComment(String command) {
    return command.replaceAll("(?m)^#.*$", "");
  }
  
}
