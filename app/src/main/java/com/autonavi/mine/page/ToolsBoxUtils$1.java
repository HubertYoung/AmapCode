package com.autonavi.mine.page;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class ToolsBoxUtils$1 extends FalconAosPrepareResponseCallback<ddt> {
    final /* synthetic */ Callback a;
    final /* synthetic */ cgq b;

    public ToolsBoxUtils$1(cgq cgq, Callback callback) {
        this.b = cgq;
        this.a = callback;
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        ddt ddt = (ddt) obj;
        if (this.a != null) {
            if (ddt == null) {
                a((Throwable) new RuntimeException("result is null!"), true);
                return;
            }
            this.a.callback(ddt);
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        a((Throwable) aosResponseException, false);
    }

    private static ddt b(AosByteResponse aosByteResponse) {
        if (aosByteResponse.getResult() == null) {
            return null;
        }
        try {
            JSONObject aosByteResponseToJSONObject = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
            new dds();
            return dds.a(aosByteResponseToJSONObject);
        } catch (JSONException unused) {
            return null;
        }
    }

    private void a(Throwable th, boolean z) {
        if (this.a != null) {
            this.a.error(th, z);
        }
    }
}
