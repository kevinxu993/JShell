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

/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * @author wang_w571
 *
 */
class MOCKTest {

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#ManagementOfContainerKernel()}.
   */
  @Test
  void testManagementOfContainerKernel() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    assert (mock != null);
  }

  /**
   * Test method for {@link fileSystem.ManagementOfContainerKernel#getRoot()}.
   */
  @Test
  void testGetRoot() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    assert (mock.getRoot().getName() == "");
  }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#createFileUnderWD(java.lang.String, java.lang.String)}.
   */
  @Test
  void testCreateFileUnderWD() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    String testFileName1 = "test1";
    String testFileContent1 = "";
    createFile(mock, testFileName1, testFileContent1);
    String testFileName2 = "test2";
    String testFileContent2 = "test2";
    createFile(mock, testFileName2, testFileContent2);
    String testFileName3 = "test3";
    String testFileContent3 = "test3\ntest3";
    createFile(mock, testFileName3, testFileContent3);
    String sameFileName = "test3";
    String sameFileContent = "test3\ntest3";
    failedCreateFile(mock, testFileContent3, sameFileName);
    String illegalFileName1 = "test_3";
    String illegalFileName2 = "test 3";
    String illegalFileName3 = "";
    String illegalFileName4 = "TEST3636-+-+";
    failedCreateFile(mock, illegalFileName1, testFileContent1);
    failedCreateFile(mock, illegalFileName2, testFileContent1);
    failedCreateFile(mock, illegalFileName3, testFileContent1);
    failedCreateFile(mock, illegalFileName4, testFileContent1);
  }

  /**
   * @param mock
   * @param testFileContent3
   * @param fileName
   */
  private void failedCreateFile(ManagementOfContainerKernel mock,
      String testFileContent3, String fileName) {
    ControllableFile newFile = null;
    try {
      newFile = mock.createFileUnderWD(fileName, testFileContent3);
      fail("testCreateFileUnderWD failed!");
    } catch (FileWithSameNameExistedException | InvalidFileNameException e) {
    }
  }

  /**
   * @param mock
   * @param testFileName
   * @param testFileContent
   * @return
   */
  private ControllableFile createFile(ManagementOfContainerKernel mock,
      String testFileName, String testFileContent) {
    ControllableFile newFile = null;
    try {
      newFile = mock.createFileUnderWD(testFileName, testFileContent);
    } catch (FileWithSameNameExistedException | InvalidFileNameException e) {
      e.printStackTrace();
      fail("testCreateFileUnderWD failed!");
    }
    String actualFileName = newFile.getName();
    String actualFileContent = new String(newFile.getContent());
    assert (actualFileName.equals(testFileName));
    assert (actualFileContent.equals(testFileContent));
    return newFile;
  }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#findFile(java.util.UUID)}.
   */
  @Test
  void testFindFileUUID() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    String testFileName1 = "test1";
    String testFileContent1 = "";
    ControllableFile newFile =
        createFile(mock, testFileName1, testFileContent1);
    ControllableFile testFindFile = mock.findFile(newFile.getUUID());
    assert (testFindFile == newFile);
  }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#findFile(java.lang.String)}.
   */
  // @Test
  // void testFindFileString() {
  // ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
  // String testFileName1 = "test1";
  // String testFileContent1 = "";
  // ControllableFile newFile =
  // createFile(mock, testFileName1, testFileContent1);
  // String testFileName2 = "test2";
  // String testFileContent2 = "";
  // createFile(mock, testFileName2, testFileContent2);
  // ControllableFile testFindFile = mock.findFile(newFile.getName());
  // assert (testFindFile == newFile);
  // }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#createDirectoryUnderWD(java.lang.String)}.
   */
  @Test
  void testCreateDirectoryUnderWD() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    String testDirectoryName1 = "test1";
    ControllableDirectory newDirectory1 =
        createNewDirectory(mock, testDirectoryName1);
    String testDirectoryName2 = "test2";
    createNewDirectory(mock, testDirectoryName2);
    String testDirectoryName3 = "test3";
    createNewDirectory(mock, testDirectoryName3);
    String sameDirectoryName = "test1";
    failCreateDirectory(mock, sameDirectoryName);
    String illegalDirectoryName1 = "test 1";
    failCreateDirectory(mock, illegalDirectoryName1);
    String illegalDirectoryName2 = "test_1";
    failCreateDirectory(mock, illegalDirectoryName2);
    String illegalDirectoryName3 = "test!1";
    failCreateDirectory(mock, illegalDirectoryName3);
  }

  /**
   * @param mock
   * @param DirectoryName
   */
  private void failCreateDirectory(ManagementOfContainerKernel mock,
      String DirectoryName) {
    ControllableDirectory newDirectory = null;
    try {
      newDirectory = mock.createDirectoryUnderWD(DirectoryName);
      fail("testCreateFileUnderWD failed!");
    } catch (FileWithSameNameExistedException | InvalidFileNameException e) {
    }
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

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#setWorkingDir(fileSystem.ControllableDirectory)}.
   */
  @Test
  void testSetWorkingDir() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    String testDirectoryName1 = "test1";
    ControllableDirectory newDirectory1 =
        createNewDirectory(mock, testDirectoryName1);
    mock.setWorkingDir(newDirectory1);
    assert (mock.getWorkingDir() == newDirectory1);
  }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#getAbsolutePathOf(java.lang.String)}.
   */
  @Test
  void testGetAbsolutePathOf() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    String testDirectoryName1 = "test1";
    ControllableDirectory newDirectory1 =
        createNewDirectory(mock, testDirectoryName1);
    String testRelativePath1 = "test1/";
    String testAbsolutePath1 = "/test1/";
    ControllableDirectory actualDirectory1a = null;
    actualDirectory1a =
        getDirectoryByPath(mock, testRelativePath1, actualDirectory1a);
    ControllableDirectory actualDirectory1b = null;
    actualDirectory1b =
        getDirectoryByPath(mock, testAbsolutePath1, actualDirectory1b);
    assert (actualDirectory1a == newDirectory1);
    assert (actualDirectory1b == newDirectory1);
  }

  /**
   * @param mock
   * @param path
   * @param directory
   * @return
   */
  private ControllableDirectory getDirectoryByPath(
      ManagementOfContainerKernel mock, String path,
      ControllableDirectory directory) {
    try {
      directory = (ControllableDirectory) mock.getAbsolutePathOf(path);
    } catch (NoSuchFileExistException e) {
      e.printStackTrace();
      fail("testGetAbsolutePathOf failed!");
    }
    return directory;
  }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#getDirectoryPath(fileSystem.ControllableDirectory)}.
   */
  @Test
  void testGetDirectoryPath() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    String testDirectoryName1 = "test1";
    ControllableDirectory newDirectory1 =
        createNewDirectory(mock, testDirectoryName1);
    String testAbsolutePath1 = "/test1/";
    assert (mock.getDirectoryPath(newDirectory1).equals(testAbsolutePath1));
  }

  /**
   * Test method for
   * {@link fileSystem.ManagementOfContainerKernel#ConcatenatingTwoByteArray(byte[], byte[])}.
   */
  @Test
  void testConcatenatingTwoByteArray() {
    ManagementOfContainerKernel mock = new ManagementOfContainerKernel();
    byte[] testByte1 = new byte[] {0, 1};
    byte[] testByte2 = new byte[] {1, 1};
    byte[] testByteE = new byte[] {};
    byte[] wantResult12 = new byte[] {0, 1, 1, 1};
    byte[] wantResult21 = new byte[] {1, 1, 0, 1};
    byte[] wantResultE1 = new byte[] {0, 1};
    assert (Arrays.equals(mock.ConcatenatingTwoByteArray(testByte1, testByte2),
        (wantResult12)));
    assert (Arrays.equals(mock.ConcatenatingTwoByteArray(testByte2, testByte1),
        (wantResult21)));
    assert (Arrays.equals(mock.ConcatenatingTwoByteArray(testByteE, testByte1),
        (wantResultE1)));
  }
}
