package com.alipay.mobile.securitycommon.aliauth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.account.adapter.AppInfoAdapter;
import com.alipay.mobile.account.adapter.CommonAdapter;
import com.alipay.mobile.account.adapter.DeviceInfoAdapter;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.account.adapter.RpcAdapter;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.SourceType;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Value;
import com.alipay.mobile.securitycommon.aliauth.taobao.BindTaobaoHelper;
import com.alipay.mobile.securitycommon.aliauth.util.AliAuthUtil;
import com.alipay.mobile.securitycommon.aliauth.util.CookieUtil;
import com.alipay.mobile.securitycommon.aliauth.util.LogUtil;
import com.alipay.mobile.securitycommon.taobaobind.util.TimeConsumingLogAgent;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.AliAutoLoginFacade;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.AutoLoginPbReqPB;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.AutoLoginPbResPB;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.EntryStringString;
import com.alipay.mobileapp.biz.rpc.taobao.login.vo.MapStringString;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

class GeneralAuthWorker implements AuthWorker {
    private Context a = CommonAdapter.b();
    private UrlParser b;
    private AliAuthCache c;
    private IAliAuthProvider d;
    private BindTaobaoHelper e = new BindTaobaoHelper();

    GeneralAuthWorker() {
        CommonAdapter.a();
    }

    public void setAuthProvider(IAliAuthProvider iAliAuthProvider) {
        this.d = iAliAuthProvider;
    }

    public boolean canAutoLogin(String str) {
        return !a().matchBlackList(str) && a().matchWhiteList(str);
    }

