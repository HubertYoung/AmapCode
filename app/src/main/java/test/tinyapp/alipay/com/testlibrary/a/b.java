package test.tinyapp.alipay.com.testlibrary.a;

import android.os.Looper;

/* compiled from: OsUtil */
public final class b {
    public static boolean a() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
