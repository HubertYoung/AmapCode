package com.alipay.mobile.framework.service.common.impl;

import android.content.Context;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.Transport;
import com.alipay.mobile.common.transport.http.HttpManager;
import java.util.concurrent.Future;

public class MpaasHttpTransportSevice implements Transport {
    private static MpaasHttpTransportSevice mpaasHttpTransportSevice;
    private HttpManager mHttpManager;

    public static final MpaasHttpTransportSevice getInstance(Context context) {
        if (mpaasHttpTransportSevice != null) {
            return mpaasHttpTransportSevice;
        }
        synchronized (MpaasHttpTransportSevice.class) {
            try {
                if (mpaasHttpTransportSevice != null) {
                    MpaasHttpTransportSevice mpaasHttpTransportSevice2 = mpaasHttpTransportSevice;
                    return mpaasHttpTransportSevice2;
                }
                mpaasHttpTransportSevice = new MpaasHttpTransportSevice(context);
                return mpaasHttpTransportSevice;
            }
        }
    }

    public MpaasHttpTransportSevice(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context parameter can't be null ");
        }
        this.mHttpManager = new HttpManager(context);
    }

    public Future<Response> execute(Request request) {
        return this.mHttpManager.execute(request);
    }
}
