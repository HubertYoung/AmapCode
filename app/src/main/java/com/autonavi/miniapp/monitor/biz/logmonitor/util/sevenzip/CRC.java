package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

public class CRC {
    public static int[] Table = new int[256];
    int _value = -1;

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (i2 & 1) != 0 ? (i2 >>> 1) ^ -306674912 : i2 >>> 1;
            }
            Table[i] = i2;
        }
    }

    public void Init() {
        this._value = -1;
    }

    public void Update(byte[] bArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            this._value = Table[(this._value ^ bArr[i + i3]) & 255] ^ (this._value >>> 8);
        }
    }

    public void Update(byte[] bArr) {
        for (byte b : bArr) {
            this._value = Table[(this._value ^ b) & 255] ^ (this._value >>> 8);
        }
    }

    public void UpdateByte(int i) {
        this._value = Table[(i ^ this._value) & 255] ^ (this._value >>> 8);
    }

    public int GetDigest() {
        return ~this._value;
    }
}
