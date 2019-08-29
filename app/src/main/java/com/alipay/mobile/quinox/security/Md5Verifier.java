package com.alipay.mobile.quinox.security;

import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.autonavi.common.SuperId;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class Md5Verifier {
    public static final String TAG = "Md5Util";
    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", SuperId.BIT_1_RQBXY, SuperId.BIT_1_NEARBY_SEARCH, "d", "e", "f"};

    private static String byteArrayToHexString(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte byteToHexString : bArr) {
            stringBuffer.append(byteToHexString(byteToHexString));
        }
        return stringBuffer.toString();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v0, types: [byte, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String byteToHexString(int r3) {
        /*
            if (r3 >= 0) goto L_0x0004
            int r3 = r3 + 256
        L_0x0004:
            int r0 = r3 / 16
            int r3 = r3 % 16
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String[] r2 = hexDigits
            r0 = r2[r0]
            r1.append(r0)
            java.lang.String[] r0 = hexDigits
            r3 = r0[r3]
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.security.Md5Verifier.byteToHexString(byte):java.lang.String");
    }

    public static boolean checkMd5(InputStream inputStream, InputStream inputStream2) {
        if (inputStream == null || inputStream2 == null) {
            return false;
        }
        String genInputStreamMd5sum = genInputStreamMd5sum(inputStream);
        String genInputStreamMd5sum2 = genInputStreamMd5sum(inputStream2);
        if (StringUtil.isEmpty(genInputStreamMd5sum) || StringUtil.isEmpty(genInputStreamMd5sum2) || !StringUtil.equals(genInputStreamMd5sum, genInputStreamMd5sum2)) {
            return false;
        }
        return true;
    }

    public static String genFileMd5sum(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            return genInputStreamMd5sum(new FileInputStream(file));
        } catch (Throwable th) {
            Log.w((String) TAG, th);
            return null;
        }
    }

    public static String genInputStreamMd5sum(InputStream inputStream) {
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            while (true) {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                instance.update(bArr, 0, read);
            }
            String byteArrayToHexString = byteArrayToHexString(instance.digest());
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
            return byteArrayToHexString;
        } catch (Throwable th) {
            Log.e((String) TAG, th);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    Log.e(TAG, "", e2);
                }
            }
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    Log.e(TAG, "", e3);
                }
            }
        }
    }
}
