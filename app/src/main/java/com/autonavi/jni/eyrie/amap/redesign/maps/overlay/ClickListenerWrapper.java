package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

class ClickListenerWrapper {
    private IClickListener mInnerListener;
    private long mNativePtr = nativeCreateInstance(this);
    private final BaseOverlay mOverlay;

    private static native long nativeCreateInstance(ClickListenerWrapper clickListenerWrapper);

    private static native void nativeDestroyInstance(long j);

    ClickListenerWrapper(BaseOverlay baseOverlay) {
        this.mOverlay = baseOverlay;
        this.mInnerListener = null;
    }

    /* access modifiers changed from: 0000 */
    public void release() {
        this.mInnerListener = null;
        nativeDestroyInstance(this.mNativePtr);
        this.mNativePtr = 0;
    }

    /* access modifiers changed from: 0000 */
    public void setInnerListener(IClickListener iClickListener) {
        this.mInnerListener = iClickListener;
    }

    public void onClick(int i, int i2, int i3) {
        if (this.mInnerListener != null) {
            this.mInnerListener.onClick(this.mOverlay, this.mOverlay.getItemById(i2), i3);
        }
    }
}
