package com.autonavi.jni.ae.dice;

import com.autonavi.jni.ae.dice.tbt.GuideResReader;
import com.autonavi.jni.ae.dice.tbt.RouteResReader;
import com.autonavi.jni.ae.dice.tbt.TBTResReader;

public class NaviEngine {
    private static GuideResReader mGuideResReader;
    private static RouteResReader mRouteResReader;
    private static TBTResReader mTBTResReader;

    private static native boolean nativeClearCache();

    private static native void nativeDestroy();

    private static native String nativeGetBlDiceVersion();

    private static native String nativeGetLibDiceSoVersion();

    private static native String nativeGetSdkVersion();

    private static native boolean nativeInit(InitConfig initConfig);

    private static native boolean nativeOnCloudConfigUpdate(String str, String str2);

    private static native void nativeUnInit();

    private static native boolean nativeUninitNaviManager();

    public static boolean init(InitConfig initConfig) {
        if (initConfig != null) {
            mGuideResReader = new GuideResReader(initConfig.mContext);
            mRouteResReader = new RouteResReader(initConfig.mContext);
            initConfig.mGuideResReaderPtr = mGuideResReader.getPtr();
            initConfig.mRouteResReaderPtr = mRouteResReader.getPtr();
            mTBTResReader = new TBTResReader(initConfig.mContext);
            initConfig.mTBTResReaderPtr = mTBTResReader.getPtr();
        }
        return nativeInit(initConfig);
    }

    public static String getSdkVersion() {
        return nativeGetSdkVersion();
    }

    public static void destroy() {
        nativeDestroy();
    }

    public static void unInit() {
        nativeUnInit();
        if (mGuideResReader != null) {
            mGuideResReader.release();
            mGuideResReader = null;
        }
        if (mRouteResReader != null) {
            mRouteResReader.release();
            mRouteResReader = null;
        }
        if (mTBTResReader != null) {
            mTBTResReader.release();
            mTBTResReader = null;
        }
    }

    public static String getLibDiceSoVersion() {
        return nativeGetLibDiceSoVersion();
    }

    public static String getBlDiceVersion() {
        return nativeGetBlDiceVersion();
    }

    public static boolean uninitNaviManager() {
        return nativeUninitNaviManager();
    }

    public static boolean clearCache() {
        return nativeClearCache();
    }

    public static boolean onCloudConfigUpdate(String str, String str2) {
        return nativeOnCloudConfigUpdate(str, str2);
    }
}
