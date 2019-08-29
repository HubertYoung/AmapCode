package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public abstract class ThirdpartyRpcService extends RpcService {
    public ThirdpartyRpcService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
