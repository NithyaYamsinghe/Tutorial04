package com.example.tutorial04.Database;

import android.provider.BaseColumns;

// IT18233704 N.R Yamasinghe
public final class UsersMaster {

    // Constructor
    private UsersMaster() {
    };

    // Inner class that defines the table contents
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
