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

package driver;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;
import command.CommandObject;

// declare a class
public class JShell {
  /**
   * string that's save the content of the input field
   */
  private final String COMMANDPROMPT = "/#: ";
  /**
   * string that's save the content of the split key word
   */
  private final String SUBSPLITSIGN = "\"";
  /**
   * string that's save the content of the error message
   */
  private final static String ERRORPROMPT = "Invalid command, please try again";
  /**
   * string that's save the content of the split key word
   */
  private final char SPLITSIGN = '\"';
  // static Map<String, Integer> initMap = new HashMap<String, Integer>();
  // crate a Command object
  /**
   * CommandObject that's packages the all operations
   */
  private CommandObject cmdObj = new CommandObject();
  // create a static scanner type variable
  /**
   * the Scanner that's listen the user input
   */
  private Scanner scanner = new Scanner(System.in);;
  // // create a static ArrayList to store all the inputs
  // private static ArrayList<String> cmdList = new ArrayList<String>();
  // // create a static ArrayList to store all the directory
  // private static ArrayList<String> dirStack = new ArrayList<String>();

  /**
   * A main function that's starts the program
   * 
   * @param str[] a string array that's pass in the command from out side
   * @return none
   */
  public static void main(String[] args) {
    // associate an object with the scanner
    // scanner = new Scanner(System.in);
    // call the recursion method
    JShell jShellOne = new JShell();
    // JShell jShellTwo = new JShell();
    jShellOne.recurShell();
    // jShellTwo.recurShell();
  }

  /**
   * A recursion method that is recursive to listen to input and make different
   * actions This method always returns immediately
   * 
   * @param none
   * @return none
   */
  private void recurShell() {
    // show the tag message to tell the last task is finished and ready to
    // receive a new command
    log(COMMANDPROMPT);
    // make the input string reference the Command variable
    /**
     * string that's holds user input
     */
    String inputCommand = scanner.nextLine();
    // add the input to the ArrayList
    // cmdList.add(inputCommand);
    cmdObj.addToCmdList(inputCommand);
    // String Command = "a b c d \"i s s b sdfs\" d ";
    // remove leading and trailing spaces
    inputCommand = inputCommand.trim();
    // System.out.println(Command + "--------");
    // Command = saveSpace(Command);
    /**
     * string array that's holds the user input as a array
     */
    String[] CommandSplitted = null;
    // if the input command isn't empty
    if (inputCommand.length() > 0) {
      // if first letter is not "
      if (inputCommand.charAt(0) != SPLITSIGN) {
        // // if first letter is not !
        // if (inputCommand.charAt(0) == '!') {
        // try {
        // // get the index of the input wanted
        // int num = Integer.parseInt(inputCommand.split("!")[1]);
        // Exclaim.execute(num);
        // } catch (Exception e) {
        // }
        // }
        if (inputCommand.charAt(0) == '!') {
          inputCommand = "exclaim " + inputCommand.substring(1);
        }
        // System.out.println(inputCommand);
        // convert the command to String array
        CommandSplitted = convertCommand(inputCommand);
      } else {
        // print error message
        logErr();
        // restart the shell
        recurShell();
      }
    } else {
      // print error message
      logErr();
      // restart the shell
      recurShell();
    }
    // logLine(Arrays.toString(CommandSplitted));
    // example: a b c d "i s s b sdfs" d
    // split the Command that distinguishes instructions and arguments
    // pass the command with argument to execute method inside Command class
    // command.Command.execute(CommandSplitted[0],
    // Arrays.copyOfRange(CommandSplitted, 1, CommandSplitted.length));

    // System.out.println(Arrays.toString(CommandSplitted));

    int redIndex = CommandSplitted.length - 2;
    String redPath = CommandSplitted[CommandSplitted.length - 1];
    if ((Arrays.asList(CommandSplitted).contains(">")
        || Arrays.asList(CommandSplitted).contains(">>"))
        && CommandSplitted.length > 2) {
      boolean overWrite = (CommandSplitted[redIndex].equals(">"));
      cmdObj.runRedirect(CommandSplitted[0],
          Arrays.copyOfRange(CommandSplitted, 1, redIndex), redPath, overWrite);
      cmdObj.addToExCmdList(new Object[] {CommandSplitted[0],
          Arrays.copyOfRange(CommandSplitted, 1, redIndex), redPath,
          overWrite});
    } else {
      cmdObj.runCommand(CommandSplitted[0],
          Arrays.copyOfRange(CommandSplitted, 1, CommandSplitted.length));
      cmdObj.addToExCmdList(new Object[] {CommandSplitted[0],
          Arrays.copyOfRange(CommandSplitted, 1, CommandSplitted.length)});
    }
    // System.out.println(cmdObj.getExCmdList(0));
    // System.out.println(cmdObj.getExCmdList(2));
    // System.out.println(cmdObj.getExCmdList(3));
    recurShell();
  }

