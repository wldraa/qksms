package com.moez.QKSMS.ui.garbage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.ui.base.QKActivity;

/**
 * Created by zhangqian on 2016/10/29.
 * 设置黑白名单，关键词
 */
public class FilterSettingActivity extends QKActivity {
    final public static String TAG = "FilterSettingActivity";

    private FilterListFragment mFilterListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int filterType = intent.getIntExtra("filterType", FilterDbHelper.TYPE_BLACK_LIST);
        String title = intent.getStringExtra("title");

        setContentView(R.layout.activity_filter_setting);
        FragmentManager fm = getFragmentManager();

        mFilterListFragment = (FilterListFragment) fm.findFragmentByTag(FilterSettingActivity.TAG);
        if (null == mFilterListFragment) {
            mFilterListFragment = new FilterListFragment();
        }
        mFilterListFragment.setFilterType(filterType);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body_fragment, mFilterListFragment, FilterSettingActivity.TAG);
        ft.commit();

        setTitle(title);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFilterListFragment.onActivityResult(requestCode, resultCode, data);
    }


}
