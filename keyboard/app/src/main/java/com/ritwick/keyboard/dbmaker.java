package com.ritwick.keyboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public  class dbmaker extends SQLiteOpenHelper {

    private static final String DB_NAME = "data1.db";
    private static final String TABLE_NAME = "Words";
    private static final String COL_CHARS = "chars";
    private static final String COL_PK = "id";
    private static final String COL_TIMESTAMP = "timestamp";

    public dbmaker(@Nullable Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = String.format("CREATE TABLE %s (%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT);",
                TABLE_NAME, COL_PK, COL_TIMESTAMP, COL_CHARS);
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public boolean insertData(String s){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        String ts = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        contentValues.put(COL_TIMESTAMP, ts);
        contentValues.put(COL_CHARS, s);

        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }
    public Cursor getData() {
        String getQuery = String.format("SELECT %s FROM %s ORDER BY %s DESC;",
                COL_CHARS, TABLE_NAME, COL_TIMESTAMP);

        SQLiteDatabase db = this.getWritableDatabase();
        return  db.rawQuery(getQuery,null);
    }
}
