package com.autonavi.inter;

import java.util.List;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IRouterLoader {
    List<Class> findRouterClass(String str);
}
