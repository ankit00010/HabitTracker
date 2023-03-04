package com.example.fukc.databaseClass;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {


    public DBHelper( Context context) {
        super(context, "habit_tracker.db", null,1);
    }
    //code to create a table for users
    @Override
    public void onCreate(SQLiteDatabase db) {
        //User table
        db.execSQL("create table users(userid INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,email TEXT,password TEXT,securityque TEXT,securityans TEXT)");
        db.execSQL("create table subhabits(shabitid INTEGER PRIMARY KEY AUTOINCREMENT,shabitlist TEXT,habitid INTEGER,FOREIGN KEY (habitid) REFERENCES habits(habitid))");
        db.execSQL("create table habits(habitid INTEGER PRIMARY KEY AUTOINCREMENT,habitname TEXT,color TEXT,question TEXT NOT NULL DEFAULT 'unknown',frequency TEXT,remainder TEXT NOT NULL DEFAULT 'unknown',habittype INTEGER,target INTEGER NOT NULL DEFAULT 'unknown',userid INTEGER,FOREIGN KEY (userid) REFERENCES users(userid))");
        db.execSQL("create table records(recordid INTEGER PRIMARY KEY AUTOINCREMENT,habitid INTEGER,record TEXT,date TEXT,target INTEGER,subrecord TEXT,FOREIGN KEY (habitid) REFERENCES habits(habitid))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS  habits");
        db.execSQL("DROP TABLE IF EXISTS  subhabits");
        db.execSQL("DROP TABLE IF EXISTS  records");
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
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }

    //Insert data in habits
    public Boolean insertDatahabit(String habitname,String color ,String question,String frequency,String remainder,Integer habittype,Integer target,Integer userid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("habitname", habitname);
        contentValues.put("color", color);
        contentValues.put("question", question);
        contentValues.put("frequency", frequency);
        contentValues.put("remainder", remainder);
        contentValues.put("habittype", habittype);
        contentValues.put("target", target);
        contentValues.put("userid", userid);
        long result = MyDB.insert("habits", null, contentValues);
        MyDB.close();

        if(result==-1) return false;
        else
            return true;
    }
    //Insert data in record
    public Boolean insertDataRecord(Integer habitid, String record, String date,Integer target,String subrecord){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("habitid", habitid);
        contentValues.put("record", record);
        contentValues.put("date", date);
        contentValues.put("target", target);
        contentValues.put("subrecord", subrecord);
        long result = MyDB.insert("records", null, contentValues);
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }

    //Insert data in subhabits
    public Boolean insertDataSubhabits(String shabitname,Integer habitid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("shabitlist", shabitname);
        contentValues.put("habitid", habitid);
        long result = MyDB.insert("subhabits", null, contentValues);
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }
    //update the subhabits of the checklist
    public Boolean updateSubHabit(String shabitname,Integer habitid){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("shabitlist", shabitname);
        contentValues.put("habitid", habitid);
        long result = MyDB.insert("subhabits", null, contentValues);
        MyDB.close();
        if(result==-1) return false;
        else
            return true;
    }
    //Get Userid
    public int getUserid(String username) {
        String query = "SELECT userid FROM users WHERE username = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int userid=0;
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{username});
            if (cursor.moveToFirst()) {
                userid = cursor.getInt(0);
            }
        }
        return userid;
    }
    //Get Record
    public String getRecord(String hname) {
        int id = getHabitId(hname);
        String query = "SELECT record FROM records WHERE habitid = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String record="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                record = cursor.getString(0);
            }
        }
        return record;
    }
    //Get Record
    public String getSubRecord(String hname) {
        int id = getHabitId(hname);
        String query = "SELECT subrecord FROM records WHERE habitid = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String record="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                record = cursor.getString(0);
            }
        }
        return record;
    }
    //Current Streak
    public int getCurrentStreak(String habitName){
        SQLiteDatabase db = this.getReadableDatabase();
        int id =getHabitId(habitName);
        String query ="SELECT record FROM records WHERE habitid = "+ id +" ORDER BY recordid DESC";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if(cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals("Y")) {
                    count++;
                } else {
                    break;
                }
            } while (cursor.moveToNext());

        }
        return count;
    }
    //Best Streak
    public int getBestStreak(String habitName){
        SQLiteDatabase db = this.getReadableDatabase();
        int id =getHabitId(habitName);
        String query ="SELECT record FROM records WHERE habitid = "+ id +" ORDER BY recordid DESC";
        Cursor cursor = db.rawQuery(query, null);
        int counts = 0;
        int maxCount = 0;
        if(cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals("Y")) {
                    counts++;
                    if (counts > maxCount) {
                        maxCount = counts;
                    }
                } else {
                    counts = 0;
                }
            }while (cursor.moveToNext());
        }
        return maxCount;
    }
    // returns success count for a month
    public int getMonthsCount(String habitName,String month){
        SQLiteDatabase db = this.getReadableDatabase();
        int id =getHabitId(habitName);
        String query ="SELECT COUNT(*) FROM records WHERE record LIKE '%Y%' AND date LIKE '%-"+month+"-%' AND habitid = "+ id +"";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count;
    }
    //for pieChart
    public int getDoneCount(String habitName){
        SQLiteDatabase db = this.getReadableDatabase();
        int id =getHabitId(habitName);
        String query ="SELECT COUNT(*) FROM records WHERE record LIKE '%Y%' AND habitid = "+ id +"";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count;
    }
    public int getSkipCount(String habitName){
        SQLiteDatabase db = this.getReadableDatabase();
        int id =getHabitId(habitName);
        String query ="SELECT COUNT(*) FROM records WHERE record LIKE '%S%' AND habitid = "+ id +"";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count;
    }
    public int getFailCount(String habitName){
        SQLiteDatabase db = this.getReadableDatabase();
        int id =getHabitId(habitName);
        String query ="SELECT COUNT(*) FROM records WHERE record LIKE '%N%' AND habitid = "+ id +"";
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        return count;
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
        db.delete("records", whereClause1, whereArgs1);
        db.delete("subhabits", whereClause1, whereArgs1);
    }


    //Update record for YN habits
    public void updateYNrecord(int habitid,String record,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("record", record);
        db.update("records", values, "habitid = ? AND date = ?", new String[]{String.valueOf(habitid),date});
    }
    //Update record for Sub habits
    public void updateSubRecord(int habitid,String subrecord,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("subrecord", subrecord);
        db.update("records", values, "habitid = ? AND date = ?", new String[]{String.valueOf(habitid),date});
    }
    //Update record for checklist
    public void updateChecklistRecord(int habitid,String record,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("record", record);
        db.update("records", values, "habitid = ? AND date = ?", new String[]{String.valueOf(habitid),date});
    }

    //Update record for Measurable habits
    public void updateMeasurableRecord(int habitid,String record,String date,int target){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("record", record);
        values.put("target", target);
        db.update("records", values, "habitid = ? AND date = ?", new String[]{String.valueOf(habitid),date});
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
        String query = "SELECT question FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String que="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                que = cursor.getString(0);
            }
        }
        Log.d("Inadatavase ",query);

        return que;
    }
    //get reminder from habits table
    public String getReminder(String hname) {
        String query = "SELECT remainder FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String remainder="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                remainder = cursor.getString(0);
            }
        }
        return remainder;
    }

    //get color from habits Table
    public String getHabitColor(String hname) {
        String query = "SELECT color FROM habits WHERE habitname = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String color="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{hname});
            if (cursor.moveToFirst()) {
                color = cursor.getString(0);
            }
        }
        return color;
    }
    //get color from subhabit Table
    public String getSubHabit(String hname) {
        int id = getHabitId(hname);
        String query = "SELECT shabitlist FROM subhabits WHERE habitid = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String subHabits="";
        if(db != null) {
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                subHabits = cursor.getString(0);
            }
        }
        return subHabits;
    }
    //check if record is available
    public boolean isRecordPresent(int habitId, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM records WHERE habitid = ? AND date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(habitId),date});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count > 0) {
            return true;
        } else {
            return false;
        }


    }
    //check if Subrecord is available
    public boolean isSubRecordPresent(int habitId, String date){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT subrecord FROM records WHERE habitid = ? AND date = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(habitId),date});
        cursor.moveToFirst();
        String str = cursor.getString(0);
        if (str.equals("")) {
            return false;
        } else {
            return true;
        }

    }
    //get data from Habit table for YN and M habits
    public Cursor getdataHabit(String day,int userid)
    {
        String query = "SELECT habitname FROM habits WHERE INSTR(frequency,?) > 0 AND userid = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{day,String.valueOf(userid)});
        }
        return cursor;
    }
    public Cursor getalldataHabit(int userid)
    {
        String query = "SELECT habitname FROM habits WHERE userid = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(userid)});
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
    //Update all records in habit
    public void updateEdit(String prevHabitName,String habitname,String frequency,String remainder,String color,String que,Integer habittype ,Integer target){

        ContentValues contentValues = new ContentValues();
        contentValues.put("habitname", habitname);
        contentValues.put("color", color);
        contentValues.put("question", que);
        contentValues.put("frequency", frequency);
        contentValues.put("remainder", remainder);
        contentValues.put("habittype", habittype);
        contentValues.put("target", target);
        String table = "habits";
        String whereClause = "habitname = ? ";
        String[] whereArgs = new String[]{prevHabitName};
        SQLiteDatabase db = getWritableDatabase();
        db.update(table, contentValues, whereClause, whereArgs);
        Cursor cursor= db.rawQuery("Select * from habits where habitname =? ",whereArgs);
        Log.d("InaDatabase","45e54e5454545454545454545455");

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
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("users", null, "username = ? AND password = ?", selectionArgs, null, null, null);
        if(cursor.moveToFirst())
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