package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListDataAttribute extends JsDomEventListData {
    public final JsAttribute attr;

    private native long nativeGetAttr(long j);

    JsDomEventListDataAttribute(int i, long j) {
        super(i, j);
        long nativeGetAttr = nativeGetAttr(j);
        this.attr = nativeGetAttr != 0 ? new JsAttribute(nativeGetAttr) : null;
    }
}
