package com.autonavi.minimap.ajx3.dom;

public final class JsDomAnimationSet {
    public final long[] childAnimationIds;
    public final int size;

    private native long[] nativeGetChildAnimationIds(long j);

    private native int nativeGetSize(long j);

    private native void nativeRelease(long j);

    JsDomAnimationSet(long j) {
        this.childAnimationIds = nativeGetChildAnimationIds(j);
        this.size = nativeGetSize(j);
        nativeRelease(j);
    }
}
