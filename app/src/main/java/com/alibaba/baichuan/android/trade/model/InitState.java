package com.alibaba.baichuan.android.trade.model;

public class InitState {
    public static final int FAILURE = 3;
    public static final int INITIALIZED = 2;
    public static final int INITIALIZING = 1;
    public static final int NOT_INITIALIZED = 0;
    public int state = 0;

    public boolean isInitialized() {
        return this.state == 2;
    }

    public boolean isInitializing() {
        return this.state == 1;
    }

    public void setState(int i) {
        this.state = i;
    }
}
