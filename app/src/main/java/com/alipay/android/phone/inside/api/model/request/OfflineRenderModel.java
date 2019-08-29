package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.OfflineRenderOp;
import com.alipay.android.phone.inside.api.result.code.OfflineRenderCode;

public class OfflineRenderModel extends BaseModel<OfflineRenderCode> {
    private String renderData;

    public void setRenderData(String str) {
        this.renderData = str;
    }

    public String getRenderData() {
        return this.renderData;
    }

    public IBizOperation<OfflineRenderCode> getOperaion() {
        return new OfflineRenderOp();
    }
}
