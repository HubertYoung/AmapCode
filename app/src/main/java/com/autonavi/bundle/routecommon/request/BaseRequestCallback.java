package com.autonavi.bundle.routecommon.request;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class BaseRequestCallback<T extends AbstractAOSParser> extends FalconAosPrepareResponseCallback<T> {
    protected T a;
    protected Callback<T> b;

    public final /* synthetic */ void a(Object obj) {
        AbstractAOSParser abstractAOSParser = (AbstractAOSParser) obj;
        if (this.b != null) {
            this.b.callback(abstractAOSParser);
        }
    }

    public BaseRequestCallback(T t, Callback<T> callback) {
        this.a = t;
        this.b = callback;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public T a(AosByteResponse aosByteResponse) {
        try {
            this.a.parser((byte[]) aosByteResponse.getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return this.a;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.b != null) {
            this.b.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
        }
    }
}
