package com.autonavi.indoor.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.location.common.a;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MapUtils {
    private static boolean isWifiConnected = false;
    private static String mBluetoothAddress = "";
    private static String mImei = "";
    private static String mImsi = "";
    private static String mWifiAddress = "";
    private static AosEncryptor sAosEncryptor;

    public interface AosEncryptor {
        String amapEncode(String str);

        byte[] amapEncode(byte[] bArr);
    }

    public static byte[] amapEncodeBody(byte[] bArr) {
        return bArr;
    }

    public static double distance(double d, double d2, double d3, double d4) {
        double cos = Math.cos((((d2 + d4) / 2.0d) * 3.141592653589793d) / 180.0d);
        double d5 = cos * (d - d3) * 1.0025d;
        double d6 = d2 - d4;
        return Math.sqrt((1.2350480292838154E10d / (((1.0d - (cos * cos)) * 1.003364139422145d) + ((0.9966471400661353d * cos) * cos))) * ((d5 * d5) + (d6 * d6)));
    }

    @RequiresPermission("android.permission.BLUETOOTH")
    public static String getBluetoothAddress() {
        String str = "";
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                str = defaultAdapter.getAddress();
            }
            mBluetoothAddress = str;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        return str;
    }

    public static String getImei(Context context) {
        if (context == null) {
            if (L.isLogging) {
                L.d((String) "context is null");
            }
            return "";
        }
        try {
            String a = a.a(context);
            if (TextUtils.isEmpty(a)) {
                String f = a.f(context);
                if (!TextUtils.isEmpty(f)) {
                    a = f.replaceAll(":", "");
                } else {
                    a = UUID.randomUUID().toString().replaceAll("-", "");
                }
            }
            mImei = a;
        } catch (Throwable unused) {
        }
        return mImei;
    }

    public static String getImei() {
        return mImei;
    }

    public static String getImsi(Context context) {
        try {
            context.getSystemService("phone");
            String d = a.d(context);
            if (TextUtils.isEmpty(d)) {
                String f = a.f(context);
                if (!TextUtils.isEmpty(f)) {
                    d = f.replaceAll(":", "");
                } else {
                    d = UUID.randomUUID().toString().replaceAll("-", "");
                }
            }
            mImsi = d;
        } catch (Throwable unused) {
        }
        return mImsi;
    }

    public static String getImsi() {
        return mImsi;
    }

    public static String getString(ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        if (b == 0) {
            return "";
        }
        byte[] bArr = new byte[b];
        byteBuffer.get(bArr);
        return new String(bArr);
    }

    public static String getString(ByteBuffer byteBuffer, int i) {
        if (i == 0) {
            return "";
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return new String(bArr);
    }

    public static void put(ByteBuffer byteBuffer, String str) {
        if (TextUtils.isEmpty(str)) {
            byteBuffer.put(0);
            return;
        }
        byte[] bytes = str.getBytes();
        byteBuffer.put((byte) bytes.length);
        byteBuffer.put(bytes);
    }

    public static void putDummyData(ByteBuffer byteBuffer, int i) {
        byteBuffer.put(new byte[i]);
    }

    public static void setAosEncryptorImpl(AosEncryptor aosEncryptor) {
        if (aosEncryptor != null) {
            sAosEncryptor = aosEncryptor;
        }
    }

    public static String amapEncode(String str) {
        if (sAosEncryptor != null) {
            return sAosEncryptor.amapEncode(str);
        }
        com.amap.location.common.d.a.c("MapUtils", "indoor aos encryptor-str is null");
        return null;
    }

    public static byte[] amapEncode(byte[] bArr) {
        if (sAosEncryptor != null) {
            return sAosEncryptor.amapEncode(bArr);
        }
        com.amap.location.common.d.a.c("MapUtils", "indoor aos encryptor-byte is null");
        return null;
    }

    private static Object invokeStaticMethod(String str, String str2, Object[] objArr, Class<?>[] clsArr) throws Exception {
        Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(null, objArr);
    }

    public static String amapEncodeUrl(String str) {
        try {
            String[] split = str.split("\\?", 2);
            if (split.length == 2) {
                String amapEncode = amapEncode(split[1]);
                if (TextUtils.isEmpty(amapEncode)) {
                    return str;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(split[0]);
                sb.append("?in=");
                sb.append(URLEncoder.encode(amapEncode, "UTF-8"));
                sb.append("&ent=2&is_bin=1");
                str = sb.toString();
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        return str;
    }

    private static String hexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte valueOf : bArr) {
            stringBuffer.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
        }
        return stringBuffer.toString();
    }

    private static boolean isWifiConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1);
        if (networkInfo == null || !networkInfo.isConnected()) {
            isWifiConnected = false;
        } else {
            isWifiConnected = true;
        }
        return isWifiConnected;
    }

    public static boolean isWifiConnected() {
        return isWifiConnected;
    }

    private static boolean isMobileNetConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0);
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public static boolean hasPressure(Context context) {
        return ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(6) != null;
    }

    public static byte[] copyOf(byte[] bArr, int i) {
        return copyOf(bArr, 0, i);
    }

    public static byte[] copyOf(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(i2 - i)];
        System.arraycopy(bArr, i, bArr2, 0, Math.min(i2, bArr.length) - i);
        return bArr2;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(HashMap<?, ?> hashMap) {
        return hashMap == null || hashMap.size() == 0;
    }

    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static boolean notEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean notEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean notEmpty(byte[] bArr) {
        return bArr != null && bArr.length > 0;
    }

    public static int length(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public static int length(Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static int length(HashMap<?, ?> hashMap) {
        if (hashMap == null) {
            return 0;
        }
        return hashMap.size();
    }

    public static int length(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    public static boolean isNetworkConnected(Context context) {
        return isMobileNetConnected(context) || isWifiConnected(context);
    }

    public static String encodeWifiMac(long j) {
        return macString(j);
    }

    public static String encodeWifiMac(String str) {
        return str.replace(':', '_');
    }

    public static void writeMac2Buffer(String str, ByteBuffer byteBuffer) {
        byte[] bytes = str.getBytes();
        byteBuffer.put((byte) bytes.length);
        byteBuffer.put(bytes);
    }

    public static void writeMac2SixByteBuffer(String str, ByteBuffer byteBuffer) {
        if (str == null || str.length() == 0) {
            putDummyData(byteBuffer, 6);
        } else {
            byteBuffer.put(encodeMac2SixByte(encodeMacLong(str)));
        }
    }

    public static long encodeMacLong(String str) {
        String replace = str.replace(":", "").replace("_", "").replace(Token.SEPARATOR, "").replace("", "");
        if (replace != null && replace.length() == 12) {
            return Long.parseLong(replace, 16);
        }
        if (L.isLogging) {
            L.d("mac=".concat(String.valueOf(replace)));
        }
        L.logStackTrace();
        return 0;
    }

    static long encodeMacLong(byte[] bArr) {
        long j = 0;
        if (bArr == null || bArr.length < 6) {
            return 0;
        }
        for (int i = 0; i < 6; i++) {
            j = (j << 8) + ((long) (bArr[i] & 255));
        }
        return j;
    }

    static byte[] encodeMac(String str) {
        return encodeMac2SixByte(encodeMacLong(str));
    }

    public static byte[] encodeMac2SixByte(long j) {
        byte[] bArr = new byte[6];
        for (int i = 5; i >= 0; i--) {
            bArr[i] = (byte) ((int) j);
            j >>= 8;
        }
        return bArr;
    }

    private static String macString(long j) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(String.format("%02x:", new Object[]{Long.valueOf((j >> (40 - (i * 8))) & 255)}));
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    static String macString(byte[] bArr) {
        if (bArr == null || bArr.length < 6) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(String.format("%02x:", new Object[]{Byte.valueOf(bArr[i])}));
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static byte[] decryptAES(byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
            instance.init(2, secretKeySpec);
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
            return null;
        }
    }
}
