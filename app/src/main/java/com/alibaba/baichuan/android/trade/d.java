package com.alibaba.baichuan.android.trade;

import com.alibaba.baichuan.android.trade.callback.AlibcCallbackContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;

final class d implements Runnable {
    final /* synthetic */ AlibcTradeInitCallback a;

    d(AlibcTradeInitCallback alibcTradeInitCallback) {
        this.a = alibcTradeInitCallback;
    }

    public final void run() {
        this.a.onSuccess();
        for (AlibcTradeInitCallback onSuccess : AlibcCallbackContext.pendingInitCallbacks) {
            onSuccess.onSuccess();
        }
        AlibcCallbackContext.pendingInitCallbacks.clear();
        AlibcTradeSDK.b();
    }
}
