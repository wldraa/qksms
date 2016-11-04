package com.moez.QKSMS.ui.garbage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.common.dbhelper.GarbageDbHelper;
import com.moez.QKSMS.common.utils.MessageUtils;
import com.moez.QKSMS.data.Filter;
import com.moez.QKSMS.transaction.SmsHelper;
import com.moez.QKSMS.ui.base.QKFragment;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangqian on 2016/10/31.
 *
 */
public class GarbageFragment extends QKFragment implements RecyclerCursorAdapter.ItemClickListener<GarbageAdapter.SimpleMessage> {
    public static final String TAG = "GarbageFragment";

    private GarbageAdapter mAdapter;
    private GarbageDbHelper mGarbageDbHelper;
    private FilterDbHelper mFilterDbHelper;

    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.garbage_list) RecyclerView mGarbageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGarbageDbHelper = new GarbageDbHelper(mContext);
        mFilterDbHelper = new FilterDbHelper(mContext);
        mAdapter = new GarbageAdapter(mContext);
        mAdapter.changeCursor(mGarbageDbHelper.getAllMessageCursor());
        mAdapter.setItemClickListener(this);

        mLayoutManager = new LinearLayoutManager(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garbage, null);
        ButterKnife.bind(this, view);

        mGarbageList.setAdapter(mAdapter);
        mGarbageList.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onItemClick(GarbageAdapter.SimpleMessage message, View view) {
        switch (view.getId()) {
            case R.id.garbage_root:
                GarbageViewHolder holder = new GarbageViewHolder(mContext, view);
                message.setVisible(!message.getVisible());
                if (message.getVisible()) {
                    holder.body.setMaxLines(10);
                    holder.buttons.setVisibility(View.VISIBLE);
                } else {
                    holder.body.setMaxLines(2);
                    holder.buttons.setVisibility(View.GONE);
                }
                break;
            case R.id.markBlack:
                mFilterDbHelper.addFilter(message.getAddress(), FilterDbHelper.TYPE_BLACK_LIST);
                Toast.makeText(mContext, "加入黑名单成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.markWhite:
                mFilterDbHelper.addFilter(message.getAddress(), FilterDbHelper.TYPE_WHITE_LIST);
                mGarbageDbHelper.removeMessage(message);
                mAdapter.changeCursor(mGarbageDbHelper.getAllMessageCursor());
                SmsHelper.addMessageToInbox(mContext, message.getAddress(), message.getBody(), message.getDateSend());
                Toast.makeText(mContext, "you click restore button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.restore:
                mGarbageDbHelper.removeMessage(message);
                mAdapter.changeCursor(mGarbageDbHelper.getAllMessageCursor());
                SmsHelper.addMessageToInbox(mContext, message.getAddress(), message.getBody(), message.getDateSend());
                Toast.makeText(mContext, "you click restore button", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(mContext, "I don't know what you are clicking", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemLongClick(GarbageAdapter.SimpleMessage object, View view) {

    }
}
