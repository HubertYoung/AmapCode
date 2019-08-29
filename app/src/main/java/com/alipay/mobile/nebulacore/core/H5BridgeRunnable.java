package com.alipay.mobile.nebulacore.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5JsCallData;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.wallet.H5JsApiConfigModel;
import com.alipay.mobile.nebulacore.wallet.H5JsApiConfigModel.AllBean;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class H5BridgeRunnable implements Runnable {
    private static List<String> a = Collections.synchronizedList(new ArrayList());
    private static List<String> b = Collections.synchronizedList(new ArrayList());
    private static JSONArray c = H5LogUtil.getSecurityJSApiBlackList();
    private static H5JsApiConfigModel i;
    private JSONObject d;
    private Map<String, Long> e;
    private String f;
    private String g;
    private H5Page h;

    static {
        a.add(CommonEvents.GET_AP_DATA_STORAGE);
        a.add(H5ContactPlugin.CONTACT);
        a.add("getAllContacts");
        a.add(CommonEvents.SET_TITLE_COLOR);
        a.add("chooseContact");
        a.add("getDTSchemeValue");
        a.add("setGestureBack");
        b.add("forbidden!");
        b.add("invalid parameter!");
        b.add("none error!");
        b.add("not implemented!");
        b.add("unknown error!");
    }

    public H5BridgeRunnable(H5Page h5Page, JSONObject param, Map<String, Long> callLog, String action, String eventId) {
        this.d = param;
        this.g = eventId;
        this.f = action;
        this.e = callLog;
        this.h = h5Page;
    }

    public void run() {
        String str;
        String jsapiSuccess;
        String data;
        if (this.h != null) {
            if (!(this.d == null || !this.d.containsKey("error") || this.f == null || a == null || a.contains(this.f))) {
                int error = H5Utils.getInt(this.d, (String) "error");
                String message = H5Utils.getString(this.d, (String) "message");
                if ("not implemented!".equals(message) && "yes".equalsIgnoreCase(H5Environment.getConfig("h5_upload_not_found_err"))) {
                    H5LogUtil.logNebulaTech(H5LogData.seedId("h5_not_implemented_api").param4().add(this.f, null));
                }
                if (!(error == 0 || b.contains(message) || this.h == null)) {
                    String paramStr = this.d != null ? H5Utils.getString(this.d, (String) "errorMessage") : null;
                    String requestParam = null;
                    if (!(this.f == null || this.h.getPageData() == null)) {
                        H5JsCallData h5JsCallData = this.h.getPageData().getJsapiInfo(this.g);
                        if (h5JsCallData != null) {
                            requestParam = h5JsCallData.getJoMsg();
                        }
                    }
                    if (requestParam != null && requestParam.length() > 500) {
                        requestParam = requestParam.substring(0, 500);
                    }
                    if (paramStr != null && paramStr.length() > 500) {
                        paramStr = paramStr.substring(0, 500);
                    }
                    JSONArray jsapiResultErrorLogBlackList = H5Utils.parseArray(H5Environment.getConfig("h5_jsapiResultErrorLogBlackList"));
                    if (jsapiResultErrorLogBlackList != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= jsapiResultErrorLogBlackList.size()) {
                                break;
                            }
                            Matcher matcherLink = Pattern.compile(jsapiResultErrorLogBlackList.getString(i2)).matcher(this.f);
                            if (matcherLink != null && matcherLink.matches()) {
                                requestParam = "";
                                paramStr = "";
                                break;
                            }
                            i2++;
                        }
                    }
                    H5LogUtil.logNebulaTech(H5LogData.seedId(CommonEvents.H5_AL_JSAPI_RESULT_ERROR).param1().add(H5Logger.DIAGNOSE, null).param2().add(this.h.getPageData().getPageInfo(), null).param3().add("jsapiName", this.f).add("params", requestParam).add("code", Integer.valueOf(error)).add("msg", paramStr).param4().addUniteParam(this.h.getPageData()).add(H5TinyAppLogUtil.TINY_APP_STANDARD_EXTRA_APPXVERSION, H5Utils.getAppxSDKVersion(this.h.getPageData().getAppId())));
                }
            }
            H5DevDebugProvider h5DevDebugProvider = (H5DevDebugProvider) H5ProviderManagerImpl.getInstance().getProvider(H5DevDebugProvider.class.getName());
            if (h5DevDebugProvider != null) {
                if (this.e != null && this.e.containsKey(this.g)) {
                    String paramStr2 = this.d != null ? this.d.toJSONString() : null;
                    String requestParam2 = null;
                    if (!(this.f == null || this.h.getPageData() == null)) {
                        H5JsCallData h5JsCallData2 = this.h.getPageData().getJsapiInfo(this.g);
                        if (h5JsCallData2 != null) {
                            requestParam2 = h5JsCallData2.getJoMsg();
                        }
                    }
                    if (this.h.getWebView() != null) {
                        h5DevDebugProvider.jsApiLog(this.f, H5BugmeIdGenerator.getBugmeViewId(this.h), this.g, requestParam2, paramStr2);
                    }
                } else if (this.h.getWebView() != null) {
                    if (this.d == null) {
                        data = "";
                    } else {
                        data = this.d.toJSONString();
                    }
                    h5DevDebugProvider.eventLog(this.f, H5BugmeIdGenerator.getBugmeViewId(this.h), data);
                }
            }
            if (i == null) {
                String jsApiConfig = H5Environment.getConfigWithProcessCache("h5_secJsApiCallConfig");
                if (!TextUtils.isEmpty(jsApiConfig)) {
                    try {
                        i = (H5JsApiConfigModel) JSON.parseObject(jsApiConfig, H5JsApiConfigModel.class);
                    } catch (Exception e2) {
                        H5Log.e((String) "H5BridgeRunnable", (Throwable) e2);
                    }
                } else {
                    return;
                }
            }
            if (i == null || !i.isEnable()) {
                H5Log.d("H5BridgeRunnable", "jsApiConfigModel == null or not enable!");
            } else if (this.h.getPageData() != null && this.e != null && this.e.containsKey(this.g)) {
                if (i.getAll() != null || i.getEvery() != null) {
                    if (c == null || !c.contains(this.f)) {
                        H5ConfigProvider configProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                        String pageUrl = this.h.getPageData().getPageUrl();
                        StringBuilder jsApiDetail = new StringBuilder();
                        if (TextUtils.isEmpty(this.f)) {
                            str = "`_`";
                        } else {
                            str = this.f + "`_`";
                        }
                        jsApiDetail.append(str);
                        String securityLevel = "";
                        boolean isInWhiteList = false;
                        if ("rpc".equals(this.f)) {
                            securityLevel = "rpc";
                            if (configProvider != null) {
                                isInWhiteList = configProvider.isRpcDomains(pageUrl);
                            }
                        } else {
                            H5PermissionManager permissionManager = (H5PermissionManager) H5Utils.getProvider(H5PermissionManager.class.getName());
                            if (permissionManager != null) {
                                securityLevel = permissionManager.getAliLevel(this.f);
                                isInWhiteList = permissionManager.urlInWhiteList(this.f, pageUrl);
                            }
                        }
                        jsApiDetail.append(securityLevel).append("`_`");
                        jsApiDetail.append(isInWhiteList ? "Y" : "N").append("`_`");
                        String inParam = "";
                        int inSize = 0;
                        String outParam = "";
                        long callTimestamp = 0;
                        H5JsCallData h5JsCallData3 = this.h.getPageData().getJsapiInfo(this.g);
                        String joMsg = "";
                        if (h5JsCallData3 != null) {
                            joMsg = h5JsCallData3.getJoMsg();
                        }
                        String paramStr3 = "";
                        if (this.d != null) {
                            paramStr3 = this.d.toJSONString();
                        }
                        if (joMsg != null) {
                            inSize = joMsg.length();
                        }
                        int outSize = paramStr3.length();
                        if (i.getAll() != null) {
                            AllBean allConfig = i.getAll();
                            boolean allIn = allConfig.isIn();
                            boolean allOut = allConfig.isOut();
                            int allMaxLength = allConfig.getMaxLength();
                            if (allIn) {
                                if (joMsg.length() > allMaxLength) {
                                    inParam = joMsg.substring(0, allMaxLength - 1);
                                } else {
                                    inParam = joMsg;
                                }
                            }
                            if (allOut) {
                                outParam = paramStr3.length() > allMaxLength ? paramStr3.substring(0, allMaxLength - 1) : paramStr3;
                            }
                        } else if (i.getEvery() != null && i.getEvery().containsKey(this.f)) {
                            JSONObject everyConfig = i.getEvery().getJSONObject(this.f);
                            boolean everyIn = H5Utils.getBoolean(everyConfig, (String) "in", false);
                            boolean everyOut = H5Utils.getBoolean(everyConfig, (String) "out", false);
                            int everyMaxLength = H5Utils.getInt(everyConfig, (String) "maxLength", 50);
                            if (everyIn) {
                                inParam = joMsg.length() > everyMaxLength ? joMsg.substring(0, everyMaxLength - 1) : joMsg;
                            }
                            if (everyOut) {
                                outParam = paramStr3.length() > everyMaxLength ? paramStr3.substring(0, everyMaxLength - 1) : paramStr3;
                            }
                        }
                        if (this.d == null || !this.d.containsKey("error")) {
                            jsapiSuccess = "Y";
                        } else {
                            outParam = paramStr3;
                            jsapiSuccess = "N";
                        }
                        if (this.e != null && this.e.containsKey(this.g)) {
                            callTimestamp = this.e.get(this.g).longValue();
                        }
                        jsApiDetail.append(inParam).append("`_`").append(inSize).append("`_`").append(outParam).append("`_`").append(outSize).append("`_`").append(jsapiSuccess).append("`_`").append(callTimestamp);
                        this.h.getPageData().appendJsApiDetail(jsApiDetail.toString());
                    }
                }
            }
        }
    }
}
