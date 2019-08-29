package com.autonavi.minimap.route.sharebike.net.parser;

import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class EndBillResponser extends BaseResponser {
    public EndBillResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (bArr != null && bArr.length != 0) {
            parseHeader(bArr);
            EndBill endBill = new EndBill();
            endBill.result = this.result;
            endBill.errorCode = this.errorCode;
            if (this.result) {
                JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("data");
                if (optJSONObject != null) {
                    endBill.distance = optJSONObject.optDouble("distance", 0.0d);
                    endBill.time = optJSONObject.optInt("time", 0);
                    endBill.toast = optJSONObject.optString("toast");
                    endBill.price = optJSONObject.optInt("price", 0);
                }
            }
            setResult(endBill);
        }
    }
}
