package com.autonavi.jni.eyrie.amap.tbt.scene;

public class NaviScene {
    private long mShadow = 0;

    private static native void nativeDestroy(long j);

    private static native void nativeHide(long j);

    private static native long nativeInit(int i);

    private static native int nativeInitPage(long j, int i, int i2);

    private static native void nativeShow(long j);

    /* access modifiers changed from: protected */
    public int getPageType() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getSceneType() {
        return 0;
    }

    protected NaviScene(int i) {
        this.mShadow = nativeInit(i);
    }

    /* access modifiers changed from: protected */
    public int init(int i, int i2) {
        return nativeInitPage(this.mShadow, i, i2);
    }

    public void show() {
        nativeShow(this.mShadow);
    }

    public void hide() {
        nativeHide(this.mShadow);
    }

    public void destroy() {
        nativeDestroy(this.mShadow);
        this.mShadow = 0;
    }
}
