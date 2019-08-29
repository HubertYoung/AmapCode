package defpackage;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: mf reason: default package */
/* compiled from: CloudResourceManager */
public class mf {
    static final /* synthetic */ boolean c = true;
    private static volatile mf d;
    String a;
    me b;
    private boolean e;

    private mf() {
        if (c || AMapPageUtil.getAppContext() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(AMapPageUtil.getAppContext().getFilesDir().getAbsolutePath());
            sb.append(File.separator);
            sb.append("cloudres");
            this.a = sb.toString();
            if (!new File(this.a).exists() || !new File(this.a).isDirectory()) {
                this.e = new File(this.a).mkdir();
            } else {
                this.e = true;
            }
        } else {
            throw new AssertionError();
        }
    }

    public static mf a() {
        if (d == null) {
            synchronized (mf.class) {
                try {
                    if (d == null) {
                        d = new mf();
                    }
                }
            }
        }
        return d;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str, mc mcVar) {
        if (this.b == null) {
            if (mcVar != null) {
                mcVar.a();
            }
            return;
        }
        new mh(this.b.a(str), mcVar).a();
    }

    public final void a(String str, boolean z) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("load:configData ");
            sb.append(str);
            sb.append(" configData,");
            sb.append(z);
        }
        if (str != null) {
            this.b = new me(str);
            a(z);
        }
    }

    public final void a(boolean z) {
        if (this.e && this.b != null) {
            ArrayList<a> arrayList = this.b.a;
            if (arrayList != null) {
                Iterator<a> it = arrayList.iterator();
                while (it.hasNext()) {
                    a next = it.next();
                    StringBuilder sb = new StringBuilder("handle item:");
                    sb.append(next.a);
                    sb.append(" isColdBoot: ");
                    sb.append(z);
                    boolean z2 = !next.b() || next.h < next.e;
                    if (z2 && z && next.d == 1) {
                        z2 = false;
                    }
                    if (z2) {
                        new mh(next, null).a();
                        if (bno.a) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(next.a);
                            sb2.append(" download immediately.");
                        }
                    } else if (bno.a) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(next.a);
                        sb3.append(" no need to download.");
                    }
                }
            }
        }
    }
}
