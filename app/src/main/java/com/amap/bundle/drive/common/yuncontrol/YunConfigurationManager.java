package com.amap.bundle.drive.common.yuncontrol;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.autonavi.ae.AEUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.guide.GuideService;
import com.autonavi.minimap.config.ConfigRequestHolder;
import com.autonavi.minimap.config.param.FileUpdateRequest;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

@SuppressFBWarnings({"LI_LAZY_INIT_STATIC"})
public class YunConfigurationManager {
    static String a = "";
    private static String e = "";
    private static volatile YunConfigurationManager f;
    /* access modifiers changed from: 0000 */
    public VersionInfoItem b;
    a c = new a() {
        public final void a() {
            if (YunConfigurationManager.this.b != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(YunConfigurationManager.f());
                sb.append(YunConfigurationManager.this.b.file);
                String a2 = agy.a(new File(sb.toString()), null, true);
                if (TextUtils.isEmpty(YunConfigurationManager.this.b.md5_zip) || !YunConfigurationManager.this.b.md5_zip.equalsIgnoreCase(a2)) {
                    AEUtil.run(new Runnable() {
                        public final void run() {
                            NaviManager.a();
                            NaviManager.a((String) "YunConfigurationManageronDownloadFinish and md5 is not right");
                            StringBuilder sb = new StringBuilder();
                            sb.append(YunConfigurationManager.f());
                            sb.append(YunConfigurationManager.this.b.file);
                            File file = new File(sb.toString());
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                    });
                } else {
                    NaviManager.a();
                    NaviManager.a((String) "YunConfigurationManageronDownloadFinish and md5 is right");
                    AEUtil.run(new Runnable() {
                        public final void run() {
                            YunConfigurationManager yunConfigurationManager = YunConfigurationManager.this;
                            StringBuilder sb = new StringBuilder();
                            sb.append(YunConfigurationManager.f());
                            sb.append(YunConfigurationManager.this.b.file);
                            YunConfigurationManager.a(yunConfigurationManager, sb.toString());
                        }
                    });
                }
            }
        }

        public final void b() {
            NaviManager.a();
            NaviManager.a((String) "YunConfigurationManagerdownload failed.");
            YunConfigurationManager.a(CheckPolicy.Error);
        }
    };
    a d = new a() {
        public final void onFinishProgress(long j) {
            if (j == 100 && YunConfigurationManager.this.b != null && YunConfigurationManager.this.b.items != null && YunConfigurationManager.this.b.items.size() != 0) {
                new File(YunConfigurationManager.d()).mkdirs();
                for (Entry next : YunConfigurationManager.this.b.items.entrySet()) {
                    String str = (String) next.getKey();
                    String str2 = (String) next.getValue();
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(YunConfigurationManager.f());
                        sb.append(str);
                        String sb2 = sb.toString();
                        if (str2.equalsIgnoreCase(agy.a(new File(sb2), null, true))) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(YunConfigurationManager.d());
                            sb3.append(str);
                            YunConfigurationManager.a(sb2, sb3.toString());
                        }
                    }
                }
                YunConfigurationManager.this.a(YunConfigurationManager.f());
            }
        }
    };
    private YunConfigurationCallback g = new YunConfigurationCallback(0);

    static class YunConfigurationCallback implements AosResponseCallbackOnUi<AosByteResponse> {
        private YunConfigurationCallback() {
        }

        /* synthetic */ YunConfigurationCallback(byte b) {
            this();
        }

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            if (aosByteResponse != null && aosByteResponse.getResult() != null) {
                ni niVar = new ni();
                try {
                    niVar.parser((byte[]) aosByteResponse.getResult());
                    if (niVar.a == null) {
                        NaviManager.a();
                        NaviManager.a((String) "YunConfigurationManager : update info from server is null");
                    }
                    VersionInfoItem versionInfoItem = niVar.a;
                    if (versionInfoItem != null) {
                        YunConfigurationManager a = YunConfigurationManager.a();
                        NaviManager.a();
                        StringBuilder sb = new StringBuilder("YunConfigurationManagerremote update exists.\n");
                        sb.append(versionInfoItem.file);
                        sb.append(", \n");
                        sb.append(versionInfoItem.path);
                        sb.append(", \n");
                        sb.append(versionInfoItem.version);
                        sb.append(", \n");
                        sb.append(versionInfoItem.md5_zip);
                        sb.append(", \n");
                        sb.append(versionInfoItem.size);
                        NaviManager.a(sb.toString());
                        if (!TextUtils.isEmpty(versionInfoItem.file)) {
                            a.b = versionInfoItem;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(PathManager.a().b(DirType.DRIVE_VOICE));
                            sb2.append("/autonavi/temp/res/guide/");
                            YunConfigurationManager.a = sb2.toString();
                            AEUtil.run(new Runnable(versionInfoItem) {
                                final /* synthetic */ VersionInfoItem a;

                                {
                                    this.a = r2;
                                }

                                public final void run() {
                                    YunConfigurationManager.this.a(YunConfigurationManager.f());
                                    Uri build = new Builder().encodedPath(this.a.path).appendPath(this.a.file).build();
                                    tg a2 = tg.a();
                                    String encodedPath = build.getEncodedPath();
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(YunConfigurationManager.f());
                                    sb.append(this.a.file);
                                    a2.a(encodedPath, sb.toString(), YunConfigurationManager.this.c);
                                }
                            });
                        }
                    }
                } catch (Exception unused) {
                    NaviManager.a();
                    NaviManager.a((String) "YunConfigurationManager failed to parse response.");
                }
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            NaviManager.a();
            NaviManager.a((String) "YunConfigurationManager remote check error.");
            if (bno.a && aosResponseException != null) {
                aosResponseException.printStackTrace();
            }
            YunConfigurationManager.a();
            YunConfigurationManager.a(CheckPolicy.Error);
        }
    }

    private YunConfigurationManager() {
    }

    public static synchronized YunConfigurationManager a() {
        YunConfigurationManager yunConfigurationManager;
        synchronized (YunConfigurationManager.class) {
            if (f == null) {
                synchronized (YunConfigurationManager.class) {
                    if (f == null) {
                        f = new YunConfigurationManager();
                    }
                }
            }
            yunConfigurationManager = f;
        }
        return yunConfigurationManager;
    }

    /* access modifiers changed from: private */
    public static String f() {
        if (TextUtils.isEmpty(a)) {
            StringBuilder sb = new StringBuilder();
            sb.append(PathManager.a().b(DirType.DRIVE_VOICE));
            sb.append("/autonavi/temp/res/guide/");
            a = sb.toString();
        }
        return a;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:29|30|(2:31|(1:33)(1:95))|34|35|36|37|38|39) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00af */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00d2 A[SYNTHETIC, Splitter:B:60:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d7 A[SYNTHETIC, Splitter:B:64:0x00d7] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00e6 A[SYNTHETIC, Splitter:B:73:0x00e6] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00eb A[SYNTHETIC, Splitter:B:77:0x00eb] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x00f3 A[SYNTHETIC, Splitter:B:84:0x00f3] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x00f8 A[SYNTHETIC, Splitter:B:88:0x00f8] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:57:0x00c8=Splitter:B:57:0x00c8, B:70:0x00dc=Splitter:B:70:0x00dc} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(java.lang.String r4, java.lang.String r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x0116
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x000e
            goto L_0x0116
        L_0x000e:
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            java.io.File r1 = new java.io.File
            r1.<init>(r5)
            boolean r5 = r0.exists()
            if (r5 != 0) goto L_0x0038
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "YunConfigurationManager file "
            r5.<init>(r0)
            r5.append(r4)
            java.lang.String r4 = " does not exist!"
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r4)
            return
        L_0x0038:
            boolean r5 = r0.isFile()
            if (r5 != 0) goto L_0x0058
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "YunConfigurationManager file "
            r5.<init>(r0)
            r5.append(r4)
            java.lang.String r4 = " is not a regular file!"
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r4)
            return
        L_0x0058:
            boolean r5 = r0.canRead()
            if (r5 != 0) goto L_0x0078
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "YunConfigurationManager file "
            r5.<init>(r0)
            r5.append(r4)
            java.lang.String r4 = " can not be read!"
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r4)
            return
        L_0x0078:
            java.io.File r5 = r1.getParentFile()
            if (r5 == 0) goto L_0x00fc
            boolean r5 = r5.exists()
            if (r5 != 0) goto L_0x0086
            goto L_0x00fc
        L_0x0086:
            boolean r4 = r1.exists()
            if (r4 == 0) goto L_0x008f
            r1.delete()
        L_0x008f:
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7, all -> 0x00c4 }
            r5.<init>(r0)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7, all -> 0x00c4 }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00c1, Exception -> 0x00be, all -> 0x00b8 }
            r0.<init>(r1)     // Catch:{ IOException -> 0x00c1, Exception -> 0x00be, all -> 0x00b8 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ IOException -> 0x00c2, Exception -> 0x00bf, all -> 0x00b3 }
        L_0x009e:
            int r1 = r5.read(r4)     // Catch:{ IOException -> 0x00c2, Exception -> 0x00bf, all -> 0x00b3 }
            if (r1 <= 0) goto L_0x00a9
            r2 = 0
            r0.write(r4, r2, r1)     // Catch:{ IOException -> 0x00c2, Exception -> 0x00bf, all -> 0x00b3 }
            goto L_0x009e
        L_0x00a9:
            r0.flush()     // Catch:{ IOException -> 0x00c2, Exception -> 0x00bf, all -> 0x00b3 }
            r5.close()     // Catch:{ Exception -> 0x00af }
        L_0x00af:
            r0.close()     // Catch:{ Exception -> 0x00b2 }
        L_0x00b2:
            return
        L_0x00b3:
            r4 = move-exception
            r3 = r5
            r5 = r4
            r4 = r3
            goto L_0x00f1
        L_0x00b8:
            r0 = move-exception
            r3 = r0
            r0 = r4
            r4 = r5
            r5 = r3
            goto L_0x00f1
        L_0x00be:
            r0 = r4
        L_0x00bf:
            r4 = r5
            goto L_0x00c8
        L_0x00c1:
            r0 = r4
        L_0x00c2:
            r4 = r5
            goto L_0x00dc
        L_0x00c4:
            r5 = move-exception
            r0 = r4
            goto L_0x00f1
        L_0x00c7:
            r0 = r4
        L_0x00c8:
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()     // Catch:{ all -> 0x00f0 }
            java.lang.String r5 = "YunConfigurationManagerFailed to move configuration file cause other exception"
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r5)     // Catch:{ all -> 0x00f0 }
            if (r4 == 0) goto L_0x00d5
            r4.close()     // Catch:{ Exception -> 0x00d5 }
        L_0x00d5:
            if (r0 == 0) goto L_0x00ef
            r0.close()     // Catch:{ Exception -> 0x00da }
        L_0x00da:
            return
        L_0x00db:
            r0 = r4
        L_0x00dc:
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()     // Catch:{ all -> 0x00f0 }
            java.lang.String r5 = "YunConfigurationManagerFailed to move configuration file cause IOException"
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r5)     // Catch:{ all -> 0x00f0 }
            if (r4 == 0) goto L_0x00e9
            r4.close()     // Catch:{ Exception -> 0x00e9 }
        L_0x00e9:
            if (r0 == 0) goto L_0x00ef
            r0.close()     // Catch:{ Exception -> 0x00ee }
        L_0x00ee:
            return
        L_0x00ef:
            return
        L_0x00f0:
            r5 = move-exception
        L_0x00f1:
            if (r4 == 0) goto L_0x00f6
            r4.close()     // Catch:{ Exception -> 0x00f6 }
        L_0x00f6:
            if (r0 == 0) goto L_0x00fb
            r0.close()     // Catch:{ Exception -> 0x00fb }
        L_0x00fb:
            throw r5
        L_0x00fc:
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "YunConfigurationManager file "
            r5.<init>(r0)
            r5.append(r4)
            java.lang.String r4 = " 's directory does not exist!"
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r4)
            return
        L_0x0116:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.common.yuncontrol.YunConfigurationManager.a(java.lang.String, java.lang.String):void");
    }

    public final void a(String str) {
        try {
            b(str);
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e2) {
            NaviManager.a();
            NaviManager.a((String) "YunConfigurationManager删除文件夹操作出错");
            e2.printStackTrace();
        }
    }

    private void b(String str) {
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(list[i]);
                    file = new File(sb.toString());
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(File.separator);
                    sb2.append(list[i]);
                    file = new File(sb2.toString());
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append("/");
                    sb3.append(list[i]);
                    b(sb3.toString());
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append("/");
                    sb4.append(list[i]);
                    a(sb4.toString());
                }
            }
        }
    }

    public static void a(CheckPolicy checkPolicy) {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("yun_control", 0).edit();
        if (edit != null) {
            edit.putInt("policy_state", checkPolicy.ordinal()).apply();
            NaviManager.a();
            StringBuilder sb = new StringBuilder("YunConfigurationManagerYun Control state = ");
            sb.append(checkPolicy.ordinal());
            NaviManager.a(sb.toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b() {
        /*
            r2 = this;
            monitor-enter(r2)
            com.amap.bundle.drive.common.yuncontrol.CheckPolicy r0 = h()     // Catch:{ all -> 0x003b }
            com.amap.bundle.drive.common.yuncontrol.CheckPolicy r1 = com.amap.bundle.drive.common.yuncontrol.CheckPolicy.NoCheck     // Catch:{ all -> 0x003b }
            if (r0 == r1) goto L_0x0011
            com.amap.bundle.drive.common.yuncontrol.CheckPolicy r0 = h()     // Catch:{ all -> 0x003b }
            com.amap.bundle.drive.common.yuncontrol.CheckPolicy r1 = com.amap.bundle.drive.common.yuncontrol.CheckPolicy.Error     // Catch:{ all -> 0x003b }
            if (r0 != r1) goto L_0x0039
        L_0x0011:
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r0 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()     // Catch:{ all -> 0x003b }
            com.amap.bundle.drive.common.yuncontrol.YunConfigurationManager$4 r1 = new com.amap.bundle.drive.common.yuncontrol.YunConfigurationManager$4     // Catch:{ all -> 0x003b }
            r1.<init>()     // Catch:{ all -> 0x003b }
            r0.k = r1     // Catch:{ all -> 0x003b }
            com.amap.bundle.drive.navi.naviwrapper.NaviManager r0 = com.amap.bundle.drive.navi.naviwrapper.NaviManager.a()     // Catch:{ all -> 0x003b }
            boolean r1 = r0.b()     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0033
            java.lang.String r1 = "version obtainVoiceVersion begin"
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r1)     // Catch:{ all -> 0x003b }
            com.autonavi.jni.ae.guide.GuideService r0 = r0.b     // Catch:{ all -> 0x003b }
            r0.obtainVoiceConfigVersion()     // Catch:{ all -> 0x003b }
            monitor-exit(r2)
            return
        L_0x0033:
            java.lang.String r0 = "version obtainVoiceVersion guide service not inited"
            com.amap.bundle.drive.navi.naviwrapper.NaviManager.a(r0)     // Catch:{ all -> 0x003b }
        L_0x0039:
            monitor-exit(r2)
            return
        L_0x003b:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.common.yuncontrol.YunConfigurationManager.b():void");
    }

    /* access modifiers changed from: private */
    public static String g() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private static CheckPolicy h() {
        if (!i()) {
            return CheckPolicy.NoCheck;
        }
        return CheckPolicy.values()[AMapAppGlobal.getApplication().getSharedPreferences("yun_control", 0).getInt("policy_state", 0)];
    }

    private static boolean i() {
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("yun_control", 0);
        String string = sharedPreferences.getString("update_date", "null");
        if (string != null && string.equals(g())) {
            return true;
        }
        Editor edit = sharedPreferences.edit();
        if (edit != null) {
            edit.putString("update_date", g());
        }
        return false;
    }

    static /* synthetic */ void a(YunConfigurationManager yunConfigurationManager, String str) {
        File file = new File(str);
        if (file.exists()) {
            try {
                ahf.a(file, f(), yunConfigurationManager.d);
            } catch (Exception unused) {
                yunConfigurationManager.b(f());
                NaviManager.a();
                NaviManager.a((String) "YunConfigurationManagerFailed to unzip configuration file");
            }
        }
    }

    static /* synthetic */ String d() {
        if (TextUtils.isEmpty(e)) {
            StringBuilder sb = new StringBuilder();
            sb.append(PathManager.a().b(DirType.DRIVE_VOICE));
            sb.append("/autonavi/res/guide/");
            e = sb.toString();
        }
        return e;
    }

    static /* synthetic */ void a(YunConfigurationManager yunConfigurationManager, int i) {
        FileUpdateRequest fileUpdateRequest = new FileUpdateRequest();
        fileUpdateRequest.c = String.valueOf(i);
        fileUpdateRequest.b = GuideService.getEngineVersion();
        ConfigRequestHolder.getInstance().sendFileUpdate(fileUpdateRequest, yunConfigurationManager.g);
    }
}
