package com.autonavi.minimap.ajx3.dom;

public class JsDomEventListSection extends JsDomEventList {
    public final JsDomListSection section;
    public final JsDomListSectionData sectionData;
    public final int sectionIndex;

    private native long nativeGetSection(long j);

    private native long nativeGetSectionData(long j);

    private native int nativeGetSectionIndex(long j);

    JsDomEventListSection(int i, long j) {
        super(i, j);
        this.sectionIndex = nativeGetSectionIndex(j);
        long nativeGetSection = nativeGetSection(j);
        JsDomListSectionData jsDomListSectionData = null;
        this.section = nativeGetSection != 0 ? new JsDomListSection(nativeGetSection) : null;
        long nativeGetSectionData = nativeGetSectionData(j);
        this.sectionData = nativeGetSectionData != 0 ? new JsDomListSectionData(nativeGetSectionData) : jsDomListSectionData;
    }
}
