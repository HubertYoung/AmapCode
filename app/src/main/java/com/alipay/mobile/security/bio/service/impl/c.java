package com.alipay.mobile.security.bio.service.impl;

import com.alipay.mobile.security.bio.service.BioUploadResult;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: BioUploadWatchThread */
final class c implements Runnable {
    final /* synthetic */ BioUploadResult a;
    final /* synthetic */ BioUploadWatchThread b;

    c(BioUploadWatchThread bioUploadWatchThread, BioUploadResult bioUploadResult) {
        this.b = bioUploadWatchThread;
        this.a = bioUploadResult;
    }

    public final void run() {
        BioLog.e((String) "BioUploadWatchThread.doCallback()");
        this.b.a(this.a);
    }
}