    private AutoLoginPbResPB b(Bundle bundle) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("YWUC-JTTYZH-C29");
        timeConsumingLogAgent.logStart().logSeedID("taobaoAutoLogin").logFacade("alipay.client.autologin");
        AutoLoginPbReqPB autoLoginPbReqPB = new AutoLoginPbReqPB();
        autoLoginPbReqPB.umid = AliAuthUtil.getUmidToken(this.a);
        autoLoginPbReqPB.mac = AliAuthUtil.getWifiMac(this.a);
        AppInfoAdapter.a();
        autoLoginPbReqPB.clientVers = AppInfoAdapter.b();
        DeviceInfoAdapter.a();
        autoLoginPbReqPB.model = DeviceInfoAdapter.b();
        DeviceInfoAdapter.a();
        autoLoginPbReqPB.cellId = DeviceInfoAdapter.c();
        autoLoginPbReqPB.bindTaobao = Boolean.TRUE;
        AppInfoAdapter.a();
        autoLoginPbReqPB.productId = AppInfoAdapter.c();
        autoLoginPbReqPB.sourceType = bundle.getString(Key.SOURCE_TYPE);
        if ("H5".equals(autoLoginPbReqPB.sourceType)) {
            autoLoginPbReqPB.targetUrl = bundle.getString("targetUrl");
            autoLoginPbReqPB.domain = this.b.getDomain(autoLoginPbReqPB.targetUrl);
        }
        String string = bundle.getString("source");
        LogAdapter.a((String) "GeneralAuthWorker", "调用业务来源:".concat(String.valueOf(string)));
        if (TextUtils.isEmpty(string)) {
            AppInfoAdapter.a();
            if (AppInfoAdapter.d()) {
                string = "taobaoAutoLoginTest";
            }
        }
        autoLoginPbReqPB.extParams = new MapStringString();
        autoLoginPbReqPB.extParams.entries = new LinkedList();
        EntryStringString entryStringString = new EntryStringString();
        entryStringString.key = Key.BIZ_SCENE;
        entryStringString.value = string;
        autoLoginPbReqPB.extParams.entries.add(entryStringString);
        try {
            RpcAdapter.a();
            RpcAdapter.a();
            AutoLoginPbResPB taobaoAutoLogin = ((AliAutoLoginFacade) RpcAdapter.a(AliAutoLoginFacade.class)).taobaoAutoLogin(autoLoginPbReqPB);
            if (taobaoAutoLogin == null) {
                timeConsumingLogAgent.logEnd().addParam3("BindTaobaoRes=null").commit();
            } else {
                timeConsumingLogAgent.logEnd().addParam3(taobaoAutoLogin.resultStatus).commit();
            }
            return taobaoAutoLogin;
        } catch (RpcException e2) {
            LogAdapter.a("GeneralAuthWorker", "ali自动登录异常", e2);
            TimeConsumingLogAgent.logRpcException(timeConsumingLogAgent, e2);
            timeConsumingLogAgent.logEnd().commit();
            throw e2;
        }
    }

    private void a(AutoLoginPbResPB autoLoginPbResPB, Bundle bundle) {
        if (bundle == null || !"NO".equals(bundle.getString(Key.SAVE_ALI_LOGIN_COOKIE))) {
            LogUtil.log("GeneralAuthWorker", "保存淘宝域cookie");
            if (autoLoginPbResPB.domains != null && !autoLoginPbResPB.domains.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String next : autoLoginPbResPB.domains) {
                    sb.append(",");
                    sb.append(next);
                    a(next, autoLoginPbResPB.cookie);
                }
                String substring = sb.substring(1);
                CommonAdapter.a();
                CommonAdapter.b().getSharedPreferences("secuitySharedDataStore", 0).edit().putString(Key.AUTO_LOGIN_DOMAINS, substring).apply();
                try {
                    CookieSyncManager.createInstance(this.a);
                    CookieSyncManager.getInstance().sync();
                    return;
                } catch (Exception e2) {
                    LogAdapter.a("GeneralAuthWorker", "同步cookie异常", e2);
                }
            }
            return;
        }
        LogUtil.log("GeneralAuthWorker", "不保存淘宝域cookie");
    }

    private void a(String str, String str2) {
        try {
            JSONArray jSONArray = new JSONArray(str2);
            for (int i = 0; i < jSONArray.length(); i++) {
                StringBuilder sb = new StringBuilder();
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.has("name") && jSONObject.has("value")) {
                    String string = AliAuthUtil.getString(jSONObject, "name");
                    String installWeiboCookie = installWeiboCookie(str, AliAuthUtil.getString(jSONObject, "value"));
                    sb.append(string);
                    sb.append("=");
                    sb.append(installWeiboCookie);
                    sb.append(";");
                    if (jSONObject.has("version")) {
                        int i2 = AliAuthUtil.getInt(jSONObject, "version", -1);
                        sb.append("version=");
                        sb.append(i2);
                        sb.append(";");
                    }
                    if (jSONObject.has("path")) {
                        String string2 = AliAuthUtil.getString(jSONObject, "path");
                        sb.append("path=");
                        sb.append(string2);
                        sb.append(";");
                    }
                    if (AliAuthUtil.getBoolean(jSONObject, "secure", false)) {
                        sb.append("Secure;");
                    }
                    sb.append("Domain=");
                    sb.append(str);
                    String sb2 = sb.toString();
                    LogUtil.log("GeneralAuthWorker", String.format("install domain:%s, cookie:%s", new Object[]{str, sb2}));
                    CookieManager.getInstance().setCookie(str, sb2);
                }
            }
        } catch (Exception e2) {
            LogAdapter.a("GeneralAuthWorker", "set taobao cookies error", e2);
        }
    }

    public String installWeiboCookie(String str, String str2) {
        if (str.contains("weibo")) {
            LogUtil.log("GeneralAuthWorker", String.format("install cookie for weibo, domain:%s", new Object[]{str}));
            String str3 = null;
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append("&did=");
                sb.append(getWeiboDeviceInfo());
                str3 = URLEncoder.encode(sb.toString(), "utf-8");
            } catch (Throwable th) {
                LogAdapter.a("GeneralAuthWorker", "encode weibo value exception", th);
            }
            if (!TextUtils.isEmpty(str3)) {
                return str3;
            }
        }
        return str2;
    }

    public String getWeiboDeviceInfo() {
        String imei = getImei(this.a);
        StringBuilder sb = new StringBuilder("imei=");
        sb.append(imei);
        sb.append("&mac=");
        DeviceInfoAdapter.a();
        sb.append(DeviceInfoAdapter.d());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("android");
        sb3.append(VERSION.RELEASE);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append("&os=");
        sb5.append(sb4);
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb6);
        sb7.append("&model=");
        sb7.append(Build.MANUFACTURER);
        sb7.append(Token.SEPARATOR);
        sb7.append(Build.MODEL);
        String sb8 = sb7.toString();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            return sb8;
        }
        String name = defaultAdapter.getName();
        if (name == null) {
            return sb8;
        }
        StringBuilder sb9 = new StringBuilder();
        sb9.append(sb8);
        sb9.append("&devicename=");
        sb9.append(name);
        return sb9.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getImei(android.content.Context r4) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            int r0 = android.support.v4.content.ContextCompat.checkSelfPermission(r4, r0)
            r1 = -1
            if (r0 != r1) goto L_0x0013
            java.lang.String r4 = "GeneralAuthWorker"
            java.lang.String r0 = "getImei:PERMISSION_DENIED"
            com.alipay.mobile.securitycommon.aliauth.util.LogUtil.log(r4, r0)
            java.lang.String r4 = "000000000000000"
            goto L_0x0049
        L_0x0013:
            r0 = 0
            java.lang.String r1 = "phone"
            java.lang.Object r4 = r4.getSystemService(r1)     // Catch:{ Exception -> 0x0036 }
            android.telephony.TelephonyManager r4 = (android.telephony.TelephonyManager) r4     // Catch:{ Exception -> 0x0036 }
            if (r4 == 0) goto L_0x0034
            java.lang.String r4 = r4.getDeviceId()     // Catch:{ Exception -> 0x0036 }
            java.lang.String r0 = "GeneralAuthWorker"
            java.lang.String r1 = "getImei:"
            java.lang.String r2 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x0032 }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ Exception -> 0x0032 }
            com.alipay.mobile.securitycommon.aliauth.util.LogUtil.log(r0, r1)     // Catch:{ Exception -> 0x0032 }
            goto L_0x0041
        L_0x0032:
            r0 = move-exception
            goto L_0x003a
        L_0x0034:
            r4 = r0
            goto L_0x0041
        L_0x0036:
            r4 = move-exception
            r3 = r0
            r0 = r4
            r4 = r3
        L_0x003a:
            java.lang.String r1 = "GeneralAuthWorker"
            java.lang.String r2 = "getImei exception"
            com.alipay.mobile.account.adapter.LogAdapter.a(r1, r2, r0)
        L_0x0041:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x0049
            java.lang.String r4 = "000000000000000"
        L_0x0049:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.securitycommon.aliauth.GeneralAuthWorker.getImei(android.content.Context):java.lang.String");
    }

    private AliAuthResult a(AutoLoginPbResPB autoLoginPbResPB, boolean z) {
        AliAuthResult aliAuthResult = new AliAuthResult();
        aliAuthResult.success = z;
        aliAuthResult.resultStatus = autoLoginPbResPB.resultStatus;
        aliAuthResult.sid = autoLoginPbResPB.sid;
        aliAuthResult.ecode = autoLoginPbResPB.ecode;
        aliAuthResult.tbUserId = autoLoginPbResPB.tbUserId;
        aliAuthResult.tbNick = autoLoginPbResPB.tbNick;
        aliAuthResult.noticeUrl = autoLoginPbResPB.noticeUrl;
        aliAuthResult.statusAction = autoLoginPbResPB.statusAction;
        aliAuthResult.timeStamp = System.currentTimeMillis();
        aliAuthResult.redirectUrl = a().parseRedirectUrl(autoLoginPbResPB.redirectUrl);
        LogAdapter.a((String) "GeneralAuthWorker", "免登成功结果:".concat(String.valueOf(aliAuthResult)));
        return aliAuthResult;
    }

    public void clearCache(Bundle bundle) {
        String str = "";
        String str2 = "";
        if (bundle != null) {
            str = bundle.getString(Key.ALIPAY_UID);
            str2 = bundle.getString(Key.DELETE_ALI_LOGIN_COOKIE);
        }
        StringBuilder sb = new StringBuilder("clearCache 入参:");
        sb.append(str);
        sb.append(",deleteAliLoginCookie:");
        sb.append(str2);
        LogUtil.log("GeneralAuthWorker", sb.toString());
        if (TextUtils.isEmpty(str) && this.d != null) {
            str = this.d.getUserId();
        }
        LogUtil.log("GeneralAuthWorker", "End_Uid:".concat(String.valueOf(str)));
        AliAuthCache.getInstance().removeCache(str);
        if ("NO".equals(str2)) {
            LogUtil.log("GeneralAuthWorker", "不删除cookie");
            return;
        }
        LogUtil.log("GeneralAuthWorker", "删除cookie");
        List<String> list = null;
        try {
            CommonAdapter.a();
            String string = CommonAdapter.b().getSharedPreferences("secuitySharedDataStore", 0).getString(Key.AUTO_LOGIN_DOMAINS, "");
            if (!TextUtils.isEmpty(string)) {
                list = Arrays.asList(string.split(","));
            }
            for (String deleteCookiesForDomain : list) {
                CookieUtil.deleteCookiesForDomain(deleteCookiesForDomain);
            }
        } catch (Throwable th) {
            LogAdapter.a("GeneralAuthWorker", "删除cookie异常", th);
        }
    }

    private UrlParser a() {
        if (this.b == null) {
            this.b = new UrlParser();
        }
        return this.b;
    }

    private AliAuthCache b() {
        if (this.c == null) {
            this.c = AliAuthCache.getInstance();
            this.c.setUrlParser(a());
        }
        return this.c;
    }

    public synchronized AliAuthResult autoLogin(Bundle bundle) {
        boolean z;
        AliAuthResult aliAuthResult;
        try {
            if (TextUtils.isEmpty(bundle.getString("userId")) && this.d != null) {
                bundle.putString("userId", this.d.getUserId());
            }
            if (SourceType.NATIVE.equals(bundle.get(Key.SOURCE_TYPE))) {
                bundle.putString("targetUrl", Value.DEFAULT_DOMAIN);
            }
            if (bundle == null || !bundle.getBoolean(Key.FORCE_AUTH)) {
                z = false;
            } else {
                LogUtil.log("GeneralAuthWorker", "强制免登");
                b().resetCache(bundle);
                z = true;
            }
            if (z) {
                aliAuthResult = null;
            } else {
                aliAuthResult = b().getValidCacheResult(bundle);
                if (aliAuthResult != null && "H5".equals(bundle.get(Key.SOURCE_TYPE))) {
                    LogUtil.log("GeneralAuthWorker", "发现缓存，将redirectUrl替换为传入的targetUrl");
                    aliAuthResult.redirectUrl = bundle.getString("targetUrl");
                }
            }
            if (aliAuthResult != null) {
                LogUtil.log("GeneralAuthWorker", String.format("时间间隔未到，返回缓存的上一次免登结果, %s", new Object[]{aliAuthResult}));
                return aliAuthResult;
            }
            LogUtil.log("GeneralAuthWorker", "未找到缓存的淘宝免登结果，发起淘宝免登");
            return a(bundle);
        }
    }

    private AliAuthResult a(Bundle bundle) {
        AutoLoginPbResPB b2;
        Bundle bindTaobao;
        do {
            if (bundle == null) {
                LogUtil.log("GeneralAuthWorker", "syncTaobaoAuth, params = null");
            } else {
                LogUtil.log("GeneralAuthWorker", String.format("syncTaobaoAuth, loginId:%s, userId:%s, handleAccountError:%s, forceAuth:%s", new Object[]{bundle.getString("loginId"), bundle.getString("userId"), bundle.containsKey(Key.BIND_TAOBAO) ? String.valueOf(bundle.getBoolean(Key.BIND_TAOBAO)) : "null", bundle.containsKey(Key.FORCE_AUTH) ? String.valueOf(bundle.getBoolean(Key.FORCE_AUTH)) : "null"}));
            }
            try {
                b2 = b(bundle);
                LogUtil.log("GeneralAuthWorker", String.format("淘宝免登结果, success:%s, code:%s, cookie:%s", new Object[]{b2.success, b2.resultStatus, b2.cookie}));
                if ("1000".equals(b2.resultStatus)) {
                    a(b2, bundle);
                    AliAuthResult a2 = a(b2, true);
                    b().addCache(bundle, b2.domains, a2);
                    a().updateWhiteList(b2.whiteList);
                    return a2;
                }
                boolean z = bundle.getBoolean(Key.SHOW_UI, false);
                LogUtil.log("GeneralAuthWorker", String.format("免登淘宝是否显示UI:%s", new Object[]{Boolean.valueOf(z)}));
                if (!z) {
                    return a(b2, false);
                }
                if ("1001".equals(b2.resultStatus) || "1002".equals(b2.resultStatus) || Result.TAOBAO_ACTIVE.equals(b2.resultStatus)) {
                    LogUtil.log("GeneralAuthWorker", "需要绑定淘宝");
                    bindTaobao = this.e.bindTaobao(bundle, b2);
                } else {
                    if (Result.PUNISH_ACCOUNT.equals(b2.resultStatus) || Result.RUBBISH_ACCOUNT.equals(b2.resultStatus)) {
                        this.e.bindTaobao(bundle, b2);
                    }
                    return a(b2, false);
                }
            } catch (RpcException unused) {
                new Thread(new Runnable() {
                    public void run() {
                        LogUtil.log("GeneralAuthWorker", "新线程里面抛出RpcException");
                    }
                }).start();
                AliAuthResult aliAuthResult = new AliAuthResult();
                aliAuthResult.success = false;
                aliAuthResult.resultStatus = "2003";
                return aliAuthResult;
            }
        } while ("1000".equals(bindTaobao.getString("resultCode")));
        AliAuthResult a3 = a(b2, false);
        if (bindTaobao.containsKey("resultCode")) {
            a3.resultStatus = bindTaobao.getString("resultCode");
        }
        return a3;
    }
}
