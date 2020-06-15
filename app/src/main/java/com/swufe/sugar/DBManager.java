package com.swufe.sugar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(MovieItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("curmovie", item.getCurRate());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAll(List<MovieItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (MovieItem item : list) {
            ContentValues values = new ContentValues();
            values.put("curname", item.getCurName());
            values.put("curmovie", item.getCurRate());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(MovieItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("curname", item.getCurName());
        values.put("curmovie", item.getCurRate());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<MovieItem> listAll(){
        List<MovieItem> ScoreList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            ScoreList = new ArrayList<MovieItem>();
            while(cursor.moveToNext()){
                MovieItem item = new MovieItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));

                ScoreList.add(item);
            }
            cursor.close();
        }
        db.close();
        return ScoreList;

    }

    public MovieItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        MovieItem movieItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            movieItem = new MovieItem();
            movieItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            movieItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            movieItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return movieItem;
    }
}
