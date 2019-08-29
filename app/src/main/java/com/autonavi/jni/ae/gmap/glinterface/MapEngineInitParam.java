package com.autonavi.jni.ae.gmap.glinterface;

import com.autonavi.jni.ae.gmap.utils.GLMapUtil;

public class MapEngineInitParam {
    private int mAsyncTaskThreadCount = 0;
    private float mCacheCountFactor = 0.0f;
    private String mConfigTableName = GLMapUtil.CONFIG_TABLE_NAME_MAIN;
    private boolean mIsFirstFrameCompleteBehaviorLog = false;
    private boolean mIsInitBehaviorLog = false;
    private boolean mIsMainMap = false;
    private boolean mIsOnlyResponseClickGesture = false;
    private boolean mIsSelfSizeAdaptive = true;
    private boolean mIsSetBkTexture = false;
    private boolean mIsSupportIrregularShape = false;
    private String mMapProfileTableName = GLMapUtil.MAPPROFILE_TABLE_NAME_MAIN;
    private int mZOrder = 0;

    public static MapEngineInitParam MainMapInitParam() {
        MapEngineInitParam mapEngineInitParam = new MapEngineInitParam();
        mapEngineInitParam.mIsOnlyResponseClickGesture = false;
        mapEngineInitParam.mIsSelfSizeAdaptive = true;
        mapEngineInitParam.mIsMainMap = true;
        mapEngineInitParam.mIsInitBehaviorLog = true;
        mapEngineInitParam.mIsFirstFrameCompleteBehaviorLog = true;
        mapEngineInitParam.mConfigTableName = GLMapUtil.CONFIG_TABLE_NAME_MAIN;
        mapEngineInitParam.mMapProfileTableName = GLMapUtil.MAPPROFILE_TABLE_NAME_MAIN;
        mapEngineInitParam.mZOrder = 0;
        mapEngineInitParam.mIsSetBkTexture = true;
        mapEngineInitParam.mAsyncTaskThreadCount = 3;
        mapEngineInitParam.mCacheCountFactor = 2.0f;
        mapEngineInitParam.mIsSupportIrregularShape = false;
        return mapEngineInitParam;
    }

    public static MapEngineInitParam EagleEyeMapInitParam() {
        MapEngineInitParam mapEngineInitParam = new MapEngineInitParam();
        mapEngineInitParam.mIsOnlyResponseClickGesture = true;
        mapEngineInitParam.mIsSelfSizeAdaptive = false;
        mapEngineInitParam.mIsMainMap = false;
        mapEngineInitParam.mIsInitBehaviorLog = false;
        mapEngineInitParam.mIsFirstFrameCompleteBehaviorLog = false;
        mapEngineInitParam.mConfigTableName = GLMapUtil.CONFIG_TABLE_NAME_EAGLE_EYE;
        mapEngineInitParam.mMapProfileTableName = GLMapUtil.MAPPROFILE_TABLE_NAME_MAIN;
        mapEngineInitParam.mZOrder = 0;
        mapEngineInitParam.mIsSetBkTexture = false;
        mapEngineInitParam.mAsyncTaskThreadCount = 2;
        mapEngineInitParam.mCacheCountFactor = 1.0f;
        mapEngineInitParam.mIsSupportIrregularShape = true;
        return mapEngineInitParam;
    }

    public static MapEngineInitParam ExternalMapInitParam() {
        MapEngineInitParam mapEngineInitParam = new MapEngineInitParam();
        mapEngineInitParam.mIsOnlyResponseClickGesture = false;
        mapEngineInitParam.mIsSelfSizeAdaptive = true;
        mapEngineInitParam.mIsMainMap = false;
        mapEngineInitParam.mIsInitBehaviorLog = false;
        mapEngineInitParam.mIsFirstFrameCompleteBehaviorLog = false;
        mapEngineInitParam.mConfigTableName = GLMapUtil.CONFIG_TABLE_NAME_MAIN;
        mapEngineInitParam.mMapProfileTableName = GLMapUtil.MAPPROFILE_TABLE_NAME_MINI;
        mapEngineInitParam.mZOrder = 0;
        mapEngineInitParam.mIsSetBkTexture = true;
        mapEngineInitParam.mAsyncTaskThreadCount = 3;
        mapEngineInitParam.mCacheCountFactor = 2.0f;
        mapEngineInitParam.mIsSupportIrregularShape = false;
        return mapEngineInitParam;
    }
}
