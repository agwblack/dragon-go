package com.agwblack.DragonGo

/** This class represents a user.
  * 
  * Each instance represents a user and his attributes (name, password,
  * cookies, running games, rank, etc)
  **/

// Eventually this class should be User.java

public class User {
  
  int _id;
  String _username;
  String _password;
  String _first_cookie;
  String _second_cookie;
  String _games;

  public User() {
  }

  public User(int id, String username, String password) {
    this._id = id;
    this._username = username;
    this._password = password;
  }

  public int getID() {
    return this._id;
  }

  public void setID(int id) {
    this._id;
  }

  public String getUsername() {
    return this._username;
  }

  public void setUsername(String username) {
    this._username = username;
  }

  // TODO: add rest of getter and setter methods
  // TODO: It may be that we want to add our utility classes here (i.e. those
  // that extract Cookies from the cookie strings, gameslists from the games
  // strings, etc)
}
