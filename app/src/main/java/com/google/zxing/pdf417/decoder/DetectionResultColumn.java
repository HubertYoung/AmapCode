package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox2) {
        this.boundingBox = new BoundingBox(boundingBox2);
        this.codewords = new Codeword[((boundingBox2.getMaxY() - boundingBox2.getMinY()) + 1)];
    }

    /* access modifiers changed from: 0000 */
    public final Codeword getCodewordNearby(int i) {
        Codeword codeword = getCodeword(i);
        if (codeword != null) {
            return codeword;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            int imageRowToCodewordIndex = imageRowToCodewordIndex(i) - i2;
            if (imageRowToCodewordIndex >= 0) {
                Codeword codeword2 = this.codewords[imageRowToCodewordIndex];
                if (codeword2 != null) {
                    return codeword2;
                }
            }
            int imageRowToCodewordIndex2 = imageRowToCodewordIndex(i) + i2;
            if (imageRowToCodewordIndex2 < this.codewords.length) {
                Codeword codeword3 = this.codewords[imageRowToCodewordIndex2];
                if (codeword3 != null) {
                    return codeword3;
                }
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final int imageRowToCodewordIndex(int i) {
        return i - this.boundingBox.getMinY();
    }

    /* access modifiers changed from: 0000 */
    public final void setCodeword(int i, Codeword codeword) {
        this.codewords[imageRowToCodewordIndex(i)] = codeword;
    }

    /* access modifiers changed from: 0000 */
    public final Codeword getCodeword(int i) {
        return this.codewords[imageRowToCodewordIndex(i)];
    }

    /* access modifiers changed from: 0000 */
    public final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    /* access modifiers changed from: 0000 */
    public final Codeword[] getCodewords() {
        return this.codewords;
    }

    public String toString() {
        Codeword[] codewordArr;
        Formatter formatter = new Formatter();
        int i = 0;
        for (Codeword codeword : this.codewords) {
            if (codeword == null) {
                formatter.format("%3d:    |   %n", new Object[]{Integer.valueOf(i)});
                i++;
            } else {
                formatter.format("%3d: %3d|%3d%n", new Object[]{Integer.valueOf(i), Integer.valueOf(codeword.getRowNumber()), Integer.valueOf(codeword.getValue())});
                i++;
            }
        }
        String formatter2 = formatter.toString();
        formatter.close();
        return formatter2;
    }
}
