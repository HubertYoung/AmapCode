package com.autonavi.jni.eyrie.amap.tbt.scene.basemap;

import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.scene.NaviBaseScene;

public class NaviBasemapMain extends NaviBaseScene {
    private static final int PAGETYPE_BASEMAP_MAIN = 18;
    private static final int SCENETYPE_BASEMAP = 12;

    private static native void nativeSendCommand(int i, int i2);

    private static native void nativeSendCommandWithInfo(int i, int i2, String str);

    private NaviBasemapMain(int i, int i2) {
        super(i, i);
    }

    public void sendCommand(int i, String str) {
        nativeSendCommandWithInfo(this.mSceneCode, i, str);
    }

    public void sendCommand(int i) {
        nativeSendCommand(this.mSceneCode, i);
    }

    public static NaviBasemapMain create() {
        int createAndInitScene = NaviManager.createAndInitScene(12, 18, 2);
        return new NaviBasemapMain(createAndInitScene, createAndInitScene);
    }
}
