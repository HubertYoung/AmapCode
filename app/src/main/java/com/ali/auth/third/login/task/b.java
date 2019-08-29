package com.ali.auth.third.login.task;

import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.message.MessageUtils;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.trace.SDKLogger;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

class b implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ RpcResponse b;
    final /* synthetic */ AbsLoginByCodeTask c;

    b(AbsLoginByCodeTask absLoginByCodeTask, int i, RpcResponse rpcResponse) {
        this.c = absLoginByCodeTask;
        this.a = i;
        this.b = rpcResponse;
    }

    public void run() {
        StringBuilder sb = new StringBuilder("code ");
        sb.append(this.a);
        sb.append(Token.SEPARATOR);
        sb.append(this.b.message);
        Message createMessage = MessageUtils.createMessage(15, "login", sb.toString());
        SDKLogger.d("AbsLoginByCodeTask", createMessage.toString());
        this.c.doWhenResultFail(createMessage.code, createMessage.message);
    }
}
