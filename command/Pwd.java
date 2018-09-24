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
 * Represents the pwd command, whose parent class is Command
 */
public class Pwd implements Command {
  /**
   * Print the current working directory (including the whole path)
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an empty string list, which shouldn't be used because
   *        command:pwd takes no argument
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // create a new string to be the directory to be returned
    String dir;
    // assign dir to be the current working directory
    dir = mock.getDirectoryPath(mock.getWorkingDir());
    // print out the current working directory
    CommandObject.logLine(dir);
  }

  /**
   * Print documentation for pwd command
   */
  @Override
  public void getManual() {
    // create a string doc to be the documentation for pwd command
    String doc =
        "Print the current working directory (including the whole path).\nIf "
            + "the user types in \"pwd\" right after running JShell, it will "
            + "show:\n/\nIf the user types in \"mkdir A\" and \"cd A\", then "
            + "\"pwd\", it will display:\n/A/";
    // print out the documentation for pwd command
    System.out.println(doc);
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length == 0
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // If there are arguments then it must be wrong
    return arg.length == 0;
  }
}
