package com.alibaba.baichuan.android.trade;

import com.alibaba.baichuan.android.trade.callback.AlibcCallbackContext;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.android.trade.model.InitResult;

final class e implements Runnable {
    final /* synthetic */ AlibcTradeInitCallback a;
    final /* synthetic */ InitResult b;

    e(AlibcTradeInitCallback alibcTradeInitCallback, InitResult initResult) {
        this.a = alibcTradeInitCallback;
        this.b = initResult;
    }

    public final void run() {
        this.a.onFailure(this.b.errorCode, this.b.errorMessage);
        for (AlibcTradeInitCallback onFailure : AlibcCallbackContext.pendingInitCallbacks) {
            onFailure.onFailure(this.b.errorCode, this.b.errorMessage);
        }
        AlibcCallbackContext.pendingInitCallbacks.clear();
    }
}
