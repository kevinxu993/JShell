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
import java.util.Collections;
import java.util.UUID;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the ls command, whose parent class is Command
 */
public class Ls implements Command {

  /**
   * Represents the errors list
   */
  private ArrayList<String> errorList = new ArrayList<String>();

  /**
   * A method to check the input argument, all is true this case
   * 
   * @param arg an String[] containing the arguments after the command
   * @return true
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    return true;
  }

  /**
   * A method to execute the ls command, print out content of directory
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    boolean recursively = arg.length > 0;
    recursively = recursively ? arg[0].equals("-R") : false;
    ArrayList<String> ALarg = new ArrayList<>();
    for (Object item : arg) {
      ALarg.add((String) item);
    }
    if (ALarg.subList(recursively ? 1 : 0, ALarg.size()).size() > 0) {
      // for each path
      for (String pathth : ALarg.subList(recursively ? 1 : 0, ALarg.size())) {
        // Split target and target's parent directory
        int indexOfSlash = pathth.lastIndexOf("/");
        String theDirectoryPath =
            indexOfSlash == -1 ? mock.getDirectoryPath(mock.getWorkingDir())
                : (pathth).substring(0, indexOfSlash);
        tryListFiles(mock, pathth, theDirectoryPath, recursively);
      }
    } else
      // the path not given, print the content of current directory
      printFileNamesUnderDirectory(mock, mock.getWorkingDir(), recursively, 0);

    for (String message : errorList) {
      CommandObject.logErr(message);
    }
  }

  /**
   * @param mock the mock file system that the JShell is running on
   * @param path the path that is going to be executing list command
   * @param theDirectoryPath get the parent directory of path
   * @param recursively recursively list content under given path
   */
  private void tryListFiles(ManagementOfContainerKernel mock, Object path,
      String theDirectoryPath, boolean recursively) {
    try {
      // Try get the directory instance
      // Since this is in a try catch, exceptions will be thrown and handled
      String fileName = (String) path;
      if(!fileName.equals("/"))
      fileName = fileName.split("/")[fileName.split("/").length - 1];
      ControllableDirectory theDirectoryInstance =
          (ControllableDirectory) mock.getAbsolutePathOf(theDirectoryPath);

      ControllableFile targetDirectory =
          ((String) path).endsWith("/") ? theDirectoryInstance
              : mock.findFile(theDirectoryInstance.getFileUUID(mock, fileName));
      if (targetDirectory != null) {
        // Log its name first
        CommandObject.log(mock.getDirectoryPath(targetDirectory.getParent())
            + targetDirectory.getName());
        // if it is a directory
        if (targetDirectory.isDirectory()) {
          // We need a colon
          CommandObject.logLine(":");
          ControllableDirectory targetDirectory1 =
              (ControllableDirectory) targetDirectory;
          printFileNamesUnderDirectory(mock, targetDirectory1, recursively, 0);
        }
        CommandObject.logLine("");// The line breaks
      }
      // Below are error messages
      else {
        // If the target cannot be located, add a error message
        addFileNotLocated(path);
      }

      // If the target cannot be located, add a error message
    } catch (NoSuchFileExistException e) {
      addFileNotLocated(path);
    }
  }

  /**
   * A method to print file names under the directory
   * 
   * @param MOCK
   * @param targetDirectory
   * @param recursively recursively list content under given path
   * @param dashCount count of dash that should be logged before file name
   */
  private void printFileNamesUnderDirectory(ManagementOfContainerKernel MOCK,
      ControllableDirectory targetDirectory, boolean recursively,
      int dashCount) {
    // Loop through every UUID
    for (UUID fileUUID : targetDirectory.getFileUUIDs()) {
      ControllableFile cFile = MOCK.findFile(fileUUID);
      // Log the file name into the line, maybe with dash
      CommandObject.log(String.join("", Collections.nCopies(dashCount, "-")));
      CommandObject.logLine(cFile.getName());
      // If recursive is happening
      if (cFile.isDirectory() && recursively) {
        printFileNamesUnderDirectory(MOCK, (ControllableDirectory) cFile,
            recursively, dashCount + 1);
      }
    }
  }

  /**
   * A method to add the error to the error list
   * 
   * @param Path
   */
  private void addFileNotLocated(Object Path) {
    errorList.add("ls: File:" + Path + "\n cannot be located!");
  }

  /**
   * Print documentation for ls command
   * 
   * @return doc
   */
  @Override
  public void getManual() {
    String doc = "This method list of files in the current working directory"
        + "\n e.g. \n ls a.txt   Output: a.txt"
        + "\n ls a       Output: a1 a2 a3"
        + "\n ls a a.txt Output: a1 a2 a3 a.txt";
    CommandObject.logLine(doc);
  }

}

