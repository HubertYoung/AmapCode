package com.autonavi.minimap.offline.dataaccess;

import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;

public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <V extends ResponseValue, E> void notifyResponse(V v, UseCaseCallback<V, E> useCaseCallback);

    <V extends ResponseValue, E> void onCancel(UseCaseCallback<V, E> useCaseCallback);

    <V extends ResponseValue, E> void onError(UseCaseCallback<V, E> useCaseCallback, E e);
}
