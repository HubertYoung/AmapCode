package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.AutoCleanStrategy;
import java.util.concurrent.atomic.AtomicBoolean;

public class CleanController {
    private static CleanController a;
    private AtomicBoolean b;
    private long c;
    private AutoCleanStrategy d;

    private CleanController() {
        this.b = new AtomicBoolean(true);
        this.c = 0;
        this.d = null;
        this.d = ConfigManager.getInstance().diskConf().autoCleanStrategy;
    }

    public static CleanController get() {
        if (a == null) {
            synchronized (CleanController.class) {
                try {
                    if (a == null) {
                        a = new CleanController();
                    }
                }
            }
        }
        return a;
    }

    public boolean isInterrupt() {
        return this.b.get() || System.currentTimeMillis() - this.c > ((long) this.d.cleanTimeOut);
    }

    public void start() {
        this.d = ConfigManager.getInstance().diskConf().autoCleanStrategy;
        this.c = System.currentTimeMillis();
        this.b.set(false);
    }

    public void stop() {
        this.b.set(true);
        this.c = 0;
    }

    public void reset() {
        this.d = ConfigManager.getInstance().diskConf().autoCleanStrategy;
    }
}
