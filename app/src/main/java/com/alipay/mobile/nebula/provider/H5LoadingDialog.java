package com.alipay.mobile.nebula.provider;

import android.app.Activity;

public interface H5LoadingDialog {
    void hide();

    void showLoading(Activity activity, String str);
}
