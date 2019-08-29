package com.ali.auth.third.core.model;

import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.message.MessageUtils;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.uc.webview.export.internal.setup.UCAsyncTask;

public class ResultCode {
    @Deprecated
    public static final ResultCode BRIDGE_EXCEPTION = new ResultCode(10001, "BRIDGE_EXCEPTION");
    public static final ResultCode CHECK = new ResultCode(108, "CHECK");
    @Deprecated
    public static final ResultCode HTTP_REQUEST_EXCEPTION = new ResultCode(10006);
    public static final ResultCode IGNORE = new ResultCode(-100, "IGNORE");
    @Deprecated
    public static final ResultCode ILLEGAL_PARAM = new ResultCode(UCAsyncTask.getPriority, "ILLEGAL_PARAM");
    @Deprecated
    public static final ResultCode INIT_EXCEPTION = new ResultCode(10002, "INIT_EXCEPTION");
    @Deprecated
    public static final ResultCode NETWORK_NOT_AVAILABLE = new ResultCode(UCAsyncTask.getTaskCount, "NETWORK_NOT_AVAILABLE");
    @Deprecated
    public static final ResultCode NO_SUCH_METHOD = new ResultCode(10000, "NO_SUCH_METHOD");
    @Deprecated
    public static final ResultCode RSA_DECRYPT_EXCEPTION = new ResultCode(10005);
    @Deprecated
    public static final ResultCode SECURITY_GUARD_INIT_EXCEPTION = new ResultCode(UCAsyncTask.inThread, "SECURITY_GUARD_INIT_EXCEPTION");
    public static final ResultCode SUCCESS = new ResultCode(100, GenBusCodeService.CODE_SUCESS);
    @Deprecated
    public static final ResultCode SYSTEM_EXCEPTION = new ResultCode(10010, "SYSTEM_EXCEPTION");
    @Deprecated
    public static final ResultCode USER_CANCEL = new ResultCode(10003, "USER_CANCEL");
    public int code;
    public String message;

    public ResultCode(int i) {
        this(i, null);
    }

    public ResultCode(int i, String str) {
        this.code = i;
        this.message = str;
    }

    public static ResultCode create(int i, Object... objArr) {
        return new ResultCode(i, MessageUtils.getMessageContent(i, objArr));
    }

    public static ResultCode create(Message message2) {
        return new ResultCode(message2.code, message2.message);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.code == ((ResultCode) obj).code;
    }

    public int hashCode() {
        return this.code + 31;
    }

    public boolean isSuccess() {
        return this.code == 100;
    }
}
