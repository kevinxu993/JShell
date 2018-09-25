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
import org.junit.jupiter.api.BeforeEach;
import command.Echo;
import command.Mkdir;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import test.CurlTest.MockControllableFile;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.NoSuchFileExistException;

public class EchoTest {
  class MockControllableFile extends ControllableFile {

  }

  class MockFileSystem extends ManagementOfContainerKernel {
      Map<String, String> map;

      public MockFileSystem() {
          map = new HashMap<String, String>();
      }

      public MockControllableFile createFileUnderWD(String fileName, String content) {
          map.put(fileName, content);
          return null;
      }

      public boolean checkFileExist(String fileName) {
          return map.containsKey(fileName);
      }

      public String getFileContent(String fileName) {
          return map.get(fileName);
      }
  }

  // MockFileSystem mock;
  private ManagementOfContainerKernel mock;
  private Echo EchoCommand = new Echo();
  //private MockFileSystem mock;

  @BeforeEach
  void setUp() throws Exception {
      mock = new ManagementOfContainerKernel();
  }

  @Test
    void testExecute1() {
      String inputURL = "a";
      EchoCommand.execute(mock, new Object[] {inputURL});
      boolean expectation = false;
      boolean actual = mock.equals("a");
      assertEquals(expectation, actual);
    }
}
