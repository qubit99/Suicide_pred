package com.ritwick.keyboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public  class dbmaker extends SQLiteOpenHelper {

    public static final String DB_NAME = "data.db";
    public static final String TABLE_NAME = "words";
    public static final String COL_NAME = "chars";

    public dbmaker(@Nullable Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME + "(chars VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertdata(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,s);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        return c;
    }
}
