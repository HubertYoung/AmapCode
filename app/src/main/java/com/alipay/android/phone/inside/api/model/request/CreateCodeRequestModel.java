package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.model.operation.GenerateCodeOp;
import com.alipay.android.phone.inside.api.result.code.GenerateCodeCode;

public class CreateCodeRequestModel extends BaseOpenAuthModel<GenerateCodeCode> {
    public static final String POLICY_DEFAULT = "default";
    public static final String POLICY_LAST_SELECT = "lastSelect";
    public static final String POLICY_NO_CHANNEL = "noChannel";
    private String alipayUserId;
    private String policy;
    private String pushDeviceId;

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String str) {
        this.policy = str;
    }

    public String getPushDeviceId() {
        return this.pushDeviceId;
    }

    public void setPushDeviceId(String str) {
        this.pushDeviceId = str;
    }

    public IBizOperation<GenerateCodeCode> getOperaion() {
        return new GenerateCodeOp();
    }
}
