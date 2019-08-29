package com.alipay.android.phone.inside.api.model.afunc;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.afunc.AlipayFuncListCode;

public class AlipayFuncListModel extends BaseModel<AlipayFuncListCode> {
    public IBizOperation<AlipayFuncListCode> getOperaion() {
        return new IBizOperation<AlipayFuncListCode>() {
            public AlipayFuncListCode parseResultCode(String str, String str2) {
                return AlipayFuncListCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.ALIPAY_FUNC_LIST;
            }
        };
    }
}
