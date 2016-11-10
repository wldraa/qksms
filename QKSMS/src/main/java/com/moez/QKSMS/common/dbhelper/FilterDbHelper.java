package com.moez.QKSMS.common.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moez.QKSMS.common.utils.GarbageUtils;
import com.moez.QKSMS.data.Filter;

import java.util.ArrayList;

/**
 * Created by zhangqian on 2016/10/29.
 * get and set filter
 */
public class FilterDbHelper extends BaseDbHelper {

    public static final String TABLE_NAME = "filter";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FILTER_TYPE = "filter_type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CONTENT = "content";

    public static final int TYPE_WHITE_LIST = 1;
    public static final int TYPE_BLACK_LIST = 2;
    public static final int TYPE_KEYWORD = 3;

    public FilterDbHelper(Context mContext) {
        super(mContext);
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
        Cursor cursor = getCursorByFilterType(type);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String white = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                result.add(white);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    public Cursor getCursorByFilterType(int type) {
        SQLiteDatabase db = getDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FILTER_TYPE + "=?", new String[]{String.valueOf(type)});
    }

    public void addFilter(String name, int type) {
        addFilter(name, type, GarbageUtils.CONTENT_PLAIN);
    }

    public void addFilter(String name, int type, int contentType) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CONTENT, GarbageUtils.complieFilter(name, contentType));
        cv.put(COLUMN_FILTER_TYPE, type);
        SQLiteDatabase db = getDatabase();
        db.insert(TABLE_NAME, COLUMN_ID, cv);
    }

    public void addFilter(Filter filter) {
        addFilter(filter.getName(), filter.getFilterType());
    }

    public void removeFilter(Filter filter) {
        SQLiteDatabase db = getDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(filter.getId())});
    }
}
