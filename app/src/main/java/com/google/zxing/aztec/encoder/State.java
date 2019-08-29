package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import java.util.LinkedList;

final class State {
    static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    private final int binaryShiftByteCount;
    private final int bitCount;
    private final int mode;
    private final Token token;

    private State(Token token2, int i, int i2, int i3) {
        this.token = token2;
        this.mode = i;
        this.binaryShiftByteCount = i2;
        this.bitCount = i3;
    }

    /* access modifiers changed from: 0000 */
    public final int getMode() {
        return this.mode;
    }

    /* access modifiers changed from: 0000 */
    public final Token getToken() {
        return this.token;
    }

    /* access modifiers changed from: 0000 */
    public final int getBinaryShiftByteCount() {
        return this.binaryShiftByteCount;
    }

    /* access modifiers changed from: 0000 */
    public final int getBitCount() {
        return this.bitCount;
    }

    /* access modifiers changed from: 0000 */
    public final State latchAndAppend(int i, int i2) {
        int i3 = this.bitCount;
        Token token2 = this.token;
        if (i != this.mode) {
            int i4 = HighLevelEncoder.LATCH_TABLE[this.mode][i];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            token2 = token2.add(i5, i6);
            i3 += i6;
        }
        int i7 = i == 2 ? 4 : 5;
        return new State(token2.add(i2, i7), i, 0, i3 + i7);
    }

    /* access modifiers changed from: 0000 */
    public final State shiftAndAppend(int i, int i2) {
        Token token2 = this.token;
        int i3 = this.mode == 2 ? 4 : 5;
        return new State(token2.add(HighLevelEncoder.SHIFT_TABLE[this.mode][i], i3).add(i2, 5), this.mode, 0, this.bitCount + i3 + 5);
    }

    /* access modifiers changed from: 0000 */
    public final State addBinaryShiftChar(int i) {
        Token token2 = this.token;
        int i2 = this.mode;
        int i3 = this.bitCount;
        if (this.mode == 4 || this.mode == 2) {
            int i4 = HighLevelEncoder.LATCH_TABLE[i2][0];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            token2 = token2.add(i5, i6);
            i3 += i6;
            i2 = 0;
        }
        int i7 = (this.binaryShiftByteCount == 0 || this.binaryShiftByteCount == 31) ? 18 : this.binaryShiftByteCount == 62 ? 9 : 8;
        State state = new State(token2, i2, this.binaryShiftByteCount + 1, i3 + i7);
        return state.binaryShiftByteCount == 2078 ? state.endBinaryShift(i + 1) : state;
    }

    /* access modifiers changed from: 0000 */
    public final State endBinaryShift(int i) {
        if (this.binaryShiftByteCount == 0) {
            return this;
        }
        return new State(this.token.addBinaryShift(i - this.binaryShiftByteCount, this.binaryShiftByteCount), this.mode, 0, this.bitCount);
    }

    /* access modifiers changed from: 0000 */
    public final boolean isBetterThanOrEqualTo(State state) {
        int i = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
        if (state.binaryShiftByteCount > 0 && (this.binaryShiftByteCount == 0 || this.binaryShiftByteCount > state.binaryShiftByteCount)) {
            i += 10;
        }
        return i <= state.bitCount;
    }

    /* access modifiers changed from: 0000 */
    public final BitArray toBitArray(byte[] bArr) {
        LinkedList<Token> linkedList = new LinkedList<>();
        for (Token token2 = endBinaryShift(bArr.length).token; token2 != null; token2 = token2.getPrevious()) {
            linkedList.addFirst(token2);
        }
        BitArray bitArray = new BitArray();
        for (Token appendTo : linkedList) {
            appendTo.appendTo(bitArray, bArr);
        }
        return bitArray;
    }

    public final String toString() {
        return String.format("%s bits=%d bytes=%d", new Object[]{HighLevelEncoder.MODE_NAMES[this.mode], Integer.valueOf(this.bitCount), Integer.valueOf(this.binaryShiftByteCount)});
    }
}
