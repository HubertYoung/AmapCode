package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierResultErrorCode;

public class IotCashierResultErrorModel extends IotCashierBaseModel<IotCashierResultErrorCode> {
    private boolean btnRetryVisible;
    private String errorMsg;
    private String extInfo;
    private int resultTimeOut;

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String str) {
        this.errorMsg = str;
    }

    public boolean isBtnRetryVisible() {
        return this.btnRetryVisible;
    }

    public void setBtnRetryVisible(boolean z) {
        this.btnRetryVisible = z;
    }

    public int getResultTimeOut() {
        return this.resultTimeOut;
    }

    public void setResultTimeOut(int i) {
        this.resultTimeOut = i;
    }

    public String getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(String str) {
        this.extInfo = str;
    }

    public IBizOperation<IotCashierResultErrorCode> getOperaion() {
        return new IBizOperation<IotCashierResultErrorCode>() {
            public IotCashierResultErrorCode parseResultCode(String str, String str2) {
                return IotCashierResultErrorCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_RESULT_ERROR;
            }
        };
    }
}
