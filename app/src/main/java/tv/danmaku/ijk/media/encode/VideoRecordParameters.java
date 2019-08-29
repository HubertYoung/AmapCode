package tv.danmaku.ijk.media.encode;

public class VideoRecordParameters {
    public static final int FHD_HEIGHT_16_9 = 720;
    public static final int FHD_WIDTH_16_9 = 1280;
    public static final int HD_HEIGHT_16_9 = 540;
    public static final int HD_WIDTH_16_9 = 960;
    public static final int SD_HEIGHT_16_9 = 360;
    public static final int SD_WIDTH_16_9 = 640;
    public RESOLUTION_LEVEL resolutionLevel;

    public enum RESOLUTION_LEVEL {
        SD,
        HD,
        FHD
    }
}
