package com.amap.bundle.planhome.voice;

import android.text.TextUtils;
import android.util.Pair;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.planhome.view.RoutePageContainer;
import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import com.amap.bundle.voiceservice.dispatch.IVoiceRouteDispatcher;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.voice.model.PoiModel;
import com.autonavi.minimap.route.voice.model.RoutePlanModel;
import com.autonavi.minimap.route.voice.model.RouteRideNaviModel;
import com.taobao.accs.common.Constants;
import com.uc.webview.export.internal.SDKFactory;

public class IVoiceRouteDispatcherImp extends ekn implements IVoiceRouteDispatcher {
    ekl a = a.a;

    @IVoiceDispatchMethod(methodName = "requestRoutePlan")
    public void requestRoutePlan(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            b(i);
            this.a.a().requestRoutePlan(i, (RoutePlanModel) JSON.parseObject(str, RoutePlanModel.class));
            return;
        }
        eko.a(i, (int) SDKFactory.getCoreType);
    }

    @IVoiceDispatchMethod(methodName = "requestRouteRideNavi")
    public void requestRouteRideNavi(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            b(i);
            this.a.a().requestRouteRideNavi(i, (RouteRideNaviModel) JSON.parseObject(str, RouteRideNaviModel.class));
            return;
        }
        eko.a(i, (int) SDKFactory.getCoreType);
    }

    @IVoiceDispatchMethod(methodName = "requestRouteFootNavi")
    public void requestRouteFootNavi(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            b(i);
            this.a.a().requestRouteFootNavi(i, (PoiModel) JSON.parseObject(str, PoiModel.class));
            return;
        }
        eko.a(i, (int) SDKFactory.getCoreType);
    }

    @IVoiceDispatchMethod(methodName = "searchBusLine")
    public void searchBusLine(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            b(i);
            JSONObject parseObject = JSONObject.parseObject(str);
            this.a.a().searchBusLine(i, parseObject.getString("busLineName"), parseObject.getString("cityName"));
            return;
        }
        eko.a(i, (int) SDKFactory.getCoreType);
    }

    @IVoiceDispatchMethod(methodName = "searchSubwayLine")
    public void searchSubwayLine(int i, String str) {
        StringBuilder sb = new StringBuilder("---json:");
        sb.append(str);
        sb.append("-----tokenId:");
        sb.append(i);
        eao.b("searchSubwayLine", sb.toString());
        if (!TextUtils.isEmpty(str)) {
            b(i);
            this.a.a().searchSubwayLine(i, JSONObject.parseObject(str).getString("adCode"));
            return;
        }
        eko.a(i, (int) SDKFactory.getCoreType);
    }

    public final void a(int i) {
        if (a()) {
            eko.a(this.b, i);
        }
    }

    @IVoiceDispatchMethod(methodName = "exitNavi")
    public void exitNavi(int i, String str) {
        b(i);
        this.a.a().exitNavi(i);
    }

    @IVoiceDispatchMethod(methodName = "switchRouteWay")
    public void switchRouteWay(int i, String str) {
        RouteType routeType;
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBasePage) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) pageContext;
            if (abstractBasePage.getPageContainer() instanceof RoutePageContainer) {
                IRouteUI routeInputUI = ((RoutePageContainer) abstractBasePage.getPageContainer()).getRouteInputUI();
                if (routeInputUI != null) {
                    RouteType routeType2 = RouteType.DEFAULT;
                    int i2 = SDKFactory.handlePerformanceTests;
                    int i3 = SDKFactory.getCoreType;
                    try {
                        switch (new org.json.JSONObject(str).optInt(Constants.KEY_MODE, -1)) {
                            case -1:
                                routeType = RouteType.DEFAULT;
                                break;
                            case 0:
                                routeType = RouteType.TAXI;
                                break;
                            case 1:
                                routeType = RouteType.CAR;
                                break;
                            case 2:
                                routeType = RouteType.BUS;
                                break;
                            case 3:
                                routeType = RouteType.RIDE;
                                break;
                            case 4:
                                routeType = RouteType.ONFOOT;
                                break;
                            case 5:
                                routeType = RouteType.TRAIN;
                                break;
                            case 6:
                                routeType = RouteType.COACH;
                                break;
                            case 7:
                                routeType = RouteType.TRUCK;
                                break;
                            default:
                                routeType = RouteType.DEFAULT;
                                i3 = SDKFactory.handlePerformanceTests;
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        routeType = RouteType.DEFAULT;
                    }
                    i3 = 10001;
                    if (routeType != RouteType.DEFAULT) {
                        RouteType i4 = routeInputUI.i();
                        if (i4 != routeType) {
                            RouteType[] h = routeInputUI.h();
                            int length = h.length;
                            boolean z = false;
                            int i5 = 0;
                            while (true) {
                                if (i5 < length) {
                                    if (h[i5] == i4) {
                                        z = true;
                                    } else {
                                        i5++;
                                    }
                                }
                            }
                            if (z) {
                                routeInputUI.a(routeType);
                                i2 = 10000;
                            }
                        } else {
                            i2 = 10031;
                        }
                    } else {
                        i2 = i3;
                    }
                    aia aia = (aia) a.a.a(aia.class);
                    if (aia != null) {
                        aia.a(i, i2, (Pair<String, Object>) null);
                    }
                }
            }
        }
    }

    @IVoiceDispatchMethod(methodName = "swapStartEndPoi")
    public void swapStartEndPoi(int i, String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        boolean z = pageContext instanceof AbstractBasePage;
        int i2 = SDKFactory.getCoreType;
        if (z) {
            AbstractBasePage abstractBasePage = (AbstractBasePage) pageContext;
            if (abstractBasePage.getPageContainer() instanceof RoutePageContainer) {
                IRouteUI routeInputUI = ((RoutePageContainer) abstractBasePage.getPageContainer()).getRouteInputUI();
                if (routeInputUI != null && routeInputUI.j()) {
                    routeInputUI.k();
                    i2 = 10000;
                }
            }
        }
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(i, i2, (Pair<String, Object>) null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    @com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod(methodName = "addMidPois")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addMidPois(int r3, java.lang.String r4) {
        /*
            r2 = this;
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            boolean r1 = r0 instanceof com.autonavi.map.fragmentcontainer.page.AbstractBasePage
            if (r1 == 0) goto L_0x0028
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r0
            com.autonavi.map.fragmentcontainer.page.PageContainer r1 = r0.getPageContainer()
            boolean r1 = r1 instanceof com.amap.bundle.planhome.view.RoutePageContainer
            if (r1 == 0) goto L_0x0028
            com.autonavi.map.fragmentcontainer.page.PageContainer r0 = r0.getPageContainer()
            com.amap.bundle.planhome.view.RoutePageContainer r0 = (com.amap.bundle.planhome.view.RoutePageContainer) r0
            com.autonavi.bundle.routecommon.inter.IRouteUI r0 = r0.getRouteInputUI()
            if (r0 == 0) goto L_0x0028
            com.amap.bundle.planhome.voice.IVoiceRouteDispatcherImp$1 r1 = new com.amap.bundle.planhome.voice.IVoiceRouteDispatcherImp$1
            r1.<init>(r0)
            int r4 = com.autonavi.common.model.RouteHeaderModel.dealVoiceAddMidPois(r4, r1)
            goto L_0x002a
        L_0x0028:
            r4 = 10020(0x2724, float:1.4041E-41)
        L_0x002a:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<aia> r1 = defpackage.aia.class
            esc r0 = r0.a(r1)
            aia r0 = (defpackage.aia) r0
            if (r0 == 0) goto L_0x003c
            r1 = 0
            r0.a(r3, r4, r1)
        L_0x003c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.planhome.voice.IVoiceRouteDispatcherImp.addMidPois(int, java.lang.String):void");
    }

    @IVoiceDispatchMethod(methodName = "switchRouteInWalk")
    public void switchRouteInWalk(int i, String str) {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(i, 9004, (Pair<String, Object>) null);
        }
    }
}
