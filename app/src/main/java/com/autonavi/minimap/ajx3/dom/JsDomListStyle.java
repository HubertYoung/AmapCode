package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
final class JsDomListStyle {
    private JsDomListCell[] mCells;
    private JsDomNode mFooter;
    private JsDomNode mHeader;
    private JsDomListSection[] mSections;

    private native long nativeGetFooter(long j);

    private native long nativeGetHeader(long j);

    private native long[] nativeGetListCells(long j);

    private native long[] nativeGetListSections(long j);

    private native void nativeReleaseSelf(long j);

    JsDomListStyle(long j) {
        init(j);
        nativeReleaseSelf(j);
    }

    private void init(long j) {
        long nativeGetHeader = nativeGetHeader(j);
        this.mHeader = nativeGetHeader != 0 ? new JsDomNode(nativeGetHeader) : null;
        long nativeGetFooter = nativeGetFooter(j);
        this.mFooter = nativeGetFooter != 0 ? new JsDomNode(nativeGetFooter) : null;
        long[] nativeGetListCells = nativeGetListCells(j);
        if (nativeGetListCells == null || nativeGetListCells.length <= 0) {
            this.mCells = null;
        } else {
            this.mCells = new JsDomListCell[nativeGetListCells.length];
            for (int i = 0; i < nativeGetListCells.length; i++) {
                this.mCells[i] = new JsDomListCell(nativeGetListCells[i]);
            }
        }
        long[] nativeGetListSections = nativeGetListSections(j);
        if (nativeGetListSections == null || nativeGetListSections.length <= 0) {
            this.mSections = null;
            return;
        }
        this.mSections = new JsDomListSection[nativeGetListSections.length];
        for (int i2 = 0; i2 < nativeGetListSections.length; i2++) {
            this.mSections[i2] = new JsDomListSection(nativeGetListSections[i2]);
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final JsDomListCell[] getListCells() {
        return this.mCells;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final JsDomListSection[] getListSection() {
        return this.mSections;
    }

    @Nullable
    public final JsDomNode getHeader() {
        return this.mHeader;
    }

    @Nullable
    public final JsDomNode getFooter() {
        return this.mFooter;
    }
}
