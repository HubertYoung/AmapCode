package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.account.AuthUtil;
import com.ali.user.mobile.account.LoginCallBack;
import com.ali.user.mobile.account.bean.DeviceInfoBean;
import com.ali.user.mobile.account.bean.Tid;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.account.bean.UserLoginResultBiz;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.account.model.UserLoginReq;
import com.ali.user.mobile.account.model.UserLoginResult;
import com.ali.user.mobile.accountbiz.TidGetter;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.LoginService;
import com.ali.user.mobile.accountbiz.extservice.RSAService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.accountbiz.sp.AUSharedPreferences;
import com.ali.user.mobile.accountbiz.sp.SharedPreferencesManager;
import com.ali.user.mobile.db.UserInfoDaoHelper;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.rpc.facade.UserUnifyLoginFacade;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.ExternParamsWithout;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.LoginTypeWithout;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.LoginWithout;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.UserLoginGWReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.autologin.UserLoginGWResultPb;
import com.ali.user.mobile.util.LastLoginStatus;
import com.ali.user.mobile.util.NetworkUtils;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.model.LocationInfo;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.RpcInvokeContext;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.taobao.accs.utl.UtilityImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public class LoginServiceImpl extends BaseExtService implements LoginService {
    private static LoginService mLoginService;
    private final String AUTO_LOGIN = "autoLoginRpcError";
    final String TAG = "LoginServiceImpl";
    LoginCallBack autoLoginCallBack;
    AtomicBoolean isSameAccount = new AtomicBoolean(false);
    private long mLastBroadcastSendTime = 0;
    private String mRegistBindType;

    private String getAppState() {
        return "alipay inside";
    }

    private boolean isSupportPb() {
        return true;
    }

    private LoginServiceImpl(Context context) {
        super(context);
    }

    public static LoginService getInstance(Context context) {
        if (mLoginService == null) {
            synchronized (LoginServiceImpl.class) {
                try {
                    if (mLoginService == null) {
                        mLoginService = new LoginServiceImpl(context);
                    }
                }
            }
        }
        return mLoginService;
    }

    private Map<String, String> getRequestHeaders() {
        HashMap hashMap = new HashMap();
        try {
            String currentLoginUserId = getAccountService().getCurrentLoginUserId();
            if (!TextUtils.isEmpty(currentLoginUserId) && currentLoginUserId.length() > 3) {
                hashMap.put(Oauth2AccessToken.KEY_UID, currentLoginUserId.substring(currentLoginUserId.length() - 3, currentLoginUserId.length() - 1));
            }
        } catch (Throwable th) {
            AliUserLog.a((String) "LoginServiceImpl", th);
        }
        return hashMap;
    }

    public void autoLogin(LoginCallBack loginCallBack) {
        _log("-----autoLogin开始调用免登服务");
        String currentLoginLogonId = getAccountService().getCurrentLoginLogonId();
        _log(String.format("查询当前登录用户信息, logonId: ", new Object[]{currentLoginLogonId}));
        if (!TextUtils.isEmpty(currentLoginLogonId)) {
            _log("调用服务器请求登录");
            try {
                login(currentLoginLogonId, null, null, null, null);
            } catch (RpcException e) {
                throw e;
            } catch (Exception e2) {
                AliUserLog.a((String) "LoginServiceImpl", (Throwable) e2);
            }
        }
    }

    private String getApdid() {
        TokenResult tokenResult = APSecuritySdk.getInstance(this.mContext).getTokenResult();
        return tokenResult != null ? tokenResult.apdid : "tokenResult=null";
    }

    private String getUmidToken() {
        TokenResult tokenResult = APSecuritySdk.getInstance(this.mContext).getTokenResult();
        return tokenResult != null ? tokenResult.umidToken : "tokenResult=null";
    }

    private String getApdidToken() {
        TokenResult tokenResult = APSecuritySdk.getInstance(this.mContext).getTokenResult();
        return tokenResult != null ? tokenResult.apdidToken : "tokenResult=null";
    }

    private String getNetType() {
        switch (NetworkUtils.a(this.mContext)) {
            case 1:
                return UtilityImpl.NET_TYPE_2G;
            case 2:
                return UtilityImpl.NET_TYPE_3G;
            case 3:
                return "wifi";
            case 4:
                return UtilityImpl.NET_TYPE_4G;
            default:
                return "";
        }
    }

    public String getDeviceKeySet() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(DictionaryKeys.V2_APDID, getApdidToken());
            return jSONObject.toString();
        } catch (Exception e) {
            AliUserLog.b((String) "LoginServiceImpl", (Throwable) e);
            return e.getMessage();
        }
    }

    public UserLoginResultBiz login(String str, String str2, String str3, String str4, String str5) {
        return login(str, str2, str3, str4, str5, false);
    }

    public UserLoginResultBiz login(String str, String str2, String str3, String str4, String str5, boolean z) {
        _log("登录服务开始");
        UserLoginResultBiz userLoginResultBiz = new UserLoginResultBiz();
        UserLoginReq buildUserLoginReq = buildUserLoginReq();
        buildUserLoginReq.setLoginId(str);
        _log(String.format("账户登录类型: %s", new Object[]{str3}));
        if (str3 == null || "".equalsIgnoreCase(str3.trim())) {
            buildUserLoginReq.setLoginType(BehavorReporter.PROVIDE_BY_ALIPAY);
        } else if ("wireless".equalsIgnoreCase(str3.trim())) {
            buildUserLoginReq.setLoginType(BehavorReporter.PROVIDE_BY_ALIPAY);
        } else {
            buildUserLoginReq.setLoginType(str3);
        }
        buildUserLoginReq.setLoginWthPwd("without");
        RSAService rSAService = AntExtServiceManager.getRSAService(this.mContext);
        if (str2 != null && !"".equals(str2)) {
            _log("密码不为空，进行账密登录");
            try {
                buildUserLoginReq.setLoginPassword(rSAService.RSAEncrypt(str2, false));
                buildUserLoginReq.setLoginWthPwd("withpwd");
            } catch (RuntimeException e) {
                StringBuilder sb = new StringBuilder("autoLoginRpcError###密码加密异常");
                sb.append(e.getMessage());
                writeLoginLog(sb.toString());
                AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
                throw e;
            }
        }
        if (!TextUtils.isEmpty(str3) && "wireless".equalsIgnoreCase(str3.trim())) {
            buildUserLoginReq.setLoginWthPwd("withmobilepwd");
        }
        if (str4 != null) {
            if (LoginConstants.TAOBAO_LOGIN.equals(str3)) {
                buildUserLoginReq.setTbCheckCode(str4);
                buildUserLoginReq.setTbCheckCodeId(str5);
            } else {
                buildUserLoginReq.setLoginCheckCode(str4);
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("terminalName", DeviceInfo.a().j());
        if (z) {
            _log("切换账户免登，增加标记");
            hashMap.put("autoLoginScene", "switchAccount");
        }
        hashMap.put("appState", getAppState());
        hashMap.put(DictionaryKeys.DEV_APDIDTOKEN, getApdid());
        hashMap.put(DictionaryKeys.V2_UMID, getUmidToken());
        hashMap.put("devKeySet", getDeviceKeySet());
        hashMap.put("netType", getNetType());
        buildUserLoginReq.setExternParams(hashMap);
        return requestLogin(userLoginResultBiz, buildUserLoginReq, null, z);
    }

    private UserLoginReq buildUserLoginReq() {
        UserLoginReq userLoginReq = new UserLoginReq();
        DeviceInfo.a();
        userLoginReq.setUserAgent(DeviceInfo.o());
        buildDerviceInfo(userLoginReq);
        buildTid(userLoginReq);
        buildLocalInfo(userLoginReq);
        return userLoginReq;
    }

    private void buildDerviceInfo(UserLoginReq userLoginReq) {
        Tid a = new TidGetter(this.mContext).a();
        userLoginReq.setWalletTid(a.getTid());
        userLoginReq.setWalletClientKey(a.getClientKey());
        userLoginReq.setClientId(DeviceInfo.a().r());
        userLoginReq.setProductId(AppInfo.a().e());
        userLoginReq.setProductVersion(AppInfo.a().f());
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceInfo.a().h());
        userLoginReq.setScreenHigh(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(DeviceInfo.a().g());
        userLoginReq.setScreenWidth(sb2.toString());
        userLoginReq.setChannels(AppInfo.a().h());
        DeviceInfo.a();
        userLoginReq.setOsVersion(DeviceInfo.n());
    }

    private void buildTid(UserLoginReq userLoginReq) {
        _log("从移动快捷获取tid");
        MspDeviceInfoBean queryCertification = AntExtServiceManager.getDeviceService(this.mContext).queryCertification();
        if (queryCertification != null) {
            _log(String.format("从移动快捷获取tid=%s", new Object[]{queryCertification.getTid()}));
            if (queryCertification.getTid() != null) {
                userLoginReq.setMspTid(queryCertification.getTid());
                userLoginReq.setMspClientKey(queryCertification.getMspkey());
                userLoginReq.setMspImei(queryCertification.getImei());
                userLoginReq.setMspImsi(queryCertification.getImsi());
                userLoginReq.setVimei(queryCertification.getVimei());
                userLoginReq.setVimsi(queryCertification.getVimsi());
            }
        }
    }

    private void buildLocalInfo(UserLoginReq userLoginReq) {
        userLoginReq.setMac(DeviceInfo.a().u());
        AliUserInit.b();
        userLoginReq.setCellId(AuthUtil.a());
        LocationInfo c = OutsideConfig.c();
        if (c != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(c.d());
            sb.append(",");
            sb.append(c.e());
            sb.append(",");
            sb.append(c.a());
            userLoginReq.setLocation(sb.toString());
        }
    }

    private UserLoginResultBiz requestLogin(UserLoginResultBiz userLoginResultBiz, UserLoginReq userLoginReq, String str, boolean z) {
        UserLoginResult userLoginResult;
        this.mRegistBindType = str;
        RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
        UserUnifyLoginFacade userUnifyLoginFacade = (UserUnifyLoginFacade) rpcService.getRpcProxy(UserUnifyLoginFacade.class);
        RpcInvokeContext rpcInvokeContext = rpcService.getRpcInvokeContext(userUnifyLoginFacade);
        rpcInvokeContext.setRequestHeaders(getRequestHeaders());
        rpcInvokeContext.setResetCookie(z);
        _log(z ? "本次登录需要清除cookie" : "本次登录不需要清除cookie");
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            if (isSupportPb()) {
                userLoginResult = convertUserLoginResult(userUnifyLoginFacade.loginPb(convertUserLoginGWReqPb(userLoginReq)));
            } else {
                userLoginResult = userUnifyLoginFacade.login(userLoginReq);
            }
            writeAutoLoginLog(userLoginResult, elapsedRealtime);
            return processLoginResult(userLoginResult, userLoginResultBiz, userLoginReq, true);
        } catch (RpcException e) {
            writeAutoLoginLog(elapsedRealtime, e);
            throw e;
        }
    }

    private UserLoginGWReqPb convertUserLoginGWReqPb(UserLoginReq userLoginReq) {
        UserLoginGWReqPb userLoginGWReqPb = new UserLoginGWReqPb();
        userLoginGWReqPb.cellId = userLoginReq.cellId;
        userLoginGWReqPb.channels = userLoginReq.channels;
        userLoginGWReqPb.clientDigest = userLoginReq.clientDigest;
        userLoginGWReqPb.clientId = userLoginReq.clientId;
        userLoginGWReqPb.deviceToken = userLoginReq.deviceToken;
        userLoginGWReqPb.location = userLoginReq.location;
        userLoginGWReqPb.loginCheckCode = userLoginReq.loginCheckCode;
        userLoginGWReqPb.loginId = userLoginReq.loginId;
        userLoginGWReqPb.loginPassword = userLoginReq.loginPassword;
        userLoginGWReqPb.mac = userLoginReq.mac;
        userLoginGWReqPb.mspClientKey = userLoginReq.mspClientKey;
        userLoginGWReqPb.mspImei = userLoginReq.mspImei;
        userLoginGWReqPb.mspImsi = userLoginReq.mspImsi;
        userLoginGWReqPb.mspTid = userLoginReq.mspTid;
        userLoginGWReqPb.operatorType = "";
        userLoginGWReqPb.osVersion = userLoginReq.osVersion;
        userLoginGWReqPb.productId = userLoginReq.productId;
        userLoginGWReqPb.productVersion = userLoginReq.productVersion;
        userLoginGWReqPb.secTS = userLoginReq.secTS;
        userLoginGWReqPb.sourceId = userLoginReq.sourceId;
        userLoginGWReqPb.tbCheckCode = userLoginReq.tbCheckCode;
        userLoginGWReqPb.tbCheckCodeId = userLoginReq.tbCheckCodeId;
        userLoginGWReqPb.userAgent = userLoginReq.userAgent;
        userLoginGWReqPb.vimei = userLoginReq.vimei;
        userLoginGWReqPb.vimsi = userLoginReq.vimsi;
        userLoginGWReqPb.walletClientKey = userLoginReq.walletClientKey;
        userLoginGWReqPb.walletTid = userLoginReq.walletTid;
        userLoginGWReqPb.loginType = makeLoginType(userLoginReq.loginType);
        userLoginGWReqPb.loginWthPwd = makeValidateType(userLoginReq.loginWthPwd);
        try {
            userLoginGWReqPb.screenHigh = Integer.valueOf(userLoginReq.screenHigh);
            userLoginGWReqPb.screenWidth = Integer.valueOf(userLoginReq.screenWidth);
        } catch (NumberFormatException e) {
            AliUserLog.b((String) "LoginServiceImpl", (Throwable) e);
        }
        if (userLoginReq.externParams != null && userLoginReq.externParams.size() > 0) {
            userLoginGWReqPb.externParams = new LinkedList();
            for (String next : userLoginReq.externParams.keySet()) {
                userLoginGWReqPb.externParams.add(makeExternParam(next, userLoginReq.externParams.get(next)));
            }
        }
        return userLoginGWReqPb;
    }

    private LoginTypeWithout makeLoginType(String str) {
        return LoginConstants.TAOBAO_LOGIN.equalsIgnoreCase(str) ? LoginTypeWithout.taobao : LoginTypeWithout.alipay;
    }

    private LoginWithout makeValidateType(String str) {
        if ("withpwd".equalsIgnoreCase(str)) {
            return LoginWithout.withpwd;
        }
        if ("without".equalsIgnoreCase(str)) {
            return LoginWithout.without;
        }
        if ("withinnertoken".equalsIgnoreCase(str)) {
            return LoginWithout.withinnertoken;
        }
        if ("withmobilepwd".equalsIgnoreCase(str)) {
            return LoginWithout.withmobilepwd;
        }
        return LoginWithout.withtoken;
    }

    private ExternParamsWithout makeExternParam(String str, String str2) {
        ExternParamsWithout externParamsWithout = new ExternParamsWithout();
        externParamsWithout.key = str;
        externParamsWithout.value = str2;
        return externParamsWithout;
    }

    private UserLoginResult convertUserLoginResult(UserLoginGWResultPb userLoginGWResultPb) {
        UserLoginResult userLoginResult = new UserLoginResult();
        userLoginResult.barcodePayToken = userLoginGWResultPb.barcodePayToken;
        userLoginResult.bindCard = userLoginGWResultPb.isBindCard.booleanValue();
        userLoginResult.currentProductVersion = userLoginGWResultPb.currentProductVersion;
        userLoginResult.customerType = userLoginGWResultPb.customerType;
        userLoginResult.downloadURL = userLoginGWResultPb.downloadURL;
        userLoginResult.existNewVersion = userLoginGWResultPb.existNewVersion;
        userLoginResult.extern_token = userLoginGWResultPb.extern_token;
        userLoginResult.headImg = userLoginGWResultPb.headImg;
        userLoginResult.isCertified = userLoginGWResultPb.isCertified;
        userLoginResult.loginCheckCodeImg = userLoginGWResultPb.loginCheckCodeImg;
        userLoginResult.loginCheckCodeUrl = userLoginGWResultPb.loginCheckCodeUrl;
        userLoginResult.loginContext = userLoginGWResultPb.loginContext;
        userLoginResult.loginId = userLoginGWResultPb.loginId;
        userLoginResult.loginServerTime = userLoginGWResultPb.loginServerTime;
        userLoginResult.loginToken = userLoginGWResultPb.loginToken;
        userLoginResult.memo = userLoginGWResultPb.memo;
        userLoginResult.mobileNo = userLoginGWResultPb.mobileNo;
        userLoginResult.resultStatus = userLoginGWResultPb.resultStatus.intValue();
        userLoginResult.sessionId = userLoginGWResultPb.sessionId;
        userLoginResult.taobaoSid = userLoginGWResultPb.taobaoSid;
        userLoginResult.tbCheckCodeId = userLoginGWResultPb.tbCheckCodeId;
        userLoginResult.tbCheckCodeUrl = userLoginGWResultPb.tbCheckCodeUrl;
        userLoginResult.userId = userLoginGWResultPb.userId;
        userLoginResult.userName = userLoginGWResultPb.userName;
        userLoginResult.wirelessUser = userLoginGWResultPb.isWirelessUser.booleanValue();
        userLoginResult.iconUrl = null;
        if (userLoginGWResultPb.extResAttrs != null && userLoginGWResultPb.extResAttrs.size() > 0) {
            userLoginResult.extResAttrs = new HashMap();
            for (ExternParamsWithout next : userLoginGWResultPb.extResAttrs) {
                if (!(next == null || next.key == null)) {
                    userLoginResult.extResAttrs.put(next.key, next.value);
                }
            }
        }
        return userLoginResult;
    }

    public UserLoginResultBiz processLoginResult(UserLoginResult userLoginResult, UserLoginResultBiz userLoginResultBiz, UserLoginReq userLoginReq, boolean z) {
        try {
            String currentLoginUserId = getAccountService().getCurrentLoginUserId();
            if (userLoginResult != null) {
                UserInfoDaoHelper a = UserInfoDaoHelper.a(this.mContext);
                boolean z2 = true;
                if (1000 == userLoginResult.getResultStatus()) {
                    _log("登录返回成功");
                    saveLoginServerTime(userLoginResult, userLoginReq);
                    _log("登录成功，将用户数据同步至本地数据库中");
                    _log("alipayLoginId:".concat(String.valueOf(userLoginReq.getLoginId())));
                    _log("组装返回数据开始");
                    dataConvert(userLoginResultBiz, userLoginResult);
                    _log("组装返回数据完成");
                    saveUserInfo(userLoginResult);
                    _log("更新当前已登录用户状态");
                    updateCurrentUser(userLoginResult.getLoginId(), userLoginResult.getUserId(), "true");
                    if (!z) {
                        AliUserLog.c("LoginServiceImpl", "需要补密，延迟发送登录广播，等补密处理完成后再发送");
                    } else if (userLoginResultBiz != null && 1000 == userLoginResultBiz.getResultStatus()) {
                        AliUserLog.c("LoginServiceImpl", "登录成功,发送登录成功消息");
                        if (userLoginResultBiz.getUserId() != null) {
                            if (userLoginResultBiz.getUserId().equals(currentLoginUserId)) {
                                z2 = false;
                            }
                        }
                        sendLoginBroadcast(userLoginResultBiz, Boolean.valueOf(z2), "withpwd".equals(userLoginReq.getLoginWthPwd()), this.mRegistBindType);
                    }
                } else {
                    _log(String.format("登录返回失败, status: %s, memo: %s", new Object[]{Integer.valueOf(userLoginResult.getResultStatus()), userLoginResult.getMemo()}));
                    LogAgent.a((String) "MTBIZ_LOGIN", (String) "AUTO_LOGIN", String.valueOf(userLoginResult.getResultStatus()), (Map<String, String>) null);
                    userLoginResultBiz.setMemo(userLoginResult.getMemo());
                    userLoginResultBiz.setResultStatus(userLoginResult.getResultStatus());
                    userLoginResultBiz.setExistNewVersion(userLoginResult.getExistNewVersion());
                    userLoginResultBiz.setCurrentProductVersion(userLoginResult.getCurrentProductVersion());
                    userLoginResultBiz.setDownloadURL(userLoginResult.getDownloadURL());
                    userLoginResultBiz.setExtResAttrs(userLoginResult.getExtResAttrs());
                    if (userLoginReq.getLoginPassword() == null || "".equals(userLoginReq.getLoginPassword())) {
                        _log(String.format("清除本地用戶免登狀態 logonId=%s", new Object[]{userLoginReq.getLoginId()}));
                        a.d(userLoginReq.getLoginId());
                    } else {
                        _log("无需清除本地登录状态");
                        userLoginResultBiz.setResultStatus(userLoginResult.getResultStatus());
                        userLoginResultBiz.setMemo(userLoginResult.getMemo());
                        userLoginResultBiz.setLoginCheckCodeImg(userLoginResult.getLoginCheckCodeImg());
                        userLoginResultBiz.setLoginCheckCodeUrl(userLoginResult.getLoginCheckCodeUrl());
                        userLoginResultBiz.setTbCheckCodeUrl(userLoginResult.getTbCheckCodeUrl());
                        userLoginResultBiz.setTbCheckCodeId(userLoginResult.getTbCheckCodeId());
                        userLoginResultBiz.setLoginId(userLoginResult.getLoginId());
                    }
                    if (userLoginReq.getExternParams() == null || !"switchAccount".equals(userLoginReq.getExternParams().get("autoLoginScene"))) {
                        _log("修改当前用户登录状态为未登录");
                        getAccountService().setCurrentLoginState("false");
                    } else {
                        _log("切换账户，免登失败不修改当前用户登录状态");
                    }
                    logoutWhenAutoLoginFail(currentLoginUserId);
                }
            }
            return userLoginResultBiz;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("autoLoginRpcError###processLoginResult");
            sb.append(e.getMessage());
            writeLoginLog(sb.toString());
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
            return userLoginResultBiz;
        }
    }

    private void logoutWhenAutoLoginFail(String str) {
        _log("免登失败发送logout广播，清cookie");
        try {
            Context b = AliUserInit.b();
            Intent intent = new Intent("com.alipay.security.logout");
            intent.putExtra("logoutUserId", str);
            LocalBroadcastManager.getInstance(b).sendBroadcast(intent);
        } catch (Exception e) {
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
        }
    }

    private AccountService getAccountService() {
        return AntExtServiceManager.getAccountService(this.mContext);
    }

    public String getTime() {
        return new SimpleDateFormat("MMddHHmmssSSS", Locale.getDefault()).format(new Date());
    }

    private void saveLoginServerTime(UserLoginResult userLoginResult, UserLoginReq userLoginReq) {
        _log(String.format("服务器登陆时间:%s，登陆类型:%s", new Object[]{userLoginResult.loginServerTime, userLoginReq.getLoginWthPwd()}));
        AUSharedPreferences a = SharedPreferencesManager.a(this.mContext, "deviceLock", 0);
        a.b("loginServerTime", userLoginResult.loginServerTime);
        if (!"without".equalsIgnoreCase(userLoginReq.getLoginWthPwd())) {
            a.b("loginServerTimeWithpwd", userLoginResult.loginServerTime);
        }
        a.b();
    }

    private void updateCurrentUser(String str, String str2, String str3) {
        try {
            getAccountService().setCurrentLoginLogonId(str);
            getAccountService().setCurrentLoginState(str3);
            getAccountService().setCurrentLoginUserId(str2);
        } catch (Exception e) {
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
        }
    }

    private void dataConvert(UserLoginResultBiz userLoginResultBiz, UserLoginResult userLoginResult) {
        try {
            userLoginResultBiz.setBarcodePayToken(userLoginResult.getBarcodePayToken());
            userLoginResultBiz.setCurrentProductVersion(userLoginResult.getCurrentProductVersion());
            userLoginResultBiz.setDownloadURL(userLoginResult.getDownloadURL());
            userLoginResultBiz.setExistNewVersion(userLoginResult.getExistNewVersion());
            userLoginResultBiz.setExtern_token(userLoginResult.getExtern_token());
            userLoginResultBiz.setIsCertified(userLoginResult.getIsCertified());
            userLoginResultBiz.setLoginCheckCodeImg(userLoginResult.getLoginCheckCodeImg());
            userLoginResultBiz.setLoginCheckCodeUrl(userLoginResult.getLoginCheckCodeUrl());
            userLoginResultBiz.setLoginFlag(true);
            userLoginResultBiz.setLoginId(userLoginResult.getLoginId());
            userLoginResultBiz.setLoginServerTime(userLoginResult.getLoginServerTime());
            userLoginResultBiz.setLoginToken(userLoginResult.getLoginToken());
            userLoginResultBiz.setMemo(userLoginResult.getMemo());
            userLoginResultBiz.setMobileNo(userLoginResult.getMobileNo());
            userLoginResultBiz.setResultStatus(userLoginResult.getResultStatus());
            userLoginResultBiz.setTaobaoSid(userLoginResult.getTaobaoSid());
            userLoginResultBiz.setTbCheckCodeId(userLoginResult.getTbCheckCodeId());
            userLoginResultBiz.setTbCheckCodeUrl(userLoginResult.getTbCheckCodeUrl());
            userLoginResultBiz.setUserId(userLoginResult.getUserId());
            userLoginResultBiz.setUserName(userLoginResult.getUserName());
            userLoginResultBiz.setSessionId(userLoginResult.getSessionId());
            userLoginResultBiz.setExtResAttrs(userLoginResult.getExtResAttrs());
        } catch (Exception e) {
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
            _log("組裝登录返回数据异常");
        }
    }

    private boolean saveUserInfo(UserLoginResult userLoginResult) {
        _log("查询当前用户详细信息");
        UserInfo userInfo = null;
        try {
            UserInfo queryAccountDetailInfoByUserId = getAccountService().queryAccountDetailInfoByUserId(userLoginResult.getUserId());
            if (queryAccountDetailInfoByUserId == null) {
                try {
                    _log("未查询出当前登录相关信息");
                    userInfo = new UserInfo();
                } catch (Exception e) {
                    e = e;
                    userInfo = queryAccountDetailInfoByUserId;
                }
            } else {
                userInfo = queryAccountDetailInfoByUserId;
            }
            userInfo.setLogonId(userLoginResult.getLoginId());
            userInfo.setUserId(userLoginResult.getUserId());
            userInfo.setUserName(userLoginResult.getUserName());
            userInfo.setMobileNumber(userLoginResult.getMobileNo());
            userInfo.setIsCertified(userLoginResult.getIsCertified());
            userInfo.setWirelessUser(userLoginResult.isWirelessUser());
            userInfo.setBindCard(userLoginResult.isBindCard());
            userInfo.setCustomerType(userLoginResult.getCustomerType());
            Map<String, String> extResAttrs = userLoginResult.getExtResAttrs();
            _log("userLoginResult.getExtResAttrs:".concat(String.valueOf(extResAttrs)));
            if (extResAttrs != null) {
                String str = extResAttrs.get("havanaId");
                if (!TextUtils.isEmpty(str)) {
                    userInfo.setHavanaId(str);
                }
                String str2 = extResAttrs.get("nickName");
                if (!TextUtils.isEmpty(str2)) {
                    userInfo.setNick(str2);
                }
                String str3 = extResAttrs.get("enabledStatus");
                if (!TextUtils.isEmpty(str3)) {
                    userInfo.setUserType(str3);
                }
                String str4 = extResAttrs.get("memberGrade");
                if (!TextUtils.isEmpty(str4)) {
                    userInfo.setMemberGrade(str4);
                }
                String str5 = extResAttrs.get("realNameStatus");
                if (!TextUtils.isEmpty(str5)) {
                    userInfo.setRealNamed(str5);
                }
                String str6 = extResAttrs.get("isNewUser");
                if (!TextUtils.isEmpty(str6)) {
                    userInfo.setNewUser("true".equalsIgnoreCase(str6));
                }
                String str7 = extResAttrs.get("realName");
                if (!TextUtils.isEmpty(str7)) {
                    userInfo.setRealName(str7);
                }
                String str8 = extResAttrs.get("walletEdition");
                if (!TextUtils.isEmpty(str8)) {
                    userInfo.setWalletEdition(str8);
                }
                String str9 = extResAttrs.get("showWalletEditionSwitch");
                if (!TextUtils.isEmpty(str9)) {
                    userInfo.setShowWalletEditionSwitch("true".equalsIgnoreCase(str9));
                }
                String str10 = extResAttrs.get("otherLoginId");
                if (!TextUtils.isEmpty(str10)) {
                    userInfo.setOtherLoginId(str10);
                }
                userInfo.setNoQueryPwdUser(extResAttrs.get("noQueryPwdUser"));
            }
            String headImg = userLoginResult.getHeadImg();
            if (!TextUtils.isEmpty(headImg)) {
                userInfo.setUserAvatar(headImg);
            }
            userInfo.setLoginTime(userLoginResult.getLoginServerTime());
            userInfo.setTaobaoSid(userLoginResult.getTaobaoSid());
            userInfo.setExternToken(userLoginResult.getExtern_token());
            userInfo.setLoginToken(userLoginResult.getLoginToken());
            userInfo.setSessionId(userLoginResult.getSessionId());
            if (getLocalTid()) {
                _log("登录成功，tid本地存在，设置免登状态");
                userInfo.setAutoLogin(true);
                writeLoginLog("setAutoLogin=true###getLocalTid()");
            } else {
                writeLoginLog("getLocalTid is false");
            }
        } catch (Exception e2) {
            e = e2;
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
            UserInfoDaoHelper.a(this.mContext).a(userInfo);
            _log("同步至本地数据库完成");
            return true;
        }
        try {
            UserInfoDaoHelper.a(this.mContext).a(userInfo);
            _log("同步至本地数据库完成");
            return true;
        } catch (Exception e3) {
            _log("同步至本地数据库异常");
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e3);
            return false;
        }
    }

    private boolean getLocalTid() {
        try {
            DeviceInfoBean queryDeviceInfo = AntExtServiceManager.getDeviceService(this.mContext).queryDeviceInfo();
            if (queryDeviceInfo == null) {
                _log("查询本地tid为空, deviceInfoBean=null");
                return false;
            } else if (queryDeviceInfo.getWalletTid() != null) {
                _log("查询本地tid不为空");
                return true;
            } else {
                _log("查询本地tid为空");
                return false;
            }
        } catch (Exception e) {
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
            return false;
        }
    }

    public void sendLoginBroadcast(boolean z, UserLoginReq userLoginReq, UserLoginResultBiz userLoginResultBiz) {
        sendLoginBroadcast(userLoginResultBiz, Boolean.valueOf(z), "withpwd".equals(userLoginReq.getLoginWthPwd()), this.mRegistBindType);
    }

    private void sendLoginBroadcast(UserLoginResultBiz userLoginResultBiz, Boolean bool, boolean z, String str) {
        _log("登录成功，发送消息开始");
        try {
            Intent intent = new Intent("com.alipay.security.login");
            intent.putExtra("logonId", userLoginResultBiz.getLoginId());
            intent.putExtra("userId", userLoginResultBiz.getUserId());
            LastLoginStatus.a = z;
            intent.putExtra("com.alipay.security.withPwd", z);
            intent.putExtra("userId", userLoginResultBiz.getUserId());
            LastLoginStatus.b = bool.booleanValue();
            intent.putExtra("switchaccount", bool);
            if (!TextUtils.isEmpty(str)) {
                _log("registBindType ".concat(String.valueOf(str)));
                intent.putExtra("registbindtype", str);
            }
            LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this.mContext);
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mLastBroadcastSendTime;
            AliUserLog.c("LoginServiceImpl", "broadcast send timeSpan: ".concat(String.valueOf(elapsedRealtime)));
            if (elapsedRealtime < 2000) {
                HashMap hashMap = new HashMap();
                hashMap.put("extype", "loginbr");
                hashMap.put("exinfo", "login_broadcast_twice");
                hashMap.put("exremark", String.valueOf(elapsedRealtime));
                LoggerUtils.a("alilogin", hashMap);
            }
            this.mLastBroadcastSendTime = SystemClock.elapsedRealtime();
            instance.sendBroadcast(intent);
            setTaoBaoSsoFlag(true);
        } catch (Exception e) {
            AliUserLog.a((String) "LoginServiceImpl", (Throwable) e);
        }
    }

    private void setTaoBaoSsoFlag(boolean z) {
        AuthService authService = AntExtServiceManager.getAuthService(this.mContext);
        if (authService != null) {
            _log(String.format("setTaoBaoSsoFlag: %s", new Object[]{Boolean.valueOf(z)}));
            authService.setTaoBaoSsoFlag(z);
        }
    }

    public static void writeLoginLog(String str) {
        LogAgent.f("UC-ZHAQ-56", "loginTrace", str, "", "");
    }

    private void writeAutoLoginLog(UserLoginResult userLoginResult, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - j;
        if (elapsedRealtime <= 100000) {
            StringBuilder sb = new StringBuilder("免登结果, ");
            if (userLoginResult == null) {
                writeAutoLoginLog((String) "N-Error-UserLoginResult=null", elapsedRealtime);
                sb.append("N-Error-UserLoginResult=null");
            } else {
                writeAutoLoginLog(String.valueOf(userLoginResult.resultStatus), elapsedRealtime);
                sb.append("result:");
                sb.append(userLoginResult.resultStatus);
                sb.append(", memo:");
                sb.append(userLoginResult.memo);
            }
            AliUserLog.a((String) "LoginServiceImpl", sb.toString());
        }
    }

    private void writeAutoLoginLog(String str, long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("facade", "alipay.user.login");
        hashMap.put("timespan", String.valueOf(j));
        LogAgent.a((String) "UC-ZHAQ-56", (String) "loginTrace-autoLogin", (String) "", String.valueOf(j), str, (Map<String, String>) hashMap);
    }

    private void writeAutoLoginLog(long j, RpcException rpcException) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - j;
        if (elapsedRealtime <= 100000) {
            HashMap hashMap = new HashMap();
            hashMap.put("facade", "alipay.user.login");
            hashMap.put("timespan", String.valueOf(elapsedRealtime));
            hashMap.put("exceptionCode", String.valueOf(rpcException.getCode()));
            hashMap.put("exceptionMsg", rpcException.getMessage());
            LogAgent.a((String) "UC-ZHAQ-56", (String) "loginTrace-autoLogin", (String) "", String.valueOf(elapsedRealtime), (String) "N-RpcException", (Map<String, String>) hashMap);
        }
    }

    private void _log(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("[Thread:");
        sb.append(Thread.currentThread().getId());
        sb.append("] ");
        sb.append(str);
        AliUserLog.c("LoginServiceImpl", sb.toString());
    }
}
