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
 * Represents the pushd command, whose parent class is Command
 */
public class Pushd implements Command {

  /**
   * Saves the current working directory by pushing onto directory stack and
   * then changes the new current working directory to DIR. The push must be
   * consistent as per the LIFO behavior of a stack. The pushd command saves the
   * old current working directory in directory stack so that it can be returned
   * to at any time (via the popd command). The size of the directory stack is
   * dynamic and dependent on the pushd and the popd commands.
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // get the first argument inside the arg array
    String path = (String) arg[0];
    try {
      // JShell.Logln(mock.getDirectoryPath(mock.getWorkingDir()));
      // save the current directory before change it
      String oldWorkingDir = mock.getDirectoryPath(mock.getWorkingDir());
      // change the current working directory to the one user typed
      mock.setWorkingDir((ControllableDirectory) mock.getAbsolutePathOf(path));
      // JShell.Logln(oldWorkingDir);
      // push the old working directory to the stack
      CommandObject.pushDirStack(oldWorkingDir);
    } catch (NoSuchFileExistException e) {
      // print the message that's shows the problem of the arg
      CommandObject.logLine("directory not exist");
    }
    // command.Command.Execute("cd", arg);
  }

  /**
   * A method to print the manual for pushd
   */
  @Override
  public void getManual() {
    // TODO Auto-generated method stub
    CommandObject.logLine(
        "Saves the current working directory by pushing onto directory "
            + "stack and\nthen changes the new current working directory to "
            + "DIR. The push must be\nconsistent as per the LIFO behavior of "
            + "a stack. The pushd command\nsaves the old current working "
            + "directory in directory stack so that it can be\nreturned to "
            + "at any time (via the popd command). The size of the directory"
            + "\nstack is dynamic and dependent on the pushd and the popd "
            + "commands. \n--------Example----------- \n" + "/#: mkdir a\n"
            + "/#: pwd\n/\n/#: pushd a\n/#: pwd" + "\n" + "/a/\n" + "/#: "
            + "popd\n/#: pwd\n/\n/#: popd\nThe " + "directory stack is "
            + "empty\n/#: \n----------------------" + "---");
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return boolean that's represents the correctness of argument
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    if (arg.length == 1)
      return true;
    return false;
  }

}
