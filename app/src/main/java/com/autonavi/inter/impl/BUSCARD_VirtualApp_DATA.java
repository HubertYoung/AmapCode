package com.autonavi.inter.impl;

import com.autonavi.bundle.buscard.BusCardVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class BUSCARD_VirtualApp_DATA extends ArrayList<Class<?>> {
    public BUSCARD_VirtualApp_DATA() {
        add(BusCardVApp.class);
    }
}
