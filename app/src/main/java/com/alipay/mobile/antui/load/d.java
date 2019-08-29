package com.alipay.mobile.antui.load;

import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AURefreshListView */
final class d implements Runnable {
    final /* synthetic */ AURefreshListView a;

    private d(AURefreshListView aURefreshListView) {
        this.a = aURefreshListView;
    }

    /* synthetic */ d(AURefreshListView x0, byte b) {
        this(x0);
    }

    public final void run() {
        AuiLogger.debug("AURefreshListView", "DelayInvokeRelease run");
        if (this.a.loadingView.isLoading() && this.a.mOnPullRefreshListener != null) {
            AuiLogger.debug("AURefreshListView", "mOnPullRefreshListener onRefreshFinished");
            this.a.mOnPullRefreshListener.onRefreshFinished();
        }
        this.a.refreshFinishLayoutAction();
    }
}
