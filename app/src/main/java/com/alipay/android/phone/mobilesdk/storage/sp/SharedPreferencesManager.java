package com.alipay.android.phone.mobilesdk.storage.sp;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class SharedPreferencesManager {
    private static LruCache<String, APSharedPreferences> spList = new LruCache<>(30);

    public static APSharedPreferences getInstance(Context context, String groupId) {
        return getInstance(context, groupId, 0);
    }

    public static APSharedPreferences getInstance(Context context, String groupId, int mode) {
        if (context == null || TextUtils.isEmpty(groupId)) {
            return null;
        }
        APSharedPreferences sp = (APSharedPreferences) spList.get(groupId);
        if (sp != null) {
            return sp;
        }
        synchronized (SharedPreferencesManager.class) {
            try {
                APSharedPreferences sp2 = (APSharedPreferences) spList.get(groupId);
                if (sp2 == null) {
                    APSharedPreferences sp3 = new APSharedPreferences(context, groupId, mode);
                    try {
                        spList.put(groupId, sp3);
                        sp2 = sp3;
                    } catch (Throwable th) {
                        th = th;
                        APSharedPreferences aPSharedPreferences = sp3;
                        throw th;
                    }
                }
                return sp2;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }
}
