package defpackage;

import android.content.Context;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.statistics.util.UploadLogThread$1;
import com.autonavi.common.Callback;
import java.io.File;

/* renamed from: afp reason: default package */
/* compiled from: UploadLogThread */
public final class afp extends Thread {
    /* access modifiers changed from: private */
    public final File a;
    private final Context b;
    private final String c;
    /* access modifiers changed from: private */
    public final int d;

    afp(Context context, File file, String str, int i) {
        this.b = context;
        this.a = file;
        this.c = str;
        this.d = i;
    }

    public final void run() {
        if (this.a.exists() && this.a.length() > 1) {
            if (!FunctionSupportConfiger.getInst().isLocalLogActive() || this.d == 4) {
                afn.a().a(this.a, this.d, (Callback<Integer>) new UploadLogThread$1<Integer>(this));
            } else {
                this.a.delete();
            }
        }
    }
}
