package com.pack.hardcorelaststraw_08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String STORE_TABLE = "STORE_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ITEM = "ITEM";
    public static final String COLUMN_PRICE = "PRICE";

    public DatabaseHelper(@Nullable Context context){ super(context, "store_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + STORE_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM + " TEXT, " +
                COLUMN_PRICE + " INT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


//    ---------------------------------- ADD ----------------------------------
    public boolean addOne(StoreModel storeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM, storeModel.getItem());
        cv.put(COLUMN_PRICE, storeModel.getPrice());

        long insert = db.insert(STORE_TABLE, null, cv);

        return insert != -1;
    }

    //    ---------------------------------- UPDATE ----------------------------------
    public boolean updateOne(StoreModel storeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM, storeModel.getItem());
        cv.put(COLUMN_PRICE, storeModel.getPrice());

        int update = db.update(STORE_TABLE, cv, "id = ?", new String[]{String.valueOf(storeModel.getId())});
        return update != -1;
    }

    //    ---------------------------------- VIEW ----------------------------------
    public List<StoreModel> getEveryone(String sortBy, String arrange){
        List<StoreModel> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + STORE_TABLE + " ORDER BY " + sortBy + " COLLATE NOCASE " + arrange;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String item = cursor.getString(1);
                int price = cursor.getInt(2);

                StoreModel updateItem = new StoreModel(id,item, price);
                returnList.add(updateItem);
            }while (cursor.moveToNext());
        }else {

        }

        db.close();
        cursor.close();
        return returnList;
    }

    //    ---------------------------------- VIEW ----------------------------------
    public boolean deleteOne(StoreModel storeModel) {
        List<StoreModel> returnList = new ArrayList<>();
        String query = "DELETE FROM " + STORE_TABLE + " WHERE " + COLUMN_ID + " = " + storeModel.getId();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}
