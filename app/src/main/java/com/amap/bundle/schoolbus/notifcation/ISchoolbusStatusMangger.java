package com.amap.bundle.schoolbus.notifcation;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface ISchoolbusStatusMangger extends bie {
    boolean isNeedOnbackground();

    boolean isTravelling();
}
