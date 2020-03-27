package com.example.tutorial04.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

// IT18233704 N.R Yamasinghe
public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                UsersMaster.Users._ID + " INTEGER PRIMARY KEY," +
                UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                UsersMaster.Users.COLUMN_NAME_PASSWORD + " Text)";

        // Execute the SQL
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME);

    }


    // Method for add info
    public long addInfo(String userName, String password) {

        // Get the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create new map of values where column names are the key
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        // Insert the new row returning the primary key value of new row
        long newRowId = db.insert(UsersMaster.Users.TABLE_NAME, null, values);
        return newRowId;
    }


    // Method for read info
    public List readAllInfo() {

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        //Filter results WHERE "userName" = "SLIIT User";
        //String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + "=?";
        //String[] selectionArgs = {""};

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List userNames = new ArrayList();
        List passwords = new ArrayList();

        while (cursor.moveToNext()) {
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            userNames.add(userName);
            passwords.add(password);
        }
        cursor.close();
        return userNames;


    }


    public boolean readInfo(String userName, String password) {

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String[] selectionArgs = {""};

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );


        while (cursor.moveToNext()) {

            String u = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String p = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            if (u.equals(userName) && p.equals(password)) {
                return true;
            }

        }
        cursor.close();
        return false;


    }


    // Delete info method
    public boolean deleteInfo(String userName) {
        SQLiteDatabase db = getReadableDatabase();

        // Define where part of the query
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";

        // Specify arguments n place holder
        String[] selectionArgs = {userName};
        int count = db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);

        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }


    // Update method
    public boolean updateInfo(String userName, String password) {
        SQLiteDatabase db = getReadableDatabase();


        // Adding new value
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);


        // Which row to update
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        // Specify arguments n place holder
        String[] selectionArgs = {userName};


        int count = db.update(UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);


        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }
}
