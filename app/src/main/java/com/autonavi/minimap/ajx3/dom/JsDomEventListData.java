package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListData extends JsDomEventList {
    public final int dataIndex;
    public final long nodeId;
    public final int sectionIndex;

    private native int nativeGetDataIndex(long j);

    private native long nativeGetNodeId(long j);

    private native int nativeGetSectionIndex(long j);

    JsDomEventListData(int i, long j) {
        super(i, j);
        this.sectionIndex = nativeGetSectionIndex(j);
        this.dataIndex = nativeGetDataIndex(j);
        this.nodeId = nativeGetNodeId(j);
    }
}
