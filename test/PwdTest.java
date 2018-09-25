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
import org.junit.Before;
import org.junit.Test;
import command.History;
import command.Pwd;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import test.EchoTest.MockFileSystem;

public class PwdTest {
  ManagementOfContainerKernel mock;
  Pwd pwd;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Test
  public void testExecute() {
    mock = new ManagementOfContainerKernel();
    pwd = new Pwd();
    System.setOut(new PrintStream(outContent));
    pwd.execute(mock, new Object[] {});
    String actualOutput = outContent.toString();
    String expectation = "/\n";
    assertEquals(expectation, actualOutput);
  }
}
