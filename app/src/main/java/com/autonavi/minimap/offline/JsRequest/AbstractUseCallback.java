package com.autonavi.minimap.offline.JsRequest;

import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;

public abstract class AbstractUseCallback<R, E> implements UseCaseCallback<R, E> {
    public void onCancel() {
    }

    public void onError(E e) {
    }

    public void onSuccess(R r) {
    }
}
