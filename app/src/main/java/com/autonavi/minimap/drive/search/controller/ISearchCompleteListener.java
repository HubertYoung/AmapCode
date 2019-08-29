package com.autonavi.minimap.drive.search.controller;

import com.autonavi.common.model.POI;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface ISearchCompleteListener {
    void a(POI poi);

    void a(String str);
}
