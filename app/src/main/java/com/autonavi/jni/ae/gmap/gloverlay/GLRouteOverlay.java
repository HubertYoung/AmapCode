package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.ae.gmap.gloverlay.GLRouteProperty;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.PolylineDrawType;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.RouteHighLightType;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;
import java.util.HashMap;

public class GLRouteOverlay extends GLLineOverlay {
    public static final int AMAPOVERLAY_ROUTE_CHARGE = 1;
    public static final int AMAPOVERLAY_ROUTE_CIMMUTE = 3;
    public static final int AMAPOVERLAY_ROUTE_LIMIT = 2;
    public static final int AMAPOVERLAY_ROUTE_NORMAL = 0;
    public static final int AMAPOVERLAY_ROUTE_WRONG = 4;

    private static native void nativeAddRouteItem(long j, int i, long[] jArr, int i2, long j2, int i3, int[] iArr);

    private static native void nativeAddRouteItem(long j, int i, long[] jArr, int i2, byte[] bArr, int[] iArr);

    /* access modifiers changed from: private */
    public static native void nativeAddRouteName(long j);

    private static native long nativeCreateRouteData(long j);

    /* access modifiers changed from: private */
    public static native long nativeCreateRouteParams();

    private static native void nativeDestoryRouteData(long j);

    /* access modifiers changed from: private */
    public static native void nativeDestoryRouteParams(long j);

    /* access modifiers changed from: private */
    public static native void nativeSetArrow3DMarker(long j, int i);

    private static native void nativeSetCar2DPosition(long j, int i, float f);

    private static native void nativeSetCar3DPosition(long j, int i, float f);

