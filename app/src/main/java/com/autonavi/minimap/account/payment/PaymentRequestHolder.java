package com.autonavi.minimap.account.payment;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.account.payment.model.PaymentMobileResponse;
import com.autonavi.minimap.account.payment.param.PaymentMobileParam;
import com.autonavi.minimap.falcon.base.FalconAosHttpResponseCallBack;
import com.taobao.accs.common.Constants;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class PaymentRequestHolder {
    private static volatile PaymentRequestHolder instance;
    private AosRequest mPaymentMobileRequest;

    private PaymentRequestHolder() {
    }

    public static PaymentRequestHolder getInstance() {
        if (instance == null) {
            synchronized (PaymentRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new PaymentRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendPaymentMobile(PaymentMobileParam paymentMobileParam, dko<PaymentMobileResponse> dko) {
        this.mPaymentMobileRequest = new AosPostRequest();
        AosRequest aosRequest = this.mPaymentMobileRequest;
        StringBuilder sb = new StringBuilder();
        sb.append(dkp.a(ConfigerHelper.AOS_PASSPORT_URL_KEY));
        sb.append("/ws/pp/account/payment-mobile/");
        aosRequest.setUrl(sb.toString());
        this.mPaymentMobileRequest.addSignParam("channel");
        this.mPaymentMobileRequest.addSignParam("mobile");
        this.mPaymentMobileRequest.addSignParam("code");
        this.mPaymentMobileRequest.addSignParam("verify_token");
        this.mPaymentMobileRequest.addReqParam("mobile", paymentMobileParam.mobile);
        this.mPaymentMobileRequest.addReqParam("code", paymentMobileParam.code);
        this.mPaymentMobileRequest.addReqParam("verify_token", paymentMobileParam.verifyToken);
        this.mPaymentMobileRequest.addReqParam(Constants.KEY_MODE, String.valueOf(paymentMobileParam.mode));
        this.mPaymentMobileRequest.addReqParam("replace_type", Integer.toString(paymentMobileParam.replaceType));
        this.mPaymentMobileRequest.addReqParam("push_token", paymentMobileParam.pushToken);
        in.a().a(this.mPaymentMobileRequest, (AosResponseCallback<T>) new FalconAosHttpResponseCallBack<PaymentMobileResponse, dko>(dko) {
            public final /* synthetic */ dkm a() {
                return new PaymentMobileResponse();
            }
        });
    }

    public void cancelPaymentMobile() {
        if (this.mPaymentMobileRequest != null) {
            in.a().a(this.mPaymentMobileRequest);
            this.mPaymentMobileRequest = null;
        }
    }
}
