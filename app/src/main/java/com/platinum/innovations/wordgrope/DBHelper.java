package com.platinum.innovations.wordgrope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "WordGropeDB.db";
    public static final String TABLE_NAME = "SEARCHED";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_N_RESULTS = "results";
    public static final String COLUMN_FAV = "favourite";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table SEARCHED " +
                        "(id integer primary key, word text, date text, results integer, favourite boolean)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SEARCHED");
        onCreate(db);
    }

    public boolean insert (String word, Date date, Integer results, Boolean favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("results", results);
        contentValues.put("favourite", favourite);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from SEARCHED where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String word, Date date, Integer results, Boolean favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("results", results);
        contentValues.put("favourite", favourite);
        db.update("SEARCHED", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SEARCHED",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
}
