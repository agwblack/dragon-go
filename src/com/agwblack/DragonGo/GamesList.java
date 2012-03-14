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
import android.os.AsyncTask;

import java.util.*;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


//TODO: 
//Optimise calls to database (very expensive)
//FIXME: 
//  We recreate the list of games everytime we refresh - not very efficient
//    Perhaps we should allocate it at the beginning, only recreating a game if
//    something about it has changed?
public class GamesList extends SherlockListActivity {

  public final static String TAG = "DragonGo GamesList";
  
  List<String> _games = new ArrayList<String>(); 
  
  ArrayAdapter<String> _arrayAdapter;

  List<Game> _gamesList;
  DatabaseHandler _db;
  User _user;

  /** Method to be overridden for our menu.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //Used to put dark icons on light action bar
    boolean isLight = Login.THEME == R.style.Theme_Sherlock_Light;

    /*
    menu.add("Save")
      .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    menu.add("Search")
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    */
    menu.add("Refresh")
      .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
      .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

    return true;
  }


  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      _db = new DatabaseHandler(this);

      // Get User
      _user = _db.getUser(getIntent().getExtras().getInt("userId"));

      _arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, _games);
      setListAdapter(_arrayAdapter);
      getListView().setTextFilterEnabled(true);

      /** This sets the behaviour for when we click on a list item - Obviously
       * needs changing later */
     getListView().setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
          // When clicked, show a toast with the TextView text
          Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
              Toast.LENGTH_SHORT).show();
        }
      });

      new RefreshTask().execute();
      //update();
    }

  /** Class responsible for refreshing the lists */
  class RefreshTask extends AsyncTask<Void, Void, DGSEnumType.Error> {

    protected DGSEnumType.Error doInBackground(Void... v) {
      Log.i(TAG, "Refreshing...");
      return update();
    }

    /** Method to update the games array */
    private DGSEnumType.Error update() {
      // update games array using QUICK_STATUS message
      String[] args = new String[0];
      Message statMsg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
      statMsg.setCookies(_user.getHandleCookie());
      statMsg.setCookies(_user.getSessionCookie());
      statMsg.send();
      String response = new String(statMsg.getResponse());
      System.out.println(response);
    
      // parse response
      // FIXME: Check for errors from the server. Without doing this we run this
      // risk of the app crashing (e.g. #Warning: empty lists)
      /** Temp fix - cleanup */
      if (response.contains("#Warning")) {
        // no games
        _gamesList = new ArrayList<Game>();
      } else {
        _gamesList = parseStatus(response);
      }
    
      // update string list for displaying
      _games.clear();
      for (int i = 0; i != _gamesList.size(); ++i) {
        String label = _gamesList.get(i).getDgsId() +
            _gamesList.get(i).getOpponent().getID();
        _games.add(label);
      }
      //FIXME: Needs to properly return errors
      return DGSEnumType.Error.NONE;
    } 

    /**
    * Parses the response from the status request and returns a list of games
    */
    private List<Game> parseStatus(String status) {
      List<Game> games = new ArrayList<Game>();
      // break status string up into lines.
      String lines[] = status.split("\\r?\\n");
      // for each line, create game and add to list
      for (int i = 0; i != lines.length; ++i) {
        System.out.println("creating game: " + lines[i]);
        Game game = new Game(lines[i]);
        games.add(game);
      }
      // return list
      return games;
    }

    protected void onPreExecute() {
      //show progess bar
    }

    protected void onPostExecute(DGSEnumType.Error error) {
      // stop progress bar
      // handle errors appropriately
      // checking we add games to array
      Log.i(TAG, "Size of games list = " + _games.size());
      _arrayAdapter.notifyDataSetChanged();
    }
  }

  /** In order to refresh status when refresh button is pressed.
   *  FIXME:Fairly confident that this is no the right way to do this
   */
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getTitle() == "Refresh") {
      new RefreshTask().execute();
    }
    return true;
  }

}
