package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.foot.RouteFootResultData;

/* renamed from: ecp reason: default package */
/* compiled from: AosOnFootRouteResponsor */
public final class ecp extends AbstractAOSParser {
    public RouteFootResultData a;
    private final int b = 4;

    public final String getErrorDesc(int i) {
        return null;
    }

    public ecp(RouteFootResultData routeFootResultData) {
        this.a = routeFootResultData;
    }

    public final void parser(byte[] bArr) {
        try {
            this.errorCode = 0;
            if (bArr == null || bArr.length < 8) {
                this.errorCode = -1;
                this.errorMessage = ERROR_NETWORK;
            } else {
                if ((bArr[0] & 255) + ((bArr[1] & 255) << 8) == 300) {
                    this.result = true;
                } else {
                    this.result = false;
                    this.errorCode = 4;
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.route_not_query_result_try_moment);
                }
                this.errorCode = bArr[16] & 255;
                StringBuilder sb = new StringBuilder("AosOnFootRes parseHeader_ errorCode");
                sb.append(this.errorCode);
                eao.e("tylorvan", sb.toString());
            }
            if (this.a != null && bArr != null && this.errorCode == 0 && !this.a.parseData(bArr)) {
                this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.route_not_query_result_try_moment);
                this.errorCode = -1;
            }
        } catch (Exception e) {
            this.a = null;
            kf.a((Throwable) e);
        }
    }

    public final String a() {
        if (!TextUtils.isEmpty(this.errorMessage)) {
            return this.errorMessage;
        }
        return AMapAppGlobal.getApplication().getString(R.string.route_not_query_suitable_footpath);
    }
}
