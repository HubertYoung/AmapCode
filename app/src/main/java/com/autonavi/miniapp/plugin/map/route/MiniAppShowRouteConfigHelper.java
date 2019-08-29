package com.autonavi.miniapp.plugin.map.route;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.wireless.security.SecExceptionCode;
import com.autonavi.amap.app.AMapAppGlobal;

public final class MiniAppShowRouteConfigHelper {
    public static final String KEY_BORDER_COLOR = "borderColor";
    public static final String KEY_BORDER_LINE_WIDTH = "borderLineWidth";
    public static final String KEY_FILL_COLOR = "fillColor";
    public static final String KEY_LINE_WIDTH = "lineWidth";
    public static final String KEY_TEXTURE_LEN = "textureLen";
    public static final String KEY_TEXTURE_LEN_3D = "textureLen3D";
    public static final String KEY_TYPE = "type";
    public static final int LINE_TYPE_AMBLE = 111;
    public static final int LINE_TYPE_AMBLE_HIGH_LIGHT = 112;
    public static final int LINE_TYPE_BUSRIDE_LINE = 12;
    public static final int LINE_TYPE_CAR_END_LINE = 123;
    public static final int LINE_TYPE_CAR_POLYGON_LINE = 5;
    public static final int LINE_TYPE_CONGESTED = 115;
    public static final int LINE_TYPE_CONGESTED_HIGH_LIGHT = 116;
    public static final int LINE_TYPE_CONGESTION = 130;
    public static final int LINE_TYPE_DRIVE_END_LINE = 140;
    public static final int LINE_TYPE_EAGLE_EYE_LINE = 7;
    public static final int LINE_TYPE_FERRY = 103;
    public static final int LINE_TYPE_FERRY_HIGH_LIGHT = 104;
    public static final int LINE_TYPE_INNER_NAVI = 101;
    public static final int LINE_TYPE_INNER_NAVI_HIGH_LIGHT = 102;
    public static final int LINE_TYPE_INNER_NOT_NAVI = 105;
    public static final int LINE_TYPE_INNER_NOT_NAVI_HIGH_LIGHT = 106;
    public static final int LINE_TYPE_JAM = 113;
    public static final int LINE_TYPE_JAM_HIGH_LIGHT = 114;
    public static final int LINE_TYPE_OFF_LINE = 107;
    public static final int LINE_TYPE_OFF_LINE_HIGH_LIGHT = 108;
    public static final int LINE_TYPE_OFF_ROUTE = 8;
    public static final int LINE_TYPE_OPEN = 109;
    public static final int LINE_TYPE_OPEN_HIGH_LIGHT = 110;
    public static final int LINE_TYPE_RESTRICT = 117;
    public static final int LINE_TYPE_RESTRICT_AREA = 121;
    public static final int LINE_TYPE_RESTRICT_AREA_NO_EFFECT = 122;
    public static final int LINE_TYPE_RESTRICT_HIGH_LIGHT = 118;
    public static final int LINE_TYPE_ROUTE_LINE = 1;
    public static final int LINE_TYPE_ROUTE_LINE_ARROW = 3;
    public static final int LINE_TYPE_ROUTE_LINE_ARROW_HIGH_LIGHT = 4;
    public static final int LINE_TYPE_ROUTE_LINE_HIGH_LIGHT = 2;
    public static final int LINE_TYPE_SEGMENT_LINE_HIGHLIGH = 9;
    public static final int LINE_TYPE_VIA_ROAD_LINE_CHARGE = 119;
    public static final int LINE_TYPE_VIA_ROAD_LINE_FREE_ = 120;
    public static final int LINE_TYPE_WALK_DOT_LINE = 6;

    public enum AmapEngineBusErrors {
        AMAP_ENGINE_RESPONSE_ERROR(1100),
        AMAP_ENGINE_CONNECT_TIMEOUT(SecExceptionCode.SEC_ERROE_OPENSDK_DECODE_FAILED),
        AMAP_ENGINE_RETURN_TIMEOUT(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH),
        AMAP_SERVICE_INVALID_PARAMS(1200),
        AMAP_SERVICE_ILLEGAL_REQUEST(1202),
        AMAP_ROUTE_NO_ROADS_NEARBY(3001),
        AMAP_OVER_DIRECTION_RANGE(amb.GL_MARKER_LINE_ARROW_DOT),
        AMAP_CLIENT_UNKNOWN_ERROR(SecExceptionCode.SEC_ERROR_AVMP);
        
        private int value;

        private AmapEngineBusErrors(int i) {
            this.value = SecExceptionCode.SEC_ERROR_AVMP;
            this.value = i;
        }

