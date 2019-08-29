package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version.ECB;
import com.google.zxing.qrcode.decoder.Version.ECBlocks;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        if (bArr.length != version.getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        ECBlocks eCBlocksForLevel = version.getECBlocksForLevel(errorCorrectionLevel);
        ECB[] eCBlocks = eCBlocksForLevel.getECBlocks();
        int i = 0;
        for (ECB count : eCBlocks) {
            i += count.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[i];
        int length = eCBlocks.length;
        int i2 = 0;
        int i3 = 0;
        while (i3 < length) {
            ECB ecb = eCBlocks[i3];
            int i4 = i2;
            int i5 = 0;
            while (i5 < ecb.getCount()) {
                int dataCodewords = ecb.getDataCodewords();
                dataBlockArr[i4] = new DataBlock(dataCodewords, new byte[(eCBlocksForLevel.getECCodewordsPerBlock() + dataCodewords)]);
                i5++;
                i4++;
            }
            i3++;
            i2 = i4;
        }
        int length2 = dataBlockArr[0].codewords.length;
        int length3 = dataBlockArr.length - 1;
        while (length3 >= 0 && dataBlockArr[length3].codewords.length != length2) {
            length3--;
        }
        int i6 = length3 + 1;
        int eCCodewordsPerBlock = length2 - eCBlocksForLevel.getECCodewordsPerBlock();
        int i7 = 0;
        int i8 = 0;
        while (i8 < eCCodewordsPerBlock) {
            int i9 = i7;
            int i10 = 0;
            while (i10 < i2) {
                dataBlockArr[i10].codewords[i8] = bArr[i9];
                i10++;
                i9++;
            }
            i8++;
            i7 = i9;
        }
        int i11 = i7;
        int i12 = i6;
        while (i12 < i2) {
            dataBlockArr[i12].codewords[eCCodewordsPerBlock] = bArr[i11];
            i12++;
            i11++;
        }
        int length4 = dataBlockArr[0].codewords.length;
        int i13 = eCCodewordsPerBlock;
        while (i13 < length4) {
            int i14 = 0;
            while (i14 < i2) {
                dataBlockArr[i14].codewords[i14 < i6 ? i13 : i13 + 1] = bArr[i11];
                i14++;
                i11++;
            }
            i13++;
        }
        return dataBlockArr;
    }

    /* access modifiers changed from: 0000 */
    public final int getNumDataCodewords() {
        return this.numDataCodewords;
    }

    /* access modifiers changed from: 0000 */
    public final byte[] getCodewords() {
        return this.codewords;
    }
}
