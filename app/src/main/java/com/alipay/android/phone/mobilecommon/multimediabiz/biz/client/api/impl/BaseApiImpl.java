package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.TokenApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

public abstract class BaseApiImpl extends AbstractApiImpl {
    protected TokenApi tokenApi;

    public BaseApiImpl(DjangoClient djangoClient, ConnectionManager<HttpClient> connectionManager) {
        super(djangoClient, connectionManager);
        this.tokenApi = djangoClient.getTokenApi();
    }

    /* access modifiers changed from: protected */
    public <T extends BaseResp> T parseDjangoFileInfoResp(Class<T> responseClass, HttpResponse resp) {
        try {
            if (resp.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(resp.getEntity(), "UTF-8");
                if (DjangoClient.DEBUG) {
                    Logger.D(DjangoClient.LOG_TAG, "parseDjangoFileInfoResp " + responseClass.getSimpleName() + ";content" + content, new Object[0]);
                }
                return (BaseResp) JSON.parseObject(content, responseClass);
            }
            BaseResp response = responseClass.newInstance();
            response.setCode(resp.getStatusLine().getStatusCode());
            response.setMsg("http invoker error!");
            return response;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            BaseResp response2 = responseClass.newInstance();
            response2.setCode(DjangoConstant.DJANGO_400);
            String msg = e.getMessage();
            if (TextUtils.isEmpty(msg)) {
                msg = e.getClass().getSimpleName();
            }
            response2.setMsg(msg + ";content=" + null);
            return response2;
        }
    }

    /* access modifiers changed from: protected */
    public void addParams(HttpRequestBase requestBase, String key, Object val, boolean convert) {
        if (requestBase != null && requestBase.getParams() != null) {
            if ((convert || requestBase.getParams().getParameter(key) == null) && key != null && val != null) {
                requestBase.getParams().setParameter(key, val);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addParams(HttpRequestBase requestBase, String key, Object val) {
        addParams(requestBase, key, val, false);
    }

    /* access modifiers changed from: protected */
    public void addIntParams(HttpRequestBase requestBase, String key, int val) {
        if (requestBase != null && requestBase.getParams() != null && requestBase.getParams().getParameter(key) == null && key != null) {
            requestBase.getParams().setIntParameter(key, val);
        }
    }
}
