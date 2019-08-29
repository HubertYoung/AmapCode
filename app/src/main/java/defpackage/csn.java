package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.autonavi.minimap.map.LocalReportOverlay;
import com.autonavi.minimap.map.TrafficOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import java.util.HashMap;

@Deprecated
/* renamed from: csn reason: default package */
/* compiled from: TrafficManager */
public class csn extends Handler {
    private static volatile csn a;
    private LocalReportOverlay b;
    private HashMap<Integer, TrafficTopic> c = new HashMap<>();
    private MapSharePreference d = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public static csn a() {
        if (a == null) {
            synchronized (csn.class) {
                try {
                    if (a == null) {
                        HandlerThread handlerThread = new HandlerThread("traffic_manager");
                        handlerThread.start();
                        a = new csn(handlerThread.getLooper());
                    }
                }
            }
        }
        csn csn = a;
        IOverlayManager overlayManager = DoNotUseTool.getMapManager().getOverlayManager();
        if (overlayManager != null && (overlayManager.getUniversalOverlay(UvOverlay.LocalReportOverlay) instanceof PointOverlay)) {
            PointOverlay pointOverlay = (PointOverlay) overlayManager.getUniversalOverlay(UvOverlay.LocalReportOverlay);
            if (pointOverlay instanceof LocalReportOverlay) {
                csn.b = (LocalReportOverlay) pointOverlay;
            }
        }
        return a;
    }

    private csn(Looper looper) {
        super(looper);
    }

    public final void a(TrafficTopic trafficTopic) {
        this.c.put(Integer.valueOf(trafficTopic.getId()), trafficTopic);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0035, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(int r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            com.autonavi.minimap.map.LocalReportOverlay r0 = r3.b     // Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x0034
            com.autonavi.minimap.map.LocalReportOverlay r0 = r3.b     // Catch:{ all -> 0x0036 }
            int r0 = r0.getSize()     // Catch:{ all -> 0x0036 }
            if (r0 <= 0) goto L_0x0034
            r0 = 0
        L_0x000e:
            com.autonavi.minimap.map.LocalReportOverlay r1 = r3.b     // Catch:{ all -> 0x0036 }
            int r1 = r1.getSize()     // Catch:{ all -> 0x0036 }
            if (r0 >= r1) goto L_0x0034
            com.autonavi.minimap.map.LocalReportOverlay r1 = r3.b     // Catch:{ all -> 0x0036 }
            java.lang.Object r1 = r1.getItem(r0)     // Catch:{ all -> 0x0036 }
            com.autonavi.minimap.map.TrafficOverlayItem r1 = (com.autonavi.minimap.map.TrafficOverlayItem) r1     // Catch:{ all -> 0x0036 }
            if (r1 == 0) goto L_0x0031
            com.autonavi.minimap.map.ITrafficTopic r2 = r1.getTopic()     // Catch:{ all -> 0x0036 }
            int r2 = r2.getId()     // Catch:{ all -> 0x0036 }
            if (r4 != r2) goto L_0x0031
            com.autonavi.minimap.map.LocalReportOverlay r4 = r3.b     // Catch:{ all -> 0x0036 }
            r4.removeItem(r1)     // Catch:{ all -> 0x0036 }
            monitor-exit(r3)
            return
        L_0x0031:
            int r0 = r0 + 1
            goto L_0x000e
        L_0x0034:
            monitor-exit(r3)
            return
        L_0x0036:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.csn.a(int):void");
    }

    public final void a(GeoPoint geoPoint, int i, String str) {
        if (this.b != null && geoPoint != null && TextUtils.isDigitsOnly(str)) {
            POI createPOI = POIFactory.createPOI("self_report", geoPoint);
            TrafficTopic trafficTopic = new TrafficTopic();
            trafficTopic.setId(i);
            trafficTopic.setFilterKey(i);
            trafficTopic.setLayerTag(Integer.parseInt(str));
            trafficTopic.addUids(0, "unkonwn");
            TrafficOverlayItem trafficOverlayItem = new TrafficOverlayItem(trafficTopic, createPOI.getPoint(), "", true);
            trafficOverlayItem.setGeneratedTime(System.currentTimeMillis());
            trafficOverlayItem.setIsLocalReport(true);
            this.b.addItem(trafficOverlayItem);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(int r5, int r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            com.autonavi.minimap.map.LocalReportOverlay r0 = r4.b     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x003a
            com.autonavi.minimap.map.LocalReportOverlay r0 = r4.b     // Catch:{ all -> 0x003c }
            int r0 = r0.getSize()     // Catch:{ all -> 0x003c }
            if (r0 <= 0) goto L_0x003a
            r0 = 0
            r1 = 0
        L_0x000f:
            com.autonavi.minimap.map.LocalReportOverlay r2 = r4.b     // Catch:{ all -> 0x003c }
            int r2 = r2.getSize()     // Catch:{ all -> 0x003c }
            if (r1 >= r2) goto L_0x003a
            com.autonavi.minimap.map.LocalReportOverlay r2 = r4.b     // Catch:{ all -> 0x003c }
            java.lang.Object r2 = r2.getItem(r1)     // Catch:{ all -> 0x003c }
            com.autonavi.minimap.map.TrafficOverlayItem r2 = (com.autonavi.minimap.map.TrafficOverlayItem) r2     // Catch:{ all -> 0x003c }
            if (r2 == 0) goto L_0x0037
            com.autonavi.minimap.map.ITrafficTopic r3 = r2.getTopic()     // Catch:{ all -> 0x003c }
            int r3 = r3.getId()     // Catch:{ all -> 0x003c }
            if (r5 != r3) goto L_0x0037
            com.autonavi.minimap.map.ITrafficTopic r5 = r2.getTopic()     // Catch:{ all -> 0x003c }
            r5.setId(r6)     // Catch:{ all -> 0x003c }
            r2.setIsLocalReport(r0)     // Catch:{ all -> 0x003c }
            monitor-exit(r4)
            return
        L_0x0037:
            int r1 = r1 + 1
            goto L_0x000f
        L_0x003a:
            monitor-exit(r4)
            return
        L_0x003c:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.csn.a(int, int):void");
    }

    public final synchronized void b() {
        if (this.b != null) {
            this.b.checkOverTime();
        }
    }
}
