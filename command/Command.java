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

import fileSystem.ManagementOfContainerKernel;

/**
 * Represents the abstract class Command
 */
// public abstract class Command {
public interface Command {
  /**
   * execute method that's force the sub-class have same method
   * 
   * @param ManagementOfContainerKernel that's save the all the variable and
   *        path
   * @param Object[] that's represents the input arguments
   * @return none
   */
  public void execute(final ManagementOfContainerKernel mock,
      final Object[] arg);

  /**
   * getManual method that's force the sub-class have same method
   * 
   * @param none
   * @return none
   */
  public void getManual();

  /**
   * checkArgFormat method that's force the sub-class have same method
   * 
   * @param String[] that's represents the input arguments
   * @return boolean taht's represent the correctness of the arguments
   */
  public boolean checkArgFormat(final String[] arg);

}
