package com.autonavi.indooroutdoordetectorsdk;

import android.os.Handler;
import android.util.Base64;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MapUtils;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GeoFenceHelper {
    static double a = 6378245.0d;
    static double ee = 0.006693421622965943d;
    static final StringBuffer mFlagsBuffer = new StringBuffer();
    static final StringBuffer mLogsBuffer = new StringBuffer();
    static double pi = 3.141592653589793d;

    static class Coord {
        float latitude;
        float longitude;

        Coord() {
            this.longitude = 0.0f;
            this.latitude = 0.0f;
        }

        Coord(float f, float f2) {
            this.longitude = f;
            this.latitude = f2;
        }

        /* access modifiers changed from: 0000 */
        public double distance(Coord coord) {
            return MapUtils.distance((double) this.longitude, (double) this.latitude, (double) coord.longitude, (double) coord.latitude);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            sb.append(this.longitude);
            sb.append(", ");
            sb.append(this.latitude);
            sb.append(")");
            return sb.toString();
        }
    }

    public static class TimeStatus {
        Object object;
        boolean status;
        long time;

        public TimeStatus() {
            reset();
        }

        public void reset() {
            this.time = 0;
            this.status = false;
        }

        public void changeStatus(boolean z) {
            this.time = System.currentTimeMillis();
            this.status = z;
        }

        /* access modifiers changed from: 0000 */
        public boolean isTimeout(long j) {
            return System.currentTimeMillis() - this.time > j;
        }

        public int elapsedTime() {
            return (int) (System.currentTimeMillis() - this.time);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(new Date(this.time));
            sb.append(" Status:");
            sb.append(this.status);
            return sb.toString();
        }
    }

    static boolean outOfChina(double d, double d2) {
        return d2 < 72.004d || d2 > 137.8347d || d < 0.8293d || d > 55.8271d;
    }

    public static double round(double d, int i) {
        int i2 = 1;
        for (int i3 = 0; i3 < i; i3++) {
            i2 *= 10;
        }
        double d2 = (double) i2;
        return ((double) ((int) (d * d2))) / d2;
    }

    GeoFenceHelper() {
    }

    static byte[] getMacBytes(String str) {
        byte[] bArr = new byte[6];
        String[] split = str.split("_");
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return bArr;
    }

    static byte getCheckData(ByteBuffer byteBuffer) {
        byte b = 0;
        for (int i = 0; i < byteBuffer.position(); i++) {
            b = (byte) (b ^ byteBuffer.get(i));
        }
        return b;
    }

    static int postRequest(String str, long j, byte[] bArr, Handler handler) {
        final byte[] bArr2;
        byte[] compressBody = BuildingLocateThread.compressBody(bArr);
        byte[] amapEncode = MapUtils.amapEncode(compressBody);
        if (amapEncode != null) {
            if (L.isLogging) {
                L.d((String) "Request encryptData is null");
            }
            bArr2 = amapEncode;
        } else {
            bArr2 = compressBody;
        }
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("Request ");
            sb.append(str);
            sb.append(", buffer.length=");
            sb.append(bArr2.length);
            L.d(sb.toString());
        }
        if (L.isLogging) {
            L.d("Request body".concat(String.valueOf(Base64.encodeToString(bArr2, 0))));
        }
        final long j2 = j;
        final String str2 = str;
        final Handler handler2 = handler;
        AnonymousClass1 r1 = new Thread("GeofenceNetwork") {
            /* JADX WARNING: Unknown top exception splitter block from list: {B:128:0x0374=Splitter:B:128:0x0374, B:152:0x03de=Splitter:B:152:0x03de} */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void run() {
                /*
                    r11 = this;
                    r0 = 212(0xd4, float:2.97E-43)
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x03e6 }
                    if (r1 == 0) goto L_0x002c
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x03e6 }
                    r1.<init>()     // Catch:{ Throwable -> 0x03e6 }
                    long r2 = r3     // Catch:{ Throwable -> 0x03e6 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x03e6 }
                    java.lang.String r2 = ", Request "
                    r1.append(r2)     // Catch:{ Throwable -> 0x03e6 }
                    java.lang.String r2 = r5     // Catch:{ Throwable -> 0x03e6 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x03e6 }
                    java.lang.String r2 = ", buffer.length="
                    r1.append(r2)     // Catch:{ Throwable -> 0x03e6 }
                    byte[] r2 = r6     // Catch:{ Throwable -> 0x03e6 }
                    int r2 = r2.length     // Catch:{ Throwable -> 0x03e6 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x03e6 }
                    java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x03e6 }
                    com.autonavi.indoor.util.L.d(r1)     // Catch:{ Throwable -> 0x03e6 }
                L_0x002c:
                    java.net.URL r1 = new java.net.URL     // Catch:{ Throwable -> 0x03e6 }
                    java.lang.String r2 = r5     // Catch:{ Throwable -> 0x03e6 }
                    r1.<init>(r2)     // Catch:{ Throwable -> 0x03e6 }
                    java.net.URLConnection r1 = r1.openConnection()     // Catch:{ Throwable -> 0x03e6 }
                    java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch:{ Throwable -> 0x03e6 }
                    r2 = 10000(0x2710, float:1.4013E-41)
                    r1.setReadTimeout(r2)     // Catch:{ Throwable -> 0x037a }
                    r1.setConnectTimeout(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = "POST"
                    r1.setRequestMethod(r2)     // Catch:{ Throwable -> 0x037a }
                    r2 = 1
                    r1.setDoOutput(r2)     // Catch:{ Throwable -> 0x037a }
                    r1.setDoInput(r2)     // Catch:{ Throwable -> 0x037a }
                    r3 = 0
                    r1.setUseCaches(r3)     // Catch:{ Throwable -> 0x037a }
                    r1.setInstanceFollowRedirects(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = "Accept-Language"
                    java.lang.String r5 = "zh-CN"
                    r1.setRequestProperty(r4, r5)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = "Charset"
                    java.lang.String r5 = "UTF-8"
                    r1.setRequestProperty(r4, r5)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = "Connection"
                    java.lang.String r5 = "Keep-Alive"
                    r1.setRequestProperty(r4, r5)     // Catch:{ Throwable -> 0x037a }
                    java.io.OutputStream r4 = r1.getOutputStream()     // Catch:{ Throwable -> 0x037a }
                    byte[] r5 = r6     // Catch:{ Throwable -> 0x037a }
                    r4.write(r5)     // Catch:{ Throwable -> 0x037a }
                    r4.close()     // Catch:{ Throwable -> 0x037a }
                    r1.connect()     // Catch:{ Throwable -> 0x037a }
                    int r4 = r1.getResponseCode()     // Catch:{ Throwable -> 0x037a }
                    r5 = 200(0xc8, float:2.8E-43)
                    r6 = 213(0xd5, float:2.98E-43)
                    if (r4 == r5) goto L_0x013d
                    r1.disconnect()     // Catch:{ Throwable -> 0x037a }
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Handler r3 = r7     // Catch:{ Throwable -> 0x037a }
                    long r7 = r3     // Catch:{ Throwable -> 0x037a }
                    java.lang.Long r5 = java.lang.Long.valueOf(r7)     // Catch:{ Throwable -> 0x037a }
                    android.os.Message r3 = r3.obtainMessage(r6, r5)     // Catch:{ Throwable -> 0x037a }
                    r2.sendMessage(r3)     // Catch:{ Throwable -> 0x037a }
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x00a9
                    java.lang.String r2 = "服务器建立连接失败，返回值错误code："
                    java.lang.String r3 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = r2.concat(r3)     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x00a9:
                    java.util.Map r2 = r1.getHeaderFields()     // Catch:{ Throwable -> 0x037a }
                    boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r3 == 0) goto L_0x00d0
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x037a }
                    r3.<init>()     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = r2.toString()     // Catch:{ Throwable -> 0x037a }
                    r3.append(r4)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = ", "
                    r3.append(r4)     // Catch:{ Throwable -> 0x037a }
                    int r4 = r2.size()     // Catch:{ Throwable -> 0x037a }
                    r3.append(r4)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r3)     // Catch:{ Throwable -> 0x037a }
                L_0x00d0:
                    java.util.Set r3 = r2.keySet()     // Catch:{ Throwable -> 0x037a }
                    java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x037a }
                L_0x00d8:
                    boolean r4 = r3.hasNext()     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0139
                    java.lang.Object r4 = r3.next()     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x037a }
                    java.lang.Object r5 = r2.get(r4)     // Catch:{ Throwable -> 0x037a }
                    java.util.List r5 = (java.util.List) r5     // Catch:{ Throwable -> 0x037a }
                    boolean r6 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r6 == 0) goto L_0x010d
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x037a }
                    r6.<init>()     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r7 = r5.toString()     // Catch:{ Throwable -> 0x037a }
                    r6.append(r7)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r7 = ", "
                    r6.append(r7)     // Catch:{ Throwable -> 0x037a }
                    int r7 = r5.size()     // Catch:{ Throwable -> 0x037a }
                    r6.append(r7)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r6)     // Catch:{ Throwable -> 0x037a }
                L_0x010d:
                    java.util.Iterator r5 = r5.iterator()     // Catch:{ Throwable -> 0x037a }
                L_0x0111:
                    boolean r6 = r5.hasNext()     // Catch:{ Throwable -> 0x037a }
                    if (r6 == 0) goto L_0x00d8
                    java.lang.Object r6 = r5.next()     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x037a }
                    boolean r7 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r7 == 0) goto L_0x0111
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x037a }
                    r7.<init>()     // Catch:{ Throwable -> 0x037a }
                    r7.append(r4)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r8 = ": "
                    r7.append(r8)     // Catch:{ Throwable -> 0x037a }
                    r7.append(r6)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r6 = r7.toString()     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r6)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x0111
                L_0x0139:
                    r1.disconnect()     // Catch:{ Throwable -> 0x03e6 }
                    return
                L_0x013d:
                    java.lang.String r5 = r1.getContentType()     // Catch:{ Throwable -> 0x037a }
                    int r7 = r1.getContentLength()     // Catch:{ Throwable -> 0x037a }
                    if (r7 > 0) goto L_0x0154
                    r7 = 2097152(0x200000, float:2.938736E-39)
                    boolean r8 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r8 == 0) goto L_0x0154
                    java.lang.String r8 = r1.getResponseMessage()     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r8)     // Catch:{ Throwable -> 0x037a }
                L_0x0154:
                    byte[] r8 = new byte[r7]     // Catch:{ Throwable -> 0x037a }
                    java.io.InputStream r9 = r1.getInputStream()     // Catch:{ Throwable -> 0x037a }
                L_0x015a:
                    if (r3 >= r7) goto L_0x0166
                    int r10 = r7 - r3
                    int r10 = r9.read(r8, r3, r10)     // Catch:{ Throwable -> 0x037a }
                    if (r10 <= 0) goto L_0x0166
                    int r3 = r3 + r10
                    goto L_0x015a
                L_0x0166:
                    byte[] r3 = com.autonavi.indoor.util.MapUtils.copyOf(r8, r3)     // Catch:{ Throwable -> 0x037a }
                    r9.close()     // Catch:{ Throwable -> 0x037a }
                    boolean r8 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r8 == 0) goto L_0x01c9
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r9 = "Response mUrl="
                    r8.<init>(r9)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r9 = r5     // Catch:{ Throwable -> 0x037a }
                    r8.append(r9)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r9 = ", buffer[1]="
                    r8.append(r9)     // Catch:{ Throwable -> 0x037a }
                    byte[] r9 = r6     // Catch:{ Throwable -> 0x037a }
                    byte r2 = r9[r2]     // Catch:{ Throwable -> 0x037a }
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = ", code="
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    r8.append(r4)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = ", length="
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    int r2 = r3.length     // Catch:{ Throwable -> 0x037a }
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = ", len="
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    r8.append(r7)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = ", contentLength="
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    int r2 = r1.getContentLength()     // Catch:{ Throwable -> 0x037a }
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = ", "
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = r1.getResponseMessage()     // Catch:{ Throwable -> 0x037a }
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = ", "
                    r8.append(r2)     // Catch:{ Throwable -> 0x037a }
                    r8.append(r5)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = r8.toString()     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x01c9:
                    java.lang.String r2 = "application/json"
                    boolean r2 = r5.startsWith(r2)     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0355
                    java.lang.String r2 = new java.lang.String     // Catch:{ Throwable -> 0x037a }
                    r2.<init>(r3)     // Catch:{ Throwable -> 0x037a }
                    boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r3 == 0) goto L_0x01dd
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x01dd:
                    org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Throwable -> 0x037a }
                    r3.<init>(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = "status"
                    boolean r2 = r3.has(r2)     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02ef
                    java.lang.String r2 = "status"
                    java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x037a }
                    boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0203
                    java.lang.String r4 = "json status:"
                    java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = r4.concat(r2)     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x0203:
                    java.lang.String r2 = "info"
                    java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x037a }
                    boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r3 == 0) goto L_0x021a
                    java.lang.String r3 = "json info:"
                    java.lang.String r4 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r3 = r3.concat(r4)     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r3)     // Catch:{ Throwable -> 0x037a }
                L_0x021a:
                    r3 = 721(0x2d1, float:1.01E-42)
                    java.lang.String r4 = "OK"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0230
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "正常"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x0230:
                    java.lang.String r4 = "INVALID_USER_KEY"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0246
                    r3 = 714(0x2ca, float:1.0E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "用户key非法或过期"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x0246:
                    java.lang.String r4 = "SERVICE_NOT_AVAILBALE"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x025b
                    r3 = 715(0x2cb, float:1.002E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "请求服务不存在"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x025b:
                    java.lang.String r4 = "SERVICE_RESPONSE_ERROR"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0270
                    r3 = 716(0x2cc, float:1.003E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "请求服务响应错误"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x0270:
                    java.lang.String r4 = "INSUFFICIENT_PRIVILEGES"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0285
                    r3 = 717(0x2cd, float:1.005E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "无权限访问此服务"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x0285:
                    java.lang.String r4 = "OVER_QUOTA"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x029a
                    r3 = 718(0x2ce, float:1.006E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "请求超出配额"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x029a:
                    java.lang.String r4 = "INVALID_PARAMS"
                    boolean r4 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x02af
                    r3 = 719(0x2cf, float:1.008E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "请求参数非法"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x02c3
                L_0x02af:
                    java.lang.String r4 = "UNKNOWN_ERROR"
                    boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    r3 = 720(0x2d0, float:1.009E-42)
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x02c3
                    java.lang.String r2 = "未知错误"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x02c3:
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0345
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Looper r2 = r2.getLooper()     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0345
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Looper r2 = r2.getLooper()     // Catch:{ Throwable -> 0x037a }
                    java.lang.Thread r2 = r2.getThread()     // Catch:{ Throwable -> 0x037a }
                    boolean r2 = r2.isAlive()     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0345
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Handler r4 = r7     // Catch:{ Throwable -> 0x037a }
                    java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x037a }
                    android.os.Message r3 = r4.obtainMessage(r6, r3)     // Catch:{ Throwable -> 0x037a }
                    r2.sendMessage(r3)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x0345
                L_0x02ef:
                    java.lang.String r2 = "result"
                    boolean r2 = r3.has(r2)     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0345
                    java.lang.String r2 = "result"
                    java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x037a }
                    boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r4 == 0) goto L_0x0310
                    java.lang.String r4 = "json result:"
                    java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r4 = r4.concat(r5)     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r4)     // Catch:{ Throwable -> 0x037a }
                L_0x0310:
                    java.lang.String r4 = "false"
                    boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0345
                    java.lang.String r2 = "message"
                    java.lang.String r2 = r3.getString(r2)     // Catch:{ Throwable -> 0x037a }
                    android.os.Handler r3 = r7     // Catch:{ Throwable -> 0x037a }
                    if (r3 == 0) goto L_0x0345
                    android.os.Handler r3 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Looper r3 = r3.getLooper()     // Catch:{ Throwable -> 0x037a }
                    if (r3 == 0) goto L_0x0345
                    android.os.Handler r3 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Looper r3 = r3.getLooper()     // Catch:{ Throwable -> 0x037a }
                    java.lang.Thread r3 = r3.getThread()     // Catch:{ Throwable -> 0x037a }
                    boolean r3 = r3.isAlive()     // Catch:{ Throwable -> 0x037a }
                    if (r3 == 0) goto L_0x0345
                    android.os.Handler r3 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Handler r4 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Message r2 = r4.obtainMessage(r6, r2)     // Catch:{ Throwable -> 0x037a }
                    r3.sendMessage(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x0345:
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0374
                    java.lang.Object r2 = r1.getContent()     // Catch:{ Throwable -> 0x037a }
                    java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x037a }
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x0374
                L_0x0355:
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Looper r2 = r2.getLooper()     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x036b
                    android.os.Handler r2 = r7     // Catch:{ Throwable -> 0x037a }
                    android.os.Handler r4 = r7     // Catch:{ Throwable -> 0x037a }
                    r5 = 1209(0x4b9, float:1.694E-42)
                    android.os.Message r3 = r4.obtainMessage(r5, r3)     // Catch:{ Throwable -> 0x037a }
                    r2.sendMessage(r3)     // Catch:{ Throwable -> 0x037a }
                    goto L_0x0374
                L_0x036b:
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x037a }
                    if (r2 == 0) goto L_0x0374
                    java.lang.String r2 = "Thread has been died"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x037a }
                L_0x0374:
                    r1.disconnect()     // Catch:{ Throwable -> 0x03e6 }
                    return
                L_0x0378:
                    r2 = move-exception
                    goto L_0x03e2
                L_0x037a:
                    r2 = move-exception
                    boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0378 }
                    if (r3 == 0) goto L_0x039a
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0378 }
                    java.lang.String r4 = "postRequest异常："
                    r3.<init>(r4)     // Catch:{ all -> 0x0378 }
                    r3.append(r2)     // Catch:{ all -> 0x0378 }
                    java.lang.String r4 = ", url="
                    r3.append(r4)     // Catch:{ all -> 0x0378 }
                    java.lang.String r4 = r5     // Catch:{ all -> 0x0378 }
                    r3.append(r4)     // Catch:{ all -> 0x0378 }
                    java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0378 }
                    com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x0378 }
                L_0x039a:
                    boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0378 }
                    if (r3 == 0) goto L_0x03a1
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ all -> 0x0378 }
                L_0x03a1:
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0378 }
                    if (r2 == 0) goto L_0x03aa
                    android.os.Handler r2 = r7     // Catch:{ all -> 0x0378 }
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ all -> 0x0378 }
                L_0x03aa:
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0378 }
                    if (r2 == 0) goto L_0x03bb
                    android.os.Handler r2 = r7     // Catch:{ all -> 0x0378 }
                    android.os.Looper r2 = r2.getLooper()     // Catch:{ all -> 0x0378 }
                    java.lang.Thread r2 = r2.getThread()     // Catch:{ all -> 0x0378 }
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ all -> 0x0378 }
                L_0x03bb:
                    android.os.Handler r2 = r7     // Catch:{ all -> 0x0378 }
                    android.os.Looper r2 = r2.getLooper()     // Catch:{ all -> 0x0378 }
                    if (r2 == 0) goto L_0x03d5
                    android.os.Handler r2 = r7     // Catch:{ all -> 0x0378 }
                    android.os.Handler r3 = r7     // Catch:{ all -> 0x0378 }
                    long r4 = r3     // Catch:{ all -> 0x0378 }
                    java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ all -> 0x0378 }
                    android.os.Message r3 = r3.obtainMessage(r0, r4)     // Catch:{ all -> 0x0378 }
                    r2.sendMessage(r3)     // Catch:{ all -> 0x0378 }
                    goto L_0x03de
                L_0x03d5:
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0378 }
                    if (r2 == 0) goto L_0x03de
                    java.lang.String r2 = "Thread has been died"
                    com.autonavi.indoor.util.L.d(r2)     // Catch:{ all -> 0x0378 }
                L_0x03de:
                    r1.disconnect()     // Catch:{ Throwable -> 0x03e6 }
                    return
                L_0x03e2:
                    r1.disconnect()     // Catch:{ Throwable -> 0x03e6 }
                    throw r2     // Catch:{ Throwable -> 0x03e6 }
                L_0x03e6:
                    r1 = move-exception
                    boolean r2 = com.autonavi.indoor.util.L.isLogging
                    if (r2 == 0) goto L_0x03f9
                    java.lang.String r2 = "打开连接异常："
                    java.lang.String r1 = java.lang.String.valueOf(r1)
                    java.lang.String r1 = r2.concat(r1)
                    com.autonavi.indoor.util.L.d(r1)
                L_0x03f9:
                    android.os.Handler r1 = r7
                    android.os.Looper r1 = r1.getLooper()
                    if (r1 == 0) goto L_0x0413
                    android.os.Handler r1 = r7
                    android.os.Handler r2 = r7
                    long r3 = r3
                    java.lang.Long r3 = java.lang.Long.valueOf(r3)
                    android.os.Message r0 = r2.obtainMessage(r0, r3)
                    r1.sendMessage(r0)
                    return
                L_0x0413:
                    boolean r0 = com.autonavi.indoor.util.L.isLogging
                    if (r0 == 0) goto L_0x041c
                    java.lang.String r0 = "Thread has been died"
                    com.autonavi.indoor.util.L.d(r0)
                L_0x041c:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.AnonymousClass1.run():void");
            }
        };
        r1.start();
        return 0;
    }

    public static String datetimeFormat(String str, long j) {
        return new SimpleDateFormat(str, Locale.CHINA).format(new Date(j));
    }

    public static String timeFormat(long j) {
        return new SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA).format(new Date(j));
    }

    public static String timeFormat() {
        return timeFormat(System.currentTimeMillis());
    }

    public static Coord transform(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        Coord coord = new Coord(f4, f3);
        double d = (double) f3;
        double d2 = (double) f4;
        if (outOfChina(d, d2)) {
            return coord;
        }
        double d3 = d2 - 105.0d;
        double d4 = d - 35.0d;
        double transformLat = transformLat(d3, d4);
        double transformLon = transformLon(d3, d4);
        double d5 = (d / 180.0d) * pi;
        double sin = Math.sin(d5);
        double d6 = 1.0d - ((ee * sin) * sin);
        double sqrt = Math.sqrt(d6);
        coord.latitude = f3 + ((float) ((transformLat * 180.0d) / (((a * (1.0d - ee)) / (d6 * sqrt)) * pi)));
        coord.longitude = ((float) ((transformLon * 180.0d) / (((a / sqrt) * Math.cos(d5)) * pi))) + f4;
        return coord;
    }

    static double transformLat(double d, double d2) {
        double d3 = d * 2.0d;
        return -100.0d + d3 + (d2 * 3.0d) + (d2 * 0.2d * d2) + (0.1d * d * d2) + (Math.sqrt(Math.abs(d)) * 0.2d) + ((((Math.sin((d * 6.0d) * pi) * 20.0d) + (Math.sin(d3 * pi) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(pi * d2) * 20.0d) + (Math.sin((d2 / 3.0d) * pi) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d2 / 12.0d) * pi) * 160.0d) + (Math.sin((d2 * pi) / 30.0d) * 320.0d)) * 2.0d) / 3.0d);
    }

    static double transformLon(double d, double d2) {
        double d3 = d * 0.1d;
        return d + 300.0d + (d2 * 2.0d) + (d3 * d) + (d3 * d2) + (Math.sqrt(Math.abs(d)) * 0.1d) + ((((Math.sin((6.0d * d) * pi) * 20.0d) + (Math.sin((d * 2.0d) * pi) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(pi * d) * 20.0d) + (Math.sin((d / 3.0d) * pi) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d / 12.0d) * pi) * 150.0d) + (Math.sin((d / 30.0d) * pi) * 300.0d)) * 2.0d) / 3.0d);
    }

    static void logFile(String str) {
        logFile("scen", str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0227, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void logFile(java.lang.String r8, java.lang.String r9) {
        /*
            boolean r0 = com.autonavi.indoor.util.L.isLogging
            if (r0 == 0) goto L_0x001b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            java.lang.String r1 = ", "
            r0.append(r1)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            com.autonavi.indoor.util.L.d(r0)
        L_0x001b:
            java.lang.StringBuffer r0 = mLogsBuffer     // Catch:{ Throwable -> 0x022b }
            monitor-enter(r0)     // Catch:{ Throwable -> 0x022b }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0228 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0228 }
            r2.<init>()     // Catch:{ all -> 0x0228 }
            java.io.File r3 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ all -> 0x0228 }
            java.lang.String r3 = r3.getPath()     // Catch:{ all -> 0x0228 }
            r2.append(r3)     // Catch:{ all -> 0x0228 }
            java.lang.String r3 = "/autonavi/indoor/iodetector"
            r2.append(r3)     // Catch:{ all -> 0x0228 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0228 }
            r1.<init>(r2)     // Catch:{ all -> 0x0228 }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x0228 }
            if (r1 != 0) goto L_0x0044
            monitor-exit(r0)     // Catch:{ all -> 0x0228 }
            return
        L_0x0044:
            java.lang.String r1 = "scen"
            r2 = 0
            if (r8 != r1) goto L_0x0178
            java.lang.String r1 = "startDetect"
            boolean r1 = r9.equals(r1)     // Catch:{ Throwable -> 0x0170 }
            r3 = 128(0x80, float:1.794E-43)
            if (r1 == 0) goto L_0x012b
            java.lang.StringBuffer r1 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            int r1 = r1.length()     // Catch:{ Throwable -> 0x0170 }
            if (r1 <= r3) goto L_0x0068
            java.lang.StringBuffer r1 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            java.lang.StringBuffer r4 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            int r4 = r4.length()     // Catch:{ Throwable -> 0x0170 }
            r1.delete(r2, r4)     // Catch:{ Throwable -> 0x0170 }
        L_0x0068:
            java.lang.StringBuffer r1 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0170 }
            r4.<init>()     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = "yyyy-MM-dd"
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = datetimeFormat(r5, r6)     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = " "
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.MANUFACTURER     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.DEVICE     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.SERIAL     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.FINGERPRINT     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.ID     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.PRODUCT     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.TYPE     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.USER     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.VERSION.RELEASE     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.VERSION.INCREMENTAL     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.getRadioVersion()     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            long r5 = android.os.Build.TIME     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = ","
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = android.os.Build.HARDWARE     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = "\n"
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0170 }
            r1.append(r4)     // Catch:{ Throwable -> 0x0170 }
        L_0x012b:
            java.lang.StringBuffer r1 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0170 }
            r4.<init>()     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = "HH:mm:ss.SSS"
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = datetimeFormat(r5, r6)     // Catch:{ Throwable -> 0x0170 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = " "
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            r4.append(r9)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r5 = "\n"
            r4.append(r5)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0170 }
            r1.append(r4)     // Catch:{ Throwable -> 0x0170 }
            java.lang.String r1 = "stopDetect"
            boolean r1 = r9.equals(r1)     // Catch:{ Throwable -> 0x0170 }
            if (r1 == 0) goto L_0x0178
            java.lang.StringBuffer r1 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            int r1 = r1.length()     // Catch:{ Throwable -> 0x0170 }
            if (r1 <= r3) goto L_0x0178
            java.lang.StringBuffer r1 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            java.lang.StringBuffer r3 = mFlagsBuffer     // Catch:{ Throwable -> 0x0170 }
            int r3 = r3.length()     // Catch:{ Throwable -> 0x0170 }
            r1.delete(r2, r3)     // Catch:{ Throwable -> 0x0170 }
            goto L_0x0178
        L_0x0170:
            r1 = move-exception
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0228 }
            if (r3 == 0) goto L_0x0178
            com.autonavi.indoor.util.L.d(r1)     // Catch:{ all -> 0x0228 }
        L_0x0178:
            java.lang.String r1 = "startDetect"
            boolean r1 = r9.equals(r1)     // Catch:{ all -> 0x0228 }
            if (r1 == 0) goto L_0x018c
            java.lang.StringBuffer r1 = mLogsBuffer     // Catch:{ all -> 0x0228 }
            java.lang.StringBuffer r3 = mLogsBuffer     // Catch:{ all -> 0x0228 }
            int r3 = r3.length()     // Catch:{ all -> 0x0228 }
            r1.delete(r2, r3)     // Catch:{ all -> 0x0228 }
        L_0x018c:
            java.lang.StringBuffer r1 = mLogsBuffer     // Catch:{ all -> 0x0228 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0228 }
            r3.<init>()     // Catch:{ all -> 0x0228 }
            r3.append(r8)     // Catch:{ all -> 0x0228 }
            java.lang.String r8 = " "
            r3.append(r8)     // Catch:{ all -> 0x0228 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0228 }
            r3.append(r4)     // Catch:{ all -> 0x0228 }
            java.lang.String r8 = " "
            r3.append(r8)     // Catch:{ all -> 0x0228 }
            r3.append(r9)     // Catch:{ all -> 0x0228 }
            java.lang.String r8 = "\n"
            r3.append(r8)     // Catch:{ all -> 0x0228 }
            java.lang.String r8 = r3.toString()     // Catch:{ all -> 0x0228 }
            r1.append(r8)     // Catch:{ all -> 0x0228 }
            java.lang.String r8 = "stopDetect"
            boolean r8 = r9.equals(r8)     // Catch:{ all -> 0x0228 }
            if (r8 == 0) goto L_0x0226
            java.lang.StringBuffer r8 = mLogsBuffer     // Catch:{ all -> 0x0228 }
            int r8 = r8.length()     // Catch:{ all -> 0x0228 }
            r9 = 64
            if (r8 <= r9) goto L_0x0226
            java.io.File r8 = new java.io.File     // Catch:{ Throwable -> 0x021e }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x021e }
            r9.<init>()     // Catch:{ Throwable -> 0x021e }
            java.io.File r1 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = r1.getPath()     // Catch:{ Throwable -> 0x021e }
            r9.append(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "/autonavi/indoor/iodetector/log."
            r9.append(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "yyyyMMdd_HHmmss"
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = datetimeFormat(r1, r3)     // Catch:{ Throwable -> 0x021e }
            r9.append(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = ".txt"
            r9.append(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x021e }
            r8.<init>(r9)     // Catch:{ Throwable -> 0x021e }
            r8.createNewFile()     // Catch:{ Throwable -> 0x021e }
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x021e }
            r9.<init>(r8)     // Catch:{ Throwable -> 0x021e }
            java.lang.StringBuffer r8 = mLogsBuffer     // Catch:{ Throwable -> 0x021e }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x021e }
            byte[] r8 = r8.getBytes()     // Catch:{ Throwable -> 0x021e }
            r9.write(r8)     // Catch:{ Throwable -> 0x021e }
            r9.close()     // Catch:{ Throwable -> 0x021e }
            java.lang.StringBuffer r8 = mLogsBuffer     // Catch:{ Throwable -> 0x021e }
            java.lang.StringBuffer r9 = mLogsBuffer     // Catch:{ Throwable -> 0x021e }
            int r9 = r9.length()     // Catch:{ Throwable -> 0x021e }
            r8.delete(r2, r9)     // Catch:{ Throwable -> 0x021e }
            goto L_0x0226
        L_0x021e:
            r8 = move-exception
            boolean r9 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0228 }
            if (r9 == 0) goto L_0x0226
            com.autonavi.indoor.util.L.d(r8)     // Catch:{ all -> 0x0228 }
        L_0x0226:
            monitor-exit(r0)     // Catch:{ all -> 0x0228 }
            return
        L_0x0228:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0228 }
            throw r8     // Catch:{ Throwable -> 0x022b }
        L_0x022b:
            r8 = move-exception
            boolean r9 = com.autonavi.indoor.util.L.isLogging
            if (r9 == 0) goto L_0x0233
            com.autonavi.indoor.util.L.d(r8)
        L_0x0233:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(java.lang.String, java.lang.String):void");
    }
}
