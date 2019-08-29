package com.ant.multimedia.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import com.alipay.alipaylogger.Log;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(18)
public class AndroidMuxer extends BaseMuxer {
    private int a;
    private MediaMuxer b;
    private boolean c;
    protected int mNumTracks;
    protected int mNumTracksFinished;
    protected String mOutputPath;

    private AndroidMuxer(String outputFile) {
        Log.d("AndroidMuxer", "AndroidMuxer create: " + outputFile);
        this.mOutputPath = outputFile;
        try {
            this.b = new MediaMuxer(outputFile, 0);
        } catch (IOException e) {
            Log.e("AndroidMuxer", "MediaMuxer:" + e.getMessage(), e);
        }
        this.c = false;
        this.mNumTracks = 0;
        this.mNumTracksFinished = 0;
        this.a = 2;
    }

    public void setTrackNum(int num) {
        this.a = num;
    }

    public static AndroidMuxer create(String outputFile) {
        return new AndroidMuxer(outputFile);
    }

    public int addTrack(MediaFormat trackFormat) {
        Log.d("AndroidMuxer", "addTrack: " + trackFormat.toString());
        if (this.c) {
            throw new RuntimeException("format changed twice");
        }
        int track = this.b.addTrack(trackFormat);
        this.mNumTracks++;
        if (e()) {
            a();
        }
        return track;
    }

    public void setOrientation(int orientation) {
        if (this.b != null) {
            this.b.setOrientationHint(orientation);
        }
    }

    private void a() {
        this.b.start();
        this.c = true;
    }

    public void clean() {
        if (!e()) {
            Log.d("AndroidMuxer", "clean " + this.mOutputPath + ", ret: " + new File(this.mOutputPath).delete());
            return;
        }
        Log.d("AndroidMuxer", "clean nothing mNumTracks:" + this.mNumTracks + ", but mExpectedNumTracks: " + this.a);
    }

    private void b() {
        Log.d("AndroidMuxer", "muxer stop begin");
        if (this.c) {
            try {
                this.b.stop();
            } catch (Exception e) {
                Log.e("AndroidMuxer", "android muxer stop exp", e);
            }
        }
        try {
            this.b.release();
        } catch (Exception e2) {
            Log.e("AndroidMuxer", "android muxer release exp", e2);
        } finally {
            this.c = false;
        }
        Log.d("AndroidMuxer", "muxer stop end");
    }

    public boolean isStarted() {
        return this.c;
    }

    private void c() {
        Log.d("AndroidMuxer", "signalEndOfTrack");
        this.mNumTracksFinished++;
    }

    public void writeSampleData(MediaCodec encoder, int trackIndex, int bufferIndex, ByteBuffer encodedData, BufferInfo bufferInfo) {
        if ((bufferInfo.flags & 4) != 0) {
            c();
        }
        if ((bufferInfo.flags & 2) != 0) {
            Log.d("AndroidMuxer", "ignoring BUFFER_FLAG_CODEC_CONFIG");
            encoder.releaseOutputBuffer(bufferIndex, false);
        } else if (bufferInfo.size == 0) {
            Log.d("AndroidMuxer", "ignoring zero size buffer");
            encoder.releaseOutputBuffer(bufferIndex, false);
            if (d()) {
                b();
            }
        } else if (!this.c) {
            Log.d("AndroidMuxer", "writeSampleData called before muxer started. Ignoring packet. Track index: " + trackIndex + "num of tracks added: " + this.mNumTracks);
            encoder.releaseOutputBuffer(bufferIndex, false);
        } else {
            bufferInfo.presentationTimeUs = a(bufferInfo.presentationTimeUs, trackIndex);
            this.b.writeSampleData(trackIndex, encodedData, bufferInfo);
            Log.d("AndroidMuxer", "track index: " + trackIndex + ", ts:" + bufferInfo.presentationTimeUs);
            encoder.releaseOutputBuffer(bufferIndex, false);
            if (d()) {
                b();
            }
        }
    }

    public void forceStop() {
        b();
    }

    private boolean d() {
        return this.mNumTracks == this.mNumTracksFinished;
    }

    private boolean e() {
        return this.mNumTracks == this.a;
    }
}