  // public void runCmd() {
  //
  // }
  // private boolean checkRedInput(String[] command) {
  // //if(command.length > 1 && command.)
  // return false;
  // }
  //
  // private static int getRedirectIndex(String[] strArray) {
  // for(int i=strArray.length-1; i>-1; i--) {
  // if(strArray[i].equals(">"))
  // return i;
  // }
  // return -1;
  // }

  /**
   * A method that split the input string to small part that stores in a string
   * array and return it. Example: command = a b c d "i s s b oh" d
   * convertCommand(command) -> [a, b, c, d, "i s s b oh", d]
   * 
   * @param str represents the input command
   * @return string[] that convert from input Command
   */
  private String[] convertCommand(String command) {
    // find the length of first half of the command and save it as a integer
    /**
     * integer that's holds the index of the first quote sign
     */
    int firsQuo = command.indexOf(SUBSPLITSIGN);
    // find the length of second half of the command and save it as a integer
    /**
     * integer that's holds the index of the last quote sign
     */
    int lastQuo = command.lastIndexOf(SUBSPLITSIGN) + 1;
    // declare a string array to store the output
    /**
     * integer that's holds the index of the last quote sign
     */
    String[] commandSplitted;
    // if the length of first half and last half part are not intersection with
    // each
    // other
    if (firsQuo != lastQuo - 1) {
      // get the slice between two quotes
      String subQuo = command.substring(firsQuo, lastQuo);
      // split whole inputString with white space
      String[] CommandSplittd = command.split(" +");
      // get the length of the slice between two quotes
      int subLength = subQuo.split("\\s+").length;
      // get the length of the part before the "
      int firstPartLen = command.substring(0, firsQuo).split("\\s+").length;
      // make first part of the substring as a stream
      Stream<String> partBeforeQuo =
          Stream.of(Arrays.copyOfRange(CommandSplittd, 0, firstPartLen));
      // make part between the quotes(substring) as a stream
      Stream<String> partBetweeQuo = Stream.of(subQuo);
      // make the substring before last quote as a string array
      String[] arr =
          Stream.concat(partBeforeQuo, partBetweeQuo).toArray(String[]::new);
      // make part before the last quotes(substring) as a stream
      Stream<String> partBeforeLastQuo = Stream.of(arr);
      // make part after the last quotes(substring) as a stream
      Stream<String> partAfterLastQuo =
          Stream.of(Arrays.copyOfRange(CommandSplittd, subLength + firstPartLen,
              CommandSplittd.length));
      // cast/combine all the part to one string list
      commandSplitted = Stream.concat(partBeforeLastQuo, partAfterLastQuo)
          .toArray(String[]::new);
    } else {
      // split the string to an string array with blank space
      commandSplitted = command.split(" +");
    }
    // return the command as a string array
    return commandSplitted;
  }

  /**
   * Print the str argument has been passed in The "str" argument is a specifier
   * that is relative to it self This method always returns immediately
   * 
   * @param str a input string that represent the original string
   * @return none
   */
  public static void log(Object str) {
    // print the input string
    // for our case, only be called to print "#"
    System.out.print(str);
  }

  // /**
  // * Print the str argument has been passed in, with a new line The "str"
  // * argument is a specifier that is relative to it self This method always
  // * returns immediately
  // *
  // * @param str a input string that represent the original string
  // * @return none
  // */
  // public static void logLine(Object str) {
  // // print the input string with new line after it
  // System.out.println(str);
  // }

  /**
   * Print the error message by calling the Logln method This method always
   * returns immediately
   * 
   * @param none
   * @return none
   */
  public static void logErr() {
    // print the error message
    CommandObject.logErr(ERRORPROMPT);
  }

}
