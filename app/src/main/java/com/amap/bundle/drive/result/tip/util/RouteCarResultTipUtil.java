package com.amap.bundle.drive.result.tip.util;

public final class RouteCarResultTipUtil {

    public enum TipType {
        RESTRICT_OTHER_PLACE_SET_PLATE(0),
        RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH(1),
        RESTRICT_ALREADY_AVOID_RESTRICT_AREA(2),
        RESTRICT_START_POI_IN_RESTRICT_AREA(3),
        RESTRICT_END_POI_IN_RESTRICT_AREA(4),
        RESTRICT_MID_POI_IN_RESTRICT_AREA(5),
        RESTRICT_ACROSS_RESTRICT_AREA(6),
        RESTRICT_SOON_EFFECT_AVOID_RESTRICT_AREA(7),
        RESTRICT_SOON_END_ACROSS_RESTRICT_AREA(8),
        RESTRICT_ETD_UNABLE_AVOID_RESTRICT(9),
        RESTRICT_ETD_AVOID_RESTRICT(10),
        INCIDENT_UNAVOIDABLE_INCIDENT_AT_END(7),
        INCIDENT_UNAVOIDABLE_INCIDENT_AT_MID(8),
        INCIDENT_UNAVOIDABLE_INCIDENT_AT_OTHER(9),
        INCIDENT_UNAVOIDABLE_INCIDENT_AT_START(10),
        INCIDENT_AVOIDABLE_INCIDENT(11),
        JAM_INFO(12),
        INCIDENT_NOT_CLOSE_ROUTE(13),
        SCHEDULE_ROUTE(14),
        SCHEDULE_END(15),
        INVALID_TYPE(Integer.MAX_VALUE);
        
        public int mPriority;

        private TipType(int i) {
            this.mPriority = i;
        }
    }

    public static TipType a(int i) {
        switch (i) {
            case 0:
                return TipType.RESTRICT_OTHER_PLACE_SET_PLATE;
            case 1:
                return TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH;
            case 2:
                return TipType.RESTRICT_ALREADY_AVOID_RESTRICT_AREA;
            case 3:
                return TipType.RESTRICT_START_POI_IN_RESTRICT_AREA;
            case 4:
                return TipType.RESTRICT_END_POI_IN_RESTRICT_AREA;
            case 5:
                return TipType.RESTRICT_MID_POI_IN_RESTRICT_AREA;
            case 6:
                return TipType.RESTRICT_ACROSS_RESTRICT_AREA;
            case 7:
                return TipType.RESTRICT_SOON_EFFECT_AVOID_RESTRICT_AREA;
            case 8:
                return TipType.RESTRICT_SOON_END_ACROSS_RESTRICT_AREA;
            case 9:
                return TipType.RESTRICT_ETD_UNABLE_AVOID_RESTRICT;
            case 10:
                return TipType.RESTRICT_ETD_AVOID_RESTRICT;
            default:
                return TipType.INVALID_TYPE;
        }
    }

    public static TipType b(int i) {
        switch (i) {
            case 0:
                return TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_START;
            case 1:
                return TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_MID;
            case 2:
                return TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_END;
            case 3:
                return TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_OTHER;
            case 4:
                return TipType.INCIDENT_AVOIDABLE_INCIDENT;
            case 5:
                return TipType.INCIDENT_NOT_CLOSE_ROUTE;
            case 6:
                return TipType.SCHEDULE_ROUTE;
            case 7:
                return TipType.SCHEDULE_END;
            default:
                return TipType.INVALID_TYPE;
        }
    }
}
