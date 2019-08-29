package com.autonavi.minimap.ajx3.modules.platform;

import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleUseridentifier;

public class AjxModuleUseridentifier extends AbstractModuleUseridentifier {
    public String getPushToken() {
        return null;
    }

    public AjxModuleUseridentifier(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public String getCifa() {
        return NetworkParam.getCifa();
    }

    public String getAdiu() {
        return NetworkParam.getAdiu();
    }

    public String getTid() {
        return NetworkParam.getTaobaoID();
    }
}
