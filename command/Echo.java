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
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the echo command, whose parent class is Command
 */
public class Echo implements Command {

  /**
   * A method to execute the echo command
   * 
   * If OUTFILE is not provided, print STRING on the shell. Otherwise, put
   * STRING into file OUTFILE. STRING is a string of characters surrounded
   * Remove the top entry from the directory stack, and cd into it. The removal
   * is consistent as per the LIFO behavior of a stack. The popd command removes
   * the top most directory from the directory stack and makes it the current
   * working directory. If there is no directory onto the stack, then give
   * appropriate error message.
   * 
   * by double quotation marks. This creates a new file if OUTFILE does not
   * exists and erases the old contents if OUTFILE already exists. In either
   * case, the only thing in OUTFILE should be STRING.
   * 
   * echo STRING >> OUTFILE: appends instead of overwrites.
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel MOCK, Object[] arg) {
    // Determine if the input is being redirected by > or >>
    boolean redirected = false;
    // Determine if the input needs to be appended
    boolean append = false;
    // If the first argument does not start with " it must be wrong
    if (!((String) arg[0]).startsWith("\"")) {
      logInvalidInput();
      return;
    }
    // Declare a new storage space
    ArrayList<String> newArg = new ArrayList<String>();
    for (Object item : arg) {
      newArg.add((String) item);
    }

    // Index of redirection symbol. if this did not exist then we use default
    int i = arg.length - 2;
    if (arg.length > 1) { // If there is only one argument then it is default
      // If redirection located
      if (arg[i].equals(">")) {
        if (!redirected)
          redirected = true;
        else {
          logInvalidInput();
          return;
        }
      } else if (arg[i].equals(">>")) {
        if (!redirected)
          redirected = true;
        else {
          logInvalidInput();
          return;
        }
        append = true;
      }
    }
    // If the string was not end by ", terminate
    if (!((String) arg[redirected ? i - 1 : i + 1]).endsWith("\"")) {
      logInvalidInput();
      return;
    }

    String content = String.join(" ",
        newArg.subList(0, redirected ? newArg.size() - 2 : newArg.size()));
    content = content.substring(1, content.length() - 1);



    if (redirected) {
      // Make sure line switch
      content = (append ? "\n" : "") + content;
      String path = (String) arg[arg.length - 1];
      ControllableFile targetFile = null;
      String theDirectoryPath = ((String) path).contains("/")
          ? ((String) path).substring(0, ((String) path).lastIndexOf("/")) + "/"
          : MOCK.getDirectoryPath(MOCK.getWorkingDir());


      ControllableDirectory theDirectoryInstace;
      try {
        theDirectoryInstace =
            (ControllableDirectory) MOCK.getAbsolutePathOf(theDirectoryPath);

        String fileName = ((String) path).contains("/")
            ? ((String) path).substring(((String) path).lastIndexOf("/") + 1)
            : ((String) path);


        ControllableDirectory tempWD = MOCK.getWorkingDir();
        MOCK.setWorkingDir(theDirectoryInstace);
        try {
          // If the file is not existing
          targetFile = MOCK.createFileUnderWD(fileName, content);
        } catch (FileWithSameNameExistedException e) {
          // If the file already exists
          targetFile =
              MOCK.findFile(theDirectoryInstace.getFileUUID(MOCK, fileName));
        } catch (InvalidFileNameException e) {
          // If the file name is not invalid
          logInvalidFileName(fileName);
          // Set the working directory back
          MOCK.setWorkingDir(tempWD);
          // Terminate
          return;
        }
        // Set the working directory back
        MOCK.setWorkingDir(tempWD);
      } catch (NoSuchFileExistException e1) {
        logPathNotValid(path);
      }
      // Get the file content in
      if (!targetFile.isDirectory())
        targetFile.setContent(MOCK.ConcatenatingTwoByteArray(
            (append ? targetFile.getContent() : "".getBytes()),
            content.getBytes()));
      else {
        // However, if target file is a directory
        logPathNotValid(path);
      }
    } else {
      CommandObject.logLine(content);
    }
  }

  /**
   * A method to show the given file name is invalid
   * 
   * @param fileName
   */
  private void logInvalidFileName(String fileName) {
    CommandObject.logErr("redirection: Invalid file name: " + fileName);
  }

  /**
   * A method to show that the path is invalid
   * 
   * @param path
   */
  private void logPathNotValid(String path) {
    CommandObject.logErr(
        "redirection: Argument error: Invalid Path for redirection: " + path);
  }

  /**
   * A method to get the manual of echo
   */
  @Override
  public void getManual() {
    String doc = "Print the content of an string in JShell, surrounded by \"\","
        + "\nIf redirection is given, the string will not be printed, "
        + "\nbut append/write to redirected output";
    CommandObject.logLine(doc);
  }

  /**
   * A method to show the input arguement is invalid
   */
  private void logInvalidInput() {
    CommandObject.logErr("echo: Argument exception: Invalid Arguments!");
  }

  /**
   * A method to check the input, in this case is all true
   * 
   * @param arg an String[] containing the arguments after the command
   * @return true
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // It's too hard moving the argument checking around so I will just true
    return true;
  }

}
