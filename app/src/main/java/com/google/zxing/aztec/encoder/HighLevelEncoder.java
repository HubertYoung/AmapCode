package com.google.zxing.aztec.encoder;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public final class HighLevelEncoder {
    private static final int[][] CHAR_MAP;
    static final int[][] LATCH_TABLE;
    static final int MODE_DIGIT = 2;
    static final int MODE_LOWER = 1;
    static final int MODE_MIXED = 3;
    static final String[] MODE_NAMES = {"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};
    static final int MODE_PUNCT = 4;
    static final int MODE_UPPER = 0;
    static final int[][] SHIFT_TABLE;
    private final byte[] text;

    static {
        int[] iArr = new int[5];
        iArr[1] = 327708;
        iArr[2] = 327710;
        iArr[3] = 327709;
        iArr[4] = 656318;
        int[] iArr2 = new int[5];
        iArr2[0] = 590318;
        iArr2[2] = 327710;
        iArr2[3] = 327709;
        iArr2[4] = 656318;
        int[] iArr3 = new int[5];
        iArr3[0] = 262158;
        iArr3[1] = 590300;
        iArr3[3] = 590301;
        iArr3[4] = 932798;
        int[] iArr4 = new int[5];
        iArr4[0] = 327709;
        iArr4[1] = 327708;
        iArr4[2] = 656318;
        iArr4[4] = 327710;
        int[] iArr5 = new int[5];
        iArr5[0] = 327711;
        iArr5[1] = 656380;
        iArr5[2] = 656382;
        iArr5[3] = 656381;
        LATCH_TABLE = new int[][]{iArr, iArr2, iArr3, iArr4, iArr5};
        int[][] iArr6 = (int[][]) Array.newInstance(int.class, new int[]{5, 256});
        CHAR_MAP = iArr6;
        iArr6[0][32] = 1;
        for (int i = 65; i <= 90; i++) {
            CHAR_MAP[0][i] = (i - 65) + 2;
        }
        CHAR_MAP[1][32] = 1;
        for (int i2 = 97; i2 <= 122; i2++) {
            CHAR_MAP[1][i2] = (i2 - 97) + 2;
        }
        CHAR_MAP[2][32] = 1;
        for (int i3 = 48; i3 <= 57; i3++) {
            CHAR_MAP[2][i3] = (i3 - 48) + 2;
        }
        CHAR_MAP[2][44] = 12;
        CHAR_MAP[2][46] = 13;
        int[] iArr7 = new int[28];
        iArr7[1] = 32;
        iArr7[2] = 1;
        iArr7[3] = 2;
        iArr7[4] = 3;
        iArr7[5] = 4;
        iArr7[6] = 5;
        iArr7[7] = 6;
        iArr7[8] = 7;
        iArr7[9] = 8;
        iArr7[10] = 9;
        iArr7[11] = 10;
        iArr7[12] = 11;
        iArr7[13] = 12;
        iArr7[14] = 13;
        iArr7[15] = 27;
        iArr7[16] = 28;
        iArr7[17] = 29;
        iArr7[18] = 30;
        iArr7[19] = 31;
        iArr7[20] = 64;
        iArr7[21] = 92;
        iArr7[22] = 94;
        iArr7[23] = 95;
        iArr7[24] = 96;
        iArr7[25] = 124;
        iArr7[26] = 126;
        iArr7[27] = 127;
        for (int i4 = 0; i4 < 28; i4++) {
            CHAR_MAP[3][iArr7[i4]] = i4;
        }
        int[] iArr8 = new int[31];
        iArr8[1] = 13;
        iArr8[6] = 33;
        iArr8[7] = 39;
        iArr8[8] = 35;
        iArr8[9] = 36;
        iArr8[10] = 37;
        iArr8[11] = 38;
        iArr8[12] = 39;
        iArr8[13] = 40;
        iArr8[14] = 41;
        iArr8[15] = 42;
        iArr8[16] = 43;
        iArr8[17] = 44;
        iArr8[18] = 45;
        iArr8[19] = 46;
        iArr8[20] = 47;
        iArr8[21] = 58;
        iArr8[22] = 59;
        iArr8[23] = 60;
        iArr8[24] = 61;
        iArr8[25] = 62;
        iArr8[26] = 63;
        iArr8[27] = 91;
        iArr8[28] = 93;
        iArr8[29] = 123;
        iArr8[30] = 125;
        for (int i5 = 0; i5 < 31; i5++) {
            if (iArr8[i5] > 0) {
                CHAR_MAP[4][iArr8[i5]] = i5;
            }
        }
        int[][] iArr9 = (int[][]) Array.newInstance(int.class, new int[]{6, 6});
        SHIFT_TABLE = iArr9;
        for (int[] fill : iArr9) {
            Arrays.fill(fill, -1);
        }
        SHIFT_TABLE[0][4] = 0;
        SHIFT_TABLE[1][4] = 0;
        SHIFT_TABLE[1][0] = 28;
        SHIFT_TABLE[3][4] = 0;
        SHIFT_TABLE[2][4] = 0;
        SHIFT_TABLE[2][0] = 15;
    }

    public HighLevelEncoder(byte[] bArr) {
        this.text = bArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.zxing.common.BitArray encode() {
        /*
            r8 = this;
            com.google.zxing.aztec.encoder.State r0 = com.google.zxing.aztec.encoder.State.INITIAL_STATE
            java.util.List r0 = java.util.Collections.singletonList(r0)
            r1 = 0
            r2 = r0
            r0 = 0
        L_0x0009:
            byte[] r3 = r8.text
            int r3 = r3.length
            if (r0 < r3) goto L_0x0020
            com.google.zxing.aztec.encoder.HighLevelEncoder$1 r0 = new com.google.zxing.aztec.encoder.HighLevelEncoder$1
            r0.<init>()
            java.lang.Object r0 = java.util.Collections.min(r2, r0)
            com.google.zxing.aztec.encoder.State r0 = (com.google.zxing.aztec.encoder.State) r0
            byte[] r1 = r8.text
            com.google.zxing.common.BitArray r0 = r0.toBitArray(r1)
            return r0
        L_0x0020:
            int r3 = r0 + 1
            byte[] r4 = r8.text
            int r4 = r4.length
            if (r3 >= r4) goto L_0x002c
            byte[] r4 = r8.text
            byte r4 = r4[r3]
            goto L_0x002d
        L_0x002c:
            r4 = 0
        L_0x002d:
            byte[] r5 = r8.text
            byte r5 = r5[r0]
            r6 = 13
            if (r5 == r6) goto L_0x0051
            r6 = 44
            r7 = 32
            if (r5 == r6) goto L_0x004d
            r6 = 46
            if (r5 == r6) goto L_0x0049
            r6 = 58
            if (r5 == r6) goto L_0x0045
        L_0x0043:
            r4 = 0
            goto L_0x0056
        L_0x0045:
            if (r4 != r7) goto L_0x0043
            r4 = 5
            goto L_0x0056
        L_0x0049:
            if (r4 != r7) goto L_0x0043
            r4 = 3
            goto L_0x0056
        L_0x004d:
            if (r4 != r7) goto L_0x0043
            r4 = 4
            goto L_0x0056
        L_0x0051:
            r5 = 10
            if (r4 != r5) goto L_0x0043
            r4 = 2
        L_0x0056:
            if (r4 <= 0) goto L_0x005f
            java.util.Collection r0 = updateStateListForPair(r2, r0, r4)
            r2 = r0
            r0 = r3
            goto L_0x0063
        L_0x005f:
            java.util.Collection r2 = r8.updateStateListForChar(r2, r0)
        L_0x0063:
            int r0 = r0 + 1
            goto L_0x0009
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.aztec.encoder.HighLevelEncoder.encode():com.google.zxing.common.BitArray");
    }

    private Collection<State> updateStateListForChar(Iterable<State> iterable, int i) {
        LinkedList linkedList = new LinkedList();
        for (State updateStateForChar : iterable) {
            updateStateForChar(updateStateForChar, i, linkedList);
        }
        return simplifyStates(linkedList);
    }

    private void updateStateForChar(State state, int i, Collection<State> collection) {
        char c = (char) (this.text[i] & 255);
        boolean z = CHAR_MAP[state.getMode()][c] > 0;
        State state2 = null;
        for (int i2 = 0; i2 <= 4; i2++) {
            int i3 = CHAR_MAP[i2][c];
            if (i3 > 0) {
                if (state2 == null) {
                    state2 = state.endBinaryShift(i);
                }
                if (!z || i2 == state.getMode() || i2 == 2) {
                    collection.add(state2.latchAndAppend(i2, i3));
                }
                if (!z && SHIFT_TABLE[state.getMode()][i2] >= 0) {
                    collection.add(state2.shiftAndAppend(i2, i3));
                }
            }
        }
        if (state.getBinaryShiftByteCount() > 0 || CHAR_MAP[state.getMode()][c] == 0) {
            collection.add(state.addBinaryShiftChar(i));
        }
    }

    private static Collection<State> updateStateListForPair(Iterable<State> iterable, int i, int i2) {
        LinkedList linkedList = new LinkedList();
        for (State updateStateForPair : iterable) {
            updateStateForPair(updateStateForPair, i, i2, linkedList);
        }
        return simplifyStates(linkedList);
    }

    private static void updateStateForPair(State state, int i, int i2, Collection<State> collection) {
        State endBinaryShift = state.endBinaryShift(i);
        collection.add(endBinaryShift.latchAndAppend(4, i2));
        if (state.getMode() != 4) {
            collection.add(endBinaryShift.shiftAndAppend(4, i2));
        }
        if (i2 == 3 || i2 == 4) {
            collection.add(endBinaryShift.latchAndAppend(2, 16 - i2).latchAndAppend(2, 1));
        }
        if (state.getBinaryShiftByteCount() > 0) {
            collection.add(state.addBinaryShiftChar(i).addBinaryShiftChar(i + 1));
        }
    }

    private static Collection<State> simplifyStates(Iterable<State> iterable) {
        LinkedList linkedList = new LinkedList();
        for (State next : iterable) {
            boolean z = true;
            Iterator it = linkedList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                State state = (State) it.next();
                if (state.isBetterThanOrEqualTo(next)) {
                    z = false;
                    break;
                } else if (next.isBetterThanOrEqualTo(state)) {
                    it.remove();
                }
            }
            if (z) {
                linkedList.add(next);
            }
        }
        return linkedList;
    }
}
