package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import java.io.IOException;
import java.io.InputStream;

public class RangeCoder_Decoder {
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    static final int kNumMoveBits = 5;
    static final int kTopMask = -16777216;
    int Code;
    int Range;
    InputStream Stream;

    public final void SetStream(InputStream inputStream) {
        this.Stream = inputStream;
    }

    public final void ReleaseStream() {
        this.Stream = null;
    }

    public final void Init() throws IOException {
        this.Code = 0;
        this.Range = -1;
        for (int i = 0; i < 5; i++) {
            this.Code = (this.Code << 8) | this.Stream.read();
        }
    }

    public final int DecodeDirectBits(int i) throws IOException {
        int i2 = 0;
        while (i != 0) {
            this.Range >>>= 1;
            int i3 = (this.Code - this.Range) >>> 31;
            this.Code -= this.Range & (i3 - 1);
            i2 = (i2 << 1) | (1 - i3);
            if ((this.Range & -16777216) == 0) {
                this.Code = (this.Code << 8) | this.Stream.read();
                this.Range <<= 8;
            }
            i--;
        }
        return i2;
    }

    public int DecodeBit(short[] sArr, int i) throws IOException {
        short s = sArr[i];
        int i2 = (this.Range >>> 11) * s;
        if ((this.Code ^ Integer.MIN_VALUE) < (Integer.MIN_VALUE ^ i2)) {
            this.Range = i2;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
            if ((this.Range & -16777216) == 0) {
                this.Code = (this.Code << 8) | this.Stream.read();
                this.Range <<= 8;
            }
            return 0;
        }
        this.Range -= i2;
        this.Code -= i2;
        sArr[i] = (short) (s - (s >>> 5));
        if ((this.Range & -16777216) == 0) {
            this.Code = (this.Code << 8) | this.Stream.read();
            this.Range <<= 8;
        }
        return 1;
    }

    public static void InitBitModels(short[] sArr) {
        for (int i = 0; i < sArr.length; i++) {
            sArr[i] = 1024;
        }
    }
}