    private static native void nativeSetHighLightParam(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nativeSetHighLightType(long j, int i);

    /* access modifiers changed from: private */
    public static native void nativeSetItemshowInfor(long j, long j2);

    private static native void nativeSetRouteParamsBool(long j, boolean z, boolean z2, boolean z3, boolean z4, boolean z5);

    private static native void nativeSetRouteParamsCapTextureInfo(long j, float f, float f2, float f3, float f4);

    private static native void nativeSetRouteParamsSimple3DTextureInfo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nativeSetRouteParamsTextureInfo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nativeSetRouteParamsWAC(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

    private static native void nativeSetSelectStatus(long j, boolean z);

    /* access modifiers changed from: private */
    public static native void nativeSetWidthScale(long j, float f);

    private static native void nativesetShowNaviRouteNameCountMap(long j, int[] iArr, int[] iArr2);

    public GLRouteOverlay(int i, akq akq, int i2) {
        super(i, akq, i2, true);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_ROUTE.ordinal());
    }

    /* access modifiers changed from: private */
    public void setRouteColorWithParams(long j, GLRouteProperty gLRouteProperty) {
        GLRouteProperty gLRouteProperty2 = gLRouteProperty;
        if (gLRouteProperty2 != null) {
            nativeSetRouteParamsTextureInfo(j, gLRouteProperty2.mX1, gLRouteProperty2.mY1, gLRouteProperty2.mX2, gLRouteProperty2.mY2, gLRouteProperty2.mGLStart, gLRouteProperty2.mTextureLen);
            if (gLRouteProperty2.mSimple3DFillResId >= 0) {
                nativeSetRouteParamsSimple3DTextureInfo(j, gLRouteProperty2.mSimple3DX1, gLRouteProperty2.mSimple3DY1, gLRouteProperty2.mSimple3DX2, gLRouteProperty2.mSimple3DY2, gLRouteProperty2.mSimple3DGLStart, gLRouteProperty2.mSimple3DTextureLen);
            }
            if (gLRouteProperty2.isUseCap) {
                nativeSetRouteParamsCapTextureInfo(j, gLRouteProperty2.mCapX1, gLRouteProperty2.mCapY1, gLRouteProperty2.mCapX2, gLRouteProperty2.mCapY2);
            }
            if (gLRouteProperty2.mUnSelectFilledColor == 0) {
                gLRouteProperty2.mUnSelectFilledColor = gLRouteProperty2.mFilledColor;
            }
            if (gLRouteProperty2.mUnSelectBgColor == 0) {
                gLRouteProperty2.mUnSelectBgColor = gLRouteProperty2.mBgColor;
            }
            nativeSetRouteParamsWAC(j, gLRouteProperty2.euRouteTexture.value(), gLRouteProperty2.mLineWidth, gLRouteProperty2.mBorderLineWidth, gLRouteProperty2.mFilledColor, gLRouteProperty2.mBgColor, gLRouteProperty2.mFilledResId, gLRouteProperty2.mSimple3DFillResId, gLRouteProperty2.mBgResId, gLRouteProperty2.mUnSelectFilledColor, gLRouteProperty2.mUnSelectBgColor);
            nativeSetRouteParamsBool(j, gLRouteProperty2.isLineExtract, gLRouteProperty2.isUseColor, gLRouteProperty2.isUseCap, gLRouteProperty2.isCanCovered, gLRouteProperty2.mShowArrow);
        }
    }

    public void addRouteItem(int i, GLRouteProperty[] gLRoutePropertyArr, boolean z, long j, int i2, boolean z2) {
        addRouteItem(i, gLRoutePropertyArr, z, j, i2, null, z2);
    }

    public void addRouteItem(int i, GLRouteProperty[] gLRoutePropertyArr, boolean z, long j, int i2, int[] iArr, boolean z2) {
        GLRouteProperty[] gLRoutePropertyArr2 = gLRoutePropertyArr;
        if (this.mNativeInstance != 0 && gLRoutePropertyArr2 != null && (gLRoutePropertyArr2 == null || gLRoutePropertyArr2.length != 0)) {
            int length = gLRoutePropertyArr2.length;
            long[] jArr = new long[length];
            int i3 = z ? 1 : 0;
            for (int i4 = 0; i4 < length; i4++) {
                jArr[i4] = nativeCreateRouteParams();
                setRouteColorWithParams(jArr[i4], gLRoutePropertyArr2[i4]);
            }
            nativeAddRouteItem(this.mNativeInstance, i, jArr, i3, j, i2, iArr != null ? iArr : new int[]{-8947849, -8947849, -8947849});
            for (int i5 = 0; i5 < length; i5++) {
                nativeDestoryRouteParams(jArr[i5]);
            }
            if (z2) {
                this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                    public void run() {
                        GLRouteOverlay.this.mGLMapView.r(GLRouteOverlay.this.mGLMapView.d.getBelongToRenderDeviceId(GLRouteOverlay.this.mEngineID));
                    }
                });
            }
        }
    }

    public void addRouteItemWithoutName(int i, GLRouteProperty[] gLRoutePropertyArr, boolean z, long j, int i2, int[] iArr, boolean z2) {
        GLRouteProperty[] gLRoutePropertyArr2 = gLRoutePropertyArr;
        if (this.mNativeInstance != 0 && gLRoutePropertyArr2 != null && (gLRoutePropertyArr2 == null || gLRoutePropertyArr2.length != 0)) {
            int length = gLRoutePropertyArr2.length;
            long[] jArr = new long[length];
            int i3 = z ? 1 : 0;
            for (int i4 = 0; i4 < length; i4++) {
                jArr[i4] = nativeCreateRouteParams();
                setRouteColorWithParams(jArr[i4], gLRoutePropertyArr2[i4]);
            }
            nativeAddRouteItem(this.mNativeInstance, i, jArr, i3, j, i2, iArr);
            for (int i5 = 0; i5 < length; i5++) {
                nativeDestoryRouteParams(jArr[i5]);
            }
        }
    }

    public void addRouteItem(int i, GLRouteProperty[] gLRoutePropertyArr, int[] iArr, boolean z, byte[] bArr, boolean z2) {
        GLRouteProperty[] gLRoutePropertyArr2 = gLRoutePropertyArr;
        if (this.mNativeInstance != 0 && gLRoutePropertyArr2 != null && (gLRoutePropertyArr2 == null || gLRoutePropertyArr2.length != 0)) {
            int length = gLRoutePropertyArr2.length;
            long[] jArr = new long[length];
            int i2 = z ? 1 : 0;
            for (int i3 = 0; i3 < length; i3++) {
                jArr[i3] = nativeCreateRouteParams();
                setRouteColorWithParams(jArr[i3], gLRoutePropertyArr2[i3]);
            }
            nativeAddRouteItem(this.mNativeInstance, i, jArr, i2, bArr, iArr);
            for (int i4 = 0; i4 < length; i4++) {
                nativeDestoryRouteParams(jArr[i4]);
            }
            if (z2) {
                this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                    public void run() {
                        GLRouteOverlay.this.mGLMapView.r(GLRouteOverlay.this.mGLMapView.d.getBelongToRenderDeviceId(GLRouteOverlay.this.mEngineID));
                    }
                });
            }
        }
    }

    public void addRouteName(final boolean z) {
        if (this.mDrawType != PolylineDrawType.GROWN_ANIMATION) {
            this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                public void run() {
                    GLRouteOverlay.nativeAddRouteName(GLRouteOverlay.this.mNativeInstance);
                    if (z) {
                        GLRouteOverlay.this.mGLMapView.r(GLRouteOverlay.this.mGLMapView.d.getBelongToRenderDeviceId(GLRouteOverlay.this.mEngineID));
                    }
                }
            });
        }
    }

    public void onGrownAnimationStart() {
        super.onGrownAnimationStart();
    }

    public void onGrownAnimationEnd() {
        if (this.mDrawType == PolylineDrawType.GROWN_ANIMATION) {
            nativeAddRouteName(this.mNativeInstance);
        }
        super.onGrownAnimationEnd();
    }

    public void setCarPosition2D(int i, double d) {
        if (0 != this.mNativeInstance) {
            nativeSetCar2DPosition(this.mNativeInstance, i, (float) d);
        }
    }

    public void setCarPosition3D(int i, double d) {
        if (0 != this.mNativeInstance) {
            nativeSetCar3DPosition(this.mNativeInstance, i, (float) d);
        }
    }

    public void removeRouteName(final boolean z) {
        if (this.mNativeInstance != 0) {
            this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                public void run() {
                    GLRouteOverlay.this.mGLMapView.d.removeRouteName(GLRouteOverlay.this.mNativeInstance, GLRouteOverlay.this.mEngineID);
                    if (z) {
                        GLRouteOverlay.this.mGLMapView.r(GLRouteOverlay.this.mGLMapView.d.getBelongToRenderDeviceId(GLRouteOverlay.this.mEngineID));
                    }
                }
            });
        }
    }

    public void SetWidthScale(final float f) {
        if (this.mNativeInstance != 0) {
            this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                public void run() {
                    GLRouteOverlay.nativeSetWidthScale(GLRouteOverlay.this.mNativeInstance, f);
                }
            });
        }
    }

    public void setArrow3DMarker(final int i) {
        if (this.mNativeInstance != 0) {
            this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                public void run() {
                    GLRouteOverlay.nativeSetArrow3DMarker(GLRouteOverlay.this.mNativeInstance, i);
                }
            });
        }
    }

    public void setItemShowInfor(final GLRouteProperty gLRouteProperty) {
        if (this.mNativeInstance != 0) {
            this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
                public void run() {
                    long access$300 = GLRouteOverlay.nativeCreateRouteParams();
                    GLRouteOverlay.this.setRouteColorWithParams(access$300, gLRouteProperty);
                    GLRouteOverlay.nativeSetItemshowInfor(GLRouteOverlay.this.mNativeInstance, access$300);
                    GLRouteOverlay.nativeDestoryRouteParams(access$300);
                }
            });
        }
    }

    public void setHighLightType(RouteHighLightType routeHighLightType) {
        if (routeHighLightType != null) {
            nativeSetHighLightType(this.mNativeInstance, routeHighLightType.ordinal());
        }
    }

    public void setHighLightParam(int i, int i2, int i3, int i4, int i5) {
        nativeSetHighLightParam(this.mNativeInstance, i, i, i3, i4, i5);
    }

    public void setSelectStatus(boolean z) {
        nativeSetSelectStatus(this.mNativeInstance, z);
        this.mGLMapView.a(this.mGLMapView.C(this.mEngineID), (Runnable) new Runnable() {
            public void run() {
                GLRouteOverlay.this.mGLMapView.r(GLRouteOverlay.this.mGLMapView.d.getBelongToRenderDeviceId(GLRouteOverlay.this.mEngineID));
            }
        });
    }

    public void setShowNaviRouteNameCountMap(HashMap<Integer, Integer> hashMap) {
        if (hashMap != null && hashMap.size() > 0) {
            int[] iArr = new int[hashMap.size()];
            int[] iArr2 = new int[hashMap.size()];
            int i = 0;
            for (Integer next : hashMap.keySet()) {
                if (next != null) {
                    Integer num = hashMap.get(next);
                    if (num != null) {
                        iArr[i] = next.intValue();
                        iArr2[i] = num.intValue();
                        i++;
                    }
                }
            }
            if (i != 0) {
                nativesetShowNaviRouteNameCountMap(this.mNativeInstance, iArr, iArr2);
            }
        }
    }
}
