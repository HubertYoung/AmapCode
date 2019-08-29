package com.alipay.android.phone.inside.storage;

import android.content.Context;

public class StorageInit {
    private static Context a;

    public static void a(Context context) {
        a = context.getApplicationContext();
    }

    public static Context a() {
        return a;
    }
}
