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

package fileSystem;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Directory implementation for MOCK File system, extends from ControllableFile
 * 
 * @author wang_w571
 */
public class ControllableDirectory extends ControllableFile {

  /**
   * Easier way to determine if this is a file
   * 
   * @return this is a directory or not
   */
  public boolean isDirectory() {
    return true;
  }

  /**
   * Create a ControllableDirectory instance
   * 
   * @param name of the Directory
   * @param The directory that this directory belongs to
   */
  public ControllableDirectory(String name, ControllableDirectory parent) {
    // Set name
    setName(name);
    // Set UUID to uuid
    setUUID();
    // Initialize the content to be empty
    content = new byte[0];
    // Set parent directory
    parentDirectory = parent;
  }

  /**
   * Create a ControllableDirectory instance
   */
  public ControllableDirectory() {}

  /**
   * @return File names under this directory
   */
  public ArrayList<String> getFileNames(ManagementOfContainerKernel mock) {
    // Initialize a result string array list
    ArrayList<String> fileNames = new ArrayList<String>();
    // Get files' UUIDs under this directory
    for (String uuidS : (new String(getContent())).split(" ")) {
      if (!uuidS.equals("")) {
        fileNames.add(mock.findFile(UUID.fromString(uuidS)).getName());
      }
    }
    return fileNames;
  }

  /**
   * Return the file UUID that matches file name
   * 
   * @param the MOCK file system that this directory is on.
   * @param the name of the file that is being looking for
   * @return the file UUID that matches given file name and under this directory
   * @throws NoSuchFileExistException
   */
  public UUID getFileUUID(ManagementOfContainerKernel mock, String fileName)
      throws NoSuchFileExistException {
    for (String uuidS : (new String(getContent())).split(" ")) {
      if (!uuidS.equals("")) {
        UUID currentUUID = UUID.fromString(uuidS);
        if (fileName.equals(mock.findFile(currentUUID).getName())) {
          return currentUUID;
        }
      }
    }
    throw new NoSuchFileExistException();
  }

  /**
   * @return File UUIDs under this directory
   */
  public ArrayList<UUID> getFileUUIDs() {
    // Initialize a result string array list
    ArrayList<UUID> fileUUIDs = new ArrayList<UUID>();
    // Get files' UUIDs under this directory
    for (String uuidS : (new String(getContent())).split(" ")) {
      if (!uuidS.equals("")) {
        UUID currentUUID = UUID.fromString(uuidS);
        fileUUIDs.add(currentUUID);
      }
    }
    return fileUUIDs;
  }
}
