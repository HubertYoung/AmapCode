package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.PolylineDrawType;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.EAMapOverlayTpye;

public class GLLineOverlay extends GLOverlay {
    protected PolylineDrawType mDrawType = PolylineDrawType.GREY_OVER_VIEW;
    Listener mListener;

    public interface Listener {
        void onGrownAnimationEnd();

        void onGrownAnimationStart();
    }

    public static class TextureGenedInfo {
        public boolean m_genMimps = false;
        public boolean m_isPreMulAlpha = true;
        public boolean m_isRepeat = false;
    }

    private static native void nativeAddPolylineItem(long j, long j2);

    private static native boolean nativeCheckIntersectionRect(long j, int i, int i2, int i3, int i4, int i5, int i6);

    protected static native long nativeCreatePolyLineParams();

    protected static native void nativeDestoryPolyLineParams(long j);

    private static native void nativeSetDrawType(long j, int i);

    private static native void nativeSetFilterZoomLevel(long j, float f, float f2);

    private static native void nativeSetGrownAnimation(long j, int i, GLLineOverlay gLLineOverlay);

    private static native void nativeSetPolyLineParamsBool(long j, boolean z, boolean z2, boolean z3, boolean z4);

    private static native void nativeSetPolyLineParamsCapTextureInfo(long j, float f, float f2, float f3, float f4);

    protected static native void nativeSetPolyLineParamsItemPriority(long j, int i);

    private static native void nativeSetPolyLineParamsPoints(long j, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4);

    private static native void nativeSetPolyLineParamsPointsColor(long j, boolean z, int[] iArr);

    private static native void nativeSetPolyLineParamsPointsWithLonLat(long j, int[] iArr);

    private static native void nativeSetPolyLineParamsTextureInfo(long j, float f, float f2, float f3, float f4, float f5, float f6);

    private static native void nativeSetPolyLineParamsWAC(long j, int i, int i2, int i3, int i4, int i5, int i6);

    public GLLineOverlay(int i, akq akq, int i2, boolean z) {
        super(i, akq, i2);
    }