        public static AmapEngineBusErrors valueOf(int i) {
            if (i != 201) {
                switch (i) {
                    case 1:
                    case 2:
                        break;
                    case 3:
                    case 4:
                    case 5:
                        return AMAP_OVER_DIRECTION_RANGE;
                    default:
                        switch (i) {
                            case 7:
                            case 8:
                                break;
                            case 9:
                                return AMAP_CLIENT_UNKNOWN_ERROR;
                            default:
                                return AMAP_CLIENT_UNKNOWN_ERROR;
                        }
                }
            }
            return AMAP_ROUTE_NO_ROADS_NEARBY;
        }

        public final int value() {
            return this.value;
        }
    }

    public enum AmapEngineErrors {
        AMAP_ENGINE_RESPONSE_ERROR(1100),
        AMAP_ENGINE_CONNECT_TIMEOUT(SecExceptionCode.SEC_ERROE_OPENSDK_DECODE_FAILED),
        AMAP_ENGINE_RETURN_TIMEOUT(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH),
        AMAP_SERVICE_INVALID_PARAMS(1200),
        AMAP_SERVICE_ILLEGAL_REQUEST(1202),
        AMAP_ROUTE_NO_ROADS_NEARBY(3001),
        AMAP_OVER_DIRECTION_RANGE(amb.GL_MARKER_LINE_ARROW_DOT),
        AMAP_CLIENT_UNKNOWN_ERROR(SecExceptionCode.SEC_ERROR_AVMP);
        
        private int value;

        private AmapEngineErrors(int i) {
            this.value = SecExceptionCode.SEC_ERROR_AVMP;
            this.value = i;
        }

        public static AmapEngineErrors valueOf(int i) {
            switch (i) {
                case 0:
                case 2:
                case 15:
                case 17:
                    return AMAP_ENGINE_RESPONSE_ERROR;
                case 3:
                case 6:
                case 7:
                case 8:
                case 9:
                case 21:
                case 23:
                case 28:
                    return AMAP_SERVICE_INVALID_PARAMS;
                case 4:
                case 5:
                    return AMAP_SERVICE_ILLEGAL_REQUEST;
                case 10:
                case 11:
                case 12:
                    return AMAP_ROUTE_NO_ROADS_NEARBY;
                case 13:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                    return AMAP_CLIENT_UNKNOWN_ERROR;
                case 16:
                    return AMAP_ENGINE_CONNECT_TIMEOUT;
                case 19:
                    return AMAP_OVER_DIRECTION_RANGE;
                case 22:
                    return AMAP_ENGINE_RETURN_TIMEOUT;
                default:
                    return AMAP_CLIENT_UNKNOWN_ERROR;
            }
        }

        public final int value() {
            return this.value;
        }
    }

