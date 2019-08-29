package com.autonavi.minimap.basemap.common.inter.impl;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import com.amap.bundle.drive.ajx.module.ModuleHeadunit;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.life.api.api.ISpotGuideManager;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.IPoiDetailDelegate;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.map.mapinterface.IMapRequestManager.d;
import com.autonavi.minimap.bundle.maphome.api.IMapEventListener;
import com.autonavi.sdk.location.LocationInstrument;

public class MapEventListenerImpl implements IMapEventListener {
    public final void a(PageBundle pageBundle, IPoiDetailDelegate iPoiDetailDelegate) {
        if (iPoiDetailDelegate != null) {
            if (iPoiDetailDelegate != null) {
                MapBasePage page = iPoiDetailDelegate.getPage();
                if (page != null) {
                    pageBundle.putInt(IOverlayManager.FROM_SOURCE_PAGE_KEY, page.getTrafficEventSource());
                }
            }
            iPoiDetailDelegate.showPoiFooter(pageBundle, 0, null);
        }
    }

    public final void a(Activity activity) {
        cof cof = (cof) ank.a(cof.class);
        if (cof != null) {
            cof.a(activity);
        }
    }

    public final void a() {
        ku.a().c(ModuleHeadunit.MODULE_NAME, "uploadUserInfoOnce");
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(latestPosition.getLongitude());
        final String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(latestPosition.getLatitude());
        final String sb4 = sb3.toString();
        final AnonymousClass1 r2 = new Callback<d>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(final d dVar) {
                ahn.b().execute(new Runnable() {
                    public final void run() {
                        if (dVar != null) {
                            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                            Editor edit = mapSharePreference.edit();
                            if (dVar.f == 1) {
                                edit.putString("spot_guid_resident_adcode".toString(), dVar.a);
                                edit.putString("spot_guid_cur_adcode".toString(), dVar.b);
                                edit.putString("spot_guid_cur_city".toString(), dVar.c);
                                StringBuilder sb = new StringBuilder("spot_guid_is_tourist_city_");
                                sb.append(dVar.b);
                                edit.putInt(sb.toString(), dVar.f);
                                edit.putLong("sopt_guid_update_time".toString(), System.currentTimeMillis());
                                edit.apply();
                            }
                            ku a2 = ku.a();
                            StringBuilder sb2 = new StringBuilder("uploadUserInfoOnce     result.car_login_flag:");
                            sb2.append(dVar.h);
                            a2.c(ModuleHeadunit.MODULE_NAME, sb2.toString());
                            edit.putInt("car_login_flag", dVar.h);
                            boolean booleanValue = mapSharePreference.getBooleanValue("car_login_update_flag", true);
                            ku.a().c(ModuleHeadunit.MODULE_NAME, "uploadUserInfoOnce     login_update_flag:".concat(String.valueOf(booleanValue)));
                            edit.putBoolean("car_login_update_flag", !booleanValue);
                            edit.commit();
                        }
                    }
                });
            }
        };
        ahm.a(new Runnable() {
            public final void run() {
                avu avu = (avu) a.a.a(avu.class);
                if (avu != null) {
                    ISpotGuideManager b2 = avu.b();
                    IMapRequestManager iMapRequestManager = (IMapRequestManager) ank.a(IMapRequestManager.class);
                    if (iMapRequestManager != null) {
                        ku.a().c(ModuleHeadunit.MODULE_NAME, "uploadUserInfoOnce     mapRequestManager.authDevice");
                        iMapRequestManager.authDevice(sb2, sb4, b2.b(), b2.a(), r2);
                    }
                }
            }
        });
    }
}
