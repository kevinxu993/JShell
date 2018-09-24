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
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the mkdir command, whose parent class is Command
 */
public class Mkdir implements Command {

  /**
   * A method to execute the mkdir command, make a new directory
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    for (Object thepath : arg) {
      // Covert the the path to String to save code
      String thePath = ((String) thepath);

      // I want to remove the end / so it will be easier to do with
      while (thePath.endsWith("/")) {
        thePath = thePath.substring(0, thePath.length() - 1);
      }
      // Use try/catch block here to utilize the exceptions for AbsolutePathOf
      try {
        // If this successes then the directory already existed and should go on
        if (mock.getAbsolutePathOf(thePath) != null) {
          logPathAlreadyExists(thePath);
          continue;
        }
      } catch (NoSuchFileExistException e) {

        String theDirectoryPath = thePath.contains("/")
            ? thePath.substring(0, thePath.lastIndexOf("/")) + "/"
            : mock.getDirectoryPath(mock.getWorkingDir());

        String directoryName = thePath.contains("/")
            ? thePath.substring(thePath.lastIndexOf("/") + 1)
            : thePath;
        try {
          ControllableDirectory theDirectoryInstace =
              (ControllableDirectory) mock.getAbsolutePathOf(theDirectoryPath);
          ControllableDirectory tempWD = mock.getWorkingDir();
          mock.setWorkingDir(theDirectoryInstace);
          try {
            // If the file is not existing
            mock.createDirectoryUnderWD(directoryName);
          } catch (FileWithSameNameExistedException ea) {
            // If the file already exists, which basically will not
            logPathAlreadyExists(thePath);
          } catch (InvalidFileNameException eb) {
            // If the file name is not invalid
            logInvalidDirectoryName(directoryName);
            mock.setWorkingDir(tempWD);
            return;
          }
          mock.setWorkingDir(tempWD);
        } catch (NoSuchFileExistException e1) {
          logPathNotValid(thePath);
        }

      }
    }
  }

  /**
   * A method to show that the given directory is already exist
   * 
   * @param thePath
   */
  private void logPathAlreadyExists(String thePath) {
    CommandObject
        .logErr("mkdir: Directory " + thePath + "/" + " already exists!");
  }

  /**
   * A method to print out the manual for mkdir
   */
  @Override
  public void getManual() {
    CommandObject.logLine("This command acquires a path and creates"
        + " the directory for user following the path. \n"
        + "Usage: mkdir /absolutepath/folderName \n"
        + "Usage: mkdir relativepath/folderName");
  }

  /**
   * A method to show that the path is invalid
   * 
   * @param Path
   */
  private void logPathNotValid(Object Path) {
    CommandObject.logErr("mkdir: Path:" + Path + "\n is not valid!");
  }

  /**
   * A method to show the invalid Filename
   * 
   * @param FileName
   */
  private void logInvalidDirectoryName(String FileName) {
    CommandObject.logErr("mkdir: Invalid directory name: " + FileName);
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length != 0
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // If there is no argument then it must be wrong
    return arg.length != 0;
  }

}
