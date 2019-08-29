package com.xiaomi.channel.commonutils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.xiaomi.channel.commonutils.string.c;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class d {
    public static final Pattern a = Pattern.compile("([^\\s;]+)(.*)");
    public static final Pattern b = Pattern.compile("(.*?charset\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);
    public static final Pattern c = Pattern.compile("(\\<\\?xml\\s+.*?encoding\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    public static final class a extends FilterInputStream {
        private boolean a;

        public a(InputStream inputStream) {
            super(inputStream);
        }

        public final int read(byte[] bArr, int i, int i2) {
            if (!this.a) {
                int read = super.read(bArr, i, i2);
                if (read != -1) {
                    return read;
                }
            }
            this.a = true;
            return -1;
        }
    }

    public static class b {
        public int a;
        public Map<String, String> b;

        public String toString() {
            return String.format("resCode = %1$d, headers = %2$s", new Object[]{Integer.valueOf(this.a), this.b.toString()});
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(android.content.Context r2) {
        /*
            r0 = -1
            java.lang.String r1 = "connectivity"
            java.lang.Object r2 = r2.getSystemService(r1)     // Catch:{ Exception -> 0x0018 }
            android.net.ConnectivityManager r2 = (android.net.ConnectivityManager) r2     // Catch:{ Exception -> 0x0018 }
            if (r2 != 0) goto L_0x000c
            return r0
        L_0x000c:
            android.net.NetworkInfo r2 = r2.getActiveNetworkInfo()     // Catch:{  }
            if (r2 != 0) goto L_0x0013
            return r0
        L_0x0013:
            int r2 = r2.getType()
            return r2
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.network.d.a(android.content.Context):int");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r4v14, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r4v15, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r4v16, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r4v19 */
    /* JADX WARNING: type inference failed for: r4v20 */
    /* JADX WARNING: type inference failed for: r4v23 */
    /* JADX WARNING: type inference failed for: r4v24 */
    /* JADX WARNING: type inference failed for: r4v25 */
    /* JADX WARNING: type inference failed for: r4v26 */
    /* JADX WARNING: type inference failed for: r4v27 */
    /* JADX WARNING: type inference failed for: r4v28 */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00e5, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e6, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e8, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e9, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x009f */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v2
      assigns: []
      uses: []
      mth insns count: 96
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
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e5 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:22:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e8 A[ExcHandler: Throwable (th java.lang.Throwable), Splitter:B:1:0x0006] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00fa A[SYNTHETIC, Splitter:B:54:0x00fa] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ff A[Catch:{ IOException -> 0x0102 }] */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.xiaomi.channel.commonutils.network.b a(android.content.Context r3, java.lang.String r4, java.lang.String r5, java.util.Map<java.lang.String, java.lang.String> r6, java.lang.String r7) {
        /*
            com.xiaomi.channel.commonutils.network.b r0 = new com.xiaomi.channel.commonutils.network.b
            r0.<init>()
            r1 = 0
            java.net.URL r4 = b(r4)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.net.HttpURLConnection r3 = b(r3, r4)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r4 = 10000(0x2710, float:1.4013E-41)
            r3.setConnectTimeout(r4)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r4 = 15000(0x3a98, float:2.102E-41)
            r3.setReadTimeout(r4)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            if (r5 != 0) goto L_0x001c
            java.lang.String r5 = "GET"
        L_0x001c:
            r3.setRequestMethod(r5)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            if (r6 == 0) goto L_0x003f
            java.util.Set r4 = r6.keySet()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
        L_0x0029:
            boolean r5 = r4.hasNext()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            if (r5 == 0) goto L_0x003f
            java.lang.Object r5 = r4.next()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.lang.Object r2 = r6.get(r5)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r3.setRequestProperty(r5, r2)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            goto L_0x0029
        L_0x003f:
            boolean r4 = android.text.TextUtils.isEmpty(r7)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r5 = 0
            r6 = 1
            if (r4 != 0) goto L_0x006c
            r3.setDoOutput(r6)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            byte[] r4 = r7.getBytes()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.io.OutputStream r7 = r3.getOutputStream()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            int r2 = r4.length     // Catch:{ IOException -> 0x0067, Throwable -> 0x0062, all -> 0x005d }
            r7.write(r4, r5, r2)     // Catch:{ IOException -> 0x0067, Throwable -> 0x0062, all -> 0x005d }
            r7.flush()     // Catch:{ IOException -> 0x0067, Throwable -> 0x0062, all -> 0x005d }
            r7.close()     // Catch:{ IOException -> 0x0067, Throwable -> 0x0062, all -> 0x005d }
            goto L_0x006c
        L_0x005d:
            r3 = move-exception
            r4 = r1
            r1 = r7
            goto L_0x00f8
        L_0x0062:
            r3 = move-exception
            r4 = r1
            r1 = r7
            goto L_0x00ea
        L_0x0067:
            r3 = move-exception
            r4 = r1
            r1 = r7
            goto L_0x00f6
        L_0x006c:
            int r4 = r3.getResponseCode()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r0.a = r4     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.lang.String r7 = "Http POST Response Code: "
            r4.<init>(r7)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            int r7 = r0.a     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r4.append(r7)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
        L_0x007e:
            java.lang.String r4 = r3.getHeaderFieldKey(r5)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.lang.String r7 = r3.getHeaderField(r5)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            if (r4 != 0) goto L_0x00dc
            if (r7 == 0) goto L_0x008b
            goto L_0x00dc
        L_0x008b:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            com.xiaomi.channel.commonutils.network.d$a r6 = new com.xiaomi.channel.commonutils.network.d$a     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            java.io.InputStream r7 = r3.getInputStream()     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x009f, Throwable -> 0x00e8, all -> 0x00e5 }
            goto L_0x00b2
        L_0x009f:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            com.xiaomi.channel.commonutils.network.d$a r6 = new com.xiaomi.channel.commonutils.network.d$a     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            java.io.InputStream r3 = r3.getErrorStream()     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r6.<init>(r3)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
        L_0x00b2:
            java.lang.String r3 = r4.readLine()     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            r5.<init>()     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            java.lang.String r6 = "line.separator"
            java.lang.String r6 = java.lang.System.getProperty(r6)     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
        L_0x00c1:
            if (r3 == 0) goto L_0x00ce
            r5.append(r3)     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            r5.append(r6)     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            java.lang.String r3 = r4.readLine()     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            goto L_0x00c1
        L_0x00ce:
            java.lang.String r3 = r5.toString()     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            r0.c = r3     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            r4.close()     // Catch:{ IOException -> 0x00da, Throwable -> 0x00d8 }
            return r0
        L_0x00d8:
            r3 = move-exception
            goto L_0x00ea
        L_0x00da:
            r3 = move-exception
            goto L_0x00f6
        L_0x00dc:
            java.util.Map<java.lang.String, java.lang.String> r2 = r0.b     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            r2.put(r4, r7)     // Catch:{ IOException -> 0x00f4, Throwable -> 0x00e8, all -> 0x00e5 }
            int r5 = r5 + 1
            int r5 = r5 + r6
            goto L_0x007e
        L_0x00e5:
            r3 = move-exception
            r4 = r1
            goto L_0x00f8
        L_0x00e8:
            r3 = move-exception
            r4 = r1
        L_0x00ea:
            java.io.IOException r5 = new java.io.IOException     // Catch:{ all -> 0x00f7 }
            java.lang.String r3 = r3.getMessage()     // Catch:{ all -> 0x00f7 }
            r5.<init>(r3)     // Catch:{ all -> 0x00f7 }
            throw r5     // Catch:{ all -> 0x00f7 }
        L_0x00f4:
            r3 = move-exception
            r4 = r1
        L_0x00f6:
            throw r3     // Catch:{ all -> 0x00f7 }
        L_0x00f7:
            r3 = move-exception
        L_0x00f8:
            if (r1 == 0) goto L_0x00fd
            r1.close()     // Catch:{ IOException -> 0x0102 }
        L_0x00fd:
            if (r4 == 0) goto L_0x0102
            r4.close()     // Catch:{ IOException -> 0x0102 }
        L_0x0102:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.network.d.a(android.content.Context, java.lang.String, java.lang.String, java.util.Map, java.lang.String):com.xiaomi.channel.commonutils.network.b");
    }

    public static b a(Context context, String str, Map<String, String> map) {
        return a(context, str, (String) "POST", (Map<String, String>) null, a(map));
    }

    public static InputStream a(Context context, URL url, boolean z, String str, String str2) {
        return a(context, url, z, str, str2, null, null);
    }

    public static InputStream a(Context context, URL url, boolean z, String str, String str2, Map<String, String> map, b bVar) {
        if (context == null) {
            throw new IllegalArgumentException("context");
        } else if (url == null) {
            throw new IllegalArgumentException("url");
        } else {
            URL url2 = !z ? new URL(a(url.toString())) : url;
            try {
                HttpURLConnection.setFollowRedirects(true);
                HttpURLConnection b2 = b(context, url2);
                b2.setConnectTimeout(10000);
                b2.setReadTimeout(HttpConstants.CONNECTION_TIME_OUT);
                if (!TextUtils.isEmpty(str)) {
                    b2.setRequestProperty(H5AppHttpRequest.HEADER_UA, str);
                }
                if (str2 != null) {
                    b2.setRequestProperty("Cookie", str2);
                }
                if (map != null) {
                    for (String next : map.keySet()) {
                        b2.setRequestProperty(next, map.get(next));
                    }
                }
                if (bVar != null && (url.getProtocol().equals("http") || url.getProtocol().equals("https"))) {
                    bVar.a = b2.getResponseCode();
                    if (bVar.b == null) {
                        bVar.b = new HashMap();
                    }
                    int i = 0;
                    while (true) {
                        String headerFieldKey = b2.getHeaderFieldKey(i);
                        String headerField = b2.getHeaderField(i);
                        if (headerFieldKey == null && headerField == null) {
                            break;
                        }
                        if (!TextUtils.isEmpty(headerFieldKey) && !TextUtils.isEmpty(headerField)) {
                            bVar.b.put(headerFieldKey, headerField);
                        }
                        i++;
                    }
                }
                return new a(b2.getInputStream());
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                throw new IOException(th.getMessage());
            }
        }
    }

    public static String a(Context context, URL url) {
        return a(context, url, false, null, "UTF-8", null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0045 A[SYNTHETIC, Splitter:B:18:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r0, java.net.URL r1, boolean r2, java.lang.String r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.io.InputStream r0 = a(r0, r1, r2, r3, r5)     // Catch:{ all -> 0x0041 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x003f }
            r2 = 1024(0x400, float:1.435E-42)
            r1.<init>(r2)     // Catch:{ all -> 0x003f }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x003f }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ all -> 0x003f }
            r3.<init>(r0, r4)     // Catch:{ all -> 0x003f }
            r2.<init>(r3)     // Catch:{ all -> 0x003f }
            r3 = 4096(0x1000, float:5.74E-42)
            char[] r3 = new char[r3]     // Catch:{ all -> 0x003f }
        L_0x0019:
            r4 = -1
            int r5 = r2.read(r3)     // Catch:{ all -> 0x003f }
            if (r4 == r5) goto L_0x0025
            r4 = 0
            r1.append(r3, r4, r5)     // Catch:{ all -> 0x003f }
            goto L_0x0019
        L_0x0025:
            if (r0 == 0) goto L_0x003a
            r0.close()     // Catch:{ IOException -> 0x002b }
            goto L_0x003a
        L_0x002b:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to close responseStream"
            r2.<init>(r3)
            java.lang.String r0 = r0.toString()
            r2.append(r0)
        L_0x003a:
            java.lang.String r0 = r1.toString()
            return r0
        L_0x003f:
            r1 = move-exception
            goto L_0x0043
        L_0x0041:
            r1 = move-exception
            r0 = 0
        L_0x0043:
            if (r0 == 0) goto L_0x0058
            r0.close()     // Catch:{ IOException -> 0x0049 }
            goto L_0x0058
        L_0x0049:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Failed to close responseStream"
            r2.<init>(r3)
            java.lang.String r0 = r0.toString()
            r2.append(r0)
        L_0x0058:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.network.d.a(android.content.Context, java.net.URL, boolean, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new String();
        return String.format("%s&key=%s", new Object[]{str, c.a(String.format("%sbe988a6134bc8254465424e5a70ef037", new Object[]{str}))});
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [java.util.Map<java.lang.String, java.lang.String>, java.util.Map] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r6v11, types: [java.io.DataOutputStream] */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r0v20 */
    /* JADX WARNING: type inference failed for: r0v22, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r0v23 */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r0v24 */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r0v25 */
    /* JADX WARNING: type inference failed for: r6v17 */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* JADX WARNING: type inference failed for: r6v19 */
    /* JADX WARNING: type inference failed for: r6v20 */
    /* JADX WARNING: type inference failed for: r0v26 */
    /* JADX WARNING: type inference failed for: r0v27 */
    /* JADX WARNING: type inference failed for: r0v28 */
    /* JADX WARNING: type inference failed for: r0v29 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v2
      assigns: []
      uses: []
      mth insns count: 126
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
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0144 A[SYNTHETIC, Splitter:B:61:0x0144] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0149 A[Catch:{ IOException -> 0x0151 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x014e A[Catch:{ IOException -> 0x0151 }] */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r5, java.util.Map<java.lang.String, java.lang.String> r6, java.io.File r7, java.lang.String r8) {
        /*
            boolean r0 = r7.exists()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            java.lang.String r0 = r7.getName()
            java.net.URL r2 = new java.net.URL     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r2.<init>(r5)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.net.URLConnection r5 = r2.openConnection()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r2 = 15000(0x3a98, float:2.102E-41)
            r5.setReadTimeout(r2)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r2 = 10000(0x2710, float:1.4013E-41)
            r5.setConnectTimeout(r2)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r2 = 1
            r5.setDoInput(r2)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r5.setDoOutput(r2)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r2 = 0
            r5.setUseCaches(r2)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.String r3 = "POST"
            r5.setRequestMethod(r3)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.String r3 = "Connection"
            java.lang.String r4 = "Keep-Alive"
            r5.setRequestProperty(r3, r4)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.String r3 = "Content-Type"
            java.lang.String r4 = "multipart/form-data;boundary=*****"
            r5.setRequestProperty(r3, r4)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            if (r6 == 0) goto L_0x0065
            java.util.Set r6 = r6.entrySet()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
        L_0x0049:
            boolean r3 = r6.hasNext()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            if (r3 == 0) goto L_0x0065
            java.lang.Object r3 = r6.next()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.Object r4 = r3.getKey()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.Object r3 = r3.getValue()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r5.setRequestProperty(r4, r3)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            goto L_0x0049
        L_0x0065:
            int r6 = r0.length()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            int r6 = r6 + 77
            long r3 = r7.length()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            int r0 = (int) r3     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            int r6 = r6 + r0
            int r0 = r8.length()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            int r6 = r6 + r0
            r5.setFixedLengthStreamingMode(r6)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.io.DataOutputStream r6 = new java.io.DataOutputStream     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.io.OutputStream r0 = r5.getOutputStream()     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            r6.<init>(r0)     // Catch:{ IOException -> 0x013d, Throwable -> 0x0130, all -> 0x012c }
            java.lang.String r0 = "--*****\r\n"
            r6.writeBytes(r0)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.String r3 = "Content-Disposition: form-data; name=\""
            r0.<init>(r3)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            r0.append(r8)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.String r8 = "\";filename=\""
            r0.append(r8)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.String r8 = r7.getName()     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            r0.append(r8)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.String r8 = "\"\r\n"
            r0.append(r8)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.String r8 = r0.toString()     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            r6.writeBytes(r8)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.lang.String r8 = "\r\n"
            r6.writeBytes(r8)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            r8.<init>(r7)     // Catch:{ IOException -> 0x0129, Throwable -> 0x0126, all -> 0x0123 }
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r7 = new byte[r7]     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
        L_0x00b7:
            int r0 = r8.read(r7)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r3 = -1
            if (r0 == r3) goto L_0x00c5
            r6.write(r7, r2, r0)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r6.flush()     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            goto L_0x00b7
        L_0x00c5:
            java.lang.String r7 = "\r\n"
            r6.writeBytes(r7)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.lang.String r7 = "--"
            r6.writeBytes(r7)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.lang.String r7 = "*****"
            r6.writeBytes(r7)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.lang.String r7 = "--"
            r6.writeBytes(r7)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.lang.String r7 = "\r\n"
            r6.writeBytes(r7)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r6.flush()     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.lang.StringBuffer r7 = new java.lang.StringBuffer     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r7.<init>()     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            com.xiaomi.channel.commonutils.network.d$a r3 = new com.xiaomi.channel.commonutils.network.d$a     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            java.io.InputStream r5 = r5.getInputStream()     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r3.<init>(r5)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x011f, Throwable -> 0x011b, all -> 0x0117 }
        L_0x00f9:
            java.lang.String r5 = r0.readLine()     // Catch:{ IOException -> 0x0115, Throwable -> 0x0113, all -> 0x0111 }
            if (r5 == 0) goto L_0x0103
            r7.append(r5)     // Catch:{ IOException -> 0x0115, Throwable -> 0x0113, all -> 0x0111 }
            goto L_0x00f9
        L_0x0103:
            java.lang.String r5 = r7.toString()     // Catch:{ IOException -> 0x0115, Throwable -> 0x0113, all -> 0x0111 }
            r8.close()     // Catch:{ IOException -> 0x0110 }
            r6.close()     // Catch:{ IOException -> 0x0110 }
            r0.close()     // Catch:{ IOException -> 0x0110 }
        L_0x0110:
            return r5
        L_0x0111:
            r5 = move-exception
            goto L_0x0119
        L_0x0113:
            r5 = move-exception
            goto L_0x011d
        L_0x0115:
            r5 = move-exception
            goto L_0x0121
        L_0x0117:
            r5 = move-exception
            r0 = r1
        L_0x0119:
            r1 = r8
            goto L_0x0142
        L_0x011b:
            r5 = move-exception
            r0 = r1
        L_0x011d:
            r1 = r8
            goto L_0x0133
        L_0x011f:
            r5 = move-exception
            r0 = r1
        L_0x0121:
            r1 = r8
            goto L_0x0140
        L_0x0123:
            r5 = move-exception
            r0 = r1
            goto L_0x0142
        L_0x0126:
            r5 = move-exception
            r0 = r1
            goto L_0x0133
        L_0x0129:
            r5 = move-exception
            r0 = r1
            goto L_0x0140
        L_0x012c:
            r5 = move-exception
            r6 = r1
            r0 = r6
            goto L_0x0142
        L_0x0130:
            r5 = move-exception
            r6 = r1
            r0 = r6
        L_0x0133:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ all -> 0x0141 }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x0141 }
            r7.<init>(r5)     // Catch:{ all -> 0x0141 }
            throw r7     // Catch:{ all -> 0x0141 }
        L_0x013d:
            r5 = move-exception
            r6 = r1
            r0 = r6
        L_0x0140:
            throw r5     // Catch:{ all -> 0x0141 }
        L_0x0141:
            r5 = move-exception
        L_0x0142:
            if (r1 == 0) goto L_0x0147
            r1.close()     // Catch:{ IOException -> 0x0151 }
        L_0x0147:
            if (r6 == 0) goto L_0x014c
            r6.close()     // Catch:{ IOException -> 0x0151 }
        L_0x014c:
            if (r0 == 0) goto L_0x0151
            r0.close()     // Catch:{ IOException -> 0x0151 }
        L_0x0151:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.network.d.a(java.lang.String, java.util.Map, java.io.File, java.lang.String):java.lang.String");
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Entry next : map.entrySet()) {
            if (!(next.getKey() == null || next.getValue() == null)) {
                try {
                    stringBuffer.append(URLEncoder.encode((String) next.getKey(), "UTF-8"));
                    stringBuffer.append("=");
                    stringBuffer.append(URLEncoder.encode((String) next.getValue(), "UTF-8"));
                    stringBuffer.append("&");
                } catch (UnsupportedEncodingException e) {
                    new StringBuilder("Failed to convert from params map to string: ").append(e.toString());
                    new StringBuilder("map: ").append(map.toString());
                    return null;
                }
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static HttpURLConnection b(Context context, URL url) {
        return (HttpURLConnection) (("http".equals(url.getProtocol()) && b(context)) ? url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress("10.0.0.200", 80))) : url.openConnection());
    }

    private static URL b(String str) {
        return new URL(str);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(android.content.Context r3) {
        /*
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r3.getSystemService(r0)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            java.lang.String r0 = r0.getSimCountryIso()
            java.lang.String r1 = "CN"
            boolean r0 = r1.equalsIgnoreCase(r0)
            r1 = 0
            if (r0 != 0) goto L_0x0016
            return r1
        L_0x0016:
            java.lang.String r0 = "connectivity"
            java.lang.Object r3 = r3.getSystemService(r0)     // Catch:{ Exception -> 0x0044 }
            android.net.ConnectivityManager r3 = (android.net.ConnectivityManager) r3     // Catch:{ Exception -> 0x0044 }
            if (r3 != 0) goto L_0x0021
            return r1
        L_0x0021:
            android.net.NetworkInfo r3 = r3.getActiveNetworkInfo()     // Catch:{  }
            if (r3 != 0) goto L_0x0028
            return r1
        L_0x0028:
            java.lang.String r3 = r3.getExtraInfo()
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x0044
            int r0 = r3.length()
            r2 = 3
            if (r0 >= r2) goto L_0x003a
            return r1
        L_0x003a:
            java.lang.String r0 = "ctwap"
            boolean r3 = r3.contains(r0)
            if (r3 == 0) goto L_0x0044
            r3 = 1
            return r3
        L_0x0044:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.network.d.b(android.content.Context):boolean");
    }

    public static boolean c(Context context) {
        return a(context) >= 0;
    }

    public static boolean d(Context context) {
        NetworkInfo networkInfo;
        try {
            networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            networkInfo = null;
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean e(android.content.Context r2) {
        /*
            r0 = 0
            java.lang.String r1 = "connectivity"
            java.lang.Object r2 = r2.getSystemService(r1)     // Catch:{ Exception -> 0x001b }
            android.net.ConnectivityManager r2 = (android.net.ConnectivityManager) r2     // Catch:{ Exception -> 0x001b }
            if (r2 != 0) goto L_0x000c
            return r0
        L_0x000c:
            android.net.NetworkInfo r2 = r2.getActiveNetworkInfo()     // Catch:{  }
            if (r2 != 0) goto L_0x0013
            return r0
        L_0x0013:
            int r2 = r2.getType()
            r1 = 1
            if (r1 != r2) goto L_0x001b
            return r1
        L_0x001b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.channel.commonutils.network.d.e(android.content.Context):boolean");
    }

    public static boolean f(Context context) {
        return g(context) || h(context) || i(context);
    }

    public static boolean g(Context context) {
        NetworkInfo j = j(context);
        return j != null && j.getType() == 0 && 13 == j.getSubtype();
    }

    public static boolean h(Context context) {
        NetworkInfo j = j(context);
        if (j == null || j.getType() != 0) {
            return false;
        }
        String subtypeName = j.getSubtypeName();
        if ("TD-SCDMA".equalsIgnoreCase(subtypeName) || "CDMA2000".equalsIgnoreCase(subtypeName) || "WCDMA".equalsIgnoreCase(subtypeName)) {
            return true;
        }
        switch (j.getSubtype()) {
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                break;
            default:
                return false;
        }
        return true;
    }

    public static boolean i(Context context) {
        NetworkInfo j = j(context);
        if (j == null || j.getType() != 0) {
            return false;
        }
        int subtype = j.getSubtype();
        if (!(subtype == 4 || subtype == 7 || subtype == 11)) {
            switch (subtype) {
                case 1:
                case 2:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public static NetworkInfo j(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String k(Context context) {
        if (e(context)) {
            return "wifi";
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            try {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                sb.append(activeNetworkInfo.getTypeName());
                sb.append("-");
                sb.append(activeNetworkInfo.getSubtypeName());
                sb.append("-");
                sb.append(activeNetworkInfo.getExtraInfo());
                return sb.toString().toLowerCase();
            } catch (Exception unused) {
                return "";
            }
        } catch (Exception unused2) {
            return "";
        }
    }
}
