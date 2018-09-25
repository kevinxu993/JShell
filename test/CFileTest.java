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

import org.junit.jupiter.api.Test;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;

class CFileTest {



  @Test
  void testControllableFileStringControllableDirectory() {
    String name1 = "test1";
    ControllableFile newF = new ControllableFile(name1, null);
    assert (newF.getName().equals(name1));
  }

  @Test
  void testControllableFileStringControllableDirectoryString() {
    String name1 = "test1";
    String content1 = "testContent1";
    ControllableFile newF = new ControllableFile(name1, null, content1);
    assert (newF.getName().equals(name1));
    assert (new String(newF.getContent()).equals(content1));
  }

  @Test
  void testIsDirectory() {
    ControllableFile newF = new ControllableFile("test1", null);
    assert (newF.isDirectory() == false);
  }

  @Test
  void testSetContent() {
    String name1 = "test1";
    String content1 = "testContent1";
    String content2 = "content2";
    ControllableFile newF = new ControllableFile(name1, null, content1);
    newF.setContent(content2.getBytes());
    assert (new String(newF.getContent()).equals(content2));
    String name2 = "test2";
    ControllableFile newF2 = new ControllableFile(name2, null);
    newF2.setContent(content2.getBytes());
    assert (new String(newF2.getContent()).equals(content2));
  }

  @Test
  void testSetParent() {
    String name1 = "test1";
    String content1 = "testContent1";
    ControllableDirectory parent = new ControllableDirectory("a", null);
    ControllableFile newF = new ControllableFile(name1, null, content1);
    newF.setParent(parent);
    assert (newF.getParent() == parent);
    String name2 = "test2";
    ControllableFile newF2 = new ControllableFile(name2, null);
    newF2.setParent(parent);
    assert (newF2.getParent() == parent);
  }

  @Test
  void testSetName() {
    String name1 = "test1";
    String content1 = "testContent1";
    String newName = "testNew";
    ControllableFile newF = new ControllableFile(name1, null, content1);
    newF.setName(newName);
    assert (newF.getName().equals(newName));
    String name2 = "test2";
    ControllableFile newF2 = new ControllableFile(name2, null);
    newF2.setName(newName);
    assert (newF2.getName().equals(newName));
  }

}
