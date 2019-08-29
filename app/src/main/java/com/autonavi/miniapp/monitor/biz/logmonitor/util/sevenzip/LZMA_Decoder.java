package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import java.io.IOException;

public class LZMA_Decoder {
    int m_DictionarySize = -1;
    int m_DictionarySizeCheck = -1;
    short[] m_IsMatchDecoders = new short[192];
    short[] m_IsRep0LongDecoders = new short[192];
    short[] m_IsRepDecoders = new short[12];
    short[] m_IsRepG0Decoders = new short[12];
    short[] m_IsRepG1Decoders = new short[12];
    short[] m_IsRepG2Decoders = new short[12];
    LenDecoder m_LenDecoder = new LenDecoder();
    LiteralDecoder m_LiteralDecoder = new LiteralDecoder();
    LZ_OutWindow m_OutWindow = new LZ_OutWindow();
    RangeCoder_BitTreeDecoder m_PosAlignDecoder = new RangeCoder_BitTreeDecoder(4);
    short[] m_PosDecoders = new short[114];
    RangeCoder_BitTreeDecoder[] m_PosSlotDecoder = new RangeCoder_BitTreeDecoder[4];
    int m_PosStateMask;
    RangeCoder_Decoder m_RangeDecoder = new RangeCoder_Decoder();
    LenDecoder m_RepLenDecoder = new LenDecoder();

    class LenDecoder {
        short[] m_Choice = new short[2];
        RangeCoder_BitTreeDecoder m_HighCoder = new RangeCoder_BitTreeDecoder(8);
        RangeCoder_BitTreeDecoder[] m_LowCoder = new RangeCoder_BitTreeDecoder[16];
        RangeCoder_BitTreeDecoder[] m_MidCoder = new RangeCoder_BitTreeDecoder[16];
        int m_NumPosStates = 0;

        LenDecoder() {
        }

        public void Create(int i) {
            while (this.m_NumPosStates < i) {
                this.m_LowCoder[this.m_NumPosStates] = new RangeCoder_BitTreeDecoder(3);
                this.m_MidCoder[this.m_NumPosStates] = new RangeCoder_BitTreeDecoder(3);
                this.m_NumPosStates++;
            }
        }

        public void Init() {
            RangeCoder_Decoder.InitBitModels(this.m_Choice);
            for (int i = 0; i < this.m_NumPosStates; i++) {
                this.m_LowCoder[i].Init();
                this.m_MidCoder[i].Init();
            }
            this.m_HighCoder.Init();
        }

        public int Decode(RangeCoder_Decoder rangeCoder_Decoder, int i) throws IOException {
            int i2;
            if (rangeCoder_Decoder.DecodeBit(this.m_Choice, 0) == 0) {
                return this.m_LowCoder[i].Decode(rangeCoder_Decoder);
            }
            if (rangeCoder_Decoder.DecodeBit(this.m_Choice, 1) == 0) {
                i2 = this.m_MidCoder[i].Decode(rangeCoder_Decoder) + 8;
            } else {
                i2 = this.m_HighCoder.Decode(rangeCoder_Decoder) + 8 + 8;
            }
            return i2;
        }
    }

    class LiteralDecoder {
        Decoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        class Decoder2 {
            short[] m_Decoders = new short[768];

            Decoder2() {
            }

            public void Init() {
                RangeCoder_Decoder.InitBitModels(this.m_Decoders);
            }

            public byte DecodeNormal(RangeCoder_Decoder rangeCoder_Decoder) throws IOException {
                int i = 1;
                do {
                    i = rangeCoder_Decoder.DecodeBit(this.m_Decoders, i) | (i << 1);
                } while (i < 256);
                return (byte) i;
            }

            public byte DecodeWithMatchByte(RangeCoder_Decoder rangeCoder_Decoder, byte b) throws IOException {
                int i = 1;
                while (true) {
                    int i2 = (b >> 7) & 1;
                    b = (byte) (b << 1);
                    int DecodeBit = rangeCoder_Decoder.DecodeBit(this.m_Decoders, ((i2 + 1) << 8) + i);
                    i = (i << 1) | DecodeBit;
                    if (i2 == DecodeBit) {
                        if (i >= 256) {
                            break;
                        }
                    } else {
                        while (i < 256) {
                            i = (i << 1) | rangeCoder_Decoder.DecodeBit(this.m_Decoders, i);
                        }
                    }
                }
                return (byte) i;
            }
        }

        LiteralDecoder() {
        }

        public void Create(int i, int i2) {
            if (this.m_Coders == null || this.m_NumPrevBits != i2 || this.m_NumPosBits != i) {
                this.m_NumPosBits = i;
                this.m_PosMask = (1 << i) - 1;
                this.m_NumPrevBits = i2;
                int i3 = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
                this.m_Coders = new Decoder2[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    this.m_Coders[i4] = new Decoder2();
                }
            }
        }

