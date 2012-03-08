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

          // Get cookies from message
          Cookie[] cookies = msg.getCookies();

          // Add cookies to database (DGS logins currently require 2 cookies)
          db.addHandleCookie(user, cookies[0]);
          db.addSessionCodeCookie(user, cookies[1]);

          // display cookies in log
          for (int i = 0; i != cookies.length; ++i) {
            System.out.println(cookies[i].toString());
          }

          /** Move all of the following into DatabaseHandler or GamesList as
           * appropriate.

           * Temporary: Attempt to grab the cookie,deconstruct it, reconstruct
           * it and perform a getStatus request - Eventually this will have the
           * database as an intermediate step in this, so we can access the
           * data from anywhere 

          // Create new cookies from the information we got from those we just
          // downloaded
          BasicClientCookie cookie = new BasicClientCookie(cookies[0].getName(), cookies[0].getValue());
          cookie.setVersion(cookies[0].getVersion());
          cookie.setDomain(cookies[0].getDomain());
          cookie.setPath(cookies[0].getPath());
          cookie.setExpiryDate(cookies[0].getExpiryDate());
          System.out.println(cookie.toString());

          BasicClientCookie cookie2 = new BasicClientCookie(cookies[1].getName(), cookies[1].getValue());
          cookie2.setVersion(cookies[1].getVersion());
          cookie2.setDomain(cookies[1].getDomain());
          cookie2.setPath(cookies[1].getPath());
          cookie2.setExpiryDate(cookies[1].getExpiryDate());
          System.out.println(cookie2.toString());

          // Use our new cookies to get status without specifying the user
          // (since that information is contained within the cookies)
          String[] args2 = new String[0];
          Message statMsg = new Message(DGSEnumType.Command.QUICK_STATUS, args);
          statMsg.setCookies(cookie);
          statMsg.setCookies(cookie2);
          statMsg.send();
          System.out.println(statMsg.getResponse());
          */

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
        // We pass the username in to the next activity so we can access the
        // login cookie from the database once we are in the next activity
        // FIXME: Change this so we pass the user ID rather than the username
        intent.putExtra("userName", usern.getText().toString());
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
