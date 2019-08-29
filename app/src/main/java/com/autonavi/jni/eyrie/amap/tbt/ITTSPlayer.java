package com.autonavi.jni.eyrie.amap.tbt;

public interface ITTSPlayer {
    public static final int TTSScenePlayAccident = 4;
    public static final int TTSScenePlayCount = 14;
    public static final int TTSScenePlayDeviation = 12;
    public static final int TTSScenePlayJoinLeft = 1;
    public static final int TTSScenePlayJoinRight = 2;
    public static final int TTSScenePlayNull = -1;
    public static final int TTSScenePlayRailway = 6;
    public static final int TTSScenePlayReady = 9;
    public static final int TTSScenePlayRockFall = 5;
    public static final int TTSScenePlaySchool = 8;
    public static final int TTSScenePlayServiceArea = 11;
    public static final int TTSScenePlaySpeedCamera = 10;
    public static final int TTSScenePlayVillage = 7;
    public static final int TTSScenePlayZigzag = 3;
    public static final int TTSScenePlaystop = 99;

    boolean isPlaying();

    void play(String str, int i, int i2);

    void playRing(int i);

    void stop();
}
