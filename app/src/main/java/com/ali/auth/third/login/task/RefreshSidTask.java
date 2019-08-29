package com.ali.auth.third.login.task;

import android.app.Activity;
import android.webkit.WebView;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.task.TaskWithDialog;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.ui.context.CallbackContext;

public class RefreshSidTask extends TaskWithDialog<String, Void, Void> {
    private WebView a;

    public RefreshSidTask(WebView webView) {
        super((Activity) webView.getContext());
        this.a = webView;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void asyncExecute(String... strArr) {
        if (!KernelContext.credentialService.isSessionValid()) {
            CallbackContext.setActivity(this.activity);
            LoginComponent.INSTANCE.showLogin(this.activity);
        }
        return null;
    }

    public void doWhenException(Throwable th) {
        CommonUtils.toastSystemException();
    }
}
