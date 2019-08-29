package com.alipay.mobile.nebulacore.ui;

import android.os.Build.VERSION;
import android.os.Bundle;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5StatusBarUtils;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5TransActivity extends H5Activity {
    public static final String TAG = "H5TransActivity";

    public static class H5TransActivity0 extends H5TransActivity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5TransActivity.super.onCreate(savedInstanceState);
            setRequestedOrientation(1);
            H5Log.d(H5TransActivity.TAG, "onCreate H5TransActivity0");
        }
    }

    public static class H5TransActivity1 extends H5TransActivity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5TransActivity.super.onCreate(savedInstanceState);
            setRequestedOrientation(1);
            H5Log.d(H5TransActivity.TAG, "onCreate H5TransActivity1");
        }
    }

    public static class H5TransActivity2 extends H5TransActivity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5TransActivity.super.onCreate(savedInstanceState);
            setRequestedOrientation(1);
            H5Log.d(H5TransActivity.TAG, "onCreate H5TransActivity2");
        }
    }

    public static class H5TransActivity3 extends H5TransActivity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5TransActivity.super.onCreate(savedInstanceState);
            setRequestedOrientation(1);
            H5Log.d(H5TransActivity.TAG, "onCreate H5TransActivity3");
        }
    }

    public static class H5TransActivity4 extends H5TransActivity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5TransActivity.super.onCreate(savedInstanceState);
            setRequestedOrientation(1);
            H5Log.d(H5TransActivity.TAG, "onCreate H5TransActivity4");
        }
    }

    public static class H5TransActivity5 extends H5TransActivity {
        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            H5TransActivity.super.onCreate(savedInstanceState);
            setRequestedOrientation(1);
            H5Log.d(H5TransActivity.TAG, "onCreate H5TransActivity5");
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (VERSION.SDK_INT != 26) {
            setRequestedOrientation(1);
        }
        super.onCreate(savedInstanceState);
        H5Log.d(TAG, "onCreate");
        b();
    }

    private void b() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                if (H5Utils.getBoolean(bundle, (String) H5Param.LONG_TRANS_ANIMATE, false)) {
                    H5StatusBarUtils.setTransparentColor(this, 855638016);
                }
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }
}
