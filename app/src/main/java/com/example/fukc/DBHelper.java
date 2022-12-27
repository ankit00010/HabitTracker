package com.example.fukc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


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
        db.execSQL("create table habits(habitid INTEGER PRIMARY KEY AUTOINCREMENT,habitname TEXT,color TEXT ,question TEXT NOT NULL DEFAULT 'unknown',frequency TEXT,remainder TEXT,habittype INTEGER)");
        db.execSQL("create table category(catid INTEGER PRIMARY KEY AUTOINCREMENT,catname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists habits");
        db.execSQL("drop table if exists category");
        onCreate(db);
    }

    //Insert data in users
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

    //Insert data in habits
    public Boolean insertDatahabit(String habitname,String color ,String question,String frequency,String remainder,Integer habittype){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("habitname", habitname);
        contentValues.put("color", color);
        contentValues.put("question", question);
        contentValues.put("frequency", frequency);
        contentValues.put("remainder", remainder);
        contentValues.put("habittype", habittype);
        long result = MyDB.insert("habits", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    //Insert data in category
    public Boolean insertDataCategory(String catname){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("catname", catname);
        long result = MyDB.insert("category", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    //get data from Category Table
    public Cursor getdata()
    {
        String query = "SELECT * FROM category";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    //Delete Category
    public void deleteCat(String catname){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM category WHERE catname = ?", new String[]{catname});
    }
    //get number of rows in category table
    public long getCountCat() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "category");
        db.close();
        return count;
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
