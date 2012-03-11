package com.agwblack.DragonGo;

import java.io.IOException;

/**
 * Class that contains everything pertaining to a particular game.
 * This includes sgf, opponent info, the last move made, the DGS specific game
 * id, etc.
 *
 * We may need to subclass this so we can have subtly different classes for
 * ongoing games, finished games, misc games, etc. For example, we wouldn't
 * want a to be able to use methods to play a move on finished games.
 *
 * TODO: Decide which methods we need other than parseStatus and accessor
 * methods. Perhaps just a method to play moves, and download sgf.
 */

public class Game {
  String dgsID;
  Opponent opponent;
  Date timeOfLastMove;
  String timeSystem;
  String timeRemaining;
  String userColour;
  int handicap;
  int komi;
  int moveNumber;

  /** Constructor */
  public Game(String status) {
    parseStatus();
  }

  /** Takes the status line returned from the server and breaks it up into its
   * component parts */
  private void parseStatus(String status) {
    String[] s = status.split(", ");
    dgsID = new String(s[1]);
    Opponent = new Opponent(s[2]);
    userColour = new String(s[3]);
    timeOfLastMove = new String(s[4]);
    timeRemaining = new String(s[5]);
  }
  
  /**
   * Method to retrieve a game from the server
   */
  void retrieveSgf(String gameID) {
    String[] args = new String[1];
    args[0] = gameID;
    Message msg = new Message(DGSEnumType.Command.DOWNLOAD_GAME, args);
    msg.send();
    sgf = new Sgf(msg.getResponse());
  }

  /** - Not sure we need this method in here...
   * Method to parse an sgf and distribute its contents among the members of
   * the game group
   */
  void parseSgf() {
    if (sgf == null) {
      // we failed to retrieve sgf before parsing it
    }
  }

  /*Accessor methods*/
  int getLastMoveNumber() {
    return moves.length;
  }

  Opponent getOpponent() {
    return opponent;
  }

  String getDgsId() {
    return dgsID;
  }

  /*play move method*/
  void playMove(Move move) {
    // Check move is legal
    // Create message from move
    // send Message
    // If sent successfully, add move to Move[]
  }
}
