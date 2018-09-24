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

import fileSystem.ManagementOfContainerKernel;

public class Exclaim implements Command {

  @Override
  public void getManual() {
    // TODO Auto-generated method stub
    CommandObject.logLine(
        "This command will recall any of previous history by its number(>=1) "
            + "preceded by an exclamation point (!). For instance, if your "
            + "history looks like \ncd ..\nmkdir textFolder\necho \"Hello "
            + "World\"\nfsjhdfks\nhistory\nYou could type the following on "
            + "the command line of your JShell i.e.\n!3\nThis will immediately "
            + "recall and execute the command associated with the history "
            + "number 3. The above command of !3 will indirectly execute\necho "
            + "\"Hello World\"");
  }

  @Override
  public boolean checkArgFormat(String[] arg) {
    return Character.isDigit(arg[0].charAt(0));
  }

  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    int cmdNum = Integer.parseInt((String) arg[0]) - 1;
    Object[] cmdArr = CommandObject.getExCmdList(cmdNum);
    if (cmdArr.length == 4) {
      new CommandObject().runRedirect((String) cmdArr[0], (Object[]) cmdArr[1],
          (String) cmdArr[2], (boolean) cmdArr[3]);
    } else if (cmdArr.length == 2) {
      new CommandObject().runCommand((String) cmdArr[0], (Object[]) cmdArr[1]);
    } else {
      CommandObject.logErr("bad input");
    }
  }

}
