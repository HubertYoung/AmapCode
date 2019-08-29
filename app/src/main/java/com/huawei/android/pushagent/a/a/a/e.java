package com.huawei.android.pushagent.a.a.a;

import com.huawei.android.pushagent.a.a.c;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class e {
    public static String a(String str) {
        String str2;
        String str3;
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(str.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer(40);
            for (byte b : digest) {
                byte b2 = b & 255;
                if (b2 < 16) {
                    stringBuffer.append('0');
                }
                stringBuffer.append(Integer.toHexString(b2));
            }
            StringBuilder sb = new StringBuilder("getSHA256str:");
            sb.append(stringBuffer.toString());
            c.a("PushLogSC2816", sb.toString());
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            str3 = "PushLogSC2816";
            str2 = e.toString();
            r0 = e;
            c.c(str3, str2, r0);
            return str;
        } catch (NullPointerException e2) {
            str3 = "PushLogSC2816";
            str2 = e2.toString();
            r0 = e2;
            c.c(str3, str2, r0);
            return str;
        } catch (Exception e3) {
            str3 = "PushLogSC2816";
            str2 = e3.toString();
            r0 = e3;
            c.c(str3, str2, r0);
            return str;
        }
    }
}
