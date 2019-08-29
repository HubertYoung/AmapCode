package com.alipay.android.phone.inside.api.model.iotcashier;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotcashier.IotCashierPayAndResultCode;

public class IotCashierPayAndResultModel extends BaseModel<IotCashierPayAndResultCode> {
    private String appId;
    private String btnAction;
    private String btnName;
    private String extInfo;
    private String ftoken;
    private String outOrderNo;
    private String payCode;
    private int payTimeOut;
    private int resultTimeOut;
    private String subject;
    private String totalAmount;
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

    public String getPayCode() {
        return this.payCode;
    }

    public void setPayCode(String str) {
        this.payCode = str;
    }

    public String getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(String str) {
        this.totalAmount = str;
    }

    public String getOutOrderNo() {
        return this.outOrderNo;
    }

    public void setOutOrderNo(String str) {
        this.outOrderNo = str;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String str) {
        this.subject = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getFtoken() {
        return this.ftoken;
    }

    public void setFtoken(String str) {
        this.ftoken = str;
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

    public String getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(String str) {
        this.extInfo = str;
    }

    public IBizOperation<IotCashierPayAndResultCode> getOperaion() {
        return new IBizOperation<IotCashierPayAndResultCode>() {
            public IotCashierPayAndResultCode parseResultCode(String str, String str2) {
                return IotCashierPayAndResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_CASHIER_PAY_AND_RESULT;
            }
        };
    }
}
