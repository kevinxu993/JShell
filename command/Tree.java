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

import fileSystem.*;

/**
 * Represents the tree command, whose parent class is Command
 */
public class Tree implements Command {

  /**
   * A method to execute the tree command, From the root directory ('\') display
   * the entire file system as a tree.
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // print the root directory
    CommandObject.logLine("\\");
    // get the root directory
    ControllableDirectory root = mock.getRoot();
    // call recurPrintTree recursive method to print out the tree
    recurPrintTree(mock, root, 1);
  }

  /**
   * A recursive method to display the entire file system as a tree, from the
   * directories under the root
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param directory an ControllableDirectory object
   * @param tab an int representing the how many tabs we need
   */
  private void recurPrintTree(ManagementOfContainerKernel MOCK,
      ControllableDirectory directory, int tab) {
    // check all the directories and files on the same level
    for (String filename : directory.getFileNames(MOCK)) {
      // create a ControllableFile object
      ControllableFile file;
      try {
        // let file be first file found under this directory
        file = MOCK.findFile(directory.getFileUUID(MOCK, filename));
        // create an int equal to the number of tabs
        int i = tab;
        // while we still need tabs
        while (i >= 1) {
          // print a tab space
          CommandObject.log("\t");
          // decrease the number of tabs by 1
          i--;
        }
        // print out the name of the directory or file
        CommandObject.logLine(filename);
        // if a directory is found under current directory
        if (file.isDirectory()) {
          // call this method on the directory and increase the number of tabs
          // by 1
          recurPrintTree(MOCK, (ControllableDirectory) file, tab + 1);
        }
      } catch (NoSuchFileExistException e) {
        // this won't happen anyway
      }
    }
  }

  /**
   * A method to print documentation for the tree command.
   */
  @Override
  public void getManual() {
    // print documentation for the tree command.
    CommandObject.logLine(man());
  }

  public String man() {
    return "From the root directory ('\') display the entire file system as a "
        + "tree.\n-For instance if the root directory contains two "
        + "subdirectories as 'A' and 'B', then it will display\n\\\n\tA"
        + "\n\tB\n-For instance if the root directory contains two sub "
        + "directories as 'A', 'B', 'C' and 'A' in turn contains 'A1' and "
        + "'A2', then it will display\n\\\n\tA\n\t\tA1\n\t\tA2\n\tB"
        + "\n\tC\n-When the user types in 'tree' and the only directory "
        + "present is the root directory, then it simply shows the root "
        + "directory\n\\";
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return arg.length == 0
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    // if there are arguments then it must be wrong
    return arg.length == 0;
  }
}
