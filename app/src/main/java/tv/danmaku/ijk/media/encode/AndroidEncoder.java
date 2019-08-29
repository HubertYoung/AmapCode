package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.view.Surface;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.nio.ByteBuffer;

@TargetApi(16)
public abstract class AndroidEncoder {
    private static final String TAG = "AndroidEncoder";
    private static final boolean VERBOSE = true;
    final int MAX_EOS_SPINS = 10;
    protected BufferInfo mBufferInfo;
    protected SessionConfig mConfig;
    protected MediaCodec mEncoder;
    int mEosSpinCount = 0;
    protected FFmpegMuxer mFFmpegMuxer;
    protected volatile boolean mForceEos = false;
    protected Surface mInputSurface;
    protected AndroidMuxer mMuxer;
    protected int mTrackIndex;
    private int testCount = 0;

    /* access modifiers changed from: protected */
    public abstract Surface getInputSurface();

    /* access modifiers changed from: protected */
    public abstract boolean isSurfaceInputEncoder();

    /* access modifiers changed from: protected */
    public void setSessionConfig(SessionConfig config) {
        this.mConfig = config;
        if (this.mConfig.isLiveConfig() || this.mConfig.isOMXVideo()) {
            this.mFFmpegMuxer = this.mConfig.getmFFmpegMuxer();
        } else {
            this.mMuxer = this.mConfig.getMuxer();
        }
    }

    public void signalEndOfStream() {
        this.mForceEos = true;
    }

    public void release() {
        if (this.mEncoder != null) {
            try {
                this.mEncoder.stop();
            } catch (Throwable t) {
                Logger.W(TAG, "release call encoder stop error, " + t, new Object[0]);
            }
            this.mEncoder.release();
            this.mEncoder = null;
            Logger.D(TAG, this + " Released encoder#########", new Object[0]);
        }
        if (this.mInputSurface != null) {
            this.mInputSurface.release();
            this.mInputSurface = null;
        }
    }

    public void drainEncoder(boolean endOfStream, boolean isVideoDataType) {
        try {
            if (this.mConfig.isLiveConfig()) {
                drainLiveEncoder(endOfStream, isVideoDataType);
            } else {
                drainVideoEncoder(endOfStream);
            }
        } catch (Exception e) {
            Logger.E((String) TAG, (Throwable) e, (String) "drainEncoder has exception", new Object[0]);
        }
    }

    private void drainLiveEncoder(boolean endOfStream, boolean isVideoDataType) {
        synchronized (this.mFFmpegMuxer) {
            boolean bLog = isNeedLog();
            if (bLog) {
                Logger.D(TAG, "drainEncoder(" + endOfStream + ") track: " + this.mTrackIndex, new Object[0]);
            }
            if (endOfStream) {
                Logger.D(TAG, "sending EOS to encoder for track " + this.mTrackIndex, new Object[0]);
            }
            ByteBuffer[] encoderOutputBuffers = this.mEncoder.getOutputBuffers();
            while (true) {
                int encoderStatus = this.mEncoder.dequeueOutputBuffer(this.mBufferInfo, 1000);
                if (encoderStatus == -1) {
                    if (!endOfStream) {
                        break;
                    }
                    this.mEosSpinCount++;
                    if (this.mEosSpinCount > 10) {
                        Logger.D(TAG, "Force shutting down Muxer", new Object[0]);
                        this.mFFmpegMuxer.uninit();
                        break;
                    }
                    Logger.D(TAG, "no output available, spinning to await EOS", new Object[0]);
                } else if (encoderStatus == -3) {
                    encoderOutputBuffers = this.mEncoder.getOutputBuffers();
                    Logger.D(TAG, "encoder output buffer changed.", new Object[0]);
                } else if (encoderStatus == -2) {
                    MediaFormat newFormat = this.mEncoder.getOutputFormat();
                    Logger.D(TAG, "encoder output format changed: " + newFormat, new Object[0]);
                    this.mTrackIndex = this.mFFmpegMuxer.addTrack(newFormat);
                } else if (encoderStatus < 0) {
                    Logger.D(TAG, "unexpected result from encoder.dequeueOutputBuffer: " + encoderStatus, new Object[0]);
                } else {
                    ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
                    if (encodedData == null) {
                        throw new RuntimeException("encoderOutputBuffer " + encoderStatus + " was null");
                    }
                    if (this.mBufferInfo.size >= 0) {
                        encodedData.position(this.mBufferInfo.offset);
                        encodedData.limit(this.mBufferInfo.offset + this.mBufferInfo.size);
                        if (this.mForceEos) {
                            this.mBufferInfo.flags |= 4;
                            Logger.D(TAG, "Forcing EOS", new Object[0]);
                        }
                        if (isVideoDataType) {
                            this.mTrackIndex = 0;
                        } else {
                            this.mTrackIndex = 1;
                        }
                        if (bLog) {
                            Logger.D(TAG, "sent " + this.mBufferInfo.size + " bytes to muxer, \t ts=" + this.mBufferInfo.presentationTimeUs + "\ttrack " + this.mTrackIndex, new Object[0]);
                        }
                        this.mFFmpegMuxer.writeSampleData(this.mEncoder, this.mTrackIndex, encoderStatus, encodedData, this.mBufferInfo);
                    }
                    if ((this.mBufferInfo.flags & 4) != 0) {
                        if (!endOfStream) {
                            Logger.D(TAG, "reached end of stream unexpectedly", new Object[0]);
                        } else {
                            Logger.D(TAG, "end of stream reached for track " + this.mTrackIndex, new Object[0]);
                        }
                    }
                }
            }
            if (endOfStream) {
                if (isSurfaceInputEncoder()) {
                    Logger.D(TAG, "final video drain complete", new Object[0]);
                } else {
                    Logger.D(TAG, "final audio drain complete", new Object[0]);
                }
            }
        }
    }

