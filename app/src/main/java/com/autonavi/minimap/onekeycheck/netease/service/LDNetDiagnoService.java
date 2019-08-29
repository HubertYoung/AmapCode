package com.autonavi.minimap.onekeycheck.netease.service;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.autonavi.minimap.onekeycheck.netease.service.LDNetTraceRoute.a;
import com.autonavi.minimap.onekeycheck.netease.utils.LDNetUtil;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class LDNetDiagnoService extends LDNetAsyncTaskEx<String, dsr, String> implements a {
    private static final BlockingQueue<Runnable> q = new LinkedBlockingQueue(2);
    private static final ThreadFactory r = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder("Trace #");
            sb.append(this.a.getAndIncrement());
            Thread thread = new Thread(runnable, sb.toString());
            thread.setPriority(1);
            return thread;
        }
    };
    private String a;
    private String b;
    private boolean c;
    private Context d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private InetAddress[] j;
    private LDNetTraceRoute k;
    private boolean l;
    private LDNetDiagnoListener m;
    private boolean n = true;
    private TelephonyManager o;
    private ThreadPoolExecutor p;

    public void printLogInfo() {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void a(Object obj) {
        String str = (String) obj;
        if (!isCancelled()) {
            super.a(str);
            a(new dsr("check_finish", "sucess"));
            stopNetDialogsis();
            if (this.m != null) {
                this.m.OnNetDiagnoFinished();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void a(Object[] objArr) {
        dsr[] dsrArr = (dsr[]) objArr;
        if (!isCancelled()) {
            super.a((Progress[]) dsrArr);
            if (this.m != null) {
                this.m.OnNetDiagnoUpdated(dsrArr[0]);
            }
        }
    }

    public LDNetDiagnoService(Context context, String str, LDNetDiagnoListener lDNetDiagnoListener) {
        this.d = context;
        this.a = str;
        this.m = lDNetDiagnoListener;
        this.l = false;
        this.o = (TelephonyManager) context.getSystemService("phone");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, q, r);
        this.p = threadPoolExecutor;
    }

    /* access modifiers changed from: protected */
    public final void b() {
        stopNetDialogsis();
    }

    public String startNetDiagnosis() {
        if (TextUtils.isEmpty(this.a)) {
            return "";
        }
        this.l = true;
        a(new dsr("operator", LDNetUtil.getMobileOperator(this.d), true));
        if (this.o != null && TextUtils.isEmpty(this.b)) {
            this.b = this.o.getNetworkCountryIso();
        }
        if (LDNetUtil.isNetworkConnected(this.d).booleanValue()) {
            this.c = true;
        } else {
            this.c = false;
        }
        this.e = LDNetUtil.getNetWorkType(this.d);
        a(new dsr("net_type", this.e, true));
        if (this.c) {
            if ("WIFI".equals(this.e)) {
                this.f = LDNetUtil.getLocalIpByWifi(this.d);
                this.g = LDNetUtil.pingGateWayInWifi(this.d);
            } else {
                this.f = LDNetUtil.getLocalIpBy3G();
            }
            a(new dsr("local_ip", this.f, true));
        } else {
            a(new dsr("local_ip", "127.0.0.1", true));
        }
        if (this.g != null) {
            a(new dsr("out_ip", this.g, true));
        }
        if (this.c) {
            this.h = LDNetUtil.getLocalDns("dns1");
            this.i = LDNetUtil.getLocalDns("dns2");
            StringBuffer stringBuffer = new StringBuffer(this.h);
            stringBuffer.append(",");
            stringBuffer.append(this.i);
            a(new dsr("dns_server", stringBuffer.toString(), true));
        } else {
            a(new dsr("dns_server", "0.0.0.0,0.0.0.0", true));
        }
        if (this.c) {
            a(this.a);
        }
        if (this.c) {
            this.k = LDNetTraceRoute.getInstance();
            this.k.initListenter(this);
            this.k.setIsCTrace(this.n);
            this.k.startTraceRoute(this.a);
            return "finish";
        }
        a(new dsr("net_error", "当前主机未联网,请检查网络！"));
        return "net_error";
    }

    public void stopNetDialogsis() {
        if (this.l) {
            if (this.k != null) {
                this.k.resetInstance();
                this.k = null;
            }
            cancel(true);
            if (this.p != null && !this.p.isShutdown()) {
                this.p.shutdown();
                this.p = null;
            }
            this.l = false;
        }
    }

    public void setIfUseJNICTrace(boolean z) {
        this.n = z;
    }

    private void a(dsr dsr) {
        b((Progress[]) new dsr[]{dsr});
    }

    public void OnNetTraceFinished() {
        a(new dsr("net_trace", "完成traceroute..."));
    }

    public void OnNetTraceUpdated(dsr dsr) {
        a(dsr);
    }

    private boolean a(String str) {
        String str2;
        String str3;
        StringBuffer stringBuffer = new StringBuffer();
        Map<String, Object> domainIp = LDNetUtil.getDomainIp(str);
        String str4 = (String) domainIp.get("useTime");
        this.j = (InetAddress[]) domainIp.get("remoteInet");
        if (Integer.parseInt(str4) > 5000) {
            StringBuilder sb = new StringBuilder(" (");
            sb.append(Integer.parseInt(str4) / 1000);
            sb.append("s)");
            str2 = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder(" (");
            sb2.append(str4);
            sb2.append("ms)");
            str2 = sb2.toString();
        }
        if (this.j != null) {
            for (InetAddress hostAddress : this.j) {
                stringBuffer.append(hostAddress.getHostAddress());
                stringBuffer.append(",");
            }
            StringBuffer stringBuffer2 = new StringBuffer(stringBuffer.substring(0, stringBuffer.length() - 1));
            stringBuffer2.append(str2);
            a(new dsr("dns_parse_result", stringBuffer2.toString(), true));
            return true;
        }
        if (Integer.parseInt(str4) > 10000) {
            Map<String, Object> domainIp2 = LDNetUtil.getDomainIp(str);
            String str5 = (String) domainIp2.get("useTime");
            this.j = (InetAddress[]) domainIp2.get("remoteInet");
            if (Integer.parseInt(str5) > 5000) {
                StringBuilder sb3 = new StringBuilder(" (");
                sb3.append(Integer.parseInt(str5) / 1000);
                sb3.append("s)");
                str3 = sb3.toString();
            } else {
                StringBuilder sb4 = new StringBuilder(" (");
                sb4.append(str5);
                sb4.append("ms)");
                str3 = sb4.toString();
            }
            if (this.j != null) {
                for (InetAddress hostAddress2 : this.j) {
                    stringBuffer.append(hostAddress2.getHostAddress());
                    stringBuffer.append(",");
                }
                StringBuffer stringBuffer3 = new StringBuffer(stringBuffer.substring(0, stringBuffer.length() - 1));
                stringBuffer3.append(str3);
                a(new dsr("dns_parse_result", stringBuffer3.toString(), true));
                return true;
            }
            a(new dsr("dns_parse_result", "parse fail".concat(String.valueOf(str3)), true));
        } else {
            a(new dsr("dns_parse_result", "parse fail".concat(String.valueOf(str2)), true));
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final ThreadPoolExecutor c() {
        return this.p;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object a() {
        if (isCancelled()) {
            return null;
        }
        return startNetDiagnosis();
    }
}
