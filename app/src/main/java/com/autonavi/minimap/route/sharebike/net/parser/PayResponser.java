package com.autonavi.minimap.route.sharebike.net.parser;

import com.autonavi.minimap.route.sharebike.model.PayInfo;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class PayResponser extends BaseResponser {
    public PayResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        JSONObject parseHeader = parseHeader(bArr);
        PayInfo payInfo = new PayInfo();
        payInfo.result = this.result;
        payInfo.errorCode = this.errorCode;
        if (parseHeader != null) {
            JSONObject optJSONObject = parseHeader.optJSONObject("data");
            if (optJSONObject != null) {
                payInfo.pasString = jsonOptString(optJSONObject, "pasString");
            }
        }
        setResult(payInfo);
    }
}
