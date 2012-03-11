package com.agwblack.DragonGo;

import org.apache.http.cookie.Cookie;

/** This class represents a user.
  * 
  * Each instance represents a user and his attributes (name, password,
  * cookies, running games, rank, etc)
  **/


public class User {
  
  int _id;
  String _username;
  String _password;
  Cookie _handle_cookie;
  Cookie _session_cookie;
  String _games;

  public User() {
  }

  public User(String username, String password) {
    this._username = username;
    this._password = password;
    System.out.println("User created");
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
    this._id = id;
  }

  public String getUsername() {
    return this._username;
  }

  public void setUsername(String username) {
    this._username = username;
  }

  public String getPassword() {
    return this._password;
  }

  public void setPassword(String password) {
    this._password = password;
  }

  public void setHandleCookie(Cookie cookie) {
    this._handle_cookie = cookie;
  }

  public Cookie getHandleCookie() {
    return this._handle_cookie;
  }

  public void setSessionCookie(Cookie cookie) {
    this._session_cookie = cookie;
  }

  public Cookie getSessionCookie() {
    return this._session_cookie;
  }

  // TODO: add rest of getter and setter methods
  // TODO: It may be that we want to add our utility classes here (e.g. those
  // that extract gameslists from the games strings, etc)
}
