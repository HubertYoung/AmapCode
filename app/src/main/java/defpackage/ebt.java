package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.io.File;

/* renamed from: ebt reason: default package */
/* compiled from: VoiceConfig */
public final class ebt {
    private static String a = "";

    public static void a() {
        try {
            a = e();
        } catch (Exception unused) {
        }
        String str = a.a().a;
        boolean z = !TextUtils.equals(str, a);
        if (z) {
            eao.b("song---", "VoiceConfig checkVersion versionChange is true");
            File file = new File(d());
            if (file.exists() && file.canRead()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (int i = 0; i < listFiles.length; i++) {
                        if (listFiles[i] != null && listFiles[i].exists()) {
                            listFiles[i].delete();
                        }
                    }
                }
                file.delete();
            }
            a = str;
            b(str);
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append(d());
            sb.append(File.separator);
            sb.append("custom_voice_bus.bin");
            if (!new File(sb.toString()).exists()) {
                a("custom_voice_bus.bin");
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(d());
            sb2.append(File.separator);
            sb2.append("custom_voice_ride.bin");
            if (!new File(sb2.toString()).exists()) {
                a("custom_voice_ride.bin");
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(d());
            sb3.append(File.separator);
            sb3.append("custom_voice_walk.bin");
            if (!new File(sb3.toString()).exists()) {
                a("custom_voice_walk.bin");
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(d());
            sb4.append(File.separator);
            sb4.append("custom_voice_elec.bin");
            if (!new File(sb4.toString()).exists()) {
                eao.b("song---", "VoiceConfig copyElecVoiceAsset");
                a("custom_voice_elec.bin");
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(d());
            sb5.append(File.separator);
            sb5.append("custom_voice_health_walk.bin");
            if (!new File(sb5.toString()).exists()) {
                eao.b("song---", "VoiceConfig copyHealthRunVoiceAsset");
                a("custom_voice_health_walk.bin");
            }
            StringBuilder sb6 = new StringBuilder();
            sb6.append(d());
            sb6.append(File.separator);
            sb6.append("custom_voice_health_ride.bin");
            if (!new File(sb6.toString()).exists()) {
                eao.b("song---", "VoiceConfig copyHealthRideVoiceAsset");
                a("custom_voice_health_ride.bin");
            }
        }
    }

    public static String b() {
        return d();
    }

    public static String c() {
        return d();
    }

    private static String d() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.a().b(DirType.DRIVE_VOICE));
        sb.append("/autonavi/res/guide");
        return sb.toString();
    }

    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v7, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r6v11, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v4
      assigns: []
      uses: []
      mth insns count: 78
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x008e A[SYNTHETIC, Splitter:B:38:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0098 A[SYNTHETIC, Splitter:B:43:0x0098] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a1 A[SYNTHETIC, Splitter:B:50:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00ab A[SYNTHETIC, Splitter:B:55:0x00ab] */
    /* JADX WARNING: Unknown variable types count: 10 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r6) {
        /*
            java.lang.String r0 = d()
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x0015
            boolean r2 = r1.isDirectory()
            if (r2 != 0) goto L_0x0018
        L_0x0015:
            r1.mkdirs()
        L_0x0018:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "/"
            r1.append(r0)
            r1.append(r6)
            java.lang.String r0 = r1.toString()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0034
            return r2
        L_0x0034:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            r1 = 0
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            java.lang.String r4 = "navi/"
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            java.lang.String r6 = r4.concat(r6)     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            java.io.InputStream r6 = r3.open(r6)     // Catch:{ Exception -> 0x0087, all -> 0x0084 }
            int r3 = r6.available()     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            byte[] r3 = new byte[r3]     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            r6.read(r3)     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            r5.<init>(r0)     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0080, all -> 0x007e }
            r4.write(r3)     // Catch:{ Exception -> 0x007c, all -> 0x007a }
            r2 = 1
            if (r6 == 0) goto L_0x0071
            r6.close()     // Catch:{ Exception -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0071:
            r4.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x009b
        L_0x0075:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x009b
        L_0x007a:
            r0 = move-exception
            goto L_0x009e
        L_0x007c:
            r0 = move-exception
            goto L_0x0082
        L_0x007e:
            r0 = move-exception
            goto L_0x009f
        L_0x0080:
            r0 = move-exception
            r4 = r1
        L_0x0082:
            r1 = r6
            goto L_0x0089
        L_0x0084:
            r0 = move-exception
            r6 = r1
            goto L_0x009f
        L_0x0087:
            r0 = move-exception
            r4 = r1
        L_0x0089:
            r0.printStackTrace()     // Catch:{ all -> 0x009c }
            if (r1 == 0) goto L_0x0096
            r1.close()     // Catch:{ Exception -> 0x0092 }
            goto L_0x0096
        L_0x0092:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0096:
            if (r4 == 0) goto L_0x009b
            r4.close()     // Catch:{ Exception -> 0x0075 }
        L_0x009b:
            return r2
        L_0x009c:
            r0 = move-exception
            r6 = r1
        L_0x009e:
            r1 = r4
        L_0x009f:
            if (r6 == 0) goto L_0x00a9
            r6.close()     // Catch:{ Exception -> 0x00a5 }
            goto L_0x00a9
        L_0x00a5:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00a9:
            if (r1 == 0) goto L_0x00b3
            r1.close()     // Catch:{ Exception -> 0x00af }
            goto L_0x00b3
        L_0x00af:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00b3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebt.a(java.lang.String):boolean");
    }

    private static synchronized boolean b(String str) {
        synchronized (ebt.class) {
            if (AMapPageUtil.getAppContext() == null) {
                return false;
            }
            new MapSharePreference((String) "route_custom_voice_config").putStringValue("voice_config_update_version_key", str);
            return true;
        }
    }

    private static synchronized String e() {
        String str;
        synchronized (ebt.class) {
            str = "";
            if (AMapPageUtil.getAppContext() != null) {
                str = new MapSharePreference((String) "route_custom_voice_config").getStringValue("voice_config_update_version_key", "");
            }
        }
        return str;
    }
}
