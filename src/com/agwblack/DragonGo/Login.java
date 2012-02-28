package com.agwblack.DragonGo;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

public class Login extends Activity
{
    /** tag for logging purpose */
    private static final String TAG = "***ANDROID_UI_MAIN***";

    private TextView user;
    private TextView passwd;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Log.d(TAG, "CREATED ACTIVITY MAIN");
        user = (TextView) findViewById(R.id.username);
        passwd = (TextView) findViewById(R.id.password);
    }

    /** Tries to login with the given details*/
    boolean login(String username, String password) {
      String[] args = new String[2];
      args[0] = username;
      args[1] = password;
      Message msg = new Message(DGSEnumType.Command.LOGIN, args);
      msg.send();
      if (msg.getResponse().contains("#")) {
        Log.e(TAG, msg.getResponse());
        return false;
      }
      return true;
      /*
      if (username.compareTo("agwblack") == 0 
          && password.compareTo("sp00ner!") == 0) {
        return true;
      } else {
        Log.d(TAG, "Username: " + username + "Password: " + password);
        return false;
      }
      */
    }

    /** Called when we click the button specified in the layout main.xml*/
    public void launchListActivity(View view) {
      Log.d(TAG, "BUTTON PRESSED!!");
      if (login(user.getText().toString(), passwd.getText().toString())) {
        Intent intent = new Intent(Login.this, GamesList.class);
        startActivity(intent);
      } else {
        Toast failed =Toast.makeText(getApplicationContext(), "Bad username or password",
          Toast.LENGTH_SHORT);
        failed.show();
      }
    }

}
