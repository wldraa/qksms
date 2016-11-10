package com.moez.QKSMS.ui.garbage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.data.Filter;
import com.moez.QKSMS.ui.base.ClickyViewHolder;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

import java.util.HashMap;

/**
 * Created by zhangqian on 2016/10/30.
 * to show filter list
 */
public class FilterListAdapter extends RecyclerCursorAdapter<FilterListViewHolder, Filter> {
    public static final String TAG = "FilterListAdapter";

    private int mFilterType = FilterDbHelper.TYPE_WHITE_LIST;

    private RecyclerCursorAdapter.ItemClickListener<Filter> mItemClickListener;

    public FilterListAdapter(QKActivity activity, int type) {
        super(activity);
        mFilterType = type;
    }

    @Override
    protected Filter getItem(int position) {
        mCursor.moveToPosition(position);
        Filter filter = new Filter();
        filter.setId(mCursor.getInt(mCursor.getColumnIndex(FilterDbHelper.COLUMN_ID)));
        filter.setFilterType(mCursor.getInt(mCursor.getColumnIndex(FilterDbHelper.COLUMN_FILTER_TYPE)));
        filter.setName(mCursor.getString(mCursor.getColumnIndex(FilterDbHelper.COLUMN_NAME)));
        return filter;
    }

    @Override
    public FilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_filter, null);
        return new FilterListViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(FilterListViewHolder holder, int position) {
        Filter filter = getItem(position);

        holder.filterName.setText(filter.getName());
        holder.mData = filter;
        holder.mContext = mContext;
        holder.mClickListener = mItemClickListener;
        holder.filterDelete.setOnClickListener(holder);
    }

    public void setFilterType(int type) {
        mFilterType = type;
    }

    public void setItemClickListener(ItemClickListener<Filter> i) {
        mItemClickListener = i;
    }
}
