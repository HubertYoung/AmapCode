package com.autonavi.common.imageloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.autonavi.common.imageloader.ImageLoader.Priority;
import com.autonavi.common.imageloader.NetworkRequestHandler.ContentLengthException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

public final class Dispatcher {
    final b a = new b();
    final Context b;
    final ExecutorService c;
    final IDownloader d;
    final Map<String, bjk> e;
    final Map<Object, bji> f;
    final Map<Object, bji> g;
    final Set<Object> h;
    public final Handler i;
    final Handler j;
    final bjv k;
    final bkd l;
    final List<bjk> m;
    final NetworkBroadcastReceiver n;
    final boolean o;
    boolean p;

    static class NetworkBroadcastReceiver extends BroadcastReceiver {
        final Dispatcher a;

        NetworkBroadcastReceiver(Dispatcher dispatcher) {
            this.a = dispatcher;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (!"android.intent.action.AIRPLANE_MODE".equals(action)) {
                    if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) bkh.a(context, (String) "connectivity");
                        if (connectivityManager != null) {
                            Dispatcher dispatcher = this.a;
                            dispatcher.i.sendMessage(dispatcher.i.obtainMessage(9, connectivityManager.getActiveNetworkInfo()));
                        }
                    }
                } else if (intent.hasExtra("state")) {
                    Dispatcher dispatcher2 = this.a;
                    dispatcher2.i.sendMessage(dispatcher2.i.obtainMessage(10, intent.getBooleanExtra("state", false) ? 1 : 0, 0));
                }
            }
        }
    }

    static class a extends Handler {
        private final Dispatcher a;

        public a(Looper looper, Dispatcher dispatcher) {
            super(looper);
            this.a = dispatcher;
        }

        public final void handleMessage(final Message message) {
            boolean z = false;
            switch (message.what) {
                case 1:
                    this.a.a((bji) message.obj, true);
                    return;
                case 2:
                    bji bji = (bji) message.obj;
                    Dispatcher dispatcher = this.a;
                    String str = bji.i;
                    bjk bjk = dispatcher.e.get(str);
                    if (bjk != null) {
                        bjk.a(bji);
                        if (bjk.a()) {
                            dispatcher.e.remove(str);
                            if (bji.a.n) {
                                bkh.a("Dispatcher", "canceled", bji.b.a());
                            }
                        }
                    }
                    if (dispatcher.h.contains(bji.j)) {
                        dispatcher.g.remove(bji.c());
                        if (bji.a.n) {
                            bkh.a("Dispatcher", "canceled", bji.b.a(), "because paused request got canceled");
                        }
                    }
                    bji remove = dispatcher.f.remove(bji.c());
                    if (remove != null && remove.a.n) {
                        bkh.a("Dispatcher", "canceled", remove.b.a(), "from replaying");
                    }
                    return;
                case 4:
                    bjk bjk2 = (bjk) message.obj;
                    Dispatcher dispatcher2 = this.a;
                    if (MemoryPolicy.a(bjk2.h)) {
                        dispatcher2.k.a(bjk2.f, bjk2.m);
                    }
                    dispatcher2.e.remove(bjk2.f);
                    if (bjk2.k == null || !bjk2.k.k) {
                        dispatcher2.d(bjk2);
                        if (bjk2.b.n) {
                            bkh.a("Dispatcher", "batched", bkh.a(bjk2), "for completion");
                        }
                        return;
                    }
                    if (!bjk2.b()) {
                        dispatcher2.i.sendMessage(dispatcher2.i.obtainMessage(14, bjk2));
                    }
                    if (bjk2.b.n) {
                        bkh.a("Dispatcher", FFmpegSessionConfig.PRESET_FAST, bkh.a(bjk2), "for completion");
                    }
                    return;
                case 5:
                    this.a.c((bjk) message.obj);
                    return;
                case 6:
                    this.a.a((bjk) message.obj, false);
                    return;
                case 7:
                    Dispatcher dispatcher3 = this.a;
                    ArrayList arrayList = new ArrayList(dispatcher3.m);
                    dispatcher3.m.clear();
                    dispatcher3.j.sendMessage(dispatcher3.j.obtainMessage(8, arrayList));
                    Dispatcher.a((List<bjk>) arrayList);
                    return;
                case 9:
                    this.a.a((NetworkInfo) message.obj);
                    return;
                case 10:
                    Dispatcher dispatcher4 = this.a;
                    if (message.arg1 == 1) {
                        z = true;
                    }
                    dispatcher4.p = z;
                    return;
                case 11:
                    Object obj = message.obj;
                    Dispatcher dispatcher5 = this.a;
                    if (dispatcher5.h.add(obj)) {
                        Iterator<bjk> it = dispatcher5.e.values().iterator();
                        while (it.hasNext()) {
                            bjk next = it.next();
                            boolean z2 = next.b.n;
                            bji bji2 = next.k;
                            List<bji> list = next.l;
                            boolean z3 = list != null && !list.isEmpty();
                            if (bji2 != null || z3) {
                                if (bji2 != null && bji2.j.equals(obj)) {
                                    next.a(bji2);
                                    dispatcher5.g.put(bji2.c(), bji2);
                                    if (z2) {
                                        String a2 = bji2.b.a();
                                        StringBuilder sb = new StringBuilder("because tag '");
                                        sb.append(obj);
                                        sb.append("' was paused");
                                        bkh.a("Dispatcher", "paused", a2, sb.toString());
                                    }
                                }
                                if (z3) {
                                    for (int size = list.size() - 1; size >= 0; size--) {
                                        bji bji3 = list.get(size);
                                        if (bji3.j.equals(obj)) {
                                            next.a(bji3);
                                            dispatcher5.g.put(bji3.c(), bji3);
                                            if (z2) {
                                                String a3 = bji3.b.a();
                                                StringBuilder sb2 = new StringBuilder("because tag '");
                                                sb2.append(obj);
                                                sb2.append("' was paused");
                                                bkh.a("Dispatcher", "paused", a3, sb2.toString());
                                            }
                                        }
                                    }
                                }
                                if (next.a()) {
                                    it.remove();
                                    if (z2) {
                                        bkh.a("Dispatcher", "canceled", bkh.a(next), "all actions paused");
                                    }
                                }
                            }
                        }
                    }
                    return;
                case 12:
                    this.a.a(message.obj);
                    return;
                case 14:
                    bjk bjk3 = (bjk) message.obj;
                    Dispatcher dispatcher6 = this.a;
                    dispatcher6.j.sendMessage(dispatcher6.j.obtainMessage(14, bjk3));
                    if (bjk3.b.n) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(bkh.a(bjk3));
                        bkh.a("Dispatcher", "delivered", sb3.toString());
                    }
                    return;
                default:
                    ImageLoader.a.post(new Runnable() {
                        public final void run() {
                            StringBuilder sb = new StringBuilder("Unknown handler message received: ");
                            sb.append(message.what);
                            throw new AssertionError(sb.toString());
                        }
                    });
                    return;
            }
        }
    }

    static class b extends HandlerThread {
        b() {
            super("ImageLoader-Dispatcher", 10);
        }
    }

    Dispatcher(Context context, ExecutorService executorService, Handler handler, IDownloader iDownloader, bjv bjv, bkd bkd) {
        this.a.start();
        bkh.a(this.a.getLooper());
        this.b = context;
        this.c = executorService;
        this.e = new LinkedHashMap();
        this.f = new WeakHashMap();
        this.g = new WeakHashMap();
        this.h = new HashSet();
        this.i = new a(this.a.getLooper(), this);
        this.d = iDownloader;
        this.j = handler;
        this.k = bjv;
        this.l = bkd;
        this.m = new ArrayList(4);
        this.p = bkh.b(this.b);
        this.o = bkh.b(context, "android.permission.ACCESS_NETWORK_STATE");
        this.n = new NetworkBroadcastReceiver(this);
        NetworkBroadcastReceiver networkBroadcastReceiver = this.n;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        if (networkBroadcastReceiver.a.o) {
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }
        networkBroadcastReceiver.a.b.registerReceiver(networkBroadcastReceiver, intentFilter);
    }

    /* access modifiers changed from: 0000 */
    public final void a(bji bji) {
        this.i.sendMessage(this.i.obtainMessage(1, bji));
    }

    /* access modifiers changed from: 0000 */
    public final void b(bji bji) {
        this.i.sendMessage(this.i.obtainMessage(2, bji));
    }

    public final void a(bjk bjk) {
        this.i.sendMessageDelayed(this.i.obtainMessage(5, bjk), 500);
    }

    public final void b(bjk bjk) {
        this.i.sendMessage(this.i.obtainMessage(6, bjk));
    }

    /* access modifiers changed from: 0000 */
    public final void a(bji bji, boolean z) {
        if (this.h.contains(bji.j)) {
            this.g.put(bji.c(), bji);
            if (bji.a.n) {
                String a2 = bji.b.a();
                StringBuilder sb = new StringBuilder("because tag '");
                sb.append(bji.j);
                sb.append("' is paused");
                bkh.a("Dispatcher", "paused", a2, sb.toString());
            }
            return;
        }
        bjk bjk = this.e.get(bji.i);
        if (bjk != null) {
            boolean z2 = bjk.b.n;
            bjz bjz = bji.b;
            if (bjk.k == null) {
                bjk.k = bji;
                if (z2) {
                    if (bjk.l == null || bjk.l.isEmpty()) {
                        bkh.a("Hunter", "joined", bjz.a(), "to empty hunter");
                        return;
                    }
                    bkh.a("Hunter", "joined", bjz.a(), bkh.a(bjk, (String) "to "));
                }
                return;
            }
            if (bjk.l == null) {
                bjk.l = new ArrayList(3);
            }
            bjk.l.add(bji);
            if (z2) {
                bkh.a("Hunter", "joined", bjz.a(), bkh.a(bjk, (String) "to "));
            }
            Priority priority = bji.b.s;
            if (priority.ordinal() > bjk.s.ordinal()) {
                bjk.s = priority;
            }
        } else if (this.c.isShutdown()) {
            if (bji.a.n) {
                bkh.a("Dispatcher", "ignored", bji.b.a(), "because shut down");
            }
        } else {
            bjk a3 = bjk.a(bji.a, this, this.k, this.l, bji);
            a3.n = this.c.submit(a3);
            this.e.put(bji.i, a3);
            if (z) {
                this.f.remove(bji.c());
            }
            if (bji.a.n) {
                bkh.a("Dispatcher", "enqueued", bji.b.a());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(Object obj) {
        if (this.h.remove(obj)) {
            ArrayList arrayList = null;
            Iterator<bji> it = this.g.values().iterator();
            while (it.hasNext()) {
                bji next = it.next();
                if (next.j.equals(obj)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(next);
                    it.remove();
                }
            }
            if (arrayList != null) {
                this.j.sendMessage(this.j.obtainMessage(13, arrayList));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c(bjk bjk) {
        boolean z;
        if (!bjk.b()) {
            boolean z2 = false;
            if (this.c.isShutdown()) {
                a(bjk, false);
                return;
            }
            NetworkInfo networkInfo = null;
            if (this.o) {
                ConnectivityManager connectivityManager = (ConnectivityManager) bkh.a(this.b, (String) "connectivity");
                if (connectivityManager != null) {
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                } else {
                    return;
                }
            }
            boolean z3 = networkInfo != null && networkInfo.isConnected();
            if (!(bjk.r > 0)) {
                z = false;
            } else {
                bjk.r--;
                z = bjk.j.a(networkInfo);
            }
            boolean b2 = bjk.j.b();
            if (!z) {
                if (this.o && b2) {
                    z2 = true;
                }
                a(bjk, z2);
                if (z2) {
                    e(bjk);
                }
            } else if (!this.o || z3) {
                if (bjk.b.n) {
                    bkh.a("Dispatcher", "retrying", bkh.a(bjk));
                }
                if (bjk.p instanceof ContentLengthException) {
                    bjk.i |= NetworkPolicy.NO_CACHE.a;
                }
                bjk.n = this.c.submit(bjk);
            } else {
                a(bjk, b2);
                if (b2) {
                    e(bjk);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(NetworkInfo networkInfo) {
        if (this.c instanceof bjy) {
            bjy bjy = (bjy) this.c;
            if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
                bjy.a(3);
            } else {
                int type = networkInfo.getType();
                if (!(type == 6 || type == 9)) {
                    switch (type) {
                        case 0:
                            int subtype = networkInfo.getSubtype();
                            switch (subtype) {
                                case 1:
                                case 2:
                                    bjy.a(1);
                                    break;
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                    bjy.a(2);
                                    break;
                                default:
                                    switch (subtype) {
                                        case 12:
                                            break;
                                        case 13:
                                        case 14:
                                        case 15:
                                            bjy.a(3);
                                            break;
                                        default:
                                            bjy.a(3);
                                            break;
                                    }
                                    bjy.a(2);
                                    break;
                            }
                        case 1:
                            break;
                        default:
                            bjy.a(3);
                            break;
                    }
                }
                bjy.a(4);
            }
        }
        if (networkInfo != null && networkInfo.isConnected() && !this.f.isEmpty()) {
            Iterator<bji> it = this.f.values().iterator();
            while (it.hasNext()) {
                bji next = it.next();
                it.remove();
                if (next.a.n) {
                    bkh.a("Dispatcher", "replaying", next.b.a());
                }
                a(next, false);
            }
        }
    }

    private void c(bji bji) {
        Object c2 = bji.c();
        if (c2 != null) {
            bji.l = true;
            this.f.put(c2, bji);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void d(bjk bjk) {
        if (!bjk.b()) {
            this.m.add(bjk);
            if (!this.i.hasMessages(7)) {
                this.i.sendEmptyMessageDelayed(7, 200);
            }
        }
    }

    static void a(List<bjk> list) {
        if (!list.isEmpty() && list.get(0).b.n) {
            StringBuilder sb = new StringBuilder();
            for (bjk next : list) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(bkh.a(next));
            }
            bkh.a("Dispatcher", "delivered", sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(bjk bjk, boolean z) {
        if (bjk.b.n) {
            String str = bjk.k.k ? FFmpegSessionConfig.PRESET_FAST : "batched";
            String a2 = bkh.a(bjk);
            StringBuilder sb = new StringBuilder("for error");
            sb.append(z ? " (will replay)" : "");
            bkh.a("Dispatcher", str, a2, sb.toString());
        }
        this.e.remove(bjk.f);
        d(bjk);
    }

    private void e(bjk bjk) {
        bji bji = bjk.k;
        if (bji != null) {
            c(bji);
        }
        List<bji> list = bjk.l;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                c(list.get(i2));
            }
        }
    }
}
