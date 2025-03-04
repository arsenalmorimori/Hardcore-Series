package com.pack.hardcore_06;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String STORE_TABLE = "STORE_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ITEM = "ITEM";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_QUANTITY = "QUANTITY";
    public static final String COLUMN_USER_ID = "USER_ID";

    public DatabaseHelper(@Nullable Context context){ super(context, "store_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + STORE_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM + " TEXT, " +
                COLUMN_QUANTITY + " INT)" ;
        sqLiteDatabase.execSQL(createTable);

        String createTableUser = "CREATE TABLE " + USER_TABLE + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT)" ;
        sqLiteDatabase.execSQL(createTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    ------------------------ USERNAME ------------------------
public boolean userAdd(Username username){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put(COLUMN_NAME, username.getName());

    long insert = db.insert(USER_TABLE, null, cv);
    return insert != -1;
}

    public boolean userUpdate(Username username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, username.getName());

        int update = db.update(USER_TABLE, cv, "id = ?", new String[]{String.valueOf((username.getId()))});

        return update != -1;
    }
    public List<Username> getUsername(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Username> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + USER_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String username = cursor.getString(1);

                Username name = new Username(id,username);
                returnList.add(name);
            }while(cursor.moveToNext());
        }else{

        }

        db.close();
        cursor.close();
        return returnList;
    }



//    ------------------------ ADD ------------------------
    public boolean addOne(StoreModel storeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM, storeModel.getItem());
        cv.put(COLUMN_QUANTITY, storeModel.getQuantity());

        long insert = db.insert(STORE_TABLE, null, cv);
        return insert != -1;
    }

//    ------------------------ VIEW ------------------------
    public List<StoreModel> getEveryone(String arrange){
        SQLiteDatabase db = this.getReadableDatabase();
        List<StoreModel> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + STORE_TABLE + " ORDER BY "  +  COLUMN_ID + " COLLATE NOCASE " + arrange;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
          do{
              int id = cursor.getInt(0);
              String item = cursor.getString(1);
              int quantity = cursor.getInt(2);

              StoreModel st = new StoreModel(id,item,quantity);
              returnList.add(st);

          }while(cursor.moveToNext());
        }else{

        }

        db.close();
        cursor.close();
        return returnList;
    }

    public List<String> getItem(String arrange){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + STORE_TABLE + " ORDER BY " + COLUMN_ITEM + " COLLATE NOCASE " + arrange;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
          do{
              int id = cursor.getInt(0);
              String item = cursor.getString(1);
              int quantity = cursor.getInt(2);

              returnList.add(item + "  :  " + quantity);
          }while(cursor.moveToNext());
        }else{

        }

        db.close();
        cursor.close();
        return returnList;
    }

    public List<String> getQuantity(String arrange){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + STORE_TABLE + " ORDER BY " + COLUMN_QUANTITY + " COLLATE NOCASE " + arrange;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
          do{
              int id = cursor.getInt(0);
              String item = cursor.getString(1);
              int quantity = cursor.getInt(2);


              returnList.add(item + "  :  " + quantity);
          }while(cursor.moveToNext());
        }else{

        }

        db.close();
        cursor.close();
        return returnList;
    }


//    ------------------------ DELETE ------------------------
    public boolean deleteOne(StoreModel storeModel){
        SQLiteDatabase db = this.getReadableDatabase();
        List<StoreModel> returnList = new ArrayList<>();
        String query = "DELETE FROM " + STORE_TABLE + " WHERE " + COLUMN_ID + " = " + storeModel.getId();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }


//    ------------------------ UPDATE ------------------------
public boolean update(StoreModel storeModel){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put(COLUMN_ITEM, storeModel.getItem());
    cv.put(COLUMN_QUANTITY, storeModel.getQuantity());
    int update = db.update(STORE_TABLE, cv, "id = ?", new String[]{String.valueOf(storeModel.getId())});

    return update != -1;
}

}
