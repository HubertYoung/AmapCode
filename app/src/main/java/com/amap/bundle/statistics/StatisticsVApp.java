package com.amap.bundle.statistics;

public class StatisticsVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
    }

    public void vAppEnterBackground() {
        super.vAppEnterBackground();
        HttpUrlCollector.b();
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        HttpUrlCollector.c();
    }
}
