package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeAdd extends JsDomEventNode {
    public final long parentNodeId;

    private native long nativeGetParentNodeId(long j);

    JsDomEventNodeAdd(int i, long j) {
        super(i, j);
        this.parentNodeId = nativeGetParentNodeId(j);
    }
}
