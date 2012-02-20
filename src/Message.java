
class Message {
  ApacheHttpClient client = new ApacheHttpClient();
  private String address = new String("http://www.dragongoserver.net/");

  Message(DGSEnumType.Command command) {
    switch (command) {
      case QUICK_STATUS:
        address += "quick_status.php?user=agwblack";
        break;
      case LOGIN:
        address += "?login.php&quick_mode=1";
        break;
      default:
    }
  }

  public void send() {
    System.out.println("Sending http request to " + address);
    client.sendMessage(address);
  }
}
