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

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import command.CommandObject;
import fileSystem.ControllableFile;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;


/**
 * @author songzhif
 */
class RedirectionTest {
  // MockFileSystem mock;
  private MockCommandObject cmdObj = new MockCommandObject();
  private MockFileSystem mock;

  class MockControllableFile extends ControllableFile {

  }

  class MockFileSystem extends ManagementOfContainerKernel {
    Map<String, String> map;

    public MockFileSystem() {
      map = new HashMap<String, String>();
    }

    public MockControllableFile createFileUnderWD(String fileName,
        String content) {
      map.put(fileName, content);
      // System.out.println(map.get("dog"));
      return null;
    }

    public boolean checkFileExist(String fileName) {
      return map.containsKey(fileName);
    }

    public String getFileContent(String fileName) {
      return map.get(fileName);
    }
  }

  class MockCommandObject extends CommandObject {
    public void runRedirect(final String command, final Object[] arg,
        String path, Boolean overwrite, ManagementOfContainerKernel mock) {
      try {
        if (command.equals("mockCmd") && arg.length == 1)
          mock.createFileUnderWD(path, "correct");
      } catch (FileWithSameNameExistedException e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
      } catch (InvalidFileNameException e) {
        // TODO Auto-generated catch block
        // e.printStackTrace();
      }
      if (arg.length == 0) {
        cmdObj.runRedirect(command, arg, path, overwrite);
      }
      //
    }
  }



  @BeforeEach
  void setUp() throws Exception {
    mock = new MockFileSystem();
  }

  @Test
  void testRunRedirectFileBeenCreate1() {
    String mockCmd = "mockCmd";
    Object[] mockArg = new Object[] {""};
    String mockPath = "mockName";
    Boolean overwrite = true;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    boolean expectation = true;
    boolean actual = mock.checkFileExist("mockName");
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileBeenCreate2() {
    String mockCmd = "mockCmd";
    Object[] mockArg = new Object[] {"", ""};
    String mockPath = "mock";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    boolean expectation = false;
    boolean actual = mock.checkFileExist("mockName");
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileBeenCreate3() {
    String mockCmd = "mockCmd";
    Object[] mockArg = new Object[] {"2"};
    String mockPath = "mockName";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    boolean expectation = true;
    boolean actual = mock.checkFileExist("mockName");
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileBeenCreate4() {
    String mockCmd = "notExistCommand!!!!!!";
    Object[] mockArg = new Object[] {"2", "3"};
    String mockPath = "mockName";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    boolean expectation = false;
    boolean actual = mock.checkFileExist("mockName");
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileBeenCreateWithBadCommand1() {
    String mockCmd = "mockBadCmd";
    Object[] mockArg = new Object[] {"", "", 3};
    String mockPath = "mockName";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    boolean expectation = false;
    boolean actual = mock.checkFileExist("mockName");
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileBeenCreateWithBadArgument1() {
    String mockCmd = "mockCmd";
    Object[] mockArg = new Object[] {"", ""};
    String mockPath = "mockName";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    boolean expectation = false;
    boolean actual = mock.checkFileExist("mockName");
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileContent1() {
    String mockCmd = "mockCmd";
    Object[] mockArg = new Object[] {"2"};
    String mockPath = "mockName";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    String expectation = "correct";
    String actual = mock.getFileContent("mockName");
    // System.out.println(actual);
    assertEquals(expectation, actual);
  }

  @Test
  void testRunRedirectFileContent2() {
    String mockCmd = "mockCmd";
    Object[] mockArg = new Object[] {"2"};
    String mockPath = "mockName";
    Boolean overwrite = false;

    cmdObj.runRedirect(mockCmd, mockArg, mockPath, overwrite, mock);
    String expectation = "not correct content";
    String actual = mock.getFileContent("mockName");
    assert (!expectation.equals(actual));
    // assertNotEquals(expectation,actual);
  }

}
