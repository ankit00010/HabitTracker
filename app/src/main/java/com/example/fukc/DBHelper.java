package com.example.fukc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="habittracker.db";
    public DBHelper( Context context) {
        super(context, "habit_tracker.db", null,1);
    }
    //code to create a table for users
    @Override
    public void onCreate(SQLiteDatabase db) {
        //User table
        db.execSQL("create table users(userid INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,email TEXT,password TEXT,securityque TEXT,securityans TEXT)");
        db.execSQL("create table subhabits(shabitid INTEGER PRIMARY KEY AUTOINCREMENT,shabitlist TEXT,habitid INTEGER,FOREIGN KEY (habitid) REFERENCES habits(habitid))");
        db.execSQL("create table habits(habitid INTEGER PRIMARY KEY AUTOINCREMENT,habitname TEXT,color TEXT,question TEXT NOT NULL DEFAULT 'unknown',frequency TEXT,remainder TEXT NOT NULL DEFAULT 'unknown',habittype INTEGER,target INTEGER NOT NULL DEFAULT 'unknown')");
        db.execSQL("create table record(recordid INTEGER PRIMARY KEY AUTOINCREMENT,habitid INTEGER,record TEXT,date TEXT,target INTEGER,FOREIGN KEY (habitid) REFERENCES habits(habitid))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS  habits");
        db.execSQL("DROP TABLE IF EXISTS  subhabits");
        db.execSQL("DROP TABLE IF EXISTS  record");
        db.close();
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
    public Boolean insertDatahabit(String habitname,String color ,String question,String frequency,String remainder,Integer habittype,Integer target){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("habitname", habitname);
        contentValues.put("color", color);
        contentValues.put("question", question);
        contentValues.put("frequency", frequency);
        contentValues.put("remainder", remainder);
        contentValues.put("habittype", habittype);
        contentValues.put("target", target);
        long result = MyDB.insert("habits", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    //Delete a habit
    public void deleteHabit(String habitName){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "habitname" + " = ?";
        String[] whereArgs = {habitName};
        db.delete("habits", whereClause, whereArgs);
        int id = getHabitId(habitName);
        String whereClause1 = "habitid" + " = ?";
        String[] whereArgs1 = {String.valueOf(id)};
        db.delete("record", whereClause1, whereArgs1);
        db.delete("subhabits", whereClause1, whereArgs1);
    }
    //Insert data in subhabits
    public Boolean insertDataSubhabits(String shabitname,Integer habitid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("shabitlist", shabitname);
        contentValues.put("habitid", habitid);
        long result = MyDB.insert("subhabits", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    //Insert data in record
    public Boolean insertDataRecord(Integer habitid, String record, String date,Integer target){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("habitid", habitid);
        contentValues.put("record", record);
        contentValues.put("date", date);
        contentValues.put("target", target);
        long result = MyDB.insert("record", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    //Update record for YN habits
    public void updateYNrecord(int habitid,String record,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("record", record);
        db.update("record", values, "habitid = ? AND date = ?", new String[]{String.valueOf(habitid),date});
    }
    //Update record for Measurable habits
    public void updateMeasurableRecord(int habitid,String record,String date,int target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("record", record);
        values.put("target", target);
        db.update("record", values, "habitid = ? AND date = ?", new String[]{String.valueOf(habitid),date});
    }
    //get id from habits Table
   public int getHabitId(String hname) {
        String query = "SELECT habitid FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int id=0;
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
        }

        return id;
    }
    //get type from habits Table
    public int getHabitType(String hname) {
        String query = "SELECT habittype FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int type=0;
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                type = cursor.getInt(0);
            }
        }

        return type;
    }
    //get frequency from habits Table
    public String getHabitFrequency(String hname) {
        String query = "SELECT frequency FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String freq="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                freq = cursor.getString(0);
            }
        }

        return freq;
    }
    ///get habit question from habits table
    public String getHabitque(String hname) {
        String query = "SELECT frequency FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String que="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                que = cursor.getString(0);
            }
        }

        return que;
    }
    //get habitname from habits table
    public String getHabitName(String hname) {
        String query = "SELECT habitname FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String name="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                name = cursor.getString(0);
            }
        }

        return name;
    }
    //get color from habits Table
    public String getHabitColor(String hname) {
        String query = "SELECT color FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String freq="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                freq = cursor.getString(0);
            }
        }

        return freq;
    }
    //check if record is available
    public boolean isRecordPresent(int habitId, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM record WHERE habitid = ? AND date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(habitId),date});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }
    //get data from Habit table for YN and M habits
    public Cursor getdataHabit(String day)
    {
        String query = "SELECT habitname FROM habits WHERE INSTR(frequency,?) > 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{day});
        }

        return cursor;
    }
    public Cursor getalldataHabit()
    {
        String query = "SELECT habitname FROM habits";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public String getdataSHabit(int id)
    {
        String idstr = String.valueOf(id);
        String query = "SELECT shabitlist FROM subhabits WHERE habitid = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String shlist= "";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{idstr});
            if (cursor.moveToFirst()) {
                shlist = cursor.getString(0);
            }
        }

        return shlist;
    }
    //get habit type from habits table
    public int getHabittype(String cname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM habits WHERE habitname = ?";
        Cursor cursor = null;
        int habittype=0;
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{cname});
            if (cursor.moveToFirst()) {
                habittype = cursor.getInt(6);
            }
        }
        return habittype;
    }
    //get habit target from habits table
    public int getHabitTarget(String cname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM habits WHERE habitname = ?";
        Cursor cursor = null;
        int target=0;
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{cname});
            if (cursor.moveToFirst()) {
                target = cursor.getInt(7);
            }
        }
        return target;
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
