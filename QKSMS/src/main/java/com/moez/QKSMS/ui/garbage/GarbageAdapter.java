package com.moez.QKSMS.ui.garbage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.LiveViewManager;
import com.moez.QKSMS.common.dbhelper.GarbageDbHelper;
import com.moez.QKSMS.common.utils.DateFormatter;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

import java.util.Date;

/**
 * Created by zhangqian on 2016/10/31.
 *
 */
public class GarbageAdapter extends RecyclerCursorAdapter<GarbageViewHolder, GarbageAdapter.SimpleMessage> {
    public static final String TAG = "GarbageAdapter";

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
        View view = inflater.inflate(R.layout.list_item_garbage, parent, false);

        GarbageViewHolder holder = new GarbageViewHolder(mContext, view);
        LiveViewManager.registerView(QKPreference.BACKGROUND, this, key -> {
            holder.root.setBackgroundDrawable(ThemeManager.getRippleBackground());
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(GarbageViewHolder holder, int position) {
        SimpleMessage message = getItem(position);

        holder.mData = message;
        holder.address.setText(message.getAddress());
//        holder.address.setTextSize(20);
        holder.dateSend.setText(DateFormatter.getConversationTimestamp(mContext, message.getDateSend()));
        holder.body.setText(message.getBody());

        holder.mClickListener = mItemClickListener;
        holder.root.setOnClickListener(holder);
        holder.markBlack.setOnClickListener(holder);
        holder.restore.setOnClickListener(holder);
        holder.markWhite.setOnClickListener(holder);

        if (message.getVisible()) {
            holder.body.setMaxLines(10);
            holder.buttons.setVisibility(View.VISIBLE);
        }
    }

    public class SimpleMessage extends com.moez.QKSMS.data.SimpleMessage {
        private boolean visible = false;
        public boolean getVisible() {
            return visible;
        }
        public void setVisible(boolean v) {
            visible = v;
        }
    }
}
