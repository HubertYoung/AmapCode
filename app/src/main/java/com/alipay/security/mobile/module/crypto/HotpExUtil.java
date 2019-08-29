package com.alipay.security.mobile.module.crypto;

public final class HotpExUtil {
    public static final int HOTPEX_HASH_LEN = 20;
    public static final int MAX_CODE_LEN = 8;
    public static final byte[] salt1 = Hex.decode((String) "7B726A5DDD72CBF8D1700FB6EB278AFD7559C40A3761E5A71614D0AC9461ED8EE9F6AAEB443CD648");
    public static final byte[] salt2 = Hex.decode((String) "C9582A82777392CAA65AD7F5228150E3F966C09D6A00288B5C6E0CFB441E111B713B4E0822A8C830");

    private HotpExUtil() {
    }

    public static byte[] process(byte[] bArr) throws Exception {
        byte[] bArr2 = new byte[20];
        if (!ByteUtil.initWithByte(bArr2, 0, 0, 20)) {
            throw new IllegalStateException("failed to init hash1.");
        }
        byte[] bArr3 = new byte[20];
        if (!ByteUtil.initWithByte(bArr3, 0, 0, 20)) {
            throw new IllegalStateException("failed to init hash2.");
        }
        byte[] digestWithHmacSha1 = CryptoUtil.digestWithHmacSha1(bArr, salt1);
        System.arraycopy(digestWithHmacSha1, 0, bArr2, 0, digestWithHmacSha1.length);
        byte[] digestWithHmacSha12 = CryptoUtil.digestWithHmacSha1(bArr, salt2);
        System.arraycopy(digestWithHmacSha12, 0, bArr3, 0, digestWithHmacSha12.length);
        byte[] bArr4 = new byte[8];
        byte b = bArr2[19] & 15;
        bArr4[3] = (byte) (bArr2[b] & Byte.MAX_VALUE);
        bArr4[2] = (byte) (bArr2[b + 1] & 255);
        bArr4[1] = (byte) (bArr2[b + 2] & 255);
        bArr4[0] = (byte) (bArr2[b + 3] & 255);
        byte b2 = bArr3[19] & 15;
        bArr4[4] = (byte) (bArr3[b2] & Byte.MAX_VALUE);
        bArr4[5] = (byte) (bArr3[b2 + 1] & 255);
        bArr4[6] = (byte) (bArr3[b2 + 2] & 255);
        bArr4[7] = (byte) (bArr3[b2 + 3] & 255);
        return bArr4;
    }

    public static byte[] process(byte[] bArr, int i) throws Exception {
        byte[] process = process(bArr);
        if (process == null || i <= 0) {
            return null;
        }
        if (i >= 8) {
            return process;
        }
        byte[] bArr2 = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr2[i2] = process[i2];
        }
        return bArr2;
    }
}
