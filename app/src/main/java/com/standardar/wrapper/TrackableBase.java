package com.standardar.wrapper;

import com.standardar.wrapper.Trackable.TrackingState;

public class TrackableBase implements Trackable {
    protected final Session mSession;
    protected long mTrackablePtr;

    private static native int arGetTrackableType(long j, long j2);

    private native int arGetTrackingState(long j, long j2);

    private static native void arReleaseTrackable(long j);

    TrackableBase(long j, Session session) {
        this.mSession = session;
        this.mTrackablePtr = j;
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == super.getClass() && this.mTrackablePtr == ((TrackableBase) obj).mTrackablePtr) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Long.valueOf(this.mTrackablePtr).hashCode();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mTrackablePtr != 0) {
            arReleaseTrackable(this.mTrackablePtr);
        }
        super.finalize();
    }

    public TrackingState getTrackingState() {
        if (this.mSession == null || this.mSession.mSessionPtr == 0 || this.mTrackablePtr == 0) {
            return TrackingState.STOPPED;
        }
        return TrackingState.fromNumber(arGetTrackingState(this.mSession.mSessionPtr, this.mTrackablePtr));
    }

    static void releaseTrackable(long j) {
        arReleaseTrackable(j);
    }

    static int getTrackableType(long j, long j2) {
        return arGetTrackableType(j, j2);
    }
}
