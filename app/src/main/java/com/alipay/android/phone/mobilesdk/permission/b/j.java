package com.alipay.android.phone.mobilesdk.permission.b;

import java.util.concurrent.Executor;

/* compiled from: TransactionPipeline */
public final class j extends i {
    public j(String name, Executor executor) {
        super(name, executor);
    }

    @Deprecated
    public final int a() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public final int d() {
        return super.a();
    }
}
