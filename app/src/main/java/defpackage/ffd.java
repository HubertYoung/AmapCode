package defpackage;

import android.content.Context;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.log.LogAdapter;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.EntranceEnum;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.intf.Mtop;

/* renamed from: ffd reason: default package */
/* compiled from: MtopConfig */
public final class ffd {
    public static LogAdapter v;
    public volatile boolean A = false;
    public volatile boolean B = true;
    public volatile boolean C = false;
    public final Set<Integer> D = new CopyOnWriteArraySet();
    protected final Map<String, String> E = new ConcurrentHashMap();
    public final Map<String, String> F = new ConcurrentHashMap();
    public final Map<String, String> G = new ConcurrentHashMap();
    public final Map<String, String> H = new ConcurrentHashMap();
    protected AtomicBoolean I = new AtomicBoolean(false);
    public defpackage.fge.a J = null;
    public fdy K = null;
    public final a L = new a();
    public final String a;
    public Mtop b;
    public EnvModeEnum c = EnvModeEnum.ONLINE;
    public EntranceEnum d = EntranceEnum.GW_INNER;
    public Context e;
    public int f = 0;
    public int g = 0;
    public String h;
    public String i;
    public String j;
    public int k;
    public volatile fgr l;
    public String m;
    public String n;
    public String o;
    public String p;
    public int q;
    public String r;
    public volatile long s;
    public final byte[] t = new byte[0];
    public ep u;
    public ffv w;
    public fee x;
    public ffj y;
    public AtomicBoolean z = new AtomicBoolean(true);

    /* renamed from: ffd$a */
    /* compiled from: MtopConfig */
    public static class a {
        public final String[] a = new String[4];

        a() {
            this.a[0] = "acs.m.taobao.com";
            this.a[1] = "acs.wapa.taobao.com";
            this.a[2] = "acs.waptest.taobao.com";
            this.a[3] = "api.waptest2nd.taobao.com";
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0011, code lost:
            r1.a[1] = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0016, code lost:
            r1.a[2] = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
            r1.a[3] = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(mtopsdk.mtop.domain.EnvModeEnum r2, java.lang.String r3) {
            /*
                r1 = this;
                int[] r0 = defpackage.ffd.AnonymousClass1.a
                int r2 = r2.ordinal()
                r2 = r0[r2]
                switch(r2) {
                    case 1: goto L_0x000c;
                    case 2: goto L_0x0011;
                    case 3: goto L_0x0016;
                    case 4: goto L_0x001b;
                    default: goto L_0x000b;
                }
            L_0x000b:
                goto L_0x0020
            L_0x000c:
                java.lang.String[] r2 = r1.a
                r0 = 0
                r2[r0] = r3
            L_0x0011:
                java.lang.String[] r2 = r1.a
                r0 = 1
                r2[r0] = r3
            L_0x0016:
                java.lang.String[] r2 = r1.a
                r0 = 2
                r2[r0] = r3
            L_0x001b:
                java.lang.String[] r2 = r1.a
                r0 = 3
                r2[r0] = r3
            L_0x0020:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ffd.a.a(mtopsdk.mtop.domain.EnvModeEnum, java.lang.String):void");
        }
    }

    public ffd(String str) {
        this.a = str;
    }

    public final Map<String, String> a() {
        if (this.I.compareAndSet(false, true)) {
            try {
                InputStream open = this.e.getAssets().open("mtopsdk.property");
                Properties properties = new Properties();
                properties.load(open);
                if (!properties.isEmpty()) {
                    for (Entry entry : properties.entrySet()) {
                        try {
                            Object key = entry.getKey();
                            Object value = entry.getValue();
                            if (key == null || value == null) {
                                StringBuilder sb = new StringBuilder("invalid mtopsdk property,key=");
                                sb.append(key);
                                sb.append(",value=");
                                sb.append(value);
                                TBSdkLog.d("mtopsdk.MtopConfig", sb.toString());
                            } else {
                                this.E.put(key.toString(), value.toString());
                            }
                        } catch (Exception e2) {
                            TBSdkLog.b((String) "mtopsdk.MtopConfig", (String) "load mtopsdk.property in android assets directory error.", (Throwable) e2);
                        }
                    }
                }
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b("mtopsdk.MtopConfig", " load mtopsdk.property file in android assets directory succeed");
                }
            } catch (Exception unused) {
                TBSdkLog.d("mtopsdk.MtopConfig", "load mtopsdk.property in android assets directory failed!");
            }
        }
        return this.E;
    }
}
