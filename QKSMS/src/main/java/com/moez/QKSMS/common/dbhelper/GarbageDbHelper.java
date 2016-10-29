package com.moez.QKSMS.common.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moez.QKSMS.data.SimpleMessage;

/**
 * Created by zhangqian on 2016/10/29.
 * get and set garbage messages
 */
public class GarbageDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "filter.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "garbage";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE_SEND = "date_send";

    private SQLiteDatabase mDatabase;

    public static final String SQL_GARBAGE_CREATE = "create table " + TABLE_NAME + " (" +
            "id integer primary key autoincrement," +
            "address varchar(30) not null default 1," +
            "body varchar(100) not null default ''," +
            "date_send integer not null" +
            ")";



    protected Context mContext;

    public GarbageDbHelper(Context mContext) {
        super(mContext, DB_NAME, null, DB_VERSION);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_GARBAGE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = this.getWritableDatabase();
        }
        return mDatabase;
    }

    public void addMessage(SimpleMessage message) {
        SQLiteDatabase db = this.getDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ADDRESS, message.getAddress());
        cv.put(COLUMN_BODY, message.getBody());
        cv.put(COLUMN_DATE_SEND, message.getDateSend());

        db.insert(TABLE_NAME, "id", cv);
    }
}
