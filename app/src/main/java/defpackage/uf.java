package defpackage;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.tool.CrashLogUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/* renamed from: uf reason: default package */
/* compiled from: AmapDumpCrashController */
public final class uf implements bmt {
    private ue a;
    private ui b;
    private uj c;
    private uh d;
    private ug e;

    public final boolean H() {
        return false;
    }

    public final int I() {
        return 0;
    }

    public final String J() {
        return null;
    }

    public final String K() {
        return null;
    }

    public final String L() {
        return "armeabi";
    }

    public final int O() {
        return 100;
    }

    public final String p() {
        return "com.autonavi.minimap";
    }

    public final String q() {
        return "/data/data/com.autonavi.minimap/lib";
    }

    public uf(uc ucVar) {
        this.a = new ue(ucVar);
        this.b = new ui(ucVar);
        this.c = new uj(ucVar);
        this.d = new uh(ucVar);
        this.e = new ug(ucVar);
    }

    public final List<Class<?>> e() {
        ue ueVar = this.a;
        if (ueVar.a == null) {
            ueVar.a = ueVar.b.a().c();
        }
        return ueVar.a;
    }

    public final Application o() {
        return this.a.b.a().a();
    }

    public final File x() {
        ui uiVar = this.b;
        if (uiVar.a == null) {
            String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
            if (appSDCardFileDir == null) {
                return null;
            }
            uiVar.a = new File(appSDCardFileDir, "autonavi_heap_error_log.txt");
        }
        return uiVar.a;
    }

    public final String G() {
        ui uiVar = this.b;
        if (uiVar.b == null) {
            uiVar.b = FileUtil.getAppSDCardFileDir();
        }
        return uiVar.b;
    }

