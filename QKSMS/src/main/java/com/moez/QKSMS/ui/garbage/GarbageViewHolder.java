package com.moez.QKSMS.ui.garbage;

import android.view.View;
import android.widget.Button;

import com.moez.QKSMS.R;
import com.moez.QKSMS.data.SimpleMessage;
import com.moez.QKSMS.ui.base.ClickyViewHolder;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.view.QKTextView;

/**
 * Created by zhangqian on 2016/10/31.
 *
 */
public class GarbageViewHolder extends ClickyViewHolder<SimpleMessage> {

    public QKTextView address;
    public QKTextView dateSend;
    public QKTextView body;
    public Button restore;
    public Button markBlack;

    public GarbageViewHolder(QKActivity context, View itemView) {
        super(context, itemView);

        address = (QKTextView) itemView.findViewById(R.id.address);
        dateSend = (QKTextView) itemView.findViewById(R.id.dateSend);
        body = (QKTextView) itemView.findViewById(R.id.body);
        restore = (Button) itemView.findViewById(R.id.restore);
        markBlack = (Button) itemView.findViewById(R.id.markBlack);
    }
}
