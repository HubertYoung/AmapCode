package com.autonavi.jni.ae.gmap;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.media.Image;
import android.media.Image.Plane;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Surface;

import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.ae.gmap.utils.GLMapStaticValue;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.MapPreLoadCommandParamType;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.MapPreLoadType;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherAnimationState;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType;
import com.autonavi.jni.ae.data.DataService;
import com.autonavi.jni.ae.gmap.gesture.EAMapPlatformGestureInfo;
import com.autonavi.jni.ae.gmap.glinterface.GLDeviceAttribute;
import com.autonavi.jni.ae.gmap.glinterface.GLSurfaceAttribute;
import com.autonavi.jni.ae.gmap.glinterface.MapEngineInitParam;
import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay.TextureGenedInfo;
import com.autonavi.jni.ae.gmap.gloverlay.GLNaviOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;
import com.autonavi.jni.ae.gmap.maploader.MapLoader;
import com.autonavi.jni.ae.gmap.maploader.MapLoader.ADataRequestParam;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import defpackage.akq;
import defpackage.akw;
import defpackage.akx;
import defpackage.ala;
import defpackage.alb;
import defpackage.alc;
import defpackage.ale;
import defpackage.aln;
import defpackage.alo;
import defpackage.alp;
import defpackage.alt;
import defpackage.alu;
import defpackage.alv;
import defpackage.amf;
import defpackage.amh;
import defpackage.ami;
import defpackage.amk;
import defpackage.amo;
import defpackage.amu;
import defpackage.amv;
import defpackage.amw;
import defpackage.amx;
import defpackage.amy;
import defpackage.ana;
import defpackage.anc;
import defpackage.and;

public class GLMapEngine {
    public static final int AMAPANIMATION_FLING = 4;
    public static final int AMAPANIMATION_GROUP = 65535;
    public static final int AMAPANIMATION_PIVOTZOOM = 1;
    public static final int AMAPANIMATION_ROTATEZOOM = 27;
    private static final int AMAPOVERLAY_WATERWAVE = 11;
    public static final int COREANIMATION_NONE = 0;
    public static final int COREANIMATION_NORMALPITCH = 32;
    public static final int COREANIMATION_NORMALROLL = 16;
    public static final int COREANIMATION_NORMALSCALE = 8;
    public static final int COREANIMATION_PIVOTROLL = 2;
    public static final int COREANIMATION_PIVOTSCALE = 1;
    public static final int COREANIMATION_PROJECTCENTER = 64;
    public static final int COREANIMATION_TRANSLATION = 4;
    private static final int LONG_LONG_TICK_COUNT = 3;
    private static final int LONG_TICK_COUNT = 2;
    public static final String MAP_DEVICE_PROFILE_NAME = "/deviceprofile.data";
    public static final String MAP_MAP_ASSETS_NAME = "map_assets";
    private static final int NORMAL_TICK_COUNT = 1;
    public static final int TAP_LINES = 2;
    public static final int TAP_POINTS = 1;
    private String TAG = "GLMapEngine";
    private amo mBoardDataListener = null;
    private HashMap<Integer, Integer> mGestureCenterTypes = new HashMap<>();
    private boolean mInUserAction;
    /* access modifiers changed from: private */
    public a mIndoorBuildingListener = null;
    /* access modifiers changed from: private */
    public ami mLastIndoorBuilding = null;
    private NaviMessage mLastNaviMsg = new NaviMessage();
    private amu mMapHeatListener = null;
    public amk mMapListener = null;
    protected ale mMapcoreListener = null;
    /* access modifiers changed from: private */
    public long mNativeMapengineInstance = 0;
    /* access modifiers changed from: private */
    public amw mScenicListener = null;
    private SparseArray< anc > mStateSparseArray = new SparseArray<>();
    private and mTextTextureGenerator = null;
    private Object mut_lock = new Object();

    static class NaviMessage {
        int bearing = 0;
        float carpitch = 0.0f;
        GLGeoPoint center = new GLGeoPoint();
        int headerAngle = 0;
        int height = 0;
        int mapAngle = 0;
        float mapLevel = 0.0f;
        GLGeoPoint navicar = new GLGeoPoint();
        GLGeoPoint navicar3d = new GLGeoPoint();

        NaviMessage() {
        }
    }

    public static native void nativeAddCustomStyle(int i, long j, CustomStyleParam[] customStyleParamArr, boolean z);

    private static native void nativeAddGeoAndScreenCenterGroupAnimation(int i, long j, int i2, int i3, int i4, int i5, int i6, boolean z);

    private static native void nativeAddGroupAnimation(int i, long j, int i2, int i3, float f, float f2, float f3, int i4, int i5, boolean z);

    private static native void nativeAddLabels3rd(int i, long j, int i2, Label3rd[] label3rdArr, boolean z);

    private static native void nativeAddMapGestureMsg(int i, long j, int i2, float f, float f2, float f3);

    private static native void nativeAddMapNavi3DMsg(int i, long j, long j2, long j3, Point point, Point point2, Point point3, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8);

    private static native void nativeAddMapNaviMsg(int i, long j, long j2, long j3, Point point, Point point2, float f, float f2, float f3, float f4, float f5);

    private static native void nativeAddMoveAnimation(int i, long j, int i2, int i3, int i4);

    private static native boolean nativeAddOverlayTexture(int i, long j, int i2, int i3, float f, float f2, Bitmap bitmap, boolean z, boolean z2, boolean z3);

    private static native boolean nativeAddOverlayTextureByPng(int i, long j, int i2, int i3, float f, float f2, byte[] bArr, boolean z, boolean z2, int[] iArr, boolean z3);

    private static native void nativeAddPivotZoomAnimation(int i, long j, int i2, float f, int i3, int i4);

    private static native void nativeAddPoiFilter(int i, long j, int i2, int i3, int i4, float f, float f2, float f3, float f4, String str, int i5);

    private static native void nativeAddScreenShotBitmapCache(long j, int i, Bitmap bitmap);

    private static native void nativeAddZoomRotateAnimation(int i, long j, int i2, float f, float f2, int i3, int i4);

    private static native void nativeAppendOpenLayer(int i, long j, byte[] bArr);

    private static native void nativeAttachSurfaceToRenderDevice(long j, int i, Surface surface, GLSurfaceAttribute gLSurfaceAttribute);

    private static native void nativeAttachSurfaceToRenderDeviceEx(long j, int i, long j2, GLSurfaceAttribute gLSurfaceAttribute);

    private static native boolean nativeBindMapEngineToRenderDevice(long j, int i, int i2);

    private static native float nativeCalculateMapZoomer(int i, long j, int i2, int i3, int i4, int i5, int i6, int i7);

    private static native boolean nativeCanStopRenderMap(int i, long j);

    private static native void nativeChangeSurface(int i, long j, int i2, int i3, int i4, int i5, int i6, int i7, GLMapEngine gLMapEngine);

    private static native boolean nativeCleanOverlayTexture(int i, long j, int i2);

    private static native void nativeClearAllMessage(int i, long j);

    private static native void nativeClearAnimation(int i, long j);

    private static native void nativeClearAnimationByTypes(int i, long j, int i2, boolean z);

    public static native void nativeClearCustomStyle(int i, long j);

    private static native void nativeClearGestureMsgs(int i, long j);

    private static native void nativeClearLabels3rd(int i, long j, int i2, boolean z);

    private static native void nativeClearNaviMsgs(int i, long j);

    private static native void nativeClearOverlayTexture(int i, long j);

    private static native void nativeClearPoiFilter(int i, long j);

    private static native void nativeClearSelectedScenicPois(int i, long j);

    private static native long nativeCreate(String str, String str2, String str3, String str4, GLMapEngine gLMapEngine, byte[] bArr);

    private static native long nativeCreateCanvasView(long j, int i);

    private static native int nativeCreateEngineWithFrame(int i, long j, int i2, int i3, int i4, int i5, int i6, int i7, MapEngineInitParam mapEngineInitParam);

    protected static native long nativeCreateNaviOverlayController(int i, long j);

    protected static native long nativeCreateOverlay(int i, long j, int i2);

    protected static native long nativeCreateRealCityController(int i, long j);

    private static native int nativeCreateRenderDevice(long j, GLDeviceAttribute gLDeviceAttribute);

    private static native boolean nativeDeleteOpenLayer(int i, long j, int i2);

    protected static native void nativeDestoryOverlay(int i, long j);

    protected static native void nativeDestoryRealCityController(int i, long j);

    private static native void nativeDestroy(long j);

    private static native void nativeDestroyCanvasView(long j, long j2);

    private static native void nativeDestroyGLThread(long j);

    protected static native void nativeDestroyNaviOverlayController(int i, long j);

    private static native void nativeDestroyRenderDevice(long j, int i);

    private static native void nativeDetachSurfaceFromRenderDevice(long j, int i);

    private static native boolean nativeDoMapDataControl(int i, long j, int i2, int i3, int i4, int i5);

    private static native void nativeFinishAnimations(int i, long j);

    private static native void nativeFinishDownLoad(int i, long j, long j2);

    private static native void nativeFreeScreenShotBuffer(long j, int i, long j2);

    private static native int nativeGetAdviseFPS(int i, long j);

    private static native int nativeGetAnimationCount(int i, long j);

    private static native int nativeGetBelongToRenderDeviceId(long j, int i);

    private static native boolean nativeGetControllerStateBoolValue(int i, long j, int i2);

    private static native int[] nativeGetEngineIDArray(long j, int i);

    private static native int nativeGetEngineIDWithGestureInfo(long j, EAMapPlatformGestureInfo eAMapPlatformGestureInfo);

    private static native int nativeGetGestureMsgCount(int i, long j);

    private static native long nativeGetGlOverlayMgrPtr(int i, long j);

    private static native boolean nativeGetIsProcessBuildingMark(int i, long j);

    private static native boolean nativeGetMapDataTaskIsCancel(int i, long j, long j2);

    public static native String nativeGetMapEngineVersion();

    private static native boolean nativeGetMapFadeAnimOver(int i, long j);

    private static native int[] nativeGetMapModeState(int i, long j, boolean z);

    private static native boolean nativeGetMapPreLoadEnable(int i, long j, int i2);

    private static native long nativeGetMapStateInstance(int i, long j);

    private static native int nativeGetMaxFps(long j, int i);

    private static native int nativeGetMinFps(long j, int i);

    public static native String nativeGetNaviRebuildVersion();

    private static native byte[] nativeGetOpenLayerParam(int i, long j, int i2);

    private static native int nativeGetPostureState(int i, long j);

    private static native float nativeGetRealRenderFps(long j, int i);

    private static native int nativeGetRenderFps(long j, int i);

    private static native int nativeGetScreenShotMode(long j, int i);

    public static native String nativeGetSimple3dParserVersion();

    private static native boolean nativeGetSrvViewStateBoolValue(int i, long j, int i2);

    private static native int nativeGetSrvViewStateIntValue(int i, long j, int i2);

    private static native long nativeGetTotalRenderFrames(long j, int i);

    private static native int[] nativeGetValidDevices(long j);

    private static native int nativeGetWeatherAnimationState(int i, long j);

    private static native void nativeInitGLThread(long j);

    private static native void nativeInsertOpenLayer(int i, long j, byte[] bArr, int i2);

    private static native boolean nativeIsExistOverlayAnimation(int i, long j);

    private static native boolean nativeIsExistOverlayByType(int i, long j, int i2);

    private static native int nativeIsRealCityAnimateFinish(int i, long j);

    private static native boolean nativeIsRenderDeviceLocked(long j, int i);

