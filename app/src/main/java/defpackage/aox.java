package defpackage;

import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: aox reason: default package */
/* compiled from: LoginCallback */
public class aox implements dko<chm> {
    public static int d = 0;
    public static int e = 1;
    public static int f = 2;
    public static int g = 3;
    private int a;

    public aox(int i) {
        this.a = i;
    }

    public void a(chm chm) {
        if (this.a == d || this.a == e) {
            apd.b();
        }
        if (chm != null) {
            if (chm.code == 1) {
                aoe.a().a(chm, this.a);
                ant b = aoe.a().b();
                aos.a();
                if (b != null) {
                    if (this.a == d) {
                        bim.aa().c(true);
                        bim.aa().i(b.a);
                        bim.aa().d(true);
                        wi.a("login", null, null);
                    }
                    if (!TextUtils.isEmpty(b.h)) {
                        aov a2 = a.a;
                        for (anq onComplete : a2.c()) {
                            onComplete.onComplete(true);
                        }
                        a2.c().clear();
                    }
                }
                if (this.a == d) {
                    a.a.a(true);
                    a.a.a(false, true);
                } else if (this.a == e) {
                    a.a.b(true);
                }
                a.a.a(b);
            } else if (chm.code == 14 || chm.code == 24 || chm.code == 32) {
                aoy.a();
            } else {
                if (chm.code == 10048 && this.a == d) {
                    a.a.a(false);
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.register_too_much));
                }
            }
        }
    }

    public void a(Exception exc) {
        if (this.a == d || this.a == e) {
            apd.b();
        }
    }
}
