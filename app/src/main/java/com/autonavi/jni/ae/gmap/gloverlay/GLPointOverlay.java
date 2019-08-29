package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationEvent;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationListener;
import java.util.ArrayList;
import java.util.List;

public class GLPointOverlay extends GLOverlay {
    public List<aly> mClickObjs;
    protected List<ame> mList = new ArrayList();
    private boolean mOlnyRespToClickArea = false;
    private long mParamsInst = 0;
    private int mPointAnimation = 0;
    protected OverlayAnimationListener mPointAnimationListener = null;
    private boolean mShowFocusTop = false;

    public static class RotateMode {
        public static final int ROTATE_MODEZ_FOLLOW_SELF = 5;
        public static final int ROTATE_MODE_MAP = 1;
        public static final int ROTATE_MODE_NONE = 0;
        public static final int ROTATE_MODE_X = 2;
        public static final int ROTATE_MODE_XZ = 4;
        public static final int ROTATE_MODE_Z = 3;
    }

    protected static native boolean nativeAddItemMarkerInfo(long j, long j2, GLMapItemMarkerInfo gLMapItemMarkerInfo);

    protected static native void nativeAddMoveAnimationPointItem(long j, long j2, int[] iArr, int[] iArr2, int[] iArr3, float f, boolean z, float f2);

    protected static native long nativeAddPointItem(long j, long j2);

    protected static native void nativeClearFocus(long j);

    protected static native void nativeClearPointItemMarker(long j, long j2);

    protected static native long nativeCreatePointParam();

    protected static native void nativeDestoryPointParam(long j);

    protected static native void nativeFinishAnimationPointItem(long j, long j2);

    protected static native void nativeGetItemPosition(long j, long j2, GLGeoPoint gLGeoPoint);

    protected static native int nativeGetOverlayDrawOrder(long j);

    protected static native boolean nativeGetPointItemMarker(long j, long j2, int i, GLMapItemMarkerInfo gLMapItemMarkerInfo);

    protected static native int nativeGetPointItemMarkerCount(long j, long j2);

    protected static native void nativeModifyAnimationPointItem(long j, long j2, int i);

    protected static native void nativeRemovePointItem(long j, long j2);

    protected static native void nativeSetAnimationListener(long j, GLPointOverlay gLPointOverlay, OverlayAnimationListener overlayAnimationListener);

    protected static native void nativeSetCheckCovered(long j, boolean z);

