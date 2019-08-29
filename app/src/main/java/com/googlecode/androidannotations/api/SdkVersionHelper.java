package com.googlecode.androidannotations.api;

import android.os.Build.VERSION;

public class SdkVersionHelper {

    class HelperInternal {
        private HelperInternal() {
        }

        /* access modifiers changed from: private */
        public static int a() {
            return VERSION.SDK_INT;
        }
    }

    public static int getSdkInt() {
        if (VERSION.RELEASE.startsWith("1.5")) {
            return 3;
        }
        return HelperInternal.a();
    }
}
