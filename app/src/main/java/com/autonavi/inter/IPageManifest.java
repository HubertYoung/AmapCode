package com.autonavi.inter;

import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IPageManifest {
    Class<?> getPage(String str);
}
