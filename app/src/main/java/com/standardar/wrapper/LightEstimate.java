package com.standardar.wrapper;

public class LightEstimate {
    protected long mLightEstimatePtr = arCreateLightEstimate(this.mSession.mSessionPtr);
    private final Session mSession;

    public enum State {
        NOT_VALID(0),
        VALID(1);
        
        int mIndex;

        private State(int i) {
            this.mIndex = i;
        }

        static State fromNumber(int i) {
            State[] values;
            for (State state : values()) {
                if (state.mIndex == i) {
                    return state;
                }
            }
            return null;
        }
    }

    private native long arCreateLightEstimate(long j);

    private native void arDestroyLightEstimate(long j);

    private native float arGetPixelIntensity(long j, long j2);

    private native int arGetState(long j, long j2);

    LightEstimate(Session session) {
        this.mSession = session;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (this.mLightEstimatePtr != 0) {
            arDestroyLightEstimate(this.mLightEstimatePtr);
        }
        super.finalize();
    }

    public float getPixelIntensity() {
        return arGetPixelIntensity(this.mSession.mSessionPtr, this.mLightEstimatePtr);
    }

    public State getState() {
        if (this.mSession == null || this.mSession.mSessionPtr == 0 || this.mLightEstimatePtr == 0) {
            return State.NOT_VALID;
        }
        return State.fromNumber(arGetState(this.mSession.mSessionPtr, this.mLightEstimatePtr));
    }
}
