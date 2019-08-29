package com.autonavi.inter.impl;

import com.autonavi.bundle.msgbox.MsgboxVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class MSGBOX_VirtualApp_DATA extends ArrayList<Class<?>> {
    public MSGBOX_VirtualApp_DATA() {
        add(MsgboxVApp.class);
    }
}
