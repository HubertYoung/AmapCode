package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class RouteBoardOverlay extends PointOverlay<RouteBoardOverlayItem> implements amg {
    public static final int MARKER_CONGESTION_BOARD_BG = 1050;
    public static final int MARKER_NAVI_ROUTE_BOARD_BG = 1000;
    private static int MIN_SHOW_LEVEL = 14;
    private boolean isDay = false;
    private boolean isEdogModel = false;
    /* access modifiers changed from: private */
    public boolean m2dShown = false;
    protected int mCarPx;
    protected int mCarPy;
    private List<GLPointOverlay> mCheckedOverlayList = new ArrayList();
    private int mCongestionMapLevel = 16;
    private RouteBoardOverlayItem mCongestionOverlayItem = null;
    protected String mCurName;
    private List<RouteBoardOverlayItem> mEdogItemList = new ArrayList();
    private int mEdogMapLevel = 16;
    protected Rect mEndPointRect = new Rect();
    protected int mEndPx;
    protected int mEndPy;
    protected amc mEndTexture;
    private List<RouteBoardOverlayItem> mInWinItemList = new ArrayList();
    private List<Integer> mIndexIds = new ArrayList();
    private Set<String> mLastKeys = new HashSet();
    /* access modifiers changed from: private */
    public int mMapLevel = 16;
    protected Rect mNaviDirectRect = new Rect();
    protected amc mNaviDirectionTexture;
    protected String mNextName;
    private RouteBoardOverlayItem mPathOverlayItem = null;
    private List<RouteBoardOverlayItem> mRouteBoardList = Collections.synchronizedList(new ArrayList());
    private int mRouteNameColor = -1;
    private int mRouteNameSize = 15;
    /* access modifiers changed from: private */
    public Rect mValidRect = null;
    private boolean mZeroCameraDegreeCongestionIsShown = false;

    public enum GLBoardType {
        BOARD_TYPE_DEFAULT,
        BOARD_TYPE_PATH,
        BOARD_TYPE_BISIDE,
        BOARD_TYPE_CONGESTION,
        BOARD_TYPE_EDOG,
        BOARD_TYPE_MARK_BUILD
    }

    /* access modifiers changed from: protected */
    public float getAnchorYRatio() {
        return 1.0f;
    }

    public RouteBoardOverlay(bty bty) {
        super(bty);
        this.isDay = this.mMapView.ae() == 0;
        setMinDisplayLevel(MIN_SHOW_LEVEL);
        setAnimatorType(6);
        setClickable(false);
        this.mMapView.a((amo) new amo() {
            public void setRouteBoardData(int i, byte[] bArr) {
                PointF pointF;
                byte[] bArr2 = bArr;
                if (bArr2 != null && ((RouteBoardOverlay.this.mMapView.J() != 0.0f || RouteBoardOverlay.this.m2dShown) && RouteBoardOverlay.this.mMapView.w() >= RouteBoardOverlay.this.mMapLevel)) {
                    int a = amy.a(bArr2, 0);
                    int i2 = 4;
                    int i3 = 4;
                    int i4 = 0;
                    while (i4 < a) {
                        int i5 = i3 + 1;
                        byte b = bArr2[i3];
                        StringBuffer stringBuffer = new StringBuffer();
                        int i6 = i5;
                        for (int i7 = 0; i7 < b; i7++) {
                            stringBuffer.append((char) amy.c(bArr2, i6));
                            i6 += 2;
                        }
                        String stringBuffer2 = stringBuffer.toString();
                        int a2 = amy.a(bArr2, i6);
                        int i8 = i6 + 4;
                        int a3 = amy.a(bArr2, i8);
                        int i9 = i8 + i2;
                        int a4 = amy.a(bArr2, i9);
                        int i10 = i9 + i2 + i2;
                        int a5 = amy.a(bArr2, i10);
                        int i11 = i10 + i2;
                        int a6 = amy.a(bArr2, i11);
                        int i12 = i11 + i2;
                        long b2 = amy.b(bArr2, i12);
                        int i13 = i12 + 8;
                        if (a5 != GLBoardType.BOARD_TYPE_BISIDE.ordinal() || ((TextUtils.isEmpty(RouteBoardOverlay.this.mCurName) || !RouteBoardOverlay.this.mCurName.contains(stringBuffer2)) && (TextUtils.isEmpty(RouteBoardOverlay.this.mNextName) || !RouteBoardOverlay.this.mNextName.contains(stringBuffer2)))) {
                            if (RouteBoardOverlay.this.mValidRect != null) {
                                if (a5 != GLBoardType.BOARD_TYPE_MARK_BUILD.ordinal()) {
                                    pointF = RouteBoardOverlay.this.mMapView.f(a2, a3);
                                } else {
                                    pointF = RouteBoardOverlay.this.mMapView.b(a2, a3, a4);
                                }
                                if (pointF != null && !RouteBoardOverlay.this.mValidRect.contains((int) pointF.x, (int) pointF.y)) {
                                }
                            }
                            GLGeoPoint gLGeoPoint = new GLGeoPoint(a2, a3);
                            if (!RouteBoardOverlay.this.hasOverlayItem(a5 == GLBoardType.BOARD_TYPE_MARK_BUILD.ordinal() ? Long.toString(b2) : stringBuffer2)) {
                                RouteBoardOverlay.this.addOtherRouteBoardItem(GeoPoint.glGeoPoint2GeoPoint(gLGeoPoint), (float) a4, stringBuffer2, a5, a6, b2, i4);
                            }
                        }
                        i4++;
                        i3 = i13;
                        i2 = 4;
                    }
                    RouteBoardOverlay.this.removeLastItems();
                    GLMapState a7 = RouteBoardOverlay.this.mMapView.a(RouteBoardOverlay.this.mMapView.ad());
                    if (a7 != null) {
                        synchronized (RouteBoardOverlay.this) {
                            try {
                                RouteBoardOverlay.this.checkOverlayItemInBound(a7);
                                RouteBoardOverlay.this.checkOverlayCovered(a7);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }
            }
        });
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLPointOverlay(this.mMapView.ad(), this.mMapView.c(), hashCode());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b1, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00b6, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void addOverlayItem(com.autonavi.minimap.base.overlay.RouteBoardOverlayItem r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x00b5
            com.autonavi.minimap.base.overlay.Marker r0 = r4.mDefaultMarker     // Catch:{ all -> 0x00b2 }
            if (r0 != 0) goto L_0x0009
            goto L_0x00b5
        L_0x0009:
            bty r0 = r3.mMapView     // Catch:{ all -> 0x00b2 }
            btm r0 = r0.F()     // Catch:{ all -> 0x00b2 }
            com.autonavi.minimap.base.overlay.Marker r1 = r4.mDefaultMarker     // Catch:{ all -> 0x00b2 }
            int r1 = r1.mID     // Catch:{ all -> 0x00b2 }
            amc r0 = r0.b(r1)     // Catch:{ all -> 0x00b2 }
            r4.mTextureItem = r0     // Catch:{ all -> 0x00b2 }
            com.autonavi.minimap.base.overlay.RouteBoardOverlay$GLBoardType r0 = r4.mRouteBoardType     // Catch:{ all -> 0x00b2 }
            int[] r1 = com.autonavi.minimap.base.overlay.RouteBoardOverlay.AnonymousClass3.$SwitchMap$com$autonavi$minimap$base$overlay$RouteBoardOverlay$GLBoardType     // Catch:{ all -> 0x00b2 }
            int r0 = r0.ordinal()     // Catch:{ all -> 0x00b2 }
            r0 = r1[r0]     // Catch:{ all -> 0x00b2 }
            switch(r0) {
                case 1: goto L_0x00a4;
                case 2: goto L_0x0073;
                case 3: goto L_0x003e;
                case 4: goto L_0x0032;
                case 5: goto L_0x0028;
                default: goto L_0x0026;
            }     // Catch:{ all -> 0x00b2 }
        L_0x0026:
            goto L_0x00b0
        L_0x0028:
            java.util.List<com.autonavi.minimap.base.overlay.RouteBoardOverlayItem> r0 = r3.mEdogItemList     // Catch:{ all -> 0x00b2 }
            r0.add(r4)     // Catch:{ all -> 0x00b2 }
            r3.addItem(r4)     // Catch:{ all -> 0x00b2 }
            goto L_0x00b0
        L_0x0032:
            com.autonavi.minimap.base.overlay.RouteBoardOverlayItem r0 = r3.mCongestionOverlayItem     // Catch:{ all -> 0x00b2 }
            r3.removeItem(r0)     // Catch:{ all -> 0x00b2 }
            r3.mCongestionOverlayItem = r4     // Catch:{ all -> 0x00b2 }
            r3.addItem(r4)     // Catch:{ all -> 0x00b2 }
            monitor-exit(r3)
            return
        L_0x003e:
            long r0 = r4.mPoiId     // Catch:{ all -> 0x00b2 }
            java.lang.String r0 = java.lang.Long.toString(r0)     // Catch:{ all -> 0x00b2 }
            java.util.Set<java.lang.String> r1 = r3.mLastKeys     // Catch:{ all -> 0x00b2 }
            r1.add(r0)     // Catch:{ all -> 0x00b2 }
            java.util.List<com.autonavi.minimap.base.overlay.RouteBoardOverlayItem> r0 = r3.mRouteBoardList     // Catch:{ all -> 0x00b2 }
            r0.add(r4)     // Catch:{ all -> 0x00b2 }
            java.util.List<java.lang.Integer> r0 = r3.mIndexIds     // Catch:{ all -> 0x00b2 }
            monitor-enter(r0)     // Catch:{ all -> 0x00b2 }
            java.util.List<java.lang.Integer> r1 = r3.mIndexIds     // Catch:{ all -> 0x0070 }
            int r2 = r4.mIndex     // Catch:{ all -> 0x0070 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0070 }
            boolean r1 = r1.contains(r2)     // Catch:{ all -> 0x0070 }
            if (r1 != 0) goto L_0x006d
            java.util.List<java.lang.Integer> r1 = r3.mIndexIds     // Catch:{ all -> 0x0070 }
            int r2 = r4.mIndex     // Catch:{ all -> 0x0070 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0070 }
            r1.add(r2)     // Catch:{ all -> 0x0070 }
            r3.addItemWithZ(r4)     // Catch:{ all -> 0x0070 }
        L_0x006d:
            monitor-exit(r0)     // Catch:{ all -> 0x0070 }
            monitor-exit(r3)
            return
        L_0x0070:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0070 }
            throw r4     // Catch:{ all -> 0x00b2 }
        L_0x0073:
            java.lang.String r0 = r4.mRouteName     // Catch:{ all -> 0x00b2 }
            java.util.Set<java.lang.String> r1 = r3.mLastKeys     // Catch:{ all -> 0x00b2 }
            r1.add(r0)     // Catch:{ all -> 0x00b2 }
            java.util.List<com.autonavi.minimap.base.overlay.RouteBoardOverlayItem> r0 = r3.mRouteBoardList     // Catch:{ all -> 0x00b2 }
            r0.add(r4)     // Catch:{ all -> 0x00b2 }
            java.util.List<java.lang.Integer> r0 = r3.mIndexIds     // Catch:{ all -> 0x00b2 }
            monitor-enter(r0)     // Catch:{ all -> 0x00b2 }
            java.util.List<java.lang.Integer> r1 = r3.mIndexIds     // Catch:{ all -> 0x00a1 }
            int r2 = r4.mIndex     // Catch:{ all -> 0x00a1 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x00a1 }
            boolean r1 = r1.contains(r2)     // Catch:{ all -> 0x00a1 }
            if (r1 != 0) goto L_0x009e
            java.util.List<java.lang.Integer> r1 = r3.mIndexIds     // Catch:{ all -> 0x00a1 }
            int r2 = r4.mIndex     // Catch:{ all -> 0x00a1 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x00a1 }
            r1.add(r2)     // Catch:{ all -> 0x00a1 }
            r3.addItemWithZ(r4)     // Catch:{ all -> 0x00a1 }
        L_0x009e:
            monitor-exit(r0)     // Catch:{ all -> 0x00a1 }
            monitor-exit(r3)
            return
        L_0x00a1:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a1 }
            throw r4     // Catch:{ all -> 0x00b2 }
        L_0x00a4:
            com.autonavi.minimap.base.overlay.RouteBoardOverlayItem r0 = r3.mPathOverlayItem     // Catch:{ all -> 0x00b2 }
            r3.removeItem(r0)     // Catch:{ all -> 0x00b2 }
            r3.mPathOverlayItem = r4     // Catch:{ all -> 0x00b2 }
            r3.addItem(r4)     // Catch:{ all -> 0x00b2 }
            monitor-exit(r3)
            return
        L_0x00b0:
            monitor-exit(r3)
            return
        L_0x00b2:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x00b5:
            monitor-exit(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.RouteBoardOverlay.addOverlayItem(com.autonavi.minimap.base.overlay.RouteBoardOverlayItem):void");
    }

    /* access modifiers changed from: protected */
    public void checkOverlayItemInBound(GLMapState gLMapState) {
        boolean z = true;
        boolean z2 = (this.mMapView.J() != 0.0f || this.m2dShown) && gLMapState.getMapZoomer() >= ((float) this.mMapLevel);
        if (this.mPathOverlayItem != null) {
            if (z2) {
                this.mPathOverlayItem.recalcBounds(gLMapState);
                isInValidBound(this.mPathOverlayItem, gLMapState);
            } else {
                setPointItemVisble((PointOverlayItem) this.mPathOverlayItem, false, false);
            }
        }
        if (this.mRouteBoardList != null && this.mRouteBoardList.size() > 0) {
            if (z2) {
                this.mInWinItemList.clear();
                for (RouteBoardOverlayItem next : this.mRouteBoardList) {
                    if (next != null) {
                        if (next.mRouteBoardType != GLBoardType.BOARD_TYPE_MARK_BUILD || (this.mMapView.ah() && this.mMapView.ag())) {
                            next.recalcBounds(gLMapState);
                            if (isInValidBound(next, gLMapState)) {
                                this.mInWinItemList.add(next);
                            }
                        } else {
                            setPointItemVisble((PointOverlayItem) next, false, false);
                        }
                    }
                }
            } else {
                clearOtherRouteBoards();
            }
        }
        if (this.mCongestionOverlayItem != null) {
            if (this.isEdogModel) {
                if (gLMapState.getMapZoomer() >= ((float) this.mCongestionMapLevel)) {
                    this.mCongestionOverlayItem.recalcBounds(gLMapState);
                    isInValidBound(this.mCongestionOverlayItem, gLMapState);
                } else {
                    setPointItemVisble((PointOverlayItem) this.mCongestionOverlayItem, false, false);
                }
            } else if ((this.mMapView.J() != 0.0f || this.mZeroCameraDegreeCongestionIsShown) && gLMapState.getMapZoomer() >= ((float) this.mCongestionMapLevel)) {
                this.mCongestionOverlayItem.recalcBounds(gLMapState);
                isInValidBound(this.mCongestionOverlayItem, gLMapState);
            } else {
                setPointItemVisble((PointOverlayItem) this.mCongestionOverlayItem, false, false);
            }
        }
        if (this.mEdogItemList != null && this.mEdogItemList.size() > 0) {
            if (gLMapState.getMapZoomer() < ((float) this.mEdogMapLevel)) {
                z = false;
            }
            for (RouteBoardOverlayItem next2 : this.mEdogItemList) {
                if (next2 != null) {
                    if (z) {
                        next2.recalcBounds(gLMapState);
                        isInValidBound(next2, gLMapState);
                    } else {
                        setPointItemVisble((PointOverlayItem) next2, false, false);
                    }
                }
            }
        }
    }

    private boolean isInValidBound(RouteBoardOverlayItem routeBoardOverlayItem, GLMapState gLMapState) {
        if (gLMapState == null || routeBoardOverlayItem == null) {
            return false;
        }
        if (!gLMapState.getMapViewBound().contains(routeBoardOverlayItem.mIconRect)) {
            routeBoardOverlayItem.mHideIcon = true;
            routeBoardOverlayItem.mHideBG = true;
            setPointItemVisble((PointOverlayItem) routeBoardOverlayItem, false, false);
            return false;
        } else if (this.mValidRect == null) {
            routeBoardOverlayItem.mHideBG = false;
            routeBoardOverlayItem.mHideIcon = false;
            setPointItemVisble((PointOverlayItem) routeBoardOverlayItem, true, true);
            return true;
        } else if (this.mValidRect.contains(routeBoardOverlayItem.mIconRect)) {
            routeBoardOverlayItem.mHideBG = false;
            routeBoardOverlayItem.mHideIcon = false;
            setPointItemVisble((PointOverlayItem) routeBoardOverlayItem, true, true);
            return true;
        } else {
            routeBoardOverlayItem.mHideBG = true;
            routeBoardOverlayItem.mHideIcon = true;
            setPointItemVisble((PointOverlayItem) routeBoardOverlayItem, false, false);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void checkOverlayCovered(GLMapState gLMapState) {
        if (this.mNaviDirectionTexture != null) {
            this.mNaviDirectRect = recalcTextureBounds(gLMapState, this.mNaviDirectionTexture, this.mCarPx, this.mCarPy, 4);
        }
        if (this.mEndTexture != null) {
            this.mEndPointRect = recalcTextureBounds(gLMapState, this.mEndTexture, this.mEndPx, this.mEndPy, 5);
        }
        if (this.mCheckedOverlayList != null && this.mCheckedOverlayList.size() > 0) {
            for (GLPointOverlay next : this.mCheckedOverlayList) {
                if (next != null) {
                    synchronized (next.getItemList()) {
                        for (ame next2 : next.getItemList()) {
                            if (next2 != null) {
                                for (int i = 0; i < this.mInWinItemList.size(); i++) {
                                    RouteBoardOverlayItem routeBoardOverlayItem = this.mInWinItemList.get(i);
                                    if (routeBoardOverlayItem != null && !routeBoardOverlayItem.mHideIcon && Rect.intersects(routeBoardOverlayItem.mIconRect, next2.a())) {
                                        routeBoardOverlayItem.mHideIcon = true;
                                        routeBoardOverlayItem.mHideBG = true;
                                        setPointItemVisble((PointOverlayItem) routeBoardOverlayItem, false, false);
                                    }
                                }
                                if (this.mPathOverlayItem != null && !this.mPathOverlayItem.mHideIcon && Rect.intersects(this.mPathOverlayItem.mIconRect, next2.a())) {
                                    this.mPathOverlayItem.mHideIcon = true;
                                    this.mPathOverlayItem.mHideBG = true;
                                    setPointItemVisble((PointOverlayItem) this.mPathOverlayItem, false, false);
                                }
                                if (this.mEndPointRect == null || !Rect.intersects(this.mEndPointRect, next2.a())) {
                                    ((GLPointOverlay) this.mGLOverlay).setPointItemVisble(next2, true, true);
                                } else {
                                    ((GLPointOverlay) this.mGLOverlay).setPointItemVisble(next2, false, false);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.mEdogItemList != null && this.mEdogItemList.size() > 0 && gLMapState.getMapZoomer() >= ((float) this.mEdogMapLevel)) {
            for (int size = this.mEdogItemList.size() - 1; size >= 0; size--) {
                RouteBoardOverlayItem routeBoardOverlayItem2 = this.mEdogItemList.get(size);
                if (routeBoardOverlayItem2 != null && !routeBoardOverlayItem2.mHideIcon && this.mPathOverlayItem != null && !this.mPathOverlayItem.mHideIcon && Rect.intersects(this.mPathOverlayItem.mIconRect, routeBoardOverlayItem2.mIconRect)) {
                    this.mPathOverlayItem.mHideIcon = true;
                    this.mPathOverlayItem.mHideBG = true;
                    setPointItemVisble((PointOverlayItem) this.mPathOverlayItem, false, false);
                }
                if (routeBoardOverlayItem2 != null && !routeBoardOverlayItem2.mHideIcon && this.mCongestionOverlayItem != null && !this.mCongestionOverlayItem.mHideIcon && Rect.intersects(this.mCongestionOverlayItem.mIconRect, routeBoardOverlayItem2.mIconRect)) {
                    this.mCongestionOverlayItem.mHideIcon = true;
                    this.mCongestionOverlayItem.mHideBG = true;
                    setPointItemVisble((PointOverlayItem) this.mCongestionOverlayItem, false, false);
                }
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    RouteBoardOverlayItem routeBoardOverlayItem3 = this.mEdogItemList.get(i2);
                    int width = (int) (((float) routeBoardOverlayItem2.mIconRect.width()) * 0.3f);
                    if (routeBoardOverlayItem3 != null && !routeBoardOverlayItem3.mHideIcon && Rect.intersects(shrinkRect(routeBoardOverlayItem2.mIconRect, width), shrinkRect(routeBoardOverlayItem3.mIconRect, width))) {
                        routeBoardOverlayItem3.mHideIcon = true;
                        routeBoardOverlayItem3.mHideBG = true;
                        setPointItemVisble((PointOverlayItem) routeBoardOverlayItem3, false, false);
                    }
                }
            }
        }
        if (this.mCongestionOverlayItem != null && !this.mCongestionOverlayItem.mHideIcon && gLMapState.getMapZoomer() >= ((float) this.mCongestionMapLevel) && this.mPathOverlayItem != null && !this.mPathOverlayItem.mHideIcon && Rect.intersects(this.mPathOverlayItem.mIconRect, this.mCongestionOverlayItem.mIconRect)) {
            this.mPathOverlayItem.mHideIcon = true;
            this.mPathOverlayItem.mHideBG = true;
            setPointItemVisble((PointOverlayItem) this.mPathOverlayItem, false, false);
        }
        if (this.mInWinItemList != null && this.mInWinItemList.size() > 0) {
            if ((this.mMapView.J() != 0.0f || this.m2dShown) && gLMapState.getMapZoomer() >= ((float) this.mMapLevel)) {
                for (int i3 = 0; i3 < this.mInWinItemList.size(); i3++) {
                    RouteBoardOverlayItem routeBoardOverlayItem4 = this.mInWinItemList.get(i3);
                    if (routeBoardOverlayItem4 != null && !routeBoardOverlayItem4.mHideIcon) {
                        for (int i4 = i3 + 1; i4 < this.mInWinItemList.size(); i4++) {
                            RouteBoardOverlayItem routeBoardOverlayItem5 = this.mInWinItemList.get(i4);
                            if (routeBoardOverlayItem5 != null && !routeBoardOverlayItem5.mHideIcon && Rect.intersects(routeBoardOverlayItem4.mIconRect, routeBoardOverlayItem5.mIconRect)) {
                                if (routeBoardOverlayItem4.mRouteBoardType != GLBoardType.BOARD_TYPE_MARK_BUILD || routeBoardOverlayItem4.mRank <= routeBoardOverlayItem5.mRank) {
                                    routeBoardOverlayItem4.mHideIcon = true;
                                    routeBoardOverlayItem4.mHideBG = true;
                                    setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                                } else {
                                    routeBoardOverlayItem5.mHideIcon = true;
                                    routeBoardOverlayItem5.mHideBG = true;
                                    setPointItemVisble((PointOverlayItem) routeBoardOverlayItem5, false, false);
                                }
                            }
                        }
                        if (!routeBoardOverlayItem4.mHideIcon) {
                            if (this.mPathOverlayItem != null && (Rect.intersects(routeBoardOverlayItem4.mIconRect, this.mPathOverlayItem.mIconRect) || routeBoardOverlayItem4.mRouteName.equals(this.mPathOverlayItem.mRouteName) || (!TextUtils.isEmpty(this.mNextName) && this.mNextName.equals(routeBoardOverlayItem4.mRouteName)))) {
                                routeBoardOverlayItem4.mHideIcon = true;
                                routeBoardOverlayItem4.mHideBG = true;
                                setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                            } else if (this.mNaviDirectionTexture != null && Rect.intersects(routeBoardOverlayItem4.mIconRect, this.mNaviDirectRect)) {
                                routeBoardOverlayItem4.mHideIcon = true;
                                routeBoardOverlayItem4.mHideBG = true;
                                setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                            } else if (this.mEndTexture == null || !Rect.intersects(routeBoardOverlayItem4.mIconRect, this.mEndPointRect)) {
                                if (this.mEdogItemList != null) {
                                    Iterator<RouteBoardOverlayItem> it = this.mEdogItemList.iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        RouteBoardOverlayItem next3 = it.next();
                                        if (next3 != null && !next3.mHideIcon && routeBoardOverlayItem4 != null && !routeBoardOverlayItem4.mHideIcon && Rect.intersects(routeBoardOverlayItem4.mIconRect, next3.mIconRect)) {
                                            routeBoardOverlayItem4.mHideIcon = true;
                                            routeBoardOverlayItem4.mHideBG = true;
                                            setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                                            break;
                                        }
                                    }
                                }
                                if (!routeBoardOverlayItem4.mHideIcon) {
                                    if (this.mCongestionOverlayItem != null && Rect.intersects(this.mCongestionOverlayItem.mIconRect, routeBoardOverlayItem4.mIconRect)) {
                                        routeBoardOverlayItem4.mHideIcon = true;
                                        routeBoardOverlayItem4.mHideBG = true;
                                        setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                                    } else if (this.mPathOverlayItem != null && Rect.intersects(this.mPathOverlayItem.mIconRect, routeBoardOverlayItem4.mIconRect)) {
                                        routeBoardOverlayItem4.mHideIcon = true;
                                        routeBoardOverlayItem4.mHideBG = true;
                                        setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                                    }
                                }
                            } else {
                                routeBoardOverlayItem4.mHideIcon = true;
                                routeBoardOverlayItem4.mHideBG = true;
                                setPointItemVisble((PointOverlayItem) routeBoardOverlayItem4, false, false);
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void removeLastItems() {
        PointF pointF;
        if (!(this.mValidRect == null || this.mRouteBoardList == null || this.mRouteBoardList.size() <= 0)) {
            for (int size = this.mRouteBoardList.size() - 1; size >= 0; size--) {
                RouteBoardOverlayItem routeBoardOverlayItem = this.mRouteBoardList.get(size);
                if (routeBoardOverlayItem != null) {
                    if (routeBoardOverlayItem.mRouteBoardType == GLBoardType.BOARD_TYPE_MARK_BUILD) {
                        pointF = this.mMapView.b(routeBoardOverlayItem.mGeoX, routeBoardOverlayItem.mGeoY, (int) routeBoardOverlayItem.mZ);
                    } else {
                        pointF = this.mMapView.f(routeBoardOverlayItem.mGeoX, routeBoardOverlayItem.mGeoY);
                    }
                    if (pointF != null && !this.mValidRect.contains((int) pointF.x, (int) pointF.y)) {
                        this.mRouteBoardList.remove(routeBoardOverlayItem);
                        if (routeBoardOverlayItem.mRouteBoardType == GLBoardType.BOARD_TYPE_MARK_BUILD) {
                            this.mLastKeys.remove(Long.toString(routeBoardOverlayItem.mPoiId));
                        } else {
                            this.mLastKeys.remove(routeBoardOverlayItem.mRouteName);
                        }
                        removeItem(routeBoardOverlayItem);
                        synchronized (this.mIndexIds) {
                            this.mIndexIds.remove(Integer.valueOf(routeBoardOverlayItem.mIndex));
                        }
                    }
                }
            }
        }
    }

    public int generateIndex(int i) {
        synchronized (this.mIndexIds) {
            if (this.mIndexIds.contains(Integer.valueOf(i))) {
                i = ((Integer) Collections.max(this.mIndexIds)).intValue() + 1;
            }
        }
        return i;
    }

    public synchronized void setValidRect(Rect rect) {
        this.mValidRect = rect;
    }

    public void setEdogMapLevel(int i) {
        this.mEdogMapLevel = i;
    }

    public void setCongestionMapLevel(int i) {
        this.mCongestionMapLevel = i;
    }

    public void setEdogModel(boolean z) {
        this.isEdogModel = z;
    }

    public void setPathNames(String str, String str2) {
        this.mCurName = str;
        this.mNextName = str2;
    }

    public synchronized void addCheckedOverlay(GLPointOverlay gLPointOverlay) {
        if (!this.mCheckedOverlayList.contains(gLPointOverlay)) {
            this.mCheckedOverlayList.add(gLPointOverlay);
        }
    }

    public synchronized void removeCheckedOverlay(GLPointOverlay gLPointOverlay) {
        if (this.mCheckedOverlayList.contains(gLPointOverlay)) {
            this.mCheckedOverlayList.remove(gLPointOverlay);
        }
    }

    public synchronized void clearPathRouteBoard() {
        if (this.mPathOverlayItem != null) {
            removeItem(this.mPathOverlayItem);
            this.mPathOverlayItem = null;
            refreshRender();
        }
    }

    public synchronized void clearOtherRouteBoards() {
        this.mLastKeys.clear();
        for (RouteBoardOverlayItem removeItem : this.mRouteBoardList) {
            removeItem(removeItem);
        }
        this.mRouteBoardList.clear();
        this.mInWinItemList.clear();
        synchronized (this.mIndexIds) {
            this.mIndexIds.clear();
        }
        refreshRender();
    }

    public synchronized void clearCongestionItem() {
        if (this.mCongestionOverlayItem != null) {
            removeItem(this.mCongestionOverlayItem);
            this.mCongestionOverlayItem = null;
            refreshRender();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0047, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void clearEdogItem(com.autonavi.common.model.GeoPoint r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.List<com.autonavi.minimap.base.overlay.RouteBoardOverlayItem> r0 = r4.mEdogItemList     // Catch:{ all -> 0x0048 }
            if (r0 == 0) goto L_0x0046
            if (r5 == 0) goto L_0x0046
            java.util.List<com.autonavi.minimap.base.overlay.RouteBoardOverlayItem> r0 = r4.mEdogItemList     // Catch:{ all -> 0x0048 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0048 }
        L_0x000d:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x0048 }
            if (r1 == 0) goto L_0x0046
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x0048 }
            com.autonavi.minimap.base.overlay.RouteBoardOverlayItem r1 = (com.autonavi.minimap.base.overlay.RouteBoardOverlayItem) r1     // Catch:{ all -> 0x0048 }
            if (r1 == 0) goto L_0x000d
            int r2 = r1.mGeoX     // Catch:{ all -> 0x0048 }
            int r3 = r5.x     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x000d
            int r2 = r1.mGeoY     // Catch:{ all -> 0x0048 }
            int r3 = r5.y     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x000d
            int r2 = r1.mGeo3Dx     // Catch:{ all -> 0x0048 }
            int r3 = r5.x3D     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x000d
            int r2 = r1.mGeo3Dy     // Catch:{ all -> 0x0048 }
            int r3 = r5.y3D     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x000d
            int r2 = r1.mGeo3Dz     // Catch:{ all -> 0x0048 }
            int r3 = r5.z3D     // Catch:{ all -> 0x0048 }
            if (r2 != r3) goto L_0x000d
            java.util.List<com.autonavi.minimap.base.overlay.RouteBoardOverlayItem> r5 = r4.mEdogItemList     // Catch:{ all -> 0x0048 }
            r5.remove(r1)     // Catch:{ all -> 0x0048 }
            r4.removeItem(r1)     // Catch:{ all -> 0x0048 }
            r4.refreshRender()     // Catch:{ all -> 0x0048 }
            monitor-exit(r4)
            return
        L_0x0046:
            monitor-exit(r4)
            return
        L_0x0048:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.base.overlay.RouteBoardOverlay.clearEdogItem(com.autonavi.common.model.GeoPoint):void");
    }

    public synchronized void clearAllEdogItem() {
        if (this.mEdogItemList != null && this.mEdogItemList.size() > 0) {
            for (RouteBoardOverlayItem removeItem : this.mEdogItemList) {
                removeItem(removeItem);
            }
            this.mEdogItemList.clear();
        }
    }

    public boolean removeAll() {
        clearPathRouteBoard();
        clearOtherRouteBoards();
        clearCongestionItem();
        return true;
    }

    public boolean hasOverlayItem(String str) {
        return this.mLastKeys.contains(str);
    }

    public void refreshRender() {
        if (this.mMapView != null) {
            this.mMapView.R();
        }
    }

    public void setMinDisplayLevel(int i) {
        MIN_SHOW_LEVEL = i;
        super.setMinDisplayLevel(i);
    }

    /* access modifiers changed from: protected */
    public Rect recalcTextureBounds(GLMapState gLMapState, amc amc, int i, int i2, int i3) {
        PointF pointF = new PointF();
        gLMapState.p20ToScreenPoint(i, i2, pointF);
        Rect rect = new Rect();
        ame.a(rect, (int) pointF.x, (int) pointF.y, amc, i3);
        return rect;
    }

    private Rect shrinkRect(Rect rect, int i) {
        Rect rect2 = new Rect(rect);
        rect2.top += i;
        rect2.bottom -= i;
        rect2.left += i;
        rect2.right -= i;
        return rect2;
    }

    public void addPathRouteBoardItem(GeoPoint geoPoint, String str, int i, int i2, int i3) {
        if (this.mGLOverlay != null) {
            this.mRouteNameSize = 18;
            final RouteBoardOverlayItem routeBoardOverlayItem = new RouteBoardOverlayItem(GLBoardType.BOARD_TYPE_PATH, geoPoint, str, i, i2, i3);
            this.mMapView.c((Runnable) new Runnable() {
                public void run() {
                    Marker access$600 = RouteBoardOverlay.this.createMarker(routeBoardOverlayItem);
                    routeBoardOverlayItem.mDefaultMarker = access$600;
                    if (access$600 != null) {
                        RouteBoardOverlay.this.addOverlayItem(routeBoardOverlayItem);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void addOtherRouteBoardItem(GeoPoint geoPoint, float f, String str, int i, int i2, long j, int i3) {
        int i4;
        GeoPoint geoPoint2 = geoPoint;
        int i5 = i3;
        if (this.mGLOverlay != null) {
            this.mRouteNameSize = 15;
            int i6 = i;
            if (i6 == GLBoardType.BOARD_TYPE_MARK_BUILD.ordinal()) {
                RouteBoardOverlayItem routeBoardOverlayItem = new RouteBoardOverlayItem(i6, GLBoardType.BOARD_TYPE_MARK_BUILD, geoPoint2, f, i2, str, generateIndex(i5 + 1001), R.drawable.a_building_mark, 1);
                routeBoardOverlayItem.mPoiId = j;
                routeBoardOverlayItem.mDefaultMarker = createMarker(routeBoardOverlayItem);
                addOverlayItem(routeBoardOverlayItem);
                return;
            }
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(this.mMapView.n());
            int i7 = this.mMapView.f(geoPoint2.x, geoPoint2.y).x <= this.mMapView.f(glGeoPoint2GeoPoint == null ? geoPoint2.x : glGeoPoint2GeoPoint.x, glGeoPoint2GeoPoint == null ? geoPoint2.y : glGeoPoint2GeoPoint.y).x ? 1 : 2;
            int i8 = R.drawable.a_board_day_left;
            if (this.isDay) {
                if (i7 == 1) {
                    i4 = R.drawable.a_board_day_left;
                } else {
                    i4 = R.drawable.a_board_day_right;
                }
            } else if (i7 == 1) {
                i4 = R.drawable.a_board_night_left;
            } else {
                i4 = R.drawable.a_board_night_right;
            }
            RouteBoardOverlayItem routeBoardOverlayItem2 = new RouteBoardOverlayItem(GLBoardType.BOARD_TYPE_BISIDE, geoPoint2, str, generateIndex(i5 + 1001), i4, i7);
            routeBoardOverlayItem2.mDefaultMarker = createMarker(routeBoardOverlayItem2);
            addOverlayItem(routeBoardOverlayItem2);
        }
    }

    /* access modifiers changed from: private */
    public Marker createMarker(RouteBoardOverlayItem routeBoardOverlayItem) {
        if (routeBoardOverlayItem.mRouteBoardType == GLBoardType.BOARD_TYPE_MARK_BUILD) {
            if (TextUtils.isEmpty(routeBoardOverlayItem.mRouteName)) {
                return null;
            }
            TextView textView = new TextView(this.mMapView.d());
            textView.setBackgroundResource(routeBoardOverlayItem.mIconId);
            textView.setGravity(17);
            textView.setTextColor(Color.parseColor("#818fb2"));
            textView.setTextSize(1, 15.0f);
            if (routeBoardOverlayItem.mRouteName.length() > 10) {
                textView.setMaxWidth(Math.round(textView.getPaint().measureText(routeBoardOverlayItem.mRouteName.substring(0, 11))) + 5);
            }
            textView.setMaxLines(2);
            textView.setEllipsize(TruncateAt.valueOf("END"));
            textView.setText(routeBoardOverlayItem.mRouteName);
            textView.setLayoutParams(new LayoutParams(-2, -2));
            return createMarker(routeBoardOverlayItem.mIndex, (View) textView, 5, 0.0f, 0.0f, false);
        } else if (TextUtils.isEmpty(routeBoardOverlayItem.mRouteName) || routeBoardOverlayItem.mRouteName.length() > 8) {
            return null;
        } else {
            LinearLayout linearLayout = new LinearLayout(this.mMapView.d());
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            linearLayout.setBackgroundResource(routeBoardOverlayItem.mIconId);
            linearLayout.setGravity(17);
            linearLayout.setOrientation(1);
            linearLayout.setLayoutParams(layoutParams);
            TextView textView2 = new TextView(this.mMapView.d());
            textView2.setGravity(17);
            textView2.setTextColor(this.mRouteNameColor);
            textView2.setTextSize(1, (float) this.mRouteNameSize);
            textView2.setText(routeBoardOverlayItem.mRouteName);
            textView2.setLayoutParams(new LayoutParams(-2, -2));
            linearLayout.addView(textView2);
            linearLayout.measure(0, 0);
            return createMarker(routeBoardOverlayItem.mIndex, (View) linearLayout, 9, getAnchorXRatio(routeBoardOverlayItem.mBoardStyle, linearLayout.getMeasuredWidth()), getAnchorYRatio(), false);
        }
    }

    /* access modifiers changed from: protected */
    public float getAnchorXRatio(int i, int i2) {
        if (i == 1) {
            return ((float) (i2 - agn.a(this.mMapView.d(), 12.0f))) / (((float) i2) * 1.0f);
        }
        if (i == 2) {
            return ((float) agn.a(this.mMapView.d(), 12.0f)) / (((float) i2) * 1.0f);
        }
        return 0.0f;
    }

    public void showBypassRouteBoard(boolean z) {
        this.isDay = !z;
        clearOtherRouteBoards();
    }

    public Marker createRouteBoardMarker(int i, int i2, float f, float f2, Bitmap bitmap) {
        if (i == -999 || this.mMapView == null) {
            return null;
        }
        this.mMapView.a(i, bitmap, i2, f, f2);
        return createMarker(i, bitmap, i2, f, f2, false);
    }

    public Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public void set2dShown(boolean z) {
        this.m2dShown = z;
    }

    public void setZeroCameraDegreeCongestionIsShown(boolean z) {
        this.mZeroCameraDegreeCongestionIsShown = z;
    }

    public synchronized void reculateOverlay(akq akq) {
        GLMapState mapState = akq.d.getMapState(this.mMapView.ad());
        if (mapState != null) {
            checkOverlayItemInBound(mapState);
            checkOverlayCovered(mapState);
        }
    }
}
