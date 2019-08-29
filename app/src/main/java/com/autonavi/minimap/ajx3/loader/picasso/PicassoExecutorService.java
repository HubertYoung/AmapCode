package com.autonavi.minimap.ajx3.loader.picasso;

import android.net.NetworkInfo;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 5;

    static final class PicassoFutureTask extends FutureTask<ImageHunter> implements Comparable<PicassoFutureTask> {
        private final ImageHunter hunter;

        public PicassoFutureTask(ImageHunter imageHunter) {
            super(imageHunter, null);
            this.hunter = imageHunter;
        }

        public final int compareTo(PicassoFutureTask picassoFutureTask) {
            Priority priority = this.hunter.getPriority();
            Priority priority2 = picassoFutureTask.hunter.getPriority();
            return priority == priority2 ? this.hunter.sequence - picassoFutureTask.hunter.sequence : priority2.ordinal() - priority.ordinal();
        }
    }

    PicassoExecutorService() {
        super(5, 5, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    /* access modifiers changed from: 0000 */
    public void adjustThreadCount(NetworkInfo networkInfo) {
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            setThreadCount(5);
            return;
        }
        int type = networkInfo.getType();
        if (!(type == 6 || type == 9)) {
            switch (type) {
                case 0:
                    int subtype = networkInfo.getSubtype();
                    switch (subtype) {
                        case 1:
                        case 2:
                            setThreadCount(2);
                            return;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            break;
                        default:
                            switch (subtype) {
                                case 12:
                                    break;
                                case 13:
                                case 14:
                                case 15:
                                    setThreadCount(4);
                                    return;
                                default:
                                    setThreadCount(5);
                                    return;
                            }
                    }
                    setThreadCount(3);
                    return;
                case 1:
                    break;
                default:
                    setThreadCount(5);
                    return;
            }
        }
        setThreadCount(5);
    }

    private void setThreadCount(int i) {
        setCorePoolSize(i);
        setMaximumPoolSize(i);
    }

    public Future<?> submit(Runnable runnable) {
        PicassoFutureTask picassoFutureTask = new PicassoFutureTask((ImageHunter) runnable);
        execute(picassoFutureTask);
        return picassoFutureTask;
    }
}
