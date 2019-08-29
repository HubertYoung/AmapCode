package com.autonavi.jni.route.health;

public interface IHealthFrame {
    void OnLengthSpeedTime(int i, double d, long j);

    void OnLocationChanged(HealthPoint healthPoint);

    void OnMileStoneUpdated(HealthPoint healthPoint, int i);

    void OnPlaySound(String str);

    void OnStatisticsUpdated(TraceStatistics traceStatistics);

    void OnStatusChanged(TraceStatus traceStatus);
}
