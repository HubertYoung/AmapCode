package com.alipay.android.phone.inside.api.model.aliautologin;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.aliautologin.PreLoadConfigCode;

public class PreLoadConfigModel extends BaseModel {
    public IBizOperation getOperaion() {
        return new IBizOperation<PreLoadConfigCode>() {
            public PreLoadConfigCode parseResultCode(String str, String str2) {
                return PreLoadConfigCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.PRE_LOAD_CONFIG_ACTION;
            }
        };
    }
}
