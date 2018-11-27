package com.jt.wb.ys.jtik.activities;

import android.content.Context;
import android.content.Intent;

import com.jt.wb.ys.common.app.Activity;
import com.jt.wb.ys.common.app.Fragment;
import com.jt.wb.ys.jtik.R;
import com.jt.wb.ys.jtik.fragments.account.UpdateUserInfoFragment;

public class AccountActivity extends Activity {

    private Fragment mCurrentFragment;

    /**
     * 账户 Activity 显示的入口
     * @param context
     */
    public static void show(Context context){
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurrentFragment = new UpdateUserInfoFragment();
                getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container, mCurrentFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurrentFragment.onActivityResult(requestCode, resultCode, data);
    }
}
