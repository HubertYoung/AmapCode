package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

import java.io.IOException;

public class LZ_BinTree extends LZ_InWindow {
    private static final int[] CrcTable = new int[256];
    static final int kBT2HashSize = 65536;
    static final int kEmptyHashValue = 0;
    static final int kHash2Size = 1024;
    static final int kHash3Offset = 1024;
    static final int kHash3Size = 65536;
    static final int kMaxValForNormalize = 1073741823;
    static final int kStartMaxLen = 1;
    boolean HASH_ARRAY = true;
    int _cutValue = 255;
    int _cyclicBufferPos;
    int _cyclicBufferSize = 0;
    int[] _hash;
    int _hashMask;
    int _hashSizeSum = 0;
    int _matchMaxLen;
    int[] _son;
    int kFixHashSize = 66560;
    int kMinMatchCheck = 4;
    int kNumHashDirectBytes = 0;

    public void SetType(int i) {
        this.HASH_ARRAY = i > 2;
        if (this.HASH_ARRAY) {
            this.kNumHashDirectBytes = 0;
            this.kMinMatchCheck = 4;
            this.kFixHashSize = 66560;
            return;
        }
        this.kNumHashDirectBytes = 2;
        this.kMinMatchCheck = 3;
        this.kFixHashSize = 0;
    }

    public void Init() throws IOException {
        super.Init();
        for (int i = 0; i < this._hashSizeSum; i++) {
            this._hash[i] = 0;
        }
        this._cyclicBufferPos = 0;
        ReduceOffsets(-1);
    }

    public void MovePos() throws IOException {
        int i = this._cyclicBufferPos + 1;
        this._cyclicBufferPos = i;
        if (i >= this._cyclicBufferSize) {
            this._cyclicBufferPos = 0;
        }
        super.MovePos();
        if (this._pos == kMaxValForNormalize) {
            Normalize();
        }
    }

