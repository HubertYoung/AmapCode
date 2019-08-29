package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.uc.webview.export.extension.UCCore;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZMA_Encoder {
    public static final int EMatchFinderTypeBT2 = 0;
    public static final int EMatchFinderTypeBT4 = 1;
    static byte[] g_FastPos = new byte[2048];
    static final int kDefaultDictionaryLogSize = 22;
    static final int kIfinityPrice = 268435455;
    static final int kNumFastBytesDefault = 32;
    public static final int kNumLenSpecSymbols = 16;
    static final int kNumOpts = 4096;
    public static final int kPropSize = 5;
    int _additionalOffset;
    int _alignPriceCount;
    int[] _alignPrices = new int[16];
    int _dictionarySize;
    int _dictionarySizePrev;
    int _distTableSize = 44;
    int[] _distancesPrices = new int[512];
    boolean _finished;
    InputStream _inStream;
    short[] _isMatch = new short[192];
    short[] _isRep = new short[12];
    short[] _isRep0Long = new short[192];
    short[] _isRepG0 = new short[12];
    short[] _isRepG1 = new short[12];
    short[] _isRepG2 = new short[12];
    LenPriceTableEncoder _lenEncoder = new LenPriceTableEncoder();
    LiteralEncoder _literalEncoder = new LiteralEncoder();
    int _longestMatchLength;
    boolean _longestMatchWasFound;
    int[] _matchDistances = new int[548];
    LZ_BinTree _matchFinder = null;
    int _matchFinderType;
    int _matchPriceCount;
    boolean _needReleaseMFStream;
    int _numDistancePairs;
    int _numFastBytes = 32;
    int _numFastBytesPrev;
    int _numLiteralContextBits;
    int _numLiteralPosStateBits;
    Optimal[] _optimum = new Optimal[4096];
    int _optimumCurrentIndex;
    int _optimumEndIndex;
    RangeCoder_BitTreeEncoder _posAlignEncoder = new RangeCoder_BitTreeEncoder(4);
    short[] _posEncoders = new short[114];
    RangeCoder_BitTreeEncoder[] _posSlotEncoder = new RangeCoder_BitTreeEncoder[4];
    int[] _posSlotPrices = new int[256];
    int _posStateBits = 2;
    int _posStateMask = 3;
    byte _previousByte;
    RangeCoder_Encoder _rangeEncoder = new RangeCoder_Encoder();
    int[] _repDistances = new int[4];
    LenPriceTableEncoder _repMatchLenEncoder = new LenPriceTableEncoder();
    int _state = LZMA_Base.StateInit();
    boolean _writeEndMark;
    int backRes;
    boolean[] finished;
    long nowPos64;
    long[] processedInSize;
    long[] processedOutSize;
    byte[] properties;
    int[] repLens;
    int[] reps;
    int[] tempPrices;

    class LenEncoder {
        short[] _choice = new short[2];
        RangeCoder_BitTreeEncoder _highCoder = new RangeCoder_BitTreeEncoder(8);
        RangeCoder_BitTreeEncoder[] _lowCoder = new RangeCoder_BitTreeEncoder[16];
        RangeCoder_BitTreeEncoder[] _midCoder = new RangeCoder_BitTreeEncoder[16];

        public LenEncoder() {
            for (int i = 0; i < 16; i++) {
                this._lowCoder[i] = new RangeCoder_BitTreeEncoder(3);
                this._midCoder[i] = new RangeCoder_BitTreeEncoder(3);
            }
        }

        public void Init(int i) {
            RangeCoder_Encoder.InitBitModels(this._choice);
            for (int i2 = 0; i2 < i; i2++) {
                this._lowCoder[i2].Init();
                this._midCoder[i2].Init();
            }
            this._highCoder.Init();
        }

        public void Encode(RangeCoder_Encoder rangeCoder_Encoder, int i, int i2) throws IOException {
            if (i < 8) {
                rangeCoder_Encoder.Encode(this._choice, 0, 0);
                this._lowCoder[i2].Encode(rangeCoder_Encoder, i);
                return;
            }
            int i3 = i - 8;
            rangeCoder_Encoder.Encode(this._choice, 0, 1);
            if (i3 < 8) {
                rangeCoder_Encoder.Encode(this._choice, 1, 0);
                this._midCoder[i2].Encode(rangeCoder_Encoder, i3);
                return;
            }
            rangeCoder_Encoder.Encode(this._choice, 1, 1);
            this._highCoder.Encode(rangeCoder_Encoder, i3 - 8);
        }

        public void SetPrices(int i, int i2, int[] iArr, int i3) {
            int i4 = 0;
            int GetPrice0 = RangeCoder_Encoder.GetPrice0(this._choice[0]);
            int GetPrice1 = RangeCoder_Encoder.GetPrice1(this._choice[0]);
            int GetPrice02 = RangeCoder_Encoder.GetPrice0(this._choice[1]) + GetPrice1;
            int GetPrice12 = GetPrice1 + RangeCoder_Encoder.GetPrice1(this._choice[1]);
            while (i4 < 8) {
                if (i4 < i2) {
                    iArr[i3 + i4] = this._lowCoder[i].GetPrice(i4) + GetPrice0;
                    i4++;
                } else {
                    return;
                }
            }
            while (i4 < 16) {
                if (i4 < i2) {
                    iArr[i3 + i4] = this._midCoder[i].GetPrice(i4 - 8) + GetPrice02;
                    i4++;
                } else {
                    return;
                }
            }
            while (i4 < i2) {
                iArr[i3 + i4] = this._highCoder.GetPrice((i4 - 8) - 8) + GetPrice12;
                i4++;
            }
        }
    }

    class LenPriceTableEncoder extends LenEncoder {
        int[] _counters = new int[16];
        int[] _prices = new int[4352];
        int _tableSize;

        LenPriceTableEncoder() {
            super();
        }

        public void SetTableSize(int i) {
            this._tableSize = i;
        }

        public int GetPrice(int i, int i2) {
            return this._prices[(i2 * LZMA_Base.kNumLenSymbols) + i];
        }

        /* access modifiers changed from: 0000 */
        public void UpdateTable(int i) {
            SetPrices(i, this._tableSize, this._prices, i * LZMA_Base.kNumLenSymbols);
            this._counters[i] = this._tableSize;
        }

        public void UpdateTables(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                UpdateTable(i2);
            }
        }

        public void Encode(RangeCoder_Encoder rangeCoder_Encoder, int i, int i2) throws IOException {
            super.Encode(rangeCoder_Encoder, i, i2);
            int[] iArr = this._counters;
            int i3 = iArr[i2] - 1;
            iArr[i2] = i3;
            if (i3 == 0) {
                UpdateTable(i2);
            }
        }
    }

    class LiteralEncoder {
        Encoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        class Encoder2 {
            short[] m_Encoders = new short[768];

            Encoder2() {
            }

            public void Init() {
                RangeCoder_Encoder.InitBitModels(this.m_Encoders);
            }

            public void Encode(RangeCoder_Encoder rangeCoder_Encoder, byte b) throws IOException {
                int i = 1;
                for (int i2 = 7; i2 >= 0; i2--) {
                    int i3 = (b >> i2) & 1;
                    rangeCoder_Encoder.Encode(this.m_Encoders, i, i3);
                    i = (i << 1) | i3;
                }
            }

            public void EncodeMatched(RangeCoder_Encoder rangeCoder_Encoder, byte b, byte b2) throws IOException {
                int i;
                int i2 = 1;
                boolean z = true;
                for (int i3 = 7; i3 >= 0; i3--) {
                    int i4 = (b2 >> i3) & 1;
                    if (z) {
                        int i5 = (b >> i3) & 1;
                        i = ((i5 + 1) << 8) + i2;
                        z = i5 == i4;
                    } else {
                        i = i2;
                    }
                    rangeCoder_Encoder.Encode(this.m_Encoders, i, i4);
                    i2 = (i2 << 1) | i4;
                }
            }

            public int GetPrice(boolean z, byte b, byte b2) {
                int i;
                int i2 = 0;
                int i3 = 7;
                if (z) {
                    i = 1;
                    while (true) {
                        if (i3 < 0) {
                            break;
                        }
                        int i4 = (b >> i3) & 1;
                        int i5 = (b2 >> i3) & 1;
                        i2 += RangeCoder_Encoder.GetPrice(this.m_Encoders[((i4 + 1) << 8) + i], i5);
                        i = (i << 1) | i5;
                        if (i4 != i5) {
                            i3--;
                            break;
                        }
                        i3--;
                    }
                } else {
                    i = 1;
                }
                while (i3 >= 0) {
                    int i6 = (b2 >> i3) & 1;
                    i2 += RangeCoder_Encoder.GetPrice(this.m_Encoders[i], i6);
                    i = (i << 1) | i6;
                    i3--;
                }
                return i2;
            }
        }

        LiteralEncoder() {
        }

        public void Create(int i, int i2) {
            if (this.m_Coders == null || this.m_NumPrevBits != i2 || this.m_NumPosBits != i) {
                this.m_NumPosBits = i;
                this.m_PosMask = (1 << i) - 1;
                this.m_NumPrevBits = i2;
                int i3 = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
                this.m_Coders = new Encoder2[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    this.m_Coders[i4] = new Encoder2();
                }
            }
        }

        public void Init() {
            int i = 1 << (this.m_NumPrevBits + this.m_NumPosBits);
            for (int i2 = 0; i2 < i; i2++) {
                this.m_Coders[i2].Init();
            }
        }

        /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r4v0, types: [byte, int] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder.LiteralEncoder.Encoder2 GetSubCoder(int r3, int r4) {
            /*
                r2 = this;
                com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder$Encoder2[] r0 = r2.m_Coders
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
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder.LiteralEncoder.GetSubCoder(int, byte):com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder$Encoder2");
        }
    }

    class Optimal {
        public int BackPrev;
        public int BackPrev2;
        public int Backs0;
        public int Backs1;
        public int Backs2;
        public int Backs3;
        public int PosPrev;
        public int PosPrev2;
        public boolean Prev1IsChar;
        public boolean Prev2;
        public int Price;
        public int State;

        Optimal() {
        }

        public void MakeAsChar() {
            this.BackPrev = -1;
            this.Prev1IsChar = false;
        }

        public void MakeAsShortRep() {
            this.BackPrev = 0;
            this.Prev1IsChar = false;
        }

        public boolean IsShortRep() {
            return this.BackPrev == 0;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean ChangePair(int i, int i2) {
        return i < 33554432 && i2 >= (i << 7);
    }

    public boolean SetAlgorithm(int i) {
        return true;
    }

    static {
        g_FastPos[0] = 0;
        g_FastPos[1] = 1;
        int i = 2;
        int i2 = 2;
        while (i < 22) {
            int i3 = 1 << ((i >> 1) - 1);
            int i4 = i2;
            int i5 = 0;
            while (i5 < i3) {
                g_FastPos[i4] = (byte) i;
                i5++;
                i4++;
            }
            i++;
            i2 = i4;
        }
    }

    static int GetPosSlot(int i) {
        if (i < 2048) {
            return g_FastPos[i];
        }
        if (i < 2097152) {
            return g_FastPos[i >> 10] + 20;
        }
        return g_FastPos[i >> 20] + 40;
    }

    static int GetPosSlot2(int i) {
        if (i < 131072) {
            return g_FastPos[i >> 6] + ClientRpcPack.SYMMETRIC_ENCRYPT_3DES;
        }
        if (i < 134217728) {
            return g_FastPos[i >> 16] + 32;
        }
        return g_FastPos[i >> 26] + 52;
    }

    /* access modifiers changed from: 0000 */
    public void BaseInit() {
        this._state = LZMA_Base.StateInit();
        this._previousByte = 0;
        for (int i = 0; i < 4; i++) {
            this._repDistances[i] = 0;
        }
    }

    /* access modifiers changed from: 0000 */
    public void Create() {
        if (this._matchFinder == null) {
            LZ_BinTree lZ_BinTree = new LZ_BinTree();
            int i = 4;
            if (this._matchFinderType == 0) {
                i = 2;
            }
            lZ_BinTree.SetType(i);
            this._matchFinder = lZ_BinTree;
        }
        this._literalEncoder.Create(this._numLiteralPosStateBits, this._numLiteralContextBits);
        if (this._dictionarySize != this._dictionarySizePrev || this._numFastBytesPrev != this._numFastBytes) {
            this._matchFinder.Create(this._dictionarySize, 4096, this._numFastBytes, 274);
            this._dictionarySizePrev = this._dictionarySize;
            this._numFastBytesPrev = this._numFastBytes;
        }
    }

    public LZMA_Encoder() {
        this._numLiteralPosStateBits = 0;
        this._numLiteralContextBits = 3;
        this._dictionarySize = UCCore.VERIFY_POLICY_WITH_SHA256;
        this._dictionarySizePrev = -1;
        this._numFastBytesPrev = -1;
        this._matchFinderType = 1;
        this._writeEndMark = false;
        this._needReleaseMFStream = false;
        this.reps = new int[4];
        this.repLens = new int[4];
        this.processedInSize = new long[1];
        this.processedOutSize = new long[1];
        this.finished = new boolean[1];
        this.properties = new byte[5];
        this.tempPrices = new int[128];
        for (int i = 0; i < 4096; i++) {
            this._optimum[i] = new Optimal();
        }
        for (int i2 = 0; i2 < 4; i2++) {
            this._posSlotEncoder[i2] = new RangeCoder_BitTreeEncoder(6);
        }
    }

    /* access modifiers changed from: 0000 */
    public void SetWriteEndMarkerMode(boolean z) {
        this._writeEndMark = z;
    }

    /* access modifiers changed from: 0000 */
    public void Init() {
        BaseInit();
        this._rangeEncoder.Init();
        RangeCoder_Encoder.InitBitModels(this._isMatch);
        RangeCoder_Encoder.InitBitModels(this._isRep0Long);
        RangeCoder_Encoder.InitBitModels(this._isRep);
        RangeCoder_Encoder.InitBitModels(this._isRepG0);
        RangeCoder_Encoder.InitBitModels(this._isRepG1);
        RangeCoder_Encoder.InitBitModels(this._isRepG2);
        RangeCoder_Encoder.InitBitModels(this._posEncoders);
        this._literalEncoder.Init();
        for (int i = 0; i < 4; i++) {
            this._posSlotEncoder[i].Init();
        }
        this._lenEncoder.Init(1 << this._posStateBits);
        this._repMatchLenEncoder.Init(1 << this._posStateBits);
        this._posAlignEncoder.Init();
        this._longestMatchWasFound = false;
        this._optimumEndIndex = 0;
        this._optimumCurrentIndex = 0;
        this._additionalOffset = 0;
    }

    /* access modifiers changed from: 0000 */
    public int ReadMatchDistances() throws IOException {
        int i;
        this._numDistancePairs = this._matchFinder.GetMatches(this._matchDistances);
        if (this._numDistancePairs > 0) {
            i = this._matchDistances[this._numDistancePairs - 2];
            if (i == this._numFastBytes) {
                i += this._matchFinder.GetMatchLen(i - 1, this._matchDistances[this._numDistancePairs - 1], 273 - i);
            }
        } else {
            i = 0;
        }
        this._additionalOffset++;
        return i;
    }

    /* access modifiers changed from: 0000 */
    public void MovePos(int i) throws IOException {
        if (i > 0) {
            this._matchFinder.Skip(i);
            this._additionalOffset += i;
        }
    }

    /* access modifiers changed from: 0000 */
    public int GetRepLen1Price(int i, int i2) {
        return RangeCoder_Encoder.GetPrice0(this._isRepG0[i]) + RangeCoder_Encoder.GetPrice0(this._isRep0Long[(i << 4) + i2]);
    }

    /* access modifiers changed from: 0000 */
    public int GetPureRepPrice(int i, int i2, int i3) {
        if (i == 0) {
            return RangeCoder_Encoder.GetPrice0(this._isRepG0[i2]) + RangeCoder_Encoder.GetPrice1(this._isRep0Long[(i2 << 4) + i3]);
        }
        int GetPrice1 = RangeCoder_Encoder.GetPrice1(this._isRepG0[i2]);
        if (i == 1) {
            return RangeCoder_Encoder.GetPrice0(this._isRepG1[i2]) + GetPrice1;
        }
        return RangeCoder_Encoder.GetPrice(this._isRepG2[i2], i - 2) + GetPrice1 + RangeCoder_Encoder.GetPrice1(this._isRepG1[i2]);
    }

    /* access modifiers changed from: 0000 */
    public int GetRepPrice(int i, int i2, int i3, int i4) {
        return this._repMatchLenEncoder.GetPrice(i2 - 2, i4) + GetPureRepPrice(i, i3, i4);
    }

    /* access modifiers changed from: 0000 */
    public int GetPosLenPrice(int i, int i2, int i3) {
        int i4;
        int GetLenToPosState = LZMA_Base.GetLenToPosState(i2);
        if (i < 128) {
            i4 = this._distancesPrices[(GetLenToPosState * 128) + i];
        } else {
            i4 = this._alignPrices[i & 15] + this._posSlotPrices[(GetLenToPosState << 6) + GetPosSlot2(i)];
        }
        return i4 + this._lenEncoder.GetPrice(i2 - 2, i3);
    }

    /* access modifiers changed from: 0000 */
    public int Backward(int i) {
        this._optimumEndIndex = i;
        int i2 = this._optimum[i].PosPrev;
        int i3 = this._optimum[i].BackPrev;
        while (true) {
            if (this._optimum[i].Prev1IsChar) {
                this._optimum[i2].MakeAsChar();
                int i4 = i2 - 1;
                this._optimum[i2].PosPrev = i4;
                if (this._optimum[i].Prev2) {
                    this._optimum[i4].Prev1IsChar = false;
                    this._optimum[i4].PosPrev = this._optimum[i].PosPrev2;
                    this._optimum[i4].BackPrev = this._optimum[i].BackPrev2;
                }
            }
            int i5 = this._optimum[i2].BackPrev;
            int i6 = this._optimum[i2].PosPrev;
            this._optimum[i2].BackPrev = i3;
            this._optimum[i2].PosPrev = i;
            if (i2 <= 0) {
                this.backRes = this._optimum[0].BackPrev;
                this._optimumCurrentIndex = this._optimum[0].PosPrev;
                return this._optimumCurrentIndex;
            }
            i = i2;
            i3 = i5;
            i2 = i6;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x04a3  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x05c9  */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x05dc  */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x05e0  */
    /* JADX WARNING: Removed duplicated region for block: B:228:0x0716  */
    /* JADX WARNING: Removed duplicated region for block: B:261:0x06f6 A[EDGE_INSN: B:261:0x06f6->B:225:0x06f6 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:263:0x0708 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int GetOptimum(int r33) throws java.io.IOException {
        /*
            r32 = this;
            r0 = r32
            r1 = r33
            int r2 = r0._optimumEndIndex
            int r3 = r0._optimumCurrentIndex
            if (r2 == r3) goto L_0x002a
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r1 = r0._optimum
            int r2 = r0._optimumCurrentIndex
            r1 = r1[r2]
            int r1 = r1.PosPrev
            int r2 = r0._optimumCurrentIndex
            int r1 = r1 - r2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r2 = r0._optimum
            int r3 = r0._optimumCurrentIndex
            r2 = r2[r3]
            int r2 = r2.BackPrev
            r0.backRes = r2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r2 = r0._optimum
            int r3 = r0._optimumCurrentIndex
            r2 = r2[r3]
            int r2 = r2.PosPrev
            r0._optimumCurrentIndex = r2
            return r1
        L_0x002a:
            r2 = 0
            r0._optimumEndIndex = r2
            r0._optimumCurrentIndex = r2
            boolean r3 = r0._longestMatchWasFound
            if (r3 != 0) goto L_0x0038
            int r3 = r32.ReadMatchDistances()
            goto L_0x003c
        L_0x0038:
            int r3 = r0._longestMatchLength
            r0._longestMatchWasFound = r2
        L_0x003c:
            int r4 = r0._numDistancePairs
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r5 = r0._matchFinder
            int r5 = r5.GetNumAvailableBytes()
            r6 = 1
            int r5 = r5 + r6
            r7 = -1
            r8 = 2
            if (r5 >= r8) goto L_0x004d
            r0.backRes = r7
            return r6
        L_0x004d:
            r5 = 0
            r9 = 0
        L_0x004f:
            r10 = 4
            if (r5 >= r10) goto L_0x0078
            int[] r10 = r0.reps
            int[] r11 = r0._repDistances
            r11 = r11[r5]
            r10[r5] = r11
            int[] r10 = r0.repLens
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r11 = r0._matchFinder
            int[] r12 = r0.reps
            r12 = r12[r5]
            r13 = 273(0x111, float:3.83E-43)
            int r11 = r11.GetMatchLen(r7, r12, r13)
            r10[r5] = r11
            int[] r10 = r0.repLens
            r10 = r10[r5]
            int[] r11 = r0.repLens
            r11 = r11[r9]
            if (r10 <= r11) goto L_0x0075
            r9 = r5
        L_0x0075:
            int r5 = r5 + 1
            goto L_0x004f
        L_0x0078:
            int[] r5 = r0.repLens
            r5 = r5[r9]
            int r11 = r0._numFastBytes
            if (r5 < r11) goto L_0x008c
            r0.backRes = r9
            int[] r1 = r0.repLens
            r1 = r1[r9]
            int r2 = r1 + -1
            r0.MovePos(r2)
            return r1
        L_0x008c:
            int r5 = r0._numFastBytes
            if (r3 < r5) goto L_0x009e
            int[] r1 = r0._matchDistances
            int r4 = r4 - r6
            r1 = r1[r4]
            int r1 = r1 + r10
            r0.backRes = r1
            int r1 = r3 + -1
            r0.MovePos(r1)
            return r3
        L_0x009e:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r5 = r0._matchFinder
            byte r5 = r5.GetIndexByte(r7)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r11 = r0._matchFinder
            int[] r12 = r0._repDistances
            r12 = r12[r2]
            int r12 = 0 - r12
            int r12 = r12 - r6
            int r12 = r12 - r6
            byte r11 = r11.GetIndexByte(r12)
            if (r3 >= r8) goto L_0x00bf
            if (r5 == r11) goto L_0x00bf
            int[] r12 = r0.repLens
            r12 = r12[r9]
            if (r12 >= r8) goto L_0x00bf
            r0.backRes = r7
            return r6
        L_0x00bf:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r12 = r0._optimum
            r12 = r12[r2]
            int r13 = r0._state
            r12.State = r13
            int r12 = r0._posStateMask
            r12 = r12 & r1
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r13 = r0._optimum
            r13 = r13[r6]
            short[] r14 = r0._isMatch
            int r15 = r0._state
            int r15 = r15 << r10
            int r15 = r15 + r12
            short r14 = r14[r15]
            int r14 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice0(r14)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder r15 = r0._literalEncoder
            byte r7 = r0._previousByte
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder$Encoder2 r7 = r15.GetSubCoder(r1, r7)
            int r15 = r0._state
            boolean r15 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateIsCharState(r15)
            r15 = r15 ^ r6
            int r7 = r7.GetPrice(r15, r11, r5)
            int r14 = r14 + r7
            r13.Price = r14
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r7 = r0._optimum
            r7 = r7[r6]
            r7.MakeAsChar()
            short[] r7 = r0._isMatch
            int r13 = r0._state
            int r13 = r13 << r10
            int r13 = r13 + r12
            short r7 = r7[r13]
            int r7 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r7)
            short[] r13 = r0._isRep
            int r14 = r0._state
            short r13 = r13[r14]
            int r13 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r13)
            int r13 = r13 + r7
            if (r11 != r5) goto L_0x012c
            int r5 = r0._state
            int r5 = r0.GetRepLen1Price(r5, r12)
            int r5 = r5 + r13
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r11 = r0._optimum
            r11 = r11[r6]
            int r11 = r11.Price
            if (r5 >= r11) goto L_0x012c
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r11 = r0._optimum
            r11 = r11[r6]
            r11.Price = r5
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r5 = r0._optimum
            r5 = r5[r6]
            r5.MakeAsShortRep()
        L_0x012c:
            int[] r5 = r0.repLens
            r5 = r5[r9]
            if (r3 < r5) goto L_0x0134
            r5 = r3
            goto L_0x0138
        L_0x0134:
            int[] r5 = r0.repLens
            r5 = r5[r9]
        L_0x0138:
            if (r5 >= r8) goto L_0x0143
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r1 = r0._optimum
            r1 = r1[r6]
            int r1 = r1.BackPrev
            r0.backRes = r1
            return r6
        L_0x0143:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r6]
            r9.PosPrev = r2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r2]
            int[] r11 = r0.reps
            r11 = r11[r2]
            r9.Backs0 = r11
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r2]
            int[] r11 = r0.reps
            r11 = r11[r6]
            r9.Backs1 = r11
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r2]
            int[] r11 = r0.reps
            r11 = r11[r8]
            r9.Backs2 = r11
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r2]
            int[] r11 = r0.reps
            r14 = 3
            r11 = r11[r14]
            r9.Backs3 = r11
            r9 = r5
        L_0x0173:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r11 = r0._optimum
            int r15 = r9 + -1
            r9 = r11[r9]
            r11 = 268435455(0xfffffff, float:2.5243547E-29)
            r9.Price = r11
            if (r15 >= r8) goto L_0x0729
            r9 = 0
        L_0x0181:
            if (r9 >= r10) goto L_0x01ba
            int[] r15 = r0.repLens
            r15 = r15[r9]
            if (r15 < r8) goto L_0x01b1
            int r11 = r0._state
            int r11 = r0.GetPureRepPrice(r9, r11, r12)
            int r11 = r11 + r13
        L_0x0190:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LenPriceTableEncoder r14 = r0._repMatchLenEncoder
            int r10 = r15 + -2
            int r10 = r14.GetPrice(r10, r12)
            int r10 = r10 + r11
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r14 = r0._optimum
            r14 = r14[r15]
            int r6 = r14.Price
            if (r10 >= r6) goto L_0x01a9
            r14.Price = r10
            r14.PosPrev = r2
            r14.BackPrev = r9
            r14.Prev1IsChar = r2
        L_0x01a9:
            int r15 = r15 + -1
            if (r15 >= r8) goto L_0x01ae
            goto L_0x01b1
        L_0x01ae:
            r6 = 1
            r10 = 4
            goto L_0x0190
        L_0x01b1:
            int r9 = r9 + 1
            r6 = 1
            r10 = 4
            r11 = 268435455(0xfffffff, float:2.5243547E-29)
            r14 = 3
            goto L_0x0181
        L_0x01ba:
            short[] r6 = r0._isRep
            int r9 = r0._state
            short r6 = r6[r9]
            int r6 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice0(r6)
            int r7 = r7 + r6
            int[] r6 = r0.repLens
            r6 = r6[r2]
            if (r6 < r8) goto L_0x01d2
            int[] r6 = r0.repLens
            r6 = r6[r2]
            r9 = 1
            int r6 = r6 + r9
            goto L_0x01d3
        L_0x01d2:
            r6 = 2
        L_0x01d3:
            if (r6 > r3) goto L_0x0209
            r3 = 0
        L_0x01d6:
            int[] r9 = r0._matchDistances
            r9 = r9[r3]
            if (r6 <= r9) goto L_0x01df
            int r3 = r3 + 2
            goto L_0x01d6
        L_0x01df:
            int[] r9 = r0._matchDistances
            int r10 = r3 + 1
            r9 = r9[r10]
            int r10 = r0.GetPosLenPrice(r9, r6, r12)
            int r10 = r10 + r7
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r11 = r0._optimum
            r11 = r11[r6]
            int r13 = r11.Price
            if (r10 >= r13) goto L_0x01fc
            r11.Price = r10
            r11.PosPrev = r2
            int r9 = r9 + 4
            r11.BackPrev = r9
            r11.Prev1IsChar = r2
        L_0x01fc:
            int[] r9 = r0._matchDistances
            r9 = r9[r3]
            if (r6 != r9) goto L_0x0206
            int r3 = r3 + 2
            if (r3 == r4) goto L_0x0209
        L_0x0206:
            int r6 = r6 + 1
            goto L_0x01df
        L_0x0209:
            r3 = r1
            r1 = 0
            r4 = 1
        L_0x020c:
            int r1 = r1 + r4
            if (r1 != r5) goto L_0x0214
            int r1 = r0.Backward(r1)
            return r1
        L_0x0214:
            int r6 = r32.ReadMatchDistances()
            int r7 = r0._numDistancePairs
            int r9 = r0._numFastBytes
            if (r6 < r9) goto L_0x0227
            r0._longestMatchLength = r6
            r0._longestMatchWasFound = r4
            int r1 = r0.Backward(r1)
            return r1
        L_0x0227:
            int r3 = r3 + r4
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r4 = r0._optimum
            r4 = r4[r1]
            int r4 = r4.PosPrev
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            boolean r9 = r9.Prev1IsChar
            if (r9 == 0) goto L_0x026a
            int r4 = r4 + -1
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            boolean r9 = r9.Prev2
            if (r9 == 0) goto L_0x025f
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r1]
            int r10 = r10.PosPrev2
            r9 = r9[r10]
            int r9 = r9.State
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r1]
            int r10 = r10.BackPrev2
            r11 = 4
            if (r10 >= r11) goto L_0x025a
            int r9 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateRep(r9)
            goto L_0x0265
        L_0x025a:
            int r9 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateMatch(r9)
            goto L_0x0265
        L_0x025f:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r4]
            int r9 = r9.State
        L_0x0265:
            int r9 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateChar(r9)
            goto L_0x0270
        L_0x026a:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r4]
            int r9 = r9.State
        L_0x0270:
            int r10 = r1 + -1
            if (r4 != r10) goto L_0x028a
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r4 = r0._optimum
            r4 = r4[r1]
            boolean r4 = r4.IsShortRep()
            if (r4 == 0) goto L_0x0284
            int r4 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateShortRep(r9)
            goto L_0x0351
        L_0x0284:
            int r4 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateChar(r9)
            goto L_0x0351
        L_0x028a:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r1]
            boolean r10 = r10.Prev1IsChar
            if (r10 == 0) goto L_0x02ac
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r1]
            boolean r10 = r10.Prev2
            if (r10 == 0) goto L_0x02ac
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r4 = r0._optimum
            r4 = r4[r1]
            int r4 = r4.PosPrev2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r1]
            int r10 = r10.BackPrev2
            int r9 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateRep(r9)
            r11 = 4
            goto L_0x02be
        L_0x02ac:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r1]
            int r10 = r10.BackPrev
            r11 = 4
            if (r10 >= r11) goto L_0x02ba
            int r9 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateRep(r9)
            goto L_0x02be
        L_0x02ba:
            int r9 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateMatch(r9)
        L_0x02be:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r12 = r0._optimum
            r4 = r12[r4]
            if (r10 >= r11) goto L_0x0336
            if (r10 != 0) goto L_0x02e1
            int[] r10 = r0.reps
            int r11 = r4.Backs0
            r10[r2] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs1
            r12 = 1
            r10[r12] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs2
            r10[r8] = r11
            int[] r10 = r0.reps
            int r4 = r4.Backs3
            r11 = 3
            r10[r11] = r4
            goto L_0x0350
        L_0x02e1:
            r12 = 1
            if (r10 != r12) goto L_0x02fe
            int[] r10 = r0.reps
            int r11 = r4.Backs1
            r10[r2] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs0
            r10[r12] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs2
            r10[r8] = r11
            int[] r10 = r0.reps
            int r4 = r4.Backs3
            r11 = 3
            r10[r11] = r4
            goto L_0x0350
        L_0x02fe:
            if (r10 != r8) goto L_0x031b
            int[] r10 = r0.reps
            int r11 = r4.Backs2
            r10[r2] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs0
            r12 = 1
            r10[r12] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs1
            r10[r8] = r11
            int[] r10 = r0.reps
            int r4 = r4.Backs3
            r11 = 3
            r10[r11] = r4
            goto L_0x0350
        L_0x031b:
            int[] r10 = r0.reps
            int r11 = r4.Backs3
            r10[r2] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs0
            r12 = 1
            r10[r12] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs1
            r10[r8] = r11
            int[] r10 = r0.reps
            int r4 = r4.Backs2
            r11 = 3
            r10[r11] = r4
            goto L_0x0350
        L_0x0336:
            int[] r11 = r0.reps
            int r10 = r10 + -4
            r11[r2] = r10
            int[] r10 = r0.reps
            int r11 = r4.Backs0
            r12 = 1
            r10[r12] = r11
            int[] r10 = r0.reps
            int r11 = r4.Backs1
            r10[r8] = r11
            int[] r10 = r0.reps
            int r4 = r4.Backs2
            r11 = 3
            r10[r11] = r4
        L_0x0350:
            r4 = r9
        L_0x0351:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            r9.State = r4
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            int[] r10 = r0.reps
            r10 = r10[r2]
            r9.Backs0 = r10
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            int[] r10 = r0.reps
            r11 = 1
            r10 = r10[r11]
            r9.Backs1 = r10
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            int[] r10 = r0.reps
            r10 = r10[r8]
            r9.Backs2 = r10
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            int[] r10 = r0.reps
            r11 = 3
            r10 = r10[r11]
            r9.Backs3 = r10
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r9 = r0._optimum
            r9 = r9[r1]
            int r9 = r9.Price
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r10 = r0._matchFinder
            r12 = -1
            byte r10 = r10.GetIndexByte(r12)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r12 = r0._matchFinder
            int[] r13 = r0.reps
            r13 = r13[r2]
            int r13 = 0 - r13
            r14 = 1
            int r13 = r13 - r14
            int r13 = r13 - r14
            byte r12 = r12.GetIndexByte(r13)
            int r13 = r0._posStateMask
            r13 = r13 & r3
            short[] r14 = r0._isMatch
            int r15 = r4 << 4
            int r15 = r15 + r13
            short r14 = r14[r15]
            int r14 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice0(r14)
            int r14 = r14 + r9
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder r11 = r0._literalEncoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r2 = r0._matchFinder
            r8 = -2
            byte r2 = r2.GetIndexByte(r8)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder$Encoder2 r2 = r11.GetSubCoder(r3, r2)
            boolean r8 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateIsCharState(r4)
            r11 = 1
            r8 = r8 ^ r11
            int r2 = r2.GetPrice(r8, r12, r10)
            int r14 = r14 + r2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r2 = r0._optimum
            int r8 = r1 + 1
            r2 = r2[r8]
            int r11 = r2.Price
            if (r14 >= r11) goto L_0x03d9
            r2.Price = r14
            r2.PosPrev = r1
            r2.MakeAsChar()
            r18 = r5
            r11 = 1
            goto L_0x03dc
        L_0x03d9:
            r18 = r5
            r11 = 0
        L_0x03dc:
            short[] r5 = r0._isMatch
            short r5 = r5[r15]
            int r5 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r5)
            int r9 = r9 + r5
            short[] r5 = r0._isRep
            short r5 = r5[r4]
            int r5 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r5)
            int r5 = r5 + r9
            if (r12 != r10) goto L_0x040c
            int r15 = r2.PosPrev
            if (r15 >= r1) goto L_0x03f8
            int r15 = r2.BackPrev
            if (r15 == 0) goto L_0x040c
        L_0x03f8:
            int r15 = r0.GetRepLen1Price(r4, r13)
            int r15 = r15 + r5
            r19 = r7
            int r7 = r2.Price
            if (r15 > r7) goto L_0x040e
            r2.Price = r15
            r2.PosPrev = r1
            r2.MakeAsShortRep()
            r11 = 1
            goto L_0x040e
        L_0x040c:
            r19 = r7
        L_0x040e:
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r2 = r0._matchFinder
            int r2 = r2.GetNumAvailableBytes()
            r7 = 1
            int r2 = r2 + r7
            int r7 = 4095 - r1
            int r2 = java.lang.Math.min(r7, r2)
            r7 = 2
            if (r2 < r7) goto L_0x0720
            int r7 = r0._numFastBytes
            if (r2 <= r7) goto L_0x0426
            int r7 = r0._numFastBytes
            goto L_0x0427
        L_0x0426:
            r7 = r2
        L_0x0427:
            if (r11 != 0) goto L_0x049a
            if (r12 == r10) goto L_0x049a
            int r10 = r2 + -1
            int r11 = r0._numFastBytes
            int r10 = java.lang.Math.min(r10, r11)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r11 = r0._matchFinder
            int[] r12 = r0.reps
            r15 = 0
            r12 = r12[r15]
            int r10 = r11.GetMatchLen(r15, r12, r10)
            r11 = 2
            if (r10 < r11) goto L_0x049a
            int r11 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateChar(r4)
            int r12 = r3 + 1
            int r15 = r0._posStateMask
            r12 = r12 & r15
            short[] r15 = r0._isMatch
            int r17 = r11 << 4
            int r17 = r17 + r12
            short r15 = r15[r17]
            int r15 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r15)
            int r14 = r14 + r15
            short[] r15 = r0._isRep
            short r15 = r15[r11]
            int r15 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r15)
            int r14 = r14 + r15
            int r15 = r8 + r10
            r20 = r9
            r9 = r18
        L_0x0466:
            if (r9 >= r15) goto L_0x047c
            r21 = r6
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r6 = r0._optimum
            int r9 = r9 + 1
            r6 = r6[r9]
            r22 = r9
            r9 = 268435455(0xfffffff, float:2.5243547E-29)
            r6.Price = r9
            r6 = r21
            r9 = r22
            goto L_0x0466
        L_0x047c:
            r21 = r6
            r6 = 0
            int r10 = r0.GetRepPrice(r6, r10, r11, r12)
            int r14 = r14 + r10
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r10 = r0._optimum
            r10 = r10[r15]
            int r11 = r10.Price
            if (r14 >= r11) goto L_0x0497
            r10.Price = r14
            r10.PosPrev = r8
            r10.BackPrev = r6
            r8 = 1
            r10.Prev1IsChar = r8
            r10.Prev2 = r6
        L_0x0497:
            r18 = r9
            goto L_0x049e
        L_0x049a:
            r21 = r6
            r20 = r9
        L_0x049e:
            r6 = 0
            r8 = 2
        L_0x04a0:
            r9 = 4
            if (r6 >= r9) goto L_0x05c3
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r10 = r0._matchFinder
            int[] r11 = r0.reps
            r11 = r11[r6]
            r14 = -1
            int r10 = r10.GetMatchLen(r14, r11, r7)
            r11 = 2
            if (r10 < r11) goto L_0x05b7
            r12 = r10
            r11 = r18
        L_0x04b4:
            int r15 = r1 + r12
            if (r11 >= r15) goto L_0x04c5
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r15 = r0._optimum
            int r11 = r11 + 1
            r15 = r15[r11]
            r9 = 268435455(0xfffffff, float:2.5243547E-29)
            r15.Price = r9
            r9 = 4
            goto L_0x04b4
        L_0x04c5:
            int r9 = r0.GetRepPrice(r6, r12, r4, r13)
            int r9 = r9 + r5
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r14 = r0._optimum
            r14 = r14[r15]
            int r15 = r14.Price
            if (r9 >= r15) goto L_0x04db
            r14.Price = r9
            r14.PosPrev = r1
            r14.BackPrev = r6
            r9 = 0
            r14.Prev1IsChar = r9
        L_0x04db:
            int r12 = r12 + -1
            r9 = 2
            if (r12 >= r9) goto L_0x05b1
            if (r6 != 0) goto L_0x04e4
            int r8 = r10 + 1
        L_0x04e4:
            if (r10 >= r2) goto L_0x05a4
            int r9 = r2 + -1
            int r9 = r9 - r10
            int r12 = r0._numFastBytes
            int r9 = java.lang.Math.min(r9, r12)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r12 = r0._matchFinder
            int[] r14 = r0.reps
            r14 = r14[r6]
            int r9 = r12.GetMatchLen(r10, r14, r9)
            r12 = 2
            if (r9 < r12) goto L_0x05a4
            int r12 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateRep(r4)
            int r14 = r3 + r10
            int r15 = r0._posStateMask
            r15 = r15 & r14
            int r16 = r0.GetRepPrice(r6, r10, r4, r13)
            int r16 = r5 + r16
            r23 = r5
            short[] r5 = r0._isMatch
            int r17 = r12 << 4
            int r17 = r17 + r15
            short r5 = r5[r17]
            int r5 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice0(r5)
            int r16 = r16 + r5
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder r5 = r0._literalEncoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r15 = r0._matchFinder
            r24 = r8
            int r8 = r10 + -1
            r25 = r11
            int r11 = r8 + -1
            byte r11 = r15.GetIndexByte(r11)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder$Encoder2 r5 = r5.GetSubCoder(r14, r11)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r11 = r0._matchFinder
            int[] r15 = r0.reps
            r15 = r15[r6]
            r26 = r3
            r3 = 1
            int r15 = r15 + r3
            int r15 = r8 - r15
            byte r11 = r11.GetIndexByte(r15)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r15 = r0._matchFinder
            byte r8 = r15.GetIndexByte(r8)
            int r5 = r5.GetPrice(r3, r11, r8)
            int r16 = r16 + r5
            int r5 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateChar(r12)
            int r14 = r14 + r3
            int r3 = r0._posStateMask
            r3 = r3 & r14
            short[] r8 = r0._isMatch
            int r11 = r5 << 4
            int r11 = r11 + r3
            short r8 = r8[r11]
            int r8 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r8)
            int r16 = r16 + r8
            short[] r8 = r0._isRep
            short r8 = r8[r5]
            int r8 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r8)
            int r16 = r16 + r8
            int r8 = r10 + 1
            int r8 = r8 + r9
            r11 = r25
        L_0x056f:
            int r12 = r1 + r8
            if (r11 >= r12) goto L_0x057f
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r12 = r0._optimum
            int r11 = r11 + 1
            r12 = r12[r11]
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r12.Price = r14
            goto L_0x056f
        L_0x057f:
            r14 = 0
            int r3 = r0.GetRepPrice(r14, r9, r5, r3)
            int r3 = r16 + r3
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r5 = r0._optimum
            r5 = r5[r12]
            int r8 = r5.Price
            if (r3 >= r8) goto L_0x059f
            r5.Price = r3
            int r10 = r10 + r1
            r3 = 1
            int r10 = r10 + r3
            r5.PosPrev = r10
            r5.BackPrev = r14
            r5.Prev1IsChar = r3
            r5.Prev2 = r3
            r5.PosPrev2 = r1
            r5.BackPrev2 = r6
        L_0x059f:
            r18 = r11
            r8 = r24
            goto L_0x05bb
        L_0x05a4:
            r26 = r3
            r23 = r5
            r24 = r8
            r25 = r11
            r8 = r24
            r18 = r25
            goto L_0x05bb
        L_0x05b1:
            r25 = r11
            r9 = 4
            r14 = -1
            goto L_0x04b4
        L_0x05b7:
            r26 = r3
            r23 = r5
        L_0x05bb:
            int r6 = r6 + 1
            r5 = r23
            r3 = r26
            goto L_0x04a0
        L_0x05c3:
            r26 = r3
            r3 = r21
            if (r3 <= r7) goto L_0x05dc
            r3 = 0
        L_0x05ca:
            int[] r5 = r0._matchDistances
            r5 = r5[r3]
            if (r7 <= r5) goto L_0x05d3
            int r3 = r3 + 2
            goto L_0x05ca
        L_0x05d3:
            int[] r5 = r0._matchDistances
            r5[r3] = r7
            int r3 = r3 + 2
            r5 = r3
            r3 = r7
            goto L_0x05de
        L_0x05dc:
            r5 = r19
        L_0x05de:
            if (r3 < r8) goto L_0x0716
            short[] r6 = r0._isRep
            short r6 = r6[r4]
            int r6 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice0(r6)
            int r9 = r20 + r6
            r6 = r18
        L_0x05ec:
            int r7 = r1 + r3
            if (r6 >= r7) goto L_0x05fc
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r7 = r0._optimum
            int r6 = r6 + 1
            r7 = r7[r6]
            r10 = 268435455(0xfffffff, float:2.5243547E-29)
            r7.Price = r10
            goto L_0x05ec
        L_0x05fc:
            r3 = 0
        L_0x05fd:
            int[] r7 = r0._matchDistances
            r7 = r7[r3]
            if (r8 <= r7) goto L_0x0606
            int r3 = r3 + 2
            goto L_0x05fd
        L_0x0606:
            int[] r7 = r0._matchDistances
            int r10 = r3 + 1
            r7 = r7[r10]
            int r10 = r0.GetPosLenPrice(r7, r8, r13)
            int r10 = r10 + r9
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r11 = r0._optimum
            int r12 = r1 + r8
            r11 = r11[r12]
            int r14 = r11.Price
            if (r10 >= r14) goto L_0x0626
            r11.Price = r10
            r11.PosPrev = r1
            int r14 = r7 + 4
            r11.BackPrev = r14
            r14 = 0
            r11.Prev1IsChar = r14
        L_0x0626:
            int[] r11 = r0._matchDistances
            r11 = r11[r3]
            if (r8 != r11) goto L_0x06f9
            if (r8 >= r2) goto L_0x06e2
            int r11 = r2 + -1
            int r11 = r11 - r8
            int r14 = r0._numFastBytes
            int r11 = java.lang.Math.min(r11, r14)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r14 = r0._matchFinder
            int r11 = r14.GetMatchLen(r8, r7, r11)
            r14 = 2
            if (r11 < r14) goto L_0x06e2
            int r15 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateMatch(r4)
            int r14 = r26 + r8
            r27 = r2
            int r2 = r0._posStateMask
            r2 = r2 & r14
            r28 = r4
            short[] r4 = r0._isMatch
            int r16 = r15 << 4
            int r16 = r16 + r2
            short r2 = r4[r16]
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice0(r2)
            int r10 = r10 + r2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder r2 = r0._literalEncoder
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r4 = r0._matchFinder
            r29 = r6
            int r6 = r8 + -1
            r30 = r9
            int r9 = r6 + -1
            byte r4 = r4.GetIndexByte(r9)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$LiteralEncoder$Encoder2 r2 = r2.GetSubCoder(r14, r4)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r4 = r0._matchFinder
            int r9 = r7 + 1
            int r9 = r8 - r9
            r31 = r13
            r13 = 1
            int r9 = r9 - r13
            byte r4 = r4.GetIndexByte(r9)
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree r9 = r0._matchFinder
            byte r6 = r9.GetIndexByte(r6)
            int r2 = r2.GetPrice(r13, r4, r6)
            int r10 = r10 + r2
            int r2 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base.StateUpdateChar(r15)
            int r14 = r14 + r13
            int r4 = r0._posStateMask
            r4 = r4 & r14
            short[] r6 = r0._isMatch
            int r9 = r2 << 4
            int r9 = r9 + r4
            short r6 = r6[r9]
            int r6 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r6)
            int r10 = r10 + r6
            short[] r6 = r0._isRep
            short r6 = r6[r2]
            int r6 = com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.RangeCoder_Encoder.GetPrice1(r6)
            int r10 = r10 + r6
            int r6 = r8 + 1
            int r6 = r6 + r11
            r9 = r29
        L_0x06a9:
            int r13 = r1 + r6
            if (r9 >= r13) goto L_0x06b9
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r13 = r0._optimum
            int r9 = r9 + 1
            r13 = r13[r9]
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r13.Price = r14
            goto L_0x06a9
        L_0x06b9:
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r15 = 0
            int r2 = r0.GetRepPrice(r15, r11, r2, r4)
            int r10 = r10 + r2
            com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder$Optimal[] r2 = r0._optimum
            r2 = r2[r13]
            int r4 = r2.Price
            if (r10 >= r4) goto L_0x06de
            r2.Price = r10
            int r12 = r12 + 1
            r2.PosPrev = r12
            r2.BackPrev = r15
            r6 = 1
            r2.Prev1IsChar = r6
            r2.Prev2 = r6
            r2.PosPrev2 = r1
            int r7 = r7 + 4
            r2.BackPrev2 = r7
            goto L_0x06df
        L_0x06de:
            r6 = 1
        L_0x06df:
            r29 = r9
            goto L_0x06f1
        L_0x06e2:
            r27 = r2
            r28 = r4
            r29 = r6
            r30 = r9
            r31 = r13
            r6 = 1
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r15 = 0
        L_0x06f1:
            int r3 = r3 + 2
            if (r3 == r5) goto L_0x06f6
            goto L_0x0708
        L_0x06f6:
            r5 = r29
            goto L_0x071d
        L_0x06f9:
            r27 = r2
            r28 = r4
            r29 = r6
            r30 = r9
            r31 = r13
            r6 = 1
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r15 = 0
        L_0x0708:
            int r8 = r8 + 1
            r2 = r27
            r4 = r28
            r6 = r29
            r9 = r30
            r13 = r31
            goto L_0x0606
        L_0x0716:
            r6 = 1
            r14 = 268435455(0xfffffff, float:2.5243547E-29)
            r15 = 0
            r5 = r18
        L_0x071d:
            r3 = r26
            goto L_0x0724
        L_0x0720:
            r26 = r3
            r5 = r18
        L_0x0724:
            r2 = 0
            r4 = 1
            r8 = 2
            goto L_0x020c
        L_0x0729:
            r9 = r15
            goto L_0x0173
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Encoder.GetOptimum(int):int");
    }

    /* access modifiers changed from: 0000 */
    public void WriteEndMarker(int i) throws IOException {
        if (this._writeEndMark) {
            this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + i, 1);
            this._rangeEncoder.Encode(this._isRep, this._state, 0);
            this._state = LZMA_Base.StateUpdateMatch(this._state);
            this._lenEncoder.Encode(this._rangeEncoder, 0, i);
            this._posSlotEncoder[LZMA_Base.GetLenToPosState(2)].Encode(this._rangeEncoder, 63);
            this._rangeEncoder.EncodeDirectBits(67108863, 26);
            this._posAlignEncoder.ReverseEncode(this._rangeEncoder, 15);
        }
    }

    /* access modifiers changed from: 0000 */
    public void Flush(int i) throws IOException {
        ReleaseMFStream();
        WriteEndMarker(i & this._posStateMask);
        this._rangeEncoder.FlushData();
        this._rangeEncoder.FlushStream();
    }

    public void CodeOneBlock(long[] jArr, long[] jArr2, boolean[] zArr) throws IOException {
        jArr[0] = 0;
        jArr2[0] = 0;
        zArr[0] = true;
        if (this._inStream != null) {
            this._matchFinder.SetStream(this._inStream);
            this._matchFinder.Init();
            this._needReleaseMFStream = true;
            this._inStream = null;
        }
        if (!this._finished) {
            this._finished = true;
            long j = this.nowPos64;
            if (this.nowPos64 == 0) {
                if (this._matchFinder.GetNumAvailableBytes() == 0) {
                    Flush((int) this.nowPos64);
                    return;
                }
                ReadMatchDistances();
                this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + (((int) this.nowPos64) & this._posStateMask), 0);
                this._state = LZMA_Base.StateUpdateChar(this._state);
                byte GetIndexByte = this._matchFinder.GetIndexByte(0 - this._additionalOffset);
                this._literalEncoder.GetSubCoder((int) this.nowPos64, this._previousByte).Encode(this._rangeEncoder, GetIndexByte);
                this._previousByte = GetIndexByte;
                this._additionalOffset--;
                this.nowPos64++;
            }
            if (this._matchFinder.GetNumAvailableBytes() == 0) {
                Flush((int) this.nowPos64);
                return;
            }
            while (true) {
                int GetOptimum = GetOptimum((int) this.nowPos64);
                int i = this.backRes;
                int i2 = ((int) this.nowPos64) & this._posStateMask;
                int i3 = (this._state << 4) + i2;
                if (GetOptimum == 1 && i == -1) {
                    this._rangeEncoder.Encode(this._isMatch, i3, 0);
                    byte GetIndexByte2 = this._matchFinder.GetIndexByte(0 - this._additionalOffset);
                    Encoder2 GetSubCoder = this._literalEncoder.GetSubCoder((int) this.nowPos64, this._previousByte);
                    if (!LZMA_Base.StateIsCharState(this._state)) {
                        GetSubCoder.EncodeMatched(this._rangeEncoder, this._matchFinder.GetIndexByte(((0 - this._repDistances[0]) - 1) - this._additionalOffset), GetIndexByte2);
                    } else {
                        GetSubCoder.Encode(this._rangeEncoder, GetIndexByte2);
                    }
                    this._previousByte = GetIndexByte2;
                    this._state = LZMA_Base.StateUpdateChar(this._state);
                } else {
                    this._rangeEncoder.Encode(this._isMatch, i3, 1);
                    if (i < 4) {
                        this._rangeEncoder.Encode(this._isRep, this._state, 1);
                        if (i == 0) {
                            this._rangeEncoder.Encode(this._isRepG0, this._state, 0);
                            if (GetOptimum == 1) {
                                this._rangeEncoder.Encode(this._isRep0Long, i3, 0);
                            } else {
                                this._rangeEncoder.Encode(this._isRep0Long, i3, 1);
                            }
                        } else {
                            this._rangeEncoder.Encode(this._isRepG0, this._state, 1);
                            if (i == 1) {
                                this._rangeEncoder.Encode(this._isRepG1, this._state, 0);
                            } else {
                                this._rangeEncoder.Encode(this._isRepG1, this._state, 1);
                                this._rangeEncoder.Encode(this._isRepG2, this._state, i - 2);
                            }
                        }
                        if (GetOptimum == 1) {
                            this._state = LZMA_Base.StateUpdateShortRep(this._state);
                        } else {
                            this._repMatchLenEncoder.Encode(this._rangeEncoder, GetOptimum - 2, i2);
                            this._state = LZMA_Base.StateUpdateRep(this._state);
                        }
                        int i4 = this._repDistances[i];
                        if (i != 0) {
                            while (i > 0) {
                                int[] iArr = this._repDistances;
                                iArr[i] = iArr[i - 1];
                                i--;
                            }
                            this._repDistances[0] = i4;
                        }
                    } else {
                        this._rangeEncoder.Encode(this._isRep, this._state, 0);
                        this._state = LZMA_Base.StateUpdateMatch(this._state);
                        this._lenEncoder.Encode(this._rangeEncoder, GetOptimum - 2, i2);
                        int i5 = i - 4;
                        int GetPosSlot = GetPosSlot(i5);
                        this._posSlotEncoder[LZMA_Base.GetLenToPosState(GetOptimum)].Encode(this._rangeEncoder, GetPosSlot);
                        if (GetPosSlot >= 4) {
                            int i6 = (GetPosSlot >> 1) - 1;
                            int i7 = ((GetPosSlot & 1) | 2) << i6;
                            int i8 = i5 - i7;
                            if (GetPosSlot < 14) {
                                RangeCoder_BitTreeEncoder.ReverseEncode(this._posEncoders, (i7 - GetPosSlot) - 1, this._rangeEncoder, i6, i8);
                            } else {
                                this._rangeEncoder.EncodeDirectBits(i8 >> 4, i6 - 4);
                                this._posAlignEncoder.ReverseEncode(this._rangeEncoder, i8 & 15);
                                this._alignPriceCount++;
                            }
                        }
                        for (int i9 = 3; i9 > 0; i9--) {
                            int[] iArr2 = this._repDistances;
                            iArr2[i9] = iArr2[i9 - 1];
                        }
                        this._repDistances[0] = i5;
                        this._matchPriceCount++;
                    }
                    this._previousByte = this._matchFinder.GetIndexByte((GetOptimum - 1) - this._additionalOffset);
                }
                this._additionalOffset -= GetOptimum;
                this.nowPos64 += (long) GetOptimum;
                if (this._additionalOffset == 0) {
                    if (this._matchPriceCount >= 128) {
                        FillDistancesPrices();
                    }
                    if (this._alignPriceCount >= 16) {
                        FillAlignPrices();
                    }
                    jArr[0] = this.nowPos64;
                    jArr2[0] = this._rangeEncoder.GetProcessedSizeAdd();
                    if (this._matchFinder.GetNumAvailableBytes() == 0) {
                        Flush((int) this.nowPos64);
                        return;
                    } else if (this.nowPos64 - j >= 4096) {
                        this._finished = false;
                        zArr[0] = false;
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void ReleaseMFStream() {
        if (this._matchFinder != null && this._needReleaseMFStream) {
            this._matchFinder.ReleaseStream();
            this._needReleaseMFStream = false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void SetOutStream(OutputStream outputStream) {
        this._rangeEncoder.SetStream(outputStream);
    }

    /* access modifiers changed from: 0000 */
    public void ReleaseOutStream() {
        this._rangeEncoder.ReleaseStream();
    }

    /* access modifiers changed from: 0000 */
    public void ReleaseStreams() {
        ReleaseMFStream();
        ReleaseOutStream();
    }

    /* access modifiers changed from: 0000 */
    public void SetStreams(InputStream inputStream, OutputStream outputStream, long j, long j2) {
        this._inStream = inputStream;
        this._finished = false;
        Create();
        SetOutStream(outputStream);
        Init();
        FillDistancesPrices();
        FillAlignPrices();
        this._lenEncoder.SetTableSize((this._numFastBytes + 1) - 2);
        this._lenEncoder.UpdateTables(1 << this._posStateBits);
        this._repMatchLenEncoder.SetTableSize((this._numFastBytes + 1) - 2);
        this._repMatchLenEncoder.UpdateTables(1 << this._posStateBits);
        this.nowPos64 = 0;
    }

    public void Code(InputStream inputStream, OutputStream outputStream, long j, long j2, ICodeProgress iCodeProgress) throws IOException {
        this._needReleaseMFStream = false;
        try {
            SetStreams(inputStream, outputStream, j, j2);
            while (true) {
                CodeOneBlock(this.processedInSize, this.processedOutSize, this.finished);
                if (!this.finished[0]) {
                    if (iCodeProgress != null) {
                        iCodeProgress.SetProgress(this.processedInSize[0], this.processedOutSize[0]);
                    }
                } else {
                    return;
                }
            }
        } finally {
            ReleaseStreams();
        }
    }

    public void WriteCoderProperties(OutputStream outputStream) throws IOException {
        this.properties[0] = (byte) ((((this._posStateBits * 5) + this._numLiteralPosStateBits) * 9) + this._numLiteralContextBits);
        int i = 0;
        while (i < 4) {
            int i2 = i + 1;
            this.properties[i2] = (byte) (this._dictionarySize >> (i * 8));
            i = i2;
        }
        outputStream.write(this.properties, 0, 5);
    }

    /* access modifiers changed from: 0000 */
    public void FillDistancesPrices() {
        for (int i = 4; i < 128; i++) {
            int GetPosSlot = GetPosSlot(i);
            int i2 = (GetPosSlot >> 1) - 1;
            int i3 = ((GetPosSlot & 1) | 2) << i2;
            this.tempPrices[i] = RangeCoder_BitTreeEncoder.ReverseGetPrice(this._posEncoders, (i3 - GetPosSlot) - 1, i2, i - i3);
        }
        for (int i4 = 0; i4 < 4; i4++) {
            RangeCoder_BitTreeEncoder rangeCoder_BitTreeEncoder = this._posSlotEncoder[i4];
            int i5 = i4 << 6;
            for (int i6 = 0; i6 < this._distTableSize; i6++) {
                this._posSlotPrices[i5 + i6] = rangeCoder_BitTreeEncoder.GetPrice(i6);
            }
            for (int i7 = 14; i7 < this._distTableSize; i7++) {
                int[] iArr = this._posSlotPrices;
                int i8 = i5 + i7;
                iArr[i8] = iArr[i8] + ((((i7 >> 1) - 1) - 4) << 6);
            }
            int i9 = i4 * 128;
            int i10 = 0;
            while (i10 < 4) {
                this._distancesPrices[i9 + i10] = this._posSlotPrices[i5 + i10];
                i10++;
            }
            while (i10 < 128) {
                this._distancesPrices[i9 + i10] = this._posSlotPrices[GetPosSlot(i10) + i5] + this.tempPrices[i10];
                i10++;
            }
        }
        this._matchPriceCount = 0;
    }

    /* access modifiers changed from: 0000 */
    public void FillAlignPrices() {
        for (int i = 0; i < 16; i++) {
            this._alignPrices[i] = this._posAlignEncoder.ReverseGetPrice(i);
        }
        this._alignPriceCount = 0;
    }

    public boolean SetDictionarySize(int i) {
        int i2 = 0;
        if (i <= 0 || i > 536870912) {
            return false;
        }
        this._dictionarySize = i;
        while (i > (1 << i2)) {
            i2++;
        }
        this._distTableSize = i2 * 2;
        return true;
    }

    public boolean SetNumFastBytes(int i) {
        if (i < 5 || i > 273) {
            return false;
        }
        this._numFastBytes = i;
        return true;
    }

    public boolean SetMatchFinder(int i) {
        if (i < 0 || i > 2) {
            return false;
        }
        int i2 = this._matchFinderType;
        this._matchFinderType = i;
        if (!(this._matchFinder == null || i2 == this._matchFinderType)) {
            this._dictionarySizePrev = -1;
            this._matchFinder = null;
        }
        return true;
    }

    public boolean SetLcLpPb(int i, int i2, int i3) {
        if (i2 < 0 || i2 > 4 || i < 0 || i > 8 || i3 < 0 || i3 > 4) {
            return false;
        }
        this._numLiteralPosStateBits = i2;
        this._numLiteralContextBits = i;
        this._posStateBits = i3;
        this._posStateMask = (1 << this._posStateBits) - 1;
        return true;
    }

    public void SetEndMarkerMode(boolean z) {
        this._writeEndMark = z;
    }
}
