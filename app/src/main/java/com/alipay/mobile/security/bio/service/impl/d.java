package com.alipay.mobile.security.bio.service.impl;

import com.alipay.mobile.security.bio.service.BioUploadResult;

/* compiled from: BioUploadWatchThread */
final class d implements Runnable {
    final /* synthetic */ BioUploadWatchThread a;

    d(BioUploadWatchThread bioUploadWatchThread) {
        this.a = bioUploadWatchThread;
    }

    public final void run() {
        BioUploadResult bioUploadResult = new BioUploadResult();
        bioUploadResult.productRetCode = 4001;
        bioUploadResult.validationRetCode = 1001;
        this.a.a(bioUploadResult);
    }
}
