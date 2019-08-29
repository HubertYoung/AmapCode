package com.alipay.mobile.common.rpc.protocol.json;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.mobile.common.rpc.protocol.AbstractSerializer;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class JsonSerializer extends AbstractSerializer {
    public static final String TAG = "JsonSerializer";
    public static final String VERSION = "1.0.0";
    private Object mExtParam;
    private int mId;
    protected String mRequestDataJson;

    public JsonSerializer(int id, String operationType, Object params) {
        super(operationType, params);
        this.mId = id;
    }

    public void setExtParam(Object o) {
        this.mExtParam = o;
    }

    public byte[] packet() {
        List list = new ArrayList();
        prePacket(list);
        buildNameValuePairs(list);
        postPacket(list);
        return nvpairs2Bytes(list);
    }

    public byte[] nvpairs2Bytes(List<BasicNameValuePair> list) {
        String s = URLEncodedUtils.format(list, "utf-8");
        LogCatUtil.printInfo("JsonSerializer", "request = " + (s == null ? "" : URLDecoder.decode(s)));
        return s.getBytes();
    }

    public void buildNameValuePairs(List<BasicNameValuePair> list) {
        if (this.mExtParam != null) {
            list.add(new BasicNameValuePair("extParam", JSON.toJSONString(this.mExtParam)));
        }
        list.add(new BasicNameValuePair(TransportConstants.KEY_OPERATION_TYPE, this.mOperationType));
        if (!TextUtils.isEmpty(this.scene)) {
            list.add(new BasicNameValuePair(H5AppUtil.scene, this.scene));
        }
        LogCatUtil.printInfo("JsonSerializer", "mParams = " + this.mParams + " scene = " + this.scene);
        list.add(buildReqDataNVPair());
    }

    public BasicNameValuePair buildReqDataNVPair() {
        return new BasicNameValuePair("requestData", getRequestDataJson());
    }

    public void prePacket(List<BasicNameValuePair> nameValuePairs) {
    }

    public void postPacket(List<BasicNameValuePair> nameValuePairs) {
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getOperationType() {
        return this.mOperationType;
    }

    public String getRequestDataJson() {
        String jSONString;
        if (!TextUtils.isEmpty(this.mRequestDataJson)) {
            return this.mRequestDataJson;
        }
        if (this.mParams == null) {
            jSONString = "[]";
        } else {
            jSONString = JSON.toJSONString(this.mParams, SerializerFeature.DisableCircularReferenceDetect);
        }
        this.mRequestDataJson = jSONString;
        return this.mRequestDataJson;
    }

    @TargetApi(8)
    public String getRequestDataDigest() {
        try {
            return new String(Base64.encode(MessageDigest.getInstance("MD5").digest(getRequestDataJson().getBytes()), 0));
        } catch (Exception e) {
            LogCatUtil.warn((String) "JsonSerializer", (Throwable) e);
            return "";
        }
    }
}
