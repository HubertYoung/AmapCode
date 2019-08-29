package com.autonavi.minimap.route.sharebike.net.parser;

import com.amap.bundle.drivecommon.inter.NetConstant;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class RideStateResponser extends BaseResponser {
    public RideStateResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (bArr != null && bArr.length != 0) {
            parseHeader(bArr);
            RideState rideState = new RideState();
            rideState.result = this.result;
            rideState.currentTimestamp = this.timeStamp;
            rideState.errorCode = this.errorCode;
            if (this.result && this.errorCode == 1) {
                JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("data");
                eao.d("zeming", "network data =".concat(String.valueOf(optJSONObject)));
                if (optJSONObject != null) {
                    rideState.status = optJSONObject.optInt("status", -1);
                    rideState.orderId = jsonOptString(optJSONObject, "orderid");
                    rideState.bikeId = jsonOptString(optJSONObject, "bikeid");
                    rideState.cost = (float) optJSONObject.getDouble("cost");
                    rideState.duration = optJSONObject.optInt("duration", 0);
                    rideState.faqUrl = jsonOptString(optJSONObject, "faq_url");
                    rideState.faqDesc = jsonOptString(optJSONObject, "faq_desc");
                    rideState.lockType = optJSONObject.optInt("lock_type", -1);
                    rideState.createTime = optJSONObject.optLong("create_time");
                    rideState.durationSec = optJSONObject.optLong("duration_s");
                    JSONObject optJSONObject2 = optJSONObject.optJSONObject(NetConstant.KEY_MONEY_ACCOUNT);
                    if (optJSONObject2 != null) {
                        rideState.accountToken = jsonOptString(optJSONObject2, "token");
                        rideState.accountUserid = jsonOptString(optJSONObject2, "userid");
                        rideState.accountAppkey = jsonOptString(optJSONObject2, "appkey");
                    }
                    rideState.tag_desc = jsonOptString(optJSONObject, "tag_desc");
                    rideState.fees = jsonOptString(optJSONObject, "fees");
                }
            }
            setResult(rideState);
        }
    }
}
