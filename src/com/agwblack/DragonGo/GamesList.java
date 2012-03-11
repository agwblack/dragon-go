package com.agwblack.DragonGo;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.view.View;
import android.util.Log;

import java.util.*;

//FIXME: Combined with the DatabaseHandler class. We need to optimise so we
//make as few calls as possible to the database. These calls are expensive.
public class GamesList extends ListActivity {

  public final static String TAG = "DragonGo GamesList";
  /** Temporary list for testing - Need to actually import this list from the
   * Status request and the database */
  List<String> games = new ArrayList<String>(); 
  
  ArrayAdapter<String> arrayAdapter;

  List<Game> gamesList;
  DatabaseHandler db;
  User user;

  //GameList immediate;

  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      db = new DatabaseHandler(this);

      // Get User
      System.out.println("User id at start of gameslist activity: " + getIntent().getExtras().getInt("userId"));
          user = db.getUser(getIntent().getExtras().getInt("userId"));

      arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, games);
      setListAdapter(arrayAdapter);
      ListView lv = getListView();
      lv.setTextFilterEnabled(true);

      /** These are placeholders and should be removed 
        * We populate the list either from the database or from status requests
        *
      games.add("game1");
      games.add("game2");
      games.add("game3");
      */

      /** This sets the behaviour for when we click on a list item - Obviously
       * needs changing later */
      lv.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
          // When clicked, show a toast with the TextView text
          Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
              Toast.LENGTH_SHORT).show();
        }
      });


      update();
      /** Temp method to test database  -  remove */
      readDatabase();
    }

  /** Method to update the games array */
  void update() {
    // update games array using QUICK_STATUS message
    String[] args = new String[0];
    Message statMsg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
    statMsg.setCookies(user.getHandleCookie());
    statMsg.setCookies(user.getSessionCookie());
    statMsg.send();
    String response = new String(statMsg.getResponse());
    System.out.println(response);
    
    // parse response
    gamesList = parseStatus();
    
    // update string list for displaying
    for (int i = 0; i != gamesList.size(); ++i) {
      games.add(gamesList.get(i).getDgsId());
    }

  }

  /**Just for testing database - DELETE THIS METHOD */
  void readDatabase() {
    Log.d(TAG, "Reading all users");
    List<User> users = db.getAllUsers();

    for (User us : users) {
      String log = "id: " + us.getID() + ", username: " + us.getUsername()
        + ", password: " + us.getPassword();
      Log.d(TAG, log);
    }
  }

  /**
   * Parses the response from the status request and returns a list of games
   */
  private List<Game> parseStatus(String status) {
    List<Game> games = new List<Games>();
    // break status string up into lines.
    String lines[] = String.split("\\r?\\n");
    // for each line, create game and add to list
    for (int i = 0; i != lines.length; ++i) {
      System.out.println("creating game: " + lines[i]);
      Game game = new Game(lines[i]);
      games.add(game);
    }
    // return list
    return games;
  }
}
