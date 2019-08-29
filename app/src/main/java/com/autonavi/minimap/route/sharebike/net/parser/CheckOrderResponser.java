package com.autonavi.minimap.route.sharebike.net.parser;

import android.text.TextUtils;
import com.autonavi.minimap.route.sharebike.model.CheckOrder;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckOrderResponser extends BaseResponser {
    public CheckOrderResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (bArr != null && bArr.length != 0) {
            parseHeader(bArr);
            CheckOrder checkOrder = new CheckOrder();
            checkOrder.result = this.result;
            checkOrder.errorCode = this.errorCode;
            if (this.result) {
                JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("data");
                if (optJSONObject != null) {
                    checkOrder.cpSource = jsonOptString(optJSONObject, "cpSource");
                    checkOrder.ridingStatus = optJSONObject.optInt("result", 0);
                    checkOrder.status = optJSONObject.optInt("status", 0);
                    checkOrder.orderId = optJSONObject.optString("orderId");
                    checkOrder.unlockpwd = optJSONObject.optString("unlockpwd");
                    checkOrder.loadingtime = optJSONObject.optString("loadingtime");
                    checkOrder.bikeId = optJSONObject.optString("bikeId");
                    checkOrder.repairurl = optJSONObject.optString("repairurl");
                    if (TextUtils.equals(checkOrder.repairurl, "null")) {
                        ehs.a((String) "share_bike_ofo_repairurl_key", (String) "");
                    } else if (!TextUtils.isEmpty(checkOrder.repairurl)) {
                        ehs.a((String) "share_bike_ofo_repairurl_key", (String) "");
                    } else {
                        ehs.a((String) "share_bike_ofo_repairurl_key", checkOrder.repairurl);
                    }
                }
            }
            setResult(checkOrder);
        }
    }
}
