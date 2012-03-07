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

public class GamesList extends ListActivity {

  public final static String TAG = "DragonGo GamesList";
  /** Temporary list for testing - Need to actually import this list from the
   * Status request and the database */
  List<String> games = new ArrayList<String>(); 
  ArrayAdapter<String> arrayAdapter;
  DatabaseHandler db;

  //GameList immediate;

  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      db = new DatabaseHandler(this);

      arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, games);
      //setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, games));
      setListAdapter(arrayAdapter);
      ListView lv = getListView();
      lv.setTextFilterEnabled(true);

      games.add("game1");
      games.add("game2");
      games.add("game3");

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
      //arrayAdapter.add(getIntent().getExtras().getString("userName"));
      /** Temp method to test database  -  remove */
      readDatabase();
    }

  /** Method to update the games array */
  void update() {
    /** example */
    arrayAdapter.add(getIntent().getExtras().getString("userName"));
    // get user session cookie from database
    // update games array using QUICK_STATUS message

    /** Testing database - Delete this! */
    Log.d(TAG, "Inserting into Database");
    db.addUser(new User("andy", "passw"));
    db.addUser(new User("dave", "saxophone"));
    db.addUser(new User("sammy", "blimmin7687"));

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
}
