package com.autonavi.minimap.route.bus.realtimebus.data;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class HeartBeatManager {
    private static final HeartBeatManager b = new HeartBeatManager();
    public Map<AosRequest, a> a = new HashMap();

    public static class HearBeatResponse extends AosResponse<String> {
        public /* synthetic */ Object parseResult() {
            return getResponseBodyString();
        }
    }

    public static class a<T> {
        b<T> a;
        private ScheduledExecutorService b = Executors.newSingleThreadScheduledExecutor();

        public a(b<T> bVar) {
            this.a = bVar;
        }

        public final void a() {
            if (!(this.a == null || this.b == null)) {
                if (this.a.a()) {
                    this.b.scheduleAtFixedRate(this.a, 0, this.a.b(), TimeUnit.SECONDS);
                    return;
                }
                this.b.schedule(this.a, 0, TimeUnit.SECONDS);
            }
        }

        public final void b() {
            if (this.b != null && !this.b.isShutdown()) {
                this.b.shutdownNow();
            }
        }
    }

    public static class b<T> implements Runnable {
        dyc<T> a;
        Class<T> b;
        private HeartBeatRequest c;
        /* access modifiers changed from: private */
        public volatile boolean d;

        public b(HeartBeatRequest heartBeatRequest, dyc<T> dyc, Class<T> cls) {
            this.c = heartBeatRequest;
            this.a = dyc;
            this.b = cls;
        }

        public final boolean a() {
            if (this.c == null) {
                return false;
            }
            return this.c.b;
        }

        public final void run() {
            if (!this.d) {
                this.d = true;
                if (this.a != null && this.c != null) {
                    yq.a();
                    yq.a((AosRequest) this.c, (AosResponseCallback<T>) new HeartBeatManager$HeartBeatTask$1<T>(this));
                }
            }
        }

        public final long b() {
            if (this.c != null) {
                return (long) this.c.a;
            }
            return 30;
        }
    }

    private HeartBeatManager() {
    }

    public static HeartBeatManager a() {
        return b;
    }

    public final void a(HeartBeatRequest heartBeatRequest) {
        if (heartBeatRequest != null) {
            a aVar = this.a.get(heartBeatRequest);
            if (aVar != null) {
                yq.a();
                yq.a((AosRequest) heartBeatRequest);
                aVar.b();
                this.a.remove(heartBeatRequest);
            }
        }
    }

    public final void b(HeartBeatRequest heartBeatRequest) {
        if (heartBeatRequest != null && !dxx.a(this.a)) {
            a aVar = this.a.get(heartBeatRequest);
            if (aVar != null) {
                aVar.b();
                a aVar2 = new a(aVar.a);
                this.a.put(heartBeatRequest, aVar2);
                aVar2.a();
            }
        }
    }

    public final void a(HeartBeatRequest heartBeatRequest, int i) {
        if (heartBeatRequest != null) {
            heartBeatRequest.a = i;
            b(heartBeatRequest);
        }
    }
}
