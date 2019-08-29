package com.xiaomi.push.service;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.a;

public class ax {
    private static int a = 8;
    private byte[] b = new byte[256];
    private int c = 0;
    private int d = 0;
    private int e = -666;

    public static int a(byte b2) {
        return b2 >= 0 ? b2 : b2 + 256;
    }

    private void a(int i, byte[] bArr, boolean z) {
        int length = bArr.length;
        for (int i2 = 0; i2 < 256; i2++) {
            this.b[i2] = (byte) i2;
        }
        this.d = 0;
        this.c = 0;
        while (this.c < i) {
            this.d = ((this.d + a(this.b[this.c])) + a(bArr[this.c % length])) % 256;
            a(this.b, this.c, this.d);
            this.c++;
        }
        if (i != 256) {
            this.e = ((this.d + a(this.b[i])) + a(bArr[i % length])) % 256;
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("S_");
            int i3 = i - 1;
            sb.append(i3);
            sb.append(":");
            for (int i4 = 0; i4 <= i; i4++) {
                sb.append(Token.SEPARATOR);
                sb.append(a(this.b[i4]));
            }
            sb.append("   j_");
            sb.append(i3);
            sb.append("=");
            sb.append(this.d);
            sb.append("   j_");
            sb.append(i);
            sb.append("=");
            sb.append(this.e);
            sb.append("   S_");
            sb.append(i3);
            sb.append("[j_");
            sb.append(i3);
            sb.append("]=");
            sb.append(a(this.b[this.d]));
            sb.append("   S_");
            sb.append(i3);
            sb.append("[j_");
            sb.append(i);
            sb.append("]=");
            sb.append(a(this.b[this.e]));
            if (this.b[1] != 0) {
                sb.append("   S[1]!=0");
            }
            b.a(sb.toString());
        }
    }

    private void a(byte[] bArr) {
        a(256, bArr, false);
    }

    private static void a(byte[] bArr, int i, int i2) {
        byte b2 = bArr[i];
        bArr[i] = bArr[i2];
        bArr[i2] = b2;
    }

    public static byte[] a(String str, String str2) {
        byte[] a2 = a.a(str);
        byte[] bytes = str2.getBytes();
        byte[] bArr = new byte[(a2.length + 1 + bytes.length)];
        for (int i = 0; i < a2.length; i++) {
            bArr[i] = a2[i];
        }
        bArr[a2.length] = 95;
        for (int i2 = 0; i2 < bytes.length; i2++) {
            bArr[a2.length + 1 + i2] = bytes[i2];
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, String str) {
        return a(bArr, a.a(str));
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr2.length];
        ax axVar = new ax();
        axVar.a(bArr);
        axVar.b();
        for (int i = 0; i < bArr2.length; i++) {
            bArr3[i] = (byte) (bArr2[i] ^ axVar.a());
        }
        return bArr3;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z, int i, int i2) {
        byte[] bArr3;
        int i3;
        if (i < 0 || i > bArr2.length || i + i2 > bArr2.length) {
            StringBuilder sb = new StringBuilder("start = ");
            sb.append(i);
            sb.append(" len = ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        if (!z) {
            bArr3 = new byte[i2];
            i3 = 0;
        } else {
            bArr3 = bArr2;
            i3 = i;
        }
        ax axVar = new ax();
        axVar.a(bArr);
        axVar.b();
        for (int i4 = 0; i4 < i2; i4++) {
            bArr3[i3 + i4] = (byte) (bArr2[i + i4] ^ axVar.a());
        }
        return bArr3;
    }

    private void b() {
        this.d = 0;
        this.c = 0;
    }

    /* access modifiers changed from: 0000 */
    public byte a() {
        this.c = (this.c + 1) % 256;
        this.d = (this.d + a(this.b[this.c])) % 256;
        a(this.b, this.c, this.d);
        return this.b[(a(this.b[this.c]) + a(this.b[this.d])) % 256];
    }
}
