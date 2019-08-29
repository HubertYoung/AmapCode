package com.sina.weibo.sdk.network;

import android.os.Bundle;
import com.sina.weibo.sdk.network.exception.InterceptException;

public interface IRequestIntercept {
    boolean doIntercept(IRequestParam iRequestParam, Bundle bundle) throws InterceptException;

    boolean needIntercept(IRequestParam iRequestParam, Bundle bundle);
}
