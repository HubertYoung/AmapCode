package com.alipay.mobile.tinyappcustom.h5plugin;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.provider.H5PreRpcProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcustom.b.b;

public class H5RpcPlugin extends H5SimplePlugin {
    public static final String RPC = "rpc";
    public static final String TAG = "H5RpcPlugin";
    private H5PreRpcProvider a;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("rpc");
        this.a = new WalletPreRpcProvider();
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return handleEvent(event, context);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!"rpc".equals(event.getAction())) {
            return false;
        }
        H5Log.d("H5RpcPlugin", "get action: rpc");
        a(event, context);
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        H5Utils.getExecutor(H5ThreadType.URGENT).execute(new b(event, bridgeContext, this.a));
    }
}
