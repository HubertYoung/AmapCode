package com.alipay.mobile.framework.service.common.impl;

import android.os.Bundle;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.HttpTransportSevice;
import java.util.concurrent.Future;

public class HttpTransportSeviceImpl extends HttpTransportSevice {
    private HttpManager a = new HttpManager(LauncherApplicationAgent.getInstance().getApplicationContext());

    public HttpTransportSeviceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public Future<Response> execute(Request request) {
        return this.a.execute(request);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle params) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle params) {
        this.a.close();
    }
}
