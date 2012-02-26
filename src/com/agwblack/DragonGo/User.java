/*
 * Class that represents the User. Contains information like username, password
 * and games lists
 */

public class User {
  String username;
  String id;
  String password;
  GameList[] gameLists;

  /* 
   * Constructor should initialise the games lists and then update them
   */
  User(String name, String passwd) {
    username = new String(name);
    password = new String(passwd);

    // needs exception handling
    login();
    initialiseLists();
  }

  /*
   * Must be called before any other command
   *
   * \param username
   * \param password
   */
  void login() {
    String[] args = new String[2];
    args[0] = username;
    args[1] = password;
    Message msg = new Message(DGSEnumType.Command.LOGIN, args);
    msg.send();
  }

  void initialiseLists() {
    gameLists = new GameList[4];
    // If we decide to implement GameList as a base class/interface then the
    // following will have to change - this is probably a good idea.
    gameLists[0] = new GameList("Immediate");
    gameLists[1] = new GameList("Ongoing");
    gameLists[2] = new GameList("Finished");
    gameLists[3] = new GameList("Misc");
  }

  /*
   * Method to update all of a User's game lists
   */
  void updateLists() {
    int i = 0;
    for (i = 0; i <= gameLists.length; ++i) {
      gameLists[i].update();
    }
  }

}
