package com.ant.multimedia.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.view.Surface;
import com.alipay.alipaylogger.Log;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@TargetApi(18)
public class VideoEncoderCore extends AndroidEncoder {
    public static final String MIME_TYPE = "video/avc";

    public VideoEncoderCore(int width, int height, int bitRate, SessionConfig config) {
        a(config);
        this.mBufferInfo = new BufferInfo();
        MediaFormat format = MediaFormat.createVideoFormat("video/avc", width, height);
        format.setInteger("color-format", 2130708361);
        format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, bitRate);
        format.setInteger("frame-rate", config.isLiveConfig() ? 20 : 30);
        format.setInteger("i-frame-interval", 1);
        Log.d("VideoEncoderCore", "format: " + format);
        this.mEncoder = MediaCodec.createEncoderByType("video/avc");
        Log.d("VideoEncoderCore", "video encoder name:" + this.mEncoder.getName());
        this.mEncoder.configure(format, null, null, 1);
        this.mInputSurface = this.mEncoder.createInputSurface();
        this.mEncoder.start();
        this.mTrackIndex = -1;
    }

    public Surface getInputSurface() {
        return this.mInputSurface;
    }

    /* access modifiers changed from: protected */
    public final boolean a() {
        return true;
    }

    public boolean isEncoderOK() {
        return this.mEncoder != null;
    }
}
