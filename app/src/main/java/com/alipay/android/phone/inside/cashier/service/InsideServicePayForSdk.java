package com.alipay.android.phone.inside.cashier.service;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;

public class InsideServicePayForSdk extends AbstractInsideService<Bundle, Bundle> {
    static final String PARAMS_EXTEND_PARAMS = "extend_params";
    static final String PARAM_AUTR_INFO = "auth_info";
    static final String PARAM_ORDER_INFO = "order_info";
    static final String PAY_RESULT_FAILED = "com.alipay.android.app.pay.ACTION_PAY_FAILED";
    static final String PAY_RESULT_SUCCESS = "com.alipay.android.app.pay.ACTION_PAY_SUCCESS";
    private BroadcastReceiver mPayReceiver;

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
        registerCallback(a, iInsideServiceCallback);
        sendPayRequest(a, string, string2);
        LoggerFactory.d().b("cashier", BehaviorType.EVENT, "CashierRequestPay");
    }

    /* access modifiers changed from: private */
    public void unRegisterCallback(Context context) {
        if (this.mPayReceiver != null) {
            try {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(this.mPayReceiver);
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        this.mPayReceiver = null;
    }

    private void registerCallback(Context context, final IInsideServiceCallback<Bundle> iInsideServiceCallback) {
        unRegisterCallback(context);
        this.mPayReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                LoggerFactory.d().b("cashier", BehaviorType.EVENT, "CashierPayOnReceive");
                InsideServicePayForSdk.this.unRegisterCallback(context);
                final Bundle extras = intent.getExtras();
                if (iInsideServiceCallback != null) {
                    new Thread(new Runnable() {
                        public void run() {
                            iInsideServiceCallback.onComplted(extras);
                        }
                    }).start();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PAY_RESULT_SUCCESS);
        intentFilter.addAction(PAY_RESULT_FAILED);
        try {
            LocalBroadcastManager.getInstance(context).registerReceiver(this.mPayReceiver, intentFilter);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    private void sendPayRequest(Context context, String str, String str2) {
        LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::sendPayRequest");
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setPackage(context.getPackageName());
        intent.setClassName(context, "com.alipay.android.app.pay.MiniLaucherActivity");
        intent.putExtra(PARAM_ORDER_INFO, str);
        intent.putExtra(PARAMS_EXTEND_PARAMS, str2);
        context.startActivity(intent);
    }

    public Bundle startForResult(Bundle bundle) throws UnsupportedOperationException {
        LoggerFactory.f().b((String) "inside", (String) "InsideServicePay::startForResult(_)");
        throw new UnsupportedOperationException("not impl of this method");
    }
}
