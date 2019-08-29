package com.alipay.android.phone.inside.api.model.wallet;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.wallet.WalletPreloadCode;

public class WalletPreloadModel extends BaseModel<WalletPreloadCode> {
    public IBizOperation<WalletPreloadCode> getOperaion() {
        return new IBizOperation<WalletPreloadCode>() {
            public WalletPreloadCode parseResultCode(String str, String str2) {
                return WalletPreloadCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.WALLET_PRELOAD;
            }
        };
    }
}