    private static native boolean nativeIsRenderPaused(long j, int i);

    private static native boolean nativeIsShowFeatureSpotIcon(int i, long j);

    private static native boolean nativeIsShowLandMarkBuildingPoi(int i, long j);

    private static native boolean nativeIsShowMask(int i, long j);

    private static native boolean nativeIsSimple3DShow(int i, long j);

    private static native boolean nativeIsSkinExist(int i, long j, int[] iArr);

    private static native int nativeIsSupportRealcity(int i, long j, int i2);

    private static native void nativeLockRenderDevice(long j, int i);

    private static native void nativeNetError(int i, long j, int i2, long j2);

    private native long nativeParseImage(int i, long j, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, byte[][] bArr, int[] iArr, int[] iArr2);

    private static native void nativePutResourceData(int i, long j, byte[] bArr);

    private static native void nativeQueueEvent(long j, int i, Runnable runnable);

    private static native byte[] nativeReadMapPixels(int i, long j, int i2, int i3, int i4, int i5);

    private static native boolean nativeReadMapPixelsToBmp(int i, long j, Bitmap bitmap, int i2, int i3, int i4);

    private static native void nativeReceiveNetData(int i, long j, byte[] bArr, long j2, int i2);

    private static native void nativeRemoveEngine(int i, long j);

    private static native void nativeRemovePoiFilter(int i, long j, String str);

    private static native void nativeRemoveRouteName(long j, long j2, int i);

    private static native void nativeRenderDeviceChanged(long j, int i, Surface surface, GLSurfaceAttribute gLSurfaceAttribute);

    private static native void nativeRenderDeviceChangedEx(long j, int i, long j2, GLSurfaceAttribute gLSurfaceAttribute);

    private static native void nativeRenderPause(long j, int i);

    private static native void nativeRenderResume(long j, int i);

    private static native void nativeResetTickcount(long j, int i, int i2);

    private static native void nativeSelectMapPois(int i, long j, int i2, int i3, int i4, boolean z);

    private static native void nativeSetBackGroundColor(int i, long j, float f, float f2, float f3, float f4);

    private static native void nativeSetControllerStateBoolValue(int i, long j, int i2, boolean z);

    private static native void nativeSetIndoorBuildingToBeActive(int i, long j, String str, int i2, String str2);

    private static native void nativeSetIndoorServerAddress(long j, String str);

    private static native void nativeSetInternalTexture(int i, long j, byte[] bArr, int i2);

    private static native void nativeSetMapFadeIn(int i, long j, int i2);

    private static native void nativeSetMapFadeOut(int i, long j, int i2);

    private static native void nativeSetMapHeatPoiRegion(int i, long j, String str, int[] iArr, int[] iArr2);

    private static native boolean nativeSetMapModeAndStyle(int i, long j, int[] iArr, String str);

    private static native void nativeSetMaskColor(int i, long j, int i2);

    private static native void nativeSetMaxFps(long j, int i, int i2);

    private static native void nativeSetMinFps(long j, int i, int i2);

    private static native void nativeSetNetHost(long j, String str, String str2);

    private static native void nativeSetNetStatus(long j, int i);

    private static native void nativeSetOpenlayerParam(int i, long j, int i2, int i3, int i4, int i5, float f);

    private static native void nativeSetParameter(int i, long j, int i2, int i3, int i4, int i5, int i6);

    private static native void nativeSetParmaterEx(int i, long j, int i2, int[] iArr);

    private static native void nativeSetPostureState(int i, long j, int i2);

    private static native void nativeSetRenderFps(long j, int i, int i2);

    private static native void nativeSetRenderFpsEx(long j, int i, int i2, int i3);

    private static native void nativeSetRenderFpsWithTimer(long j, int i, int i2, boolean z);

    private static native void nativeSetRequestObjToTask(int i, long j, long j2, MapLoader mapLoader);

    private static native void nativeSetRequestResponseInfo(int i, long j, long j2, int i2, int i3);

    private static native void nativeSetScenicFilter(int i, long j, int i2, int[] iArr);

    private static native void nativeSetScreenShotCallBackMethod(long j, int i, int i2);

    private static native void nativeSetScreenShotCarType(long j, int i, int i2);

    private static native void nativeSetScreenShotMode(long j, int i, int i2);

    private static native void nativeSetScreenShotRect(long j, int i, int i2, int i3, int i4, int i5);

    private static native void nativeSetSearchedSubwayIds(int i, long j, String[] strArr);

    private static native void nativeSetServerAddress(long j, String str);

    private static native void nativeSetServiceViewRect(int i, long j, int i2, int i3, int i4, int i5, int i6, int i7);

    private static native void nativeSetServiceViewVisible(int i, boolean z, long j);

    private static native void nativeSetShowFeatureSpotIcon(int i, long j, boolean z);

    private static native void nativeSetShowMask(int i, long j, boolean z);

    private static native void nativeSetSrvViewStateBoolValue(int i, long j, int i2, boolean z);

    private static native void nativeSetSrvViewStateIntValue(int i, long j, int i2, int i3);

    private static native void nativeStartWeatherEffect(int i, long j, int i2, Bitmap bitmap, int i3);

    private static native void nativeStopWeatherEffect(int i, long j, boolean z);

    private static native boolean nativeTaskCompressedResponse(int i, long j, long j2);

    private static native boolean nativeTestMapParamter(long j, int i, int i2, int i3, int i4, int i5, String str);

    private static native int nativeTmcCacheCheckValid(int i, long j, String str, boolean z, boolean z2, byte[] bArr);

    private static native boolean nativeUnBindMapEngineFromRenderDevice(long j, int i, int i2);

    private static native void nativeUnLockRenderDevice(long j, int i);

    /* access modifiers changed from: private */
    public static native void nativeUpdateStyleOrIcons(int i, long j, String str, int i2);

    public int getEngineIDWithType(int i) {
        return i + 1;
    }

    public float getGLUnitWithPixel20(int i, int i2) {
        return (float) i2;
    }

    public native long nativeGetAnimationObserver(long j, int i);

    public native long nativeGetNativeMapControllerInstance(long j);

    public native long nativeGetNativeMapViewInstance(long j, int i);

    public void onClearCache(int i) {
    }

    public void onMapHeatActive(int i, boolean z) {
    }

    public void setShowFeatureSpotIcon(boolean z) {
    }

    public GLMapEngine(String str, String str2, String str3, ale ale) {
        this.mMapcoreListener = ale;
        this.mTextTextureGenerator = new and();
        String dataPath = DataService.getInstance().getDataPathManager().getDataPath();
        this.mNativeMapengineInstance = nativeCreate(dataPath, str, str2, str3, this, GetConfigBuffer(dataPath));
    }

    public long getNativeInstance() {
        return this.mNativeMapengineInstance;
    }

    public void setNetStatus(boolean z) {
        nativeSetNetStatus(this.mNativeMapengineInstance, z ? 1 : 0);
    }

    public static void setNetHost(String str, String str2) {
        nativeSetNetHost(0, str, str2);
    }

    public void setInUserAction(boolean z) {
        this.mInUserAction = z;
    }

    public boolean getInUserAction() {
        return this.mInUserAction;
    }

    public Context getContext() {
        return this.mMapcoreListener.a();
    }

    public void startWeatherEffect(int i, WeatherType weatherType, Bitmap bitmap, int i2) {
        nativeStartWeatherEffect(i, this.mNativeMapengineInstance, weatherType.value(), bitmap, i2);
    }

    public void stopWeatherEffect(int i, boolean z) {
        nativeStopWeatherEffect(i, this.mNativeMapengineInstance, z);
    }

    public WeatherAnimationState getWeatherAnimationState(int i) {
        WeatherAnimationState weatherAnimationState = WeatherAnimationState.UNKNOWN;
        switch (nativeGetWeatherAnimationState(i, this.mNativeMapengineInstance)) {
            case 0:
            case 4:
                return WeatherAnimationState.STOPPED;
            case 1:
                return WeatherAnimationState.FADE_IN;
            case 2:
                return WeatherAnimationState.RUNNNING;
            case 3:
                return WeatherAnimationState.FADE_OUT;
            default:
                return weatherAnimationState;
        }
    }

    public void clearAllMessages(int i) {
        nativeClearAllMessage(i, this.mNativeMapengineInstance);
    }

    /* access modifiers changed from: protected */
    public void clearGestureMsgs(int i) {
        nativeClearGestureMsgs(i, this.mNativeMapengineInstance);
    }

    public void clearNaviMsgs(int i) {
        nativeClearNaviMsgs(i, this.mNativeMapengineInstance);
    }

    public void addGestureMessage(int i, akw akw) {
        int a = akw.a();
        if (akw.a == 101) {
            switch (a) {
                case 0:
                    ala ala = (ala) akw;
                    nativeAddMapGestureMsg(i, this.mNativeMapengineInstance, a, ala.b, ala.c, 0.0f);
                    return;
                case 1:
                    alc alc = (alc) akw;
                    nativeAddMapGestureMsg(i, this.mNativeMapengineInstance, a, alc.b, alc.c, alc.d);
                    return;
                case 2:
                    alb alb = (alb) akw;
                    nativeAddMapGestureMsg(i, this.mNativeMapengineInstance, a, alb.d, (float) alb.b, (float) alb.c);
                    return;
                case 3:
                    nativeAddMapGestureMsg(i, this.mNativeMapengineInstance, a, (( akx ) akw).b, 0.0f, 0.0f);
                    break;
            }
        }
    }

    public boolean addNaviStateMsg( int i, GLNaviOverlay gLNaviOverlay, amf amf, GLGeoPoint gLGeoPoint, int i2, GLGeoPoint gLGeoPoint2, int i3, int i4, int i5, float f) {
        Point point;
        GLGeoPoint gLGeoPoint3 = gLGeoPoint;
        int i6 = i2;
        GLGeoPoint gLGeoPoint4 = gLGeoPoint2;
        int i7 = i3;
        int i8 = i5;
        if (gLNaviOverlay == null || !gLNaviOverlay.getIsInBundle()) {
            return false;
        }
        Point point2 = null;
        if (gLGeoPoint4 != null) {
            this.mLastNaviMsg.center.x = gLGeoPoint4.x;
            this.mLastNaviMsg.center.y = gLGeoPoint4.y;
            point = new Point(gLGeoPoint4.x, gLGeoPoint4.y);
        } else {
            point = null;
        }
        if (gLGeoPoint3 != null) {
            this.mLastNaviMsg.navicar.x = gLGeoPoint3.x;
            this.mLastNaviMsg.navicar.y = gLGeoPoint3.y;
            point2 = new Point(gLGeoPoint3.x, gLGeoPoint3.y);
        }
        Point point3 = point2;
        this.mLastNaviMsg.bearing = i6;
        this.mLastNaviMsg.mapAngle = i7;
        this.mLastNaviMsg.headerAngle = i8;
        float f2 = f;
        this.mLastNaviMsg.mapLevel = f2;
        long nativeInstatnce = gLNaviOverlay.getNativeInstatnce();
        long j = 0;
        if (amf != null) {
            j = amf.getNativeInstatnce();
        }
        int i9 = i;
        nativeAddMapNaviMsg(i9, this.mNativeMapengineInstance, nativeInstatnce, j, point, point3, (float) i7, (float) i6, (float) i4, (float) i8, f2);
        return true;
    }