    public final boolean z() {
        uj ujVar = this.c;
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity == null) {
            return false;
        }
        aho.a(new Runnable(topActivity) {
            final /* synthetic */ Activity a;

            {
                this.a = r2;
            }

            public final void run() {
                Builder builder = new Builder(this.a);
                builder.setMessage("高德地图无法继续运行，请卸载不常用的软件后重试");
                builder.setTitle("手机存储空间不足");
                builder.setPositiveButton("确定", new OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        bmx.b();
                    }
                });
                builder.setCancelable(false);
                try {
                    builder.show();
                } catch (Exception e) {
                    DebugLog.error(e);
                }
            }
        });
        return true;
    }

    public final void B() {
        uj ujVar = this.c;
        ujVar.b = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("Start:");
        sb.append(ujVar.b);
        AMapLog.d("DS", sb.toString());
        ujVar.a().putStringValue("dumpcrash_starttime", String.valueOf(System.currentTimeMillis()));
        ujVar.a().putStringValue("dumpcrash_status", "2");
    }

    public final boolean a(Thread thread, Throwable th, String str) {
        uj ujVar = this.c;
        if (TextUtils.isEmpty(str) && thread != null && th != null) {
            String name = th.getClass().getName();
            String message = th.getMessage();
            String str2 = thread == Looper.getMainLooper().getThread() ? "1" : "2";
            MapSharePreference a2 = ujVar.a();
            if (name == null) {
                name = "";
            }
            a2.putStringValue("dumpcrash_etype", name);
            MapSharePreference a3 = ujVar.a();
            if (message == null) {
                message = "";
            }
            a3.putStringValue("dumpcrash_emsg", message);
            ujVar.a().putStringValue("dumpcrash_ismtid", str2);
            try {
                if (!bmx.a(th, ClassNotFoundException.class)) {
                    if (!bmx.a(th, NoClassDefFoundError.class)) {
                        if (bmx.a(th, OutOfMemoryError.class)) {
                            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
                            ThreadGroup threadGroup2 = threadGroup;
                            while (threadGroup != null) {
                                threadGroup2 = threadGroup;
                                threadGroup = threadGroup.getParent();
                            }
                            CrashLogUtil.addCustomData("OutOfMemoryError : thread counts - ".concat(String.valueOf(threadGroup2.activeCount())));
                        }
                    }
                }
                uk ukVar = new uk(ujVar.a.a().a());
                try {
                    if (uk.d.length() > 0) {
                        ukVar.b.append(uk.d.toString());
                        uk.d.delete(0, uk.d.length() - 1);
                    }
                    ukVar.b.append(uk.d());
                    ukVar.b.append("handle start！");
                    ClassLoader b2 = ukVar.b();
                    Object obj = uk.a(b2, "pathList").get(b2);
                    Object[] objArr = (Object[]) uk.a(obj, "dexElements").get(obj);
                    ukVar.b.append("dexElements count：");
                    ukVar.b.append(objArr == null ? 0 : objArr.length);
                    ukVar.c();
                    ukVar.b.append("dexElements detail：");
                    ukVar.b.append(Arrays.toString(objArr));
                    ukVar.c();
                } catch (Exception e2) {
                    ukVar.b.append("get dexElements cause exception：");
                    ukVar.b.append(e2.getMessage());
                    ukVar.c();
                    ukVar.b.append(uk.a((Throwable) e2));
                } catch (Throwable th2) {
                    ukVar.b.append(uk.d());
                    ukVar.b.append("handle error:");
                    ukVar.b.append(th2.getMessage());
                }
                try {
                    SharedPreferences sharedPreferences = ukVar.a.getSharedPreferences("multidex.version", VERSION.SDK_INT < 11 ? 0 : 4);
                    if (sharedPreferences != null) {
                        int i = sharedPreferences.getInt("dex.number", 0);
                        long j = sharedPreferences.getLong("crc", 0);
                        long j2 = sharedPreferences.getLong("timestamp", 0);
                        String valueOf = String.valueOf(j2);
                        if (j2 > 0) {
                            valueOf = ukVar.c.format(new Date(j2));
                        }
                        ukVar.b.append("sp info： dex.number = ");
                        ukVar.b.append(i);
                        ukVar.b.append(", crc = ");
                        ukVar.b.append(j);
                        ukVar.b.append(", timestamp = ");
                        ukVar.b.append(valueOf);
                        ukVar.c();
                    } else {
                        ukVar.b.append("sp info：xml not exist");
                        ukVar.c();
                    }
                } catch (Exception e3) {
                    ukVar.b.append("getSPInfo error: ");
                    ukVar.b.append(e3.getMessage());
                    ukVar.c();
                    ukVar.b.append(uk.a((Throwable) e3));
                }
                ukVar.a(ukVar.a());
                ukVar.b.append("handle finish！");
                if (ukVar.b.length() > 0) {
                    bmp.b();
                    ukVar.b.delete(0, ukVar.b.length() - 1);
                }
            } catch (Throwable unused) {
            }
        } else if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("\n");
            if (split.length != 0) {
                for (String str3 : split) {
                    if (!TextUtils.isEmpty(str3)) {
                        if (str3.startsWith("signal ") && str3.contains("code ")) {
                            int indexOf = str3.indexOf("(");
                            if (-1 != indexOf) {
                                int indexOf2 = str3.indexOf(")", indexOf);
                                if (-1 != indexOf2) {
                                    ujVar.a().putStringValue("dumpcrash_etype", str3.substring(indexOf + 1, indexOf2));
                                    int indexOf3 = str3.indexOf("(", indexOf2);
                                    if (-1 != indexOf3) {
                                        int indexOf4 = str3.indexOf(")", indexOf3);
                                        if (-1 != indexOf4) {
                                            ujVar.a().putStringValue("dumpcrash_emsg", str3.substring(indexOf3 + 1, indexOf4));
                                        }
                                    }
                                }
                            }
                        } else if (str3.startsWith("pid:") && str3.contains("tid:")) {
                            int indexOf5 = str3.indexOf(":");
                            if (-1 != indexOf5) {
                                int indexOf6 = str3.indexOf(",", indexOf5);
                                if (-1 != indexOf6) {
                                    String trim = str3.substring(indexOf5 + 1, indexOf6).trim();
                                    int indexOf7 = str3.indexOf(":", indexOf6);
                                    if (-1 != indexOf7) {
                                        int indexOf8 = str3.indexOf(",", indexOf7);
                                        if (-1 != indexOf8) {
                                            ujVar.a().putStringValue("dumpcrash_ismtid", TextUtils.equals(trim, str3.substring(indexOf7 + 1, indexOf8).trim()) ? "1" : "2");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        AMapLog.flushData();
        return true;
    }

    public final void C() {
        uj ujVar = this.c;
        StringBuilder sb = new StringBuilder("end:");
        sb.append(System.currentTimeMillis());
        sb.append(" used:");
        sb.append(System.currentTimeMillis() - ujVar.b);
        AMapLog.d("DS", sb.toString());
        ujVar.a().putStringValue("dumpcrash_endtime", String.valueOf(System.currentTimeMillis()));
        ujVar.a().putStringValue("dumpcrash_status", "1");
    }

    public final String a(String str, File file) {
        return uj.a(str, file);
    }

    public final String b() {
        return this.d.a();
    }

    public final String c() {
        return kx.a(this.d.b);
    }

    public final int d() {
        return aaw.d(this.d.b);
    }

    public final String i() {
        return this.d != null ? NetworkParam.getAdiu() : "";
    }

    public final int j() {
        return this.d.a.b().b();
    }

    public final String k() {
        return this.d.a.b().c();
    }

    public final int l() {
        return this.d.a.b().d();
    }

    public final String m() {
        return this.d.a.b().e();
    }

    public final String D() {
        return FileUtil.getInternalRefreshSDCardPath(this.d.b);
    }

    public final String E() {
        return FileUtil.getExternalRefreshSDCardPath(this.d.b);
    }

    public final int r() {
        GeoPoint a2 = this.d.a.b().a();
        if (a2 != null) {
            return a2.getAdCode();
        }
        return 0;
    }

    public final String F() {
        String c2 = this.e.a.c().c();
        if (TextUtils.isEmpty(c2)) {
            return null;
        }
        return c2;
    }

    public final String P() {
        return this.a.b.a().d();
    }

    public final String Q() {
        return this.a.b.a().e();
    }

    public final String R() {
        return this.e.a.c().a();
    }

    public final boolean a() {
        return NetworkReachability.b();
    }

    public final File u() {
        return new File(FileUtil.getAppSDCardFileDir(), "offline_error");
    }

    public final File v() {
        return new File(FileUtil.getAppSDCardFileDir(), ".init_error");
    }

    public final Activity y() {
        return AMapAppGlobal.getTopActivity();
    }

    public final String n() {
        return ui.a();
    }

    public final File s() {
        String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
        if (appSDCardFileDir == null) {
            return null;
        }
        File file = new File(appSDCardFileDir, "uploadcrash");
        boolean z = true;
        if (!file.exists()) {
            z = file.mkdir();
        }
        if (!z) {
            return null;
        }
        return file;
    }

    public final File t() {
        String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
        if (appSDCardFileDir == null) {
            return null;
        }
        File file = new File(appSDCardFileDir, "uploadsoerr");
        boolean z = true;
        if (!file.exists()) {
            z = file.mkdir();
        }
        if (!z) {
            return null;
        }
        return file;
    }

    public final File w() {
        String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
        if (appSDCardFileDir == null) {
            return null;
        }
        return new File(appSDCardFileDir, "autonavi_error_log.txt");
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

    public final String M() {
        return NetworkParam.getDiv();
    }

    public final String N() {
        return NetworkParam.getDibv();
    }

    public final List<String> A() {
        return new ArrayList();
    }
}
