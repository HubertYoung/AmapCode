package com.alipay.mobile.common.transportext.biz.appevent;

import android.content.ContextWrapper;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.common.amnet.ipcapi.pushproc.OutEventNotifyService;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.transport.http.CookieAccessHelper;
import com.alipay.mobile.common.transport.http.GwCookieCacheHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;

public class AmnetUserInfo {
    public static final String KEY_SESSION_ID = "sessionId";
    public static final String KEY_USER_ID = "userId";
    private static final String LOGTAG = "ext_AmnetUserInfo";
    public static final String SHARED_FILE_NAME = "amnetsui";
    private static volatile String mSessionId;
    private static volatile String mUserId;

    public static synchronized void setUserInfo(String userId, String sessionId, byte[] syncExtParam) {
        synchronized (AmnetUserInfo.class) {
            LogUtilAmnet.i(LOGTAG, "setUserInfo,userId: " + userId + ", sessionid: " + sessionId + ", syncExtParam: " + (syncExtParam == null ? "is null" : syncExtParam.length + " byte "));
            mUserId = userId;
            mSessionId = sessionId;
            updateUserInfo(mUserId, mSessionId);
            notifyLogioEvent(userId, sessionId, syncExtParam);
        }
    }

    private static void notifyLogioEvent(String userId, String sessionId, byte[] extParam) {
        try {
            ((OutEventNotifyService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(OutEventNotifyService.class)).notifyLoginOrLogoutEvent(userId, sessionId, extParam);
        } catch (Exception e) {
            LogCatUtil.error((String) LOGTAG, (Throwable) e);
        }
    }

    private static final void updateUserInfo(String userId, String sessionId) {
        if (TextUtils.isEmpty(mUserId)) {
            clearUserInfo();
            return;
        }
        try {
            LogCatUtil.info(LOGTAG, "updateUserInfo,userId=[" + userId + "] sessionId=[" + sessionId + "]");
            String encryptUserId = TaobaoSecurityEncryptor.encrypt((ContextWrapper) ExtTransportEnv.getAppContext().getApplicationContext(), userId);
            String encryptSessionId = TaobaoSecurityEncryptor.encrypt((ContextWrapper) ExtTransportEnv.getAppContext().getApplicationContext(), sessionId);
            Editor edit = ExtTransportEnv.getAppContext().getSharedPreferences(SHARED_FILE_NAME, 4).edit();
            edit.putString("userId", encryptUserId);
            edit.putString("sessionId", encryptSessionId);
            edit.commit();
        } catch (Exception e) {
            LogCatUtil.error((String) LOGTAG, (Throwable) e);
        }
    }

    private static final void clearUserInfo() {
        try {
            Editor edit = ExtTransportEnv.getAppContext().getSharedPreferences(SHARED_FILE_NAME, 4).edit();
            edit.remove("sessionId");
            edit.remove("userId");
            edit.commit();
        } catch (Exception e) {
            LogCatUtil.error((String) LOGTAG, (Throwable) e);
        }
    }

    public static void resendSessionid() {
        try {
            String userid = mUserId;
            String sessionid = getSessionid();
            LogCatUtil.debug(LOGTAG, "resendSessionid,userid: " + userid + ",sessionid: " + sessionid);
            if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(sessionid)) {
                ((OutEventNotifyService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(OutEventNotifyService.class)).notifyResendSessionid(userid, sessionid);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) LOGTAG, "resendSessionid ex:" + ex.toString());
        }
    }

    private static String getSessionid() {
        try {
            if (!MiscUtils.isInAlipayClient(ExtTransportEnv.getAppContext())) {
                return "";
            }
            String gwUrl = ReadSettingServerUrl.getInstance().getGWFURL(ExtTransportEnv.getAppContext());
            String sessionidFromCache = getSessionidFromCookiestr(GwCookieCacheHelper.getCookie(gwUrl));
            if (!TextUtils.isEmpty(sessionidFromCache)) {
                LogCatUtil.debug(LOGTAG, "sessionidFromCache:" + sessionidFromCache);
                return sessionidFromCache;
            }
            String sessionidFromCookieStore = getSessionidFromCookiestr(CookieAccessHelper.getCookie(gwUrl, ExtTransportEnv.getAppContext()));
            if (!TextUtils.isEmpty(sessionidFromCookieStore)) {
                LogCatUtil.debug(LOGTAG, "sessionidFromCookieStore:" + sessionidFromCookieStore);
                return sessionidFromCookieStore;
            }
            LogCatUtil.debug(LOGTAG, "getSessionid return null");
            return "";
        } catch (Throwable ex) {
            LogCatUtil.error((String) LOGTAG, "getSessionid ex:" + ex.toString());
        }
    }

    private static String getSessionidFromCookiestr(String cookieStr) {
        String[] split;
        try {
            if (TextUtils.isEmpty(cookieStr)) {
                return "";
            }
            for (String cookie : cookieStr.split("; ")) {
                if (!TextUtils.isEmpty(cookie) && cookie.contains("ALIPAYJSESSIONID")) {
                    return cookie.substring(cookie.indexOf("=") + 1);
                }
            }
            return "";
        } catch (Throwable ex) {
            LogCatUtil.error((String) LOGTAG, "getAlipayJsessionidFromCookiestr ex:" + ex.toString());
        }
    }
}
