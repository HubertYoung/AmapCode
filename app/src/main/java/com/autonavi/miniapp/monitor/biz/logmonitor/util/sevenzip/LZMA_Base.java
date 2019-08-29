package com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip;

public class LZMA_Base {
    public static final int kAlignMask = 15;
    public static final int kAlignTableSize = 16;
    public static final int kDicLogSizeMin = 0;
    public static final int kEndPosModelIndex = 14;
    public static final int kMatchMaxLen = 273;
    public static final int kMatchMinLen = 2;
    public static final int kNumAlignBits = 4;
    public static final int kNumFullDistances = 128;
    public static final int kNumHighLenBits = 8;
    public static final int kNumLenSymbols = 272;
    public static final int kNumLenToPosStates = 4;
    public static final int kNumLenToPosStatesBits = 2;
    public static final int kNumLitContextBitsMax = 8;
    public static final int kNumLitPosStatesBitsEncodingMax = 4;
    public static final int kNumLowLenBits = 3;
    public static final int kNumLowLenSymbols = 8;
    public static final int kNumMidLenBits = 3;
    public static final int kNumMidLenSymbols = 8;
    public static final int kNumPosModels = 10;
    public static final int kNumPosSlotBits = 6;
    public static final int kNumPosStatesBitsEncodingMax = 4;
    public static final int kNumPosStatesBitsMax = 4;
    public static final int kNumPosStatesEncodingMax = 16;
    public static final int kNumPosStatesMax = 16;
    public static final int kNumRepDistances = 4;
    public static final int kNumStates = 12;
    public static final int kStartPosModelIndex = 4;

    public static final int GetLenToPosState(int i) {
        int i2 = i - 2;
        if (i2 < 4) {
            return i2;
        }
        return 3;
    }

    public static final int StateInit() {
        return 0;
    }

    public static final boolean StateIsCharState(int i) {
        return i < 7;
    }

    public static final int StateUpdateChar(int i) {
        if (i < 4) {
            return 0;
        }
        return i < 10 ? i - 3 : i - 6;
    }

    public static final int StateUpdateMatch(int i) {
        return i < 7 ? 7 : 10;
    }

    public static final int StateUpdateRep(int i) {
        return i < 7 ? 8 : 11;
    }

    public static final int StateUpdateShortRep(int i) {
        return i < 7 ? 9 : 11;
    }
}
