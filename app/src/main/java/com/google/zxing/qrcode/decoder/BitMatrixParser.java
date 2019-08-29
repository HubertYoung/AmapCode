package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private boolean mirror;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    BitMatrixParser(BitMatrix bitMatrix2) throws FormatException {
        int height = bitMatrix2.getHeight();
        if (height < 21 || (height & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.bitMatrix = bitMatrix2;
    }

    /* access modifiers changed from: 0000 */
    public final FormatInformation readFormatInformation() throws FormatException {
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            i2 = copyBit(i3, 8, i2);
        }
        int copyBit = copyBit(8, 7, copyBit(8, 8, copyBit(7, 8, i2)));
        for (int i4 = 5; i4 >= 0; i4--) {
            copyBit = copyBit(8, i4, copyBit);
        }
        int height = this.bitMatrix.getHeight();
        int i5 = height - 7;
        for (int i6 = height - 1; i6 >= i5; i6--) {
            i = copyBit(8, i6, i);
        }
        for (int i7 = height - 8; i7 < height; i7++) {
            i = copyBit(i7, 8, i);
        }
        this.parsedFormatInfo = FormatInformation.decodeFormatInformation(copyBit, i);
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        throw FormatException.getFormatInstance();
    }

    /* access modifiers changed from: 0000 */
    public final Version readVersion() throws FormatException {
        if (this.parsedVersion != null) {
            return this.parsedVersion;
        }
        int height = this.bitMatrix.getHeight();
        int i = (height - 17) / 4;
        if (i <= 6) {
            return Version.getVersionForNumber(i);
        }
        int i2 = height - 11;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = height - 9; i6 >= i2; i6--) {
                i4 = copyBit(i6, i5, i4);
            }
        }
        Version decodeVersionInformation = Version.decodeVersionInformation(i4);
        if (decodeVersionInformation == null || decodeVersionInformation.getDimensionForVersion() != height) {
            for (int i7 = 5; i7 >= 0; i7--) {
                for (int i8 = height - 9; i8 >= i2; i8--) {
                    i3 = copyBit(i7, i8, i3);
                }
            }
            Version decodeVersionInformation2 = Version.decodeVersionInformation(i3);
            if (decodeVersionInformation2 == null || decodeVersionInformation2.getDimensionForVersion() != height) {
                throw FormatException.getFormatInstance();
            }
            this.parsedVersion = decodeVersionInformation2;
            return decodeVersionInformation2;
        }
        this.parsedVersion = decodeVersionInformation;
        return decodeVersionInformation;
    }

    private int copyBit(int i, int i2, int i3) {
        return this.mirror ? this.bitMatrix.get(i2, i) : this.bitMatrix.get(i, i2) ? (i3 << 1) | 1 : i3 << 1;
    }

    /* access modifiers changed from: 0000 */
    public final byte[] readCodewords() throws FormatException {
        FormatInformation readFormatInformation = readFormatInformation();
        Version readVersion = readVersion();
        DataMask dataMask = DataMask.values()[readFormatInformation.getDataMask()];
        int height = this.bitMatrix.getHeight();
        dataMask.unmaskBitMatrix(this.bitMatrix, height);
        BitMatrix buildFunctionPattern = readVersion.buildFunctionPattern();
        byte[] bArr = new byte[readVersion.getTotalCodewords()];
        int i = height - 1;
        int i2 = i;
        int i3 = 0;
        boolean z = true;
        int i4 = 0;
        int i5 = 0;
        while (i2 > 0) {
            if (i2 == 6) {
                i2--;
            }
            int i6 = i5;
            int i7 = i4;
            int i8 = i3;
            int i9 = 0;
            while (i9 < height) {
                int i10 = z ? i - i9 : i9;
                int i11 = i7;
                int i12 = i8;
                for (int i13 = 0; i13 < 2; i13++) {
                    int i14 = i2 - i13;
                    if (!buildFunctionPattern.get(i14, i10)) {
                        i6++;
                        int i15 = i11 << 1;
                        int i16 = this.bitMatrix.get(i14, i10) ? i15 | 1 : i15;
                        if (i6 == 8) {
                            bArr[i12] = (byte) i16;
                            i12++;
                            i6 = 0;
                            i11 = 0;
                        } else {
                            i11 = i16;
                        }
                    }
                }
                i9++;
                i8 = i12;
                i7 = i11;
            }
            z = !z;
            i2 -= 2;
            i3 = i8;
            i4 = i7;
            i5 = i6;
        }
        if (i3 == readVersion.getTotalCodewords()) {
            return bArr;
        }
        throw FormatException.getFormatInstance();
    }

    /* access modifiers changed from: 0000 */
    public final void remask() {
        if (this.parsedFormatInfo != null) {
            DataMask.values()[this.parsedFormatInfo.getDataMask()].unmaskBitMatrix(this.bitMatrix, this.bitMatrix.getHeight());
        }
    }

    /* access modifiers changed from: 0000 */
    public final void setMirror(boolean z) {
        this.parsedVersion = null;
        this.parsedFormatInfo = null;
        this.mirror = z;
    }

    /* access modifiers changed from: 0000 */
    public final void mirror() {
        int i = 0;
        while (i < this.bitMatrix.getWidth()) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < this.bitMatrix.getHeight(); i3++) {
                if (this.bitMatrix.get(i, i3) != this.bitMatrix.get(i3, i)) {
                    this.bitMatrix.flip(i3, i);
                    this.bitMatrix.flip(i, i3);
                }
            }
            i = i2;
        }
    }
}
