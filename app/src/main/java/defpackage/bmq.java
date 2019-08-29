package defpackage;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.regex.Pattern;

/* renamed from: bmq reason: default package */
/* compiled from: DumpCrashMD5 */
public final class bmq {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(File file) {
        int i;
        try {
            if (!file.exists()) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                instance.update(bArr, 0, read);
            }
            fileInputStream.close();
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                byte b2 = b & 255;
                if (b2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b2));
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String a(byte[] bArr) {
        byte[] digest;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            char[] cArr = new char[(r1 * 2)];
            int i = 0;
            for (byte b : instance.digest()) {
                int i2 = i + 1;
                cArr[i] = a[(b >> 4) & 15];
                i = i2 + 1;
                cArr[i2] = a[b & 15];
            }
            return new String(cArr);
        } catch (Exception e) {
            e.printStackTrace();
            r9 = "";
            return "";
        }
    }

    public static String a(String str) {
        String str2 = "";
        if (str != null) {
            try {
                str2 = Pattern.compile("\\s*|\t|\r|\n").matcher(str).replaceAll("");
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return a(str2.getBytes("UTF-8"));
    }
}
