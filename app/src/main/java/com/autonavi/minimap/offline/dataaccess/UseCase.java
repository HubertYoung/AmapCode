package com.autonavi.minimap.offline.dataaccess;

import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;

public abstract class UseCase<Q extends RequestValues, P extends ResponseValue, E> {
    private boolean mIsCancle;
    private Q mRequestValues;
    private UseCaseCallback<P, E> mUseCaseCallback;

    public interface RequestValues {
    }

    public interface ResponseValue {
    }

    public interface UseCaseCallback<R, E> {
        void onCancel();

        void onError(E e);

        void onSuccess(R r);
    }

    /* access modifiers changed from: protected */
    public abstract void executeUseCase(Q q);

    public void setRequestValues(Q q) {
        this.mRequestValues = q;
    }

    public Q getRequestValues() {
        return this.mRequestValues;
    }

    public UseCaseCallback<P, E> getUseCaseCallback() {
        return this.mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P, E> useCaseCallback) {
        this.mUseCaseCallback = useCaseCallback;
    }

    public void cancle() {
        this.mIsCancle = true;
    }

    public boolean isCancle() {
        return this.mIsCancle;
    }

    /* access modifiers changed from: 0000 */
    public void run() {
        executeUseCase(this.mRequestValues);
    }
}
