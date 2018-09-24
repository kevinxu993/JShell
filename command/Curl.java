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
package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;

// declare a class
public class Curl implements Command {
  /**
   * fileName that's hold the name of the file will be create
   */
  private String fileName;

  /**
   * A function that cut the tail for the URL if it has one Example: ....a.txt/
   * - > ....a.txt
   * 
   * @param urlStr a string that represents the input URL and will to be cut the
   *        tail
   * @return urlStr by property, the String variable will not be mutated, but
   *         the different variable will be returned
   */
  private String splitURLFromString(String urlStr) {
    // if the URL end with "/"
    if (urlStr.lastIndexOf('/') == urlStr.length() - 1) {
      // remove "/" at end of URL and save it in urlStr
      urlStr = urlStr.substring(0, urlStr.length() - 1);
    }
    return urlStr;
  }

  /**
   * A function gets the file name from URL
   * 
   * @param url a string that represents the input URL
   * 
   * @return fileName the fileName that was hiding inside URL
   */
  private String getFileNameFromURL(String url) {
    // get the working URL and save it inside url (global variable)
    url = splitURLFromString(url);
    // create a result variable that's going to return, and it holds a substring
    // of
    // url that represents the fileName
    String fileName = url.substring(url.lastIndexOf('/') + 1);
    // CommandObject.logLine(fileName);
    return fileName;
  }

  /**
   * A method to execute the curl command, grab the file from URL and save it
   * locally
   * 
   * @param mock a ManagementOfContainerKernel object
   * @param arg an Object[] containing the command the user inputs
   */
  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    // the local variable has been created that can be accessed by different
    // try/catch block
    URL url;
    InputStream is = null;
    BufferedReader br;
    String line;
    String content = "";
    // a global variable that shares the name of the file
    fileName = getFileNameFromURL((String) arg[0]);
    try {
      // assume a value that has URL type to url variable
      url = new URL(splitURLFromString((String) arg[0]));
      // throws an IOException the setup for gain the data using BufferedReader
      is = url.openStream();
      br = new BufferedReader(new InputStreamReader(is));
      // CommandObject.logLine("here");
      // read each line, record each line
      while ((line = br.readLine()) != null) {
        content += line + "\n";
      }
      // create a file under current working directory using file name and
      // content
      // come from URL
      try {
        mock.createFileUnderWD(fileName, content);
      } catch (FileWithSameNameExistedException e) {
        // if FileWithSameNameExistedException, print a message that describes
        // the
        // problem
        CommandObject.logErr("file/dir: " + fileName + "already exist");
        // e.printStackTrace();
      } catch (InvalidFileNameException e) {
        // if InvalidFileNameException, print a message that describes the
        // problem
        CommandObject.logErr("invalid file name: " + fileName);
        // e.printStackTrace();
      }
    } catch (MalformedURLException mue) {
      // if MalformedURLException, print a message that describes the problem
      CommandObject.logErr("URL not in correct format");
      // mue.printStackTrace();
    } catch (IOException ioe) {
      // if IOException, print a message that describes the problem
      CommandObject.logErr("URL invaild, content not found");
      // ioe.printStackTrace();
    } finally {
      // this block of code closes the InputStream, it's making sure user input
      // will
      // be listen normally
      try {
        if (is != null)
          is.close();
      } catch (IOException ioe) {
        // nothing to see here
      }
    }

  }

  /**
   * A method to print documentation for the Curl command.
   * 
   * @param none
   *
   * @param none
   */
  @Override
  public void getManual() {
    CommandObject.logLine(
        "get an item from a URL and save it to the current working directory"
            + "\r\nthe URL should be linking to a file that has the extended "
            + "type\r\nsuch as: \".txt\", \".html\", \".com\", \".pdf\", etc, "
            + "etc\r\nand the content will be saved as String, for more detail "
            + "check\r\nthe ControllableFile.java\r");
  }

  /**
   * A method to check whether the input is correct
   * 
   * @param arg an String[] containing the arguments after the command
   * @return boolean that's represents the correctness of argument
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    return arg.length == 1;
  }

}
