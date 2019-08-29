package com.alipay.android.phone.inside.commonbiz.status;

public class BizRunningStatus {
    private static boolean a;

    public static synchronized boolean a() {
        boolean z;
        synchronized (BizRunningStatus.class) {
            try {
                z = a;
            }
        }
        return z;
    }

    public static synchronized void a(boolean z) {
        synchronized (BizRunningStatus.class) {
            a = z;
        }
    }
}
