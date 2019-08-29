package com.autonavi.minimap.route.logs.operation;

import com.autonavi.minimap.archivement.ArchivementRequestHolder;
import com.autonavi.minimap.archivement.param.ReportRequest;

public final class UpLoadOperationDataUtil {

    public enum OperationType {
        TYPE_BUS_ROUTE_SELECT,
        TYPE_BUS_NAV,
        TYPE_FOOT_NAV,
        TYPE_DRIVE_NAV,
        TYPE_FOOT_ROUTE_SELECT,
        TYPE_RIDE_NAV,
        TYPE_ELE_RIDE_NAV,
        TYPE_RIDE_ROUTE_SELECT,
        TYPE_ELE_RIDE_ROUTE_SELECT,
        TYPE_HEALTH_RIDE,
        TYPE_HEALTH_RUN,
        TYPE_HEALTH_RUN_RECOMMEND,
        TYPE_SHAREBIKE_END,
        TYPE_ADD_ROUTECOMMUTE_SHORTCUT
    }

    public static void a(OperationType operationType, int i, int i2, int i3) {
        ReportRequest buildParam = OperationCollectionParam.buildParam(a(operationType), i, i2, i3);
        ArchivementRequestHolder.getInstance().sendReport(buildParam, new OperationCollectionRequestCallback());
        StringBuilder sb = new StringBuilder("## upload oprationcollection :");
        sb.append(buildParam.b);
        sb.append(",");
        sb.append(buildParam.f);
        sb.append(",");
        sb.append(buildParam.c);
        sb.append(",");
        sb.append(buildParam.d);
        sb.append(",");
        sb.append(buildParam.e);
        eao.a(sb.toString());
    }

    public static int a(OperationType operationType) {
        switch (operationType) {
            case TYPE_BUS_ROUTE_SELECT:
                return 1;
            case TYPE_BUS_NAV:
                return 2;
            case TYPE_FOOT_NAV:
                return 3;
            case TYPE_DRIVE_NAV:
                return 4;
            case TYPE_FOOT_ROUTE_SELECT:
                return 5;
            case TYPE_RIDE_NAV:
                return 6;
            case TYPE_ELE_RIDE_NAV:
                return 13;
            case TYPE_RIDE_ROUTE_SELECT:
                return 7;
            case TYPE_ELE_RIDE_ROUTE_SELECT:
                return 12;
            case TYPE_HEALTH_RIDE:
                return 8;
            case TYPE_HEALTH_RUN:
                return 9;
            case TYPE_HEALTH_RUN_RECOMMEND:
                return 10;
            case TYPE_SHAREBIKE_END:
                return 11;
            case TYPE_ADD_ROUTECOMMUTE_SHORTCUT:
                return 19;
            default:
                return 0;
        }
    }
}
