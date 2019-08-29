package com.alipay.mobile.common.rpc.protocol.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.protocol.AbstractDeserializer;
import com.alipay.sdk.util.j;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import java.lang.reflect.Type;

public class JsonDeserializer extends AbstractDeserializer {
    public JsonDeserializer(Type type, byte[] data) {
        super(type, data);
    }

    public Object parser() {
        String message;
        try {
            String res = new String(this.mData);
            logResult(res);
            JSONObject jsonObject = JSON.parseObject(res);
            int resultStatus = jsonObject.getIntValue(j.a);
            String memo = jsonObject.getString(ModulePoi.TIPS);
            if (resultStatus != 1000) {
                RpcException rpcException = new RpcException(Integer.valueOf(resultStatus), memo);
                if (resultStatus == 1002) {
                    JSONObject control = jsonObject.getJSONObject("control");
                    if (control != null) {
                        rpcException.setControl(control.toJSONString());
                    }
                }
                throw rpcException;
            } else if (this.mType == String.class) {
                return jsonObject.getString("result");
            } else {
                return JSON.parseObject(jsonObject.getString("result"), this.mType, new Feature[0]);
            }
        } catch (JSONException e) {
            Integer valueOf = Integer.valueOf(10);
            if (("response  =" + new String(this.mData) + ":" + e) == null) {
                message = "";
            } else {
                message = e.getMessage();
            }
            throw new RpcException(valueOf, message);
        }
    }

    private void logResult(String resData) {
    }
}
