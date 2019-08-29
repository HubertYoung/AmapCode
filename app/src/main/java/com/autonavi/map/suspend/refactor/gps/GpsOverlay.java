package com.autonavi.map.suspend.refactor.gps;

import android.content.Context;
import android.os.Bundle;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLGpsOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.AbstractGpsOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.sdk.location.LocationInstrument;

public class GpsOverlay extends AbstractGpsOverlay<cdy> implements xq {
    public static final int HEAD_UP_ALWAYS = 1;
    private static final float INVALID_BEARING = -10000.0f;
    public static final int LOCATE_MODE_GPS = 0;
    public static final int LOCATE_MODE_INDOOR = 2;
    public static final int LOCATE_MODE_NETWORK = 1;
    public static final int NORTH_UP_ALWAYS = 0;
    private static final int NO_ARC_TEXTURE = -1;
    private static final int NO_FIX_BEARING_TIMES = 5;
    private static a mDefaultMarkerProvider = new a();
    private static final long serialVersionUID = 1;
    private int altitude;
    private float bearing;
    /* access modifiers changed from: private */
    public float curGpsBearing = 0.0f;
    private int curShowMode = 0;
    private Bundle extras;
    private boolean hasGpsDevice = true;
    private boolean hasSensorDevice = true;
    private boolean isFloorMatched = true;
    private boolean isLockCenter = false;
    private float lastFixBearing = INVALID_BEARING;
    private int last_fix_x = 0;
    private int last_fix_y = 0;
    private GeoPoint mCurrentPosition = null;
    private boolean mDisableRadius = false;
    private a mGpsOverlayMarkerProvider = mDefaultMarkerProvider;
    private boolean mItemSetted = false;
    private int mLastAngle = 0;
    private agl<b> mListeners = new agl<>();
    private OnItemClickListener mOverlayEventListener = null;
    private int mode;
    private int noBearingTimes = 0;
    private int radius;
    private int x;
    private int y;

    public static class a {
    }

    public interface b {
    }

    @Deprecated
    public void addItem(cdy cdy) {
    }

    public void setMoveToFocus(boolean z) {
    }

    public GpsOverlay(Context context, bty bty) {
        super(bty);
        resumeMarker();
        this.hasGpsDevice = agq.c(AMapAppGlobal.getApplication());
        this.hasSensorDevice = ahr.b();
        clearFocus();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOverlayEventListener = onItemClickListener;
    }

    public void setCenterLocked(boolean z) {
        if (this.isLockCenter != z) {
            this.isLockCenter = z;
            this.mMapView.a((GLGpsOverlay) this.mGLOverlay, this.isLockCenter);
        }
    }

    public void setShowMode(int i) {
        if (this.curShowMode != i) {
            this.curShowMode = i;
            if (getSize() > 0) {
                cdy cdy = (cdy) getItem(0);
                if (cdy != null) {
                    int a2 = cfe.a(cdy.a(), cdy.a);
                    int overlayTextureid = getOverlayTextureid(cdy.c);
                    createMarker(this.isFloorMatched ? getMarkerGpsShine() : getMarkerGpsShineGrey(), 4);
                    ((GLGpsOverlay) this.mGLOverlay).setGpsOverlayItem(cdy.a().x, cdy.a().y, a2, (int) this.curGpsBearing, overlayTextureid, this.isFloorMatched ? getMarkerGpsShine() : getMarkerGpsShineGrey(), -1);
                }
            }
        }
    }

    public int getGpsAngle() {
        return (int) this.curGpsBearing;
    }

    private void setLastBearing(float f, boolean z) {
        if (!z || f == 0.0f) {
            this.noBearingTimes++;
            if (this.noBearingTimes > 5 && this.hasSensorDevice) {
                this.lastFixBearing = INVALID_BEARING;
            }
            return;
        }
        this.lastFixBearing = f;
        this.noBearingTimes = 0;
    }

    public synchronized void setBearings(float f, boolean z, float f2) {
        this.curGpsBearing = (float) Math.ceil((double) f);
        this.hasGpsDevice = true;
        setLastBearing(f, z);
    }

    private int getOverlayTextureid(int i) {
        int i2 = (this.hasGpsDevice || this.hasSensorDevice) ? this.hasSensorDevice ? this.curShowMode == 1 ? this.isFloorMatched ? getMarkerGps3d() : getMarkerGps3dGrey() : this.isFloorMatched ? getMarkerGpsValid() : getMarkerGpsValidGrey() : (this.lastFixBearing == INVALID_BEARING || i == 1) ? this.isFloorMatched ? getMarkerGpsNoSensor() : getMarkerGpsNoSensorGrey() : this.curShowMode == 1 ? this.isFloorMatched ? getMarkerGps3d() : getMarkerGps3dGrey() : this.isFloorMatched ? getMarkerGpsValid() : getMarkerGpsValidGrey() : this.isFloorMatched ? getMarkerGpsNoSensor() : getMarkerGpsNoSensorGrey();
        createMarker(i2, 4);
        return i2;
    }

