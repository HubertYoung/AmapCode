package com.autonavi.minimap.ajx3.dom;

public class JsDomRelativeAnimation {
    public final String keyFrames;
    public final int option;

    private native Object nativeGetKeyFrames(long j);

    private native int nativeGetOption(long j);

    private native void nativeRelease(long j);

    JsDomRelativeAnimation(long j) {
        Object nativeGetKeyFrames = nativeGetKeyFrames(j);
        this.keyFrames = nativeGetKeyFrames != null ? nativeGetKeyFrames.toString() : null;
        this.option = nativeGetOption(j);
        nativeRelease(j);
    }
}
