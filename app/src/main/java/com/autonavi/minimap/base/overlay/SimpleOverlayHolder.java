package com.autonavi.minimap.base.overlay;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SimpleOverlayHolder {
    private HashSet<BaseOverlay> mAutoResumeOverlays = new HashSet<>();
    private bty mMapView;
    private ArrayList<BaseOverlay> mStoredOverlays = new ArrayList<>();

    public SimpleOverlayHolder(bty bty) {
        this.mMapView = bty;
    }

    public synchronized void addOverlay(BaseOverlay baseOverlay, boolean z) {
        if (this.mMapView != null) {
            if (z) {
                if (this.mAutoResumeOverlays.contains(baseOverlay)) {
                    throw new IllegalStateException("Cannot add the same overlay !!!");
                }
                this.mAutoResumeOverlays.add(baseOverlay);
            }
            this.mMapView.F().b((BaseMapOverlay) baseOverlay);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void removeOverlay(com.autonavi.ae.gmap.gloverlay.BaseOverlay r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            bty r0 = r1.mMapView     // Catch:{ all -> 0x002c }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            bty r0 = r1.mMapView     // Catch:{ all -> 0x002c }
            btm r0 = r0.F()     // Catch:{ all -> 0x002c }
            r0.c(r2)     // Catch:{ all -> 0x002c }
            java.util.ArrayList<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r0 = r1.mStoredOverlays     // Catch:{ all -> 0x002c }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x001d
            java.util.ArrayList<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r0 = r1.mStoredOverlays     // Catch:{ all -> 0x002c }
            r0.remove(r2)     // Catch:{ all -> 0x002c }
        L_0x001d:
            java.util.HashSet<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r0 = r1.mAutoResumeOverlays     // Catch:{ all -> 0x002c }
            boolean r0 = r0.contains(r2)     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x002a
            java.util.HashSet<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r0 = r1.mAutoResumeOverlays     // Catch:{ all -> 0x002c }
            r0.remove(r2)     // Catch:{ all -> 0x002c }
        L_0x002a:
            monitor-exit(r1)
            return
        L_0x002c:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.SimpleOverlayHolder.removeOverlay(com.autonavi.ae.gmap.gloverlay.BaseOverlay):void");
    }

    public synchronized void onSave() {
        this.mStoredOverlays.clear();
        if (this.mMapView != null) {
            btm F = this.mMapView.F();
            Iterator<BaseOverlay> it = this.mAutoResumeOverlays.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                BaseOverlay next = it.next();
                if (!F.a((BaseMapOverlay) next)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(next);
                }
            }
            if (arrayList != null) {
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    this.mAutoResumeOverlays.remove((BaseOverlay) it2.next());
                }
            }
            int a = F.a();
            for (int i = 0; i < a; i++) {
                BaseMapOverlay a2 = F.a(i);
                if ((a2 instanceof BaseOverlay) && this.mAutoResumeOverlays.contains(a2)) {
                    this.mStoredOverlays.add((BaseOverlay) a2);
                }
            }
            Iterator<BaseOverlay> it3 = this.mStoredOverlays.iterator();
            while (it3.hasNext()) {
                BaseOverlay next2 = it3.next();
                next2.onPause();
                F.c(next2);
            }
        }
    }

    public synchronized void onRestore() {
        if (this.mMapView != null) {
            Iterator<BaseOverlay> it = this.mStoredOverlays.iterator();
            while (it.hasNext()) {
                BaseOverlay next = it.next();
                this.mMapView.F().b((BaseMapOverlay) next);
                next.onResume();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onDestroy() {
        /*
            r3 = this;
            monitor-enter(r3)
            bty r0 = r3.mMapView     // Catch:{ all -> 0x0031 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r3)
            return
        L_0x0007:
            bty r0 = r3.mMapView     // Catch:{ all -> 0x0031 }
            btm r0 = r0.F()     // Catch:{ all -> 0x0031 }
            java.util.ArrayList<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r1 = r3.mStoredOverlays     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x002f
            java.util.ArrayList<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r1 = r3.mStoredOverlays     // Catch:{ all -> 0x0031 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0031 }
        L_0x0017:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0031 }
            if (r2 == 0) goto L_0x0027
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0031 }
            com.autonavi.ae.gmap.gloverlay.BaseOverlay r2 = (com.autonavi.ae.gmap.gloverlay.BaseOverlay) r2     // Catch:{ all -> 0x0031 }
            r0.c(r2)     // Catch:{ all -> 0x0031 }
            goto L_0x0017
        L_0x0027:
            java.util.ArrayList<com.autonavi.ae.gmap.gloverlay.BaseOverlay> r0 = r3.mStoredOverlays     // Catch:{ all -> 0x0031 }
            r0.clear()     // Catch:{ all -> 0x0031 }
            r0 = 0
            r3.mStoredOverlays = r0     // Catch:{ all -> 0x0031 }
        L_0x002f:
            monitor-exit(r3)
            return
        L_0x0031:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.SimpleOverlayHolder.onDestroy():void");
    }
}
