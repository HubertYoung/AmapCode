package com.autonavi.jni.eyrie.amap.tbt.scene;

public class NaviBusiness extends NaviScene {
    private static final int PAGETYPE_COMMONPAGE = 1000;
    private static final int SCENETYPE_COMMONSCENE = 1000;
    private long mShadow = 0;

    private static native long nativeCreateObj(int i);

    private static native void nativeDestroyObj(long j);

    private static native void nativeSendCommand(long j, int i);

    private static native void nativeSendCommandWithInfo(long j, int i, String str);

    /* access modifiers changed from: protected */
    public int getPageType() {
        return 1000;
    }

    /* access modifiers changed from: protected */
    public int getSceneType() {
        return 1000;
    }

    public NaviBusiness(int i) {
        super(i);
        initScene();
    }

    /* access modifiers changed from: protected */
    public void initScene() {
        this.mShadow = nativeCreateObj(init(getSceneType(), getPageType()));
    }

    public void sendCommand(int i, String str) {
        nativeSendCommandWithInfo(this.mShadow, i, str);
    }

    public void sendCommand(int i) {
        nativeSendCommand(this.mShadow, i);
    }

    public void destroy() {
        super.destroy();
        nativeDestroyObj(this.mShadow);
        this.mShadow = 0;
    }
}
