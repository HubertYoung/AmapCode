package android.support.v4.content;

import android.os.Build.VERSION;
import java.util.concurrent.Executor;

public class ParallelExecutorCompat {
    public static Executor getParallelExecutor() {
        if (VERSION.SDK_INT >= 11) {
            return ExecutorCompatHoneycomb.a();
        }
        return ModernAsyncTask.d;
    }
}
