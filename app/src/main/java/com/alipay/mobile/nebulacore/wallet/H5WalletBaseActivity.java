package com.alipay.mobile.nebulacore.wallet;

import android.os.Bundle;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.nebula.util.H5Log;

public class H5WalletBaseActivity extends BaseActivity {
    public static final String TAG = "H5WalletBaseActivity";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        H5Log.d(TAG, "onCreate");
    }
}
