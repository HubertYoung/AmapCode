package com.alipay.android.phone.inside.offlinecode.rpc;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.util.PackageUtils;
import com.alipay.android.phone.inside.common.util.RandamUtil;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.rpc.model.ScardCenterRes;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.AlipayInsideRPCRequestInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.request.base.BaseRPCRequestInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardIdentityVerifyRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardDataRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardDetailRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardListRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryCardSceneRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQueryScriptRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardQuerySubSceneRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.request.card.VirtualCardUploadDataRequest;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.BaseRPCResponseInfo;
import com.alipay.android.phone.inside.offlinecode.rpc.response.base.VirtualCardSubScene;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardIdentityVerifyResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardDataResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardDetailResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardListResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryCardSceneResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQueryScriptResponse;
import com.alipay.android.phone.inside.offlinecode.rpc.response.card.VirtualCardQuerySubSceneResponse;
import com.alipay.android.phone.inside.offlinecode.storage.CardDataStorage;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScardCenterRpcProvider {
    public static final String CLIENT_SDK_VERSION = "0.0.5";
    static final String CODE_FAILED = "FAILED";
    static final String CODE_NOCARD = "NOCARD";
    static final String CODE_SUCESS = "SUCCESS";
    static final String CODE_UNAUTH = "UNAUTH";
    public static final String REQ_VALUE_ADCODE = "";
    public static final String REQ_VALUE_SCENECODE = "TRANSIT";
    public static final String REQ_VALUE_SOURCE = "ALIPAY_INSIDE";
    public static final String REQ_VALUE_SUBSCENECATEGORY = "city";
    public static final String REQ_VALUE_SUBSCENECODE = "";
    public static final String REQ_VALUE_SYSTEMTYPE = "android";
    public static final String RES_VALUE_UNAUTH = "UNAUTHORIZED";

    public ScardCenterRes queryCardDetail(Context context, String str, String str2) throws Exception {
        ScardCenterRpcFacade scardCenterRpcFacade = (ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class);
        VirtualCardQueryCardDetailRequest virtualCardQueryCardDetailRequest = new VirtualCardQueryCardDetailRequest();
        virtualCardQueryCardDetailRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardQueryCardDetailRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardQueryCardDetailRequest.bizId = RandamUtil.a();
        virtualCardQueryCardDetailRequest.sceneCode = REQ_VALUE_SCENECODE;
        virtualCardQueryCardDetailRequest.subSceneCode = "";
        virtualCardQueryCardDetailRequest.cardType = str;
        virtualCardQueryCardDetailRequest.cardNo = str2;
        virtualCardQueryCardDetailRequest.source = REQ_VALUE_SOURCE;
        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
            virtualCardQueryCardDetailRequest.autoDecide = false;
        } else {
            virtualCardQueryCardDetailRequest.autoDecide = true;
        }
        return packageResponseCardDetail(scardCenterRpcFacade.queryCardDetail(virtualCardQueryCardDetailRequest));
    }

    private boolean syncQuery(Context context, String str, String str2) {
        return CardDataStorage.getInstance().getCardData(context, CardDataStorage.getCardIdentify(str, str2)) == null;
    }

    public ScardCenterRes queryCardData(Context context, String str, String str2) throws Exception {
        VirtualCardQueryCardDataRequest virtualCardQueryCardDataRequest = new VirtualCardQueryCardDataRequest();
        virtualCardQueryCardDataRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardQueryCardDataRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardQueryCardDataRequest.bizId = RandamUtil.a();
        virtualCardQueryCardDataRequest.sceneCode = REQ_VALUE_SCENECODE;
        virtualCardQueryCardDataRequest.subSceneCode = "";
        virtualCardQueryCardDataRequest.cardType = str;
        virtualCardQueryCardDataRequest.cardNo = str2;
        virtualCardQueryCardDataRequest.source = REQ_VALUE_SOURCE;
        virtualCardQueryCardDataRequest.syncQuery = syncQuery(context, str, str2);
        virtualCardQueryCardDataRequest.channelType = REQ_VALUE_SOURCE;
        virtualCardQueryCardDataRequest.supportOfflineCrypto = true;
        virtualCardQueryCardDataRequest.clientGencodeSDKVersion = "0.0.5";
        return packageResponseCardData(((ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class)).queryCardData(virtualCardQueryCardDataRequest), (System.currentTimeMillis() - System.currentTimeMillis()) / 1000);
    }

    public ScardCenterRes queryCardList(Context context, String str, String str2) throws Exception {
        ScardCenterRpcFacade scardCenterRpcFacade = (ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class);
        VirtualCardQueryCardListRequest virtualCardQueryCardListRequest = new VirtualCardQueryCardListRequest();
        virtualCardQueryCardListRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardQueryCardListRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardQueryCardListRequest.bizId = RandamUtil.a();
        virtualCardQueryCardListRequest.sceneCode = REQ_VALUE_SCENECODE;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        virtualCardQueryCardListRequest.subSceneCode = str;
        virtualCardQueryCardListRequest.cardType = null;
        virtualCardQueryCardListRequest.cardNo = null;
        virtualCardQueryCardListRequest.source = REQ_VALUE_SOURCE;
        virtualCardQueryCardListRequest.listType = str2;
        return packageResponseCardList(scardCenterRpcFacade.queryCardList(virtualCardQueryCardListRequest));
    }

    public ScardCenterRes queryCardScene(Context context) throws Exception {
        VirtualCardQueryCardSceneRequest virtualCardQueryCardSceneRequest = new VirtualCardQueryCardSceneRequest();
        virtualCardQueryCardSceneRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardQueryCardSceneRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardQueryCardSceneRequest.bizId = RandamUtil.a();
        virtualCardQueryCardSceneRequest.sceneCode = REQ_VALUE_SCENECODE;
        virtualCardQueryCardSceneRequest.subSceneCode = "";
        virtualCardQueryCardSceneRequest.cardType = "";
        virtualCardQueryCardSceneRequest.cardNo = "";
        virtualCardQueryCardSceneRequest.source = REQ_VALUE_SOURCE;
        return packageResponseCardScene(((ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class)).queryCardScene(virtualCardQueryCardSceneRequest));
    }

    public ScardCenterRes querySubScene(Context context) throws Exception {
        VirtualCardQuerySubSceneRequest virtualCardQuerySubSceneRequest = new VirtualCardQuerySubSceneRequest();
        virtualCardQuerySubSceneRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardQuerySubSceneRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardQuerySubSceneRequest.bizId = RandamUtil.a();
        virtualCardQuerySubSceneRequest.sceneCode = REQ_VALUE_SCENECODE;
        virtualCardQuerySubSceneRequest.subSceneCode = "";
        virtualCardQuerySubSceneRequest.cardType = "";
        virtualCardQuerySubSceneRequest.cardNo = "";
        virtualCardQuerySubSceneRequest.source = REQ_VALUE_SOURCE;
        virtualCardQuerySubSceneRequest.subSceneCategory = "city";
        return packageResSubScene(((ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class)).querySubScene(virtualCardQuerySubSceneRequest));
    }

    private ScardCenterRes packageResSubScene(VirtualCardQuerySubSceneResponse virtualCardQuerySubSceneResponse) throws Exception {
        JSONObject jSONObject;
        String str;
        JSONArray jSONArray = new JSONArray();
        if (isSuccessForSubScene(virtualCardQuerySubSceneResponse)) {
            str = "SUCCESS";
            List<VirtualCardSubScene> list = virtualCardQuerySubSceneResponse.subScenes;
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    jSONArray.put(list.get(i).serializeJSON());
                }
            }
            jSONObject = null;
        } else {
            str = getFailedCode(virtualCardQuerySubSceneResponse.baseRPCResponseInfo);
            jSONObject = getFailedIndicator(virtualCardQuerySubSceneResponse.baseRPCResponseInfo);
        }
        ScardCenterRes scardCenterRes = new ScardCenterRes();
        scardCenterRes.cleanCard = isCleanCard(virtualCardQuerySubSceneResponse.baseRPCResponseInfo);
        scardCenterRes.code = str;
        scardCenterRes.indicator = jSONObject;
        scardCenterRes.result = jSONArray;
        return scardCenterRes;
    }

    private boolean isSuccessForSubScene(VirtualCardQuerySubSceneResponse virtualCardQuerySubSceneResponse) {
        return virtualCardQuerySubSceneResponse.baseRPCResponseInfo != null && virtualCardQuerySubSceneResponse.baseRPCResponseInfo.success;
    }

    public ScardCenterRes verifyRequest(Context context, String str, boolean z, String str2) throws Exception {
        VirtualCardIdentityVerifyRequest virtualCardIdentityVerifyRequest = new VirtualCardIdentityVerifyRequest();
        virtualCardIdentityVerifyRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardIdentityVerifyRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardIdentityVerifyRequest.bizId = str;
        virtualCardIdentityVerifyRequest.sceneCode = REQ_VALUE_SCENECODE;
        virtualCardIdentityVerifyRequest.subSceneCode = "";
        virtualCardIdentityVerifyRequest.cardType = "";
        virtualCardIdentityVerifyRequest.cardNo = "";
        virtualCardIdentityVerifyRequest.source = REQ_VALUE_SOURCE;
        virtualCardIdentityVerifyRequest.identityVerified = z;
        virtualCardIdentityVerifyRequest.verifyId = str2;
        return packageResponseIdentityVerify(((ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class)).identityVerify(virtualCardIdentityVerifyRequest));
    }

    private boolean isIdentitySuccess(VirtualCardIdentityVerifyResponse virtualCardIdentityVerifyResponse) {
        return virtualCardIdentityVerifyResponse.baseRPCResponseInfo != null && virtualCardIdentityVerifyResponse.baseRPCResponseInfo.success;
    }

    private ScardCenterRes packageResponseIdentityVerify(VirtualCardIdentityVerifyResponse virtualCardIdentityVerifyResponse) throws Exception {
        JSONObject jSONObject;
        String str;
        JSONObject jSONObject2 = new JSONObject();
        if (isIdentitySuccess(virtualCardIdentityVerifyResponse)) {
            str = "SUCCESS";
            jSONObject2.put("bizId", virtualCardIdentityVerifyResponse.bizId);
            jSONObject2.put(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, virtualCardIdentityVerifyResponse.verifyId);
            jSONObject = null;
        } else {
            str = getFailedCode(virtualCardIdentityVerifyResponse.baseRPCResponseInfo);
            jSONObject = getFailedIndicator(virtualCardIdentityVerifyResponse.baseRPCResponseInfo);
        }
        ScardCenterRes scardCenterRes = new ScardCenterRes();
        scardCenterRes.cleanCard = false;
        scardCenterRes.code = str;
        scardCenterRes.indicator = jSONObject;
        scardCenterRes.result = jSONObject2;
        return scardCenterRes;
    }

    private boolean isSuccessForCardDetail(VirtualCardQueryCardDetailResponse virtualCardQueryCardDetailResponse) {
        return (virtualCardQueryCardDetailResponse.virtualCardInfo == null || virtualCardQueryCardDetailResponse.virtualCardInfo.cardModels == null || virtualCardQueryCardDetailResponse.virtualCardInfo.cardModels.size() <= 0) ? false : true;
    }

    private boolean isNoCardForCardDetail(VirtualCardQueryCardDetailResponse virtualCardQueryCardDetailResponse) {
        return (virtualCardQueryCardDetailResponse.baseRPCResponseInfo == null || virtualCardQueryCardDetailResponse.baseRPCResponseInfo.errorIndicator == null) && (virtualCardQueryCardDetailResponse.virtualCardInfo == null || virtualCardQueryCardDetailResponse.virtualCardInfo.cardModels == null || virtualCardQueryCardDetailResponse.virtualCardInfo.cardModels.size() <= 0);
    }

    private boolean isSuccessForCardData(VirtualCardQueryCardDataResponse virtualCardQueryCardDataResponse) {
        return (virtualCardQueryCardDataResponse == null || virtualCardQueryCardDataResponse.offlineDataInfo == null) ? false : true;
    }

    private boolean isNoCardForCardData(VirtualCardQueryCardDataResponse virtualCardQueryCardDataResponse) {
        return virtualCardQueryCardDataResponse.offlineDataInfo == null && virtualCardQueryCardDataResponse.baseRPCResponseInfo != null && virtualCardQueryCardDataResponse.baseRPCResponseInfo.success;
    }

    private ScardCenterRes packageResponseCardDetail(VirtualCardQueryCardDetailResponse virtualCardQueryCardDetailResponse) throws Exception {
        String str;
        if (virtualCardQueryCardDetailResponse == null) {
            return new ScardCenterRes();
        }
        Object obj = null;
        boolean isCleanCard = isCleanCard(virtualCardQueryCardDetailResponse.baseRPCResponseInfo);
        JSONObject jSONObject = new JSONObject();
        if (isSuccessForCardDetail(virtualCardQueryCardDetailResponse)) {
            str = "SUCCESS";
            jSONObject = virtualCardQueryCardDetailResponse.virtualCardInfo.serializeJson();
        } else if (isNoCardForCardDetail(virtualCardQueryCardDetailResponse)) {
            str = "NOCARD";
            if (virtualCardQueryCardDetailResponse.virtualCardInfo != null) {
                jSONObject = virtualCardQueryCardDetailResponse.virtualCardInfo.serializeJson();
            }
        } else {
            str = getFailedCode(virtualCardQueryCardDetailResponse.baseRPCResponseInfo);
            obj = getFailedIndicator(virtualCardQueryCardDetailResponse.baseRPCResponseInfo);
        }
        ScardCenterRes scardCenterRes = new ScardCenterRes();
        scardCenterRes.cleanCard = isCleanCard;
        scardCenterRes.code = str;
        scardCenterRes.indicator = obj;
        scardCenterRes.result = jSONObject;
        return scardCenterRes;
    }

    private boolean isCleanCard(BaseRPCResponseInfo baseRPCResponseInfo) {
        if (baseRPCResponseInfo == null || baseRPCResponseInfo.errorIndicator == null) {
            return false;
        }
        return baseRPCResponseInfo.errorIndicator.cleanCard;
    }

    private ScardCenterRes packageResponseCardData(VirtualCardQueryCardDataResponse virtualCardQueryCardDataResponse, long j) throws Exception {
        String str;
        if (virtualCardQueryCardDataResponse == null) {
            return new ScardCenterRes();
        }
        JSONObject jSONObject = null;
        boolean isCleanCard = isCleanCard(virtualCardQueryCardDataResponse.baseRPCResponseInfo);
        JSONObject jSONObject2 = new JSONObject();
        if (isSuccessForCardData(virtualCardQueryCardDataResponse)) {
            str = "SUCCESS";
            jSONObject2.put("data", virtualCardQueryCardDataResponse.offlineDataInfo.serializeJSON(j));
            if (virtualCardQueryCardDataResponse.offlineDataInfo.config != null) {
                JSONObject jSONObject3 = new JSONObject();
                for (String next : virtualCardQueryCardDataResponse.offlineDataInfo.config.keySet()) {
                    jSONObject3.put(next, virtualCardQueryCardDataResponse.offlineDataInfo.config.get(next));
                }
                jSONObject2.put("config", jSONObject3);
            }
            jSONObject2.put("isFirstTime", virtualCardQueryCardDataResponse.isFirstTime);
            jSONObject2.put("enableOnsitePay", virtualCardQueryCardDataResponse.enableOnsitePay);
        } else if (isNoCardForCardData(virtualCardQueryCardDataResponse)) {
            str = "NOCARD";
        } else {
            str = getFailedCode(virtualCardQueryCardDataResponse.baseRPCResponseInfo);
            jSONObject = getFailedIndicator(virtualCardQueryCardDataResponse.baseRPCResponseInfo);
        }
        ScardCenterRes scardCenterRes = new ScardCenterRes();
        scardCenterRes.cleanCard = isCleanCard;
        scardCenterRes.code = str;
        scardCenterRes.indicator = jSONObject;
        scardCenterRes.result = jSONObject2;
        return scardCenterRes;
    }

    private ScardCenterRes packageResponseCardList(VirtualCardQueryCardListResponse virtualCardQueryCardListResponse) throws Exception {
        String str;
        if (virtualCardQueryCardListResponse == null) {
            return new ScardCenterRes();
        }
        JSONObject jSONObject = null;
        JSONArray jSONArray = new JSONArray();
        if (virtualCardQueryCardListResponse.baseRPCResponseInfo == null || !virtualCardQueryCardListResponse.baseRPCResponseInfo.success) {
            str = getFailedCode(virtualCardQueryCardListResponse.baseRPCResponseInfo);
            jSONObject = getFailedIndicator(virtualCardQueryCardListResponse.baseRPCResponseInfo);
        } else {
            str = "SUCCESS";
            if (virtualCardQueryCardListResponse.virtualCardInfoList != null) {
                for (int i = 0; i < virtualCardQueryCardListResponse.virtualCardInfoList.size(); i++) {
                    jSONArray.put(virtualCardQueryCardListResponse.virtualCardInfoList.get(i).serializeJson());
                }
            }
        }
        ScardCenterRes scardCenterRes = new ScardCenterRes();
        scardCenterRes.cleanCard = isCleanCard(virtualCardQueryCardListResponse.baseRPCResponseInfo);
        scardCenterRes.code = str;
        scardCenterRes.indicator = jSONObject;
        scardCenterRes.result = jSONArray;
        return scardCenterRes;
    }

    private ScardCenterRes packageResponseCardScene(VirtualCardQueryCardSceneResponse virtualCardQueryCardSceneResponse) throws Exception {
        JSONArray jSONArray = new JSONArray();
        if (virtualCardQueryCardSceneResponse.baseRPCResponseInfo == null || !virtualCardQueryCardSceneResponse.baseRPCResponseInfo.success) {
            getFailedCode(virtualCardQueryCardSceneResponse.baseRPCResponseInfo);
            getFailedIndicator(virtualCardQueryCardSceneResponse.baseRPCResponseInfo);
        } else if (virtualCardQueryCardSceneResponse.cardScenes != null) {
            for (int i = 0; i < virtualCardQueryCardSceneResponse.cardScenes.size(); i++) {
                jSONArray.put(virtualCardQueryCardSceneResponse.cardScenes.get(i).serializeJson());
            }
        }
        return new ScardCenterRes();
    }

    private String getFailedCode(BaseRPCResponseInfo baseRPCResponseInfo) {
        if (baseRPCResponseInfo == null || baseRPCResponseInfo.errorIndicator == null) {
            return "";
        }
        Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "ScardCenterFailCode");
        a.g = baseRPCResponseInfo.errorIndicator.errorCode;
        a.h = String.valueOf(baseRPCResponseInfo.success);
        return TextUtils.equals(baseRPCResponseInfo.errorIndicator.errorCode, RES_VALUE_UNAUTH) ? "UNAUTH" : "FAILED";
    }

    private JSONObject getFailedIndicator(BaseRPCResponseInfo baseRPCResponseInfo) {
        if (baseRPCResponseInfo == null || baseRPCResponseInfo.errorIndicator == null) {
            return null;
        }
        return baseRPCResponseInfo.errorIndicator.serializeJSON();
    }

    private AlipayInsideRPCRequestInfo getInsideRequestInfo(Context context) {
        AlipayInsideRPCRequestInfo alipayInsideRPCRequestInfo = new AlipayInsideRPCRequestInfo();
        alipayInsideRPCRequestInfo.tid = RunningConfig.a(false);
        alipayInsideRPCRequestInfo.insideModel = StaticConfig.b();
        alipayInsideRPCRequestInfo.version = StaticConfig.c();
        return alipayInsideRPCRequestInfo;
    }

    private BaseRPCRequestInfo getBaseRequestInfo(Context context, Map<String, String> map) {
        BaseRPCRequestInfo baseRPCRequestInfo = new BaseRPCRequestInfo();
        baseRPCRequestInfo.time = System.currentTimeMillis();
        baseRPCRequestInfo.isRoot = false;
        baseRPCRequestInfo.adCode = "";
        baseRPCRequestInfo.systemType = "android";
        baseRPCRequestInfo.clientVersion = PackageUtils.a(context);
        StringBuilder sb = new StringBuilder("inside:");
        sb.append(StaticConfig.c());
        baseRPCRequestInfo.packageVersion = sb.toString();
        baseRPCRequestInfo.extInfo = new HashMap();
        if (map != null) {
            baseRPCRequestInfo.extInfo.putAll(map);
        }
        baseRPCRequestInfo.apdidToken = RunningConfig.g();
        return baseRPCRequestInfo;
    }

    public ScardCenterRes queryScript(Context context, String str, String str2) throws Exception {
        VirtualCardQueryScriptRequest virtualCardQueryScriptRequest = new VirtualCardQueryScriptRequest();
        virtualCardQueryScriptRequest.bizId = RandamUtil.a();
        virtualCardQueryScriptRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardQueryScriptRequest.scriptName = str2;
        virtualCardQueryScriptRequest.scriptType = str;
        return packageResponseScript(((ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class)).queryScript(virtualCardQueryScriptRequest));
    }

    private ScardCenterRes packageResponseScript(VirtualCardQueryScriptResponse virtualCardQueryScriptResponse) throws Exception {
        String str;
        if (virtualCardQueryScriptResponse == null) {
            return new ScardCenterRes();
        }
        JSONObject jSONObject = null;
        boolean isCleanCard = isCleanCard(virtualCardQueryScriptResponse.baseRPCResponseInfo);
        JSONObject jSONObject2 = new JSONObject();
        if (virtualCardQueryScriptResponse.baseRPCResponseInfo.success) {
            str = "SUCCESS";
            jSONObject2.put("scriptCode", virtualCardQueryScriptResponse.scriptCode);
        } else {
            str = getFailedCode(virtualCardQueryScriptResponse.baseRPCResponseInfo);
            jSONObject = getFailedIndicator(virtualCardQueryScriptResponse.baseRPCResponseInfo);
        }
        ScardCenterRes scardCenterRes = new ScardCenterRes();
        scardCenterRes.cleanCard = isCleanCard;
        scardCenterRes.code = str;
        scardCenterRes.indicator = jSONObject;
        scardCenterRes.result = jSONObject2;
        return scardCenterRes;
    }

    public void uploadData(Context context, String str, String str2, String str3, String str4) {
        ScardCenterRpcFacade scardCenterRpcFacade = (ScardCenterRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ScardCenterRpcFacade.class);
        VirtualCardUploadDataRequest virtualCardUploadDataRequest = new VirtualCardUploadDataRequest();
        virtualCardUploadDataRequest.baseRPCRequestInfo = getBaseRequestInfo(context, null);
        virtualCardUploadDataRequest.alipayInsideRPCRequestInfo = getInsideRequestInfo(context);
        virtualCardUploadDataRequest.bizId = RandamUtil.a();
        virtualCardUploadDataRequest.sceneCode = REQ_VALUE_SCENECODE;
        virtualCardUploadDataRequest.subSceneCode = "";
        virtualCardUploadDataRequest.cardType = str3;
        virtualCardUploadDataRequest.cardNo = str4;
        virtualCardUploadDataRequest.source = REQ_VALUE_SOURCE;
        virtualCardUploadDataRequest.channelType = REQ_VALUE_SOURCE;
        virtualCardUploadDataRequest.syncQuery = false;
        virtualCardUploadDataRequest.dataType = "RAW_OFFLINE_CODE";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("rawCode", str2);
            jSONObject.put("genCodeTime", str);
        } catch (JSONException unused) {
        }
        virtualCardUploadDataRequest.data = jSONObject.toString();
        scardCenterRpcFacade.virtualCardUploadData(virtualCardUploadDataRequest);
    }
}
