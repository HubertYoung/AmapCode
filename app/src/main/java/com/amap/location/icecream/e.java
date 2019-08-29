package com.amap.location.icecream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: IcecreamDownloader */
public class e extends Handler {
    /* access modifiers changed from: private */
    public Context a = null;
    /* access modifiers changed from: private */
    public a b;
    private int c = 0;
    private Queue<h> d;
    private b e;
    /* access modifiers changed from: private */
    public boolean f = false;

    /* compiled from: IcecreamDownloader */
    class a extends a {
        private String b;

        private a(String str) {
            this.b = "unknow";
            if (str != null) {
                this.b = str;
            }
        }

        public void a(int i, int i2, Throwable th) {
            int i3;
            synchronized (e.this) {
                if (e.this.f) {
                    e.this.b = null;
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            i3 = 0;
                            e.this.obtainMessage(1, i3, 0).sendToTarget();
                        }
                    }
                    i3 = 1;
                    e.this.obtainMessage(1, i3, 0).sendToTarget();
                }
            }
        }
    }

    /* compiled from: IcecreamDownloader */
    class b extends BroadcastReceiver {
        private ConnectivityManager b;
        private boolean c = false;

        protected b() {
            this.b = (ConnectivityManager) e.this.a.getSystemService("connectivity");
        }

        /* access modifiers changed from: protected */
        public synchronized void a() {
            if (!this.c) {
                try {
                    e.this.a.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                    this.c = true;
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                }
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void b() {
            if (this.c) {
                try {
                    e.this.a.unregisterReceiver(this);
                    this.c = false;
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                }
            }
        }

        /* access modifiers changed from: protected */
        public int c() {
            NetworkInfo activeNetworkInfo = this.b.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return 1;
            }
            if (activeNetworkInfo.getType() == 1) {
                return 2;
            }
            if (activeNetworkInfo.getType() == 0) {
                return 3;
            }
            return 1;
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null && "android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                synchronized (e.this) {
                    if (!e.this.f) {
                        e.this.obtainMessage(2).sendToTarget();
                    }
                }
            }
        }
    }

    protected e(Context context) {
        super(Looper.myLooper());
        this.a = context;
        this.d = new LinkedList();
        this.e = new b();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0051, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0056, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.util.List<com.amap.location.icecream.h> r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x0055
            int r0 = r4.size()     // Catch:{ all -> 0x0052 }
            if (r0 <= 0) goto L_0x0055
            int r0 = r4.size()     // Catch:{ all -> 0x0052 }
            r1 = 20
            if (r0 <= r1) goto L_0x0012
            goto L_0x0055
        L_0x0012:
            r0 = 0
            r3.f = r0     // Catch:{ all -> 0x0052 }
            java.util.Queue<com.amap.location.icecream.h> r1 = r3.d     // Catch:{ all -> 0x0052 }
            r1.clear()     // Catch:{ all -> 0x0052 }
            com.amap.location.icecream.e$b r1 = r3.e     // Catch:{ all -> 0x0052 }
            r1.a()     // Catch:{ all -> 0x0052 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0052 }
        L_0x0023:
            boolean r1 = r4.hasNext()     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x0037
            java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x0052 }
            com.amap.location.icecream.h r1 = (com.amap.location.icecream.h) r1     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x0023
            java.util.Queue<com.amap.location.icecream.h> r2 = r3.d     // Catch:{ all -> 0x0052 }
            r2.offer(r1)     // Catch:{ all -> 0x0052 }
            goto L_0x0023
        L_0x0037:
            r3.c = r0     // Catch:{ all -> 0x0052 }
            java.util.Queue<com.amap.location.icecream.h> r4 = r3.d     // Catch:{ all -> 0x0052 }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x0052 }
            if (r4 != 0) goto L_0x0050
            com.amap.location.icecream.e$a r4 = r3.b     // Catch:{ all -> 0x0052 }
            if (r4 != 0) goto L_0x0050
            java.util.Queue<com.amap.location.icecream.h> r4 = r3.d     // Catch:{ all -> 0x0052 }
            java.lang.Object r4 = r4.peek()     // Catch:{ all -> 0x0052 }
            com.amap.location.icecream.h r4 = (com.amap.location.icecream.h) r4     // Catch:{ all -> 0x0052 }
            r3.a(r4)     // Catch:{ all -> 0x0052 }
        L_0x0050:
            monitor-exit(r3)
            return
        L_0x0052:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x0055:
            monitor-exit(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.e.a(java.util.List):void");
    }

    /* access modifiers changed from: protected */
    public synchronized void a() {
        this.f = true;
        this.d.clear();
        this.e.b();
        if (this.b != null) {
            j.a().a((a) this.b);
        }
    }

    public void handleMessage(Message message) {
        if (message != null) {
            if (message.what == 1) {
                a(message.arg1);
                return;
            }
            if (message.what == 2) {
                b();
            }
        }
    }

    private synchronized void a(int i) {
        this.b = null;
        if (i != 1) {
            this.c++;
            if (this.c >= 6) {
                this.d.clear();
            }
        } else if (!this.d.isEmpty()) {
            this.d.poll();
        }
        if (this.d.isEmpty()) {
            a();
        } else {
            a(this.d.peek());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b() {
        /*
            r2 = this;
            monitor-enter(r2)
            com.amap.location.icecream.e$a r0 = r2.b     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x001a
            java.util.Queue<com.amap.location.icecream.h> r0 = r2.d     // Catch:{ all -> 0x002e }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x002c
            java.util.Queue<com.amap.location.icecream.h> r0 = r2.d     // Catch:{ all -> 0x002e }
            java.lang.Object r0 = r0.peek()     // Catch:{ all -> 0x002e }
            com.amap.location.icecream.h r0 = (com.amap.location.icecream.h) r0     // Catch:{ all -> 0x002e }
            r2.a(r0)     // Catch:{ all -> 0x002e }
            monitor-exit(r2)
            return
        L_0x001a:
            com.amap.location.icecream.e$b r0 = r2.e     // Catch:{ all -> 0x002e }
            int r0 = r0.c()     // Catch:{ all -> 0x002e }
            r1 = 3
            if (r0 != r1) goto L_0x002c
            com.amap.location.icecream.j r0 = com.amap.location.icecream.j.a()     // Catch:{ all -> 0x002e }
            com.amap.location.icecream.e$a r1 = r2.b     // Catch:{ all -> 0x002e }
            r0.a(r1)     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r2)
            return
        L_0x002e:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.e.b():void");
    }

    private void a(h hVar) {
        if (this.e.c() == 2 && com.amap.location.common.f.e.b(this.a) > 104857600) {
            bpf bpf = new bpf();
            bpf.setUrl(hVar.b);
            StringBuilder sb = new StringBuilder();
            sb.append(com.amap.location.icecream.a.a.a(this.a));
            sb.append(File.separator);
            sb.append(hVar.a);
            String sb2 = sb.toString();
            this.b = new a(hVar.a);
            j.a().a(bpf, sb2, this.b);
        }
    }
}