    static class BusConfig {
        private static final float BUS_LINE_WIDTH = MiniAppShowRouteConfigHelper.linePx(14.0f);
        public static final String CARD_JSON = "[\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0.5,\n        \"resID\": 12345,\n        \"xml\": \"<div style='width:100;height:100;background-color:black;display:flex;border-radius:200'></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 1,\n        \"resID\": 200003,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#FFFFFF;display:flex;border-radius:40px;flex-direction:row;margin-bottom:22;'><div style='width:auto;height:80px;display:flex;flex-direction:row;align-items:baseline'><label style='margin-top:28px;margin-left:20px;color:#CCCCCC;font-size:28px' text=\\\"前往\\\"></label><label style='color:#333333;font-size:32px;margin-left:8px' text=\\\"%s\\\"></label></div><div style='width:1px;height:60px;background-color:#CCCCCC;margin-top:10px;margin-top:10px;margin-left:10px;margin-right:10px'></div><div style='width:auto;height:auto;background-color:#FFFFFF;display:flex;border-radius:40px;padding-top:0px;padding-left:0px;padding-bottom:0px;padding-right:0px;flex-direction:column;margin-bottom:2;align-items:center'><image src='%s' style='margin-top:3px;margin-right:20px'></image><label style='margin-top:4px;margin-bottom:4px;margin-left:2px;margin-right:20px;color:#0000FF;font-size:28px' text=\\\"导航\\\"></label></div></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 1,\n        \"resID\": 200004,\n        \"xml\": \"<div style='width:auto;height:auto;background-image:%s;display:flex;margin-right:8;margin-bottom:%s;padding-top:12px;padding-left:12px;padding-bottom:20px;padding-right:12px;flex-direction:column;align-items:center'><div style='width:auto;height:auto;flex-direction:column;align-items:center'><label style='margin-bottom:8px;color:#212121;font-size:28px' text='%s'></label></div><div style='width:auto;height:auto;flex-direction:column;align-items:center'><label style='width:auto;color:#212121;font-size:22px' text='%s'></label></div></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 1.0,\n        \"resID\": 200005,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;border-radius:8px;padding-top:12px;padding-left:12px;padding-bottom:12px;padding-right:12px;flex-direction:column;margin-top:6;margin-bottom:6;margin-left:6;margin-right:6;align-items:center'><label style='margin-bottom:0px;color:black;font-size:24px;text-stroke:4px white' text='%s'></label></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0.0,\n        \"resID\": 200006,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00CCCCCC;display:flex;padding-top:26px;padding-left:16px;padding-bottom:16px;padding-right:16px;flex-direction:column;margin-top:2;margin-bottom:0;margin-left:0;margin-right:0;align-items:%s'><label style='margin-bottom:0px;margin-left:12px;margin-right:12px;color:black;font-size:24px;text-stroke:4px white' text='%s'></label><div style='width:auto;height:auto;background-color:#00AAAAAA;display:flex;border-radius:88px;padding-top:6px;padding-left:12px;padding-bottom:10px;padding-right:12px;flex-direction:row;margin-bottom:0;align-items:center'><div style='width:auto;height:34;background-color:%s;display:flex;border-radius:88px;padding-left:10px;padding-right:10px;flex-direction:row;margin-bottom:2;align-items:center'><label style='margin-bottom:0px;color:#ffffff;font-size:20px' text='%s'></label></div><image src='%s' style='margin-bottom:6px;margin-left:4px;margin-right:4px'></image><div style='width:auto;height:34;background-color:%s;display:flex;border-radius:88px;padding-left:10px;padding-right:10px;flex-direction:row;margin-bottom:2;align-items:center'><label style='margin-bottom:0px;color:#ffffff;font-size:20px' text='%s'></label></div></div></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0.0,\n        \"resID\": 200007,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;padding-top:12px;padding-left:12px;padding-bottom:12px;padding-right:12px;flex-direction:column;margin-top:16;margin-bottom:16;margin-left:16;margin-right:16;align-items:%s'><label style='margin-bottom:8px;color:black;font-size:24px;text-stroke:4px white' text='%s'></label><div style='width:auto;height:34;background-color:%s;display:flex;border-radius:88px;padding-left:10px;padding-right:10px;flex-direction:row;margin-bottom:2;align-items:center'><label style='margin-bottom:0px;color:#ffffff;font-size:20px' text='%s'></label></div></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0.0,\n        \"resID\": 200008,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;padding-top:12px;padding-left:12px;padding-bottom:12px;padding-right:12px;flex-direction:column;margin-top:16;margin-bottom:16;margin-left:16;margin-right:16;align-items:center'><label style='margin-bottom:0px;color:black;font-size:24px;text-stroke:4px white' text='%s'></label></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0.0,\n        \"resID\": 200009,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00777777;display:flex;padding-top:12px;padding-left:12px;padding-bottom:12px;padding-right:12px;flex-direction:column;margin-top:16;margin-bottom:16;margin-left:6;margin-right:6;align-items:%s'><label style='margin-bottom:0px;margin-left:12px;margin-right:12px;color:black;font-size:24px;text-stroke:4px white' text='%s'></label><div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;border-radius:88px;padding-top:6px;padding-left:8px;padding-bottom:0px;padding-right:8px;flex-direction:row;margin-bottom:0;align-items:center'><image src='%s' style='margin-bottom:4px;margin-left:4px;margin-right:4px'></image><image src='%s' style='margin-bottom:8px;margin-left:4px;margin-right:4px'></image><div style='width:auto;height:34;background-color:%s;display:flex;border-radius:88px;padding-left:8px;padding-right:8px;flex-direction:row;margin-bottom:2;align-items:center'><label style='margin-bottom:0px;color:#ffffff;font-size:20px' text='%s'></label></div></div></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0.0,\n        \"resID\": 200010,\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00777777;display:flex;padding-top:12px;padding-left:12px;padding-bottom:12px;padding-right:12px;flex-direction:column;margin-top:16;margin-bottom:16;margin-left:6;margin-right:6;align-items:%s'><label style='margin-bottom:0px;margin-left:12px;margin-right:12px;color:black;font-size:24px;text-stroke:4px white' text='%s'></label><div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;border-radius:88px;padding-top:6px;padding-left:8px;padding-bottom:0px;padding-right:8px;flex-direction:row;margin-bottom:0;align-items:center'><div style='width:auto;height:34;background-color:%s;display:flex;border-radius:88px;padding-left:8px;padding-right:8px;flex-direction:row;margin-bottom:2;align-items:center'><label style='margin-bottom:0px;color:#ffffff;font-size:20px' text='%s'></label></div><image src='%s' style='margin-bottom:8px;margin-left:4px;margin-right:4px'></image><image src='%s' style='margin-bottom:4px;margin-left:0px;margin-right:4px'></image></div></div></div>\"\n    },\n    {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 1,\n        \"resID\": 200011,\n        \"clickAreas\":[{\"viewID\":\"ClickAreaIdBusGuideTipTopClicked\",\"areaID\":30401}],\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;background-image:%s;flex-direction:row;margin-bottom:%s;padding-top:8px;padding-left:30px;padding-bottom:16px;padding-right:34px;'><div id='ClickAreaIdBusGuideTipTopClicked' style='width:auto;height:auto;background-color:#00CCCCCC;display:flex;flex-direction:row'><div style='width:auto;height:auto;display:flex;flex-direction:row;align-items:center;padding-bottom:6px'><label style='margin-left:2px;color:#9E9E9E;font-size:24px' text=\\\"前往\\\"></label><label style='color:#212121;font-size:28px;margin-left:6px' text=\\\"%s\\\"></label></div><div style='width:1px;height:auto;background-color:#CCCCCC;display:flex;margin-top:8px;margin-bottom:16px;margin-left:14px;margin-right:10px'></div><div style='width:auto;height:auto;display:flex;padding-top:2px;padding-left:2px;padding-bottom:2px;padding-right:2px;flex-direction:column;margin-bottom:2px;align-items:center'><image src='%s' style='margin-top:2px;margin-right:2px'></image><label style='margin-top:2px;margin-bottom:6px;margin-left:2px;margin-right:2px;color:#2267FF;font-size:20px' text=\\\"导航\\\"></label></div></div></div>\"\n    },\n        {\n        \"anchorRatioX\": 0.5,\n        \"anchorRatioY\": 0,\n        \"resID\": 200012,\n        \"clickAreas\":[{\"viewID\":\"ClickAreaIdBusGuideTipBottomClicked\",\"areaID\":30402}],\n        \"xml\": \"<div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;background-image:%s;flex-direction:row;margin-top:%spx;margin-bottom:20px;padding-top:18px;padding-left:30px;padding-bottom:10px;padding-right:34px;'><div id='ClickAreaIdBusGuideTipBottomClicked' style='width:auto;height:auto;background-color:#00CCCCCC;display:flex;flex-direction:row'><div style='width:auto;height:auto;display:flex;flex-direction:row;align-items:center;padding-bottom:6px'><label style='margin-left:2px;color:#9E9E9E;font-size:24px' text=\\\"前往\\\"></label><label style='color:#212121;font-size:28px;margin-left:6px' text=\\\"%s\\\"></label></div><div style='width:1px;height:auto;background-color:#CCCCCC;margin-top:8px;margin-bottom:16px;margin-left:14px;margin-right:10px'></div><div style='width:auto;height:auto;background-color:#00FFFFFF;display:flex;padding-top:0px;padding-left:0px;padding-bottom:0px;padding-right:0px;flex-direction:column;margin-bottom:2;align-items:center'><image src='%s' style='margin-top:3px;margin-right:2px'></image><label style='margin-top:2px;margin-bottom:6px;margin-left:2px;margin-right:2px;color:#2267FF;font-size:20px' text=\\\"导航\\\"></label></div></div></div>\"\n    }\n]";
        private static final float UNSELECTED_LINE_WIDTH = MiniAppShowRouteConfigHelper.linePx(14.0f);

