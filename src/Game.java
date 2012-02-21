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
  boolean ongoing;
  boolean immediate;
  /*Opponent class*/

  /*Constructor*/

  /*Accessor methods*/
}
