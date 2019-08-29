package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.HashMap;

public final class JsDomListSection {
    private HashMap<String, String> mAttribute;
    private JsDomListCellData[] mCells;
    private JsDomNode mFooter;
    private JsDomNode mHeader;
    private long mShadow;

    private native int nativeGetAttributeCount(long j);

    private native String nativeGetAttributeKey(long j, int i);

    private native String nativeGetAttributeValue(long j, int i);

    private native long nativeGetFooter(long j);

    private native long nativeGetHeader(long j);

    private native void nativeReleaseSelf(long j);

    JsDomListSection(long j) {
        this.mShadow = j;
        long nativeGetHeader = nativeGetHeader(j);
        JsDomNode jsDomNode = null;
        this.mHeader = nativeGetHeader != 0 ? new JsDomNode(nativeGetHeader) : null;
        long nativeGetFooter = nativeGetFooter(j);
        this.mFooter = nativeGetFooter != 0 ? new JsDomNode(nativeGetFooter) : jsDomNode;
        initAttributes();
        nativeReleaseSelf(j);
    }

    public final String getAttributeValue(String str) {
        if (TextUtils.isEmpty(str) || this.mAttribute == null) {
            return "";
        }
        return this.mAttribute.containsKey(str) ? this.mAttribute.get(str) : "";
    }

    private void initAttributes() {
        int attributeCount = getAttributeCount();
        if (attributeCount > 0) {
            this.mAttribute = new HashMap<>();
            for (int i = 0; i < attributeCount; i++) {
                this.mAttribute.put(getAttributeKey(i), getAttributeValue(i));
            }
        }
    }

    @Nullable
    public final JsDomNode getHeader() {
        return this.mHeader;
    }

    @Nullable
    public final JsDomNode getFooter() {
        return this.mFooter;
    }

    public final void setCells(JsDomListCellData[] jsDomListCellDataArr) {
        this.mCells = jsDomListCellDataArr;
    }

    @Nullable
    public final JsDomListCellData[] getCells() {
        return this.mCells;
    }

    private int getAttributeCount() {
        return nativeGetAttributeCount(this.mShadow);
    }

    private String getAttributeKey(int i) {
        return nativeGetAttributeKey(this.mShadow, i);
    }

    public final String getAttributeValue(int i) {
        return nativeGetAttributeValue(this.mShadow, i);
    }
}
