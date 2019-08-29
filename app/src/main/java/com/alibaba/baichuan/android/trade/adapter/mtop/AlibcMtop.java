package com.alibaba.baichuan.android.trade.adapter.mtop;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.AlibcContext.Environment;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkClient.NetworkRequestListener;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpConstants;
import com.taobao.tao.remotebusiness.MtopBusiness;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.global.SDKUtils;
import mtopsdk.mtop.intf.Mtop;
import org.json.JSONObject;

public class AlibcMtop implements NetworkClient {
    private boolean a;

    static class a {
        public static AlibcMtop a = new AlibcMtop(null);
    }

    private AlibcMtop() {
    }

    /* synthetic */ AlibcMtop(a aVar) {
        this();
    }

    private MtopRequest a(NetworkRequest networkRequest) {
        if (TextUtils.isEmpty(networkRequest.apiVersion)) {
            networkRequest.apiVersion = "1.0";
        }
        MtopRequest mtopRequest = new MtopRequest();
        mtopRequest.setApiName(networkRequest.apiName);
        mtopRequest.setVersion(networkRequest.apiVersion);
        mtopRequest.setNeedEcode(networkRequest.needLogin);
        mtopRequest.setNeedSession(true);
        if (networkRequest.paramMap != null) {
            JSONObject jsonObject = JSONUtils.getJsonObject(mtopRequest.getData());
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            for (Entry entry : networkRequest.paramMap.entrySet()) {
                if (entry.getValue() != null) {
                    try {
                        jsonObject.put((String) entry.getKey(), ((Serializable) entry.getValue()).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mtopRequest.setData(jsonObject.toString());
        }
        return mtopRequest;
    }

    /* access modifiers changed from: private */
    public void a(MtopResponse mtopResponse) {
        if (mtopResponse.isApiSuccess()) {
            AlibcLogger.d("AlibcMtop", "网络请求成功");
        } else if (mtopResponse.isSessionInvalid()) {
            StringBuilder sb = new StringBuilder("session 失效， do autologin or login business msg = ");
            sb.append(mtopResponse.getRetMsg());
            AlibcLogger.e("AlibcMtop", sb.toString());
        } else if (mtopResponse.isSystemError() || mtopResponse.isNetworkError() || mtopResponse.isExpiredRequest() || mtopResponse.is41XResult() || mtopResponse.isApiLockedResult() || mtopResponse.isMtopSdkError()) {
            StringBuilder sb2 = new StringBuilder("系统错误，网络错误，防刷，防雪崩 msg =");
            sb2.append(mtopResponse.getRetMsg());
            AlibcLogger.e("AlibcMtop", sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder("业务错误 msg =");
            sb3.append(mtopResponse.getRetMsg());
            AlibcLogger.e("AlibcMtop", sb3.toString());
        }
    }

    private void a(MtopResponse mtopResponse, String str, String str2) {
        String str3 = mtopResponse.isSessionInvalid() ? "session 失效， do autologin or login business" : (mtopResponse.isSystemError() || mtopResponse.isNetworkError() || mtopResponse.isExpiredRequest() || mtopResponse.is41XResult() || mtopResponse.isApiLockedResult() || mtopResponse.isMtopSdkError()) ? "系统错误，网络错误，防刷，防雪崩 " : "业务错误 ";
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(str2);
        UserTrackerCompoment.sendUseabilityFailureForOtherErrmsg(UserTrackerConstants.U_INVOKE, sb.toString(), UserTrackerConstants.ERRCODE_MTOP.concat(String.valueOf(str)));
    }

    /* access modifiers changed from: private */
    public NetworkResponse b(MtopResponse mtopResponse) {
        NetworkResponse networkResponse = new NetworkResponse();
        if (mtopResponse == null) {
            return networkResponse;
        }
        networkResponse.byteData = mtopResponse.getBytedata();
        StringBuilder sb = new StringBuilder();
        sb.append(mtopResponse.getResponseCode());
        networkResponse.httpCode = sb.toString();
        networkResponse.errorCode = mtopResponse.getRetCode();
        networkResponse.errorMsg = mtopResponse.getRetMsg();
        networkResponse.isSuccess = mtopResponse.isApiSuccess();
        if (mtopResponse.getDataJsonObject() != null) {
            String jSONObject = mtopResponse.getDataJsonObject().toString();
            networkResponse.data = (Map) JSONUtils.parseStringValue(jSONObject, Map.class);
            networkResponse.jsonData = jSONObject;
        }
        return networkResponse;
    }

    public static AlibcMtop getInstance() {
        return a.a;
    }

    public synchronized void init() {
        Mtop a2;
        EnvModeEnum envModeEnum;
        if (!this.a) {
            this.a = true;
            TBSdkLog.a();
            TBSdkLog.a(AlibcContext.isDebuggable());
            ffh.a(0, 0);
            ffh.a(AlibcContext.sdkVersion);
            if (AlibcContext.environment == Environment.TEST) {
                a2 = Mtop.a(AlibcContext.context);
                envModeEnum = EnvModeEnum.TEST;
            } else if (AlibcContext.environment == Environment.PRE) {
                a2 = Mtop.a(AlibcContext.context);
                envModeEnum = EnvModeEnum.PREPARE;
            } else {
                a2 = Mtop.a(AlibcContext.context);
                envModeEnum = EnvModeEnum.ONLINE;
            }
            a2.a(envModeEnum);
            Mtop.a(AlibcContext.context, AlibcConfig.getInstance().getWebTTID());
            SDKUtils.registerTtid(AlibcConfig.getInstance().getWebTTID());
            AlibcLogger.d("AlibcMtop", "mtop init complete");
        }
    }

    public NetworkResponse sendRequest(NetworkRequest networkRequest) {
        if (networkRequest == null) {
            return null;
        }
        MtopBusiness build = MtopBusiness.build(a(networkRequest), AlibcConfig.getInstance().getWebTTID());
        if (networkRequest.needWua) {
            build.useWua();
        }
        if (networkRequest.needAuth && !AlibcContext.isVip) {
            build.setNeedAuth(networkRequest.authParams, true);
        }
        if (networkRequest.isPost) {
            build.reqMethod(MethodEnum.POST);
        }
        MtopResponse syncRequest = build.syncRequest();
        a(syncRequest);
        if (!syncRequest.isApiSuccess()) {
            String retCode = syncRequest.getRetCode();
            StringBuilder sb = new StringBuilder("errmsg = ");
            sb.append(syncRequest.getRetMsg());
            sb.append(" ,api = ");
            sb.append(syncRequest.getApi());
            a(syncRequest, retCode, sb.toString());
        } else {
            UserTrackerCompoment.sendUseabilitySuccess(UserTrackerConstants.U_INVOKE);
        }
        return b(syncRequest);
    }

    public boolean sendRequest(NetworkRequestListener networkRequestListener, NetworkRequest networkRequest) {
        if (networkRequest == null) {
            if (networkRequestListener != null) {
                networkRequestListener.onError(-1, null);
            }
            return false;
        }
        MtopBusiness build = MtopBusiness.build(a(networkRequest), AlibcConfig.getInstance().getWebTTID());
        if (networkRequest.needWua) {
            build.useWua();
        }
        if (networkRequest.needAuth && !AlibcContext.isVip) {
            build.setNeedAuth(networkRequest.authParams, true);
        }
        if (networkRequest.isPost) {
            build.reqMethod(MethodEnum.POST);
        }
        if (networkRequest.extHeaders != null && networkRequest.extHeaders.size() > 0) {
            build.headers(networkRequest.extHeaders);
        }
        build.setSocketTimeoutMilliSecond((int) HttpConstants.CONNECTION_TIME_OUT);
        build.setConnectionTimeoutMilliSecond((int) HttpConstants.CONNECTION_TIME_OUT);
        build.registeListener(new a(this, networkRequestListener, networkRequest)).asyncRequest();
        return true;
    }
}
