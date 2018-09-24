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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import driver.JShell;
import fileSystem.ControllableFile;
import fileSystem.FileWithSameNameExistedException;
import fileSystem.InvalidFileNameException;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

/**
 * Represents the Command Object
 */
public class CommandObject {
  // create a static ArrayList to store all the inputs
  private static ArrayList<String> cmdList = new ArrayList<String>();
  // create a static ArrayList to store all the input
  private static ArrayList<Object[]> exCmdList = new ArrayList<Object[]>();
  // create a static ArrayList to store all the directory
  private static ArrayList<String> dirStack = new ArrayList<String>();
  // create a string that's stores the error message
  private final static String ERRORPROMPT = "Invalid command, please try again";

  private static boolean redHapping = false;
  private static boolean redNotLog = false;
  private static boolean redValid = true;
  private static String redirectString = "";
  // private Command cmd = new Command();
  // a void method that's call the Command class
  // create the 'MOCK' object that's stores all the variable and path for JShell
  private ManagementOfContainerKernel mock = new ManagementOfContainerKernel();

  public void runCommand(final String command, final Object[] arg) {
    execute(command, arg);
  }

  public void runRedirect(final String command, final Object[] arg, String path,
      Boolean overwrite) {
    redHapping = true;
    redNotLog = true;
    execute(command, arg);
    // String quotationmark = !command.equals("echo") ? "\"" : "";
    String quotationmark = "\"";
    if (redValid) {
      execute("echo",
          new String[] {
              quotationmark + redirectString.substring(0,
                  Math.max(0, redirectString.length() - 1)) + quotationmark,
              overwrite ? ">" : ">>", path});
    }
    redNotLog = false;
    redValid = true;
    redirectString = "";
    redHapping = false;
  }

  //////////////////////////////////////////////////////////////////////////
  /**
   * execute method that's assign command to the corresponding class
   * 
   * @param String the type of command
   * @param Object[] that's represents the input arguments
   * @return none
   */
  public void execute(final String command, final Object[] arg) {
    // find the class object from string command
    String commandClass = findClassName(command);
    // set the default argument correctness is false
    boolean commandFindedAlsoHaveCorrectArg = false;

    try {
      // make class object that represent by command variable
      Class myClass = Class.forName(commandClass);
      // make the myClass as object to get all the method inside
      Object commandObject = myClass.newInstance();
      // get the method and store it as method variable
      Method checkCommandInputClass =
          myClass.getDeclaredMethod("checkArgFormat", arg.getClass());
      // run the checkCommandInputClass method and save the output
      commandFindedAlsoHaveCorrectArg = (boolean) checkCommandInputClass
          .invoke(commandObject, new Object[] {arg});
      // if commandFindedAlsoHaveCorrectArg is true, that means the argument is
      // correct order
      if (commandFindedAlsoHaveCorrectArg) {
        // find execute method from myClass and save that object as method
        // object
        Method executeInputClass = myClass.getDeclaredMethod("execute",
            mock.getClass(), Object[].class);
        // run the execute method to execute the command
        executeInputClass.invoke(commandObject, new Object[] {mock, arg});
      } else {
        // log the error message
        CommandObject.logErr("incorrect argument");
      }
    } catch (ClassNotFoundException | NoSuchMethodException
        | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | InstantiationException e) {
      // e.printStackTrace();
      JShell.logErr();
    }
    // System.out.println("The checkInput() method from " + command
    // + " class returns: " + commandFindedAlsoHaveCorrectArg);
  }

