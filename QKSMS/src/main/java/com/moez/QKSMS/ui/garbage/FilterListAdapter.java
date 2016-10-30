package com.moez.QKSMS.ui.garbage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;

/**
 * Created by zhangqian on 2016/10/30.
 * to show filter list
 */
public class FilterListAdapter extends RecyclerCursorAdapter<FilterListViewHolder, Filter> {
    public static final String TAG = "FilterListAdapter";

    private int mFilterType = FilterDbHelper.TYPE_WHITE_LIST;

    public FilterListAdapter(QKActivity activity) {
        super(activity);
    }

    public FilterListAdapter(QKActivity activity, int type) {
        super(activity);
        mFilterType = type;
    }

    @Override
    protected Filter getItem(int position) {
        mCursor.moveToPosition(position);
        return new Filter(mCursor.getString(mCursor.getColumnIndex(FilterDbHelper.COLUMN_CONTENT)));
    }

    @Override
    public FilterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_filter, null);

        FilterListViewHolder viewHolder = new FilterListViewHolder(mContext, view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FilterListViewHolder holder, int position) {
        Filter filter = getItem(position);

        holder.filterName.setText(filter.getName());
    }

    public void setFilterType(int type) {
        mFilterType = type;
    }
}
