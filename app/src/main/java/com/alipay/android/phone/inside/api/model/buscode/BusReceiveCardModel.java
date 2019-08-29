package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusReceiveCardCode;

public class BusReceiveCardModel extends BaseOpenAuthModel<BusReceiveCardCode> {
    private String cardNo;
    private String cardType;
    private int minVersionCode;

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

    public int getMinVersionCode() {
        return this.minVersionCode;
    }

    public void setMinVersionCode(int i) {
        this.minVersionCode = i;
    }

    public IBizOperation<BusReceiveCardCode> getOperaion() {
        return new IBizOperation<BusReceiveCardCode>() {
            public BusReceiveCardCode parseResultCode(String str, String str2) {
                return BusReceiveCardCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_RECEIVE_CARD_ACTION;
            }
        };
    }
}
