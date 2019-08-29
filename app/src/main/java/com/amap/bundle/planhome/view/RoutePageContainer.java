package com.amap.bundle.planhome.view;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabBar;
import com.amap.bundle.planhome.page.AjxPlanHomePage;
import com.amap.bundle.planhome.page.AjxPlanResultPage;
import com.amap.bundle.planhome.third.PlanThirdTaxiAjxPage;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.PageContainer.Transition;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

public class RoutePageContainer extends PageContainer implements axd {
    public static final int PAGE_LEVEL_1 = 1;
    public static final int PAGE_LEVEL_2 = 2;
    private static final int SWITCH_FROM_LEFT_TO_RIGHT = 0;
    private static final int SWITCH_FROM_LEVEL1_TO_LEVEL2 = 2;
    private static final int SWITCH_FROM_RIGHT_TO_LEFT = 1;
    private boolean isFirstEntry = true;
    private int mLastPageLevel = 1;
    private int mPageLevel = 1;
    private IRouteUI mRouteInputUI;
    private RouteType mRouteType;

    public RoutePageContainer(Context context) {
        super(context);
    }

    public RoutePageContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RoutePageContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setRouteInputUI(IRouteUI iRouteUI) {
        this.mRouteInputUI = iRouteUI;
    }

    public IRouteUI getRouteInputUI() {
        return this.mRouteInputUI;
    }

    public void setPageLevel(int i) {
        this.mLastPageLevel = this.mPageLevel;
        this.mPageLevel = i;
    }

    public int getPageLevel() {
        return this.mPageLevel;
    }

    public void switchPage(RouteType routeType) {
        switchPage(routeType, null);
    }

    public void switchPage(RouteType routeType, PageBundle pageBundle) {
        if (this.mPageLevel == 1) {
            AjxPlanHomePage.b = this.mRouteType;
            this.mRouteType = routeType;
            Ajx.sStartTime = System.currentTimeMillis();
            if (RouteType.TAXI == routeType) {
                showPage(getResultPageClass(routeType), pageBundle);
            } else if (RouteType.FREERIDE == routeType) {
                ank.a(dhz.class);
                showPage((Class<? extends AbstractBasePage>) null, pageBundle);
            } else {
                showAjxRoutePage(routeType, pageBundle);
            }
        } else if (this.mPageLevel == 2) {
            Class resultPageClass = getResultPageClass(routeType);
            uploadActionLog(routeType);
            showResultPage(resultPageClass, routeType, pageBundle);
        }
        this.isFirstEntry = false;
    }

