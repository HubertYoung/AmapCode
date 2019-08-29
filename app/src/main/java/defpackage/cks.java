package defpackage;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.activity.NewMapActivity;
import com.autonavi.map.activity.SplashActivity;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.AjxConstant;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/* renamed from: cks reason: default package */
/* compiled from: DumpCrash */
public final class cks extends cky {

    /* renamed from: cks$a */
    /* compiled from: DumpCrash */
    static class a implements defpackage.uc.a {
        private Application a;

        public final String b() {
            return ".crash.tag";
        }

        a(Application application) {
            this.a = application;
        }

        public final Application a() {
            return this.a;
        }

        public final List<Class<?>> c() {
            return Arrays.asList(new Class[]{SplashActivity.class, NewMapActivity.class});
        }

        public final String d() {
            String b = enh.a(AMapAppGlobal.getApplication()).b();
            if (b != null) {
                return b.substring(b.lastIndexOf(47) + 1);
            }
            return null;
        }

        public final String e() {
            return enh.a(AMapAppGlobal.getApplication()).b();
        }
    }

    /* renamed from: cks$b */
    /* compiled from: DumpCrash */
    static class b implements uc {
        private defpackage.uc.a a;
        private defpackage.uc.c b;
        private defpackage.uc.b c = new c(0);
        private un d = new ckf();

        b(Application application) {
            this.a = new a(application);
            this.b = new d(application);
        }

        public final defpackage.uc.a a() {
            return this.a;
        }

        public final defpackage.uc.c b() {
            return this.b;
        }

        public final defpackage.uc.b c() {
            return this.c;
        }

        public final un d() {
            return this.d;
        }
    }

    /* renamed from: cks$c */
    /* compiled from: DumpCrash */
    static class c implements defpackage.uc.b {
        private c() {
        }

        /* synthetic */ c(byte b) {
            this();
        }

        public final String a() {
            vg vgVar = (vg) ank.a(vg.class);
            if (vgVar == null) {
                return "";
            }
            return vgVar.a();
        }

        public final String b() {
            if (!TextUtils.isEmpty(AjxConstant.AAR_VERSION)) {
                return AjxConstant.AAR_VERSION;
            }
            return chl.e();
        }

        public final String c() {
            return ckg.a();
        }
    }

    /* renamed from: cks$d */
    /* compiled from: DumpCrash */
    static class d implements defpackage.uc.c {
        private Application a;

        d(Application application) {
            this.a = application;
        }

        public final int b() {
            return chl.a();
        }

        public final String c() {
            return chl.b();
        }

        public final int d() {
            return chl.c();
        }

        public final String e() {
            return chl.d();
        }

        public final String f() {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                Activity activity = pageContext.getActivity();
                if (activity != null) {
                    return activity.getClass().getName();
                }
            }
            return "";
        }

        public final Class g() {
            return AMapPageUtil.getTopPageClass();
        }

        public final GeoPoint a() {
            return LocationInstrument.getInstance().getLatestPosition(5);
        }
    }

    /* renamed from: cks$e */
    /* compiled from: DumpCrash */
    public static class e extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "InstallErrorProcessDumpCrash";
        }

        public final void a(Application application) {
            ub.a(3, new b(application));
        }
    }

    /* renamed from: cks$f */
    /* compiled from: DumpCrash */
    public static class f extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "LocationProcessDumpCrash";
        }

        public final void a(Application application) {
            ub.a(1, new b(application));
        }
    }

    /* renamed from: cks$g */
    /* compiled from: DumpCrash */
    public static class g extends cky {
        /* access modifiers changed from: 0000 */
        @NonNull
        public final String a() {
            return "LotuspoolProcessDumpCrash";
        }

        public final void a(Application application) {
            ub.a(2, new b(application));
        }
    }

    /* renamed from: cks$h */
    /* compiled from: DumpCrash */
    static class h implements defpackage.dro.e {
        private h() {
        }

        /* synthetic */ h(byte b) {
            this();
        }

        public final void a(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                Class<?> cls = abstractBasePage.getClass();
                String pageIdentifier = AMapPageUtil.getPageIdentifier(abstractBasePage);
                StringBuilder sb = new StringBuilder();
                sb.append(cls.getSimpleName());
                sb.append(AUScreenAdaptTool.PREFIX_ID);
                sb.append(cls.hashCode());
                String sb2 = sb.toString();
                if (!TextUtils.isEmpty(pageIdentifier)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("(");
                    sb3.append(pageIdentifier);
                    sb3.append(")");
                    sb2 = sb3.toString();
                }
                bmp.b();
                bmp.a((String) "LastPage", sb2);
            }
        }

        public final void b(@NonNull WeakReference<AbstractBasePage> weakReference) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) weakReference.get();
            if (abstractBasePage != null) {
                Class<?> cls = abstractBasePage.getClass();
                String pageIdentifier = AMapPageUtil.getPageIdentifier(abstractBasePage);
                StringBuilder sb = new StringBuilder();
                sb.append(cls.getSimpleName());
                sb.append(AUScreenAdaptTool.PREFIX_ID);
                sb.append(cls.hashCode());
                String sb2 = sb.toString();
                if (!TextUtils.isEmpty(pageIdentifier)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append("(");
                    sb3.append(pageIdentifier);
                    sb3.append(")");
                }
                bmp.b();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "DumpCrash";
    }

    public final void a(Application application) {
        ub.a(0, new b(application));
        bmp.a((String) "IsFirstInstall", emu.a());
        emt a2 = emu.a((String) "VERSION_CURVERINFO");
        if (a2 != null) {
            bmp.a((String) "CurrentVersion", a2.toString());
        }
        emt a3 = emu.a((String) "VERSION_LASTVERINFO");
        if (a3 != null) {
            bmp.a((String) "LastUpdateVersion", a3.toString());
        }
        DebugLog.DEBUG = bno.a;
        drm.a((defpackage.dro.c) new h(0));
        int i = enh.a(application).b.a.a.getSharedPreferences("HotfixSharedPreferences", 0).getInt("hotfix_successful_version", -1);
        if (i != -1) {
            bmp.a((String) "SoHotfixSuccessful", i);
        }
    }
}
