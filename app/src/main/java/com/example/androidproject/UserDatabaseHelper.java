package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static UserDatabaseHelper instance;
    private static final String DATABASE_CREATE = "create TABLE "
            + USERS+" ("+ID
            + " integer primary key autoincrement, "
            + FULL_NAME + " text not null, "
            + USERNAME + " text not null, "
            + EMAIL + " text not null, "
            + PASSWORD + " text not null);";

    public static synchronized UserDatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new UserDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    public UserDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
        //Log.i(tag,"UserDatabaseHelper called");
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //Log.i(tag,"inside onCreate");
        db.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        //Log.i(tag,"Calling onUpgrade, old version = " + oldVersion +"new version = "+newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }
    public Cursor getUserInfo(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {FULL_NAME,EMAIL};
        String selection = USERNAME + " = ?";
        String[] selectionArgs = {username};
        return db.query(USERS,columns,selection,selectionArgs,null,null,null);
    }
    public void deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(USERS, EMAIL + " = ?", new String[]{email});
        if (rowsDeleted > 0) {
            Log.i(tag, "User deleted successfully.");
        } else {
            Log.i(tag, "User not found or unable to delete.");
        }
        db.close();
    }
    public boolean updatePassword(String username, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(PASSWORD,newPassword);
        int rows = db.update(USERS,cValues,USERNAME+" = ?",new String[]{username});
        return rows >0;
    }
}
