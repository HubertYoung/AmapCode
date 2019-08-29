package com.autonavi.minimap.ajx3.dom;

public class JsDomEventAnimation extends JsDomEvent {
    public final JsDomAnimation animation;
    public final long animationId;
    public final long nodeId;

    private native long nativeGetAnimation(long j);

    private native long nativeGetAnimationId(long j);

    private native long nativeGetNodeId(long j);

    JsDomEventAnimation(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        this.animationId = nativeGetAnimationId(j);
        long nativeGetAnimation = nativeGetAnimation(j);
        this.animation = nativeGetAnimation != 0 ? new JsDomAnimation(nativeGetAnimation) : null;
    }
}
