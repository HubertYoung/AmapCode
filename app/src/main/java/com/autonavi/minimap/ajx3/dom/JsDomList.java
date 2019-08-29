package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;

public final class JsDomList {
    private AjxDomNode mFooter;
    private AjxDomNode mHeader;
    private long mId;
    private JsDomListSection[] mSections;
    private AjxDomNode[] mTemplates;

    private native long nativeGetId(long j);

    private native long nativeGetListData(long j);

    private native long nativeGetListStyle(long j);

    private native void nativeReleaseSelf(long j);

    JsDomList(long j) {
        this.mId = nativeGetId(j);
        long nativeGetListStyle = nativeGetListStyle(j);
        if (nativeGetListStyle != 0) {
            JsDomListStyle jsDomListStyle = new JsDomListStyle(nativeGetListStyle);
            JsDomNode header = jsDomListStyle.getHeader();
            AjxDomNode ajxDomNode = null;
            this.mHeader = header != null ? header.createAjxNode() : null;
            JsDomNode footer = jsDomListStyle.getFooter();
            this.mFooter = footer != null ? footer.createAjxNode() : ajxDomNode;
            this.mSections = jsDomListStyle.getListSection();
            JsDomListCell[] listCells = jsDomListStyle.getListCells();
            if (listCells != null) {
                int length = listCells.length;
                this.mTemplates = new AjxDomNode[length];
                for (int i = 0; i < length; i++) {
                    JsDomNode jsDomNode = listCells[i].getJsDomNode();
                    if (jsDomNode != null) {
                        this.mTemplates[i] = jsDomNode.createTemplateAjxNode();
                    }
                }
            }
        }
        long nativeGetListData = nativeGetListData(j);
        if (nativeGetListData != 0) {
            JsDomListSectionData[] sectionData = new JsDomListData(nativeGetListData).getSectionData();
            if (sectionData != null) {
                int length2 = sectionData.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.mSections[i2].setCells(sectionData[i2].getCellData());
                }
            }
        }
        nativeReleaseSelf(j);
    }

    public final long getId() {
        return this.mId;
    }

    @Nullable
    public final AjxDomNode getHeader() {
        return this.mHeader;
    }

    @Nullable
    public final AjxDomNode getFooter() {
        return this.mFooter;
    }

    @Nullable
    public final JsDomListSection[] getSections() {
        return this.mSections;
    }

    @Nullable
    public final AjxDomNode[] getTemplates() {
        return this.mTemplates;
    }
}
