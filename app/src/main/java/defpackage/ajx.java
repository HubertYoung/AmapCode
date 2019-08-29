package defpackage;

import android.support.annotation.Nullable;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/* renamed from: ajx reason: default package */
/* compiled from: PageBackScheme */
public final class ajx {
    private static Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        StringBuilder sb = new StringBuilder("amapuri://ajx?path=");
        sb.append(URLEncoder.encode("path://amap_bundle_carowner/src/car_owner/CarHomeViewController.page.js"));
        hashMap.put("amapuri://carownerservice/homepage", sb.toString());
        Map<String, String> map = a;
        StringBuilder sb2 = new StringBuilder("amapuri://ajx?path=");
        sb2.append(URLEncoder.encode("path://amap_bundle_carowner/src/car_owner/CarListViewController.page.js"));
        map.put("amapuri://carownerservice/carlist", sb2.toString());
        Map<String, String> map2 = a;
        StringBuilder sb3 = new StringBuilder("amapuri://ajx?path=");
        sb3.append(URLEncoder.encode("path://amap_bundle_carowner/src/car_owner/CarAddViewController.page.js"));
        map2.put("amapuri://carownerservice/addcar", sb3.toString());
        Map<String, String> map3 = a;
        StringBuilder sb4 = new StringBuilder("amapuri://ajx?path=");
        sb4.append(URLEncoder.encode("path://amap_bundle_carowner/src/car_owner/CarEditViewController.page.js"));
        map3.put("amapuri://carownerservice/editcar", sb4.toString());
        Map<String, String> map4 = a;
        StringBuilder sb5 = new StringBuilder("amapuri://ajx?path=");
        sb5.append(URLEncoder.encode("path://amap_bundle_carowner/src/car_owner/CarBrandSelectController.page.js"));
        map4.put("amapuri://carownerservice/get_brand", sb5.toString());
        Map<String, String> map5 = a;
        StringBuilder sb6 = new StringBuilder("amapuri://ajx?path=");
        sb6.append(URLEncoder.encode("path://amap_bundle_drive/src/car/select_page/CarSelectViewController.page.js"));
        map5.put("amapuri://drive/vehicleSelect", sb6.toString());
        Map<String, String> map6 = a;
        StringBuilder sb7 = new StringBuilder("amapuri://ajx?path=");
        sb7.append(URLEncoder.encode("path://amap_bundle_basemap_feedback/src/location/FeedbackLocation.page.js"));
        map6.put("amapuri://feedback/location", sb7.toString());
    }

    @Nullable
    public static String a(String str) {
        if (str == null) {
            return null;
        }
        return a.get(str);
    }
}
