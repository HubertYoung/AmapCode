package com.standardar.wrapper;

import com.standardar.common.Pose;
import com.standardar.wrapper.Trackable.TrackingState;

public class Anchor {
    protected long mAnchorPtr;
    private final Session mSession;

    private native void arDetach(long j, long j2);

    private native Pose arGetPose(long j, long j2);

    private native int arGetTrackingState(long j, long j2);

    private native void arReleaseAnchor(long j);

    Anchor(long j, Session session) {
        this.mAnchorPtr = j;
        this.mSession = session;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == super.getClass() && this.mAnchorPtr == ((Anchor) obj).mAnchorPtr) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Long.valueOf(this.mAnchorPtr).hashCode();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mAnchorPtr != 0) {
            arReleaseAnchor(this.mAnchorPtr);
        }
        super.finalize();
    }

    public Pose getPose() {
        if (this.mSession == null || this.mSession.mSessionPtr == 0 || this.mAnchorPtr == 0) {
            return new Pose();
        }
        return arGetPose(this.mSession.mSessionPtr, this.mAnchorPtr);
    }

    public TrackingState getTrackingState() {
        if (this.mSession == null || this.mSession.mSessionPtr == 0 || this.mAnchorPtr == 0) {
            return TrackingState.STOPPED;
        }
        return TrackingState.fromNumber(arGetTrackingState(this.mSession.mSessionPtr, this.mAnchorPtr));
    }

    public void detach() {
        arDetach(this.mSession.mSessionPtr, this.mAnchorPtr);
    }
}
