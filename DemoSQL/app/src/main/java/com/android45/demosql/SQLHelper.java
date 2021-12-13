package com.android45.demosql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    //Phím tắt: logt
    private static final String TAG = "Cannot invoke method length() on null object";
    private static final String DB_NAME = "OrderFoods.db";
    private static final String DB_TABLE = "Foods";
    private static final int DB_VERSION = 1;

    private static String DB_FOODS_ID = "id";
    private static String DB_FOODS_NAME = "name";
    private static String DB_FOODS_QUANTITY = "quantity";
    private static String DB_FOODS_PRICE = "price";

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE Foods(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name Text, " +
                "quantity INTEGER," +
                "price INTEGER)";

        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public void addFoods(Foods foods) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_FOODS_NAME, foods.getName());
        contentValues.put(DB_FOODS_QUANTITY, foods.getQuantity());
        contentValues.put(DB_FOODS_PRICE, foods.getPrice());

        sqLiteDatabase.insert(DB_TABLE, null, contentValues);
    }

    public void onUpdateFoods(String id, Foods foods) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_FOODS_NAME, foods.getName());
        contentValues.put(DB_FOODS_QUANTITY, foods.getQuantity());
        contentValues.put(DB_FOODS_PRICE, foods.getPrice());

        sqLiteDatabase.update(DB_TABLE, contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public void onDeleteAll() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE, null, null);
    }

    public void onDeleteFoods(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Foods> getAllFoods() {
        List<Foods> foodsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Foods foods;

        Cursor cursor = sqLiteDatabase.query(false, DB_TABLE, null, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DB_FOODS_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DB_FOODS_NAME));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex(DB_FOODS_PRICE));
            @SuppressLint("Range") String quantity = cursor.getString(cursor.getColumnIndex(DB_FOODS_QUANTITY));

            foods = new Foods(id, name, quantity, price);
            foodsList.add(foods);
        }

        return foodsList;
    }
}
