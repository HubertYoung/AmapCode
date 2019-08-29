package com.autonavi.common.imageloader;

public enum MemoryPolicy {
    NO_CACHE(1),
    NO_STORE(2);
    
    public final int a;

    public static boolean shouldReadFromMemoryCache(int i) {
        return (i & NO_CACHE.a) == 0;
    }

    static boolean a(int i) {
        return (i & NO_STORE.a) == 0;
    }

    private MemoryPolicy(int i) {
        this.a = i;
    }
}
