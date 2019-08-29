package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.ScanCode;

public class ScanCodeOp implements IBizOperation<ScanCode> {
    public ScanCode parseResultCode(String str, String str2) {
        return ScanCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.SCAN_CODE;
    }
}
