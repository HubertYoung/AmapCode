package com.danikula.videocache;

public class InterruptedProxyCacheException extends ProxyCacheException {
    public InterruptedProxyCacheException(String str) {
        super(str);
    }

    public InterruptedProxyCacheException(String str, Throwable th) {
        super(str, th);
    }

    public InterruptedProxyCacheException(Throwable th) {
        super(th);
    }
}
