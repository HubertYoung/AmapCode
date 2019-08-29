package com.autonavi.minimap.ajx3.dom;

public class JsDomEventAnimationGroup extends JsDomEvent {
    public final JsDomAnimationSet animationSet;
    public final long groupAnimationId;

    private native long nativeGetAnimationSet(long j);

    private native long nativeGetGroupAnimationId(long j);

    JsDomEventAnimationGroup(int i, long j) {
        super(i, j);
        this.groupAnimationId = nativeGetGroupAnimationId(j);
        long nativeGetAnimationSet = nativeGetAnimationSet(j);
        this.animationSet = nativeGetAnimationSet != 0 ? new JsDomAnimationSet(nativeGetAnimationSet) : null;
    }
}
