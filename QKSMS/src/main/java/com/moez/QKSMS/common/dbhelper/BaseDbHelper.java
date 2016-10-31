package com.moez.QKSMS.common.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhangqian on 2016/10/31.
 * qksms.db base database open helper
 */
public class BaseDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "qksms.db";
    public static final int DB_VERSION = 1;

    private SQLiteDatabase mDatabase;

    public static final String SQL_FILTER_CREATE = "create table filter (" +
            "id integer primary key autoincrement," +
            "filter_type integer not null default 1," +
            "content varchar(100) not null default ''" +
            ")";

    public static final String SQL_GARBAGE_CREATE = "create table garbage (" +
            "id integer primary key autoincrement," +
            "address varchar(30) not null default '1'," +
            "body nvarchar(1000) not null default ''," +
            "date_send integer not null" +
            ")";


    protected Context mContext;

    public BaseDbHelper(Context mContext) {
        super(mContext, DB_NAME, null, DB_VERSION);
        this.mContext = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_FILTER_CREATE);
        db.execSQL(SQL_GARBAGE_CREATE);
//        db.execSQL("insert into garbage values(null, '1066164125', '广告来了', '1477897443111')");
//        db.execSQL("insert into garbage values(null, '4001212345', '这是一条很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很很长的广告', '1477897443111')");
//        db.execSQL("insert into garbage values(null, '17325637854', '', '1477897443111')");
//        db.execSQL("insert into garbage values(null, '4564645631', '【xx科技】难码123131', '1477897443111')");
//        db.execSQL("insert into garbage values(null, '106902608403503', '231434235432521423143张三', '1477897443111')");
//        db.execSQL("insert into garbage values(null, '106902608403503', '这是一条很普通的广告', '1477897443111')");
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
}
