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

import java.lang.reflect.InvocationTargetException;
import fileSystem.ManagementOfContainerKernel;

/**
 * Represents the man command, whose parent class is Command
 */
public class Man implements Command {

  /**
   * A method to execute the man command, print out the manual of given cmd
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // Get the command's path
    String command = (String) arg[0];
    if (command.equals("!")) {
      command = "exclaim";
    }
    String commandPath = new CommandObject().findClassName(command);

    // getManual is the method for printing the manual
    String methodName = "getManual";
    // Initialize the class
    Class myClass;
    try {
      // Find desired class and call getManual
      myClass = Class.forName(commandPath);
      myClass.getDeclaredMethod(methodName).invoke(myClass.newInstance(),
          new Object[] {});
    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
        | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | InstantiationException e) {
      // If command not found, print error message
      CommandObject.logErr("Command not exist!");
    }
  }

  /**
   * A method to show the given command is not existed
   * 
   * @param command
   */
  private void commandNotExist(Object command) {
    CommandObject.logLine("Command:" + command + "\n does not exit!");
  }

  /**
   * A method to print documentation for the man command.
   */
  @Override
  public void getManual() {
    String doc = "This method print documentation for commands. \n Usage:"
        + " \n man <command>" + "\ne.g." + "\nman cd\n"
        + "\nThis method change the current directory to another directory"
        + "\ne.g. \n" + "cd a" + "\npwd" + "\nOutput: a" + "\n\ncd a1"
        + "\nOutput: File a1 does not exist";
    System.out.println(doc);
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return true
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // TODO Auto-generated method stub\
    return true;
  }

}
