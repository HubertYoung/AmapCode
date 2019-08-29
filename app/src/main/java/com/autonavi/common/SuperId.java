package com.autonavi.common;

import android.text.TextUtils;
import java.io.Serializable;

public class SuperId implements Serializable {
    public static final String BIT_1_BUS = "i";
    public static final String BIT_1_BUS_ROUTE = "d";
    public static final String BIT_1_CAR_ROUTE = "f";
    public static final String BIT_1_FOOT_ROUTE = "e";
    public static final String BIT_1_HOME_COMPANY = "j";
    public static final String BIT_1_INDOOR = "m";
    public static final String BIT_1_LIFE = "l";
    public static final String BIT_1_MAIN_BUSSTATION = "n";
    public static final String BIT_1_MAIN_VOICE_ASSISTANT = "p";
    public static final String BIT_1_MAP_POINT = "h";
    public static final String BIT_1_NAVI = "g";
    public static final String BIT_1_NEARBY_SEARCH = "c";
    public static final String BIT_1_REALTIMEBUS_BUSSTATION = "k";
    public static final String BIT_1_RQBXY = "b";
    public static final String BIT_1_TQUERY = "a";
    public static final String BIT_1_UNKNOWN = "0";
    public static final String BIT_2_HISTORY_TIP = "02";
    public static final String BIT_2_HOT_TAG_MORE = "10";
    public static final String BIT_2_INDOOR_TAG_HOT = "41";
    public static final String BIT_2_INPUT = "03";
    public static final String BIT_2_INPUT_TIP = "01";
    public static final String BIT_2_LIFE_FOOD = "23";
    public static final String BIT_2_MAIN_BUSSTATION = "65";
    public static final String BIT_2_REALTIMEBUS_BUSSTATION = "64";
    public static final String BIT_2_REALTIMEBUS_BUSSTATION_AUTO = "63";
    public static final String BIT_2_TAG_ALL = "12";
    public static final String BIT_2_TAG_AROUND = "06";
    public static final String BIT_2_TAG_DETAIL = "11";
    public static final String BIT_2_TAG_HOT = "05";
    public static final String BIT_2_TAG_HOTEL = "26";
    public static final String BIT_2_TAG_NEARBY_FOOD_CIRCLE = "17";
    public static final String BIT_2_TAG_NEARBY_ICON = "07";
    public static final String BIT_2_TAG_SEARCH_POLYGON = "66";
    public static final String BIT_2_TAG_SUGG_RQ = "14";
    public static final String BIT_2_UNKNOWN = "00";
    public static final String BIT_2_VOICE_ASSISTANT = "04";
    public static final String BIT_2_VOICE_INPUT = "09";
    public static final String BIT_3_BUSLINE = "11";
    public static final String BIT_3_CITY_LIST = "09";
    public static final String BIT_3_CITY_RETURN = "10";
    public static final String BIT_3_ERROR = "08";
    public static final String BIT_3_HISTORY_IDQ = "07";
    public static final String BIT_3_HISTORY_TQUERY = "06";
    public static final String BIT_3_LIFE_FOOD_CIRCEL = "24";
    public static final String BIT_3_LIST_SUB_POI = "19";
    public static final String BIT_3_MAP_SUB_POI = "20";
    public static final String BIT_3_NO_RESULT = "14";
    public static final String BIT_3_RS = "15";
    public static final String BIT_3_TAG_HOTEL_SEARCH = "28";
    public static final String BIT_3_TIP_ = "01";
    public static final String BIT_3_TIP_IDQ = "01";
    public static final String BIT_3_TIP_IDQ_CHILD_ADD_PARENT = "03";
    public static final String BIT_3_TIP_TQUERY = "02";
    public static final String BIT_3_TIP_VIEW_SEARCH = "05";
    public static final String BIT_3_TUIJIAN = "16";
    public static final String BIT_3_UNKNOWN = "00";
    public static final String BIT_4_CLASSIFY_AJX = "06";
    public static final String BIT_4_CLASSIFY_NORMAL = "01";
    public static final String BIT_4_CLASSIFY_SENCE = "02";
    public static final String BIT_4_CLASSIFY_TAG = "08";
    public static final String BIT_4_POIMARKER = "05";
    public static final String BIT_4_UNKNOWN = "00";
    public static final String BIT_4_VIEW_SEARCH = "03";
    public static final String BIT_5_UNKNOWN = "0";
    public static final String BIT_6_UNKNOWN = "0";
    public static final String BIT_7_CLICK_SEARCH = "k_03";
    public static final String BIT_8_TIPS = "k_14";
    private static SuperId instance = new SuperId();
    private String SUPPER_ID_BIT_1 = "";
    private String SUPPER_ID_BIT_2 = "";
    private String SUPPER_ID_BIT_3 = "";
    private String SUPPER_ID_BIT_4 = "";
    private String SUPPER_ID_BIT_5 = "";
    private String SUPPER_ID_BIT_6 = "";
    private String SUPPER_ID_BIT_7 = "";
    private String SUPPER_ID_BIT_8 = "";

    public void setBit1(String str) {
        this.SUPPER_ID_BIT_1 = str;
    }

