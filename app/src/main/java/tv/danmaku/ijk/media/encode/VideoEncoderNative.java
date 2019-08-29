package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.view.Surface;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

@TargetApi(18)
public class VideoEncoderNative extends AndroidEncoder {
    private static final String TAG = "VideoEncoderNative";
    private Surface mInputSurface;

    public VideoEncoderNative(SessionConfig config) {
        Logger.I(TAG, "VideoEncoderNative construct.", new Object[0]);
        this.mConfig = config;
        this.mFFmpegMuxer = config.getmFFmpegMuxer();
    }

    public void setSessionConfig(SessionConfig config) {
    }

    public void release() {
        synchronized (VideoEncoderNative.class) {
            if (this.mFFmpegMuxer != null) {
                this.mFFmpegMuxer.releaseInputSurface(this.mInputSurface);
            }
            if (this.mInputSurface != null) {
                this.mInputSurface.release();
                this.mInputSurface = null;
            }
            Logger.D(TAG, this + " Released omx encoder #########", new Object[0]);
        }
    }

    public Surface getInputSurface() {
        Surface surface;
        synchronized (VideoEncoderNative.class) {
            if (!(this.mFFmpegMuxer == null || this.mConfig == null)) {
                this.mInputSurface = this.mFFmpegMuxer.getInputSurface(this.mConfig.getFFmpegSessionConfig());
            }
            Logger.D(TAG, "getInputSurface " + toString(), new Object[0]);
            if (this.mInputSurface == null) {
                throw new RuntimeException("VideoEncoderNative mInputSurface can not be null");
            }
            surface = this.mInputSurface;
        }
        return surface;
    }

    public void drainEncoder(boolean endOfStream, boolean isVideoDataType) {
        if (this.mFFmpegMuxer != null) {
            this.mFFmpegMuxer.drainEncoder();
            if (endOfStream) {
                this.mFFmpegMuxer.uninit();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isSurfaceInputEncoder() {
        return true;
    }
}