    public boolean Create(int i, int i2, int i3, int i4) {
        if (i > 1073741567) {
            return false;
        }
        this._cutValue = (i3 >> 1) + 16;
        int i5 = i2 + i;
        super.Create(i5, i4 + i3, (((i5 + i3) + i4) / 2) + 256);
        this._matchMaxLen = i3;
        int i6 = i + 1;
        if (this._cyclicBufferSize != i6) {
            this._cyclicBufferSize = i6;
            this._son = new int[(i6 * 2)];
        }
        int i7 = 65536;
        if (this.HASH_ARRAY) {
            int i8 = i - 1;
            int i9 = i8 | (i8 >> 1);
            int i10 = i9 | (i9 >> 2);
            int i11 = i10 | (i10 >> 4);
            int i12 = ((i11 | (i11 >> 8)) >> 1) | 65535;
            if (i12 > 16777216) {
                i12 >>= 1;
            }
            this._hashMask = i12;
            i7 = this.kFixHashSize + i12 + 1;
        }
        if (i7 != this._hashSizeSum) {
            this._hashSizeSum = i7;
            this._hash = new int[i7];
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x0125  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01cf A[EDGE_INSN: B:74:0x01cf->B:70:0x01cf ?: BREAK  
    EDGE_INSN: B:74:0x01cf->B:70:0x01cf ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int GetMatches(int[] r22) throws java.io.IOException {
        /*
            r21 = this;
            r0 = r21
            int r2 = r0._pos
            int r3 = r0._matchMaxLen
            int r2 = r2 + r3
            int r3 = r0._streamPos
            r4 = 0
            if (r2 > r3) goto L_0x000f
            int r2 = r0._matchMaxLen
            goto L_0x001c
        L_0x000f:
            int r2 = r0._streamPos
            int r3 = r0._pos
            int r2 = r2 - r3
            int r3 = r0.kMinMatchCheck
            if (r2 >= r3) goto L_0x001c
            r21.MovePos()
            return r4
        L_0x001c:
            int r3 = r0._pos
            int r5 = r0._cyclicBufferSize
            if (r3 <= r5) goto L_0x0028
            int r3 = r0._pos
            int r5 = r0._cyclicBufferSize
            int r3 = r3 - r5
            goto L_0x0029
        L_0x0028:
            r3 = 0
        L_0x0029:
            int r5 = r0._bufferOffset
            int r6 = r0._pos
            int r5 = r5 + r6
            boolean r6 = r0.HASH_ARRAY
            if (r6 == 0) goto L_0x0069
            int[] r6 = CrcTable
            byte[] r7 = r0._bufferBase
            byte r7 = r7[r5]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r6 = r6[r7]
            byte[] r7 = r0._bufferBase
            int r8 = r5 + 1
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r6 = r6 ^ r7
            r7 = r6 & 1023(0x3ff, float:1.434E-42)
            byte[] r8 = r0._bufferBase
            int r9 = r5 + 2
            byte r8 = r8[r9]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r8 << 8
            r6 = r6 ^ r8
            r8 = 65535(0xffff, float:9.1834E-41)
            r8 = r8 & r6
            int[] r9 = CrcTable
            byte[] r10 = r0._bufferBase
            int r11 = r5 + 3
            byte r10 = r10[r11]
            r10 = r10 & 255(0xff, float:3.57E-43)
            r9 = r9[r10]
            int r9 = r9 << 5
            r6 = r6 ^ r9
            int r9 = r0._hashMask
            r6 = r6 & r9
            goto L_0x007c
        L_0x0069:
            byte[] r6 = r0._bufferBase
            byte r6 = r6[r5]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte[] r7 = r0._bufferBase
            int r8 = r5 + 1
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r6 = r6 ^ r7
            r7 = 0
            r8 = 0
        L_0x007c:
            int[] r9 = r0._hash
            int r10 = r0.kFixHashSize
            int r10 = r10 + r6
            r9 = r9[r10]
            boolean r10 = r0.HASH_ARRAY
            r11 = 1
            if (r10 == 0) goto L_0x00e4
            int[] r10 = r0._hash
            r10 = r10[r7]
            int[] r12 = r0._hash
            int r8 = r8 + 1024
            r12 = r12[r8]
            int[] r13 = r0._hash
            int r14 = r0._pos
            r13[r7] = r14
            int[] r7 = r0._hash
            int r13 = r0._pos
            r7[r8] = r13
            r7 = 2
            if (r10 <= r3) goto L_0x00b8
            byte[] r8 = r0._bufferBase
            int r13 = r0._bufferOffset
            int r13 = r13 + r10
            byte r8 = r8[r13]
            byte[] r13 = r0._bufferBase
            byte r13 = r13[r5]
            if (r8 != r13) goto L_0x00b8
            r22[r4] = r7
            int r8 = r0._pos
            int r8 = r8 - r10
            int r8 = r8 - r11
            r22[r11] = r8
            r8 = 2
            goto L_0x00ba
        L_0x00b8:
            r7 = 0
            r8 = 1
        L_0x00ba:
            r13 = 3
            if (r12 <= r3) goto L_0x00db
            byte[] r14 = r0._bufferBase
            int r15 = r0._bufferOffset
            int r15 = r15 + r12
            byte r14 = r14[r15]
            byte[] r15 = r0._bufferBase
            byte r15 = r15[r5]
            if (r14 != r15) goto L_0x00db
            if (r12 != r10) goto L_0x00ce
            int r7 = r7 + -2
        L_0x00ce:
            int r8 = r7 + 1
            r22[r7] = r13
            int r7 = r8 + 1
            int r10 = r0._pos
            int r10 = r10 - r12
            int r10 = r10 - r11
            r22[r8] = r10
            goto L_0x00dd
        L_0x00db:
            r13 = r8
            r12 = r10
        L_0x00dd:
            if (r7 == 0) goto L_0x00e6
            if (r12 != r9) goto L_0x00e6
            int r7 = r7 + -2
            goto L_0x00e5
        L_0x00e4:
            r7 = 0
        L_0x00e5:
            r13 = 1
        L_0x00e6:
            int[] r8 = r0._hash
            int r10 = r0.kFixHashSize
            int r10 = r10 + r6
            int r6 = r0._pos
            r8[r10] = r6
            int r6 = r0._cyclicBufferPos
            int r6 = r6 << r11
            int r6 = r6 + r11
            int r8 = r0._cyclicBufferPos
            int r8 = r8 << r11
            int r10 = r0.kNumHashDirectBytes
            int r12 = r0.kNumHashDirectBytes
            if (r12 == 0) goto L_0x011f
            if (r9 <= r3) goto L_0x011f
            byte[] r12 = r0._bufferBase
            int r14 = r0._bufferOffset
            int r14 = r14 + r9
            int r15 = r0.kNumHashDirectBytes
            int r14 = r14 + r15
            byte r12 = r12[r14]
            byte[] r14 = r0._bufferBase
            int r15 = r0.kNumHashDirectBytes
            int r15 = r15 + r5
            byte r14 = r14[r15]
            if (r12 == r14) goto L_0x011f
            int r12 = r7 + 1
            int r13 = r0.kNumHashDirectBytes
            r22[r7] = r13
            int r7 = r12 + 1
            int r14 = r0._pos
            int r14 = r14 - r9
            int r14 = r14 - r11
            r22[r12] = r14
        L_0x011f:
            int r12 = r0._cutValue
            r14 = r7
            r7 = r10
        L_0x0123:
            if (r9 <= r3) goto L_0x01cf
            int r15 = r12 + -1
            if (r12 != 0) goto L_0x012b
            goto L_0x01cf
        L_0x012b:
            int r12 = r0._pos
            int r12 = r12 - r9
            int r4 = r0._cyclicBufferPos
            if (r12 > r4) goto L_0x0136
            int r4 = r0._cyclicBufferPos
            int r4 = r4 - r12
            goto L_0x013d
        L_0x0136:
            int r4 = r0._cyclicBufferPos
            int r4 = r4 - r12
            int r11 = r0._cyclicBufferSize
            int r4 = r4 + r11
            r11 = 1
        L_0x013d:
            int r4 = r4 << r11
            int r11 = r0._bufferOffset
            int r11 = r11 + r9
            int r16 = java.lang.Math.min(r10, r7)
            r17 = r3
            byte[] r3 = r0._bufferBase
            int r18 = r11 + r16
            byte r3 = r3[r18]
            r19 = r7
            byte[] r7 = r0._bufferBase
            int r18 = r5 + r16
            byte r7 = r7[r18]
            if (r3 != r7) goto L_0x0196
        L_0x0157:
            r3 = 1
            int r7 = r16 + 1
            if (r7 == r2) goto L_0x0172
            byte[] r3 = r0._bufferBase
            int r16 = r11 + r7
            byte r3 = r3[r16]
            r20 = r10
            byte[] r10 = r0._bufferBase
            int r16 = r5 + r7
            byte r10 = r10[r16]
            if (r3 == r10) goto L_0x016d
            goto L_0x0174
        L_0x016d:
            r16 = r7
            r10 = r20
            goto L_0x0157
        L_0x0172:
            r20 = r10
        L_0x0174:
            if (r13 >= r7) goto L_0x0194
            int r3 = r14 + 1
            r22[r14] = r7
            int r14 = r3 + 1
            int r12 = r12 + -1
            r22[r3] = r12
            if (r7 != r2) goto L_0x0191
            int[] r1 = r0._son
            r2 = r1[r4]
            r1[r8] = r2
            int[] r1 = r0._son
            r3 = 1
            int r4 = r4 + r3
            r2 = r1[r4]
            r1[r6] = r2
            goto L_0x01d6
        L_0x0191:
            r3 = 1
            r13 = r7
            goto L_0x019b
        L_0x0194:
            r3 = 1
            goto L_0x019b
        L_0x0196:
            r20 = r10
            r3 = 1
            r7 = r16
        L_0x019b:
            byte[] r10 = r0._bufferBase
            int r11 = r11 + r7
            byte r10 = r10[r11]
            r10 = r10 & 255(0xff, float:3.57E-43)
            byte[] r11 = r0._bufferBase
            int r12 = r5 + r7
            byte r11 = r11[r12]
            r11 = r11 & 255(0xff, float:3.57E-43)
            if (r10 >= r11) goto L_0x01bf
            int[] r10 = r0._son
            r10[r8] = r9
            int r8 = r4 + 1
            int[] r4 = r0._son
            r9 = r4[r8]
            r12 = r15
            r3 = r17
            r10 = r20
        L_0x01bb:
            r4 = 0
            r11 = 1
            goto L_0x0123
        L_0x01bf:
            int[] r10 = r0._son
            r10[r6] = r9
            int[] r6 = r0._son
            r9 = r6[r4]
            r6 = r4
            r10 = r7
            r12 = r15
            r3 = r17
            r7 = r19
            goto L_0x01bb
        L_0x01cf:
            int[] r1 = r0._son
            r2 = 0
            r1[r8] = r2
            r1[r6] = r2
        L_0x01d6:
            r21.MovePos()
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree.GetMatches(int[]):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0017, code lost:
        if (r2 >= r0.kMinMatchCheck) goto L_0x0019;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void Skip(int r19) throws java.io.IOException {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
        L_0x0004:
            int r2 = r0._pos
            int r3 = r0._matchMaxLen
            int r2 = r2 + r3
            int r3 = r0._streamPos
            if (r2 > r3) goto L_0x0010
            int r2 = r0._matchMaxLen
            goto L_0x0019
        L_0x0010:
            int r2 = r0._streamPos
            int r3 = r0._pos
            int r2 = r2 - r3
            int r3 = r0.kMinMatchCheck
            if (r2 < r3) goto L_0x0128
        L_0x0019:
            int r3 = r0._pos
            int r4 = r0._cyclicBufferSize
            if (r3 <= r4) goto L_0x0025
            int r3 = r0._pos
            int r4 = r0._cyclicBufferSize
            int r3 = r3 - r4
            goto L_0x0026
        L_0x0025:
            r3 = 0
        L_0x0026:
            int r4 = r0._bufferOffset
            int r6 = r0._pos
            int r4 = r4 + r6
            boolean r6 = r0.HASH_ARRAY
            if (r6 == 0) goto L_0x0074
            int[] r6 = CrcTable
            byte[] r7 = r0._bufferBase
            byte r7 = r7[r4]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r6 = r6[r7]
            byte[] r7 = r0._bufferBase
            int r8 = r4 + 1
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            r6 = r6 ^ r7
            r7 = r6 & 1023(0x3ff, float:1.434E-42)
            int[] r8 = r0._hash
            int r9 = r0._pos
            r8[r7] = r9
            byte[] r7 = r0._bufferBase
            int r8 = r4 + 2
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r6 = r6 ^ r7
            r7 = 65535(0xffff, float:9.1834E-41)
            r7 = r7 & r6
            int[] r8 = r0._hash
            int r7 = r7 + 1024
            int r9 = r0._pos
            r8[r7] = r9
            int[] r7 = CrcTable
            byte[] r8 = r0._bufferBase
            int r9 = r4 + 3
            byte r8 = r8[r9]
            r8 = r8 & 255(0xff, float:3.57E-43)
            r7 = r7[r8]
            int r7 = r7 << 5
            r6 = r6 ^ r7
            int r7 = r0._hashMask
            r6 = r6 & r7
            goto L_0x0085
        L_0x0074:
            byte[] r6 = r0._bufferBase
            byte r6 = r6[r4]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte[] r7 = r0._bufferBase
            int r8 = r4 + 1
            byte r7 = r7[r8]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 << 8
            r6 = r6 ^ r7
        L_0x0085:
            int[] r7 = r0._hash
            int r8 = r0.kFixHashSize
            int r8 = r8 + r6
            r7 = r7[r8]
            int[] r8 = r0._hash
            int r9 = r0.kFixHashSize
            int r9 = r9 + r6
            int r6 = r0._pos
            r8[r9] = r6
            int r6 = r0._cyclicBufferPos
            int r6 = r6 << 1
            int r6 = r6 + 1
            int r8 = r0._cyclicBufferPos
            int r8 = r8 << 1
            int r9 = r0.kNumHashDirectBytes
            int r10 = r0._cutValue
            r11 = r9
        L_0x00a4:
            if (r7 <= r3) goto L_0x0121
            int r12 = r10 + -1
            if (r10 != 0) goto L_0x00ac
            goto L_0x0121
        L_0x00ac:
            int r10 = r0._pos
            int r10 = r10 - r7
            int r13 = r0._cyclicBufferPos
            if (r10 > r13) goto L_0x00b7
            int r13 = r0._cyclicBufferPos
            int r13 = r13 - r10
            goto L_0x00bd
        L_0x00b7:
            int r13 = r0._cyclicBufferPos
            int r13 = r13 - r10
            int r10 = r0._cyclicBufferSize
            int r13 = r13 + r10
        L_0x00bd:
            int r10 = r13 << 1
            int r13 = r0._bufferOffset
            int r13 = r13 + r7
            int r14 = java.lang.Math.min(r9, r11)
            byte[] r15 = r0._bufferBase
            int r16 = r13 + r14
            byte r15 = r15[r16]
            byte[] r5 = r0._bufferBase
            int r16 = r4 + r14
            byte r5 = r5[r16]
            if (r15 != r5) goto L_0x00f7
        L_0x00d4:
            int r14 = r14 + 1
            if (r14 == r2) goto L_0x00e6
            byte[] r5 = r0._bufferBase
            int r15 = r13 + r14
            byte r5 = r5[r15]
            byte[] r15 = r0._bufferBase
            int r16 = r4 + r14
            byte r15 = r15[r16]
            if (r5 == r15) goto L_0x00d4
        L_0x00e6:
            if (r14 != r2) goto L_0x00f7
            int[] r2 = r0._son
            r3 = r2[r10]
            r2[r8] = r3
            int[] r2 = r0._son
            int r10 = r10 + 1
            r3 = r2[r10]
            r2[r6] = r3
            goto L_0x0128
        L_0x00f7:
            byte[] r5 = r0._bufferBase
            int r13 = r13 + r14
            byte r5 = r5[r13]
            r5 = r5 & 255(0xff, float:3.57E-43)
            byte[] r13 = r0._bufferBase
            int r15 = r4 + r14
            byte r13 = r13[r15]
            r13 = r13 & 255(0xff, float:3.57E-43)
            if (r5 >= r13) goto L_0x0115
            int[] r5 = r0._son
            r5[r8] = r7
            int r8 = r10 + 1
            int[] r5 = r0._son
            r7 = r5[r8]
            r10 = r12
            r11 = r14
            goto L_0x00a4
        L_0x0115:
            int[] r5 = r0._son
            r5[r6] = r7
            int[] r5 = r0._son
            r7 = r5[r10]
            r6 = r10
            r10 = r12
            r9 = r14
            goto L_0x00a4
        L_0x0121:
            int[] r2 = r0._son
            r3 = 0
            r2[r8] = r3
            r2[r6] = r3
        L_0x0128:
            r18.MovePos()
            int r1 = r1 + -1
            if (r1 != 0) goto L_0x0004
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZ_BinTree.Skip(int):void");
    }

    /* access modifiers changed from: 0000 */
    public void NormalizeLinks(int[] iArr, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            iArr[i3] = i4 <= i2 ? 0 : i4 - i2;
        }
    }

    /* access modifiers changed from: 0000 */
    public void Normalize() {
        int i = this._pos - this._cyclicBufferSize;
        NormalizeLinks(this._son, this._cyclicBufferSize * 2, i);
        NormalizeLinks(this._hash, this._hashSizeSum, i);
        ReduceOffsets(i);
    }

    public void SetCutValue(int i) {
        this._cutValue = i;
    }

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (i2 & 1) != 0 ? (i2 >>> 1) ^ -306674912 : i2 >>> 1;
            }
            CrcTable[i] = i2;
        }
    }
}
