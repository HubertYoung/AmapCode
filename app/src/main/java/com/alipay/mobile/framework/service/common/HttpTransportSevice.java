package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.framework.service.CommonService;

public abstract class HttpTransportSevice extends CommonService implements Transport {
    public HttpTransportSevice() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
