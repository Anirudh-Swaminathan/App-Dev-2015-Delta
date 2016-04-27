package com.anirudh.anirudhswami.delta_2015_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Currency;

/**
 * Created by Anirudh Swami on 25-04-2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contact";
    public static final String COL_1 = "Name";
    public static final String COL_2 = "Number";
    public static final String COL_3 = "Img";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contact (NAME TEXT PRIMARY KEY, NUMBER TEXT,IMG BLOB)");//adding image blob
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name, String number,byte[] img){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        contentValues.put(COL_3,img);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1) return false;
        return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME+" order by "+COL_1, null);
        return res;
    }
    public boolean update_data(String name, String number,byte[] img){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,number);
        contentValues.put(COL_3,img);
        long res = db.update(TABLE_NAME, contentValues, "Name = ?", new String[]{name});
        if(res == 0) return false;
        return true;
    }
    public Integer delete_data(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"Name = ?",new String[]{ name });
    }
}
