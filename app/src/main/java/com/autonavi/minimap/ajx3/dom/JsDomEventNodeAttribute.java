package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeAttribute extends JsDomEvent {
    public final JsAttribute attr;
    public final long nodeId;

    private native long nativeGetAttr(long j);

    private native long nativeGetNodeId(long j);

    JsDomEventNodeAttribute(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        long nativeGetAttr = nativeGetAttr(j);
        this.attr = nativeGetAttr != 0 ? new JsAttribute(nativeGetAttr) : null;
    }
}
