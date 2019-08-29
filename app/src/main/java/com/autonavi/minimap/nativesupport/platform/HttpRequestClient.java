package com.autonavi.minimap.nativesupport.platform;

import java.io.IOException;

final class HttpRequestClient {
    private static HttpRequestClient mIns = null;
    private static int threadPoolSize = 5;
    private boy mClient = new boy();

    private HttpRequestClient() {
        this.mClient.a(threadPoolSize);
    }

    public static HttpRequestClient getInstance() {
        if (mIns == null) {
            synchronized (HttpRequestClient.class) {
                try {
                    if (mIns == null) {
                        mIns = new HttpRequestClient();
                    }
                }
            }
        }
        return mIns;
    }

    public final <T extends bpk> T send(bph bph, Class<T> cls) throws IOException {
        return this.mClient.a(bph, cls);
    }

    public final <T extends bpk> void send(bph bph, bpl<T> bpl) {
        this.mClient.a(bph, bpl);
    }

    public final void cancel(bph bph) {
        this.mClient.a(bph);
    }
}
