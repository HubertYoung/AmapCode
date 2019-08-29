package com.autonavi.minimap.drive.navi.navitts_dependencies;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.autonavi.bundle.banner.data.BannerItem;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.d;
import com.autonavi.minimap.drive.navi.navitts.NaviRecordUtil;
import com.autonavi.minimap.drive.navi.navitts.download.NaviTtsDownloadException;
import com.autonavi.minimap.drive.navi.navitts.download.NaviTtsErrorType;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.model.compat.CompatHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public final class DriveOfflineSDK {
    private static volatile DriveOfflineSDK c;
    private static final Object d = new Object();
    private static final ReentrantLock e = new ReentrantLock();
    public boolean a = false;
    public Handler b;

    class DownloadItemCallback implements Callback<File>, d {
        private boolean isCanceled = false;
        private dgl mDownloadItem = null;

        public String getSavePath() {
            return null;
        }

        public DownloadItemCallback(dgl dgl) {
            this.mDownloadItem = dgl;
        }

        public void callback(File file) {
            DriveOfflineSDK.this.a(this.mDownloadItem, 4);
        }

        public void error(Throwable th, boolean z) {
            if (th != null && (th instanceof NaviTtsDownloadException)) {
                DriveOfflineSDK.this.a(this.mDownloadItem, ((NaviTtsDownloadException) th).getErrorType());
            }
        }

        public void onStart() {
            DriveOfflineSDK.this.a(this.mDownloadItem, 1);
        }

        public void onLoading(long j, long j2) {
            if (!this.isCanceled) {
                DriveOfflineSDK.this.a(this.mDownloadItem, 1);
            }
        }

        public void onCancelled() {
            this.isCanceled = true;
        }
    }

    public static class a {
        public dgl a;
        public NaviTtsErrorType b;

        public a(dgl dgl, NaviTtsErrorType naviTtsErrorType) {
            this.a = dgl;
            this.b = naviTtsErrorType;
        }
    }

    public static int a() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        int i = (iOfflineManager == null || !iOfflineManager.isHaveDownloadingCity()) ? 0 : 1;
        return c().size() > 0 ? i + 2 : i;
    }

    public static boolean b() {
        return dgm.a().a(true);
    }

    public static ArrayList<dgl> c() {
        ArrayList<dgl> arrayList = new ArrayList<>();
        if (dgz.a().d()) {
            dgz.a();
            CopyOnWriteArrayList<dgl> b2 = dgz.b();
            if (b2 != null && b2.size() > 0) {
                Iterator<dgl> it = b2.iterator();
                while (it.hasNext()) {
                    dgl next = it.next();
                    if (next != null && (next.g() == 1 || next.g() == 2)) {
                        arrayList.add(next);
                    }
                }
            }
        }
        return arrayList;
    }

    public static boolean d() {
        return dgz.a().d();
    }

    private DriveOfflineSDK() {
    }

    public static DriveOfflineSDK e() {
        e.lock();
        try {
            if (c == null) {
                c = new DriveOfflineSDK();
            }
            return c;
        } finally {
            e.unlock();
        }
    }

    public static void f() {
        dhb.a("DriveOfflineSDK", "destroy start");
        dgu.b();
        e().o();
    }

    public static String g() {
        StringBuilder sb = new StringBuilder();
        sb.append(dhd.f());
        sb.append("OfflineDbV6.db");
        return sb.toString();
    }

    public static String h() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.a().b());
        sb.append(File.separator);
        sb.append(CompatHelper.DB_OFFLINE_SDCARD_PATH_DBV6);
        return sb.toString();
    }

    public static void i() {
        dgz a2 = dgz.a();
        NaviRecordUtil.init();
        if (dgz.c()) {
            a2.a((dgx) null);
            return;
        }
        a2.e();
        e().m();
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0007 */
    /* JADX WARNING: Removed duplicated region for block: B:2:0x0007 A[LOOP:0: B:2:0x0007->B:14:0x0007, LOOP_START, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void j() {
        /*
            dgz r0 = defpackage.dgz.a()
            java.lang.Object r1 = r0.d
            monitor-enter(r1)
        L_0x0007:
            boolean r2 = r0.c     // Catch:{ all -> 0x0023 }
            if (r2 != 0) goto L_0x0021
            java.lang.String r2 = "OfflineDataInit"
            java.lang.String r3 = "waitTTSDataReady start"
            defpackage.dhb.a(r2, r3)     // Catch:{ InterruptedException -> 0x0007 }
            java.lang.Object r2 = r0.d     // Catch:{ InterruptedException -> 0x0007 }
            r2.wait()     // Catch:{ InterruptedException -> 0x0007 }
            java.lang.String r2 = "OfflineDataInit"
            java.lang.String r3 = "waitTTSDataReady end"
            defpackage.dhb.a(r2, r3)     // Catch:{ InterruptedException -> 0x0007 }
            goto L_0x0007
        L_0x0021:
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            return
        L_0x0023:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0023 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK.j():void");
    }

    public static String a(Context context, String str) {
        if (context == null) {
            return null;
        }
        dgz.a();
        return dgz.a(context, str);
    }

    public static dgl a(String str) {
        dgl dgl;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (dgz.a().d()) {
            dgz.a();
            CopyOnWriteArrayList<dgl> b2 = dgz.b();
            if (b2 != null && b2.size() > 0) {
                Iterator<dgl> it = b2.iterator();
                while (it.hasNext()) {
                    dgl next = it.next();
                    if (next != null && str.equalsIgnoreCase(next.a.f)) {
                        int g = next.g();
                        if ((g == 4 || g == 64) && dgl.a(next.a(), (String) null)) {
                            return next;
                        }
                    }
                }
            }
        }
        try {
            dgl = dgm.a().a(str);
        } catch (Throwable unused) {
            dgl = null;
        }
        return dgl;
    }

    public static List<dgl> k() {
        ArrayList arrayList = new ArrayList();
        if (dgz.a().d()) {
            dgz.a();
            CopyOnWriteArrayList<dgl> b2 = dgz.b();
            if (b2 != null && b2.size() > 0) {
                Iterator<dgl> it = b2.iterator();
                while (it.hasNext()) {
                    dgl next = it.next();
                    if (next != null) {
                        int g = next.g();
                        if ((g == 4 || g == 64) && dgl.a(next.a(), (String) null)) {
                            arrayList.add(next);
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static CopyOnWriteArrayList<dgl> l() {
        CopyOnWriteArrayList<dgl> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        if (dgz.a().d()) {
            dgz.a();
            CopyOnWriteArrayList<dgl> b2 = dgz.b();
            if (b2 != null && b2.size() > 0) {
                Iterator<dgl> it = b2.iterator();
                while (it.hasNext()) {
                    dgl next = it.next();
                    if (!(next == null || next.g() == 0)) {
                        if (!next.e() || next.g() == 4) {
                            copyOnWriteArrayList.add(next);
                        }
                    }
                }
            }
        }
        return copyOnWriteArrayList;
    }

    public final void b(dgl dgl) {
        if (dgl != null && 4 != dgl.g()) {
            dfy.a().a(dgl, new DownloadItemCallback(dgl));
            a(dgl, 2);
        }
    }

    public static void n() {
        dfy.a();
    }

    public final void a(final dgl dgl, final dgx dgx) {
        ahl.a(new defpackage.dgw.a() {
            public final Object doBackground() throws Exception {
                dgl dgl;
                DriveOfflineSDK.b(DriveOfflineSDK.this, dgl);
                dfx a2 = dfx.a();
                dgl dgl2 = dgl;
                AnonymousClass1 r2 = new dgx() {
                    public final void a(boolean z) {
                        if (DriveOfflineSDK.this.b != null) {
                            DriveOfflineSDK.this.b.sendEmptyMessage(41);
                        }
                        if (dgx != null) {
                            dgx.a(true);
                        }
                    }
                };
                String b2 = a2.b();
                if (!TextUtils.isEmpty(b2) && b2.equals(dgl2.a.f)) {
                    String b3 = a2.b();
                    if (!TextUtils.isEmpty(b3)) {
                        DriveOfflineSDK.e();
                        dgl = DriveOfflineSDK.a(b3);
                    } else {
                        dgl = null;
                    }
                    if (dgl == null) {
                        dgl = dfx.d();
                    }
                    if (dgl == null) {
                        DriveOfflineSDK.e();
                        if (DriveOfflineSDK.d()) {
                            CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
                            if (p != null && p.size() > 0) {
                                Iterator<dgl> it = p.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    dgl next = it.next();
                                    if (next != null && next.g() == 4) {
                                        dgl = next;
                                        break;
                                    }
                                }
                            }
                        }
                        dgl = dgm.a().d();
                    }
                    if (dgl != null) {
                        dfx.a(dgl, (dgx) r2);
                    } else {
                        a2.a((String) "linzhilingyuyin", (String) null);
                    }
                }
                if (DriveOfflineSDK.this.b != null) {
                    DriveOfflineSDK.this.b.sendEmptyMessage(50);
                }
                dgm.a().a(false);
                return null;
            }
        });
    }

    public final void c(final dgl dgl) {
        if (dgl != null) {
            ahl.a(new defpackage.dgw.a() {
                final /* synthetic */ boolean b = false;

                public final Object doBackground() throws Exception {
                    DriveOfflineSDK.a(DriveOfflineSDK.this, dgl, this.b);
                    return null;
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(dgl dgl, int i) {
        if (dgl != null && this.b != null) {
            if (i == 0) {
                this.b.sendEmptyMessage(41);
            }
            if (!(2 == i && dgl.g() == 1)) {
                dgl.a(i);
            }
            Message message = new Message();
            message.what = i;
            message.obj = dgl;
            if (this.b != null) {
                this.b.sendMessage(message);
            }
        }
    }

    public final void a(dgl dgl, NaviTtsErrorType naviTtsErrorType) {
        if (dgl != null && this.b != null) {
            Message message = new Message();
            message.what = 5;
            message.obj = new a(dgl, naviTtsErrorType);
            this.b.sendMessage(message);
        }
    }

    public static CopyOnWriteArrayList<dgl> p() {
        dgz.a();
        return dgz.b();
    }

    public static boolean r() {
        return dgz.a().b;
    }

    public static void a(boolean z) {
        dgz.a().b = z;
    }

    public static boolean a(dgl dgl) {
        if (dgl == null) {
            return false;
        }
        String str = dgl.a.f;
        if (dgz.a().d()) {
            dgz.a();
            CopyOnWriteArrayList<dgl> b2 = dgz.b();
            if (b2 != null && b2.size() > 0) {
                Iterator<dgl> it = b2.iterator();
                while (it.hasNext()) {
                    dgl next = it.next();
                    if (next != null && str != null && str.equalsIgnoreCase(next.a.f) && next.g() != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void m() {
        if (dgz.a().d()) {
            ahl.a(new defpackage.dgw.a() {
                public final Object doBackground() throws Exception {
                    CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
                    if (p != null && p.size() > 0) {
                        Iterator<dgl> it = p.iterator();
                        while (it.hasNext()) {
                            dgl next = it.next();
                            int g = next.g();
                            if (g == 1 || g == 2 || g == 5 || g == 10) {
                                next.a(3);
                            }
                        }
                    }
                    return null;
                }
            });
        }
    }

    public final void o() {
        if (dgz.a().d()) {
            ahl.a(new defpackage.dgw.a() {
                final /* synthetic */ boolean a = false;

                public final Object doBackground() throws Exception {
                    CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
                    if (p != null && p.size() > 0) {
                        Iterator<dgl> it = p.iterator();
                        while (it.hasNext()) {
                            dgl next = it.next();
                            if (next != null) {
                                int g = next.g();
                                if (g == 1 || g == 2) {
                                    DriveOfflineSDK.a(DriveOfflineSDK.this, next, this.a);
                                }
                            }
                        }
                    }
                    return null;
                }
            });
        }
    }

    public static LinkedList<BannerItem> q() {
        if (dgz.a().d()) {
            return dgz.a().a;
        }
        return null;
    }

    static /* synthetic */ void b(DriveOfflineSDK driveOfflineSDK, dgl dgl) {
        if (dgl != null) {
            dfy.a().a(dgl);
            dgl.i();
            dgl.a(0);
            dgl.a(0);
            driveOfflineSDK.a(dgl, 0);
        }
    }

    static /* synthetic */ void a(DriveOfflineSDK driveOfflineSDK, dgl dgl, boolean z) {
        if (dgl.g() != 4) {
            dfy.a().a(dgl);
            if (z) {
                dgl.a(10);
                driveOfflineSDK.a(dgl, NaviTtsErrorType.network_exception);
                return;
            }
            dgl.a(3);
            driveOfflineSDK.a(dgl, 3);
        }
    }
}
