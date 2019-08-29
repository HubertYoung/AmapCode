package com.autonavi.jni.eyrie.amap.tbt.scene;

public class NaviBaseScene {
    public static final int NullSceneCode = -1;
    public static final int NullSceneID = -1;
    protected int mSceneCode = -1;
    protected int mSceneID = -1;

    private static native void nativeDestroy(int i);

    private static native void nativeHide(int i);

    private static native void nativeShow(int i);

    public NaviBaseScene() {
    }

    public NaviBaseScene(int i, int i2) {
        this.mSceneID = i;
        this.mSceneCode = i2;
    }

    public void show() {
        nativeShow(this.mSceneID);
    }

    public void hide() {
        nativeHide(this.mSceneID);
    }

    public void destroy() {
        nativeDestroy(this.mSceneID);
        this.mSceneID = -1;
        this.mSceneCode = -1;
    }
}
