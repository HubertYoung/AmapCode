package com.ali.auth.third.login.task;

import android.app.Activity;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.message.MessageUtils;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.task.AbsAsyncTask;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.ResourceUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.login.UTConstants;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import java.util.HashMap;

public class LoginByIVTokenTask extends AbsAsyncTask<String, Void, Void> {
    /* access modifiers changed from: private */
    public LoginCallback a;
    private Activity b;

    public LoginByIVTokenTask(Activity activity, LoginCallback loginCallback) {
        this.b = activity;
        this.a = loginCallback;
    }

    private void a() {
        KernelContext.executorService.postUITask(new d(this));
    }

    private void a(int i, String str) {
        KernelContext.executorService.postUITask(new c(this, i, str));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void asyncExecute(String... strArr) {
        if (!CommonUtils.isNetworkAvailable()) {
            RpcResponse rpcResponse = new RpcResponse();
            rpcResponse.code = -1;
            rpcResponse.message = ResourceUtils.getString("com_taobao_tae_sdk_network_not_available_message");
            a(rpcResponse.code, rpcResponse.message);
            return null;
        }
        RpcResponse<LoginReturnData> loginByIVToken = LoginComponent.INSTANCE.loginByIVToken(strArr[0], strArr[1], strArr[2]);
        if (loginByIVToken == null || loginByIVToken.returnValue == null) {
            a(ResultCode.SYSTEM_EXCEPTION.code, ResultCode.SYSTEM_EXCEPTION.message);
            return null;
        } else if (loginByIVToken.code == 3000) {
            KernelContext.credentialService.refreshWhenLogin((LoginReturnData) loginByIVToken.returnValue);
            a();
            return null;
        } else {
            a(loginByIVToken.code, loginByIVToken.message);
            return null;
        }
    }

    public void doFinally() {
    }

    public void doWhenException(Throwable th) {
        Message createMessage = MessageUtils.createMessage(10010, th.getMessage());
        SDKLogger.log("login", createMessage, th);
        HashMap hashMap = new HashMap();
        hashMap.put("code", H5AppPrepareData.PREPARE_TIMEOUT);
        hashMap.put("message", LogCategory.CATEGORY_EXCEPTION);
        ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_H5_LOGIN_FAILURE, hashMap);
        a(createMessage.code, createMessage.message);
    }
}
