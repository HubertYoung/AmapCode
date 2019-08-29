package com.autonavi.inter;

import java.util.Map;
import java.util.Set;
import proguard.annotation.Keep;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@Keep
@KeepName
@KeepImplementations
public interface IMainMapFeatureProvider {
    Set<Class<?>> getMainMapFeatures();

    Map<String, Map<Class<?>, Float>> getPriorityMap();
}
