package com.agwblack.DragonGo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

/**
 * Database Handler class.
 *
 * We are required to create this class in order to access the SQLite database.
 **/

public class DatabaseHandler extends SQLiteOpenHelper {

  // Constants
  private static final String DATABASE_NAME = "users.db";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_USERS = "users";
  private static final String COLUMN_ID = "_id";
  private static final String COLUMN_USERNAME = "username";
  private static final String COLUMN_PASSWORD = "password";

  // Maybe we can have a second table that deals with cookies, linked to the
  // users by id (using sql JOINs) - each table can be thought of as an object
  // (and needs an appropriate implementation). This implementation uses 
  // cookies as semi-colon separated strings which we extract with a suitable 
  // utility method
  private static final String COLUMN_FIRST_COOKIE = "cookie1";
  private static final String COLUMN_SECOND_COOKIE = "cookie2";

  // Likewise, we may wish to have games as a separate table, or alternatively
  // just represented by a semi-colon separated string in the user table
  private static final String COLUMN_ACTIVE_GAMES = "activegames";
  // Database creation statement
  private static final String DATABASE_CREATE = "create table " + TABLE_USERS +
    "( " + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_USERNAME
    + " text not null);";

  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    // This function is only called if the constructor could not find the
    // database to open
    database.execSQL(DATABASE_CREATE)
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(DatabaseHandler.class.getName(), "Upgrading from version " + oldVersion + 
        " to " + newVersion + ", which may destory all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    onCreate(db);
  }

  // Methods to interact with database (CRUD operations)
  // Clearly we will need to reevaluate some of these methods, currently this
  // is just following:
  // http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
  public void addUser(User user) {}

  public User getUser(int id) {}

  public List<User> getAllUsers() {}

  public int getUsersCount() {}

  public void updateUser(User user) {}

  public void deleteUser(User user) {}

}
