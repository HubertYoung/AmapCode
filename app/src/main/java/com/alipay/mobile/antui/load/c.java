package com.alipay.mobile.antui.load;

import com.alipay.mobile.antui.load.AbsLoadingView.LoadingListener;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AURefreshListView */
final class c implements LoadingListener {
    final /* synthetic */ AURefreshListView a;

    c(AURefreshListView this$0) {
        this.a = this$0;
    }

    public final void onLoadingAppeared() {
        AuiLogger.debug("AURefreshListView", "onLoadingAppeared, refreshFinished:" + this.a.refreshFinished);
        if (this.a.refreshFinished) {
            this.a.loadingView.pause();
            this.a.finishRefresh();
        }
    }
}
