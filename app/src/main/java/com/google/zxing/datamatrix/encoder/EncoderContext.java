package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import java.nio.charset.Charset;

final class EncoderContext {
    private final StringBuilder codewords;
    private Dimension maxSize;
    private Dimension minSize;
    private final String msg;
    private int newEncoding;
    int pos;
    private SymbolShapeHint shape;
    private int skipAtEnd;
    private SymbolInfo symbolInfo;

    EncoderContext(String str) {
        byte[] bytes = str.getBytes(Charset.forName("ISO-8859-1"));
        StringBuilder sb = new StringBuilder(bytes.length);
        int length = bytes.length;
        int i = 0;
        while (i < length) {
            char c = (char) (bytes[i] & 255);
            if (c != '?' || str.charAt(i) == '?') {
                sb.append(c);
                i++;
            } else {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
        }
        this.msg = sb.toString();
        this.shape = SymbolShapeHint.FORCE_NONE;
        this.codewords = new StringBuilder(str.length());
        this.newEncoding = -1;
    }

    public final void setSymbolShape(SymbolShapeHint symbolShapeHint) {
        this.shape = symbolShapeHint;
    }

    public final void setSizeConstraints(Dimension dimension, Dimension dimension2) {
        this.minSize = dimension;
        this.maxSize = dimension2;
    }

    public final String getMessage() {
        return this.msg;
    }

    public final void setSkipAtEnd(int i) {
        this.skipAtEnd = i;
    }

    public final char getCurrentChar() {
        return this.msg.charAt(this.pos);
    }

    public final char getCurrent() {
        return this.msg.charAt(this.pos);
    }

    public final StringBuilder getCodewords() {
        return this.codewords;
    }

    public final void writeCodewords(String str) {
        this.codewords.append(str);
    }

    public final void writeCodeword(char c) {
        this.codewords.append(c);
    }

    public final int getCodewordCount() {
        return this.codewords.length();
    }

    public final int getNewEncoding() {
        return this.newEncoding;
    }

    public final void signalEncoderChange(int i) {
        this.newEncoding = i;
    }

    public final void resetEncoderSignal() {
        this.newEncoding = -1;
    }

    public final boolean hasMoreCharacters() {
        return this.pos < getTotalMessageCharCount();
    }

    private int getTotalMessageCharCount() {
        return this.msg.length() - this.skipAtEnd;
    }

    public final int getRemainingCharacters() {
        return getTotalMessageCharCount() - this.pos;
    }

    public final SymbolInfo getSymbolInfo() {
        return this.symbolInfo;
    }

    public final void updateSymbolInfo() {
        updateSymbolInfo(getCodewordCount());
    }

    public final void updateSymbolInfo(int i) {
        if (this.symbolInfo == null || i > this.symbolInfo.getDataCapacity()) {
            this.symbolInfo = SymbolInfo.lookup(i, this.shape, this.minSize, this.maxSize, true);
        }
    }

    public final void resetSymbolInfo() {
        this.symbolInfo = null;
    }
}
