package com.alipay.ma.decode;

import java.io.Serializable;
import java.util.Arrays;

public class DecodeResult implements Serializable {
    public int bitErrors;
    public byte[] bytes;
    public byte[] decodeBytes;
    public char ecLevel;
    public int height;
    public String hiddenData;
    public String strCode;
    public int strategy;
    public int subType;
    public int type;
    public int version;
    public int width;
    public int x;
    public int[] xCorner;
    public int y;
    public int[] yCorner;

    public DecodeResult(int type2, int subType2, String strCode2) {
        this.type = type2;
        this.subType = subType2;
        this.strCode = strCode2;
    }

    public DecodeResult(int type2, int subType2, byte[] bytes2, int x2, int y2, int width2, int height2) {
        this.type = type2;
        this.subType = subType2;
        this.bytes = bytes2;
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
    }

    public DecodeResult(int type2, int subType2, byte[] bytes2, int x2, int y2, int width2, int height2, byte[] decodeBytes2) {
        this.type = type2;
        this.subType = subType2;
        this.bytes = bytes2;
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
        this.decodeBytes = decodeBytes2;
    }

    public DecodeResult(int type2, int subType2, byte[] bytes2, int x2, int y2, int width2, int height2, byte[] decodeBytes2, String hiddenData2) {
        this.type = type2;
        this.subType = subType2;
        this.bytes = bytes2;
        this.x = x2;
        this.y = y2;
        this.width = width2;
        this.height = height2;
        this.decodeBytes = decodeBytes2;
        this.hiddenData = hiddenData2;
        this.xCorner = new int[4];
        this.yCorner = new int[4];
    }

    public DecodeResult(int type2, int subType2, byte[] bytes2) {
        this.type = type2;
        this.subType = subType2;
        this.bytes = bytes2;
    }

    public DecodeResult(int type2, int subType2, byte[] bytes2, byte[] decodeBytes2) {
        this.type = type2;
        this.subType = subType2;
        this.bytes = bytes2;
        this.decodeBytes = decodeBytes2;
    }

    public String toString() {
        return "DecodeResult [type=" + this.type + ", subType=" + this.subType + ", strCode=" + this.strCode + ", bytes=" + Arrays.toString(this.bytes) + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", decodeBytes=" + Arrays.toString(this.decodeBytes) + ", hiddenData=" + this.hiddenData + "]";
    }
}
