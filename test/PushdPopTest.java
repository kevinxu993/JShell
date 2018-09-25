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
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import command.CommandObject;
import command.Curl;
import command.Popd;
import command.Pushd;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;
import test.CurlTest.MockControllableFile;
import test.CurlTest.MockFileSystem;

class PushdPopTest {

  class mockControllableDirectory extends ControllableDirectory {
    private String dir = "/";

    public mockControllableDirectory(String s) {
      dir = s;
    }

    public String getDir() {
      return dir;
    }
  }

  class mockControllableFile extends ControllableFile {
    private String dir = "/";

    public mockControllableFile(String s) {
      dir = s;
    }
  }

  class MockFileSystem extends ManagementOfContainerKernel {
    mockControllableDirectory wd;
    String initDir = "a";

    public MockFileSystem() {
      wd = new mockControllableDirectory(initDir);
    }

    public mockControllableDirectory getWorkingDir() {
      return wd;
    }

    public String getDirectoryPath(mockControllableDirectory directory) {
      return wd.getDir();
    }

    public mockControllableDirectory getAbsolutePathOf(String path)
        throws NoSuchFileExistException {
      return new mockControllableDirectory(wd.getDir());
    }

    public void setWorkingDir(mockControllableDirectory directoryInstance) {
      wd = new mockControllableDirectory(directoryInstance.getDir());
    }
  }

  // MockFileSystem mock;
  // private CommandObject cmdObj = new CommandObject();
  private Popd popd = new Popd();
  private Pushd pushd = new Pushd();
  private MockFileSystem mock;

  @BeforeEach
  void setUp() throws Exception {
    mock = new MockFileSystem();
  }

  @Test
  void testPushSingle() {
    String inputPath = "a";
    pushd.execute(mock, new Object[] {inputPath});
    String expectation = "a";
    String actual = mock.getWorkingDir().getDir();
    // System.out.println(actual);
    assertEquals(expectation, actual);
  }

  @Test
  void testPushMore() {
    String inputPath = "e";
    pushd.execute(mock, new Object[] {inputPath});
    inputPath = "b";
    pushd.execute(mock, new Object[] {inputPath});
    inputPath = "a";
    pushd.execute(mock, new Object[] {inputPath});
    String expectation = "a";
    String actual = mock.getWorkingDir().getDir();
    assertEquals(expectation, actual);
  }


  @Test
  void testPushPop1() {
    String inputPath = "a";
    pushd.execute(mock, new Object[] {inputPath});
    popd.execute(mock, new Object[] {});
    String expectation = "a";
    String actual = mock.getWorkingDir().getDir();
    assertEquals(expectation, actual);
  }

  @Test
  void testPushPop2() {
    String inputPath = "a";
    pushd.execute(mock, new Object[] {inputPath});
    inputPath = "b";
    pushd.execute(mock, new Object[] {inputPath});
    popd.execute(mock, new Object[] {});
    String expectation = "a";
    String actual = mock.getWorkingDir().getDir();
    assertEquals(expectation, actual);
  }

  @Test
  void testPop1() {
    popd.execute(mock, new Object[] {});
    String expectation = "a";
    String actual = mock.getWorkingDir().getDir();
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckArgFormatPushd1() {
    boolean expectation = true;
    boolean actual = pushd.checkArgFormat(new String[] {""});
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckArgFormatPushd2() {
    boolean expectation = false;
    boolean actual = pushd.checkArgFormat(new String[] {"", ""});
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckArgFormatPopd1() {
    boolean expectation = false;
    boolean actual = popd.checkArgFormat(new String[] {"", ""});
    assertEquals(expectation, actual);
  }

  @Test
  void testCheckArgFormatPopd2() {
    boolean expectation = true;
    boolean actual = popd.checkArgFormat(new String[] {});
    assertEquals(expectation, actual);
  }


}
