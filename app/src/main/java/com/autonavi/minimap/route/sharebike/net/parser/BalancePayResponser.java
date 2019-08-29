package com.autonavi.minimap.route.sharebike.net.parser;

import com.autonavi.minimap.route.sharebike.model.BalancePay;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class BalancePayResponser extends BaseResponser {
    public BalancePayResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        BalancePay balancePay = new BalancePay();
        balancePay.result = this.result;
        balancePay.errorCode = this.errorCode;
        setResult(balancePay);
    }
}
