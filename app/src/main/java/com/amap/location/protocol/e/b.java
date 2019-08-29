package com.amap.location.protocol.e;

import android.text.TextUtils;
import java.io.ByteArrayOutputStream;

/* compiled from: RequestDataBuffer */
public class b {
    private ByteArrayOutputStream a = new ByteArrayOutputStream(1024);

    public void a(String str) {
        a(str, 1);
    }

    public void a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            a(0, i);
            return;
        }
        try {
            byte[] bytes = str.getBytes("GBK");
            int min = Math.min(((int) Math.pow(2.0d, (double) ((i * 8) - 1))) - 1, bytes.length);
            a(min, i);
            this.a.write(bytes, 0, min);
        } catch (Exception unused) {
            a(0, i);
        }
    }

    public void a(byte b) {
        this.a.write(b);
    }

    public void a(int i, int i2) {
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((i >> (i3 * 8)) & 255);
        }
        this.a.write(bArr, 0, i2);
    }

    public void a(long j, int i) {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) ((int) ((j >> (i2 * 8)) & 255));
        }
        this.a.write(bArr, 0, i);
    }

    public void a(long j) {
        byte[] bArr = new byte[6];
        for (int i = 0; i < 6; i++) {
            bArr[(6 - i) - 1] = (byte) ((int) ((j >> (i * 8)) & 255));
        }
        this.a.write(bArr, 0, 6);
    }

    public void a(byte[] bArr, int i) {
        a(bArr, bArr.length, i);
    }

    public void a(byte[] bArr, int i, int i2) {
        int min = Math.min(bArr.length, Math.min(((int) Math.pow(2.0d, (double) ((i2 * 8) - 1))) - 1, i));
        a(min, i2);
        this.a.write(bArr, 0, min);
    }

    public byte[] a() {
        return this.a.toByteArray();
    }
}
