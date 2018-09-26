package com.jt.wb.ys.jtik;

import android.os.Bundle;
import android.widget.TextView;

import com.jt.wb.ys.common.app.Activity;

import butterknife.BindView;

public class MainActivity extends Activity {

    @BindView(R.id.txt_test)
    TextView mTestText;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @Override
    protected Boolean initArgs(Bundle bundle) {
        return super.initArgs(bundle);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestText.setText("Ori");
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
