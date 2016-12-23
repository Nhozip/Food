package com.it.nhozip.food.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.it.nhozip.food.obj.Food;

import java.util.ArrayList;

/**
 * Created by Nhozip on 4/25/2016.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food.db";

    public static final String TABLE_FOOD = "tb_food";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_IMG = "img";
    public static final String COL_VIDEO = "video";
    public static final String COL_HUONG_DAN = "huongdan";
    public static final String COL_NGUYEN_LIEU = "nguyenlieu";

    public static final int DATA_VERSION = 1;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TB_NOTE =
                "CREATE TABLE " + TABLE_FOOD + "(" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" +
                        ", " + COL_NAME + " TEXT NOT NULL" +
                        "," + COL_IMG + " TEXT NOT NULL" + "," + COL_HUONG_DAN +
                        " TEXT NOT NULL" + "," + COL_VIDEO + " TEXT NOT NULL" + "," + COL_NGUYEN_LIEU + " TEXT NOT NULL" +
                        ")";
        db.execSQL(TB_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void open() {
        try {
            database = getWritableDatabase();
        } catch (Exception e) {
            Log.e("open", e + "");
        }

    }

    public void close() {
        if (database != null && database.isOpen()) {
            try {
                database.close();
            } catch (Exception e) {
                Log.e("close", e + "");
            }
        }
    }

    public long insertNote(Food food) {
        open();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, food.getName());
        values.put(COL_IMG, food.getImg());
        values.put(COL_HUONG_DAN, food.getHuongdan());
        values.put(COL_VIDEO, food.getVideo());
        values.put(COL_NGUYEN_LIEU, food.getNguyenlieu());
        long i = database.insert(TABLE_FOOD, null, values);
        close();
        return i;
    }

    public void dellAll(){
        open();
        database.execSQL("delete from "+ TABLE_FOOD);
        close();
    }
    public long dellNote(String img) {
        open();
        long i = 0;
        try {
            i = database.delete(TABLE_FOOD, COL_IMG + "=?", new String[]{img + ""});
            close();
        } catch (Exception e) {
            Log.e("del", "obj ko ton tai");
        }

        return i;
    }

    public ArrayList<Food> getListNote() {
        open();
        ArrayList<Food> listFood = new ArrayList<>();
        Cursor cursor = database.query(TABLE_FOOD, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Food food = new Food();
            food.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            food.setImg(cursor.getString(cursor.getColumnIndex(COL_IMG)));
            food.setHuongdan(cursor.getString(cursor.getColumnIndex(COL_HUONG_DAN)));
            food.setVideo(cursor.getString(cursor.getColumnIndex(COL_VIDEO)));
            food.setNguyenlieu(cursor.getString(cursor.getColumnIndex(COL_NGUYEN_LIEU)));
            listFood.add(food);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listFood;
    }

    public Food getNot(String img) {
        open();
        Cursor cursor = database.rawQuery("SELECT *FROM " + TABLE_FOOD + " WHERE " + COL_IMG + " = " + img, null);
        cursor.moveToFirst();
        Food food = new Food();
        food.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
        food.setImg(cursor.getString(cursor.getColumnIndex(COL_IMG)));
        food.setHuongdan(cursor.getString(cursor.getColumnIndex(COL_HUONG_DAN)));
        food.setVideo(cursor.getString(cursor.getColumnIndex(COL_VIDEO)));
        food.setNguyenlieu(cursor.getString(cursor.getColumnIndex(COL_NGUYEN_LIEU)));
        close();
        return food;
    }

    public String getNotIMG(String img) {
        open();
        String[] args = {img};
        String link_img;
        Cursor cursor = database.rawQuery("SELECT * FROM tb_food WHERE img = ?", args);
        try {
            cursor.moveToFirst();
            link_img = cursor.getString(2);
        } catch (Exception e) {
            link_img = "XXX";
        }
        close();
        return link_img;
    }

}
