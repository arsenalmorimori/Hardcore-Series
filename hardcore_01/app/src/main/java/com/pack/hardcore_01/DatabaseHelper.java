package com.pack.hardcore_01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PRICE = "PRICE";
    public static final String GROCERY_TABLE = "GROCERY_TABLE";

    public DatabaseHelper(@Nullable Context context){ super(context, "grocery.db", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + GROCERY_TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_PRICE + " INT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    //    ------------------------ ADD ------------------------
    public boolean addOne(GroceryModel groceryModel){

//        W C I - C-1

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, groceryModel.getName());
        cv.put(COLUMN_PRICE, groceryModel.getPrice());

        long insert = db.insert(GROCERY_TABLE, null, cv);

        if (insert == -1) {
            return false;
        }else{
            return true;
        }
    }

//    ------------------------ VIEW ------------------------
    public List<GroceryModel> getEveryone() {

        List<GroceryModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + GROCERY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int col_id = cursor.getInt(0);
                String col_name = cursor.getString(1);
                int col_price = cursor.getInt(2);

                GroceryModel newGroceryModel = new GroceryModel(col_id,col_name,col_price);
                returnList.add(newGroceryModel);
            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return returnList;
    }

//    ------------------------ UPDATE ------------------------
 public boolean updateOne(GroceryModel groceryModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();


     cv.put(COLUMN_NAME, groceryModel.getName());
     cv.put(COLUMN_PRICE, groceryModel.getPrice());

     int update = db.update(GROCERY_TABLE, cv, "id = ?", new String[]{String.valueOf(groceryModel.getId())});
     return update != -1;
 }

//    ------------------------ DELETE ------------------------
 public boolean deleteOne(GroceryModel groceryModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + GROCERY_TABLE + " WHERE " + COLUMN_ID + " = " + groceryModel.getId();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }
}
