package com.moez.QKSMS.ui.garbage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.moez.QKSMS.R;
import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.common.utils.ColorUtils;
import com.moez.QKSMS.common.utils.GarbageUtils;
import com.moez.QKSMS.data.Filter;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKFragment;
import com.moez.QKSMS.ui.base.RecyclerCursorAdapter;
import com.moez.QKSMS.ui.dialog.QKDialog;
import com.moez.QKSMS.ui.importer.ContactImporterActivity;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangqian on 2016/10/30.
 * list to edit the filter
 */
public class FilterListFragment extends QKFragment implements View.OnClickListener, RecyclerCursorAdapter.ItemClickListener<Filter>, AdapterView.OnItemClickListener {
    public static final String TAG = "FilterListFragment";

    private FilterListAdapter mAdapter;
    private int mFilterType = FilterDbHelper.TYPE_WHITE_LIST;
    private FilterDbHelper mFilterDbHelper;

    private LinearLayoutManager mLayoutManager;

    // this editText is used for input filter in dialog
    private EditText mEditText;

    private static final int MENU_ADD_PLAIN = 1;
    private static final int MENU_ADD_SIMPLE = 2;
    private static final int MENU_ADD_REGEX = 3;
    private static final int MENU_FROM_CONTACT = 4;
    private static final int MENU_FROM_FILE = 5;

    private long mAddType;

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
            QKDialog dialog = new QKDialog();
            dialog.setContext(mContext);
            dialog.setTitle("选择添加方式");
            dialog.addMenuItem("添加普通文本", MENU_ADD_PLAIN);
            dialog.addMenuItem("添加简单匹配", MENU_ADD_SIMPLE);
            dialog.addMenuItem("添加正则匹配", MENU_ADD_REGEX);
            if (mFilterType == FilterDbHelper.TYPE_WHITE_LIST || mFilterType == FilterDbHelper.TYPE_BLACK_LIST) {
                dialog.addMenuItem("通讯录导入", MENU_FROM_CONTACT);
            }
            dialog.buildMenu(this);
            dialog.show();
        } else {
            int contentType;
            String text = mEditText.getText().toString();
            switch ((int) mAddType) {
                case MENU_ADD_PLAIN:
                    contentType = GarbageUtils.CONTENT_PLAIN;
                    break;
                case MENU_ADD_SIMPLE:
                    contentType = GarbageUtils.CONTENT_SIMPLE;
                    break;
                case MENU_ADD_REGEX:
                    contentType = GarbageUtils.CONTENT_REGEX;
                    try {
                        Pattern.compile(text);
                    } catch (Exception e) {
                        Toast.makeText(mContext, "正则表达式有误", Toast.LENGTH_LONG).show();
                        launchInput(text);
                        return;
                    }
                    break;
                default:
                    contentType = GarbageUtils.CONTENT_PLAIN;
                    break;
            }
            mFilterDbHelper.addFilter(mEditText.getText().toString(), mFilterType, contentType);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch((int) l) {
            case MENU_ADD_PLAIN:
            case MENU_ADD_SIMPLE:
            case MENU_ADD_REGEX:
                mAddType = l;
                launchInput(null);
            case MENU_FROM_CONTACT:
                mContext.startActivityForResult(ContactImporterActivity.class, MENU_FROM_CONTACT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "result received!");
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MENU_FROM_CONTACT:
                    String[] numbers = data.getStringArrayExtra("numbers");
                    for (String number : numbers) {
                        mFilterDbHelper.addFilter(number.replace(" ", ""), mFilterType);
                    }
                    dataChanged();
            }
        }
    }

    private void launchInput(String defaultValue) {
        mEditText = new EditText(mContext);
        mEditText.setText(defaultValue);
        new QKDialog()
                .setContext(mContext)
                .setTitle("添加")
                .setCustomView(mEditText)
                .setPositiveButton("确定", this)
                .setNegativeButton("取消", null)
                .show();
    }

    private void dataChanged() {
        mAdapter.changeCursor(mFilterDbHelper.getCursorByFilterType(mFilterType));
    }
}
