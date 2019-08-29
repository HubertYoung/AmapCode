package com.autonavi.minimap.account.unbind;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.unbind.model.UnbindResponse;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class UnBindRequestHolder {
    private static volatile UnBindRequestHolder instance;
    private AosRequest mUnBindAlipayRequest;
    private AosRequest mUnBindEmailRequest;
    private AosRequest mUnBindMobileRequest;
    private AosRequest mUnBindQQRequest;
    private AosRequest mUnBindTabobaoRequest;
    private AosRequest mUnBindWeiboRequest;
    private AosRequest mUnBindWxRequest;

    private UnBindRequestHolder() {
    }

    public static UnBindRequestHolder getInstance() {
        if (instance == null) {
            synchronized (UnBindRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new UnBindRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendUnBindAlipay(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindAlipayRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindAlipayRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/unbind/alipay/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindAlipayRequest.addSignParam("channel");
        this.mUnBindAlipayRequest.addReqParam("check", chv.a);
        this.mUnBindAlipayRequest.addReqParam("env", chv.b);
        in.a().a(this.mUnBindAlipayRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindAlipay() {
        if (this.mUnBindAlipayRequest != null) {
            in.a().a(this.mUnBindAlipayRequest);
            this.mUnBindAlipayRequest = null;
        }
    }

    public void sendUnBindMobile(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindMobileRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindMobileRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/contact/unbind/mobile/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindMobileRequest.addSignParam("channel");
        this.mUnBindMobileRequest.addReqParam("check", chv.a);
        in.a().a(this.mUnBindMobileRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindMobile() {
        if (this.mUnBindMobileRequest != null) {
            in.a().a(this.mUnBindMobileRequest);
            this.mUnBindMobileRequest = null;
        }
    }

    public void sendUnBindEmail(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindEmailRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindEmailRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/contact/unbind/email/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindEmailRequest.addSignParam("channel");
        this.mUnBindEmailRequest.addReqParam("check", chv.a);
        in.a().a(this.mUnBindEmailRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindEmail() {
        if (this.mUnBindEmailRequest != null) {
            in.a().a(this.mUnBindEmailRequest);
            this.mUnBindEmailRequest = null;
        }
    }

    public void sendUnBindWeibo(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindWeiboRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindWeiboRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/unbind/weibo/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindWeiboRequest.addSignParam("channel");
        this.mUnBindWeiboRequest.addReqParam("check", chv.a);
        in.a().a(this.mUnBindWeiboRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindWeibo() {
        if (this.mUnBindWeiboRequest != null) {
            in.a().a(this.mUnBindWeiboRequest);
            this.mUnBindWeiboRequest = null;
        }
    }

    public void sendUnBindTabobao(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindTabobaoRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindTabobaoRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/unbind/taobao/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindTabobaoRequest.addSignParam("channel");
        this.mUnBindTabobaoRequest.addReqParam("check", chv.a);
        in.a().a(this.mUnBindTabobaoRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindTabobao() {
        if (this.mUnBindTabobaoRequest != null) {
            in.a().a(this.mUnBindTabobaoRequest);
            this.mUnBindTabobaoRequest = null;
        }
    }

    public void sendUnBindQQ(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindQQRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindQQRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/unbind/qq/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindQQRequest.addSignParam("channel");
        this.mUnBindQQRequest.addReqParam("check", chv.a);
        in.a().a(this.mUnBindQQRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindQQ() {
        if (this.mUnBindQQRequest != null) {
            in.a().a(this.mUnBindQQRequest);
            this.mUnBindQQRequest = null;
        }
    }

    public void sendUnBindWx(chv chv, dko<UnbindResponse> dko) {
        this.mUnBindWxRequest = new AosPostRequest();
        AosRequest aosRequest = this.mUnBindWxRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/unbind/weixin/");
        aosRequest.setUrl(sb.toString());
        this.mUnBindWxRequest.addSignParam("channel");
        this.mUnBindWxRequest.addReqParam("check", chv.a);
        in.a().a(this.mUnBindWxRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<UnbindResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new UnbindResponse();
            }
        });
    }

    public void cancelUnBindWx() {
        if (this.mUnBindWxRequest != null) {
            in.a().a(this.mUnBindWxRequest);
            this.mUnBindWxRequest = null;
        }
    }
}