    public void setBit2(String str) {
        this.SUPPER_ID_BIT_2 = str;
    }

    public void setBit3(String str) {
        this.SUPPER_ID_BIT_3 = str;
    }

    public void setBit4(String str) {
        this.SUPPER_ID_BIT_4 = str;
    }

    public void setBit5(String str) {
        this.SUPPER_ID_BIT_5 = str;
    }

    public void setBit6(String str) {
        this.SUPPER_ID_BIT_6 = str;
    }

    public void setBit7(String str) {
        this.SUPPER_ID_BIT_7 = str;
    }

    public void setBit8(String str) {
        this.SUPPER_ID_BIT_8 = str;
    }

    public String getBit1() {
        return this.SUPPER_ID_BIT_1;
    }

    public String getBit2() {
        return this.SUPPER_ID_BIT_2;
    }

    public String getBit3() {
        return this.SUPPER_ID_BIT_3;
    }

    public String getBit4() {
        return this.SUPPER_ID_BIT_4;
    }

    public String getBit5() {
        return this.SUPPER_ID_BIT_5;
    }

    public String getBit6() {
        return this.SUPPER_ID_BIT_6;
    }

    public String getBit7() {
        return this.SUPPER_ID_BIT_7;
    }

    public String getBit8() {
        return this.SUPPER_ID_BIT_8;
    }

    public void reset() {
        this.SUPPER_ID_BIT_1 = "";
        this.SUPPER_ID_BIT_2 = "";
        this.SUPPER_ID_BIT_3 = "";
        this.SUPPER_ID_BIT_4 = "";
        this.SUPPER_ID_BIT_5 = "";
        this.SUPPER_ID_BIT_6 = "";
        this.SUPPER_ID_BIT_7 = "";
    }

    public String getScenceId() {
        String str = "";
        if (!this.SUPPER_ID_BIT_6.equals("")) {
            StringBuilder sb = new StringBuilder("_");
            sb.append(this.SUPPER_ID_BIT_6);
            str = sb.toString();
        }
        if (!this.SUPPER_ID_BIT_5.equals("")) {
            StringBuilder sb2 = new StringBuilder("_");
            sb2.append(this.SUPPER_ID_BIT_5);
            sb2.append(str);
            str = sb2.toString();
        } else if (!str.equals("")) {
            str = "_0".concat(String.valueOf(str));
        }
        if (!this.SUPPER_ID_BIT_4.equals("")) {
            StringBuilder sb3 = new StringBuilder("_");
            sb3.append(this.SUPPER_ID_BIT_4);
            sb3.append(str);
            str = sb3.toString();
        } else if (!str.equals("")) {
            str = "_00".concat(String.valueOf(str));
        }
        if (!this.SUPPER_ID_BIT_3.equals("")) {
            StringBuilder sb4 = new StringBuilder("_");
            sb4.append(this.SUPPER_ID_BIT_3);
            sb4.append(str);
            str = sb4.toString();
        } else if (!str.equals("")) {
            str = "_00".concat(String.valueOf(str));
        }
        if (!this.SUPPER_ID_BIT_2.equals("")) {
            StringBuilder sb5 = new StringBuilder("_");
            sb5.append(this.SUPPER_ID_BIT_2);
            sb5.append(str);
            str = sb5.toString();
        } else if (!str.equals("")) {
            str = "_00".concat(String.valueOf(str));
        }
        if (this.SUPPER_ID_BIT_1.equals("")) {
            return !str.equals("") ? "0".concat(String.valueOf(str)) : str;
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(this.SUPPER_ID_BIT_1);
        sb6.append(str);
        return sb6.toString();
    }

    private SuperId() {
    }

    public static SuperId getInstance() {
        return instance;
    }

    public SuperId getCopy() {
        SuperId superId = new SuperId();
        superId.SUPPER_ID_BIT_1 = instance.SUPPER_ID_BIT_1;
        superId.SUPPER_ID_BIT_2 = instance.SUPPER_ID_BIT_2;
        superId.SUPPER_ID_BIT_3 = instance.SUPPER_ID_BIT_3;
        superId.SUPPER_ID_BIT_4 = instance.SUPPER_ID_BIT_4;
        superId.SUPPER_ID_BIT_5 = instance.SUPPER_ID_BIT_5;
        superId.SUPPER_ID_BIT_6 = instance.SUPPER_ID_BIT_6;
        superId.SUPPER_ID_BIT_7 = instance.SUPPER_ID_BIT_7;
        superId.SUPPER_ID_BIT_8 = instance.SUPPER_ID_BIT_8;
        return superId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("superid:");
        sb.append(getBit(getBit1()));
        sb.append("_");
        sb.append(getBit(getBit2()));
        sb.append("_");
        sb.append(getBit(getBit3()));
        sb.append("_");
        sb.append(getBit(getBit4()));
        sb.append("_");
        sb.append(getBit(getBit5()));
        sb.append("_");
        sb.append(getBit(getBit6()));
        sb.append("_");
        sb.append(getBit(getBit7()));
        sb.append("_");
        sb.append(getBit(getBit8()));
        return sb.toString();
    }

    private String getBit(String str) {
        return TextUtils.isEmpty(str) ? "00" : str;
    }
}
