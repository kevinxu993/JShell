package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import command.Mv;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;

class MvTest {
  ManagementOfContainerKernel mock;
  ControllableDirectory dOne;
  ControllableFile testFileA;
  ControllableFile testFileB;
  
  @Before
  public void init() {
    mock = new ManagementOfContainerKernel();
    try {
      testFileA = mock.createFileUnderWD("a", "");
      dOne = mock.createDirectoryUnderWD("d1");
      mock.setWorkingDir(mock.createDirectoryUnderWD("d0"));
      testFileB = mock.createFileUnderWD("b", "");
      mock.setWorkingDir(mock.getRoot());
    } catch (FileWithSameNameExistedException | InvalidFileNameException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  public void testExecute() {
    init();
    new Mv().execute(mock, new String[] {"a", "d1"});
    assert(dOne.getFileNames(mock).contains(testFileA.getName()));
    new Mv().execute(mock, new String[] {"/d0/", "d1"});
    assert(dOne.getFileNames(mock).contains(testFileB.getName()));
    
  }

  @Test
  public void testCheckArgFormat() {
    assert(new Mv().checkArgFormat(new String[] {"a", "d0"}));
    assert(!new Mv().checkArgFormat(new String[] {}));
    assert(!new Mv().checkArgFormat(new String[] {"a"}));
    assert(!new Mv().checkArgFormat(new String[] {"a", "b", "c"}));
  }

  void log(Object o) {
    System.out.println(o);
  }
}
