package com.sina.weibo.sdk.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.Utility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Set;

public class HttpManager {
    private static final String BOUNDARY = getBoundry();
    private static final String END_MP_BOUNDARY;
    private static final String MP_BOUNDARY;
    private static final String TAG = "HttpManager";

    private static native String calcOauthSignNative(Context context, String str, String str2);

    public static void fillCommonRequestParam(IRequestParam iRequestParam) {
    }

    static {
        System.loadLibrary("weibosdkcore");
        StringBuilder sb = new StringBuilder("--");
        sb.append(BOUNDARY);
        MP_BOUNDARY = sb.toString();
        StringBuilder sb2 = new StringBuilder("--");
        sb2.append(BOUNDARY);
        sb2.append("--");
        END_MP_BOUNDARY = sb2.toString();
    }

    public static String openUrl(Context context, String str, String str2, WeiboParameters weiboParameters) throws WeiboException {
        String requestHttpExecute = requestHttpExecute(context, str, str2, weiboParameters);
        LogUtil.d("HttpManager", "Response : ".concat(String.valueOf(requestHttpExecute)));
        return requestHttpExecute;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String requestHttpExecute(android.content.Context r2, java.lang.String r3, java.lang.String r4, com.sina.weibo.sdk.net.WeiboParameters r5) {
        /*
            r0 = 0
            setHttpCommonParam(r2, r5)     // Catch:{ IOException -> 0x009a }
            java.lang.String r1 = "GET"
            boolean r4 = r1.equals(r4)     // Catch:{ IOException -> 0x009a }
            r1 = 1
            if (r4 == 0) goto L_0x0035
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x009a }
            r4.<init>()     // Catch:{ IOException -> 0x009a }
            r4.append(r3)     // Catch:{ IOException -> 0x009a }
            java.lang.String r3 = "?"
            r4.append(r3)     // Catch:{ IOException -> 0x009a }
            java.lang.String r3 = r5.encodeUrl()     // Catch:{ IOException -> 0x009a }
            r4.append(r3)     // Catch:{ IOException -> 0x009a }
            java.lang.String r3 = r4.toString()     // Catch:{ IOException -> 0x009a }
            java.net.HttpURLConnection r2 = com.sina.weibo.sdk.net.ConnectionFactory.createConnect(r3, r2)     // Catch:{ IOException -> 0x009a }
            java.lang.String r3 = "GET"
            r2.setRequestMethod(r3)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r2.setInstanceFollowRedirects(r1)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r2.connect()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            goto L_0x0074
        L_0x0035:
            java.net.HttpURLConnection r2 = com.sina.weibo.sdk.net.ConnectionFactory.createConnect(r3, r2)     // Catch:{ IOException -> 0x009a }
            r2.setInstanceFollowRedirects(r1)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r2.connect()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            boolean r3 = r5.hasBinaryData()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            if (r3 == 0) goto L_0x0058
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            buildParams(r3, r5)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.flush()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.close()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            goto L_0x0074
        L_0x0058:
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.io.OutputStream r4 = r2.getOutputStream()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.lang.String r4 = r5.encodeUrl()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.write(r4)     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.flush()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
            r3.close()     // Catch:{ IOException -> 0x0095, all -> 0x0092 }
        L_0x0074:
            r0 = r2
            int r2 = r0.getResponseCode()     // Catch:{ IOException -> 0x009a }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 == r3) goto L_0x0087
            java.lang.String r3 = readConnectResponse(r0, r1)     // Catch:{ IOException -> 0x009a }
            com.sina.weibo.sdk.exception.WeiboHttpException r4 = new com.sina.weibo.sdk.exception.WeiboHttpException     // Catch:{ IOException -> 0x009a }
            r4.<init>(r3, r2)     // Catch:{ IOException -> 0x009a }
            throw r4     // Catch:{ IOException -> 0x009a }
        L_0x0087:
            r2 = 0
            java.lang.String r2 = readConnectResponse(r0, r2)     // Catch:{ IOException -> 0x009a }
            if (r0 == 0) goto L_0x0091
            r0.disconnect()
        L_0x0091:
            return r2
        L_0x0092:
            r3 = move-exception
            r0 = r2
            goto L_0x00a4
        L_0x0095:
            r3 = move-exception
            r0 = r2
            goto L_0x009b
        L_0x0098:
            r3 = move-exception
            goto L_0x00a4
        L_0x009a:
            r3 = move-exception
        L_0x009b:
            r3.printStackTrace()     // Catch:{ all -> 0x0098 }
            com.sina.weibo.sdk.exception.WeiboException r2 = new com.sina.weibo.sdk.exception.WeiboException     // Catch:{ all -> 0x0098 }
            r2.<init>(r3)     // Catch:{ all -> 0x0098 }
            throw r2     // Catch:{ all -> 0x0098 }
        L_0x00a4:
            if (r0 == 0) goto L_0x00a9
            r0.disconnect()
        L_0x00a9:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.net.HttpManager.requestHttpExecute(android.content.Context, java.lang.String, java.lang.String, com.sina.weibo.sdk.net.WeiboParameters):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v6, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v8, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v9, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r4v10, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:6|7|(2:(3:9|10|(1:12)(1:46))|13)|(2:18|19)|20|21|22) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x003a */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2
      assigns: []
      uses: []
      mth insns count: 50
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
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0058 A[SYNTHETIC, Splitter:B:38:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x005d A[SYNTHETIC, Splitter:B:42:0x005d] */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String readConnectResponse(java.net.HttpURLConnection r4, boolean r5) {
        /*
            r0 = 8192(0x2000, float:1.14794E-41)
            r1 = 0
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x004c, all -> 0x0049 }
            if (r5 == 0) goto L_0x000c
            java.io.InputStream r4 = r4.getErrorStream()     // Catch:{ IOException -> 0x004c, all -> 0x0049 }
            goto L_0x0010
        L_0x000c:
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ IOException -> 0x004c, all -> 0x0049 }
        L_0x0010:
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0044, all -> 0x003e }
            r5.<init>()     // Catch:{ IOException -> 0x0044, all -> 0x003e }
            if (r4 == 0) goto L_0x0035
        L_0x0017:
            int r1 = r4.read(r0)     // Catch:{ IOException -> 0x0033, all -> 0x002f }
            r2 = -1
            if (r1 == r2) goto L_0x0023
            r2 = 0
            r5.write(r0, r2, r1)     // Catch:{ IOException -> 0x0033, all -> 0x002f }
            goto L_0x0017
        L_0x0023:
            java.lang.String r1 = new java.lang.String     // Catch:{ IOException -> 0x0033, all -> 0x002f }
            byte[] r0 = r5.toByteArray()     // Catch:{ IOException -> 0x0033, all -> 0x002f }
            java.lang.String r2 = "UTF-8"
            r1.<init>(r0, r2)     // Catch:{ IOException -> 0x0033, all -> 0x002f }
            goto L_0x0035
        L_0x002f:
            r0 = move-exception
            r1 = r4
            r4 = r0
            goto L_0x0056
        L_0x0033:
            r0 = move-exception
            goto L_0x0047
        L_0x0035:
            if (r4 == 0) goto L_0x003a
            r4.close()     // Catch:{ Exception -> 0x003a }
        L_0x003a:
            r5.close()     // Catch:{ Exception -> 0x003d }
        L_0x003d:
            return r1
        L_0x003e:
            r5 = move-exception
            r3 = r1
            r1 = r4
            r4 = r5
            r5 = r3
            goto L_0x0056
        L_0x0044:
            r5 = move-exception
            r0 = r5
            r5 = r1
        L_0x0047:
            r1 = r4
            goto L_0x004f
        L_0x0049:
            r4 = move-exception
            r5 = r1
            goto L_0x0056
        L_0x004c:
            r4 = move-exception
            r0 = r4
            r5 = r1
        L_0x004f:
            com.sina.weibo.sdk.exception.WeiboException r4 = new com.sina.weibo.sdk.exception.WeiboException     // Catch:{ all -> 0x0055 }
            r4.<init>(r0)     // Catch:{ all -> 0x0055 }
            throw r4     // Catch:{ all -> 0x0055 }
        L_0x0055:
            r4 = move-exception
        L_0x0056:
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ Exception -> 0x005b }
        L_0x005b:
            if (r5 == 0) goto L_0x0060
            r5.close()     // Catch:{ Exception -> 0x0060 }
        L_0x0060:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.net.HttpManager.readConnectResponse(java.net.HttpURLConnection, boolean):java.lang.String");
    }

    private static void setHttpCommonParam(Context context, WeiboParameters weiboParameters) {
        String str = "";
        if (!TextUtils.isEmpty(weiboParameters.getAppKey())) {
            str = Utility.getAid(context, weiboParameters.getAppKey());
            if (!TextUtils.isEmpty(str)) {
                weiboParameters.put((String) "aid", str);
            }
        }
        String timestamp = getTimestamp();
        weiboParameters.put((String) "oauth_timestamp", timestamp);
        String str2 = "";
        Object obj = weiboParameters.get("access_token");
        Object obj2 = weiboParameters.get(Oauth2AccessToken.KEY_REFRESH_TOKEN);
        Object obj3 = weiboParameters.get("phone");
        if (obj != null && (obj instanceof String)) {
            str2 = (String) obj;
        } else if (obj2 != null && (obj2 instanceof String)) {
            str2 = (String) obj2;
        } else if (obj3 != null && (obj3 instanceof String)) {
            str2 = (String) obj3;
        }
        weiboParameters.put((String) "oauth_sign", getOauthSign(context, str, str2, weiboParameters.getAppKey(), timestamp));
    }

    public static String openRedirectUrl4LocationUri(Context context, String str, String str2, WeiboParameters weiboParameters) {
        HttpURLConnection httpURLConnection;
        if (str2.equals("GET")) {
            if (str.endsWith("?")) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(weiboParameters.encodeUrl());
                str = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("?");
                sb2.append(weiboParameters.encodeUrl());
                str = sb2.toString();
            }
            httpURLConnection = ConnectionFactory.createConnect(str, context);
        } else {
            httpURLConnection = ConnectionFactory.createConnect(str, context);
        }
        try {
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 302) {
                if (responseCode != 301) {
                    if (responseCode == 200) {
                        return str;
                    }
                    return "";
                }
            }
            return httpURLConnection.getHeaderField("Location");
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(19:17|18|19|20|21|22|23|(1:25)(1:26)|27|(2:29|(1:31)(2:32|33))(1:34)|35|(2:36|(1:38)(1:56))|39|(2:41|(1:43)(3:44|45|46))|47|48|49|51|52) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x0054 */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005c A[Catch:{ Exception -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0061 A[Catch:{ Exception -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007c A[Catch:{ Exception -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0091 A[Catch:{ Exception -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00ab A[Catch:{ Exception -> 0x00cf }, LOOP:0: B:36:0x00a4->B:38:0x00ab, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ba A[Catch:{ Exception -> 0x00cf }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b0 A[EDGE_INSN: B:56:0x00b0->B:39:0x00b0 ?: BREAK  
    EDGE_INSN: B:56:0x00b0->B:39:0x00b0 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String downloadFile(android.content.Context r8, java.lang.String r9, java.lang.String r10, java.lang.String r11) throws com.sina.weibo.sdk.exception.WeiboException {
        /*
            java.lang.Class<com.sina.weibo.sdk.net.HttpManager> r0 = com.sina.weibo.sdk.net.HttpManager.class
            monitor-enter(r0)
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00d3 }
            r1.<init>(r10)     // Catch:{ all -> 0x00d3 }
            boolean r2 = r1.exists()     // Catch:{ all -> 0x00d3 }
            if (r2 != 0) goto L_0x0011
            r1.mkdirs()     // Catch:{ all -> 0x00d3 }
        L_0x0011:
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x00d3 }
            r2.<init>(r1, r11)     // Catch:{ all -> 0x00d3 }
            boolean r1 = r2.exists()     // Catch:{ all -> 0x00d3 }
            if (r1 == 0) goto L_0x0022
            java.lang.String r8 = r2.getPath()     // Catch:{ all -> 0x00d3 }
            monitor-exit(r0)
            return r8
        L_0x0022:
            boolean r1 = android.webkit.URLUtil.isValidUrl(r9)     // Catch:{ all -> 0x00d3 }
            if (r1 != 0) goto L_0x002c
            java.lang.String r8 = ""
            monitor-exit(r0)
            return r8
        L_0x002c:
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00d3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d3 }
            r3.<init>()     // Catch:{ all -> 0x00d3 }
            r3.append(r11)     // Catch:{ all -> 0x00d3 }
            java.lang.String r11 = "_temp"
            r3.append(r11)     // Catch:{ all -> 0x00d3 }
            java.lang.String r11 = r3.toString()     // Catch:{ all -> 0x00d3 }
            r1.<init>(r10, r11)     // Catch:{ all -> 0x00d3 }
            java.net.HttpURLConnection r8 = com.sina.weibo.sdk.net.ConnectionFactory.createConnect(r9, r8)     // Catch:{ all -> 0x00d3 }
            r9 = 300000(0x493e0, float:4.2039E-40)
            r8.setConnectTimeout(r9)     // Catch:{ all -> 0x00d3 }
            r8.setReadTimeout(r9)     // Catch:{ all -> 0x00d3 }
            java.lang.String r9 = "GET"
            r8.setRequestMethod(r9)     // Catch:{ Exception -> 0x0054 }
        L_0x0054:
            boolean r9 = r1.exists()     // Catch:{ Exception -> 0x00cf }
            r10 = 0
            if (r9 == 0) goto L_0x0061
            long r3 = r1.length()     // Catch:{ Exception -> 0x00cf }
            goto L_0x0065
        L_0x0061:
            r1.createNewFile()     // Catch:{ Exception -> 0x00cf }
            r3 = r10
        L_0x0065:
            java.lang.String r9 = "RANGE"
            java.lang.String r5 = "bytes="
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r3 = r5.concat(r3)     // Catch:{ Exception -> 0x00cf }
            r8.setRequestProperty(r9, r3)     // Catch:{ Exception -> 0x00cf }
            int r9 = r8.getResponseCode()     // Catch:{ Exception -> 0x00cf }
            r3 = 206(0xce, float:2.89E-43)
            if (r9 == r3) goto L_0x0091
            r3 = 200(0xc8, float:2.8E-43)
            if (r9 != r3) goto L_0x0086
            int r9 = r8.getContentLength()     // Catch:{ Exception -> 0x00cf }
            long r3 = (long) r9     // Catch:{ Exception -> 0x00cf }
            goto L_0x0092
        L_0x0086:
            r10 = 1
            java.lang.String r8 = readConnectResponse(r8, r10)     // Catch:{ Exception -> 0x00cf }
            com.sina.weibo.sdk.exception.WeiboHttpException r10 = new com.sina.weibo.sdk.exception.WeiboHttpException     // Catch:{ Exception -> 0x00cf }
            r10.<init>(r8, r9)     // Catch:{ Exception -> 0x00cf }
            throw r10     // Catch:{ Exception -> 0x00cf }
        L_0x0091:
            r3 = r10
        L_0x0092:
            java.io.InputStream r8 = r8.getInputStream()     // Catch:{ Exception -> 0x00cf }
            java.io.RandomAccessFile r9 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x00cf }
            java.lang.String r5 = "rw"
            r9.<init>(r1, r5)     // Catch:{ Exception -> 0x00cf }
            r9.seek(r10)     // Catch:{ Exception -> 0x00cf }
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x00cf }
        L_0x00a4:
            int r6 = r8.read(r5)     // Catch:{ Exception -> 0x00cf }
            r7 = -1
            if (r6 == r7) goto L_0x00b0
            r7 = 0
            r9.write(r5, r7, r6)     // Catch:{ Exception -> 0x00cf }
            goto L_0x00a4
        L_0x00b0:
            r9.close()     // Catch:{ Exception -> 0x00cf }
            r8.close()     // Catch:{ Exception -> 0x00cf }
            int r8 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r8 == 0) goto L_0x00cc
            long r8 = r1.length()     // Catch:{ Exception -> 0x00cf }
            int r8 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r8 >= 0) goto L_0x00c3
            goto L_0x00cc
        L_0x00c3:
            r1.renameTo(r2)     // Catch:{ Exception -> 0x00cf }
            java.lang.String r8 = r2.getPath()     // Catch:{ Exception -> 0x00cf }
            monitor-exit(r0)
            return r8
        L_0x00cc:
            r1.delete()     // Catch:{ Exception -> 0x00cf }
        L_0x00cf:
            java.lang.String r8 = ""
            monitor-exit(r0)
            return r8
        L_0x00d3:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.net.HttpManager.downloadFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    public static void buildParams(OutputStream outputStream, WeiboParameters weiboParameters) throws WeiboException {
        try {
            Set<String> keySet = weiboParameters.keySet();
            for (String next : keySet) {
                if (weiboParameters.get(next) instanceof String) {
                    StringBuilder sb = new StringBuilder(100);
                    sb.setLength(0);
                    sb.append(MP_BOUNDARY);
                    sb.append(MultipartUtility.LINE_FEED);
                    sb.append("content-disposition: form-data; name=\"");
                    sb.append(next);
                    sb.append("\"\r\n\r\n");
                    sb.append(weiboParameters.get(next));
                    sb.append(MultipartUtility.LINE_FEED);
                    outputStream.write(sb.toString().getBytes());
                }
            }
            for (String next2 : keySet) {
                Object obj = weiboParameters.get(next2);
                if (obj instanceof Bitmap) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(MP_BOUNDARY);
                    sb2.append(MultipartUtility.LINE_FEED);
                    sb2.append("content-disposition: form-data; name=\"");
                    sb2.append(next2);
                    sb2.append("\"; filename=\"file\"\r\n");
                    sb2.append("Content-Type: application/octet-stream; charset=utf-8\r\n\r\n");
                    outputStream.write(sb2.toString().getBytes());
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ((Bitmap) obj).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
                    outputStream.write(byteArrayOutputStream.toByteArray());
                    outputStream.write(MultipartUtility.LINE_FEED.getBytes());
                } else if (obj instanceof ByteArrayOutputStream) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(MP_BOUNDARY);
                    sb3.append(MultipartUtility.LINE_FEED);
                    sb3.append("content-disposition: form-data; name=\"");
                    sb3.append(next2);
                    sb3.append("\"; filename=\"file\"\r\n");
                    sb3.append("Content-Type: application/octet-stream; charset=utf-8\r\n\r\n");
                    outputStream.write(sb3.toString().getBytes());
                    ByteArrayOutputStream byteArrayOutputStream2 = (ByteArrayOutputStream) obj;
                    outputStream.write(byteArrayOutputStream2.toByteArray());
                    outputStream.write(MultipartUtility.LINE_FEED.getBytes());
                    byteArrayOutputStream2.close();
                }
            }
            StringBuilder sb4 = new StringBuilder(MultipartUtility.LINE_FEED);
            sb4.append(END_MP_BOUNDARY);
            outputStream.write(sb4.toString().getBytes());
        } catch (IOException e) {
            throw new WeiboException((Throwable) e);
        }
    }

    public static String getBoundry() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i < 12; i++) {
            long currentTimeMillis = System.currentTimeMillis() + ((long) i);
            long j = currentTimeMillis % 3;
            if (j == 0) {
                stringBuffer.append(((char) ((int) currentTimeMillis)) % 9);
            } else if (j == 1) {
                stringBuffer.append((char) ((int) ((currentTimeMillis % 26) + 65)));
            } else {
                stringBuffer.append((char) ((int) ((currentTimeMillis % 26) + 97)));
            }
        }
        return stringBuffer.toString();
    }

    private static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String getOauthSign(Context context, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder("");
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            sb.append(str2);
        }
        if (!TextUtils.isEmpty(str3)) {
            sb.append(str3);
        }
        return calcOauthSignNative(context, sb.toString(), str4);
    }
}
