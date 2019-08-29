package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.vcs.NuiConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eqa reason: default package */
/* compiled from: CommonUtils */
public final class eqa {
    public static final String a;
    public static final String b;

    static {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append("/idst");
        a = sb.toString();
        if (Environment.getExternalStorageDirectory() != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().toString());
            sb2.append("/autonavi/idst/debug");
            str = sb2.toString();
        } else {
            str = "";
        }
        b = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0083, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean a(android.content.Context r8) {
        /*
            java.lang.Class<eqa> r0 = defpackage.eqa.class
            monitor-enter(r0)
            java.lang.String r1 = c()     // Catch:{ all -> 0x0084 }
            android.content.Context r2 = r8.getApplicationContext()     // Catch:{ all -> 0x0084 }
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch:{ all -> 0x0084 }
            java.lang.String r3 = "AliSR"
            r4 = 0
            android.content.SharedPreferences r8 = r8.getSharedPreferences(r3, r4)     // Catch:{ all -> 0x0084 }
            boolean r8 = a(r8)     // Catch:{ all -> 0x0084 }
            r3 = 1
            if (r8 == 0) goto L_0x0082
            java.util.List r8 = a(r2)     // Catch:{ all -> 0x0084 }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ all -> 0x0084 }
        L_0x0025:
            boolean r5 = r8.hasNext()     // Catch:{ all -> 0x0084 }
            if (r5 == 0) goto L_0x004d
            java.lang.Object r5 = r8.next()     // Catch:{ all -> 0x0084 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x0084 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0084 }
            r6.<init>()     // Catch:{ all -> 0x0084 }
            r6.append(r1)     // Catch:{ all -> 0x0084 }
            java.lang.String r7 = "/"
            r6.append(r7)     // Catch:{ all -> 0x0084 }
            r6.append(r5)     // Catch:{ all -> 0x0084 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0084 }
            boolean r5 = a(r2, r5, r6)     // Catch:{ all -> 0x0084 }
            if (r5 != 0) goto L_0x0025
            r8 = 0
            goto L_0x004e
        L_0x004d:
            r8 = 1
        L_0x004e:
            if (r8 == 0) goto L_0x0080
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ all -> 0x0084 }
            java.lang.String r1 = "AliSR"
            android.content.SharedPreferences r8 = r8.getSharedPreferences(r1, r4)     // Catch:{ all -> 0x0084 }
            java.lang.String r1 = "VERSION_CURVERINFO"
            emt r1 = defpackage.emu.a(r1)     // Catch:{ all -> 0x0084 }
            if (r8 == 0) goto L_0x0082
            if (r1 == 0) goto L_0x0082
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x0084 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0084 }
            if (r2 != 0) goto L_0x0082
            android.content.SharedPreferences$Editor r8 = r8.edit()     // Catch:{ all -> 0x0084 }
            java.lang.String r2 = "version"
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0084 }
            android.content.SharedPreferences$Editor r8 = r8.putString(r2, r1)     // Catch:{ all -> 0x0084 }
            r8.apply()     // Catch:{ all -> 0x0084 }
            goto L_0x0082
        L_0x0080:
            monitor-exit(r0)
            return r4
        L_0x0082:
            monitor-exit(r0)
            return r3
        L_0x0084:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eqa.a(android.content.Context):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0041 A[Catch:{ all -> 0x0072 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0062 A[SYNTHETIC, Splitter:B:32:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006a A[Catch:{ IOException -> 0x0066 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0075 A[SYNTHETIC, Splitter:B:41:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x007d A[Catch:{ IOException -> 0x0079 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(android.content.Context r6, java.lang.String r7) {
        /*
            r0 = 0
            android.content.res.AssetManager r6 = r6.getAssets()     // Catch:{ IOException -> 0x003a, all -> 0x0036 }
            java.io.InputStream r6 = r6.open(r7)     // Catch:{ IOException -> 0x003a, all -> 0x0036 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0033, all -> 0x0030 }
            r1.<init>()     // Catch:{ IOException -> 0x0033, all -> 0x0030 }
        L_0x000e:
            int r2 = r6.read()     // Catch:{ IOException -> 0x002e }
            r3 = -1
            if (r2 == r3) goto L_0x0019
            r1.write(r2)     // Catch:{ IOException -> 0x002e }
            goto L_0x000e
        L_0x0019:
            java.lang.String r2 = r1.toString()     // Catch:{ IOException -> 0x002e }
            if (r6 == 0) goto L_0x0025
            r6.close()     // Catch:{ IOException -> 0x0023 }
            goto L_0x0025
        L_0x0023:
            r6 = move-exception
            goto L_0x0029
        L_0x0025:
            r1.close()     // Catch:{ IOException -> 0x0023 }
            goto L_0x002c
        L_0x0029:
            r6.printStackTrace()
        L_0x002c:
            r0 = r2
            goto L_0x0071
        L_0x002e:
            r2 = move-exception
            goto L_0x003d
        L_0x0030:
            r7 = move-exception
            r1 = r0
            goto L_0x0073
        L_0x0033:
            r2 = move-exception
            r1 = r0
            goto L_0x003d
        L_0x0036:
            r7 = move-exception
            r6 = r0
            r1 = r6
            goto L_0x0073
        L_0x003a:
            r2 = move-exception
            r6 = r0
            r1 = r6
        L_0x003d:
            boolean r3 = defpackage.bno.a     // Catch:{ all -> 0x0072 }
            if (r3 == 0) goto L_0x0060
            java.lang.String r3 = "VUI_JAVA"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0072 }
            java.lang.String r5 = "read asset file error , "
            r4.<init>(r5)     // Catch:{ all -> 0x0072 }
            r4.append(r7)     // Catch:{ all -> 0x0072 }
            java.lang.String r7 = " , "
            r4.append(r7)     // Catch:{ all -> 0x0072 }
            java.lang.String r7 = r2.toString()     // Catch:{ all -> 0x0072 }
            r4.append(r7)     // Catch:{ all -> 0x0072 }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x0072 }
            defpackage.bfh.a(r3, r7)     // Catch:{ all -> 0x0072 }
        L_0x0060:
            if (r6 == 0) goto L_0x0068
            r6.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0068
        L_0x0066:
            r6 = move-exception
            goto L_0x006e
        L_0x0068:
            if (r1 == 0) goto L_0x0071
            r1.close()     // Catch:{ IOException -> 0x0066 }
            goto L_0x0071
        L_0x006e:
            r6.printStackTrace()
        L_0x0071:
            return r0
        L_0x0072:
            r7 = move-exception
        L_0x0073:
            if (r6 == 0) goto L_0x007b
            r6.close()     // Catch:{ IOException -> 0x0079 }
            goto L_0x007b
        L_0x0079:
            r6 = move-exception
            goto L_0x0081
        L_0x007b:
            if (r1 == 0) goto L_0x0084
            r1.close()     // Catch:{ IOException -> 0x0079 }
            goto L_0x0084
        L_0x0081:
            r6.printStackTrace()
        L_0x0084:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eqa.a(android.content.Context, java.lang.String):java.lang.String");
    }

    public static boolean a(SharedPreferences sharedPreferences) {
        if (sharedPreferences == null) {
            return true;
        }
        String string = sharedPreferences.getString("version", "default");
        emt a2 = emu.a((String) "VERSION_CURVERINFO");
        if (a2 != null && string.equals(a2.toString())) {
            return false;
        }
        return true;
    }

    private static String c() {
        StringBuilder sb = new StringBuilder("yuanhc1111 use dataPath = ");
        sb.append(a);
        AMapLog.d("VUI_JAVA", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a);
        sb2.append("/asr");
        return sb2.toString();
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r3v4, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.io.Reader, java.io.InputStreamReader] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x006f, code lost:
        if (r3 == 0) goto L_0x0072;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v6
      assigns: []
      uses: []
      mth insns count: 55
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
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0055 A[SYNTHETIC, Splitter:B:35:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x005a A[Catch:{ IOException -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x005f A[Catch:{ IOException -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0067 A[SYNTHETIC, Splitter:B:46:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x006c A[Catch:{ IOException -> 0x0072 }] */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<java.lang.String> a(android.content.res.AssetManager r6) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.lang.String r2 = "copylist.txt"
            java.io.InputStream r6 = r6.open(r2)     // Catch:{ IOException -> 0x0063, all -> 0x004f }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            r2.<init>(r6)     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0047, all -> 0x0044 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0047, all -> 0x0044 }
        L_0x0016:
            java.lang.String r1 = r3.readLine()     // Catch:{ IOException -> 0x0048, all -> 0x0042 }
            if (r1 == 0) goto L_0x0036
            java.lang.String r4 = r1.trim()     // Catch:{ IOException -> 0x0048, all -> 0x0042 }
            java.lang.String r5 = "#"
            boolean r4 = r4.startsWith(r5)     // Catch:{ IOException -> 0x0048, all -> 0x0042 }
            if (r4 != 0) goto L_0x0016
            java.lang.String r4 = r1.trim()     // Catch:{ IOException -> 0x0048, all -> 0x0042 }
            boolean r4 = r4.isEmpty()     // Catch:{ IOException -> 0x0048, all -> 0x0042 }
            if (r4 != 0) goto L_0x0016
            r0.add(r1)     // Catch:{ IOException -> 0x0048, all -> 0x0042 }
            goto L_0x0016
        L_0x0036:
            if (r6 == 0) goto L_0x003b
            r6.close()     // Catch:{ IOException -> 0x0072 }
        L_0x003b:
            r2.close()     // Catch:{ IOException -> 0x0072 }
        L_0x003e:
            r3.close()     // Catch:{ IOException -> 0x0072 }
            goto L_0x0072
        L_0x0042:
            r0 = move-exception
            goto L_0x0053
        L_0x0044:
            r0 = move-exception
            r3 = r1
            goto L_0x0053
        L_0x0047:
            r3 = r1
        L_0x0048:
            r1 = r2
            goto L_0x0065
        L_0x004a:
            r0 = move-exception
            r2 = r1
            goto L_0x0052
        L_0x004d:
            r3 = r1
            goto L_0x0065
        L_0x004f:
            r0 = move-exception
            r6 = r1
            r2 = r6
        L_0x0052:
            r3 = r2
        L_0x0053:
            if (r6 == 0) goto L_0x0058
            r6.close()     // Catch:{ IOException -> 0x0062 }
        L_0x0058:
            if (r2 == 0) goto L_0x005d
            r2.close()     // Catch:{ IOException -> 0x0062 }
        L_0x005d:
            if (r3 == 0) goto L_0x0062
            r3.close()     // Catch:{ IOException -> 0x0062 }
        L_0x0062:
            throw r0
        L_0x0063:
            r6 = r1
            r3 = r6
        L_0x0065:
            if (r6 == 0) goto L_0x006a
            r6.close()     // Catch:{ IOException -> 0x0072 }
        L_0x006a:
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ IOException -> 0x0072 }
        L_0x006f:
            if (r3 == 0) goto L_0x0072
            goto L_0x003e
        L_0x0072:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eqa.a(android.content.res.AssetManager):java.util.List");
    }

    private static boolean a(AssetManager assetManager, String str, String str2) {
        boolean z;
        try {
            String[] list = assetManager.list(str);
            boolean z2 = true;
            if (list.length == 0) {
                z = b(assetManager, str, str2) & true;
            } else {
                File file = new File(str2);
                if (!file.exists()) {
                    file.mkdirs();
                }
                for (int i = 0; i < list.length; i++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("/");
                    sb.append(list[i]);
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str2);
                    sb3.append("/");
                    sb3.append(list[i]);
                    z2 = a(assetManager, sb2, sb3.toString());
                    if (!z2) {
                        return z2;
                    }
                }
                z = z2;
            }
            return z;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0069 A[Catch:{ all -> 0x0097 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0082 A[SYNTHETIC, Splitter:B:37:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x008a A[Catch:{ IOException -> 0x0086 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x009a A[SYNTHETIC, Splitter:B:47:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00a2 A[Catch:{ IOException -> 0x009e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(android.content.res.AssetManager r5, java.lang.String r6, java.lang.String r7) {
        /*
            int r0 = r6.length()
            r1 = 1
            r2 = 0
            if (r0 <= 0) goto L_0x0014
            char r0 = r6.charAt(r2)
            r3 = 47
            if (r0 != r3) goto L_0x0014
            java.lang.String r6 = r6.substring(r1)
        L_0x0014:
            r0 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            java.lang.String r4 = "copying asset files from ["
            r3.<init>(r4)     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            r3.append(r6)     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            java.lang.String r4 = "] to ["
            r3.append(r4)     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            r3.append(r7)     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            java.lang.String r4 = "]"
            r3.append(r4)     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            java.io.InputStream r5 = r5.open(r6)     // Catch:{ Exception -> 0x0064, all -> 0x0061 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x005e, all -> 0x005a }
            r3.<init>(r7)     // Catch:{ Exception -> 0x005e, all -> 0x005a }
            r3.createNewFile()     // Catch:{ Exception -> 0x005e, all -> 0x005a }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x005e, all -> 0x005a }
            r3.<init>(r7)     // Catch:{ Exception -> 0x005e, all -> 0x005a }
            a(r5, r3)     // Catch:{ Exception -> 0x005f, all -> 0x0058 }
            r3.flush()     // Catch:{ Exception -> 0x005f, all -> 0x0058 }
            if (r5 == 0) goto L_0x004b
            r5.close()     // Catch:{ IOException -> 0x0049 }
            goto L_0x004b
        L_0x0049:
            r5 = move-exception
            goto L_0x004f
        L_0x004b:
            r3.close()     // Catch:{ IOException -> 0x0049 }
            goto L_0x0057
        L_0x004f:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
        L_0x0057:
            return r1
        L_0x0058:
            r6 = move-exception
            goto L_0x005c
        L_0x005a:
            r6 = move-exception
            r3 = r0
        L_0x005c:
            r0 = r5
            goto L_0x0098
        L_0x005e:
            r3 = r0
        L_0x005f:
            r0 = r5
            goto L_0x0065
        L_0x0061:
            r6 = move-exception
            r3 = r0
            goto L_0x0098
        L_0x0064:
            r3 = r0
        L_0x0065:
            boolean r5 = defpackage.bno.a     // Catch:{ all -> 0x0097 }
            if (r5 == 0) goto L_0x0080
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0097 }
            java.lang.String r1 = "Abort copying asset files from ["
            r5.<init>(r1)     // Catch:{ all -> 0x0097 }
            r5.append(r6)     // Catch:{ all -> 0x0097 }
            java.lang.String r6 = "] to ["
            r5.append(r6)     // Catch:{ all -> 0x0097 }
            r5.append(r7)     // Catch:{ all -> 0x0097 }
            java.lang.String r6 = "]"
            r5.append(r6)     // Catch:{ all -> 0x0097 }
        L_0x0080:
            if (r0 == 0) goto L_0x0088
            r0.close()     // Catch:{ IOException -> 0x0086 }
            goto L_0x0088
        L_0x0086:
            r5 = move-exception
            goto L_0x008e
        L_0x0088:
            if (r3 == 0) goto L_0x0096
            r3.close()     // Catch:{ IOException -> 0x0086 }
            goto L_0x0096
        L_0x008e:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
        L_0x0096:
            return r2
        L_0x0097:
            r6 = move-exception
        L_0x0098:
            if (r0 == 0) goto L_0x00a0
            r0.close()     // Catch:{ IOException -> 0x009e }
            goto L_0x00a0
        L_0x009e:
            r5 = move-exception
            goto L_0x00a6
        L_0x00a0:
            if (r3 == 0) goto L_0x00ae
            r3.close()     // Catch:{ IOException -> 0x009e }
            goto L_0x00ae
        L_0x00a6:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r5)
        L_0x00ae:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eqa.b(android.content.res.AssetManager, java.lang.String, java.lang.String):boolean");
    }

    private static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public static int a(String str) {
        File file = new File(str);
        if (file.exists()) {
            StringBuilder sb = new StringBuilder("The directory [ ");
            sb.append(str);
            sb.append(" ] has already exists");
            AMapLog.w("VUI_JAVA", sb.toString());
            return 1;
        }
        if (!str.endsWith(File.separator)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(File.separator);
            str = sb2.toString();
        }
        if (file.mkdirs()) {
            StringBuilder sb3 = new StringBuilder("create directory [ ");
            sb3.append(str);
            sb3.append(" ] success");
            AMapLog.d("VUI_JAVA", sb3.toString());
            return 0;
        }
        StringBuilder sb4 = new StringBuilder("create directory [ ");
        sb4.append(str);
        sb4.append(" ] failed");
        AMapLog.e("VUI_JAVA", sb4.toString());
        return -1;
    }

    public static void b(String str) {
        if (bno.a) {
            AMapLog.d("VUI_JAVA", str);
            ku.a().c("VUIMonitor", "VUI_JAVA ".concat(String.valueOf(str)));
        }
    }

    public static int b(Context context) {
        context.getSystemService("connectivity");
        NetworkInfo e = aaw.e(context);
        if (e == null) {
            return 0;
        }
        int type = e.getType();
        int i = 2;
        if (type == 1) {
            i = 1;
        } else if (type == 0) {
            int subtype = e.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (subtype == 13 && !telephonyManager.isNetworkRoaming()) {
                i = 4;
            } else if (subtype == 3 || subtype == 8 || (subtype == 5 && !telephonyManager.isNetworkRoaming())) {
                i = 3;
            } else if (!(subtype == 1 || subtype == 2 || subtype != 4)) {
                boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();
            }
        } else {
            i = 0;
        }
        return i;
    }

    public static NuiConfig a() {
        NuiConfig nuiConfig = new NuiConfig();
        nuiConfig.setWorkspace(c());
        nuiConfig.setDebugPath(b);
        nuiConfig.setEnv("test".equals(ConfigerHelper.getInstance().getNetCondition()) ? "1" : "0");
        nuiConfig.setKeepAlive("60");
        nuiConfig.setDip(NetworkParam.getDip());
        nuiConfig.setDic(NetworkParam.getDic());
        nuiConfig.setDiv(NetworkParam.getDiv());
        nuiConfig.setTid(NetworkParam.getTaobaoID());
        nuiConfig.setOssupload(bgp.a() ? "true" : "false");
        String diu = NetworkParam.getDiu();
        if (TextUtils.isEmpty(diu)) {
            diu = NetworkParam.getAdiu();
            if (TextUtils.isEmpty(diu)) {
                diu = NetworkParam.getTaobaoID();
            }
        }
        nuiConfig.setDiu(diu);
        nuiConfig.setAdiu(NetworkParam.getAdiu());
        return nuiConfig;
    }

    public static String b() {
        String str;
        Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
        if ("test".equals(ConfigerHelper.getInstance().getNetCondition())) {
            str = a(applicationContext, (String) "nui_1.json");
        } else {
            str = a(applicationContext, (String) "nui.json");
        }
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject optJSONObject = new JSONObject(str).optJSONObject("nls_config");
                if (optJSONObject != null) {
                    Uri parse = Uri.parse(optJSONObject.optString("url"));
                    if (parse != null) {
                        str2 = parse.getHost();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            aau.a(str2);
        }
        return str2;
    }

    public static JSONObject c(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
            try {
                String e = agd.e(jSONObject.getJSONObject("voiceCommandResponse"), "voiceResult");
                if (TextUtils.isEmpty(e)) {
                    return null;
                }
                return new JSONObject(e);
            } catch (JSONException e2) {
                e = e2;
                e.printStackTrace();
                return jSONObject;
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = null;
            e.printStackTrace();
            return jSONObject;
        }
    }
}
