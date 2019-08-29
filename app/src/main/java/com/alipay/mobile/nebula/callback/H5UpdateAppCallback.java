package com.alipay.mobile.nebula.callback;

import com.alipay.mobile.nebula.util.H5Log;

public class H5UpdateAppCallback {
    private static final String TAG = "H5UpdateAppCallback";

    public void onResult(boolean success, boolean isLimit) {
        H5Log.d(TAG, "install result " + success);
    }
}
