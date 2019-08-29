package com.alipay.mobile.beehive.rpc.ext;

public abstract class CacheProcessor<T> {
    public abstract T load(String str);

    public abstract void save(String str, Object obj);
}
