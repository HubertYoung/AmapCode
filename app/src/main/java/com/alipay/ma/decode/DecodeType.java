package com.alipay.ma.decode;

public interface DecodeType {
    public static final int ALLCODE = 36607;
    public static final int APCODE = 65536;
    public static final int ARCODE = 0;
    public static final int BAR = 0;
    public static final int CODE128 = 32;
    public static final int CODE39 = 16;
    public static final int DMCODE = 1024;
    public static final int EAN13 = 1;
    public static final int EAN14 = 128;
    public static final int EAN8 = 2;
    public static final int Express = 2;
    public static final int FASTMAIL = 48;
    public static final int GEN3GCODE = 32768;
    public static final int ITF = 64;
    public static final int MA4GCODE = 2048;
    public static final int ONE = 127;
    public static final int ONECODE = 255;
    public static final int QR = 1;
    public static final int QRCODE = 512;
    public static final int UPCA = 4;
    public static final int UPCE = 8;
    public static final String[] strTypes = {"EAN13", "EAN8", "UPCA", "UPCE", "CODE39", "CODE128", "EAN14"};
}