    public GLLineOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createOverlay(i, EAMapOverlayTpye.AMAPOVERLAY_POLYLINE.ordinal());
    }

    private void doAddLineItemWithLonLat(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3) {
        long nativeCreatePolyLineParams = nativeCreatePolyLineParams();
        nativeSetPolyLineParamsPointsWithLonLat(nativeCreatePolyLineParams, iArr);
        setLineColorWithParams(nativeCreatePolyLineParams, i, i2, i3, i4, i5, i6, z, z2);
        nativeSetPolyLineParamsItemPriority(nativeCreatePolyLineParams, this.mItemPriority);
        nativeAddPolylineItem(this.mNativeInstance, nativeCreatePolyLineParams);
        nativeDestoryPolyLineParams(nativeCreatePolyLineParams);
        if (true == z3) {
            this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
        }
    }

    public void addLineItemWithLonLat(int[] iArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        doAddLineItemWithLonLat(iArr, i, i2, i3, i4, i5, i6, z, z2, true);
    }

    public void addLineItemWithLonLat(int[] iArr, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3) {
        doAddLineItemWithLonLat(iArr, i, i2, i2, i3, i4, i5, z, z2, z3);
    }

    private void doAddLineItem(GLGeoPoint[] gLGeoPointArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3) {
        GLGeoPoint[] gLGeoPointArr2 = gLGeoPointArr;
        if (this.mNativeInstance != 0) {
            int length = gLGeoPointArr2.length;
            if (length >= 2) {
                int[] iArr = new int[length];
                int[] iArr2 = new int[length];
                for (int i7 = 0; i7 < length; i7++) {
                    if (gLGeoPointArr2[i7] != null) {
                        iArr[i7] = gLGeoPointArr2[i7].x;
                        iArr2[i7] = gLGeoPointArr2[i7].y;
                    }
                }
                long nativeCreatePolyLineParams = nativeCreatePolyLineParams();
                nativeSetPolyLineParamsPoints(nativeCreatePolyLineParams, iArr, iArr2, null, null);
                setLineColorWithParams(nativeCreatePolyLineParams, i, i2, i3, i4, i5, i6, z, z2);
                nativeSetPolyLineParamsItemPriority(nativeCreatePolyLineParams, this.mItemPriority);
                nativeAddPolylineItem(this.mNativeInstance, nativeCreatePolyLineParams);
                nativeDestoryPolyLineParams(nativeCreatePolyLineParams);
                if (true == z3) {
                    this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
                }
            }
        }
    }

    public void addLineItem(GLGeoPoint[] gLGeoPointArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        doAddLineItem(gLGeoPointArr, i, i2, i3, i4, i5, i6, z, z2, true);
    }

    public void addLineItem(GLGeoPoint[] gLGeoPointArr, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        doAddLineItem(gLGeoPointArr, i, i2, i2, i3, i4, i5, z, z2, true);
    }

    public void addLineItem(GLGeoPoint[] gLGeoPointArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3) {
        doAddLineItem(gLGeoPointArr, i, i2, i3, i4, i5, i6, z, z2, z3);
    }

    public void addLineItem(GLGeoPoint[] gLGeoPointArr, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3) {
        doAddLineItem(gLGeoPointArr, i, i2, i2, i3, i4, i5, z, z2, z3);
    }

    /* access modifiers changed from: protected */
    public void setLineColorWithParams(long j, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        int i7 = i4;
        float f = (float) (i2 * 4);
        float f2 = (float) (i3 * 4);
        if (i7 == 3000 || i7 == 3050) {
            nativeSetPolyLineParamsTextureInfo(j, 0.05f, 0.5f, 0.95f, 0.5f, 0.0f, 32.0f);
            nativeSetPolyLineParamsCapTextureInfo(j, 0.05f, 0.5f, 0.95f, 0.75f);
            nativeSetPolyLineParamsWAC(j, (int) f, (int) f2, i, i6, i7, i5);
            nativeSetPolyLineParamsBool(j, z, true, true, z2);
        } else if (i7 < 3000) {
            nativeSetPolyLineParamsTextureInfo(j, 0.0f, 0.5f, 1.0f, 0.5f, 0.0f, 32.0f);
            nativeSetPolyLineParamsCapTextureInfo(j, 0.0f, 0.5f, 1.0f, 0.75f);
            nativeSetPolyLineParamsWAC(j, (int) f, (int) f2, i, i6, i7, i5);
            nativeSetPolyLineParamsBool(j, z, false, true, z2);
        } else if (i7 < 3003) {
            nativeSetPolyLineParamsTextureInfo(j, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, 64.0f);
            nativeSetPolyLineParamsCapTextureInfo(j, 0.5f, 0.25f, 1.0f, 0.6f);
            nativeSetPolyLineParamsWAC(j, (int) f, (int) f2, i, i6, i7, i5);
            nativeSetPolyLineParamsBool(j, z, false, true, z2);
        } else if (i7 == 3003) {
            nativeSetPolyLineParamsTextureInfo(j, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 64.0f);
            nativeSetPolyLineParamsWAC(j, (int) f, (int) f2, i, i6, i7, i5);
            nativeSetPolyLineParamsBool(j, z, false, false, z2);
        } else if (3010 <= i7 || i7 <= 3003) {
            if (i7 >= 3010) {
                nativeSetPolyLineParamsTextureInfo(j, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 32.0f);
                nativeSetPolyLineParamsWAC(j, (int) f, (int) f2, i, i6, i7, i5);
                nativeSetPolyLineParamsBool(j, z, true, false, z2);
            }
        } else {
            nativeSetPolyLineParamsTextureInfo(j, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 64.0f);
            nativeSetPolyLineParamsWAC(j, (int) f, (int) f2, i, i6, i7, i5);
            nativeSetPolyLineParamsBool(j, z, false, false, z2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0087  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addLineItem(defpackage.ama r9) {
        /*
            r8 = this;
            long r0 = r8.mNativeInstance
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x0097
            if (r9 == 0) goto L_0x0097
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r0 = r9.a
            if (r0 != 0) goto L_0x0010
            goto L_0x0097
        L_0x0010:
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r0 = r9.a
            int r0 = r0.length
            r1 = 2
            if (r0 >= r1) goto L_0x0017
            return
        L_0x0017:
            int[] r4 = new int[r0]
            int[] r5 = new int[r0]
            r1 = 0
            r2 = 0
        L_0x001d:
            if (r2 >= r0) goto L_0x0038
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r3 = r9.a
            r3 = r3[r2]
            if (r3 == 0) goto L_0x0035
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r3 = r9.a
            r3 = r3[r2]
            int r3 = r3.x
            r4[r2] = r3
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r3 = r9.a
            r3 = r3[r2]
            int r3 = r3.y
            r5[r2] = r3
        L_0x0035:
            int r2 = r2 + 1
            goto L_0x001d
        L_0x0038:
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r0 = r9.b
            r2 = 0
            if (r0 == 0) goto L_0x005e
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r0 = r9.b
            int r0 = r0.length
            if (r0 <= 0) goto L_0x005e
            int[] r2 = new int[r0]
            int[] r3 = new int[r0]
        L_0x0046:
            if (r1 >= r0) goto L_0x005b
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r6 = r9.b
            r6 = r6[r1]
            int r6 = r6.x
            r2[r1] = r6
            com.autonavi.ae.gmap.glinterface.GLGeoPoint[] r6 = r9.b
            r6 = r6[r1]
            int r6 = r6.y
            r3[r1] = r6
            int r1 = r1 + 1
            goto L_0x0046
        L_0x005b:
            r6 = r2
            r7 = r3
            goto L_0x0060
        L_0x005e:
            r6 = r2
            r7 = r6
        L_0x0060:
            long r0 = nativeCreatePolyLineParams()
            r2 = r0
            nativeSetPolyLineParamsPoints(r2, r4, r5, r6, r7)
            boolean r2 = r9.A
            if (r2 == 0) goto L_0x0073
            boolean r2 = r9.A
            int[] r3 = r9.e
            nativeSetPolyLineParamsPointsColor(r0, r2, r3)
        L_0x0073:
            r8.setLineColorWithParams(r0, r9)
            int r2 = r8.mItemPriority
            nativeSetPolyLineParamsItemPriority(r0, r2)
            long r2 = r8.mNativeInstance
            nativeAddPolylineItem(r2, r0)
            nativeDestoryPolyLineParams(r0)
            boolean r9 = r9.z
            if (r9 == 0) goto L_0x0096
            akq r9 = r8.mGLMapView
            com.autonavi.jni.ae.gmap.GLMapEngine r9 = r9.d
            int r0 = r8.mEngineID
            int r9 = r9.getBelongToRenderDeviceId(r0)
            akq r0 = r8.mGLMapView
            r0.r(r9)
        L_0x0096:
            return
        L_0x0097:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay.addLineItem(ama):void");
    }

    /* access modifiers changed from: protected */
    public void setLineColorWithParams(long j, ama ama) {
        ama ama2 = ama;
        if (ama2 != null) {
            nativeSetPolyLineParamsTextureInfo(j, ama2.l, ama2.m, ama2.n, ama2.o, ama2.p, ama2.q);
            if (ama2.y) {
                nativeSetPolyLineParamsCapTextureInfo(j, ama2.r, ama2.s, ama2.t, ama2.u);
            }
            nativeSetPolyLineParamsWAC(j, ama2.j * 4, ama2.k * 4, ama2.g, ama2.i, ama2.f, ama2.h);
            nativeSetPolyLineParamsBool(j, ama2.v, ama2.x, ama2.y, ama2.w);
        }
    }

    public static TextureGenedInfo CheckRepeat(int i) {
        TextureGenedInfo textureGenedInfo = new TextureGenedInfo();
        if (i == 3000) {
            textureGenedInfo.m_genMimps = true;
            textureGenedInfo.m_isRepeat = false;
        } else if (i < 3000) {
            textureGenedInfo.m_genMimps = false;
            textureGenedInfo.m_isRepeat = false;
        } else if (i < 3003) {
            textureGenedInfo.m_genMimps = false;
            textureGenedInfo.m_isRepeat = true;
        } else if (i == 3003) {
            textureGenedInfo.m_genMimps = false;
            textureGenedInfo.m_isRepeat = true;
        } else if (3010 > i && i > 3003) {
            textureGenedInfo.m_genMimps = false;
            textureGenedInfo.m_isRepeat = true;
        } else if (i >= 3010 && i < 10000) {
            textureGenedInfo.m_genMimps = false;
            textureGenedInfo.m_isRepeat = true;
        } else if (i >= 10000) {
            textureGenedInfo.m_genMimps = false;
            textureGenedInfo.m_isRepeat = false;
        }
        return textureGenedInfo;
    }

    public boolean checkIntersectionRect(alw alw) {
        if (this.mNativeInstance == 0) {
            return false;
        }
        return nativeCheckIntersectionRect(this.mNativeInstance, alw.d, alw.a, alw.b, alw.c, alw.e, alw.f);
    }

    public void setGrownAnimation(int i) {
        if (this.mNativeInstance != 0) {
            nativeSetGrownAnimation(this.mNativeInstance, i, this);
        }
    }

    public void setDrawType(PolylineDrawType polylineDrawType) {
        this.mDrawType = polylineDrawType;
        if (this.mNativeInstance != 0) {
            nativeSetDrawType(this.mNativeInstance, polylineDrawType.ordinal());
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setFilterZoomLevel(float f, float f2) {
        if (this.mNativeInstance != 0) {
            nativeSetFilterZoomLevel(this.mNativeInstance, f, f2);
        }
    }

    public void onGrownAnimationStart() {
        if (this.mListener != null) {
            this.mListener.onGrownAnimationStart();
        }
    }

    public void onGrownAnimationEnd() {
        if (this.mListener != null) {
            this.mListener.onGrownAnimationEnd();
        }
    }
}
