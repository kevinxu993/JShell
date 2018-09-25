package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import command.CommandObject;

// public class MockCommandObject {
// // create a static ArrayList to store all the inputs
// public static ArrayList<String> cmdList = new ArrayList<String>();
// }

class CommandObjectTest {
  CommandObject cmdObj;
  ArrayList<String> cmdList;
  ArrayList<Object[]> exCmdList;
  ArrayList<String> dirStack;

  @BeforeEach
  void setUp() throws Exception {
    cmdObj = new CommandObject();
    cmdList = new ArrayList<String>();
    exCmdList = new ArrayList<Object[]>();
    dirStack = new ArrayList<String>();
  }


  @Test
  void testFindClassName1() {
    String input = "ok";
    String expectation = "command.Ok";
    String actual = cmdObj.findClassName(input);
    assertEquals(expectation, actual);
  }

  @Test
  void testFindClassName2() {
    String input = "k";
    String expectation = "command.K";
    String actual = cmdObj.findClassName(input);
    assertEquals(expectation, actual);
  }

  @Test
  void testFindClassName3() {
    String input = "e4";
    String expectation = "command.E4";
    String actual = cmdObj.findClassName(input);
    assertEquals(expectation, actual);
  }

  @Test
  void testAddGetToCmdList1() {
    cmdObj.addToCmdList("a");
    cmdObj.addToCmdList("b");
    boolean expectation = true;
    boolean acutal =
        cmdObj.getCmdList().contains("a") && cmdObj.getCmdList().contains("b");
  }

  @Test
  void testAddGetToCmdList2() {
    cmdObj.addToCmdList("1");
    boolean expectation = true;
    boolean acutal = cmdObj.getCmdList().contains("1");
  }

  @Test
  void testAddGetToCmdList3() {
    cmdObj.addToCmdList("1");
    boolean expectation = false;
    boolean acutal = cmdObj.getCmdList().contains("a");
  }


  @Test
  void testSetGetCmdList1() {
    ArrayList<String> inputCmdlist = new ArrayList<String>() {
      {
        add("A");
        add("B");
        add("C");
      }
    };
    cmdObj.setCmdList(inputCmdlist);
    boolean expectation = true;
    boolean acutal = cmdObj.getCmdList() == inputCmdlist;
  }

  @Test
  void testSetGetCmdList2() {
    ArrayList<String> inputCmdlist = new ArrayList<String>() {
      {
        add("A");
      }
    };
    cmdObj.setCmdList(inputCmdlist);
    boolean expectation = true;
    boolean acutal = cmdObj.getCmdList() == inputCmdlist;
  }

  @Test
  void testSetGetCmdList3() {
    ArrayList<String> inputCmdlist = new ArrayList<String>() {
      {
      }
    };
    cmdObj.setCmdList(inputCmdlist);
    boolean expectation = true;
    boolean acutal = cmdObj.getCmdList() == inputCmdlist;
  }


  @Test
  void testAddGetToExCmdList1() {
    cmdObj.addToExCmdList(new Object[] {1, 2});
    boolean expectation = true;
    boolean acutal = cmdObj.getExCmdList(0) == new Object[] {1, 2};
  }

  @Test
  void testAddGetToExCmdList2() {
    cmdObj.addToExCmdList(new Object[] {1, 2});
    cmdObj.addToExCmdList(new Object[] {2, 3});
    boolean expectation = true;
    boolean acutal = cmdObj.getExCmdList(0) == new Object[] {1, 2};
  }

  @Test
  void testAddGetToExCmdList3() {
    cmdObj.addToExCmdList(new Object[] {1, 2});
    cmdObj.addToExCmdList(new Object[] {2, 3});
    boolean expectation = true;
    boolean acutal = cmdObj.getExCmdList(1) == new Object[] {2, 3};
  }

  @Test
  void testPushPopDirStack1() {
    cmdObj.pushDirStack("ok");
    String expectation = "ok";
    String actual = cmdObj.popDirStack();
    assertEquals(expectation, actual);
  }

  @Test
  void testPushPopDirStack2() {
    cmdObj.pushDirStack("a");
    cmdObj.pushDirStack("b");
    String expectation = "a";
    cmdObj.popDirStack();
    String actual = cmdObj.popDirStack();
    assertEquals(expectation, actual);
  }
}
