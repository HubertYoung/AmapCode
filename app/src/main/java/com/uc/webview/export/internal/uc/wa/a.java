package com.uc.webview.export.internal.uc.wa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCSettings;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.utility.Utils;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ProGuard */
public final class a {
    public static boolean a = true;
    public static boolean c = false;
    public static int e = 20480;
    public static int f = 5242880;
    public static int g = (e + 1024);
    private static a j;
    public Handler b;
    public List<b> d;
    public SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Object i = new Object();
    /* access modifiers changed from: private */
    public Context k;
    private HandlerThread l = new HandlerThread("SDKWaStatThread", 0);
    private Map<String, C0071a> m;
    private SimpleDateFormat n = new SimpleDateFormat("yyyyMMdd");

    /* renamed from: com.uc.webview.export.internal.uc.wa.a$a reason: collision with other inner class name */
    /* compiled from: ProGuard */
    class C0071a {
        Map<String, Integer> a;
        Map<String, String> b;

        private C0071a() {
            this.a = new HashMap();
            this.b = new HashMap();
        }

        /* synthetic */ C0071a(a aVar, byte b2) {
            this();
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Int Data: ");
            sb.append(this.a.toString());
            sb.append(" String Data: ");
            sb.append(this.b.toString());
            return sb.toString();
        }
    }

    /* compiled from: ProGuard */
    public class b {
        String a;
        Map<String, String> b;

        public b(String str, Map<String, String> map) {
            this.a = str;
            this.b = map;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Key: ");
            sb.append(this.a);
            sb.append(" Data: ");
            sb.append(this.b.toString());
            return sb.toString();
        }
    }

    static /* synthetic */ String a(String str, boolean z) {
        String valueOf = String.valueOf(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        sb.append("27120f2b4115");
        sb.append(str);
        sb.append(valueOf);
        sb.append("AppChk#2014");
        String a2 = f.a(sb.toString());
        StringBuilder sb2 = new StringBuilder("https://applog.uc.cn/collect?uc_param_str=&chk=");
        sb2.append(a2.substring(a2.length() - 8, a2.length()));
        sb2.append("&vno=");
        sb2.append(valueOf);
        sb2.append("&uuid=");
        sb2.append(str);
        sb2.append("&app=");
        sb2.append("27120f2b4115");
        if (z) {
            sb2.append("&enc=aes");
        }
        return sb2.toString();
    }

    static /* synthetic */ void a(a aVar, long j2, String str) {
        Editor edit = aVar.k.getSharedPreferences("UC_WA_STAT", 4).edit();
        edit.putLong(h(), j2);
        if (str != null) {
            edit.putString("4", str);
        }
        edit.commit();
    }

    static /* synthetic */ byte[] a(a aVar, String[] strArr) {
        Object[] i2 = aVar.i();
        if (i2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("lt=uc");
        Map map = (Map) i2[0];
        List list = (List) i2[1];
        List<PackageInfo> b2 = b(aVar.k);
        List<String[]> b3 = aVar.b(b2);
        strArr[0] = b(map, list);
        Iterator it = map.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Entry entry = (Entry) it.next();
            if (sb.length() < e) {
                sb.append("\n");
                for (String[] next : b3) {
                    a(sb, next[0], next[1]);
                }
                if (!j.d() && b(((C0071a) entry.getValue()).b).equals(strArr[0])) {
                    for (String[] next2 : aVar.a(b2, strArr[0])) {
                        a(sb, next2[0], next2[1]);
                    }
                }
                for (Entry next3 : ((C0071a) entry.getValue()).a.entrySet()) {
                    a(sb, (String) next3.getKey(), String.valueOf(next3.getValue()));
                }
                for (Entry next4 : ((C0071a) entry.getValue()).b.entrySet()) {
                    a(sb, (String) next4.getKey(), (String) next4.getValue());
                }
                for (Entry next5 : SDKFactory.D.entrySet()) {
                    a(sb, (String) next5.getKey(), String.valueOf(((Integer) next5.getValue()).intValue()));
                }
            } else if (Utils.sWAPrintLog) {
                StringBuilder sb2 = new StringBuilder("getUploadData MergeData size(");
                sb2.append(sb.length());
                sb2.append(") more then ");
                sb2.append(e);
                Log.d("SDKWaStat", sb2.toString());
            }
        }
        Iterator it2 = list.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            b bVar = (b) it2.next();
            if (sb.length() < e) {
                sb.append("\n");
                for (String[] next6 : b3) {
                    a(sb, next6[0], next6[1]);
                }
                for (Entry next7 : bVar.b.entrySet()) {
                    a(sb, (String) next7.getKey(), (String) next7.getValue());
                }
            } else if (Utils.sWAPrintLog) {
                StringBuilder sb3 = new StringBuilder("getUploadData UnMergeData size(");
                sb3.append(sb.length());
                sb3.append(") more then ");
                sb3.append(e);
                Log.d("SDKWaStat", sb3.toString());
            }
        }
        if (Utils.sWAPrintLog) {
            StringBuilder sb4 = new StringBuilder("getUploadData:\n");
            sb4.append(sb.toString());
            Log.i("SDKWaStat", sb4.toString());
        }
        sb.append("\n");
        a(sb, (String) "stat_size", String.valueOf(sb.toString().getBytes().length));
        return sb.toString().getBytes();
    }

    static /* synthetic */ void b(String str) {
        IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
        if (iGlobalSettings != null) {
            iGlobalSettings.setStringValue(UCSettings.SDKUUID, str);
        }
    }

    static /* synthetic */ void c(a aVar) {
        if (b()) {
            new e(aVar).start();
        }
    }

    private a() {
        this.l.start();
        this.b = new Handler(this.l.getLooper());
    }

    public static a a() {
        if (j == null && SDKFactory.e != null) {
            a(SDKFactory.e);
        }
        return j;
    }

    public static synchronized void a(Context context) {
        synchronized (a.class) {
            if (b()) {
                if (j == null) {
                    j = new a();
                }
                j.k = context.getApplicationContext();
            }
        }
    }

    public static boolean b() {
        return ((Boolean) SDKFactory.invoke(10006, "stat", Boolean.TRUE)).booleanValue();
    }

    public final void a(String str) {
        if (b()) {
            a(str, 0, 0, 1, null);
        }
    }

