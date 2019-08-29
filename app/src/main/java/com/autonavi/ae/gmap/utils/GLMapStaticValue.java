package com.autonavi.ae.gmap.utils;

public final class GLMapStaticValue {
    public static int a = 10;
    public static int b = 20;
    public static int c = 60;
    public static int d = 30;
    public static int e = 60;
    public static int f = 60;
    public static boolean g = false;
    public static int h = 0;
    public static int i = 1;
    public static int j = 2;
    public static int k = 0;
    public static int l = 1;
    public static int m = 2;
    public static int n = 80;
    public static int o = 0;
    public static int p = 1;
    public static int q = 2;
    public static int r;

    public enum MapPreLoadCommandParamType {
        PreLoadParam(0),
        PreLoadSwitchOn(1),
        PreLoadSwitchOff(2);
        
        private final int value;

        private MapPreLoadCommandParamType(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }

    public enum MapPreLoadType {
        PreLoadRoadLine(0),
        PreLoadRegion(1),
        PreLoadAll(3);
        
        private final int value;

        private MapPreLoadType(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }

    public enum PolylineDrawType {
        GREY_OVER_VIEW(0),
        GROWN_ANIMATION(1);
        
        private final int value;

        private PolylineDrawType(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }

    public enum RouteHighLightType {
        NONE(0),
        SEGMENT(1);
        
        private final int value;

        private RouteHighLightType(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }

    public enum WeatherAnimationState {
        UNKNOWN,
        STOPPED,
        RUNNNING,
        FADE_IN,
        FADE_OUT
    }

    public enum WeatherType {
        FROG(0),
        RAIN(1),
        SNOW(2),
        CLOUD(3);
        
        private final int value;

        private WeatherType(int i) {
            this.value = i;
        }

        public final int value() {
            return this.value;
        }
    }
}
