package defpackage;

import android.os.Message;
import android.support.annotation.NonNull;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxd reason: default package */
/* compiled from: ScanEngine */
public class cxd {
    private static cxd a;

    private cxd() {
    }

    public static cxd a() throws NullPointerException {
        if (a == null) {
            synchronized (cxd.class) {
                try {
                    if (a == null) {
                        a = new cxd();
                    }
                }
            }
        }
        return a;
    }

    public final synchronized int a(int i, @NonNull JSONObject jSONObject, JSONObject jSONObject2) {
        int i2;
        int i3 = 0;
        try {
            String string = jSONObject.getString(SuperId.BIT_1_NEARBY_SEARCH);
            String string2 = jSONObject.getString(LogItem.MM_C15_K4_TIME);
            if (11 != i || ((!"SearchErrorPage$".equals(string) || !"SearchErrorPage".equals(string2)) && (!"SearchNativeNoResultPage$".equals(string) || !"SearchNativeNoResultPage".equals(string2)))) {
                i2 = 0;
            } else {
                i2 = 4352;
                try {
                    cxg f = cxg.f();
                    if (jSONObject != null) {
                        f.b = jSONObject;
                        f.c = jSONObject2;
                        Message obtain = Message.obtain();
                        obtain.what = 0;
                        f.a.sendMessage(obtain);
                    }
                } catch (JSONException e) {
                    e = e;
                    i3 = 4352;
                    e.printStackTrace();
                    i2 = i3;
                    return i2;
                }
            }
        } catch (JSONException e2) {
            e = e2;
            e.printStackTrace();
            i2 = i3;
            return i2;
        }
        return i2;
    }
}
