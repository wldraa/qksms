package com.moez.QKSMS.common.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moez.QKSMS.data.SimpleMessage;

/**
 * Created by zhangqian on 2016/10/29.
 * get and set garbage messages
 */
public class GarbageDbHelper extends BaseDbHelper {

    public static final String TABLE_NAME = "garbage";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE_SEND = "date_send";

    public GarbageDbHelper(Context mContext) {
        super(mContext);
    }


    public void addMessage(SimpleMessage message) {
        SQLiteDatabase db = getDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ADDRESS, message.getAddress());
        cv.put(COLUMN_BODY, message.getBody());
        cv.put(COLUMN_DATE_SEND, message.getDateSend());
        if (message.getId() > 0) {
            cv.put(COLUMN_ID, message.getId());
        }

        db.insert(TABLE_NAME, COLUMN_ID, cv);
    }

    public Cursor getAllMessageCursor() {
        SQLiteDatabase db = getDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " order by id desc", null);
    }

    public void removeMessage(SimpleMessage message) {
        SQLiteDatabase db = getDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(message.getId())});
    }

    public void clearMessage() {
        SQLiteDatabase db = getDatabase();
        db.delete(TABLE_NAME, "", null);
    }
}
