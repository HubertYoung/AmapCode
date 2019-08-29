package com.ali.user.mobile.service.impl;

import android.content.Context;
import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.info.DeviceInfo;
import com.ali.user.mobile.info.NetWorkInfo;
import com.ali.user.mobile.info.TidInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoginMonitor;
import com.ali.user.mobile.log.TimeConsumingLogAgent;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.MsgLoginParam;
import com.ali.user.mobile.rpc.facade.UnifyLoginFacade;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwReq;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwRes;
import com.ali.user.mobile.rpc.vo.mobilegw.login.ExternParams;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginType;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginWthPwd;
import com.ali.user.mobile.rpc.vo.mobilegw.login.SupplyLoginPwdReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.SupplyLoginPwdResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginReq;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginReqPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginResPb;
import com.ali.user.mobile.rsa.Rsa;
import com.ali.user.mobile.service.BaseBizService;
import com.ali.user.mobile.service.UserLoginService;
import com.ali.user.mobile.util.EdgeUtil;
import com.ali.user.mobile.util.ReflectUtils;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.model.CdmaModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.LocationInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.TelephoneInfo;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.RpcInvokeContext;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.huawei.android.pushagent.PushReceiver.BOUND_KEY;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UserLoginServiceImpl extends BaseBizService<UnifyLoginFacade> implements UserLoginService {
    private final AppInfo c = AppInfo.getInstance();
    private final DeviceInfo d = DeviceInfo.b();
    private final NetWorkInfo e;
    private final Map<String, LoginWthPwd> f = new HashMap();

    public UserLoginServiceImpl(Context context) {
        super(context);
        this.e = NetWorkInfo.a(context);
        this.f.put("withpwd", LoginWthPwd.withpwd);
        this.f.put("withchecktoken", LoginWthPwd.withchecktoken);
        this.f.put("withface", LoginWthPwd.withface);
        this.f.put("withmsg", LoginWthPwd.withmsg);
        this.f.put("withlogintoken", LoginWthPwd.withlogintoken);
        this.f.put("afterreg", LoginWthPwd.afterreg);
        this.f.put("withsso", LoginWthPwd.withsso);
        this.f.put("withonekeytoken", LoginWthPwd.withonekeytoken);
        this.f.put("withsndmsg", LoginWthPwd.withsndmsg);
        this.f.put("withtbsso", LoginWthPwd.withtbsso);
        this.f.put("withsndpwd", LoginWthPwd.withsndpwd);
        this.f.put("withopenauthtoken", LoginWthPwd.withopenauthtoken);
        this.f.put("withauthtoken", LoginWthPwd.withauthtoken);
    }

    public final UnifyLoginRes a(LoginParam loginParam) {
        UnifyLoginResPb b = b(loginParam);
        UnifyLoginRes unifyLoginRes = new UnifyLoginRes();
        unifyLoginRes.success = b.success.booleanValue();
        unifyLoginRes.code = b.code;
        unifyLoginRes.msg = b.msg;
        unifyLoginRes.token = b.token;
        unifyLoginRes.signData = b.signData;
        unifyLoginRes.ssoToken = b.ssoToken;
        unifyLoginRes.scene = b.scene;
        unifyLoginRes.hid = b.hid.longValue();
        unifyLoginRes.userId = b.userId;
        unifyLoginRes.taobaoUserId = b.taobaoUserId.longValue();
        unifyLoginRes.taobaoNick = b.taobaoNick;
        unifyLoginRes.headImg = b.headImg;
        unifyLoginRes.alipayLoginId = b.alipayLoginId;
        unifyLoginRes.tbLoginId = b.tbLoginId;
        unifyLoginRes.h5Url = b.h5Url;
        unifyLoginRes.checkCodeId = b.checkCodeId;
        unifyLoginRes.checkCodeUrl = b.checkCodeUrl;
        unifyLoginRes.data = b.resultData;
        unifyLoginRes.extMap = new HashMap();
        unifyLoginRes.extMap.put("sessionId", b.sessionId);
        if (b.extMap != null && b.extMap.size() > 0) {
            for (ExternParams next : b.extMap) {
                if (!(next == null || next.key == null)) {
                    unifyLoginRes.extMap.put(next.key, next.value);
                }
            }
        }
        if (!unifyLoginRes.success) {
            HashMap hashMap = new HashMap();
            hashMap.put("validateTpye", loginParam.validateTpye);
            LoginMonitor.a("MTBIZ_LOGIN", "PASSWORD_LOGIN", unifyLoginRes.code, hashMap);
        }
        return unifyLoginRes;
    }

    private UnifyLoginResPb b(LoginParam loginParam) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("UC-LOG-150512-T01", "unifyLoginPb");
        timeConsumingLogAgent.a().a("ali.user.gw.unifyLoginPb").c(loginParam.validateTpye).a((String) "loginType", loginParam.loginType).a((String) "validateTpye", loginParam.validateTpye);
        if (loginParam.monitorParams != null && !loginParam.monitorParams.isEmpty()) {
            timeConsumingLogAgent.a((String) ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, loginParam.monitorParams.get(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID));
            for (String next : loginParam.monitorParams.keySet()) {
                timeConsumingLogAgent.a(next, loginParam.monitorParams.get(next));
            }
        }
        try {
            UnifyLoginReqPb a = a(c(loginParam));
            String str = loginParam.loginAccount;
            RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
            UnifyLoginFacade unifyLoginFacade = (UnifyLoginFacade) rpcService.getRpcProxy(UnifyLoginFacade.class);
            RpcInvokeContext rpcInvokeContext = rpcService.getRpcInvokeContext(unifyLoginFacade);
            rpcInvokeContext.setRequestHeaders(a(str));
            rpcInvokeContext.setResetCookie(AliuserLoginContext.b());
            boolean z = true;
            AliUserLog.c("UserLoginServiceImpl", String.format("if this loginPb request resetCookie:%s", new Object[]{Boolean.valueOf(AliuserLoginContext.b())}));
            UnifyLoginResPb unifyLoginPb = unifyLoginFacade.unifyLoginPb(a);
            if (unifyLoginPb == null) {
                timeConsumingLogAgent.b().d("UnifyLoginResPb=null").b(loginParam.token).c();
            } else {
                timeConsumingLogAgent.b().d(unifyLoginPb.code).b(unifyLoginPb.token).c();
            }
            try {
                String str2 = a.loginId;
                String valueOf = String.valueOf(a.validateTpye.getValue());
                String str3 = unifyLoginPb == null ? "" : unifyLoginPb.userId;
                if (unifyLoginPb != null) {
                    if (!"200".equals(unifyLoginPb.code)) {
                        if ("1000".equals(unifyLoginPb.code)) {
                        }
                    }
                    EdgeUtil.a("login", str2, valueOf, str3, z);
                    return unifyLoginPb;
                }
                z = false;
                EdgeUtil.a("login", str2, valueOf, str3, z);
            } catch (Throwable th) {
                AliUserLog.b("UserLoginServiceImpl", "processEdgeAfterLogin", th);
            }
            return unifyLoginPb;
        } catch (RpcException e2) {
            TimeConsumingLogAgent.a(timeConsumingLogAgent, e2);
            timeConsumingLogAgent.b().c();
            throw e2;
        }
    }

    public final SupplyPassGwRes a(SupplyPassGwReq supplyPassGwReq) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("UC-LOG-150512-T01", "supplySimplePassword");
        timeConsumingLogAgent.a().a("ali.user.gw.login.supplysimplepass");
        try {
            if (supplyPassGwReq.externParams == null) {
                supplyPassGwReq.externParams = new HashMap();
                supplyPassGwReq.externParams.put("isPrisonBreak", String.valueOf(OutsideConfig.i()));
                supplyPassGwReq.externParams.put("mobileModel", com.alipay.android.phone.inside.common.info.DeviceInfo.a().j());
                supplyPassGwReq.externParams.put("isTrojan", String.valueOf(OutsideConfig.j()));
                supplyPassGwReq.externParams.put("currentOperateMobile", OutsideConfig.h());
            }
        } catch (Throwable th) {
            try {
                AliUserLog.a("UserLoginServiceImpl", "login-supply six getSecurityParams error", th);
            } catch (RpcException e2) {
                TimeConsumingLogAgent.a(timeConsumingLogAgent, e2);
                timeConsumingLogAgent.b().c();
                throw e2;
            }
        }
        RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
        UnifyLoginFacade unifyLoginFacade = (UnifyLoginFacade) rpcService.getRpcProxy(UnifyLoginFacade.class);
        rpcService.getRpcInvokeContext(unifyLoginFacade).setRequestHeaders(a((String) null));
        SupplyPassGwRes supplySimplePassword = unifyLoginFacade.supplySimplePassword(supplyPassGwReq);
        if (supplySimplePassword == null) {
            timeConsumingLogAgent.b().d("SupplyPassGwRes=null").b(supplyPassGwReq.token).c();
        } else {
            timeConsumingLogAgent.b().d(supplySimplePassword.code).b(supplySimplePassword.token).c();
        }
        return supplySimplePassword;
    }

    public final LoginSendMSGResPb a(MsgLoginParam msgLoginParam) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("UC-LOG-150512-T01", "initMsgLogin");
        timeConsumingLogAgent.a().a("ali.user.gw.unifyLogin.sendMsg");
        try {
            LoginSendMSGReqPb loginSendMSGReqPb = new LoginSendMSGReqPb();
            loginSendMSGReqPb.apdid = AppInfo.getInstance().getApdid();
            loginSendMSGReqPb.devKeySet = AppInfo.getInstance().getDeviceKeySet();
            loginSendMSGReqPb.envJson = msgLoginParam.envJson;
            loginSendMSGReqPb.loginId = msgLoginParam.loginId;
            loginSendMSGReqPb.productId = AppInfo.getInstance().getProductId();
            loginSendMSGReqPb.productVersion = AppInfo.getInstance().getProductVersion();
            loginSendMSGReqPb.sdkVersion = AppInfo.getInstance().getSdkVersion();
            loginSendMSGReqPb.securityId = msgLoginParam.verifyId;
            loginSendMSGReqPb.umidtoken = AppInfo.getInstance().getUmid();
            DeviceInfo.b();
            loginSendMSGReqPb.utdid = DeviceInfo.g();
            loginSendMSGReqPb.token = msgLoginParam.token;
            TidInfo tidInfo = AppInfo.getInstance().getTidInfo();
            if (tidInfo != null) {
                loginSendMSGReqPb.tid = tidInfo.a();
            }
            LoginSendMSGResPb initMsgLogin = ((UnifyLoginFacade) this.b).initMsgLogin(loginSendMSGReqPb);
            if (initMsgLogin == null) {
                timeConsumingLogAgent.b().d("LoginSendMSGResPb=null").c();
            } else {
                timeConsumingLogAgent.b().d(initMsgLogin.resultCode).c();
            }
            return initMsgLogin;
        } catch (RpcException e2) {
            TimeConsumingLogAgent.a(timeConsumingLogAgent, e2);
            timeConsumingLogAgent.b().c();
            throw e2;
        }
    }

    public final SupplyLoginPwdResPb a(String str, String str2, String str3) {
        TimeConsumingLogAgent timeConsumingLogAgent = new TimeConsumingLogAgent("UC-LOG-150512-T01", "supplyLoginPwd");
        timeConsumingLogAgent.a().a("ali.user.gw.unifyLogin.supplyLoginPwd");
        try {
            SupplyLoginPwdReqPb supplyLoginPwdReqPb = new SupplyLoginPwdReqPb();
            supplyLoginPwdReqPb.loginId = str;
            supplyLoginPwdReqPb.scene = str2;
            supplyLoginPwdReqPb.queryPassword = str3;
            supplyLoginPwdReqPb.apdid = AppInfo.getInstance().getApdid();
            supplyLoginPwdReqPb.devKeySet = AppInfo.getInstance().getDeviceKeySet();
            supplyLoginPwdReqPb.productId = AppInfo.getInstance().getProductId();
            supplyLoginPwdReqPb.productVersion = AppInfo.getInstance().getProductVersion();
            supplyLoginPwdReqPb.sdkVersion = AppInfo.getInstance().getSdkVersion();
            supplyLoginPwdReqPb.umidtoken = AppInfo.getInstance().getUmid();
            DeviceInfo.b();
            supplyLoginPwdReqPb.utdid = DeviceInfo.g();
            TidInfo tidInfo = AppInfo.getInstance().getTidInfo();
            if (tidInfo != null) {
                supplyLoginPwdReqPb.tid = tidInfo.a();
            }
            if (supplyLoginPwdReqPb.externParams == null) {
                supplyLoginPwdReqPb.externParams = new ArrayList();
            }
            List<ExternParams> list = supplyLoginPwdReqPb.externParams;
            try {
                ExternParams externParams = new ExternParams();
                externParams.key = "isPrisonBreak";
                externParams.value = String.valueOf(OutsideConfig.i());
                list.add(externParams);
                ExternParams externParams2 = new ExternParams();
                externParams2.key = "mobileModel";
                externParams2.value = com.alipay.android.phone.inside.common.info.DeviceInfo.a().j();
                list.add(externParams2);
                ExternParams externParams3 = new ExternParams();
                externParams3.key = "isTrojan";
                externParams3.value = String.valueOf(OutsideConfig.j());
                list.add(externParams3);
                ExternParams externParams4 = new ExternParams();
                externParams4.key = "currentOperateMobile";
                externParams4.value = OutsideConfig.h();
                list.add(externParams4);
            } catch (Throwable th) {
                AliUserLog.a("UserLoginServiceImpl", "login-supply login getSecurityParams error", th);
            }
            SupplyLoginPwdResPb supplyLoginPwd = ((UnifyLoginFacade) this.b).supplyLoginPwd(supplyLoginPwdReqPb);
            if (supplyLoginPwd == null) {
                timeConsumingLogAgent.b().d("SupplyLoginPwdResPb=null").c();
            } else {
                timeConsumingLogAgent.b().d(supplyLoginPwd.resultCode).c();
            }
            return supplyLoginPwd;
        } catch (RpcException e2) {
            TimeConsumingLogAgent.a(timeConsumingLogAgent, e2);
            timeConsumingLogAgent.b().c();
            throw e2;
        }
    }

    private UnifyLoginReqPb a(UnifyLoginReq unifyLoginReq) {
        LoginWthPwd loginWthPwd;
        UnifyLoginReqPb unifyLoginReqPb = new UnifyLoginReqPb();
        unifyLoginReqPb.loginId = unifyLoginReq.loginId;
        unifyLoginReqPb.loginPwd = unifyLoginReq.loginPwd;
        unifyLoginReqPb.tid = unifyLoginReq.tid;
        unifyLoginReqPb.ttid = unifyLoginReq.tid;
        unifyLoginReqPb.accessPoint = unifyLoginReq.accessPoint;
        unifyLoginReqPb.apdid = unifyLoginReq.apdid;
        unifyLoginReqPb.appId = unifyLoginReq.appId;
        unifyLoginReqPb.appKey = unifyLoginReq.appKey;
        unifyLoginReqPb.deviceId = unifyLoginReq.deviceId;
        unifyLoginReqPb.cellId = unifyLoginReq.cellId;
        unifyLoginReqPb.channel = unifyLoginReq.channel;
        unifyLoginReqPb.clientPostion = unifyLoginReq.location;
        unifyLoginReqPb.clientType = unifyLoginReq.clientType;
        unifyLoginReqPb.alipayEnvJson = unifyLoginReq.alipayEnvJson;
        unifyLoginReqPb.taobaoEnvJson = unifyLoginReq.taobaoEnvJson;
        unifyLoginReqPb.imei = unifyLoginReq.IMEI;
        unifyLoginReqPb.imsi = unifyLoginReq.IMSI;
        unifyLoginReqPb.isPrisonBreak = unifyLoginReq.isPrisonBreak;
        unifyLoginReqPb.lacId = unifyLoginReq.lacId;
        unifyLoginReqPb.checkCodeId = unifyLoginReq.checkCodeId;
        unifyLoginReqPb.checkCode = unifyLoginReq.checkCode;
        unifyLoginReqPb.mobileBrand = unifyLoginReq.mobileBrand;
        unifyLoginReqPb.mobileModel = unifyLoginReq.mobileModel;
        unifyLoginReqPb.sdkVersion = unifyLoginReq.sdkVersion;
        unifyLoginReqPb.productId = unifyLoginReq.productId;
        unifyLoginReqPb.productVersion = unifyLoginReq.productVersion;
        unifyLoginReqPb.scene = unifyLoginReq.scene;
        unifyLoginReqPb.token = unifyLoginReq.token;
        unifyLoginReqPb.signData = unifyLoginReq.signData;
        unifyLoginReqPb.ssoToken = unifyLoginReq.ssoToken;
        unifyLoginReqPb.systemType = unifyLoginReq.systemType;
        unifyLoginReqPb.systemVersion = unifyLoginReq.systemVersion;
        unifyLoginReqPb.umidToken = unifyLoginReq.umidToken;
        unifyLoginReqPb.userAgent = unifyLoginReq.userAgent;
        unifyLoginReqPb.utdid = unifyLoginReq.utdid;
        unifyLoginReqPb.wifiMac = unifyLoginReq.mac;
        unifyLoginReqPb.wifiNodeName = unifyLoginReq.wifiNodeName;
        unifyLoginReqPb.loginType = LoginConstants.TAOBAO_LOGIN.equalsIgnoreCase(unifyLoginReq.loginType) ? LoginType.taobao : LoginType.alipay;
        String str = unifyLoginReq.validateTpye;
        if (this.f.containsKey(str)) {
            loginWthPwd = this.f.get(str);
        } else {
            loginWthPwd = LoginWthPwd.withsndpwd;
        }
        unifyLoginReqPb.validateTpye = loginWthPwd;
        try {
            unifyLoginReqPb.screenHigh = Integer.valueOf(unifyLoginReq.screenHigh);
            unifyLoginReqPb.screenWidth = Integer.valueOf(unifyLoginReq.screenWidth);
        } catch (NumberFormatException e2) {
            AliUserLog.b((String) "UserLoginServiceImpl", (Throwable) e2);
        }
        if (unifyLoginReq.externParams != null && unifyLoginReq.externParams.size() > 0) {
            unifyLoginReqPb.externParams = new LinkedList();
            for (String next : unifyLoginReq.externParams.keySet()) {
                unifyLoginReqPb.externParams.add(a(next, unifyLoginReq.externParams.get(next)));
            }
        }
        if (unifyLoginReq.appData != null && unifyLoginReq.appData.size() > 0) {
            unifyLoginReqPb.appData = new LinkedList();
            for (String next2 : unifyLoginReq.appData.keySet()) {
                unifyLoginReqPb.appData.add(a(next2, unifyLoginReq.appData.get(next2)));
            }
        }
        return unifyLoginReqPb;
    }

    private static ExternParams a(String str, String str2) {
        ExternParams externParams = new ExternParams();
        externParams.key = str;
        externParams.value = str2;
        return externParams;
    }

    private UnifyLoginReq c(LoginParam loginParam) {
        String str;
        UnifyLoginReq unifyLoginReq = new UnifyLoginReq();
        try {
            RSAPKeyResult a = Rsa.a(this.a);
            if (loginParam.loginPassword != null) {
                unifyLoginReq.loginPwd = Rsa.a(loginParam.loginPassword, a.rsaPK);
            }
        } catch (RpcException e2) {
            AliUserLog.a((String) "UserLoginServiceImpl", (Throwable) e2);
        } catch (Exception e3) {
            AliUserLog.a("UserLoginServiceImpl", "密码加密失败", e3);
        }
        LocationInfo c2 = OutsideConfig.c();
        if (c2 == null) {
            str = "";
        } else {
            str = String.format("%s;%s;%s", new Object[]{c2.d(), c2.e(), c2.a()});
        }
        unifyLoginReq.location = str;
        unifyLoginReq.accessPoint = this.e.b();
        unifyLoginReq.appId = "ALIPAY";
        unifyLoginReq.appKey = this.c.getAppKey(this.a);
        unifyLoginReq.deviceId = this.c.getDeviceId();
        TelephoneInfo e4 = OutsideConfig.e();
        if (e4 != null) {
            List<GsmModel> a2 = e4.a();
            CdmaModel b = e4.b();
            if (a2 != null && !a2.isEmpty()) {
                unifyLoginReq.lacId = a2.get(0) == null ? "" : a2.get(0).d();
                unifyLoginReq.cellId = a2.get(0) == null ? "" : a2.get(0).c();
            }
            if (b != null) {
                unifyLoginReq.lacId = b.a();
                unifyLoginReq.cellId = b.a();
            }
        }
        unifyLoginReq.channel = this.c.getChannel();
        unifyLoginReq.clientType = "android";
        unifyLoginReq.alipayEnvJson = loginParam.alipayEnvJson;
        unifyLoginReq.taobaoEnvJson = loginParam.taobaoEnvJson;
        unifyLoginReq.IMEI = DeviceInfo.c();
        unifyLoginReq.IMSI = DeviceInfo.d();
        unifyLoginReq.isPrisonBreak = String.valueOf(OutsideConfig.i());
        unifyLoginReq.loginId = loginParam.loginAccount;
        unifyLoginReq.checkCode = loginParam.smsCode;
        unifyLoginReq.loginType = loginParam.loginType;
        unifyLoginReq.mobileBrand = com.alipay.android.phone.inside.common.info.DeviceInfo.a().i();
        unifyLoginReq.mobileModel = com.alipay.android.phone.inside.common.info.DeviceInfo.a().j();
        unifyLoginReq.sdkVersion = this.c.getSdkVersion();
        unifyLoginReq.productId = this.c.getProductId();
        unifyLoginReq.productVersion = this.c.getProductVersion();
        unifyLoginReq.token = loginParam.token;
        unifyLoginReq.ssoToken = loginParam.ssoToken;
        StringBuilder sb = new StringBuilder();
        sb.append(DeviceInfo.f());
        unifyLoginReq.screenHigh = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(DeviceInfo.e());
        unifyLoginReq.screenWidth = sb2.toString();
        unifyLoginReq.systemType = "android";
        com.alipay.android.phone.inside.common.info.DeviceInfo.a();
        unifyLoginReq.systemVersion = com.alipay.android.phone.inside.common.info.DeviceInfo.n();
        com.alipay.android.phone.inside.common.info.DeviceInfo.a();
        unifyLoginReq.userAgent = com.alipay.android.phone.inside.common.info.DeviceInfo.o();
        unifyLoginReq.utdid = DeviceInfo.g();
        unifyLoginReq.validateTpye = loginParam.validateTpye;
        unifyLoginReq.mac = this.e.a();
        unifyLoginReq.wifiNodeName = this.e.b();
        unifyLoginReq.externParams = loginParam.externParams;
        if (unifyLoginReq.externParams == null) {
            unifyLoginReq.externParams = new HashMap();
        }
        unifyLoginReq.externParams.put("devKeySet", this.c.getDeviceKeySet());
        unifyLoginReq.externParams.put("netType", NetWorkInfo.b(this.a));
        try {
            unifyLoginReq.externParams.put("isTrojan", String.valueOf(OutsideConfig.j()));
            unifyLoginReq.externParams.put("currentOperateMobile", OutsideConfig.h());
            unifyLoginReq.externParams.put("edgeData", EdgeUtil.a("login", loginParam.validateTpye, loginParam.loginAccount, ""));
        } catch (Throwable th) {
            AliUserLog.a("UserLoginServiceImpl", "unifylogin get security param error", th);
        }
        TokenResult tokenResult = this.c.getTokenResult();
        if (tokenResult != null) {
            unifyLoginReq.umidToken = tokenResult.umidToken;
            unifyLoginReq.apdid = tokenResult.apdid;
            if (TextUtils.isEmpty(tokenResult.apdid)) {
                unifyLoginReq.externParams.put("apdidDowngrade", "Y");
            } else {
                unifyLoginReq.externParams.put("apdidDowngrade", tokenResult.apdid.equals(tokenResult.clientKey) ? "Y" : "N");
            }
        }
        unifyLoginReq.appData = new HashMap();
        unifyLoginReq.appData.put("clientId", com.alipay.android.phone.inside.common.info.DeviceInfo.a().r());
        unifyLoginReq.appData.put(Constants.SERVICE_SOURCE_ID, "");
        unifyLoginReq.appData.put(BOUND_KEY.deviceTokenKey, "");
        Map<String, String> map = unifyLoginReq.appData;
        com.alipay.android.phone.inside.common.info.DeviceInfo.a();
        map.put("osVersion", com.alipay.android.phone.inside.common.info.DeviceInfo.n());
        Map<String, String> map2 = unifyLoginReq.appData;
        com.alipay.android.phone.inside.common.info.DeviceInfo.a();
        map2.put("terminalName", com.alipay.android.phone.inside.common.info.DeviceInfo.o());
        unifyLoginReq.appData.put("loginWthPwd", loginParam.validateTpye);
        AliUserLog.c("UserLoginServiceImpl", "addDexInfo > ".concat(String.valueOf(unifyLoginReq)));
        try {
            TidInfo tidInfo = this.c.getTidInfo();
            if (tidInfo != null) {
                unifyLoginReq.tid = tidInfo.a();
                unifyLoginReq.appData.put("vimsi", tidInfo.f());
                unifyLoginReq.appData.put("vimei", tidInfo.e());
                unifyLoginReq.appData.put("mspImsi", tidInfo.d());
                unifyLoginReq.appData.put("mspImei", tidInfo.c());
                unifyLoginReq.appData.put("mspTid", tidInfo.a());
                unifyLoginReq.appData.put("mspClientKey", tidInfo.b());
                unifyLoginReq.appData.put("walletTid", tidInfo.a());
                unifyLoginReq.appData.put("walletClientKey", tidInfo.b());
            }
        } catch (Exception e5) {
            AliUserLog.a((String) "UserLoginServiceImpl", (Throwable) e5);
        }
        return unifyLoginReq;
    }

    private static Map<String, String> a(String str) {
        HashMap hashMap = new HashMap();
        try {
            hashMap.put(DictionaryKeys.DEV_APDIDTOKEN, (String) ReflectUtils.a(ReflectUtils.a((String) "com.ali.user.mobile.info.AppInfo", (String) "getInstance"), (String) "getApdid"));
        } catch (Throwable th) {
            AliUserLog.b((String) "UserLoginServiceImpl", th);
        }
        try {
            hashMap.put("aliusergw", "1");
        } catch (Throwable th2) {
            AliUserLog.b((String) "UserLoginServiceImpl", th2);
        }
        try {
            if (!TextUtils.isEmpty(str) && str.matches("^([a-zA-Z0-9_\\.\\-\\+])+\\@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,20}$|^\\d{11}$|^([0-9])+\\-([0-9])+$")) {
                AliUserLog.c("UserLoginServiceImpl", String.format("%s match the regex, add to header", new Object[]{str}));
                hashMap.put("loginid", str);
            }
        } catch (Throwable th3) {
            AliUserLog.b((String) "UserLoginServiceImpl", th3);
        }
        return hashMap;
    }
}
