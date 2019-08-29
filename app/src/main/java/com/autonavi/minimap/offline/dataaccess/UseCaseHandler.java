package com.autonavi.minimap.offline.dataaccess;

import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;

public class UseCaseHandler {
    private static volatile UseCaseHandler INSTANCE;
    private final UseCaseScheduler mUseCaseScheduler;

    static final class a<V extends ResponseValue, E> implements UseCaseCallback<V, E> {
        private final UseCaseCallback<V, E> a;
        private final UseCaseHandler b;

        public final /* synthetic */ void onSuccess(Object obj) {
            this.b.notifyResponse((ResponseValue) obj, this.a);
        }

        public a(UseCaseCallback<V, E> useCaseCallback, UseCaseHandler useCaseHandler) {
            this.a = useCaseCallback;
            this.b = useCaseHandler;
        }

        public final void onError(E e) {
            this.b.notifyError(this.a, e);
        }

        public final void onCancel() {
            this.b.notifyCancle(this.a);
        }
    }

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        this.mUseCaseScheduler = useCaseScheduler;
    }

    public <T extends RequestValues, R extends ResponseValue, E> void execute(final UseCase<T, R, E> useCase, T t, UseCaseCallback<R, E> useCaseCallback) {
        useCase.setRequestValues(t);
        useCase.setUseCaseCallback(new a(useCaseCallback, this));
        this.mUseCaseScheduler.execute(new Runnable() {
            public final void run() {
                useCase.run();
            }
        });
    }

    public <T extends RequestValues, R extends ResponseValue, E> void execute(final UseCase<T, R, E> useCase, UseCaseCallback<R, E> useCaseCallback) {
        useCase.setUseCaseCallback(new a(useCaseCallback, this));
        this.mUseCaseScheduler.execute(new Runnable() {
            public final void run() {
                useCase.run();
            }
        });
    }

    public <V extends ResponseValue, E> void notifyResponse(V v, UseCaseCallback<V, E> useCaseCallback) {
        this.mUseCaseScheduler.notifyResponse(v, useCaseCallback);
    }

    /* access modifiers changed from: private */
    public <V extends ResponseValue, E> void notifyError(UseCaseCallback<V, E> useCaseCallback, E e) {
        this.mUseCaseScheduler.onError(useCaseCallback, e);
    }

    private <V extends ResponseValue, E> void notifyProgress(UseCaseCallback<V, E> useCaseCallback, E e) {
        this.mUseCaseScheduler.onError(useCaseCallback, e);
    }

    /* access modifiers changed from: private */
    public <V extends ResponseValue, E> void notifyCancle(UseCaseCallback<V, E> useCaseCallback) {
        this.mUseCaseScheduler.onCancel(useCaseCallback);
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (UseCaseHandler.class) {
                try {
                    if (INSTANCE == null) {
                        INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
                    }
                }
            }
        }
        return INSTANCE;
    }
}
