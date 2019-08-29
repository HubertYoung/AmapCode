package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListTemplateProperty extends JsDomEventList {
    public final long nodeId;
    public final JsDomProperty prop;
    public final long templateId;

    private native long nativeGetNodeId(long j);

    private native long nativeGetProp(long j);

    private native long nativeGetTemplateId(long j);

    JsDomEventListTemplateProperty(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        this.templateId = nativeGetTemplateId(j);
        long nativeGetProp = nativeGetProp(j);
        this.prop = nativeGetProp != 0 ? new JsDomProperty(nativeGetProp) : null;
    }
}
