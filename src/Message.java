
class Message {
  ApacheHttpClient client = new ApacheHttpClient();
  private String address = new String("http://www.dragongoserver.net/");

  /*
   * Creates a new message to send to the server.
   * The size of the args parameter may determine what kind of message gets
   * sent.
   *
   * \param command - specifies the type of message we wish to send
   * \param args - gives additional arguments to the message
   */
  Message(DGSEnumType.Command command, String[] args) {
    switch (command) {
      case QUICK_STATUS:
        address += "quick_status.php";
        if (args.length == 1) {
          address += "?user=" + args[0];
        }
        break;
      case LOGIN:
        address += "login.php?quick_mode=1&userid=" + args[0] + "&passwd="
          + args[1];
        break;
      case DOWNLOAD_GAME:
        // need to decide how to deal with owned_comments parameter
        address += "sgf.php?gid=" + args[0];
        address += "&quick_mode=1";
        break;
      case QUICK_PLAY:
        address += "quick_play.php?gid=" + args[0] + "&color=" 
          + args[1] + "sgf_prev=" + args[2] + "&board_move=" + args[3];
        if (args.length == 5) {
          address += "&message=" + args[4];
        }
        break;
      default:
    }
  }

  /*
   * Sends the message to the server
   */
  public void send() {
    System.out.println("Sending http request to " + address);
    client.sendMessage(address);
  }
}
