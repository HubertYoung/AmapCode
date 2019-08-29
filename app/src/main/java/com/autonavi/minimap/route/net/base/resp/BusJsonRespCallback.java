package com.autonavi.minimap.route.net.base.resp;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.autonavi.minimap.route.net.module.AOSRespData;

public abstract class BusJsonRespCallback implements AosResponseCallback<BusJsonResp>, edf<BusJsonResp> {
    /* access modifiers changed from: protected */
    public abstract Class<?> a();

    public boolean a(BusJsonResp busJsonResp, Object obj) {
        return true;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        boolean z;
        final BusJsonResp busJsonResp = (BusJsonResp) aosResponse;
        String str = "";
        try {
            final Object a = busJsonResp.a(a());
            boolean z2 = false;
            if (a != null) {
                if (a instanceof AOSRespData) {
                    String result = ((AOSRespData) a).getResult();
                    z = result != null && "true".equalsIgnoreCase(result);
                    if (!z) {
                        str = ((AOSRespData) a).getMessage();
                    } else {
                        z2 = true;
                    }
                } else {
                    z2 = true;
                    z = false;
                }
                if (z2) {
                    z2 = a(busJsonResp, (Object) a);
                } else {
                    z2 = z;
                }
            }
            if (z2) {
                ebr.a(true).post(new Runnable() {
                    public final void run() {
                        BusJsonRespCallback.this.a(a);
                    }
                });
                return;
            }
            if (TextUtils.isEmpty(str)) {
                str = "process bean failed.";
            }
            a(busJsonResp.getAosRequest(), new AosResponseException(str));
        } catch (Exception e) {
            e.printStackTrace();
            a(busJsonResp.getAosRequest(), new AosResponseException(e.getMessage()));
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(aosRequest, aosResponseException);
    }

    private void a(final AosRequest aosRequest, final AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("postUiFailure ");
        sb.append(aosRequest.getUrl());
        eao.a(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, sb.toString(), aosResponseException);
        ebr.a(true).post(new Runnable() {
            public final void run() {
                BusJsonRespCallback.this.a(aosResponseException);
            }
        });
    }
}
