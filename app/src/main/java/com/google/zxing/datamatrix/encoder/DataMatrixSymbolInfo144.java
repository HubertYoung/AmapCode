package com.google.zxing.datamatrix.encoder;

final class DataMatrixSymbolInfo144 extends SymbolInfo {
    public final int getDataLengthForInterleavedBlock(int i) {
        return i <= 8 ? 156 : 155;
    }

    public final int getInterleavedBlockCount() {
        return 10;
    }

    DataMatrixSymbolInfo144() {
        super(false, 1558, 620, 22, 22, 36, -1, 62);
    }
}
