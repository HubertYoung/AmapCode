package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.view.Surface;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@TargetApi(16)
public class AudioEncoderCore extends AndroidEncoder {
    protected static final String MIME_TYPE = "audio/mp4a-latm";
    protected int mChannelConfig;
    protected int mSampleRate;

    public AudioEncoderCore(SessionConfig config) {
        int numChannels = config.getNumAudioChannels();
        switch (numChannels) {
            case 1:
                this.mChannelConfig = 16;
                break;
            case 2:
                this.mChannelConfig = 12;
                break;
            default:
                throw new IllegalArgumentException("Invalid channel count. Must be 1 or 2");
        }
        this.mSampleRate = config.getAudioSamplerate();
        setSessionConfig(config);
        this.mBufferInfo = new BufferInfo();
        MediaFormat format = MediaFormat.createAudioFormat(MIME_TYPE, this.mSampleRate, this.mChannelConfig);
        format.setInteger("aac-profile", 2);
        format.setInteger("sample-rate", this.mSampleRate);
        format.setInteger("channel-count", numChannels);
        format.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, config.getAudioBitrate());
        format.setInteger("max-input-size", 16384);
        this.mEncoder = MediaCodec.createEncoderByType(MIME_TYPE);
        this.mEncoder.configure(format, null, null, 1);
        this.mEncoder.start();
        this.mTrackIndex = -1;
    }

    public MediaCodec getMediaCodec() {
        return this.mEncoder;
    }

    /* access modifiers changed from: protected */
    public Surface getInputSurface() {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isSurfaceInputEncoder() {
        return false;
    }
}
