package com.autonavi.minimap.ajx3.dom;

public class JsDomEventScrollIntoView extends JsDomEvent {
    public final long nodeId;
    public final JsDomScrollIntoView scrollIntoView;

    private native long nativeGetNodeId(long j);

    private native long nativeGetScrollIntoView(long j);

    JsDomEventScrollIntoView(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        long nativeGetScrollIntoView = nativeGetScrollIntoView(j);
        this.scrollIntoView = nativeGetScrollIntoView != 0 ? new JsDomScrollIntoView(nativeGetScrollIntoView) : null;
    }
}
