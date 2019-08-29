package com.alipay.android.phone.inside.api.model.iotads;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.iotads.IotAdsPayResultCode;

public class IotAdsPayResultModel extends IotAdsBaseModel<IotAdsPayResultCode> {
    private String action;
    private String appId;
    private String barToken;
    private String btnName;
    private boolean doubleDisplays;
    private String extInfo;
    private int payTimeOut;
    private int resultTimeOut;
    private boolean showLoading = true;
    private String userId;

    public String getBarToken() {
        return this.barToken;
    }

    public void setBarToken(String str) {
        this.barToken = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public String getBtnName() {
        return this.btnName;
    }

    public void setBtnName(String str) {
        this.btnName = str;
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

    public boolean isDoubleDisplays() {
        return this.doubleDisplays;
    }

    public void setDoubleDisplays(boolean z) {
        this.doubleDisplays = z;
    }

    public IBizOperation<IotAdsPayResultCode> getOperaion() {
        return new IBizOperation<IotAdsPayResultCode>() {
            public IotAdsPayResultCode parseResultCode(String str, String str2) {
                return IotAdsPayResultCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.IOT_ADS_PAY_RESULT;
            }
        };
    }
}
