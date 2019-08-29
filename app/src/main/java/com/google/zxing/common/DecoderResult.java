package com.google.zxing.common;

import java.util.List;

public final class DecoderResult {
    private final List<byte[]> byteSegments;
    private final String ecLevel;
    private Integer erasures;
    private Integer errorsCorrected;
    private int numBits;
    private Object other;
    private final byte[] rawBytes;
    private final int structuredAppendParity;
    private final int structuredAppendSequenceNumber;
    private final String text;

    public DecoderResult(byte[] bArr, String str, List<byte[]> list, String str2) {
        this(bArr, str, list, str2, -1, -1);
    }

    public DecoderResult(byte[] bArr, String str, List<byte[]> list, String str2, int i, int i2) {
        int i3;
        this.rawBytes = bArr;
        if (bArr == null) {
            i3 = 0;
        } else {
            i3 = bArr.length * 8;
        }
        this.numBits = i3;
        this.text = str;
        this.byteSegments = list;
        this.ecLevel = str2;
        this.structuredAppendParity = i2;
        this.structuredAppendSequenceNumber = i;
    }

    public final byte[] getRawBytes() {
        return this.rawBytes;
    }

    public final int getNumBits() {
        return this.numBits;
    }

    public final void setNumBits(int i) {
        this.numBits = i;
    }

    public final String getText() {
        return this.text;
    }

    public final List<byte[]> getByteSegments() {
        return this.byteSegments;
    }

    public final String getECLevel() {
        return this.ecLevel;
    }

    public final Integer getErrorsCorrected() {
        return this.errorsCorrected;
    }

    public final void setErrorsCorrected(Integer num) {
        this.errorsCorrected = num;
    }

    public final Integer getErasures() {
        return this.erasures;
    }

    public final void setErasures(Integer num) {
        this.erasures = num;
    }

    public final Object getOther() {
        return this.other;
    }

    public final void setOther(Object obj) {
        this.other = obj;
    }

    public final boolean hasStructuredAppend() {
        return this.structuredAppendParity >= 0 && this.structuredAppendSequenceNumber >= 0;
    }

    public final int getStructuredAppendParity() {
        return this.structuredAppendParity;
    }

    public final int getStructuredAppendSequenceNumber() {
        return this.structuredAppendSequenceNumber;
    }
}
