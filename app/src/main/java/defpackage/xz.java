package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: xz reason: default package */
/* compiled from: SpEncryptExecutor */
public final class xz {
    public static void a() {
        boolean z;
        String[] a = yc.a();
        if (a != null && a.length != 0) {
            if (bno.a) {
                AMapLog.d(xz.class.getSimpleName(), "Do encrypt");
            }
            for (String mapSharePreference : a) {
                ya yaVar = new MapSharePreference(mapSharePreference).a;
                synchronized (yaVar.c) {
                    yd a2 = yd.a((Context) AMapAppGlobal.getApplication());
                    String str = yaVar.a;
                    if (TextUtils.isEmpty(str)) {
                        z = false;
                    } else {
                        z = a2.a.getBoolean(str, false);
                    }
                    if (z) {
                        yaVar.b = 2;
                    }
                    if (yaVar.b == 0) {
                        yaVar.b = 1;
                        ahn.b().execute(new Runnable() {
                            public final void run() {
                                synchronized (ya.this.c) {
                                    Map<String, ?> all = ya.this.d.getAll();
                                    if (all != null) {
                                        if (!all.isEmpty()) {
                                            Set<Entry<String, ?>> entrySet = all.entrySet();
                                            HashSet<Entry> hashSet = new HashSet<>();
                                            for (Entry next : entrySet) {
                                                if (next.getValue() instanceof String) {
                                                    String str = (String) next.getKey();
                                                    if (!(!TextUtils.isEmpty(str) && str.startsWith("qplxzg") && str.endsWith("gffdge"))) {
                                                        hashSet.add(next);
                                                    }
                                                }
                                            }
                                            if (hashSet.isEmpty()) {
                                                yd.a((Context) AMapAppGlobal.getApplication()).a(ya.this.a);
                                                ya.this.b = 2;
                                                ya.this.c.notifyAll();
                                                return;
                                            }
                                            for (Entry entry : hashSet) {
                                                ya.this.a().putString(yb.a((String) entry.getKey()), yb.c(entry.getValue().toString()));
                                                ya.this.a().remove((String) entry.getKey());
                                            }
                                            if (!ya.this.a().commit()) {
                                                ya.this.b = 3;
                                                ya.this.c.notifyAll();
                                                return;
                                            }
                                            yd.a((Context) AMapAppGlobal.getApplication()).a(ya.this.a);
                                            ya.this.b = 2;
                                            ya.this.c.notifyAll();
                                            return;
                                        }
                                    }
                                    yd.a((Context) AMapAppGlobal.getApplication()).a(ya.this.a);
                                    ya.this.b = 2;
                                    ya.this.c.notifyAll();
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
