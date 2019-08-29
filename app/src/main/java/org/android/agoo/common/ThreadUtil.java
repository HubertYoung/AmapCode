package org.android.agoo.common;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public final class ThreadUtil {

    static class ThreadLoader {
        /* access modifiers changed from: private */
        public static final ScheduledThreadPoolExecutor a = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, "AGOO");
            }
        });

        private ThreadLoader() {
        }
    }

    private ThreadUtil() {
        if (ThreadLoader.a != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static ScheduledThreadPoolExecutor a() {
        return ThreadLoader.a;
    }
}
