package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.carowner.payfor.ApplyPayForListFragment;
import com.autonavi.carowner.payfor.ApplyPayForTypeChooseFragment;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentListPage;
import com.autonavi.minimap.drive.auto.page.AutoConnectionManagerFragment;
import com.autonavi.minimap.drive.auto.page.RemoteControlFragment;
import com.autonavi.minimap.drive.errorreport.NavigationErrorReportFragment;
import com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment;
import com.autonavi.minimap.drive.fragment.CarPlateInputFragment;
import com.autonavi.minimap.drive.fragment.TruckPlateInputPage;
import com.autonavi.minimap.drive.restrictedarea.RestrictedCityListFragment;
import com.autonavi.minimap.drive.route.VoiceTrafficABFragment;
import com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment;
import com.autonavi.minimap.drive.sticker.page.StickersPage;
import com.autonavi.minimap.drive.trafficboard.page.TrafficBoardPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.drive.action.remote.control", "amap.basemap.action.traffic_board", "amap.drive.action.trafficab", "drive.search.fragment.SearchCallbackFragment", "apply_pay_type_choose", "amap.basemap.action.route_car_error_report", "amap.drive.action.stickers", "amap.basemap.action.truck_plate_input", "amap.basemap.action.car_plate_input", "amap.basemap.action.navigation_error_report", "apply_pay_main_page", "amap.basemap.action.car_restrict_city_list", "amap.drive.action.alicar.manage", "amap.drive.action.road.camera", "search.fragment.SearchCallbackFragment"}, module = "tripgroup", pages = {"com.autonavi.minimap.drive.auto.page.RemoteControlFragment", "com.autonavi.minimap.drive.trafficboard.page.TrafficBoardPage", "com.autonavi.minimap.drive.route.VoiceTrafficABFragment", "com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment", "com.autonavi.carowner.payfor.ApplyPayForTypeChooseFragment", "com.autonavi.minimap.drive.errorreport.RouteCarResultErrorReportFragment", "com.autonavi.minimap.drive.sticker.page.StickersPage", "com.autonavi.minimap.drive.fragment.TruckPlateInputPage", "com.autonavi.minimap.drive.fragment.CarPlateInputFragment", "com.autonavi.minimap.drive.errorreport.NavigationErrorReportFragment", "com.autonavi.carowner.payfor.ApplyPayForListFragment", "com.autonavi.minimap.drive.restrictedarea.RestrictedCityListFragment", "com.autonavi.minimap.drive.auto.page.AutoConnectionManagerFragment", "com.autonavi.carowner.roadcamera.page.RdCameraPaymentListPage", "com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment"})
@KeepName
public final class TRIPGROUP_PageAction_DATA extends HashMap<String, Class<?>> {
    public TRIPGROUP_PageAction_DATA() {
        put("amap.drive.action.remote.control", RemoteControlFragment.class);
        put("amap.basemap.action.traffic_board", TrafficBoardPage.class);
        put("amap.drive.action.trafficab", VoiceTrafficABFragment.class);
        put("drive.search.fragment.SearchCallbackFragment", DriveSearchCallbackFragment.class);
        put("apply_pay_type_choose", ApplyPayForTypeChooseFragment.class);
        put("amap.basemap.action.route_car_error_report", RouteCarResultErrorReportFragment.class);
        put("amap.drive.action.stickers", StickersPage.class);
        put("amap.basemap.action.truck_plate_input", TruckPlateInputPage.class);
        put("amap.basemap.action.car_plate_input", CarPlateInputFragment.class);
        put("amap.basemap.action.navigation_error_report", NavigationErrorReportFragment.class);
        put("apply_pay_main_page", ApplyPayForListFragment.class);
        put("amap.basemap.action.car_restrict_city_list", RestrictedCityListFragment.class);
        put("amap.drive.action.alicar.manage", AutoConnectionManagerFragment.class);
        put("amap.drive.action.road.camera", RdCameraPaymentListPage.class);
        put("search.fragment.SearchCallbackFragment", SearchCallbackFragment.class);
    }
}
