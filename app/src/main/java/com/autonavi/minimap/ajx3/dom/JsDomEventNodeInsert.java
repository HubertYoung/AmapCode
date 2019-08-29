package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeInsert extends JsDomEventNode {
    public final long beforeNodeId;
    public final long parentNodeId;

    private native long nativeGetBeforeNodeId(long j);

    private native long nativeGetParentNodeId(long j);

    JsDomEventNodeInsert(int i, long j) {
        super(i, j);
        this.parentNodeId = nativeGetParentNodeId(j);
        this.beforeNodeId = nativeGetBeforeNodeId(j);
    }
}
