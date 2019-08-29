package com.tencent.open.utils;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.sdk.util.h;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.taobao.agoo.control.data.BaseDO;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.b.a;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.uc.webview.export.extension.UCCore;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class Util {
    private static String a = "";
    private static String b = "";
    private static String c = "";
    private static String d = "";
    private static int e = -1;
    private static String f = null;
    private static String g = "0123456789ABCDEF";

    /* compiled from: ProGuard */
    public static class Statistic {
        public long reqSize;
        public String response;
        public long rspSize;

        public Statistic(String str, int i) {
            this.response = str;
            this.reqSize = (long) i;
            if (this.response != null) {
                this.rspSize = (long) this.response.length();
            }
        }
    }

    /* compiled from: ProGuard */
    static class TBufferedOutputStream extends BufferedOutputStream {
        private int a = 0;

        public TBufferedOutputStream(OutputStream outputStream) {
            super(outputStream);
        }

        public void write(byte[] bArr) throws IOException {
            super.write(bArr);
            this.a += bArr.length;
        }

        public int getLength() {
            return this.a;
        }
    }

    private static char a(int i) {
        int i2 = i & 15;
        return i2 < 10 ? (char) (i2 + 48) : (char) ((i2 - 10) + 97);
    }

    public static String encodePostBody(Bundle bundle, String str) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int size = bundle.size();
        for (String str2 : bundle.keySet()) {
            i++;
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                StringBuilder sb2 = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb2.append(str2);
                sb2.append("\"\r\n\r\n");
                sb2.append((String) obj);
                sb.append(sb2.toString());
                if (i < size - 1) {
                    StringBuilder sb3 = new StringBuilder("\r\n--");
                    sb3.append(str);
                    sb3.append(MultipartUtility.LINE_FEED);
                    sb.append(sb3.toString());
                }
            }
        }
        return sb.toString();
    }

    public static String encodeUrl(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if ((obj instanceof String) || (obj instanceof String[])) {
                if (obj instanceof String[]) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append("&");
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(URLEncoder.encode(str));
                    sb2.append("=");
                    sb.append(sb2.toString());
                    String[] stringArray = bundle.getStringArray(str);
                    if (stringArray != null) {
                        for (int i = 0; i < stringArray.length; i++) {
                            if (i == 0) {
                                sb.append(URLEncoder.encode(stringArray[i]));
                            } else {
                                StringBuilder sb3 = new StringBuilder(",");
                                sb3.append(stringArray[i]);
                                sb.append(URLEncoder.encode(sb3.toString()));
                            }
                        }
                    }
                } else {
                    if (z) {
                        z = false;
                    } else {
                        sb.append("&");
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(URLEncoder.encode(str));
                    sb4.append("=");
                    sb4.append(URLEncoder.encode(bundle.getString(str)));
                    sb.append(sb4.toString());
                }
            }
        }
        return sb.toString();
    }

    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str == null) {
            return bundle;
        }
        try {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (split2.length == 2) {
                    bundle.putString(URLDecoder.decode(split2[0]), URLDecoder.decode(split2[1]));
                }
            }
            return bundle;
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONObject decodeUrlToJson(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                if (split2.length == 2) {
                    try {
                        jSONObject.put(URLDecoder.decode(split2[0]), URLDecoder.decode(split2[1]));
                    } catch (JSONException e2) {
                        StringBuilder sb = new StringBuilder("decodeUrlToJson has exception: ");
                        sb.append(e2.getMessage());
                        f.e("openSDK_LOG.Util", sb.toString());
                    }
                }
            }
        }
        return jSONObject;
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str.replace("auth://", AjxHttpLoader.DOMAIN_HTTP));
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (MalformedURLException unused) {
            return new Bundle();
        }
    }

    public static JSONObject parseUrlToJson(String str) {
        try {
            URL url = new URL(str.replace("auth://", AjxHttpLoader.DOMAIN_HTTP));
            JSONObject decodeUrlToJson = decodeUrlToJson(null, url.getQuery());
            decodeUrlToJson(decodeUrlToJson, url.getRef());
            return decodeUrlToJson;
        } catch (MalformedURLException unused) {
            return new JSONObject();
        }
    }

    public static Statistic upload(Context context, String str, Bundle bundle) throws MalformedURLException, IOException, NetworkUnavailableException, HttpStatusException {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                    throw new NetworkUnavailableException(NetworkUnavailableException.ERROR_INFO);
                }
            }
        }
        Bundle bundle2 = new Bundle(bundle);
        String string = bundle2.getString("appid_for_getting_config");
        bundle2.remove("appid_for_getting_config");
        HttpClient httpClient = HttpUtils.getHttpClient(context, string, str);
        HttpPost httpPost = new HttpPost(str);
        Bundle bundle3 = new Bundle();
        for (String str2 : bundle2.keySet()) {
            Object obj = bundle2.get(str2);
            if (obj instanceof byte[]) {
                bundle3.putByteArray(str2, (byte[]) obj);
            }
        }
        httpPost.setHeader("Content-Type", "multipart/form-data; boundary=3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f");
        httpPost.setHeader(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(getBytesUTF8("--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
        byteArrayOutputStream.write(getBytesUTF8(encodePostBody(bundle2, "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f")));
        if (!bundle3.isEmpty()) {
            int size = bundle3.size();
            int i = -1;
            byteArrayOutputStream.write(getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
            for (String str3 : bundle3.keySet()) {
                i++;
                StringBuilder sb = new StringBuilder("Content-Disposition: form-data; name=\"");
                sb.append(str3);
                sb.append("\"; filename=\"value.file\"\r\n");
                byteArrayOutputStream.write(getBytesUTF8(sb.toString()));
                byteArrayOutputStream.write(getBytesUTF8("Content-Type: application/octet-stream\r\n\r\n"));
                byte[] byteArray = bundle3.getByteArray(str3);
                if (byteArray != null) {
                    byteArrayOutputStream.write(byteArray);
                }
                if (i < size - 1) {
                    byteArrayOutputStream.write(getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f\r\n"));
                }
            }
        }
        byteArrayOutputStream.write(getBytesUTF8("\r\n--3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f--\r\n"));
        byte[] byteArray2 = byteArrayOutputStream.toByteArray();
        int length = byteArray2.length + 0;
        byteArrayOutputStream.close();
        httpPost.setEntity(new ByteArrayEntity(byteArray2));
        HttpResponse execute = httpClient.execute(httpPost);
        int statusCode = execute.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return new Statistic(a(execute), length);
        }
        throw new HttpStatusException(HttpStatusException.ERROR_INFO.concat(String.valueOf(statusCode)));
    }

    private static String a(HttpResponse httpResponse) throws IllegalStateException, IOException {
        InputStream content = httpResponse.getEntity().getContent();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Header firstHeader = httpResponse.getFirstHeader(TransportConstants.KEY_X_CONTENT_ENCODING);
        InputStream gZIPInputStream = (firstHeader == null || firstHeader.getValue().toLowerCase().indexOf("gzip") < 0) ? content : new GZIPInputStream(content);
        byte[] bArr = new byte[512];
        while (true) {
            int read = gZIPInputStream.read(bArr);
            if (read == -1) {
                return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static JSONObject parseJson(String str) throws JSONException {
        if (str.equals("false")) {
            str = "{value : false}";
        }
        if (str.equals("true")) {
            str = "{value : true}";
        }
        if (str.contains("allback(")) {
            str = str.replaceFirst("[\\s\\S]*allback\\(([\\s\\S]*)\\);[^\\)]*\\z", "$1").trim();
        }
        if (str.contains("online[0]=")) {
            StringBuilder sb = new StringBuilder("{online:");
            sb.append(str.charAt(str.length() - 2));
            sb.append(h.d);
            str = sb.toString();
        }
        return new JSONObject(str);
    }

    public static void showAlert(Context context, String str, String str2) {
        Builder builder = new Builder(context);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.create().show();
    }

    public static String getUserIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces != null && networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (true) {
                    if (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress()) {
                            return nextElement.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (SocketException e2) {
            f.a("openSDK_LOG.Util", "getUserIp SocketException ", e2);
        }
        return "";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private static boolean a(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mtt", 64);
            String str = packageInfo.versionName;
            if (SystemUtils.compareVersion(str, "4.3") >= 0 && !str.startsWith("4.4")) {
                Signature[] signatureArr = packageInfo.signatures;
                if (signatureArr != null) {
                    try {
                        MessageDigest instance = MessageDigest.getInstance("MD5");
                        instance.update(signatureArr[0].toByteArray());
                        String hexString = toHexString(instance.digest());
                        instance.reset();
                        if (hexString.equals("d8391a394d4a179e6fe7bdb8a301258b")) {
                            return true;
                        }
                    } catch (NoSuchAlgorithmException e2) {
                        StringBuilder sb = new StringBuilder("isQQBrowerAvailable has exception: ");
                        sb.append(e2.getMessage());
                        f.e("openSDK_LOG.Util", sb.toString());
                    }
                }
            }
            return false;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x001a  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean openBrowser(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            boolean r1 = a(r4)     // Catch:{ Exception -> 0x0017 }
            if (r1 == 0) goto L_0x000f
            java.lang.String r2 = "com.tencent.mtt"
            java.lang.String r3 = "com.tencent.mtt.MainActivity"
            a(r4, r2, r3, r5)     // Catch:{ Exception -> 0x0018 }
            goto L_0x0042
        L_0x000f:
            java.lang.String r2 = "com.android.browser"
            java.lang.String r3 = "com.android.browser.BrowserActivity"
            a(r4, r2, r3, r5)     // Catch:{ Exception -> 0x0018 }
            goto L_0x0042
        L_0x0017:
            r1 = 0
        L_0x0018:
            if (r1 == 0) goto L_0x0033
            java.lang.String r1 = "com.android.browser"
            java.lang.String r2 = "com.android.browser.BrowserActivity"
            a(r4, r1, r2, r5)     // Catch:{ Exception -> 0x0022 }
            goto L_0x0042
        L_0x0022:
            java.lang.String r1 = "com.google.android.browser"
            java.lang.String r2 = "com.android.browser.BrowserActivity"
            a(r4, r1, r2, r5)     // Catch:{ Exception -> 0x002a }
            goto L_0x0042
        L_0x002a:
            java.lang.String r1 = "com.android.chrome"
            java.lang.String r2 = "com.google.android.apps.chrome.Main"
            a(r4, r1, r2, r5)     // Catch:{ Exception -> 0x0032 }
            goto L_0x0042
        L_0x0032:
            return r0
        L_0x0033:
            java.lang.String r1 = "com.google.android.browser"
            java.lang.String r2 = "com.android.browser.BrowserActivity"
            a(r4, r1, r2, r5)     // Catch:{ Exception -> 0x003b }
            goto L_0x0042
        L_0x003b:
            java.lang.String r1 = "com.android.chrome"
            java.lang.String r2 = "com.google.android.apps.chrome.Main"
            a(r4, r1, r2, r5)     // Catch:{ Exception -> 0x0044 }
        L_0x0042:
            r4 = 1
            return r4
        L_0x0044:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.Util.openBrowser(android.content.Context, java.lang.String):boolean");
    }

    private static void a(Context context, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(UCCore.VERIFY_POLICY_QUICK);
        intent.addFlags(268435456);
        intent.setData(Uri.parse(str3));
        context.startActivity(intent);
    }

    public static boolean isMobileQQSupportShare(Context context) {
        try {
            if (SystemUtils.compareVersion(context.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0).versionName, "4.1") >= 0) {
                return true;
            }
            return false;
        } catch (NameNotFoundException e2) {
            f.b("openSDK_LOG.Util", "NameNotFoundException", e2);
            return false;
        }
    }

    public static String encrypt(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(getBytesUTF8(str));
            byte[] digest = instance.digest();
            if (digest == null) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            for (byte b2 : digest) {
                sb.append(a(b2 >>> 4));
                sb.append(a((int) b2));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            StringBuilder sb2 = new StringBuilder("encrypt has exception: ");
            sb2.append(e2.getMessage());
            f.e("openSDK_LOG.Util", sb2.toString());
            return str;
        }
    }

    public static void reportBernoulli(final Context context, String str, long j, String str2) {
        final Bundle bundle = new Bundle();
        bundle.putString("appid_for_getting_config", str2);
        bundle.putString("strValue", str2);
        bundle.putString("nValue", str);
        bundle.putString("qver", Constants.SDK_VERSION);
        if (j != 0) {
            bundle.putLong("elt", j);
        }
        new Thread() {
            public final void run() {
                try {
                    HttpUtils.openUrl2(context, "http://cgi.qplus.com/report/report", "GET", bundle);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("reportBernoulli has exception: ");
                    sb.append(e.getMessage());
                    f.e("openSDK_LOG.Util", sb.toString());
                }
            }
        }.start();
    }

    public static boolean hasSDCard() {
        return (Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory() : null) != null;
    }

    public static String toHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            String num = Integer.toString(b2 & 255, 16);
            if (num.length() == 1) {
                num = "0".concat(String.valueOf(num));
            }
            sb.append(num);
        }
        return sb.toString();
    }

    public static String toHexString(String str) {
        byte[] bytesUTF8 = getBytesUTF8(str);
        StringBuilder sb = new StringBuilder(bytesUTF8.length * 2);
        for (int i = 0; i < bytesUTF8.length; i++) {
            sb.append(g.charAt((bytesUTF8[i] & 240) >> 4));
            sb.append(g.charAt((bytesUTF8[i] & 15) >> 0));
        }
        return sb.toString();
    }

    public static String hexToString(String str) {
        if ("0x".equals(str.substring(0, 2))) {
            str = str.substring(2);
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            try {
                bArr[i] = (byte) (Integer.parseInt(str.substring(i2, i2 + 2), 16) & 255);
            } catch (Exception e2) {
                StringBuilder sb = new StringBuilder("hexToString has exception: ");
                sb.append(e2.getMessage());
                f.e("openSDK_LOG.Util", sb.toString());
            }
        }
        try {
            return new String(bArr, "utf-8");
        } catch (Exception e3) {
            StringBuilder sb2 = new StringBuilder("hexToString has exception: ");
            sb2.append(e3.getMessage());
            f.e("openSDK_LOG.Util", sb2.toString());
            return str;
        }
    }

    public static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e2) {
            StringBuilder sb = new StringBuilder("getAppVersion error");
            sb.append(e2.getMessage());
            f.e("openSDK_LOG.Util", sb.toString());
            return "";
        }
    }

    public static final String getApplicationLable(Context context) {
        if (context != null) {
            CharSequence applicationLabel = context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
            if (applicationLabel != null) {
                return applicationLabel.toString();
            }
        }
        return null;
    }

    public static final boolean isValidUrl(String str) {
        if (str == null) {
            return false;
        }
        return str.startsWith(AjxHttpLoader.DOMAIN_HTTP) || str.startsWith(AjxHttpLoader.DOMAIN_HTTPS);
    }

    public static final boolean isValidPath(String str) {
        if (str != null && new File(str).exists()) {
            return true;
        }
        return false;
    }

    public static boolean fileExists(String str) {
        if (str != null && new File(str).exists()) {
            return true;
        }
        return false;
    }

    public static final String subString(String str, int i, String str2, String str3) {
        String str4;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "UTF-8";
        }
        try {
            if (str.getBytes(str2).length <= i) {
                return str;
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < str.length()) {
                int i4 = i2 + 1;
                i3 += str.substring(i2, i4).getBytes(str2).length;
                if (i3 > i) {
                    String substring = str.substring(0, i2);
                    try {
                        if (!TextUtils.isEmpty(str3)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(substring);
                            sb.append(str3);
                            str4 = sb.toString();
                        } else {
                            str4 = substring;
                        }
                        return str4;
                    } catch (Exception e2) {
                        String str5 = substring;
                        e = e2;
                        str = str5;
                        PrintStream printStream = System.out;
                        new StringBuilder("StructMsg sSubString error : ").append(e.getMessage());
                        return str;
                    }
                } else {
                    i2 = i4;
                }
            }
            return str;
        } catch (Exception e3) {
            e = e3;
            PrintStream printStream2 = System.out;
            new StringBuilder("StructMsg sSubString error : ").append(e.getMessage());
            return str;
        }
    }

    public static int parseIntValue(String str) {
        return parseIntValue(str, 0);
    }

    public static int parseIntValue(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public static boolean checkNetWork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return true;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo != null) {
            for (NetworkInfo isConnectedOrConnecting : allNetworkInfo) {
                if (isConnectedOrConnecting.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Bundle composeViaReportParams(String str, String str2, String str3, String str4, String str5, String str6) {
        return composeViaReportParams(str, str3, str4, str2, str5, str6, "", "", "", "", "", "");
    }

    public static Bundle composeViaReportParams(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12) {
        Bundle bundle = new Bundle();
        bundle.putString("openid", str);
        bundle.putString("report_type", str2);
        bundle.putString("act_type", str3);
        bundle.putString("via", str4);
        bundle.putString("app_id", str5);
        bundle.putString("result", str6);
        bundle.putString("type", str7);
        bundle.putString("login_status", str8);
        bundle.putString("need_user_auth", str9);
        bundle.putString("to_uin", str10);
        bundle.putString("call_source", str11);
        bundle.putString("to_type", str12);
        return bundle;
    }

    public static Bundle composeHaboCgiReportParams(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        Bundle bundle = new Bundle();
        bundle.putString("platform", "1");
        bundle.putString("result", str);
        bundle.putString("code", str2);
        bundle.putString("tmcost", str3);
        bundle.putString("rate", str4);
        bundle.putString(BaseDO.JSON_CMD, str5);
        bundle.putString("uin", str6);
        bundle.putString("appid", str7);
        bundle.putString("share_type", str8);
        bundle.putString("detail", str9);
        bundle.putString("os_ver", VERSION.RELEASE);
        bundle.putString("network", a.a(Global.getContext()));
        bundle.putString(CommonUtils.APN_PROP_APN, a.b(Global.getContext()));
        bundle.putString("model_name", Build.MODEL);
        bundle.putString("sdk_ver", Constants.SDK_VERSION);
        bundle.putString("packagename", Global.getPackageName());
        bundle.putString("app_ver", getAppVersionName(Global.getContext(), Global.getPackageName()));
        return bundle;
    }

    public static String getLocation(Context context) {
        if (context == null) {
            return "";
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            Criteria criteria = new Criteria();
            criteria.setCostAllowed(false);
            criteria.setAccuracy(2);
            String bestProvider = locationManager.getBestProvider(criteria, true);
            if (bestProvider != null) {
                Location lastKnownLocation = locationManager.getLastKnownLocation(bestProvider);
                if (lastKnownLocation == null) {
                    return "";
                }
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();
                StringBuilder sb = new StringBuilder();
                sb.append(latitude);
                sb.append("*");
                sb.append(longitude);
                String sb2 = sb.toString();
                f = sb2;
                return sb2;
            }
        } catch (Exception e2) {
            f.b("openSDK_LOG.Util", "getLocation>>>", e2);
        }
        return "";
    }

    public static void getPackageInfo(Context context, String str) {
        if (context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 0);
                String str2 = packageInfo.versionName;
                b = str2;
                a = str2.substring(0, b.lastIndexOf(46));
                String str3 = b;
                d = str3.substring(str3.lastIndexOf(46) + 1, b.length());
                e = packageInfo.versionCode;
            } catch (NameNotFoundException e2) {
                StringBuilder sb = new StringBuilder("getPackageInfo has exception: ");
                sb.append(e2.getMessage());
                f.e("openSDK_LOG.Util", sb.toString());
            } catch (Exception e3) {
                StringBuilder sb2 = new StringBuilder("getPackageInfo has exception: ");
                sb2.append(e3.getMessage());
                f.e("openSDK_LOG.Util", sb2.toString());
            }
        }
    }

    public static String getVersionName(Context context, String str) {
        if (context == null) {
            return "";
        }
        getPackageInfo(context, str);
        return b;
    }

    public static String getAppVersionName(Context context, String str) {
        if (context == null) {
            return "";
        }
        getPackageInfo(context, str);
        return a;
    }

    public static String getQUA3(Context context, String str) {
        if (context == null) {
            return "";
        }
        String appVersionName = getAppVersionName(context, str);
        c = appVersionName;
        return appVersionName;
    }

    public static byte[] getBytesUTF8(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public static boolean isTablet(Context context) {
        double d2;
        boolean z;
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            d2 = Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d));
        } catch (Throwable unused) {
            d2 = 0.0d;
        }
        try {
            if (((TelephonyManager) context.getSystemService("phone")).getPhoneType() == 0) {
                z = false;
                return d2 <= 6.5d && !z;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        z = true;
        if (d2 <= 6.5d) {
        }
    }
}
