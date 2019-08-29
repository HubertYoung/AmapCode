package com.autonavi.jni.ae.guide.model;

public class AsyncInfo {
    public int[] arg = new int[4];
    public Object obj;
    public int what;

    public interface IAsyncInfoType {
        public static final int AITNaviExitDirUtils = 3;
        public static final int AITNull = 0;
        public static final int AITRenderManeuverIcon = 2;
        public static final int AITVoiceConfigVersion = 1;
    }
}
