package com.autonavi.minimap.ajx3.dom;

public final class JsDomListSectionData {
    private JsDomListCellData[] mCellData;

    private native long[] nativeGetCellDatas(long j);

    private native void nativeReleaseSelf(long j);

    JsDomListSectionData(long j) {
        long[] nativeGetCellDatas = nativeGetCellDatas(j);
        if (nativeGetCellDatas == null || nativeGetCellDatas.length <= 0) {
            this.mCellData = null;
        } else {
            this.mCellData = new JsDomListCellData[nativeGetCellDatas.length];
            for (int i = 0; i < nativeGetCellDatas.length; i++) {
                this.mCellData[i] = new JsDomListCellData(nativeGetCellDatas[i]);
            }
        }
        nativeReleaseSelf(j);
    }

    public final JsDomListCellData[] getCellData() {
        return this.mCellData;
    }
}
