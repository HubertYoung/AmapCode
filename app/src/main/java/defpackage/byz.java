package defpackage;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.InputDeviceCompat;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.mapinterface.IMapRequestManager.a;
import com.autonavi.map.search.overlay.MarkFocusOverlay;
import com.autonavi.map.search.overlay.SearchPoiMarkOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@SuppressFBWarnings({"MS_CANNOT_BE_FINAL"})
/* renamed from: byz reason: default package */
/* compiled from: PoiMarkManager */
public final class byz extends Handler {
    public static boolean e = false;
    SearchPoiMarkOverlay a;
    MarkFocusOverlay b;
    public Set<String> c = new HashSet();
    String d;
    bzw f;
    private bty g;
    private HashMap<String, ArrayList<POI>> h = new HashMap<>();
    private String i = "";
    private ArrayList<String> j = new ArrayList<>();
    private ArrayList<String> k = new ArrayList<>();
    private ArrayList<String> l;
    private Set<String> m = new HashSet();
    private boolean n = true;
    private InfoliteResult o;
    private AosRequest p;

    private byz(Looper looper, bty bty, SearchPoiMarkOverlay searchPoiMarkOverlay, MarkFocusOverlay markFocusOverlay) {
        super(looper);
        this.g = bty;
        this.a = searchPoiMarkOverlay;
        searchPoiMarkOverlay.showReversed(true);
        searchPoiMarkOverlay.setCheckCover(true);
        searchPoiMarkOverlay.setHideIconWhenCovered(true);
        this.b = markFocusOverlay;
    }

    public static byz a(bty bty, SearchPoiMarkOverlay searchPoiMarkOverlay, MarkFocusOverlay markFocusOverlay) {
        return new byz(Looper.getMainLooper(), bty, searchPoiMarkOverlay, markFocusOverlay);
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 16385:
                if (aaw.c(AMapAppGlobal.getApplication())) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    Rect H = this.g.H();
                    int w = this.g.w();
                    int i2 = 20 - w;
                    GeoPoint b2 = cfg.b((long) (H.left >> i2), (long) (H.top >> i2));
                    GeoPoint b3 = cfg.b((long) (H.right >> i2), (long) (H.bottom >> i2));
                    if (b3.x - b2.x <= 30 && b3.y - b2.y <= 30) {
                        int i3 = 0;
                        int i4 = 0;
                        while (i3 <= b3.x - b2.x) {
                            int i5 = i4;
                            int i6 = 0;
                            while (i6 <= b3.y - b2.y) {
                                i5++;
                                StringBuilder sb = new StringBuilder();
                                sb.append(b2.x + i3);
                                sb.append("-");
                                sb.append(b2.y + i6);
                                sb.append("-");
                                sb.append(w);
                                arrayList.add(sb.toString());
                                if (i5 < 100) {
                                    i6++;
                                }
                            }
                            i3++;
                            i4 = i5;
                        }
                    }
                    this.l = arrayList;
                    b(this.l);
                    c(this.k);
                    d(this.j);
                    a(this.l);
                    return;
                }
                return;
            case InputDeviceCompat.SOURCE_STYLUS /*16386*/:
                a aVar = (a) message.obj;
                if (aVar != null) {
                    HashMap<String, ArrayList<POI>> hashMap = aVar.b;
                    if (hashMap != null) {
                        String[] strArr = (String[]) hashMap.keySet().toArray(new String[0]);
                        this.h.putAll(hashMap);
                        if (this.n) {
                            for (String str : strArr) {
                                a(this.a, hashMap.get(str), str);
                            }
                            this.g.R();
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            case 16387:
                return;
            case 16388:
                return;
            case 16389:
                this.a.clear();
                this.i = this.d;
                this.h.clear();
                this.k.clear();
                this.j.clear();
                this.c.clear();
                this.m.clear();
                break;
            case 16390:
                this.c.clear();
                this.m.clear();
                if (this.o != null) {
                    a(this.o);
                    return;
                }
                break;
        }
    }

    private void a(ArrayList<String> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            for (String next : this.c) {
                if (!arrayList.contains(next)) {
                    this.m.add(next);
                }
            }
            for (String next2 : this.m) {
                if (this.a.removeTilesPoiFromOverlay(next2)) {
                    this.c.remove(next2);
                }
            }
            this.m.clear();
        }
    }

    public final void a() {
        removeMessages(16390);
        sendEmptyMessage(16390);
        if (this.g != null) {
            float v = this.g.v();
            if (v >= 20.0f || v <= 10.0f) {
                this.a.setVisible(false);
            } else {
                this.a.setVisible(true);
            }
        }
    }

