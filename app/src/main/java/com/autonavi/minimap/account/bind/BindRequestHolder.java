package com.autonavi.minimap.account.bind;

import android.support.v4.app.NotificationCompat;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.account.bind.model.BindInfoResponse;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class BindRequestHolder {
    private static volatile BindRequestHolder instance;
    private AosRequest mBindAlipayRequest;
    private AosRequest mBindEmailRequest;
    private AosRequest mBindInfoRequest;
    private AosRequest mBindMobileRequest;
    private AosRequest mBindQQRequest;
    private AosRequest mBindTaobaoRequest;
    private AosRequest mBindWeiboRequest;
    private AosRequest mBindWxRequest;

    private BindRequestHolder() {
    }

    public static BindRequestHolder getInstance() {
        if (instance == null) {
            synchronized (BindRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new BindRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendBindAlipay(chn chn, dko<chm> dko) {
        this.mBindAlipayRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindAlipayRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/bind/alipay/");
        aosRequest.setUrl(sb.toString());
        this.mBindAlipayRequest.addSignParam("channel");
        this.mBindAlipayRequest.addSignParam("code");
        this.mBindAlipayRequest.addReqParam("env", chn.a);
        this.mBindAlipayRequest.addReqParam("code", chn.b);
        this.mBindAlipayRequest.addReqParam("token", chn.c);
        this.mBindAlipayRequest.addReqParam(Constants.KEY_MODE, Integer.toString(chn.d));
        this.mBindAlipayRequest.addReqParam("type", Integer.toString(chn.e));
        this.mBindAlipayRequest.addReqParam("replace_type", Integer.toString(chn.f));
        this.mBindAlipayRequest.addReqParam("update_mode", Integer.toString(chn.g));
        this.mBindAlipayRequest.addReqParam("scope", chn.h);
        in.a().a(this.mBindAlipayRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindAlipay() {
        if (this.mBindAlipayRequest != null) {
            in.a().a(this.mBindAlipayRequest);
            this.mBindAlipayRequest = null;
        }
    }

    public void sendBindQQ(chr chr, dko<chm> dko) {
        this.mBindQQRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindQQRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/bind/qq/");
        aosRequest.setUrl(sb.toString());
        this.mBindQQRequest.addSignParam("channel");
        this.mBindQQRequest.addSignParam("code");
        this.mBindQQRequest.addSignParam("token");
        this.mBindQQRequest.addReqParam("code", chr.a);
        this.mBindQQRequest.addReqParam("token", chr.b);
        this.mBindQQRequest.addReqParam(Constants.KEY_MODE, Integer.toString(chr.c));
        this.mBindQQRequest.addReqParam("type", Integer.toString(chr.d));
        this.mBindQQRequest.addReqParam("replace_type", Integer.toString(chr.e));
        in.a().a(this.mBindQQRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindQQ() {
        if (this.mBindQQRequest != null) {
            in.a().a(this.mBindQQRequest);
            this.mBindQQRequest = null;
        }
    }

    public void sendBindTaobao(chs chs, dko<chm> dko) {
        this.mBindTaobaoRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindTaobaoRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/bind/taobao/v2/");
        aosRequest.setUrl(sb.toString());
        this.mBindTaobaoRequest.addSignParam("channel");
        this.mBindTaobaoRequest.addSignParam("open_sid");
        this.mBindTaobaoRequest.addReqParam("key", chs.a);
        this.mBindTaobaoRequest.addReqParam("open_sid", chs.b);
        this.mBindTaobaoRequest.addReqParam(Constants.KEY_MODE, Integer.toString(chs.c));
        this.mBindTaobaoRequest.addReqParam("type", Integer.toString(chs.d));
        this.mBindTaobaoRequest.addReqParam("replace_type", Integer.toString(chs.e));
        this.mBindTaobaoRequest.addReqParam("update_mode", Integer.toString(chs.f));
        in.a().a(this.mBindTaobaoRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindTaobao() {
        if (this.mBindTaobaoRequest != null) {
            in.a().a(this.mBindTaobaoRequest);
            this.mBindTaobaoRequest = null;
        }
    }

    public void sendBindWeibo(cht cht, dko<chm> dko) {
        this.mBindWeiboRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindWeiboRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/bind/weibo/");
        aosRequest.setUrl(sb.toString());
        this.mBindWeiboRequest.addSignParam("channel");
        this.mBindWeiboRequest.addSignParam("code");
        this.mBindWeiboRequest.addSignParam("token");
        this.mBindWeiboRequest.addReqParam("env", cht.a);
        this.mBindWeiboRequest.addReqParam("code", cht.b);
        this.mBindWeiboRequest.addReqParam("token", cht.c);
        this.mBindWeiboRequest.addReqParam(Constants.KEY_MODE, Integer.toString(cht.d));
        this.mBindWeiboRequest.addReqParam("type", Integer.toString(cht.e));
        this.mBindWeiboRequest.addReqParam("replace_type", Integer.toString(cht.f));
        this.mBindWeiboRequest.addReqParam("update_mode", Integer.toString(cht.g));
        in.a().a(this.mBindWeiboRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindWeibo() {
        if (this.mBindWeiboRequest != null) {
            in.a().a(this.mBindWeiboRequest);
            this.mBindWeiboRequest = null;
        }
    }

    public void sendBindWx(chu chu, dko<chm> dko) {
        this.mBindWxRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindWxRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/provider/bind/weixin/");
        aosRequest.setUrl(sb.toString());
        this.mBindWxRequest.addSignParam("channel");
        this.mBindWxRequest.addSignParam("code");
        this.mBindWxRequest.addReqParam("code", chu.a);
        this.mBindWxRequest.addReqParam("token", chu.b);
        this.mBindWxRequest.addReqParam(Constants.KEY_MODE, Integer.toString(chu.c));
        this.mBindWxRequest.addReqParam("type", Integer.toString(chu.d));
        this.mBindWxRequest.addReqParam("replace_type", Integer.toString(chu.e));
        in.a().a(this.mBindWxRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindWx() {
        if (this.mBindWxRequest != null) {
            in.a().a(this.mBindWxRequest);
            this.mBindWxRequest = null;
        }
    }

    public void sendBindMobile(chq chq, dko<chm> dko) {
        this.mBindMobileRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindMobileRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/contact/bind/mobile/");
        aosRequest.setUrl(sb.toString());
        this.mBindMobileRequest.addSignParam("channel");
        this.mBindMobileRequest.addSignParam("mobile");
        this.mBindMobileRequest.addSignParam("code");
        this.mBindMobileRequest.addReqParam("mobile", chq.a);
        this.mBindMobileRequest.addReqParam("code", chq.b);
        this.mBindMobileRequest.addReqParam("replace_type", Integer.toString(chq.c));
        in.a().a(this.mBindMobileRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindMobile() {
        if (this.mBindMobileRequest != null) {
            in.a().a(this.mBindMobileRequest);
            this.mBindMobileRequest = null;
        }
    }

    public void sendBindEmail(cho cho, dko<chm> dko) {
        this.mBindEmailRequest = new AosPostRequest();
        AosRequest aosRequest = this.mBindEmailRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/contact/bind/email/");
        aosRequest.setUrl(sb.toString());
        this.mBindEmailRequest.addSignParam("channel");
        this.mBindEmailRequest.addSignParam(NotificationCompat.CATEGORY_EMAIL);
        this.mBindEmailRequest.addSignParam("code");
        this.mBindEmailRequest.addReqParam(NotificationCompat.CATEGORY_EMAIL, cho.a);
        this.mBindEmailRequest.addReqParam("code", cho.b);
        this.mBindEmailRequest.addReqParam("replace_type", Integer.toString(cho.c));
        in.a().a(this.mBindEmailRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<chm, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new chm();
            }
        });
    }

    public void cancelBindEmail() {
        if (this.mBindEmailRequest != null) {
            in.a().a(this.mBindEmailRequest);
            this.mBindEmailRequest = null;
        }
    }

    public void sendBindInfo(chp chp, dko<BindInfoResponse> dko) {
        this.mBindInfoRequest = new AosGetRequest();
        AosRequest aosRequest = this.mBindInfoRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/bind/info/");
        aosRequest.setUrl(sb.toString());
        this.mBindInfoRequest.addSignParam("channel");
        this.mBindInfoRequest.addSignParam(LocationParams.PARA_COMMON_ADIU);
        in.a().a(this.mBindInfoRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<BindInfoResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new BindInfoResponse();
            }
        });
    }

    public void cancelBindInfo() {
        if (this.mBindInfoRequest != null) {
            in.a().a(this.mBindInfoRequest);
            this.mBindInfoRequest = null;
        }
    }
}
