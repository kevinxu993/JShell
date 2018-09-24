// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: songzhif
// UT Student #: 1004359026
// Author: Zhifei Song
//
// Student2:
// UTORID user_name: xuxinzhe
// UT Student #: 1004050661
// Author: Xinzheng Xu
//
// Student3:
// UTORID user_name: wangq150
// UT Student #: 1004193419
// Author: Qingtian Wang
//
// Student4:
// UTORID user_name: wangz442
// UT Student #: 1004154960
// Author: Zijian Wang
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package command;

import java.util.ArrayList;
import fileSystem.ManagementOfContainerKernel;

/**
 * Represents the history command, whose parent class is Command
 */
public class History implements Command {

  /**
   * A method to execute the history command, print out recent commands, one
   * command per line.
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // get the ArrayList for inputs from JShell
    ArrayList<String> cmds = CommandObject.getCmdList();
    cmds = filter(cmds);
    // get the length of the ArrayList
    int cmdLen = cmds.size();
    // check if the user truncates the output by specifying a number
    if (arg.length == 0) {
      // get all the commands stored in the ArrayList
      for (int i = 0; i < cmdLen; i++) {
        // print the index number and each command from 1 to the last one
        System.out.println(Integer.toString(i + 1) + ". " + cmds.get(i));
      }
      // if the user truncates the output by specifying a number
    } else if (arg.length == 1) {
      // set the starting index
      int start = cmdLen - Integer.parseInt((String) arg[0]);
      // if the number of commands is incorrect
      if (Integer.parseInt((String) arg[0]) <= 0) {
        // print the error message
        logErr();
        return;
      }
      if (start >= 0) {
        // while the current index is smaller than the last index
        while (start <= cmdLen - 1) {
          // print the index number and each command from the starting index to
          // the last one
          System.out
              .println(Integer.toString(start + 1) + ". " + cmds.get(start));
          // increase the current index by 1
          start++;
        }
      } else {
        // get all the commands stored in the ArrayList
        for (int i = 0; i < cmdLen; i++) {
          // print the index number and each command from 1 to the last one
          System.out.println(Integer.toString(i + 1) + ". " + cmds.get(i));
        }
      }

    }
  }

  /**
   * A method to change the elements in the list
   * 
   * @param cmds an ArrayList of strings representing the commands typed in
   */
  private ArrayList<String> filter(ArrayList<String> cmds) {
    int i = 0;
    for (String com : cmds) {
      String str = com.trim();
      if ((str.charAt(0) == '!') && (str.length() > 1)) {
        if (str.split("!")[1].chars().allMatch(Character::isDigit)) {
          int num = Integer.parseInt(str.split("!")[1]);
          if (num <= cmds.size()) {
            cmds.set(i, cmds.get(num - 1));
          }
        }
      }
      i++;
    }
    return cmds;
  }

  /**
   * A method to print documentation for the history command.
   */
  @Override
  public void getManual() {
    CommandObject.logLine(
        "This command will print out recent commands, one command per line. You"
            + " can truncate the output by specifying a number (>=0) after the "
            + "command.\nFor example, if the user types in the following:\ncd "
            + "..\nmkdir textFolder\necho \"Hello World\"\nfsjhdfks\nhistory\n"
            + "Then it will display as following:\n1. cd ..\n2. mkdir text"
            + "Folder\n3. echo \"Hello World\"\n4. fsjhdfks\n5. history\nIf the"
            + " user types in \"history 3\" as the sixth input, then it will "
            + "display:\n4. fsjhdfks\n5. history\n6. history 3");
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length <= 1
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // check if the number of argument is smaller than 2 because it can only
    // takes 0 or 1 arguments
    return arg.length <= 1;
  }

  /**
   * A method to print error message
   * 
   * @param num an int representing the number of commands the user wants to see
   */
  public static void logErr() {
    // print the error message
    CommandObject.logErr("Invalid command, please try again");
  }
}

