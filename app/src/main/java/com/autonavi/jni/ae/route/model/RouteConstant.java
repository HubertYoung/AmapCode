package com.autonavi.jni.ae.route.model;

public class RouteConstant {

    public static final class AbnormalState {
        public static final int ETbtAbnormalState_CheckFail = -1;
        public static final int ETbtAbnormalState_Normal = 0;
        public static final int ETbtAbnormalState_Other = -3;
        public static final int ETbtAbnormalState_Outside = -2;
    }

    public static final class IconType {
        public static final int ICONTYPE_FOOT = 0;
        public static final int ICONTYPE_MANNED_FERRY = 2;
        public static final int ICONTYPE_VECHICLE_FERRY = 1;
        public static final int LineIconTypeNULL = -1;
    }

    public static final class LineStatus {
        public static final int STATUS_AMBLE = 4;
        public static final int STATUS_ARROW = -1;
        public static final int STATUS_CHARGE = 16;
        public static final int STATUS_DEFAULT = 2;
        public static final int STATUS_FASTER = 128;
        public static final int STATUS_FERRY = 7;
        public static final int STATUS_FREE = 0;
        public static final int STATUS_INNER_NAVI = 1;
        public static final int STATUS_INNER_NOT_NAVI = 0;
        public static final int STATUS_JAM = 5;
        public static final int STATUS_LIMIT = 32;
        public static final int STATUS_NORMAL = 0;
        public static final int STATUS_NO_LIMIT = 0;
        public static final int STATUS_OPEN = 3;
        public static final int STATUS_SLOWER = 64;
        public static final int STAUTS_CONGESTED = 6;
    }

    public static final class NetError {
        public static final int ETbtNetError_NoNetConn = 3;
        public static final int ETbtNetError_Other = -1;
        public static final int ETbtNetError_TimeOut = 1;
        public static final int ETbtNetError_UserCancel = 2;
    }

    public static final class RequestState {
        public static final int ETbtRequestState_CallCenterError = 5;
        public static final int ETbtRequestState_DataBufError = 9;
        public static final int ETbtRequestState_EncodeFalse = 7;
        public static final int ETbtRequestState_EndNoRoad = 11;
        public static final int ETbtRequestState_EndPointFalse = 6;
        public static final int ETbtRequestState_HalfwayNoRoad = 12;
        public static final int ETbtRequestState_IllegalRequest = 4;
        public static final int ETbtRequestState_LackEndData = 24;
        public static final int ETbtRequestState_LackPreview = 8;
        public static final int ETbtRequestState_LackStartData = 18;
        public static final int ETbtRequestState_LackViaData = 25;
        public static final int ETbtRequestState_LackWayCityData = 20;
        public static final int ETbtRequestState_NULL = 0;
        public static final int ETbtRequestState_NetERROR = 2;
        public static final int ETbtRequestState_NetTimeOut = 16;
        public static final int ETbtRequestState_NoNetConn = 17;
        public static final int ETbtRequestState_OfflineRouteFail = 14;
        public static final int ETbtRequestState_SilentRerouteNoMeetCriteria = 23;
        public static final int ETbtRequestState_StartNoRoad = 10;
        public static final int ETbtRequestState_StartPointFalse = 3;
        public static final int ETbtRequestState_Success = 1;
        public static final int ETbtRequestState_TooFar = 19;
        public static final int ETbtRequestState_UnknownRouteFail = 13;
        public static final int ETbtRequestState_Updating = 22;
        public static final int ETbtRequestState_UserCancel = 15;
        public static final int ETbtRequestState_ViaPointFalse = 21;
    }

    public static final class RouteType {
        public static final int RouteTypeAvoid = 15;
        public static final int RouteTypeChangeJnyPnt = 9;
        public static final int RouteTypeChangeStratege = 3;
        public static final int RouteTypeCommon = 1;
        public static final int RouteTypeDamagedRoad = 7;
        public static final int RouteTypeDispatch = 16;
        public static final int RouteTypeLimitForbid = 11;
        public static final int RouteTypeLimitForbidOffLine = 13;
        public static final int RouteTypeLimitLine = 6;
        public static final int RouteTypeManualRefresh = 12;
        public static final int RouteTypeMax = 17;
        public static final int RouteTypeMutiRouteRequest = 14;
        public static final int RouteTypeParallelRoad = 4;
        public static final int RouteTypePressure = 8;
        public static final int RouteTypeTMC = 5;
        public static final int RouteTypeUpdateCityData = 10;
        public static final int RouteTypeYaw = 2;
    }

    public interface WeatherType {
        public static final int WeatherTypeAlert = 2;
        public static final int WeatherTypeHourly = 1;
    }
}
