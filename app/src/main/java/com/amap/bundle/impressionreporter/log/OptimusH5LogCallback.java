package com.amap.bundle.impressionreporter.log;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;

public class OptimusH5LogCallback extends FalconAosPrepareResponseCallback<vx> {
    private String a;
    private int b;
    private final String c = "OptimusH5LogCallback";

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        vx vxVar = (vx) obj;
        if (vxVar == null || !vxVar.result) {
            StringBuilder sb = new StringBuilder("report failed ,id=");
            sb.append(this.a);
            sb.append(", tag:");
            sb.append(this.b);
            AMapLog.d("OptimusH5LogCallback", sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder("report success,id=");
        sb2.append(this.a);
        AMapLog.d("OptimusH5LogCallback", sb2.toString());
    }

    public OptimusH5LogCallback(String str) {
        this.a = str;
        this.b = 12;
    }

    private static vx b(AosByteResponse aosByteResponse) {
        vx vxVar = new vx();
        try {
            vxVar.parser((byte[]) aosByteResponse.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vxVar;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("report failed ,id=");
        sb.append(this.a);
        sb.append(", tag:");
        sb.append(this.b);
        AMapLog.d("OptimusH5LogCallback", sb.toString());
    }
}
