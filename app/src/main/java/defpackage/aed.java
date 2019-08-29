package defpackage;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.searchservice.callback.AbsSearchCallBack;
import com.amap.bundle.searchservice.service.offline.OfflineSearchManager$OfflineSearchInfo$1;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/* renamed from: aed reason: default package */
/* compiled from: OfflineSearchManager */
public final class aed extends Handler {
    private static volatile aed c;
    private static HandlerThread d;
    private static Queue<a> e;
    long a = 0;
    /* access modifiers changed from: private */
    public InfoliteResult b = null;
    private boolean f = false;
    private a g;

    /* renamed from: aed$a */
    /* compiled from: OfflineSearchManager */
    public static class a {
        public OfflineSearchMode a;
        public AbsSearchCallBack b;
        public AbsSearchCallBack c;
        public boolean d;
        int e;
        boolean f;

        public a(OfflineSearchMode offlineSearchMode, AbsSearchCallBack absSearchCallBack) {
            this(offlineSearchMode, absSearchCallBack, 0);
            this.e = 10;
            this.f = true;
        }

        private a(OfflineSearchMode offlineSearchMode, AbsSearchCallBack absSearchCallBack, byte b2) {
            this.d = false;
            this.a = offlineSearchMode;
            this.b = absSearchCallBack;
            this.c = new OfflineSearchManager$OfflineSearchInfo$1(this);
        }
    }

    private aed(Looper looper) {
        super(looper);
    }

    public static synchronized aed a() {
        aed aed;
        synchronized (aed.class) {
            if (c == null) {
                HandlerThread handlerThread = new HandlerThread("OfflineSearch");
                d = handlerThread;
                handlerThread.start();
                e = new LinkedList();
                c = new aed(d.getLooper());
            }
            aed = c;
        }
        return aed;
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        if (message.what == 0 && !this.f) {
            this.f = true;
            while (!e.isEmpty()) {
                try {
                    this.g = e.poll();
                    if (this.g != null) {
                        a(this.g.a, this.g.c, this.g.e, this.g.f);
                    }
                } catch (NoSuchElementException unused) {
                }
            }
            this.f = false;
            if (!this.f) {
                if (d != null) {
                    removeCallbacksAndMessages(null);
                    if (VERSION.SDK_INT >= 18) {
                        d.quitSafely();
                    } else {
                        d.quit();
                    }
                }
                if (e != null) {
                    e.clear();
                }
                c = null;
            }
        }
    }

    public final synchronized void a(a aVar) {
        e.add(aVar);
        sendEmptyMessage(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005b, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x005d, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean a(com.autonavi.bundle.entity.common.OfflineSearchMode r6, final com.amap.bundle.searchservice.callback.AbsSearchCallBack r7, int r8, boolean r9) {
        /*
            r5 = this;
            monitor-enter(r5)
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x005e }
            long r2 = r5.a     // Catch:{ all -> 0x005e }
            r4 = 0
            long r0 = r0 - r2
            r2 = 1000(0x3e8, double:4.94E-321)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 0
            if (r0 >= 0) goto L_0x0018
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x005e }
            r5.a = r6     // Catch:{ all -> 0x005e }
            monitor-exit(r5)
            return r1
        L_0x0018:
            if (r6 == 0) goto L_0x005c
            if (r7 != 0) goto L_0x001d
            goto L_0x005c
        L_0x001d:
            java.lang.String r0 = r6.strKeyWord     // Catch:{ all -> 0x005e }
            boolean r0 = defpackage.bcz.a(r0)     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x002d
            r6 = 2
            java.lang.String r8 = ""
            r7.error(r6, r8)     // Catch:{ all -> 0x005e }
            monitor-exit(r5)
            return r1
        L_0x002d:
            java.lang.String r0 = r6.strAdCode     // Catch:{ all -> 0x005e }
            java.lang.String r2 = r6.strLongitude     // Catch:{ all -> 0x005e }
            java.lang.String r3 = r6.strLatitude     // Catch:{ all -> 0x005e }
            aee r0 = defpackage.aee.a(r0, r2, r3)     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x0054
            boolean r2 = defpackage.aee.b()     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x0054
            java.lang.String r2 = r6.strKeyWord     // Catch:{ all -> 0x005e }
            boolean r2 = r0.a(r2)     // Catch:{ all -> 0x005e }
            if (r2 == 0) goto L_0x005a
            int r6 = r6.searchType     // Catch:{ all -> 0x005e }
            aed$1 r1 = new aed$1     // Catch:{ all -> 0x005e }
            r1.<init>(r0, r7)     // Catch:{ all -> 0x005e }
            r0.a(r6, r1, r8, r9)     // Catch:{ all -> 0x005e }
            r6 = 1
            monitor-exit(r5)
            return r6
        L_0x0054:
            r6 = 3
            java.lang.String r8 = ""
            r7.error(r6, r8)     // Catch:{ all -> 0x005e }
        L_0x005a:
            monitor-exit(r5)
            return r1
        L_0x005c:
            monitor-exit(r5)
            return r1
        L_0x005e:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aed.a(com.autonavi.bundle.entity.common.OfflineSearchMode, com.amap.bundle.searchservice.callback.AbsSearchCallBack, int, boolean):boolean");
    }

    static /* synthetic */ void a(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            POI poi = (POI) it.next();
            if (poi != null && TextUtils.isEmpty(poi.getAddr())) {
                try {
                    String adCode = poi.getAdCode();
                    if (!TextUtils.isEmpty(adCode) && TextUtils.isDigitsOnly(adCode)) {
                        GADAREAEXTRAINFO b2 = aee.b(ahh.b(adCode));
                        if (b2 != null) {
                            String townName = b2.getTownName();
                            String cityName = b2.getCityName();
                            String provName = b2.getProvName();
                            if (!TextUtils.isEmpty(townName)) {
                                poi.setAddr(townName);
                            } else if (!TextUtils.isEmpty(cityName)) {
                                poi.setAddr(cityName);
                            } else if (!TextUtils.isEmpty(provName)) {
                                poi.setAddr(provName);
                            }
                        }
                    }
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
