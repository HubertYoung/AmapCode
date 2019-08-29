package com.ant.multimedia.encode;

import com.alipay.streammedia.encode.RecorderInternalCounter;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class LiveCounter {
    public static final int TYPE_PLAY = 1;
    public static final int TYPE_RECORD = 0;
    public long audioCachedBytes;
    public long audioCachedDuration;
    public int mType = 0;
    public long streamSendSize;
    public long videoCachedBytes;
    public long videoCachedDuration;
    public double videoConvertCostTime;
    public double videoEncodingCostTime;
    public int videoFrameCount;
    public double videoFrameProcessTime;
    public double videoFrameWriteTime;
    public int videoIFrameCount;
    public double videoMirrorCostTime;
    public int videoPFrameCount;
    public double videoRotateCostTime;

    public void setRecordValues(RecorderInternalCounter counter) {
        this.streamSendSize = counter.streamSendSize;
        this.videoConvertCostTime = counter.videoConvertCostTime;
        this.videoFrameCount = counter.videoFrameCount;
        this.videoEncodingCostTime = counter.videoEncodingCostTime;
        this.videoRotateCostTime = counter.videoRotateCostTime;
        this.videoMirrorCostTime = counter.videoMirrorCostTime;
        this.videoFrameProcessTime = counter.videoFrameProcessTime;
        this.videoFrameWriteTime = counter.videoFrameWriteTime;
        this.videoIFrameCount = counter.videoIFrameCount;
        this.videoPFrameCount = counter.videoPFrameCount;
    }

    public void setPlayValues(IjkMediaPlayer mp) {
        if (mp != null) {
            this.videoCachedDuration = mp.getVideoCachedDuration();
            this.audioCachedDuration = mp.getAudioCachedDuration();
            this.videoCachedBytes = mp.getVideoCachedBytes();
            this.audioCachedBytes = mp.getAudioCachedBytes();
        }
    }

    public boolean isRecord() {
        return this.mType == 0;
    }
}
