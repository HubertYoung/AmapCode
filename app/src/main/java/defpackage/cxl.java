package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.evaluate.draugorp.Drawing;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxl reason: default package */
/* compiled from: LogManagerHelper */
public final class cxl {
    private static int a;

    public static final void a(@NonNull String str, @NonNull JSONObject jSONObject) {
        try {
            cxk.a();
            jSONObject.put("ADIU", cxk.e());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("CONTENT", jSONObject.toString());
        kd.b(str, hashMap);
    }

    public static final void a(@NonNull String str, @NonNull String str2, @NonNull JSONObject jSONObject) {
        try {
            LogManager.actionLogV2(str, str2, jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(int i, JSONObject jSONObject) {
        a(i, jSONObject, (JSONObject) null);
    }

    public static void a(int i, JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            a(i, jSONObject.toString(), jSONObject2);
            if (cxk.a().c) {
                long currentTimeMillis = System.currentTimeMillis();
                if (jSONObject2 == null) {
                    jSONObject2 = new JSONObject();
                }
                jSONObject2.put("stamp", currentTimeMillis);
                if (4352 == cxd.a().a(i, jSONObject, jSONObject2)) {
                    b(4, jSONObject, jSONObject2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static void b(int i, @NonNull JSONObject jSONObject, @Nullable JSONObject jSONObject2) {
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        try {
            jSONObject2.put("feed", i);
            a(1, jSONObject.toString(), jSONObject2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void a(int i, String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        int i2 = a;
        a = i2 + 1;
        jSONObject2.put(DictionaryKeys.CTRLXY_Y, i2);
        cxk.a();
        jSONObject2.put("l", cxk.g());
        jSONObject2.put("s", Long.toString(System.currentTimeMillis(), 36));
        jSONObject2.put("e", i);
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        jSONObject2.put(DictionaryKeys.CTRLXY_X, jSONObject.toString());
        cxk.a();
        jSONObject2.put("z", cxk.d());
        cxk.a();
        jSONObject2.put("v", cxk.f());
        if (bno.a) {
            jSONObject2.put("d", str);
        } else {
            jSONObject2.put("d", b.a(a.a(str)));
        }
        if (cxk.a().b()) {
            jSONObject2.put("qa_switch", true);
            cxm.a(jSONObject2, str, i);
            return;
        }
        Drawing.drawLine1(jSONObject2);
    }
}
