package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

public interface IGpsStateResume {
    void cancelResumeGpsState();

    void restoreGpsState();

    void resumeGpsState();
}
