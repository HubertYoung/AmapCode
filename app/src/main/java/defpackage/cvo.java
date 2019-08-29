package defpackage;

import android.app.Application;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import org.json.JSONObject;

/* renamed from: cvo reason: default package */
/* compiled from: LaunchPlugin */
public class cvo extends Plugin {
    /* access modifiers changed from: private */
    public cuu c;

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        super.a(application, cuu, jSONObject);
        this.c = cuu;
        a.b.postDelayed(new Runnable() {
            public final void run() {
                cvo.this.c.b().send(new cvn(System.currentTimeMillis(), String.valueOf(la.g())));
            }
        }, 20000);
    }
}
