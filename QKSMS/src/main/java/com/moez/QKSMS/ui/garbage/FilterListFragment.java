package com.moez.QKSMS.ui.garbage;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.moez.QKSMS.R;
import com.moez.QKSMS.common.LiveViewManager;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.common.utils.ColorUtils;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangqian on 2016/10/30.
 * list to edit the filter
 */
public class FilterListFragment extends QKFragment {
    public static final String TAG = "FilterListFragment";

    private FilterListAdapter mAdapter;
    private int mFilterType = FilterDbHelper.TYPE_WHITE_LIST;
    private FilterDbHelper mFilterDbHelper;

    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.filter_add) FloatingActionButton mFilterAdd;
    @Bind(R.id.filter_list) RecyclerView mFilterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFilterDbHelper = new FilterDbHelper(mContext);
        mAdapter = new FilterListAdapter(mContext, mFilterType);
        mAdapter.changeCursor(mFilterDbHelper.getCursorByFilterType(mFilterType));

        mLayoutManager = new LinearLayoutManager(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_setting, null);
        ButterKnife.bind(this, view);

        mFilterList.setHasFixedSize(true);
        mFilterList.setAdapter(mAdapter);
        mFilterList.setLayoutManager(mLayoutManager);

        mFilterAdd.setColorNormal(ThemeManager.getColor());
        mFilterAdd.setColorPressed(ColorUtils.lighten(ThemeManager.getColor()));
        mFilterAdd.attachToRecyclerView(mFilterList);
        mFilterAdd.setColorFilter(ThemeManager.getTextOnColorPrimary());

        return view;
    }

    public void setFilterType(int type) {
        mFilterType = type;
    }

}
