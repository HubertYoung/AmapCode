package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListTemplateAttribute extends JsDomEventList {
    public final JsAttribute attr;
    public final long nodeId;
    public final long templateId;

    private native long nativeGetAttr(long j);

    private native long nativeGetNodeId(long j);

    private native long nativeGetTemplateId(long j);

    JsDomEventListTemplateAttribute(int i, long j) {
        super(i, j);
        this.nodeId = nativeGetNodeId(j);
        this.templateId = nativeGetTemplateId(j);
        long nativeGetAttr = nativeGetAttr(j);
        this.attr = nativeGetAttr != 0 ? new JsAttribute(nativeGetAttr) : null;
    }
}
