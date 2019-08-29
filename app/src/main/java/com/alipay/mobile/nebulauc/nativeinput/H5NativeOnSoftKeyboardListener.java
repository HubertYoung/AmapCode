package com.alipay.mobile.nebulauc.nativeinput;

public interface H5NativeOnSoftKeyboardListener {
    boolean displaySoftKeyboard(String str, int i);

    void onBackPressed();

    void onCustomKeyboardHide();

    void onKeyPreIme();

    void onPushWindow();
}
