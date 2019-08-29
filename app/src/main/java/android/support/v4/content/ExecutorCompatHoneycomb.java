package android.support.v4.content;

import android.os.AsyncTask;
import java.util.concurrent.Executor;

class ExecutorCompatHoneycomb {
    ExecutorCompatHoneycomb() {
    }

    public static Executor a() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }
}
