package com.standardar.wrapper;

import com.standardar.common.Pose;
import com.standardar.wrapper.Trackable.TrackingState;

public class Camera {
    protected long mCameraPtr = 0;
    private final Session mSession;

    private native long arAcquireCamera(long j, long j2);

    private native Pose arGetPose(long j, long j2);

    private native void arGetProjectionMatrix(long j, long j2, float[] fArr, int i, float f, float f2);

    private native int arGetTrackingState(long j, long j2);

    private native void arGetViewMatrix(long j, long j2, float[] fArr, int i);

    private native void arLookAt(long j, long j2, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4);

    private native void arReleaseCamera(long j);

    Camera(Session session, Frame frame) {
        this.mSession = session;
        if (session != null && session.mSessionPtr != 0 && frame != null && frame.mFramePtr != 0) {
            this.mCameraPtr = arAcquireCamera(session.mSessionPtr, frame.mFramePtr);
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == super.getClass() && this.mCameraPtr == ((Camera) obj).mCameraPtr) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Long.valueOf(this.mCameraPtr).hashCode();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mCameraPtr != 0) {
            arReleaseCamera(this.mCameraPtr);
        }
        super.finalize();
    }

    public TrackingState getTrackingState() {
        if (this.mSession == null || this.mSession.mSessionPtr == 0 || this.mCameraPtr == 0) {
            return TrackingState.STOPPED;
        }
        return TrackingState.fromNumber(arGetTrackingState(this.mSession.mSessionPtr, this.mCameraPtr));
    }

    public Pose getPose() {
        if (this.mSession == null || this.mSession.mSessionPtr == 0 || this.mCameraPtr == 0) {
            return new Pose();
        }
        return arGetPose(this.mSession.mSessionPtr, this.mCameraPtr);
    }

    public void getViewMatrix(float[] fArr, int i) {
        arGetViewMatrix(this.mSession.mSessionPtr, this.mCameraPtr, fArr, i);
    }

    public void getProjectionMatrix(float[] fArr, int i, float f, float f2) {
        arGetProjectionMatrix(this.mSession.mSessionPtr, this.mCameraPtr, fArr, i, f, f2);
    }

    public void lookAt(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4) {
        arLookAt(this.mSession.mSessionPtr, this.mCameraPtr, fArr, fArr2, fArr3, fArr4);
    }
}
