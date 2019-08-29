package com.autonavi.minimap.ajx3.dom;

import android.support.annotation.Nullable;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
final class JsDomListData {
    private JsDomListSectionData[] mSectionData;

    private native long[] nativeGetSectionData(long j);

    private native void nativeReleaseSelf(long j);

    JsDomListData(long j) {
        long[] nativeGetSectionData = nativeGetSectionData(j);
        if (nativeGetSectionData == null || nativeGetSectionData.length <= 0) {
            this.mSectionData = null;
        } else {
            this.mSectionData = new JsDomListSectionData[nativeGetSectionData.length];
            for (int i = 0; i < nativeGetSectionData.length; i++) {
                this.mSectionData[i] = new JsDomListSectionData(nativeGetSectionData[i]);
            }
        }
        nativeReleaseSelf(j);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final JsDomListSectionData[] getSectionData() {
        return this.mSectionData;
    }
}
