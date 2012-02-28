package com.agwblack.DragonGo;

// Main class

public class GetMessage {

  /*
   * Method to get the status of the logged in user
   */
  static void getStatus() {
    String[] args = new String[0];
    Message msg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
    //msg.send();
  }

  /*
   * Method to get the status of the spcified user
   */
  static void getStatus(String username) {
    String[] args = new String[1];
    args[0] = username;
    Message msg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
    //msg.send();
    System.out.println(msg.getResponse());
  }

  /*
   * Main function
   */
  public static void main(String[] args) {
    User user = new User("agwblack", "sp00ner!");
  }
}

