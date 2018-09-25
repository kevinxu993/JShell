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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

class CDirectoryTest {


  @Test
  void testControllableDirectory() {
    String testName1 = "test1";
    ControllableDirectory newD = new ControllableDirectory(testName1, null);
    assert (newD.getName().equals(testName1));
    assert (newD.getParent() == null);
    String testName2 = "test2";
    ControllableDirectory newD2 = new ControllableDirectory(testName2, newD);
    assert (newD2.getName().equals(testName2));
    assert (newD2.getParent() == newD);
  }

  @Test
  void testIsDirectory() {
    ControllableDirectory newD = new ControllableDirectory("test1", null);
    assert (newD.isDirectory() == true);
    ControllableFile newD2 = new ControllableDirectory("test2", null);
    assert (newD2.isDirectory() == true);
  }

  @Test
  void testGetFileUUID() {
    ManagementOfContainerKernel MOCK = new ManagementOfContainerKernel();
    String testName1 = "test1";
    ControllableDirectory newD = createNewDirectory(MOCK, testName1);
    MOCK.setWorkingDir(newD);
    String testName2 = "test2";
    ControllableDirectory newD2 = createNewDirectory(MOCK, testName2);
    try {
      assert (newD.getFileUUID(MOCK, testName2).equals(newD2.getUUID()));
    } catch (NoSuchFileExistException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      fail("testGetFileUUID failed!");
    }
  }

  @Test
  void testGetFileNames() {
    ManagementOfContainerKernel MOCK = new ManagementOfContainerKernel();
    String testName1 = "test1";
    ControllableDirectory newD = createNewDirectory(MOCK, testName1);
    MOCK.setWorkingDir(newD);
    String testName2 = "test2";
    ControllableDirectory newD2 = createNewDirectory(MOCK, testName2);
    assert (MOCK.getRoot().getFileNames(MOCK).get(0).equals(newD.getName()));
    assert (MOCK.getRoot().getFileNames(MOCK).size() == 1);
    assert (newD.getFileNames(MOCK).get(0).equals(newD2.getName()));
    assert (newD.getFileNames(MOCK).size() == 1);

    assert (newD2.getFileNames(MOCK).size() == 0);
  }

  /**
   * @param mock
   * @param testDirectoryName1
   * @param newDirectory
   * @return
   */
  private ControllableDirectory createNewDirectory(
      ManagementOfContainerKernel mock, String testDirectoryName1) {
    ControllableDirectory newDirectory = null;
    try {
      newDirectory = mock.createDirectoryUnderWD(testDirectoryName1);
    } catch (FileWithSameNameExistedException | InvalidFileNameException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      fail("testCreateDirectoryUnderWD failed!");
    }
    assert (newDirectory.getName().equals(testDirectoryName1));
    assert (newDirectory.getParent() == mock.getWorkingDir());
    return newDirectory;
  }

}
