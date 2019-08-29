package com.standardar.wrapper;

public class Config {
    protected long mConfigPtr = 0;
    private final Session mSession;

    public enum LightEstimationMode {
        DISABLED(0),
        AMBIENT_INTENSITY(1);
        
        final int mIndex;

        private LightEstimationMode(int i) {
            this.mIndex = i;
        }

        public static LightEstimationMode fromNumber(int i) {
            LightEstimationMode[] values;
            for (LightEstimationMode lightEstimationMode : values()) {
                if (lightEstimationMode.mIndex == i) {
                    return lightEstimationMode;
                }
            }
            return null;
        }
    }

    public enum PlaneFindingMode {
        DISABLED(0),
        HORIZONTAL(1),
        HORIZONTAL_VERTICAL(2);
        
        final int mIndex;

        private PlaneFindingMode(int i) {
            this.mIndex = i;
        }

        public static PlaneFindingMode fromNumber(int i) {
            PlaneFindingMode[] values;
            for (PlaneFindingMode planeFindingMode : values()) {
                if (planeFindingMode.mIndex == i) {
                    return planeFindingMode;
                }
            }
            return null;
        }
    }

    private native long arCreateConfig(long j);

    private native void arDestroyConfig(long j);

    private native int arGetLightEstimationMode(long j, long j2);

    private native int arGetPlaneFindingMode(long j, long j2);

    private native void arSetLightEstimationMode(long j, long j2, int i);

    private native void arSetPlaneFindingMode(long j, long j2, int i);

    public Config(Session session) {
        this.mSession = session;
        if (this.mSession != null && this.mSession.mSessionPtr != 0) {
            this.mConfigPtr = arCreateConfig(this.mSession.mSessionPtr);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mConfigPtr != 0) {
            arDestroyConfig(this.mConfigPtr);
        }
        super.finalize();
    }

    public LightEstimationMode getLightEstimationMode() {
        return LightEstimationMode.fromNumber(arGetLightEstimationMode(this.mSession.mSessionPtr, this.mConfigPtr));
    }

    public void setLightEstimationMode(LightEstimationMode lightEstimationMode) {
        arSetLightEstimationMode(this.mSession.mSessionPtr, this.mConfigPtr, lightEstimationMode.mIndex);
    }

    public PlaneFindingMode getPlaneFindingMode() {
        return PlaneFindingMode.fromNumber(arGetPlaneFindingMode(this.mSession.mSessionPtr, this.mConfigPtr));
    }

    public void setPlaneFindingMode(PlaneFindingMode planeFindingMode) {
        arSetPlaneFindingMode(this.mSession.mSessionPtr, this.mConfigPtr, planeFindingMode.mIndex);
    }
}
