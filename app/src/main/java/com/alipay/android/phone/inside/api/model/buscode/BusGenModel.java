package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusGenCode;

public class BusGenModel extends BaseOpenAuthModel<BusGenCode> {
    private String cardNo;
    private String cardType;

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String str) {
        this.cardType = str;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardNo(String str) {
        this.cardNo = str;
    }

    public IBizOperation<BusGenCode> getOperaion() {
        return new IBizOperation<BusGenCode>() {
            public BusGenCode parseResultCode(String str, String str2) {
                return BusGenCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_GEN_ACTION;
            }
        };
    }
}
