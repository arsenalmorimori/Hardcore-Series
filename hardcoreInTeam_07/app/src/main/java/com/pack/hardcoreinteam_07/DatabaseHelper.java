package com.pack.hardcoreinteam_07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_STUDENT = "tbl_student";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_COURSE = "course";
    public static final String COLUMN_GWA = "gwa";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "students.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENT + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_COURSE + " TEXT, " + COLUMN_GWA + " FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STUDENT);
        onCreate(db);
    }

    public boolean addOne(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, student.getName());
        cv.put(COLUMN_AGE, student.getAge());
        cv.put(COLUMN_COURSE, student.getCourse());
        cv.put(COLUMN_GWA, student.getGwa());

        long insert = db.insert(TABLE_STUDENT, null, cv);
        return insert != -1;
    }

    public List<Student> getAll(){
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_STUDENT+" ORDER BY "+COLUMN_NAME+" COLLATE NOCASE ASC", null);

        if(cursor.moveToFirst()){
            do{
                students.add(new Student(
                   cursor.getInt(0),
                   cursor.getString(1),
                   cursor.getInt(2),
                   cursor.getString(3),
                   cursor.getFloat(4)
                ));
            }while (cursor.moveToNext());
        }
        return students;
    }

    public boolean updateOne(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, student.getName());
        cv.put(COLUMN_AGE, student.getAge());
        cv.put(COLUMN_COURSE, student.getCourse());
        cv.put(COLUMN_GWA, student.getGwa());

        int update = db.update(TABLE_STUDENT, cv, COLUMN_ID+"=?", new String[] {String.valueOf(student.getId())});
        return update > 0;
    }

    public boolean deleteOne(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int update = db.delete(TABLE_STUDENT, COLUMN_ID+"=?", new String[]{String.valueOf(id)});
        return update > 0;
    }
}
