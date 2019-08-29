package defpackage;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: bpp reason: default package */
/* compiled from: RequestStatistics */
public final class bpp {
    public volatile String a;
    public volatile String b = "";
    public volatile String c;
    public volatile int d;
    public volatile int e = 0;
    public volatile String f;
    public volatile byte[] g;
    public volatile int h;
    public volatile long i;
    public volatile StringBuffer j = new StringBuffer();
    public volatile long k = 0;
    public volatile long l = 0;
    public volatile long m = 0;
    public volatile long n = 0;
    public volatile long o = 0;
    public volatile long p = 0;
    public volatile Map<String, Object> q = new ConcurrentHashMap();
    public volatile Object r;

    public final void a(String str) {
        this.j.append(str);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("[RequestStatistics]result=");
        sb.append(this.d);
        sb.append(",statusCode=");
        sb.append(this.e);
        sb.append(",method=");
        sb.append(this.a);
        sb.append(",netType=");
        sb.append(this.b);
        sb.append(",retryTime=");
        sb.append(this.h);
        sb.append(",bizId=");
        sb.append(this.c);
        sb.append(",stepId=");
        sb.append(this.i);
        sb.append(",bizId=");
        sb.append(this.c);
        sb.append(",sendSize=");
        sb.append(this.m);
        sb.append(",recDataSize=");
        sb.append(this.o);
        sb.append(",start=");
        sb.append(this.k);
        sb.append(",requestStartTime=");
        sb.append(this.l);
        sb.append(",requestEndTime=");
        sb.append(this.p);
        sb.append(",url=");
        sb.append(this.f);
        sb.append(",trace=");
        sb.append(this.j);
        try {
            sb.append(",body=");
            sb.append(this.g == null ? "null" : new String(this.g, "utf-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        sb.append(",extra=");
        sb.append(this.r);
        return sb.toString();
    }
}
