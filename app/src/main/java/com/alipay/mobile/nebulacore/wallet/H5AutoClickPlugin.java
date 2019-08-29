package com.alipay.mobile.nebulacore.wallet;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebulacore.Nebula;

public class H5AutoClickPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("autoClick");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!TextUtils.equals(event.getAction(), "autoClick")) {
            return super.handleEvent(event, context);
        }
        if (H5Logger.enableStockTradeLog() && Nebula.getH5LogHandler() != null) {
            Nebula.getH5LogHandler().autoClick(event);
        }
        context.sendSuccess();
        return true;
    }
}
