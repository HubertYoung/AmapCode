package com.autonavi.miniapp.plugin.mtop;

import android.content.Context;
import android.os.Handler;
import android.util.Pair;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import java.util.Map;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.domain.MethodEnum;
import mtopsdk.mtop.domain.ProtocolEnum;
import org.json.JSONObject;

public abstract class MtopService extends ExternalService {
    public abstract void MtopInit(Context context, int i, int i2);

    public abstract <InputDO extends ffb, OutputDO extends BaseOutDo> boolean asyncRequest(InputDO inputdo, Class<OutputDO> cls, MtopAsyncRequestCallback<OutputDO> mtopAsyncRequestCallback);

    public abstract <InputDO extends ffb, OutputDO extends BaseOutDo> boolean asyncRequest(InputDO inputdo, Class<OutputDO> cls, MtopAsyncRequestCallback<OutputDO> mtopAsyncRequestCallback, String str, ProtocolEnum protocolEnum, boolean z, MethodEnum methodEnum, int i, Map<String, String> map);

    public abstract SsoLoginInfo getSsoLoginInfo();

    public abstract boolean isHasSession();

    public abstract void logout();

    public abstract void preConnect();

    public abstract void registerSession(String str, String str2, String str3);

    public abstract void registerSessionInfo();

    public abstract void registerSessionInfo(RegisterSessionListener registerSessionListener);

    public abstract void sendMtopJsapiRequest(Map<String, Object> map, IRemoteBaseListener iRemoteBaseListener);

    public abstract void setHasSession(boolean z);

    public abstract void setUseAlipaySession(boolean z);

    public abstract void switchEnvMode(EnvModeEnum envModeEnum);

    public abstract <InputDO extends ffb, OutputDO extends BaseOutDo> MtopResponseWrapper<OutputDO> syncRequest(InputDO inputdo, Class<OutputDO> cls);

    public abstract <InputDO extends ffb, OutputDO extends BaseOutDo> MtopResponseWrapper<OutputDO> syncRequest(InputDO inputdo, Class<OutputDO> cls, String str, ProtocolEnum protocolEnum, boolean z, MethodEnum methodEnum, int i, Map<String, String> map);

    public abstract JSONObject syncRequest(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5);

    public abstract JSONObject syncRequest(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5, String str6, MethodEnum methodEnum);

    public abstract JSONObject syncRequest(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5, MethodEnum methodEnum);

    public abstract JSONObject syncRequest(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4, com.alibaba.fastjson.JSONObject jSONObject, String str5, String str6, MethodEnum methodEnum);

    public abstract JSONObject syncRequest(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4, String str5, String str6, MethodEnum methodEnum);

    public abstract Pair<Handler, com.alibaba.fastjson.JSONObject> syncRequestFastJson(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, String str3, String str4, com.alibaba.fastjson.JSONObject jSONObject, String str5, String str6, String str7, String str8, MethodEnum methodEnum);
}
