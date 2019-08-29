package com.alipay.android.phone.mobilesdk.permission.guide.a;

import android.content.Context;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* compiled from: FileLock */
public final class b {
    private FileLock a;
    private FileChannel b;

    public final boolean a(Context ctx) {
        try {
            a();
            this.b = new FileOutputStream(new File(ctx.getFilesDir(), "permission-guide.lock")).getChannel();
            this.a = this.b.tryLock();
            if (this.a != null) {
                return true;
            }
            return false;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("FileLock", "try lock error.", tr);
            return false;
        }
    }

    public final void a() {
        try {
            if (this.a != null) {
                this.a.release();
                this.a = null;
            }
            if (this.b != null) {
                this.b.close();
                this.b = null;
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("FileLock", "release error.", tr);
        }
    }
}
