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

    private static final String DATABASE_NAME = "WordGropeDB.db";
    private static final String TABLE_NAME_SEARCHED = "SEARCHED";
    private static final String TABLE_NAME_FAVOURITES = "FAVOURITES";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_N_RESULTS = "results";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table SEARCHED " +
                        "(id integer primary key, word text, date text, results integer)"
        );

        db.execSQL(
                "create table FAVOURITES " +
                        "(id integer primary key, word text, date text, results integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SEARCHED");
        db.execSQL("DROP TABLE IF EXISTS FAVOURITES");
        onCreate(db);
    }

    void insertSearched(String word, Date date, Integer results) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("results", results);
        db.insert("SEARCHED", null, contentValues);
    }

    public boolean insertFavourite (String word, Date date, Integer results) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("results", results);
        db.insert("FAVOURITES", null, contentValues);
        return true;
    }

    public Cursor getDataSearched(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from SEARCHED where id="+id+"", null );
    }

    public Cursor getDataFavoutite(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "select * from FAVOURITES where id="+id+"", null );
    }

    public int numberOfRowsSearched(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME_SEARCHED);
    }

    public int numberOfRowsFavourite(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME_FAVOURITES);
    }

    public boolean update (Integer id, String word, Date date, Integer results, Boolean favourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("results", results);
        db.update("SEARCHED", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer delete (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("SEARCHED",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    Cursor getAllSearched(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from SEARCHED", null );
        res.moveToFirst();
        return res;
    }

    Cursor getAllFavourites(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from FAVOURITES", null );
        res.moveToFirst();
        return res;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}

