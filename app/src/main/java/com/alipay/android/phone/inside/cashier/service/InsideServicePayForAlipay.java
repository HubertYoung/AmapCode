package com.alipay.android.phone.inside.cashier.service;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.ext.phonecashier.PhoneCashierCallback;
import com.alipay.mobile.framework.service.ext.phonecashier.PhoneCashierPaymentResult;
import com.alipay.mobile.framework.service.ext.phonecashier.PhoneCashierServcie;
import com.alipay.sdk.util.j;

public class InsideServicePayForAlipay extends AbstractInsideService<Bundle, Bundle> {
    static final String PARAMS_EXTEND_PARAMS = "extend_params";
    static final String PARAM_AUTR_INFO = "auth_info";
    static final String PARAM_ORDER_INFO = "order_info";

    public void start(IInsideServiceCallback<Bundle> iInsideServiceCallback, Bundle bundle) {
        LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::start(_,_)");
        Application a = LauncherApplication.a();
        String string = bundle.getString(PARAM_ORDER_INFO, null);
        if (TextUtils.isEmpty(string)) {
            string = bundle.getString(PARAM_AUTR_INFO, null);
        }
        String string2 = bundle.getString(PARAMS_EXTEND_PARAMS);
        LoggerFactory.f().b((String) "inside", "InsideServicePay::start(_,_), extendParams:".concat(String.valueOf(string2)));
        if (TextUtils.isEmpty(string2)) {
            string2 = ExtendParamsService.getDefaultExt();
            LoggerFactory.f().b((String) "inside", "InsideServicePay::start(_,_), default extendParams:".concat(String.valueOf(string2)));
        }
        sendPayRequest(a, string, string2, iInsideServiceCallback);
    }

    private void sendPayRequest(Context context, String str, String str2, final IInsideServiceCallback<Bundle> iInsideServiceCallback) {
        getPhoneCashierServcie().boot(str, str2, new PhoneCashierCallback() {
            public void onInstallFailed() {
            }

            public void onPaySuccess(PhoneCashierPaymentResult phoneCashierPaymentResult) {
                LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::sendPayRequest > onPaySuccess");
                InsideServicePayForAlipay.this.notifyCompleted(phoneCashierPaymentResult, iInsideServiceCallback);
            }

            public void onPayFailed(PhoneCashierPaymentResult phoneCashierPaymentResult) {
                LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::sendPayRequest > onPayFailed");
                InsideServicePayForAlipay.this.notifyCompleted(phoneCashierPaymentResult, iInsideServiceCallback);
            }
        });
    }

    /* access modifiers changed from: private */
    public void notifyCompleted(PhoneCashierPaymentResult phoneCashierPaymentResult, IInsideServiceCallback<Bundle> iInsideServiceCallback) {
        if (iInsideServiceCallback != null) {
            Bundle bundle = new Bundle();
            bundle.putString("memo", phoneCashierPaymentResult.getMemo());
            bundle.putString("result", phoneCashierPaymentResult.getResult());
            bundle.putString(j.a, String.valueOf(phoneCashierPaymentResult.getResultCode()));
            iInsideServiceCallback.onComplted(bundle);
            LoggerFactory.f().b((String) "inside", "InsideServicePay::notifyCompleted > bundle: ".concat(String.valueOf(bundle)));
        }
    }

    private PhoneCashierServcie getPhoneCashierServcie() {
        return (PhoneCashierServcie) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PhoneCashierServcie.class.getName());
    }

    public Bundle startForResult(Bundle bundle) throws Exception {
        LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::startForResult(_)");
        throw new UnsupportedOperationException("not impl of this method");
    }
}
