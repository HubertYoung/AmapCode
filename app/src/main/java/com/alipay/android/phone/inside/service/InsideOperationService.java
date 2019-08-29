package com.alipay.android.phone.inside.service;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.alipay.android.phone.inside.api.IEnvProvider;
import com.alipay.android.phone.inside.api.IMspEnvProvider;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.android.phone.inside.api.result.util.OperationResultUtil;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.sdk.helper.InsideOperationServiceHelper;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.inside.sdk.util.LogContextSDK;
import com.alipay.android.phone.inside.sdk.util.LogUtils;
import org.json.JSONObject;

public class InsideOperationService {
    private static InsideOperationService sInstance = new InsideOperationService();
    private IEnvProvider mEnvProvider;
    private InsideOperationServiceHelper mHelper = null;
    private IMspEnvProvider mMspEnvProvider;

    public static class RunInMainThreadException extends Exception {
        private static final long serialVersionUID = 2425617529954970681L;

        public RunInMainThreadException(String str) {
            super(str);
        }
    }

    public static OperationResult jumpToAlipayScheme(Activity activity, String str, String str2) {
        return null;
    }

    public String getSdkVersion() {
        return "1.0.0";
    }

    private InsideOperationService() {
    }

    public static InsideOperationService getInstance() throws RunInMainThreadException {
        if (!isInMainThread()) {
            return sInstance;
        }
        throw new RunInMainThreadException("onAuth  cannot exec in mainThread.");
    }

    public void preLoadService(Context context, boolean z, boolean z2) {
        try {
            LoggerFactory.a(new LogContextSDK(context));
            if (z) {
                getPayCodeServiceHelper().doInsideInvoke(context, null);
            }
        } catch (Throwable th) {
            LoggerFactory.f().c(InsideOperationService.class.getName(), th);
        }
    }

    public void registerEnvProvider(Context context, IEnvProvider iEnvProvider) {
        LogUtils.d("InsideOperationService::registerEnvProvider > ".concat(String.valueOf(iEnvProvider)));
        this.mEnvProvider = iEnvProvider;
    }

    public IMspEnvProvider getMspEnvProvider(final Context context) {
        if (this.mMspEnvProvider == null) {
            this.mMspEnvProvider = new IMspEnvProvider() {
                public String getSessionId() throws Exception {
                    LogUtils.d("sid > start");
                    IEnvProvider envProvider = InsideOperationService.this.getEnvProvider(context);
                    if (envProvider == null) {
                        LogUtils.d("sid > 0");
                        return null;
                    }
                    try {
                        LogUtils.d("sid > 1");
                        return envProvider.getSessionId();
                    } catch (Exception e) {
                        LogUtils.d("sid > 2");
                        throw e;
                    }
                }

                public JSONObject mtop(String str, String str2, boolean z, boolean z2, String str3, String str4, String str5, String str6, boolean z3) throws Exception {
                    LogUtils.d("mtop > start");
                    IEnvProvider envProvider = InsideOperationService.this.getEnvProvider(context);
                    if (envProvider == null) {
                        LogUtils.d("mtop > 0");
                        return null;
                    }
                    try {
                        LogUtils.d("mtop > 1");
                        return envProvider.mtop(str, str2, z, z2, str3, str4, str5, str6, z3);
                    } catch (Exception e) {
                        Exception exc = e;
                        LogUtils.d("mtop > 2");
                        throw exc;
                    }
                }

                public String getBizFrom() {
                    LogUtils.d("bizFrom > start");
                    String b = StaticConfig.b();
                    LogUtils.d("bizFrom > end");
                    return b;
                }
            };
        }
        return this.mMspEnvProvider;
    }

    public IEnvProvider getEnvProvider(Context context) {
        StringBuilder sb = new StringBuilder("InsideOperationService::getEnvProvider > ");
        sb.append(this.mEnvProvider);
        LogUtils.d(sb.toString());
        return this.mEnvProvider;
    }

    public Bundle startAction(Context context, Bundle bundle) {
        Bundle bundle2;
        LoggerFactory.f().b((String) "inside", (String) "InsideOperationService::startAction > start action");
        LoggerFactory.a();
        String string = bundle.getString("action", "");
        LoggerFactory.d().b(GlobalConstants.EXCEPTIONTYPE, BehaviorType.EVENT, "InsideSdkCallAction|".concat(String.valueOf(string)));
        Bundle bundle3 = new Bundle();
        try {
            bundle2 = getPayCodeServiceHelper().doInvoke(context, bundle);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) GlobalConstants.EXCEPTIONTYPE, (String) "DoActionBundleEx", th);
            bundle2 = bundle3;
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("InsideOperationService::startAction > end Action:");
        sb.append(string);
        sb.append(", result:");
        sb.append(bundle2);
        f.e("inside", sb.toString());
        LoggerFactory.b();
        return bundle2;
    }

    public <T extends ResultCode> OperationResult<T> startAction(Context context, BaseModel<T> baseModel) {
        OperationResult<T> operationResult;
        LoggerFactory.f().b((String) "inside", (String) "InsideOperationService::startAction > start action");
        LoggerFactory.a();
        BehaviorLogger d = LoggerFactory.d();
        BehaviorType behaviorType = BehaviorType.EVENT;
        StringBuilder sb = new StringBuilder("InsideSdkCallAction|");
        sb.append(baseModel.getOperaion().getAction().toString());
        d.b(GlobalConstants.EXCEPTIONTYPE, behaviorType, sb.toString());
        try {
            operationResult = OperationResultUtil.analysisOperationResult(getPayCodeServiceHelper().doInvoke(context, baseModel.getOperaion().getAction(), baseModel), baseModel.getOperaion());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) GlobalConstants.EXCEPTIONTYPE, (String) "DoActionEx", th);
            operationResult = null;
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb2 = new StringBuilder("InsideOperationService::startAction > end Action:");
        sb2.append(baseModel.getOperaion().getAction());
        sb2.append(", result:");
        sb2.append(operationResult);
        f.e("inside", sb2.toString());
        LoggerFactory.b();
        return operationResult;
    }

    public void updateAlipaySupportMinVersion(int i) {
        com.alipay.android.phone.inside.commonbiz.GlobalConstants.a = i;
    }

    private InsideOperationServiceHelper getPayCodeServiceHelper() {
        if (this.mHelper == null) {
            this.mHelper = new InsideOperationServiceHelper();
        }
        return this.mHelper;
    }

    private static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
