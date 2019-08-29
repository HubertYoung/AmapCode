package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.common.page.LicenseConfirmPage;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointListPage;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointMapPage;
import com.autonavi.minimap.myProfile.page.CarIllegalDlgPage;
import com.autonavi.minimap.myProfile.page.VerifyUserPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.verifyuser_page", "amap.basemap.action.multpoint_map_page", "amap.basemap.action.weizhang", "amap.basemap.action.multpoint_lis_page", "amap.basemap.action.licenseconfirm_page"}, module = "amap_module_operation", pages = {"com.autonavi.minimap.myProfile.page.VerifyUserPage", "com.autonavi.minimap.basemap.multipoint.page.MultiPointMapPage", "com.autonavi.minimap.myProfile.page.CarIllegalDlgPage", "com.autonavi.minimap.basemap.multipoint.page.MultiPointListPage", "com.autonavi.map.common.page.LicenseConfirmPage"})
@KeepName
public final class AMAP_MODULE_OPERATION_PageAction_DATA extends HashMap<String, Class<?>> {
    public AMAP_MODULE_OPERATION_PageAction_DATA() {
        put("amap.basemap.action.verifyuser_page", VerifyUserPage.class);
        put("amap.basemap.action.multpoint_map_page", MultiPointMapPage.class);
        put("amap.basemap.action.weizhang", CarIllegalDlgPage.class);
        put("amap.basemap.action.multpoint_lis_page", MultiPointListPage.class);
        put("amap.basemap.action.licenseconfirm_page", LicenseConfirmPage.class);
    }
}
