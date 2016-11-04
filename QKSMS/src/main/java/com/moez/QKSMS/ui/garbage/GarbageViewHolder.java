package com.moez.QKSMS.ui.garbage;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.moez.QKSMS.R;
import com.moez.QKSMS.data.SimpleMessage;
import com.moez.QKSMS.ui.base.ClickyViewHolder;
import com.moez.QKSMS.ui.base.QKActivity;
import com.moez.QKSMS.ui.view.QKTextView;

import butterknife.ButterKnife;

/**
 * Created by zhangqian on 2016/10/31.
 *
 */
public class GarbageViewHolder extends ClickyViewHolder<GarbageAdapter.SimpleMessage> {

    public LinearLayout root;
    public QKTextView address;
    public QKTextView dateSend;
    public QKTextView body;
    public LinearLayout buttons;
    public Button restore;
    public Button markBlack;
    public Button markWhite;

    public GarbageViewHolder(QKActivity context, View itemView) {
        super(context, itemView);

        root = (LinearLayout) itemView;
        address = (QKTextView) itemView.findViewById(R.id.address);
        dateSend = (QKTextView) itemView.findViewById(R.id.dateSend);
        body = (QKTextView) itemView.findViewById(R.id.body);

        buttons = (LinearLayout) itemView.findViewById(R.id.garbage_buttons);
        restore = (Button) itemView.findViewById(R.id.restore);
        markBlack = (Button) itemView.findViewById(R.id.markBlack);
        markWhite = (Button) itemView.findViewById(R.id.markWhite);
    }
}
