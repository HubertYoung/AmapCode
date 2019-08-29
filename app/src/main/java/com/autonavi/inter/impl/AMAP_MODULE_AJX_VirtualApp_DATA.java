package com.autonavi.inter.impl;

import com.autonavi.minimap.ajx3.Ajx3AppLifeCycle;
import com.autonavi.minimap.ajx3.Ajx3Application;
import com.autonavi.minimap.ajx3.assistant.AjxAssistantApplication;
import com.autonavi.minimap.ajx3.modules.MapPageVirtualApplication;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class AMAP_MODULE_AJX_VirtualApp_DATA extends ArrayList<Class<?>> {
    public AMAP_MODULE_AJX_VirtualApp_DATA() {
        add(Ajx3Application.class);
        add(Ajx3AppLifeCycle.class);
        add(AjxAssistantApplication.class);
        add(MapPageVirtualApplication.class);
    }
}
