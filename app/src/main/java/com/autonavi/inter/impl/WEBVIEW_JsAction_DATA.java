package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import com.autonavi.common.js.action.AosrequestAction;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"execAlipay", "nativeStorage", "registerData", "xxEncode", "registRightButtonNew", "loadSchema", "xxDecode", "registRightButton", "removeAjxLocalStorageItem", "triggerJS", "commercialSubscribe", "getHomeAndCompany", "getWebData", "getAlipayLoginToken", "setWebViewCloseBtn", "getSoftInputMode", "aosrequest", "setHomeAndCompany", "setWebLongpressEnable", "jsCallBack", "openTRCCompensate", "getTransparentParams", "getFeatureList", "registerVUISceneIdAndCmd", "jsAuthorizeWhiteListUpdate", "setSoftInputMode", "amapLog", "openAppUrl", "nativeAlert", "toggleFavoritePoint", "barHeight", "getPosition", "closeCurrentWebview", "setGobackStep", "toggleComponent", "getExtraUrl", "handleVUIWakeUp", "getAjxLocalStorageItem", "noticeH5", "setWebViewTitlebar", "promptMessage", "sendMediaEvent", "getFavoriteMark", "toggleLoading", "sendVUICmdResult", "userHomeAndCompany"}, jsActions = {"com.autonavi.common.js.action.alipay.ExecAlipay", "com.autonavi.common.js.action.NativeStorageAction", "com.autonavi.common.js.action.RegisterDataAction", "com.autonavi.common.js.action.xxEncodeAction", "com.autonavi.common.js.action.RegistRightButtonNewAction", "com.autonavi.common.js.action.LoadSchemaAction", "com.autonavi.common.js.action.xxDecodeAction", "com.autonavi.common.js.action.RegistRightButtonAction", "com.autonavi.common.js.action.RemoveAjxStorageItemAction", "com.autonavi.common.js.action.TriggerJSAction", "com.autonavi.common.js.action.CommercialSubscribeAction", "com.autonavi.common.js.action.GetHomeAndCompanyAction", "com.autonavi.common.js.action.GetWebDataAction", "com.autonavi.common.js.action.alipay.GetAlipayLoginToken", "com.autonavi.common.js.action.SetWebViewCloseBtnAction", "com.autonavi.common.js.action.GetSoftInputModeAction", "com.autonavi.common.js.action.AosrequestAction", "com.autonavi.common.js.action.SetHomeAndCompanyAction", "com.autonavi.common.js.action.SetWebLongpressEnableAction", "com.autonavi.common.js.action.JsCallBackAction", "com.autonavi.common.js.action.TRCCompensateAction", "com.autonavi.common.js.action.GetTransparentParamsAction", "com.autonavi.common.js.action.GetFeatureListAction", "com.autonavi.common.js.action.vui.RegisterVUISceneIdAndCommand", "com.autonavi.common.js.action.JsAuthorizeWhiteListUpdateAction", "com.autonavi.common.js.action.SetSoftInputModeAction", "com.autonavi.common.js.action.AMapLogAction", "com.autonavi.common.js.action.OpenAppUrlAction", "com.autonavi.common.js.action.NativeAlertAction", "com.autonavi.common.js.action.ToggleFavoritePointAction", "com.autonavi.common.js.action.BarHeightAction", "com.autonavi.common.js.action.GetPositionAction", "com.autonavi.common.js.action.CloseCurrentWebviewAction", "com.autonavi.common.js.action.SetGobackStepAction", "com.autonavi.common.js.action.ToggleComponentAction", "com.autonavi.common.js.action.GetExtraUrlAction", "com.autonavi.common.js.action.vui.HandleVUIWakeUpAction", "com.autonavi.common.js.action.GetAjxStorageItemAction", "com.autonavi.common.js.action.NoticeH5Action", "com.autonavi.common.js.action.SetWebViewTitleBarAction", "com.autonavi.common.js.action.PromptMessageAction", "com.autonavi.common.js.action.vui.SendMediaEventAction", "com.autonavi.common.js.action.GetFavoriteMarkAction", "com.autonavi.common.js.action.ToggleLoadingAction", "com.autonavi.common.js.action.vui.SendVUICmdResultAction", "com.autonavi.common.js.action.UserHomeAndCompanyAction"}, module = "webview")
@KeepName
public final class WEBVIEW_JsAction_DATA extends HashMap<String, Class<?>> {
    public WEBVIEW_JsAction_DATA() {
        put("execAlipay", bmd.class);
        put("nativeStorage", blh.class);
        put("registerData", bln.class);
        put("xxEncode", bmk.class);
        put("registRightButtonNew", blm.class);
        put("loadSchema", blf.class);
        put("xxDecode", bmj.class);
        put("registRightButton", bll.class);
        put("removeAjxLocalStorageItem", blo.class);
        put("triggerJS", blz.class);
        put("commercialSubscribe", bks.class);
        put("getHomeAndCompany", bkx.class);
        put("getWebData", blc.class);
        put("getAlipayLoginToken", bme.class);
        put("setWebViewCloseBtn", blt.class);
        put("getSoftInputMode", bla.class);
        put("aosrequest", AosrequestAction.class);
        put("setHomeAndCompany", blq.class);
        put("setWebLongpressEnable", bls.class);
        put("jsCallBack", ble.class);
        put("openTRCCompensate", blv.class);
        put("getTransparentParams", blb.class);
        put("getFeatureList", bkw.class);
        put("registerVUISceneIdAndCmd", bmg.class);
        put("jsAuthorizeWhiteListUpdate", bld.class);
        put("setSoftInputMode", blr.class);
        put("amapLog", bko.class);
        put("openAppUrl", blj.class);
        put("nativeAlert", blg.class);
        put("toggleFavoritePoint", blx.class);
        put("barHeight", bkp.class);
        put("getPosition", bkz.class);
        put("closeCurrentWebview", bkr.class);
        put("setGobackStep", blp.class);
        put("toggleComponent", blw.class);
        put("getExtraUrl", bku.class);
        put("handleVUIWakeUp", bmf.class);
        put("getAjxLocalStorageItem", bkt.class);
        put("noticeH5", bli.class);
        put("setWebViewTitlebar", blu.class);
        put("promptMessage", blk.class);
        put("sendMediaEvent", bmh.class);
        put("getFavoriteMark", bkv.class);
        put("toggleLoading", bly.class);
        put("sendVUICmdResult", bmi.class);
        put("userHomeAndCompany", bma.class);
    }
}
