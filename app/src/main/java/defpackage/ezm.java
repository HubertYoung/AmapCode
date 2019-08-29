package defpackage;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ezm reason: default package */
/* compiled from: ICacheSettings */
public abstract class ezm<T> {
    public static final byte[] a = {34, 32, 33, 37, 33, 34, 32, 33, 33, 33, 34, 41, 35, 32, 32, 32};
    public static final byte[] b = {33, 34, 35, 36, 37, 38, 39, 40, 41, 32, 38, 37, 36, 35, 34, 33};
    protected static final Object c = new Object();
    protected List<T> d = new ArrayList();
    protected Context e;

    /* access modifiers changed from: protected */
    public abstract String a();

    /* access modifiers changed from: protected */
    public abstract List<T> a(String str);

    /* access modifiers changed from: 0000 */
    public abstract String b(String str) throws Exception;

    protected ezm(Context context) {
        this.e = context.getApplicationContext();
        c();
    }

    public final void c() {
        synchronized (c) {
            fal.a(a());
            this.d.clear();
            String a2 = fbc.b(this.e).a(a(), null);
            if (TextUtils.isEmpty(a2)) {
                StringBuilder sb = new StringBuilder("ClientManager init ");
                sb.append(a());
                sb.append(" strApps empty.");
                fat.d("CacheSettings", sb.toString());
            } else if (a2.length() > 10000) {
                StringBuilder sb2 = new StringBuilder("sync ");
                sb2.append(a());
                sb2.append(" strApps lenght too large");
                fat.d("CacheSettings", sb2.toString());
                d();
            } else {
                try {
                    StringBuilder sb3 = new StringBuilder("ClientManager init ");
                    sb3.append(a());
                    sb3.append(" strApps : ");
                    sb3.append(a2);
                    fat.d("CacheSettings", sb3.toString());
                    List a3 = a(b(a2));
                    if (a3 != null) {
                        this.d.addAll(a3);
                    }
                } catch (Exception e2) {
                    d();
                    fat.d("CacheSettings", fat.a((Throwable) e2));
                }
            }
        }
    }

    public final void d() {
        synchronized (c) {
            this.d.clear();
            fbc.b(this.e).b(a(), "");
            StringBuilder sb = new StringBuilder("clear ");
            sb.append(a());
            sb.append(" strApps");
            fat.d("CacheSettings", sb.toString());
        }
    }
}
