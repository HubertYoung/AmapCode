package com.alipay.mobile.common.rpc.protocol.json;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.rpc.protocol.Serializer;
import com.alipay.mobile.common.rpc.transport.InnerRpcInvokeContext;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.RpcSignUtil;
import com.alipay.mobile.common.transport.utils.RpcSignUtil.SignData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;

public class SignJsonSerializer implements Serializer {
    private InnerRpcInvokeContext invokeContext;
    private Context mContext;
    private JsonSerializer mJsonSerializer;
    private boolean mOnline;
    private String mOutAppKey;
    private SignData mSignData;
    private long mSignTimestamp = -1;

    public SignJsonSerializer(JsonSerializer jsonSerializer, Context context, String outAppKey, boolean online, InnerRpcInvokeContext innerRpcInvokeContext) {
        this.mJsonSerializer = jsonSerializer;
        this.mContext = context;
        this.mOnline = online;
        this.mOutAppKey = outAppKey;
        this.invokeContext = innerRpcInvokeContext;
    }

    public byte[] packet() {
        List nvPairList = new ArrayList();
        this.mJsonSerializer.buildNameValuePairs(nvPairList);
        buildSignNameValuePairs(nvPairList);
        return this.mJsonSerializer.nvpairs2Bytes(nvPairList);
    }

    private void buildSignNameValuePairs(List<BasicNameValuePair> nvPairList) {
        nvPairList.add(new BasicNameValuePair("ts", String.valueOf(getSignTimestamp())));
        sortNVPairList(nvPairList);
        StringBuilder builder = new StringBuilder();
        for (BasicNameValuePair basicNameValuePair : nvPairList) {
            builder.append(basicNameValuePair.getName()).append("=").append(basicNameValuePair.getValue()).append("&");
        }
        this.mSignData = RpcSignUtil.signature(this.mContext, this.mOutAppKey, this.mOnline, builder.deleteCharAt(builder.length() - 1).toString(), MiscUtils.isAlipayGW(this.invokeContext.gwUrl));
        if (this.mSignData != null && !TextUtils.isEmpty(this.mSignData.sign)) {
            nvPairList.add(new BasicNameValuePair("sign", this.mSignData.sign));
        }
    }

    private void sortNVPairList(List<BasicNameValuePair> nvPairList) {
        Collections.sort(nvPairList, new Comparator<BasicNameValuePair>() {
            public int compare(BasicNameValuePair lhs, BasicNameValuePair rhs) {
                try {
                    return lhs.getName().compareTo(rhs.getName());
                } catch (Exception e) {
                    return 0;
                }
            }
        });
    }

    public void setExtParam(Object o) {
    }

    public long getSignTimestamp() {
        if (this.mSignTimestamp != -1) {
            return this.mSignTimestamp;
        }
        this.mSignTimestamp = System.currentTimeMillis();
        return this.mSignTimestamp;
    }

    public String getRequestDataDigest() {
        return this.mJsonSerializer.getRequestDataDigest();
    }

    public SignData getSignData() {
        return this.mSignData;
    }
}
