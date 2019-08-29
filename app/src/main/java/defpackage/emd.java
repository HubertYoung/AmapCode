package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.sdk.util.e;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.core.network.inter.response.InputStreamResponse;
import com.autonavi.minimap.track.SplashLogManager;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: emd reason: default package */
/* compiled from: Tracker */
public final class emd {
    private static MapSharePreference a = new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents);
    /* access modifiers changed from: private */
    public static int b;

    static /* synthetic */ int c() {
        int i = b;
        b = i + 1;
        return i;
    }

    public static void a(chi chi) {
        String a2 = chi.a();
        StringBuilder sb = new StringBuilder("afp");
        sb.append(chi.e);
        a(a2, sb.toString(), (ArrayList) chi.t, chi.w, (String) LogConstant.SPLASH_SCREEN_SHOWN, (String) ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW);
    }

    public static void b(chi chi) {
        String a2 = chi.a();
        StringBuilder sb = new StringBuilder("afp");
        sb.append(chi.e);
        a(a2, sb.toString(), (ArrayList) chi.u, chi.w, (String) LogConstant.SPLASH_SCREEN_CLICKED, (String) "click");
    }

    public static void c(chi chi) {
        String a2 = chi.a();
        StringBuilder sb = new StringBuilder("afp");
        sb.append(chi.e);
        a(a2, sb.toString(), (ArrayList) null, chi.w, (String) LogConstant.SPLASH_SCREEN_SKIPPED, (String) LogConstant.SPLASH_SCREEN_SKIPPED);
    }

    private static void a(String str, String str2, ArrayList arrayList, String str3, String str4, String str5) {
        a(str, str2, str4, (String) null, (String) null, (String) null);
        a(str, str2, str4, arrayList);
        b(str, str2, str3, str5);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6) {
        SplashLogManager.a(str, str2, str3, str4, str5, str6);
    }

    private static void b(String str, String str2, String str3, String str4) {
        long currentTimeMillis = System.currentTimeMillis() - a.getLongValue("afp_splash_show_to_skip_time", 0);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("session_id", str);
            jSONObject.put("itemId", str2);
            jSONObject.put("type", str3);
            jSONObject.put("from", a.getBooleanValue("afp_splash_again_show", false) ? Subscribe.THREAD_BACKGROUND : "cold");
            if (LogConstant.SPLASH_SCREEN_SKIPPED.equals(str4) || "click".equals(str4)) {
                jSONObject.put("time", currentTimeMillis);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String str5 = "";
        if (ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW.equals(str4)) {
            str5 = "B010";
        } else if ("click".equals(str4)) {
            str5 = "B011";
        } else if (LogConstant.SPLASH_SCREEN_SKIPPED.equals(str4)) {
            str5 = "B012";
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_SPLASH_SCREEN, str5, jSONObject);
    }

    public static void a(chi chi, String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("session_id", chi.a());
            StringBuilder sb = new StringBuilder("afp");
            sb.append(chi.e);
            jSONObject.put("itemId", sb.toString());
            jSONObject.put("type", str);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String str3 = "";
        if (H5PageData.KEY_UC_START.equals(str2)) {
            str3 = "B007";
        } else if ("successed".equals(str2)) {
            str3 = "B008";
        } else if (e.b.equals(str2)) {
            str3 = "B009";
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_SPLASH_SCREEN, str3, jSONObject);
    }

    public static void a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("session_id", str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String str3 = "";
        if (H5PageData.KEY_UC_START.equals(str2)) {
            str3 = "B004";
        } else if ("successed".equals(str2)) {
            str3 = "B005";
        } else if (e.b.equals(str2)) {
            str3 = "B006";
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_SPLASH_SCREEN, str3, jSONObject);
    }

    private static void a(String str, String str2, String str3, ArrayList<String> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!TextUtils.isEmpty(next)) {
                    c(str, str2, str3, next);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void c(final String str, final String str2, final String str3, final String str4) {
        bpf bpf = new bpf();
        bpf.setUrl(str4);
        box.a();
        box.a((bph) bpf, (bpl<T>) new bpl<InputStreamResponse>() {
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0049 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:20:0x004a  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onFailure(defpackage.bph r7, com.autonavi.core.network.inter.response.ResponseException r8) {
                /*
                    r6 = this;
                    int r7 = defpackage.emd.b
                    r8 = 3
                    if (r7 >= r8) goto L_0x0016
                    defpackage.emd.c()
                    java.lang.String r7 = r2
                    java.lang.String r8 = r3
                    java.lang.String r0 = r4
                    java.lang.String r1 = r5
                    defpackage.emd.c(r7, r8, r0, r1)
                    return
                L_0x0016:
                    defpackage.emd.b = 0
                    java.lang.String r7 = "picshow"
                    java.lang.String r8 = r4
                    boolean r7 = r7.equals(r8)
                    r8 = 0
                    if (r7 == 0) goto L_0x0028
                    java.lang.String r7 = "picshow_fail"
                L_0x0026:
                    r4 = r7
                    goto L_0x0036
                L_0x0028:
                    java.lang.String r7 = "clickpicture"
                    java.lang.String r0 = r4
                    boolean r7 = r7.equals(r0)
                    if (r7 == 0) goto L_0x0035
                    java.lang.String r7 = "clickpicture_fail"
                    goto L_0x0026
                L_0x0035:
                    r4 = r8
                L_0x0036:
                    java.lang.String r7 = r5     // Catch:{ Throwable -> 0x0042 }
                    android.net.Uri r7 = android.net.Uri.parse(r7)     // Catch:{ Throwable -> 0x0042 }
                    java.lang.String r7 = r7.getHost()     // Catch:{ Throwable -> 0x0042 }
                    r5 = r7
                    goto L_0x0043
                L_0x0042:
                    r5 = r8
                L_0x0043:
                    boolean r7 = android.text.TextUtils.isEmpty(r5)
                    if (r7 == 0) goto L_0x004a
                    return
                L_0x004a:
                    java.lang.String r0 = r2
                    java.lang.String r1 = r3
                    r2 = 0
                    r3 = 0
                    defpackage.emd.a(r0, r1, r2, r3, r4, r5)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.emd.AnonymousClass1.onFailure(bph, com.autonavi.core.network.inter.response.ResponseException):void");
            }

            /* JADX WARNING: Removed duplicated region for block: B:15:0x0033  */
            /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final /* synthetic */ void onSuccess(defpackage.bpk r8) {
                /*
                    r7 = this;
                    defpackage.emd.b = 0
                    java.lang.String r8 = "picshow"
                    java.lang.String r0 = r4
                    boolean r8 = r8.equals(r0)
                    r0 = 0
                    if (r8 == 0) goto L_0x0012
                    java.lang.String r8 = "picshow"
                L_0x0010:
                    r5 = r8
                    goto L_0x0020
                L_0x0012:
                    java.lang.String r8 = "clickpicture"
                    java.lang.String r1 = r4
                    boolean r8 = r8.equals(r1)
                    if (r8 == 0) goto L_0x001f
                    java.lang.String r8 = "clickpicture"
                    goto L_0x0010
                L_0x001f:
                    r5 = r0
                L_0x0020:
                    java.lang.String r8 = r5     // Catch:{ Throwable -> 0x002c }
                    android.net.Uri r8 = android.net.Uri.parse(r8)     // Catch:{ Throwable -> 0x002c }
                    java.lang.String r8 = r8.getHost()     // Catch:{ Throwable -> 0x002c }
                    r6 = r8
                    goto L_0x002d
                L_0x002c:
                    r6 = r0
                L_0x002d:
                    boolean r8 = android.text.TextUtils.isEmpty(r6)
                    if (r8 != 0) goto L_0x003c
                    java.lang.String r1 = r2
                    java.lang.String r2 = r3
                    r3 = 0
                    r4 = 0
                    defpackage.emd.a(r1, r2, r3, r4, r5, r6)
                L_0x003c:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.emd.AnonymousClass1.onSuccess(bpk):void");
            }
        });
    }
}
