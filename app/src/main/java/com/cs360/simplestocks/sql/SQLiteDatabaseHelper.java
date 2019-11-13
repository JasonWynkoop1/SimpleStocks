package com.cs360.simplestocks.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs360.simplestocks.model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "UserManager.db";

    //User table name
    private static final String TABLE_USER = "user";

    //User table columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    /**
     * Constructor
     * @param context
     */
    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table sql query
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //drop user table if exist
        //Drop table sql query
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        db.execSQL(DROP_USER_TABLE);

        //create tables again
        onCreate(db);
    }

    /**
     * Creating a new user record
     * @param user
     */
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //inserting row
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    public List<User> getAllUser(){
        //An array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };

        //Sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        //query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */

        Cursor cursor = db.query(
                TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        //Traversing through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                //adding user record to list
                userList.add(user);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        //return user list
        return userList;
    }

    /**
     * updates user record
     * @param user
     */
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " =?",
                    new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Method for deleting a user record
     * @param user
     */
    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " =?",
                    new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * Method to check if a user exists or not
     * using just email
     * @param email - user email to check
     * @return true if user exists
     */
    public boolean checkIfUserExists(String email){
        //array of columns to fetch
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_USER_EMAIL + " =?";

        //selection argument
        String[] selectionArgs = {email};

        //query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //table to query
                columns,                     //columns to return
                selection,                   //columns to return
                selectionArgs,               //the values for the WHERE clause
                null,               //group the rows
                null,                //filter by row groups
                null                //the sort order
        );

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    /**
     * Method to check if a user exists or not
     * using email and password
     * @param email - email entered
     * @param password - password entered
     * @return
     */
    public boolean checkIfUserExists(String email, String password){
        //array of columns to fetch
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_USER_EMAIL + " =?" + " AND "
                           + COLUMN_USER_PASSWORD + " =?";

        //selection argument
        String[] selectionArgs = {email, password};

        //query user table with condition
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //table to query
                columns,                     //columns to return
                selection,                   //columns to return
                selectionArgs,               //the values for the WHERE clause
                null,               //group the rows
                null,                //filter by row groups
                null                //the sort order
        );

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

}
