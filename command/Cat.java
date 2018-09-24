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
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the cat command, whose parent class is Command
 */
public class Cat implements Command {

  /**
   * Represents instance variable error list
   */
  private ArrayList<String> errorList = new ArrayList<String>();

  /**
   * A method to execute the cat command, print out content of file
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel MOCK, Object[] arg) {
    // Since the first time does not need line break, we use this bool to judge
    boolean firstTime = true;
    // For each path
    for (Object path : arg) {
      // Get the directory's string path from Path
      String theDirectoryPath = ((String) path).contains("/")
          ? ((String) path).substring(0, ((String) path).lastIndexOf("/")) + "/"
          : MOCK.getDirectoryPath(MOCK.getWorkingDir());
      try {
        // Try get the directory instance
        // Since this is in a try catch, exceptions will be thrown and handled
        ControllableDirectory theDirectoryInstace =
            (ControllableDirectory) MOCK.getAbsolutePathOf(theDirectoryPath);

        // If above did not fail, We get the file name of given path
        String fileName = ((String) path).contains("/")
            ? ((String) path).substring(((String) path).lastIndexOf("/") + 1)
            : ((String) path);

        // Check if desired file is under the directory
        if (theDirectoryInstace.getFileNames(MOCK).contains(fileName)) {
          // If exists
          ControllableFile targetFile =
              MOCK.findFile(theDirectoryInstace.getFileUUID(MOCK, fileName));

          // However, if the target is a directory, add a error message
          if (targetFile.isDirectory()) {
            addIsADirectory(path);
            continue;
          }
          CommandObject.log(!firstTime ? "\n\n\n" : "");// The line breaks
          CommandObject.logLine(new String(fileContent(targetFile)));// The
                                                                     // content
          firstTime = false;
        }
        // Below are error messages
        else {
          // If the target cannot be located, add a error message
          addFileNotLocated(path);
          continue;
        }
        // If the target cannot be located, add a error message
      } catch (NoSuchFileExistException e) {
        addFileNotLocated(path);
      }
    }

    // Log Error messages
    LogErrorMessages();
  }

  /**
   * A method to show the error messages
   */
  private void LogErrorMessages() {
    for (String message : errorList) {
      CommandObject.logErr(message);
    }
    // String outErrMsg = "";
    // for (String message : errorList) {
    // outErrMsg += message;
    // }
    // CommandObject.logErr(outErrMsg);
  }

  /**
   * A method to show given path is directory not file
   * 
   * @param path
   */
  private void addIsADirectory(Object path) {
    errorList.add("cat: " + path + " is a directory.");
  }

  /**
   * A method to show that given path is not exist
   * 
   * @param path
   */
  private void addFileNotLocated(Object path) {
    errorList.add("cat: File: " + path + "\n cannot be located!");
  }

  /**
   * A method to print out the manual for cat
   */
  @Override
  public void getManual() {
    // TODO Auto-generated method stub
    CommandObject.logLine("This command prints all content in files/ one"
        + " file, given in one or more argument."
        + "\n If multiple files were given, those contents will be displayed,"
        + " concatenated in the shell, separated by three line breaks");
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length != 0
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    return arg.length != 0;
  }

  /**
   * A method to return content of a file
   * 
   * @param file a ControllableFile object
   * @return file.getContent()
   */
  public byte[] fileContent(ControllableFile file) {
    return file.getContent();
  }

}
