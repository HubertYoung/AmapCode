package com.autonavi.minimap.offline.dataaccess;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.minimap.offline.WorkThreadManager;
import com.autonavi.minimap.offline.WorkThreadManager.OfflineTask;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void execute(final Runnable runnable) {
        WorkThreadManager.start(new OfflineTask<Object>() {
            public final Object doBackground() throws Exception {
                runnable.run();
                return null;
            }
        });
    }

    public <V extends ResponseValue, E> void notifyResponse(final V v, final UseCaseCallback<V, E> useCaseCallback) {
        this.mHandler.post(new Runnable() {
            public final void run() {
                useCaseCallback.onSuccess(v);
            }
        });
    }

    public <V extends ResponseValue, E> void onError(final UseCaseCallback<V, E> useCaseCallback, final E e) {
        this.mHandler.post(new Runnable() {
            public final void run() {
                useCaseCallback.onError(e);
            }
        });
    }

    public <V extends ResponseValue, E> void onCancel(final UseCaseCallback<V, E> useCaseCallback) {
        this.mHandler.post(new Runnable() {
            public final void run() {
                useCaseCallback.onCancel();
            }
        });
    }
}
