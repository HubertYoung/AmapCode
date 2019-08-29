package com.sijla.a;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class a {
    private static ScheduledExecutorService a = Executors.newScheduledThreadPool(4);

    public static void a(Runnable runnable) {
        a(runnable, false);
    }

    public static void a(Runnable runnable, long j) {
        b(runnable, j);
    }

    public static void a(Runnable runnable, boolean z) {
        a.schedule(runnable, 0, TimeUnit.MILLISECONDS);
    }

    public static void b(Runnable runnable, long j) {
        a.schedule(runnable, j, TimeUnit.MILLISECONDS);
    }
}
