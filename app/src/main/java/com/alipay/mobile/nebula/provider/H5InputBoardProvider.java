package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.alipay.mobile.nebula.callback.H5InputOperator;
import com.alipay.mobile.nebula.webview.APWebView;

public interface H5InputBoardProvider {

    public interface OnKeyboardEventListener {
        View getKeyboard();

        boolean isKeyboardShown();

        boolean onHide();

        boolean onRelease();

        boolean onShow(Activity activity, APWebView aPWebView);
    }

    View getKeyboard();

    void hideKeyboard();

    void init(Context context, APWebView aPWebView);

    boolean isKeyboardShown();

    void onRelease();

    void register(String str, OnKeyboardEventListener onKeyboardEventListener);

    void setIfUseRandomNumber(boolean z);

    void setKeyboardType(String str);

    void setOperateListener(H5InputOperator h5InputOperator);

    void showKeyboard();
}
