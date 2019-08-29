package com.ant.phone.imu;

public abstract class RotationTracker {
    protected IMUListener listener;

    public interface IMUListener {
        void a(float[] fArr);
    }

    public abstract void getEulerAngles(float[] fArr);

    public abstract float[] getLastMatrix();

    public abstract void getMatrix(float[] fArr);

    public abstract void startTracking();

    public abstract void stopTracking();

    public synchronized void registerListener(IMUListener listener2) {
        this.listener = listener2;
    }

    public synchronized void unRegisterListener(IMUListener listener2) {
        this.listener = null;
    }
}
