package com.autonavi.jni.ae.dice;

import android.content.Context;

public class InitConfig {
    public String mAdiu;
    public String mCachePath;
    public String mConfigFileContent;
    public String mConfigPath;
    public Context mContext;
    public boolean mDebugConstant;
    public String mDeviceID;
    public long mGuideResReaderPtr;
    public boolean mIncludeGuide;
    public boolean mIncludeMap;
    public boolean mIncludePos;
    public boolean mIncludeRoute;
    public boolean mIncludeSearch;
    public LocModeType mLocModeType;
    public ELogLevel mLogLevel;
    public boolean mLogMaskAll;
    public String mLogTag;
    public String mMotorPassword;
    public String mMotorUserCode;
    public String mOfflineDataPath;
    public String mOnlineDataPath;
    public String mP3dCrossPath;
    public String mPassword;
    public String mRootPath;
    public long mRouteResReaderPtr;
    public long mTBTResReaderPtr;
    public String mTid;
    public long mUiWorkerPtr;
    public String mUserBatch;
    public String mUserCode;
    public int mWormHoleFlag;

    public enum ELogLevel {
        LOG_LEVEL_NONE(0),
        LOG_LEVEL_FATAL(1),
        LOG_LEVEL_ERROR(2),
        LOG_LEVEL_WARNING(3),
        LOG_LEVEL_INFO(4),
        LOG_LEVEL_DEBUG(5),
        LOG_LEVEL_VERBOSE(6);
        
        private int mLevel;

        private ELogLevel(int i) {
            this.mLevel = i;
        }
    }

    public InitConfig() {
        this(true, true, true, true, true, new LocModeType());
    }

    public InitConfig(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, LocModeType locModeType) {
        this.mUiWorkerPtr = 0;
        this.mIncludePos = z;
        this.mIncludeRoute = z2;
        this.mIncludeGuide = z3;
        this.mIncludeSearch = z4;
        this.mIncludeMap = z5;
        this.mLogLevel = ELogLevel.LOG_LEVEL_NONE;
        this.mLogMaskAll = true;
        this.mLogTag = "";
        this.mDebugConstant = false;
        this.mLocModeType = locModeType;
        this.mOnlineDataPath = "";
        this.mUserCode = "";
        this.mUserBatch = "";
        this.mDeviceID = "";
        this.mPassword = "";
        this.mMotorUserCode = "";
        this.mMotorPassword = "";
        this.mCachePath = "";
        this.mWormHoleFlag = 1;
        this.mGuideResReaderPtr = 0;
        this.mRouteResReaderPtr = 0;
        this.mTBTResReaderPtr = 0;
        this.mContext = null;
    }
}
