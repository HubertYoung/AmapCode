package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListDataProperty extends JsDomEventListData {
    public final JsDomProperty prop;

    private native long nativeGetProp(long j);

    JsDomEventListDataProperty(int i, long j) {
        super(i, j);
        long nativeGetProp = nativeGetProp(j);
        this.prop = nativeGetProp != 0 ? new JsDomProperty(nativeGetProp) : null;
    }
}