    private void b(ArrayList<String> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            this.k.clear();
            this.j.clear();
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (this.h.containsKey(next)) {
                    this.k.add(next);
                } else {
                    this.j.add(next);
                }
            }
        }
    }

    private void c(ArrayList<String> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str = arrayList.get(i2);
                if (!this.c.contains(str)) {
                    a(this.a, this.h.get(str), str);
                }
            }
        }
    }

    public final void a(boolean z) {
        this.c.clear();
        String[] strArr = (String[]) this.h.keySet().toArray(new String[0]);
        for (String str : strArr) {
            SearchPoiMarkOverlay searchPoiMarkOverlay = this.a;
            ArrayList arrayList = this.h.get(str);
            if (searchPoiMarkOverlay.isVisible() && arrayList != null && arrayList.size() > 0 && !this.c.contains(str)) {
                this.c.add(str);
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    searchPoiMarkOverlay.addMarkItem((POI) arrayList.get(i2), str, false, true);
                }
            }
        }
        if (this.f != null) {
            a((PointOverlayItem) this.f, this.f.a, z);
        }
    }

    public final void a(bzx bzx) {
        if (bzx != null) {
            a((PointOverlayItem) bzx, bzx.b, false);
        }
    }

    private void f() {
        sendMessage(obtainMessage(16389));
    }

    public final void a(InfoliteResult infoliteResult) {
        if (this.n) {
            if (infoliteResult == null || infoliteResult.searchInfo == null || infoliteResult.searchInfo.u != 1 || !(this.o == null || this.o.searchInfo == null || this.o.searchInfo.u == 1)) {
                c();
                f();
                return;
            }
            this.o = infoliteResult;
            this.d = infoliteResult.mWrapper.keywords;
            if (this.d != null && !this.d.equals(this.i)) {
                f();
            }
            removeMessages(16385);
            sendMessageDelayed(obtainMessage(16385), 1000);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0050, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void d(java.util.ArrayList<java.lang.String> r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            if (r9 == 0) goto L_0x0054
            int r0 = r9.size()     // Catch:{ all -> 0x0051 }
            if (r0 > 0) goto L_0x000a
            goto L_0x0054
        L_0x000a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0051 }
            r0.<init>()     // Catch:{ all -> 0x0051 }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x0051 }
        L_0x0013:
            boolean r1 = r9.hasNext()     // Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x0028
            java.lang.Object r1 = r9.next()     // Catch:{ all -> 0x0051 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0051 }
            r0.append(r1)     // Catch:{ all -> 0x0051 }
            java.lang.String r1 = ";"
            r0.append(r1)     // Catch:{ all -> 0x0051 }
            goto L_0x0013
        L_0x0028:
            r9 = 16386(0x4002, float:2.2962E-41)
            r8.removeMessages(r9)     // Catch:{ all -> 0x0051 }
            java.lang.Class<com.autonavi.map.mapinterface.IMapRequestManager> r9 = com.autonavi.map.mapinterface.IMapRequestManager.class
            java.lang.Object r9 = defpackage.ank.a(r9)     // Catch:{ all -> 0x0051 }
            r1 = r9
            com.autonavi.map.mapinterface.IMapRequestManager r1 = (com.autonavi.map.mapinterface.IMapRequestManager) r1     // Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x004f
            java.lang.String r2 = r0.toString()     // Catch:{ all -> 0x0051 }
            r3 = 0
            java.lang.String r4 = r8.d     // Catch:{ all -> 0x0051 }
            java.lang.String r5 = r8.h()     // Catch:{ all -> 0x0051 }
            r6 = 0
            com.autonavi.map.search.manager.PoiMarkManager$1 r7 = new com.autonavi.map.search.manager.PoiMarkManager$1     // Catch:{ all -> 0x0051 }
            r7.<init>(r8)     // Catch:{ all -> 0x0051 }
            com.amap.bundle.aosservice.request.AosRequest r9 = r1.poiMark(r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0051 }
            r8.p = r9     // Catch:{ all -> 0x0051 }
        L_0x004f:
            monitor-exit(r8)
            return
        L_0x0051:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        L_0x0054:
            monitor-exit(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.byz.d(java.util.ArrayList):void");
    }

    public final void b() {
        this.b.clear();
        this.f = null;
    }

    public final void c() {
        this.a.clear();
        this.b.clear();
        g();
        removeCallbacksAndMessages(null);
    }

    private void g() {
        if (this.p != null && !this.p.isCanceled()) {
            this.p.cancel();
            this.p = null;
        }
    }

    public final void d() {
        this.a.clearFocus();
        this.b.clear();
    }

    public final void e() {
        this.a.onlyClear();
        this.b.clear();
    }

    /* access modifiers changed from: 0000 */
    public final void a(PointOverlayItem pointOverlayItem, String str, boolean z) {
        if (pointOverlayItem != null) {
            this.b.clear();
            bzw bzw = new bzw(pointOverlayItem.getGeoPoint());
            bzw.a = str;
            bzw.mBubbleMarker = null;
            bzw.mDefaultMarker = this.b.createMarker(R.drawable.marker_other, 4);
            bzw.mFocusMarker = this.b.createMarker(R.drawable.marker_other_highlight, 4);
            this.f = bzw;
            this.b.addItem(bzw);
            this.b.setFocus((PointOverlayItem) bzw, z);
        }
    }

    private String h() {
        if (!(this.o == null || this.o.mWrapper == null || TextUtils.isEmpty(this.o.mWrapper.classify_data))) {
            String[] split = this.o.mWrapper.classify_data.split("\\+");
            if (split.length > 0) {
                int i2 = 0;
                while (i2 < split.length) {
                    if (!split[i2].contains("category")) {
                        i2++;
                    } else if (split[i2].indexOf(59) > 0) {
                        return "";
                    } else {
                        return split[i2].substring(split[i2].indexOf(61) + 1, split[i2].length());
                    }
                }
            }
        }
        return "";
    }

    private void a(SearchPoiMarkOverlay searchPoiMarkOverlay, ArrayList<POI> arrayList, String str) {
        if (searchPoiMarkOverlay.isVisible() && arrayList != null && arrayList.size() > 0 && this.l.contains(str) && !this.c.contains(str)) {
            this.c.add(str);
            if (e) {
                if (this.a != null) {
                    this.a.clear();
                }
                e = false;
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                searchPoiMarkOverlay.addMarkItem(arrayList.get(i2), str, false, false);
            }
        }
    }
}