    public boolean addNaviState3DMsg(int i, GLNaviOverlay gLNaviOverlay, amf amf, GLGeoPoint gLGeoPoint, GLGeoPoint gLGeoPoint2, GLGeoPoint gLGeoPoint3, int i2, float f, int i3, int i4, float f2, float f3) {
        Point point;
        Point point2;
        GLGeoPoint gLGeoPoint4 = gLGeoPoint;
        GLGeoPoint gLGeoPoint5 = gLGeoPoint2;
        GLGeoPoint gLGeoPoint6 = gLGeoPoint3;
        int i5 = i2;
        int i6 = i3;
        int i7 = i4;
        if (gLNaviOverlay == null || !gLNaviOverlay.getIsInBundle()) {
            return false;
        }
        Point point3 = null;
        if (gLGeoPoint6 != null) {
            this.mLastNaviMsg.center.x = gLGeoPoint6.x;
            this.mLastNaviMsg.center.y = gLGeoPoint6.y;
            point = new Point(gLGeoPoint6.x, gLGeoPoint6.y);
        } else {
            point = null;
        }
        if (gLGeoPoint4 != null) {
            this.mLastNaviMsg.navicar.x = gLGeoPoint4.x;
            this.mLastNaviMsg.navicar.y = gLGeoPoint4.y;
            point2 = new Point(gLGeoPoint4.x, gLGeoPoint4.y);
        } else {
            point2 = null;
        }
        if (gLGeoPoint5 != null) {
            this.mLastNaviMsg.navicar3d.x = gLGeoPoint5.x;
            this.mLastNaviMsg.navicar3d.y = gLGeoPoint5.y;
            this.mLastNaviMsg.navicar3d.z = gLGeoPoint5.z;
            point3 = new Point(gLGeoPoint5.x, gLGeoPoint5.y);
        }
        Point point4 = point3;
        float f4 = f;
        this.mLastNaviMsg.carpitch = f4;
        this.mLastNaviMsg.bearing = i5;
        this.mLastNaviMsg.mapAngle = i6;
        this.mLastNaviMsg.headerAngle = i7;
        float f5 = f2;
        this.mLastNaviMsg.mapLevel = f5;
        long nativeInstatnce = gLNaviOverlay.getNativeInstatnce();
        long j = 0;
        if (amf != null) {
            j = amf.getNativeInstatnce();
        }
        int i8 = i;
        nativeAddMapNavi3DMsg(i8, this.mNativeMapengineInstance, nativeInstatnce, j, point, point2, point4, (float) i6, (float) i5, gLGeoPoint4.z, gLGeoPoint5.z, (float) i7, f4, f5, f3);
        return true;
    }

    public boolean isInMapAction(int i) {
        return nativeGetGestureMsgCount(i, this.mNativeMapengineInstance) > 0;
    }

    public boolean isInMapAnimation(int i) {
        return nativeGetAnimationCount(i, this.mNativeMapengineInstance) > 0;
    }

    public boolean isExistOverlayAnimation(int i) {
        return nativeIsExistOverlayAnimation(i, this.mNativeMapengineInstance);
    }

    public int getAdviseFPS(int i) {
        return nativeGetAdviseFPS(i, this.mNativeMapengineInstance);
    }

    public void setPostureState(int i, int i2) {
        nativeSetPostureState(i, this.mNativeMapengineInstance, i2);
    }

    public int getPostureState(int i) {
        return nativeGetPostureState(i, this.mNativeMapengineInstance);
    }

    public boolean isMapInWaterWave(int i) {
        return nativeIsExistOverlayByType(i, this.mNativeMapengineInstance, 11);
    }

    public void setMapState(int i, GLMapState gLMapState) {
        if (this.mNativeMapengineInstance != 0 && gLMapState != null) {
            gLMapState.setNativeMapengineState(i, this.mNativeMapengineInstance);
        }
    }

    public GLMapState getNewMapState(int i) {
        if (this.mNativeMapengineInstance != 0) {
            return new GLMapState(i, this.mNativeMapengineInstance);
        }
        return null;
    }

    public GLMapState getMapState(int i) {
        if (this.mNativeMapengineInstance == 0) {
            return null;
        }
        GLMapState gLMapState = new GLMapState();
        gLMapState.setMapstateInstance(getStateInstanceWithEngineID(i));
        return gLMapState;
    }

    public long getMapStateInstance(int i) {
        return getStateInstanceWithEngineID(i);
    }

    public void clearException(int i) {
        if (this.mMapcoreListener != null) {
            this.mMapcoreListener.a(i);
        }
    }

    public void onException(int i, int i2) {
        if (this.mMapcoreListener != null) {
            this.mMapcoreListener.a(i, i2);
        }
    }

    public void onMapSurfaceRenderer(int i, int i2) {
        if (i2 != 1) {
            if (i2 != 5) {
                switch (i2) {
                    case 12:
                        if (this.mMapListener != null) {
                            this.mMapListener.onRealCityAnimateFinish(i);
                            return;
                        }
                        break;
                    case 13:
                        try {
                            if (this.mMapListener != null) {
                                this.mMapListener.onMapRenderCompleted(i);
                                break;
                            }
                        } catch (BufferOverflowException unused) {
                            return;
                        }
                        break;
                }
            } else if (this.mMapListener != null) {
                this.mMapListener.afterDrawFrame(i, getMapState(i));
            }
        } else if (this.mMapListener != null) {
            this.mMapListener.beforeDrawFrame(i, getMapState(i));
        }
    }

    public void onMapDataRequired(int i, byte[] bArr) {
        if (bArr != null) {
            ADataRequestParam aDataRequestParam = new ADataRequestParam();
            int a = amy.a(bArr, 0);
            aDataRequestParam.RequestBaseUrl = amy.a(bArr, 4, a);
            int i2 = a + 4;
            int a2 = amy.a(bArr, i2);
            int i3 = i2 + 4;
            aDataRequestParam.RequestUrl = amy.a(bArr, i3, a2);
            int i4 = i3 + a2;
            aDataRequestParam.Handler = amy.b(bArr, i4);
            int i5 = i4 + 8;
            aDataRequestParam.nRequestType = amy.a(bArr, i5);
            int i6 = i5 + 4;
            int a3 = amy.a(bArr, i6);
            int i7 = i6 + 4;
            byte[] bArr2 = new byte[a3];
            System.arraycopy(bArr, i7, bArr2, 0, a3);
            aDataRequestParam.enCodeString = bArr2;
            aDataRequestParam.nCompress = amy.a(bArr, i7 + a3);
            new MapLoader(i, this, aDataRequestParam).DoRequest();
        }
    }

    public byte[] onCharBitmapRequired(int i, int i2, int i3) {
        byte[] a = this.mTextTextureGenerator.a(i2, i3);
        resetTickCount(getBelongToRenderDeviceId(i), 6);
        return a;
    }

    public byte[] onMapCharsWidthsRequired(int i, int[] iArr, int i2, int i3) {
        and and = this.mTextTextureGenerator;
        int length = iArr.length;
        byte[] bArr = new byte[length];
        float[] fArr = new float[1];
        for (int i4 = 0; i4 < length; i4++) {
            Paint paint = and.c;
            StringBuilder sb = new StringBuilder();
            sb.append((char) iArr[i4]);
            fArr[0] = paint.measureText(sb.toString());
            bArr[i4] = (byte) ((int) (fArr[0] + ((float) (and.a - and.b))));
        }
        return bArr;
    }

    public void onIndoorBuildingActivity(final int i, byte[] bArr) {
        ami ami;
        if (bArr != null) {
            ami = new ami();
            byte b = bArr[0];
            ami.a = new String(bArr, 1, b);
            int i2 = b + 1;
            int i3 = i2 + 1;
            byte b2 = bArr[i2];
            ami.b = new String(bArr, i3, b2);
            int i4 = i3 + b2;
            int i5 = i4 + 1;
            byte b3 = bArr[i4];
            ami.c = new String(bArr, i5, b3);
            int i6 = i5 + b3;
            ami.d = amy.a(bArr, i6);
            int i7 = i6 + 4;
            int i8 = i7 + 1;
            byte b4 = bArr[i7];
            ami.e = new String(bArr, i8, b4);
            int i9 = i8 + b4;
            int i10 = i9 + 1;
            byte b5 = bArr[i9];
            ami.f = new String(bArr, i10, b5);
            int i11 = i10 + b5;
            ami.g = amy.a(bArr, i11);
            ami.h = new int[ami.g];
            ami.i = new String[ami.g];
            ami.j = new String[ami.g];
            int i12 = i11 + 4;
            for (int i13 = 0; i13 < ami.g; i13++) {
                ami.h[i13] = amy.a(bArr, i12);
                int i14 = i12 + 4;
                int i15 = i14 + 1;
                byte b6 = bArr[i14];
                if (b6 > 0) {
                    ami.i[i13] = new String(bArr, i15, b6);
                    i15 += b6;
                }
                i12 = i15 + 1;
                byte b7 = bArr[i15];
                if (b7 > 0) {
                    ami.j[i13] = new String(bArr, i12, b7);
                    i12 += b7;
                }
            }
            ami.k = amy.a(bArr, i12);
            int i16 = i12 + 4;
            if (ami.k > 0) {
                ami.l = new int[ami.k];
                for (int i17 = 0; i17 < ami.k; i17++) {
                    ami.l[i17] = amy.a(bArr, i16);
                    i16 += 4;
                }
            }
        } else {
            ami = null;
        }
        if (this.mLastIndoorBuilding != null || ami != null) {
            this.mLastIndoorBuilding = ami;
            this.mMapcoreListener.a((Runnable) new Runnable() {
                public void run() {
                    if (GLMapEngine.this.mIndoorBuildingListener != null) {
                        GLMapEngine.this.mIndoorBuildingListener.indoorBuildingActivity(i, GLMapEngine.this.mLastIndoorBuilding);
                    }
                }
            });
        }
    }

    public void setIndoorBuildingListener(a aVar) {
        this.mIndoorBuildingListener = aVar;
    }

    public boolean canStopMapRender(int i) {
        synchronized (this.mut_lock) {
            if (this.mNativeMapengineInstance == 0) {
                return true;
            }
            boolean nativeCanStopRenderMap = nativeCanStopRenderMap(i, this.mNativeMapengineInstance);
            return nativeCanStopRenderMap;
        }
    }

    public void setInternaltexture(int i, byte[] bArr, int i2) {
        if (bArr != null) {
            nativeSetInternalTexture(i, this.mNativeMapengineInstance, bArr, i2);
        }
    }

    @TargetApi(21)
    public void setCameraImage(int i, Image image) {
        if (image != null) {
            image.getFormat();
            image.getWidth();
            image.getHeight();
            image.getCropRect();
            Plane[] planes = image.getPlanes();
            if (planes == null) {
                image.close();
                return;
            }
            int length = planes.length;
            byte[][] bArr = new byte[length][];
            int[] iArr = new int[length];
            int[] iArr2 = new int[length];
            for (int i2 = 0; i2 < planes.length; i2++) {
                Plane plane = planes[i2];
                ByteBuffer buffer = plane.getBuffer();
                if (buffer.hasArray()) {
                    bArr[i2] = buffer.array();
                } else {
                    bArr[i2] = new byte[buffer.remaining()];
                    buffer.get(bArr[i2]);
                }
                iArr[i2] = plane.getRowStride();
                iArr2[i2] = plane.getPixelStride();
            }
        }
    }

    public void setMapCameraBuffer(int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        int i5 = i3;
        Rect rect = new Rect(0, 0, i4, i5);
        byte[][] bArr2 = new byte[1][];
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        for (int i6 = 0; i6 <= 0; i6++) {
            bArr2[0] = bArr;
            iArr[0] = 0;
            iArr2[0] = 0;
        }
        long j = this.mNativeMapengineInstance;
        int i7 = rect.bottom;
        int i8 = rect.top;
        int i9 = rect.left;
        int[] iArr3 = iArr2;
        nativeParseImage(i, j, 0, 21, i4, i5, i7, i8, i9, rect.right, bArr2, iArr3, iArr);
    }

