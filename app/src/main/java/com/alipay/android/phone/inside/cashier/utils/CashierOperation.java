package com.alipay.android.phone.inside.cashier.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;

public class CashierOperation {
    static final String ACTION_OPERATION_NAME = "com.alipay.android.app.sdk.ACTION_CASHIER_OPERATION";
    static final String ACTION_OPERATION_RESULT = "com.alipay.android.app.sdk.ACTION_CASHIER_OPERATION_RESULT";
    public static final String BIZ_CASHIER_REPORT = "biz_cashier_report";
    public static final String BIZ_GET_LOCAL_TID = "biz_get_local_tid";
    public static final String BIZ_GET_TID = "biz_get_tid";
    protected static final String TAG = "inside";

    public class CallResult {
        public Bundle mResult = new Bundle();

        public CallResult() {
        }
    }

    public Bundle requestOperationResult(Context context, String str, Bundle bundle) {
        long currentTimeMillis = System.currentTimeMillis();
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("CashierOperation::getBroadcastResult > enter:");
        sb.append(str);
        sb.append(", flag:");
        sb.append(currentTimeMillis);
        f.b((String) TAG, sb.toString());
        CallResult callResult = new CallResult();
        BroadcastReceiver operationReceiver = getOperationReceiver(str, callResult);
        registerOperationReceiver(context, operationReceiver);
        sendOperationRequest(context, operationReceiver, str, bundle);
        synchronized (operationReceiver) {
            try {
                operationReceiver.wait(6000);
            } catch (Exception e) {
                LoggerFactory.e().a((String) "cashier", (String) "CashierOperationEx", (Throwable) e);
            }
        }
        BehaviorLogger d = LoggerFactory.d();
        BehaviorType behaviorType = BehaviorType.EVENT;
        String concat = "SendOperationResult|".concat(String.valueOf(str));
        StringBuilder sb2 = new StringBuilder("receiver hashcode: ");
        sb2.append(operationReceiver.hashCode());
        d.a("cashier", behaviorType, concat, sb2.toString());
        return callResult.mResult;
    }

    private void registerOperationReceiver(Context context, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_OPERATION_RESULT);
        try {
            LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, intentFilter);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) TAG, (String) "CashierOpRegEx", th);
        }
    }

    private void sendOperationRequest(Context context, BroadcastReceiver broadcastReceiver, String str, Bundle bundle) {
        final Intent intent = new Intent();
        intent.setPackage(context.getPackageName());
        intent.setAction(ACTION_OPERATION_NAME);
        intent.addFlags(268435456);
        intent.putExtras(bundle);
        final Context context2 = context;
        final String str2 = str;
        final BroadcastReceiver broadcastReceiver2 = broadcastReceiver;
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                try {
                    context2.sendBroadcast(intent);
                } catch (Throwable th) {
                    LoggerFactory.f().c((String) CashierOperation.TAG, th);
                }
                BehaviorLogger d = LoggerFactory.d();
                BehaviorType behaviorType = BehaviorType.EVENT;
                StringBuilder sb = new StringBuilder("SendOperationRequest|");
                sb.append(str2);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder("receiver hashcode: ");
                sb3.append(broadcastReceiver2.hashCode());
                d.a("cashier", behaviorType, sb2, sb3.toString());
            }
        };
        new Thread(r0).start();
    }

    private BroadcastReceiver getOperationReceiver(final String str, final CallResult callResult) {
        return new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                String string = extras.getString("action");
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("CashierOperation::getOperationReceiver > action:");
                sb.append(str);
                sb.append(", targetAction:");
                sb.append(string);
                sb.append(", result:");
                sb.append(extras);
                sb.append(", receiver hashcode: ");
                sb.append(hashCode());
                f.b((String) CashierOperation.TAG, sb.toString());
                if (TextUtils.equals(string, str)) {
                    TraceLogger f2 = LoggerFactory.f();
                    StringBuilder sb2 = new StringBuilder("CashierOperation::getOperationReceiver > equals action:");
                    sb2.append(str);
                    f2.b((String) CashierOperation.TAG, sb2.toString());
                    BehaviorLogger d = LoggerFactory.d();
                    BehaviorType behaviorType = BehaviorType.EVENT;
                    String concat = "OperationOnReceive|".concat(String.valueOf(string));
                    StringBuilder sb3 = new StringBuilder("receiver hashcode: ");
                    sb3.append(hashCode());
                    d.a("cashier", behaviorType, concat, sb3.toString());
                    CashierOperation.this.unRegisterCallback(context, this);
                    callResult.mResult = extras;
                    synchronized (this) {
                        notifyAll();
                    }
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void unRegisterCallback(Context context, BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            try {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) TAG, (String) "CashierOpUnRegEx", th);
            }
        }
    }
}
