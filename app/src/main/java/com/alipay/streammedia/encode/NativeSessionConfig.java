package com.alipay.streammedia.encode;

import com.ant.multimedia.encode.SessionConfig;

public class NativeSessionConfig {
    public static final int OMX_MODE_NONE = 0;
    public static final int OMX_MODE_SURFACE = 2;
    public static final int OMX_MODE_YUV = 1;
    public int aEchoCancel;
    public int aEncode;
    public int aSamplerate;
    public int cpu_level = 1;
    public String crf;
    public int fps = 25;
    public int omxMask;
    public int omxMode;
    public int perfLog;
    public String preset;
    public int recordLog;
    public int rotate = 0;
    public int timeout;
    public int useAbr = 1;
    public int vEncode;
    public int vPreviewHeight;
    public int vPreviewWidth;
    public String vPublishUrl;
    public int vRecordHeight;
    public int vRecordWidth;
    public int vencHardware = 0;
    public int videoBitrate = SessionConfig.VIDEO_BITRATE_DEFAULT;

    public String toString() {
        return "NativeSessionConfig{vPreviewWidth=" + this.vPreviewWidth + ", vPreviewHeight=" + this.vPreviewHeight + ", vRecordWidth=" + this.vRecordWidth + ", vRecordHeight=" + this.vRecordHeight + ", videoBitrate=" + this.videoBitrate + ", fps=" + this.fps + ", vEncode=" + this.vEncode + ", audioSamplerate=" + this.aSamplerate + ", audioEchoCancel=" + this.aEchoCancel + ", aEncode=" + this.aEncode + ", recordLog=" + this.recordLog + ", perfLog=" + this.perfLog + ", vPublishUrl='" + this.vPublishUrl + '\'' + ", cpu_level=" + this.cpu_level + ", crf=" + this.crf + ", useAbr=" + this.useAbr + ", preset=" + this.preset + ", rotate=" + this.rotate + ", timeout=" + this.timeout + ", vencType=" + this.vencHardware + '}';
    }
}
