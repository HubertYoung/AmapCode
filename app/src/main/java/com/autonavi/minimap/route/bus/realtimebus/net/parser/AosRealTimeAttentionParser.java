package com.autonavi.minimap.route.bus.realtimebus.net.parser;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.request.BaseRequestCallback;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public final class AosRealTimeAttentionParser extends AbstractAOSParser {

    public static final class RealTimeAttentionCallback extends BaseRequestCallback<AosRealTimeAttentionParser> {
        public RealTimeAttentionCallback(Callback<AosRealTimeAttentionParser> callback) {
            super(new AosRealTimeAttentionParser(), callback);
        }
    }

    public final void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        if (this.errorCode == 1) {
            new String(bArr, "utf-8");
        }
    }

    public final String getErrorDesc(int i) {
        switch (i) {
            case 0:
                return AMapAppGlobal.getApplication().getString(R.string.route_unknow_error);
            case 2:
                return AMapAppGlobal.getApplication().getString(R.string.bus_green_ok_error_access);
            case 3:
                return AMapAppGlobal.getApplication().getString(R.string.bus_green_ok_prams_error);
            case 4:
                return AMapAppGlobal.getApplication().getString(R.string.bus_green_ok_error_sign);
            case 5:
                this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.taxi_error_expire);
                return "";
            default:
                return "";
        }
    }
}
