package com.alipay.android.phone.inside.api.model.buscode;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.buscode.BusCitiesCode;

public class BusCitiesModel extends BaseOpenAuthModel<BusCitiesCode> {
    public IBizOperation<BusCitiesCode> getOperaion() {
        return new IBizOperation<BusCitiesCode>() {
            public BusCitiesCode parseResultCode(String str, String str2) {
                return BusCitiesCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.BUS_CITIES_ACTION;
            }
        };
    }
}
