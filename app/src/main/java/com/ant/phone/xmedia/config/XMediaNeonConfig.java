package com.ant.phone.xmedia.config;

import android.os.Build;
import android.text.TextUtils;
import com.alipay.android.phone.falcon.ar.render.cloudconfig.DeviceConfig;
import com.alipay.android.phone.falcon.util.log.LogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.sdk.util.h;

public class XMediaNeonConfig {
    private int a = 1;
    private long b = 0;

    public final boolean a() {
        LogUtil.logInfo("XMediaNeonConfig", "current md" + Build.MODEL + ",mf" + Build.MANUFACTURER);
        if (this.a == 1) {
            return true;
        }
        return false;
    }

    private void a(int neonSwitch) {
        this.a = neonSwitch;
    }

    private void b() {
        this.b = System.currentTimeMillis();
    }

    private boolean c() {
        return Math.abs(System.currentTimeMillis() - this.b) > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL;
    }

    public static void a(XMediaNeonConfig neonConfig, DeviceConfig config) {
        if (neonConfig != null && config != null && neonConfig.c()) {
            neonConfig.b();
            if (!TextUtils.isEmpty(config.content) && config.content.contains(MergeUtil.SEPARATOR_KV)) {
                String[] items = config.content.split("\\|");
                try {
                    if (items.length > 1) {
                        neonConfig.a(Integer.parseInt(items[1]));
                    }
                } catch (Throwable th) {
                    LogUtil.logInfo("XMediaNeonConfig", "parseNeonDeviceConfig exp");
                }
            }
        }
    }

    public String toString() {
        return "XMediaNeonConfig{supportNeon=" + this.a + h.d;
    }
}
