package com.ant.multimedia.encode;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import java.nio.ByteBuffer;

abstract class BaseMuxer {
    protected long[] mFirstPts = {0, 0};
    protected long[] mLastPts = {0, 0};
    protected byte[] videoConfig = null;

    public abstract int addTrack(MediaFormat mediaFormat);

    public abstract void writeSampleData(MediaCodec mediaCodec, int i, int i2, ByteBuffer byteBuffer, BufferInfo bufferInfo);

    BaseMuxer() {
    }

    /* access modifiers changed from: protected */
    public final long a(long absPts, int trackIndex) {
        if (this.mFirstPts[trackIndex] != 0) {
            return b(absPts - this.mFirstPts[trackIndex], trackIndex);
        }
        this.mFirstPts[trackIndex] = absPts;
        return 0;
    }

    private long b(long pts, int trackIndex) {
        if (this.mLastPts[trackIndex] >= pts) {
            long[] jArr = this.mLastPts;
            jArr[trackIndex] = jArr[trackIndex] + 9643;
            return this.mLastPts[trackIndex];
        }
        this.mLastPts[trackIndex] = pts;
        return pts;
    }
}
