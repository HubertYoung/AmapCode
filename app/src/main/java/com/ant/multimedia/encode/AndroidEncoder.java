package com.ant.multimedia.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.view.Surface;
import com.alipay.alipaylogger.Log;
import java.nio.ByteBuffer;

@TargetApi(16)
public abstract class AndroidEncoder {
    int a = 0;
    final int b = 10;
    private int c = 0;
    protected BufferInfo mBufferInfo;
    protected SessionConfig mConfig;
    protected MediaCodec mEncoder;
    protected volatile boolean mForceEos = false;
    protected Surface mInputSurface;
    protected AndroidMuxer mMuxer;
    protected int mTrackIndex;

    /* access modifiers changed from: protected */
    public abstract boolean a();

    public abstract Surface getInputSurface();

    /* access modifiers changed from: protected */
    public final void a(SessionConfig config) {
        this.mConfig = config;
        this.mMuxer = this.mConfig.getMuxer();
    }

    public void signalEndOfStream() {
        this.mForceEos = true;
    }

    public void release() {
        if (this.mEncoder != null) {
            try {
                this.mEncoder.stop();
            } catch (Throwable t) {
                Log.w("AndroidEncoder", "release call encoder stop error, " + t);
            }
            this.mEncoder.release();
            this.mEncoder = null;
            Log.d("AndroidEncoder", this + " Released encoder#########");
        }
        if (this.mInputSurface != null) {
            this.mInputSurface.release();
            this.mInputSurface = null;
        }
    }

    public void drainEncoder(boolean endOfStream, boolean isVideoDataType) {
        try {
            a(endOfStream);
        } catch (Exception e) {
            Log.e("AndroidEncoder", "drainEncoder has exception", e);
        }
    }

    private void a(boolean endOfStream) {
        synchronized (this.mMuxer) {
            Log.d("AndroidEncoder", "drainEncoder(" + endOfStream + ") track: " + this.mTrackIndex);
            if (endOfStream) {
                Log.d("AndroidEncoder", "sending EOS to encoder for track " + this.mTrackIndex);
            }
            ByteBuffer[] encoderOutputBuffers = this.mEncoder.getOutputBuffers();
            while (true) {
                int encoderStatus = this.mEncoder.dequeueOutputBuffer(this.mBufferInfo, 1000);
                if (encoderStatus == -1) {
                    if (!endOfStream) {
                        Log.d("AndroidEncoder", "no output available yet");
                        break;
                    }
                    this.a++;
                    if (this.a > 10) {
                        Log.d("AndroidEncoder", "Force shutting down Muxer");
                        this.mMuxer.forceStop();
                        break;
                    }
                    Log.d("AndroidEncoder", "no output available, spinning to await EOS");
                } else if (encoderStatus == -3) {
                    encoderOutputBuffers = this.mEncoder.getOutputBuffers();
                    Log.d("AndroidEncoder", "encoder output buffer changed.");
                } else if (encoderStatus == -2) {
                    MediaFormat newFormat = this.mEncoder.getOutputFormat();
                    Log.d("AndroidEncoder", "encoder output format changed: " + newFormat);
                    this.mTrackIndex = this.mMuxer.addTrack(newFormat);
                } else if (encoderStatus < 0) {
                    Log.d("AndroidEncoder", "unexpected result from encoder.dequeueOutputBuffer: " + encoderStatus);
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
                            Log.d("AndroidEncoder", "Forcing EOS");
                        }
                        Log.d("AndroidEncoder", "sent " + this.mBufferInfo.size + " bytes to muxer, \t ts=" + this.mBufferInfo.presentationTimeUs + "\ttrack " + this.mTrackIndex);
                        this.mMuxer.writeSampleData(this.mEncoder, this.mTrackIndex, encoderStatus, encodedData, this.mBufferInfo);
                    }
                    if ((this.mBufferInfo.flags & 4) != 0) {
                        if (!endOfStream) {
                            Log.d("AndroidEncoder", "reached end of stream unexpectedly");
                        } else {
                            Log.d("AndroidEncoder", "end of stream reached for track " + this.mTrackIndex);
                        }
                    }
                }
            }
            if (endOfStream) {
                if (a()) {
                    Log.d("AndroidEncoder", "final video drain complete");
                } else {
                    Log.d("AndroidEncoder", "final audio drain complete");
                }
            }
        }
    }
}
