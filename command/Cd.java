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

import fileSystem.ControllableDirectory;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the cd command, whose parent class is Command
 */
public class Cd implements Command {

  /**
   * A method to execute the cd command, change current working directory
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // Get the directory's string path
    String path = (String) arg[0];
    try {
      // set working directory to the given path
      mock.setWorkingDir((ControllableDirectory) mock.getAbsolutePathOf(path));
    } catch (NoSuchFileExistException e) {
      // if the path not exit
      logFileNotExist(path);
    }
  }

  /**
   * A method to show that the given path is not existed
   * 
   * @param Path
   */
  private void logFileNotExist(Object Path) {
    CommandObject.logErr("File:" + Path + "\n does not exist!");
  }

  /**
   * A method to print documentation for the cd command.
   */
  @Override
  public void getManual() {
    CommandObject
        .logLine("This method change the current directory to another directory"
            + "\ne.g. \n" + "cd a" + "\npwd" + "\nOutput: a" + "\n\ncd a1"
            + "\nOutput: File a1 does not exist");
  }


  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length >= 1
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // CD requires only one argument, which is the path
    return (arg.length >= 1);
  }
}
