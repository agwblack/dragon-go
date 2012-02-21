// Main class

public class GetMessage {
  /*
   * Must be called before any other command
   *
   * \param username
   * \param password
   */
  static void login(String username, String password) {
    String[] args = new String[2];
    args[0] = username;
    args[1] = password;
    Message msg = new Message(DGSEnumType.Command.LOGIN, args);
    msg.send();
  }

  /*
   * Method to get the status of the logged in user
   */
  static void getStatus() {
    String[] args = new String[0];
    Message msg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
    msg.send();
  }

  /*
   * Method to get the status of the spcified user
   */
  static void getStatus(String username) {
    String[] args = new String[1];
    args[0] = username;
    Message msg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
    msg.send();
    System.out.println(msg.getResponse());
  }

  /*
   * Method to download an sgf
   *
   * \param sgf - name/number of sgf file to be downloaded
   */
  static void downloadGame(String sgf) {
    String[] args = new String[1];
    args[0] = sgf;
    Message msg = new Message(DGSEnumType.Command.DOWNLOAD_GAME, args);
    msg.send();
  }

  /*
   * Main function
   */
  public static void main(String[] args) {
    login("agwblack", "sp00ner!");
    getStatus();
    getStatus("agwblack");
    downloadGame("710891");
  }
}

