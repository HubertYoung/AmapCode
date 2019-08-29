package com.squareup.leakcanary;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.view.LayoutInflater;
import android.widget.Toast;
import com.squareup.leakcanary.internal.FutureResult;
import com.squareup.leakcanary.internal.LeakCanaryInternals;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class AndroidHeapDumper implements HeapDumper {
    private static final String TAG = "AndroidHeapDumper";
    /* access modifiers changed from: private */
    public final Context context;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public AndroidHeapDumper(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public final File dumpHeap() {
        LeakCanaryInternals.isExternalStorageWritable();
        File heapDumpFile = getHeapDumpFile();
        if (heapDumpFile.exists()) {
            return NO_DUMP;
        }
        FutureResult futureResult = new FutureResult();
        showToast(futureResult);
        if (!futureResult.wait(5, TimeUnit.SECONDS)) {
            return NO_DUMP;
        }
        Toast toast = (Toast) futureResult.get();
        try {
            Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
            cancelToast(toast);
            return heapDumpFile;
        } catch (IOException unused) {
            cleanup();
            return NO_DUMP;
        }
    }

    public final void cleanup() {
        LeakCanaryInternals.executeOnFileIoThread(new Runnable() {
            public void run() {
                LeakCanaryInternals.isExternalStorageWritable();
                File access$000 = AndroidHeapDumper.this.getHeapDumpFile();
                if (access$000.exists()) {
                    new StringBuilder("Previous analysis did not complete correctly, cleaning: ").append(access$000);
                    access$000.delete();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public File getHeapDumpFile() {
        return new File(LeakCanaryInternals.storageDirectory(), "suspected_leak_heapdump.hprof");
    }

    private void showToast(final FutureResult<Toast> futureResult) {
        this.mainHandler.post(new Runnable() {
            public void run() {
                final Toast toast = new Toast(AndroidHeapDumper.this.context);
                toast.setGravity(16, 0, 0);
                toast.setDuration(1);
                toast.setView(LayoutInflater.from(AndroidHeapDumper.this.context).inflate(R.layout.__leak_canary_heap_dump_toast, null));
                toast.show();
                Looper.myQueue().addIdleHandler(new IdleHandler() {
                    public boolean queueIdle() {
                        futureResult.set(toast);
                        return false;
                    }
                });
            }
        });
    }

    private void cancelToast(final Toast toast) {
        this.mainHandler.post(new Runnable() {
            public void run() {
                toast.cancel();
            }
        });
    }
}
