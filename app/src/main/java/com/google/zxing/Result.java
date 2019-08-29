package com.google.zxing;

import java.util.EnumMap;
import java.util.Map;

public final class Result {
    private final BarcodeFormat format;
    private final int numBits;
    private final byte[] rawBytes;
    private Map<ResultMetadataType, Object> resultMetadata;
    private ResultPoint[] resultPoints;
    private final String text;
    private final long timestamp;

    public Result(String str, byte[] bArr, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat) {
        this(str, bArr, resultPointArr, barcodeFormat, System.currentTimeMillis());
    }

    public Result(String str, byte[] bArr, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat, long j) {
        this(str, bArr, bArr == null ? 0 : bArr.length * 8, resultPointArr, barcodeFormat, j);
    }

    public Result(String str, byte[] bArr, int i, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat, long j) {
        this.text = str;
        this.rawBytes = bArr;
        this.numBits = i;
        this.resultPoints = resultPointArr;
        this.format = barcodeFormat;
        this.resultMetadata = null;
        this.timestamp = j;
    }

    public final String getText() {
        return this.text;
    }

    public final byte[] getRawBytes() {
        return this.rawBytes;
    }

    public final int getNumBits() {
        return this.numBits;
    }

    public final ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public final BarcodeFormat getBarcodeFormat() {
        return this.format;
    }

    public final Map<ResultMetadataType, Object> getResultMetadata() {
        return this.resultMetadata;
    }

    public final void putMetadata(ResultMetadataType resultMetadataType, Object obj) {
        if (this.resultMetadata == null) {
            this.resultMetadata = new EnumMap(ResultMetadataType.class);
        }
        this.resultMetadata.put(resultMetadataType, obj);
    }

    public final void putAllMetadata(Map<ResultMetadataType, Object> map) {
        if (map != null) {
            if (this.resultMetadata == null) {
                this.resultMetadata = map;
                return;
            }
            this.resultMetadata.putAll(map);
        }
    }

    public final void addResultPoints(ResultPoint[] resultPointArr) {
        ResultPoint[] resultPointArr2 = this.resultPoints;
        if (resultPointArr2 == null) {
            this.resultPoints = resultPointArr;
            return;
        }
        if (resultPointArr != null && resultPointArr.length > 0) {
            ResultPoint[] resultPointArr3 = new ResultPoint[(resultPointArr2.length + resultPointArr.length)];
            System.arraycopy(resultPointArr2, 0, resultPointArr3, 0, resultPointArr2.length);
            System.arraycopy(resultPointArr, 0, resultPointArr3, resultPointArr2.length, resultPointArr.length);
            this.resultPoints = resultPointArr3;
        }
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final String toString() {
        return this.text;
    }
}