        public void Init() {
            int i = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i2 = 0; i2 < i; i2++) {
                this.m_Coders[i2].Init();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r4v0, types: [byte, int] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder.LiteralDecoder.Decoder2 GetDecoder(int r3, int r4) {
            /*
                r2 = this;
                com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder$LiteralDecoder$Decoder2[] r0 = r2.m_Coders
                int r1 = r2.m_PosMask
                r3 = r3 & r1
                int r1 = r2.m_NumPrevBits
                int r3 = r3 << r1
                r4 = r4 & 255(0xff, float:3.57E-43)
                int r1 = r2.m_NumPrevBits
                int r1 = 8 - r1
                int r4 = r4 >>> r1
                int r3 = r3 + r4
                r3 = r0[r3]
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder.LiteralDecoder.GetDecoder(int, byte):com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder$LiteralDecoder$Decoder2");
        }
    }

    public LZMA_Decoder() {
        for (int i = 0; i < 4; i++) {
            this.m_PosSlotDecoder[i] = new RangeCoder_BitTreeDecoder(6);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean SetDictionarySize(int i) {
        if (i < 0) {
            return false;
        }
        if (this.m_DictionarySize != i) {
            this.m_DictionarySize = i;
            this.m_DictionarySizeCheck = Math.max(this.m_DictionarySize, 1);
            this.m_OutWindow.Create(Math.max(this.m_DictionarySizeCheck, 4096));
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean SetLcLpPb(int i, int i2, int i3) {
        if (i > 8 || i2 > 4 || i3 > 4) {
            return false;
        }
        this.m_LiteralDecoder.Create(i2, i);
        int i4 = 1 << i3;
        this.m_LenDecoder.Create(i4);
        this.m_RepLenDecoder.Create(i4);
        this.m_PosStateMask = i4 - 1;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void Init() throws IOException {
        this.m_OutWindow.Init(false);
        RangeCoder_Decoder.InitBitModels(this.m_IsMatchDecoders);
        RangeCoder_Decoder.InitBitModels(this.m_IsRep0LongDecoders);
        RangeCoder_Decoder.InitBitModels(this.m_IsRepDecoders);
        RangeCoder_Decoder.InitBitModels(this.m_IsRepG0Decoders);
        RangeCoder_Decoder.InitBitModels(this.m_IsRepG1Decoders);
        RangeCoder_Decoder.InitBitModels(this.m_IsRepG2Decoders);
        RangeCoder_Decoder.InitBitModels(this.m_PosDecoders);
        this.m_LiteralDecoder.Init();
        for (int i = 0; i < 4; i++) {
            this.m_PosSlotDecoder[i].Init();
        }
        this.m_LenDecoder.Init();
        this.m_RepLenDecoder.Init();
        this.m_PosAlignDecoder.Init();
        this.m_RangeDecoder.Init();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0124, code lost:
        r0.m_OutWindow.Flush();
        r0.m_OutWindow.ReleaseStream();
        r0.m_RangeDecoder.ReleaseStream();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0134, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean Code(java.io.InputStream r19, java.io.OutputStream r20, long r21) throws java.io.IOException {
        /*
            r18 = this;
            r0 = r18
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r2 = r0.m_RangeDecoder
            r3 = r19
            r2.SetStream(r3)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r2 = r0.m_OutWindow
            r3 = r20
            r2.SetStream(r3)
            r18.Init()
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateInit()
            r3 = 0
            r6 = r3
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
        L_0x001f:
            int r13 = (r21 > r3 ? 1 : (r21 == r3 ? 0 : -1))
            if (r13 < 0) goto L_0x0027
            int r13 = (r6 > r21 ? 1 : (r6 == r21 ? 0 : -1))
            if (r13 >= 0) goto L_0x0124
        L_0x0027:
            int r13 = (int) r6
            int r3 = r0.m_PosStateMask
            r3 = r3 & r13
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            short[] r5 = r0.m_IsMatchDecoders
            int r15 = r2 << 4
            int r14 = r15 + r3
            int r4 = r4.DecodeBit(r5, r14)
            if (r4 != 0) goto L_0x0069
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder$LiteralDecoder r3 = r0.m_LiteralDecoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder$LiteralDecoder$Decoder2 r3 = r3.GetDecoder(r13, r8)
            boolean r4 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateIsCharState(r2)
            if (r4 != 0) goto L_0x0053
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r5 = r0.m_OutWindow
            byte r5 = r5.GetByte(r9)
            byte r3 = r3.DecodeWithMatchByte(r4, r5)
        L_0x0051:
            r8 = r3
            goto L_0x005a
        L_0x0053:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            byte r3 = r3.DecodeNormal(r4)
            goto L_0x0051
        L_0x005a:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r3 = r0.m_OutWindow
            r3.PutByte(r8)
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateChar(r2)
            r3 = 1
            long r6 = r6 + r3
        L_0x0066:
            r3 = 0
            goto L_0x001f
        L_0x0069:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            short[] r5 = r0.m_IsRepDecoders
            int r4 = r4.DecodeBit(r5, r2)
            r5 = 1
            if (r4 != r5) goto L_0x00cd
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            short[] r5 = r0.m_IsRepG0Decoders
            int r4 = r4.DecodeBit(r5, r2)
            if (r4 != 0) goto L_0x0092
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            short[] r5 = r0.m_IsRep0LongDecoders
            int r4 = r4.DecodeBit(r5, r14)
            if (r4 != 0) goto L_0x008f
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateShortRep(r2)
            r16 = 1
            goto L_0x00b9
        L_0x008f:
            r16 = 0
            goto L_0x00b9
        L_0x0092:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            short[] r5 = r0.m_IsRepG1Decoders
            int r4 = r4.DecodeBit(r5, r2)
            if (r4 != 0) goto L_0x00a2
            r17 = r11
            r11 = r10
        L_0x009f:
            r10 = r17
            goto L_0x00b1
        L_0x00a2:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            short[] r5 = r0.m_IsRepG2Decoders
            int r4 = r4.DecodeBit(r5, r2)
            if (r4 != 0) goto L_0x00ad
            goto L_0x00b1
        L_0x00ad:
            r17 = r12
            r12 = r10
            goto L_0x009f
        L_0x00b1:
            r16 = 0
            r17 = r11
            r11 = r9
            r9 = r10
            r10 = r17
        L_0x00b9:
            if (r16 != 0) goto L_0x00c9
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder$LenDecoder r4 = r0.m_RepLenDecoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r5 = r0.m_RangeDecoder
            int r3 = r4.Decode(r5, r3)
            int r16 = r3 + 2
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateRep(r2)
        L_0x00c9:
            r3 = r16
            goto L_0x0139
        L_0x00cd:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder$LenDecoder r4 = r0.m_LenDecoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r5 = r0.m_RangeDecoder
            int r3 = r4.Decode(r5, r3)
            int r3 = r3 + 2
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateMatch(r2)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_BitTreeDecoder[] r4 = r0.m_PosSlotDecoder
            int r5 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.GetLenToPosState(r3)
            r4 = r4[r5]
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r5 = r0.m_RangeDecoder
            int r4 = r4.Decode(r5)
            r5 = 4
            if (r4 < r5) goto L_0x0135
            int r8 = r4 >> 1
            r12 = 1
            int r8 = r8 - r12
            r13 = r4 & 1
            r13 = r13 | 2
            int r13 = r13 << r8
            r14 = 14
            if (r4 >= r14) goto L_0x010a
            short[] r5 = r0.m_PosDecoders
            int r4 = r13 - r4
            int r4 = r4 - r12
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r12 = r0.m_RangeDecoder
            int r4 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_BitTreeDecoder.ReverseDecode(r5, r4, r12, r8)
            int r13 = r13 + r4
        L_0x0105:
            r12 = r10
            r10 = r11
            r11 = r9
            r9 = r13
            goto L_0x0139
        L_0x010a:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r4 = r0.m_RangeDecoder
            int r8 = r8 + -4
            int r4 = r4.DecodeDirectBits(r8)
            int r4 = r4 << r5
            int r13 = r13 + r4
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_BitTreeDecoder r4 = r0.m_PosAlignDecoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r5 = r0.m_RangeDecoder
            int r4 = r4.ReverseDecode(r5)
            int r13 = r13 + r4
            if (r13 >= 0) goto L_0x0105
            r1 = -1
            if (r13 == r1) goto L_0x0124
            r1 = 0
            return r1
        L_0x0124:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r1 = r0.m_OutWindow
            r1.Flush()
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r1 = r0.m_OutWindow
            r1.ReleaseStream()
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Decoder r1 = r0.m_RangeDecoder
            r1.ReleaseStream()
            r1 = 1
            return r1
        L_0x0135:
            r12 = r10
            r10 = r11
            r11 = r9
            r9 = r4
        L_0x0139:
            long r4 = (long) r9
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0153
            int r4 = r0.m_DictionarySizeCheck
            if (r9 < r4) goto L_0x0143
            goto L_0x0153
        L_0x0143:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r4 = r0.m_OutWindow
            r4.CopyBlock(r9, r3)
            long r3 = (long) r3
            long r6 = r6 + r3
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_OutWindow r3 = r0.m_OutWindow
            r4 = 0
            byte r8 = r3.GetByte(r4)
            goto L_0x0066
        L_0x0153:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Decoder.Code(java.io.InputStream, java.io.OutputStream, long):boolean");
    }

    public boolean SetDecoderProperties(byte[] bArr) {
        if (bArr.length < 5) {
            return false;
        }
        byte b = bArr[0] & 255;
        int i = b % 9;
        int i2 = b / 9;
        int i3 = i2 % 5;
        int i4 = i2 / 5;
        int i5 = 0;
        int i6 = 0;
        while (i5 < 4) {
            int i7 = i5 + 1;
            i6 += (bArr[i7] & 255) << (i5 * 8);
            i5 = i7;
        }
        if (!SetLcLpPb(i, i3, i4)) {
            return false;
        }
        return SetDictionarySize(i6);
    }
}
