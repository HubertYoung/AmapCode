package com.alipay.android.phone.inside.wallet.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.IAlipayCodeService;
import com.alipay.android.phone.inside.api.IAlipayInsideService;
import com.alipay.android.phone.inside.api.IAlipayRemoteCallback;
import com.alipay.android.phone.inside.api.IAlipayRemoteCallback.Stub;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.lang.ref.WeakReference;

public class AlipayServiceBinder {
    static AlipayServiceBinder mAlipayServiceBinder;
    /* access modifiers changed from: private */
    public IAlipayCodeService mAlipayCodeService;
    /* access modifiers changed from: private */
    public IAlipayInsideService mAlipayInsideService;
    /* access modifiers changed from: private */
    public IAlipayRemoteCallback mAlipayRemoteCallback = new Stub() {
        public void startActivity(String str, String str2, int i, Bundle bundle) throws RemoteException {
            try {
                Context context = (Context) AlipayServiceBinder.this.mContextRef.get();
                if (context == null) {
                    throw new NullPointerException("context is null");
                }
                Intent intent = new Intent("android.intent.action.MAIN", null);
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putInt("CallingPid", i);
                intent.putExtras(bundle);
                intent.setClassName(str, str2);
                context.startActivity(intent);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "wallet", (String) "AlipayServiceBinderEx", th);
            }
        }
    };
    private ServiceConnection mAlipayServiceConnection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName componentName) {
            synchronized (AlipayServiceBinder.this.mLock) {
                LoggerFactory.f().e("inside", "onAlipayServiceDisconnected");
                AlipayServiceBinder.this.mAlipayInsideService = null;
                AlipayServiceBinder.this.mAlipayCodeService = null;
                AlipayServiceBinder.this.mLock.notifyAll();
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LoggerFactory.f().e("inside", "onAlipayServiceConnected");
            synchronized (AlipayServiceBinder.this.mLock) {
                try {
                    AlipayServiceBinder.this.mAlipayInsideService = IAlipayInsideService.Stub.asInterface(iBinder);
                    IBinder queryBinderService = AlipayServiceBinder.this.mAlipayInsideService.queryBinderService("CodeService", 1, null);
                    AlipayServiceBinder.this.linkToDeath(queryBinderService);
                    AlipayServiceBinder.this.mAlipayCodeService = IAlipayCodeService.Stub.asInterface(queryBinderService);
                    AlipayServiceBinder.this.mAlipayCodeService.registerAlipayRemoteCallback(AlipayServiceBinder.this.mAlipayRemoteCallback);
                } catch (Throwable th) {
                    String message = th.getMessage();
                    if (!TextUtils.isEmpty(message)) {
                        message.contains("INVOKE_ALIPAY_SIGN_ERROR");
                    }
                    LoggerFactory.f().c((String) "inside", th);
                }
                AlipayServiceBinder.this.mLock.notifyAll();
            }
        }
    };
    /* access modifiers changed from: private */
    public WeakReference<Context> mContextRef;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();

    public static synchronized AlipayServiceBinder getInstance() {
        AlipayServiceBinder alipayServiceBinder;
        synchronized (AlipayServiceBinder.class) {
            try {
                if (mAlipayServiceBinder == null) {
                    mAlipayServiceBinder = new AlipayServiceBinder();
                }
                alipayServiceBinder = mAlipayServiceBinder;
            }
        }
        return alipayServiceBinder;
    }

    public Bundle invokeAlipayService(Context context, Bundle bundle) throws Throwable {
        this.mContextRef = new WeakReference<>(context);
        bindAlipayService(context, true);
        if (this.mAlipayCodeService == null) {
            return null;
        }
        LoggerFactory.f().e("inside", "start Alipay code invoke");
        Bundle processCode = this.mAlipayCodeService.processCode(bundle);
        LoggerFactory.f().e("inside", "end Alipay code invoke");
        return processCode;
    }

    private Intent getAlipayServiceIntent() {
        Intent intent = new Intent("com.alipay.android.phone.inside.IAlipayInsideService");
        intent.setPackage("com.eg.android.AlipayGphone");
        intent.putExtra("inside:sdk_version", "1.0.0");
        return intent;
    }

    private void preloadAlipay(Context context) {
        try {
            Intent intent = new Intent();
            intent.setClassName("com.eg.android.AlipayGphone", "com.alipay.android.app.TransProcessPayActivity");
            intent.setFlags(268435456);
            context.startActivity(intent);
            Thread.sleep(150);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "PreloadAlipayServiceEx", th);
        }
    }

    public synchronized boolean bindAlipayService(final Context context, boolean z) throws Exception {
        LoggerFactory.f().b((String) "inside", (String) "InsideOperationServiceHelper::doInvoke start ");
        if (!needInitService()) {
            LoggerFactory.f().e("inside", "don't need init service");
            return true;
        }
        LoggerFactory.f().e("inside", "initialize binding ".concat(String.valueOf("com.eg.android.AlipayGphone")));
        if (z) {
            preloadAlipay(context);
        }
        LoggerFactory.f().e("inside", "start bindService");
        final Intent alipayServiceIntent = getAlipayServiceIntent();
        final ServiceConnection serviceConnection = this.mAlipayServiceConnection;
        new Thread(new Runnable() {
            public void run() {
                LoggerFactory.f().b((String) "inside", (String) "before bindService");
                try {
                    context.getApplicationContext().bindService(alipayServiceIntent, serviceConnection, 1);
                } catch (Throwable th) {
                    LoggerFactory.e().a((String) "inside", (String) "BindAlipayServiceEx", th);
                }
                LoggerFactory.f().b((String) "inside", (String) "end bindService");
            }
        }).start();
        synchronized (this.mLock) {
            this.mLock.wait(6000);
        }
        LoggerFactory.f().e("inside", "end bindService");
        return true;
    }

    private boolean needInitService() {
        boolean z = this.mAlipayCodeService == null;
        LoggerFactory.f().b((String) "inside", "AlipayServiceBinder::needInitService > ".concat(String.valueOf(z)));
        return z;
    }

    /* access modifiers changed from: private */
    public void linkToDeath(IBinder iBinder) {
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(new DeathRecipient() {
                    public void binderDied() {
                        AlipayServiceBinder.this.mAlipayInsideService = null;
                    }
                }, 0);
            } catch (RemoteException e) {
                LoggerFactory.f().c((String) "inside", (Throwable) e);
            }
        }
    }
}