  public void redirect(String redirectString, String path, Boolean overwrite) {
    // TODO Auto-generated method stub
    // System.out.println(redirectString + " | " + path + " | " + overwrite);
    try {
      // is null if not find any
      // ControllableFile redFile = MOCK.findFile(path);
      ControllableFile redFile = mock.getAbsolutePathOf(path);
      if (overwrite) {
        try {
          // MOCK.createFileUnderWD(path, redirectString);
          mock.createFileUnderWD(redFile.getName(), redirectString);
        } catch (FileWithSameNameExistedException e) {
          if (redFile.isDirectory()) {
            CommandObject
                .logErr(redFile.getName() + " is a directory not file");
          } else {
            redFile.setContent(redirectString.getBytes());
          }
        }
      } else {
        try {
          mock.createFileUnderWD(path, redirectString);
        } catch (FileWithSameNameExistedException e) {
          if (redFile.isDirectory()) {
            CommandObject
                .logErr(redFile.getName() + " is a directory not file");
          } else {
            byte[] existCont = redFile.getContent();
            byte[] extenCont = redirectString.getBytes();
            byte[] newConten = new byte[existCont.length + extenCont.length];

            for (int i = 0; i < newConten.length; ++i) {
              newConten[i] = i < existCont.length ? existCont[i]
                  : extenCont[i - existCont.length];
            }
            redFile.setContent(newConten);
          }
        }
      }
    } catch (InvalidFileNameException | NoSuchFileExistException e) {
      CommandObject.logErr("Invalid File Name");
    }
    // try {
    // MOCK.createFileUnderWD(path, redirectString);
    // } catch (FileWithSameNameExistedException e) {
    // //MOCK.
    // //MOCK.createFileUnderWD(path, redirectString);
    // } catch (InvalidFileNameException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
  }

  /**
   * A method to find the class name of any input
   * 
   * @param String a string represent input
   * @return return "command." + input.substring(0, 1).toUpperCase() +
   *         input.substring(1);
   */
  public String findClassName(String input) {
    return "command." + input.substring(0, 1).toUpperCase()
        + input.substring(1);
  }
  /////////////////////////////////////////////////////////////////////////

  /**
   * A method append the command list
   * 
   * @param String item
   */
  public void addToCmdList(String item) {
    cmdList.add(item);
  }

  /**
   * setter method for ArrayList cmdList, This method always returns immediately
   * 
   * @param ArrayList<String> that recorded the user input
   * @return none
   */
  public void setCmdList(ArrayList<String> cmdlist) {
    cmdList = cmdlist;
  }

  /**
   * getter method for ArrayList cmdList, This method always returns immediately
   * 
   * @param none
   * @return ArrayList<String> that recorded the user input
   */
  public static ArrayList<String> getCmdList() {
    return cmdList;
  }

  /**
   * A method append the command list
   * 
   * @param String item
   */
  public void addToExCmdList(Object[] item) {
    exCmdList.add(item);
    // System.out.println(exCmdList.toString());
  }

  /**
   * getter method for ArrayList cmdList, This method always returns immediately
   * 
   * @param none
   * @return ArrayList<String> that recorded the user input
   */
  public static Object[] getExCmdList(int num) {
    return exCmdList.get(num);
  }

  /**
   * setter method for stack dirStack, This method always returns immediately
   * 
   * @param str that represents the last current working directory
   * @return none
   */
  public static void pushDirStack(String path) {
    dirStack.add(path);
  }

  /**
   * getter method for stack dirStack, This method always returns immediately
   * 
   * @param none
   * @return str remove from the top entry from the dirStack, and return it
   */
  public static String popDirStack() {
    if (dirStack.size() == 0) {
      return "";
    } else {
      return dirStack.remove(dirStack.size() - 1);
    }

  }

  /**
   * Print the str argument has been passed in, with a new line The "str"
   * argument is a specifier that is relative to it self This method always
   * returns immediately
   * 
   * @param str a input string that represent the original string
   * @return none
   */
  public static void logLine(Object str) {
    // Just directly use log
    log(str + "\n");
  }

  /**
   * Print the error message by calling the Logln method This method always
   * returns immediately
   * 
   * @param none
   * @return none
   */
  public static void logErr() {
    // print the error message
    System.out.println(ERRORPROMPT);
    if (redHapping)
      redValid = false;
  }

  /**
   * Print the error message by calling the Logln method This method always
   * returns immediately
   * 
   * @param none
   * @return none
   */
  public static void logErr(Object str) {
    // print the error message
    System.out.println(str);
    if (redHapping)
      redValid = false;
  }

  /**
   * A method to print the str argument has been passed in The "str" argument is
   * a specifier that is relative to it self This method always returns
   * immediately
   * 
   * @param str a input string that represent the original string
   */
  public static void log(Object str) {
    // print the input string with new line after it
    if (redHapping) {
      redirectString += (String) str;
      // System.out.println(redirectString);
    } else {
      System.out.print(str);
    }
    redNotLog = false;
  }
}
