package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListInit extends JsDomEventList {
    public final JsDomList list;
    public final JsDomNode node;

    private native long nativeGetList(long j);

    private native long nativeGetNode(long j);

    JsDomEventListInit(int i, long j) {
        super(i, j);
        long nativeGetNode = nativeGetNode(j);
        JsDomList jsDomList = null;
        this.node = nativeGetNode != 0 ? new JsDomNode(nativeGetNode) : null;
        long nativeGetList = nativeGetList(j);
        this.list = nativeGetList != 0 ? new JsDomList(nativeGetList) : jsDomList;
    }
}
