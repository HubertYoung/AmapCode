package com.alipay.mobile.antui.load;

/* compiled from: AUDefaultLoadingView */
final class a implements OnLoadingAppearedListener {
    final /* synthetic */ AUDefaultLoadingView a;

    a(AUDefaultLoadingView this$0) {
        this.a = this$0;
    }

    public final void appeared() {
        if (this.a.loadingListener != null) {
            this.a.loadingListener.onLoadingAppeared();
            this.a.setFirstLoadingAppeared(true);
        }
    }
}
