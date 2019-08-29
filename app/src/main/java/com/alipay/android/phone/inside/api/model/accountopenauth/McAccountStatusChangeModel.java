package com.alipay.android.phone.inside.api.model.accountopenauth;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.accountopenauth.McAccountChangeCode;

public class McAccountStatusChangeModel extends BaseOpenAuthModel<McAccountChangeCode> {
    private String deleteAliLoginCookie;
    private String mcAccountStatus;

    public String getMcAccountStatus() {
        return this.mcAccountStatus;
    }

    public void setMcAccountStatus(MCAccountStatusEnum mCAccountStatusEnum) {
        this.mcAccountStatus = mCAccountStatusEnum == null ? "unknown" : mCAccountStatusEnum.getActionName();
    }

    public String getDeleteAliLoginCookie() {
        return this.deleteAliLoginCookie;
    }

    public void setDeleteAliLoginCookie(String str) {
        this.deleteAliLoginCookie = str;
    }

    public IBizOperation<McAccountChangeCode> getOperaion() {
        return new IBizOperation<McAccountChangeCode>() {
            public McAccountChangeCode parseResultCode(String str, String str2) {
                return McAccountChangeCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.MC_ACCOUNT_STATUS_CHANGE;
            }
        };
    }
}
