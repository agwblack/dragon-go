/* Class which gathers and holds information about games in a particular
 * category
 */
public class GameList {
  String category;
  Game[] games;

  GameList(String cat) {
    category = new String(cat);
    update();
  }

  /* Method to retrieve relevant games from the server. It might be best if
   * this was a pure virtual method of a base class or interface, since
   * different lists will want to have different methods for doing this (i.e.
   * different messages to send, different methods for parsing the output,
   * etc.)
   */
  void update() {
    System.out.println("Updating GameList " + category);
    /* The following is how to update the immediate games list, since that is
     * most important and easiest to do first */

    // getStatus();

    // parseStatus(); to get current list

    // compareList(); compare to previous list - might have new games, might 
    // have fewer games (if we played a move), might have no change in some 
    // games, might have the same games but a move further on (although this 
    // should not happen if we update the list after playing a move).
    
    // Depending on the outcome of compareList(), we may need to addGame();
    // removeGame(); updateGame(); moveGame() to a different list; copyGame() to a
    // different list. These should be directly called from compareList()
  }

  // Methods referenced from update() method
  /*
  void retrieveStatus();
  void parseStatus();
  void compareList();
  void addGame();
  void removeGame();
  void copyGame();
  void moveGame();
  void updateGame();
  */

  /*
   * Method to return a string array containing the names of the opponents for
   * each game in the list
   */
  String[] getGameListString() {
    String[] list = new String[games.length];
    int i;
    for (i = 0; i <= games.length; ++i) {
      list[i] = games[i].getOpponent().getName();
    }
    return list;
  }

  String getCategory() {
    return category;
  }
}
