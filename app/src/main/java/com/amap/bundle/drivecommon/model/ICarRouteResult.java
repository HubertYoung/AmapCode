package com.amap.bundle.drivecommon.model;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.drive.route.IDriveRouteResult;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMemberNames
@KeepName
@KeepImplementations
public interface ICarRouteResult extends IDriveRouteResult {
    byte[] getBackUpTbtData();

    NavigationPath getFocusNavigationPath();

    NavigationResult getNaviResultData();

    NavigationPath getNavigationPath(int i);

    boolean isMultiRoute();

    int parseData(byte[] bArr, int i, int i2);

    boolean parseTBTData(byte[] bArr);

    void setIsMultiRoute(boolean z);

    void setNaviResultData(POI poi, POI poi2, NavigationResult navigationResult, String str);
}
