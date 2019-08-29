package mtopsdk.mtop.intf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.common.Constants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.global.init.IMtopInitTask;
import mtopsdk.mtop.global.init.MtopInitTaskFactory;

public class Mtop {
    public static final Map<String, Mtop> a = new ConcurrentHashMap();
    public volatile String b = null;
    public final ffd c;
    final IMtopInitTask d;
    public volatile boolean e = false;
    final byte[] f = new byte[0];
    private volatile boolean g = false;

    public interface Id {

        @Retention(RetentionPolicy.SOURCE)
        public @interface Definition {
        }
    }

    private Mtop(String str, @NonNull ffd ffd) {
        this.b = str;
        this.c = ffd;
        this.d = MtopInitTaskFactory.getMtopInitTask(str);
        if (this.d == null) {
            throw new RuntimeException("IMtopInitTask is null,instanceId=".concat(String.valueOf(str)));
        }
    }

    @Deprecated
    public static Mtop a(Context context) {
        return a((String) null, context, (String) null);
    }

    @Deprecated
    public static Mtop a(Context context, String str) {
        return a((String) null, context, str);
    }

    public static Mtop a(String str, @NonNull Context context) {
        return a(str, context, (String) null);
    }

    private static Mtop a(String str, @NonNull Context context, String str2) {
        if (str == null) {
            str = "INNER";
        }
        Mtop mtop = a.get(str);
        if (mtop == null) {
            synchronized (Mtop.class) {
                mtop = a.get(str);
                if (mtop == null) {
                    ffd ffd = ffh.a.get(str);
                    if (ffd == null) {
                        ffd = new ffd(str);
                    }
                    Mtop mtop2 = new Mtop(str, ffd);
                    ffd.b = mtop2;
                    a.put(str, mtop2);
                    mtop = mtop2;
                }
            }
        }
        if (!mtop.g) {
            mtop.b(context, str2);
        }
        return mtop;
    }

