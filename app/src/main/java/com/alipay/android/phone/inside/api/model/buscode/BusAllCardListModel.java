package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusAllCardListCode;

public class BusAllCardListModel extends BaseOpenAuthModel<BusAllCardListCode> {
    private String cityCode;

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String str) {
        this.cityCode = str;
    }

    public IBizOperation<BusAllCardListCode> getOperaion() {
        return new IBizOperation<BusAllCardListCode>() {
            public BusAllCardListCode parseResultCode(String str, String str2) {
                return BusAllCardListCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_ALL_CARD_LIST_ACTION;
            }
        };
    }
}
