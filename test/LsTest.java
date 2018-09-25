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
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import command.Cat;
import command.Ls;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;

/*
 * This is the test for LS For the similarity of LS and Cat, we can use the same
 * idea
 */
public class LsTest {
  Ls l;
  Cat c;
  ControllableFile file;

  class MockFileSystem extends ManagementOfContainerKernel {
  }

  @Before
  public void setUp() {
    c = new Cat();
    ControllableDirectory d = new ControllableDirectory();
    file = new ControllableFile("a", d, "123");
  }

  @Test
  public void testExecute() {
    String actualOutput = new String(c.fileContent(file));
    String expectation = "123";
    assertEquals(expectation, actualOutput);
  }

}
