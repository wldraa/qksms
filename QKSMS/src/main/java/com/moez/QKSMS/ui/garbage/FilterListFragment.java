package com.moez.QKSMS.ui.garbage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.melnykov.fab.FloatingActionButton;
import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.common.utils.ColorUtils;
import com.moez.QKSMS.data.Filter;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKFragment;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;
import com.moez.QKSMS.ui.dialog.QKDialog;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangqian on 2016/10/30.
 * list to edit the filter
 */
public class FilterListFragment extends QKFragment implements View.OnClickListener, RecyclerCursorAdapter.ItemClickListener<Filter>{
    public static final String TAG = "FilterListFragment";

    private FilterListAdapter mAdapter;
    private int mFilterType = FilterDbHelper.TYPE_WHITE_LIST;
    private FilterDbHelper mFilterDbHelper;

    private LinearLayoutManager mLayoutManager;

    // this editText is used for input filter in dialog
    private EditText mEditText;

    @Bind(R.id.filter_add) FloatingActionButton mFilterAdd;
    @Bind(R.id.filter_list) RecyclerView mFilterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFilterDbHelper = new FilterDbHelper(mContext);
        mAdapter = new FilterListAdapter(mContext, mFilterType);
        mAdapter.changeCursor(mFilterDbHelper.getCursorByFilterType(mFilterType));
        mAdapter.setItemClickListener(this);

        mLayoutManager = new LinearLayoutManager(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_setting, null);
        ButterKnife.bind(this, view);

        mFilterList.setHasFixedSize(true);
        mFilterList.setAdapter(mAdapter);
        mFilterList.setLayoutManager(mLayoutManager);

        mFilterAdd.setOnClickListener(this);

        mFilterAdd.setColorNormal(ThemeManager.getColor());
        mFilterAdd.setColorPressed(ColorUtils.lighten(ThemeManager.getColor()));
        mFilterAdd.attachToRecyclerView(mFilterList);
        mFilterAdd.setColorFilter(ThemeManager.getTextOnColorPrimary());

        return view;
    }

    public void setFilterType(int type) {
        mFilterType = type;
    }

    @Override
    public void onClick(View v) {
        if (v == mFilterAdd) {
            mEditText = new EditText(mContext);
            new QKDialog()
                    .setContext(mContext)
                    .setTitle("添加")
                    .setCustomView(mEditText)
                    .setPositiveButton("确定", this)
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            Filter filter = new Filter();
            String name = mEditText.getText().toString();
            filter.setName(name);
            filter.setFilterType(mFilterType);
            mFilterDbHelper.addFilter(filter);
            mAdapter.changeCursor(mFilterDbHelper.getCursorByFilterType(mFilterType));
        }
    }

    @Override
    public void onItemClick(Filter filter, View view) {
        mFilterDbHelper.removeFilter(filter);
        mAdapter.changeCursor(mFilterDbHelper.getCursorByFilterType(mFilterType));
    }

    @Override
    public void onItemLongClick(Filter filter, View view) {

    }
}
