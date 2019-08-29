package mtopsdk.mtop.util;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.common.MtopNetworkProp;
import mtopsdk.network.domain.NetworkStats;

public final class MtopStatistics implements Cloneable {
    private static volatile AtomicBoolean M = new AtomicBoolean(false);
    public long A;
    protected String B;
    public NetworkStats C;
    public String D;
    public final String E;
    public int F;
    public String G;
    public String H;
    public String I;
    public String J;
    public boolean K;
    private a L;
    private ffv N;
    public boolean a;
    public long b;
    public long c;
    public long d;
    public long e;
    public long f;
    public long g;
    public long h;
    public long i;
    public int j;
    public long k;
    public int l;
    public long m;
    public int n;
    public String o;
    public int p;
    public String q;
    public String r;
    public long s;
    public long t;
    protected long u;
    public long v;
    public long w;
    public long x;
    public long y;
    public long z;

    public interface RetType {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    public class a implements Cloneable {
        public long a;
        public long b;
        public long c;
        public long d;
        @Deprecated
        public long e;
        public long f;
        @Deprecated
        public long g;
        public long h;
        public long i;
        public int j;

        /* synthetic */ a(MtopStatistics mtopStatistics, byte b2) {
            this();
        }

        private a() {
            this.j = 0;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("rbReqTime=");
            sb.append(this.d);
            sb.append(",mtopReqTime=");
            sb.append(this.a);
            sb.append(",mtopJsonParseTime=");
            sb.append(this.f);
            sb.append(",toMainThTime=");
            sb.append(this.i);
            sb.append(",isCache=");
            sb.append(this.j);
            sb.append(",beforeReqTime=");
            sb.append(this.b);
            sb.append(",afterReqTime=");
            sb.append(this.c);
            sb.append(",parseTime=");
            sb.append(this.h);
            return sb.toString();
        }

        public final Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private MtopStatistics(ffv ffv) {
        this.a = true;
        this.j = 0;
        this.p = 0;
        this.B = "";
        this.D = "";
        this.N = ffv;
        this.F = fdb.a();
        StringBuilder sb = new StringBuilder("MTOP");
        sb.append(this.F);
        this.E = sb.toString();
    }

    public MtopStatistics(ffv ffv, MtopNetworkProp mtopNetworkProp) {
        this(ffv);
        if (mtopNetworkProp != null) {
            this.J = mtopNetworkProp.pageName;
            this.I = mtopNetworkProp.pageUrl;
            this.K = mtopNetworkProp.backGround;
        }
    }

    public static long a() {
        return System.nanoTime() / 1000000;
    }

    public final void c() {
        this.a = true;
        e();
    }

