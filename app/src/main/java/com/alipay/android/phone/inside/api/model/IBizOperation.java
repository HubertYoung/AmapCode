package com.alipay.android.phone.inside.api.model;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.ResultCode;

public interface IBizOperation<T extends ResultCode> {
    ActionEnum getAction();

    T parseResultCode(String str, String str2);
}
