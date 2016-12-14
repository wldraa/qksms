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
import com.moez.QKSMS.ui.view.AvatarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangqian on 2016/12/10.
 *
 */
public class ContactImporterAdapter extends SimpleAdapter {
    public static final String TAG = "ContactImporterAdapter";
    private List<Map<String, String>> mData;
    private HashMap<Integer, CheckBox> mCheckBoxList;

    public ContactImporterAdapter(Context context, List<Map<String,String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mData = data;
        for (Map<String, String> map: mData) {
            map.put("checked", "false");
        }
        mCheckBoxList = new HashMap<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View res = super.getView(position, convertView, parent);
        CheckBox cb = (CheckBox) res.findViewById(R.id.contact_list_selected);
        cb.setChecked("true".equals(mData.get(position).get("checked")));
        if (!mCheckBoxList.containsKey(position)) {
            mCheckBoxList.put(position, cb);
        }
        AvatarView avatarView = (AvatarView) res.findViewById(R.id.contact_list_contact);
        avatarView.setContactName(mData.get(position).get("name"));
        if (convertView == null) {
            LiveViewManager.registerView(QKPreference.BACKGROUND, this, key -> {
                res.setBackgroundDrawable(ThemeManager.getRippleBackground());
            });
        }
        return res;
    }

    public void check(View view, int position) {
        Map<String, String> map = mData.get(position);
        if ("true".equals(map.get("checked"))) {
            map.put("checked", "false");
        } else {
            map.put("checked", "true");
        }
        CheckBox cb = (CheckBox) view.findViewById(R.id.contact_list_selected);
        cb.setChecked(!cb.isChecked());
    }

    public CheckBox getItemView(int position) {
        return mCheckBoxList.get(position);
    }

    public void checkAll(int first, int last) {
        for (Map<String, String> map: mData) {
            map.put("checked", "true");
        }
        if (last > first) {
            while (first <= last) {
                CheckBox cb =  mCheckBoxList.get(first);
                if (cb != null) {
                    cb.setChecked(true);
                }
                first++;
            }
        }
    }

    public void reveerseCheck(int first, int last) {
        for (Map<String, String> map: mData) {
            if ("true".equals(map.get("checked"))) {
                map.put("checked", "false");
            } else {
                map.put("checked", "true");
            }
        }
        if (last > first) {
            while (first <= last) {
                CheckBox cb =  mCheckBoxList.get(first);
                if (cb != null) {
                    cb.setChecked(!cb.isChecked());
                }
                first++;
            }
        }
    }

    public String[] getAllCheckedNumber() {
        ArrayList<String> result = new ArrayList<>();
        for (Map<String, String> map : mData) {
            if ("true".equals(map.get("checked"))) {
                result.add(map.get("number"));
            }
        }
        if (result.size() == 0) {
            return null;
        }
        return result.toArray(new String[0]);
    }
}
