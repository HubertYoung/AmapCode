package defpackage;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: abz reason: default package */
/* compiled from: PayUtil */
public final class abz {
    private static final Random a = new Random();

    public static String a(Map<String, Object> map, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap hashMap = new HashMap();
        for (Entry next : map.entrySet()) {
            String str2 = (String) next.getKey();
            if (!map.get(str2).equals("")) {
                String valueOf = String.valueOf(next.getValue());
                hashMap.put(str2, valueOf);
                stringBuffer.append(str2);
                stringBuffer.append("=");
                stringBuffer.append(valueOf);
                stringBuffer.append("&");
            }
        }
        try {
            String a2 = a((Map<String, String>) hashMap);
            StringBuilder sb = new StringBuilder();
            sb.append(a2);
            sb.append("&key=");
            sb.append(str);
            String sb2 = sb.toString();
            Mac instance = Mac.getInstance("HmacSHA256");
            instance.init(new SecretKeySpec(str.getBytes(), "HmacSHA256"));
            String upperCase = a(instance.doFinal(sb2.getBytes("utf-8"))).toUpperCase();
            stringBuffer.append("sign=");
            stringBuffer.append(upperCase);
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (bArr != null && i < bArr.length) {
            String hexString = Integer.toHexString(bArr[i] & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
            i++;
        }
        return sb.toString().toLowerCase();
    }

    private static String a(Map<String, String> map) throws UnsupportedEncodingException {
        Object[] array = map.keySet().toArray();
        Arrays.sort(array);
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = true;
        for (Object obj : array) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append("&");
            }
            stringBuffer.append(obj.toString());
            stringBuffer.append("=");
            String str = map.get(obj);
            String str2 = "";
            if (str != null) {
                str2 = str.toString();
            }
            stringBuffer.append(str2);
        }
        return stringBuffer.toString();
    }

    public static String a() {
        Random random = a;
        int i = 16;
        char[] cArr = new char[16];
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return new String(cArr);
            }
            char nextInt = (char) (random.nextInt(91) + 32);
            if (Character.isLetter(nextInt) || Character.isDigit(nextInt)) {
                if (nextInt < 56320 || nextInt > 57343) {
                    if (nextInt < 55296 || nextInt > 56191) {
                        if (nextInt < 56192 || nextInt > 56319) {
                            cArr[i2] = nextInt;
                        }
                    } else if (i2 != 0) {
                        cArr[i2] = (char) (random.nextInt(128) + 56320);
                        i2--;
                        cArr[i2] = nextInt;
                    }
                    i = i2;
                } else if (i2 != 0) {
                    cArr[i2] = nextInt;
                    i = i2 - 1;
                    cArr[i] = (char) (random.nextInt(128) + 55296);
                }
            }
            i = i2 + 1;
        }
    }
}
