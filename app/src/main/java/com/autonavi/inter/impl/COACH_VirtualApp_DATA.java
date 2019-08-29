package com.autonavi.inter.impl;

import com.autonavi.minimap.route.coach.CoachVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class COACH_VirtualApp_DATA extends ArrayList<Class<?>> {
    public COACH_VirtualApp_DATA() {
        add(CoachVApp.class);
    }
}
