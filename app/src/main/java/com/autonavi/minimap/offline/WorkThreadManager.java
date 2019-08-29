package com.autonavi.minimap.offline;

import java.util.concurrent.Executor;

public class WorkThreadManager {
    static final ahn sDefaultExecutor = new ahn(3);

    public static abstract class OfflineTask<ResultType> extends a<ResultType> {
        public void onError(Throwable th) {
        }

        public void onFinished(ResultType resulttype) {
        }

        public Executor getExecutor() {
            return WorkThreadManager.sDefaultExecutor;
        }
    }

    public static <T> a start(OfflineTask<T> offlineTask) {
        return ahl.a(offlineTask, offlineTask.getExecutor());
    }
}
