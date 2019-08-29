package com.alipay.android.phone.inside.api.model.accountopenauth;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.accountopenauth.AFastOAuthCode;

public class AFastOAuthModel extends BaseOpenAuthModel<AFastOAuthCode> {
    private long authUUID;
    private String authUrlAddress;
    private String authUrlParams;
    private boolean isRecommend;
    private boolean needShowFastAuthPage;
    private String phoneNumber;

    public String getAuthUrlParams() {
        return this.authUrlParams;
    }

    public void setAuthUrlParams(String str) {
        this.authUrlParams = str;
    }

    public boolean isRecommend() {
        return this.isRecommend;
    }

    public void setRecommend(boolean z) {
        this.isRecommend = z;
    }

    public String getAuthUrlAddress() {
        return this.authUrlAddress;
    }

    public void setAuthUrlAddress(String str) {
        this.authUrlAddress = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public boolean isNeedShowFastAuthPage() {
        return this.needShowFastAuthPage;
    }

    public void setNeedShowFastAuthPage(boolean z) {
        this.needShowFastAuthPage = z;
    }

    public long getAuthUUID() {
        return this.authUUID;
    }

    public void setAuthUUID(long j) {
        this.authUUID = j;
    }

    public IBizOperation<AFastOAuthCode> getOperaion() {
        return new IBizOperation<AFastOAuthCode>() {
            public AFastOAuthCode parseResultCode(String str, String str2) {
                return AFastOAuthCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ALIPAY_FAST_OAUTH;
            }
        };
    }
}
