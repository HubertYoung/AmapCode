package com.autonavi.minimap.basemap.traffic;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.common.Callback;
import com.autonavi.minimap.basemap.traffic.TrafficTopic.Type;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class TrafficUtil {
    private static final String COMMENTFROM = "commentFrom";
    public static final String FROM = "from";
    public static final String IMAGE_PARAM_IMGOSS = "imgoss=1";
    public static final String KEYWORD = "keyword";
    public static final String POIID = "poiId";
    public static final String THUMBNAIL_IMAGE_PARAM = "@!s";
    public static final String TYPE = "type";

    public static class ImageSizeCallback<Drawable> implements Callback {
        private int height;
        private int width;

        public void callback(Object obj) {
        }

        public void error(Throwable th, boolean z) {
        }

        public ImageSizeCallback(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public int getHeight() {
            return this.width * 2;
        }

        public int getWidth() {
            return this.height * 2;
        }
    }

    public static String getPageName(int i) {
        switch (i) {
            case 0:
                return "主图";
            case 1:
                return "驾车规划";
            case 2:
                return "公交规划";
            case 3:
                return "步行规划";
            case 4:
                return "驾车导航";
            case 5:
                return "公交导航";
            case 6:
                return "步行导航";
            case 7:
                return "实景拍摄";
            case 8:
                return "开放图层";
            case 9:
                return "皮肤图层";
            default:
                return IVoicePackageManager.NAVITTS_ENTER_TYPE_OTHER;
        }
    }

    public static String processImageUrl(String str) {
        if (str.contains(AUScreenAdaptTool.PREFIX_ID) || !str.contains(IMAGE_PARAM_IMGOSS)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("&x-oss-process=image/resize,h_100");
        return sb.toString();
    }

    public static void logAction(Integer num, String str, int i) {
        if (TextUtils.isEmpty(str)) {
            str = IVoicePackageManager.NAVITTS_ENTER_TYPE_OTHER;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            jSONObject.put(POIID, num);
            jSONObject.put("from", getPageName(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_TRAFFIC_EVENT_BUTTON_ID, jSONObject);
        HashMap hashMap = new HashMap();
        hashMap.put("type", str);
        hashMap.put(POIID, String.valueOf(num));
        hashMap.put("from", getPageName(i));
        kd.a((String) "amap.P00001.0.B163", (Map<String, String>) hashMap);
    }

    public static void logAction(String str, int i, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "1".equals(str) ? "useful" : "invalid");
            jSONObject.put(KEYWORD, TrafficTopic.LayerTag2Title.get(Integer.valueOf(i)));
            if (i2 == Type.OFFICCIAL.ordinal()) {
                jSONObject.put("from", "guanfang");
            } else if (i2 == Type.AUTHORITY.ordinal()) {
                jSONObject.put("from", "quanwei");
            } else {
                jSONObject.put("from", "yonghu");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", LogConstant.MAIN_TRAFFIC_EVENT_EVALUATE_BUTTON_ID, jSONObject);
    }

    public static void logActionFromRouteResult(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEYWORD, TrafficTopic.LayerTag2Title.get(Integer.valueOf(i)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str, jSONObject);
    }

    public static void logAction(int i, int i2) {
        String str = TrafficTopic.LayerTag2Title.get(Integer.valueOf(i));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(KEYWORD, str);
            jSONObject.put("type", String.valueOf(i2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B048", jSONObject);
    }
}
