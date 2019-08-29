package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeRemove extends JsDomEventNode {
    public final long nodeId;
    public final long parentNodeId;

    private native long nativeGetNodeId(long j);

    private native long nativeGetParentNodeId(long j);

    JsDomEventNodeRemove(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        this.parentNodeId = nativeGetParentNodeId(j);
    }
}
