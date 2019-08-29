package com.alipay.android.phone.inside.sdk.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.IInsideInteraction;
import com.alipay.android.phone.inside.api.IInsideInteraction.Stub;
import com.alipay.android.phone.inside.api.IRemoteServiceCallback;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.sdk.InsideProviderService;
import com.alipay.android.phone.inside.sdk.InsideProviderService.IInteractionProvider;
import com.alipay.android.phone.inside.sdk.util.ConvertTool;
import com.alipay.android.phone.inside.sdk.util.DeviceConfigTool;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import java.util.concurrent.TimeoutException;

public class InsideOperationServiceHelper {
    /* access modifiers changed from: private */
    public static final Object mLock = new Object();
    /* access modifiers changed from: private */
    public Context mContext;
    private ServiceConnection mInsideConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (InsideOperationServiceHelper.mLock) {
                try {
                    InsideOperationServiceHelper.this.mInsideIntercation = Stub.asInterface(iBinder);
                    InsideOperationServiceHelper.this.mInsideIntercation.registerCallback(new IRemoteServiceCallback.Stub() {
                        public void startActivity(Intent intent) throws RemoteException {
                        }

                        public void notify(Bundle bundle) throws RemoteException {
                            if (TextUtils.equals(bundle.getString("KEY_TYPE"), "BROADCAST")) {
                                InsideOperationServiceHelper.this.dealBroadcast(InsideOperationServiceHelper.this.mContext, bundle);
                            }
                        }
                    });
                    InsideOperationServiceHelper.this.linkToDeath(iBinder);
                    LoggerFactory.d().b(GlobalConstants.EXCEPTIONTYPE, BehaviorType.EVENT, "InsideSdkConnected");
                } catch (Throwable th) {
                    LoggerFactory.f().b((String) "inside", th);
                }
                InsideOperationServiceHelper.mLock.notifyAll();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (InsideOperationServiceHelper.mLock) {
                InsideOperationServiceHelper.this.mInsideIntercation = null;
                InsideOperationServiceHelper.mLock.notifyAll();
                LoggerFactory.d().b(GlobalConstants.EXCEPTIONTYPE, BehaviorType.EVENT, "InsideSdkDisConnected");
            }
        }
    };
    /* access modifiers changed from: private */
    public IInsideInteraction mInsideIntercation;

    /* access modifiers changed from: private */
    public void dealBroadcast(Context context, Bundle bundle) {
        String string = bundle.getString("KEY_ACTION");
        Bundle bundle2 = bundle.getBundle("KEY_VALUE");
        try {
            Intent intent = new Intent(string);
            intent.putExtras(bundle2);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) GlobalConstants.EXCEPTIONTYPE, (String) "DealBroadcastEx", th);
        }
    }

    public Bundle doInvoke(Context context, ActionEnum actionEnum, BaseModel baseModel) throws Throwable {
        return doInsideInvoke(context, getInvokeParams(context, actionEnum, baseModel));
    }

    public Bundle doInvoke(Context context, Bundle bundle) throws Throwable {
        return doInsideInvoke(context, getInvokeParams(context, bundle));
    }

    private Bundle getInvokeParams(Context context, Bundle bundle) {
        try {
            putEnvParams(context, bundle);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) GlobalConstants.EXCEPTIONTYPE, (String) "SdkBuildBundleParamsEx", th);
        }
        return bundle;
    }

    private Bundle getInvokeParams(Context context, ActionEnum actionEnum, BaseModel baseModel) {
        Bundle bundle = baseModel.toBundle();
        bundle.putString("action", actionEnum.getActionName());
        try {
            putEnvParams(context, bundle);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) GlobalConstants.EXCEPTIONTYPE, (String) "SdkBuildBaseModelParamsEx", th);
        }
        return bundle;
    }

    private void putEnvParams(Context context, Bundle bundle) throws Throwable {
        Context applicationContext = context.getApplicationContext();
        String packageName = applicationContext.getPackageName();
        String str = applicationContext.getPackageManager().getPackageInfo(packageName, 128).versionName;
        StringBuilder sb = new StringBuilder("(");
        sb.append(packageName);
        sb.append(",");
        sb.append(str);
        sb.append(",");
        sb.append(VERSION.RELEASE);
        sb.append(",");
        sb.append(Build.MODEL);
        sb.append(")");
        bundle.putString("env", sb.toString());
        bundle.putString("sdk_version", "1.0.0");
        bundle.putString(GlobalConstants.SDK_ENV_INFO, DeviceConfigTool.buildEnvInfo(context));
    }

    public Bundle doInsideInvoke(Context context, Bundle bundle) throws Throwable {
        if (bundle == null) {
            return null;
        }
        this.mContext = context.getApplicationContext();
        if (this.mInsideIntercation == null) {
            bindInsideService(context);
        }
        if (this.mInsideIntercation != null) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("content", ConvertTool.convertBundleToJsonStr(bundle));
            return this.mInsideIntercation.interaction(bundle2);
        }
        LoggerFactory.e().a("inside", "BindInsideServiceFailed");
        throw new TimeoutException("bind inside service failed");
    }

    private synchronized void bindInsideService(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        LoggerFactory.f().b((String) "inside", "InsideOperationServiceHelper::bindInsideService start=".concat(String.valueOf(currentTimeMillis)));
        context.getApplicationContext().bindService(getBindIntent(context), this.mInsideConnection, 1);
        synchronized (mLock) {
            try {
                mLock.wait(5000);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "inside", (String) "BindInsideEx", th);
            }
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        LoggerFactory.c().a(GlobalConstants.EXCEPTIONTYPE, "BinderInsideServiceTime", Long.valueOf(currentTimeMillis2 - currentTimeMillis));
        LoggerFactory.f().b((String) "inside", "InsideOperationServiceHelper::bindInsideService end=".concat(String.valueOf(currentTimeMillis2)));
    }

    private Intent getBindIntent(Context context) {
        Intent intent = new Intent();
        IInteractionProvider interactionProvider = InsideProviderService.getInteractionProvider();
        if (interactionProvider == null) {
            intent.setClassName(context, GlobalConstants.INSIDE_INTER_SERVICE_NAME);
            intent.setAction(GlobalConstants.INSIDE_INTER_SERVICE_ACTION_NAME);
        } else {
            intent.setPackage(interactionProvider.getPkgName());
            intent.setAction(GlobalConstants.INSIDE_INTER_SERVICE_ACTION_NAME);
        }
        return intent;
    }

    /* access modifiers changed from: private */
    public void linkToDeath(IBinder iBinder) {
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(new DeathRecipient() {
                    public void binderDied() {
                        InsideOperationServiceHelper.this.mInsideIntercation = null;
                    }
                }, 0);
            } catch (RemoteException e) {
                LoggerFactory.f().c(InsideOperationServiceHelper.class.getName(), (Throwable) e);
            }
        }
    }
}
