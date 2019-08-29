package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeSnapshot extends JsDomEventNode {
    public final long nodeId;
    public final String option;

    private native long nativeGetNodeId(long j);

    private native String nativeGetOption(long j);

    JsDomEventNodeSnapshot(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        this.option = nativeGetOption(j);
    }
}
