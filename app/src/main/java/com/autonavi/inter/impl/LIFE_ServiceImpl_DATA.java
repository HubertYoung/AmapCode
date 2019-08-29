package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.life.intent.inter.ILifeIntentDispatcherFactory;
import com.autonavi.minimap.life.intent.inter.impl.LifeIntentDispatcherFactoryImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.life.intent.inter.impl.LifeIntentDispatcherFactoryImpl", "com.autonavi.minimap.life.sketchscenic.SketchScenicHandler"}, inters = {"com.autonavi.minimap.life.intent.inter.ILifeIntentDispatcherFactory", "com.autonavi.bundle.amaphome.api.ISketchScenicHandler"}, module = "life")
@KeepName
public final class LIFE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public LIFE_ServiceImpl_DATA() {
        put(ILifeIntentDispatcherFactory.class, LifeIntentDispatcherFactoryImpl.class);
        put(apt.class, dqu.class);
    }
}
