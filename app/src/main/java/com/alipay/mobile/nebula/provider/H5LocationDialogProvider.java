package com.alipay.mobile.nebula.provider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

public interface H5LocationDialogProvider {
    AlertDialog createLocationDialog(Activity activity, String str, OnClickListener onClickListener);
}
