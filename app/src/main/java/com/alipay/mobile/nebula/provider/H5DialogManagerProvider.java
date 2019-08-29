package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import android.app.Dialog;

public interface H5DialogManagerProvider {

    public interface OnClickNegativeListener {
        void onClick();
    }

    public interface OnClickPositiveListener {
        void onClick();
    }

    Dialog createDialog(Activity activity, String str, String str2, String str3, String str4, String str5);

    void disMissDialog();

    void release();

    void setNegativeListener(OnClickNegativeListener onClickNegativeListener);

    void setNegativeTextColor(String str);

    void setPositiveListener(OnClickPositiveListener onClickPositiveListener);

    void setPositiveTextColor(String str);

    void showDialog();
}
