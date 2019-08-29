package defpackage;

import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.drive.auto.AutoConnectionTypeEnum;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dej reason: default package */
/* compiled from: AutoConnectionManagerModel */
public final class dej extends su<deo> {
    private static String i = "Xcar_";
    private static String j = "IOV";
    public boolean c = false;
    public boolean d = false;
    public AutoConnectionTypeEnum e = AutoConnectionTypeEnum.NONE;
    public fbl f;
    public fbn g = new fbn() {
        public final void a() {
            dej.this.f.a(dej.this.g);
        }

        public final void b() {
            agb.c(false);
        }

        public final void c() {
            dej.this.f.a(dej.this.h);
            dej.this.f.f();
        }
    };
    public fbm h = new fbm() {
        public final void a(String str, int i) {
            if (dej.a(str)) {
                if (i == 1) {
                    agb.c(true);
                    dej.this.c = true;
                    ((deo) dej.this.a).a(AutoConnectionTypeEnum.ALI_AUTO);
                    dej.this.b();
                } else {
                    agb.c(false);
                    dej.this.c = false;
                    ((deo) dej.this.a).a(AutoConnectionTypeEnum.ALI_AUTO);
                }
                agb.a((String) "ali_auto_wifi");
            }
        }
    };

    public dej(deo deo) {
        super(deo);
    }

    public final void b() {
        int i2 = 2;
        int i3 = c() ? 1 : 2;
        if (this.d) {
            i2 = 1;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", i2);
            jSONObject.put("type", i3);
            LogManager.actionLogV2("P00250", "B004", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final boolean c() {
        return this.e == AutoConnectionTypeEnum.AMAP_BLUETOOTH_10 || this.e == AutoConnectionTypeEnum.AMAP_BLUETOOTH_20;
    }

    static /* synthetic */ boolean a(String str) {
        return !TextUtils.isEmpty(str) && (str.startsWith(i) || str.startsWith(j));
    }
}
