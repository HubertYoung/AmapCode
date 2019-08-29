package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public final class BinaryBitmap {
    private final Binarizer binarizer;
    private BitMatrix matrix;

    public BinaryBitmap(Binarizer binarizer2) {
        if (binarizer2 == null) {
            throw new IllegalArgumentException("Binarizer must be non-null.");
        }
        this.binarizer = binarizer2;
    }

    public final int getWidth() {
        return this.binarizer.getWidth();
    }

    public final int getHeight() {
        return this.binarizer.getHeight();
    }

    public final BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException {
        return this.binarizer.getBlackRow(i, bitArray);
    }

    public final BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.matrix == null) {
            this.matrix = this.binarizer.getBlackMatrix();
        }
        return this.matrix;
    }

    public final boolean isCropSupported() {
        return this.binarizer.getLuminanceSource().isCropSupported();
    }

    public final BinaryBitmap crop(int i, int i2, int i3, int i4) {
        return new BinaryBitmap(this.binarizer.createBinarizer(this.binarizer.getLuminanceSource().crop(i, i2, i3, i4)));
    }

    public final boolean isRotateSupported() {
        return this.binarizer.getLuminanceSource().isRotateSupported();
    }

    public final BinaryBitmap rotateCounterClockwise() {
        return new BinaryBitmap(this.binarizer.createBinarizer(this.binarizer.getLuminanceSource().rotateCounterClockwise()));
    }

    public final BinaryBitmap rotateCounterClockwise45() {
        return new BinaryBitmap(this.binarizer.createBinarizer(this.binarizer.getLuminanceSource().rotateCounterClockwise45()));
    }

    public final String toString() {
        try {
            return getBlackMatrix().toString();
        } catch (NotFoundException unused) {
            return "";
        }
    }
}
