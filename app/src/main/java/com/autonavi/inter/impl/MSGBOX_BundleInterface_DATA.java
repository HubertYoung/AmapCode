package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;
import com.autonavi.minimap.bundle.msgbox.impl.MsgboxService;
import java.util.HashMap;
import proguard.annotation.KeepName;

@KeepName
public final class MSGBOX_BundleInterface_DATA extends HashMap {
    public MSGBOX_BundleInterface_DATA() {
        put(IMsgboxService.class, MsgboxService.class);
    }
}
