package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeReplace extends JsDomEventNode {
    public final long oldNodeId;
    public final long parentNodeId;

    private native long nativeGetOldNodeId(long j);

    private native long nativeGetParentNodeId(long j);

    JsDomEventNodeReplace(int i, long j) {
        super(i, j);
        this.parentNodeId = nativeGetParentNodeId(j);
        this.oldNodeId = nativeGetOldNodeId(j);
    }
}
