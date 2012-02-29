package com.agwblack.DragonGo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public class GamesList extends Activity {

  public final static String TAG = "DragonGo GamesList";

  //GameList immediate;

  @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.gameslist);
      TextView display = (TextView) findViewById(R.id.user);
      String name = getIntent().getExtras().getString("userName");
      if (name != null) {
        String message = "Hello " + name;
        display.setText(message);
      } else {
        Log.w(TAG, "Couldn't get username");
      }

      // get user session cookie 

      // Now we need to populate the gamesLists
      // First we just try to get running games
      // This involves a quick status request which we can then parse to get
      // the list.

      // immediate.update(sessionCookie);


    }

  
}

