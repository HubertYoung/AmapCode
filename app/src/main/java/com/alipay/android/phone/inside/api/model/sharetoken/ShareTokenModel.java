package com.alipay.android.phone.inside.api.model.sharetoken;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.sharetoken.ShareTokenCode;

public class ShareTokenModel extends BaseModel<ShareTokenCode> {
    private String content;

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public IBizOperation<ShareTokenCode> getOperaion() {
        return new IBizOperation<ShareTokenCode>() {
            public ShareTokenCode parseResultCode(String str, String str2) {
                return ShareTokenCode.SUCCESS;
            }

            public ActionEnum getAction() {
                return ActionEnum.SHARE_TOKEN;
            }
        };
    }
}
