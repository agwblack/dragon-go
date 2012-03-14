package com.agwblack.DragonGo;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;
import android.os.AsyncTask;

import  org.apache.http.cookie.Cookie;
import  org.apache.http.impl.cookie.BasicClientCookie;

import com.actionbarsherlock.app.SherlockActivity;

// TODO: 
// Automatically login!
// Fix landscape/portrait appearance
public class Login extends SherlockActivity
{
    /** tag for logging purpose */
    private static final String TAG = "DragonGo Login";

    /** For use in decing which colour buttons to draw etc.*/
    public static int THEME = R.style.Theme_Sherlock;


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


    /**
     *  Class which runs in separate thread which handle login process 
     */
    private class LoginTask extends AsyncTask<String, Void, DGSEnumType.Error> {
      
      protected DGSEnumType.Error doInBackground(String... credentials) {
        return login(credentials[0], credentials[1]);
      }

      /** Tries to login with the given details */
      DGSEnumType.Error login(String username, String password) {
        Message msg = new Message(DGSEnumType.Command.LOGIN, username, password);
        DGSEnumType.Error error = msg.send();
        if (error == DGSEnumType.Error.NONE) {
          // Parse the result
          if (msg.getResponse().contains("#Error")) {
            // something went wrong
            Log.e(TAG, msg.getResponse());
            error = DGSEnumType.Error.BAD_LOGIN_DETAILS;
          } else {
            // No problem, create user in main thread.
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
              Log.i(TAG, cookies[i].toString());
            }
          }
        } else {
          // We could not find the server
        } 
        return error;
      }

      /** Method launched in main thread as soon as we start the worker thread
       */
      protected void onPreExecute() {
        // display indefinited progress bar
      }
      
      /** Method launched in main thread once the doInBackground method 
       * returns
       */
      protected void onPostExecute(DGSEnumType.Error error) {
        // stop progress bar

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


    /** Called when we click the button specified in the layout main.xml.
     * */
    public void launchListActivity(View view) {
      // launch the logging in process in a new thread.
      new LoginTask().execute(usern.getText().toString(), passwd.getText().toString());
    }

}
