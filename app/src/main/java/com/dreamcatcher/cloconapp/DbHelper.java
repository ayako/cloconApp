package com.dreamcatcher.cloconapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "clocon";
    public static final String ITEM_TABLE_NAME = "item";

    public  DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE = "CREATE TABLE " + ITEM_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "detail TEXT, " +
                "imageUrl TEXT)";
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void addData(ItemData itemData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", itemData.title);
        cv.put("detail", itemData.detail);
        cv.put("imageUrl",itemData.imageUrl);

        long result =db.insert(ITEM_TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
        }
    }
    
    void updateData(ItemData itemData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", itemData.title);
        cv.put("detail", itemData.detail);
        cv.put("imageUrl",itemData.imageUrl);

        long result =db.update(ITEM_TABLE_NAME, cv, "id = " + itemData.id, null);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readData(){
        String query = "SELECT * FROM " + ITEM_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    public static class ItemData{
        private Integer id;
        private String title;
        private String detail;
        private String imageUrl;

        public ItemData(Integer id, String title, String detail, String imageUrl){
            this.id = id;
            this.title = title;
            this.detail = detail;
            this.imageUrl = imageUrl;
        }
    }
}
