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
import command.Exclaim;
import command.History;
import command.Mkdir;
import command.Pwd;
import fileSystem.ManagementOfContainerKernel;

public class ExclaimTest {
  Exclaim exclaim;
  ManagementOfContainerKernel mock;
  History history;
  Pwd pwd;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() {
    exclaim = new Exclaim();
    mock = new ManagementOfContainerKernel();
    history = new History();
    pwd = new Pwd();
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testExecute() {
    pwd.execute(mock, new Object[] {});
    history.execute(mock, new Object[] {});
    exclaim.execute(mock, new Object[] {"1"});
    String actualOutput = outContent.toString();
    String expectation = "/:";
    assertEquals(expectation, actualOutput);

  }

  @Test
  public void testCheckArgFormat1() {
    String[] arg = {"1"};
    boolean actualOutput = exclaim.checkArgFormat(arg);
    boolean expectation = true;
    assertEquals(expectation, actualOutput);
  }

  @Test
  public void testCheckArgFormat2() {
    String[] arg = {"a"};
    boolean actualOutput = exclaim.checkArgFormat(arg);
    boolean expectation = false;
    assertEquals(expectation, actualOutput);
  }
}
