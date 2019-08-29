package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.utils;

import android.content.SharedPreferences;
import android.os.Build.VERSION;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;

public class AshmemLocalMonitor {
    private static AshmemLocalMonitor a;
    private static final Logger c = Logger.getLogger((String) "AshmemMonitor");
    private SharedPreferences b;
    private boolean d = isSdkMatch();

    private AshmemLocalMonitor() {
        a();
    }

    public static AshmemLocalMonitor get() {
        if (a == null) {
            a = new AshmemLocalMonitor();
        }
        return a;
    }

    private SharedPreferences a() {
        boolean z = false;
        if (this.b == null) {
            this.b = AppUtils.getApplicationContext().getSharedPreferences("pref_ashmem_local_switcher", 0);
            if (this.b.getInt("key_os", -1) != VERSION.SDK_INT) {
                resetAll();
            }
            if (isSdkMatch() && this.b.getInt("key_invalid_count", 0) < 10) {
                z = true;
            }
            this.d = z;
        }
        return this.b;
    }

    public void increaseInvalidCount() {
        if (a() != null) {
            int preCount = a().getInt("key_invalid_count", 0);
            a().edit().putInt("key_invalid_count", preCount + 1).apply();
            a(preCount + 1);
            c.d("increaseInvalidCount preCount: " + preCount, new Object[0]);
        }
    }

    public void resetAll() {
        if (a() != null) {
            a().edit().remove("key_invalid_count").remove("key_os").putInt("key_os", VERSION.SDK_INT).apply();
        }
        c.d("resetAll", new Object[0]);
    }

    private void a(int invalidCount) {
        synchronized (this) {
            this.d = invalidCount < 10;
            if (!this.d) {
                b();
                UCLogUtil.UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_ASHMEM, "-" + invalidCount);
            }
        }
    }

    private static void b() {
        isSdkMatch();
        c.d("updateUseAshmem useAshmem: false", new Object[0]);
        FalconFacade.get().setUseAshmem(false);
        CacheContext.get().resetUseAshmem(false);
    }

    public boolean isUseAshmem() {
        return isSdkMatch() && this.d;
    }

    public static boolean isSdkMatch() {
        return VERSION.SDK_INT >= 14 && VERSION.SDK_INT <= 19;
    }
}
