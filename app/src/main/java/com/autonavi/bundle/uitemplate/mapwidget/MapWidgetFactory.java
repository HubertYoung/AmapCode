package com.autonavi.bundle.uitemplate.mapwidget;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;

public class MapWidgetFactory {
    private static String getAMapLogTag() {
        return "MapWidgetFactory";
    }

    public static IMapWidget createInstance(Context context, IWidgetProperty iWidgetProperty) {
        IMapWidget iMapWidget;
        if (context == null || iWidgetProperty == null) {
            return null;
        }
        String widgetType = iWidgetProperty.getWidgetType();
        if (TextUtils.isEmpty(widgetType)) {
            return null;
        }
        if (iWidgetProperty.isLoadNewWidgetStyle()) {
            iMapWidget = createNewStyleWidget(context, widgetType, iWidgetProperty);
        } else {
            iMapWidget = createOldStyleWidget(context, widgetType, iWidgetProperty);
        }
        bez.a(getAMapLogTag(), "create map widget", new bew("name", iWidgetProperty.getWidgetType()));
        return iMapWidget;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget createOldStyleWidget(android.content.Context r1, java.lang.String r2, com.autonavi.bundle.uitemplate.api.IWidgetProperty r3) {
        /*
            int r0 = r2.hashCode()
            switch(r0) {
                case -1655966961: goto L_0x00bc;
                case -1281874730: goto L_0x00b1;
                case -1069988064: goto L_0x00a6;
                case -1067310595: goto L_0x009b;
                case -867048160: goto L_0x0090;
                case -3523736: goto L_0x0086;
                case 99476: goto L_0x007b;
                case 102570: goto L_0x0071;
                case 97526796: goto L_0x0066;
                case 102749521: goto L_0x005c;
                case 109250890: goto L_0x004f;
                case 430270992: goto L_0x0043;
                case 912505898: goto L_0x0037;
                case 950484242: goto L_0x002c;
                case 1344056333: goto L_0x0021;
                case 1615955123: goto L_0x0015;
                case 2117707530: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x00c7
        L_0x0009:
            java.lang.String r0 = "home_corp"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 13
            goto L_0x00c8
        L_0x0015:
            java.lang.String r0 = "pathPreferenceAndScale"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 15
            goto L_0x00c8
        L_0x0021:
            java.lang.String r0 = "msg_box"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 5
            goto L_0x00c8
        L_0x002c:
            java.lang.String r0 = "compass"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 3
            goto L_0x00c8
        L_0x0037:
            java.lang.String r0 = "route_line"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 4
            goto L_0x00c8
        L_0x0043:
            java.lang.String r0 = "indoor_guide"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 12
            goto L_0x00c8
        L_0x004f:
            java.lang.String r0 = "scale"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 10
            goto L_0x00c8
        L_0x005c:
            java.lang.String r0 = "layer"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 6
            goto L_0x00c8
        L_0x0066:
            java.lang.String r0 = "floor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 11
            goto L_0x00c8
        L_0x0071:
            java.lang.String r0 = "gps"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 2
            goto L_0x00c8
        L_0x007b:
            java.lang.String r0 = "diy"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 14
            goto L_0x00c8
        L_0x0086:
            java.lang.String r0 = "nearby_search"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 0
            goto L_0x00c8
        L_0x0090:
            java.lang.String r0 = "pathTipsEnterView"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 16
            goto L_0x00c8
        L_0x009b:
            java.lang.String r0 = "traffic"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 7
            goto L_0x00c8
        L_0x00a6:
            java.lang.String r0 = "zoom_in_out"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 1
            goto L_0x00c8
        L_0x00b1:
            java.lang.String r0 = "auto_remote"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 9
            goto L_0x00c8
        L_0x00bc:
            java.lang.String r0 = "activity"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00c7
            r0 = 8
            goto L_0x00c8
        L_0x00c7:
            r0 = -1
        L_0x00c8:
            switch(r0) {
                case 0: goto L_0x013e;
                case 1: goto L_0x0138;
                case 2: goto L_0x0132;
                case 3: goto L_0x012c;
                case 4: goto L_0x0126;
                case 5: goto L_0x0120;
                case 6: goto L_0x011a;
                case 7: goto L_0x0114;
                case 8: goto L_0x010e;
                case 9: goto L_0x0108;
                case 10: goto L_0x0102;
                case 11: goto L_0x00fc;
                case 12: goto L_0x00f6;
                case 13: goto L_0x00ef;
                case 14: goto L_0x00e8;
                case 15: goto L_0x00e1;
                case 16: goto L_0x00da;
                default: goto L_0x00cb;
            }
        L_0x00cb:
            java.lang.String r0 = "combine"
            boolean r0 = r2.startsWith(r0)
            if (r0 == 0) goto L_0x0144
            com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x00da:
            com.autonavi.bundle.uitemplate.mapwidget.widget.pathtipentence.PathTipEntenceWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.pathtipentence.PathTipEntenceWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x00e1:
            com.autonavi.bundle.uitemplate.mapwidget.widget.recommend.RecommendAndScaleWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.recommend.RecommendAndScaleWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x00e8:
            com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x00ef:
            com.autonavi.bundle.uitemplate.mapwidget.widget.homecorp.HomeCorpMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.homecorp.HomeCorpMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x00f6:
            com.autonavi.bundle.uitemplate.mapwidget.widget.indoorguide.IndoorGuideWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.indoorguide.IndoorGuideWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x00fc:
            com.autonavi.bundle.uitemplate.mapwidget.widget.floor.FloorMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.floor.FloorMapWidget
            r2.<init>(r1, r3)
            return r2
        L_0x0102:
            com.autonavi.bundle.uitemplate.mapwidget.widget.scale.ScaleMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.scale.ScaleMapWidget
            r2.<init>(r1, r3)
            goto L_0x0159
        L_0x0108:
            com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.OldAutoRemoteMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.OldAutoRemoteMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x010e:
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x0114:
            com.autonavi.bundle.uitemplate.mapwidget.widget.traffic.RouteTrafficMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.traffic.RouteTrafficMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x011a:
            com.autonavi.bundle.uitemplate.mapwidget.widget.layer.OldMapLayerMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.layer.OldMapLayerMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x0120:
            com.autonavi.bundle.uitemplate.mapwidget.widget.msg.OldMsgGroupMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.msg.OldMsgGroupMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x0126:
            com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.OldRouteLineMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.OldRouteLineMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x012c:
            com.autonavi.bundle.uitemplate.mapwidget.widget.compass.CompassMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.compass.CompassMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x0132:
            com.autonavi.bundle.uitemplate.mapwidget.widget.gps.OldGPSMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.gps.OldGPSMapWidget
            r2.<init>(r1, r3)
            return r2
        L_0x0138:
            com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.OldZoomInOutMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.OldZoomInOutMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x013e:
            com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchMapWidget
            r2.<init>(r1)
            goto L_0x0159
        L_0x0144:
            java.lang.String r0 = "ajx_"
            boolean r2 = r2.startsWith(r0)
            if (r2 == 0) goto L_0x0158
            if (r3 == 0) goto L_0x0158
            boolean r2 = r3 instanceof com.autonavi.bundle.uitemplate.mapwidget.inter.AJXWidgetProperty
            if (r2 == 0) goto L_0x0158
            com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate.AJXTemplateMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate.AJXTemplateMapWidget
            r2.<init>(r1, r3)
            goto L_0x0159
        L_0x0158:
            r2 = 0
        L_0x0159:
            if (r2 == 0) goto L_0x015e
            r2.setWidgetProperty(r3)
        L_0x015e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.mapwidget.MapWidgetFactory.createOldStyleWidget(android.content.Context, java.lang.String, com.autonavi.bundle.uitemplate.api.IWidgetProperty):com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget createNewStyleWidget(android.content.Context r1, java.lang.String r2, com.autonavi.bundle.uitemplate.api.IWidgetProperty r3) {
        /*
            int r0 = r2.hashCode()
            switch(r0) {
                case -1892194649: goto L_0x00e3;
                case -1655966961: goto L_0x00d8;
                case -1359486782: goto L_0x00ce;
                case -1281874730: goto L_0x00c3;
                case -1069988064: goto L_0x00b8;
                case -1067310595: goto L_0x00ac;
                case -867048160: goto L_0x00a1;
                case -576013447: goto L_0x0095;
                case -3523736: goto L_0x008b;
                case 102570: goto L_0x0081;
                case 97526796: goto L_0x0075;
                case 102749521: goto L_0x0069;
                case 109250890: goto L_0x005c;
                case 339193965: goto L_0x0050;
                case 430270992: goto L_0x0044;
                case 912505898: goto L_0x0038;
                case 950484242: goto L_0x002d;
                case 1344056333: goto L_0x0021;
                case 1403846288: goto L_0x0015;
                case 1615955123: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x00ee
        L_0x0009:
            java.lang.String r0 = "pathPreferenceAndScale"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 18
            goto L_0x00ef
        L_0x0015:
            java.lang.String r0 = "mini_search"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 12
            goto L_0x00ef
        L_0x0021:
            java.lang.String r0 = "msg_box"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 8
            goto L_0x00ef
        L_0x002d:
            java.lang.String r0 = "compass"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 6
            goto L_0x00ef
        L_0x0038:
            java.lang.String r0 = "route_line"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 7
            goto L_0x00ef
        L_0x0044:
            java.lang.String r0 = "indoor_guide"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 16
            goto L_0x00ef
        L_0x0050:
            java.lang.String r0 = "user_icon"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 2
            goto L_0x00ef
        L_0x005c:
            java.lang.String r0 = "scale"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 14
            goto L_0x00ef
        L_0x0069:
            java.lang.String r0 = "layer"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 9
            goto L_0x00ef
        L_0x0075:
            java.lang.String r0 = "floor"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 15
            goto L_0x00ef
        L_0x0081:
            java.lang.String r0 = "gps"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 4
            goto L_0x00ef
        L_0x008b:
            java.lang.String r0 = "nearby_search"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 0
            goto L_0x00ef
        L_0x0095:
            java.lang.String r0 = "scenic_area"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 17
            goto L_0x00ef
        L_0x00a1:
            java.lang.String r0 = "pathTipsEnterView"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 19
            goto L_0x00ef
        L_0x00ac:
            java.lang.String r0 = "traffic"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 10
            goto L_0x00ef
        L_0x00b8:
            java.lang.String r0 = "zoom_in_out"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 3
            goto L_0x00ef
        L_0x00c3:
            java.lang.String r0 = "auto_remote"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 13
            goto L_0x00ef
        L_0x00ce:
            java.lang.String r0 = "mini_gps"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 5
            goto L_0x00ef
        L_0x00d8:
            java.lang.String r0 = "activity"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 11
            goto L_0x00ef
        L_0x00e3:
            java.lang.String r0 = "weather_restrict"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x00ee
            r0 = 1
            goto L_0x00ef
        L_0x00ee:
            r0 = -1
        L_0x00ef:
            switch(r0) {
                case 0: goto L_0x0179;
                case 1: goto L_0x0173;
                case 2: goto L_0x016d;
                case 3: goto L_0x0167;
                case 4: goto L_0x0161;
                case 5: goto L_0x015b;
                case 6: goto L_0x0155;
                case 7: goto L_0x014f;
                case 8: goto L_0x0149;
                case 9: goto L_0x0143;
                case 10: goto L_0x013d;
                case 11: goto L_0x0137;
                case 12: goto L_0x0131;
                case 13: goto L_0x012a;
                case 14: goto L_0x0123;
                case 15: goto L_0x011d;
                case 16: goto L_0x0116;
                case 17: goto L_0x010f;
                case 18: goto L_0x0108;
                case 19: goto L_0x0101;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            java.lang.String r0 = "combine"
            boolean r0 = r2.startsWith(r0)
            if (r0 == 0) goto L_0x017f
            com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0101:
            com.autonavi.bundle.uitemplate.mapwidget.widget.pathtipentence.PathTipEntenceWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.pathtipentence.PathTipEntenceWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0108:
            com.autonavi.bundle.uitemplate.mapwidget.widget.recommend.RecommendAndScaleWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.recommend.RecommendAndScaleWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x010f:
            com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea.ScenicAreaWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea.ScenicAreaWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0116:
            com.autonavi.bundle.uitemplate.mapwidget.widget.indoorguide.IndoorGuideWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.indoorguide.IndoorGuideWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x011d:
            com.autonavi.bundle.uitemplate.mapwidget.widget.floor.FloorMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.floor.FloorMapWidget
            r2.<init>(r1, r3)
            return r2
        L_0x0123:
            com.autonavi.bundle.uitemplate.mapwidget.widget.scale.ScaleMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.scale.ScaleMapWidget
            r2.<init>(r1, r3)
            goto L_0x0194
        L_0x012a:
            com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.AutoRemoteMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.AutoRemoteMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0131:
            com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFrameWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFrameWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0137:
            com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x013d:
            com.autonavi.bundle.uitemplate.mapwidget.widget.traffic.RouteTrafficMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.traffic.RouteTrafficMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0143:
            com.autonavi.bundle.uitemplate.mapwidget.widget.layer.MapLayerMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.layer.MapLayerMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0149:
            com.autonavi.bundle.uitemplate.mapwidget.widget.msg.MsgGroupMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.msg.MsgGroupMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x014f:
            com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.RouteLineMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.routeline.RouteLineMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0155:
            com.autonavi.bundle.uitemplate.mapwidget.widget.compass.CompassMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.compass.CompassMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x015b:
            com.autonavi.bundle.uitemplate.mapwidget.widget.gps.MiniGpsMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.gps.MiniGpsMapWidget
            r2.<init>(r1, r3)
            return r2
        L_0x0161:
            com.autonavi.bundle.uitemplate.mapwidget.widget.gps.GPSMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.gps.GPSMapWidget
            r2.<init>(r1, r3)
            return r2
        L_0x0167:
            com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.ZoomInOutMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.ZoomInOutMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x016d:
            com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.user.UserCenterMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x0173:
            com.autonavi.bundle.uitemplate.mapwidget.widget.weather.WeatherRestrictMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.weather.WeatherRestrictMapWidget
            r2.<init>(r1, r3)
            goto L_0x0194
        L_0x0179:
            com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchMapWidget
            r2.<init>(r1)
            goto L_0x0194
        L_0x017f:
            java.lang.String r0 = "ajx_"
            boolean r2 = r2.startsWith(r0)
            if (r2 == 0) goto L_0x0193
            if (r3 == 0) goto L_0x0193
            boolean r2 = r3 instanceof com.autonavi.bundle.uitemplate.mapwidget.inter.AJXWidgetProperty
            if (r2 == 0) goto L_0x0193
            com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate.AJXTemplateMapWidget r2 = new com.autonavi.bundle.uitemplate.mapwidget.widget.ajxtemplate.AJXTemplateMapWidget
            r2.<init>(r1, r3)
            goto L_0x0194
        L_0x0193:
            r2 = 0
        L_0x0194:
            if (r2 == 0) goto L_0x0199
            r2.setWidgetProperty(r3)
        L_0x0199:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.mapwidget.MapWidgetFactory.createNewStyleWidget(android.content.Context, java.lang.String, com.autonavi.bundle.uitemplate.api.IWidgetProperty):com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget");
    }
}
