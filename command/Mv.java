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

import java.util.UUID;
import fileSystem.ControllableDirectory;
import fileSystem.ControllableFile;
import fileSystem.ManagementOfContainerKernel;
import fileSystem.NoSuchFileExistException;

public class Mv implements Command {

  @Override
  public void execute(ManagementOfContainerKernel mock, Object[] arg) {
    try {
      // Get source and desto
      ControllableFile source = mock.getAbsolutePathOf((String) arg[0]);
      ControllableDirectory desto =
          (ControllableDirectory) mock.getAbsolutePathOf((String) arg[1]);
      if (!desto.isDirectory()) { // Verify if destination is directory
        CommandObject.logLine("mv: Given destination is not a directory!");
        return;
      }
      // Move them
      if (source.isDirectory()) {
          for(UUID uuid : ((ControllableDirectory)source).getFileUUIDs()) {
            mock.moveFileTo(mock.findFile(uuid), desto);
          }
      } else {
        mock.moveFileTo(source, desto);
      }
    } catch (NoSuchFileExistException e) {
      // If one of the pathes is not valid, give message
      CommandObject.logErr("mv: Given path invalid!");
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see command.Command#getManual()
   */
  @Override
  public void getManual() {
    CommandObject.logLine(
        "This command help user move a file from source to destination. ");
    CommandObject
        .logLine("If source is a directory, the move will be recursive. ");
    CommandObject.logLine("Usage: \nmv ./a/b /c/d/");

  }

  /*
   * (non-Javadoc)
   * 
   * @see command.Command#checkArgFormat(java.lang.String[])
   */
  @Override
  public boolean checkArgFormat(String[] arg) {
    return arg.length == 2;
  }

}
