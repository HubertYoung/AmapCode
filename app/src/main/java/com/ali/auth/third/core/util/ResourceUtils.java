package com.ali.auth.third.core.util;

import android.content.Context;
import android.text.TextUtils;
import com.ali.auth.third.core.context.KernelContext;
import com.alipay.mobile.common.share.widget.ResUtils;

public class ResourceUtils {
    public static final String TAG = "ResourceUtils";

    public static int getIdentifier(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str2, str, !TextUtils.isEmpty(KernelContext.packageName) ? KernelContext.packageName : context.getPackageName());
    }

    public static int getIdentifier(String str, String str2) {
        return getIdentifier(KernelContext.getApplicationContext(), str, str2);
    }

    public static int getRDrawable(Context context, String str) {
        return getIdentifier(context, ResUtils.DRAWABLE, str);
    }

    public static int getRId(Context context, String str) {
        return getIdentifier(context, "id", str);
    }

    public static int getRLayout(Context context, String str) {
        return getIdentifier(context, ResUtils.LAYOUT, str);
    }

    public static int getRLayout(String str) {
        return getIdentifier(KernelContext.getApplicationContext(), ResUtils.LAYOUT, str);
    }

    public static String getString(Context context, String str) {
        return context.getResources().getString(getIdentifier(context, ResUtils.STRING, str));
    }

    public static String getString(String str) {
        return getString(KernelContext.getApplicationContext(), str);
    }
}
