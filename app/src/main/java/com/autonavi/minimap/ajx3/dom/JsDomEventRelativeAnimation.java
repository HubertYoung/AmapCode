package com.autonavi.minimap.ajx3.dom;

public class JsDomEventRelativeAnimation extends JsDomEvent {
    public final long animationId;
    public final long nodeId;
    public final JsDomRelativeAnimation relativeAnimation;

    private native long nativeGetAnimationId(long j);

    private native long nativeGetNodeId(long j);

    private native long nativeGetRelativeAnimation(long j);

    JsDomEventRelativeAnimation(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        this.animationId = nativeGetAnimationId(j);
        long nativeGetRelativeAnimation = nativeGetRelativeAnimation(j);
        this.relativeAnimation = nativeGetRelativeAnimation != 0 ? new JsDomRelativeAnimation(nativeGetRelativeAnimation) : null;
    }
}
