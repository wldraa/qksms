package com.moez.QKSMS.ui.importer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.LiveViewManager;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.ThemeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangqian on 2016/12/10.
 *
 */
public class ContactImporterAdapter extends SimpleAdapter {
    private List<Map<String, String>> mData;
    private List<CheckBox> mViewList;

    public ContactImporterAdapter(Context context, List<Map<String,String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mData = data;
        mViewList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View res = super.getView(position, convertView, parent);
        mViewList.add(position, (CheckBox) res.findViewById(R.id.contact_list_selected));
        LiveViewManager.registerView(QKPreference.BACKGROUND, this, key -> {
            res.setBackgroundDrawable(ThemeManager.getRippleBackground());
        });
        return res;
    }

    public CheckBox getItemView(int position) {
        return mViewList.get(position);
    }

    public String[] getAllCheckedNumber() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0 ; i < mViewList.size() ; i++) {
            CheckBox cb = mViewList.get(i);
            if (cb.isChecked()) {
                result.add(mData.get(i).get("number"));
            }
        }
        return result.toArray(new String[0]);
    }
}
