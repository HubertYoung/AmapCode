package com.autonavi.common.json;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;

@Keep
@KeepClassMembers
@KeepImplementations
public interface IJsonItem {
    IJsonItem fromJson(Object obj, big big);

    Object toJson(big big);
}
