package com.agwblack.DragonGo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public class GamesList extends Activity {

  public final static String TAG = "DragonGo GamesList";

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
    }
}

