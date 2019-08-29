package com.alipay.android.phone.inside.cashier.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.app.helper.TidHelper;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;

public class CashierTidService extends AbstractInsideService<Bundle, Bundle> {
    static final String BIZ_ACTION_CLEAR_TID = "bizActionClearTid";
    static final String BIZ_ACTION_RESET_TID = "bizActionResetTid";
    static final String KEY_BIZ_ACTION = "bizAction";

    public Bundle startForResult(Bundle bundle) throws Exception {
        String string = bundle.getString(KEY_BIZ_ACTION, "");
        if (TextUtils.equals(string, BIZ_ACTION_CLEAR_TID)) {
            TidHelper.clearTID();
        } else if (TextUtils.equals(string, BIZ_ACTION_RESET_TID)) {
            TidHelper.resetTID(getContext());
        }
        return new Bundle();
    }
}
