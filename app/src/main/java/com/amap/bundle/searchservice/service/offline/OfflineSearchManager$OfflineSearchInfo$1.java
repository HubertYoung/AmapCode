package com.amap.bundle.searchservice.service.offline;

import com.amap.bundle.searchservice.callback.AbsSearchCallBack;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;

public class OfflineSearchManager$OfflineSearchInfo$1 extends AbsSearchCallBack {
    final /* synthetic */ a a;

    public OfflineSearchManager$OfflineSearchInfo$1(a aVar) {
        this.a = aVar;
    }

    public void callback(InfoliteResult infoliteResult) {
        if (!this.a.d) {
            this.a.b.callback(infoliteResult);
        }
    }

    public void error(Throwable th, boolean z) {
        if (!this.a.d) {
            this.a.b.error(th, z);
        }
    }

    public void error(int i, String str) {
        if (!this.a.d) {
            this.a.b.error(i, str);
        }
    }
}
