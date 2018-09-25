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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.Before;
import org.junit.Test;
import command.Man;
import command.Ls;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
public class ManTest {

  @Test
  public void test() {
   Man man = null;
    String expected = "This method print documentation for commands. \n Usage:"
        + " \n man <command>" + "\ne.g." + "\nman cd\n"
        + "\nThis method change the current directory to another directory"
        + "\ne.g. \n" + "cd a" + "\npwd" + "\nOutput: a" + "\n\ncd a1"
        + "\nOutput: File a1 does not exist";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    man.getManual();
    String actual = null;
    try {
      actual = br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(expected,actual);
  }

}