    public void setMapCenter(int i, int i2, int i3) {
        GLMapState.nativeSetMapCenter(getStateInstanceWithEngineID(i), i2, i3);
    }

    public void setMapLeftTop(int i, float f, float f2) {
        GLMapState.nativeSetMapViewLeftTop(getStateInstanceWithEngineID(i), f, f2);
    }

    public void setMapLeftTopPercent(int i, float f, float f2) {
        GLMapState.nativeSetMapViewLeftTopPercent(getStateInstanceWithEngineID(i), f, f2);
    }

    public Point getMapCenter(int i) {
        Point point = new Point();
        GLMapState.nativeGetMapCenter(getStateInstanceWithEngineID(i), point);
        return point;
    }

    public Point getMapLeftTop(int i) {
        Point point = new Point();
        GLMapState.nativeGetMapViewLeftTop(getStateInstanceWithEngineID(i), point);
        return point;
    }

    public PointF getMapLeftTopPercent(int i) {
        PointF pointF = new PointF();
        GLMapState.nativeGetMapViewLeftTopPercent(getStateInstanceWithEngineID(i), pointF);
        return pointF;
    }

    public void setMapZoom(int i, float f) {
        if (f > ((float) getMaxZoomLevel(i))) {
            f = (float) getMaxZoomLevel(i);
        }
        GLMapState.nativeSetMapZoomer(getStateInstanceWithEngineID(i), f);
    }

    public void setMapMaxZoomer(int i, float f) {
        GLMapState.nativeSetMaxZoomLevel(getStateInstanceWithEngineID(i), f);
    }

    public void setMapMinZoomer(int i, float f) {
        GLMapState.nativeSetMinZoomLevel(getStateInstanceWithEngineID(i), f);
    }

    public void setMapMovableArea( int i, alp alp, alp alp2) {
        if (alp != null && alp2 != null) {
            GLMapState.nativeSetMovableArea(getStateInstanceWithEngineID(i), alp.a, alp.b, alp2.a, alp2.b);
        }
    }

    public void OnSelectMapPois(byte[] bArr) {
        this.mMapcoreListener.a(bArr);
    }

    public void getLabelBuffer(int i, int i2, int i3, int i4, boolean z) {
        nativeSelectMapPois(i, this.mNativeMapengineInstance, i2, i3, i4, z);
    }

    public void clearSelectMapPois(int i) {
        nativeClearSelectedScenicPois(i, this.mNativeMapengineInstance);
    }

    public void PutResourceData(int i, byte[] bArr) {
        synchronized (this.mut_lock) {
            if (this.mNativeMapengineInstance != 0) {
                int belongToRenderDeviceId = getBelongToRenderDeviceId(i);
                nativePutResourceData(i, this.mNativeMapengineInstance, bArr);
                nativeResetTickcount(this.mNativeMapengineInstance, belongToRenderDeviceId, 6);
            }
        }
    }

    public void UpdateStyleOrIcons(final int i, final String str, final int i2) {
        if (this.mMapcoreListener != null && 0 != this.mNativeMapengineInstance) {
            nativeQueueEvent(this.mNativeMapengineInstance, getBelongToRenderDeviceId(i), new Runnable() {
                public void run() {
                    GLMapEngine.nativeUpdateStyleOrIcons(i, GLMapEngine.this.mNativeMapengineInstance, str, i2);
                }
            });
        }
    }

