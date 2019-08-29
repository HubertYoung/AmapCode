package com.amap.bundle.drive.ajx.inter;

public interface INaviEnd {
    int getErrorReportNum();

    void reportDestinationError(String str);

    void reportDriveEndError(String str);

    void requestDriveEndOperationActivities(String str);
}
