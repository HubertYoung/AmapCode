package com.alipay.android.phone.inside.cashier.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import org.json.JSONObject;

public class AuthV2Provider {
    static final String ACTION_REND_FINISH = "com.alipay.android.app.pay.ACTION_REND_FINISH";
    static final String TAG = "inside";
    private BroadcastReceiver mRenderReceiver;

    public void render(Context context, String str, IInsideServiceCallback<Bundle> iInsideServiceCallback) throws Exception {
        String buildData = buildData(str);
        registerCallback(context, iInsideServiceCallback);
        sendPayRequest(context, buildData);
    }

    private String buildData(String str) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("sign_params", str);
        jSONObject.put("inside_env", new InsideEnvBuilder().getInnerQrcodeAuthEnv());
        return jSONObject.toString();
    }

    private void sendPayRequest(Context context, String str) {
        LoggerFactory.f().b((String) TAG, (String) "AuthV2Provider::sendPayRequest");
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setPackage(context.getPackageName());
        intent.setClassName(context, "com.alipay.android.app.pay.MiniLaucherActivity");
        intent.putExtra("bizType", "aggregationSign");
        intent.putExtra("data", str);
        context.startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void unRegisterCallback(Context context) {
        if (this.mRenderReceiver != null) {
            try {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(this.mRenderReceiver);
            } catch (Throwable th) {
                LoggerFactory.f().c((String) TAG, th);
            }
        }
        this.mRenderReceiver = null;
    }

    private void registerCallback(Context context, final IInsideServiceCallback<Bundle> iInsideServiceCallback) {
        unRegisterCallback(context);
        this.mRenderReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                LoggerFactory.d().b("cashier", BehaviorType.EVENT, "CashierAuthV2OnReceive");
                AuthV2Provider.this.unRegisterCallback(context);
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
        intentFilter.addAction(ACTION_REND_FINISH);
        try {
            LocalBroadcastManager.getInstance(context).registerReceiver(this.mRenderReceiver, intentFilter);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) TAG, th);
        }
    }
}
