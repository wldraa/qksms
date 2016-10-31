package com.moez.QKSMS.ui.garbage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.GarbageDbHelper;
import com.moez.QKSMS.ui.base.QKFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangqian on 2016/10/31.
 *
 */
public class GarbageFragment extends QKFragment {
    public static final String TAG = "GarbageFragment";

    private GarbageAdapter mAdapter;
    private GarbageDbHelper mGarbageDbHelper;

    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.garbage_list) RecyclerView mGarbageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGarbageDbHelper = new GarbageDbHelper(mContext);
        mAdapter = new GarbageAdapter(mContext);
        mAdapter.changeCursor(mGarbageDbHelper.getAllMessageCursor());

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
}
