package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.AuthOp;
import com.alipay.android.phone.inside.api.result.code.AuthCode;

public class AuthRequestModel extends BaseOpenAuthModel<AuthCode> {
    private String authBizData;
    private String currentUserPhoneNo;
    private String mcLoginId;
    private String mcLoginUserPhone;
    private String pushDeviceId;

    public String getAuthBizData() {
        return this.authBizData;
    }

    public void setAuthBizData(String str) {
        this.authBizData = str;
    }

    public String getPushDeviceId() {
        return this.pushDeviceId;
    }

    public void setPushDeviceId(String str) {
        this.pushDeviceId = str;
    }

    public String getMcLoginId() {
        return this.mcLoginId;
    }

    public void setMcLoginId(String str) {
        this.mcLoginId = str;
    }

    public String getMcLoginUserPhone() {
        return this.mcLoginUserPhone;
    }

    public void setMcLoginUserPhone(String str) {
        this.mcLoginUserPhone = str;
    }

    public String getCurrentUserPhoneNo() {
        return this.currentUserPhoneNo;
    }

    public void setCurrentUserPhoneNo(String str) {
        this.currentUserPhoneNo = str;
    }

    public IBizOperation<AuthCode> getOperaion() {
        return new AuthOp();
    }
}