    public static void a(Pair<String, HashMap<String, String>> pair) {
        UCLogger create = UCLogger.create("d", "SDKWaStat");
        if (create != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("ev_ac=");
            sb.append((String) pair.first);
            for (Entry entry : ((HashMap) pair.second).entrySet()) {
                sb.append("`");
                sb.append((String) entry.getKey());
                sb.append("=");
                sb.append((String) entry.getValue());
            }
            create.print(sb.toString(), new Throwable[0]);
        }
    }

    public final void a(String str, int i2, int i3, int i4, String str2) {
        Date date = new Date(System.currentTimeMillis());
        int intValue = ((Boolean) SDKFactory.invoke(10010, new Object[0])).booleanValue() ? ((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue() : 0;
        if (!(intValue == 2 || intValue == 0)) {
            intValue = (intValue * 10) + SDKFactory.o;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.n.format(date));
        sb.append(Constants.WAVE_SEPARATOR);
        sb.append(intValue);
        String sb2 = sb.toString();
        synchronized (this.i) {
            if (this.m == null) {
                this.m = new HashMap();
            }
            C0071a aVar = this.m.get(sb2);
            if (aVar == null) {
                aVar = new C0071a(this, 0);
                this.m.put(sb2, aVar);
            }
            aVar.b.put("tm", this.h.format(date));
            switch (i2) {
                case 0:
                    Integer num = aVar.a.get(str);
                    if (num != null) {
                        aVar.a.put(str, Integer.valueOf(i4 + num.intValue()));
                        break;
                    } else {
                        aVar.a.put(str, Integer.valueOf(i4));
                        break;
                    }
                case 1:
                    if (i3 != 1) {
                        Map<String, String> map = aVar.b;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(aVar.b.get(str));
                        sb3.append(MergeUtil.SEPARATOR_KV);
                        sb3.append(str2);
                        map.put(str, sb3.toString());
                        break;
                    } else {
                        aVar.b.put(str, str2);
                        break;
                    }
                case 2:
                    Integer num2 = aVar.a.get(str);
                    if (num2 == null || Integer.MAX_VALUE - num2.intValue() >= i4) {
                        if (num2 != null) {
                            aVar.a.put(str, Integer.valueOf(i4 + num2.intValue()));
                            Map<String, Integer> map2 = aVar.a;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str);
                            sb4.append("_sc");
                            Map<String, Integer> map3 = aVar.a;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str);
                            sb5.append("_sc");
                            map3.put(sb5.toString(), Integer.valueOf(map2.get(sb4.toString()).intValue() + 1));
                            break;
                        } else {
                            aVar.a.put(str, Integer.valueOf(i4));
                            Map<String, Integer> map4 = aVar.a;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append("_sc");
                            map4.put(sb6.toString(), Integer.valueOf(1));
                            break;
                        }
                    }
            }
        }
    }

