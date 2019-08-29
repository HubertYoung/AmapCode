package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.map.VerifyUserServiceManager"}, inters = {"com.autonavi.minimap.basemap.maphome.IVerifyUserServiceManager"}, module = "main")
@KeepName
public final class MAIN_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public MAIN_ServiceImpl_DATA() {
        put(cqc.class, brc.class);
    }
}
