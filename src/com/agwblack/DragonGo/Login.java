package com.agwblack.DragonGo;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

import  org.apache.http.cookie.Cookie;
import  org.apache.http.impl.cookie.BasicClientCookie;

public class Login extends Activity
{
    /** tag for logging purpose */
    private static final String TAG = "DragonGo Login";

    private TextView usern;
    // FIXME: obfuscate password in TextView
    private TextView passwd;
    private DatabaseHandler db;
    private User user;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        db = new DatabaseHandler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usern = (TextView) findViewById(R.id.username);
        passwd = (TextView) findViewById(R.id.password);
    }

    /** Tries to login with the given details */
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
          // We have a valid user. Add them and their details to the database
          user = new User(username, password);
          db.addUser(user);
          System.out.println("User has initial id: " + user.getID());

          // Get cookies from message
          Cookie[] cookies = msg.getCookies();

          // Add cookies to database (DGS logins currently require 2 cookies)
          db.addHandleCookie(user, cookies[0]);
          db.addSessionCodeCookie(user, cookies[1]);

          // display cookies in log
          for (int i = 0; i != cookies.length; ++i) {
            Log.i(TAG, cookies[i].toString());
          }
        }
      } else {
        // We could not find the server
      } 
      return error;
    }

    /** Called when we click the button specified in the layout main.xml*/
    public void launchListActivity(View view) {
      DGSEnumType.Error error = DGSEnumType.Error.NONE;
      // FIXME: We need to ensure we strip any spaces out of the username or
      // password entries as they cause an exception to be thrown. We shouldn't
      // catch this as a BAD_LOGIN_DETAILS error since users will not necessarily 
      // notice the problem.
      error = login(usern.getText().toString(), passwd.getText().toString());

      if (error == DGSEnumType.Error.NONE) {
        // Login OK - Load the next activity
        Intent intent = new Intent(Login.this, GamesList.class);
        // Pass the database ID of the logged in user to the next activity
        intent.putExtra("userId", user.getID());
        startActivity(intent);
      } 

      // FIXME: These toasts are too small and don't persist long enough
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