        enum BlueDashStyle {
            MAIN_BORDER(-16739841),
            MAIN_FILL(-16739841),
            BACKUP_BORDER(-6834194),
            BACKUP_FILL(-6834194),
            MAIN_BORDERNIGHT(-16739841),
            MAIN_FILLNIGHT(-16739841),
            BACKUP_BORDERNIGHT(-15512457),
            BACKUP_FILLNIGHT(-15512457);
            
            long colorValue;

            private BlueDashStyle(long j) {
                this.colorValue = 0;
                this.colorValue = j;
            }

            public final long value() {
                return this.colorValue;
            }
        }

        enum BlueStyle {
            MAIN_BORDER(-16553003),
            MAIN_FILL(-16739841),
            BACKUP_BORDER(-8276762),
            BACKUP_FILL(-6239252),
            MAIN_BORDERNIGHT(-16553003),
            MAIN_FILLNIGHT(-16739841),
            BACKUP_BORDERNIGHT(-14917999),
            BACKUP_FILLNIGHT(-15512457);
            
            long colorValue;

            private BlueStyle(long j) {
                this.colorValue = 0;
                this.colorValue = j;
            }

            public final long value() {
                return this.colorValue;
            }
        }

        enum GrayRedStyle {
            MAIN_BORDER(-8648437),
            MAIN_FILL(-5764853),
            BACKUP_BORDER(-4553068),
            BACKUP_FILL(-3172444),
            MAIN_BORDERNIGHT(-7665397),
            MAIN_FILLNIGHT(-5764853),
            BACKUP_BORDERNIGHT(-8173501),
            BACKUP_FILLNIGHT(-10078666);
            
