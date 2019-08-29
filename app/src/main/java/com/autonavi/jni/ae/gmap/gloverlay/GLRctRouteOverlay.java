package com.autonavi.jni.ae.gmap.gloverlay;

import com.autonavi.jni.ae.data.DataService;
import com.autonavi.jni.ae.gmap.GLMapEngine;
import com.autonavi.jni.ae.gmap.utils.GLMapUtil;
import java.io.File;

public class GLRctRouteOverlay extends GLOverlay {

    public static class AmapNaviInfo {
        public int n3DEDLinkNumber;
        public int n3DSTLinkNumber;
        public long n64routeRemainDistance;
        public long n64routeRemainTime;
        public int ncurrentLinkNumber;
        public int ncurrentSegNumber;
        public int nmaneuverID;
        public int nsegmentLength;
        public int nsegmentRemainDistance;
        public int nsegmentRemainTime;
        public int ntype;
    }

    public static class AmapNaviTextures {
        public int mCarResID;
        public int mComPassResID;
        public int mShineResID;
    }

    public static class AmapRctPolyline {
        public int nCount;
        public int nIndex;
        public int[] xs;
        public int[] ys;
        public int[] zs;
    }

    public static class AmapRctRoutePath {
        public int n2DLinkNum;
        public int n3DLinkNum;
        public AmapRctPolyline[] p2DLink;
        public AmapRctPolyline[] p3DLink;
    }

    public static class AmapRctRouteProperty {
        public int mArrowTexId;
        public int mFlyLineTexId;
        public AmapRctRouteTextureInfo mFlylineArrowTexInfo = new AmapRctRouteTextureInfo();
        public AmapRctRouteTextureInfo mFlylineTexInfo = new AmapRctRouteTextureInfo();
        public float mSideWidth;
        public float mWidth;
    }

    public static class AmapRctRouteTextureInfo {
        public float glTexLen;
        public float glstart;
        public float x1;
        public float x2;
        public float y1;
        public float y2;
    }

    public static class AmapTmcProperty {
        public int mFillColor;
        public int mSideColor;
        public int mTmcStatus;
    }

    private static native void nativePauseNavi(long j);

    private static native void nativeResumeNavi(long j);

    private static native int nativeSetGpsPos(long j, int i, int i2, int i3, float f);

    private static native int nativeSetParam(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nativeSetRCTCompassMarkerTextures(long j, int i, int i2, int i3, int i4, int i5);

    private static native int nativeSetRCTFlyRoute(long j, byte[] bArr, int i, int[] iArr, float[] fArr);

    private static native int nativeSetRCTFlyTmc(long j, byte[] bArr, int i, int[] iArr);

    private static native void nativeSetRCTNaviTextures(long j, int[] iArr);

    private static native int nativeSetRCTStyleParam(long j, byte[] bArr);

    private static native void nativeStartNavi(long j);

    private static native void nativeStopNavi(long j);

    private static native int nativeUpdataCarPos(long j, float[] fArr);

    private static native void nativeUpdataNaviInfo(long j, int[] iArr);

    public int SetRCTFlyRoute(byte[] bArr, int i) {
        return 0;
    }

    public void clearFocus() {
    }

    public int getOverlayPriority() {
        return 0;
    }

    public void setMaxCountShown(int i) {
    }

    public void setMaxDisplayLevel(float f) {
    }

    public void setMinDisplayLevel(float f) {
    }

    public void setOverlayItemPriority(int i) {
    }

    public void setOverlayOnTop(boolean z) {
    }

    public void setOverlayPriority(int i) {
    }

    public GLRctRouteOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
        this.mNativeInstance = akq.d.createRealCityController(i);
    }

    public long getNativeInstatnce() {
        return this.mNativeInstance;
    }

