package com.autonavi.minimap.offline.inter.inner;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface DelOnlineFilesObserver {
    void onDelOnlineFileDone();
}
