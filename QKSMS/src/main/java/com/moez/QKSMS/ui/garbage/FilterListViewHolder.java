package com.moez.QKSMS.ui.garbage;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moez.QKSMS.R;
import com.moez.QKSMS.ui.base.ClickyViewHolder;
import com.moez.QKSMS.ui.base.QKActivity;

/**
 * Created by zhangqian on 2016/10/30.
 * filter view holder
 */
public class FilterListViewHolder extends ClickyViewHolder<Filter> {

    public TextView filterName;
    public Button filterDelete;

    public FilterListViewHolder(QKActivity context, View itemView) {
        super(context, itemView);

        filterName = (TextView) itemView.findViewById(R.id.filter_name);
        filterDelete = (Button) itemView.findViewById(R.id.filter_delete);
    }
}