            long colorValue;

            private GrayRedStyle(long j) {
                this.colorValue = 0;
                this.colorValue = j;
            }

            public final long value() {
                return this.colorValue;
            }
        }

        enum GreenStyle {
            MAIN_BORDER(-16749038),
            MAIN_FILL(-16729569),
            BACKUP_BORDER(-7289704),
            BACKUP_FILL(-4922425),
            MAIN_BORDERNIGHT(-16749038),
            MAIN_FILLNIGHT(-16729569),
            BACKUP_BORDERNIGHT(-11694996),
            BACKUP_FILLNIGHT(-12159132);
            
            long colorValue;

            private GreenStyle(long j) {
                this.colorValue = 0;
                this.colorValue = j;
            }

            public final long value() {
                return this.colorValue;
            }
        }

        enum OrangeStyle {
            MAIN_BORDER(-3047422),
            MAIN_FILL(-17920),
            BACKUP_BORDER(-3556721),
            BACKUP_FILL(-267849),
            MAIN_BORDERNIGHT(-3047422),
            MAIN_FILLNIGHT(-17920),
            BACKUP_BORDERNIGHT(-4876471),
            BACKUP_FILLNIGHT(-6322620);
            
            long colorValue;

            private OrangeStyle(long j) {
                this.colorValue = 0;
                this.colorValue = j;
            }

            public final long value() {
                return this.colorValue;
            }
        }

        enum RedStyle {
            MAIN_BORDER(-5566703),
            MAIN_FILL(-844512),
            BACKUP_BORDER(-2714732),
            BACKUP_FILL(-609098),
            MAIN_BORDERNIGHT(-5566703),
            MAIN_FILLNIGHT(-844512),
            BACKUP_BORDERNIGHT(-6532533),
            BACKUP_FILLNIGHT(-8173752);
            
            long colorValue;

            private RedStyle(long j) {
                this.colorValue = 0;
                this.colorValue = j;
            }

            public final long value() {
                return this.colorValue;
            }
        }

        private BusConfig() {
        }

