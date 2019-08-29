package com.autonavi.minimap.ajx3.dom;

public class JsDomEventNodeProperty extends JsDomEvent {
    public final long nodeId;
    public final JsDomProperty prop;

    private native long nativeGetNodeId(long j);

    private native long nativeGetProp(long j);

    JsDomEventNodeProperty(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        long nativeGetProp = nativeGetProp(j);
        this.prop = nativeGetProp != 0 ? new JsDomProperty(nativeGetProp) : null;
    }
}
