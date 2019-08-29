package com.amap.location.e.c;

import android.content.Context;
import android.os.Handler;
import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.amap.location.common.model.WiFi;

/* compiled from: WifiAgeEstimator */
public final class c extends a<WiFi> {
    public c(Context context, String str, Handler handler) {
        super(context, str, handler);
    }

    /* renamed from: a */
    public final String b(WiFi wiFi) {
        return wiFi == null ? "" : wiFi.getKey();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final int c(WiFi wiFi) {
        return wiFi == null ? APCallCode.CALL_ERROR_INNER : wiFi.rssi;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final long d(WiFi wiFi) {
        if (wiFi == null) {
            return 0;
        }
        return wiFi.lastUpdateUtcMills;
    }

    /* access modifiers changed from: 0000 */
    public final void a(WiFi wiFi, long j) {
        if (wiFi != null) {
            wiFi.lastUpdateUtcMills = j;
        }
    }
}
