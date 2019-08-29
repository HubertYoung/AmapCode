package com.alipay.mobile.nebula.search;

public interface H5InputListen {
    void back();

    void entranceClick();

    void onBlur();

    void onCancel();

    void onChange(String str);

    void onFocus();

    void onSubmit(String str);

    void onVoiceBtnClick();
}
