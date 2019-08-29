package com.autonavi.minimap.route.bus.realtimebus.callback;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.poi.param.BusBaseRequest;
import com.autonavi.minimap.route.bus.busline.model.BusLineSearchException;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class RealTimeBusLineSearchCallback extends FalconAosPrepareResponseCallback<duk> {
    private Callback<duk> a;
    private BusBaseRequest b;

    public final /* synthetic */ void a(Object obj) {
        duk duk = (duk) obj;
        if (duk != null) {
            if (duk.a != null) {
                duk.a.setBusRequest(this.b);
            }
            this.a.callback(duk);
        }
    }

    public RealTimeBusLineSearchCallback(Callback<duk> callback, BusBaseRequest busBaseRequest) {
        this.a = callback;
        this.b = busBaseRequest;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.error(aosResponseException, aosResponseException != null && aosResponseException.isCallbackError);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public duk a(AosByteResponse aosByteResponse) {
        duk duk = new duk();
        try {
            duk.parser((byte[]) aosByteResponse.getResult());
        } catch (UnsupportedEncodingException | JSONException e) {
            kf.a(e);
        } catch (BusLineSearchException e2) {
            if (this.a != null) {
                this.a.error(e2, false);
            }
            return null;
        }
        return duk;
    }
}
