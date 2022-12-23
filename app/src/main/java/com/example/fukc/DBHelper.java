package com.example.fukc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="habittracker.db";
    public DBHelper( Context context) {
        super(context, "login.db", null,1);
    }
    //code to create a table for users
    @Override
    public void onCreate(SQLiteDatabase db) {
        //User table
        db.execSQL("create table users(userid INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,email TEXT,password TEXT,securityque TEXT,securityans TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
    }
    //Insert data Function

    public Boolean insertData(String username,String email, String password, String securityque,String securityans){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("securityque", securityque);
        contentValues.put("securityans", securityans);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    //Update pass
    public void updatepass(String pass,String email){

        ContentValues values = new ContentValues();
        values.put("password", pass);
        String table = "users";
        String whereClause = "email=?";
        String[] whereArgs = new String[]{email};


        SQLiteDatabase db = getWritableDatabase();
        db.update(table, values, whereClause, whereArgs);
    }
    //Check username Function
    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //Check username and password Function
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    //Check email Function
    public Boolean email(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //Check security Question
    public Boolean securityverify(String securityque,String securityans) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where securityque = ? and securityans = ?", new String[] {securityque,securityans});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    //Gives Security question

    public String giveque(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
       // String queno = String.valueOf(db.rawQuery("Select securityque from users where email = ?", new String[] {email}));


        String query = "Select securityque from users where email = ?";
        String[] selectionArgs = { email };
        Cursor cursor = db.rawQuery(query, selectionArgs);

        String text = null;
        if (cursor.moveToFirst()) {
            text = cursor.getString(cursor.getColumnIndexOrThrow("securityque"));
        }
        return text;
    }

}
