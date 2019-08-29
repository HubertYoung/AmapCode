package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.offlinecode.storage.CardDataStorage;
import org.json.JSONObject;

public class BusCodeStatusService extends AbstractInsideService<Bundle, Boolean> {
    public Boolean startForResult(Bundle bundle) throws Exception {
        JSONObject cardMapByUser = CardDataStorage.getInstance().getCardMapByUser(getContext());
        if (cardMapByUser == null || cardMapByUser.length() <= 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
