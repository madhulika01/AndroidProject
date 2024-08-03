package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String tag = "UserDatabaseHelper";
    public static final String TRAVELERS = "travelers";
    public static final String ID = "_id";
    public static final String FULL_NAME = "fullName";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String THIRD_PARTY = "thirdParty";
    public static final String RECEIVE_EMAILS = "receiveEmails";
    public static final String REMINDERS = "reminders";
    public static final String TRENDING_PLACES = "trendingPlaces";
    public static final String FEEDBACK = "feedback";
    public static final String SUPPORT = "support";
    private static final String DATABASE_NAME = "travelers.db";
    private static final int VERSION_NUM = 2; // Update the version number
    private static UserDatabaseHelper instance;

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TRAVELERS + " (" + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FULL_NAME + " TEXT NOT NULL, "
            + USERNAME + " TEXT NOT NULL, "
            + EMAIL + " TEXT NOT NULL, "
            + PASSWORD + " TEXT NOT NULL, "
            + THIRD_PARTY + " INTEGER NOT NULL DEFAULT 1, "
            + RECEIVE_EMAILS + " INTEGER NOT NULL DEFAULT 1, "
            + REMINDERS + " INTEGER NOT NULL DEFAULT 1, "
            + TRENDING_PLACES + " INTEGER NOT NULL DEFAULT 1, "
            + FEEDBACK + " INTEGER NOT NULL DEFAULT 1, "
            + SUPPORT + " INTEGER NOT NULL DEFAULT 1);";

    public static synchronized UserDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new UserDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public UserDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TRAVELERS + " ADD COLUMN " + THIRD_PARTY + " INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE " + TRAVELERS + " ADD COLUMN " + RECEIVE_EMAILS + " INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE " + TRAVELERS + " ADD COLUMN " + REMINDERS + " INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE " + TRAVELERS + " ADD COLUMN " + TRENDING_PLACES + " INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE " + TRAVELERS + " ADD COLUMN " + FEEDBACK + " INTEGER NOT NULL DEFAULT 1");
            db.execSQL("ALTER TABLE " + TRAVELERS + " ADD COLUMN " + SUPPORT + " INTEGER NOT NULL DEFAULT 1");
        }
    }

    public Cursor getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {FULL_NAME, EMAIL};
        String selection = USERNAME + " = ?";
        String[] selectionArgs = {username};
        return db.query(TRAVELERS, columns, selection, selectionArgs, null, null, null);
    }

    public void deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TRAVELERS, EMAIL + " = ?", new String[]{email});
        if (rowsDeleted > 0) {
            Log.i(tag, "User deleted successfully.");
        } else {
            Log.i(tag, "User not found or unable to delete.");
        }
        db.close();
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(PASSWORD, newPassword);
        int rows = db.update(TRAVELERS, cValues, USERNAME + " = ?", new String[]{username});
        return rows > 0;
    }

    public void saveAccountPrivacySettings(String username, boolean thirdParty, boolean receiveEmails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(THIRD_PARTY, thirdParty ? 1 : 0);
        cValues.put(RECEIVE_EMAILS, receiveEmails ? 1 : 0);
        db.update(TRAVELERS, cValues, USERNAME + "=?", new String[]{username});
    }

    public boolean[] loadAccountPrivacySettings(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {THIRD_PARTY, RECEIVE_EMAILS};
        String selection = USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TRAVELERS, columns, selection, selectionArgs, null, null, null);
        boolean[] settings = new boolean[2];
        if (cursor != null && cursor.moveToFirst()) {
            int thirdPartyIndex = cursor.getColumnIndex(THIRD_PARTY);
            int receiveEmailsIndex = cursor.getColumnIndex(RECEIVE_EMAILS);
            settings[0] = cursor.getInt(thirdPartyIndex) == 1;
            settings[1] = cursor.getInt(receiveEmailsIndex) == 1;
            cursor.close();
        }
        return settings;
    }

    public void saveNotificationSettings(String username, boolean reminders, boolean trendingPlaces, boolean feedback, boolean support) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(REMINDERS, reminders ? 1 : 0);
        cValues.put(TRENDING_PLACES, trendingPlaces ? 1 : 0);
        cValues.put(FEEDBACK, feedback ? 1 : 0);
        cValues.put(SUPPORT, support ? 1 : 0);
        db.update(TRAVELERS, cValues, USERNAME + " = ?", new String[]{username});
    }

    public boolean[] loadNotificationSettings(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {REMINDERS, TRENDING_PLACES, FEEDBACK, SUPPORT};
        String selection = USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TRAVELERS, columns, selection, selectionArgs, null, null, null);
        boolean[] settings = new boolean[4];
        if (cursor != null && cursor.moveToFirst()) {
            int remindersIndex = cursor.getColumnIndex(REMINDERS);
            int trendingPlacesIndex = cursor.getColumnIndex(TRENDING_PLACES);
            int feedbackIndex = cursor.getColumnIndex(FEEDBACK);
            int supportIndex = cursor.getColumnIndex(SUPPORT);
            settings[0] = cursor.getInt(remindersIndex) == 1;
            settings[1] = cursor.getInt(trendingPlacesIndex) == 1;
            settings[2] = cursor.getInt(feedbackIndex) == 1;
            settings[3] = cursor.getInt(supportIndex) == 1;
            cursor.close();
        }
        return settings;
    }
}
