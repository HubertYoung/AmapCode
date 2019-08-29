package com.sina.weibo.sdk.statistic;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.android.phone.a.a.a;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.zip.GZIPOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

class LogReport {
    private static final int CONNECTION_TIMEOUT = 25000;
    private static final String PRIVATE_CODE = "dqwef1864il4c9m6";
    private static final int SOCKET_TIMEOUT = 20000;
    private static String UPLOADTIME = "uploadtime";
    private static String mAid = null;
    private static String mAppkey = null;
    private static String mBaseUrl = "https://api.weibo.com/2/proxy/sdk/statistic.json";
    private static String mChannel;
    private static String mKeyHash;
    public static LogReport mLogReport;
    private static String mPackageName;
    private static JSONObject mParams;
    private static String mVersionName;

    public LogReport(Context context) {
        try {
            if (mPackageName == null) {
                mPackageName = context.getPackageName();
            }
            mAppkey = StatisticConfig.getAppkey(context);
            checkAid(context);
            mKeyHash = Utility.getSign(context, mPackageName);
            mVersionName = LogBuilder.getVersion(context);
            mChannel = StatisticConfig.getChannel(context);
        } catch (Exception e) {
            LogUtil.e(WBAgent.TAG, e.toString());
        }
        initCommonParams();
    }

