package com.alipay.android.phone.mobilecommon.multimediabiz.biz.security;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Arrays;

public class AftsSecurityMsg {
    public static final String OPERATION_DELETE = "delete";
    @JSONField(name = "file_id")
    public String[] fileIds;
    @JSONField(name = "op")

    /* renamed from: operation reason: collision with root package name */
    public String f18operation;

    public String toString() {
        return "AftsSecurityMsg{operation='" + this.f18operation + '\'' + ", fileIds=" + Arrays.toString(this.fileIds) + '}';
    }
}
