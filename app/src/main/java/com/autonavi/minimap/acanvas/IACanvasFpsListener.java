package com.autonavi.minimap.acanvas;

public interface IACanvasFpsListener {
    void actionLogError(int i, String str);

    void actionLogFPS(long j, long j2);

    void drawTime(long j, long j2, int i);
}
