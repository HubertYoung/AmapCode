package com.standardar.wrapper;

import com.standardar.common.Pose;
import java.nio.FloatBuffer;

public class Plane extends TrackableBase {

    public enum Type {
        HORIZONTAL_UPWARD_FACING(0),
        HORIZONTAL_DOWNWARD_FACING(1);
        
        int mIndex;

        private Type(int i) {
            this.mIndex = i;
        }

        static Type fromNumber(int i) {
            Type[] values;
            for (Type type : values()) {
                if (type.mIndex == i) {
                    return type;
                }
            }
            return null;
        }
    }

    private native Pose arGetCenterPose(long j, long j2);

    private native float arGetExtentX(long j, long j2);

    private native float arGetExtentY(long j, long j2);

    private native float arGetExtentZ(long j, long j2);

    private native float[] arGetPolygon(long j, long j2);

    private native float[] arGetPolygon3D(long j, long j2);

    private native int arGetPolygon3DSize(long j, long j2);

    private native int arGetType(long j, long j2);

    private native boolean arIsPoseInExtents(long j, long j2, Pose pose);

    private native boolean arIsPoseInPolygon(long j, long j2, Pose pose);

    Plane(long j, Session session) {
        super(j, session);
    }

    public Type getType() {
        return Type.fromNumber(arGetType(this.mSession.mSessionPtr, this.mTrackablePtr));
    }

    public Pose getCenterPose() {
        return arGetCenterPose(this.mSession.mSessionPtr, this.mTrackablePtr);
    }

    public float getExtentX() {
        return arGetExtentX(this.mSession.mSessionPtr, this.mTrackablePtr);
    }

    public float getExtentY() {
        return arGetExtentY(this.mSession.mSessionPtr, this.mTrackablePtr);
    }

    public float getExtentZ() {
        return arGetExtentZ(this.mSession.mSessionPtr, this.mTrackablePtr);
    }

    public FloatBuffer getPolygon() {
        return FloatBuffer.wrap(arGetPolygon(this.mSession.mSessionPtr, this.mTrackablePtr));
    }

    public int getPolygon3DSize() {
        return arGetPolygon3DSize(this.mSession.mSessionPtr, this.mTrackablePtr);
    }

    public FloatBuffer getPolygon3D() {
        return FloatBuffer.wrap(arGetPolygon3D(this.mSession.mSessionPtr, this.mTrackablePtr));
    }

    public boolean isPoseInPolygon(Pose pose) {
        return arIsPoseInPolygon(this.mSession.mSessionPtr, this.mTrackablePtr, pose);
    }

    public boolean isPoseInExtents(Pose pose) {
        return arIsPoseInExtents(this.mSession.mSessionPtr, this.mTrackablePtr, pose);
    }
}
