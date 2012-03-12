package com.agwblack.DragonGo;

/*
 * Class that represents the opponent in a game. Contains obvious information
 * like their username, rank and id. We should add whatever information we can
 * get about the opponent.
 */
public class Opponent {
  String _name;
  String _id;
  String _rank;

  /*Constructor*/
  public Opponent(String id) {
    this._id = id;
  }

  public String getID() {
    return this._id;
  }

  String getName() {
    return this._name;
  }
}
