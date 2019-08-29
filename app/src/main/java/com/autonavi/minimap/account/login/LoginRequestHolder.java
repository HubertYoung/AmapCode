package com.autonavi.minimap.account.login;

import com.alipay.mobile.mrtc.api.wwj.StreamerConstants;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.login.param.LoginAlipayParam;
import com.autonavi.minimap.account.login.param.LoginMobileParam;
import com.autonavi.minimap.account.login.param.LoginPWParam;
import com.autonavi.minimap.account.login.param.LoginQQParam;
import com.autonavi.minimap.account.login.param.LoginTaobaoParam;
import com.autonavi.minimap.account.login.param.LoginWeiboParam;
import com.autonavi.minimap.account.login.param.LoginWxParam;
import com.autonavi.minimap.account.login.param.ProfileGetParam;
import com.autonavi.minimap.account.login.param.ProfileUpdateParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class LoginRequestHolder {
    private static volatile LoginRequestHolder instance;
    private AosRequest mLoginAlipayRequest;
    private AosRequest mLoginMobileRequest;
    private AosRequest mLoginPWRequest;
    private AosRequest mLoginQQRequest;
    private AosRequest mLoginTaobaoRequest;
    private AosRequest mLoginWeiboRequest;
    private AosRequest mLoginWxRequest;
    private AosRequest mProfileGetRequest;
    private AosMultipartRequest mProfileUpdateRequest;

    private LoginRequestHolder() {
    }

    public static LoginRequestHolder getInstance() {
        if (instance == null) {
            synchronized (LoginRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new LoginRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendLoginAlipay(LoginAlipayParam loginAlipayParam, dko<chm> dko) {
        this.mLoginAlipayRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginAlipayRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/login/alipay/");
        aosRequest.setUrl(sb.toString());
        this.mLoginAlipayRequest.addSignParam("channel");
        this.mLoginAlipayRequest.addSignParam("code");
        this.mLoginAlipayRequest.addSignParam(Constants.KEY_MODE);
        this.mLoginAlipayRequest.addReqParam("limit_login", loginAlipayParam.limit_login);
        this.mLoginAlipayRequest.addReqParam("code", loginAlipayParam.code);
        this.mLoginAlipayRequest.addReqParam("token", loginAlipayParam.token);
        this.mLoginAlipayRequest.addReqParam("auto", Integer.toString(loginAlipayParam.autoValue));
        this.mLoginAlipayRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginAlipayParam.mode));
        this.mLoginAlipayRequest.addReqParam("env", loginAlipayParam.env);
        this.mLoginAlipayRequest.addReqParam("scope", loginAlipayParam.scope);
        in.a().a(this.mLoginAlipayRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginAlipay() {
        if (this.mLoginAlipayRequest != null) {
            in.a().a(this.mLoginAlipayRequest);
            this.mLoginAlipayRequest = null;
        }
    }

    public void sendLoginMobile(LoginMobileParam loginMobileParam, dko<chm> dko) {
        this.mLoginMobileRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginMobileRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/login/mobile/");
        aosRequest.setUrl(sb.toString());
        this.mLoginMobileRequest.addSignParam("channel");
        this.mLoginMobileRequest.addSignParam("mobile");
        this.mLoginMobileRequest.addSignParam("code");
        this.mLoginMobileRequest.addReqParam("mobile", loginMobileParam.mobile);
        this.mLoginMobileRequest.addReqParam("code", loginMobileParam.code);
        this.mLoginMobileRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginMobileParam.mode));
        this.mLoginMobileRequest.addReqParam("push_token", loginMobileParam.push_token);
        in.a().a(this.mLoginMobileRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginMobile() {
        if (this.mLoginMobileRequest != null) {
            in.a().a(this.mLoginMobileRequest);
            this.mLoginMobileRequest = null;
        }
    }

    public void sendLoginPW(LoginPWParam loginPWParam, dko<chm> dko) {
        this.mLoginPWRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginPWRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/login/");
        aosRequest.setUrl(sb.toString());
        this.mLoginPWRequest.addSignParam("channel");
        this.mLoginPWRequest.addSignParam("userid");
        this.mLoginPWRequest.addSignParam("password");
        this.mLoginPWRequest.addReqParam("userid", loginPWParam.userid);
        this.mLoginPWRequest.addReqParam("password", loginPWParam.password);
        this.mLoginPWRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginPWParam.mode));
        in.a().a(this.mLoginPWRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginPW() {
        if (this.mLoginPWRequest != null) {
            in.a().a(this.mLoginPWRequest);
            this.mLoginPWRequest = null;
        }
    }

    public void sendLoginQQ(LoginQQParam loginQQParam, dko<chm> dko) {
        this.mLoginQQRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginQQRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/login/qq/");
        aosRequest.setUrl(sb.toString());
        this.mLoginQQRequest.addSignParam("channel");
        this.mLoginQQRequest.addSignParam("token");
        this.mLoginQQRequest.addSignParam(Constants.KEY_MODE);
        this.mLoginQQRequest.addReqParam("limit_login", loginQQParam.limit_login);
        this.mLoginQQRequest.addReqParam("token", loginQQParam.token);
        this.mLoginQQRequest.addReqParam("code", loginQQParam.code);
        this.mLoginQQRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginQQParam.mode));
        in.a().a(this.mLoginQQRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginQQ() {
        if (this.mLoginQQRequest != null) {
            in.a().a(this.mLoginQQRequest);
            this.mLoginQQRequest = null;
        }
    }

    public void sendLoginTaobao(LoginTaobaoParam loginTaobaoParam, dko<chm> dko) {
        this.mLoginTaobaoRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginTaobaoRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/login/taobao/v2/");
        aosRequest.setUrl(sb.toString());
        this.mLoginTaobaoRequest.addSignParam("channel");
        this.mLoginTaobaoRequest.addSignParam("open_sid");
        this.mLoginTaobaoRequest.addSignParam(Constants.KEY_MODE);
        this.mLoginTaobaoRequest.addReqParam("limit_login", loginTaobaoParam.limit_login);
        this.mLoginTaobaoRequest.addReqParam("open_sid", loginTaobaoParam.open_sid);
        this.mLoginTaobaoRequest.addReqParam("key", loginTaobaoParam.key);
        this.mLoginTaobaoRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginTaobaoParam.mode));
        in.a().a(this.mLoginTaobaoRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginTaobao() {
        if (this.mLoginTaobaoRequest != null) {
            in.a().a(this.mLoginTaobaoRequest);
            this.mLoginTaobaoRequest = null;
        }
    }

    public void sendLoginWeibo(LoginWeiboParam loginWeiboParam, dko<chm> dko) {
        this.mLoginWeiboRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginWeiboRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/login/weibo/");
        aosRequest.setUrl(sb.toString());
        this.mLoginWeiboRequest.addSignParam("channel");
        this.mLoginWeiboRequest.addSignParam("token");
        this.mLoginWeiboRequest.addSignParam(Constants.KEY_MODE);
        this.mLoginWeiboRequest.addReqParam("limit_login", loginWeiboParam.limit_login);
        this.mLoginWeiboRequest.addReqParam("token", loginWeiboParam.token);
        this.mLoginWeiboRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginWeiboParam.mode));
        in.a().a(this.mLoginWeiboRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginWeibo() {
        if (this.mLoginWeiboRequest != null) {
            in.a().a(this.mLoginWeiboRequest);
            this.mLoginWeiboRequest = null;
        }
    }

    public void sendLoginWx(LoginWxParam loginWxParam, dko<chm> dko) {
        this.mLoginWxRequest = new AosPostRequest();
        AosRequest aosRequest = this.mLoginWxRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/login/weixin/");
        aosRequest.setUrl(sb.toString());
        this.mLoginWxRequest.addSignParam("channel");
        this.mLoginWxRequest.addSignParam("code");
        this.mLoginWxRequest.addSignParam(Constants.KEY_MODE);
        this.mLoginWxRequest.addReqParam("limit_login", loginWxParam.limit_login);
        this.mLoginWxRequest.addReqParam("code", loginWxParam.code);
        this.mLoginWxRequest.addReqParam("token", loginWxParam.token);
        this.mLoginWxRequest.addReqParam(Constants.KEY_MODE, Integer.toString(loginWxParam.mode));
        in.a().a(this.mLoginWxRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelLoginWx() {
        if (this.mLoginWxRequest != null) {
            in.a().a(this.mLoginWxRequest);
            this.mLoginWxRequest = null;
        }
    }

    public void sendProfileGet(ProfileGetParam profileGetParam, dko<chm> dko) {
        this.mProfileGetRequest = new AosGetRequest();
        AosRequest aosRequest = this.mProfileGetRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/profile/get/");
        aosRequest.setUrl(sb.toString());
        this.mProfileGetRequest.addSignParam("channel");
        this.mProfileGetRequest.addSignParam(Constants.KEY_MODE);
        this.mProfileGetRequest.addReqParam(Constants.KEY_MODE, Integer.toString(profileGetParam.mode));
        in.a().a(this.mProfileGetRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelProfileGet() {
        if (this.mProfileGetRequest != null) {
            in.a().a(this.mProfileGetRequest);
            this.mProfileGetRequest = null;
        }
    }

    public void sendProfileUpdate(ProfileUpdateParam profileUpdateParam, dko<chm> dko) {
        this.mProfileUpdateRequest = new AosMultipartRequest();
        AosMultipartRequest aosMultipartRequest = this.mProfileUpdateRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/profile/update/");
        aosMultipartRequest.setUrl(sb.toString());
        this.mProfileUpdateRequest.addSignParam("channel");
        this.mProfileUpdateRequest.addReqParam("gender", profileUpdateParam.gender);
        this.mProfileUpdateRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, profileUpdateParam.adcode);
        this.mProfileUpdateRequest.addReqParam("birthday", profileUpdateParam.birthday);
        this.mProfileUpdateRequest.addReqParam(StreamerConstants.DEFAULT_SIGNATURE, profileUpdateParam.signature);
        this.mProfileUpdateRequest.addReqParam("nickname", profileUpdateParam.nickname);
        this.mProfileUpdateRequest.addReqParam("avatarfield", profileUpdateParam.avatarfield);
        if (profileUpdateParam.avatar != null && profileUpdateParam.avatar.exists()) {
            this.mProfileUpdateRequest.addReqParam("avatarfield", "avatar");
            this.mProfileUpdateRequest.a((String) "avatar", profileUpdateParam.avatar);
        }
        in.a().a((AosRequest) this.mProfileUpdateRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelProfileUpdate() {
        if (this.mProfileUpdateRequest != null) {
            in.a().a((AosRequest) this.mProfileUpdateRequest);
            this.mProfileUpdateRequest = null;
        }
    }
}
