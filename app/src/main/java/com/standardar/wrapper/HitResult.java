package com.standardar.wrapper;

import com.standardar.common.Pose;

public class HitResult {
    protected long mHitResultPtr;
    private final Session mSession;

    private native long arAcquireTrackable(long j, long j2);

    private native long arCreateAnchor(long j, long j2);

    private native void arDestroyHitResult(long j);

    private native float arGetDistance(long j, long j2);

    private native Pose arGetHitPose(long j, long j2);

    HitResult(long j, Session session) {
        this.mHitResultPtr = j;
        this.mSession = session;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == super.getClass() && this.mHitResultPtr == ((HitResult) obj).mHitResultPtr) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Long.valueOf(this.mHitResultPtr).hashCode();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mHitResultPtr != 0) {
            arDestroyHitResult(this.mHitResultPtr);
        }
        super.finalize();
    }

    public Pose getHitPose() {
        return arGetHitPose(this.mSession.mSessionPtr, this.mHitResultPtr);
    }

    public float getDistance() {
        return arGetDistance(this.mSession.mSessionPtr, this.mHitResultPtr);
    }

    public Trackable getTrackable() {
        return this.mSession.createTrackable(arAcquireTrackable(this.mSession.mSessionPtr, this.mHitResultPtr));
    }

    public Anchor createAnchor() {
        return new Anchor(arCreateAnchor(this.mSession.mSessionPtr, this.mHitResultPtr), this.mSession);
    }
}
