// **********************************************************
// Assignment1:
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

import fileSystem.ControllableDirectory;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the popd command, whose parent class is Command
 */
public class Popd implements Command {

  /**
   * A method to execute the exit command, exit from current running shell
   * Remove the top entry from the directory stack, and cd into it. The removal
   * must be consistent as per the LIFO behavior of a stack. The popd command
   * removes the top most directory from the directory stack and makes it the
   * current working directory. If there is no directory onto the stack, then
   * give appropriate error message.
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // get the directory from top of the stack
    String popDir = CommandObject.popDirStack();
    // if popDir is not "", means the stack is not empty
    if (popDir.equals("")) {
      CommandObject.logLine("The directory stack is empty");
    } else {
      try {
        // change the current working directory to popDir
        mock.setWorkingDir(
            (ControllableDirectory) mock.getAbsolutePathOf(popDir));
        // JShell.Logln(mock.getDirectoryPath(mock.getWorkingDir()));
      } catch (NoSuchFileExistException e) {
        // Do nothing
      }
    }
  }

  /**
   * A method to print the manual for popd
   */
  @Override
  public void getManual() {
    CommandObject.logLine(
        "Remove the top entry from the directory stack, and cd into it. "
            + "The removal\nmust be consistent as per the LIFO behavior of a "
            + "stack. The popd\ncommand removes the top most directory from "
            + "the directory stack and\nmakes it the current working "
            + "directory. If there is no directory onto the\nstack, then give "
            + "appropriate error message. \n--------Example-----------\n"
            + "/#: mkdir a\n" + "/#: pwd\n/\n" + "/#: pushd a\n" + "/#: pwd\n"
            + "/a/\n" + "/#: popd\n/#: pwd\n" + "/\n" + "/#: popd\nThe "
            + "directory stack is empty\n" + "/#: \n-------------------------");
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return boolean that's represents the correctness of argument
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    if (arg.length == 0)
      return true;
    return false;
  }

}
