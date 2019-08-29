package com.standardar.wrapper;

public interface Trackable {

    public enum TrackingState {
        TRACKING(0),
        PAUSED(1),
        STOPPED(2);
        
        int mIndex;

        private TrackingState(int i) {
            this.mIndex = i;
        }

        static TrackingState fromNumber(int i) {
            TrackingState[] values;
            for (TrackingState trackingState : values()) {
                if (trackingState.mIndex == i) {
                    return trackingState;
                }
            }
            return null;
        }
    }

    TrackingState getTrackingState();
}
