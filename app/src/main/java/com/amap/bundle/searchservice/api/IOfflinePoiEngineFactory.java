package com.amap.bundle.searchservice.api;

import com.autonavi.ae.search.SearchEngine;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IOfflinePoiEngineFactory extends bie {
    boolean a();

    SearchEngine b();
}