    public synchronized void refreshItem(boolean z) {
        if (this.mItemSetted) {
            setItem(z, this.x, this.y, this.radius, this.altitude, this.mode, this.extras, this.isFloorMatched);
        }
    }

    public void setFloorMatched(boolean z) {
        if (this.isFloorMatched != z) {
            this.isFloorMatched = z;
            refreshItem(true);
        }
    }

    public void setItem(int i, int i2, int i3, int i4, int i5, Bundle bundle) {
        setItem(false, i, i2, i3, i4, i5, bundle, this.isFloorMatched);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0131, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setItem(boolean r23, int r24, int r25, int r26, int r27, int r28, android.os.Bundle r29, boolean r30) {
        /*
            r22 = this;
            r10 = r22
            r11 = r24
            r12 = r25
            r13 = r28
            monitor-enter(r22)
            if (r23 != 0) goto L_0x0025
            float r8 = r10.curGpsBearing     // Catch:{ all -> 0x0021 }
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r26
            r5 = r27
            r6 = r13
            r7 = r29
            r9 = r30
            boolean r1 = r1.isFilterItem(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0025
            monitor-exit(r22)
            return
        L_0x0021:
            r0 = move-exception
            r1 = r0
            goto L_0x0132
        L_0x0025:
            android.content.Context r1 = r10.mContext     // Catch:{ all -> 0x0021 }
            int r2 = com.autonavi.minimap.R.string.LocationMe     // Catch:{ all -> 0x0021 }
            java.lang.String r1 = defpackage.lh.a(r1, r2)     // Catch:{ all -> 0x0021 }
            com.autonavi.common.model.GeoPoint r3 = new com.autonavi.common.model.GeoPoint     // Catch:{ all -> 0x0021 }
            r3.<init>(r11, r12)     // Catch:{ all -> 0x0021 }
            int r2 = r10.last_fix_x     // Catch:{ all -> 0x0021 }
            r4 = 0
            if (r2 != 0) goto L_0x003b
            int r2 = r10.last_fix_y     // Catch:{ all -> 0x0021 }
            if (r2 == 0) goto L_0x006c
        L_0x003b:
            int r2 = r10.last_fix_x     // Catch:{ all -> 0x0021 }
            if (r11 != r2) goto L_0x006c
            int r2 = r10.last_fix_y     // Catch:{ all -> 0x0021 }
            if (r12 != r2) goto L_0x006c
            java.util.Vector r2 = r10.mItemList     // Catch:{ all -> 0x0021 }
            int r2 = r2.size()     // Catch:{ all -> 0x0021 }
            if (r2 <= 0) goto L_0x0070
            java.util.Vector r2 = r10.mItemList     // Catch:{ all -> 0x0021 }
            java.lang.Object r2 = r2.get(r4)     // Catch:{ all -> 0x0021 }
            cdy r2 = (defpackage.cdy) r2     // Catch:{ all -> 0x0021 }
            if (r2 == 0) goto L_0x0070
            com.autonavi.common.model.GeoPoint r5 = r2.a()     // Catch:{ all -> 0x0021 }
            if (r5 == 0) goto L_0x0070
            com.autonavi.common.model.GeoPoint r5 = r2.a()     // Catch:{ all -> 0x0021 }
            int r5 = r5.x     // Catch:{ all -> 0x0021 }
            r3.x = r5     // Catch:{ all -> 0x0021 }
            com.autonavi.common.model.GeoPoint r2 = r2.a()     // Catch:{ all -> 0x0021 }
            int r2 = r2.y     // Catch:{ all -> 0x0021 }
            r3.y = r2     // Catch:{ all -> 0x0021 }
            goto L_0x0070
        L_0x006c:
            r10.last_fix_x = r11     // Catch:{ all -> 0x0021 }
            r10.last_fix_y = r12     // Catch:{ all -> 0x0021 }
        L_0x0070:
            cdy r2 = new cdy     // Catch:{ all -> 0x0021 }
            r2.<init>(r3)     // Catch:{ all -> 0x0021 }
            r2.a = r4     // Catch:{ all -> 0x0021 }
            boolean r5 = r10.mDisableRadius     // Catch:{ all -> 0x0021 }
            if (r5 == 0) goto L_0x007d
            r5 = 0
            goto L_0x007f
        L_0x007d:
            r5 = r26
        L_0x007f:
            r2.a = r5     // Catch:{ all -> 0x0021 }
            r5 = r27
            r2.b = r5     // Catch:{ all -> 0x0021 }
            r2.c = r13     // Catch:{ all -> 0x0021 }
            r6 = r29
            r2.f = r6     // Catch:{ all -> 0x0021 }
            r2.d = r1     // Catch:{ all -> 0x0021 }
            java.util.Vector r1 = r10.mItemList     // Catch:{ all -> 0x0021 }
            int r1 = r1.size()     // Catch:{ all -> 0x0021 }
            if (r1 != 0) goto L_0x009b
            java.util.Vector r1 = r10.mItemList     // Catch:{ all -> 0x0021 }
            r1.add(r2)     // Catch:{ all -> 0x0021 }
            goto L_0x00a5
        L_0x009b:
            java.util.Vector r1 = r10.mItemList     // Catch:{ all -> 0x0021 }
            r1.clear()     // Catch:{ all -> 0x0021 }
            java.util.Vector r1 = r10.mItemList     // Catch:{ all -> 0x0021 }
            r1.add(r2)     // Catch:{ all -> 0x0021 }
        L_0x00a5:
            r10.mCurrentPosition = r3     // Catch:{ all -> 0x0021 }
            boolean r1 = r10.mDisableRadius     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x00b0
            r4 = r26
            r17 = 0
            goto L_0x00b8
        L_0x00b0:
            r4 = r26
            int r1 = defpackage.cfe.a(r3, r4)     // Catch:{ all -> 0x0021 }
            r17 = r1
        L_0x00b8:
            int r19 = r10.getOverlayTextureid(r13)     // Catch:{ all -> 0x0021 }
            boolean r1 = r10.isFloorMatched     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x00c5
            int r1 = r22.getMarkerGpsShine()     // Catch:{ all -> 0x0021 }
            goto L_0x00c9
        L_0x00c5:
            int r1 = r22.getMarkerGpsShineGrey()     // Catch:{ all -> 0x0021 }
        L_0x00c9:
            r2 = 4
            r10.createMarker(r1, r2)     // Catch:{ all -> 0x0021 }
            com.autonavi.jni.ae.gmap.gloverlay.GLOverlay r1 = r10.mGLOverlay     // Catch:{ all -> 0x0021 }
            r14 = r1
            com.autonavi.jni.ae.gmap.gloverlay.GLGpsOverlay r14 = (com.autonavi.jni.ae.gmap.gloverlay.GLGpsOverlay) r14     // Catch:{ all -> 0x0021 }
            int r15 = r3.x     // Catch:{ all -> 0x0021 }
            int r1 = r3.y     // Catch:{ all -> 0x0021 }
            float r2 = r10.curGpsBearing     // Catch:{ all -> 0x0021 }
            int r2 = (int) r2     // Catch:{ all -> 0x0021 }
            boolean r6 = r10.isFloorMatched     // Catch:{ all -> 0x0021 }
            if (r6 == 0) goto L_0x00e2
            int r6 = r22.getMarkerGpsShine()     // Catch:{ all -> 0x0021 }
            goto L_0x00e6
        L_0x00e2:
            int r6 = r22.getMarkerGpsShineGrey()     // Catch:{ all -> 0x0021 }
        L_0x00e6:
            r20 = r6
            r21 = -1
            r16 = r1
            r18 = r2
            r14.setGpsOverlayItem(r15, r16, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x0021 }
            agl<com.autonavi.map.suspend.refactor.gps.GpsOverlay$b> r7 = r10.mListeners     // Catch:{ all -> 0x0021 }
            com.autonavi.map.suspend.refactor.gps.GpsOverlay$1 r8 = new com.autonavi.map.suspend.refactor.gps.GpsOverlay$1     // Catch:{ all -> 0x0021 }
            r1 = r8
            r2 = r10
            r6 = r13
            r1.<init>(r3, r4, r5, r6)     // Catch:{ all -> 0x0021 }
            r7.a(r8)     // Catch:{ all -> 0x0021 }
            int r1 = r10.curShowMode     // Catch:{ all -> 0x0021 }
            r2 = 1
            if (r1 != r2) goto L_0x0114
            int r1 = r10.mLastAngle     // Catch:{ all -> 0x0021 }
            float r3 = r10.curGpsBearing     // Catch:{ all -> 0x0021 }
            int r3 = (int) r3     // Catch:{ all -> 0x0021 }
            if (r1 == r3) goto L_0x0114
            bty r1 = r10.mMapView     // Catch:{ all -> 0x0021 }
            float r3 = r10.curGpsBearing     // Catch:{ all -> 0x0021 }
            int r3 = (int) r3     // Catch:{ all -> 0x0021 }
            float r3 = (float) r3     // Catch:{ all -> 0x0021 }
            r1.a(r3)     // Catch:{ all -> 0x0021 }
            goto L_0x0125
        L_0x0114:
            bty r1 = r10.mMapView     // Catch:{ all -> 0x0021 }
            android.graphics.Rect r1 = r1.H()     // Catch:{ all -> 0x0021 }
            boolean r1 = r1.contains(r11, r12)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0125
            bty r1 = r10.mMapView     // Catch:{ all -> 0x0021 }
            r1.S()     // Catch:{ all -> 0x0021 }
        L_0x0125:
            float r1 = r10.curGpsBearing     // Catch:{ all -> 0x0021 }
            int r1 = (int) r1     // Catch:{ all -> 0x0021 }
            r10.mLastAngle = r1     // Catch:{ all -> 0x0021 }
            boolean r1 = r10.mItemSetted     // Catch:{ all -> 0x0021 }
            if (r1 != 0) goto L_0x0130
            r10.mItemSetted = r2     // Catch:{ all -> 0x0021 }
        L_0x0130:
            monitor-exit(r22)
            return
        L_0x0132:
            monitor-exit(r22)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.suspend.refactor.gps.GpsOverlay.setItem(boolean, int, int, int, int, int, android.os.Bundle, boolean):void");
    }

    public cdy getItem() {
        if (getSize() > 0) {
            return (cdy) this.mItemList.get(0);
        }
        return null;
    }

    public GeoPoint getCurrentPosition() {
        return this.mCurrentPosition;
    }

    public void onPointOverlayClick() {
        if (this.mOverlayEventListener != null) {
            this.mOverlayEventListener.onItemClick(this.mMapView, this, getItem());
        }
    }

    private boolean isFilterItem(int i, int i2, int i3, int i4, int i5, Bundle bundle, float f, boolean z) {
        if (this.x == i && this.y == i2 && this.radius == i3 && this.altitude == i4 && this.mode == i5 && isEqualIndoorBundle(this.extras, bundle) && Math.abs(this.bearing - f) < 1.0f && this.isFloorMatched == z) {
            return true;
        }
        this.x = i;
        this.y = i2;
        this.radius = i3;
        this.altitude = i4;
        this.mode = i5;
        this.extras = bundle;
        this.bearing = (float) ((int) f);
        this.isFloorMatched = z;
        return false;
    }

    public boolean clear() {
        this.x = -1;
        this.y = -1;
        this.radius = -1;
        this.altitude = -1;
        this.mode = -1;
        this.extras = null;
        this.bearing = 0.0f;
        setCenterLocked(false);
        this.mCurrentPosition = null;
        return super.clear();
    }

    private boolean isEqualIndoorBundle(Bundle bundle, Bundle bundle2) {
        if (bundle == null || bundle2 == null || (bundle.getDouble(LocationInstrument.INDOOR_LOCATION_LON) == bundle2.getDouble(LocationInstrument.INDOOR_LOCATION_LON) && bundle.getDouble(LocationInstrument.INDOOR_LOCATION_LAT) == bundle2.getDouble(LocationInstrument.INDOOR_LOCATION_LAT) && bundle.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, "").equalsIgnoreCase(bundle2.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, "")) && bundle.getString("floor", "").equalsIgnoreCase(bundle2.getString("floor", "")))) {
            return true;
        }
        return false;
    }

    public void setMarkerProvider(a aVar) {
        if (aVar != null && aVar != this.mGpsOverlayMarkerProvider) {
            this.mGpsOverlayMarkerProvider = aVar;
            refreshItem(true);
        }
    }

    public static a getDefaultMarkerProvider() {
        return mDefaultMarkerProvider;
    }

    public void addListener(b bVar) {
        this.mListeners.a(bVar);
    }

    public void removeListener(b bVar) {
        this.mListeners.b(bVar);
    }

    public void setDisableRadius(boolean z) {
        if (z != this.mDisableRadius) {
            this.mDisableRadius = z;
            refreshItem(true);
        }
    }

    private int getMarkerGpsShine() {
        return R.drawable.navi_map_flash;
    }

    private int getMarkerGpsNoSensor() {
        return R.drawable.marker_gps_no_sensor;
    }

    private int getMarkerGpsValid() {
        return R.drawable.navi_map_gps_locked;
    }

    private int getMarkerGps3d() {
        return R.drawable.navi_map_gps_3d;
    }

    private int getMarkerGpsShineGrey() {
        return R.drawable.navi_map_flash_grey;
    }

    private int getMarkerGpsNoSensorGrey() {
        return R.drawable.marker_gps_no_sensor_grey;
    }

    private int getMarkerGpsValidGrey() {
        return R.drawable.navi_map_gps_locked_grey;
    }

    private int getMarkerGps3dGrey() {
        return R.drawable.navi_map_gps_3d_grey;
    }
}
