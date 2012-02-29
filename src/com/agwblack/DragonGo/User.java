package com.agwblack.DragonGo;

/**
 * Class that represents the User. Contains information like username, password
 * and Session cookie. The User is the reference point for accessing and
 * updating Game Lists
 *
 * So that is can be passed between Activities, we need this class to implement
 * the Parcelable interface. An alternative would be to save the user infor to
 * the database and just pass the username between activities. We can then use
 * this to access the database to retrieve status, gamesLists, etc.
 */

public class User {
  String username;
  String id;
  String password;
  //GameList[] gameLists;

  /**
   * We should only create a User once we have managed to successfully login
   * with their details.
   *
   * We may want to pass the constructor an HttpContext with the relevant
   * cookies from the login.
   *
   * We should store these details in a database so that they can be accessed
   * easily offline, and without us having to login everytime
   */
  User(String name, String passwd) {
    username = new String(name);
    password = new String(passwd);
  }


  /** Not sure if these functions should be implemented - Should the GameList
   * array be owned by the User or by the GamesList Activity which owns the
   * User?
   
  void initialiseLists() {
    gameLists = new GameList[4];
    // If we decide to implement GameList as a base class/interface then the
    // following will have to change - this is probably a good idea.
    gameLists[0] = new GameList("Immediate");
    gameLists[1] = new GameList("Ongoing");
    gameLists[2] = new GameList("Finished");
    gameLists[3] = new GameList("Misc");
  }

  void updateLists() {
    int i = 0;
    for (i = 0; i <= gameLists.length; ++i) {
      gameLists[i].update();
    }
  }

  */
}
