package com.autonavi.indoor.util;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings.System;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class EncryptHelper {
    static final String AESMODE = "AES/CBC/PKCS5Padding";
    static final String RSAMODE = "RSA/ECB/PKCS1Padding";
    private static final String UM_SETTINGS_STORAGE_NEW = "mqBRboGZkQPcAkyk";
    private static final String UTID_FILE = "/.UTSystemConfig/Global/Alvin2.xml";
    private static final byte[] base64DecodeChars = new byte[128];
    private static final char[] base64EncodeChars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    /* access modifiers changed from: private */
    public static boolean isUtdidFlag = false;
    /* access modifiers changed from: private */
    public static String mUtidid = "";

    static class UTDXMLHandler extends DefaultHandler {
        UTDXMLHandler() {
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str2.equals(ResUtils.STRING) && "UTDID".equals(attributes.getValue("name"))) {
                EncryptHelper.isUtdidFlag = true;
            }
        }

        public void characters(char[] cArr, int i, int i2) throws SAXException {
            if (EncryptHelper.isUtdidFlag) {
                EncryptHelper.mUtidid = new String(cArr, i, i2);
            }
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            EncryptHelper.isUtdidFlag = false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0037 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0059  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.security.PublicKey getPublicKey(android.content.Context r3) throws java.security.cert.CertificateException, java.security.spec.InvalidKeySpecException, java.security.NoSuchAlgorithmException, java.lang.NullPointerException, java.io.IOException {
        /*
            r3 = 674(0x2a2, float:9.44E-43)
            byte[] r3 = new byte[r3]
            r3 = {48, -126, 2, -98, 48, -126, 2, 7, -96, 3, 2, 1, 2, 2, 9, 0, -99, 15, 119, 58, 44, -19, -105, -40, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 5, 5, 0, 48, 104, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 19, 48, 17, 6, 3, 85, 4, 8, 12, 10, 83, 111, 109, 101, 45, 83, 116, 97, 116, 101, 49, 16, 48, 14, 6, 3, 85, 4, 7, 12, 7, 66, 101, 105, 106, 105, 110, 103, 49, 17, 48, 15, 6, 3, 85, 4, 10, 12, 8, 65, 117, 116, 111, 110, 97, 118, 105, 49, 31, 48, 29, 6, 3, 85, 4, 3, 12, 22, 99, 111, 109, 46, 97, 117, 116, 111, 110, 97, 118, 105, 46, 97, 112, 105, 115, 101, 114, 118, 101, 114, 48, 30, 23, 13, 49, 51, 48, 56, 49, 53, 48, 55, 53, 54, 53, 53, 90, 23, 13, 50, 51, 48, 56, 49, 51, 48, 55, 53, 54, 53, 53, 90, 48, 104, 49, 11, 48, 9, 6, 3, 85, 4, 6, 19, 2, 67, 78, 49, 19, 48, 17, 6, 3, 85, 4, 8, 12, 10, 83, 111, 109, 101, 45, 83, 116, 97, 116, 101, 49, 16, 48, 14, 6, 3, 85, 4, 7, 12, 7, 66, 101, 105, 106, 105, 110, 103, 49, 17, 48, 15, 6, 3, 85, 4, 10, 12, 8, 65, 117, 116, 111, 110, 97, 118, 105, 49, 31, 48, 29, 6, 3, 85, 4, 3, 12, 22, 99, 111, 109, 46, 97, 117, 116, 111, 110, 97, 118, 105, 46, 97, 112, 105, 115, 101, 114, 118, 101, 114, 48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -15, -27, -128, -56, 118, -59, 62, -127, 79, 125, -36, 121, 0, 63, -125, -30, 118, 5, -85, -121, 91, 39, 90, 123, 72, -126, -83, -41, -45, -77, -42, -120, -81, 23, -2, -121, -29, 123, -7, 22, -114, -20, -25, 74, 67, -43, 65, 124, -7, 11, -72, 38, -123, 16, -58, 80, 32, 58, -33, 14, 11, 36, 60, 13, -121, 100, 105, -32, 123, -31, 114, -101, -41, 12, 100, 33, -120, 63, 126, -123, 48, 55, 80, -116, 28, -10, 125, 59, -41, -95, -126, 118, -70, 43, -127, 9, 93, -100, 81, -19, -114, -41, 85, -103, -37, -116, 118, 72, 86, 125, -43, -92, -11, 63, 69, -38, -10, -65, 126, -53, -115, 60, 62, -86, -80, 1, 39, 19, 2, 3, 1, 0, 1, -93, 80, 48, 78, 48, 29, 6, 3, 85, 29, 14, 4, 22, 4, 20, -29, 63, 48, -79, -113, -13, 26, 85, 22, -27, 93, -5, 122, -103, -109, 14, -18, 6, -13, -109, 48, 31, 6, 3, 85, 29, 35, 4, 24, 48, 22, -128, 20, -29, 63, 48, -79, -113, -13, 26, 85, 22, -27, 93, -5, 122, -103, -109, 14, -18, 6, -13, -109, 48, 12, 6, 3, 85, 29, 19, 4, 5, 48, 3, 1, 1, -1, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 5, 5, 0, 3, -127, -127, 0, -32, -74, 55, -125, -58, -128, 15, -62, 100, -60, 3, -86, 81, 112, -61, -56, -69, -126, 8, 99, -100, -38, -108, -56, -122, 125, 19, -64, -61, 90, 85, -47, -8, -123, -103, 105, 77, -32, -65, -62, -28, 67, -28, -78, 116, -49, 120, -2, 33, 13, 47, 46, -5, -112, 3, -101, -125, -115, 92, -124, 58, 80, 107, -67, 82, 6, -63, 39, -90, -1, 85, -58, 82, -115, 119, 13, -4, -32, 0, -98, 100, -41, 94, -75, 75, -103, 126, -80, 85, 40, -27, 60, 105, 28, -27, -21, -15, -98, 103, -88, -109, 35, -119, -27, -26, -122, 113, 63, 35, -33, 70, 23, 33, -23, 66, 108, 56, 112, 46, -85, -123, -123, 33, 118, 27, 96, -7, -103} // fill-array
            r0 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Throwable -> 0x0029, all -> 0x0026 }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0029, all -> 0x0026 }
            java.lang.String r3 = "X.509"
            java.security.cert.CertificateFactory r3 = java.security.cert.CertificateFactory.getInstance(r3)     // Catch:{ Throwable -> 0x0023 }
            java.lang.String r2 = "RSA"
            java.security.KeyFactory r2 = java.security.KeyFactory.getInstance(r2)     // Catch:{ Throwable -> 0x0023 }
            java.security.cert.Certificate r3 = r3.generateCertificate(r1)     // Catch:{ Throwable -> 0x0021 }
            r1.close()
            goto L_0x0035
        L_0x0021:
            r3 = move-exception
            goto L_0x002c
        L_0x0023:
            r3 = move-exception
            r2 = r0
            goto L_0x002c
        L_0x0026:
            r3 = move-exception
            r1 = r0
            goto L_0x0057
        L_0x0029:
            r3 = move-exception
            r1 = r0
            r2 = r1
        L_0x002c:
            r3.printStackTrace()     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0034
            r1.close()
        L_0x0034:
            r3 = r0
        L_0x0035:
            if (r3 == 0) goto L_0x004c
            if (r2 != 0) goto L_0x003a
            goto L_0x004c
        L_0x003a:
            java.security.spec.X509EncodedKeySpec r0 = new java.security.spec.X509EncodedKeySpec
            java.security.PublicKey r3 = r3.getPublicKey()
            byte[] r3 = r3.getEncoded()
            r0.<init>(r3)
            java.security.PublicKey r3 = r2.generatePublic(r0)
            return r3
        L_0x004c:
            boolean r3 = com.autonavi.indoor.util.L.isLogging
            if (r3 == 0) goto L_0x0055
            java.lang.String r3 = "PublicKey has null"
            com.autonavi.indoor.util.L.d(r3)
        L_0x0055:
            return r0
        L_0x0056:
            r3 = move-exception
        L_0x0057:
            if (r1 == 0) goto L_0x005c
            r1.close()
        L_0x005c:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.util.EncryptHelper.getPublicKey(android.content.Context):java.security.PublicKey");
    }

    static byte[] rsaEncrypt(byte[] bArr, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher instance = Cipher.getInstance(RSAMODE);
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    static {
        for (int i = 0; i < 128; i++) {
            base64DecodeChars[i] = -1;
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            base64DecodeChars[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            base64DecodeChars[i3] = (byte) ((i3 - 97) + 26);
        }
        for (int i4 = 48; i4 <= 57; i4++) {
            base64DecodeChars[i4] = (byte) ((i4 - 48) + 52);
        }
        base64DecodeChars[43] = 62;
        base64DecodeChars[47] = 63;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0024 A[LOOP:2: B:11:0x0024->B:14:0x0031, LOOP_START, PHI: r4 
      PHI: (r4v1 int) = (r4v0 int), (r4v8 int) binds: [B:10:0x0022, B:14:0x0031] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x007d A[LOOP:0: B:5:0x0010->B:36:0x007d, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0087 A[EDGE_INSN: B:41:0x0087->B:37:0x0087 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0087 A[EDGE_INSN: B:42:0x0087->B:37:0x0087 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0087 A[EDGE_INSN: B:44:0x0087->B:37:0x0087 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0087 A[EDGE_INSN: B:45:0x0087->B:37:0x0087 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decodeBase64(java.lang.String r8) {
        /*
            r0 = 0
            if (r8 != 0) goto L_0x0006
            byte[] r8 = new byte[r0]
            return r8
        L_0x0006:
            byte[] r8 = r8.getBytes()
            int r1 = r8.length
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>(r1)
        L_0x0010:
            if (r0 >= r1) goto L_0x0087
        L_0x0012:
            byte[] r3 = base64DecodeChars
            int r4 = r0 + 1
            byte r0 = r8[r0]
            byte r0 = r3[r0]
            r3 = -1
            if (r4 >= r1) goto L_0x0022
            if (r0 == r3) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            r0 = r4
            goto L_0x0012
        L_0x0022:
            if (r0 == r3) goto L_0x0087
        L_0x0024:
            byte[] r5 = base64DecodeChars
            int r6 = r4 + 1
            byte r4 = r8[r4]
            byte r4 = r5[r4]
            if (r6 >= r1) goto L_0x0033
            if (r4 == r3) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            r4 = r6
            goto L_0x0024
        L_0x0033:
            if (r4 == r3) goto L_0x0087
            int r0 = r0 << 2
            r5 = r4 & 48
            int r5 = r5 >>> 4
            r0 = r0 | r5
            r2.write(r0)
        L_0x003f:
            int r0 = r6 + 1
            byte r5 = r8[r6]
            r6 = 61
            if (r5 != r6) goto L_0x004c
            byte[] r8 = r2.toByteArray()
            return r8
        L_0x004c:
            byte[] r7 = base64DecodeChars
            byte r5 = r7[r5]
            if (r0 >= r1) goto L_0x0057
            if (r5 == r3) goto L_0x0055
            goto L_0x0057
        L_0x0055:
            r6 = r0
            goto L_0x003f
        L_0x0057:
            if (r5 == r3) goto L_0x0087
            r4 = r4 & 15
            int r4 = r4 << 4
            r7 = r5 & 60
            int r7 = r7 >>> 2
            r4 = r4 | r7
            r2.write(r4)
        L_0x0065:
            int r4 = r0 + 1
            byte r0 = r8[r0]
            if (r0 != r6) goto L_0x0070
            byte[] r8 = r2.toByteArray()
            return r8
        L_0x0070:
            byte[] r7 = base64DecodeChars
            byte r0 = r7[r0]
            if (r4 >= r1) goto L_0x007b
            if (r0 == r3) goto L_0x0079
            goto L_0x007b
        L_0x0079:
            r0 = r4
            goto L_0x0065
        L_0x007b:
            if (r0 == r3) goto L_0x0087
            r3 = r5 & 3
            int r3 = r3 << 6
            r0 = r0 | r3
            r2.write(r0)
            r0 = r4
            goto L_0x0010
        L_0x0087:
            byte[] r8 = r2.toByteArray()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.util.EncryptHelper.decodeBase64(java.lang.String):byte[]");
    }

    public static byte[] privateAesEncrypt(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String[] split = new StringBuffer("16,16,18,77,15,911,121,77,121,911,38,77,911,99,86,67,611,96,48,77,84,911,38,67,021,301,86,67,611,98,48,77,511,77,48,97,511,58,48,97,511,84,501,87,511,96,48,77,221,911,38,77,121,37,86,67,25,301,86,67,021,96,86,67,021,701,86,67,35,56,86,67,611,37,221,87").reverse().toString().split(",");
        byte[] bArr3 = new byte[split.length];
        for (int i = 0; i < split.length; i++) {
            bArr3[i] = Byte.parseByte(split[i]);
        }
        String[] split2 = new StringBuffer(new String(decodeBase64(new String(bArr3)))).reverse().toString().split(",");
        byte[] bArr4 = new byte[split2.length];
        for (int i2 = 0; i2 < split2.length; i2++) {
            bArr4[i2] = Byte.parseByte(split2[i2]);
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr4);
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance(AESMODE);
        try {
            instance.init(1, secretKeySpec, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return instance.doFinal(bArr2);
    }

    public static String privateEncodeBase64(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            byte b = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(base64EncodeChars[b >>> 2]);
                stringBuffer.append(base64EncodeChars[(b & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i3 = i2 + 1;
            byte b2 = bArr[i2] & 255;
            if (i3 == length) {
                stringBuffer.append(base64EncodeChars[b >>> 2]);
                stringBuffer.append(base64EncodeChars[((b & 3) << 4) | ((b2 & 240) >>> 4)]);
                stringBuffer.append(base64EncodeChars[(b2 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            int i4 = i3 + 1;
            byte b3 = bArr[i3] & 255;
            stringBuffer.append(base64EncodeChars[b >>> 2]);
            stringBuffer.append(base64EncodeChars[((b & 3) << 4) | ((b2 & 240) >>> 4)]);
            stringBuffer.append(base64EncodeChars[((b2 & 15) << 2) | ((b3 & 192) >>> 6)]);
            stringBuffer.append(base64EncodeChars[b3 & 63]);
            i = i4;
        }
        return stringBuffer.toString();
    }

    public static String privateRsaAesData(Context context, byte[] bArr) throws InvalidKeyException, IOException, InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, CertificateException {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        instance.init(256);
        byte[] encoded = instance.generateKey().getEncoded();
        PublicKey publicKey = getPublicKey(context);
        if (publicKey == null) {
            if (L.isLogging) {
                L.d((String) "key is empty");
            }
            return null;
        }
        byte[] rsaEncrypt = rsaEncrypt(encoded, publicKey);
        byte[] privateAesEncrypt = privateAesEncrypt(encoded, bArr);
        byte[] bArr2 = new byte[(rsaEncrypt.length + privateAesEncrypt.length)];
        System.arraycopy(rsaEncrypt, 0, bArr2, 0, rsaEncrypt.length);
        System.arraycopy(privateAesEncrypt, 0, bArr2, rsaEncrypt.length, privateAesEncrypt.length);
        byte[] bArr3 = new byte[0];
        try {
            bArr3 = Utils.gzip(bArr2);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        return bArr3 != null ? privateEncodeBase64(bArr3) : "";
    }

    public static String getUTDID(Context context) {
        try {
            if (mUtidid != null && !"".equals(mUtidid)) {
                return mUtidid;
            }
            if (context.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0) {
                mUtidid = System.getString(context.getContentResolver(), UM_SETTINGS_STORAGE_NEW);
            }
            if (mUtidid != null && !"".equals(mUtidid)) {
                return mUtidid;
            }
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    StringBuilder sb = new StringBuilder();
                    sb.append(absolutePath);
                    sb.append(UTID_FILE);
                    File file = new File(sb.toString());
                    if (file.exists()) {
                        SAXParserFactory.newInstance().newSAXParser().parse(file, new UTDXMLHandler());
                    }
                }
            } catch (Exception e) {
                if (L.isLogging) {
                    L.d((Throwable) e);
                }
            }
            return mUtidid;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
