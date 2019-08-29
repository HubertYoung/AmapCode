package com.amap.location.sdk.e;

import android.content.Context;
import android.os.Build.VERSION;

/* compiled from: PermissionUtil */
public class e {
    public static boolean a(Context context, String str) {
        return VERSION.SDK_INT < 23 || context.checkSelfPermission(str) == 0;
    }
}
