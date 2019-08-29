package com.autonavi.minimap.ajx3.dom;

public final class JsDomAnimation {
    public final Object keyFrames;
    public final Object options;

    private native Object nativeGetKeyFrames(long j);

    private native Object nativeGetOptions(long j);

    private native void nativeRelease(long j);

    JsDomAnimation(long j) {
        this.keyFrames = nativeGetKeyFrames(j);
        this.options = nativeGetOptions(j);
        nativeRelease(j);
    }
}
