package com.autonavi.minimap.offline.JsRequest;

import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;

public final class OfflineStoragePageRequest extends UseCase<EmptyParam, Response, Integer> {

    public static final class EmptyParam implements RequestValues {
    }

    public static final class Response implements ResponseValue {
    }

    /* access modifiers changed from: protected */
    public final void executeUseCase(EmptyParam emptyParam) {
        getUseCaseCallback().onSuccess(new Response());
    }
}
