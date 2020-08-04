package com.platinum.innovations.wordgrope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WordGropeDB.db";
    private static final String TABLE_NAME_FAVOURITES = "FAVOURITES";

    DBHelper(@Nullable Context context) {
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

    void insertSearched(String word, String date, Integer results) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", date);
        contentValues.put("results", results);
        db.insert("SEARCHED", null, contentValues);
    }

    void insertFavourite(String word, String date, Integer results) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("date", date);
        contentValues.put("results", results);
        db.insert("FAVOURITES", null, contentValues);
    }

    void delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_FAVOURITES, "word = ?", new String[]{id});
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

    Cursor getLimitFavourites() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from FAVOURITES LIMIT 6", null );
        res.moveToFirst();
        return res;
    }

    public boolean isFieldExist(String fieldName){
        Boolean True = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from FAVOURITES where word = '" +fieldName+"'" , null );
        cursor.moveToFirst();
        if(cursor.getCount() < 1){
            True = true;
        }
        cursor.close();
        return True;
    }

}