    protected static native void nativeSetFocus(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    protected static native void nativeSetHideIconWhenCovered(long j, boolean z);

    protected static native void nativeSetOverlayDrawOrder(long j, int i);

    protected static native void nativeSetPoint3DParamPoints(long j, int i, int i2, float f);

    protected static native void nativeSetPointItemTexture(long j, long j2, int i, int i2);

    protected static native void nativeSetPointItemVisble(long j, long j2, boolean z, boolean z2);

    protected static native void nativeSetPointParamAngle(long j, float f);

    protected static native void nativeSetPointParamAnimation(long j, int i);

    protected static native void nativeSetPointParamInfo(long j, boolean z, int i, int i2);

    protected static native void nativeSetPointParamItemPriority(long j, int i);

    protected static native void nativeSetPointParamPoints(long j, int i, int i2);

    protected static native void nativeSetPointParamPointsWithZ(long j, int i, int i2, float f);

    protected static native void nativeSetPointParamRotateMode(long j, int i);

    protected static native void nativeSetPointParamTexture(long j, int i, int i2);

    protected static native void nativeSetReversed(long j, boolean z);

    protected static native void nativeSetShowFocusTop(long j, boolean z);

    protected static native boolean nativeUpdatePointItemMarker(long j, long j2, int i, GLMapItemMarkerInfo gLMapItemMarkerInfo);

    protected static native void nativeUpdatePointParam(long j, long j2, long j3);

    protected static native boolean nativeUpdatePointPositionAngle(long j, long j2, long j3);

    @Deprecated
    public void setAnimationListener(ame ame, OverlayAnimationListener overlayAnimationListener) {
    }

    public void setPerspective(boolean z) {
    }

    public List<ame> getItemList() {
        return this.mList;
    }

    public GLPointOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_POINT.ordinal());
        this.mParamsInst = nativeCreatePointParam();
        setOverlayDrawOrder(2);
    }

    public void releaseInstance() {
        if (this.mParamsInst != 0) {
            nativeDestoryPointParam(this.mParamsInst);
            this.mParamsInst = 0;
        }
        synchronized (this.mList) {
            this.mList.clear();
        }
        super.releaseInstance();
    }

    public void setClickList(List<aly> list) {
        this.mClickObjs = list;
    }

    public void setAnimatorType(int i) {
        this.mPointAnimation = i;
    }

    public void setHideIconWhenCovered(boolean z) {
        nativeSetHideIconWhenCovered(this.mNativeInstance, z);
    }

    public void setCheckCover(boolean z) {
        nativeSetCheckCovered(this.mNativeInstance, z);
    }

    public void showReversed(boolean z) {
        nativeSetReversed(this.mNativeInstance, z);
    }

    public void setAnimationListener(OverlayAnimationListener overlayAnimationListener) {
        this.mPointAnimationListener = overlayAnimationListener;
        nativeSetAnimationListener(this.mNativeInstance, this, overlayAnimationListener);
    }

    /* access modifiers changed from: protected */
    public void OnAnimationObserver(int i, int i2, long j) {
        if (this.mPointAnimationListener != null) {
            this.mPointAnimationListener.onProcessOverlayAnimationEvent(new OverlayAnimationEvent(i2, i, j));
        }
    }

    /* access modifiers changed from: protected */
    public ame getItemWithItemHashCode(long j) {
        synchronized (this.mList) {
            for (int size = this.mList.size() - 1; size >= 0; size--) {
                ame ame = this.mList.get(size);
                if (ame != null && ame.e == j) {
                    return ame;
                }
            }
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0052, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized long addPointOverlayItem(int r11, int r12, int r13) {
        /*
            r10 = this;
            monitor-enter(r10)
            akq r0 = r10.mGLMapView     // Catch:{ all -> 0x0057 }
            if (r0 == 0) goto L_0x0053
            akq r0 = r10.mGLMapView     // Catch:{ all -> 0x0057 }
            int r1 = r10.mEngineID     // Catch:{ all -> 0x0057 }
            boolean r0 = r0.a(r1)     // Catch:{ all -> 0x0057 }
            if (r0 != 0) goto L_0x0010
            goto L_0x0053
        L_0x0010:
            long r0 = r10.mParamsInst     // Catch:{ all -> 0x0057 }
            nativeSetPointParamPoints(r0, r11, r12)     // Catch:{ all -> 0x0057 }
            long r0 = r10.mParamsInst     // Catch:{ all -> 0x0057 }
            r2 = -1
            nativeSetPointParamTexture(r0, r13, r2)     // Catch:{ all -> 0x0057 }
            long r0 = r10.mParamsInst     // Catch:{ all -> 0x0057 }
            r2 = 1
            r3 = 0
            nativeSetPointParamInfo(r0, r2, r3, r3)     // Catch:{ all -> 0x0057 }
            long r0 = r10.mParamsInst     // Catch:{ all -> 0x0057 }
            int r2 = r10.mPointAnimation     // Catch:{ all -> 0x0057 }
            nativeSetPointParamAnimation(r0, r2)     // Catch:{ all -> 0x0057 }
            long r0 = r10.mParamsInst     // Catch:{ all -> 0x0057 }
            int r2 = r10.mItemPriority     // Catch:{ all -> 0x0057 }
            nativeSetPointParamItemPriority(r0, r2)     // Catch:{ all -> 0x0057 }
            long r0 = r10.mNativeInstance     // Catch:{ all -> 0x0057 }
            long r2 = r10.mParamsInst     // Catch:{ all -> 0x0057 }
            long r0 = nativeAddPointItem(r0, r2)     // Catch:{ all -> 0x0057 }
            r4 = r10
            r5 = r0
            r7 = r11
            r8 = r12
            r9 = r13
            r4.addPointOverlayItemToList(r5, r7, r8, r9)     // Catch:{ all -> 0x0057 }
            akq r11 = r10.mGLMapView     // Catch:{ all -> 0x0057 }
            if (r11 == 0) goto L_0x0051
            akq r11 = r10.mGLMapView     // Catch:{ all -> 0x0057 }
            int r12 = r10.mEngineID     // Catch:{ all -> 0x0057 }
            int r11 = r11.C(r12)     // Catch:{ all -> 0x0057 }
            akq r12 = r10.mGLMapView     // Catch:{ all -> 0x0057 }
            r12.p(r11)     // Catch:{ all -> 0x0057 }
        L_0x0051:
            monitor-exit(r10)
            return r0
        L_0x0053:
            r11 = 0
            monitor-exit(r10)
            return r11
        L_0x0057:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay.addPointOverlayItem(int, int, int):long");
    }

    public synchronized long addPointOverlayItemWithZ(int i, int i2, float f, int i3) {
        long nativeAddPointItem;
        try {
            nativeSetPointParamPointsWithZ(this.mParamsInst, i, i2, f);
            nativeSetPointParamTexture(this.mParamsInst, i3, -1);
            nativeSetPointParamInfo(this.mParamsInst, true, 0, 0);
            nativeSetPointParamAnimation(this.mParamsInst, this.mPointAnimation);
            nativeSetPointParamItemPriority(this.mParamsInst, this.mItemPriority);
            nativeAddPointItem = nativeAddPointItem(this.mNativeInstance, this.mParamsInst);
            addPointOverlayItemToList(nativeAddPointItem, i, i2, i3);
            if (this.mGLMapView != null) {
                this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
            }
        }
        return nativeAddPointItem;
    }

    public long addPointOverlayItemWithAngle(int i, int i2, int i3, int i4, int i5) {
        nativeSetPointParamPoints(this.mParamsInst, i, i2);
        nativeSetPointParamTexture(this.mParamsInst, i3, -1);
        nativeSetPointParamInfo(this.mParamsInst, false, 0, 0);
        nativeSetPointParamAnimation(this.mParamsInst, this.mPointAnimation);
        nativeSetPointParamAngle(this.mParamsInst, (float) i4);
        nativeSetPointParamRotateMode(this.mParamsInst, i5);
        nativeSetPointParamItemPriority(this.mParamsInst, this.mItemPriority);
        long nativeAddPointItem = nativeAddPointItem(this.mNativeInstance, this.mParamsInst);
        addPointOverlayItemToList(nativeAddPointItem, i, i2, i3);
        if (this.mGLMapView != null) {
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
        return nativeAddPointItem;
    }

    public long addPointOverlayItemWithAngle(int i, int i2, int i3, int i4, float f, int i5, int i6, int i7) {
        nativeSetPointParamPoints(this.mParamsInst, i, i2);
        nativeSetPoint3DParamPoints(this.mParamsInst, i3, i4, f);
        nativeSetPointParamTexture(this.mParamsInst, i5, -1);
        nativeSetPointParamInfo(this.mParamsInst, false, 0, 0);
        nativeSetPointParamAnimation(this.mParamsInst, this.mPointAnimation);
        nativeSetPointParamAngle(this.mParamsInst, (float) i6);
        nativeSetPointParamRotateMode(this.mParamsInst, i7);
        nativeSetPointParamItemPriority(this.mParamsInst, this.mItemPriority);
        long nativeAddPointItem = nativeAddPointItem(this.mNativeInstance, this.mParamsInst);
        addPointOverlayItemToList(nativeAddPointItem, i, i2, i5);
        if (this.mGLMapView != null) {
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
        return nativeAddPointItem;
    }

    public void upDatePointParam(long j, int i, int i2, int i3, int i4, float f, float f2) {
        nativeSetPointParamPoints(this.mParamsInst, i, i2);
        if (!(i3 == 0 || i4 == 0)) {
            nativeSetPoint3DParamPoints(this.mParamsInst, i3, i4, f);
        }
        nativeSetPointParamAngle(this.mParamsInst, f2);
        if (nativeUpdatePointPositionAngle(this.mNativeInstance, this.mParamsInst, j) && this.mGLMapView != null) {
            this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
        }
    }

    public GLGeoPoint getItemPosition(long j) {
        if (0 == j) {
            return null;
        }
        GLGeoPoint gLGeoPoint = new GLGeoPoint();
        nativeGetItemPosition(this.mNativeInstance, j, gLGeoPoint);
        return gLGeoPoint;
    }

    public long addPointOverlayItemWithBGMarker(int i, int i2, int i3, int i4) {
        nativeSetPointParamPoints(this.mParamsInst, i, i2);
        nativeSetPointParamTexture(this.mParamsInst, i3, i4);
        nativeSetPointParamInfo(this.mParamsInst, true, 64, 64);
        nativeSetPointParamAnimation(this.mParamsInst, this.mPointAnimation);
        nativeSetPointParamItemPriority(this.mParamsInst, this.mItemPriority);
        long nativeAddPointItem = nativeAddPointItem(this.mNativeInstance, this.mParamsInst);
        addPointOverlayItemToList(nativeAddPointItem, i, i2, i3);
        return nativeAddPointItem;
    }

    public void setPointOverlay3DPoint(long j, int i, int i2, float f) {
        nativeSetPoint3DParamPoints(this.mParamsInst, i, i2, f);
        nativeUpdatePointPositionAngle(this.mNativeInstance, this.mParamsInst, j);
    }

    public void setFocus(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        nativeSetFocus(this.mNativeInstance, i, i2, i3, i4, i5, i6, i7);
    }

    public void clearFocus() {
        if (0 != this.mNativeInstance) {
            nativeClearFocus(this.mNativeInstance);
        }
    }

    /* access modifiers changed from: protected */
    public void addPointOverlayItemToList(long j, int i, int i2, int i3) {
        ame ame = new ame(i, i2);
        ame.e = j;
        if (this.mGLMapView.s(this.mEngineID) != null) {
            ame.a(this.mGLMapView.s(this.mEngineID).getOverlayTextureItem(i3));
        }
        synchronized (this.mList) {
            this.mList.add(ame);
        }
    }

    public void removeAll() {
        super.removeAll();
        synchronized (this.mList) {
            this.mList.clear();
        }
    }

    public boolean onSingleTap(int i, int i2, int i3) {
        synchronized (this.mList) {
            for (int size = this.mList.size() - 1; size >= 0; size--) {
                ame ame = this.mList.get(size);
                if (ame != null) {
                    if (ame.a(i, this.mGLMapView, this.mClickObjs, i2, i3)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void setPointItemVisble(ame ame, boolean z, boolean z2) {
        nativeSetPointItemVisble(this.mNativeInstance, ame.e, z, z2);
    }

    public void setPointItemVisble(long j, boolean z, boolean z2) {
        nativeSetPointItemVisble(this.mNativeInstance, j, z, z2);
    }

    public void setPointItemTexture(long j, int i, int i2) {
        nativeSetPointItemTexture(this.mNativeInstance, j, i, i2);
    }

    public void removePointItem(ame ame) {
        if (ame != null) {
            synchronized (this.mList) {
                this.mList.remove(ame);
            }
            nativeRemovePointItem(this.mNativeInstance, ame.e);
        }
    }

    public void removeItem(int i) {
        ame ame;
        synchronized (this.mList) {
            if (i >= 0) {
                try {
                    if (i < this.mList.size()) {
                        ame = this.mList.get(i);
                        if (ame != null) {
                            this.mList.remove(ame);
                        }
                    }
                } finally {
                    while (true) {
                    }
                }
            }
            ame = null;
        }
        if (ame != null) {
            nativeRemovePointItem(this.mNativeInstance, ame.e);
        }
    }

    public void FinishAnimationPointItem(ame ame) {
        if (ame != null) {
            nativeFinishAnimationPointItem(this.mNativeInstance, ame.e);
        }
    }

    public void FinishAnimationPointItem(long j) {
        nativeFinishAnimationPointItem(this.mNativeInstance, j);
    }

    public void ModifyAnimationPointItem(ame ame, int i) {
        if (ame != null) {
            nativeModifyAnimationPointItem(this.mNativeInstance, ame.e, i);
        }
    }

    public void ModifyAnimationPointItem(long j, int i) {
        nativeModifyAnimationPointItem(this.mNativeInstance, j, i);
    }

    public void setShowFocusTop(boolean z) {
        nativeSetShowFocusTop(this.mNativeInstance, z);
        this.mShowFocusTop = z;
    }

    public boolean getShowFocusTop() {
        return this.mShowFocusTop;
    }

    public void setOverlayDrawOrder(int i) {
        nativeSetOverlayDrawOrder(this.mNativeInstance, i);
    }

    public int getOverlayDrawOrder() {
        return nativeGetOverlayDrawOrder(this.mNativeInstance);
    }

    public void setOnlyRespToClickArea(boolean z) {
        this.mOlnyRespToClickArea = z;
    }

    public boolean getOnlyRespToClickArea() {
        return this.mOlnyRespToClickArea;
    }

    public void addMoveAnimationPointItem(long j, GLGeoPoint[] gLGeoPointArr, float f, boolean z, double d) {
        GLGeoPoint[] gLGeoPointArr2 = gLGeoPointArr;
        if (0 != this.mNativeInstance && 0 != j && gLGeoPointArr2 != null) {
            int length = gLGeoPointArr2.length;
            if (length >= 2) {
                int[] iArr = new int[length];
                int[] iArr2 = new int[length];
                int[] iArr3 = new int[length];
                for (int i = 0; i < length; i++) {
                    if (gLGeoPointArr2[i] != null) {
                        iArr[i] = gLGeoPointArr2[i].x;
                        iArr2[i] = gLGeoPointArr2[i].y;
                        iArr3[i] = (int) gLGeoPointArr2[i].z;
                    }
                }
                nativeAddMoveAnimationPointItem(this.mNativeInstance, j, iArr, iArr2, iArr3, f, z, (float) d);
                if (this.mGLMapView != null) {
                    this.mGLMapView.p(this.mGLMapView.C(this.mEngineID));
                }
            }
        }
    }

    public boolean addPointItemMarker(long j, GLMapItemMarkerInfo gLMapItemMarkerInfo) {
        return nativeAddItemMarkerInfo(this.mNativeInstance, j, gLMapItemMarkerInfo);
    }

    public GLMapItemMarkerInfo getPointItemMarker(long j, int i) {
        GLMapItemMarkerInfo gLMapItemMarkerInfo = new GLMapItemMarkerInfo();
        if (nativeGetPointItemMarker(this.mNativeInstance, j, i, gLMapItemMarkerInfo)) {
            return gLMapItemMarkerInfo;
        }
        return null;
    }

    public int getPointItemMarkerCount(long j) {
        return nativeGetPointItemMarkerCount(this.mNativeInstance, j);
    }

    public void clearPointItemMarker(long j) {
        nativeClearPointItemMarker(this.mNativeInstance, j);
    }

    public boolean updatePointItemMarker(long j, GLMapItemMarkerInfo gLMapItemMarkerInfo, int i) {
        return nativeUpdatePointItemMarker(this.mNativeInstance, j, i, gLMapItemMarkerInfo);
    }
}
