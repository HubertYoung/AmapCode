package com.autonavi.miniapp.plugin.userinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.nebulaapp.MiniAppAuthHelper;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.BasePlugin;
import com.autonavi.miniapp.plugin.util.AMapUserInfoUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.List;

public class UserInfoPlugin extends BasePlugin {
    public static final String GET_DEVICE_INFO = "amapGetDeviceInfo";
    public static final String GET_PRE_LOGIN_USER_INFO = "amapGetPreLoginUserInfo";
    public static final String GET_USER_BASIC_INFO = "amapGetUserInfo";
    public static final String GET_USER_FAVORITE_POI = "amapGetUserFavoritePoi";
    public static final String OPEN_URL = "amapOpenUrl";
    public static final String TAG = "UserInfoPlugin";
    private List<String> mActions = new ArrayList();
    private UserInfoPluginDelegate mUserInfoPluginDelegate;

    public UserInfoPlugin() {
        this.mActions.add(GET_USER_BASIC_INFO);
        this.mActions.add(GET_USER_FAVORITE_POI);
        this.mActions.add(GET_DEVICE_INFO);
        this.mActions.add(OPEN_URL);
        this.mActions.add(GET_PRE_LOGIN_USER_INFO);
        this.mUserInfoPluginDelegate = new UserInfoPluginDelegate();
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        if (this.mActions != null && h5EventFilter != null) {
            for (String addAction : this.mActions) {
                h5EventFilter.addAction(addAction);
            }
        }
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event == null) {
            LoggerFactory.getTraceLogger().info(TAG, "event == null");
            return false;
        }
        String action = h5Event.getAction();
        if (GET_USER_BASIC_INFO.equals(action)) {
            getUserInfo(h5Event, h5BridgeContext);
            return true;
        } else if (GET_USER_FAVORITE_POI.equals(action)) {
            getUserFavoritePoi(h5Event, h5BridgeContext);
            return true;
        } else if (GET_DEVICE_INFO.equals(action)) {
            getDeviceInfo(h5Event, h5BridgeContext);
            return true;
        } else if (OPEN_URL.equals(action)) {
            openAmapUrl(h5Event, h5BridgeContext);
            return true;
        } else if (!GET_PRE_LOGIN_USER_INFO.equals(action)) {
            return false;
        } else {
            getPreLoginUserInfo(h5Event, h5BridgeContext);
            return true;
        }
    }

    private void getPreLoginUserInfo(H5Event h5Event, final H5BridgeContext h5BridgeContext) {
        if (AMapUserInfoUtil.getInstance().isLogin()) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "success", (Object) Boolean.TRUE);
            jSONObject.put((String) Oauth2AccessToken.KEY_UID, (Object) AMapUserInfoUtil.getInstance().getUserInfo().a);
            jSONObject.put((String) Key.ALIPAY_UID, (Object) AMapUserInfoUtil.getInstance().getUserInfo().u);
            h5BridgeContext.sendBridgeResult(jSONObject);
            return;
        }
        new MiniAppAuthHelper().authMiniApp(new IAccountOAuthCallback() {
            public void onAuthResult(String str, String str2, String str3, Bundle bundle) {
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                    TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                    StringBuilder sb = new StringBuilder("auth failed, amapUid:");
                    sb.append(str);
                    sb.append(", alipayUid:");
                    sb.append(str2);
                    sb.append(", token:");
                    sb.append(str3);
                    traceLogger.debug(UserInfoPlugin.TAG, sb.toString());
                    UserInfoPlugin.this.callMtopBizJsError("登录失败", h5BridgeContext);
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "success", (Object) Boolean.TRUE);
                jSONObject.put((String) Oauth2AccessToken.KEY_UID, (Object) str);
                jSONObject.put((String) Key.ALIPAY_UID, (Object) str2);
                h5BridgeContext.sendBridgeResult(jSONObject);
            }
        }, null, false, 0);
    }

    private void openAmapUrl(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        cgz.a((Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext());
        String string = H5Utils.getString(h5Event.getParam(), (String) "url");
        if (TextUtils.isEmpty(string)) {
            h5BridgeContext.sendError(Error.INVALID_PARAM.ordinal(), (String) "invalid parameter!");
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(string));
        AMapPageUtil.getPageContext().startScheme(intent);
    }

    private void getDeviceInfo(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) Constants.KEY_MODEL, (Object) Build.MODEL);
        h5BridgeContext.sendBridgeResult(jSONObject);
    }

    private void getUserInfo(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        this.mUserInfoPluginDelegate.getUserInfo(h5Event, h5BridgeContext);
    }

    private void getUserFavoritePoi(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        this.mUserInfoPluginDelegate.getUserFavoritePoi(h5Event, h5BridgeContext);
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(this.mActions);
    }
}
