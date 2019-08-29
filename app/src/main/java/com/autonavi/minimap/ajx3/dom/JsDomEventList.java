package com.autonavi.minimap.ajx3.dom;

public class JsDomEventList extends JsDomEvent {
    public final long listId;

    private native long nativeGetListId(long j);

    JsDomEventList(int i, long j) {
        super(i, j);
        this.listId = nativeGetListId(j);
    }
}
