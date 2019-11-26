package com.cs360.simplestocks.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cs360.simplestocks.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "UserDatabase.db";

    //User table name
    private static final String TABLE_USER = "userTable";

    //User table columns names
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";

    /**
     * Constructor
     * @param context
     */
    public UserDatabaseHelper(Context context) {
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
     * New user creation
     * @param user - new record for table
     */
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());


        db.insert(TABLE_USER, null, values);
        db.close();
    }


    /**
     * Returns all users
     *
     * @return - user list
     */
    public List<User> getAllUser(){
        //An array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };

        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

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
     * update user
     * @param user - user to be updated
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
     * Delete user from table
     * @param user - user to delete
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
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " =?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
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
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " =?" + " AND "
                           + COLUMN_USER_PASSWORD + " =?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

}
