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
import java.lang.reflect.Field;
import javax.naming.InitialContext;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.OBJ_ADAPTER;
import command.CommandObject;
import driver.JShell;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

public class HistoryTest {

  CommandObject testCO;
  ManagementOfContainerKernel mock;

  @Before
  public void init() {
    testCO = new CommandObject();
    try {

      Field mockField =
          testCO.getClass().getDeclaredField("mock");
      mockField.setAccessible(true);
      mock = (ManagementOfContainerKernel) mockField.get(testCO);
    } catch (IllegalArgumentException | IllegalAccessException
        | NoSuchFieldException | SecurityException e) {
      fail("N/A");
    }
    testCO.addToCmdList("mkdir test1 test2");
    testCO.execute("Mkdir", new String[] {"test1", "test2"});
    testCO.addToCmdList("echo \"a\" > b.txt");
    testCO.execute("Echo", new String[] {"\"a\"", ">", "b.txt"});
  }

  @Test
  public void testExecuteRegular() {
    testCO.runRedirect("history", new String[] {}, "c.txt", true);
    System.out.println(mock.getRoot().getFileNames(mock));
    try {
      ControllableFile ctxt = mock.getAbsolutePathOf("/c.txt");
      System.out.println(new String(ctxt.getContent()));
      System.out.println("axe");
      assertEquals("1. mkdir test1 test2\r\n" + 
          "2. echo \"a\" > b.txt\r\n" + 
          "3. mkdir test1 test2\r\n" + 
          "4. echo \"a\" > b.txt",
          new String(ctxt.getContent()));
    } catch (NoSuchFileExistException e) {
      e.printStackTrace();
      fail("History: redirection failed");
    }
  }

  @Test
  public void testExecuteExceed() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public void testExecuteIrregular() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public void testGetManual() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public void testCheckArgFormat() {
    fail("Not yet implemented"); // TODO
  }

  @Test
  public void testLogErr() {
    fail("Not yet implemented"); // TODO
  }

}
