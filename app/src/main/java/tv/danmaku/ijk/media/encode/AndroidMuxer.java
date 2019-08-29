package tv.danmaku.ijk.media.encode;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoBenchmark;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(18)
public class AndroidMuxer extends BaseMuxer {
    private static final String TAG = "AndroidMuxer";
    private static final boolean VERBOSE = true;
    private final int mExpectedNumTracks;
    private MediaMuxer mMuxer;
    protected int mNumTracks;
    protected int mNumTracksFinished;
    protected String mOutputPath;
    private boolean mStarted;

    private AndroidMuxer(String outputFile) {
        Logger.D(TAG, "AndroidMuxer create: " + outputFile, new Object[0]);
        this.mOutputPath = outputFile;
        try {
            this.mMuxer = new MediaMuxer(outputFile, 0);
        } catch (IOException e) {
            Logger.E((String) TAG, (Throwable) e, "MediaMuxer:" + e.getMessage(), new Object[0]);
        }
        this.mStarted = false;
        this.mNumTracks = 0;
        this.mNumTracksFinished = 0;
        this.mExpectedNumTracks = 2;
    }

    public static AndroidMuxer create(String outputFile) {
        return new AndroidMuxer(outputFile);
    }

    public int addTrack(MediaFormat trackFormat) {
        Logger.D(TAG, "addTrack: " + trackFormat.toString(), new Object[0]);
        if (this.mStarted) {
            throw new RuntimeException("format changed twice");
        }
        int track = this.mMuxer.addTrack(trackFormat);
        this.mNumTracks++;
        if (allTracksAdded()) {
            start();
        }
        return track;
    }

    public void setOrientation(int orientation) {
        if (this.mMuxer != null) {
            this.mMuxer.setOrientationHint(orientation);
        }
    }

    /* access modifiers changed from: protected */
    public void start() {
        this.mMuxer.start();
        this.mStarted = true;
    }

    public void clean() {
        if (!allTracksAdded()) {
            Logger.D(TAG, "clean " + this.mOutputPath + ", ret: " + new File(this.mOutputPath).delete(), new Object[0]);
            return;
        }
        Logger.D(TAG, "clean nothing mNumTracks:" + this.mNumTracks + ", but mExpectedNumTracks: " + this.mExpectedNumTracks, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void stop() {
        Logger.D(TAG, "muxer stop begin", new Object[0]);
        if (this.mStarted) {
            try {
                this.mMuxer.stop();
            } catch (Exception e) {
                Logger.E((String) TAG, (Throwable) e, (String) "android muxer stop exp", new Object[0]);
            }
        }
        try {
            this.mMuxer.release();
        } catch (Exception e2) {
            Logger.E((String) TAG, (Throwable) e2, (String) "android muxer release exp", new Object[0]);
        } finally {
            this.mStarted = false;
        }
        Logger.D(TAG, "muxer stop end", new Object[0]);
        VideoBenchmark.getBundle(VideoBenchmark.KEY_REC).putLong("record_finish", System.nanoTime());
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    /* access modifiers changed from: protected */
    public void signalEndOfTrack() {
        Logger.D(TAG, "signalEndOfTrack", new Object[0]);
        this.mNumTracksFinished++;
    }

    public void writeSampleData(MediaCodec encoder, int trackIndex, int bufferIndex, ByteBuffer encodedData, BufferInfo bufferInfo) {
        if ((bufferInfo.flags & 4) != 0) {
            signalEndOfTrack();
        }
        if ((bufferInfo.flags & 2) != 0) {
            Logger.D(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG", new Object[0]);
            encoder.releaseOutputBuffer(bufferIndex, false);
        } else if (bufferInfo.size == 0) {
            Logger.D(TAG, "ignoring zero size buffer", new Object[0]);
            encoder.releaseOutputBuffer(bufferIndex, false);
            if (allTracksFinished()) {
                stop();
            }
        } else if (!this.mStarted) {
            Logger.D(TAG, "writeSampleData called before muxer started. Ignoring packet. Track index: " + trackIndex + "num of tracks added: " + this.mNumTracks, new Object[0]);
            encoder.releaseOutputBuffer(bufferIndex, false);
        } else {
            bufferInfo.presentationTimeUs = getNextRelativePts(bufferInfo.presentationTimeUs, trackIndex);
            this.mMuxer.writeSampleData(trackIndex, encodedData, bufferInfo);
            Logger.D(TAG, "track index: " + trackIndex + ", ts:" + bufferInfo.presentationTimeUs, new Object[0]);
            encoder.releaseOutputBuffer(bufferIndex, false);
            if (allTracksFinished()) {
                stop();
            }
        }
    }

    public void forceStop() {
        stop();
    }

    /* access modifiers changed from: protected */
    public boolean allTracksFinished() {
        return this.mNumTracks == this.mNumTracksFinished;
    }

    /* access modifiers changed from: protected */
    public boolean allTracksAdded() {
        return this.mNumTracks == this.mExpectedNumTracks;
    }
}
