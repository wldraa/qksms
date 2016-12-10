package com.moez.QKSMS.ui.importer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.LiveViewManager;
import com.moez.QKSMS.enums.QKPreference;
import com.moez.QKSMS.ui.ThemeManager;
import com.moez.QKSMS.ui.base.QKActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangqian on 2016/12/10.
 *
 */
public class ContactImporterActivity extends QKActivity implements AdapterView.OnItemClickListener {
    public static final String TAG = "ContactImporterActivity";

    private ContactImporterAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_importer);

        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1,
                ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, "sort_key COLLATE LOCALIZED asc");

        ArrayList<Map<String, String>> list = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                Map<String,String> map = new HashMap<>();
                map.put("name", cursor.getString(1));
                map.put("number", cursor.getString(2));
                list.add(map);
            }
        }

        mAdapter = new ContactImporterAdapter(this, list, R.layout.list_item_contact_importer, new String[]{"name", "number"}, new int[]{R.id.contact_list_name, R.id.contact_list_number});

        ListView listView = (ListView) findViewById(R.id.contact_importer_listview);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(this);

        listView.setAdapter(mAdapter);

        setTitle("从通讯录中导入");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckBox cb = mAdapter.getItemView(i);
        cb.setChecked(!cb.isChecked());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.importer, menu);
        setTitle(R.string.pref_garbage);
        showBackButton(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.menu_select_all:
                for (int i = 0 ; i < mAdapter.getCount() ; i++) {
                    CheckBox cb = mAdapter.getItemView(i);
                    cb.setChecked(true);
                }
                return true;
            case R.id.menu_reverse_select:
                for (int i = 0 ; i < mAdapter.getCount() ; i++) {
                    CheckBox cb = mAdapter.getItemView(i);
                    cb.setChecked(!cb.isChecked());
                }
                return true;
            case R.id.menu_done:
                Intent result = getIntent();
                result.putExtra("numbers", mAdapter.getAllCheckedNumber());
                setResult(RESULT_OK, result);
                Log.d(TAG, "set result" + result.toString());
                finish();
                return true;
        }
        return super.onOptionsItemSelected(menu);
    }

}
