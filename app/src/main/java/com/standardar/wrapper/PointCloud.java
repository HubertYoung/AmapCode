package com.standardar.wrapper;

import java.nio.FloatBuffer;

public class PointCloud {
    protected long mPointCloudPtr;
    private final Session mSession;

    private native float[] arGetData(long j, long j2);

    private native int arGetNumberOfPoints(long j, long j2);

    private native void arReleasePointCloud(long j);

    PointCloud(Session session, long j) {
        this.mPointCloudPtr = j;
        this.mSession = session;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mPointCloudPtr != 0) {
            arReleasePointCloud(this.mPointCloudPtr);
        }
        super.finalize();
    }

    public void release() {
        arReleasePointCloud(this.mPointCloudPtr);
        this.mPointCloudPtr = 0;
    }

    public int getNumberOfPoints() {
        return arGetNumberOfPoints(this.mSession.mSessionPtr, this.mPointCloudPtr);
    }

    public FloatBuffer getPoints() {
        return FloatBuffer.wrap(arGetData(this.mSession.mSessionPtr, this.mPointCloudPtr));
    }
}
