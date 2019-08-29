package defpackage;

import android.content.Context;
import com.ali.auth.third.core.model.Constants;
import com.ta.utdid2.device.UTDevice;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.xstate.XStateService;
import mtopsdk.xstate.aidl.IXState;

/* renamed from: fgy reason: default package */
/* compiled from: XState */
public final class fgy {
    static volatile AtomicBoolean a = new AtomicBoolean(false);
    private static final ConcurrentHashMap<String, String> b = new ConcurrentHashMap<>();
    private static fcx<IXState> c;
    private static AtomicBoolean d = new AtomicBoolean(false);
    private static Context e;

    public static void a(Context context) {
        if (context == null) {
            TBSdkLog.d("mtopsdk.XState", "[init]init error,context is null");
        } else if (d.compareAndSet(false, true)) {
            e = context.getApplicationContext();
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b("mtopsdk.XState", "[init]XState init called");
            }
            try {
                String a2 = fha.a();
                if (a2 != null) {
                    b.put(Constants.UA, a2);
                }
                String utdid = UTDevice.getUtdid(context);
                if (utdid != null) {
                    b.put("utdid", utdid);
                }
                b.put("t_offset", "0");
            } catch (Throwable th) {
                TBSdkLog.b((String) "mtopsdk.XState", (String) "[initPhoneInfo]initPhoneInfo error", th);
            }
            if (c == null) {
                AnonymousClass1 r0 = new fcx<IXState>(IXState.class, XStateService.class) {
                    public final void a() {
                        fgy.a.compareAndSet(true, false);
                        ffy.a(new Runnable() {
                            public final void run() {
                                fgy.c();
                            }
                        });
                    }
                };
                c = r0;
                r0.a(context);
                return;
            }
            c();
        }
    }

    public static String a(String str) {
        return a(null, str);
    }

    public static String a(String str, String str2) {
        if (fdd.b(str2)) {
            return null;
        }
        if (fdd.a(str)) {
            str2 = fdd.a(str, str2);
        }
        if (!d() || !a.get()) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b("mtopsdk.XState", "[getValue]Attention :Use XState Local Mode: key:".concat(String.valueOf(str2)));
            }
            return b.get(str2);
        }
        try {
            return ((IXState) c.b()).getValue(str2);
        } catch (Exception e2) {
            TBSdkLog.b((String) "mtopsdk.XState", "[getValue] IXState.getValue(Key) failed,key:".concat(String.valueOf(str2)), (Throwable) e2);
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b("mtopsdk.XState", "[getValue]Attention :Use XState Local Mode: key:".concat(String.valueOf(str2)));
            }
            return b.get(str2);
        }
    }

    public static String b(String str, String str2) {
        if (fdd.b(str2)) {
            return null;
        }
        if (fdd.a(str)) {
            str2 = fdd.a(str, str2);
        }
        if (!d() || !a.get()) {
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b("mtopsdk.XState", "[removeKey]Attention :Use XState Local Mode: key:".concat(String.valueOf(str2)));
            }
            b.remove(str2);
        } else {
            try {
                return ((IXState) c.b()).removeKey(str2);
            } catch (Exception e2) {
                TBSdkLog.b((String) "mtopsdk.XState", "[removeKey] IXState.removeKey(key) failed,key:".concat(String.valueOf(str2)), (Throwable) e2);
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b("mtopsdk.XState", "[removeKey]Attention :Use XState Local Mode: key:".concat(String.valueOf(str2)));
                }
                b.remove(str2);
            }
        }
        return null;
    }

    public static void c(String str, String str2) {
        a(null, str, str2);
    }

    public static void a(String str, String str2, String str3) {
        if (!fdd.b(str2) && !fdd.b(str3)) {
            if (fdd.a(str)) {
                str2 = fdd.a(str, str2);
            }
            if (!d() || !a.get()) {
                if (TBSdkLog.a(LogEnable.WarnEnable)) {
                    StringBuilder sb = new StringBuilder("[setValue]Attention :Use XState Local Mode: key:");
                    sb.append(str2);
                    sb.append(",value:");
                    sb.append(str3);
                    TBSdkLog.b("mtopsdk.XState", sb.toString());
                }
                b.put(str2, str3);
                return;
            }
            try {
                ((IXState) c.b()).setValue(str2, str3);
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder("[setValue] IXState.setValue(key,value) failed,key:");
                sb2.append(str2);
                sb2.append(",value:");
                sb2.append(str3);
                TBSdkLog.b((String) "mtopsdk.XState", sb2.toString(), (Throwable) e2);
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb3 = new StringBuilder("[setValue]Attention :Use XState Local Mode: key:");
                    sb3.append(str2);
                    sb3.append(",value:");
                    sb3.append(str3);
                    TBSdkLog.b("mtopsdk.XState", sb3.toString());
                }
                b.put(str2, str3);
            }
        }
    }

    static void c() {
        String str;
        String str2;
        if (d()) {
            IXState iXState = (IXState) c.b();
            try {
                iXState.init();
                for (Entry next : b.entrySet()) {
                    str = (String) next.getKey();
                    str2 = (String) next.getValue();
                    iXState.setValue(str, str2);
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb = new StringBuilder("[syncToRemote] sync succeed, key:");
                        sb.append(str);
                        sb.append(",value:");
                        sb.append(str2);
                        TBSdkLog.b("mtopsdk.XState", sb.toString());
                    }
                }
                a.compareAndSet(false, true);
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder("[syncToRemote] sync error, key:");
                sb2.append(str);
                sb2.append(",value:");
                sb2.append(str2);
                TBSdkLog.b((String) "mtopsdk.XState", sb2.toString(), (Throwable) e2);
            } catch (Throwable th) {
                TBSdkLog.b((String) "mtopsdk.XState", (String) "syncToRemote error.", th);
            }
        }
    }

    private static boolean d() {
        if (c == null) {
            return false;
        }
        if (c.b() != null) {
            return true;
        }
        c.a(e);
        return false;
    }

    public static String a() {
        return a(null, "t_offset");
    }

    public static boolean b() {
        String a2 = a(null, "AppBackground");
        if (a2 != null) {
            try {
                return Boolean.valueOf(a2).booleanValue();
            } catch (Exception unused) {
                TBSdkLog.d("mtopsdk.XState", "[isAppBackground] parse KEY_APP_BACKGROUND error");
            }
        }
        return false;
    }
}
