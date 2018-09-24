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
import java.util.UUID;
import java.util.regex.Pattern;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

public class Grep implements Command {
  ArrayList<String> errors = new ArrayList<>();

  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // Local variables
    boolean recursively = false;
    int endOfRegexIndex = determineEndOfRegex(arg);
    ArrayList<String> results = new ArrayList<>();
    String regex = "";
    // Loop through all arguments
    for (int i = 0; i < arg.length; ++i) {
      String path = (String) arg[i];
      // Detect recursive
      if (i == 0) {
        recursively = path.equals("-R");
        // Not recursive? then it should be regex
        if (!recursively) {
          if (path.startsWith("\""))
            regex += path.substring(1);
          else {
            errors.add("grep: Regular expression incorrect!");
            break;
          }
        }
        // If recursive then this must be regex
      } else if (i == 1 && recursively) {
        if (path.startsWith("\""))
          regex += path.substring(1);
        else {
          errors.add("grep: Regular expression incorrect!");
          break;
        }
        // Normal situation
      } else if (i > endOfRegexIndex) {
        try {
          ControllableFile targetFile = mock.getAbsolutePathOf(path);
          fileOpreations(mock, recursively, results, regex, targetFile);
        } catch (NoSuchFileExistException e) {
          // File not located
          errors.add(path + " is not a valid path.");
        }
      }
      // when this argument is still a part of regex, where i < @endOfRegexIndex
      else {
        regex += path;
      }
      // When at end of regex index
      if (i == endOfRegexIndex) {
        regex = regex.substring(0, regex.length() - 1);
      }
    }
    for (String string : results) {
      CommandObject.logLine(string);
    }
    if (errors.size() > 0) {
      CommandObject.logLine("");
      for (String string : errors) {
        CommandObject.logLine(string);
      }
    }
  }

  /**
   * @param arg the arguments
   * @return the end index of regex
   */
  private int determineEndOfRegex(Object[] arg) {
    int endOfRegexIndex = 0;
    for (int i = arg.length - 1; i >= 0; --i) {
      String path = (String) arg[i];
      if (path.endsWith("\"")) {
        endOfRegexIndex = i;
        break;
      }
    }
    return endOfRegexIndex;
  }

  /**
   * @param mock Management of Container Kernel instance
   * @param recursively if recursive was required
   * @param results the result arrayList
   * @param regex the regular expression
   * @param targetFile the file that is to be determined
   */
  private void fileOpreations(ManagementOfContainerKernel mock,
      boolean recursively, ArrayList<String> results, String regex,
      ControllableFile targetFile) {
    // Recursive & directory
    if (targetFile.isDirectory() && recursively)
      results
          .addAll(recursing((ControllableDirectory) targetFile, regex, mock));
    else if (targetFile.isDirectory()) {// Not recursive
      errors.add(targetFile.getName() + " is not a file.");
    } else if (recursively) {// Not directory
      errors.add(targetFile.getName() + " is not a directory.");
    } else {// Non-recursive * file
      results.addAll(matching(targetFile, regex));
    }
  }

  /**
   * for recursive searching
   * 
   * @param targetDirectory target directory that will be recursived
   * @param regex the regex
   * @param mock the Management of Container kernel file system
   * @return an arraylist containing all lines from the file that match regex,
   *         recursively found from given directory
   */
  private ArrayList<String> recursing(ControllableDirectory targetDirectory,
      String regex, ManagementOfContainerKernel mock) {
    ArrayList<String> results = new ArrayList<>();
    for (UUID uuid : targetDirectory.getFileUUIDs()) {
      ControllableFile theFile = mock.findFile(uuid);
      if (theFile.isDirectory()) {
        results.addAll(recursing((ControllableDirectory) theFile, regex, mock));
      } else {
        results.add(mock.getDirectoryPath(theFile.getParent())
            + theFile.getName() + ":");
        results.addAll(matching(theFile, regex));
      }
    }
    return results;
  }

  /**
   * for outputing file contents that match regex
   * 
   * @param file the file that will be searched
   * @param regex the regular expression
   * @return an arraylist containing all lines from the file that match regex
   */
  private ArrayList<String> matching(ControllableFile file, String regex) {
    ArrayList<String> results = new ArrayList<>();
    Pattern p = Pattern.compile(regex);
    String[] lines = new String(file.getContent()).split("\n");
    for (String line : lines) {
      if (p.matcher(line).find()) {
        results.add(line);
      }
    }
    return results;
  }


  @Override
  public void getManual() {
    CommandObject.logLine("This method is for finding lines in given file/files"
        + " that matches given regex in a string, surrounded by \" marks. ");
    CommandObject.logLine("If -R is provided, the finding will be recursive"
        + " for pathes that are directories, and warn for normal files. ");
    CommandObject.logLine("Usage: ");
    CommandObject.logLine("grep \"regex\" file file");
    CommandObject.logLine("grep \"regex\" file");
    CommandObject.logLine("grep -R \"regex\" directory");
    CommandObject.logLine("grep -R \"regex\" directory directory");
  }

  @Override
  public boolean checkArgFormat(String[] arg) {
    return (arg.length >= 2 && !arg[0].equals("-R"))
        || (arg.length >= 3 && arg[0].equals("-R"));
  }

}
