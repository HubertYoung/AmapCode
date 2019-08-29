package com.sijla.g;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.q.Qt;
import com.sijla.f.a;
import com.sijla.g.a.c;
import com.sijla.g.a.d;
import com.sijla.g.a.e;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class b {
    public static String a = null;
    private static String b = "";

    public static String e(Context context) {
        return "";
    }

    public static boolean a(String str) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != ' ' && charAt != 9 && charAt != 13 && charAt != 10) {
                return false;
            }
        }
        return true;
    }

    public static List<String> a() {
        return new ArrayList();
    }

    public static List<String> a(Context context) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(com.sijla.g.a.b.a((String) "autonavi/qmt").toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(context.getPackageName());
        sb2.append("qmt1");
        sb.append(d.a(sb2.toString()));
        arrayList.add(sb.toString());
        return arrayList;
    }

    public static byte[] a(File file) {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] b2 = e.b(fileInputStream);
        a(fileInputStream);
        return b2;
    }

    public static String[] b() {
        String valueOf = String.valueOf(d.d());
        return new String[]{d.a(valueOf).substring(0, 8), valueOf};
    }

    public static String b(String str) {
        try {
            String a2 = a(8);
            String a3 = com.sijla.d.b.a(a2, str);
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append(a3);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(JSONArray jSONArray) {
        try {
            String a2 = a(8);
            String a3 = com.sijla.d.b.a(a2, jSONArray.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append(a3);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String c(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return com.sijla.d.b.b(str.substring(0, 8), str.substring(8, str.length()));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static JSONObject d(String str) {
        if (!a(str)) {
            try {
                return new JSONObject(c(str));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static JSONArray e(String str) {
        if (!a(str)) {
            try {
                return new JSONArray(com.sijla.d.b.b(str.substring(0, 8), str.substring(8, str.length())));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static String a(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt("abcdefghijklmnopqrstuvwxyz0123456789".length())));
        }
        return sb.toString();
    }

    public static String a(String str, String str2) {
        try {
            return com.sijla.d.b.a(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static JSONObject a(JSONObject jSONObject) {
        return f(jSONObject.toString());
    }

    public static JSONObject f(String str) {
        return a(str, new JSONObject());
    }

    public static JSONObject a(String str, JSONObject jSONObject) {
        String valueOf = String.valueOf(d.d());
        String a2 = com.sijla.d.b.a(d.a(valueOf).substring(0, 8), str);
        if (!TextUtils.isEmpty(a2)) {
            try {
                jSONObject.put("data", a2);
                jSONObject.put("ts", valueOf);
                return jSONObject;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public static byte[] g(String str) {
        return a(str.getBytes());
    }

    public static byte[] a(byte[] bArr) {
        Closeable[] closeableArr;
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream2.write(bArr);
                    closeableArr = new Closeable[]{gZIPOutputStream2};
                } catch (IOException e) {
                    e = e;
                    gZIPOutputStream = gZIPOutputStream2;
                    try {
                        e.printStackTrace();
                        closeableArr = new Closeable[]{gZIPOutputStream};
                        a(closeableArr);
                        return byteArrayOutputStream.toByteArray();
                    } catch (Throwable th) {
                        th = th;
                        a(gZIPOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    gZIPOutputStream = gZIPOutputStream2;
                    a(gZIPOutputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                closeableArr = new Closeable[]{gZIPOutputStream};
                a(closeableArr);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e3) {
            e = e3;
            byteArrayOutputStream = null;
            e.printStackTrace();
            closeableArr = new Closeable[]{gZIPOutputStream};
            a(closeableArr);
            return byteArrayOutputStream.toByteArray();
        }
        a(closeableArr);
        return byteArrayOutputStream.toByteArray();
    }

    public static String b(byte[] bArr) {
        GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bArr));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[1024];
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read != -1) {
                byteArrayOutputStream.write(bArr2, 0, read);
            } else {
                a(gZIPInputStream);
                byteArrayOutputStream.flush();
                return new String(byteArrayOutputStream.toByteArray(), "utf-8");
            }
        }
    }

    public static void b(Context context) {
        try {
            String b2 = d.b();
            if (!b2.equals((String) j.b(context, "nd_sys_t", ""))) {
                j.a(context, "nd_sys_t", b2);
                c.a(context, h((String) "TruthInfo"), a.a(context).a());
                i(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String h(Context context) {
        String string = Secure.getString(context.getContentResolver(), "default_input_method");
        if (!a(string)) {
            return string.split("/")[0];
        }
        return null;
    }

    private static void i(Context context) {
        String h = h(context);
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null) {
            String g = g(context);
            StringBuilder sb = new StringBuilder();
            sb.append(d.d());
            String sb2 = sb.toString();
            List<InputMethodInfo> inputMethodList = inputMethodManager.getInputMethodList();
            if (inputMethodList != null) {
                ArrayList arrayList = new ArrayList();
                for (InputMethodInfo packageName : inputMethodList) {
                    String packageName2 = packageName.getPackageName();
                    boolean z = h != null && h.equals(packageName2);
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(g);
                    arrayList2.add(packageName2);
                    arrayList2.add(z ? "1" : "0");
                    arrayList2.add(sb2);
                    arrayList.add(arrayList2);
                }
                if (arrayList.size() > 0) {
                    c.c(context, h((String) "imlist"), arrayList);
                }
            }
        }
    }

    public static String h(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".csv-");
        sb.append(d.c());
        return sb.toString();
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [int] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r3v2 */
    /* JADX WARNING: type inference failed for: r2v4, types: [int] */
    /* JADX WARNING: type inference failed for: r3v3, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v4, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v7, types: [boolean] */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v10 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: type inference failed for: r3v12 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r3v13 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008f, code lost:
        r3 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008f, code lost:
        r3 = r3;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], int]
      uses: [boolean, ?[int, byte, short, char], ?[int, short, byte, char], int]
      mth insns count: 54
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
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.sijla.b.c a(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
            com.sijla.b.c r0 = new com.sijla.b.c
            r0.<init>()
            java.lang.String r8 = com.sijla.g.a.b.c(r8)
            java.io.File r1 = new java.io.File
            r1.<init>(r8)
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            r2 = 0
            if (r8 != 0) goto L_0x0093
            java.io.File[] r8 = r1.listFiles()
            int r1 = r8.length
            r3 = 0
        L_0x001b:
            if (r2 >= r1) goto L_0x0092
            r4 = r8[r2]
            java.lang.String r5 = r4.getName()
            boolean r6 = r4.isFile()
            if (r6 == 0) goto L_0x008f
            boolean r6 = r5.startsWith(r9)
            if (r6 == 0) goto L_0x008f
            java.lang.String r6 = ".lock"
            boolean r6 = r5.endsWith(r6)
            if (r6 != 0) goto L_0x008f
            java.lang.String r6 = r4.getAbsolutePath()
            java.lang.String r7 = "AK"
            boolean r7 = r9.equals(r7)
            if (r7 != 0) goto L_0x0065
            java.lang.String r7 = "AS"
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto L_0x004c
            goto L_0x0065
        L_0x004c:
            java.lang.String r7 = "UA"
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto L_0x0069
            long r3 = r4.lastModified()
            java.lang.String r7 = com.sijla.g.d.b()
            java.lang.String r3 = com.sijla.g.d.a(r3)
            boolean r3 = r7.equals(r3)
            goto L_0x0069
        L_0x0065:
            boolean r3 = r5.contains(r10)
        L_0x0069:
            if (r3 == 0) goto L_0x008f
            r0.a(r6)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r6 = "CHKDATA:"
            r4.<init>(r6)
            r4.append(r9)
            java.lang.String r6 = " NAME:"
            r4.append(r6)
            r4.append(r5)
            java.lang.String r5 = " status:"
            r4.append(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            com.sijla.g.g.a(r4)
        L_0x008f:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0092:
            r2 = r3
        L_0x0093:
            r0.a(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sijla.g.b.a(android.content.Context, java.lang.String, java.lang.String):com.sijla.b.c");
    }

    public static void a(Context context, List<String> list) {
        for (String next : a()) {
            c.b(context, next, list);
            g.a("uuid", "path = ".concat(String.valueOf(next)));
        }
    }

    public static void a(Context context, JSONObject jSONObject) {
        if (!com.sijla.g.a.a.a() || !com.sijla.g.a.a.d(context, (String) "android.permission.WRITE_EXTERNAL_STORAGE")) {
            PrintStream printStream = System.err;
            return;
        }
        byte[] g = g(jSONObject.toString());
        for (String a2 : a(context)) {
            c.a(a2, g);
        }
    }

    public static String c(Context context) {
        return context.getSharedPreferences("arch", 0).getString(DictionaryKeys.SECTION_LOC_INFO, "");
    }

    public static String d(Context context) {
        return context.getSharedPreferences("arch", 0).getString("sadr", "emt");
    }

    public static String f(Context context) {
        return Qt._channel;
    }

    public static boolean a(Context context, String str, long j) {
        boolean z = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("arch", 0);
        long j2 = sharedPreferences.getLong(str, 0);
        long d = d.d();
        if (Math.abs(d - j2) > j) {
            z = true;
        }
        if (z) {
            sharedPreferences.edit().putLong(str, d).apply();
        }
        return z;
    }

    public static int a(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).applicationInfo.uid;
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    public static String g(Context context) {
        if (a == null) {
            String packageName = context.getPackageName();
            StringBuilder sb = new StringBuilder();
            sb.append(packageName);
            sb.append("-");
            sb.append(com.sijla.g.a.a.a(packageName, context));
            a = sb.toString().replace(Token.SEPARATOR, "");
        }
        return a;
    }

    public static void a(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void a(HttpURLConnection... httpURLConnectionArr) {
        for (HttpURLConnection httpURLConnection : httpURLConnectionArr) {
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
