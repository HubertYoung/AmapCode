package defpackage;

import android.content.Context;
import android.content.IntentFilter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.xstate.network.NetworkStateReceiver;

/* renamed from: fgz reason: default package */
/* compiled from: XStateDelegate */
public final class fgz {
    private static ConcurrentHashMap<String, String> a = null;
    private static NetworkStateReceiver b = null;
    private static Context c = null;
    private static volatile boolean d = false;
    private static Lock e = new ReentrantLock();

    public static void a(Context context) {
        if (!d) {
            e.lock();
            try {
                if (!d) {
                    if (context == null) {
                        TBSdkLog.d("mtopsdk.XStateDelegate", "[checkInit]parameter context for init(Context context) is null.");
                        e.unlock();
                        return;
                    }
                    if (a == null) {
                        a = new ConcurrentHashMap<>();
                    }
                    c = context;
                    if (b == null) {
                        b = new NetworkStateReceiver();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                        context.registerReceiver(b, intentFilter);
                    }
                    d = true;
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb = new StringBuilder("[checkInit] init XState OK,isInit=");
                        sb.append(d);
                        TBSdkLog.b("mtopsdk.XStateDelegate", sb.toString());
                    }
                }
            } catch (Throwable th) {
                try {
                    StringBuilder sb2 = new StringBuilder("[checkInit] checkInit error --");
                    sb2.append(th.toString());
                    TBSdkLog.d("mtopsdk.XStateDelegate", sb2.toString());
                } catch (Throwable th2) {
                    e.unlock();
                    throw th2;
                }
            }
            e.unlock();
        }
    }

    public static void a() {
        if (d) {
            e.lock();
            try {
                if (d) {
                    if (a != null) {
                        a.clear();
                        a = null;
                    }
                    if (c == null) {
                        TBSdkLog.d("mtopsdk.XStateDelegate", "[unInit] context in Class XState is null.");
                        e.unlock();
                        return;
                    }
                    try {
                        if (b != null) {
                            c.unregisterReceiver(b);
                            b = null;
                        }
                    } catch (Throwable th) {
                        TBSdkLog.b((String) "mtopsdk.XStateDelegate", (String) "[unRegisterReceive]unRegisterReceive failed", th);
                    }
                    d = false;
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb = new StringBuilder("[unInit] unInit XState OK,isInit=");
                        sb.append(d);
                        TBSdkLog.b("mtopsdk.XStateDelegate", sb.toString());
                    }
                }
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder("[unInit] unInit error --");
                sb2.append(e2.toString());
                TBSdkLog.d("mtopsdk.XStateDelegate", sb2.toString());
            } catch (Throwable th2) {
                e.unlock();
                throw th2;
            }
            e.unlock();
        }
    }

    public static String a(String str) {
        if (a == null || str == null) {
            return null;
        }
        return a.get(str);
    }

    public static void a(String str, String str2) {
        if (a != null && str != null && str2 != null) {
            a.put(str, str2);
            if (TBSdkLog.a(LogEnable.DebugEnable)) {
                StringBuilder sb = new StringBuilder("[setValue]set  XStateID succeed,");
                sb.append(str);
                sb.append("=");
                sb.append(str2);
                TBSdkLog.a((String) "mtopsdk.XStateDelegate", sb.toString());
            }
        } else if (TBSdkLog.a(LogEnable.DebugEnable)) {
            StringBuilder sb2 = new StringBuilder("[setValue]set  XStateID failed,key=");
            sb2.append(str);
            sb2.append(",value=");
            sb2.append(str2);
            TBSdkLog.a((String) "mtopsdk.XStateDelegate", sb2.toString());
        }
    }

    public static String b(String str) {
        if (a == null || str == null) {
            return null;
        }
        if (TBSdkLog.a(LogEnable.DebugEnable)) {
            TBSdkLog.a((String) "mtopsdk.XStateDelegate", "remove XState key=".concat(String.valueOf(str)));
        }
        return a.remove(str);
    }
}
