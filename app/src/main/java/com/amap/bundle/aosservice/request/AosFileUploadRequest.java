package com.amap.bundle.aosservice.request;

import java.io.File;

public class AosFileUploadRequest extends AosRequest {
    protected File a;
    protected String b;

    public AosFileUploadRequest() {
        setMethod(1);
    }

    public final void a(File file) {
        this.a = file;
    }

    public final void a(String str) {
        this.b = str;
    }

    /* access modifiers changed from: protected */
    public bph createHttpRequest() {
        bpe bpe = new bpe();
        bpe.a(this.a);
        bpe.a(this.b);
        return bpe;
    }
}
