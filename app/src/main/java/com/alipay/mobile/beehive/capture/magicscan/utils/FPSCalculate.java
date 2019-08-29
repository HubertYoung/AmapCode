package com.alipay.mobile.beehive.capture.magicscan.utils;

import java.util.LinkedList;
import java.util.List;

public class FPSCalculate {
    private static int queueLen = 10;
    private volatile float currentFps;
    private List<Long> mPtsList;

    public FPSCalculate() {
        this(queueLen);
    }

    public FPSCalculate(int len) {
        this.mPtsList = new LinkedList();
        queueLen = len;
    }

    public void addRecord() {
        long curTime = System.currentTimeMillis();
        if (this.mPtsList.size() < queueLen) {
            this.mPtsList.add(Long.valueOf(curTime));
            this.currentFps = -1.0f;
            return;
        }
        this.mPtsList.remove(0);
        this.mPtsList.add(Long.valueOf(curTime));
        long start = this.mPtsList.get(0).longValue();
        int size = this.mPtsList.size() - 1;
        this.currentFps = (1000.0f * ((float) size)) / ((float) (this.mPtsList.get(size).longValue() - start));
    }

    public float getFps() {
        return this.currentFps;
    }
}
