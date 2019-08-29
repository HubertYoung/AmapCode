package com.ali.auth.third.core.task;

import android.app.Activity;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.FailureCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.message.MessageUtils;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.ReflectionUtils;

public class InitWaitTask extends TaskWithDialog<Void, Void, Void> {
    /* access modifiers changed from: private */
    public Runnable a;
    private String b;
    private Class<?> c;
    protected FailureCallback failureCallback;

    public InitWaitTask(Activity activity, FailureCallback failureCallback2, Runnable runnable) {
        this(activity, failureCallback2, runnable, null);
    }

    public InitWaitTask(Activity activity, FailureCallback failureCallback2, Runnable runnable, String str) {
        super(activity);
        this.a = runnable;
        this.failureCallback = failureCallback2;
        this.b = str;
        this.c = ReflectionUtils.loadClassQuietly("com.alibaba.sdk.android.session.CredentialService");
    }

    /* access modifiers changed from: protected */
    public Void asyncExecute(Void... voidArr) {
        Boolean checkInitStatus = KernelContext.checkInitStatus();
        if (checkInitStatus == null) {
            CommonUtils.onFailure(this.failureCallback, MessageUtils.createMessage(10012, new Object[0]));
            return null;
        } else if (!checkInitStatus.booleanValue()) {
            MemberSDK.init(KernelContext.context, new e(this));
            return null;
        } else {
            if (this.c != null) {
                Object a2 = KernelContext.serviceRegistry.a(this.c, null);
                if (a2 != null) {
                    ReflectionUtils.invoke("com.alibaba.sdk.android.session.CredentialService", "init", null, a2, null);
                }
            }
            this.a.run();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void doWhenException(Throwable th) {
        CommonUtils.toastSystemException();
    }
}
