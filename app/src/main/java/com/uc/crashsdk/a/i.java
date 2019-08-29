package com.uc.crashsdk.a;

import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import java.util.HashMap;

/* compiled from: ProGuard */
public final class i {
    static volatile HandlerThread a;
    static volatile HandlerThread b;
    static Handler c;
    static Handler d;
    /* access modifiers changed from: private */
    public static HashMap<Object, a> e = new HashMap<>();

    /* compiled from: ProGuard */
    final class a {
        public Runnable a;
        public int b;

        public a(Runnable runnable, int i) {
            this.a = runnable;
            this.b = i;
        }
    }

    public static boolean a(int i, Runnable runnable, long j) {
        Handler handler;
        c.a("ThreadManager.post: " + runnable + ", type: " + i + ", delay: " + j + RPCDataParser.TIME_MS);
        if (runnable == null) {
            return false;
        }
        switch (i) {
            case 0:
                if (a == null) {
                    b();
                }
                handler = c;
                break;
            case 1:
                if (b == null) {
                    c();
                }
                handler = d;
                break;
            default:
                throw new RuntimeException("unknown thread type: " + i);
        }
        if (handler == null) {
            return false;
        }
        j jVar = new j(runnable);
        synchronized (e) {
            e.put(runnable, new a(jVar, i));
        }
        return handler.postDelayed(jVar, j);
    }

    public static boolean a(int i, Runnable runnable) {
        return a(i, runnable, 0);
    }

    public static void a(Runnable runnable) {
        a aVar;
        c.a("ThreadManager.remove: " + runnable);
        if (runnable != null) {
            synchronized (e) {
                aVar = e.get(runnable);
            }
            if (aVar != null) {
                c.a("found task in cache, item: " + aVar + ", type: " + aVar.b);
                Handler handler = null;
                switch (aVar.b) {
                    case 0:
                        handler = c;
                        break;
                    case 1:
                        handler = d;
                        break;
                }
                if (handler != null) {
                    handler.removeCallbacks(aVar.a);
                }
                synchronized (e) {
                    if (e.get(runnable) != null) {
                        e.remove(runnable);
                    }
                }
            }
        }
    }

    public static boolean b(Runnable runnable) {
        a aVar;
        if (runnable == null) {
            return false;
        }
        synchronized (e) {
            aVar = e.get(runnable);
        }
        if (aVar != null) {
            return true;
        }
        return false;
    }

    private static synchronized void b() {
        synchronized (i.class) {
            if (a == null) {
                HandlerThread handlerThread = new HandlerThread("CrashSDKBkgdHandler", 10);
                a = handlerThread;
                handlerThread.start();
                c = new Handler(a.getLooper());
            }
        }
    }

    private static synchronized void c() {
        synchronized (i.class) {
            if (b == null) {
                HandlerThread handlerThread = new HandlerThread("CrashSDKNormalHandler", 0);
                b = handlerThread;
                handlerThread.start();
                d = new Handler(b.getLooper());
            }
        }
    }
}