    public final void a(boolean z) {
        if (b() && !com.uc.webview.export.internal.utility.b.a((String) UCCore.getGlobalOption(UCCore.PROCESS_PRIVATE_DATA_DIR_SUFFIX_OPTION))) {
            try {
                new b(this).start();
                if (z) {
                    Thread.sleep(20);
                }
            } catch (Exception e2) {
                Log.e("SDKWaStat", "saveData", e2);
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void e() {
        if (b()) {
            try {
                if (Utils.sWAPrintLog) {
                    Log.d("SDKWaStat", "saveData");
                }
                HashMap hashMap = new HashMap();
                ArrayList arrayList = new ArrayList();
                synchronized (this.i) {
                    if (((Boolean) SDKFactory.invoke(10010, new Object[0])).booleanValue() && !a((Map) this.m)) {
                        hashMap.putAll(this.m);
                        this.m.clear();
                    }
                    if (!a((List) this.d)) {
                        arrayList.addAll(this.d);
                        this.d.clear();
                    }
                }
                a((Map<String, C0071a>) hashMap, (List<b>) arrayList);
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public String f() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.k.getApplicationContext().getApplicationInfo().dataDir);
        sb.append("/ucwa");
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            file.mkdir();
        }
        return sb2;
    }

    /* access modifiers changed from: private */
    public static String g() {
        String str = (String) UCCore.getGlobalOption(UCCore.PROCESS_PRIVATE_DATA_DIR_SUFFIX_OPTION);
        return (com.uc.webview.export.internal.utility.b.a(str) || str.equals("0")) ? "wa_upload_new.wa" : "wa_upload_new.wa_".concat(String.valueOf(str));
    }

    /* access modifiers changed from: private */
    public static String h() {
        String str = (String) UCCore.getGlobalOption(UCCore.PROCESS_PRIVATE_DATA_DIR_SUFFIX_OPTION);
        return (com.uc.webview.export.internal.utility.b.a(str) || str.equals("0")) ? "1" : "1_".concat(String.valueOf(str));
    }

    private static boolean a(List list) {
        return list == null || list.size() == 0;
    }

    private static boolean a(Map map) {
        return map == null || map.size() == 0;
    }

    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r13v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r2v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r2v7, types: [java.io.OutputStream, java.io.Closeable, java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r13v6 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7, types: [java.io.BufferedWriter, java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r13v27 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v5
      assigns: []
      uses: []
      mth insns count: 182
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
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.util.Map<java.lang.String, com.uc.webview.export.internal.uc.wa.a.C0071a> r13, java.util.List<com.uc.webview.export.internal.uc.wa.a.b> r14) {
        /*
            r12 = this;
            boolean r0 = a(r13)
            if (r0 == 0) goto L_0x000d
            boolean r0 = a(r14)
            if (r0 == 0) goto L_0x000d
            return
        L_0x000d:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r12.f()
            java.lang.String r2 = g()
            r0.<init>(r1, r2)
            boolean r1 = com.uc.webview.export.utility.Utils.sWAPrintLog
            if (r1 == 0) goto L_0x002d
            java.lang.String r1 = "SDKWaStat"
            java.lang.String r2 = "savePVToFile:"
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
        L_0x002d:
            r1 = 0
            boolean r2 = r0.exists()     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            r3 = 0
            if (r2 == 0) goto L_0x004c
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            r2.<init>(r0)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            int r4 = r2.available()     // Catch:{ Exception -> 0x0047, all -> 0x0042 }
            r2.close()     // Catch:{ Exception -> 0x0047, all -> 0x0042 }
            goto L_0x004d
        L_0x0042:
            r13 = move-exception
            r0 = r1
            r14 = r2
            goto L_0x01d2
        L_0x0047:
            r13 = r1
            r14 = r2
            r2 = r13
            goto L_0x01e3
        L_0x004c:
            r4 = 0
        L_0x004d:
            int r2 = e     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            if (r4 < r2) goto L_0x007f
            boolean r13 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            if (r13 == 0) goto L_0x0072
            java.lang.String r13 = "SDKWaStat"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            java.lang.String r0 = "file size("
            r14.<init>(r0)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            r14.append(r4)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            java.lang.String r0 = ") more then "
            r14.append(r0)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            int r0 = e     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            r14.append(r0)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            com.uc.webview.export.internal.utility.Log.d(r13, r14)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
        L_0x0072:
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            return
        L_0x007f:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            r5 = 1
            r2.<init>(r0, r5)     // Catch:{ Exception -> 0x01e0, all -> 0x01cf }
            java.io.BufferedWriter r0 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x01cc, all -> 0x01c7 }
            java.io.OutputStreamWriter r6 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x01cc, all -> 0x01c7 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x01cc, all -> 0x01c7 }
            r7 = 1024(0x400, float:1.435E-42)
            r0.<init>(r6, r7)     // Catch:{ Exception -> 0x01cc, all -> 0x01c7 }
            java.util.Set r13 = r13.entrySet()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r6 = 0
        L_0x009a:
            boolean r7 = r13.hasNext()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            if (r7 == 0) goto L_0x0150
            java.lang.Object r7 = r13.next()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.util.Map$Entry r7 = (java.util.Map.Entry) r7     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r8 = r6 + r4
            int r9 = e     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            if (r8 < r9) goto L_0x00d0
            boolean r13 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            if (r13 == 0) goto L_0x0150
            java.lang.String r13 = "SDKWaStat"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r9 = "write merge data, size("
            r7.<init>(r9)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r7.append(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r8 = ") more then "
            r7.append(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r8 = e     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r7.append(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            com.uc.webview.export.internal.utility.Log.d(r13, r7)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            goto L_0x0150
        L_0x00d0:
            java.lang.String r8 = "@0"
            r0.write(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + 2
            java.lang.String r8 = "@k@"
            r0.write(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + 3
            java.lang.Object r8 = r7.getKey()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r9 = "~"
            java.lang.String[] r9 = r8.split(r9)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r10 = r9[r5]     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r11 = "0"
            boolean r10 = r10.equals(r11)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            if (r10 == 0) goto L_0x0122
            r8 = 10020(0x2724, float:1.4041E-41)
            java.lang.Object[] r10 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.Object r8 = com.uc.webview.export.internal.SDKFactory.invoke(r8, r10)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r8 = r8.intValue()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r10 = 2
            if (r8 == r10) goto L_0x010b
            int r8 = r8 * 10
            int r10 = com.uc.webview.export.internal.SDKFactory.o     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r8 = r8 + r10
        L_0x010b:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r10.<init>()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r9 = r9[r3]     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r10.append(r9)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r9 = "~"
            r10.append(r9)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r10.append(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r8 = r10.toString()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
        L_0x0122:
            r0.write(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r8 = r8.length()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + r8
            java.lang.String r8 = "@d@"
            r0.write(r8)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + 3
            java.lang.Object r8 = r7.getValue()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            com.uc.webview.export.internal.uc.wa.a$a r8 = (com.uc.webview.export.internal.uc.wa.a.C0071a) r8     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.util.Map<java.lang.String, java.lang.Integer> r8 = r8.a     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r8 = a(r0, r8, r3)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + r8
            java.lang.Object r7 = r7.getValue()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            com.uc.webview.export.internal.uc.wa.a$a r7 = (com.uc.webview.export.internal.uc.wa.a.C0071a) r7     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.util.Map<java.lang.String, java.lang.String> r7 = r7.b     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r7 = a(r0, r7, r5)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + r7
            r0.newLine()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            goto L_0x009a
        L_0x0150:
            r13 = 10
            if (r3 >= r13) goto L_0x01b3
            int r13 = r14.size()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            if (r3 >= r13) goto L_0x01b3
            int r13 = r6 + r4
            int r7 = e     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            if (r13 < r7) goto L_0x017f
            java.lang.String r14 = "SDKWaStat"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r4 = "write un merge data, size("
            r3.<init>(r4)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r3.append(r13)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r13 = ") more then "
            r3.append(r13)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r13 = e     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r3.append(r13)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r13 = r3.toString()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            com.uc.webview.export.internal.utility.Log.d(r14, r13)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            goto L_0x01b3
        L_0x017f:
            java.lang.Object r13 = r14.get(r3)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            com.uc.webview.export.internal.uc.wa.a$b r13 = (com.uc.webview.export.internal.uc.wa.a.b) r13     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r7 = "@1"
            r0.write(r7)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + 2
            java.lang.String r7 = "@k@"
            r0.write(r7)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + 3
            java.lang.String r7 = r13.a     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            r0.write(r7)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            java.lang.String r7 = r13.a     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r7 = r7.length()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + r7
            java.lang.String r7 = "@d@"
            r0.write(r7)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + 3
            java.util.Map<java.lang.String, java.lang.String> r13 = r13.b     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r13 = a(r0, r13, r5)     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r6 = r6 + r13
            r0.newLine()     // Catch:{ Exception -> 0x01c3, all -> 0x01c0 }
            int r3 = r3 + 1
            goto L_0x0150
        L_0x01b3:
            com.uc.webview.export.cyclone.UCCyclone.close(r0)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            return
        L_0x01c0:
            r13 = move-exception
            r14 = r1
            goto L_0x01ca
        L_0x01c3:
            r14 = r1
            r13 = r2
            r1 = r0
            goto L_0x01e3
        L_0x01c7:
            r13 = move-exception
            r14 = r1
            r0 = r14
        L_0x01ca:
            r1 = r2
            goto L_0x01d3
        L_0x01cc:
            r14 = r1
            r13 = r2
            goto L_0x01e3
        L_0x01cf:
            r13 = move-exception
            r14 = r1
            r0 = r14
        L_0x01d2:
            r2 = r0
        L_0x01d3:
            com.uc.webview.export.cyclone.UCCyclone.close(r0)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r14)
            throw r13
        L_0x01e0:
            r13 = r1
            r14 = r13
            r2 = r14
        L_0x01e3:
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r2)
            com.uc.webview.export.cyclone.UCCyclone.close(r13)
            com.uc.webview.export.cyclone.UCCyclone.close(r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.uc.wa.a.a(java.util.Map, java.util.List):void");
    }

    private static <T> int a(BufferedWriter bufferedWriter, Map<String, T> map, int i2) throws Exception {
        int i3 = 0;
        if (!a((Map) map)) {
            for (Entry next : map.entrySet()) {
                bufferedWriter.write((String) next.getKey());
                bufferedWriter.write("=");
                bufferedWriter.write(MetaRecord.LOG_SEPARATOR.concat(String.valueOf(i2)));
                StringBuilder sb = new StringBuilder();
                sb.append(next.getValue());
                sb.append("`");
                bufferedWriter.write(sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(next.getValue());
                sb2.append("`");
                i3 += ((String) next.getKey()).length() + 1 + MetaRecord.LOG_SEPARATOR.concat(String.valueOf(i2)).length() + sb2.toString().length();
            }
        }
        return i3;
    }

    private Object[] i() {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        BufferedReader bufferedReader;
        FileInputStream fileInputStream3;
        BufferedReader bufferedReader2;
        Throwable th;
        int i2;
        File file = new File(f(), g());
        if (Utils.sWAPrintLog) {
            StringBuilder sb = new StringBuilder("getPVFromFile:");
            sb.append(file);
            sb.append(" exists:");
            sb.append(file.exists());
            Log.d("SDKWaStat", sb.toString());
        }
        if (!file.exists()) {
            return null;
        }
        file.length();
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        try {
            fileInputStream = new FileInputStream(file);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream), 1024);
                byte b2 = 0;
                int i3 = 0;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        int i4 = 2;
                        char c2 = 1;
                        if (readLine == null) {
                            break;
                        }
                        if (Utils.sWAPrintLog) {
                            Log.d("SDKWaStat", readLine);
                        }
                        if (!com.uc.webview.export.internal.utility.b.a(readLine)) {
                            if (readLine.length() + i3 <= e) {
                                i3 += readLine.length();
                                String trim = readLine.trim();
                                if (trim.startsWith("@0") || trim.startsWith("@1")) {
                                    int indexOf = trim.indexOf("@k@");
                                    int indexOf2 = trim.indexOf("@d@");
                                    if (indexOf >= 0 && indexOf2 >= 0) {
                                        String substring = trim.substring(indexOf + 3, indexOf2);
                                        String[] split = trim.substring(indexOf2 + 3).split("`");
                                        if (trim.startsWith("@0")) {
                                            String[] split2 = substring.split(Constants.WAVE_SEPARATOR);
                                            if (split2.length == 2 && split2[b2].length() == 8 && split2[1].length() <= 2) {
                                                C0071a aVar = (C0071a) hashMap.get(substring);
                                                if (aVar == null) {
                                                    if (hashMap.size() == 8) {
                                                        if (arrayList.size() == 10) {
                                                            break;
                                                        }
                                                    } else {
                                                        aVar = new C0071a(this, b2);
                                                        hashMap.put(substring, aVar);
                                                    }
                                                }
                                                int length = split.length;
                                                int i5 = 0;
                                                while (i5 < length) {
                                                    String[] split3 = split[i5].split("=");
                                                    if (split3.length != i4 || split3[c2].length() <= i4) {
                                                        i2 = length;
                                                    } else {
                                                        String substring2 = split3[c2].substring(i4);
                                                        if (split3[c2].startsWith("#0")) {
                                                            i2 = length;
                                                            Integer num = aVar.a.get(split3[0]);
                                                            if (num == null) {
                                                                aVar.a.put(split3[0], Integer.valueOf(Integer.parseInt(substring2)));
                                                            } else {
                                                                aVar.a.put(split3[0], Integer.valueOf(Integer.parseInt(substring2) + num.intValue()));
                                                            }
                                                        } else {
                                                            i2 = length;
                                                            if (split3[1].startsWith("#1")) {
                                                                aVar.b.put(split3[0], substring2);
                                                            }
                                                        }
                                                    }
                                                    i5++;
                                                    length = i2;
                                                    i4 = 2;
                                                    c2 = 1;
                                                }
                                                aVar.b.put("core_t", split2[1]);
                                            }
                                        } else if (arrayList.size() != 10) {
                                            HashMap hashMap2 = new HashMap(split.length);
                                            for (String split4 : split) {
                                                String[] split5 = split4.split("=");
                                                if (split5.length == 2) {
                                                    hashMap2.put(split5[0], split5[1].substring(2));
                                                }
                                            }
                                            hashMap2.put("ev_ac", substring);
                                            arrayList.add(new b(substring, hashMap2));
                                        }
                                    }
                                }
                                b2 = 0;
                            } else if (Utils.sWAPrintLog) {
                                StringBuilder sb2 = new StringBuilder("upload data size(");
                                sb2.append(i3 + readLine.length());
                                sb2.append(") more then ");
                                sb2.append(e);
                                Log.d("SDKWaStat", sb2.toString());
                            }
                        }
                    } catch (Exception unused) {
                        fileInputStream2 = fileInputStream;
                        UCCyclone.close(bufferedReader);
                        UCCyclone.close(fileInputStream);
                        UCCyclone.close(fileInputStream2);
                        return null;
                    } catch (Throwable th2) {
                        bufferedReader2 = bufferedReader;
                        fileInputStream3 = fileInputStream;
                        th = th2;
                        UCCyclone.close(bufferedReader2);
                        UCCyclone.close(fileInputStream);
                        UCCyclone.close(fileInputStream3);
                        throw th;
                    }
                }
                if (hashMap.size() > 0 || arrayList.size() > 0) {
                    Object[] objArr = {hashMap, arrayList};
                    UCCyclone.close(bufferedReader);
                    UCCyclone.close(fileInputStream);
                    UCCyclone.close(fileInputStream);
                    return objArr;
                }
                UCCyclone.close(bufferedReader);
                UCCyclone.close(fileInputStream);
                UCCyclone.close(fileInputStream);
                return null;
            } catch (Exception unused2) {
                fileInputStream2 = fileInputStream;
                bufferedReader = null;
                UCCyclone.close(bufferedReader);
                UCCyclone.close(fileInputStream);
                UCCyclone.close(fileInputStream2);
                return null;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream3 = fileInputStream;
                bufferedReader2 = null;
                UCCyclone.close(bufferedReader2);
                UCCyclone.close(fileInputStream);
                UCCyclone.close(fileInputStream3);
                throw th;
            }
        } catch (Exception unused3) {
            bufferedReader = null;
            fileInputStream2 = null;
            fileInputStream = null;
            UCCyclone.close(bufferedReader);
            UCCyclone.close(fileInputStream);
            UCCyclone.close(fileInputStream2);
            return null;
        } catch (Throwable th4) {
            th = th4;
            bufferedReader2 = null;
            fileInputStream3 = null;
            fileInputStream = null;
            UCCyclone.close(bufferedReader2);
            UCCyclone.close(fileInputStream);
            UCCyclone.close(fileInputStream3);
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x0343  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x03ca  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x03e3  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x03f6  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x040f  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0422  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x045c  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x045f  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0472  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x047f  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x04b6  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0131  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x016d  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0170  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x018e  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01a9  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01ac  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01ca  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01e5  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x01e8  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0203  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0206  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0224  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x023f  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0260  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x027b  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x027e  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0299  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x029c  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x02b8  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x02bb  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x02d7  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x02da  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x02f6  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x02f9  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0315  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0318  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List<java.lang.String[]> b(java.util.List<android.content.pm.PackageInfo> r11) {
        /*
            r10 = this;
            java.util.Iterator r11 = r11.iterator()
        L_0x0004:
            boolean r0 = r11.hasNext()
            r1 = 2
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x002b
            java.lang.Object r0 = r11.next()
            android.content.pm.PackageInfo r0 = (android.content.pm.PackageInfo) r0
            java.lang.String r4 = r0.packageName
            java.lang.String r5 = "com.UCMobile"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x001f
            r11 = 1
            goto L_0x002c
        L_0x001f:
            java.lang.String r0 = r0.packageName
            java.lang.String r4 = "com.UCMobile.intl"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0004
            r11 = 2
            goto L_0x002c
        L_0x002b:
            r11 = 0
        L_0x002c:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r4 = "lt"
            java.lang.String r5 = "ev"
            java.lang.String[] r4 = new java.lang.String[]{r4, r5}
            r0.add(r4)
            java.lang.String r4 = "ct"
            java.lang.String r5 = "corepv"
            java.lang.String[] r4 = new java.lang.String[]{r4, r5}
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "ver"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.Build.Version.NAME
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "pkg"
            r4[r2] = r5
            android.content.Context r5 = r10.k
            java.lang.String r5 = r5.getPackageName()
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_sn"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.Build.TIME
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_pm"
            r4[r2] = r5
            java.lang.String r5 = android.os.Build.MODEL
            boolean r5 = com.uc.webview.export.internal.utility.b.a(r5)
            if (r5 == 0) goto L_0x0085
            java.lang.String r5 = "unknown"
            goto L_0x0093
        L_0x0085:
            java.lang.String r5 = android.os.Build.MODEL
            java.lang.String r5 = r5.trim()
            java.lang.String r6 = "[`|=]"
            java.lang.String r7 = ""
            java.lang.String r5 = r5.replaceAll(r6, r7)
        L_0x0093:
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_f"
            r4[r2] = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r7 = 524288(0x80000, double:2.590327E-318)
            java.lang.Long r7 = java.lang.Long.valueOf(r7)
            r6[r2] = r7
            r7 = 10003(0x2713, float:1.4017E-41)
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 != 0) goto L_0x00ce
            r6 = 10036(0x2734, float:1.4063E-41)
            java.lang.Object[] r8 = new java.lang.Object[r3]
            android.content.Context r9 = r10.k
            r8[r2] = r9
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r6, r8)
            if (r6 != 0) goto L_0x00cb
            goto L_0x00ce
        L_0x00cb:
            java.lang.String r6 = "1"
            goto L_0x00d0
        L_0x00ce:
            java.lang.String r6 = "0"
        L_0x00d0:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 1
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x00ec
            java.lang.String r6 = "1"
            goto L_0x00ee
        L_0x00ec:
            java.lang.String r6 = "0"
        L_0x00ee:
            r5.append(r6)
            boolean r6 = com.uc.webview.export.internal.SDKFactory.l
            if (r6 == 0) goto L_0x00f8
            java.lang.String r6 = "1"
            goto L_0x00fa
        L_0x00f8:
            java.lang.String r6 = "0"
        L_0x00fa:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 2
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0116
            java.lang.String r6 = "1"
            goto L_0x0118
        L_0x0116:
            java.lang.String r6 = "0"
        L_0x0118:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 4
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0134
            java.lang.String r6 = "1"
            goto L_0x0136
        L_0x0134:
            java.lang.String r6 = "0"
        L_0x0136:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 8
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0152
            java.lang.String r6 = "1"
            goto L_0x0154
        L_0x0152:
            java.lang.String r6 = "0"
        L_0x0154:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 16
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0170
            java.lang.String r6 = "1"
            goto L_0x0172
        L_0x0170:
            java.lang.String r6 = "0"
        L_0x0172:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 32
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x018e
            java.lang.String r6 = "1"
            goto L_0x0190
        L_0x018e:
            java.lang.String r6 = "0"
        L_0x0190:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 64
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x01ac
            java.lang.String r6 = "1"
            goto L_0x01ae
        L_0x01ac:
            java.lang.String r6 = "0"
        L_0x01ae:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 128(0x80, double:6.32E-322)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x01ca
            java.lang.String r6 = "1"
            goto L_0x01cc
        L_0x01ca:
            java.lang.String r6 = "0"
        L_0x01cc:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 256(0x100, double:1.265E-321)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x01e8
            java.lang.String r6 = "1"
            goto L_0x01ea
        L_0x01e8:
            java.lang.String r6 = "0"
        L_0x01ea:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 512(0x200, double:2.53E-321)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0206
            java.lang.String r6 = "1"
            goto L_0x0208
        L_0x0206:
            java.lang.String r6 = "0"
        L_0x0208:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 1024(0x400, double:5.06E-321)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0224
            java.lang.String r6 = "1"
            goto L_0x0226
        L_0x0224:
            java.lang.String r6 = "0"
        L_0x0226:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 2048(0x800, double:1.0118E-320)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0242
            java.lang.String r6 = "1"
            goto L_0x0244
        L_0x0242:
            java.lang.String r6 = "0"
        L_0x0244:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 4096(0x1000, double:2.0237E-320)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0260
            java.lang.String r6 = "1"
            goto L_0x0262
        L_0x0260:
            java.lang.String r6 = "0"
        L_0x0262:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 8192(0x2000, double:4.0474E-320)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x027e
            java.lang.String r6 = "1"
            goto L_0x0280
        L_0x027e:
            java.lang.String r6 = "0"
        L_0x0280:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 16384(0x4000, double:8.0948E-320)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x029c
            java.lang.String r6 = "1"
            goto L_0x029e
        L_0x029c:
            java.lang.String r6 = "0"
        L_0x029e:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 32768(0x8000, double:1.61895E-319)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x02bb
            java.lang.String r6 = "1"
            goto L_0x02bd
        L_0x02bb:
            java.lang.String r6 = "0"
        L_0x02bd:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 65536(0x10000, double:3.2379E-319)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x02da
            java.lang.String r6 = "1"
            goto L_0x02dc
        L_0x02da:
            java.lang.String r6 = "0"
        L_0x02dc:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 131072(0x20000, double:6.47582E-319)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x02f9
            java.lang.String r6 = "1"
            goto L_0x02fb
        L_0x02f9:
            java.lang.String r6 = "0"
        L_0x02fb:
            r5.append(r6)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            r8 = 262144(0x40000, double:1.295163E-318)
            java.lang.Long r8 = java.lang.Long.valueOf(r8)
            r6[r2] = r8
            java.lang.Object r6 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r6)
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 == 0) goto L_0x0318
            java.lang.String r6 = "1"
            goto L_0x031a
        L_0x0318:
            java.lang.String r6 = "0"
        L_0x031a:
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_uf"
            r4[r2] = r5
            java.lang.String r5 = java.lang.String.valueOf(r11)
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_bd"
            r4[r2] = r5
            java.lang.String r5 = android.os.Build.BRAND
            boolean r5 = com.uc.webview.export.internal.utility.b.a(r5)
            if (r5 == 0) goto L_0x0347
            java.lang.String r5 = "unknown"
            goto L_0x0355
        L_0x0347:
            java.lang.String r5 = android.os.Build.BRAND
            java.lang.String r5 = r5.trim()
            java.lang.String r6 = "[`|=]"
            java.lang.String r8 = ""
            java.lang.String r5 = r5.replaceAll(r6, r8)
        L_0x0355:
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_osv"
            r4[r2] = r5
            java.lang.String r5 = android.os.Build.VERSION.RELEASE
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_prd"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.Build.SDK_PRD
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_pfid"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.Build.SDK_PROFILE_ID
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_cos"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.internal.utility.j.c()
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "pro_sf"
            r4[r2] = r5
            java.lang.String r5 = "process_private_data_dir_suffix"
            java.lang.Object r5 = com.uc.webview.export.extension.UCCore.getGlobalOption(r5)
            java.lang.String r5 = (java.lang.String) r5
            r4[r3] = r5
            r0.add(r4)
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "uuid"
            r4[r2] = r5
            android.content.Context r5 = r10.k
            java.lang.String r6 = "UC_WA_STAT"
            r8 = 4
            android.content.SharedPreferences r5 = r5.getSharedPreferences(r6, r8)
            java.lang.String r5 = r10.a(r5)
            r4[r3] = r5
            r0.add(r4)
            java.lang.String r4 = "adapter_build_timing"
            java.lang.Object r4 = com.uc.webview.export.extension.UCCore.getGlobalOption(r4)
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = com.uc.webview.export.internal.utility.b.a(r4)
            if (r5 != 0) goto L_0x03d5
            java.lang.String[] r5 = new java.lang.String[r1]
            java.lang.String r6 = "ab_sn"
            r5[r2] = r6
            r5[r3] = r4
            r0.add(r5)
        L_0x03d5:
            java.lang.String r4 = "adapter_build_version"
            java.lang.Object r4 = com.uc.webview.export.extension.UCCore.getGlobalOption(r4)
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = com.uc.webview.export.internal.utility.b.a(r4)
            if (r5 != 0) goto L_0x03ee
            java.lang.String[] r5 = new java.lang.String[r1]
            java.lang.String r6 = "ab_ve"
            r5[r2] = r6
            r5[r3] = r4
            r0.add(r5)
        L_0x03ee:
            java.lang.String r4 = com.uc.webview.export.Build.CORE_VERSION
            boolean r4 = com.uc.webview.export.internal.utility.b.a(r4)
            if (r4 != 0) goto L_0x0407
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_sdk_cv"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.Build.CORE_VERSION
            java.lang.String r5 = r5.trim()
            r4[r3] = r5
            r0.add(r4)
        L_0x0407:
            java.lang.String r4 = com.uc.webview.export.Build.UCM_VERSION
            boolean r4 = com.uc.webview.export.internal.utility.b.a(r4)
            if (r4 != 0) goto L_0x0420
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "sdk_ucm_cv"
            r4[r2] = r5
            java.lang.String r5 = com.uc.webview.export.Build.UCM_VERSION
            java.lang.String r5 = r5.trim()
            r4[r3] = r5
            r0.add(r4)
        L_0x0420:
            if (r11 != 0) goto L_0x043f
            java.lang.String[] r11 = new java.lang.String[r1]
            java.lang.String r4 = "sdk_ucbackup"
            r11[r2] = r4
            java.io.File r4 = new java.io.File
            java.lang.String r5 = "/sdcard/Backucup/com.UCMobile"
            r4.<init>(r5)
            boolean r4 = r4.exists()
            if (r4 == 0) goto L_0x0438
            java.lang.String r4 = "1"
            goto L_0x043a
        L_0x0438:
            java.lang.String r4 = "0"
        L_0x043a:
            r11[r3] = r4
            r0.add(r11)
        L_0x043f:
            java.lang.String[] r11 = new java.lang.String[r1]
            java.lang.String r4 = "sdk_vac"
            r11[r2] = r4
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r5 = 1048576(0x100000, double:5.180654E-318)
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
            r4[r2] = r5
            java.lang.Object r4 = com.uc.webview.export.internal.SDKFactory.invoke(r7, r4)
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 == 0) goto L_0x045f
            java.lang.String r4 = "1"
            goto L_0x0461
        L_0x045f:
            java.lang.String r4 = "0"
        L_0x0461:
            r11[r3] = r4
            r0.add(r11)
            android.content.Context r11 = r10.k
            java.lang.String r11 = com.uc.webview.export.internal.utility.j.a.a(r11)
            boolean r4 = com.uc.webview.export.internal.utility.b.a(r11)
            if (r4 != 0) goto L_0x047f
            java.lang.String[] r4 = new java.lang.String[r1]
            java.lang.String r5 = "ut_k"
            r4[r2] = r5
            r4[r3] = r11
            r0.add(r4)
            goto L_0x048b
        L_0x047f:
            java.lang.String r11 = "ut_k"
            java.lang.String r4 = "null"
            java.lang.String[] r11 = new java.lang.String[]{r11, r4}
            r0.add(r11)
        L_0x048b:
            java.lang.String[] r11 = new java.lang.String[r1]
            java.lang.String r4 = "data_dir"
            r11[r2] = r4
            android.content.Context r4 = r10.k
            android.content.pm.ApplicationInfo r4 = r4.getApplicationInfo()
            java.lang.String r4 = r4.dataDir
            r11[r3] = r4
            r0.add(r11)
            android.content.Context r11 = r10.k
            java.lang.String r4 = "getSharedPrefsFile"
            java.lang.Class[] r5 = new java.lang.Class[r3]
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r2] = r6
            java.lang.Object[] r6 = new java.lang.Object[r3]
            java.lang.String r7 = "UC_WA_STAT"
            r6[r2] = r7
            java.lang.Object r11 = com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r11, r4, r5, r6)
            java.io.File r11 = (java.io.File) r11
            if (r11 == 0) goto L_0x04c5
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r4 = "sp_dir"
            r1[r2] = r4
            java.lang.String r11 = r11.getAbsolutePath()
            r1[r3] = r11
            r0.add(r1)
        L_0x04c5:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.uc.wa.a.b(java.util.List):java.util.List");
    }

    private List<String[]> a(List<PackageInfo> list, String str) {
        if (str == null) {
            return new ArrayList(0);
        }
        ArrayList arrayList = new ArrayList();
        String string = this.k.getSharedPreferences("UC_WA_STAT", 4).getString("4", null);
        if (string == null || !string.equals(str)) {
            arrayList.add(new String[]{"sdk_3rdappf", c(list)});
        }
        return arrayList;
    }

    private static String b(Map<String, C0071a> map, List<b> list) {
        String[] strArr = {"01", "10", "20"};
        String str = null;
        for (int i2 = 0; i2 < 3; i2++) {
            String str2 = strArr[i2];
            for (Entry<String, C0071a> value : map.entrySet()) {
                String b2 = b(((C0071a) value.getValue()).b);
                if (b2 != null && b2.endsWith(str2)) {
                    if (str == null || b2.compareTo(str) > 0) {
                        str = b2;
                    }
                }
            }
            for (b bVar : list) {
                String b3 = b(bVar.b);
                if (b3 != null && b3.endsWith(str2)) {
                    if (str == null || b3.compareTo(str) > 0) {
                        str = b3;
                    }
                }
            }
            if (str != null) {
                break;
            }
        }
        return str;
    }

    private static String b(Map<String, String> map) {
        String str = map.get("tm");
        if (str == null || str.length() <= 10) {
            return null;
        }
        return str.substring(0, 10);
    }

    private static void a(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append("=");
        sb.append(str2);
        sb.append("`");
    }

    /* access modifiers changed from: private */
    public String a(String[] strArr) {
        Object[] i2 = i();
        if (i2 == null) {
            return null;
        }
        Map map = (Map) i2[0];
        List<b> list = (List) i2[1];
        try {
            JSONObject jSONObject = new JSONObject();
            List<PackageInfo> b2 = b(this.k);
            for (String[] next : b(b2)) {
                jSONObject.put(next[0], next[1]);
            }
            if (!j.d()) {
                strArr[0] = b(map, list);
                for (String[] next2 : a(b2, strArr[0])) {
                    jSONObject.put(next2[0], next2[1]);
                }
            }
            for (Entry next3 : SDKFactory.D.entrySet()) {
                jSONObject.put((String) next3.getKey(), ((Integer) next3.getValue()).intValue());
            }
            JSONArray jSONArray = new JSONArray();
            for (Entry entry : map.entrySet()) {
                JSONObject jSONObject2 = new JSONObject();
                for (Entry next4 : ((C0071a) entry.getValue()).a.entrySet()) {
                    jSONObject2.put((String) next4.getKey(), String.valueOf(next4.getValue()));
                }
                for (Entry next5 : ((C0071a) entry.getValue()).b.entrySet()) {
                    jSONObject2.put((String) next5.getKey(), next5.getValue());
                }
                jSONArray.put(jSONObject2);
            }
            for (b bVar : list) {
                JSONObject jSONObject3 = new JSONObject();
                for (Entry next6 : bVar.b.entrySet()) {
                    jSONObject3.put((String) next6.getKey(), next6.getValue());
                }
                jSONArray.put(jSONObject3);
            }
            jSONObject.put("items", jSONArray);
            jSONObject.put("stat_size", String.valueOf(jSONObject.toString().length()));
            return jSONObject.toString();
        } catch (Exception e2) {
            Log.e("SDKWaStat", "getJsonUploadData", e2);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public String a(SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString("2", "");
        if (!com.uc.webview.export.internal.utility.b.a(string)) {
            return string;
        }
        String uuid = UUID.randomUUID().toString();
        Editor edit = this.k.getSharedPreferences("UC_WA_STAT", 4).edit();
        edit.putString("2", uuid);
        edit.commit();
        return uuid;
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r9v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r9v2 */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r8v10, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r9v7 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r4v11, types: [java.io.ByteArrayOutputStream, java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r1v15 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v4
      assigns: []
      uses: []
      mth insns count: 106
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
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f1 A[Catch:{ all -> 0x0102 }] */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(java.lang.String r8, byte[] r9) {
        /*
            boolean r0 = com.uc.webview.export.internal.SDKFactory.g     // Catch:{ Throwable -> 0x001d }
            if (r0 != 0) goto L_0x001d
            java.lang.String r0 = "traffic_stat"
            java.lang.String r0 = com.uc.webview.export.extension.UCCore.getParam(r0)     // Catch:{ Throwable -> 0x001d }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ Throwable -> 0x001d }
            if (r0 == 0) goto L_0x001d
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x001d }
            r1 = 14
            if (r0 < r1) goto L_0x001d
            r0 = 40962(0xa002, float:5.74E-41)
            android.net.TrafficStats.setThreadStatsTag(r0)     // Catch:{ Throwable -> 0x001d }
        L_0x001d:
            r0 = 0
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r2.<init>(r8)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.net.URLConnection r8 = r2.openConnection()     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.net.HttpURLConnection r8 = (java.net.HttpURLConnection) r8     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            int r2 = com.uc.webview.export.internal.utility.j.a     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r8.setConnectTimeout(r2)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            int r2 = com.uc.webview.export.internal.utility.j.b     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r8.setReadTimeout(r2)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r2 = 1
            r8.setDoInput(r2)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r8.setDoOutput(r2)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.lang.String r3 = "POST"
            r8.setRequestMethod(r3)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r8.setUseCaches(r0)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.lang.String r3 = "Content-Type"
            java.lang.String r4 = "application/x-www-form-urlencoded"
            r8.setRequestProperty(r3, r4)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.lang.String r3 = "Content-Length"
            int r4 = r9.length     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r8.setRequestProperty(r3, r4)     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            java.io.OutputStream r3 = r8.getOutputStream()     // Catch:{ Throwable -> 0x00ea, all -> 0x00e6 }
            r3.write(r9)     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            int r9 = r8.getResponseCode()     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            r4 = 200(0xc8, float:2.8E-43)
            if (r9 == r4) goto L_0x007d
            boolean r8 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            if (r8 == 0) goto L_0x0073
            java.lang.String r8 = "SDKWaStat"
            java.lang.String r9 = "response == null"
            java.lang.Throwable r2 = new java.lang.Throwable     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            r2.<init>()     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            com.uc.webview.export.internal.utility.Log.e(r8, r9, r2)     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
        L_0x0073:
            com.uc.webview.export.cyclone.UCCyclone.close(r3)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            return r0
        L_0x007d:
            java.io.InputStream r8 = r8.getInputStream()     // Catch:{ Throwable -> 0x00e3, all -> 0x00e0 }
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r9 = new byte[r9]     // Catch:{ Throwable -> 0x00db, all -> 0x00d6 }
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x00db, all -> 0x00d6 }
            int r5 = r8.available()     // Catch:{ Throwable -> 0x00db, all -> 0x00d6 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00db, all -> 0x00d6 }
        L_0x008e:
            int r1 = r8.read(r9)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            r5 = -1
            if (r1 == r5) goto L_0x0099
            r4.write(r9, r0, r1)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            goto L_0x008e
        L_0x0099:
            java.lang.String r9 = new java.lang.String     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            byte[] r1 = r4.toByteArray()     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            r9.<init>(r1)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            boolean r1 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            if (r1 == 0) goto L_0x00b5
            java.lang.String r1 = "SDKWaStat"
            java.lang.String r5 = "response:"
            java.lang.String r6 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            com.uc.webview.export.internal.utility.Log.i(r1, r5)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
        L_0x00b5:
            java.lang.String r1 = "retcode=0"
            boolean r9 = r9.contains(r1)     // Catch:{ Throwable -> 0x00d3, all -> 0x00d1 }
            if (r9 == 0) goto L_0x00c7
            com.uc.webview.export.cyclone.UCCyclone.close(r3)
            com.uc.webview.export.cyclone.UCCyclone.close(r8)
            com.uc.webview.export.cyclone.UCCyclone.close(r4)
            return r2
        L_0x00c7:
            com.uc.webview.export.cyclone.UCCyclone.close(r3)
            com.uc.webview.export.cyclone.UCCyclone.close(r8)
            com.uc.webview.export.cyclone.UCCyclone.close(r4)
            goto L_0x0101
        L_0x00d1:
            r9 = move-exception
            goto L_0x00d8
        L_0x00d3:
            r9 = move-exception
            r1 = r4
            goto L_0x00dc
        L_0x00d6:
            r9 = move-exception
            r4 = r1
        L_0x00d8:
            r1 = r8
            r8 = r9
            goto L_0x0105
        L_0x00db:
            r9 = move-exception
        L_0x00dc:
            r7 = r9
            r9 = r8
            r8 = r7
            goto L_0x00ed
        L_0x00e0:
            r8 = move-exception
            r4 = r1
            goto L_0x0105
        L_0x00e3:
            r8 = move-exception
            r9 = r1
            goto L_0x00ed
        L_0x00e6:
            r8 = move-exception
            r3 = r1
            r4 = r3
            goto L_0x0105
        L_0x00ea:
            r8 = move-exception
            r9 = r1
            r3 = r9
        L_0x00ed:
            boolean r2 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ all -> 0x0102 }
            if (r2 == 0) goto L_0x00f8
            java.lang.String r2 = "SDKWaStat"
            java.lang.String r4 = ""
            com.uc.webview.export.internal.utility.Log.e(r2, r4, r8)     // Catch:{ all -> 0x0102 }
        L_0x00f8:
            com.uc.webview.export.cyclone.UCCyclone.close(r3)
            com.uc.webview.export.cyclone.UCCyclone.close(r9)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
        L_0x0101:
            return r0
        L_0x0102:
            r8 = move-exception
            r4 = r1
            r1 = r9
        L_0x0105:
            com.uc.webview.export.cyclone.UCCyclone.close(r3)
            com.uc.webview.export.cyclone.UCCyclone.close(r1)
            com.uc.webview.export.cyclone.UCCyclone.close(r4)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.uc.wa.a.b(java.lang.String, byte[]):boolean");
    }

    private static List<PackageInfo> b(Context context) {
        return context.getPackageManager().getInstalledPackages(0);
    }

    private static String c(List<PackageInfo> list) {
        long currentTimeMillis = System.currentTimeMillis();
        HashSet hashSet = new HashSet(list.size());
        for (PackageInfo packageInfo : list) {
            hashSet.add(Integer.valueOf(packageInfo.packageName.hashCode()));
        }
        StringBuilder sb = new StringBuilder();
        int[] iArr = {744792033, -796004189, 1536737232, -1864872766, -245593387, 559984781, 1254578009, 460049591, -103524201, -191341086, 2075805265, -860300598, 195266379, 851655498, -172581751, -1692253156, -1709882794, 978047406, -1447376190, 1085732649, 400412247, 1007750384, 321803898, 1319538838, -1459422248, -173313837, 1488133239, 551552610, 1310504938, 633261597, -548160304, 1971200218, 757982267, 996952171, 1855462465, 2049668591, -189253699, -761937585, -1102972298, 195210534, -1433071276, -118960061, 810513273, 1659293491, 1552103645, 361910168, -973170826, -1805061386, -1635328017, -1131240456, 1429484426, -918490570, 1791072826, -894368837, -1394248969, -1476255283, 1994036591, 1219220171, 201325446, -1215205363, -257645900, 1197124177, 1765779203, 313184810, 308524937, -1652150487, 1174097286, -69877540, 2123438483, -1769871671};
        for (int i2 = 0; i2 < 70; i2++) {
            if (hashSet.contains(Integer.valueOf(iArr[i2]))) {
                sb.append("1");
            } else {
                sb.append("0");
            }
        }
        StringBuilder sb2 = new StringBuilder("getOtherAppInstallFlag用时:");
        sb2.append(System.currentTimeMillis() - currentTimeMillis);
        sb2.append(Token.SEPARATOR);
        sb2.append(sb);
        Log.i("SDKWaStat", sb2.toString());
        return sb.toString();
    }
}
