package defpackage;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationManagerProxy;
import com.autonavi.common.tool.CrashLogUtil;
import java.io.File;
import java.util.List;

/* renamed from: ud reason: default package */
/* compiled from: LocationDumpCrashControllor */
public final class ud implements bmt {
    private uc a;

    public final List<String> A() {
        return null;
    }

    public final void B() {
    }

    public final void C() {
    }

    public final String F() {
        return null;
    }

    public final boolean H() {
        return true;
    }

    public final int I() {
        return 7;
    }

    public final String J() {
        return "location_sdk";
    }

    public final String K() {
        return "U3CCMdrytJgURH9dS4DIHJQ5KjKsNqym91IXLKkd";
    }

    public final String L() {
        return "armeabi";
    }

    public final int O() {
        return 0;
    }

    public final String a(String str, File file) {
        return null;
    }

    public final String b() {
        return null;
    }

    public final List<Class<?>> e() {
        return null;
    }

    public final String p() {
        return "com.autonavi.minimap:locationservice";
    }

    public final String q() {
        return "/data/data/com.autonavi.minimap/lib";
    }

    public final int r() {
        return 0;
    }

    public final Activity y() {
        return null;
    }

    public final boolean z() {
        return false;
    }

    ud(uc ucVar) {
        this.a = ucVar;
    }

    public final boolean a() {
        return aaw.c(o());
    }

    public final String c() {
        return kx.a(o());
    }

    public final int d() {
        return aaw.d(o());
    }

    public final String f() {
        return NetworkParam.getTaobaoID();
    }

    public final String g() {
        return NetworkParam.getDic();
    }

    public final String h() {
        return NetworkParam.getDiu();
    }

    public final String i() {
        return NetworkParam.getAdiu();
    }

    public final int j() {
        return this.a.b().b();
    }

    public final String k() {
        return this.a.b().c();
    }

    public final int l() {
        return this.a.b().d();
    }

    public final String m() {
        return this.a.b().e();
    }

    public final String n() {
        String b = aaf.b("crash_log_url");
        return !TextUtils.isEmpty(b) ? b : "";
    }

    public final Application o() {
        return this.a.a().a();
    }

    public final File s() {
        String G = G();
        if (G == null) {
            return null;
        }
        File file = new File(G, "uploadcrash");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public final File t() {
        String G = G();
        if (G == null) {
            return null;
        }
        File file = new File(G, "uploadsoerr");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public final File u() {
        return new File(G(), "offline_error");
    }

    public final File v() {
        return new File(G(), ".init_error");
    }

    public final File w() {
        String G = G();
        if (G == null) {
            return null;
        }
        return new File(G, "location_error_log.txt");
    }

    public final File x() {
        String G = G();
        if (G == null) {
            return null;
        }
        return new File(G, "location_heap_error_log.txt");
    }

    public final boolean a(Thread thread, Throwable th, String str) {
        if (!(!TextUtils.isEmpty(str) || thread == null || th == null)) {
            try {
                if (bmx.a(th, ClassNotFoundException.class) || bmx.a(th, NoClassDefFoundError.class)) {
                    CrashLogUtil.addCustomData(LocationManagerProxy.getCrashInfo());
                }
            } catch (Throwable unused) {
            }
        }
        return true;
    }

    public final String D() {
        return FileUtil.getInternalRefreshSDCardPath(o());
    }

    public final String E() {
        return FileUtil.getExternalRefreshSDCardPath(o());
    }

    public final String G() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getAppSDCardFileDir());
        sb.append("/location");
        return sb.toString();
    }

    public final String M() {
        return NetworkParam.getDiv();
    }

    public final String N() {
        return NetworkParam.getDibv();
    }

    public final String P() {
        return this.a.a().d();
    }

    public final String Q() {
        return this.a.a().e();
    }

    public final String R() {
        return this.a.c().a();
    }
}
