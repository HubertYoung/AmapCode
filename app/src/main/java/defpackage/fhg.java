package defpackage;

import android.app.Activity;
import operation.pay.AliSignTools;

/* renamed from: fhg reason: default package */
/* compiled from: PayManager */
public class fhg {
    private static fhg b;
    public AliSignTools a = new AliSignTools();

    private fhg() {
    }

    public static fhg a() {
        if (b == null) {
            synchronized (fhg.class) {
                try {
                    if (b == null) {
                        b = new fhg();
                    }
                }
            }
        }
        return b;
    }

    public final void a(ans ans, Activity activity) {
        this.a.a(activity);
        this.a.a = ans;
        this.a.a();
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.a(AliSignTools.a(str, (String) null));
        }
    }

    public final void a(String str, String str2, String str3) {
        AliSignTools.a((String) "signZhiMaCallback. role: %s, auth_no: %s, state: %s", str, str2, str3);
        this.a.a(str, str2, str3);
    }
}