    private void showAjxRoutePage(RouteType routeType, PageBundle pageBundle) {
        Class<AjxPlanHomePage> cls = AjxPlanHomePage.class;
        if (pageBundle == null) {
            pageBundle = new PageBundle();
        }
        JSONObject jSONObject = new JSONObject();
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            latestPosition = LocationInstrument.getInstance().getLatestPosition();
        }
        if (latestPosition != null) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                StringBuilder sb = new StringBuilder();
                sb.append(latestPosition.getLatitude());
                jSONObject2.put("lat", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(latestPosition.getLongitude());
                jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb2.toString());
                jSONObject2.put(AutoJsonUtils.JSON_ADCODE, latestPosition.getAdCode());
                jSONObject2.put("name", getResources().getString(R.string.my_location));
                jSONObject.put("my_location", jSONObject2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jSONObject.put("current_type", routeType.getKeyName());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        boolean z = false;
        try {
            lx lxVar = lt.a().c.w;
            jSONObject.put("has_share_bike", (lxVar == null || lxVar.b == null) ? false : lxVar.b.booleanValue());
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        try {
            cuh cuh = (cuh) a.a.a(cuh.class);
            if (cuh != null) {
                z = cuh.c().c();
            }
            jSONObject.put("has_agroup", z);
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        pageBundle.putString("jsData", jSONObject.toString());
        pageBundle.putObject("route_type", routeType);
        pageBundle.putString("url", "path://amap_bundle_basemap_route/src/index.page.js");
        showPage(cls, pageBundle);
    }

    public void showResultPage(Class cls, RouteType routeType, PageBundle pageBundle) {
        this.mLastPageLevel = this.mPageLevel;
        this.mPageLevel = 2;
        if (pageBundle == null) {
            pageBundle = new PageBundle();
        }
        if (RouteType.BUS == routeType) {
            pageBundle.putString("url", "path://amap_bundle_busnavi/src/components/result_page/BizRPBusResult.page.js");
            checkPageBundleSource(pageBundle);
        }
        if (RouteType.ETRIP == routeType) {
            pageBundle.putString("url", "path://amap_bundle_tripgroup/src/etrip/page/ETripPathResult.page.js");
        }
        if (this.isFirstEntry) {
            showPage(cls, pageBundle);
            this.isFirstEntry = false;
            return;
        }
        if (getSwitchType() == 2) {
            showPage(cls, pageBundle, new Transition(R.anim.route_slide_in_from_bottom, R.anim.route_slide_out_to_bottom));
        } else {
            showPage(cls, pageBundle);
        }
        this.mLastPageLevel = this.mPageLevel;
    }

    private void checkPageBundleSource(PageBundle pageBundle) {
        if (pageBundle != null && !pageBundle.containsKey("bundle_key_source")) {
            if (this.mRouteInputUI == null || !this.mRouteInputUI.o()) {
                pageBundle.putString("bundle_key_source", "others");
            } else {
                pageBundle.putString("bundle_key_source", H5SessionTabBar.SWITCH_TAB);
            }
        }
    }

    public void showResultPage(RouteType routeType, PageBundle pageBundle) {
        this.mLastPageLevel = this.mPageLevel;
        this.mPageLevel = 2;
        Class resultPageClass = getResultPageClass(routeType);
        if (pageBundle == null) {
            pageBundle = new PageBundle();
        }
        checkPageBundleSource(pageBundle);
        if (RouteType.BUS == routeType) {
            pageBundle.putString("url", "path://amap_bundle_busnavi/src/components/result_page/BizRPBusResult.page.js");
        }
        if (RouteType.ETRIP == routeType) {
            pageBundle.putString("url", "path://amap_bundle_tripgroup/src/etrip/page/ETripPathResult.page.js");
        }
        if (this.isFirstEntry) {
            showPage(resultPageClass, pageBundle);
            this.isFirstEntry = false;
            return;
        }
        if (getSwitchType() == 2) {
            showPage(resultPageClass, pageBundle, new Transition(R.anim.route_slide_in_from_bottom, R.anim.route_slide_out_to_bottom));
        } else {
            showPage(resultPageClass, pageBundle);
        }
        this.mLastPageLevel = this.mPageLevel;
    }

    private Class getResultPageClass(RouteType routeType) {
        if (RouteType.CAR == routeType) {
            dhz dhz = (dhz) ank.a(dhz.class);
            if (dhz != null) {
                return dhz.a();
            }
            return null;
        } else if (RouteType.TRUCK == routeType) {
            dhz dhz2 = (dhz) ank.a(dhz.class);
            if (dhz2 != null) {
                return dhz2.c();
            }
            return null;
        } else if (RouteType.ETRIP == routeType) {
            dhz dhz3 = (dhz) ank.a(dhz.class);
            if (dhz3 != null) {
                return dhz3.d();
            }
            return null;
        } else if (RouteType.BUS == routeType) {
            return AjxPlanResultPage.class;
        } else {
            if (RouteType.ONFOOT == routeType) {
                avl avl = (avl) a.a.a(avl.class);
                if (avl != null) {
                    return avl.a().a(1);
                }
                return null;
            } else if (RouteType.RIDE == routeType) {
                awy awy = (awy) a.a.a(awy.class);
                if (awy != null) {
                    return awy.a().a(1);
                }
                return null;
            } else if (RouteType.TRAIN == routeType) {
                bdo bdo = (bdo) a.a.a(bdo.class);
                if (bdo != null) {
                    return bdo.c();
                }
                return null;
            } else if (RouteType.COACH == routeType) {
                atw atw = (atw) a.a.a(atw.class);
                if (atw != null) {
                    return atw.b();
                }
                return null;
            } else if (RouteType.TAXI == routeType) {
                return PlanThirdTaxiAjxPage.class;
            } else {
                if (RouteType.FREERIDE == routeType) {
                    dhz dhz4 = (dhz) ank.a(dhz.class);
                    return null;
                } else if (RouteType.AIRTICKET == routeType) {
                    apl apl = (apl) a.a.a(apl.class);
                    if (apl != null) {
                        return apl.a().a(0);
                    }
                    return null;
                } else if (RouteType.MOTOR != routeType) {
                    return null;
                } else {
                    dhz dhz5 = (dhz) ank.a(dhz.class);
                    if (dhz5 != null) {
                        return dhz5.b();
                    }
                    return null;
                }
            }
        }
    }

    private int getSwitchType() {
        return this.mLastPageLevel != this.mPageLevel ? 2 : 0;
    }

    private void uploadActionLog(RouteType routeType) {
        if (routeType == RouteType.BUS) {
            dys.a((String) "P00094", (String) "B013", (JSONObject) null);
        }
    }
}