    private void drainVideoEncoder(boolean endOfStream) {
        synchronized (this.mMuxer) {
            Logger.D(TAG, "drainEncoder(" + endOfStream + ") track: " + this.mTrackIndex, new Object[0]);
            if (endOfStream) {
                Logger.D(TAG, "sending EOS to encoder for track " + this.mTrackIndex, new Object[0]);
            }
            ByteBuffer[] encoderOutputBuffers = this.mEncoder.getOutputBuffers();
            while (true) {
                int encoderStatus = this.mEncoder.dequeueOutputBuffer(this.mBufferInfo, 1000);
                if (encoderStatus == -1) {
                    if (!endOfStream) {
                        Logger.D(TAG, "no output available yet", new Object[0]);
                        break;
                    }
                    this.mEosSpinCount++;
                    if (this.mEosSpinCount > 10) {
                        Logger.D(TAG, "Force shutting down Muxer", new Object[0]);
                        this.mMuxer.forceStop();
                        break;
                    }
                    Logger.D(TAG, "no output available, spinning to await EOS", new Object[0]);
                } else if (encoderStatus == -3) {
                    encoderOutputBuffers = this.mEncoder.getOutputBuffers();
                    Logger.D(TAG, "encoder output buffer changed.", new Object[0]);
                } else if (encoderStatus == -2) {
                    MediaFormat newFormat = this.mEncoder.getOutputFormat();
                    Logger.D(TAG, "encoder output format changed: " + newFormat, new Object[0]);
                    this.mTrackIndex = this.mMuxer.addTrack(newFormat);
                } else if (encoderStatus < 0) {
                    Logger.D(TAG, "unexpected result from encoder.dequeueOutputBuffer: " + encoderStatus, new Object[0]);
                } else {
                    ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
                    if (encodedData == null) {
                        throw new RuntimeException("encoderOutputBuffer " + encoderStatus + " was null");
                    }
                    if (this.mBufferInfo.size >= 0) {
                        encodedData.position(this.mBufferInfo.offset);
                        encodedData.limit(this.mBufferInfo.offset + this.mBufferInfo.size);
                        if (this.mForceEos) {
                            this.mBufferInfo.flags |= 4;
                            Logger.D(TAG, "Forcing EOS", new Object[0]);
                        }
                        Logger.D(TAG, "sent " + this.mBufferInfo.size + " bytes to muxer, \t ts=" + this.mBufferInfo.presentationTimeUs + "\ttrack " + this.mTrackIndex, new Object[0]);
                        this.mMuxer.writeSampleData(this.mEncoder, this.mTrackIndex, encoderStatus, encodedData, this.mBufferInfo);
                    }
                    if ((this.mBufferInfo.flags & 4) != 0) {
                        if (!endOfStream) {
                            Logger.D(TAG, "reached end of stream unexpectedly", new Object[0]);
                        } else {
                            Logger.D(TAG, "end of stream reached for track " + this.mTrackIndex, new Object[0]);
                        }
                    }
                }
            }
            if (endOfStream) {
                if (isSurfaceInputEncoder()) {
                    Logger.D(TAG, "final video drain complete", new Object[0]);
                } else {
                    Logger.D(TAG, "final audio drain complete", new Object[0]);
                }
            }
        }
    }

    private boolean isNeedLog() {
        if (this.testCount % 30 != 0) {
            this.testCount++;
            return false;
        }
        this.testCount = 0;
        this.testCount++;
        return true;
    }
}
