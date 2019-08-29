package com.alibaba.baichuan.android.trade.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

class h extends Handler {
    final /* synthetic */ g a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    h(g gVar, Looper looper) {
        // this.a = gVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            super.handleMessage(message);
        } catch (Throwable th) {
            AlibcLogger.d("ExecutorServiceUtils", th.getMessage());
        }
    }
}
