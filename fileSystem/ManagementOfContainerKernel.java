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

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class ManagementOfContainerKernel {

  private HashMap<UUID, ControllableFile> files;
  private ControllableDirectory workingDirectory;
  private final ControllableDirectory root;

  /**
   * Creation of a ManagementOfContainerKernel instance
   */
  public ManagementOfContainerKernel() {
    files = new HashMap<UUID, ControllableFile>();
    root = new ControllableDirectory("", null);
    files.put(root.uuid, root);
    workingDirectory = root;
  }

  /**
   * @return root instance of this ManagementOfContainerKernel instance
   */
  public ControllableDirectory getRoot() {
    return root;
  }

  /**
   * Find the file instance with UUID
   * 
   * @param uuid file UUID
   * @return the file instance that has the required UUID
   */
  public ControllableFile findFile(UUID uuid) {
    return files.get(uuid);
  }

  /**
   * Find the file instance with name
   * 
   * @param fileName File Name
   * @return the file instance that has the required name and created earlier
   */
  protected ControllableFile findFile(String fileName) {
    ControllableFile targetFile = null;
    for (ControllableFile f : files.values()) {
      if (f.getName().equals(fileName)) {
        targetFile = f;
        break;
      }
    }
    return targetFile;
  }

  /**
   * @param path the String Representation of path
   * @return the designated directory/ file instance of required path
   * @throws NoSuchFileExistException given path not found
   */
  public ControllableFile getAbsolutePathOf(String path)
      throws NoSuchFileExistException {
    path = cleanUpDoubleSlash(path);
    String[] splitted = path.split("/");
    ControllableFile targetD =
        splitted.length == 0 && !path.equals("/") ? workingDirectory
            : (path.startsWith("/") ? root : workingDirectory);

    // if (!path.endsWith("/") && path.contains("/")) {
    // // TODO: Proper exception info
    // throw new IrregularInputException();
    // }

    for (String directoryName : splitted) {
      if (!targetD.isDirectory())
        throw new NoSuchFileExistException();
      targetD = getTargetFile((ControllableDirectory) targetD, directoryName);
    }
    return targetD;
  }

  /**
   * @param startingDirectory The starting directory, which is for relative path
   *        searching
   * @param directoryName the directory name that to be find under given
   *        directory
   * @return the file/directory instance of desired directory
   * @throws NoSuchFileExistException if desired directory not found
   */
  private ControllableFile getTargetFile(ControllableFile startingDirectory,
      String directoryName) throws NoSuchFileExistException {
    if (directoryName.equals("") || directoryName.equals("."))
      return startingDirectory;
    else if (directoryName.equals("..")) {
      if (startingDirectory.getName().equals(""))
        throw new NoSuchFileExistException();
      else
        startingDirectory = startingDirectory.parentDirectory;
    } else {
      if (((ControllableDirectory) startingDirectory).getFileNames(this)
          .contains(directoryName)) {
        ControllableFile potentialNewWD =
            this.findFile(((ControllableDirectory) startingDirectory)
                .getFileUUID(this, directoryName));
        if (potentialNewWD.isDirectory()) {
          startingDirectory = (ControllableDirectory) potentialNewWD;
        } else {
          // TODO: Proper exception info
          return potentialNewWD;
        }
      } else {
        throw new NoSuchFileExistException();
      }
    }
    return startingDirectory;
  }

  /**
   * @param path a path that need to clean up double slashes
   * @return path without double slashes
   */
  private String cleanUpDoubleSlash(String path) {
    while (path.contains("//")) {
      path = path.replace("//", "/");
    }
    return path;
  }

  /**
   * @return the Directory instance of working directory
   */
  public ControllableDirectory getWorkingDir() {
    return workingDirectory;
  }

  public void setWorkingDir(ControllableDirectory directoryInstance) {
    workingDirectory = directoryInstance;
  }

  /**
   * @return the string representation of a given Directory
   */
  public String getDirectoryPath(ControllableDirectory directory) {
    String path = "";
    ControllableDirectory tempWD = directory;
    while (tempWD != null) {
      path = tempWD.getName() + "/" + path;
      tempWD = tempWD.parentDirectory;
    }
    return path;
  }

  /**
   * This method creates a file under working directory
   * 
   * @param fileName The name of the file
   * @param content The contents that the file will have
   * @return the file instance that just created
   * @throws FileWithSameNameExistedException
   * @throws InvalidFileNameException
   */
  public ControllableFile createFileUnderWD(String fileName, String content)
      throws FileWithSameNameExistedException, InvalidFileNameException {
    // Check file name valid
    IfSameFileExist(fileName);
    ifFileNameInvalid(fileName);
    // Create file
    ControllableFile NewFile =
        new ControllableFile(fileName, workingDirectory, content);
    // Add file
    AddFileUnderWD(NewFile);
    return NewFile;
  }

  // public void changeFileContentUnderWD(String fileName, String newContent) {
  // findFile(fileName).setContent(newContent.getBytes());
  // }

  /**
   * @param fileName the file name that is to be checked
   * @throws InvalidFileNameException - will be thrown if file name is bad
   */
  private void ifFileNameInvalid(String fileName)
      throws InvalidFileNameException {
    // The pattern that not match dot, numbers and letters
    Pattern p = Pattern.compile("[^a-z0-9\u002e ]", Pattern.CASE_INSENSITIVE);
    // If not valid, throw exception
    if (fileName.equals("") || fileName.contains(" ")
        || p.matcher(fileName).find() || fileName.equals(".")) {
      throw new InvalidFileNameException();
    }
  }

  /**
   * @param The name of the directory
   * @throws FileWithSameNameExistedException
   * @throws InvalidFileNameException
   */
  /**
   * @param directoryName The name of the directory
   * @return the directory instance that just created
   * @throws FileWithSameNameExistedException - if same name file already
   *         existed
   * @throws InvalidFileNameException - if file name is bad
   */
  public ControllableDirectory createDirectoryUnderWD(String directoryName)
      throws FileWithSameNameExistedException, InvalidFileNameException {
    // Check
    IfSameFileExist(directoryName);
    ifFileNameInvalid(directoryName);
    // Create
    ControllableDirectory newDict =
        new ControllableDirectory(directoryName, workingDirectory);
    AddFileUnderWD(newDict);
    return newDict;
  }

  /**
   * Add a file under working directory
   * 
   * @param NewFile the file that is going to be added
   */
  private void AddFileUnderWD(ControllableFile NewFile) {
    byte[] UUIDByte = (" " + NewFile.getUUID().toString()).getBytes();
    files.put(NewFile.uuid, NewFile);
    workingDirectory.setContent(
        ConcatenatingTwoByteArray(workingDirectory.getContent(), UUIDByte));
  }

  /**
   * @param fileName the file name that is going to be checked
   * @throws FileWithSameNameExistedException - if file with same name existed
   */
  private void IfSameFileExist(String fileName)
      throws FileWithSameNameExistedException {
    if (workingDirectory.getFileNames(this).contains(fileName)) {
      throw new FileWithSameNameExistedException();
    }
  }

  /**
   * This method concatenate two byte arrays into one and return it
   * 
   * @param byte1 first byte array
   * @param byte2 second byte array
   * @return Concatenated byte array
   */
  public byte[] ConcatenatingTwoByteArray(byte[] byte1, byte[] byte2) {
    ByteBuffer BB = ByteBuffer.allocate(byte1.length + byte2.length);
    BB.put(byte1);
    BB.put(byte2);
    return BB.array();
  }


  /**
   * Move a file instance under designated directory
   * 
   * @param file that will be moved
   * @param target directory
   */
  public void moveFileTo(ControllableFile designatedFile,
      ControllableDirectory designatedDirectory) {
    // Switch Working directory and modify the UUID content
    ControllableDirectory oldParent = designatedFile.getParent();
    ControllableDirectory tempWD = workingDirectory;
    workingDirectory = designatedDirectory;
    if (oldParent != null) {
      for (String uuidS : (new String(oldParent.getContent())).split(" ")) {
        if (!uuidS.equals("")) {
          UUID currentUUID = UUID.fromString(uuidS);
          if (!designatedFile.getUUID().equals(currentUUID)) {
            byte[] UUIDByte = (" " + currentUUID.toString()).getBytes();
            workingDirectory.setContent(ConcatenatingTwoByteArray(
                workingDirectory.getContent(), UUIDByte));
          }
        }
      }
    }
    // Set new Parent
    designatedFile.setParent(designatedDirectory);
    // Put UUID in
    designatedDirectory.content =
        ConcatenatingTwoByteArray(designatedDirectory.content,
            (" " + designatedFile.getUUID().toString()).getBytes());
    workingDirectory = tempWD;
  }

  /**
   * This method is for easy operation of copying a file/ directory to another
   * directory. If a directory was given, the copy will be recursive.
   * 
   * @param source the source that is going to be copied
   * @param targetDirectory where the copied file will go
   * @return The copied file instance
   */
  public ControllableFile copyFileTo(ControllableFile source,
      ControllableDirectory targetDirectory) {
    // Make reference of new file
    ControllableFile cf;
    // Save current working directory
    ControllableDirectory tempWKdir = getWorkingDir();
    setWorkingDir(targetDirectory);

    // Decide to do recursive copy for directory or just a file
    if (source.isDirectory()) {
      cf = new ControllableDirectory(source.getName(), targetDirectory);
      // For directory, files under it will also be copied.
      for (UUID singleFileUUID : ((ControllableDirectory) source)
          .getFileUUIDs()) {
        copyFileTo(findFile(singleFileUUID), (ControllableDirectory) cf);
      }
    } else {
      // Just create
      cf = new ControllableFile(source.getName(), targetDirectory,
          source.getContent().clone());
    }
    // Make sure link it to target Directory
    AddFileUnderWD(cf);
    // Set working directory back
    setWorkingDir(tempWKdir);
    return cf;
  }
}
