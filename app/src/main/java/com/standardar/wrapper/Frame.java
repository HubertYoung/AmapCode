package com.standardar.wrapper;

import android.view.MotionEvent;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Frame {
    private Camera mCamera;
    protected long mFramePtr = 0;
    private final LightEstimate mLightEstimate;
    private final Session mSession;

    public enum HitTestMode {
        AR_HIT_TEST_MODE_POLYGON_ONLY(0),
        AR_HIT_TEST_MODE_POLYGON_AND_HORIZONPLANE(1),
        AR_HIT_TEST_MODE_POLYGON_PERSISTENCE(2);
        
        final int nativeCode;

        private HitTestMode(int i) {
            this.nativeCode = i;
        }

        static HitTestMode forNumber(int i) {
            HitTestMode[] values;
            for (HitTestMode hitTestMode : values()) {
                if (hitTestMode.nativeCode == i) {
                    return hitTestMode;
                }
            }
            return null;
        }
    }

    private native long arAcquirePointCloud(long j, long j2);

    private native long arCreateFrame(long j);

    private native void arDestroyFrame(long j);

    private native void arGetLightEstimate(long j, long j2, long j3);

    private native long[] arGetPreviewSize(long j, long j2);

    private native long[] arGetUpdatedAnchors(long j, long j2);

    private native long[] arGetUpdatedTrackables(long j, long j2, int i);

    private native boolean arHasDisplayGeometryChanged(long j, long j2);

    private native long[] arHitTest(long j, long j2, float f, float f2);

    private native void arSetHitTestMode(long j, long j2, int i);

    private native void arTransformDisplayUvCoords(long j, long j2, float[] fArr, float[] fArr2);

    Frame(Session session) {
        this.mSession = session;
        if (!(this.mSession == null || this.mSession.mSessionPtr == 0)) {
            this.mFramePtr = arCreateFrame(this.mSession.mSessionPtr);
        }
        this.mLightEstimate = new LightEstimate(this.mSession);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mFramePtr != 0) {
            arDestroyFrame(this.mFramePtr);
        }
        super.finalize();
    }

    public PointCloud acquirePointCloud() {
        return new PointCloud(this.mSession, arAcquirePointCloud(this.mSession.mSessionPtr, this.mFramePtr));
    }

    public Camera getCamera() {
        if (this.mCamera == null) {
            this.mCamera = new Camera(this.mSession, this);
        }
        return this.mCamera;
    }

    public LightEstimate getLightEstimate() {
        arGetLightEstimate(this.mSession.mSessionPtr, this.mFramePtr, this.mLightEstimate.mLightEstimatePtr);
        return this.mLightEstimate;
    }

    public boolean hasDisplayGeometryChanged() {
        return arHasDisplayGeometryChanged(this.mSession.mSessionPtr, this.mFramePtr);
    }

    public void setHitTestMode(HitTestMode hitTestMode) {
        arSetHitTestMode(this.mSession.mSessionPtr, this.mFramePtr, hitTestMode.nativeCode);
    }

    public List<HitResult> hitTest(float f, float f2) {
        long[] arHitTest = arHitTest(this.mSession.mSessionPtr, this.mFramePtr, f, f2);
        if (arHitTest == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(arHitTest.length);
        for (long hitResult : arHitTest) {
            HitResult hitResult2 = new HitResult(hitResult, this.mSession);
            if (hitResult2.getTrackable() != null) {
                arrayList.add(hitResult2);
            }
        }
        return arrayList;
    }

    public List<HitResult> hitTest(MotionEvent motionEvent) {
        return hitTest(motionEvent.getX(), motionEvent.getY());
    }

    public void transformDisplayUvCoords(FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        floatBuffer.position(0);
        floatBuffer2.position(0);
        int remaining = floatBuffer.remaining();
        float[] fArr = new float[remaining];
        floatBuffer.get(fArr);
        float[] fArr2 = new float[remaining];
        arTransformDisplayUvCoords(this.mSession.mSessionPtr, this.mFramePtr, fArr, fArr2);
        floatBuffer2.put(fArr2, 0, remaining);
        floatBuffer.rewind();
        floatBuffer2.rewind();
    }

    public Collection<Anchor> getUpdatedAnchors() {
        return this.mSession.anchorsToCollection(arGetUpdatedAnchors(this.mSession.mSessionPtr, this.mFramePtr));
    }

    public long[] getPreviewSize() {
        return arGetPreviewSize(this.mSession.mSessionPtr, this.mFramePtr);
    }

    public <T extends Trackable> Collection<T> getUpdatedTrackables(Class<T> cls) {
        long[] jArr;
        if (cls == Plane.class) {
            jArr = arGetUpdatedTrackables(this.mSession.mSessionPtr, this.mFramePtr, 1095893249);
        } else if (cls == Point.class) {
            jArr = arGetUpdatedTrackables(this.mSession.mSessionPtr, this.mFramePtr, 1095893250);
        } else if (cls == Trackable.class) {
            jArr = arGetUpdatedTrackables(this.mSession.mSessionPtr, this.mFramePtr, 1095893248);
        } else {
            jArr = null;
        }
        if (jArr == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(jArr.length);
        for (long createTrackable : jArr) {
            Trackable createTrackable2 = this.mSession.createTrackable(createTrackable);
            if (createTrackable2 != null) {
                arrayList.add(createTrackable2);
            }
        }
        return arrayList;
    }
}
