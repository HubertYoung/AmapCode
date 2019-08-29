package com.autonavi.common.utils;

import com.autonavi.minimap.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class Constant {
    public static final int ErrorTypeDefault = 1;
    public static final int ErrorTypeInValid = 0;
    public static final int ErrorTypeNoLine = 2;
    public static final String HTTP_TPL_TESTING_AMAP_COM = "http://tpl.testing.amap.com";
    public static final String KEY_IS_BACK = "isBack";
    public static final String KEY_IS_FAVORITE = "key_is_favorite";
    public static final String KEY_RROM_DRIVE = "drive";
    public static final String KEY_TRAFFIC_RESULT = "key_traffic_result";
    public static final String LAUNCHER_ACTIVITY_NAME = "com.autonavi.map.activity.NewMapActivity";
    public static final String MAP_PLACE_DES_2 = "地图选定位置";
    public static final int MAP_TOP_INTERACTIVE_VIEW_ID = R.id.mapTopInteractiveView;
    public static final String OPEN_MAPLAYER_DRAWER = "open_maplayer_drawer";
    public static final String PHONELIST_SPLITER = "$";
    public static final String SOURCE_SINA = "sinaweibo";
    public static final int TRAFFIC_PAGE_REQUEST_CODE = 1000;
    public static final String TYPE_DRIVE = "110";

    public static final class SelectPoiFromMapFragment {

        public enum SelectFor {
            DEFAULT_POI,
            FROM_POI,
            MID_POI,
            MID_POI_1,
            MID_POI_2,
            MID_POI_3,
            TO_POI,
            FIX_POI,
            SAVE_POI
        }
    }

    public static final class SubwayCityListFragment {

        public enum ActionType {
            SELECT_CITY_CALLBACK,
            NEED_TO_SUBWAY
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuggestType {
    }

    public static final class a {
        private static String[] a = {"B035301F2G", "B02F302519", "B036705IZ2", "B00160EDSG", "B001600021", "B024F0318I", "B0FFFR0U6U", "B00170I5LC", "B02000JR83", "B020015V08", "B001816EXG", "B02000K28P", "B0200172JB", "B01FE0JZIO", "B01FE0K9K0", "B01FE160HN", "B0FFF5TS93", "B001600023", "B0016113IJ", "B023E0YC7M", "B01370Y3C6", "B015F16GQE", "B0FFF3K7UZ", "B022700CD7", "B02130TQNU", "B022718IR2", "B001781ZYX", "B0214082PH", "B001B0JDH8", "B00170AOPA", "B001B0IZUF", "B001B15YCV", "B0FFF6PXF2", "B017316LOP", "B02DB07MDA", "B02DB0696H", "B001D08X0O", "B001D08UZ3", "B000A83C36", "B023B07ERI", "B01730K2X2", "B001C7XBE0", "B023B08WDR", "B02F30A89U", "B001C8WIJI", "B000A83M61", "B000A350CB", "B000A833V8", "B001C80DL2", "B00190YPLY", "B000A83AJN", "B02F38MIAX", "B001905HYA", "B00155KAZY", "B00155DNI5", "B0FFFDSOEQ", "B00155KSDE", "B00140WEW0", "B00140TY64", "B00140UERW", "B00140VAP3", "B02F38IPWZ"};

        public static final boolean a(String str) {
            return "060100".equals(str) || "060101".equals(str) || "060102".equals(str) || "060103".equals(str);
        }

        public static final boolean b(String str) {
            return Arrays.asList(a).contains(str);
        }
    }

    public static final class b {
        public static String a = "savetime";
        public static String b = "routetime";
        public static String c = "to_poi";
        public static String d = "mid_poi";
        public static String e = "/autonavi/toPOI.data";
        public static String f = "/autonavi/midPOI.data";
        public static String g = "/autonavi/saveTime.data";
    }
}
