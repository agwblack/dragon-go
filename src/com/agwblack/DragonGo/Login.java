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
    private static final String TAG = "DragonGo Login";

    private TextView user;
    private TextView passwd;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (TextView) findViewById(R.id.username);
        passwd = (TextView) findViewById(R.id.password);
    }

    /** Tries to login with the given details*/
    DGSEnumType.Error login(String username, String password) {
      String[] args = new String[2];
      args[0] = username;
      args[1] = password;
      Message msg = new Message(DGSEnumType.Command.LOGIN, args);
      DGSEnumType.Error error = msg.send();
      if (error == DGSEnumType.Error.NONE) {
        // Parse the result
        if (msg.getResponse().contains("#Error")) {
          // something went wrong
          Log.e(TAG, msg.getResponse());
          error = DGSEnumType.Error.BAD_LOGIN_DETAILS;
        } else {
          // We have a valid user!
        }
      } else {
        // We could not find the server
      } 
      return error;
    }

    /** Called when we click the button specified in the layout main.xml*/
    public void launchListActivity(View view) {
      DGSEnumType.Error error = DGSEnumType.Error.NONE;
      error = login(user.getText().toString(), passwd.getText().toString());
      if (error == DGSEnumType.Error.NONE) {
        // Login OK - Load the next activity
        Intent intent = new Intent(Login.this, GamesList.class);
        startActivity(intent);
      } 
      else if (error == DGSEnumType.Error.BAD_LOGIN_DETAILS) {
        Toast failed = Toast.makeText(getApplicationContext(), "Bad username or password",
          Toast.LENGTH_SHORT);
        failed.show();
      }
      else if (error == DGSEnumType.Error.CANNOT_FIND_SERVER) {
        Toast failed = Toast.makeText(getApplicationContext(), 
            "Cannot find server - are you connected to the Internet?",
          Toast.LENGTH_SHORT);
        failed.show();
      }
    }

}
