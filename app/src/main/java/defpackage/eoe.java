package defpackage;

import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: eoe reason: default package */
/* compiled from: HttpAssist */
public final class eoe {
    private static String a = "http://11.239.183.190:8090";

    public static boolean a(File file) {
        return a(file, (String) "pic");
    }

    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r7v0, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r5v3, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r8v0 */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r8v1 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r8v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v6, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r8v4, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v7, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r8v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v8, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r8v10 */
    /* JADX WARNING: type inference failed for: r8v11 */
    /* JADX WARNING: type inference failed for: r8v12 */
    /* JADX WARNING: type inference failed for: r7v8 */
    /* JADX WARNING: type inference failed for: r7v9, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r8v13 */
    /* JADX WARNING: type inference failed for: r5v9 */
    /* JADX WARNING: type inference failed for: r8v14 */
    /* JADX WARNING: type inference failed for: r8v15 */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: type inference failed for: r8v16 */
    /* JADX WARNING: type inference failed for: r8v20, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r5v18 */
    /* JADX WARNING: type inference failed for: r5v19 */
    /* JADX WARNING: type inference failed for: r5v20 */
    /* JADX WARNING: type inference failed for: r7v10 */
    /* JADX WARNING: type inference failed for: r8v21 */
    /* JADX WARNING: type inference failed for: r8v22 */
    /* JADX WARNING: type inference failed for: r8v23 */
    /* JADX WARNING: type inference failed for: r5v21 */
    /* JADX WARNING: type inference failed for: r5v22 */
    /* JADX WARNING: type inference failed for: r8v24 */
    /* JADX WARNING: type inference failed for: r8v25 */
    /* JADX WARNING: type inference failed for: r5v23 */
    /* JADX WARNING: type inference failed for: r5v24 */
    /* JADX WARNING: type inference failed for: r8v26 */
    /* JADX WARNING: type inference failed for: r8v27 */
    /* JADX WARNING: type inference failed for: r5v25 */
    /* JADX WARNING: type inference failed for: r5v26 */
    /* JADX WARNING: type inference failed for: r7v11 */
    /* JADX WARNING: type inference failed for: r7v12 */
    /* JADX WARNING: type inference failed for: r8v28 */
    /* JADX WARNING: type inference failed for: r8v29 */
    /* JADX WARNING: type inference failed for: r8v30 */
    /* JADX WARNING: type inference failed for: r8v31 */
    /* JADX WARNING: type inference failed for: r8v32 */
    /* JADX WARNING: type inference failed for: r8v33 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v1
      assigns: []
      uses: []
      mth insns count: 191
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
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01a1 A[SYNTHETIC, Splitter:B:101:0x01a1] */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01af A[SYNTHETIC, Splitter:B:110:0x01af] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x01b9 A[SYNTHETIC, Splitter:B:115:0x01b9] */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x01c3 A[SYNTHETIC, Splitter:B:120:0x01c3] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x016d A[SYNTHETIC, Splitter:B:74:0x016d] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0177 A[SYNTHETIC, Splitter:B:79:0x0177] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0181 A[SYNTHETIC, Splitter:B:84:0x0181] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x018d A[SYNTHETIC, Splitter:B:91:0x018d] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0197 A[SYNTHETIC, Splitter:B:96:0x0197] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:71:0x0168=Splitter:B:71:0x0168, B:88:0x0188=Splitter:B:88:0x0188} */
    /* JADX WARNING: Unknown variable types count: 18 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r11, java.lang.String r12) {
        /*
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "--"
            java.lang.String r2 = "\r\n"
            java.lang.String r3 = "multipart/form-data"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = a
            r4.append(r5)
            java.lang.String r5 = "/bridge/"
            r4.append(r5)
            r4.append(r12)
            java.lang.String r12 = r4.toString()
            r4 = 0
            r5 = 0
            java.net.URL r6 = new java.net.URL     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r6.<init>(r12)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.net.URLConnection r12 = r6.openConnection()     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.net.HttpURLConnection r12 = (java.net.HttpURLConnection) r12     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r6 = 100000000(0x5f5e100, float:2.3122341E-35)
            r12.setReadTimeout(r6)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r12.setConnectTimeout(r6)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r6 = 1
            r12.setDoInput(r6)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r12.setDoOutput(r6)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r12.setUseCaches(r4)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.lang.String r7 = "POST"
            r12.setRequestMethod(r7)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.lang.String r7 = "Charset"
            java.lang.String r8 = "utf-8"
            r12.setRequestProperty(r7, r8)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.lang.String r7 = "connection"
            java.lang.String r8 = "keep-alive"
            r12.setRequestProperty(r7, r8)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.lang.String r7 = "Content-Type"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r8.<init>()     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r8.append(r3)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.lang.String r3 = ";boundary="
            r8.append(r3)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r8.append(r0)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.lang.String r3 = r8.toString()     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            r12.setRequestProperty(r7, r3)     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            if (r11 == 0) goto L_0x013e
            java.io.OutputStream r3 = r12.getOutputStream()     // Catch:{ MalformedURLException -> 0x0185, IOException -> 0x0165, all -> 0x0161 }
            java.io.DataOutputStream r7 = new java.io.DataOutputStream     // Catch:{ MalformedURLException -> 0x013b, IOException -> 0x0138, all -> 0x0134 }
            r7.<init>(r3)     // Catch:{ MalformedURLException -> 0x013b, IOException -> 0x0138, all -> 0x0134 }
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.<init>()     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.append(r1)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.append(r0)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.append(r2)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r10 = "Content-Disposition: form-data; name=\"file\"; filename=\""
            r9.<init>(r10)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r10 = r11.getName()     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r9.append(r10)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r10 = "\""
            r9.append(r10)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r9.append(r2)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r9 = r9.toString()     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.append(r9)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r9 = "Content-Type: application/octet-stream; charset=utf-8"
            java.lang.String r10 = java.lang.String.valueOf(r2)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r9 = r9.concat(r10)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.append(r9)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.append(r2)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.lang.String r8 = r8.toString()     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            byte[] r8 = r8.getBytes()     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r7.write(r8)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r8.<init>(r11)     // Catch:{ MalformedURLException -> 0x012f, IOException -> 0x012b, all -> 0x0128 }
            r11 = 1024(0x400, float:1.435E-42)
            byte[] r11 = new byte[r11]     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
        L_0x00cb:
            int r5 = r8.read(r11)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r9 = -1
            if (r5 == r9) goto L_0x00d6
            r7.write(r11, r4, r5)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            goto L_0x00cb
        L_0x00d6:
            byte[] r11 = r2.getBytes()     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r7.write(r11)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r11.<init>()     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r11.append(r1)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r11.append(r0)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r11.append(r1)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r11.append(r2)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            java.lang.String r11 = r11.toString()     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            byte[] r11 = r11.getBytes()     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r7.write(r11)     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r7.flush()     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            int r11 = r12.getResponseCode()     // Catch:{ MalformedURLException -> 0x0126, IOException -> 0x0124, all -> 0x0121 }
            r0 = 200(0xc8, float:2.8E-43)
            if (r11 != r0) goto L_0x011f
            r7.close()     // Catch:{ IOException -> 0x0108 }
            goto L_0x010c
        L_0x0108:
            r11 = move-exception
            r11.printStackTrace()
        L_0x010c:
            if (r3 == 0) goto L_0x0116
            r3.close()     // Catch:{ IOException -> 0x0112 }
            goto L_0x0116
        L_0x0112:
            r11 = move-exception
            r11.printStackTrace()
        L_0x0116:
            r8.close()     // Catch:{ IOException -> 0x011a }
            goto L_0x011e
        L_0x011a:
            r11 = move-exception
            r11.printStackTrace()
        L_0x011e:
            return r6
        L_0x011f:
            r5 = r7
            goto L_0x0140
        L_0x0121:
            r11 = move-exception
            goto L_0x01ac
        L_0x0124:
            r11 = move-exception
            goto L_0x012d
        L_0x0126:
            r11 = move-exception
            goto L_0x0131
        L_0x0128:
            r11 = move-exception
            goto L_0x01ad
        L_0x012b:
            r11 = move-exception
            r8 = r5
        L_0x012d:
            r5 = r7
            goto L_0x0168
        L_0x012f:
            r11 = move-exception
            r8 = r5
        L_0x0131:
            r5 = r7
            goto L_0x0188
        L_0x0134:
            r11 = move-exception
            r7 = r5
            goto L_0x01ad
        L_0x0138:
            r11 = move-exception
            r8 = r5
            goto L_0x0168
        L_0x013b:
            r11 = move-exception
            r8 = r5
            goto L_0x0188
        L_0x013e:
            r3 = r5
            r8 = r3
        L_0x0140:
            r12.disconnect()     // Catch:{ MalformedURLException -> 0x015f, IOException -> 0x015d }
            if (r5 == 0) goto L_0x014d
            r5.close()     // Catch:{ IOException -> 0x0149 }
            goto L_0x014d
        L_0x0149:
            r11 = move-exception
            r11.printStackTrace()
        L_0x014d:
            if (r3 == 0) goto L_0x0157
            r3.close()     // Catch:{ IOException -> 0x0153 }
            goto L_0x0157
        L_0x0153:
            r11 = move-exception
            r11.printStackTrace()
        L_0x0157:
            if (r8 == 0) goto L_0x01a9
            r8.close()     // Catch:{ IOException -> 0x01a5 }
            goto L_0x01a9
        L_0x015d:
            r11 = move-exception
            goto L_0x0168
        L_0x015f:
            r11 = move-exception
            goto L_0x0188
        L_0x0161:
            r11 = move-exception
            r3 = r5
            r7 = r3
            goto L_0x01ad
        L_0x0165:
            r11 = move-exception
            r3 = r5
            r8 = r3
        L_0x0168:
            r11.printStackTrace()     // Catch:{ all -> 0x01aa }
            if (r5 == 0) goto L_0x0175
            r5.close()     // Catch:{ IOException -> 0x0171 }
            goto L_0x0175
        L_0x0171:
            r11 = move-exception
            r11.printStackTrace()
        L_0x0175:
            if (r3 == 0) goto L_0x017f
            r3.close()     // Catch:{ IOException -> 0x017b }
            goto L_0x017f
        L_0x017b:
            r11 = move-exception
            r11.printStackTrace()
        L_0x017f:
            if (r8 == 0) goto L_0x01a9
            r8.close()     // Catch:{ IOException -> 0x01a5 }
            goto L_0x01a9
        L_0x0185:
            r11 = move-exception
            r3 = r5
            r8 = r3
        L_0x0188:
            r11.printStackTrace()     // Catch:{ all -> 0x01aa }
            if (r5 == 0) goto L_0x0195
            r5.close()     // Catch:{ IOException -> 0x0191 }
            goto L_0x0195
        L_0x0191:
            r11 = move-exception
            r11.printStackTrace()
        L_0x0195:
            if (r3 == 0) goto L_0x019f
            r3.close()     // Catch:{ IOException -> 0x019b }
            goto L_0x019f
        L_0x019b:
            r11 = move-exception
            r11.printStackTrace()
        L_0x019f:
            if (r8 == 0) goto L_0x01a9
            r8.close()     // Catch:{ IOException -> 0x01a5 }
            goto L_0x01a9
        L_0x01a5:
            r11 = move-exception
            r11.printStackTrace()
        L_0x01a9:
            return r4
        L_0x01aa:
            r11 = move-exception
            r7 = r5
        L_0x01ac:
            r5 = r8
        L_0x01ad:
            if (r7 == 0) goto L_0x01b7
            r7.close()     // Catch:{ IOException -> 0x01b3 }
            goto L_0x01b7
        L_0x01b3:
            r12 = move-exception
            r12.printStackTrace()
        L_0x01b7:
            if (r3 == 0) goto L_0x01c1
            r3.close()     // Catch:{ IOException -> 0x01bd }
            goto L_0x01c1
        L_0x01bd:
            r12 = move-exception
            r12.printStackTrace()
        L_0x01c1:
            if (r5 == 0) goto L_0x01cb
            r5.close()     // Catch:{ IOException -> 0x01c7 }
            goto L_0x01cb
        L_0x01c7:
            r12 = move-exception
            r12.printStackTrace()
        L_0x01cb:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eoe.a(java.io.File, java.lang.String):boolean");
    }

    public static String a() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("/bridge/piclist");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append(sb3.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(sb4.toString()).openConnection();
            httpURLConnection.setConnectTimeout(100000000);
            httpURLConnection.setReadTimeout(100000000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.addRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                return a(httpURLConnection.getInputStream());
            }
            httpURLConnection.disconnect();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String a(String str, Map<String, Object> map) {
        String str2;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append(str);
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            for (Entry next : map.entrySet()) {
                sb3.append((String) next.getKey());
                sb3.append("=");
                sb3.append(next.getValue().toString());
                sb3.append("&");
            }
            if (sb3.length() > 0) {
                sb3.deleteCharAt(sb3.lastIndexOf("&"));
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            if (sb3.length() > 0) {
                StringBuilder sb5 = new StringBuilder("?");
                sb5.append(sb3.toString());
                str2 = sb5.toString();
            } else {
                str2 = "";
            }
            sb4.append(str2);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(sb4.toString()).openConnection();
            httpURLConnection.setConnectTimeout(100000000);
            httpURLConnection.setReadTimeout(100000000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.addRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                return a(httpURLConnection.getInputStream());
            }
            httpURLConnection.disconnect();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String a(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byteArrayOutputStream.close();
                    inputStream.close();
                    return new String(byteArrayOutputStream.toByteArray());
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }
}
