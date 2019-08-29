package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListCellData extends JsDomEventListData {
    public final JsDomListCellData cellData;

    private native long nativeGetCellData(long j);

    JsDomEventListCellData(int i, long j) {
        super(i, j);
        long nativeGetCellData = nativeGetCellData(j);
        this.cellData = nativeGetCellData != 0 ? new JsDomListCellData(nativeGetCellData) : null;
    }
}
