package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.pool;

public class PoolManager {
    private static PoolManager a;
    private BitmapPool b = new BitmapPool(2097152);

    private PoolManager() {
    }

    public static synchronized PoolManager get() {
        PoolManager poolManager;
        synchronized (PoolManager.class) {
            try {
                if (a == null) {
                    a = new PoolManager();
                }
                poolManager = a;
            }
        }
        return poolManager;
    }

    public BitmapPool getBitmapPool() {
        return this.b;
    }
}
