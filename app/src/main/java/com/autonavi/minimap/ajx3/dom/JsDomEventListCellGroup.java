package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListCellGroup extends JsDomEventList {
    public final JsDomEventListCellGroup group;

    private native long nativeGetGroups(long j);

    JsDomEventListCellGroup(int i, long j) {
        super(i, j);
        long nativeGetGroups = nativeGetGroups(j);
        this.group = nativeGetGroups != 0 ? new JsDomEventListCellGroup(i, nativeGetGroups) : null;
    }
}
