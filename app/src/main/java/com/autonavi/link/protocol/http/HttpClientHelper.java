package com.autonavi.link.protocol.http;

import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.autonavi.link.connect.a.b;
import com.autonavi.link.transmit.proxy.LinkProxy;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpClientHelper {
    private static volatile HttpClientHelper d;
    private final String a = "GET";
    private final String b = "POST";
    private final String c = "Mozilla/5";

    public interface IPostFileProgresser {
        boolean onDataReceived(byte[] bArr, int i, int i2);

        void onRequestFinished();

        void onRequestReceived(Map<String, List<String>> map);
    }

    private HttpClientHelper() {
    }

    public static HttpClientHelper getInstance() {
        if (d == null) {
            synchronized (HttpClientHelper.class) {
                try {
                    if (d == null) {
                        d = new HttpClientHelper();
                    }
                }
            }
        }
        return d;
    }

    public byte[] getBytes(String str, String str2, Map<String, String> map) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(str, str2, map, byteArrayOutputStream, null, 0);
        return byteArrayOutputStream.toByteArray();
    }

    public boolean getFile(String str, String str2, Map<String, String> map, String str3) throws IOException {
        a(str3);
        return a(str, str2, map, new FileOutputStream(str3), null, 0);
    }

    public boolean getFile(String str, String str2, Map<String, String> map, String str3, Progresser progresser) throws IOException {
        a(str3);
        return a(str, str2, map, new FileOutputStream(str3), progresser, 0);
    }

    public boolean getFileFromBreakPoint(String str, String str2, Map<String, String> map, String str3, Progresser progresser) throws IOException {
        a(str3);
        FileOutputStream fileOutputStream = new FileOutputStream(str3, true);
        return a(str, str2, map, fileOutputStream, progresser, fileOutputStream.getChannel().size());
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r11, java.lang.String r12, java.util.Map<java.lang.String, java.lang.String> r13, java.io.OutputStream r14, com.autonavi.link.protocol.http.Progresser r15, long r16) throws java.io.IOException {
        /*
            r10 = this;
            r4 = r13
            if (r4 == 0) goto L_0x001b
            java.lang.String r1 = "fileSize"
            boolean r1 = r4.containsKey(r1)
            if (r1 == 0) goto L_0x001b
            java.lang.String r1 = "fileSize"
            java.lang.Object r1 = r4.get(r1)     // Catch:{ NumberFormatException -> 0x001b }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ NumberFormatException -> 0x001b }
            long r1 = java.lang.Long.parseLong(r1)     // Catch:{ NumberFormatException -> 0x001b }
            r3 = 0
            long r1 = r1 - r16
            goto L_0x001d
        L_0x001b:
            r1 = 0
        L_0x001d:
            r8 = r1
            java.lang.String r5 = "GET"
            r1 = r10
            r2 = r11
            r3 = r12
            r6 = r16
            java.net.HttpURLConnection r1 = r1.createConnection(r2, r3, r4, r5, r6)
            r2 = 0
            if (r1 == 0) goto L_0x009a
            r3 = 0
            java.io.InputStream r4 = r1.getInputStream()     // Catch:{ IOException -> 0x007c }
            int r3 = r1.getResponseCode()     // Catch:{ IOException -> 0x0075, all -> 0x0071 }
            r5 = 200(0xc8, float:2.8E-43)
            if (r3 == r5) goto L_0x0045
            int r3 = r1.getResponseCode()     // Catch:{ IOException -> 0x0075, all -> 0x0071 }
            r5 = 206(0xce, float:2.89E-43)
            if (r3 != r5) goto L_0x0042
            goto L_0x0045
        L_0x0042:
            r2 = r4
            r3 = 0
            goto L_0x0052
        L_0x0045:
            java.io.InputStream r2 = r1.getInputStream()     // Catch:{ IOException -> 0x0075, all -> 0x0071 }
            r3 = r10
            r4 = r2
            r5 = r14
            r6 = r15
            r7 = r8
            r3.a(r4, r5, r6, r7)     // Catch:{ IOException -> 0x006e, all -> 0x006b }
            r3 = 1
        L_0x0052:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "code: "
            r4.<init>(r5)
            int r5 = r1.getResponseCode()
            r4.append(r5)
            r1.disconnect()
            a(r14)
            a(r2)
            r2 = r3
            goto L_0x009a
        L_0x006b:
            r0 = move-exception
            r3 = r2
            goto L_0x007a
        L_0x006e:
            r0 = move-exception
            r3 = r2
            goto L_0x007d
        L_0x0071:
            r0 = move-exception
            r2 = r0
            r3 = r4
            goto L_0x0082
        L_0x0075:
            r0 = move-exception
            r2 = r0
            r3 = r4
            goto L_0x007e
        L_0x0079:
            r0 = move-exception
        L_0x007a:
            r2 = r0
            goto L_0x0082
        L_0x007c:
            r0 = move-exception
        L_0x007d:
            r2 = r0
        L_0x007e:
            android.util.Log.getStackTraceString(r2)     // Catch:{ all -> 0x0079 }
            throw r2     // Catch:{ all -> 0x0079 }
        L_0x0082:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "code: "
            r4.<init>(r5)
            int r5 = r1.getResponseCode()
            r4.append(r5)
            r1.disconnect()
            a(r14)
            a(r3)
            throw r2
        L_0x009a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.protocol.http.HttpClientHelper.a(java.lang.String, java.lang.String, java.util.Map, java.io.OutputStream, com.autonavi.link.protocol.http.Progresser, long):boolean");
    }

    /* JADX WARNING: type inference failed for: r11v0, types: [java.util.Map<java.lang.String, java.lang.String>] */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.util.Map] */
    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r11v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r10v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r11v2 */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r11v4 */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r11v5 */
    /* JADX WARNING: type inference failed for: r11v6 */
    /* JADX WARNING: type inference failed for: r11v7, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r9v9, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r11v8 */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r11v9 */
    /* JADX WARNING: type inference failed for: r10v7 */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r7v4 */
    /* JADX WARNING: type inference failed for: r11v10 */
    /* JADX WARNING: type inference failed for: r10v10 */
    /* JADX WARNING: type inference failed for: r11v11 */
    /* JADX WARNING: type inference failed for: r11v12 */
    /* JADX WARNING: type inference failed for: r11v13 */
    /* JADX WARNING: type inference failed for: r11v17, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r11v18 */
    /* JADX WARNING: type inference failed for: r11v19 */
    /* JADX WARNING: type inference failed for: r10v12 */
    /* JADX WARNING: type inference failed for: r10v13 */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r11v20 */
    /* JADX WARNING: type inference failed for: r11v21 */
    /* JADX WARNING: type inference failed for: r11v22 */
    /* JADX WARNING: type inference failed for: r11v23 */
    /* JADX WARNING: type inference failed for: r11v24 */
    /* JADX WARNING: type inference failed for: r11v25 */
    /* JADX WARNING: type inference failed for: r11v26 */
    /* JADX WARNING: type inference failed for: r11v27 */
    /* JADX WARNING: type inference failed for: r11v28 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r11v6
      assigns: []
      uses: []
      mth insns count: 97
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043 A[Catch:{ IOException -> 0x0095, all -> 0x0092 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007a A[SYNTHETIC, Splitter:B:33:0x007a] */
    /* JADX WARNING: Unknown variable types count: 16 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] postBytes(java.lang.String r9, java.lang.String r10, java.util.Map<java.lang.String, java.lang.String> r11, byte[] r12) throws java.io.IOException {
        /*
            r8 = this;
            java.lang.String r4 = "POST"
            r5 = 0
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r11
            java.net.HttpURLConnection r9 = r0.createConnection(r1, r2, r3, r4, r5)
            r10 = 0
            if (r9 == 0) goto L_0x00a8
            if (r12 == 0) goto L_0x003a
            int r11 = r12.length     // Catch:{ IOException -> 0x0035, all -> 0x0030 }
            if (r11 <= 0) goto L_0x003a
            java.lang.String r11 = "Content-Length"
            int r0 = r12.length     // Catch:{ IOException -> 0x0035, all -> 0x0030 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x0035, all -> 0x0030 }
            r9.setRequestProperty(r11, r0)     // Catch:{ IOException -> 0x0035, all -> 0x0030 }
            java.lang.String r11 = "Content-Type"
            java.lang.String r0 = "application/octet-stream"
            r9.setRequestProperty(r11, r0)     // Catch:{ IOException -> 0x0035, all -> 0x0030 }
            java.io.OutputStream r11 = r9.getOutputStream()     // Catch:{ IOException -> 0x0035, all -> 0x0030 }
            r11.write(r12)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r11.close()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            goto L_0x003b
        L_0x0030:
            r9 = move-exception
            r11 = r10
            r12 = r11
            goto L_0x009e
        L_0x0035:
            r9 = move-exception
            r11 = r10
            r12 = r11
            goto L_0x0099
        L_0x003a:
            r11 = r10
        L_0x003b:
            int r12 = r9.getResponseCode()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r0 = 200(0xc8, float:2.8E-43)
            if (r12 != r0) goto L_0x007a
            java.io.InputStream r9 = r9.getInputStream()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.io.ByteArrayOutputStream r12 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0073, all -> 0x006d }
            r12.<init>()     // Catch:{ IOException -> 0x0073, all -> 0x006d }
            r4 = 0
            r5 = 0
            r1 = r8
            r2 = r9
            r3 = r12
            r1.a(r2, r3, r4, r5)     // Catch:{ IOException -> 0x0068, all -> 0x0063 }
            byte[] r10 = r12.toByteArray()     // Catch:{ IOException -> 0x0068, all -> 0x0063 }
            a(r11)
            a(r9)
            a(r12)
            return r10
        L_0x0063:
            r10 = move-exception
            r7 = r10
            r10 = r9
            r9 = r7
            goto L_0x009e
        L_0x0068:
            r10 = move-exception
            r7 = r11
            r11 = r9
            r9 = r10
            goto L_0x0078
        L_0x006d:
            r12 = move-exception
            r7 = r10
            r10 = r9
            r9 = r12
            r12 = r7
            goto L_0x009e
        L_0x0073:
            r12 = move-exception
            r7 = r11
            r11 = r9
            r9 = r12
            r12 = r10
        L_0x0078:
            r10 = r7
            goto L_0x0099
        L_0x007a:
            java.io.IOException r12 = new java.io.IOException     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.lang.String r1 = "HttpURLConnection get getResponseCode: "
            r0.<init>(r1)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            int r9 = r9.getResponseCode()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r0.append(r9)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.lang.String r9 = r0.toString()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r12.<init>(r9)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            throw r12     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
        L_0x0092:
            r9 = move-exception
            r12 = r10
            goto L_0x009e
        L_0x0095:
            r9 = move-exception
            r12 = r10
            r10 = r11
            r11 = r12
        L_0x0099:
            throw r9     // Catch:{ all -> 0x009a }
        L_0x009a:
            r9 = move-exception
            r7 = r11
            r11 = r10
            r10 = r7
        L_0x009e:
            a(r11)
            a(r10)
            a(r12)
            throw r9
        L_0x00a8:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.protocol.http.HttpClientHelper.postBytes(java.lang.String, java.lang.String, java.util.Map, byte[]):byte[]");
    }

    /* JADX WARNING: type inference failed for: r10v0, types: [java.util.Map<java.lang.String, java.lang.String>] */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.util.Map] */
    /* JADX WARNING: type inference failed for: r9v1 */
    /* JADX WARNING: type inference failed for: r10v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r9v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r11v1 */
    /* JADX WARNING: type inference failed for: r10v2 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r11v2 */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r10v3 */
    /* JADX WARNING: type inference failed for: r11v3 */
    /* JADX WARNING: type inference failed for: r10v4 */
    /* JADX WARNING: type inference failed for: r9v5 */
    /* JADX WARNING: type inference failed for: r10v5 */
    /* JADX WARNING: type inference failed for: r11v4 */
    /* JADX WARNING: type inference failed for: r10v6 */
    /* JADX WARNING: type inference failed for: r10v7, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r11v7, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r10v8 */
    /* JADX WARNING: type inference failed for: r11v9 */
    /* JADX WARNING: type inference failed for: r10v9 */
    /* JADX WARNING: type inference failed for: r10v13, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r10v14 */
    /* JADX WARNING: type inference failed for: r10v15 */
    /* JADX WARNING: type inference failed for: r9v12 */
    /* JADX WARNING: type inference failed for: r9v13 */
    /* JADX WARNING: type inference failed for: r9v14 */
    /* JADX WARNING: type inference failed for: r10v16 */
    /* JADX WARNING: type inference failed for: r11v10 */
    /* JADX WARNING: type inference failed for: r11v11 */
    /* JADX WARNING: type inference failed for: r10v17 */
    /* JADX WARNING: type inference failed for: r10v18 */
    /* JADX WARNING: type inference failed for: r10v19 */
    /* JADX WARNING: type inference failed for: r10v20 */
    /* JADX WARNING: type inference failed for: r10v21 */
    /* JADX WARNING: type inference failed for: r10v22 */
    /* JADX WARNING: type inference failed for: r11v12 */
    /* JADX WARNING: type inference failed for: r11v13 */
    /* JADX WARNING: type inference failed for: r10v23 */
    /* JADX WARNING: type inference failed for: r10v24 */
    /* JADX WARNING: type inference failed for: r10v25 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v2
      assigns: []
      uses: []
      mth insns count: 99
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041 A[Catch:{ IOException -> 0x00b1, all -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0073 A[Catch:{ IOException -> 0x0095, all -> 0x0093 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0089 A[Catch:{ IOException -> 0x0095, all -> 0x0093 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0097 A[SYNTHETIC, Splitter:B:46:0x0097] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0081 A[EDGE_INSN: B:62:0x0081->B:39:0x0081 ?: BREAK  
    EDGE_INSN: B:62:0x0081->B:39:0x0081 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 16 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void postBytes(java.lang.String r8, java.lang.String r9, java.util.Map<java.lang.String, java.lang.String> r10, byte[] r11, com.autonavi.link.protocol.http.HttpClientHelper.IPostFileProgresser r12) throws java.io.IOException {
        /*
            r7 = this;
            java.lang.String r4 = "POST"
            r5 = 0
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r10
            java.net.HttpURLConnection r8 = r0.createConnection(r1, r2, r3, r4, r5)
            if (r8 == 0) goto L_0x00bf
            r9 = 0
            if (r11 == 0) goto L_0x0038
            int r10 = r11.length     // Catch:{ IOException -> 0x0034, all -> 0x0030 }
            if (r10 <= 0) goto L_0x0038
            java.lang.String r10 = "Content-Length"
            int r0 = r11.length     // Catch:{ IOException -> 0x0034, all -> 0x0030 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x0034, all -> 0x0030 }
            r8.setRequestProperty(r10, r0)     // Catch:{ IOException -> 0x0034, all -> 0x0030 }
            java.lang.String r10 = "Content-Type"
            java.lang.String r0 = "application/octet-stream"
            r8.setRequestProperty(r10, r0)     // Catch:{ IOException -> 0x0034, all -> 0x0030 }
            java.io.OutputStream r10 = r8.getOutputStream()     // Catch:{ IOException -> 0x0034, all -> 0x0030 }
            r10.write(r11)     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            r10.close()     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            goto L_0x0039
        L_0x0030:
            r8 = move-exception
            r10 = r9
            goto L_0x00b8
        L_0x0034:
            r8 = move-exception
            r11 = r9
            goto L_0x00b4
        L_0x0038:
            r10 = r9
        L_0x0039:
            int r11 = r8.getResponseCode()     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            r0 = 200(0xc8, float:2.8E-43)
            if (r11 != r0) goto L_0x0097
            if (r12 == 0) goto L_0x004a
            java.util.Map r11 = r8.getHeaderFields()     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            r12.onRequestReceived(r11)     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
        L_0x004a:
            java.io.InputStream r11 = r8.getInputStream()     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            java.lang.String r9 = "content-length"
            java.lang.String r9 = r8.getHeaderField(r9)     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            r0 = 0
            if (r9 == 0) goto L_0x0066
            int r1 = r9.length()     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            if (r1 <= 0) goto L_0x0066
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ Exception -> 0x0062 }
            goto L_0x0067
        L_0x0062:
            r9 = move-exception
            r9.printStackTrace()     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
        L_0x0066:
            r9 = 0
        L_0x0067:
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            r2 = 0
        L_0x006c:
            int r3 = r11.read(r1)     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            r4 = -1
            if (r3 == r4) goto L_0x0081
            int r2 = r2 + r3
            if (r12 == 0) goto L_0x006c
            byte[] r4 = new byte[r3]     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            java.lang.System.arraycopy(r1, r0, r4, r0, r3)     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            boolean r3 = r12.onDataReceived(r4, r2, r9)     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            if (r3 != 0) goto L_0x006c
        L_0x0081:
            r11.close()     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            r8.disconnect()     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
            if (r12 == 0) goto L_0x008c
            r12.onRequestFinished()     // Catch:{ IOException -> 0x0095, all -> 0x0093 }
        L_0x008c:
            a(r10)
            a(r11)
            return
        L_0x0093:
            r8 = move-exception
            goto L_0x00b7
        L_0x0095:
            r8 = move-exception
            goto L_0x00b3
        L_0x0097:
            java.io.IOException r11 = new java.io.IOException     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            java.lang.String r0 = "HttpURLConnection get getResponseCode: "
            r12.<init>(r0)     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            int r8 = r8.getResponseCode()     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            r12.append(r8)     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            java.lang.String r8 = r12.toString()     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            r11.<init>(r8)     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
            throw r11     // Catch:{ IOException -> 0x00b1, all -> 0x00af }
        L_0x00af:
            r8 = move-exception
            goto L_0x00b8
        L_0x00b1:
            r8 = move-exception
            r11 = r9
        L_0x00b3:
            r9 = r10
        L_0x00b4:
            throw r8     // Catch:{ all -> 0x00b5 }
        L_0x00b5:
            r8 = move-exception
            r10 = r9
        L_0x00b7:
            r9 = r11
        L_0x00b8:
            a(r10)
            a(r9)
            throw r8
        L_0x00bf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.protocol.http.HttpClientHelper.postBytes(java.lang.String, java.lang.String, java.util.Map, byte[], com.autonavi.link.protocol.http.HttpClientHelper$IPostFileProgresser):void");
    }

    public boolean postFile(String str, String str2, Map<String, String> map, String str3) throws IOException {
        return postFile(str, str2, map, str3, null);
    }

    public boolean postFile(String str, String str2, Map<String, String> map, String str3, Progresser progresser) throws IOException {
        String str4 = str;
        String str5 = str2;
        Map<String, String> map2 = map;
        String str6 = str3;
        boolean z = false;
        if (str4.contains("127.0.0.1")) {
            Proxy a2 = a();
            if (a2 != null) {
                z = true;
            }
            new MultipartUtility("UTF-8").addFilePart(a(str4, str5, map2, z), a2, "uploadFile", new File(str6), progresser);
        } else {
            new MultipartUtility("UTF-8").addFilePart(a(str4, str5, map2, false), null, "uploadFile", new File(str6), progresser);
        }
        return true;
    }

    public HttpURLConnection createConnection(String str, String str2, Map<String, String> map, String str3, long j) throws IOException {
        HttpURLConnection httpURLConnection;
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        if (str.contains("127.0.0.1")) {
            httpURLConnection = (HttpURLConnection) new URL(a(str, str2, map, true)).openConnection(a());
        } else {
            httpURLConnection = (HttpURLConnection) new URL(a(str, str2, map, false)).openConnection();
        }
        httpURLConnection.setDoInput(true);
        if ("POST".equalsIgnoreCase(str3)) {
            httpURLConnection.setDoOutput(true);
        }
        httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_UA, "Mozilla/5");
        if (j > 0) {
            StringBuilder sb = new StringBuilder("bytes=");
            sb.append(j);
            sb.append("-");
            httpURLConnection.setRequestProperty("Range", sb.toString());
        }
        return httpURLConnection;
    }

    private String a(String str, String str2, Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(AjxHttpLoader.DOMAIN_HTTP);
        sb.append(str);
        sb.append(str2);
        sb.append("?");
        if (z) {
            sb.append("connectionId=");
            sb.append(LinkProxy.getInstance().getConnectId());
            sb.append("&");
        } else {
            sb.append("connectionId=");
            sb.append(b.a().a(str));
            sb.append("&");
        }
        if (map != null && map.size() > 0) {
            int i = 0;
            for (String next : map.keySet()) {
                i++;
                String str3 = map.get(next);
                if (!TextUtils.isEmpty(str3)) {
                    try {
                        sb.append(next);
                        sb.append("=");
                        sb.append(URLEncoder.encode(str3, "UTF-8"));
                        if (i < map.size()) {
                            sb.append("&");
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    private void a(InputStream inputStream, OutputStream outputStream, Progresser progresser, long j) throws IOException {
        long j2;
        InputStream inputStream2 = inputStream;
        OutputStream outputStream2 = outputStream;
        Progresser progresser2 = progresser;
        long j3 = j;
        if (inputStream2 != null && outputStream2 != null) {
            long currentTimeMillis = System.currentTimeMillis();
            byte[] bArr = new byte[1024];
            int i = 0;
            long j4 = currentTimeMillis;
            int i2 = 0;
            long j5 = 0;
            while (true) {
                int read = inputStream2.read(bArr);
                if (read == -1) {
                    break;
                }
                outputStream2.write(bArr, i, read);
                i2 += read;
                j5 += (long) read;
                long currentTimeMillis2 = System.currentTimeMillis() - j4;
                if (progresser2 == null || currentTimeMillis2 <= 1000 || j3 <= 0) {
                    j2 = currentTimeMillis;
                } else {
                    long currentTimeMillis3 = System.currentTimeMillis();
                    j2 = currentTimeMillis;
                    String format = String.format("%.1f%%", new Object[]{Double.valueOf((((double) i2) * 100.0d) / ((double) j3))});
                    StringBuilder sb = new StringBuilder();
                    sb.append((((double) j5) / (((double) currentTimeMillis2) * 1024.0d)) * 1000.0d);
                    progresser2.onProgress(format, sb.toString());
                    j4 = currentTimeMillis3;
                    j5 = 0;
                }
                currentTimeMillis = j2;
                inputStream2 = inputStream;
                i = 0;
            }
            long j6 = currentTimeMillis;
            if (progresser2 != null && j3 > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append((((double) j3) / (((double) (System.currentTimeMillis() - j6)) * 1024.0d)) * 1000.0d);
                progresser2.onProgress("100", sb2.toString());
            }
            outputStream.flush();
        }
    }

    private Proxy a() {
        return new Proxy(Type.HTTP, new InetSocketAddress("127.0.0.1", LinkProxy.getInstance().getProxyPort()));
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    private static void a(String str) {
        File file = new File(str);
        if (!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
