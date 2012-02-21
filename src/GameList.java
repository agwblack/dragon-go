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
  }

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
