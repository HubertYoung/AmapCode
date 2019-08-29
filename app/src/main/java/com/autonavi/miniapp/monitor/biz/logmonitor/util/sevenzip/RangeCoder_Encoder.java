package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import com.uc.webview.export.extension.UCCore;
import java.io.IOException;
import java.io.OutputStream;

public class RangeCoder_Encoder {
    private static int[] ProbPrices = new int[512];
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    public static final int kNumBitPriceShiftBits = 6;
    static final int kNumMoveBits = 5;
    static final int kNumMoveReducingBits = 2;
    static final int kTopMask = -16777216;
    long Low;
    int Range;
    OutputStream Stream;
    int _cache;
    int _cacheSize;
    long _position;

    public void SetStream(OutputStream outputStream) {
        this.Stream = outputStream;
    }

    public void ReleaseStream() {
        this.Stream = null;
    }

    public void Init() {
        this._position = 0;
        this.Low = 0;
        this.Range = -1;
        this._cacheSize = 1;
        this._cache = 0;
    }

    public void FlushData() throws IOException {
        for (int i = 0; i < 5; i++) {
            ShiftLow();
        }
    }

    public void FlushStream() throws IOException {
        this.Stream.flush();
    }

    public void ShiftLow() throws IOException {
        int i;
        int i2 = (int) (this.Low >>> 32);
        if (i2 != 0 || this.Low < 4278190080L) {
            this._position += (long) this._cacheSize;
            int i3 = this._cache;
            do {
                this.Stream.write(i3 + i2);
                i3 = 255;
                i = this._cacheSize - 1;
                this._cacheSize = i;
            } while (i != 0);
            this._cache = ((int) this.Low) >>> 24;
        }
        this._cacheSize++;
        this.Low = (this.Low & 16777215) << 8;
    }

    public void EncodeDirectBits(int i, int i2) throws IOException {
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            this.Range >>>= 1;
            if (((i >>> i3) & 1) == 1) {
                this.Low += (long) this.Range;
            }
            if ((this.Range & -16777216) == 0) {
                this.Range <<= 8;
                ShiftLow();
            }
        }
    }

    public long GetProcessedSizeAdd() {
        return ((long) this._cacheSize) + this._position + 4;
    }

    public static void InitBitModels(short[] sArr) {
        for (int i = 0; i < sArr.length; i++) {
            sArr[i] = 1024;
        }
    }

    public void Encode(short[] sArr, int i, int i2) throws IOException {
        short s = sArr[i];
        int i3 = (this.Range >>> 11) * s;
        if (i2 == 0) {
            this.Range = i3;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
        } else {
            this.Low += ((long) i3) & 4294967295L;
            this.Range -= i3;
            sArr[i] = (short) (s - (s >>> 5));
        }
        if ((this.Range & -16777216) == 0) {
            this.Range <<= 8;
            ShiftLow();
        }
    }

    static {
        for (int i = 8; i >= 0; i--) {
            int i2 = 9 - i;
            int i3 = i2 - 1;
            int i4 = 1 << i2;
            for (int i5 = 1 << i3; i5 < i4; i5++) {
                ProbPrices[i5] = (i << 6) + (((i4 - i5) << 6) >>> i3);
            }
        }
    }

    public static int GetPrice(int i, int i2) {
        return ProbPrices[(((i - i2) ^ (-i2)) & UCCore.SPEEDUP_DEXOPT_POLICY_ALL) >>> 2];
    }

    public static int GetPrice0(int i) {
        return ProbPrices[i >>> 2];
    }

    public static int GetPrice1(int i) {
        return ProbPrices[(2048 - i) >>> 2];
    }
}
