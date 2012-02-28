package com.agwblack.DragonGo;

/*
 * Class that holds the SGF information for a game.
 * For now this just holds SGF info and accessor methods, but we may add more
 * as it becomes necessary.
 */

public class Sgf {
  String sgfcontents;

  Sgf(String sgf) {
    sgfcontents = new String(sgf);
  }

  public String getSgfContents() {
    return sgfcontents;
  }

  public void setSgfContents(String sgf) {
    sgfcontents = sgf;
  }
}
