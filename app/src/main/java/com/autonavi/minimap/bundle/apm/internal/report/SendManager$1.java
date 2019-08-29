package com.autonavi.minimap.bundle.apm.internal.report;

import com.autonavi.common.Callback;
import java.io.File;

public class SendManager$1 implements Callback<Integer> {
    final /* synthetic */ a a;
    final /* synthetic */ File b;

    public SendManager$1(a aVar, File file) {
        this.a = aVar;
        this.b = file;
    }

    public final void callback(Integer num) {
        this.a.a = 200;
        if (bno.a) {
            new StringBuilder("upload success zipFile ").append(this.b.getAbsolutePath());
        }
    }

    public final void error(Throwable th, boolean z) {
        this.a.a = 0;
        if (bno.a) {
            new StringBuilder("upload failure zipFile ").append(this.b.getAbsolutePath());
        }
    }
}