    private synchronized void b(Context context, String str) {
        if (!this.g) {
            if (context == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.b);
                sb.append(" [init] The Parameter context can not be null.");
                TBSdkLog.d("mtopsdk.Mtop", sb.toString());
                return;
            }
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.b);
                sb2.append(" [init] context=");
                sb2.append(context);
                sb2.append(", ttid=");
                sb2.append(str);
                TBSdkLog.b("mtopsdk.Mtop", sb2.toString());
            }
            this.c.e = context.getApplicationContext();
            if (fdd.a(str)) {
                this.c.m = str;
            }
            ffy.a(new Runnable() {
                public final void run() {
                    try {
                        synchronized (Mtop.this.f) {
                            long currentTimeMillis = System.currentTimeMillis();
                            try {
                                Mtop.this.a();
                                Mtop.this.d.executeCoreTask(Mtop.this.c);
                                ffy.a(new Runnable() {
                                    public final void run() {
                                        try {
                                            Mtop.this.d.executeExtraTask(Mtop.this.c);
                                        } catch (Throwable th) {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(Mtop.this.b);
                                            sb.append(" [init] executeExtraTask error.");
                                            TBSdkLog.b((String) "mtopsdk.Mtop", sb.toString(), th);
                                        }
                                    }
                                });
                                StringBuilder sb = new StringBuilder();
                                sb.append(Mtop.this.b);
                                sb.append(" [init]do executeCoreTask cost[ms]: ");
                                sb.append(System.currentTimeMillis() - currentTimeMillis);
                                TBSdkLog.b("mtopsdk.Mtop", sb.toString());
                                Mtop.this.e = true;
                                Mtop.this.f.notifyAll();
                            } catch (Throwable th) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(Mtop.this.b);
                                sb2.append(" [init]do executeCoreTask cost[ms]: ");
                                sb2.append(System.currentTimeMillis() - currentTimeMillis);
                                TBSdkLog.b("mtopsdk.Mtop", sb2.toString());
                                Mtop.this.e = true;
                                Mtop.this.f.notifyAll();
                                throw th;
                            }
                        }
                    } catch (Exception e) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Mtop.this.b);
                        sb3.append(" [init] executeCoreTask error.");
                        TBSdkLog.b((String) "mtopsdk.Mtop", sb3.toString(), (Throwable) e);
                    }
                }
            });
            this.g = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        EnvModeEnum envModeEnum = this.c.c;
        if (envModeEnum != null) {
            switch (envModeEnum) {
                case ONLINE:
                case PREPARE:
                    this.c.k = this.c.f;
                    return;
                case TEST:
                case TEST_SANDBOX:
                    this.c.k = this.c.g;
                    break;
            }
        }
    }

    public final Mtop a(final EnvModeEnum envModeEnum) {
        if (envModeEnum == null || this.c.c == envModeEnum) {
            return this;
        }
        if (fdb.a(this.c.e) || this.c.z.compareAndSet(true, false)) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.b);
                sb.append(" [switchEnvMode]MtopSDK switchEnvMode called.envMode=");
                sb.append(envModeEnum);
                TBSdkLog.b("mtopsdk.Mtop", sb.toString());
            }
            ffy.a(new Runnable() {
                public final void run() {
                    Mtop.this.b();
                    if (Mtop.this.c.c == envModeEnum) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(Mtop.this.b);
                        sb.append(" [switchEnvMode] Current EnvMode matches target EnvMode,envMode=");
                        sb.append(envModeEnum);
                        TBSdkLog.b("mtopsdk.Mtop", sb.toString());
                        return;
                    }
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Mtop.this.b);
                        sb2.append(" [switchEnvMode]MtopSDK switchEnvMode start");
                        TBSdkLog.b("mtopsdk.Mtop", sb2.toString());
                    }
                    Mtop.this.c.c = envModeEnum;
                    try {
                        Mtop.this.a();
                        if (EnvModeEnum.ONLINE == envModeEnum) {
                            TBSdkLog.a(false);
                        }
                        Mtop.this.d.executeCoreTask(Mtop.this.c);
                        Mtop.this.d.executeExtraTask(Mtop.this.c);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(Mtop.this.b);
                        sb3.append(" [switchEnvMode]MtopSDK switchEnvMode end. envMode =");
                        sb3.append(envModeEnum);
                        TBSdkLog.b("mtopsdk.Mtop", sb3.toString());
                    }
                }
            });
            return this;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.b);
        sb2.append(" [switchEnvMode]release package can switch environment only once!");
        TBSdkLog.d("mtopsdk.Mtop", sb2.toString());
        return this;
    }

    public final boolean b() {
        if (this.e) {
            return this.e;
        }
        synchronized (this.f) {
            try {
                if (!this.e) {
                    this.f.wait(60000);
                    if (!this.e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.b);
                        sb.append(" [checkMtopSDKInit]Didn't call Mtop.instance(...),please execute global init.");
                        TBSdkLog.d("mtopsdk.Mtop", sb.toString());
                    }
                }
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.b);
                sb2.append(" [checkMtopSDKInit] wait Mtop initLock failed---");
                sb2.append(e2.toString());
                TBSdkLog.d("mtopsdk.Mtop", sb2.toString());
            }
        }
        return this.e;
    }

    @Deprecated
    public static void a(int i, int i2) {
        ffh.a(i, i2);
    }

    private String e(String str) {
        if (fdd.b(str)) {
            str = "DEFAULT";
        }
        return fdd.a(this.b, str);
    }

    public final Mtop a(@Nullable String str, String str2, String str3) {
        String e2 = e(str);
        fgy.a(e2, Constants.KEY_SID, str2);
        fgy.a(e2, Oauth2AccessToken.KEY_UID, str3);
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(e2);
            sb.append(" [registerSessionInfo]register sessionInfo succeed: sid=");
            sb.append(str2);
            sb.append(",uid=");
            sb.append(str3);
            TBSdkLog.b("mtopsdk.Mtop", sb.toString());
        }
        if (this.c.y != null) {
            this.c.y.a(str3);
        }
        return this;
    }

    public final Mtop c() {
        String e2 = e(null);
        fgy.b(e2, Constants.KEY_SID);
        fgy.b(e2, Oauth2AccessToken.KEY_UID);
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder(32);
            sb.append(e2);
            sb.append(" [logout] remove sessionInfo succeed.");
            TBSdkLog.b("mtopsdk.Mtop", sb.toString());
        }
        if (this.c.y != null) {
            this.c.y.a(null);
        }
        return this;
    }

    public final Mtop a(String str) {
        if (str != null) {
            this.c.m = str;
            fgy.a(this.b, "ttid", str);
            if (this.c.y != null) {
                this.c.y.b(str);
            }
        }
        return this;
    }

    public final Mtop b(String str) {
        if (str != null) {
            this.c.n = str;
            fgy.c("utdid", str);
        }
        return this;
    }

    public final String c(String str) {
        return fgy.a(e(str), Constants.KEY_SID);
    }

    public final String d(String str) {
        return fgy.a(e(str), Oauth2AccessToken.KEY_UID);
    }

    public static String d() {
        return fgy.a((String) "utdid");
    }

    public final ffg a(ffb ffb, String str) {
        return new ffg(this, ffb, str);
    }

    public final ffg a(MtopRequest mtopRequest, String str) {
        return new ffg(this, mtopRequest, str);
    }
}
