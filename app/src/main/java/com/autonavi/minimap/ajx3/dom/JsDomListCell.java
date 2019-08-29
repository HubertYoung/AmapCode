package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
final class JsDomListCell {
    private JsDomNode mJsDomNode;

    private native long nativeGetJsDomNode(long j);

    private native void nativeReleaseSelf(long j);

    JsDomListCell(long j) {
        long nativeGetJsDomNode = nativeGetJsDomNode(j);
        this.mJsDomNode = nativeGetJsDomNode != 0 ? new JsDomNode(nativeGetJsDomNode) : null;
        nativeReleaseSelf(j);
    }

    @Nullable
    public final JsDomNode getJsDomNode() {
        return this.mJsDomNode;
    }
}
