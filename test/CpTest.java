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
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import command.Cp;
import command.Mkdir;
import fileSystem.ManagementOfContainerKernel;

public class CpTest {

  // MockFileSystem mock;
  private ManagementOfContainerKernel mock;
  private Cp cpCommand = new Cp();
  Mkdir mkdirCommand = new Mkdir();
  // private MockFileSystem mock;

  @BeforeEach
  void setUp() throws Exception {
    mock = new ManagementOfContainerKernel();
  }

  @Test
  void testExecute1() {
    String inputURL1 = "a";
    String inputURL2 = "b";
    mkdirCommand.execute(mock, new Object[] {inputURL1});
    mkdirCommand.execute(mock, new Object[] {inputURL2});
    cpCommand.execute(mock, new Object[] {"a", "b"});
    // boolean expectation = false;
    boolean expectation = true;
    boolean actual = mock.getWorkingDir().getFileNames(mock).contains("b");
    assertEquals(expectation, actual);
  }

}
