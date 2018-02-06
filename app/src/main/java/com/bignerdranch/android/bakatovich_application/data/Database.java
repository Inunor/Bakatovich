package com.bignerdranch.android.bakatovich_application.data;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class Database {

    private interface AppDatabase {
        String TABLE_NAME = "launched";

        interface Columns extends BaseColumns {
            String FIELD_NUMBER = "number";
            String FIELD_TITLE = "package_name";
        }

        String CREATE_TABLE_SCRIPT =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(" +
                        Columns.FIELD_NUMBER + " NUMBER, " +
                        Columns.FIELD_TITLE + " TEXT" +
                        ")";

        String DROP_TABLE_SCRIPT =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    private static class MyDbHelper extends SQLiteOpenHelper {
        static final int DATABASE_VERSION = 1;
        static final String DATABASE_NAME = "applications.db";

        MyDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(AppDatabase.CREATE_TABLE_SCRIPT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(AppDatabase.DROP_TABLE_SCRIPT);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    private static MyDbHelper dbHelper;

    public static void initialize(Activity activity) {
        dbHelper = new MyDbHelper(activity);
    }

    private static void insert(Entry entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppDatabase.Columns.FIELD_NUMBER, entry.getLaunched());
        contentValues.put(AppDatabase.Columns.FIELD_TITLE, entry.getPackageName());
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(
                    AppDatabase.TABLE_NAME,
                    null,
                    contentValues
            );
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private static void update(Entry entry) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppDatabase.Columns.FIELD_NUMBER, entry.getLaunched());
        contentValues.put(AppDatabase.Columns.FIELD_TITLE, entry.getPackageName());

        String table = AppDatabase.TABLE_NAME;
        String whereClause = AppDatabase.Columns.FIELD_TITLE + " = ?";
        String[] whereArgs =  {entry.getPackageName()};

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(table, contentValues, whereClause, whereArgs);
        } catch (SQLiteException e) {
           e.printStackTrace();
        }
    }

    public static void insertOrUpdate(Entry entry) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String table = AppDatabase.TABLE_NAME;
            String[] projection = {AppDatabase.Columns.FIELD_NUMBER};
            String selection = AppDatabase.Columns.FIELD_TITLE + " = ?";
            String[] selectionArgs = {entry.getPackageName()};

            Cursor cursor = db.query(
                    table,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if (cursor.getCount() == 0) {
                insert(entry);
            } else {
                update(entry);
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public static int get(String title) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String table = AppDatabase.TABLE_NAME;
            String[] projection = {AppDatabase.Columns.FIELD_NUMBER};
            String selection = AppDatabase.Columns.FIELD_TITLE + " = ?";
            String[] selectionArgs = {title};

            Cursor cursor = db.query(
                    table,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            int result = 0;
            while (cursor.moveToNext()) {
                result += cursor.getInt(cursor.getColumnIndex(AppDatabase.Columns.FIELD_NUMBER));
            }
            cursor.close();
            return result;
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void remove(Entry entry) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String table  = AppDatabase.TABLE_NAME;
            String whereClause = AppDatabase.Columns.FIELD_TITLE + " = ?";
            String[] whereArgs = {entry.getPackageName()};

            db.delete(table, whereClause, whereArgs);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(AppDatabase.TABLE_NAME, null, null);
            db.close();
        } catch (SQLiteException e) {
           e.printStackTrace();
        }
    }
}
