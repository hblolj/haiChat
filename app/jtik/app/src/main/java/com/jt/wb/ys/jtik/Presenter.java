package com.jt.wb.ys.jtik;

import android.text.TextUtils;

public class Presenter implements IPresenter{

    private IView mView;

    public Presenter(IView mView) {
        this.mView = mView;
    }

    @Override
    public void search() {

        // 开启界面 Loading.....

        String inputString = mView.getInputString();
        if (TextUtils.isEmpty(inputString)){
            // 为空直接返回
            return;
        }

        int hashCode = inputString.hashCode();

        IUserService service = new UserService();
        String result = service.search(hashCode);
        result = "Result: " + inputString + "-" + result;

        // 关闭界面 Loading......

        mView.setResultString(result);
    }
}
