package com.amap.bundle.aosservice.response;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;

public interface AosResponseCallback<T extends AosResponse> {
    void onFailure(AosRequest aosRequest, AosResponseException aosResponseException);

    void onSuccess(T t);
}
