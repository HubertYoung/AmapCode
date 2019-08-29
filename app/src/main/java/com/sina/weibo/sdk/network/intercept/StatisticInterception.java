package com.sina.weibo.sdk.network.intercept;

import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.network.IRequestIntercept;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.exception.InterceptException;

public class StatisticInterception implements IRequestIntercept {
    public boolean needIntercept(IRequestParam iRequestParam, Bundle bundle) {
        return true;
    }

    public boolean doIntercept(IRequestParam iRequestParam, Bundle bundle) throws InterceptException {
        TextUtils.isEmpty(bundle.getString("from"));
        bundle.putString("from", "2578195010");
        bundle.putString("wm", "20004_90015");
        return false;
    }
}
