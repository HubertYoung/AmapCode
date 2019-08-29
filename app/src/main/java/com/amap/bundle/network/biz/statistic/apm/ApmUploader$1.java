package com.amap.bundle.network.biz.statistic.apm;

import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import java.io.File;

public class ApmUploader$1 implements Callback<Integer> {
    final /* synthetic */ String a;
    final /* synthetic */ File b;
    final /* synthetic */ zq c;

    public ApmUploader$1(zq zqVar, String str, File file) {
        this.c = zqVar;
        this.a = str;
        this.b = file;
    }

    public void callback(Integer num) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("upload success --> ");
            sb.append(this.a);
            AMapLog.d("ApmUploader", sb.toString());
        }
        ahm.a(new Runnable() {
            public final void run() {
                ApmUploader$1.this.b.delete();
            }
        });
    }

    public void error(Throwable th, boolean z) {
        if (bno.a) {
            AMapLog.d("ApmUploader", "uploadLog failure");
        }
        ahm.a(new Runnable() {
            public final void run() {
                ApmUploader$1.this.b.delete();
                ahm.a(new Runnable(System.currentTimeMillis(), ApmUploader$1.this.a) {
                    final /* synthetic */ long a;
                    final /* synthetic */ String b;

                    {
                        this.a = r2;
                        this.b = r4;
                    }

                    public final void run() {
                        StringBuilder sb = new StringBuilder();
                        sb.append(zk.this.d);
                        sb.append("/apm/cache/");
                        sb.append(this.a);
                        sb.append(".cache");
                        FileUtil.writeTextFile(new File(sb.toString()), this.b);
                    }
                });
            }
        });
    }
}
