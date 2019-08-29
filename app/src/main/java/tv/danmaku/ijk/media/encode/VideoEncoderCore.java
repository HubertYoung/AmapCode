package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.view.Surface;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@TargetApi(18)
public class VideoEncoderCore extends AndroidEncoder {
    private static final int FRAME_LIVE_RATE = 20;
    private static final int FRAME_RATE = 30;
    private static final int IFRAME_INTERVAL = 1;
    public static final String MIME_TYPE = "video/avc";
    private static final String TAG = "VideoEncoderCore";
    private static final boolean VERBOSE = true;

    public VideoEncoderCore(int width, int height, int bitRate, SessionConfig config) {
        setSessionConfig(config);
        this.mBufferInfo = new BufferInfo();
        MediaFormat format = MediaFormat.createVideoFormat("video/avc", width, height);
        format.setInteger("color-format", 2130708361);
        format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, bitRate);
        format.setInteger("frame-rate", config.isLiveConfig() ? 20 : 30);
        format.setInteger("i-frame-interval", 1);
        Logger.D(TAG, "format: " + format, new Object[0]);
        this.mEncoder = MediaCodec.createEncoderByType("video/avc");
        Logger.D(TAG, "video encoder name:" + this.mEncoder.getName(), new Object[0]);
        this.mEncoder.configure(format, null, null, 1);
        this.mInputSurface = this.mEncoder.createInputSurface();
        this.mEncoder.start();
        this.mTrackIndex = -1;
    }

    public Surface getInputSurface() {
        return this.mInputSurface;
    }

    /* access modifiers changed from: protected */
    public boolean isSurfaceInputEncoder() {
        return true;
    }

    public boolean isEncoderOK() {
        return this.mEncoder != null;
    }
}
