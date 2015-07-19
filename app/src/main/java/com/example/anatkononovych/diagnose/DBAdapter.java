package com.example.anatkononovych.diagnose;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by anatkononovych on 4/22/15.
 */
public class DBAdapter {
    static final String DATABASE_NAME = "MyDB.db";
    static final String DATABASE_TABLE = "Motions";
    static final int DATABASE_VERSION = 1;
    static final String KEY_USER_NAME = "userName";
    static final String KEY_LEVEL_NUM = "levelNum";
    static final String KEY_IS_ON_TARGET = "isOnTarget";
    static final String KEY_DISTRACTOR = "distractor";
    static final String KEY_RESPONSE_TIME = "responseTime";
    static final String KEY_PERFORMANCE_TIME = "performanceTime";
    static final String KEY_NUM_OF_TOUCHES = "numOfTouches";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + "("+KEY_USER_NAME+" TEXT,"+KEY_LEVEL_NUM+
                    " INTEGER,"+KEY_DISTRACTOR+" TEXT,"+KEY_PERFORMANCE_TIME+" LONG,"
                    +KEY_RESPONSE_TIME+" LONG,"+KEY_NUM_OF_TOUCHES+" INTEGER,"+KEY_IS_ON_TARGET+" INTEGER)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    public void open(){
        db = DBHelper.getWritableDatabase();
    }
    public void close(){
        DBHelper.close();
    }

    public long insertMotion(String userName, int levelNum, String distractor, long performanceTime, long responseTime,
                             int numOfTouches, int isOnTarget){
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, userName);
        values.put(KEY_LEVEL_NUM, levelNum);
        values.put(KEY_DISTRACTOR, distractor);
        values.put(KEY_RESPONSE_TIME, responseTime);
        values.put(KEY_PERFORMANCE_TIME, performanceTime);
        values.put(KEY_NUM_OF_TOUCHES, numOfTouches);
        values.put(KEY_IS_ON_TARGET, isOnTarget);
        return db.insert(DATABASE_TABLE, null, values);

    }

    public Cursor getAllMotions(){
        return db.query(DATABASE_TABLE, new String[]{KEY_USER_NAME, KEY_LEVEL_NUM, KEY_DISTRACTOR,
                        KEY_RESPONSE_TIME, KEY_PERFORMANCE_TIME, KEY_NUM_OF_TOUCHES, KEY_IS_ON_TARGET}, null,
                null, null, null, null);
    }

    public void deleteAllMotions(){
        db.delete(DATABASE_TABLE, null, null);
    }
}
