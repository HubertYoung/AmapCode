package com.autonavi.inter.impl;

import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.amap.bundle.schoolbus.notifcation.SchoolbusStatusManagerImpl;
import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.schoolbus.notifcation.SchoolbusStatusManagerImpl"}, inters = {"com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger"}, module = "schoolbus")
@KeepName
public final class SCHOOLBUS_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public SCHOOLBUS_ServiceImpl_DATA() {
        put(ISchoolbusStatusMangger.class, SchoolbusStatusManagerImpl.class);
    }
}
