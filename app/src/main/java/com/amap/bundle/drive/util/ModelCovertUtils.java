package com.amap.bundle.drive.util;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.model.POIInfo;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.List;

public final class ModelCovertUtils {

    public enum POIType {
        START,
        VIA,
        END
    }

    public static POIInfo[] a(POI poi, List<GeoPoint> list, POIType pOIType) {
        return new POIInfo[]{b(poi, list, pOIType)};
    }

    private static POIInfo b(POI poi, List<GeoPoint> list, POIType pOIType) {
        POIInfo pOIInfo = new POIInfo();
        if (DriveUtil.isLegalPoiId(poi.getId())) {
            pOIInfo.poiID = poi.getId();
        }
        pOIInfo.latitude = (double) ((float) poi.getPoint().getLatitude());
        pOIInfo.longitude = (double) ((float) poi.getPoint().getLongitude());
        pOIInfo.typeCode = poi.getType();
        pOIInfo.type = DriveUtil.genPointType(poi);
        if (!(list == null || list.size() <= 0 || list.get(0) == null)) {
            pOIInfo.naviLat = (double) ((float) list.get(0).getLatitude());
            pOIInfo.naviLon = (double) ((float) list.get(0).getLongitude());
        }
        pOIInfo.name = poi.getName();
        new rq();
        if (rq.b()) {
            float a = rx.a(LocationInstrument.getInstance().getGpsStatus(null));
            if (a < 0.0f || a > 1.0f) {
                pOIInfo.sigshelter = -1.0f;
            } else {
                pOIInfo.sigshelter = a;
            }
            StringBuilder sb = new StringBuilder("toRoutePoi : routePoi.mSigshelter = ");
            sb.append(pOIInfo.sigshelter);
            AMapLog.d("pressure", sb.toString());
        }
        if (pOIType != null && pOIType == POIType.END) {
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            pOIInfo.parentID = iSearchPoiData.getParent();
            pOIInfo.parentRel = iSearchPoiData.getChildType();
            pOIInfo.floorName = iSearchPoiData.getFnona();
            pOIInfo.angle = iSearchPoiData.getTowardsAngle();
            String endPoiExtension = iSearchPoiData.getEndPoiExtension();
            if (TextUtils.isEmpty(endPoiExtension)) {
                endPoiExtension = "0";
            }
            pOIInfo.extendInfoFlag = endPoiExtension;
        }
        return pOIInfo;
    }
}
