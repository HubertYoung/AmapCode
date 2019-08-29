package com.alipay.mobile.framework.app.ui;

import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;

public interface ActivityResponsable {
    void alert(String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2);

    void alert(String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2, Boolean bool);

    void dismissProgressDialog();

    void requestPermissions(String[] strArr, int i, RequestPermissionsResultCallback requestPermissionsResultCallback);

    void showProgressDialog(String str);

    void showProgressDialog(String str, boolean z, OnCancelListener onCancelListener);

    void toast(String str, int i);
}
