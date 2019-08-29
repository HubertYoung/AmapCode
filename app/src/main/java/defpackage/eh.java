package defpackage;

import anet.channel.statist.RequestStatistic;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.Cache.Entry;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import java.util.Collections;

/* renamed from: eh reason: default package */
/* compiled from: CacheTask */
public final class eh implements ek {
    private em a = null;
    private Cache b = null;
    private volatile boolean c = false;

    public eh(em emVar, Cache cache) {
        this.a = emVar;
        this.b = cache;
    }

    public final void a() {
        this.c = true;
        this.a.a.f.ret = 2;
    }

    public final void run() {
        Entry entry;
        boolean z;
        if (!this.c) {
            RequestStatistic requestStatistic = this.a.a.f;
            if (this.b != null) {
                String str = this.a.a.b.a.e;
                String str2 = Collections.unmodifiableMap(this.a.a.b.c).get("Cache-Control");
                boolean equals = "no-store".equals(str2);
                long currentTimeMillis = System.currentTimeMillis();
                if (equals) {
                    this.b.b(str);
                    z = false;
                    entry = null;
                } else {
                    z = "no-cache".equals(str2);
                    Entry a2 = this.b.a(str);
                    if (cl.a(2)) {
                        String str3 = this.a.c;
                        Object[] objArr = new Object[8];
                        objArr[0] = "hit";
                        objArr[1] = Boolean.valueOf(a2 != null);
                        objArr[2] = "cost";
                        objArr[3] = Long.valueOf(requestStatistic.cacheTime);
                        objArr[4] = "length";
                        objArr[5] = Integer.valueOf(a2 != null ? a2.data.length : 0);
                        objArr[6] = "key";
                        objArr[7] = str;
                        cl.b("anet.CacheTask", "read cache", str3, objArr);
                    }
                    entry = a2;
                }
                long currentTimeMillis2 = System.currentTimeMillis();
                requestStatistic.cacheTime = currentTimeMillis2 - currentTimeMillis;
                if (entry != null && !z && entry.isFresh()) {
                    if (this.a.d.compareAndSet(false, true)) {
                        this.a.a();
                        requestStatistic.ret = 1;
                        requestStatistic.statusCode = 200;
                        requestStatistic.msg = GenBusCodeService.CODE_SUCESS;
                        requestStatistic.protocolType = "cache";
                        requestStatistic.rspEnd = currentTimeMillis2;
                        requestStatistic.processTime = currentTimeMillis2 - requestStatistic.start;
                        if (cl.a(2)) {
                            cl.b("anet.CacheTask", "hit fresh cache", this.a.c, MonitorItemConstants.KEY_URL, this.a.a.b.a.e);
                        }
                        this.a.b.a(200, entry.responseHeaders);
                        this.a.b.a(1, entry.data.length, aa.a(entry.data));
                        this.a.b.a(new DefaultFinishEvent(200, GenBusCodeService.CODE_SUCESS, requestStatistic));
                    }
                } else if (!this.c) {
                    el elVar = new el(this.a, equals ? null : this.b, entry);
                    this.a.e = elVar;
                    elVar.run();
                }
            }
        }
    }
}
