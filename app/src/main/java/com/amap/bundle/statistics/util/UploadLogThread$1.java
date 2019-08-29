package com.amap.bundle.statistics.util;

import com.autonavi.common.Callback;

public class UploadLogThread$1 implements Callback<Integer> {
    final /* synthetic */ afp a;

    public void error(Throwable th, boolean z) {
    }

    public UploadLogThread$1(afp afp) {
        this.a = afp;
    }

    public void callback(Integer num) {
        if (num != null && num.intValue() < 300) {
            this.a.d;
        }
        this.a.a.delete();
    }
}
