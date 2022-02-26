package com.example.uidemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Blob;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    //DATABASE VERSION
    private static final int DATABASE_VERSION = 1;
    //DATABASE NAME
    public static final String DB_NAME ="logindata";

    //TABLE NAME
    public static final String TABLE_NAME = "users";

    //column names
//    public static final String COLUMN_ID = "id";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public SQLiteDatabaseHandler(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("abd","created");
        String user_create = "CREATE TABLE " + TABLE_NAME + "("
                + EMAIL + " TEXT,"
                + PASSWORD + " TEXT"
                + ")";
        db.execSQL(user_create);

    }

    public void insertdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(EMAIL,"abc@gmail.com");
        Values.put(PASSWORD,"12345678");
        db.insert(TABLE_NAME,null,Values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor fetch() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{EMAIL,PASSWORD}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
