package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.offlinecode.biz.BusCodeGen;
import com.alipay.android.phone.inside.offlinecode.biz.IdentifyVerify;
import com.alipay.android.phone.inside.offlinecode.model.CardData;
import com.alipay.android.phone.inside.offlinecode.model.ScriptGenCodeException;
import com.alipay.android.phone.inside.offlinecode.model.UnknownProtocolException;
import com.alipay.android.phone.inside.offlinecode.plugin.utils.Utils;
import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.offlinecode.rpc.model.ScardCenterRes;
import com.alipay.android.phone.inside.offlinecode.storage.CardDataStorage;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class GenBusCodeService extends AbstractInsideService<JSONObject, Bundle> {
    static final int ALIPAT_MIN_VERSION_FOR_CHECK = 121;
    public static final String CODE_ALIPAY_NOT_INSTALL = "ALIPAY_NOT_INSTALL";
    public static final String CODE_ALIPAY_SIGN_ERROR = "ALIPAY_SIGN_ERROR";
    public static final String CODE_ALIPAY_UNMATCH = "ALIPAY_UNMATCH";
    public static final String CODE_FAILED = "FAILED";
    public static final String CODE_NOCARD = "NOCARD";
    public static final String CODE_RETRY_IN_ALIPAY = "RETRY_IN_ALIPAY";
    public static final String CODE_SUCESS = "SUCCESS";
    public static final String CODE_TIMEOUT = "TIMEOUT";
    public static final String CODE_UNAUTH = "UNAUTH";

    public Bundle startForResult(JSONObject jSONObject) throws Exception {
        dealUniformity(jSONObject);
        return generateCode(jSONObject);
    }

    private Bundle generateCode(JSONObject jSONObject) throws Exception {
        Context context = getContext();
        String optString = jSONObject.optString("cardType");
        String optString2 = jSONObject.optString("cardNo");
        String cardIdentify = CardDataStorage.getCardIdentify(optString, optString2);
        CardData cardData = CardDataStorage.getInstance().getCardData(context, cardIdentify);
        Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusGenCodeCardType");
        a.g = optString;
        StringBuilder sb = new StringBuilder();
        sb.append(optString2);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(cardData == null ? "localNotExist" : "localExist");
        a.a(sb.toString());
        if (cardData == null) {
            cardData = new CardData(cardIdentify);
        }
        ScardCenterRpcProvider scardCenterRpcProvider = new ScardCenterRpcProvider();
        BusCodeGen busCodeGen = new BusCodeGen();
        if (TextUtils.isEmpty(cardData.cardDetail)) {
            LoggerFactory.d().b(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "NeedUpdateCardDetail");
            ScardCenterRes queryCardDetail = scardCenterRpcProvider.queryCardDetail(context, optString, optString2);
            if (TextUtils.equals(queryCardDetail.code, CODE_SUCESS)) {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "UpdateCardDetailResult").g = "success";
                cardData.cardDetail = queryCardDetail.getResult();
            } else {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "UpdateCardDetailResult").g = UploadDataResult.FAIL_MSG;
                if (queryCardDetail.isCleanCard()) {
                    clearCard(context, cardData);
                }
                return buildFailedResult(queryCardDetail.code, queryCardDetail.getJSONIndicator());
            }
        }
        boolean z = false;
        if (busCodeGen.isNeedUpdateCardData(cardData)) {
            LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "NeedUpdateCardData");
            ScardCenterRes queryCardData = scardCenterRpcProvider.queryCardData(context, cardData.getCardType(), cardData.getCardNo());
            if (TextUtils.equals(queryCardData.code, CODE_SUCESS)) {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "UpdateCardDataResult").g = "success";
                if (queryCardData.result != null) {
                    cardData.cardData = queryCardData.getJSONResult().optString("data");
                    cardData.cardConfig = queryCardData.getJSONResult().optString("config");
                }
                cardData.resetOgTimes();
                cardData.refreshUpdateTime();
                z = true;
            } else {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "UpdateCardDataResult").g = UploadDataResult.FAIL_MSG;
                if (queryCardData.isCleanCard()) {
                    clearCard(context, cardData);
                }
                return buildFailedResult(queryCardData.code, queryCardData.getJSONIndicator());
            }
        }
        if (busCodeGen.isReachQrcodeOgTimes(cardData)) {
            LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "ReachQrcodeOgTimes");
            ScardCenterRes queryCardData2 = scardCenterRpcProvider.queryCardData(context, cardData.getCardType(), cardData.getCardNo());
            if (TextUtils.equals(queryCardData2.code, CODE_SUCESS)) {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "ReachQrcodeOgTimesResult").g = "success";
                if (queryCardData2.result != null) {
                    cardData.setCardData(queryCardData2.getJSONResult().optString("data"));
                    cardData.setCardConfig(queryCardData2.getJSONResult().optString("config"));
                }
                cardData.resetOgTimes();
                cardData.refreshUpdateTime();
                z = true;
            } else {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "ReachQrcodeOgTimesResult").g = UploadDataResult.FAIL_MSG;
                if (queryCardData2.isCleanCard()) {
                    clearCard(context, cardData);
                }
                return buildFailedResult(queryCardData2.code, queryCardData2.getJSONIndicator());
            }
        }
        if (busCodeGen.isReachQrcodeMgTime(cardData)) {
            LoggerFactory.d().b(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "ReachQrcodeMgTime");
            WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), 121);
            if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
                return buildFailedResult(adapterWalletStatus(checkAlipayStatus), (String) "钱包状态异常");
            }
            try {
                if (!new IdentifyVerify().verify(context, Utils.getLoginId())) {
                    return buildFailedResult((String) CODE_FAILED, (String) "核身失败");
                }
                cardData.resetMgTimes();
            } catch (TimeoutException unused) {
                return buildFailedResult((String) CODE_TIMEOUT, (String) "核身超时");
            }
        }
        try {
            String generateCode = busCodeGen.generateCode(context, cardData);
            uploadRawDataAsyncIfNeeded(cardData, generateCode);
            if (!z) {
                updateCardDataAsync(cardData);
            }
            return buildSuccessResult(CODE_SUCESS, cardData, generateCode);
        } catch (ScriptGenCodeException e) {
            LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "ScriptGenCodeException", (Throwable) e);
            return buildScriptFailResult(CODE_RETRY_IN_ALIPAY, cardData);
        } catch (UnknownProtocolException e2) {
            LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "UnknownProtocolException", (Throwable) e2);
            return buildScriptFailResult(CODE_RETRY_IN_ALIPAY, cardData);
        }
    }

    private String adapterWalletStatus(WalletStatusEnum walletStatusEnum) {
        String str = CODE_SUCESS;
        switch (walletStatusEnum) {
            case SUCCESS:
                str = CODE_SUCESS;
                break;
            case NOT_INSTALL:
                str = CODE_ALIPAY_NOT_INSTALL;
                break;
            case SIGN_ERROR:
                str = CODE_ALIPAY_SIGN_ERROR;
                break;
            case VERSION_UNMATCH:
                str = CODE_ALIPAY_UNMATCH;
                break;
        }
        LoggerFactory.f().b((String) "insdie", "::adapterWalletStatus > code:".concat(String.valueOf(str)));
        return str;
    }

    private void dealUniformity(JSONObject jSONObject) {
        try {
            ServiceExecutor.b("COMMONBIZ_SERVICE_ACCOUNTUNIFORMITY", jSONObject);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    /* access modifiers changed from: private */
    public void clearCard(Context context, CardData cardData) {
        LoggerFactory.f().e(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "GenBusCodeService::clearCard");
        cardData.cardData = "";
        cardData.cardConfig = "";
        cardData.resetOgTimes();
        CardDataStorage.getInstance().saveCardData(context, cardData);
    }

    private void updateCardDataAsync(final CardData cardData) {
        final Context context = getContext();
        LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "NeedUpdateCardDataAsync");
        new Thread(new Runnable() {
            public void run() {
                ScardCenterRpcProvider scardCenterRpcProvider = new ScardCenterRpcProvider();
                try {
                    ScardCenterRes queryCardData = scardCenterRpcProvider.queryCardData(context, cardData.getCardType(), cardData.getCardNo());
                    if (TextUtils.equals(queryCardData.code, GenBusCodeService.CODE_SUCESS)) {
                        LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "UpdateCardDataAsyncResult").g = "success";
                        if (queryCardData.result != null) {
                            cardData.setCardData(queryCardData.getJSONResult().optString("data"));
                            cardData.setCardConfig(queryCardData.getJSONResult().optString("config"));
                        }
                        cardData.resetOgTimes();
                        cardData.refreshUpdateTime();
                        CardDataStorage.getInstance().saveCardData(context, cardData);
                    } else {
                        LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "UpdateCardDataAsyncResult").g = UploadDataResult.FAIL_MSG;
                        if (queryCardData.isCleanCard()) {
                            GenBusCodeService.this.clearCard(context, cardData);
                        }
                    }
                } catch (Throwable th) {
                    LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "UpdateCardDataAsyncEx", th);
                } finally {
                    LoggerFactory.b();
                }
            }
        }).start();
    }

    private void uploadRawDataAsyncIfNeeded(final CardData cardData, final String str) {
        if (cardData.getOfflineData().uploadRawCode) {
            final String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            new Thread(new Runnable() {
                public void run() {
                    try {
                        new ScardCenterRpcProvider().uploadData(GenBusCodeService.this.getContext(), format, str, cardData.getCardType(), cardData.getCardNo());
                        Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusUploadRawDataAsync");
                        a.g = "success";
                        StringBuilder sb = new StringBuilder("genCodeTime : ");
                        sb.append(format);
                        a.a(sb.toString());
                    } catch (Exception e) {
                        LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BusUploadRawDataAsyncEx", (Throwable) e);
                    } finally {
                        LoggerFactory.b();
                    }
                }
            }).start();
        }
    }

    private Bundle buildFailedResult(String str, String str2) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ModulePoi.TIPS, str2);
        return buildFailedResult(str, jSONObject);
    }

    private Bundle buildFailedResult(String str, JSONObject jSONObject) throws Exception {
        Bundle bundle = new Bundle();
        bundle.putString("code", str);
        if (jSONObject != null) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("indicator", jSONObject);
            bundle.putString("value", jSONObject2.toString());
        }
        return bundle;
    }

    private Bundle buildSuccessResult(String str, CardData cardData, String str2) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("cardDetail", cardData.getJSONCardDetail());
        jSONObject.put("cardConfig", cardData.getJSONCardConfig());
        jSONObject.put("cardCode", str2);
        Bundle bundle = new Bundle();
        bundle.putString("code", str);
        bundle.putString("value", jSONObject.toString());
        return bundle;
    }

    private Bundle buildScriptFailResult(String str, CardData cardData) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("cardDetail", cardData.getJSONCardDetail());
        jSONObject.put("jumpWalletUrl", cardData.getJumpWalletUrl());
        Bundle bundle = new Bundle();
        bundle.putString("code", str);
        bundle.putString("value", jSONObject.toString());
        return bundle;
    }
}
