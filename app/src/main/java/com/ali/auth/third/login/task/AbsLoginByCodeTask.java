package com.ali.auth.third.login.task;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.task.TaskWithDialog;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.login.RequestCode;
import com.ali.auth.third.login.a.a;
import com.ali.auth.third.ui.LoginWebViewActivity;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;

public abstract class AbsLoginByCodeTask extends TaskWithDialog<String, Void, Void> {
    public AbsLoginByCodeTask(Activity activity) {
        super(activity);
    }

    /* access modifiers changed from: protected */
    public Void asyncExecute(String... strArr) {
        RpcResponse<LoginReturnData> login = login(strArr);
        int i = login.code;
        SDKLogger.d("AbsLoginByCodeTask", "asyncExecute code = ".concat(String.valueOf(i)));
        if (i == 3000) {
            try {
                if (login.returnValue != null) {
                    a.b.refreshWhenLogin((LoginReturnData) login.returnValue);
                }
                KernelContext.executorService.postUITask(new a(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i == 13060) {
            String str = ((LoginReturnData) login.returnValue).h5Url;
            SDKLogger.d("AbsLoginByCodeTask", "asyncExecute doubleCheckUrl = ".concat(String.valueOf(str)));
            if (!TextUtils.isEmpty(str)) {
                Activity activity = this.activity;
                if ((activity instanceof LoginWebViewActivity) && LoginWebViewActivity.originActivity != null) {
                    activity = LoginWebViewActivity.originActivity;
                }
                CallbackContext.setActivity(activity);
                Intent intent = new Intent(activity, LoginWebViewActivity.class);
                intent.putExtra("url", str);
                intent.putExtra("token", ((LoginReturnData) login.returnValue).token);
                intent.putExtra(H5AppUtil.scene, ((LoginReturnData) login.returnValue).scene);
                LoginWebViewActivity.token = ((LoginReturnData) login.returnValue).token;
                LoginWebViewActivity.scene = ((LoginReturnData) login.returnValue).scene;
                this.activity.startActivityForResult(intent, RequestCode.OPEN_DOUBLE_CHECK);
            }
        } else {
            KernelContext.executorService.postUITask(new b(this, i, login));
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract void doWhenResultFail(int i, String str);

    /* access modifiers changed from: protected */
    public abstract void doWhenResultOk();

    /* access modifiers changed from: protected */
    public abstract RpcResponse<LoginReturnData> login(String[] strArr);
}
