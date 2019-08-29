package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusCardListCode;

public class BusCardListModel extends BaseOpenAuthModel<BusCardListCode> {
    public IBizOperation<BusCardListCode> getOperaion() {
        return new IBizOperation<BusCardListCode>() {
            public BusCardListCode parseResultCode(String str, String str2) {
                return BusCardListCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_CARD_LIST_ACTION;
            }
        };
    }
}
