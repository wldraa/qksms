package com.moez.QKSMS.ui.garbage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.moez.QKSMS.R;
import com.moez.QKSMS.ui.base.QKActivity;

import butterknife.Bind;


/**
 * Created by zhangqian on 2016/10/31.
 * show blocked messages.
 */
public class GarbageActivity extends QKActivity {
    public static final String TAG = "GarbageActivity";

    private GarbageFragment mGarbageFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_garbage);
        FragmentManager fm = getFragmentManager();

        mGarbageFragment = (GarbageFragment) fm.findFragmentByTag(GarbageActivity.TAG);
        if (null == mGarbageFragment) {
            mGarbageFragment = new GarbageFragment();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.body_fragment, mGarbageFragment, GarbageActivity.TAG);
        ft.commit();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.garbage, menu);
        setTitle(R.string.pref_garbage);
        showBackButton(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_clear:
                mGarbageFragment.clearGarbage();
                Toast.makeText(this, "清空所有", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(menu);
    }

}
