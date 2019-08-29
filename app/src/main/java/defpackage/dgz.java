package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import org.xidea.el.JsonType;
import org.xidea.el.json.JSONDecoder;

/* renamed from: dgz reason: default package */
/* compiled from: OfflineDataInit */
public final class dgz {
    static final ReentrantLock e = new ReentrantLock();
    private static dgz f;
    private static CopyOnWriteArrayList<dgl> g = new CopyOnWriteArrayList<>();
    public volatile LinkedList<BannerItem> a = new LinkedList<>();
    public boolean b = false;
    public boolean c = false;
    public Object d = new Object();
    private int h;

    public static dgz a() {
        e.lock();
        try {
            if (f == null) {
                f = new dgz();
            }
            return f;
        } finally {
            e.unlock();
        }
    }

    public static CopyOnWriteArrayList<dgl> b() {
        return g;
    }

    public static String a(Context context, String str) {
        return context.getSharedPreferences("AppInit_OffVersion", 0).getString(str, "");
    }

    public static boolean c() {
        return (dhd.a(dhd.b()) || dhd.b(dhd.b())) && !dhc.a();
    }

    public final void a(final dgx dgx) {
        dgy.a(new dgx() {
            public final void a(boolean z) {
                StringBuilder sb = new StringBuilder("requestAllVoiceList callback ThreadID:");
                sb.append(Thread.currentThread().getId());
                sb.append(", success:");
                sb.append(z);
                dhb.a("OfflineDataInit", sb.toString());
                dgz.this.e();
                if (dgx != null) {
                    dgx.a(z);
                } else {
                    DriveOfflineSDK.e().m();
                }
            }
        });
    }

    public final synchronized boolean d() {
        try {
        }
        return this.c;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.d
            monitor-enter(r0)
            java.lang.String r1 = "OfflineDataInit"
            java.lang.String r2 = "setTTSDataReady"
            defpackage.dhb.a(r1, r2)     // Catch:{ Exception -> 0x0015 }
            r1 = 1
            r3.c = r1     // Catch:{ Exception -> 0x0015 }
            java.lang.Object r1 = r3.d     // Catch:{ Exception -> 0x0015 }
            r1.notifyAll()     // Catch:{ Exception -> 0x0015 }
            goto L_0x0015
        L_0x0013:
            r1 = move-exception
            goto L_0x0017
        L_0x0015:
            monitor-exit(r0)     // Catch:{ all -> 0x0013 }
            return
        L_0x0017:
            monitor-exit(r0)     // Catch:{ all -> 0x0013 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dgz.f():void");
    }

    private void a(List<BannerItem> list, int i) {
        if (list != null) {
            this.a.clear();
            this.a.addAll(list);
        }
        this.h = i;
    }

    public final void e() {
        dhb.a("OfflineDataInit", "initTTSList");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap = new HashMap();
        List<ua> c2 = dgm.a().c();
        if (c2 != null && c2.size() > 0) {
            for (ua next : c2) {
                if (next != null) {
                    hashMap.put(next.b, next);
                }
            }
        }
        List<tw> b2 = dgm.a().b();
        if (b2 != null && b2.size() > 0) {
            g.clear();
            for (tw next2 : b2) {
                if (next2 != null) {
                    String str = next2.e;
                    ua uaVar = (ua) hashMap.get(next2.f);
                    if (uaVar != null) {
                        arrayList2.add(uaVar);
                        if (!TextUtils.isEmpty(str)) {
                            if (!TextUtils.isEmpty(uaVar.n)) {
                                if (Double.valueOf(uaVar.n).compareTo(Double.valueOf(str)) < 0) {
                                    uaVar.c = 64;
                                }
                            }
                            uaVar.n = str;
                            arrayList.add(uaVar);
                        }
                    }
                    g.add(dgl.a(next2, uaVar));
                }
            }
        }
        if (c2 != null && arrayList2.size() < c2.size()) {
            c2.removeAll(arrayList2);
            dgm.a().b(c2);
        }
        if (arrayList.size() > 0) {
            dgm.a().a((List<ua>) arrayList);
        }
        f();
    }

    static /* synthetic */ void b(dgz dgz) {
        dhb.a("OfflineDataInit", "initTTSList");
        try {
            String a2 = dfo.a();
            if (!TextUtils.isEmpty(a2)) {
                ArrayList arrayList = (ArrayList) JSONDecoder.decode(a2, new JsonType(ArrayList.class, BannerItem.class));
                if (arrayList != null) {
                    dgz.a((List<BannerItem>) arrayList, dfo.b());
                }
            }
        } catch (Exception e2) {
            dgz.a((List<BannerItem>) null, 0);
            e2.printStackTrace();
        }
    }
}
