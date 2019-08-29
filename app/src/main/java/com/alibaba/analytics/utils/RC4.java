package com.alibaba.analytics.utils;

public class RC4 {
    private static final String RC4_PK = "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK";

    static class RC4Key {
        int[] state;
        int x;
        int y;

        private RC4Key() {
            this.state = new int[256];
        }
    }

    public static byte[] rc4(byte[] bArr) {
        return rc4(bArr, RC4_PK);
    }

    private static byte[] rc4(byte[] bArr, String str) {
        if (!(bArr == null || str == null)) {
            RC4Key prepareKey = prepareKey(str);
            if (prepareKey != null) {
                return doRc4(bArr, prepareKey);
            }
        }
        return null;
    }

    private static RC4Key prepareKey(String str) {
        if (str == null) {
            return null;
        }
        RC4Key rC4Key = new RC4Key();
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            rC4Key.state[i2] = i2;
        }
        rC4Key.x = 0;
        rC4Key.y = 0;
        int i3 = 0;
        int i4 = 0;
        while (i < 256) {
            try {
                i4 = ((str.charAt(i3) + rC4Key.state[i]) + i4) % 256;
                int i5 = rC4Key.state[i];
                int[] iArr = rC4Key.state;
                iArr[i] = iArr[i4];
                rC4Key.state[i4] = i5;
                i3 = (i3 + 1) % str.length();
                i++;
            } catch (Exception unused) {
                return null;
            }
        }
        return rC4Key;
    }

    private static byte[] doRc4(byte[] bArr, RC4Key rC4Key) {
        if (bArr == null || rC4Key == null) {
            return null;
        }
        int i = rC4Key.x;
        int i2 = rC4Key.y;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) % 256;
            i2 = (rC4Key.state[i] + i2) % 256;
            int i4 = rC4Key.state[i];
            int[] iArr = rC4Key.state;
            iArr[i] = iArr[i2];
            rC4Key.state[i2] = i4;
            bArr[i3] = (byte) (rC4Key.state[(rC4Key.state[i] + rC4Key.state[i2]) % 256] ^ bArr[i3]);
        }
        rC4Key.x = i;
        rC4Key.y = i2;
        return bArr;
    }
}
