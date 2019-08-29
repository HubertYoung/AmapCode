package com.googlecode.androidannotations.api;

import android.os.Looper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class BackgroundExecutor {
    public static final Executor DEFAULT_EXECUTOR;
    public static final WrongThreadListener DEFAULT_WRONG_THREAD_LISTENER;
    private static Executor a;
    private static WrongThreadListener b;
    /* access modifiers changed from: private */
    public static final List<Task> c = new ArrayList();
    /* access modifiers changed from: private */
    public static final ThreadLocal<String> d = new ThreadLocal<>();

    public abstract class Task implements Runnable {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public int b;
        private long c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public boolean e;
        /* access modifiers changed from: private */
        public Future<?> f;
        /* access modifiers changed from: private */
        public AtomicBoolean g = new AtomicBoolean();

        public abstract void execute();

        public Task(String id, int delay, String serial) {
            if (!"".equals(id)) {
                this.a = id;
            }
            if (delay > 0) {
                this.b = delay;
                this.c = System.currentTimeMillis() + ((long) delay);
            }
            if (!"".equals(serial)) {
                this.d = serial;
            }
        }

        public void run() {
            if (!this.g.getAndSet(true)) {
                try {
                    BackgroundExecutor.d.set(this.d);
                    execute();
                } finally {
                    a();
                }
            }
        }

        /* access modifiers changed from: private */
        public void a() {
            if (this.a != null || this.d != null) {
                BackgroundExecutor.d.set(null);
                synchronized (BackgroundExecutor.class) {
                    BackgroundExecutor.c.remove(this);
                    if (this.d != null) {
                        Task next = BackgroundExecutor.b(this.d);
                        if (next != null) {
                            if (next.b != 0) {
                                next.b = Math.max(0, (int) (this.c - System.currentTimeMillis()));
                            }
                            BackgroundExecutor.execute(next);
                        }
                    }
                }
            }
        }
    }

    public interface WrongThreadListener {
        void onBgExpected(String... strArr);

        void onUiExpected();

        void onWrongBgSerial(String str, String... strArr);
    }

    static {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        DEFAULT_EXECUTOR = newScheduledThreadPool;
        a = newScheduledThreadPool;
        AnonymousClass1 r0 = new WrongThreadListener() {
            public final void onUiExpected() {
                throw new IllegalStateException("Method invocation is expected from the UI thread");
            }

            public final void onBgExpected(String... expectedSerials) {
                if (expectedSerials.length == 0) {
                    throw new IllegalStateException("Method invocation is expected from a background thread, but it was called from the UI thread");
                }
                throw new IllegalStateException("Method invocation is expected from one of serials " + Arrays.toString(expectedSerials) + ", but it was called from the UI thread");
            }

            public final void onWrongBgSerial(String currentSerial, String... expectedSerials) {
                if (currentSerial == null) {
                    currentSerial = "anonymous";
                }
                throw new IllegalStateException("Method invocation is expected from one of serials " + Arrays.toString(expectedSerials) + ", but it was called from " + currentSerial + " serial");
            }
        };
        DEFAULT_WRONG_THREAD_LISTENER = r0;
        b = r0;
    }

    private BackgroundExecutor() {
    }

    private static Future<?> a(final Runnable runnable, int delay) {
        Runnable runnableTmp = runnable;
        if (runnable != null && !(runnable instanceof Task)) {
            runnableTmp = new Runnable() {
                public final void run() {
                    try {
                        runnable.run();
                    } catch (Throwable e) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    }
                }
            };
        }
        if (delay > 0) {
            if (a instanceof ScheduledExecutorService) {
                return ((ScheduledExecutorService) a).schedule(runnableTmp, (long) delay, TimeUnit.MILLISECONDS);
            }
            throw new IllegalArgumentException("The executor set does not support scheduling");
        } else if (a instanceof ExecutorService) {
            return ((ExecutorService) a).submit(runnableTmp);
        } else {
            a.execute(runnableTmp);
            return null;
        }
    }

    public static synchronized void execute(Task task) {
        synchronized (BackgroundExecutor.class) {
            Future future = null;
            if (task.d == null || !a(task.d)) {
                task.e = true;
                future = a(task, task.b);
            }
            if (!(task.a == null && task.d == null)) {
                task.f = future;
                c.add(task);
            }
        }
    }

    public static void execute(final Runnable runnable, String id, int delay, String serial) {
        execute((Task) new Task(id, delay, serial) {
            public final void execute() {
                runnable.run();
            }
        });
    }

    public static void execute(Runnable runnable, int delay) {
        a(runnable, delay);
    }

    public static void execute(Runnable runnable) {
        a(runnable, 0);
    }

    public static void execute(Runnable runnable, String id, String serial) {
        execute(runnable, id, 0, serial);
    }

    public static void setExecutor(Executor executor) {
        a = executor;
    }

    public static void setWrongThreadListener(WrongThreadListener listener) {
        b = listener;
    }

    public static synchronized void cancelAll(String id, boolean mayInterruptIfRunning) {
        synchronized (BackgroundExecutor.class) {
            for (int i = c.size() - 1; i >= 0; i--) {
                Task task = c.get(i);
                if (id.equals(task.a)) {
                    if (task.f != null) {
                        task.f.cancel(mayInterruptIfRunning);
                        if (!task.g.getAndSet(true)) {
                            task.a();
                        }
                    } else if (task.e) {
                        Log.w("BackgroundExecutor", "A task with id " + task.a + " cannot be cancelled (the executor set does not support it)");
                    } else {
                        c.remove(i);
                    }
                }
            }
        }
    }

    public static void checkUiThread() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            b.onUiExpected();
        }
    }

    public static void checkBgThread(String... serials) {
        if (serials.length != 0) {
            String current = d.get();
            if (current == null) {
                b.onWrongBgSerial(null, serials);
                return;
            }
            String[] arr$ = serials;
            int len$ = serials.length;
            int i$ = 0;
            while (i$ < len$) {
                if (!arr$[i$].equals(current)) {
                    i$++;
                } else {
                    return;
                }
            }
            b.onWrongBgSerial(current, serials);
        } else if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            b.onBgExpected(serials);
        }
    }

    private static boolean a(String serial) {
        for (Task task : c) {
            if (task.e && serial.equals(task.d)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static Task b(String serial) {
        int len = c.size();
        for (int i = 0; i < len; i++) {
            if (serial.equals(c.get(i).d)) {
                return c.remove(i);
            }
        }
        return null;
    }
}
