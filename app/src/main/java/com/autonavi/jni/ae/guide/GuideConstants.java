package com.autonavi.jni.ae.guide;

public class GuideConstants {

    public interface CameraType {
        public static final int CAMERA_TYPE_BICYCLELANE = 6;
        public static final int CAMERA_TYPE_BREAKRULE = 3;
        public static final int CAMERA_TYPE_BUSWAY = 4;
        public static final int CAMERA_TYPE_CONSEQUENT = 255;
        public static final int CAMERA_TYPE_EMERGENCYLANE = 5;
        public static final int CAMERA_TYPE_FLOWSPEED = 10;
        public static final int CAMERA_TYPE_INTERVALVELOCITY = 7;
        public static final int CAMERA_TYPE_INTERVALVELOCITYEND = 9;
        public static final int CAMERA_TYPE_INTERVALVELOCITYSTART = 8;
        public static final int CAMERA_TYPE_SPEED = 0;
        public static final int CAMERA_TYPE_SURVEILLANCE = 1;
        public static final int CAMERA_TYPE_TRAFFIC_LIGHT = 2;
    }

    interface CrossImageType {
        public static final int CROSS_IMAGE_TYPE_3D = 4;
        public static final int CROSS_IMAGE_TYPE_GRID = 1;
        public static final int CROSS_IMAGE_TYPE_VECTOR = 3;
    }

    public interface NaviInfoPanelFlag {
        public static final int PANEL_CONTINUE = 1;
        public static final int PANEL_CUR_INFO_FLAG = 0;
    }
}
