/* 
 * Class that contains everything pertaining to a particular game.
 * This includes sgf, opponent info, the last move made, the DGS specific game
 * id, etc.
 *
 * We may need to subclass this so we can have subtly different classes for
 * ongoing games, finished games, misc games, etc. For example, we wouldn't
 * want a to be able to use methods to play a move on finished games.
 */

public class Game {
  String dgsID;
  Sgf sgf;

  /* These may not be necessary if we are distinguishing the type of game
   * according to what category of list it belongs to.
   */
  boolean ongoing;
  boolean immediate;
  
  boolean userIsBlack;
  Move[] moves;
  Opponent opponent;

  /*Constructor - may need to be overloaded*/
  Game(String gameID, Opponent op) {
    retrieveSgf(gameID);
    // Once we have the SGF we need to parse it to get the information we are
    // after
    parseSgf();
  }
  
  /*
   * Method to retrieve a game from the server
   */
  void retrieveSgf(String gameID) {
    String[] args = new String[1];
    args[0] = gameID;
    Message msg = new Message(DGSEnumType.Command.DOWNLOAD_GAME, args);
    msg.send();
    sgf = new Sgf(msg.getResponse());
  }

  /*
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

  /*play move method*/
  void playMove(Move move) {
    // Check move is legal
    // Create message from move
    // send Message
    // If sent successfully, add move to Move[]
  }
}