    private int copy2Inter(int[] iArr, int i, AmapRctPolyline[] amapRctPolylineArr, int i2) {
        int i3 = i;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = amapRctPolylineArr[i4].nCount;
            int i6 = i3 + 1;
            iArr[i3] = i5;
            int i7 = i6 + 1;
            iArr[i6] = amapRctPolylineArr[i4].nIndex;
            System.arraycopy(amapRctPolylineArr[i4].xs, 0, iArr, i7, i5);
            int i8 = i7 + i5;
            System.arraycopy(amapRctPolylineArr[i4].ys, 0, iArr, i8, i5);
            int i9 = i8 + i5;
            System.arraycopy(amapRctPolylineArr[i4].zs, 0, iArr, i9, i5);
            i3 = i9 + i5;
        }
        return i3;
    }

    public int SetRCTFlyRoute(byte[] bArr, int i, AmapRctRouteProperty amapRctRouteProperty) {
        if (amapRctRouteProperty == null) {
            amapRctRouteProperty = new AmapRctRouteProperty();
        }
        return nativeSetRCTFlyRoute(this.mNativeInstance, bArr, i, new int[]{amapRctRouteProperty.mFlyLineTexId, amapRctRouteProperty.mArrowTexId}, new float[]{amapRctRouteProperty.mFlylineTexInfo.x1, amapRctRouteProperty.mFlylineTexInfo.y1, amapRctRouteProperty.mFlylineTexInfo.x2, amapRctRouteProperty.mFlylineTexInfo.y2, amapRctRouteProperty.mFlylineTexInfo.glstart, amapRctRouteProperty.mFlylineTexInfo.glTexLen, amapRctRouteProperty.mFlylineArrowTexInfo.x1, amapRctRouteProperty.mFlylineArrowTexInfo.y1, amapRctRouteProperty.mFlylineArrowTexInfo.x2, amapRctRouteProperty.mFlylineArrowTexInfo.y2, amapRctRouteProperty.mFlylineArrowTexInfo.glstart, amapRctRouteProperty.mFlylineArrowTexInfo.glTexLen, amapRctRouteProperty.mWidth, amapRctRouteProperty.mSideWidth});
    }

    public int SetRCTFlyTMC(byte[] bArr, int i, AmapTmcProperty[] amapTmcPropertyArr) {
        int[] iArr = new int[((amapTmcPropertyArr.length * 3) + 1)];
        iArr[0] = amapTmcPropertyArr.length;
        for (int i2 = 0; i2 < amapTmcPropertyArr.length; i2++) {
            int i3 = i2 * 3;
            iArr[i3 + 1] = amapTmcPropertyArr[i2].mTmcStatus;
            iArr[i3 + 2] = amapTmcPropertyArr[i2].mFillColor;
            iArr[i3 + 3] = amapTmcPropertyArr[i2].mSideColor;
        }
        return nativeSetRCTFlyTmc(this.mNativeInstance, bArr, i, iArr);
    }

    public int SetRCTFlyDirection(boolean z) {
        byte[] bArr;
        String str = z ? "3dlandscape.xml" : "3dportrait.xml";
        String dataPath = DataService.getInstance().getDataPathManager().getDataPath();
        StringBuilder sb = new StringBuilder();
        sb.append(dataPath);
        sb.append(str);
        File file = new File(sb.toString());
        if (file.exists()) {
            bArr = amz.a(file.getAbsolutePath());
        } else {
            bArr = GLMapUtil.decodeAssetResData(this.mGLMapView.c, "map_assets/".concat(String.valueOf(str)));
        }
        return nativeSetRCTStyleParam(this.mNativeInstance, bArr);
    }

    public int navistart() {
        nativeStartNavi(this.mNativeInstance);
        return 0;
    }

    public int navipause() {
        nativePauseNavi(this.mNativeInstance);
        return 0;
    }

    public int naviresume() {
        nativeResumeNavi(this.mNativeInstance);
        return 0;
    }

    public int navistop() {
        nativeStopNavi(this.mNativeInstance);
        return 0;
    }

    public int SetNaviMode(int i) {
        return nativeSetParam(this.mNativeInstance, 0, i, 0, 0, 0);
    }

    public int SetHeadingSyncToNormal(int i) {
        return nativeSetParam(this.mNativeInstance, 1, i, 0, 0, 0);
    }

    public void UpdataNaviInfo(AmapNaviInfo amapNaviInfo) {
        nativeUpdataNaviInfo(this.mNativeInstance, new int[]{amapNaviInfo.ntype, amapNaviInfo.nmaneuverID, (int) (amapNaviInfo.n64routeRemainDistance & -1), (int) ((amapNaviInfo.n64routeRemainDistance >> 32) & -1), (int) (amapNaviInfo.n64routeRemainTime & -1), (int) ((amapNaviInfo.n64routeRemainTime >> 32) & -1), amapNaviInfo.nsegmentRemainDistance, amapNaviInfo.nsegmentLength, amapNaviInfo.nsegmentRemainTime, amapNaviInfo.ncurrentSegNumber, amapNaviInfo.ncurrentLinkNumber, amapNaviInfo.n3DSTLinkNumber, amapNaviInfo.n3DEDLinkNumber});
    }

    public int SetGpsPos(int i, int i2, int i3, float f) {
        return nativeSetGpsPos(this.mNativeInstance, i, i2, i3, f);
    }

    public int UpdataCarPos(float[] fArr) {
        return nativeUpdataCarPos(this.mNativeInstance, fArr);
    }

    public void SetNaviTextures(AmapNaviTextures amapNaviTextures) {
        nativeSetRCTNaviTextures(this.mNativeInstance, new int[]{amapNaviTextures.mCarResID, amapNaviTextures.mComPassResID, amapNaviTextures.mShineResID});
    }

    public void SetCompassMarkerTextures(int i, int i2, int i3, int i4, int i5) {
        nativeSetRCTCompassMarkerTextures(this.mNativeInstance, i, i2, i3, i4, i5);
    }

    public void releaseInstance() {
        if (this.mNativeInstance != 0) {
            long j = this.mNativeInstance;
            this.mNativeInstance = 0;
            GLMapEngine.destoryRealCityController(this.mEngineID, j);
        }
    }

    public int getType() {
        return this.mNativeInstance == 0 ? -1 : 9;
    }

    public int getSubType() {
        return this.mNativeInstance == 0 ? -1 : 0;
    }

    public void removeItem(int i) {
        if (this.mNativeInstance != 0) {
        }
    }

    public void removeAll() {
        if (!(this.mNativeInstance == 0 || this.mGLMapView == null)) {
            this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
        }
    }

    public int getSize() {
        return this.mNativeInstance == 0 ? 0 : 0;
    }

    public void setVisible(boolean z) {
        if (this.mNativeInstance != 0) {
            this.mGLMapView.r(this.mGLMapView.d.getBelongToRenderDeviceId(this.mEngineID));
        }
    }

    public boolean isVisible() {
        return this.mNativeInstance != 0;
    }

    public void setClickable(boolean z) {
        if (this.mNativeInstance != 0) {
        }
    }

    public boolean isClickable() {
        return this.mNativeInstance != 0;
    }

    public boolean getIsInBundle() {
        return this.mIsInBundle;
    }
}
