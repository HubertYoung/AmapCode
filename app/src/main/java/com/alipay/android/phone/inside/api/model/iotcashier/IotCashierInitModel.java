package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierInitCode;

public class IotCashierInitModel extends IotCashierBaseModel<IotCashierInitCode> {
    private String bindClazz;
    private String loadingClazz;
    private String memberResultClazz;
    private String productKey;
    private String resultClazz;
    private String sourceId;

    public String getProductKey() {
        return this.productKey;
    }

    public void setProductKey(String str) {
        this.productKey = str;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String str) {
        this.sourceId = str;
    }

    public String getResultClazz() {
        return this.resultClazz;
    }

    public void setResultClazz(String str) {
        this.resultClazz = str;
    }

    public String getMemberResultClazz() {
        return this.memberResultClazz;
    }

    public void setMemberResultClazz(String str) {
        this.memberResultClazz = str;
    }

    public String getLoadingClazz() {
        return this.loadingClazz;
    }

    public void setLoadingClazz(String str) {
        this.loadingClazz = str;
    }

    public String getBindClazz() {
        return this.bindClazz;
    }

    public void setBindClazz(String str) {
        this.bindClazz = str;
    }

    public IBizOperation<IotCashierInitCode> getOperaion() {
        return new IBizOperation<IotCashierInitCode>() {
            public IotCashierInitCode parseResultCode(String str, String str2) {
                return IotCashierInitCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_INIT;
            }
        };
    }
}
