// Main class

public class GetMessage {
  public static void main(String[] args) {
    Message msg = new Message(DGSEnumType.Command.QUICK_STATUS);
    msg.send();
    Message msg2 = new Message(DGSEnumType.Command.LOGIN);
    //msg2.send();
  }
}

