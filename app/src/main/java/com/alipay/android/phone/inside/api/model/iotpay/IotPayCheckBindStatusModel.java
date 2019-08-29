package com.alipay.android.phone.inside.api.model.iotpay;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotpay.IotPayCheckBindStatusCode;

public class IotPayCheckBindStatusModel extends IotPayBaseModel<IotPayCheckBindStatusCode> {
    private boolean queryFacePayAudit;

    public boolean isQueryFacePayAudit() {
        return this.queryFacePayAudit;
    }

    public void setQueryFacePayAudit(boolean z) {
        this.queryFacePayAudit = z;
    }

    public IBizOperation<IotPayCheckBindStatusCode> getOperaion() {
        return new IBizOperation<IotPayCheckBindStatusCode>() {
            public IotPayCheckBindStatusCode parseResultCode(String str, String str2) {
                return IotPayCheckBindStatusCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_PAY_CHECK_BIND_STATUS;
            }
        };
    }
}