        /* access modifiers changed from: private */
        public static String getBusConfig() {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "type", (Object) Integer.valueOf(4));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Float.valueOf(BUS_LINE_WIDTH * 2.0f));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Float.valueOf(BUS_LINE_WIDTH * 2.0f));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "type", (Object) Integer.valueOf(3));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(16.0f)));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(16.0f)));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put((String) "type", (Object) Integer.valueOf(2));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put((String) "type", (Object) Integer.valueOf(1));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16553003));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(18.0f)));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(18.0f)));
            jSONArray.add(jSONObject4);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put((String) "type", (Object) Integer.valueOf(6));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-13321480));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Double.valueOf(((double) BUS_LINE_WIDTH) * 1.5d));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Double.valueOf(((double) BUS_LINE_WIDTH) * 1.5d));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(12.0f)));
            jSONArray.add(jSONObject5);
            JSONObject jSONObject6 = new JSONObject();
            jSONObject6.put((String) "type", (Object) Integer.valueOf(12));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-15089937));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16745060));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Double.valueOf(((double) BUS_LINE_WIDTH) * 1.5d));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Double.valueOf(((double) BUS_LINE_WIDTH) * 1.5d));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH + MiniAppShowRouteConfigHelper.linePx(4.0f)));
            jSONArray.add(jSONObject6);
            JSONObject jSONObject7 = new JSONObject();
            jSONObject7.put((String) "type", (Object) Integer.valueOf(110));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(GreenStyle.MAIN_FILL.value()));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(GreenStyle.MAIN_BORDER.value()));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject7);
            JSONObject jSONObject8 = new JSONObject();
            jSONObject8.put((String) "type", (Object) Integer.valueOf(109));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(GreenStyle.BACKUP_FILL.value()));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(GreenStyle.BACKUP_BORDER.value()));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONArray.add(jSONObject8);
            JSONObject jSONObject9 = new JSONObject();
            jSONObject9.put((String) "type", (Object) Integer.valueOf(112));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(OrangeStyle.MAIN_FILL.value()));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(OrangeStyle.MAIN_BORDER.value()));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject9);
            JSONObject jSONObject10 = new JSONObject();
            jSONObject10.put((String) "type", (Object) Integer.valueOf(111));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(OrangeStyle.BACKUP_FILL.value()));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(OrangeStyle.BACKUP_BORDER.value()));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONArray.add(jSONObject10);
            JSONObject jSONObject11 = new JSONObject();
            jSONObject11.put((String) "type", (Object) Integer.valueOf(102));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(BlueStyle.MAIN_FILL.value()));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(BlueStyle.MAIN_BORDER.value()));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject11);
            JSONObject jSONObject12 = new JSONObject();
            jSONObject12.put((String) "type", (Object) Integer.valueOf(101));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(BlueStyle.BACKUP_FILL.value()));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(BlueStyle.BACKUP_BORDER.value()));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONArray.add(jSONObject12);
            JSONObject jSONObject13 = new JSONObject();
            jSONObject13.put((String) "type", (Object) Integer.valueOf(108));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(BlueStyle.MAIN_FILL.value()));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(BlueStyle.MAIN_BORDER.value()));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject13);
            JSONObject jSONObject14 = new JSONObject();
            jSONObject14.put((String) "type", (Object) Integer.valueOf(107));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(BlueStyle.BACKUP_FILL.value()));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(BlueStyle.BACKUP_BORDER.value()));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject14);
            JSONObject jSONObject15 = new JSONObject();
            jSONObject15.put((String) "type", (Object) Integer.valueOf(114));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(RedStyle.MAIN_FILL.value()));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(RedStyle.MAIN_BORDER.value()));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject15);
            JSONObject jSONObject16 = new JSONObject();
            jSONObject16.put((String) "type", (Object) Integer.valueOf(113));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(RedStyle.BACKUP_FILL.value()));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(RedStyle.BACKUP_BORDER.value()));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONArray.add(jSONObject16);
            JSONObject jSONObject17 = new JSONObject();
            jSONObject17.put((String) "type", (Object) Integer.valueOf(116));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(GrayRedStyle.MAIN_FILL.value()));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(GrayRedStyle.MAIN_BORDER.value()));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(BUS_LINE_WIDTH));
            jSONArray.add(jSONObject17);
            JSONObject jSONObject18 = new JSONObject();
            jSONObject18.put((String) "type", (Object) Integer.valueOf(115));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Long.valueOf(GrayRedStyle.BACKUP_FILL.value()));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Long.valueOf(GrayRedStyle.BACKUP_BORDER.value()));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(UNSELECTED_LINE_WIDTH));
            jSONArray.add(jSONObject18);
            return jSONArray.toJSONString();
        }
    }

    static class DriveConfig {
        private DriveConfig() {
        }

        /* access modifiers changed from: private */
        public static String getDriveConfig() {
            float access$300 = MiniAppShowRouteConfigHelper.linePx(20.0f);
            float access$3002 = MiniAppShowRouteConfigHelper.linePx(16.0f);
            float access$3003 = MiniAppShowRouteConfigHelper.linePx(20.0f);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "type", (Object) Integer.valueOf(4));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            float f = access$300 * 2.0f;
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Float.valueOf(f));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Float.valueOf(f));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "type", (Object) Integer.valueOf(3));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Float.valueOf(f));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Float.valueOf(f));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put((String) "type", (Object) Integer.valueOf(102));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16553003));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put((String) "type", (Object) Integer.valueOf(101));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-6239252));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-8276762));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject4);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put((String) "type", (Object) Integer.valueOf(104));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject5);
            JSONObject jSONObject6 = new JSONObject();
            jSONObject6.put((String) "type", (Object) Integer.valueOf(103));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-6834194));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-6834194));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject6);
            JSONObject jSONObject7 = new JSONObject();
            jSONObject7.put((String) "type", (Object) Integer.valueOf(106));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject7);
            JSONObject jSONObject8 = new JSONObject();
            jSONObject8.put((String) "type", (Object) Integer.valueOf(105));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-6834194));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-6834194));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject8);
            JSONObject jSONObject9 = new JSONObject();
            jSONObject9.put((String) "type", (Object) Integer.valueOf(108));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16553003));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject9);
            JSONObject jSONObject10 = new JSONObject();
            jSONObject10.put((String) "type", (Object) Integer.valueOf(107));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-6239252));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-8276762));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject10);
            JSONObject jSONObject11 = new JSONObject();
            jSONObject11.put((String) "type", (Object) Integer.valueOf(110));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16729569));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16749038));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject11);
            JSONObject jSONObject12 = new JSONObject();
            jSONObject12.put((String) "type", (Object) Integer.valueOf(109));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-6564923));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-8537177));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject12.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject12);
            JSONObject jSONObject13 = new JSONObject();
            jSONObject13.put((String) "type", (Object) Integer.valueOf(112));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-17920));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-3047422));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject13.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject13);
            JSONObject jSONObject14 = new JSONObject();
            jSONObject14.put((String) "type", (Object) Integer.valueOf(111));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-272715));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-2378091));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject14.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject14);
            JSONObject jSONObject15 = new JSONObject();
            jSONObject15.put((String) "type", (Object) Integer.valueOf(114));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-844512));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-5566703));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject15.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject15);
            JSONObject jSONObject16 = new JSONObject();
            jSONObject16.put((String) "type", (Object) Integer.valueOf(113));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1985857));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-3170388));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject16.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject16);
            JSONObject jSONObject17 = new JSONObject();
            jSONObject17.put((String) "type", (Object) Integer.valueOf(116));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-5764853));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-7665397));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject17.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject17);
            JSONObject jSONObject18 = new JSONObject();
            jSONObject18.put((String) "type", (Object) Integer.valueOf(115));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-3172444));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-4487018));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject18.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject18);
            JSONObject jSONObject19 = new JSONObject();
            jSONObject19.put((String) "type", (Object) Integer.valueOf(117));
            jSONObject19.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-266567));
            jSONObject19.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject19.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONObject19.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$3002));
            jSONArray.add(jSONObject19);
            JSONObject jSONObject20 = new JSONObject();
            jSONObject20.put((String) "type", (Object) Integer.valueOf(118));
            jSONObject20.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-266567));
            jSONObject20.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject20.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONObject20.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(access$300));
            jSONArray.add(jSONObject20);
            JSONObject jSONObject21 = new JSONObject();
            jSONObject21.put((String) "type", (Object) Integer.valueOf(119));
            jSONObject21.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1));
            jSONObject21.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-4174591));
            jSONObject21.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3003));
            float f2 = 16.0f + access$3003;
            jSONObject21.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(f2));
            jSONArray.add(jSONObject21);
            JSONObject jSONObject22 = new JSONObject();
            jSONObject22.put((String) "type", (Object) Integer.valueOf(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_));
            jSONObject22.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1));
            jSONObject22.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16751156));
            jSONObject22.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(access$3003));
            jSONObject22.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(f2));
            jSONArray.add(jSONObject22);
            JSONObject jSONObject23 = new JSONObject();
            jSONObject23.put((String) "type", (Object) Integer.valueOf(123));
            jSONObject23.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(SupportMenu.CATEGORY_MASK));
            jSONObject23.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(2.0f)));
            jSONArray.add(jSONObject23);
            JSONObject jSONObject24 = new JSONObject();
            jSONObject24.put((String) "type", (Object) Integer.valueOf(140));
            jSONObject24.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject24.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-16739841));
            jSONObject24.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(14.0f)));
            jSONObject24.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(14.0f)));
            jSONArray.add(jSONObject24);
            JSONObject jSONObject25 = new JSONObject();
            jSONObject25.put((String) "type", (Object) Integer.valueOf(130));
            jSONObject25.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-5764853));
            jSONObject25.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-5764853));
            jSONObject25.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(6.0f)));
            jSONObject25.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(6.0f)));
            jSONArray.add(jSONObject25);
            JSONObject jSONObject26 = new JSONObject();
            jSONObject26.put((String) "type", (Object) Integer.valueOf(5));
            jSONObject26.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-9643521));
            jSONObject26.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-9643521));
            jSONObject26.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(5.0f)));
            jSONObject26.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(6.0f)));
            jSONArray.add(jSONObject26);
            JSONObject jSONObject27 = new JSONObject();
            jSONObject27.put((String) "type", (Object) Integer.valueOf(121));
            jSONObject27.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1754368));
            jSONObject27.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject27.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(4.0f)));
            jSONObject27.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Integer.valueOf(0));
            jSONArray.add(jSONObject27);
            JSONObject jSONObject28 = new JSONObject();
            jSONObject28.put((String) "type", (Object) Integer.valueOf(122));
            jSONObject28.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-41123));
            jSONObject28.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject28.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(4.0f)));
            jSONObject28.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Integer.valueOf(0));
            jSONArray.add(jSONObject28);
            return jSONArray.toJSONString();
        }
    }

    static class RideFootConfig {
        private static final int LINE_TYPE_EAGLE_EYE_LINE = 7;
        private static final int LINE_TYPE_OFF_ROUTE = 8;
        private static final int LINE_TYPE_OUT_DOOR_ALPHA_ALTER_LINE = 11;
        private static final int LINE_TYPE_OUT_DOOR_ALPHA_LINE = 10;
        private static final int LINE_TYPE_RIDE_POLYGON_LINE = 5;
        private static final int LINE_TYPE_ROUTE_LINE = 1;
        private static final int LINE_TYPE_ROUTE_LINE_ARROW = 3;
        private static final int LINE_TYPE_ROUTE_LINE_ARROW_HIGH_LIGHT = 4;
        private static final int LINE_TYPE_ROUTE_LINE_HIGH_LIGHT = 2;
        private static final int LINE_TYPE_SEGMENT_LINE_HIGHLIGHT = 9;
        private static final int LINE_TYPE_WALK_AR_EAGLE_EYE_DOT_LINE = 303;
        private static final int LINE_TYPE_WALK_DOT_LINE = 6;

        private RideFootConfig() {
        }

        /* access modifiers changed from: private */
        public static String getRideFootConfig() {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "type", (Object) Integer.valueOf(1));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-4601370));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-8416846));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(18.0f)));
            jSONObject.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(18.2f)));
            jSONArray.add(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "type", (Object) Integer.valueOf(2));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-10577921));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-14395987));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(19.0f)));
            jSONObject2.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(19.2f)));
            jSONArray.add(jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put((String) "type", (Object) Integer.valueOf(4));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(48.0f)));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(48.0f)));
            jSONObject3.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(16.0f)));
            jSONArray.add(jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put((String) "type", (Object) Integer.valueOf(3));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(48.0f)));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(48.0f)));
            jSONObject4.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(12.0f)));
            jSONArray.add(jSONObject4);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put((String) "type", (Object) Integer.valueOf(6));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Integer.valueOf(64));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(17.0f)));
            jSONObject5.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Integer.valueOf(0));
            jSONArray.add(jSONObject5);
            JSONObject jSONObject6 = new JSONObject();
            jSONObject6.put((String) "type", (Object) Integer.valueOf(5));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-7678469));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-7678469));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.getDpPx(1.0f)));
            jSONObject6.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.getDpPx(1.0f)));
            jSONArray.add(jSONObject6);
            JSONObject jSONObject7 = new JSONObject();
            jSONObject7.put((String) "type", (Object) Integer.valueOf(7));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-12414209));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.getDpPx(3.0f)));
            jSONObject7.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Integer.valueOf(0));
            jSONArray.add(jSONObject7);
            JSONObject jSONObject8 = new JSONObject();
            jSONObject8.put((String) "type", (Object) Integer.valueOf(8));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Integer.valueOf(64));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.getDpPx(3.0f)));
            jSONObject8.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Integer.valueOf(0));
            jSONArray.add(jSONObject8);
            JSONObject jSONObject9 = new JSONObject();
            jSONObject9.put((String) "type", (Object) Integer.valueOf(9));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-1286411314));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(0));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(20.0f)));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Integer.valueOf(0));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN, (Object) Integer.valueOf(32));
            jSONObject9.put((String) MiniAppShowRouteConfigHelper.KEY_TEXTURE_LEN_3D, (Object) Integer.valueOf(32));
            jSONArray.add(jSONObject9);
            JSONObject jSONObject10 = new JSONObject();
            jSONObject10.put((String) "type", (Object) Integer.valueOf(10));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-3942657));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-6045706));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(17.0f)));
            jSONObject10.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(17.5f)));
            jSONArray.add(jSONObject10);
            JSONObject jSONObject11 = new JSONObject();
            jSONObject11.put((String) "type", (Object) Integer.valueOf(11));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_FILL_COLOR, (Object) Integer.valueOf(-3942657));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_COLOR, (Object) Integer.valueOf(-6045706));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(13.0f)));
            jSONObject11.put((String) MiniAppShowRouteConfigHelper.KEY_BORDER_LINE_WIDTH, (Object) Float.valueOf(MiniAppShowRouteConfigHelper.linePx(13.5f)));
            jSONArray.add(jSONObject11);
            return jSONArray.toJSONString();
        }
    }

    private MiniAppShowRouteConfigHelper() {
    }

    public static String getRideFootLineConfig() {
        return RideFootConfig.getRideFootConfig();
    }

    public static String getDriveLineConfig() {
        return DriveConfig.getDriveConfig();
    }

    public static String getBusLineConfig() {
        return BusConfig.getBusConfig();
    }

    public static String getCardXmlConfig() {
        return BusConfig.CARD_JSON;
    }

    public static String get7PaddingConfig() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "left", (Object) Float.valueOf(dp2px(50.0f)));
        jSONObject.put((String) "top", (Object) Float.valueOf(dp2px(50.0f)));
        jSONObject.put((String) "right", (Object) Float.valueOf(dp2px(50.0f)));
        jSONObject.put((String) "bottom", (Object) Float.valueOf(dp2px(50.0f)));
        return jSONObject.toJSONString();
    }

    /* access modifiers changed from: private */
    public static float getDpPx(float f) {
        return linePx(f) * 4.0f;
    }

    /* access modifiers changed from: private */
    public static float linePx(float f) {
        return dp2px(f);
    }

    private static float dp2px(float f) {
        return (float) agn.a((Context) AMapAppGlobal.getApplication(), f);
    }
}
