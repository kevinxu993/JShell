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
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the find command, whose parent class is Command
 */
public class Find implements Command {

  private ArrayList<String> ErrorList = new ArrayList<String>();

  /**
   * A method to execute the find command
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel MOCK, Object[] arg) {
    // Convert it to Array List for better methods
    ArrayList<Object> ALarg = new ArrayList<Object>();
    for (Object item : arg) {
      ALarg.add(item);
    }
    int ALARGsize = ALarg.size();
    // TODO: get the paths and do the finding job
    ArrayList<ControllableDirectory> TargetDirectories = new ArrayList<>();
    for (int i = 0; i < ALARGsize - 4; ++i) {
      try {
        TargetDirectories.add((ControllableDirectory) MOCK
            .getAbsolutePathOf((String) ALarg.get(i)));
      } catch (NoSuchFileExistException e) {
        addFileNotLocated(ALarg.get(i));
      }
    }
    // Get required file name and type
    String fileName = (String) ALarg.get(ALarg.size() - 1);
    fileName = fileName.substring(1, fileName.length() - 1);
    boolean typeIsFile = (((String) ALarg.get(ALarg.size() - 3)).equals("f"));
    // Another array for files/directories
    int TDsize = TargetDirectories.size();
    ArrayList<Integer> IndexList = new ArrayList<>();
    addExistIndex(MOCK, TargetDirectories, fileName, typeIsFile, TDsize,
        IndexList);
    for (int index : IndexList) {
      // For each search result, log the path
      CommandObject.logLine(MOCK.getDirectoryPath(TargetDirectories.get(index))
          + fileName + (typeIsFile ? "" : "/"));
    }
    addNotFountMessage(IndexList);

    // Log Error messages
    logErrorMessages();
  }

  /**
   * @param MOCK MOCK instance
   * @param TargetDirectories Target directories
   * @param fileName wanted file name
   * @param typeIsFile if file is required, then true. otherwise false.
   * @param TDsize TargetDirectories' size
   * @param IndexList
   */
  private void addExistIndex(ManagementOfContainerKernel MOCK,
      ArrayList<ControllableDirectory> TargetDirectories, String fileName,
      boolean typeIsFile, int TDsize, ArrayList<Integer> IndexList) {
    for (int ii = 0; ii < TDsize; ++ii) {
      if (TargetDirectories.get(ii).getFileNames(MOCK).contains(fileName)) {
        try {
          // If we can find the file and find out the type is correct
          if (MOCK
              .findFile(TargetDirectories.get(ii).getFileUUID(MOCK, fileName))
              .isDirectory() != typeIsFile) {
            IndexList.add(ii);
          }
        } catch (NoSuchFileExistException e) {
          // If we cannot, just do nothing
        }
      }
    }
  }

  /**
   * @param IndexList
   */
  private void addNotFountMessage(ArrayList<Integer> IndexList) {
    if (IndexList.size() == 0) {
      ErrorList.add("find: No result!");
    }
  }

  /**
   * A method to print all the errors
   */
  private void logErrorMessages() {
    for (String Message : ErrorList) {
      CommandObject.logErr(Message);
    }
  }

  private boolean detectInvalid(ArrayList<Object> ALarg) {
    // Multiple returns to reduce consumption
    String fileName = "";
    String dashName = "";
    String dashType = "";
    String typee = "";
    // If there are not even enough
    if (ALarg.size() < 5) {
      return true;
    }
    // If -type and -name are not existing
    if (!(ALarg.contains("-type") && ALarg.contains("-name"))) {
      return true;
    }
    try {
      // Assign them
      fileName = (String) ALarg.get(ALarg.size() - 1);
      dashName = (String) ALarg.get(ALarg.size() - 2);
      typee = (String) ALarg.get(ALarg.size() - 3);
      dashType = (String) ALarg.get(ALarg.size() - 4);
      // If error occurred, then it must be wrong
    } catch (Exception e) {
      return true;
    }
    // If given path does not look like a directory
    if (!(fileName.startsWith("\"") && fileName.endsWith("\""))) {
      return true;
    } else if (!(fileName.startsWith("\"") && fileName.endsWith("\""))) {
      return true;
      // if parameters are not correct
    } else if (!dashName.equals("-name") || !dashType.equals("-type")
        || !(typee.equals("f") || typee.equals("d"))) {
      return true;
    }
    return false;
  }


  /**
   * A method to print documentation for the tree command.
   */
  @Override
  public void getManual() {
    CommandObject.logLine("This command lists directories/files' path that"
        + " have the name exactly given.");
    CommandObject.logLine("This command is used as below: ");
    CommandObject.logLine("find <path> -type <type> -name <name>");
    CommandObject
        .logLine("Where <path> is path/ paths that are going to be searched, ");
    CommandObject
        .logLine("<type> is f or d, f means file and d means directory, ");
    CommandObject.logLine("<name> is the name, which is a string"
        + " surrounded by double quotation marks. ");
    CommandObject
        .logLine("Missing any of those arguments will not run find command");
  }

  /**
   * A method to add the error, file is not located
   * 
   * @param Path
   */
  private void addFileNotLocated(Object Path) {
    ErrorList.add("find: File:" + Path + "\n cannot be located!");
  }

  /**
   * A method to check the input format
   * 
   * @param String[] arg represent the input string
   * @return !detectInvalid(ALarg)
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // Declare a new storage space
    ArrayList<Object> ALarg = new ArrayList<Object>();
    for (Object item : arg) {
      ALarg.add(item);
    }
    return !detectInvalid(ALarg);
  }

}
