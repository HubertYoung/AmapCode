package com.autonavi.inter;

import java.util.List;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IMultipleServiceLoader {
    <T> List<Class<? extends T>> loadServices(Class<T> cls);
}
