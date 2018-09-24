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

import fileSystem.ManagementOfContainerKernel;

/**
 * Represents the exit command, whose parent class is Command
 */
public class Exit implements Command {

  /**
   * A method to execute the exit command, exit from current running shell
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    System.exit(0);
  }

  /**
   * A method to print the manual for exit
   */
  @Override
  public void getManual() {
    CommandObject
        .logLine("Terminates the JShell \n" + "--------Example----------- \n"
            + "/#: exit\n" + "\n" + "-------------------------");
  }

  /**
   * A method to check whether the input is correct in this case, it is always
   * false
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length == 0
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    if (arg.length == 0)
      return true;
    return false;
  }

}
