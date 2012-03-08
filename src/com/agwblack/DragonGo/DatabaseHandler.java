package com.agwblack.DragonGo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import  org.apache.http.cookie.Cookie;
import  org.apache.http.impl.cookie.BasicClientCookie;

import java.util.List;
import java.util.ArrayList;

/**
 * Database Handler class.
 *
 * We are required to create this class in order to access the SQLite database.
 **/

// TODO: Tidy this class up and comment properly
//       Add cookie table and add cookie ids to user table

public class DatabaseHandler extends SQLiteOpenHelper {

  // Constants for creating database
  private static final String DATABASE_NAME = "users.db";
  private static final int DATABASE_VERSION = 1;
  
  // Constants for creating Users table
  private static final String TABLE_USERS = "users";
  private static final String COLUMN_ID = "_id";
  private static final String COLUMN_USERNAME = "username";
  private static final String COLUMN_PASSWORD = "password";
  // Use SQL joins to access cookie table using these ids
  private static final String COLUMN_HANDLE_COOKIE = "handle_cookie";
  private static final String COLUMN_SESSION_COOKIE = "session_cookie";
  // Consider having a separate table for games, but either way we need a comma
  // separated String of game IDs in the users table
  private static final String COLUMN_ACTIVE_GAMES = "active_games";

  // User table creation statement
  private static final String USER_TABLE_CREATE = "create table " + TABLE_USERS +
    "( " + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_USERNAME
    + " text not null, " + COLUMN_PASSWORD + " text not null, " + 
    COLUMN_HANDLE_COOKIE + " interger, " + COLUMN_SESSION_COOKIE +  " integer);";

  // Constants for creating Cookies table
  private static final String TABLE_COOKIES = "cookies";
  private static final String COLUMN_COOKIE_ID = "id";
  private static final String COLUMN_COOKIE_NAME = "name";
  private static final String COLUMN_COOKIE_VALUE = "value";
  private static final String COLUMN_COOKIE_VERSION = "version";
  private static final String COLUMN_COOKIE_DOMAIN = "domain";
  private static final String COLUMN_COOKIE_PATH = "path";
  private static final String COLUMN_COOKIE_EXPIRY_DATE = "expiry_date";

  // Cookie table creation statement
  private static final String COOKIE_TABLE_CREATE = "create table " + 
    TABLE_COOKIES + "( " + COLUMN_COOKIE_ID + 
    " integer primary key autoincrement " + COLUMN_COOKIE_NAME + 
    " text not null, " + COLUMN_COOKIE_VALUE + " text not null, " +
    COOKIE_COLUMN_VERSION + " text not null, " + COLUMN_COOKIE_DOMAIN +
    " text not null, " + COLUMN_COOKIE_PATH + " text not null, " + 
    COLUMN_COOKIE_EXPIRY_DATE + " text not null);";

  /** Constructor - Attempts to open database */
  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  /** Called if database could not be found */
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(USER_TABLE_CREATE);
    database.execSQL(COOKIE_TABLE_CREATE);
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

  /** 
   * Adds the handle cookie.
   *
   * We add the cookie to the Cookie table and add its id to the User table
   */
  public void addHandleCookie(User user, Cookie cookie) {
    // Add Cookie to Cookie table
    int id = addCookie(cookie);
    // Add Cookie ID to User table
  }

  /** 
   * Adds the handle cookie.
   *
   * We add the cookie to the Cookie table and add its id to the User table
   */
  public void addSessionCodeCookie(User user, Cookie cookie) {
    // Add Cookie to Cookie table - Add cookie
    int id = addCookie(cookie);
    // Add Cookie ID to User table
  }

  /**
   * Returns the handle cookie associated with the user
   * 
   * Returns null if we couldn't access the cookie
   */
  public Cookie getHandleCookie(User user) {
    // Get handle cookie id from user table
    // return getCookie(id)
  }

  /**
   * Returns the session code cookie associated with the user
   * 
   * Returns null if we couldn't access the cookie
   */
  public Cookie getSessionCodeCookie(User user) {
    // Get session cookie id from user table
    // return getCookie(id)
  }

  /**
   * Adds cookie to the cookie table
   *
   * Returns the cookie's ID so it can be linked to the user
   * Returns -1 if we couldn't add the cookie.
   */
  private int addCookie(Cookie cookie) {
    // open the database for writing
    SQLiteDatabase db = this.getWritableDatabase();

    // Set up the values we wish to add
    ContentValues values = new ContentValues();
    values.put(COLUMN_COOKIE_NAME, cookie.getName());
    values.put(COLUMN_COOKIE_VALUE, cookie.getValue());
    values.put(COLUMN_COOKIE_VERSION, cookie.getVersion());
    values.put(COLUMN_COOKIE_PATH, cookie.getPath());
    values.put(COLUMN_COOKIE_DOMAIN, cookie.getDomain());
    values.put(COLUMN_COOKIE_EXPIRY_DATE, cookie.getExpiryDate());

    // Insert values into the database and close it
    db.insert(TABLE_COOKIES, null, values);
    db.close();
  }

  /**
   * Retrieves a cookie from the Cookie table
   *
   * Returns null if cookie id can't be found
   */
  private Cookie getCookie(int id) {
    // This does all the work of recreating a cookie from the strings in the
    // Cookie table.
  }

}
