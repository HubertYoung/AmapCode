package com.standardar.wrapper;

import android.content.Context;
import com.standardar.common.Pose;
import com.standardar.exceptions.UnavailableDeviceNotCompatibleException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Session {
    static final int AR_TRACKABLE_BASE_TRACKABLE = 1095893248;
    static final int AR_TRACKABLE_NOT_VALID = 0;
    static final int AR_TRACKABLE_PLANE = 1095893249;
    static final int AR_TRACKABLE_POINT = 1095893250;
    Context mContext;
    Frame mFrame;
    long mSessionPtr;

    private native long[] arAcquireAllAnchors(long j);

    private native long[] arAcquireAllTrackables(long j, int i);

    private native void arConfigure(long j, long j2);

    private native long arCreateAnchor(long j, Pose pose);

    private native long arCreateSession(Context context);

    private native void arDebugCommandInt(long j, int i, int i2);

    private native void arDestroySession(long j);

    private native String arGetSLAMInfo(long j);

    private native boolean arHasDetectedPlanes(long j);

    private native boolean arIsSupported(long j, long j2);

    private native void arPause(long j);

    private native void arResume(long j);

    private native void arSetCameraTextureName(long j, int i);

    private native void arSetDisplayGeometry(long j, int i, int i2, int i3);

    private native void arStartSLAM(long j);

    private native void arStopSLAM(long j);

    private native void arUpdate(long j, long j2);

    public Session(Context context) throws UnavailableDeviceNotCompatibleException {
        System.loadLibrary("standardar");
        this.mContext = context;
        this.mSessionPtr = arCreateSession(context.getApplicationContext());
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mSessionPtr != 0) {
            arDestroySession(this.mSessionPtr);
        }
        super.finalize();
    }

    public void configure(Config config) {
        arConfigure(this.mSessionPtr, config.mConfigPtr);
    }

    public void setCameraTextureName(int i) {
        arSetCameraTextureName(this.mSessionPtr, i);
    }

    public void setDisplayGeometry(int i, int i2, int i3) {
        arSetDisplayGeometry(this.mSessionPtr, i, i2, i3);
    }

    public boolean isSupported(Config config) {
        return arIsSupported(this.mSessionPtr, config.mConfigPtr);
    }

    public Collection<Anchor> getAllAnchors() {
        return anchorsToCollection(arAcquireAllAnchors(this.mSessionPtr));
    }

    public Anchor createAnchor(Pose pose) {
        return new Anchor(arCreateAnchor(this.mSessionPtr, pose), this);
    }

    public void onStartSLAM() {
        arStartSLAM(this.mSessionPtr);
    }

    public void onStopSLAM() {
        arStopSLAM(this.mSessionPtr);
    }

    public Collection<Anchor> anchorsToCollection(long[] jArr) {
        if (jArr == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (long anchor : jArr) {
            arrayList.add(new Anchor(anchor, this));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public void resume() {
        arResume(this.mSessionPtr);
    }

    public void pause() {
        arPause(this.mSessionPtr);
    }

    public Frame update() {
        if (this.mFrame == null) {
            this.mFrame = new Frame(this);
        }
        arUpdate(this.mSessionPtr, this.mFrame.mFramePtr);
        return this.mFrame;
    }

    public Trackable createTrackable(long j) {
        switch (TrackableBase.getTrackableType(this.mSessionPtr, j)) {
            case AR_TRACKABLE_PLANE /*1095893249*/:
                return new Plane(j, this);
            case AR_TRACKABLE_POINT /*1095893250*/:
                return new Point(j, this);
            default:
                TrackableBase.releaseTrackable(j);
                return null;
        }
    }

    public boolean hasDetectedPlanes() {
        return arHasDetectedPlanes(this.mSessionPtr);
    }

    public <T extends Trackable> Collection<T> getAllTrackables(Class<T> cls) {
        long[] jArr;
        if (cls == Plane.class) {
            jArr = arAcquireAllTrackables(this.mSessionPtr, AR_TRACKABLE_PLANE);
        } else if (cls == Point.class) {
            jArr = arAcquireAllTrackables(this.mSessionPtr, AR_TRACKABLE_POINT);
        } else if (cls != Trackable.class) {
            return Collections.emptyList();
        } else {
            jArr = arAcquireAllTrackables(this.mSessionPtr, AR_TRACKABLE_BASE_TRACKABLE);
        }
        if (jArr == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(jArr.length);
        for (long createTrackable : jArr) {
            Trackable createTrackable2 = createTrackable(createTrackable);
            if (createTrackable2 != null) {
                arrayList.add((Trackable) cls.cast(createTrackable2));
            }
        }
        return arrayList;
    }

    public void onDebugCommandInt(int i, int i2) {
        arDebugCommandInt(this.mSessionPtr, i, i2);
    }

    public String getSLAMInfo() {
        return arGetSLAMInfo(this.mSessionPtr);
    }
}
