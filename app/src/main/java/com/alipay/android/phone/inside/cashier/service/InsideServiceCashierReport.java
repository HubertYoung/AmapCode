package com.alipay.android.phone.inside.cashier.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.cashier.utils.CashierOperation;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;

public class InsideServiceCashierReport extends AbstractInsideService<Bundle, Bundle> {
    public Bundle startForResult(Bundle bundle) throws Exception {
        Bundle bundle2 = new Bundle();
        bundle2.putString("action", CashierOperation.BIZ_CASHIER_REPORT);
        bundle2.putString("identify", Long.toString(System.currentTimeMillis()));
        bundle2.putString("extend_params", ExtendParamsService.getDefaultExt());
        return new CashierOperation().requestOperationResult(getContext(), CashierOperation.BIZ_CASHIER_REPORT, bundle2);
    }
}