    private static JSONObject initCommonParams() {
        if (mParams == null) {
            mParams = new JSONObject();
        }
        try {
            mParams.put("appkey", mAppkey);
            mParams.put("platform", a.a);
            mParams.put("packagename", mPackageName);
            mParams.put("key_hash", mKeyHash);
            mParams.put("version", mVersionName);
            mParams.put("channel", mChannel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mParams;
    }

    private static boolean checkAid(Context context) {
        if (TextUtils.isEmpty(mAid)) {
            mAid = Utility.getAid(context, mAppkey);
        }
        if (mParams == null) {
            mParams = new JSONObject();
        }
        try {
            mParams.put("aid", mAid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return !TextUtils.isEmpty(mAid);
    }

    public static void setPackageName(String str) {
        mPackageName = str;
    }

    public static String getPackageName() {
        return mPackageName;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00aa, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void uploadAppLogs(android.content.Context r8, java.lang.String r9) {
        /*
            java.lang.Class<com.sina.weibo.sdk.statistic.LogReport> r0 = com.sina.weibo.sdk.statistic.LogReport.class
            monitor-enter(r0)
            com.sina.weibo.sdk.statistic.LogReport r1 = mLogReport     // Catch:{ all -> 0x00ab }
            if (r1 != 0) goto L_0x000e
            com.sina.weibo.sdk.statistic.LogReport r1 = new com.sina.weibo.sdk.statistic.LogReport     // Catch:{ all -> 0x00ab }
            r1.<init>(r8)     // Catch:{ all -> 0x00ab }
            mLogReport = r1     // Catch:{ all -> 0x00ab }
        L_0x000e:
            boolean r1 = com.sina.weibo.sdk.net.NetStateManager.isNetworkConnected(r8)     // Catch:{ all -> 0x00ab }
            r2 = 1
            if (r1 != 0) goto L_0x0027
            java.lang.String r8 = "WBAgent"
            java.lang.String r1 = "network is not connected"
            com.sina.weibo.sdk.utils.LogUtil.i(r8, r1)     // Catch:{ all -> 0x00ab }
            java.lang.String r8 = "app_logs"
            java.lang.String r8 = com.sina.weibo.sdk.statistic.LogFileUtil.getAppLogPath(r8)     // Catch:{ all -> 0x00ab }
            com.sina.weibo.sdk.statistic.LogFileUtil.writeToFile(r8, r9, r2)     // Catch:{ all -> 0x00ab }
            monitor-exit(r0)
            return
        L_0x0027:
            java.util.List r9 = com.sina.weibo.sdk.statistic.LogBuilder.getValidUploadLogs(r9)     // Catch:{ all -> 0x00ab }
            if (r9 != 0) goto L_0x0036
            java.lang.String r8 = "WBAgent"
            java.lang.String r9 = "applogs is null"
            com.sina.weibo.sdk.utils.LogUtil.i(r8, r9)     // Catch:{ all -> 0x00ab }
            monitor-exit(r0)
            return
        L_0x0036:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x00ab }
            r1.<init>()     // Catch:{ all -> 0x00ab }
            boolean r3 = checkAid(r8)     // Catch:{ all -> 0x00ab }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x00ab }
        L_0x0043:
            boolean r4 = r9.hasNext()     // Catch:{ all -> 0x00ab }
            if (r4 == 0) goto L_0x0075
            java.lang.Object r4 = r9.next()     // Catch:{ all -> 0x00ab }
            org.json.JSONArray r4 = (org.json.JSONArray) r4     // Catch:{ all -> 0x00ab }
            r5 = 0
            if (r3 == 0) goto L_0x005c
            java.lang.String r5 = mBaseUrl     // Catch:{ all -> 0x00ab }
            java.lang.String r6 = "POST"
            org.json.JSONObject r7 = mParams     // Catch:{ all -> 0x00ab }
            boolean r5 = requestHttpExecute(r5, r6, r7, r4, r8)     // Catch:{ all -> 0x00ab }
        L_0x005c:
            if (r5 != 0) goto L_0x0069
            r1.add(r4)     // Catch:{ all -> 0x00ab }
            java.lang.String r4 = "WBAgent"
            java.lang.String r5 = "upload applogs error"
            com.sina.weibo.sdk.utils.LogUtil.e(r4, r5)     // Catch:{ all -> 0x00ab }
            goto L_0x0043
        L_0x0069:
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00ab }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x00ab }
            updateTime(r8, r4)     // Catch:{ all -> 0x00ab }
            goto L_0x0043
        L_0x0075:
            java.lang.String r8 = "app_logs"
            java.lang.String r8 = com.sina.weibo.sdk.statistic.LogFileUtil.getAppLogPath(r8)     // Catch:{ all -> 0x00ab }
            com.sina.weibo.sdk.statistic.LogFileUtil.delete(r8)     // Catch:{ all -> 0x00ab }
            int r8 = r1.size()     // Catch:{ all -> 0x00ab }
            if (r8 <= 0) goto L_0x00a9
            java.util.Iterator r8 = r1.iterator()     // Catch:{ all -> 0x00ab }
        L_0x0088:
            boolean r9 = r8.hasNext()     // Catch:{ all -> 0x00ab }
            if (r9 == 0) goto L_0x00a9
            java.lang.Object r9 = r8.next()     // Catch:{ all -> 0x00ab }
            org.json.JSONArray r9 = (org.json.JSONArray) r9     // Catch:{ all -> 0x00ab }
            java.lang.String r1 = "app_logs"
            java.lang.String r1 = com.sina.weibo.sdk.statistic.LogFileUtil.getAppLogPath(r1)     // Catch:{ all -> 0x00ab }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00ab }
            com.sina.weibo.sdk.statistic.LogFileUtil.writeToFile(r1, r9, r2)     // Catch:{ all -> 0x00ab }
            java.lang.String r9 = "WBAgent"
            java.lang.String r1 = "save failed_log"
            com.sina.weibo.sdk.utils.LogUtil.d(r9, r1)     // Catch:{ all -> 0x00ab }
            goto L_0x0088
        L_0x00a9:
            monitor-exit(r0)
            return
        L_0x00ab:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.statistic.LogReport.uploadAppLogs(android.content.Context, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00fe, code lost:
        if (r7 != null) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0107, code lost:
        if (r7 != null) goto L_0x0100;
     */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x010d A[SYNTHETIC, Splitter:B:53:0x010d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:47:0x0104=Splitter:B:47:0x0104, B:42:0x00fb=Splitter:B:42:0x00fb} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean requestHttpExecute(java.lang.String r6, java.lang.String r7, org.json.JSONObject r8, org.json.JSONArray r9, android.content.Context r10) {
        /*
            java.lang.String r7 = mAppkey
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            r0 = 0
            if (r7 == 0) goto L_0x0011
            java.lang.String r6 = "WBAgent"
            java.lang.String r7 = "unexpected null AppKey"
            com.sina.weibo.sdk.utils.LogUtil.e(r6, r7)
            return r0
        L_0x0011:
            r7 = 0
            if (r8 != 0) goto L_0x0022
            org.json.JSONObject r8 = initCommonParams()     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            goto L_0x0022
        L_0x0019:
            r6 = move-exception
            goto L_0x010b
        L_0x001c:
            r6 = move-exception
            goto L_0x00fb
        L_0x001f:
            r6 = move-exception
            goto L_0x0104
        L_0x0022:
            java.lang.String r1 = "time"
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x006f }
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 / r4
            r8.put(r1, r2)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r1 = "length"
            int r2 = r9.length()     // Catch:{ JSONException -> 0x006f }
            r8.put(r1, r2)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r1 = "sign"
            java.lang.String r2 = "aid"
            java.lang.String r2 = r8.getString(r2)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r3 = "appkey"
            java.lang.String r3 = r8.getString(r3)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r4 = "time"
            long r4 = r8.getLong(r4)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r2 = getSign(r2, r3, r4)     // Catch:{ JSONException -> 0x006f }
            r8.put(r1, r2)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r1 = "content"
            r8.put(r1, r9)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r9 = "WBAgent"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x006f }
            java.lang.String r2 = "post content--- "
            r1.<init>(r2)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r2 = r8.toString()     // Catch:{ JSONException -> 0x006f }
            r1.append(r2)     // Catch:{ JSONException -> 0x006f }
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x006f }
            com.sina.weibo.sdk.utils.LogUtil.d(r9, r1)     // Catch:{ JSONException -> 0x006f }
            goto L_0x0073
        L_0x006f:
            r9 = move-exception
            r9.printStackTrace()     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
        L_0x0073:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            r9.<init>()     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            r9.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            java.lang.String r6 = "?source="
            r9.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            java.lang.String r6 = mAppkey     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            r9.append(r6)     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            java.lang.String r6 = r9.toString()     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            java.net.HttpURLConnection r6 = com.sina.weibo.sdk.net.ConnectionFactory.createConnect(r6, r10)     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            java.io.ByteArrayOutputStream r9 = new java.io.ByteArrayOutputStream     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            r9.<init>()     // Catch:{ UnsupportedEncodingException -> 0x001f, IOException -> 0x001c }
            boolean r7 = com.sina.weibo.sdk.statistic.StatisticConfig.isNeedGizp()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            if (r7 == 0) goto L_0x00a4
            java.lang.String r7 = r8.toString()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            byte[] r7 = gzipLogs(r7)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r9.write(r7)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            goto L_0x00af
        L_0x00a4:
            java.lang.String r7 = r8.toString()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            byte[] r7 = r7.getBytes()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r9.write(r7)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
        L_0x00af:
            setPost(r6)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r6.connect()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            java.io.DataOutputStream r7 = new java.io.DataOutputStream     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            java.io.OutputStream r10 = r6.getOutputStream()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r7.<init>(r10)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            java.lang.String r8 = r8.toString()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            byte[] r8 = gzipLogs(r8)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r7.write(r8)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r7.flush()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r7.close()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            int r7 = r6.getResponseCode()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r8 = 200(0xc8, float:2.8E-43)
            if (r7 != r8) goto L_0x00df
            r6.getResponseMessage()     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r9.close()     // Catch:{ IOException -> 0x00dd }
        L_0x00dd:
            r6 = 1
            return r6
        L_0x00df:
            java.lang.String r6 = "WBAgent"
            java.lang.String r8 = "status code = "
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            java.lang.String r7 = r8.concat(r7)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            com.sina.weibo.sdk.utils.LogUtil.i(r6, r7)     // Catch:{ UnsupportedEncodingException -> 0x00f8, IOException -> 0x00f5, all -> 0x00f2 }
            r9.close()     // Catch:{ IOException -> 0x010a }
            goto L_0x010a
        L_0x00f2:
            r6 = move-exception
            r7 = r9
            goto L_0x010b
        L_0x00f5:
            r6 = move-exception
            r7 = r9
            goto L_0x00fb
        L_0x00f8:
            r6 = move-exception
            r7 = r9
            goto L_0x0104
        L_0x00fb:
            r6.printStackTrace()     // Catch:{ all -> 0x0019 }
            if (r7 == 0) goto L_0x010a
        L_0x0100:
            r7.close()     // Catch:{ IOException -> 0x010a }
            goto L_0x010a
        L_0x0104:
            r6.printStackTrace()     // Catch:{ all -> 0x0019 }
            if (r7 == 0) goto L_0x010a
            goto L_0x0100
        L_0x010a:
            return r0
        L_0x010b:
            if (r7 == 0) goto L_0x0110
            r7.close()     // Catch:{ IOException -> 0x0110 }
        L_0x0110:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.statistic.LogReport.requestHttpExecute(java.lang.String, java.lang.String, org.json.JSONObject, org.json.JSONArray, android.content.Context):boolean");
    }

    private static void setPost(HttpURLConnection httpURLConnection) {
        try {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty(H5AppHttpRequest.HEADER_CONNECTION, "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
        } catch (Exception unused) {
        }
    }

    private static String getSign(String str, String str2, long j) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        sb.append(str2);
        sb.append(PRIVATE_CODE);
        sb.append(j);
        String hexdigest = MD5.hexdigest(sb.toString());
        String substring = hexdigest.substring(hexdigest.length() - 6);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(substring);
        sb2.append(substring.substring(0, 4));
        String hexdigest2 = MD5.hexdigest(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(substring);
        sb3.append(hexdigest2.substring(hexdigest2.length() - 1));
        return sb3.toString();
    }

    private static byte[] gzipLogs(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bytes = str.getBytes("utf-8");
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bytes);
            gZIPOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static long getTime(Context context) {
        return context.getSharedPreferences(UPLOADTIME, 0).getLong("lasttime", 0);
    }

    private static void updateTime(Context context, Long l) {
        Editor edit = context.getSharedPreferences(UPLOADTIME, 0).edit();
        edit.putLong("lasttime", l.longValue());
        edit.commit();
    }
}
