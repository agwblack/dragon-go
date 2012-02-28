package com.agwblack.DragonGo;

/*
 * A class that captures the essence of a move on the board, i.e. its
 * coordinates, and which colour made it.
 *
 * Note that we use the sgf formatting for board positions, i.e top left is
 * 'aa' rather than 'a1'.
 */
public class Move {
  /* If true, then it is a black move. Otherwise it is a white move*/
  boolean colourBlack;
  char xPosition;
  char yPosition;
  boolean taken;

  /*Constructor is rather self-explanatory*/
  Move(char xPos, char yPos, boolean black) {
    xPosition = xPos;
    yPosition = yPos;
    colourBlack = black;
    taken = false;
  }

  /*
   * Method to call when a stone is taken
   */
  void stoneTaken() {
    taken = true;
  }
}
