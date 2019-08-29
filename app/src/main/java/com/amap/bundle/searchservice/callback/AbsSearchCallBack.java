package com.amap.bundle.searchservice.callback;

import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public class AbsSearchCallBack extends aec {
    private OfflineSearchMode mOfflineSearchMode;

    public void error(int i, String str) {
    }

    public void error(Throwable th, boolean z) {
    }

    public void callback(InfoliteResult infoliteResult) {
        super.callback(infoliteResult);
    }

    public void setOfflineSearchMode(OfflineSearchMode offlineSearchMode) {
        this.mOfflineSearchMode = offlineSearchMode;
    }

    public OfflineSearchMode getOfflineSearchMode() {
        return this.mOfflineSearchMode;
    }
}
