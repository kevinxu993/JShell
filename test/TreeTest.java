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

package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import command.Mkdir;
import command.Tree;
import fileSystem.ManagementOfContainerKernel;

public class TreeTest {
  Tree tree;
  ManagementOfContainerKernel mock;
  Mkdir mkdir;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() {
    tree = new Tree();
    mock = new ManagementOfContainerKernel();
    mkdir = new Mkdir();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testExecute() {
    mkdir.execute(mock, new Object[] {"A"});
    mkdir.execute(mock, new Object[] {"B"});
    tree.execute(mock, new Object[] {});
    String actualOutput = outContent.toString();
    String expectation = "\\\n\tA\n\tB\n";
    assertEquals(expectation, actualOutput);
  }

  @Test
  public void testMan() {
    String actualOutput = tree.man();
    String expectation =
        "From the root directory ('\') display the entire file system as a "
            + "tree.\n-For instance if the root directory contains two "
            + "subdirectories as 'A' and 'B', then it will display\n\\\n\tA"
            + "\n\tB\n-For instance if the root directory contains two sub "
            + "directories as 'A', 'B', 'C' and 'A' in turn contains 'A1' and "
            + "'A2', then it will display\n\\\n\tA\n\t\tA1\n\t\tA2\n\tB"
            + "\n\tC\n-When the user types in 'tree' and the only directory "
            + "present is the root directory, then it simply shows the root "
            + "directory\n\\";
    assertEquals(expectation, actualOutput);
  }

  @Test
  public void testCheckArgFormat1() {
    String[] arg = {""};
    boolean actualOutput = tree.checkArgFormat(arg);
    boolean expectation = false;
    assertEquals(expectation, actualOutput);
  }

  @Test
  public void testCheckArgFormat2() {
    String[] arg = {};
    boolean actualOutput = tree.checkArgFormat(arg);
    boolean expectation = true;
    assertEquals(expectation, actualOutput);
  }

}
