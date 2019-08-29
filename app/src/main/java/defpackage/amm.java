package defpackage;

/* renamed from: amm reason: default package */
/* compiled from: MapSurfaceListener */
public interface amm {
    void onDrawFrameFirst(int i);

    void onDrawFrameFirstOnGLThread(int i);

    void onRenderDeviceCreated(int i);

    void onRenderDeviceDestroyed(int i);

    void onSurfaceChanged(int i, int i2, int i3, int i4);

    void onSurfaceCreated(int i);

    void onSurfaceDestroy(int i);

    void onSurfaceRenderFrame(int i, int i2);
}