    private void e() {
        if (this.a && this.N != null) {
            if (M.compareAndSet(false, true)) {
                f();
            }
            try {
                HashMap hashMap = new HashMap();
                hashMap.put(MtopJSParam.API, this.D);
                hashMap.put("ret", this.o);
                hashMap.put("retType", String.valueOf(this.p));
                hashMap.put("httpResponseStatus", String.valueOf(this.n));
                hashMap.put("domain", this.r);
                hashMap.put("cacheSwitch", String.valueOf(this.l));
                hashMap.put("cacheHitType", String.valueOf(this.j));
                hashMap.put("clientTraceId", this.G);
                hashMap.put("serverTraceId", this.H);
                hashMap.put(DictionaryKeys.V2_PAGENAME, this.J);
                hashMap.put(MtopJSParam.PAGE_URL, this.I);
                hashMap.put("backGround", String.valueOf(this.K ? 1 : 0));
                NetworkStats networkStats = this.C;
                if (networkStats != null) {
                    hashMap.put("connType", networkStats.connectionType);
                    hashMap.put("isSSL", networkStats.isSSL ? "1" : "0");
                    hashMap.put("retryTimes", String.valueOf(networkStats.retryTimes));
                    hashMap.put("ip_port", networkStats.ip_port);
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put("totalTime", Double.valueOf((double) this.b));
                hashMap2.put("networkExeTime", Double.valueOf((double) this.c));
                hashMap2.put("cacheCostTime", Double.valueOf((double) this.k));
                hashMap2.put("cacheResponseParseTime", Double.valueOf((double) this.m));
                hashMap2.put("waitExecuteTime", Double.valueOf((double) this.d));
                hashMap2.put("waitCallbackTime", Double.valueOf((double) this.e));
                hashMap2.put("signTime", Double.valueOf((double) this.g));
                hashMap2.put("wuaTime", Double.valueOf((double) this.h));
                hashMap2.put("miniWuaTime", Double.valueOf((double) this.i));
                if (networkStats != null) {
                    hashMap2.put("firstDataTime", Double.valueOf((double) networkStats.firstDataTime));
                    hashMap2.put("recDataTime", Double.valueOf((double) networkStats.recDataTime));
                    hashMap2.put("oneWayTime_ANet", Double.valueOf((double) networkStats.oneWayTime_ANet));
                    hashMap2.put("serverRT", Double.valueOf((double) networkStats.serverRT));
                    hashMap2.put("revSize", Double.valueOf((double) networkStats.recvSize));
                    hashMap2.put("dataSpeed", Double.valueOf((double) networkStats.dataSpeed));
                }
                if (this.L != null) {
                    hashMap2.put("rbReqTime", Double.valueOf((double) this.L.d));
                    hashMap2.put("toMainThTime", Double.valueOf((double) this.L.i));
                    hashMap2.put("mtopJsonParseTime", Double.valueOf((double) this.L.f));
                    hashMap2.put("mtopReqTime", Double.valueOf((double) this.L.a));
                }
                if (this.N != null) {
                    this.N.a((String) "mtopsdk", (String) "mtopStats", (Map<String, String>) hashMap, (Map<String, Double>) hashMap2);
                }
                if (!ErrorConstant.h(this.o)) {
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put(MtopJSParam.API, this.D);
                    hashMap3.put("ret", this.o);
                    hashMap3.put("retType", String.valueOf(this.p));
                    hashMap3.put("mappingCode", this.q);
                    hashMap3.put("httpResponseStatus", String.valueOf(this.n));
                    hashMap3.put("domain", this.r);
                    hashMap3.put("refer", this.I);
                    hashMap3.put("clientTraceId", this.G);
                    hashMap3.put("serverTraceId", this.H);
                    hashMap3.put(DictionaryKeys.V2_PAGENAME, this.J);
                    hashMap3.put(MtopJSParam.PAGE_URL, this.I);
                    hashMap3.put("backGround", String.valueOf(this.K ? 1 : 0));
                    if (this.N != null) {
                        this.N.a((String) "mtopsdk", (String) "mtopExceptions", (Map<String, String>) hashMap3, (Map<String, Double>) null);
                    }
                }
            } catch (Throwable th) {
                String str = this.E;
                StringBuilder sb = new StringBuilder("[commitStatData] commit mtopStats error ---");
                sb.append(th.toString());
                TBSdkLog.d("mtopsdk.MtopStatistics", str, sb.toString());
            } finally {
                this.a = false;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("MtopStatistics ");
        sb.append(hashCode());
        sb.append("[SumStat(ms)]:");
        sb.append(this.B);
        if (this.L != null) {
            sb.append(" [rbStatData]:");
            sb.append(this.L);
        }
        return sb.toString();
    }

    public final synchronized a d() {
        if (this.L == null) {
            this.L = new a(this, 0);
        }
        return this.L;
    }

    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private void f() {
        try {
            if (this.N == null) {
                TBSdkLog.d("mtopsdk.MtopStatistics", this.E, "[registerMtopStats]register MtopStats error, uploadStats=null");
                return;
            }
            HashSet hashSet = new HashSet();
            hashSet.add(MtopJSParam.API);
            hashSet.add("domain");
            hashSet.add("httpResponseStatus");
            hashSet.add("ret");
            hashSet.add("retType");
            hashSet.add("cacheSwitch");
            hashSet.add("cacheHitType");
            hashSet.add("clientTraceId");
            hashSet.add("serverTraceId");
            hashSet.add("connType");
            hashSet.add("isSSL");
            hashSet.add("retryTimes");
            hashSet.add("ip_port");
            hashSet.add(DictionaryKeys.V2_PAGENAME);
            hashSet.add(MtopJSParam.PAGE_URL);
            hashSet.add("backGround");
            HashSet hashSet2 = new HashSet();
            hashSet2.add("totalTime");
            hashSet2.add("networkExeTime");
            hashSet2.add("cacheCostTime");
            hashSet2.add("cacheResponseParseTime");
            hashSet2.add("waitExecuteTime");
            hashSet2.add("waitCallbackTime");
            hashSet2.add("signTime");
            hashSet2.add("wuaTime");
            hashSet2.add("miniWuaTime");
            hashSet2.add("rbReqTime");
            hashSet2.add("toMainThTime");
            hashSet2.add("mtopJsonParseTime");
            hashSet2.add("mtopReqTime");
            hashSet2.add("firstDataTime");
            hashSet2.add("recDataTime");
            hashSet2.add("revSize");
            hashSet2.add("dataSpeed");
            hashSet2.add("oneWayTime_ANet");
            hashSet2.add("serverRT");
            if (this.N != null) {
                this.N.a((String) "mtopsdk", (String) "mtopStats", (Set<String>) hashSet, (Set<String>) hashSet2);
            }
            HashSet hashSet3 = new HashSet();
            hashSet3.add(MtopJSParam.API);
            hashSet3.add("domain");
            hashSet3.add("ret");
            hashSet3.add("retType");
            hashSet3.add("mappingCode");
            hashSet3.add("httpResponseStatus");
            hashSet3.add("refer");
            hashSet3.add("clientTraceId");
            hashSet3.add("serverTraceId");
            hashSet3.add(DictionaryKeys.V2_PAGENAME);
            hashSet3.add(MtopJSParam.PAGE_URL);
            hashSet3.add("backGround");
            if (this.N != null) {
                this.N.a((String) "mtopsdk", (String) "mtopExceptions", (Set<String>) hashSet3, (Set<String>) null);
            }
            String str = this.E;
            StringBuilder sb = new StringBuilder("[registerMtopStats]register MtopStats executed.uploadStats=");
            sb.append(this.N);
            TBSdkLog.b((String) "mtopsdk.MtopStatistics", str, sb.toString());
        } catch (Throwable th) {
            String str2 = this.E;
            StringBuilder sb2 = new StringBuilder("[registerMtopStats] register MtopStats error ---");
            sb2.append(th.toString());
            TBSdkLog.d("mtopsdk.MtopStatistics", str2, sb2.toString());
        }
    }

    public final void b() {
        this.u = System.nanoTime() / 1000000;
        this.b = this.u - this.s;
        long j2 = 0;
        this.d = this.t > this.s ? this.t - this.s : 0;
        this.k = this.v > 0 ? this.v - this.s : 0;
        this.m = this.x - this.w;
        this.c = this.z - this.y;
        if (this.A > this.z) {
            j2 = this.A - this.z;
        }
        this.e = j2;
        StringBuilder sb = new StringBuilder(128);
        sb.append("apiKey=");
        sb.append(this.D);
        sb.append(",httpResponseStatus=");
        sb.append(this.n);
        sb.append(",retCode=");
        sb.append(this.o);
        sb.append(",retType=");
        sb.append(this.p);
        sb.append(",mappingCode=");
        sb.append(this.q);
        sb.append(",mtopTotalTime=");
        sb.append(this.b);
        sb.append(",networkTotalTime=");
        sb.append(this.c);
        sb.append(",waitExecuteTime=");
        sb.append(this.d);
        sb.append(",buildParamsTime=");
        sb.append(this.f);
        sb.append(",computeSignTime=");
        sb.append(this.g);
        sb.append(",computeMiniWuaTime=");
        sb.append(this.i);
        sb.append(",computeWuaTime=");
        sb.append(this.h);
        sb.append(",waitCallbackTime=");
        sb.append(this.e);
        sb.append(",cacheSwitch=");
        sb.append(this.l);
        sb.append(",cacheHitType=");
        sb.append(this.j);
        sb.append(",cacheCostTime=");
        sb.append(this.k);
        sb.append(",cacheResponseParseTime=");
        sb.append(this.m);
        if (this.C != null) {
            sb.append(",");
            if (fdd.b(this.C.netStatSum)) {
                sb.append(this.C.sumNetStat());
            } else {
                sb.append(this.C.netStatSum);
            }
        }
        this.B = sb.toString();
        e();
        TBSdkLog.e(this.G, this.H);
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) "mtopsdk.MtopStatistics", this.E, toString());
        }
    }
}
