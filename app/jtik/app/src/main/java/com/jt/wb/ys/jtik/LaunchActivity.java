package com.jt.wb.ys.jtik;

import com.jt.wb.ys.jtik.activities.MainActivity;
import com.jt.wb.ys.jtik.fragments.assist.PermissionsFragment;

public class LaunchActivity extends com.jt.wb.ys.common.app.Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PermissionsFragment.haveAll(this, getSupportFragmentManager())) {
            MainActivity.show(this);
            finish();
        }
    }
}
