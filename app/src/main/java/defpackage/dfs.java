package defpackage;

import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.drive.navi.navitts.PPHelper.PPApkInstallReceiver;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.minimap.offline.preference.OfflinePreference;
import java.io.File;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: dfs reason: default package */
/* compiled from: PPHelperUtil */
public final class dfs {
    private static String a;

    public static dgl a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        DriveOfflineSDK.e();
        CopyOnWriteArrayList<dgl> p = DriveOfflineSDK.p();
        if (p == null || p.size() == 0) {
            return null;
        }
        Iterator<dgl> it = p.iterator();
        while (it.hasNext()) {
            dgl next = it.next();
            if (next != null && str.equals(next.a.i)) {
                return next;
            }
        }
        return null;
    }

    public static PPApkInstallReceiver a(OfflineNaviTtsFragment offlineNaviTtsFragment, String str, dgl dgl) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        Intent intent = new Intent();
        intent.addFlags(268468224);
        intent.setAction("android.intent.action.VIEW");
        kh.a(offlineNaviTtsFragment.getContext(), intent, "application/vnd.android.package-archive", file);
        offlineNaviTtsFragment.getContext().startActivity(intent);
        if (offlineNaviTtsFragment.mIsPPInstallReceiverRegister) {
            return null;
        }
        PPApkInstallReceiver pPApkInstallReceiver = new PPApkInstallReceiver(offlineNaviTtsFragment, dgl, str);
        PPApkInstallReceiver.a(offlineNaviTtsFragment, pPApkInstallReceiver);
        return pPApkInstallReceiver;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0019  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x001b A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(android.content.Context r2) {
        /*
            android.content.pm.PackageManager r2 = r2.getPackageManager()
            r0 = 0
            java.lang.String r1 = "com.pp.assistant"
            android.content.pm.PackageInfo r2 = r2.getPackageInfo(r1, r0)     // Catch:{ Exception -> 0x0010 }
            if (r2 == 0) goto L_0x0014
            int r2 = r2.versionCode     // Catch:{ Exception -> 0x0010 }
            goto L_0x0015
        L_0x0010:
            r2 = move-exception
            r2.printStackTrace()
        L_0x0014:
            r2 = 0
        L_0x0015:
            r1 = 1613(0x64d, float:2.26E-42)
            if (r2 < r1) goto L_0x001b
            r2 = 1
            return r2
        L_0x001b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfs.a(android.content.Context):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0025 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0026 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(android.content.Context r3) {
        /*
            android.content.pm.PackageManager r3 = r3.getPackageManager()
            r0 = 1
            r1 = 0
            java.lang.String r2 = "com.pp.assistant"
            android.content.pm.PackageInfo r3 = r3.getPackageInfo(r2, r1)     // Catch:{ Exception -> 0x001e }
            if (r3 == 0) goto L_0x0022
            java.lang.String r2 = r3.packageName     // Catch:{ Exception -> 0x001e }
            if (r2 == 0) goto L_0x0022
            java.lang.String r3 = r3.packageName     // Catch:{ Exception -> 0x001e }
            java.lang.String r2 = "com.pp.assistant"
            boolean r3 = r3.contains(r2)     // Catch:{ Exception -> 0x001e }
            if (r3 == 0) goto L_0x0022
            r3 = 1
            goto L_0x0023
        L_0x001e:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0022:
            r3 = 0
        L_0x0023:
            if (r3 != 0) goto L_0x0026
            return r0
        L_0x0026:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dfs.b(android.content.Context):boolean");
    }

    public static String c() {
        return a;
    }

    public static void d() {
        a = null;
    }

    public static boolean a() {
        return "1".equals(sy.a().getStringValue(OfflinePreference.KEY_NAVITTS_PP_SWITCH, "0"));
    }

    public static boolean a(dfq dfq) {
        String stringValue = sy.a().getStringValue(OfflinePreference.KEY_NAVITTS_PP_APK_DOWNLOAD_PATH, "");
        if (TextUtils.isEmpty(stringValue)) {
            return false;
        }
        String str = "";
        if (Environment.getExternalStorageDirectory() != null && Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb.append("/pp/PPGaode/download");
            String str2 = null;
            if (!TextUtils.isEmpty(stringValue)) {
                int lastIndexOf = stringValue.lastIndexOf("/");
                if (lastIndexOf != -1) {
                    str2 = stringValue.substring(lastIndexOf);
                }
            }
            sb.append(str2);
            str = sb.toString();
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        bjg bjg = new bjg(str);
        bjg.setUrl(stringValue);
        a = str;
        bjh.a().a(bjg, (bjf) dfq);
        return true;
    }

    public static void b() {
        if (!TextUtils.isEmpty(a)) {
            LogManager.actionLogV2("P00067", "B026", 0, 0);
            bjh.a().a(a);
            a = null;
        }
    }
}
