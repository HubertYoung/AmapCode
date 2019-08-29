package defpackage;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ckl reason: default package */
/* compiled from: AndroidQPhoneChanged */
public final class ckl extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "AndroidQPhoneChanged";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        boolean z;
        if (VERSION.SDK_INT >= 29) {
            String b = kn.b((Context) application);
            String a = kn.a(true);
            String c = kn.c(application);
            String b2 = kn.b(true);
            if (TextUtils.isEmpty(b) || TextUtils.isEmpty(a) ? TextUtils.isEmpty(b2) || TextUtils.isEmpty(c) || c.equalsIgnoreCase(b2) : b.equalsIgnoreCase(a)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                kn.a((Context) application, (String) "", true);
                kn.a((Context) application, (String) "", false);
                String c2 = agm.c(application);
                String str = Build.MODEL;
                kn.c(application, c2, true);
                kn.c(application, c2, false);
                kn.b(application, str, true);
                kn.b(application, str, false);
                ik.a().b((Context) application);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.UPDATE_IMEI_CACHE, jSONObject);
            }
        }
    }
}
