package defpackage;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.io.File;
import java.util.List;
import java.util.Map;

@Router({"featureGuide"})
/* renamed from: cxp reason: default package */
/* compiled from: FeatureGuideRouter */
public class cxp extends esk implements bjf, a {
    /* access modifiers changed from: private */
    public a a = null;
    /* access modifiers changed from: private */
    public ProgressDlg b = null;
    private String c = null;
    private String d = null;
    private Uri e = null;

    public void onProgressUpdate(long j, long j2) {
    }

    public void onStart(long j, Map<String, List<String>> map, int i) {
        a();
        aho.a(new Runnable() {
            public final void run() {
                if (cxp.this.b == null) {
                    cxp.this.b = new ProgressDlg(AMapAppGlobal.getTopActivity());
                    cxp.this.b.setMessage("正在下载Lottie测试数据");
                    cxp.this.b.setCancelable(false);
                }
                cxp.this.b.show();
            }
        });
    }

    public void onFinish(bpk bpk) {
        a(this.d, FileUtil.getCacheDir().getAbsolutePath());
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.putBoolean("lottieDebug", true);
        edit.apply();
        a();
        aho.a(new Runnable() {
            public final void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    cxp.this.a = new a(pageContext.getActivity());
                    cxp.this.a.a((CharSequence) "Lottie下载成功，立即重启");
                    cxp.this.a.a((CharSequence) "确定", (a) cxp.this);
                    cxp.this.a.b((CharSequence) "取消", (a) cxp.this);
                    cxp.this.a.a(false);
                    AlertView a2 = cxp.this.a.a();
                    pageContext.showViewLayer(a2);
                    a2.startAnimation();
                }
            }
        });
    }

    public void onError(int i, int i2) {
        a();
        ToastHelper.showToast("下载失败，请重试!");
    }

    private void a() {
        aho.a(new Runnable() {
            public final void run() {
                if (cxp.this.b != null && cxp.this.b.isShowing()) {
                    cxp.this.b.dismiss();
                }
            }
        });
    }

    public void onClick(AlertView alertView, int i) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.dismissViewLayer(alertView);
        }
        if (i == -1) {
            Application application = AMapAppGlobal.getApplication();
            Intent launchIntentForPackage = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
            launchIntentForPackage.addFlags(268435456);
            launchIntentForPackage.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            launchIntentForPackage.addFlags(32768);
            application.getApplicationContext().startActivity(launchIntentForPackage);
            System.exit(0);
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0088 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList a(java.lang.String r8, java.lang.String r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x009d }
            r1.<init>(r8)     // Catch:{ Throwable -> 0x009d }
            r8 = 256(0x100, float:3.59E-43)
            byte[] r8 = new byte[r8]     // Catch:{ Throwable -> 0x009d }
            java.util.zip.ZipInputStream r2 = new java.util.zip.ZipInputStream     // Catch:{ Throwable -> 0x009d }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x009d }
        L_0x0013:
            java.util.zip.ZipEntry r3 = r2.getNextEntry()     // Catch:{ Throwable -> 0x009d }
            if (r3 == 0) goto L_0x0096
            java.lang.String r4 = r3.getName()     // Catch:{ Throwable -> 0x009d }
            java.lang.String r5 = "../"
            boolean r5 = r4.contains(r5)     // Catch:{ Throwable -> 0x009d }
            if (r5 == 0) goto L_0x002e
            java.lang.Exception r8 = new java.lang.Exception     // Catch:{ Throwable -> 0x009d }
            java.lang.String r9 = "unsecurity zipfile!"
            r8.<init>(r9)     // Catch:{ Throwable -> 0x009d }
            throw r8     // Catch:{ Throwable -> 0x009d }
        L_0x002e:
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x009d }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x009d }
            r6.<init>()     // Catch:{ Throwable -> 0x009d }
            r6.append(r9)     // Catch:{ Throwable -> 0x009d }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Throwable -> 0x009d }
            r6.append(r7)     // Catch:{ Throwable -> 0x009d }
            r6.append(r4)     // Catch:{ Throwable -> 0x009d }
            java.lang.String r4 = r6.toString()     // Catch:{ Throwable -> 0x009d }
            r5.<init>(r4)     // Catch:{ Throwable -> 0x009d }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x009d }
            java.io.File r6 = r5.getParentFile()     // Catch:{ Throwable -> 0x009d }
            java.lang.String r6 = r6.getPath()     // Catch:{ Throwable -> 0x009d }
            r4.<init>(r6)     // Catch:{ Throwable -> 0x009d }
            boolean r3 = r3.isDirectory()     // Catch:{ Throwable -> 0x009d }
            if (r3 == 0) goto L_0x0067
            boolean r3 = r5.exists()     // Catch:{ Throwable -> 0x009d }
            if (r3 != 0) goto L_0x0063
            r5.mkdirs()     // Catch:{ Throwable -> 0x009d }
        L_0x0063:
            r2.closeEntry()     // Catch:{ Throwable -> 0x009d }
            goto L_0x0013
        L_0x0067:
            boolean r3 = r4.exists()     // Catch:{ Throwable -> 0x009d }
            if (r3 != 0) goto L_0x0070
            r4.mkdirs()     // Catch:{ Throwable -> 0x009d }
        L_0x0070:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x009d }
            r3.<init>(r5)     // Catch:{ Throwable -> 0x009d }
            java.lang.String r4 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x0088, all -> 0x008c }
            r0.add(r4)     // Catch:{ Exception -> 0x0088, all -> 0x008c }
        L_0x007c:
            int r4 = r2.read(r8)     // Catch:{ Exception -> 0x0088, all -> 0x008c }
            r5 = -1
            if (r4 == r5) goto L_0x0088
            r5 = 0
            r3.write(r8, r5, r4)     // Catch:{ Exception -> 0x0088, all -> 0x008c }
            goto L_0x007c
        L_0x0088:
            r3.close()     // Catch:{ Throwable -> 0x009d }
            goto L_0x0091
        L_0x008c:
            r8 = move-exception
            r3.close()     // Catch:{ Throwable -> 0x009d }
            throw r8     // Catch:{ Throwable -> 0x009d }
        L_0x0091:
            r2.closeEntry()     // Catch:{ Throwable -> 0x009d }
            goto L_0x0013
        L_0x0096:
            r1.close()     // Catch:{ Throwable -> 0x009d }
            r2.close()     // Catch:{ Throwable -> 0x009d }
            goto L_0x00a1
        L_0x009d:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00a1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cxp.a(java.lang.String, java.lang.String):java.util.ArrayList");
    }

    public boolean start(ese ese) {
        boolean z;
        this.e = ese.a;
        List<String> pathSegments = this.e.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 0 && TextUtils.equals(pathSegments.get(0), "lottieDebug") && bno.a) {
            bjg bjg = new bjg(this.d);
            bjg.setUrl(this.e.getQueryParameter("url"));
            StringBuilder sb = new StringBuilder();
            sb.append(FileUtil.getCacheDir().getAbsolutePath());
            sb.append(File.separator);
            sb.append("lottie");
            this.c = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.c);
            sb2.append(FilePathHelper.SUFFIX_DOT_ZIP);
            this.d = sb2.toString();
            if (TextUtils.isEmpty(this.c) || TextUtils.isEmpty(this.d)) {
                z = false;
            } else {
                File file = new File(this.c);
                if (file.exists()) {
                    ahd.c(file);
                }
                z = true;
            }
            if (z) {
                bjh.a().a(bjg, (bjf) this);
                return true;
            }
        }
        return false;
    }
}
