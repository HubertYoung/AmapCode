package com.alipay.security.mobile.module.commonutils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Base64;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.alipay.security.mobile.module.crypto.Base64Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonUtils {
    public static String getNonNullString(String str) {
        return str == null ? "" : str;
    }

    public static boolean isByteArrayEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static String getStringFromMap(Map<String, Object> map, String str, String str2) {
        if (map == null) {
            return str2;
        }
        Object obj = map.get(str);
        if (obj != null && (obj instanceof String)) {
            return (String) obj;
        }
        return str2;
    }

    public static int getIntegerFromMap(Map<String, Object> map, String str, int i) {
        if (map == null) {
            return i;
        }
        Object obj = map.get(str);
        if (obj != null && (obj instanceof Integer)) {
            return ((Integer) obj).intValue();
        }
        return i;
    }

    public static boolean isBlank(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean equals(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public static boolean isZero(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i)) && str.charAt(i) != '0') {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    public static String sha1ByString(String str) {
        try {
            if (isBlank(str)) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            for (byte valueOf : digest) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] sha1StringWithBytes(String str) {
        try {
            if (isBlank(str)) {
                return null;
            }
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes("UTF-8"));
            return instance.digest();
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equalsIgnoreCase(str2);
    }

    public static String getStackString(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            StringBuilder sb = new StringBuilder();
            sb.append(stackTraceElement.toString());
            sb.append("\n");
            stringBuffer.append(sb.toString());
        }
        return stringBuffer.toString();
    }

    public static File getExternalDirectory() {
        try {
            return (File) Environment.class.getMethod(new String(Base64Util.decode("Z2V0RXh0ZXJuYWxTdG9yYWdlRGlyZWN0b3J5")), new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String textCompress(String str) {
        try {
            byte[] array = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(str.length()).array();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.length());
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes("UTF-8"));
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            byte[] bArr = new byte[(byteArrayOutputStream.toByteArray().length + 4)];
            System.arraycopy(array, 0, bArr, 0, 4);
            System.arraycopy(byteArrayOutputStream.toByteArray(), 0, bArr, 4, byteArrayOutputStream.toByteArray().length);
            return Base64.encodeToString(bArr, 8);
        } catch (Exception unused) {
            return "";
        }
    }

    public static byte[] gzipCompress(String str) {
        if (isBlank(str)) {
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("utf-8"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = byteArrayInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    gZIPOutputStream.write(bArr, 0, read);
                } else {
                    gZIPOutputStream.close();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                    byteArrayInputStream.close();
                    return byteArray;
                }
            }
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONArray fromFloatArray(float[] fArr) {
        JSONArray jSONArray = new JSONArray();
        if (fArr != null) {
            for (float f : fArr) {
                try {
                    jSONArray.put((double) f);
                } catch (JSONException unused) {
                }
            }
        }
        return jSONArray;
    }

    public static String getSystemProperties(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{str, str2});
        } catch (Exception unused) {
            return str2;
        }
    }

    public static byte[] convertMacAddressFromStringToBytes(String str) {
        if (str == null) {
            return null;
        }
        String upperCase = str.toUpperCase();
        byte[] bArr = new byte[6];
        int i = 0;
        for (int i2 = 0; i2 < upperCase.length(); i2++) {
            char charAt = upperCase.charAt(i2);
            if (charAt != ':') {
                int indexOf = HexUtils.HEX_CHARS.indexOf(charAt);
                if (indexOf < 0) {
                    return null;
                }
                int i3 = i / 2;
                if (i3 >= 6) {
                    return null;
                }
                bArr[i3] = (byte) (((byte) (indexOf & 255)) | (bArr[i3] << 4));
                i++;
            }
        }
        return bArr;
    }

    public static boolean supportArmeabiV7() {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        if (VERSION.SDK_INT >= 21 && Build.SUPPORTED_ABIS != null) {
            for (String str : Build.SUPPORTED_ABIS) {
                if (str != null && str.toLowerCase().contains("armeabi-v7a")) {
                    return true;
                }
            }
        }
        if (VERSION.SDK_INT >= 21 && Build.SUPPORTED_32_BIT_ABIS != null) {
            for (String str2 : Build.SUPPORTED_32_BIT_ABIS) {
                if (str2 != null && str2.toLowerCase().contains("armeabi-v7a")) {
                    return true;
                }
            }
        }
        if (VERSION.SDK_INT >= 21 && Build.SUPPORTED_64_BIT_ABIS != null) {
            for (String str3 : Build.SUPPORTED_64_BIT_ABIS) {
                if (str3 != null && str3.toLowerCase().contains("armeabi-v7a")) {
                    return true;
                }
            }
        }
        if (Build.CPU_ABI == null || !Build.CPU_ABI.toLowerCase().contains("armeabi-v7a")) {
            return Build.CPU_ABI2 != null && Build.CPU_ABI2.toLowerCase().contains("armeabi-v7a");
        }
        return true;
    }

    public static byte[] convertBooleanStringToBitset(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split(":");
        if (split == null || split.length != 2) {
            return null;
        }
        String str2 = split[0];
        String str3 = split[1];
        int length = str3.length();
        int i = length % 8;
        int i2 = (length / 8) + 1 + (i == 0 ? 0 : 1);
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = 0;
        }
        try {
            bArr[0] = (byte) ((Integer.parseInt(str2) << 3) | i);
            for (int i4 = 0; i4 < length; i4++) {
                int i5 = (i4 / 8) + 1;
                byte b = bArr[i5];
                if ('1' == str3.charAt(i4)) {
                    b |= 1 << (7 - (i4 % 8));
                }
                bArr[i5] = (byte) b;
            }
            return bArr;
        } catch (Exception unused) {
            return null;
        }
    }

    public static JSONArray list2Json(List<Map<String, String>> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        if (list == null || list.size() <= 0) {
            return jSONArray;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            JSONObject jSONObject = new JSONObject();
            for (Entry entry : list.get(size).entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    public static boolean isMapEmpty(Map<String, String> map) {
        return map == null || map.size() <= 0;
    }

    public static JSONObject map2Json(Map<String, String> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (isMapEmpty(map)) {
            return jSONObject;
        }
        for (Entry next : map.entrySet()) {
            jSONObject.put((String) next.getKey(), next.getValue());
        }
        return jSONObject;
    }

    public static boolean isAlipayWallet(Context context) {
        if (context == null) {
            return false;
        }
        String packageName = context.getPackageName();
        if (packageName == null) {
            return false;
        }
        if ("com.eg.android.AlipayGphone".equals(packageName) || "com.eg.android.AlipayGphoneRC".equals(packageName)) {
            return true;
        }
        return false;
    }

    public static boolean isApkDebugable(Context context) {
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
