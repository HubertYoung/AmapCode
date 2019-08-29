package com.autonavi.map.core;

import android.content.Context;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailDelegate;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.TrafficPointOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import java.util.List;

public interface IOverlayManager extends btw {
    public static final String EVENT_HEAD_KEY = "traffic_event_head";
    public static final String EVENT_ID_KEY = "traffic_event_id";
    public static final String EVENT_IS_FROM_ROUTE_RESULT = "traffic_event_is_from_route_result";
    public static final String EVENT_LAYERTAG_FROM_ROUTE_RESULT = "event_layertag_from_route_result";
    public static final String FROM_SOURCE_PAGE_KEY = "from_source_page";
    public static final String MAP_ITEM_EXTRA_KEY_IS_SAVE = "is_save";
    public static final String POI_EXTRA_FROM_FAV = "FromFavorite";
    public static final String POI_EXTRA_FROM_FAV_ON_MAP = "FromFavoriteOnMap";
    public static final String POI_EXTRA_IS_SCENIC = "IS_SCENIC";
    public static final String TRAFFIC_EVENT_FLAG_DRIVE_LINE = "traffic_event_from_route";
    public static final String TRAFFIC_GROUP_FLAG_KEY = "traffic_group_flag";
    public static final String TRAFFIC_ITEM_LISTENER = "traffic_item_listener";

    public enum SAVED_POINT_TYPE {
        traffic,
        save,
        geo_code,
        map_point,
        traffic_point
    }

    public interface a {
        void a();

        void a(int i, POI poi);

        void a(POI poi);
    }

    public interface b {
    }

    public static class c {
        public PointOverlayItem a;
        public SAVED_POINT_TYPE b;
    }

    void clearAllFocus();

    void clearAllMapPointRequest();

    void clearScenicSelectMapPois();

    void deleteSaveFocusKey(int i);

    void dimissTips();

    brp getAffectAreaOverlayManager();

    a getDeepInfoOverlayManager();

    MapPointOverlay getGeoCodeOverlay();

    int getGpsAngle();

    cdx getGpsLayer();

    MapPointOverlay getMapPointOverlay();

    TrafficPointOverlay getTrafficPointOverlay();

    xq getUniversalOverlay(UvOverlay uvOverlay);

    void handleTrafficItemClick(PageBundle pageBundle);

    void init(bro bro, bty bty, Context context, List<bts> list);

    boolean isGPSVisible();

    boolean isScenicSelected();

    void recoverSubwayHighlight();

    void removeWhenMapDestroy();

    void resetMapPointAnimatorType();

    void restoreWhenMapCreate();

    int saveFocus();

    void setGPSCenterLocked(boolean z);

    void setGPSShowMode(int i);

    void setGPSVisible(boolean z);

    void setIMapPointRequestingCallBack(b bVar);

    void setIRealtimeBusStateListener(ccz ccz);

    void setMapCommonOverlayListener(btu btu);

    void setPoiDetailDelegate(IPoiDetailDelegate iPoiDetailDelegate);

    void showGpsFooter();

    void showTrafficFooter(int i, int i2, int i3, int i4, boolean z);

    List<c> solveSavedFocusWithKey(int i, boolean z);
}
