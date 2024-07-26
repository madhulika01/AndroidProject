package com.example.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String tag = "UserDatabaseHelper";
    public static final String USERS = "users";
    public static final String ID = "_id";
    public static final String FULL_NAME = "fullName";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD ="password";
    private static final String DATABASE_NAME = "users.db";
    private static final int VERSION_NUM = 1;
    private static final String DATABASE_CREATE = "create TABLE "
            + USERS+" ("+ID
            + " integer primary key autoincrement, "
            + FULL_NAME + " text not null, "
            + USERNAME + " text not null, "
            + EMAIL + " text not null, "
            + PASSWORD + " text not null);";
    public UserDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
        Log.i(tag,"UserDatabaseHelper called");
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(tag,"inside onCreate");
        db.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVerison){
        Log.i(tag,"Calling onUpgrade, old version = " + oldVersion +"new version = "+newVerison);
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }
}
