package com.autonavi.minimap.route.sharebike.net.parser;

import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderDetailResonser extends BaseResponser {
    public OrderDetailResonser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (bArr != null && bArr.length != 0) {
            parseHeader(bArr);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.result = this.result;
            orderDetail.errorCode = this.errorCode;
            if (this.result) {
                JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("data");
                if (optJSONObject != null) {
                    orderDetail.orderid = jsonOptString(optJSONObject, "orderid");
                    orderDetail.bikeid = jsonOptString(optJSONObject, "bikeid");
                    orderDetail.status = jsonOptString(optJSONObject, "status");
                    orderDetail.ridingTime = jsonOptString(optJSONObject, "ridingTime");
                    orderDetail.totalFee = jsonOptString(optJSONObject, "totalFee");
                    orderDetail.isPay = jsonOptString(optJSONObject, "isPay");
                    orderDetail.alipay = jsonOptString(optJSONObject, BehavorReporter.PROVIDE_BY_ALIPAY);
                    orderDetail.pasString = jsonOptString(optJSONObject, "pasString");
                    orderDetail.fees = jsonOptString(optJSONObject, "fees");
                    orderDetail.tagDesc = jsonOptString(optJSONObject, "tagDesc");
                    JSONArray optJSONArray = optJSONObject.optJSONArray("costSection");
                    if (optJSONArray != null) {
                        orderDetail.costSection = new ArrayList();
                        for (int i = 0; !optJSONArray.isNull(i); i++) {
                            JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                            if (optJSONObject2 != null) {
                                egq egq = new egq();
                                egq.b = jsonOptString(optJSONObject2, "costAmount");
                                egq.a = jsonOptString(optJSONObject2, "costBucket");
                                egq.c = jsonOptString(optJSONObject2, "costDetail");
                                orderDetail.costSection.add(egq);
                            }
                        }
                    }
                }
            }
            setResult(orderDetail);
        }
    }
}
