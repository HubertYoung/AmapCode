package com.ali.auth.third.core.task;

import android.os.AsyncTask;
import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.device.DeviceInfo;
import com.ali.auth.third.core.exception.AlibabaSDKException;
import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.model.Constants;
import com.ali.auth.third.core.service.CredentialService;
import com.ali.auth.third.core.service.MemberExecutorService;
import com.ali.auth.third.core.service.RpcService;
import com.ali.auth.third.core.service.StorageService;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.service.impl.CredentialManager;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.ReflectionUtils;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import java.io.File;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class a implements Runnable {
    /* access modifiers changed from: private */
    public InitResultCallback a;
    private Integer b;
    private CountDownLatch c = new CountDownLatch(1);

    public a(InitResultCallback initResultCallback, Integer num) {
        this.a = new b(this, initResultCallback);
        this.b = num;
    }

    private Object a(String str, String[] strArr, Object[] objArr) {
        try {
            return ReflectionUtils.newInstance(str, strArr, objArr);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
            return null;
        }
    }

    private void a(com.ali.auth.third.core.registry.a aVar) {
        boolean z;
        SDKLogger.d("kernel", "registerStorage");
        try {
            Class.forName("com.ali.auth.third.securityguard.SecurityGuardWrapper");
            try {
                KernelContext.isMini = false;
                KernelContext.sdkVersion = KernelContext.SDK_VERSION_STD;
            } catch (Throwable unused) {
            }
            z = true;
        } catch (Throwable unused2) {
            z = false;
        }
        try {
            aVar.a(new Class[]{StorageService.class}, a(z ? "com.ali.auth.third.securityguard.SecurityGuardWrapper" : "com.ali.auth.third.core.storage.CommonStorageServiceImpl", (String[]) null, (Object[]) null), null);
            KernelContext.storageService = (StorageService) aVar.a(StorageService.class, null);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z, int i, String str) {
        InitResultCallback[] initResultCallbackArr = (InitResultCallback[]) KernelContext.serviceRegistry.b(InitResultCallback.class, null);
        if (initResultCallbackArr != null) {
            MemberExecutorService memberExecutorService = KernelContext.executorService;
            c cVar = new c(this, initResultCallbackArr, z, i, str);
            memberExecutorService.postTask(cVar);
        }
    }

    private void b(com.ali.auth.third.core.registry.a aVar) {
        boolean z;
        SDKLogger.d("kernel", "registerRpc");
        try {
            Class.forName("com.ali.auth.third.mtop.rpc.impl.MtopRpcServiceImpl");
            z = true;
        } catch (Throwable unused) {
            z = false;
        }
        try {
            aVar.a(new Class[]{RpcService.class}, a(z ? "com.ali.auth.third.mtop.rpc.impl.MtopRpcServiceImpl" : "com.ali.auth.third.core.rpc.CommRpcServiceImpl", (String[]) null, (Object[]) null), null);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }
    }

    private void c() {
        DeviceInfo.init(KernelContext.context);
    }

    private void c(com.ali.auth.third.core.registry.a aVar) {
        boolean z;
        SDKLogger.d("kernel", "registerUserTrack");
        String str = "com.ali.auth.third.ut.UserTrackerImpl";
        try {
            Class.forName(str);
            z = true;
        } catch (Throwable unused) {
            z = false;
        }
        if (!z) {
            str = "com.ali.auth.third.core.ut.UserTracer";
        }
        try {
            aVar.a(new Class[]{UserTrackerService.class}, a(str, (String[]) null, (Object[]) null), null);
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }
    }

    private boolean d() {
        try {
            Class.forName(AsyncTask.class.getName());
        } catch (Exception unused) {
        }
        KernelContext.wrapServiceRegistry();
        ConfigManager.getInstance().init(this.b.intValue());
        com.ali.auth.third.core.registry.a aVar = KernelContext.serviceRegistry;
        b(aVar);
        a(aVar);
        c(aVar);
        Class[] clsArr = {MemberExecutorService.class, ExecutorService.class};
        aVar.a(clsArr, KernelContext.executorService, Collections.singletonMap(Constants.PLUGIN_VENDOR_KEY, "kernel"));
        aVar.a(new Class[]{CredentialService.class}, CredentialManager.INSTANCE, Collections.singletonMap("scop", "system"));
        KernelContext.credentialService = (CredentialService) aVar.a(CredentialService.class, null);
        boolean e = e();
        if (!KernelContext.isMini) {
            f();
        }
        SDKLogger.d("syncRun", "INIT SUCCESS");
        return e;
    }

    private boolean e() {
        SDKLogger.d("kernel", "register login service");
        try {
            ReflectionUtils.invoke("com.ali.auth.third.login.LoginLifecycleAdapter", "init", null, Class.forName("com.ali.auth.third.login.LoginLifecycleAdapter"), null);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean f() {
        SDKLogger.d("kernel", "register account link service");
        try {
            ReflectionUtils.invoke("com.ali.auth.third.accountlink.AccountLinkLifecycleAdapter", "init", null, Class.forName("com.ali.auth.third.accountlink.AccountLinkLifecycleAdapter"), null);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private boolean g() {
        try {
            long h = h();
            SDKLogger.e("kernel", "timeStamp=".concat(String.valueOf(h)));
            KernelContext.timestamp = h;
        } catch (Exception e) {
            SDKLogger.e("kernel", e.getMessage(), e);
        }
        if (!a()) {
            SDKLogger.d("asyncRun", "FAILURE");
            return false;
        }
        KernelContext.executorService.postUITask(new d(this));
        KernelContext.isInitOk = Boolean.TRUE;
        SDKLogger.d("asyncRun", GenBusCodeService.CODE_SUCESS);
        return true;
    }

    private long h() {
        StringBuilder sb = new StringBuilder();
        sb.append(KernelContext.context.getFilesDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append("timestamp");
        File file = new File(sb.toString());
        if (file.exists()) {
            return file.lastModified();
        }
        try {
            file.createNewFile();
            return file.lastModified();
        } catch (Exception e) {
            SDKLogger.e("kernel", e.getMessage(), e);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public void a(Throwable th) {
        String str;
        int i;
        KernelContext.isInitOk = Boolean.FALSE;
        if (th instanceof AlibabaSDKException) {
            AlibabaSDKException alibabaSDKException = (AlibabaSDKException) th;
            if (alibabaSDKException.getSDKMessage() != null) {
                Message sDKMessage = alibabaSDKException.getSDKMessage();
                i = sDKMessage.code;
                str = sDKMessage.message;
                CommonUtils.onFailure(this.a, i, str);
            }
        }
        i = 10010;
        str = CommonUtils.toString(th);
        CommonUtils.onFailure(this.a, i, str);
    }

    public boolean a() {
        StringBuilder sb = new StringBuilder("sdk version = ");
        sb.append(ConfigManager.SDK_VERSION.toString());
        SDKLogger.d("", sb.toString());
        c();
        if (KernelContext.syncInitialized) {
            return true;
        }
        try {
            if (d()) {
                KernelContext.syncInitialized = true;
                return true;
            }
        } catch (Throwable th) {
            SDKLogger.e("kernel", "fail to sync start", th);
            a(th);
        }
        this.c.countDown();
        return false;
    }

    /* access modifiers changed from: protected */
    public void b() {
        KernelContext.initLock.unlock();
    }

    public void run() {
        try {
            KernelContext.initLock.lock();
            g();
        } catch (Throwable th) {
            this.c.countDown();
            b();
            throw th;
        }
        this.c.countDown();
        b();
    }
}
