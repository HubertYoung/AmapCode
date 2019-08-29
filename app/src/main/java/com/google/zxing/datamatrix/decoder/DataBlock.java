package com.google.zxing.datamatrix.decoder;

final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version) {
        ECBlocks eCBlocks = version.getECBlocks();
        ECB[] eCBlocks2 = eCBlocks.getECBlocks();
        int i = 0;
        for (ECB count : eCBlocks2) {
            i += count.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[i];
        int length = eCBlocks2.length;
        int i2 = 0;
        int i3 = 0;
        while (i3 < length) {
            ECB ecb = eCBlocks2[i3];
            int i4 = i2;
            int i5 = 0;
            while (i5 < ecb.getCount()) {
                int dataCodewords = ecb.getDataCodewords();
                dataBlockArr[i4] = new DataBlock(dataCodewords, new byte[(eCBlocks.getECCodewords() + dataCodewords)]);
                i5++;
                i4++;
            }
            i3++;
            i2 = i4;
        }
        int length2 = dataBlockArr[0].codewords.length - eCBlocks.getECCodewords();
        int i6 = length2 - 1;
        int i7 = 0;
        int i8 = 0;
        while (i8 < i6) {
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
        boolean z = version.getVersionNumber() == 24;
        int i11 = z ? 8 : i2;
        int i12 = 0;
        while (i12 < i11) {
            dataBlockArr[i12].codewords[i6] = bArr[i7];
            i12++;
            i7++;
        }
        int length3 = dataBlockArr[0].codewords.length;
        for (int i13 = length2; i13 < length3; i13++) {
            int i14 = 0;
            while (i14 < i2) {
                int i15 = z ? (i14 + 8) % i2 : i14;
                dataBlockArr[i15].codewords[(!z || i15 <= 7) ? i13 : i13 - 1] = bArr[i7];
                i14++;
                i7++;
            }
        }
        if (i7 == bArr.length) {
            return dataBlockArr;
        }
        throw new IllegalArgumentException();
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
