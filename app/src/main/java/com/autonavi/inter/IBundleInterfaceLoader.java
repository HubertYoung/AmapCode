package com.autonavi.inter;

import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IBundleInterfaceLoader {
    <T> Class<? extends T> getBundle(Class<T> cls);
}
