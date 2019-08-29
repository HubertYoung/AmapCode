package com.ant.phone.xmedia.algorithm;

import com.alipay.mobile.common.utils.load.LibraryLoadUtils;
import tv.danmaku.ijk.media.player.IjkLibLoader;

/* compiled from: TrackAlgorithm */
final class e implements IjkLibLoader {
    final /* synthetic */ TrackAlgorithm a;

    e(TrackAlgorithm this$0) {
        this.a = this$0;
    }

    public final void loadLibrary(String s) {
        LibraryLoadUtils.loadLibrary(s, false);
    }
}
