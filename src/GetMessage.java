// Main class

public class GetMessage {
  static void login(String username, String password) {
    String[] args = new String[2];
    args[0] = username;
    args[1] = password;
    Message msg = new Message(DGSEnumType.Command.LOGIN, args);
    msg.send();
  }

  static void getStatus() {
    String[] args = new String[0];
    Message msg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
    msg.send();
  }


  public static void main(String[] args) {
    login("agwblack", "sp00ner!");
    getStatus();
  }
}

