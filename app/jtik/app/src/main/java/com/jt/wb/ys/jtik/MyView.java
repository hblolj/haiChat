package com.jt.wb.ys.jtik;

import android.widget.EditText;
import android.widget.TextView;

/**
 * 组合View，通过构造函数将 View 传递进来，从 View 中获取数据
 * 输出时，设置数据给 View
 */
public class MyView implements IView{

    private EditText editText;

    private TextView textView;

    public MyView(EditText editText, TextView textView) {
        this.editText = editText;
        this.textView = textView;
    }

    @Override
    public String getInputString() {
        return editText.getText().toString();
    }

    @Override
    public void setResultString(String s) {
        textView.setText(s);
    }
}
