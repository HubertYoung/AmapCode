package com.autonavi.inter;

import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IServiceLoader {
    <T> Class<? extends T> getService(Class<T> cls);
}
