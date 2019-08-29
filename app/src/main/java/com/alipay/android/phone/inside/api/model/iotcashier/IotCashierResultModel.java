package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierResultCode;

public class IotCashierResultModel extends IotCashierBaseModel<IotCashierResultCode> {
    private String appId;
    private String barToken;
    private String btnAction;
    private String btnName;
    private String extInfo;
    private int payTimeOut;
    private int resultTimeOut;
    private boolean showLoading = true;
    private String userId;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getBtnAction() {
        return this.btnAction;
    }

    public void setBtnAction(String str) {
        this.btnAction = str;
    }

    public String getBtnName() {
        return this.btnName;
    }

    public void setBtnName(String str) {
        this.btnName = str;
    }

    public String getBarToken() {
        return this.barToken;
    }

    public void setBarToken(String str) {
        this.barToken = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(String str) {
        this.extInfo = str;
    }

    public boolean isShowLoading() {
        return this.showLoading;
    }

    public void setShowLoading(boolean z) {
        this.showLoading = z;
    }

    public int getPayTimeOut() {
        return this.payTimeOut;
    }

    public void setPayTimeOut(int i) {
        this.payTimeOut = i;
    }

    public int getResultTimeOut() {
        return this.resultTimeOut;
    }

    public void setResultTimeOut(int i) {
        this.resultTimeOut = i;
    }

    public IBizOperation<IotCashierResultCode> getOperaion() {
        return new IBizOperation<IotCashierResultCode>() {
            public IotCashierResultCode parseResultCode(String str, String str2) {
                return IotCashierResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_RESULT;
            }
        };
    }
}
