package com.autonavi.minimap.app.init;

import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;

public enum Process {
    MAIN("com.autonavi.minimap"),
    PUSH("com.autonavi.minimap:channel"),
    LOTUSPOOL("com.autonavi.minimap:lotuspool"),
    LOCATION("com.autonavi.minimap:locationservice"),
    INSTALLERROR("com.autonavi.minimap:installerror"),
    OTHER(H5ResourceHandlerUtil.OTHER);
    
    public String name;

    private Process(String str) {
        this.name = str;
    }
}