    public void setIndoorBuildingToBeActive(int i, String str, int i2, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            int belongToRenderDeviceId = getBelongToRenderDeviceId(i);
            nativeSetIndoorBuildingToBeActive(i, this.mNativeMapengineInstance, str, i2, str2);
            nativeResetTickcount(this.mNativeMapengineInstance, belongToRenderDeviceId, 30);
        }
    }

    public void resetRenderTime(int i) {
        nativeResetTickcount(this.mNativeMapengineInstance, i, 30);
    }

    public float getMapZoomer(int i) {
        return GLMapState.nativeGetMapZoomer(getStateInstanceWithEngineID(i));
    }

    public int getMaxZoomLevel(int i) {
        return GLMapState.nativeGetMaxZoomLevel(getStateInstanceWithEngineID(i));
    }

    public int getMinZoomLevel(int i) {
        return GLMapState.nativeGetMinZoomLevel(getStateInstanceWithEngineID(i));
    }

    public float calculateMapZoomer(int i, alo alo) {
        return nativeCalculateMapZoomer(i, this.mNativeMapengineInstance, alo.a, alo.b, alo.c, alo.d, alo.e, alo.f);
    }

    public float calMapZoomLevelWithMapRect(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f) {
        return GLMapState.nativeCalcMapZoomLevelWithMapRect(getStateInstanceWithEngineID(i), i2, i3, i4, i5, i6, i7, i8, i9, f);
    }

    public float calMapZoomLevel(int i, int i2, int i3, int i4, int i5, int i6, int i7, float f) {
        return GLMapState.nativeCalcMapZoomLevel(getStateInstanceWithEngineID(i), i2, i3, i4, i5, i6, i7, f);
    }

    public float calMapZoomScalefactor(int i, int i2, float f, float f2) {
        return GLMapState.nativeCalMapZoomScalefactor(i, i2, f, f2);
    }

    public float GetMaxCameraHeadeRangle() {
        return GLMapState.nativeGetMaxCameraHeadeRangle();
    }

    public void setCameraDegree(int i, float f) {
        if (f == 65.0f) {
            setBusinessDataParamater(i, 66, 0, 0, 1, 0);
        }
        GLMapState.nativeSetCameraDegree(getStateInstanceWithEngineID(i), f);
    }

    public float getCameraDegree(int i) {
        return GLMapState.nativeGetCameraDegree(getStateInstanceWithEngineID(i));
    }

    public void setMapAngle(int i, float f) {
        if (f < 0.0f) {
            f += 360.0f;
        } else if (f >= 360.0f) {
            f -= 360.0f;
        }
        GLMapState.nativeSetMapAngle(getStateInstanceWithEngineID(i), f);
    }

    public float getMapAngle(int i) {
        return GLMapState.nativeGetMapAngle(getStateInstanceWithEngineID(i));
    }

    public void p20ToScreenPoint(int i, int i2, int i3, PointF pointF) {
        GLMapState.nativeP20ToScreenPoint(getStateInstanceWithEngineID(i), i2, i3, 0, pointF);
    }

    public void p20ToScreenPoint(int i, int i2, int i3, int i4, PointF pointF) {
        GLMapState.nativeP20ToScreenPoint(getStateInstanceWithEngineID(i), i2, i3, i4, pointF);
    }

    public void screenToP20Point(int i, float f, float f2, Point point) {
        GLMapState.nativeScreenToP20Point(getStateInstanceWithEngineID(i), f, f2, point);
    }

    public float getGLUnitWithWin(int i, int i2) {
        return GLMapState.nativeGetGLUnitWithWin(getStateInstanceWithEngineID(i), i2);
    }

    public float getGLUnitWithWinByY(int i, int i2, int i3) {
        return GLMapState.nativeGetGLUnitWithWinByY(getStateInstanceWithEngineID(i), i2, i3);
    }

    public void getPixel20Bound(int i, Rect rect) {
        GLMapState.nativeGetPixel20Bound(getStateInstanceWithEngineID(i), rect);
    }

    public void addOverlayTexture(int i, int i2, int i3, float f, float f2, Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        TextureGenedInfo CheckRepeat = GLLineOverlay.CheckRepeat(i2);
        nativeAddOverlayTexture(i, this.mNativeMapengineInstance, i2, i3, f, f2, bitmap, CheckRepeat.m_genMimps, CheckRepeat.m_isRepeat, true);
    }

    public void addOverlayTexture( int i, amh amh, int[] iArr) {
        amh amh2 = amh;
        if (amh2 == null || amh2.b == null || amh2.b.isRecycled()) {
            if (amh2.c != null) {
                nativeAddOverlayTextureByPng(i, this.mNativeMapengineInstance, amh2.a, amh2.d, amh2.e, amh2.f, amh2.c, amh2.g, amh2.h, iArr, false);
            }
            return;
        }
        nativeAddOverlayTexture(i, this.mNativeMapengineInstance, amh2.a, amh2.d, amh2.e, amh2.f, amh2.b, amh2.g, amh2.h, true);
    }

    public boolean cleanOverlayTexture(int i, int i2) {
        if (this.mNativeMapengineInstance != 0) {
            return nativeCleanOverlayTexture(i, this.mNativeMapengineInstance, i2);
        }
        return false;
    }

    public void clearAnimationsByTypes(int i, boolean z, int i2) {
        clearAnimationsByTypes(i, i2);
    }

    public void clearAnimationsByTypes(int i, int i2) {
        clearAnimationsByTypes(i, i2, false);
    }

    public void clearAnimationsByTypes(int i, int i2, boolean z) {
        int i3;
        if ((i2 & 1) == 0) {
            i3 = (i2 & 2) != 0 ? 4 : 0;
            if ((i2 & 4) != 0) {
                i3 |= 1;
            }
            if ((i2 & 8) != 0) {
                i3 |= 27;
            }
        } else {
            i3 = 65535;
        }
        nativeClearAnimationByTypes(i, this.mNativeMapengineInstance, i3, z);
    }

    public void clearAnimations(int i, boolean z) {
        nativeClearAnimation(i, this.mNativeMapengineInstance);
    }

    public void clearAnimationsByContent(int i, int i2, boolean z) {
        int i3 = (i2 & 1) != 0 ? 4 : 0;
        if ((i2 & 2) != 0) {
            i3 |= 9;
        }
        if ((i2 & 4) != 0) {
            i3 |= 18;
        }
        if ((i2 & 8) != 0) {
            i3 |= 32;
        }
        if ((i2 & 16) != 0) {
            i3 |= 64;
        }
        nativeClearAnimationByTypes(i, this.mNativeMapengineInstance, i3, z);
    }

    public void AddGroupAnimation(int i, int i2, int i3, float f, float f2, float f3, int i4, int i5, boolean z) {
        nativeAddGroupAnimation(i, this.mNativeMapengineInstance, i2, i3, f, f2, f3, i4, i5, z);
    }

    public void AddGeoAndScreenCenterGroupAnimation(int i, int i2, GLGeoPoint gLGeoPoint, Point point, boolean z) {
        if (this.mMapcoreListener != null && gLGeoPoint != null && point != null) {
            nativeAddGeoAndScreenCenterGroupAnimation(i, this.mNativeMapengineInstance, i2, gLGeoPoint.x, gLGeoPoint.y, point.x, point.y, z);
        }
    }

    public void startPivotZoomRotateAnim( akq akq, int i, Point point, float f, float f2, int i2) {
        if (f != -9999.0f || f2 != -9999.0f) {
            if (f2 != -9999.0f && f2 < 0.0f) {
                f2 += 360.0f;
            }
            float f3 = f2;
            if (point != null) {
                nativeAddZoomRotateAnimation(i, this.mNativeMapengineInstance, i2, f, f3, point.x, point.y);
                return;
            }
            nativeAddZoomRotateAnimation(i, this.mNativeMapengineInstance, i2, f, f3, -1, -1);
        }
    }

    public void startPivotZoomAnim(akq akq, int i, Point point, float f, int i2) {
        Point point2 = point;
        if (point2 != null) {
            nativeAddPivotZoomAnimation(i, this.mNativeMapengineInstance, i2, f, point2.x, point2.y);
            return;
        }
        nativeAddPivotZoomAnimation(i, this.mNativeMapengineInstance, i2, f, -1, -1);
    }

    public void startMapSlidAnim(akq akq, int i, int i2, Point point) {
        if (point != null) {
            nativeAddMoveAnimation(i, this.mNativeMapengineInstance, i2, point.x, point.y);
        }
    }

    public void FinishAnimations(int i, boolean z) {
        nativeFinishAnimations(i, this.mNativeMapengineInstance);
    }

    public void FinishAnimations(int i) {
        nativeFinishAnimations(i, this.mNativeMapengineInstance);
    }

    public long getGlOverlayMgrPtr(int i) {
        return nativeGetGlOverlayMgrPtr(i, this.mNativeMapengineInstance);
    }

    public long createOverlay(int i, int i2) {
        return nativeCreateOverlay(i, this.mNativeMapengineInstance, i2);
    }

    public static void destoryOverlay(int i, long j) {
        nativeDestoryOverlay(i, j);
    }

    public long createNaviOverlayController(int i) {
        return nativeCreateNaviOverlayController(i, this.mNativeMapengineInstance);
    }

    public static void destroyNaviOverlayController(int i, long j) {
        nativeDestroyNaviOverlayController(i, j);
    }

    public long createRealCityController(int i) {
        return nativeCreateRealCityController(i, this.mNativeMapengineInstance);
    }

    public static void destoryRealCityController(int i, long j) {
        nativeDestoryRealCityController(i, j);
    }

    public void setBusinessDataParamater(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mNativeMapengineInstance != 0) {
            nativeSetParameter(i, this.mNativeMapengineInstance, i2, i3, i4, i5, i6);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0224, code lost:
        if (r4 != 1) goto L_0x0227;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0226, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0227, code lost:
        nativeSetSrvViewStateBoolValue(r1, r9.mNativeMapengineInstance, 28, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x022e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x022f, code lost:
        if (r4 != 1) goto L_0x0232;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0231, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0232, code lost:
        nativeSetSrvViewStateBoolValue(r1, r9.mNativeMapengineInstance, 29, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0239, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0244, code lost:
        if (r4 != 1) goto L_0x0247;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0246, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0247, code lost:
        nativeSetSrvViewStateBoolValue(r1, r9.mNativeMapengineInstance, 23, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x024e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0259, code lost:
        if (r4 != 1) goto L_0x025c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x025b, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x025c, code lost:
        nativeSetControllerStateBoolValue(r1, r9.mNativeMapengineInstance, 1, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0261, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0288, code lost:
        if (r4 != 1) goto L_0x028b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x028a, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x028b, code lost:
        nativeSetControllerStateBoolValue(r1, r9.mNativeMapengineInstance, 3, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0290, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0182, code lost:
        if (r4 != 1) goto L_0x0185;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0184, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0185, code lost:
        nativeSetSrvViewStateBoolValue(r1, r9.mNativeMapengineInstance, 24, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x018c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setParamater(int r11, int r12, int r13, int r14, int r15, int r16) {
        /*
            r10 = this;
            r9 = r10
            r1 = r11
            r4 = r13
            long r2 = r9.mNativeMapengineInstance
            r5 = 0
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 == 0) goto L_0x02a3
            r2 = 2
            r3 = 3
            r5 = 0
            r6 = 1
            switch(r12) {
                case 1: goto L_0x029a;
                case 2: goto L_0x0291;
                case 3: goto L_0x0288;
                case 4: goto L_0x027f;
                case 5: goto L_0x0276;
                case 6: goto L_0x026c;
                case 7: goto L_0x0262;
                default: goto L_0x0012;
            }
        L_0x0012:
            switch(r12) {
                case 17: goto L_0x0259;
                case 18: goto L_0x024f;
                case 19: goto L_0x0244;
                case 20: goto L_0x023a;
                case 21: goto L_0x022f;
                case 22: goto L_0x0224;
                default: goto L_0x0015;
            }
        L_0x0015:
            switch(r12) {
                case 1021: goto L_0x0244;
                case 1022: goto L_0x0219;
                case 1023: goto L_0x020e;
                case 1024: goto L_0x0224;
                case 1025: goto L_0x0203;
                case 1026: goto L_0x01f8;
                case 1027: goto L_0x01ed;
                default: goto L_0x0018;
            }
        L_0x0018:
            switch(r12) {
                case 2012: goto L_0x01de;
                case 2013: goto L_0x01d0;
                case 2014: goto L_0x01c1;
                case 2015: goto L_0x01b6;
                case 2016: goto L_0x01ab;
                default: goto L_0x001b;
            }
        L_0x001b:
            switch(r12) {
                case 2019: goto L_0x019c;
                case 2020: goto L_0x018d;
                default: goto L_0x001e;
            }
        L_0x001e:
            switch(r12) {
                case 2034: goto L_0x0182;
                case 2035: goto L_0x0288;
                case 2036: goto L_0x022f;
                case 2037: goto L_0x0177;
                case 2038: goto L_0x016c;
                case 2039: goto L_0x0161;
                default: goto L_0x0021;
            }
        L_0x0021:
            switch(r12) {
                case 2051: goto L_0x0152;
                case 2052: goto L_0x0143;
                case 2053: goto L_0x0135;
                case 2054: goto L_0x0126;
                case 2055: goto L_0x011b;
                case 2056: goto L_0x010c;
                case 2057: goto L_0x00fc;
                default: goto L_0x0024;
            }
        L_0x0024:
            switch(r12) {
                case 2603: goto L_0x00ed;
                case 2604: goto L_0x00de;
                default: goto L_0x0027;
            }
        L_0x0027:
            switch(r12) {
                case 2701: goto L_0x00cf;
                case 2702: goto L_0x00c0;
                default: goto L_0x002a;
            }
        L_0x002a:
            switch(r12) {
                case 13: goto L_0x0182;
                case 1001: goto L_0x00b5;
                case 1029: goto L_0x00a6;
                case 1900: goto L_0x0097;
                case 2010: goto L_0x0259;
                case 2401: goto L_0x0088;
                case 2501: goto L_0x0079;
                case 2601: goto L_0x006a;
                case 2801: goto L_0x005f;
                case 3001: goto L_0x0054;
                case 3003: goto L_0x0049;
                case 5001: goto L_0x003a;
                case 5101: goto L_0x002f;
                default: goto L_0x002d;
            }
        L_0x002d:
            goto L_0x02a3
        L_0x002f:
            if (r4 != r6) goto L_0x0032
            r5 = 1
        L_0x0032:
            long r2 = r9.mNativeMapengineInstance
            r0 = 100
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x003a:
            long r2 = r9.mNativeMapengineInstance
            r5 = 76
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0049:
            if (r4 != r6) goto L_0x004c
            r5 = 1
        L_0x004c:
            long r2 = r9.mNativeMapengineInstance
            r0 = 32
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0054:
            if (r4 != r6) goto L_0x0057
            r5 = 1
        L_0x0057:
            long r2 = r9.mNativeMapengineInstance
            r0 = 31
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x005f:
            if (r4 != r6) goto L_0x0062
            r5 = 1
        L_0x0062:
            long r2 = r9.mNativeMapengineInstance
            r0 = 42
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x006a:
            long r2 = r9.mNativeMapengineInstance
            r5 = 72
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0079:
            long r2 = r9.mNativeMapengineInstance
            r5 = 71
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0088:
            long r2 = r9.mNativeMapengineInstance
            r5 = 61
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0097:
            long r2 = r9.mNativeMapengineInstance
            r5 = 66
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x00a6:
            long r2 = r9.mNativeMapengineInstance
            r5 = 65
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x00b5:
            if (r4 != r6) goto L_0x00b8
            r5 = 1
        L_0x00b8:
            long r2 = r9.mNativeMapengineInstance
            r0 = 45
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x00c0:
            long r2 = r9.mNativeMapengineInstance
            r5 = 79
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x00cf:
            long r2 = r9.mNativeMapengineInstance
            r5 = 80
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x00de:
            long r2 = r9.mNativeMapengineInstance
            r5 = 75
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x00ed:
            long r2 = r9.mNativeMapengineInstance
            r5 = 73
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x00fc:
            long r2 = r9.mNativeMapengineInstance
            r5 = 78
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            goto L_0x02a3
        L_0x010c:
            long r2 = r9.mNativeMapengineInstance
            r5 = 69
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x011b:
            if (r4 != r6) goto L_0x011e
            r5 = 1
        L_0x011e:
            long r2 = r9.mNativeMapengineInstance
            r0 = 38
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0126:
            long r2 = r9.mNativeMapengineInstance
            r5 = 70
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0135:
            java.lang.String r6 = ""
            r7 = 0
            r8 = 1
            r0 = r9
            r2 = r4
            r3 = r14
            r4 = r15
            r5 = r16
            r0.SetMapModeAndStyle(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        L_0x0143:
            long r2 = r9.mNativeMapengineInstance
            r5 = 68
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0152:
            long r2 = r9.mNativeMapengineInstance
            r5 = 67
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x0161:
            if (r4 != r6) goto L_0x0164
            r5 = 1
        L_0x0164:
            long r2 = r9.mNativeMapengineInstance
            r0 = 40
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x016c:
            if (r4 != r6) goto L_0x016f
            r5 = 1
        L_0x016f:
            long r2 = r9.mNativeMapengineInstance
            r0 = 39
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0177:
            if (r4 != r6) goto L_0x017a
            r5 = 1
        L_0x017a:
            long r2 = r9.mNativeMapengineInstance
            r0 = 30
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0182:
            if (r4 != r6) goto L_0x0185
            r5 = 1
        L_0x0185:
            long r2 = r9.mNativeMapengineInstance
            r0 = 24
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x018d:
            long r2 = r9.mNativeMapengineInstance
            r5 = 60
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x019c:
            long r2 = r9.mNativeMapengineInstance
            r5 = 74
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x01ab:
            if (r4 != r6) goto L_0x01ae
            r5 = 1
        L_0x01ae:
            long r2 = r9.mNativeMapengineInstance
            r0 = 33
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x01b6:
            if (r4 != r6) goto L_0x01b9
            r5 = 1
        L_0x01b9:
            long r2 = r9.mNativeMapengineInstance
            r0 = 37
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x01c1:
            long r2 = r9.mNativeMapengineInstance
            r5 = 63
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x01d0:
            java.lang.String r6 = ""
            r7 = 0
            r8 = 0
            r0 = r9
            r2 = r4
            r3 = r14
            r4 = r15
            r5 = r16
            r0.SetMapModeAndStyle(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        L_0x01de:
            long r2 = r9.mNativeMapengineInstance
            r5 = 62
            r0 = r1
            r1 = r2
            r3 = r5
            r5 = r14
            r6 = r15
            r7 = r16
            nativeSetParameter(r0, r1, r3, r4, r5, r6, r7)
            return
        L_0x01ed:
            if (r4 != r6) goto L_0x01f0
            r5 = 1
        L_0x01f0:
            long r2 = r9.mNativeMapengineInstance
            r0 = 36
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x01f8:
            if (r4 != r6) goto L_0x01fb
            r5 = 1
        L_0x01fb:
            long r2 = r9.mNativeMapengineInstance
            r0 = 27
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0203:
            if (r4 != r6) goto L_0x0206
            r5 = 1
        L_0x0206:
            long r2 = r9.mNativeMapengineInstance
            r0 = 46
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x020e:
            if (r4 != r6) goto L_0x0211
            r5 = 1
        L_0x0211:
            long r2 = r9.mNativeMapengineInstance
            r0 = 21
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0219:
            if (r4 != r6) goto L_0x021c
            r5 = 1
        L_0x021c:
            long r2 = r9.mNativeMapengineInstance
            r0 = 26
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0224:
            if (r4 != r6) goto L_0x0227
            r5 = 1
        L_0x0227:
            long r2 = r9.mNativeMapengineInstance
            r0 = 28
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x022f:
            if (r4 != r6) goto L_0x0232
            r5 = 1
        L_0x0232:
            long r2 = r9.mNativeMapengineInstance
            r0 = 29
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x023a:
            if (r4 != r6) goto L_0x023d
            r5 = 1
        L_0x023d:
            long r2 = r9.mNativeMapengineInstance
            r0 = 7
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0244:
            if (r4 != r6) goto L_0x0247
            r5 = 1
        L_0x0247:
            long r2 = r9.mNativeMapengineInstance
            r0 = 23
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x024f:
            if (r4 != r6) goto L_0x0252
            r5 = 1
        L_0x0252:
            long r2 = r9.mNativeMapengineInstance
            r0 = 6
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0259:
            if (r4 != r6) goto L_0x025c
            r5 = 1
        L_0x025c:
            long r2 = r9.mNativeMapengineInstance
            nativeSetControllerStateBoolValue(r1, r2, r6, r5)
            return
        L_0x0262:
            if (r4 != r6) goto L_0x0265
            r5 = 1
        L_0x0265:
            long r2 = r9.mNativeMapengineInstance
            r0 = 5
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x026c:
            if (r4 != r6) goto L_0x026f
            r5 = 1
        L_0x026f:
            long r2 = r9.mNativeMapengineInstance
            r0 = 4
            nativeSetSrvViewStateBoolValue(r1, r2, r0, r5)
            return
        L_0x0276:
            if (r4 != r6) goto L_0x0279
            r5 = 1
        L_0x0279:
            long r6 = r9.mNativeMapengineInstance
            nativeSetSrvViewStateBoolValue(r1, r6, r3, r5)
            return
        L_0x027f:
            if (r4 != r6) goto L_0x0282
            r5 = 1
        L_0x0282:
            long r3 = r9.mNativeMapengineInstance
            nativeSetSrvViewStateBoolValue(r1, r3, r2, r5)
            return
        L_0x0288:
            if (r4 != r6) goto L_0x028b
            r5 = 1
        L_0x028b:
            long r6 = r9.mNativeMapengineInstance
            nativeSetControllerStateBoolValue(r1, r6, r3, r5)
            return
        L_0x0291:
            if (r4 != 0) goto L_0x0294
            r5 = 1
        L_0x0294:
            long r3 = r9.mNativeMapengineInstance
            nativeSetControllerStateBoolValue(r1, r3, r2, r5)
            return
        L_0x029a:
            if (r4 != r6) goto L_0x029d
            r5 = 1
        L_0x029d:
            long r2 = r9.mNativeMapengineInstance
            nativeSetSrvViewStateBoolValue(r1, r2, r6, r5)
            return
        L_0x02a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.ae.gmap.GLMapEngine.setParamater(int, int, int, int, int, int):void");
    }

    public void setParamaterEx(int i, int i2, int[] iArr) {
        if (this.mNativeMapengineInstance != 0) {
            nativeSetParmaterEx(i, this.mNativeMapengineInstance, i2, iArr);
        }
    }

    public boolean TestMapParamter(int i, int i2, int i3, int i4, int i5, String str) {
        return nativeTestMapParamter(this.mNativeMapengineInstance, i, i2, i3, i4, i5, str);
    }

    public void addPoiFilter(int i, int i2, int i3, int i4, float f, float f2, float f3, float f4, String str, int i5) {
        if (this.mNativeMapengineInstance != 0) {
            nativeAddPoiFilter(i, this.mNativeMapengineInstance, i2, i3, i4, f, f2, f3, f4, str, i5);
        }
    }

    public void removePoiFilter(int i, String str) {
        if (this.mNativeMapengineInstance != 0) {
            nativeRemovePoiFilter(i, this.mNativeMapengineInstance, str);
        }
    }

    public void clearPoiFilter(int i) {
        if (this.mNativeMapengineInstance != 0) {
            nativeClearPoiFilter(i, this.mNativeMapengineInstance);
        }
    }

    public void setSearchedSubwayIds(int i, String[] strArr) {
        if (strArr != null && this.mNativeMapengineInstance != 0) {
            nativeSetSearchedSubwayIds(i, this.mNativeMapengineInstance, strArr);
        }
    }

    public void setNaviRouteBoardDataListener(amo amo) {
        this.mBoardDataListener = amo;
    }

    public void onLogReport(int i, int i2, int i3, String str) {
        ana.a(i2, i3, str);
    }

    public void onOfflineMap(int i, String str, int i2) {
        if (str.length() != 0 && this.mMapListener != null) {
            this.mMapListener.onOfflineMap(i, str, i2);
        }
    }

    public void onSelectSubWayActive(int i, byte[] bArr) {
        if (this.mMapListener != null) {
            this.mMapListener.onSelectSubWayActive(i, bArr);
        }
    }

    public void setScenicWidgetFilter(int i, amx amx) {
        if (amx == null) {
            nativeSetScenicFilter(i, this.mNativeMapengineInstance, 0, null);
            return;
        }
        int[] iArr = new int[(amx.b.length * 3)];
        for (int i2 = 0; i2 < amx.b.length; i2++) {
            int i3 = i2 * 3;
            iArr[i3] = amx.b[i2].a;
            iArr[i3 + 1] = amx.b[i2].b;
            iArr[i3 + 2] = amx.b[i2].c;
        }
        nativeSetScenicFilter(i, this.mNativeMapengineInstance, amx.a, iArr);
    }

    public void setShowMask(int i, boolean z) {
        nativeSetShowMask(i, this.mNativeMapengineInstance, z);
    }

    public boolean isShowMask(int i) {
        return nativeIsShowMask(i, this.mNativeMapengineInstance);
    }

    public void setMaskColor(int i, int i2) {
        nativeSetMaskColor(i, this.mNativeMapengineInstance, i2);
    }

    public boolean isShowLandMarkBuildingPoi(int i) {
        return nativeIsShowLandMarkBuildingPoi(i, this.mNativeMapengineInstance);
    }

    public boolean isShowFeatureSpotIcon(int i) {
        return nativeIsShowFeatureSpotIcon(i, this.mNativeMapengineInstance);
    }

    public void onScenicActive(final int i, byte[] bArr) {
        final amv amv;
        if (bArr != null) {
            boolean z = false;
            byte b = bArr[0];
            amv = new amv();
            amv.a = new String(bArr, 1, b);
            int i2 = b + 1;
            int i3 = i2 + 1;
            amv.b = bArr[i2] != 0;
            int i4 = i3 + 1;
            amv.c = bArr[i3] != 0;
            int i5 = i4 + 1;
            amv.d = bArr[i4] != 0;
            int i6 = i5 + 1;
            amv.e = bArr[i5] != 0;
            int i7 = i6 + 1;
            amv.f = bArr[i6] != 0;
            int i8 = i7 + 1;
            amv.g = bArr[i7] != 0;
            int i9 = i8 + 1;
            amv.h = bArr[i8] != 0;
            int i10 = i9 + 1;
            if (bArr[i9] != 0) {
                z = true;
            }
            amv.i = z;
            amv.j = bArr[i10];
        } else {
            amv = null;
        }
        this.mMapcoreListener.a((Runnable) new Runnable() {
            public void run() {
                if (GLMapEngine.this.mScenicListener != null) {
                    GLMapEngine.this.mScenicListener.a(i, amv);
                }
            }
        });
    }

    public void setScenicListener(amw amw) {
        this.mScenicListener = amw;
    }

    public void setScenicGuideEnable(int i, boolean z) {
        setSrvViewStateBoolValue(i, 36, z);
    }

    public void setScenicHDMapEnable(int i, boolean z) {
        setSrvViewStateBoolValue(i, 52, z);
    }

    public void setDiffEnable(int i, boolean z) {
        setSrvViewStateBoolValue(i, 53, z);
    }

    public void setMapListener(amk amk) {
        this.mMapListener = amk;
    }

    public void setMapHeatListener(amu amu) {
        this.mMapHeatListener = amu;
    }

    public void setMapHeatEnable(int i, boolean z) {
        setSrvViewStateBoolValue(i, 37, z);
    }

    public void setRenderListenerStatus(int i, int i2) {
        boolean z = true;
        if (i2 != 1) {
            z = false;
        }
        setSrvViewStateBoolValue(i, 42, z);
    }

    public void setMapPreLoadEnable(int i, MapPreLoadType mapPreLoadType, boolean z) {
        setParamater(i, 2057, mapPreLoadType.value(), 0, 0, (z ? MapPreLoadCommandParamType.PreLoadSwitchOn : MapPreLoadCommandParamType.PreLoadSwitchOff).value());
    }

    public boolean getMapPreLoadEnable(int i, MapPreLoadType mapPreLoadType) {
        return nativeGetMapPreLoadEnable(i, this.mNativeMapengineInstance, mapPreLoadType.value());
    }

    public void setMapPreLoadParam(int i, MapPreLoadType mapPreLoadType, int i2, int i3) {
        setParamater(i, 2057, mapPreLoadType.value(), i2, i3, MapPreLoadCommandParamType.PreLoadParam.value());
    }

    public void setMapHeatPoiRegion(int i, String str, int[] iArr, int[] iArr2) {
        nativeSetMapHeatPoiRegion(i, this.mNativeMapengineInstance, str, iArr, iArr2);
    }

    public void addLabels3rd(int i, int i2, Label3rd[] label3rdArr, boolean z) {
        nativeAddLabels3rd(i, this.mNativeMapengineInstance, i2, label3rdArr, z);
    }

    public void clearLabels3rd(int i, int i2, boolean z) {
        nativeClearLabels3rd(i, this.mNativeMapengineInstance, i2, z);
    }

    public void addCustomStyle(int i, CustomStyleParam[] customStyleParamArr, boolean z) {
        nativeAddCustomStyle(i, this.mNativeMapengineInstance, customStyleParamArr, z);
    }

    public void clearCustomStyle(int i) {
        nativeClearCustomStyle(i, this.mNativeMapengineInstance);
    }

    public boolean doMapDataControl(int i, int i2, int i3, int i4, int i5) {
        if (this.mNativeMapengineInstance == 0) {
            return true;
        }
        return nativeDoMapDataControl(i, this.mNativeMapengineInstance, i2, i3, i4, i5);
    }

    public void SetRequestResponseInfo(int i, long j, int i2, int i3) {
        if (this.mNativeMapengineInstance != 0) {
            nativeSetRequestResponseInfo(i, this.mNativeMapengineInstance, j, i2, i3);
        }
    }

    public void onTransferParam(final int i, int[] iArr) {
        if (iArr != null) {
            final boolean z = false;
            int i2 = iArr[0];
            if (i2 != 10001) {
                if (i2 == 10003) {
                    onException(i, 1007);
                }
                return;
            }
            if (iArr[1] != 0) {
                z = true;
            }
            this.mMapcoreListener.a((Runnable) new Runnable() {
                public void run() {
                    GLMapEngine.this.mMapcoreListener.a(i, z);
                }
            });
        }
    }

    public void onLogOfflineDataStatusReport(int i, String str, String str2, String str3) {
        ana.a(str, str2, str3);
    }

    public byte[] OnMapLoadResourceByName(int i, String str) {
        if (str == null) {
            return null;
        }
        try {
            InputStream open = this.mMapcoreListener.a().getAssets().open("map_assets/".concat(String.valueOf(str)));
            int available = open.available();
            if (available == 0) {
                return null;
            }
            byte[] bArr = new byte[available];
            for (int i2 = 0; i2 < available; i2 += open.read(bArr, i2, available - i2)) {
            }
            open.close();
            return bArr;
        } catch (IOException unused) {
            return null;
        } catch (OutOfMemoryError unused2) {
            return null;
        }
    }

    public void OnMapResourceReLoad(int i, String str, int i2) {
        UpdateStyleOrIcons(i, str, i2);
    }

    public void appendOpenLayer(int i, byte[] bArr) {
        if (this.mNativeMapengineInstance != 0) {
            nativeAppendOpenLayer(i, this.mNativeMapengineInstance, bArr);
        }
    }

    public void deleteOpenLayer(int i, int i2) {
        if (this.mNativeMapengineInstance != 0) {
            nativeDeleteOpenLayer(i, this.mNativeMapengineInstance, i2);
        }
    }

    public void setOpenlayerParam(int i, alv alv) {
        if (this.mNativeMapengineInstance != 0) {
            nativeSetOpenlayerParam(i, this.mNativeMapengineInstance, alv.a, alv.d, alv.b, alv.c, alv.e);
        }
    }

    public alt getOpenlayerParam( int i, alu alu) {
        alt alt = new alt();
        if (this.mNativeMapengineInstance != 0) {
            byte[] nativeGetOpenLayerParam = nativeGetOpenLayerParam(i, this.mNativeMapengineInstance, alu.a);
            if (nativeGetOpenLayerParam != null) {
                alt.a = amy.a(nativeGetOpenLayerParam, 0);
                alt.b = amy.a(nativeGetOpenLayerParam, 4);
            }
        }
        return alt;
    }

    public void onClearOverlayTexture(int i) {
        nativeClearOverlayTexture(i, this.mNativeMapengineInstance);
    }

    public int IsSupportRealcity(int i, int i2) {
        return nativeIsSupportRealcity(i, this.mNativeMapengineInstance, i2);
    }

    public int IsRealCityAnimateFinish(int i) {
        return nativeIsRealCityAnimateFinish(i, this.mNativeMapengineInstance);
    }

    public boolean SetMapModeAndStyle(int i, int i2, int i3, int i4, int i5, String str, int i6, int i7) {
        return nativeSetMapModeAndStyle(i, this.mNativeMapengineInstance, new int[]{i2, i3, i4, i5, i6, i7}, str);
    }

    public boolean IsSkinExist(int i, int i2, int i3, int i4, int i5) {
        return nativeIsSkinExist(i, this.mNativeMapengineInstance, new int[]{i2, i3, i4, i5});
    }

    public int createEngineWithFrame(int i, Rect rect, int i2, int i3, MapEngineInitParam mapEngineInitParam) {
        if (rect == null) {
            return -1;
        }
        int nativeCreateEngineWithFrame = nativeCreateEngineWithFrame(i, this.mNativeMapengineInstance, rect.left, rect.top, rect.width(), rect.height(), i2, i3, mapEngineInitParam);
        this.mStateSparseArray.put(nativeCreateEngineWithFrame, new anc(nativeCreateEngineWithFrame));
        this.mGestureCenterTypes.put(Integer.valueOf(nativeCreateEngineWithFrame), Integer.valueOf(0));
        StringBuilder sb = new StringBuilder("createEngineWithFrame: ");
        sb.append(i);
        sb.append(", ");
        sb.append(nativeCreateEngineWithFrame);
        sb.append(", ");
        sb.append(this.mStateSparseArray.size());
        sb.append(", ");
        sb.append(this.mStateSparseArray);
        sb.append(", ");
        sb.append(this);
        ana.a();
        return nativeCreateEngineWithFrame;
    }

    public void removeEngine(int i) {
        StringBuilder sb = new StringBuilder("removeEngine: ");
        sb.append(i);
        sb.append(", ");
        sb.append(this.mStateSparseArray.size());
        ana.a();
        synchronized (this.mut_lock) {
            if (0 != this.mNativeMapengineInstance) {
                if (getOvelayBundle(i) != null) {
                    getOvelayBundle(i).removeAll(true);
                }
                nativeRemoveEngine(i, this.mNativeMapengineInstance);
                if (this.mStateSparseArray != null) {
                    this.mStateSparseArray.remove(i);
                }
            }
        }
    }

    public void setEngineVisible(int i, boolean z) {
        nativeSetServiceViewVisible(i, z, this.mNativeMapengineInstance);
    }

    public boolean isEngineCreated(int i) {
        int[] engineIDs = getEngineIDs(-1);
        if (engineIDs != null && engineIDs.length > 0) {
            for (int i2 : engineIDs) {
                if (i2 == i) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] getEngineIDs(int i) {
        if (0 == this.mNativeMapengineInstance) {
            return null;
        }
        return nativeGetEngineIDArray(this.mNativeMapengineInstance, i);
    }

    public int[] GetValidDevices() {
        if (0 == this.mNativeMapengineInstance) {
            return null;
        }
        return nativeGetValidDevices(this.mNativeMapengineInstance);
    }

    public int getBelongToRenderDeviceId(int i) {
        return nativeGetBelongToRenderDeviceId(this.mNativeMapengineInstance, i);
    }

    public String getUserAgent() {
        if (this.mMapcoreListener != null) {
            return this.mMapcoreListener.b();
        }
        return null;
    }

    private long getStateInstanceWithEngineID(int i) {
        return nativeGetMapStateInstance(i, this.mNativeMapengineInstance);
    }

    public void setOvelayBundle(int i, GLOverlayBundle<BaseMapOverlay<?, ?>> gLOverlayBundle) {
        if (this.mStateSparseArray.get(i) != null && i >= 0) {
            this.mStateSparseArray.get(i).a = gLOverlayBundle;
        }
    }

    public GLOverlayBundle<BaseMapOverlay<?, ?>> getOvelayBundle(int i) {
        if (this.mStateSparseArray.get(i) == null || i < 0) {
            return null;
        }
        return this.mStateSparseArray.get(i).a;
    }

    public int getEngineIDWithGestureInfo(EAMapPlatformGestureInfo eAMapPlatformGestureInfo) {
        if (this.mNativeMapengineInstance != 0) {
            return nativeGetEngineIDWithGestureInfo(this.mNativeMapengineInstance, eAMapPlatformGestureInfo);
        }
        return -1;
    }

    public void setBackGroundColor(int i, float f, float f2, float f3, float f4) {
        nativeSetBackGroundColor(i, this.mNativeMapengineInstance, f, f2, f3, f4);
    }

    public void setServiceViewRect(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        nativeSetServiceViewRect(i, this.mNativeMapengineInstance, i2, i3, i4, i5, i6, i7);
    }

    public boolean getSrvViewStateBoolValue(int i, int i2) {
        return nativeGetSrvViewStateBoolValue(i, this.mNativeMapengineInstance, i2);
    }

    public int getSrvViewStateIntValue(int i, int i2) {
        return nativeGetSrvViewStateIntValue(i, this.mNativeMapengineInstance, i2);
    }

    public void setSrvViewStateBoolValue(int i, int i2, boolean z) {
        nativeSetSrvViewStateBoolValue(i, this.mNativeMapengineInstance, i2, z);
    }

    public void setSrvViewStateIntValue(int i, int i2, int i3) {
        nativeSetSrvViewStateIntValue(i, this.mNativeMapengineInstance, i2, i3);
    }

    public boolean getControllerStateBoolValue(int i, int i2) {
        return nativeGetControllerStateBoolValue(i, this.mNativeMapengineInstance, i2);
    }

    public void setControllerStateBoolValue(int i, int i2, boolean z) {
        nativeSetControllerStateBoolValue(i, this.mNativeMapengineInstance, i2, z);
    }

    public int[] getMapModeState(int i, boolean z) {
        return nativeGetMapModeState(i, this.mNativeMapengineInstance, z);
    }

    public byte[] readMapPixels(int i, int i2, int i3, int i4, int i5) {
        return nativeReadMapPixels(i, this.mNativeMapengineInstance, i2, i3, i4, i5);
    }

    public boolean readMapPixelsToBmp(int i, Bitmap bitmap, int i2, int i3, int i4) {
        return nativeReadMapPixelsToBmp(i, this.mNativeMapengineInstance, bitmap, i2, i3, i4);
    }

    public boolean getIsProcessBuildingMark(int i) {
        return nativeGetIsProcessBuildingMark(i, this.mNativeMapengineInstance);
    }

    public boolean getMapDataTaskIsCancel(int i, long j) {
        return nativeGetMapDataTaskIsCancel(i, this.mNativeMapengineInstance, j);
    }

    public boolean taskCompressedResponse(int i, long j) {
        return nativeTaskCompressedResponse(i, this.mNativeMapengineInstance, j);
    }

    public void receiveNetData(int i, long j, byte[] bArr, int i2) {
        nativeReceiveNetData(i, this.mNativeMapengineInstance, bArr, j, i2);
    }

    public void finishDownLoad(int i, long j) {
        nativeFinishDownLoad(i, this.mNativeMapengineInstance, j);
    }

    public void netError(int i, long j, int i2) {
        nativeNetError(i, this.mNativeMapengineInstance, i2, j);
    }

    public void setServerAddress(String str) {
        nativeSetServerAddress(this.mNativeMapengineInstance, str);
    }

    public void setIndoorServerAddress(String str) {
        nativeSetIndoorServerAddress(this.mNativeMapengineInstance, str);
    }

    public void OnMapAnimationFinished(int i, byte[] bArr) {
        if (this.mMapListener != null && bArr != null) {
            aln aln = new aln();
            aln.a = amy.a(bArr, 0);
            aln.b = amy.a(bArr, 4);
            aln.c = amy.a(bArr, 8);
            this.mMapListener.onMapAnimationFinished(i, aln);
        }
    }

    public boolean isSimple3DShow(int i) {
        if (this.mNativeMapengineInstance != 0) {
            return nativeIsSimple3DShow(i, this.mNativeMapengineInstance);
        }
        return false;
    }

    public void setMapLoaderToTask(int i, long j, MapLoader mapLoader) {
        if (this.mNativeMapengineInstance != 0) {
            nativeSetRequestObjToTask(i, this.mNativeMapengineInstance, j, mapLoader);
        }
    }

    public void setMapViewFadeIn(int i, int i2) {
        if (isEngineCreated(i)) {
            nativeSetMapFadeIn(i, this.mNativeMapengineInstance, i2);
        }
    }

    public void setMapViewFadeOut(int i, int i2) {
        if (isEngineCreated(i)) {
            nativeSetMapFadeOut(i, this.mNativeMapengineInstance, i2);
        }
    }

    public boolean getMapFadeAnimOver(int i) {
        if (isEngineCreated(i)) {
            return nativeGetMapFadeAnimOver(i, this.mNativeMapengineInstance);
        }
        return true;
    }

    public void setGestureCenterType(int i, int i2) {
        this.mGestureCenterTypes.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public Point getGestureCenter(int i, int i2, int i3) {
        Integer num = this.mGestureCenterTypes.get(Integer.valueOf(i));
        StringBuilder sb = new StringBuilder("getGestureCenter: ");
        sb.append(i);
        sb.append(", ");
        sb.append(num);
        sb.append(", ");
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        ana.a();
        Point point = new Point(i2, i3);
        if (num != null && num.intValue() == 1) {
            point.x = -1;
            point.y = -1;
        }
        return point;
    }

    public byte[] GetConfigBuffer(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("mapcache/vmap4res/deviceprofile.data");
        File file = new File(sb.toString());
        int i = 0;
        if (!file.exists()) {
            try {
                InputStream open = this.mMapcoreListener.a().getAssets().open("map_assets/deviceprofile.data");
                int available = open.available();
                if (available == 0) {
                    return null;
                }
                byte[] bArr = new byte[available];
                while (i < available) {
                    i += open.read(bArr, i, available - i);
                }
                open.close();
                return bArr;
            } catch (IOException unused) {
                return null;
            } catch (OutOfMemoryError unused2) {
                return null;
            }
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                int available2 = fileInputStream.available();
                if (available2 == 0) {
                    return null;
                }
                byte[] bArr2 = new byte[available2];
                while (i < available2) {
                    i += fileInputStream.read(bArr2, i, available2 - i);
                }
                fileInputStream.close();
                return bArr2;
            } catch (IOException unused3) {
                return null;
            } catch (OutOfMemoryError unused4) {
                return null;
            }
        }
    }

    public long createCanvasView(int i) {
        return nativeCreateCanvasView(this.mNativeMapengineInstance, i);
    }

    public void destroyCanvasView(long j) {
        nativeDestroyCanvasView(this.mNativeMapengineInstance, j);
    }

    private void OnRunnableRun(Runnable runnable) {
        runnable.run();
    }

    public int createRenderDevice(GLDeviceAttribute gLDeviceAttribute) {
        return nativeCreateRenderDevice(this.mNativeMapengineInstance, gLDeviceAttribute);
    }

    public void destroyRenderDevice(int i) {
        nativeDestroyRenderDevice(this.mNativeMapengineInstance, i);
    }

    public void attachSurfaceToRenderDevice(int i, Surface surface, GLSurfaceAttribute gLSurfaceAttribute) {
        nativeAttachSurfaceToRenderDevice(this.mNativeMapengineInstance, i, surface, gLSurfaceAttribute);
    }

    public void attachSurfaceToRenderDeviceEx(int i, long j, GLSurfaceAttribute gLSurfaceAttribute) {
        nativeAttachSurfaceToRenderDeviceEx(this.mNativeMapengineInstance, i, j, gLSurfaceAttribute);
    }

    public void detachSurfaceFromRenderDevice(int i) {
        nativeDetachSurfaceFromRenderDevice(this.mNativeMapengineInstance, i);
    }

    public boolean bindMapEngineToRenderDevice(int i, int i2) {
        return nativeBindMapEngineToRenderDevice(this.mNativeMapengineInstance, i, i2);
    }

    public boolean unbindMapEngineFromRenderDevice(int i, int i2) {
        return nativeUnBindMapEngineFromRenderDevice(this.mNativeMapengineInstance, i, i2);
    }

    public void renderDeviceChanged(int i, Surface surface, GLSurfaceAttribute gLSurfaceAttribute) {
        nativeRenderDeviceChanged(this.mNativeMapengineInstance, i, surface, gLSurfaceAttribute);
    }

    public void renderDeviceChangedEx(int i, long j, GLSurfaceAttribute gLSurfaceAttribute) {
        nativeRenderDeviceChangedEx(this.mNativeMapengineInstance, i, j, gLSurfaceAttribute);
    }

    public void lockRenderDevice(int i) {
        nativeLockRenderDevice(this.mNativeMapengineInstance, i);
    }

    public void unlockRenderDevice(int i) {
        nativeUnLockRenderDevice(this.mNativeMapengineInstance, i);
    }

    public boolean isRenderDeviceLocked(int i) {
        return nativeIsRenderDeviceLocked(this.mNativeMapengineInstance, i);
    }

    public void freeScreenShotBuffer(int i, long j) {
        nativeFreeScreenShotBuffer(this.mNativeMapengineInstance, i, j);
    }

    public void setScreenShotCarType(int i, int i2) {
        nativeSetScreenShotCarType(this.mNativeMapengineInstance, i, i2);
    }

    public void setScreenShotMode(int i, int i2) {
        nativeSetScreenShotMode(this.mNativeMapengineInstance, i, i2);
    }

    public int getScreenShotMode(int i) {
        return nativeGetScreenShotMode(this.mNativeMapengineInstance, i);
    }

    public void setScreenShotCallBackMethod(int i, int i2) {
        nativeSetScreenShotCallBackMethod(this.mNativeMapengineInstance, i, i2);
    }

    public void setScreenShotRect(int i, int i2, int i3, int i4, int i5) {
        nativeSetScreenShotRect(this.mNativeMapengineInstance, i, i2, i3, i4, i5);
    }

    public void addScreenShotBitmapCache(int i, Bitmap bitmap) {
        nativeAddScreenShotBitmapCache(this.mNativeMapengineInstance, i, bitmap);
    }

    public void OnScreenShot(int i, int i2, byte[] bArr, long[] jArr, Bitmap bitmap) {
        this.mMapcoreListener.a(i, i2, jArr, bitmap);
    }

    public void setRenderFps(int i, int i2) {
        nativeSetRenderFps(this.mNativeMapengineInstance, i, i2);
    }

    public void setMaxFps(int i, int i2) {
        nativeSetMaxFps(this.mNativeMapengineInstance, i, i2);
    }

    /* access modifiers changed from: 0000 */
    public int getMaxFps(int i) {
        return nativeGetMaxFps(this.mNativeMapengineInstance, i);
    }

    public void setMinFps(int i, int i2) {
        nativeSetMinFps(this.mNativeMapengineInstance, i, i2);
    }

    /* access modifiers changed from: 0000 */
    public int getMinFps(int i) {
        return nativeGetMinFps(this.mNativeMapengineInstance, i);
    }

    public void setRenderFpsEx(int i, int i2, int i3) {
        nativeSetRenderFpsEx(this.mNativeMapengineInstance, i, i2, i3);
    }

    public int getRenderFps(int i) {
        return nativeGetRenderFps(this.mNativeMapengineInstance, i);
    }

    public void setRenderFpsWithTimer(int i, int i2, boolean z) {
        nativeSetRenderFpsWithTimer(this.mNativeMapengineInstance, i, i2, z);
    }

    public float getRealRenderFps(int i) {
        return nativeGetRealRenderFps(this.mNativeMapengineInstance, i);
    }

    public long getTotalRenderFrames(int i) {
        return nativeGetTotalRenderFrames(this.mNativeMapengineInstance, i);
    }

    public void sendToRenderEvent(int i, Runnable runnable) {
        nativeQueueEvent(this.mNativeMapengineInstance, i, runnable);
    }

    public void renderPauseIn(int i) {
        nativeRenderPause(this.mNativeMapengineInstance, i);
    }

    public void renderResumeIn(int i) {
        nativeRenderResume(this.mNativeMapengineInstance, i);
    }

    public boolean isRenderPauseIn(int i) {
        return nativeIsRenderPaused(this.mNativeMapengineInstance, i);
    }

    public void resetTickCount(int i, int i2) {
        nativeResetTickcount(this.mNativeMapengineInstance, i, i2);
    }

    public void OnRenderDeviceCreated(int i) {
        this.mMapcoreListener.b(i);
    }

    public void OnEGLSurfaceChanged(int i, int i2, int i3, int i4) {
        this.mMapcoreListener.a(i3, i, i2, i4);
    }

    public void OnRenderDeviceDestroyed(int i) {
        StringBuilder sb = new StringBuilder("OnRenderDeviceDestroyed: ");
        sb.append(i);
        sb.append(", ");
        sb.append(this.mStateSparseArray.size());
        sb.append(", ");
        sb.append(this.mStateSparseArray);
        sb.append(", ");
        sb.append(this);
        ana.a();
        if (this.mStateSparseArray != null) {
            int[] engineIDs = getEngineIDs(i);
            if (engineIDs != null && engineIDs.length > 0) {
                for (int remove : engineIDs) {
                    this.mStateSparseArray.remove(remove);
                }
            }
        }
        this.mMapcoreListener.c(i);
    }

    public void OnEGLRenderFrame(int i, int i2) {
        try {
            this.mMapcoreListener.b(i, i2);
        } catch (IllegalMonitorStateException unused) {
        }
    }

    public void OnEGLAutoDropFrame(int i, int i2, boolean z) {
        try {
            int max = Math.max(getAdapterRenderFps(i, z), i2);
            if (max > 0) {
                z = true;
            }
            setRenderFpsWithTimer(i, max, z);
        } catch (IllegalMonitorStateException unused) {
        }
    }

    public void OnRequireMapRender(int i, int i2, int i3) {
        if (i3 > 0) {
            setRenderFpsWithTimer(i, Math.max(i3, getAdapterRenderFps(i, true)), true);
        } else {
            setRenderFpsWithTimer(i, Math.max(getAdapterRenderFps(i, true), GLMapStaticValue.b), true);
        }
    }

    public void OnCreateBitmapFromGLSurface(int i) {
        this.mMapcoreListener.d(i);
    }

    public void InitGLThread() {
        nativeInitGLThread(this.mNativeMapengineInstance);
    }

    public void DestroyGLThread() {
        StringBuilder sb = new StringBuilder("DestroyGLThread: , ");
        sb.append(this.mStateSparseArray.size());
        sb.append(", ");
        sb.append(this.mStateSparseArray);
        sb.append(", ");
        sb.append(this);
        ana.a();
        nativeDestroyGLThread(this.mNativeMapengineInstance);
        if (this.mStateSparseArray != null) {
            this.mStateSparseArray.clear();
        }
        synchronized (this.mut_lock) {
            if (this.mNativeMapengineInstance != 0) {
                long j = this.mNativeMapengineInstance;
                this.mNativeMapengineInstance = 0;
                nativeDestroy(j);
            }
        }
    }

    public int getAdapterRenderFps(int i, boolean z) {
        int[] engineIDs = getEngineIDs(i);
        if (engineIDs == null || engineIDs.length <= 0) {
            return 0;
        }
        int length = engineIDs.length;
        int i2 = engineIDs[0];
        int i3 = -1;
        if (length >= 2) {
            i3 = engineIDs[1];
        }
        if (isInMapAction(i2) || this.mInUserAction) {
            return GLMapStaticValue.e;
        }
        WeatherAnimationState weatherAnimationState = getWeatherAnimationState(i2);
        if (weatherAnimationState != WeatherAnimationState.STOPPED && weatherAnimationState != WeatherAnimationState.UNKNOWN) {
            return GLMapStaticValue.f;
        }
        if (isInMapAnimation(i2) || !getMapFadeAnimOver(i3)) {
            return GLMapStaticValue.c;
        }
        int adviseFPS = getAdviseFPS(i2);
        if (adviseFPS > 0) {
            return adviseFPS;
        }
        if (!z) {
            return getMinFps(i);
        }
        return getMaxFps(i);
    }

    public void removeRouteName(long j, int i) {
        nativeRemoveRouteName(this.mNativeMapengineInstance, j, i);
    }

    public long getNativeMapViewInstance(int i) {
        return nativeGetNativeMapViewInstance(this.mNativeMapengineInstance, i);
    }

    public long getNativeMapControllerInstance() {
        return nativeGetNativeMapControllerInstance(this.mNativeMapengineInstance);
    }

    public long getAnimationObserver(int i) {
        return nativeGetAnimationObserver(this.mNativeMapengineInstance, i);
    }
}
