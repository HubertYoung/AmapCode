package com.alipay.android.phone.inside.api.model.operation;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.OfflineRenderCode;

public class OfflineRenderOp implements IBizOperation<OfflineRenderCode> {
    public OfflineRenderCode parseResultCode(String str, String str2) {
        return OfflineRenderCode.parse(str2);
    }

    public ActionEnum getAction() {
        return ActionEnum.OFFLINE_RENDER;
    }
}
