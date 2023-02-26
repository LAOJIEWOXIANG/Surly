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
          /* The character is a semicolon - end of command */
          sb.append(nextChar);
          String finalString = sb.toString();
          sb.setLength(0);
          processCommand(finalString);
        } else {
          /* The character is still part of the command */
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
    String commandType = command.split("\\s+",2)[0];
    switch (commandType) {
      case "RELATION": {
        handleRelation(command);
        break;
      }
      case "INSERT": {
        handleInsert(command);
        break;
      }
      case "PRINT":{
        handlePrint(command);
        break;
      }
      case "DESTROY": {
        handleDestroy(command);
        break;
      }
      case "DELETE": {
        handleDelete(command);
        break;
      }
      /* Need to change LexicalAnalyzer to be able to handle setting temp relations */
      case "SELECT": {
        handleSelect(command);
        break;
      }
      default:
      if (!command.equals("")) {
        System.out.println("INVALID COMMAND: " + command);
      }
    }
  }


  private void handleSelect(String command) {
    SelectParser select = new SelectParser(command);
    String relationName = select.parseRelationName();
    Relation selectedRelation = database.getRelation(relationName);
    if (selectedRelation != null) {
      database.createRelation(select.selectWhere(selectedRelation, "T1"));
    } else {
      System.out.print("ERROR SELECTING FROM RELATION \"" + relationName + "\": ");
      System.out.println("RELATION NOT FOUND.");
    }
  }
  
  /* Processes a DELETE command by passing it to the delete parser. */
  private void handleDelete(String command) {
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
  }

  /* Processes a RELATION command by passing it to the relation parser. */
  private void handleRelation(String command) {
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
  }
  
  /* Processes an INSERT command by passing it to the insert parser. */
  private void handleInsert(String command) {
    InsertParser insert = new InsertParser(command);
    if (insert.getIsValidSyntax()) {
      String relationName = insert.parseRelationName();
      if (relationName.equalsIgnoreCase("CATALOG")) {
        System.out.println("ERROR INSERTING TO RELATION: CANNOT INSERT TO "
        + "CATALOG.");
        return;
      }
      Tuple tuple = insert.parseTuple();
      if (tuple == null) {
        return;
      }
      Relation currentRelation = this.database.getRelation(relationName);
      if (currentRelation != null) {
        currentRelation.insert(tuple);
      } else {
        System.out.println("ERROR INSERTING TO RELATION \"" + relationName + "\": "
        + "RELATION NOT FOUND.");
      }
    } else {
      System.out.println("INVALID SYNTAX: " + command);
    }
  }
  
  /* Processes a PRINT command by passing it to the print parser. */
  private void handlePrint(String command) {
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
  }
  
  /* Processes a DESTROY command by passing it to the destroy parser. */
  private void handleDestroy(String command) {
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
  }
  
  
  
  /* Drops commented lines from a string. */
  private String dropComment(String command) {
    return command.replaceAll("(?m)^#.*$", "");
  }
}
