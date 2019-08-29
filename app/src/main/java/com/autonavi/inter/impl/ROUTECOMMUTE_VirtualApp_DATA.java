package com.autonavi.inter.impl;

import com.autonavi.bundle.routecommute.bundle.CommuteVapp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class ROUTECOMMUTE_VirtualApp_DATA extends ArrayList<Class<?>> {
    public ROUTECOMMUTE_VirtualApp_DATA() {
        add(CommuteVapp.class);
    }
}
