package com.example.helloworld2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "todo.db";
    private static final String TABLE_NAME = "list";
    private static final String COL_1 = "id";
    private static final String COL_2 = "noteTitle";
    private static final String COL_3 = "noteDetail";
    private static final String COL_4 = "status";

    public MySQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*===================================================================*/
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_1 + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " Text NOT NULL," +
                COL_3 + " Text NOT NULL," +
                COL_4 + " number DEFAULT 0)" + ";";

        Log.d("List", createTable);

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("Drop table IF EXISTS " + TABLE_NAME + ";");
        this.onCreate(db);
    }

    public boolean addRecord(ListItem listItem) {
        ContentValues values = new ContentValues();
        values.put(COL_2,listItem.getNoteTitle());
        values.put(COL_3, listItem.getNoteDetail());
        values.put(COL_4, "1");

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, values);


        db.close();

        if (result == 0) return false;
        else
            return true;
    }

    public ArrayList<String> display() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " +
                TABLE_NAME + ";";
        ArrayList<String> tasksDisplay = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()) {
            tasksDisplay.add(c.getString(1));
        }
        return tasksDisplay;

    }

}
