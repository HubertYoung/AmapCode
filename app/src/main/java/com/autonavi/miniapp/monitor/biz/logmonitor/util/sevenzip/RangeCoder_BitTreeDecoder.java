package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import java.io.IOException;

public class RangeCoder_BitTreeDecoder {
    short[] Models;
    int NumBitLevels;

    public RangeCoder_BitTreeDecoder(int i) {
        this.NumBitLevels = i;
        this.Models = new short[(1 << i)];
    }

    public void Init() {
        RangeCoder_Decoder.InitBitModels(this.Models);
    }

    public int Decode(RangeCoder_Decoder rangeCoder_Decoder) throws IOException {
        int i = 1;
        for (int i2 = this.NumBitLevels; i2 != 0; i2--) {
            i = rangeCoder_Decoder.DecodeBit(this.Models, i) + (i << 1);
        }
        return i - (1 << this.NumBitLevels);
    }

    public int ReverseDecode(RangeCoder_Decoder rangeCoder_Decoder) throws IOException {
        int i = 1;
        int i2 = 0;
        for (int i3 = 0; i3 < this.NumBitLevels; i3++) {
            int DecodeBit = rangeCoder_Decoder.DecodeBit(this.Models, i);
            i = (i << 1) + DecodeBit;
            i2 |= DecodeBit << i3;
        }
        return i2;
    }

    public static int ReverseDecode(short[] sArr, int i, RangeCoder_Decoder rangeCoder_Decoder, int i2) throws IOException {
        int i3 = 1;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int DecodeBit = rangeCoder_Decoder.DecodeBit(sArr, i + i3);
            i3 = (i3 << 1) + DecodeBit;
            i4 |= DecodeBit << i5;
        }
        return i4;
    }
}
