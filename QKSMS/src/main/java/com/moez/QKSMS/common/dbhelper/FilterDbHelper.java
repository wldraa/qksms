package com.moez.QKSMS.common.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by zhangqian on 2016/10/29.
 * get and set filter
 */
public class FilterDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "filter.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "filter";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FILTER_TYPE = "filter_type";
    public static final String COLUMN_CONTENT = "content";

    public static final int TYPE_WHITE_LIST = 1;
    public static final int TYPE_BLACK_LIST = 2;
    public static final int TYPE_KEYWORD = 3;


    private SQLiteDatabase mDatabase;

    public static final String SQL_FILTER_CREATE = "create table filter (" +
            "id integer primary key autoincrement," +
            "filter_type integer not null default 1," +
            "content varchar(100) not null default ''" +
            ")";


    protected Context mContext;

    public FilterDbHelper(Context mContext) {
        super(mContext, DB_NAME, null, DB_VERSION);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_FILTER_CREATE);
        db.execSQL("insert into filter values (null, 3, 'test')");
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

    public ArrayList<String> getWhiteList() {
        return getList(TYPE_WHITE_LIST);
    }

    public ArrayList<String> getBlackList() {
        return getList(TYPE_BLACK_LIST);
    }

    public ArrayList<String> getKeywordList() {
        return getList(TYPE_KEYWORD);
    }



    public ArrayList<String> getList(int type) {
        ArrayList<String> result = new ArrayList<>();

        SQLiteDatabase db = getDatabase();
        Cursor cursor= db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FILTER_TYPE + "=?", new String[]{String.valueOf(type)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String white = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                result.add(white);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }
}
