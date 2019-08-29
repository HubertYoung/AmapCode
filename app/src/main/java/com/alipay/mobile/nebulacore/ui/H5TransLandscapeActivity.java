package com.alipay.mobile.nebulacore.ui;

import android.os.Bundle;
import com.alipay.mobile.nebula.util.H5Log;

public class H5TransLandscapeActivity extends H5TransActivity {
    public static final String TAG = "H5TransLandscapeActivity";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(0);
        H5Log.d(TAG, "onCreate, landscape activity = " + this);
    }
}
