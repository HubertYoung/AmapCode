package com.amap.bundle.network.biz.statistic.apm;

import com.autonavi.common.Callback;
import java.io.File;

public class ApmCacheTaskManager$1 implements Callback<Integer> {
    final /* synthetic */ File a;
    final /* synthetic */ File b;
    final /* synthetic */ zk c;

    public ApmCacheTaskManager$1(zk zkVar, File file, File file2) {
        this.c = zkVar;
        this.a = file;
        this.b = file2;
    }

    public void callback(Integer num) {
        if (this.a != null) {
            this.a.delete();
        }
        ahm.a(new Runnable() {
            public final void run() {
                ApmCacheTaskManager$1.this.b.delete();
            }
        });
    }

    public void error(Throwable th, boolean z) {
        ahm.a(new Runnable() {
            public final void run() {
                ApmCacheTaskManager$1.this.b.delete();
            }
        });
    }
}
