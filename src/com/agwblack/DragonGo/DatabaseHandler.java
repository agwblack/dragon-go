package com.agwblack.DragonGo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

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
    + " text not null, " + COLUMN_PASSWORD + " text not null);";

  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    // This function is only called if the constructor could not find the
    // database to open
    database.execSQL(DATABASE_CREATE);
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
  public void addUser(User user) {
    // open the database for writing
    SQLiteDatabase db = this.getWritableDatabase();

    // Set up the values we wish to add
    ContentValues values = new ContentValues();
    values.put(COLUMN_USERNAME, user.getUsername());
    values.put(COLUMN_PASSWORD, user.getPassword());

    // Insert values into the database and close it
    db.insert(TABLE_USERS, null, values);
    db.close();
  }

  public User getUser(int id) {
    // FIXME: Might we have problems if cursor is null? Need exception handling
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_USERS, 
        new String[] { COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD }, 
        COLUMN_ID + "=?",
        new String[] {String.valueOf(id)}, null, null, null, null);

    if (cursor != null)
      cursor.moveToFirst();

    User user = new User(Integer.parseInt(cursor.getString(0)), 
        cursor.getString(1), cursor.getString(2));

    return user;
  }

  public List<User> getAllUsers() {
    List<User> userList = new ArrayList<User>();

    String selectQuery = "SELECT * FROM " + TABLE_USERS;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
      do {
        User user = new User();
        user.setID(Integer.parseInt(cursor.getString(0)));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        userList.add(user);
      } while (cursor.moveToNext());
    }

    return userList;
  }

  public int getUsersCount() {
    String countQuery = "SELECT * FROM " + TABLE_USERS;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.close();

    return cursor.getCount();
  }

  public int updateUser(User user) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_USERNAME, user.getUsername());
    values.put(COLUMN_PASSWORD, user.getPassword());

    return db.update(TABLE_USERS, values, COLUMN_ID + "=?",
        new String[] { String.valueOf(user.getID()) });
  }

  public void deleteUser(User user) {
    SQLiteDatabase db = this.getWritableDatabase();

    db.delete(TABLE_USERS, COLUMN_ID + "=?", 
        new String[] { String.valueOf(user.getID()) });
    db.close();
  }

}
