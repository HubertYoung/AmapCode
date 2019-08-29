package com.alibaba.baichuan.android.trade.callback;

import android.app.Activity;
import com.alibaba.baichuan.android.trade.b.a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlibcCallbackContext {
    private static final String a = "AlibcCallbackContext";
    public static volatile WeakReference activity;
    public static List pendingInitCallbacks = Collections.synchronizedList(new ArrayList());
    public static a showProcessContext;

    public static void setActivity(Activity activity2) {
        activity = new WeakReference(activity2);
    }
}
