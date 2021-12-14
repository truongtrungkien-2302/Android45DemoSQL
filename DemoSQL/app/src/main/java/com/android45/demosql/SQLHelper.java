package com.android45.demosql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    SQLiteDatabase sqLiteDatabase;
    //Phím tắt: logt
    private Context context;
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
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE " + DB_TABLE + "(" +
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

        long result = sqLiteDatabase.insert(DB_TABLE, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + DB_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null)
            cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor;
    }

//    public void onUpdateFoods(String id, Foods foods) {
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(DB_FOODS_NAME, foods.getName());
//        contentValues.put(DB_FOODS_QUANTITY, foods.getQuantity());
//        contentValues.put(DB_FOODS_PRICE, foods.getPrice());
//
//        long result = sqLiteDatabase.update(DB_TABLE, contentValues, "id = ?", new String[]{String.valueOf(id)});
//
//        if (result == -1) {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void onUpdateFoods(String id, String name, String quantity, String price) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_FOODS_NAME, name);
        contentValues.put(DB_FOODS_QUANTITY, quantity);
        contentValues.put(DB_FOODS_PRICE, price);

        long result = sqLiteDatabase.update(DB_TABLE, contentValues, "id = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteAll() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE, null, null);
    }

    public void onDeleteFoods(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        long result = sqLiteDatabase.delete(DB_TABLE, "id = ?", new String[]{String.valueOf(id)});

        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
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
