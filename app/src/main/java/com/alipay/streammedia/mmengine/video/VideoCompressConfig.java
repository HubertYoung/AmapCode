package com.alipay.streammedia.mmengine.video;

public class VideoCompressConfig {
    public int bitrate;
    public int enableAudioCopy = 1;
    public int enableMediaCodec;
    public int height;
    public String inputPath;
    public String outputPath;
    public int useFixTimebase = 1;
    public long videoId;
    public int width;

    public static VideoCompressConfig create720P() {
        VideoCompressConfig config = new VideoCompressConfig();
        config.width = 360;
        config.height = 640;
        config.bitrate = 791000;
        return config;
    }
}
