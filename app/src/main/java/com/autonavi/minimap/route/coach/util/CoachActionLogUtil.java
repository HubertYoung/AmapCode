package com.autonavi.minimap.route.coach.util;

import com.amap.bundle.statistics.LogManager;
import org.json.JSONException;
import org.json.JSONObject;

public final class CoachActionLogUtil {

    public enum DateType {
        YESTERDAY,
        TODAY,
        TOMORROW
    }

    public enum FilterType {
        FILTER_TIME,
        FILTER_STATION,
        FILTER_TIME_BUCKET
    }

    public static void a(FilterType filterType) {
        String str;
        switch (filterType) {
            case FILTER_TIME:
                str = "B007";
                break;
            case FILTER_STATION:
                str = "B005";
                break;
            case FILTER_TIME_BUCKET:
                str = "B006";
                break;
            default:
                return;
        }
        LogManager.actionLogV2("P00286", str);
    }

    public static void a(DateType dateType) {
        String str;
        switch (dateType) {
            case YESTERDAY:
                str = "B001";
                break;
            case TODAY:
                str = "B002";
                break;
            case TOMORROW:
                str = "B003";
                break;
            default:
                return;
        }
        LogManager.actionLogV2("P00286", str);
    }

    public static void a(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00287", "B002", jSONObject);
    }
}
