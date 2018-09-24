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

import java.util.Date;
import java.util.UUID;

/**
 * File implementation of instances for ManagementOfControllableKernel(MOCK as
 * short) File system, using byte stream to store data
 * 
 * @author wangq150
 */
public class ControllableFile extends Object {

  protected ControllableDirectory parentDirectory;
  protected byte[] content;
  protected UUID uuid;
  protected String name;


  /**
   * Easier way to determine if this is a file
   * 
   * @return this is a directory or not
   */
  public boolean isDirectory() {
    return false;
  }

  // Constructor for ControllableDirectory to get compiled
  protected ControllableFile() {}

  /**
   * Constructor for ControllableFile, initializing with Parent
   * 
   * @param name of this file
   * @param the directory that this file is under
   */
  public ControllableFile(String name, ControllableDirectory parent) {
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
   * Constructor for ControllableFile, initializing with Parent and content
   * 
   * @param name of this file
   * @param the directory that this file is under
   * @param content of this file
   */
  public ControllableFile(String name, ControllableDirectory parent,
      String content) {
    // Use the upper constructor to reduce repeated code
    this(name, parent);
    // Set content as stream bytes
    this.content = content.getBytes();
  }

  /**
   * Constructor for ControllableFile, initializing with Parent and byte content
   * 
   * @param name of this file
   * @param the directory that this file is under
   * @param content of this file in byte stream
   */
  public ControllableFile(String name, ControllableDirectory parent,
      byte[] content) {
    // Use the upper constructor to reduce repeated code
    this(name, parent);
    // Set content as stream bytes
    this.content = content;
  }

  /**
   * @return the byte stream of content in this file instance
   */
  public byte[] getContent() {
    return content;
  }

  /**
   * @param new contents that are to be written in this file instance
   */
  public void setContent(byte[] newContent) {
    content = newContent;
  }

  public UUID getUUID() {
    return uuid;
  }

  /**
   * Set a new UUID for this file instance
   */
  protected void setUUID() {
    // Get current time
    Date now = new Date();
    // Initialize a new UUID
    uuid = new UUID(now.getTime(), this.hashCode());
  }

  /**
   * @return the directory that this file is under
   */
  public ControllableDirectory getParent() {
    return parentDirectory;
  }

  /**
   * @param set this file's parent to a new directory
   */
  public void setParent(ControllableDirectory parent) {
    parentDirectory = parent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
