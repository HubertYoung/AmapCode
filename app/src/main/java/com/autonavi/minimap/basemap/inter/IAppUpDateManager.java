package com.autonavi.minimap.basemap.inter;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IAppUpDateManager extends bie {
    void setLegalDownloadUrl(String str);
}
