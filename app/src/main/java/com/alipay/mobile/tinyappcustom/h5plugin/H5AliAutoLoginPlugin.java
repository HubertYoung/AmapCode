package com.alipay.mobile.tinyappcustom.h5plugin;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.aliautologin.AliAutoLoginCheckConditionModel;
import com.alipay.android.phone.inside.api.model.aliautologin.AliAutoLoginModel;
import com.alipay.android.phone.inside.api.model.aliautologin.SourceTypeEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCheckConditionCode;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCode;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.securitycommon.aliauth.AliAuthResult;
import com.taobao.accs.common.Constants;

public class H5AliAutoLoginPlugin extends H5SimplePlugin {
    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return super.interceptEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!"aliAutoLogin".equals(event.getAction())) {
            return super.handleEvent(event, context);
        }
        a(event.getParam(), context);
        return true;
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("aliAutoLogin");
    }

    public void onRelease() {
        super.onRelease();
    }

    private void a(JSONObject object, H5BridgeContext context) {
        boolean showBindingPage = false;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String autoLoginSwitchValue = h5ConfigProvider.getConfig("h5_autologinbind");
            String h5AutoLoginNeedBindingPage = H5Utils.getString(object, (String) "H5AutoLoginNeedBindingPage");
            if (TextUtils.isEmpty(h5AutoLoginNeedBindingPage)) {
                h5AutoLoginNeedBindingPage = autoLoginSwitchValue;
            }
            showBindingPage = "YES".equalsIgnoreCase(h5AutoLoginNeedBindingPage);
            H5Log.d("H5AliAutoLoginPlugin", "autoLoginSwitchValue " + autoLoginSwitchValue + ", h5AutoLoginNeedBindingPage " + h5AutoLoginNeedBindingPage + ", showBindingPage " + showBindingPage);
        }
        final String authUrl = H5Utils.getString(object, (String) "H5AutoLoginUrl");
        if (object == null || !object.containsKey("H5AutoLoginUrl")) {
            b(context, "10", "参数格式错误");
        } else if (TextUtils.isEmpty(authUrl)) {
            b(context, "11", "未配置H5AutoLoginUrl");
        } else {
            final boolean forceAuth = "NO".equalsIgnoreCase(H5Utils.getString(object, (String) "useCache", (String) "YES"));
            final String source = H5Utils.getString(object, (String) "source");
            final boolean finalShowBindingPage = showBindingPage;
            final H5BridgeContext h5BridgeContext = context;
            H5Utils.getExecutor("RPC").execute(new Runnable() {
                public void run() {
                    if (!H5AliAutoLoginPlugin.b(authUrl)) {
                        H5AliAutoLoginPlugin.b(h5BridgeContext, "12", "URL不需要免登");
                    } else {
                        H5AliAutoLoginPlugin.b(finalShowBindingPage, forceAuth, source, authUrl, h5BridgeContext);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(String authUrl) {
        AliAutoLoginCheckConditionModel model = new AliAutoLoginCheckConditionModel();
        model.setTargetUrl(authUrl);
        try {
            OperationResult result = InsideOperationService.getInstance().startAction((Context) LauncherApplicationAgent.getInstance().getApplicationContext(), (BaseModel<T>) model);
            if (result == null) {
                H5Log.d("H5AliAutoLoginPlugin", "canAuthLogin result null");
                return false;
            }
            H5Log.d("H5AliAutoLoginPlugin", "canAuthLogin result: " + result.toJsonString());
            if (result.getCode() == AliAutoLoginCheckConditionCode.CAN_AUTO_LOGIN) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            H5Log.e("H5AliAutoLoginPlugin", "canAuthLogin error", e);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void b(boolean showUi, boolean forceAuth, String source, String targetUrl, H5BridgeContext context) {
        AliAutoLoginModel model = new AliAutoLoginModel();
        model.setShowUi(showUi);
        model.setForceAuth(forceAuth);
        model.setSourceType(SourceTypeEnum.H5);
        model.setSource(source);
        model.setTargetUrl(targetUrl);
        model.setSaveAliLoginCookie("NO");
        try {
            OperationResult result = InsideOperationService.getInstance().startAction((Context) LauncherApplicationAgent.getInstance().getApplicationContext(), (BaseModel<T>) model);
            if (result == null) {
                H5Log.d("H5AliAutoLoginPlugin", "autoLogin result null");
                b(context, "13", "免登失败");
                return;
            }
            H5Log.d("H5AliAutoLoginPlugin", "autoLogin result: " + result.toJsonString());
            if (result.getCode() == AliAutoLoginCode.SUCCESS) {
                AliAuthResult aliAuthResult = (AliAuthResult) JSONObject.parseObject(result.getResult(), AliAuthResult.class);
                if (aliAuthResult != null) {
                    a(aliAuthResult, context);
                    return;
                }
            }
            b(context, "13", "免登失败");
        } catch (Throwable e) {
            H5Log.e("H5AliAutoLoginPlugin", "autoLogin error", e);
        }
    }

    private static void a(AliAuthResult authResult, H5BridgeContext context) {
        JSONObject result = new JSONObject();
        result.put((String) "resultCode", (Object) authResult.resultStatus);
        result.put((String) "resultMemo", (Object) authResult.memo);
        result.put((String) Constants.KEY_SID, (Object) authResult.sid);
        result.put((String) "ecode", (Object) authResult.ecode);
        result.put((String) "tbUserId", (Object) authResult.tbUserId);
        result.put((String) "tbNick", (Object) authResult.tbNick);
        result.put((String) "redirectUrl", (Object) authResult.redirectUrl);
        context.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void b(H5BridgeContext context, String resultCode, String memo) {
        JSONObject result = new JSONObject();
        result.put((String) "resultCode", (Object) resultCode);
        result.put((String) "resultMemo", (Object) memo);
        context.sendBridgeResult(result);
    }
}
