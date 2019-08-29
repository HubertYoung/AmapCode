package com.alipay.android.phone.inside.api.model.request;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.code.SwitchChannelCode;

public class SwitchChannelModel extends BaseOpenAuthModel<SwitchChannelCode> {
    public IBizOperation<SwitchChannelCode> getOperaion() {
        return new IBizOperation<SwitchChannelCode>() {
            public SwitchChannelCode parseResultCode(String str, String str2) {
                return SwitchChannelCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.SWITCH_CHANNEL;
            }
        };
    }
}
