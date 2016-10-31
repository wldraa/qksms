package com.moez.QKSMS.ui.garbage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.GarbageDbHelper;
import com.moez.QKSMS.common.utils.DateFormatter;
import com.moez.QKSMS.data.SimpleMessage;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

import java.util.Date;

/**
 * Created by zhangqian on 2016/10/31.
 *
 */
public class GarbageAdapter extends RecyclerCursorAdapter<GarbageViewHolder, SimpleMessage> {

    public GarbageAdapter(QKActivity context) {
        super(context);
    }

    @Override
    protected SimpleMessage getItem(int position) {
        mCursor.moveToPosition(position);
        SimpleMessage message = new SimpleMessage();
        message.setId(mCursor.getInt(mCursor.getColumnIndex(GarbageDbHelper.COLUMN_ID)));
        message.setAddress(mCursor.getString(mCursor.getColumnIndex(GarbageDbHelper.COLUMN_ADDRESS)));
        message.setBody(mCursor.getString(mCursor.getColumnIndex(GarbageDbHelper.COLUMN_BODY)));
        message.setDateSend(mCursor.getLong(mCursor.getColumnIndex(GarbageDbHelper.COLUMN_DATE_SEND)));
        return message;
    }

    @Override
    public GarbageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_garbage, null);
        return new GarbageViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(GarbageViewHolder holder, int position) {
        SimpleMessage message = getItem(position);

        holder.address.setText(message.getAddress());
        holder.dateSend.setText(DateFormatter.getConversationTimestamp(mContext, message.getDateSend()));
        holder.body.setText(message.getBody());

    }
}
